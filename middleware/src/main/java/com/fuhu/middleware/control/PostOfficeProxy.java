package com.fuhu.middleware.control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MailTask;
import com.fuhu.middleware.contract.GSONUtil;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.IPostOfficeVisitor;
import com.fuhu.middleware.contract.IResponse;
import com.fuhu.middleware.service.MockServer;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PostOfficeProxy implements IPostOfficeProxy {
	private static final String TAG = PostOfficeProxy.class.getSimpleName();
	public static String ACTION_DATA_UPDATE = "ACTION_DATA_UPDATE";
    private static PostOfficeProxy instance = new PostOfficeProxy();

    private IPostOfficeVisitor mPostOfficeVisitor;
    private Map<ICommand, MailTask> mMailTaskList;

    private PostOfficeProxy(){
         this.mMailTaskList = new HashMap<ICommand, MailTask>();
         this.mPostOfficeVisitor = new PostOfficeVisitor(this);
    }

    public static PostOfficeProxy getInstance(){
        return instance;
    }
    
    @Override
    public void onMailItemUpdate(ICommand myCommand, AMailItem queryItem, AMailItem result, Object... parameters) {
    	Intent intent = new Intent();
	    Bundle bundle = new Bundle();
	    bundle.putSerializable("mailTask", (Serializable) mMailTaskList.get(myCommand));
	    bundle.putSerializable("queryItem",(Serializable) queryItem);
	    bundle.putSerializable("result", (Serializable) result);
	    bundle.putSerializable("parameters", (Serializable) parameters);
	    
	    intent.setAction(PostOfficeProxy.ACTION_DATA_UPDATE);
	    intent.putExtras(bundle);
	    MailBox.getInstance().receiveMail(intent);
//        MailBox.getInstance().receiveMail(mMailTaskList.get(myCommand), queryItem, result, parameters);
    }

//    @Override
//    public void onMailRequest(Context mContext, AMailItem queryItem, MailTask mMailTask, Object... parameter) {
//        mMailTaskList.put(mMailTask.getCommand(), mMailTask);
//        PostOffice mPostOffice = PostOffice.lookup(mMailTask.getCommand().getID());
//        mPostOffice.doAction(mContext, queryItem, this, parameter);
//    }

    @Override
    public void onMailRequest(Context mContext, MailTask mailTask) {
        ICommand command = mailTask.getCommand();
        mMailTaskList.put(command, mailTask);

        // Checks if using mock data
        if (command.useMockData()) {
            handleMockData(command);
        } else {
            // send request to server
            command.sendRequest(mContext, mPostOfficeVisitor);
        }
    }

    /**
     * Send request to MockServer
     * @param command
     */
    private void handleMockData(ICommand command) {
        // Find a mock response from MockServer
        IResponse mockResponse = MockServer.getInstance().findResponse(command);

        // Checks if MockResponse is exist
        if (mockResponse != null) {
            Log.d(TAG, "mock url: " + mockResponse.getURL());

            AMailItem dataItem = mockResponse.getDataObject();

            if (dataItem != null) {
                Log.d(TAG, "dataItem status: " + dataItem.getStatus());
                onMailItemUpdate(command, command.getDataObject(), dataItem);
            } else if (mockResponse.getBody() != null) {
                // Parsing the json string of MockResponse to java object
                Log.d(TAG, "body: " + mockResponse.getBody());
                try {
                    dataItem = GSONUtil.fromJSON(mockResponse.getBody(), command.getDataModel());
                } catch (JSONException je) {
                    je.printStackTrace();
                    dataItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
                }
                onMailItemUpdate(command, command.getDataObject(), dataItem);
            } else {
                Log.w(TAG, "No data object and json strong of MockResponse");
                // No data object and json strong of MockResponse
                onMailItemUpdate(command, command.getDataObject(),
                        ErrorCodeHandler.genErrorItem(ErrorCodeList.NO_MAILITEM_DATA, command.getDataModel()));
            }
        } else {
            Log.w(TAG, "UNKNOWN ERROR");
            onMailItemUpdate(command, command.getDataObject(),
                    ErrorCodeHandler.genErrorItem(ErrorCodeList.UNKNOWN_ERROR, command.getDataModel()));
        }
    }

    @Override
    public void onMailDeliver(ICommand mCommand, boolean isForceDelivery, Object... parameter) {

    }

//    public void onMailRequest(ICommand mCommand, Context mContext, AMailItem queryItem, Object... parameter) {
//        PostOffice mPostOffice = PostOffice.lookup(mCommand.getID());
//        mPostOffice.doAction(mContext, queryItem, this, parameter);
//    }

    public static boolean clear() {
        if (instance != null) {
            instance = null;
        }
        return true;
    }
}

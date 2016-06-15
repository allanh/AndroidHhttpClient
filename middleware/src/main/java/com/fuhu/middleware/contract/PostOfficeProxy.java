package com.fuhu.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.IBleCommand;
import com.fuhu.middleware.componet.ICommand;
import com.fuhu.middleware.componet.IHttpCommand;
import com.fuhu.middleware.componet.IPostOfficeProxy;
import com.fuhu.middleware.componet.IResponse;
import com.fuhu.middleware.componet.IRtcCommand;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MailTask;
import com.fuhu.middleware.service.MockServer;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PostOfficeProxy implements IPostOfficeProxy {
	private static final String TAG = PostOfficeProxy.class.getSimpleName();
	public static String ACTION_DATA_UPDATE = "ACTION_DATA_UPDATE";
    private static PostOfficeProxy instance = new PostOfficeProxy();
    private Map<ICommand, MailTask> mMailTaskList;
    
    private PostOfficeProxy(){
         this.mMailTaskList = new HashMap<ICommand, MailTask>();
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

            // Send command using NabiVolley
            if (command instanceof IHttpCommand) {
                IHttpCommand httpCommand = (IHttpCommand) command;
                new NabiVolleyActionProxy(mContext, this, httpCommand).execute();
            } else if (command instanceof IRtcCommand) {
                // TODO WebRTC
            } else if (command instanceof IBleCommand) {
                // TODO BLE
            } else {
                Log.d(TAG, "Command isn't HttpCommand.");
//            PostOffice mPostOffice = PostOffice.lookup(command.getID());
//            mPostOffice.doAction(mContext, queryItem, this, parameter);
            }
        }
    }

    /**
     *
     * @param command
     */
    private void handleMockData(ICommand command) {
        // Find a mock response from MockServer
        IResponse mockResponse = MockServer.getInstance().findResponse(command);

        // Checks if MockResponse is exist
        if (mockResponse != null) {
            AMailItem dataItem = mockResponse.getDataObject();

            // Checks if data object is exist
            if (dataItem != null) {
                onMailItemUpdate(command, command.getDataObject(), dataItem);
            } else if (mockResponse.getBody() != null) {
                // Parsing the json string of MockResponse to java object
                try {
                    dataItem = GSONUtil.fromJSON(mockResponse.getBody(), command.getDataModel());
                } catch (JSONException je) {
                    je.printStackTrace();
                    dataItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
                }
                onMailItemUpdate(command, command.getDataObject(), dataItem);
            } else {
                // No data object and json strong of MockResponse
                onMailItemUpdate(command, command.getDataObject(),
                        ErrorCodeHandler.genErrorItem(ErrorCodeList.NO_MAILITEM_DATA, command.getDataModel()));
            }
        } else {
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
}

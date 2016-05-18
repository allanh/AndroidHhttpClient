package com.fuhu.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.HttpCommand;
import com.fuhu.middleware.componet.ICommand;
import com.fuhu.middleware.componet.IPostOfficeProxy;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MailTask;

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

        // Send command using NabiVolley
        if (command instanceof HttpCommand) {
            new NabiVolleyActionProxy(mContext, this, (HttpCommand) command).execute();
        } else {
            Log.d(TAG, "Command isn't HttpCommand.");
//            PostOffice mPostOffice = PostOffice.lookup(command.getID());
//            mPostOffice.doAction(mContext, queryItem, this, parameter);
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

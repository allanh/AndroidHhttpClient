package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fuhu.test.smarthub.middleware.PostOffice;
import com.fuhu.test.smarthub.middleware.componet.AMailItem;
import com.fuhu.test.smarthub.middleware.componet.HttpCommand;
import com.fuhu.test.smarthub.middleware.componet.ICommand;
import com.fuhu.test.smarthub.middleware.componet.IPostOfficeProxy;
import com.fuhu.test.smarthub.middleware.componet.MailTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostOfficeProxy implements IPostOfficeProxy {
	
	public static String ACTION_DATAUPDATE = "ACTION_DATAUPDATE";
    private static PostOfficeProxy instance = new PostOfficeProxy();
    private Map<ICommand, MailTask> mMailTaskList;
    
    private PostOfficeProxy(){
         this.mMailTaskList = new HashMap<ICommand, MailTask>();
    }

    public static PostOfficeProxy getInstance(){
        return instance; 
    }
    
    @Override
    public void onMailItemUpdate(ICommand myCommand, AMailItem queryItem, List<AMailItem> result, Object... parameters) {
    	Intent intent = new Intent();
	    Bundle bundle = new Bundle();
	    bundle.putSerializable("mailTask", (Serializable) mMailTaskList.get(myCommand));
	    bundle.putSerializable("queryItem",(Serializable) queryItem);
	    bundle.putSerializable("result", (Serializable) result);
	    bundle.putSerializable("parameters", (Serializable) parameters);
	    
	    intent.setAction(PostOfficeProxy.ACTION_DATAUPDATE);
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
    public void onMailRequest(Context mContext, List<MailTask> mailTaskList) {
        ICommand command = mailTaskList.get(0).getCommand();
        mMailTaskList.put(command, mailTaskList.get(0));

        if (command instanceof HttpCommand) {
            List<ICommand> commandList = new ArrayList<ICommand>();
            for (MailTask task : mailTaskList) {
                commandList.add(task.getCommand());
            }
            new NabiVolleyActionProxy(mContext, this, commandList).execute();
        } else {

//            PostOffice mPostOffice = PostOffice.lookup(command.getID());
//            mPostOffice.doAction(mContext, queryItem, this, parameter);
        }
    }

    @Override
    public void onMailDeliver(ICommand mCommand, boolean isForceDelivery, Object... parameter) {
       
    }

    public void onMailRequest(ICommand mCommand, Context mContext, AMailItem queryItem, Object... parameter) {
        PostOffice mPostOffice = PostOffice.lookup(mCommand.getID());
        mPostOffice.doAction(mContext, queryItem, this, parameter);
    }
}

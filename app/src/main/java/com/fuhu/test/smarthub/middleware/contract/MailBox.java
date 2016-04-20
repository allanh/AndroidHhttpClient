package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fuhu.test.smarthub.middleware.componet.ICommand;
import com.fuhu.test.smarthub.middleware.componet.IMailItem;
import com.fuhu.test.smarthub.middleware.componet.IMailReceiveCallback;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.MailTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailBox {
    private static final String TAG = MailBox.class.getSimpleName();
    private static MailBox instance = null;
    private Map<MailTask, IMailReceiveCallback> mMailTaskList;
    private Handler mReceiveHandler = null;
    private final static int ReceiveIntent = 1;

    private BlockingQueue<Bundle> receiveQueue;
    private boolean running = false;
    private long startTime;

    /**
     * Default constructor.
     */
    private MailBox(){
        this.mMailTaskList = new HashMap<MailTask, IMailReceiveCallback>();
        
        mReceiveHandler = new Handler(){
			@Override
			public void handleMessage(Message msg){
//                Log.d(TAG, "msg what: " + msg.what);
				if(msg.what == ReceiveIntent){
					Intent mIntent = (Intent) msg.obj;
					Bundle bundle = mIntent.getExtras();
                    MailTask mMailTask = (MailTask)bundle.getSerializable("mailTask");

                    if (receiveQueue == null) {
                        receiveQueue = new LinkedBlockingQueue<Bundle>();
                    }

                    try {
                        // Inserts the MailTask into this queue
                        receiveQueue.put(bundle);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }

                    // check if receiveMailTask is running
                    if (!running) {
                        running = true;
//                        Log.d(TAG, "post : " + mMailTask.getCommand());
                        this.post(receiveMailTask);
                    }
				}
			}
		};
    }
    
    public static MailBox getInstance() {
        if (instance == null) {
            instance = new MailBox();
        }
        return instance;
    }
    
    Context mContext;
    
    public void deliverMail(Context mContext, ICommand mCommand, IMailReceiveCallback mMailRecCallback, Object... parameters){
        MailTask mMailTask=new MailTask();
        mMailTask.setCommand(mCommand);
        String add=null;
        if(mCommand.getAddress()==null){
            add=String.valueOf(System.currentTimeMillis());
        }else{
            add=mCommand.getAddress();
        }
        Log.d("MailBox", "Command: " + mCommand.getID() + " addr: " + add);
        mMailTask.setAddress(add);
        mMailTask.setClassName(this.getClass().getName());
        mMailTaskList.put(mMailTask, mMailRecCallback);
                
        PostOfficeProxy.getInstance().onMailRequest(mContext, (IMailItem)parameters[0], mMailTask, parameters);
    }
    
    public void receiveMail(MailTask mMailTask, IMailItem queryItem, List<IMailItem> result, Object... parameters){
        try{
            for (MailTask key : mMailTaskList.keySet()) {
                if(key.getCommand().equals(mMailTask.getCommand())){
                    if(mMailTask.getAddress() == null || key.getAddress().equals(mMailTask.getAddress())){ 
                        mMailTaskList.get(key).onMailReceive(result);
                        break;
                    }
                }
            }
        }catch(Exception e){
             
        }
    }
    
    public void receiveMail(Intent intent){
        MailTask mMailTask = (MailTask)intent.getExtras().getSerializable("mailTask");
        startTime = System.currentTimeMillis();
        Log.d(TAG, "receive mail: " + mMailTask.getCommand());
//		mReceiveHandler.removeMessages(ReceiveIntent);
		mReceiveHandler.sendMessage(mReceiveHandler.obtainMessage(ReceiveIntent, intent));
	}

    /**
     * Delivery the result to callback in thread
     */
    private Runnable receiveMailTask = new Runnable() {
        @Override
        public void run() {
//            Log.d(TAG, "running: " + running + " queue size: " + receiveQueue.size());

            try{
                while (running && receiveQueue.size() > 0) {
                    // Retrieve MailTask from receive Queue
                    Bundle bundle = receiveQueue.take();

                    MailTask mMailTask = (MailTask)bundle.getSerializable("mailTask");
                    List<IMailItem> result =  (List<IMailItem>)bundle.getSerializable("result");

                    // check the command of MailTask
                    for (MailTask key : mMailTaskList.keySet()) {
                        if(key.getCommand().equals(mMailTask.getCommand())){
                            if(mMailTask.getAddress() == null || key.getAddress().equals(mMailTask.getAddress())){
//                                Log.d(TAG, "receive command: " + mMailTask.getCommand() + " time: " + (System.currentTimeMillis() - startTime));
                                mMailTaskList.get(key).onMailReceive(result);
                                break;
                            }
                        }
                    }
                }
            }catch(Exception e){
            }

//            Log.d(TAG, "stop thread");
            // stop thread
            running = false;
            mReceiveHandler.removeCallbacks(this);
        }
    };
}
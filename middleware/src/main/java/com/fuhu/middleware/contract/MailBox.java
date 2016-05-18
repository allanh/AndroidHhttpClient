package com.fuhu.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.HttpCommand;
import com.fuhu.middleware.componet.ICommand;
import com.fuhu.middleware.componet.IMailReceiveCallback;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MailTask;

import java.util.HashMap;
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
        
        mReceiveHandler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg){
                Log.d(TAG, "msg what: " + msg.what);
				if(msg.what == ReceiveIntent){
					Intent mIntent = (Intent) msg.obj;
					Bundle bundle = mIntent.getExtras();
                    MailTask mMailTask = (MailTask)bundle.getSerializable("mailTask");

                    if (receiveQueue == null) {
                        receiveQueue = new LinkedBlockingQueue<Bundle>();
                    }

                    synchronized (receiveQueue) {
                        try {
                            // Inserts the MailTask into this queue
                            receiveQueue.put(bundle);
                        } catch (InterruptedException e) {
//                        e.printStackTrace();
                        }
                    }

                    // check if receiveMailTask is running
                    if (!running) {
                        running = true;
                        Log.d(TAG, "post : " + mMailTask.getCommand().getID());
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

    public void deliverMail(Context context, ICommand command, IMailReceiveCallback mailRecCallback){
        // Checks if MailReceiveCallback is null.
        if (mailRecCallback == null) {
            Log.e(TAG, "callback is null");
            return;
        }

        if (command != null) {
            // Checks if HttpCommand is valid.
            if (command instanceof HttpCommand) {
                HttpCommand httpCommand = (HttpCommand) command;
                AMailItem errorItem = null;

                if (httpCommand.getDataModel() == null) {
                    errorItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.DATA_CLASS_NULL, AMailItem.class);
                } else if (httpCommand.getURL() == null) {
                    errorItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.URL_NULL, AMailItem.class);
                }

                if (errorItem != null) {
                    Log.e(TAG, "error: " + errorItem.getStatus() + " message: " + errorItem.getMessage());
                    mailRecCallback.onMailReceive(errorItem);
                    return;
                }
            }

            MailTask mMailTask=new MailTask();
            mMailTask.setCommand(command);

            String id = null;
            if(command.getID() != null) {
                id = command.getID();
            } else {
                id = String.valueOf(System.currentTimeMillis());
            }

            Log.d(TAG, "Command: " + command.getID());
            mMailTask.setAddress(id);
            mMailTask.setClassName(this.getClass().getName());

            // set up callback
            mMailTaskList.put(mMailTask, mailRecCallback);

            // send command list to server
            PostOfficeProxy.getInstance().onMailRequest(context, mMailTask);
        } else {
            Log.e(TAG, "command is null");
            mailRecCallback.onMailReceive(ErrorCodeHandler.genErrorItem(ErrorCodeList.COMMAND_NULL, AMailItem.class));
        }
    }

//    public void deliverMail(Context mContext, ICommand mCommand, IMailReceiveCallback mMailRecCallback, Object... parameters){
//        MailTask mMailTask=new MailTask();
//        mMailTask.setCommand(mCommand);
//        String add=null;
//        if(mCommand.getAddress()==null){
//            add=String.valueOf(System.currentTimeMillis());
//        }else{
//            add=mCommand.getAddress();
//        }
//        Log.d("MailBox", "Command: " + mCommand.getID() + " addr: " + add);
//        mMailTask.setAddress(add);
//        mMailTask.setClassName(this.getClass().getName());
//        mMailTaskList.put(mMailTask, mMailRecCallback);
//
//        PostOfficeProxy.getInstance().onMailRequest(mContext, (IMailItem)parameters[0], mMailTask, parameters);
//    }
    
    public void receiveMail(Intent intent){
        MailTask mMailTask = (MailTask)intent.getExtras().getSerializable("mailTask");
        startTime = System.currentTimeMillis();
        Log.d(TAG, "receive mail: " + mMailTask.getCommand().getID());
//		mReceiveHandler.removeMessages(ReceiveIntent);
        Message message = mReceiveHandler.obtainMessage(ReceiveIntent, intent);
		mReceiveHandler.sendMessage(message);
	}

    /**
     * Cancels all pending requests by the specified Id.
     */
    public void cancelMail(Context context, String mailId) {
        VolleyHandler.getInstance(context).cancelRequests(mailId);
    }

    /**
     * Delivery the result to callback in thread
     */
    private Runnable receiveMailTask = new Runnable() {
        @Override
        public void run() {
        Log.d(TAG, "running: " + running + " queue size: " + receiveQueue.size());

        try{
            while (running && receiveQueue.size() > 0) {
                synchronized (receiveQueue) {
                    // Retrieve MailTask from receive Queue
                    Bundle bundle = receiveQueue.take();

                    MailTask mMailTask = (MailTask) bundle.getSerializable("mailTask");
                    AMailItem result = (AMailItem) bundle.getSerializable("result");

                    // check the command of MailTask
                    for (MailTask key : mMailTaskList.keySet()) {
                        if (key.getCommand().equals(mMailTask.getCommand())) {
                            if (mMailTask.getAddress() == null || key.getAddress().equals(mMailTask.getAddress())) {
//                                Log.d(TAG, "receive command: " + mMailTask.getCommand() + " time: " + (System.currentTimeMillis() - startTime));
                                mMailTaskList.get(key).onMailReceive(result);
                                break;
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
//            e.printStackTrace();
        }

        Log.d(TAG, "stop thread");
        // stop thread
        running = false;
        mReceiveHandler.removeCallbacks(this);
        }
    };
}
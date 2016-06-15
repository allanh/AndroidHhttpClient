package com.fuhu.middleware.contract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.ICommand;
import com.fuhu.middleware.componet.IHttpCommand;
import com.fuhu.middleware.componet.IMailReceiveCallback;
import com.fuhu.middleware.componet.MailTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailBox {
    private static final String TAG = MailBox.class.getSimpleName();
    private static MailBox instance = null;
    private Map<MailTask, IMailReceiveCallback> mMailTaskList;
    private static Handler mReceiveHandler;
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
//                Log.d(TAG, "msg what: " + msg.what);
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
//                        Log.d(TAG, "post : " + mMailTask.getCommand().getID());
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

    /**
     * Send command to server
     * @param context
     * @param command
     * @param mailRecCallback
     */
    public void deliverMail(Context context, ICommand command, IMailReceiveCallback mailRecCallback){
        AMailItem errorItem = null;

        /** Null checks */
        if (context == null || command == null || mailRecCallback == null) {
            mailRecCallback.onMailReceive(ErrorCodeHandler.genErrorItem(ErrorCodeList.COMMAND_NULL));
            return;
        }

        /** Checks if the data model have been set up */
        if (command.getDataModel() == null) {
            errorItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.DATA_CLASS_NULL);
        }

        /** Checks if HttpCommand is valid */
        if (command instanceof IHttpCommand) {
            IHttpCommand httpCommand = (IHttpCommand) command;

            if (httpCommand.getURL() == null) {
                errorItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.URL_NULL);
            }
        }

        if (errorItem != null) {
//                    Log.e(TAG, "error: " + errorItem.getStatus() + " message: " + errorItem.getMessage());
            mailRecCallback.onMailReceive(errorItem);
            return;
        }

        /** Make a MailTask */
        MailTask mMailTask=new MailTask();
        mMailTask.setCommand(command);

        String id = null;
        if(command.getID() != null) {
            id = command.getID();
        } else {
            id = String.valueOf(System.currentTimeMillis());
        }

//            Log.d(TAG, "Command: " + command.getID());
        mMailTask.setAddress(id);
        mMailTask.setClassName(this.getClass().getName());

        /** Add a MailTask with callback to MailTaskList */
        mMailTaskList.put(mMailTask, mailRecCallback);

        PostOfficeProxy.getInstance().onMailRequest(context, mMailTask);
    }
    
    public void receiveMail(Intent intent){
        MailTask mMailTask = (MailTask)intent.getExtras().getSerializable("mailTask");
        startTime = System.currentTimeMillis();
//        Log.d(TAG, "receive mail: " + mMailTask.getCommand().getID());
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
//        Log.d(TAG, "running: " + running + " queue size: " + receiveQueue.size());

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

//        Log.d(TAG, "stop thread");
        // stop thread
        running = false;
        mReceiveHandler.removeCallbacks(this);
        }
    };
}
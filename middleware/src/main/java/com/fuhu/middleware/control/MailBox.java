package com.fuhu.middleware.control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.ICommandVisitor;
import com.fuhu.middleware.contract.IMailReceiveCallback;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.MailTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailBox {
    private static final String TAG = MailBox.class.getSimpleName();
    private static final int ReceiveIntent = 1;

    private static MailBox instance = null;
    private static Map<MailTask, IMailReceiveCallback> mMailTaskList;
    private static ReceiveHandler mReceiveHandler;
    private static ICommandVisitor commandVisitor;

    private static BlockingQueue<Bundle> receiveQueue;
    private static boolean running = false;
    private long startTime;

    /**
     * Default constructor.
     */
    private MailBox(){
        this.mMailTaskList = new HashMap<MailTask, IMailReceiveCallback>();
        this.commandVisitor = new CommandVisitor();
        this.running = false;

        mReceiveHandler = new ReceiveHandler();
    }
    
    public static synchronized MailBox getInstance() {
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

        /** Checks if HttpCommand is valid */
        if (!command.isValid(commandVisitor)) {
            mailRecCallback.onMailReceive(ErrorCodeHandler.genErrorItem(ErrorCodeList.INVALID_PARAMETER));
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
     * Inserts the MailTask into receive queue
     */
    private static class ReceiveHandler extends Handler {
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
    }

    /**
     * Delivery the result to callback in thread
     */
    private static Runnable receiveMailTask = new Runnable() {
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
//                                Log.d(TAG, "receive command: " + mMailTask.getCommand());
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

    private void clear() {
        running = false;
        commandVisitor = null;

        if (receiveQueue != null) {
            receiveQueue.clear();
            receiveQueue = null;
        }

        if (mMailTaskList != null) {
            mMailTaskList.clear();
            mMailTaskList = null;
        }

        if (mReceiveHandler != null) {
            mReceiveHandler.removeMessages(ReceiveIntent);
            mReceiveHandler = null;
        }
    }

    public static boolean clearMailBox() {
        if (instance != null) {
            instance.clear();
            instance = null;
        }
        return true;
    }
}
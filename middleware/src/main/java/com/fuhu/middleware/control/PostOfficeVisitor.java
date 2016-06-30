package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.IPostOfficeVisitor;
import com.fuhu.middleware.contract.IWebRtcCommand;

public class PostOfficeVisitor implements IPostOfficeVisitor {
    private static final String TAG = PostOfficeVisitor.class.getSimpleName();
    private static IPostOfficeProxy mPostOfficeProxy;

    public PostOfficeVisitor(IPostOfficeProxy iPostOfficeProxy) {
        this.mPostOfficeProxy = iPostOfficeProxy;
    }

    public void sendRequest(Context context, ICommand command) {

    }

    /**
     * Send request using Volley or other HttpClient
     * @param command
     */
    public void sendRequest(Context context, IHttpCommand command) {
        new NabiVolleyActionProxy(context, mPostOfficeProxy, command).execute();
    }

    /**
     * Send request using WebRTC Data Channel
     * @param command
     */
    public void sendRequest(Context context, IWebRtcCommand command) {
        Log.d(TAG, "send request using WebRTC");
        new MockWebRtcActionProxy(context, mPostOfficeProxy, command).execute();
    }

    /**
     * Send request using Bluetooth
     * @param command
     */
    public void sendRequest(Context context, IBleCommand command) {

    }
}

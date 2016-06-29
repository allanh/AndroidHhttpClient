package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.IPostOfficeVisitor;
import com.fuhu.middleware.contract.IWebRtpCommand;

public class PostOfficeVisitor implements IPostOfficeVisitor {
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
    public void sendRequest(Context context, IWebRtpCommand command) {
        new PnWebRtcActionProxy(context, mPostOfficeProxy, command).execute();
    }

    /**
     * Send request using Bluetooth
     * @param command
     */
    public void sendRequest(Context context, IBleCommand command) {

    }
}

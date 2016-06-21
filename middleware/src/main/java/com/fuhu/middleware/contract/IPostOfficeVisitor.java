package com.fuhu.middleware.contract;

import android.content.Context;

public interface IPostOfficeVisitor {
    public void sendRequest(Context context, ICommand command);

    /**
     * Send request using Volley or other HttpClient
     * @param command
     */
    public void sendRequest(Context context, IHttpCommand command);

    /**
     * Send request using WebRTC Data Channel
     * @param command
     */
    public void sendRequest(Context context, IRtpCommand command);

    /**
     * Send request using Bluetooth
     * @param command
     */
    public void sendRequest(Context context, IBleCommand command);
}

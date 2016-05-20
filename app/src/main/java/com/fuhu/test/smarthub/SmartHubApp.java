package com.fuhu.test.smarthub;

import android.app.Application;

import com.fuhu.middleware.MiddlewareApp;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.IFTTTItem;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MockResponse;
import com.fuhu.middleware.service.MockServer;

public class SmartHubApp extends Application {
    public static final String TAG = SmartHubApp.class.getSimpleName();

    private static SmartHubApp INSTANCE;

    public SmartHubApp() {
        super();
    }

    public static SmartHubApp getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " initial");
        INSTANCE = this;

        // Initializing middleware
        MiddlewareApp.getInstance(this).initial();

        // Tracking mock response
        MockResponse trackResponse = new MockResponse()
                .setURL("http://ec2-54-201-90-113.us-west-2.compute.amazonaws.com:8080/IITService/tracking/voicetracking")
                .setBody("{\"status\":\"0\"");

        // IFTTT mock response
        IFTTTItem iftttItem = new IFTTTItem();
        iftttItem.setStatus(ErrorCodeList.Success.getCode());
        iftttItem.setValue1("ifttt test");

        MockResponse iftttResponse = new MockResponse()
                .setURL("https://maker.ifttt.com/trigger/aws_test/with/key/bSFARZ-rJzNywhtItAh4IS")
                .setDataObject(IFTTTItem.class, iftttItem);

        // Sets the mock responses to MockServer
        MockServer.getInstance().addResponse(trackResponse, iftttResponse);
    }
}

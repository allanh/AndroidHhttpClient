package com.fuhu.test.smarthub;

import android.app.Application;

import com.fuhu.middleware.MiddlewareApp;
import com.fuhu.middleware.MiddlewareConfig;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.MockWebRtcResponse;
import com.fuhu.middleware.contract.PayloadType;
import com.fuhu.middleware.contract.SilkMessageType;
import com.fuhu.middleware.service.MockServer;
import com.fuhu.test.smarthub.componet.AppStatus;
import com.fuhu.test.smarthub.componet.InitItem;

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

        // Initializing mock data
        initMockData();
    }

    private void initMockData() {
        // Tracking mock response
//        MockHttpResponse trackResponse = new MockHttpResponse()
//                .setURL("http://ec2-54-201-90-113.us-west-2.compute.amazonaws.com:8080/IITService/tracking/voicetracking")
//                .setBody("{\"status\":\"0\"}");
//
//        // IFTTT mock response
//        IFTTTItem iftttItem = new IFTTTItem();
//        iftttItem.setStatus(ErrorCodeList.Success.getCode());
//        iftttItem.setValue1("ifttt test");
//
//        MockHttpResponse iftttResponse = new MockHttpResponse()
//                .setURL("https://maker.ifttt.com/trigger/aws_test/with/key/bSFARZ-rJzNywhtItAh4IS")
//                .setDataObject(iftttItem);
//
//
//        // Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(trackResponse, iftttResponse);
//
//
//        // RecipeList mock response ------------------------------------------------------
//        MockResponse recipeListResponse = new MockResponse()
//                .setURL("ReqRecipeList")
//                .setBody("{\"status\":\"0\"}");
//
//// fail mock response
//        TrackItem trackItem_RecipeItemList = new TrackItem();
//        trackItem_RecipeItemList.setStatus("-123");
//        trackItem_RecipeItemList.setMessage("Fail test");
//
//        MockResponse recipeListFailResponse = new MockResponse()
//                .setURL("ReqRecipeList.getFailedURL")
//                .setDataObject(trackItem_RecipeItemList);
//
//// Sets the mock responses to MockServer
//        MockServer.getInstance().addResponse(recipeListResponse, recipeListFailResponse);

        /** INITIALIZED */
        InitItem initItem = new InitItem();
        initItem.setStatus(ErrorCodeList.Success.getCode());
        initItem.setType(SilkMessageType.INITIALIZED.toString());

        MockWebRtcResponse initResponse = new MockWebRtcResponse()
                .setType(SilkMessageType.INITIALIZED)
                .setDataObject(initItem);

        /** App status */
        AppStatus appStatus = new AppStatus();
        appStatus.setStatus(ErrorCodeList.Success.getCode());
        appStatus.setType(SilkMessageType.APP_STATUS.toString());
        appStatus.setClientConnectionStatus("NOT_CONNECTED");
        appStatus.setBleStatus("BLUETOOTH_DISABLED");

        MockWebRtcResponse appStatusResponse = new MockWebRtcResponse()
                .setType(SilkMessageType.APP_STATUS)
                .setDataObject(appStatus);


        /**
         * Device Message, The payload field may contain any arbitrary data structure.
         * Get hub info
         */
        String deviceMessage = "{\"type\": \"DEVICE_MESSAGE\", " +
                "\"deviceId\": \"0fe09a0814465449f1c989478fcea4e6254060243b89b3c8f27b03269cbdd199\"," +
                "\"payload\": {\"score\": \"12345\", \"time\":1234567890 }}";

        MockWebRtcResponse deviceMessageResponse = new MockWebRtcResponse()
                .setType(SilkMessageType.DEVICE_MESSAGE)
                .setPayloadType(PayloadType.GetHubInfo)
                .setBody(deviceMessage);

        /**
         * Check device info
         */
        String deviceInfo = "{\"type\": \"DEVICE_MESSAGE\", " +
                "\"deviceId\": \"0fe09a0814465449f1c989478fcea4e6254060243b89b3c8f27b03269cbdd199\"," +
                "\"payload\": {\"device_info\": {\"id\": \"41j\", \"name\": \"apple\"} }}";

        MockWebRtcResponse deviceInfoResponse = new MockWebRtcResponse()
                .setType(SilkMessageType.DEVICE_MESSAGE)
                .setPayloadType(PayloadType.PushCheckDeviceInfo)
                .setBody(deviceInfo);

//        try {
//            Bundle bundle = GSONUtil.toBundle(new JSONObject(deviceInfo));
//            Log.d(TAG, "json to bundle: " + bundle.toString());
//
//            JSONObject jsonObject = GSONUtil.toJSON(bundle);
//            Log.d(TAG, "bundle to json: " + jsonObject.toString());
//        } catch (JSONException je) {
//            je.printStackTrace();
//        }
//        MockData.testMockData();

        MockServer.getInstance().addResponse(initResponse, appStatusResponse,
                deviceMessageResponse, deviceInfoResponse);

        MiddlewareConfig.enableDebugMode(true);
    }
}

package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fuhu.test.smarthub.middleware.PostOffice;
import com.fuhu.test.smarthub.middleware.componet.AMailItem;
import com.fuhu.test.smarthub.middleware.componet.IPostOfficeProxy;
import com.fuhu.test.smarthub.middleware.componet.ISchedulingActionProxy;
import com.fuhu.test.smarthub.middleware.componet.Log;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VolleyActionProxy implements ISchedulingActionProxy, Runnable{
    private static String TAG = VolleyActionProxy.class.getSimpleName();
    private static final int RETRY_MAX_COUNT = 3;

    private boolean isNeedToDoNext=false;
    private PostOffice mPostOffice;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private List<AMailItem> obtainItems=null;
    private AMailItem mMediaItem;
    private Map<String, String> headerPair = null;

    private String id;
    private int HTTP_ACTION;
    private String url;
    private String apiKey;
    private Object[] params;

    private static int retry_count = 0;

    private static Executor executorGeneral = Executors.newFixedThreadPool(10);
    private static Executor executorSpeed = Executors.newFixedThreadPool(2);

    public VolleyActionProxy(final Context mContext, final PostOffice mPostOffice,AMailItem mMediaItem, final boolean isNeedToDoNext, IPostOfficeProxy mPostOfficeProxy, final Map<String, String> headerPair, int httpAction, Object ...obj ){
        this.mPostOffice=mPostOffice;
        this.isNeedToDoNext=isNeedToDoNext;
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mMediaItem=mMediaItem;
        this.HTTP_ACTION = httpAction;
        this.headerPair = headerPair;

        url=this.mPostOffice.getURL();
        apiKey=this.mPostOffice.get_APIKey();
        params=obj;
        id=mPostOffice.getID();
    }

    public Object execute(String name){
        executorGeneral.execute(this);
        return null;
    }

    public Object executeSpeed(String name){
        executorGeneral.execute(this);
        return null;
    }

    @Override
    public void run() {
        doInBackground();
    }

    protected String doInBackground(String... params) {
        JsonObjectRequest jsonObjectRequest;

        Log.d(TAG, "url: " + url);
        if (headerPair != null) {
            for (String key: headerPair.keySet()) {
                Log.d(TAG, "header: " + key +" value: " + headerPair.get(key));
            }
        }

        if (this.params != null && this.params.length > 0 && this.params[0] instanceof JSONObject) {
            JSONObject mJSONObject = (JSONObject) this.params[0];
            Log.d(TAG, "params: " + mJSONObject.toString());

            // Creates a new request with JSON parameters
            jsonObjectRequest = new JsonObjectRequest(this.HTTP_ACTION, url, mJSONObject, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return (headerPair != null)? headerPair : super.getHeaders();
                }
            };

        } else {

            // Creates a new request
            jsonObjectRequest = new JsonObjectRequest(this.HTTP_ACTION, url, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return (headerPair != null)? headerPair : super.getHeaders();
                }
            };
        }

        // Add the request to the RequestQueue.
        VolleyHandler.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
        return null;
    }

    protected void onPostExecute(String result) {
        Log.i(TAG, "isNeedToDoNext:" + isNeedToDoNext);
        if(isNeedToDoNext){
            mPostOffice.doNextAction(mMediaItem, mPostOfficeProxy, obtainItems);
        }else{
            mPostOffice.onCommandComplete(mPostOfficeProxy, this, mMediaItem, obtainItems);
        }
    }

    /**
     * Listener to receive the JSON response
     */
    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            obtainItems = mPostOffice.JSONParse(isNeedToDoNext, jsonObject, mMediaItem, params);

            if(obtainItems!=null){
                Log.i(TAG, "obtainItems!=null");
                onPostExecute("");
            }else{
                Log.i(TAG, "obtainItems=null" );
                mPostOffice.onCommandFailed(mContext, mPostOfficeProxy, mMediaItem, ErrorCodeHandler.NO_MAILITEM_DATA);
            }
        }
    };

    /**
     * Error listener, or null to ignore errors
     */
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
        String errorMessage = null;
        ErrorCodeHandler errorCodeHandler = ErrorCodeHandler.UNKNOWN_ERROR;

        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
            Log.e(TAG, "Timeout");
            errorCodeHandler = ErrorCodeHandler.VOLLEY_TIMEOUT;
        } else if (volleyError instanceof AuthFailureError) {
            Log.e(TAG, "Auth Failure Error");
            errorCodeHandler = ErrorCodeHandler.VOLLEY_AUTH_FAILURE;
        } else if (volleyError instanceof ServerError) {
            Log.e(TAG, "Server Error");
            errorCodeHandler = ErrorCodeHandler.VOLLEY_SERVER_ERROR;
        } else if (volleyError instanceof NetworkError) {
            Log.e(TAG, "Network Error");
            errorCodeHandler = ErrorCodeHandler.VOLLEY_NETWORK_ERROR;
        } else if (volleyError instanceof ParseError) {
            Log.e(TAG, "Parse Error");
            errorCodeHandler = ErrorCodeHandler.VOLLEY_PARSE_ERROR;
        }

        NetworkResponse response = volleyError.networkResponse;
        if(response != null && response.data != null){
            Log.d(TAG, "status code: " + response.statusCode);
            errorMessage = response.data.toString();
//        }else{
//            errorMessage = volleyError.getClass().getSimpleName();
        }

        if(!TextUtils.isEmpty(errorMessage)){
            Log.d(TAG, "error message: " + errorMessage);
        }

        Log.d(TAG, "retry_count = " + retry_count);
        // check if retry count more than RETRY_MAX_COUNT
        if (retry_count > RETRY_MAX_COUNT) {
            VolleyHandler.getInstance(mContext).clearCache();
            retry_count = 0;
        } else {
            retry_count++;
        }

        mPostOffice.onCommandFailed(mContext, mPostOfficeProxy, mMediaItem, errorCodeHandler);
        }
    };
}

package com.fuhu.middleware.control;

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
import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.Priority;
import com.fuhu.middleware.contract.GSONUtil;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.ISchedulingActionProxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

public class NabiVolleyActionProxy implements ISchedulingActionProxy, Runnable{
    private static String TAG = NabiVolleyActionProxy.class.getSimpleName();
    private static final int RETRY_MAX_COUNT = 3;

    private IHttpCommand mCurrentCommand;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private AMailItem mObtainItem = null;
    private Map<String, String> mHeaderPair = null;
    private Map<String, DataPart> mDataPartMap = null;

    private String mUrl;
    private String mId;
    private int mMethod;
    private JSONObject mDataJSONObject;
    private Priority mPriority;
    private boolean shouldCache;

    private static int retry_count = 0;
    private int mStatusCode;

    private static Executor executorGeneral = Executors.newFixedThreadPool(10);
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public NabiVolleyActionProxy(final Context mContext, IPostOfficeProxy mPostOfficeProxy, IHttpCommand command){
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mCurrentCommand = command;

        this.mId = command.getID();
        this.mUrl = command.getURL();
        this.mDataJSONObject = command.getJSONObject();
        this.mMethod = command.getMethod();
        this.mPriority = command.getPriority();
        this.shouldCache = command.shouldCache();
        this.mHeaderPair = command.getHeaders();
        this.mDataPartMap = command.getDataPartMap();
        mStatusCode = -1;
    }

    public void execute() {
        executorGeneral.execute(this);
    }

    @Override
    public void run() {
        doInBackground();
    }

    protected String doInBackground(String... params) {
        JsonObjectRequest jsonObjectRequest;

        Log.d(TAG, "url: " + mUrl);
        Log.d(TAG, "id: " + mId + " method: " + mMethod + " priority: " + mPriority);
        if (mDataJSONObject != null) {
            Log.d(TAG, "params: " + mDataJSONObject);
        }

        // Creates a new request with JSON parameters
        if (mDataPartMap != null) {
            Log.d(TAG, "send MultiPartRequest");

            Log.d(TAG, "size: " + mDataPartMap.keySet().size());

            // Send MultiPart request using okhttp3
            okhttp3.Response response = OkHttpRequest.getInstance(mContext).sendMultiPartRequest(mCurrentCommand);

            if (response != null) {
                Log.d(TAG, "response code: " + response.code());
                if (response.body() != null) {
                    try {
                        parseJsonObject(response.body().string());
                    } catch (IOException ie) {
                        ie.printStackTrace();
                        onCommandFailed(ErrorCodeList.VOLLEY_NETWORK_ERROR, mCurrentCommand.getDataModel());
                    }
                // Check if the HTTP status code is Ok
                } else if (response.code() != HttpURLConnection.HTTP_OK) {
                    mObtainItem = ErrorCodeHandler.genSuccessItem(mCurrentCommand.getDataModel());
                    onCommandComplete();
                } else {
                    onCommandFailed(ErrorCodeList.UNKNOWN_ERROR, mCurrentCommand.getDataModel());
                }
            } else {
                onCommandFailed(ErrorCodeList.UNKNOWN_ERROR, mCurrentCommand.getDataModel());
            }
        } else {
            if (mHeaderPair != null) {
                for (String key : mHeaderPair.keySet()) {
                    Log.d(TAG, "header: " + key + " value: " + mHeaderPair.get(key));
                }
            }

            jsonObjectRequest = new JsonObjectRequest(mMethod, mUrl, mDataJSONObject, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return (mHeaderPair != null) ? mHeaderPair : super.getHeaders();
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    /*
                    Map<String, String> responseHeaders = response.headers;
                    for (String key : responseHeaders.keySet()) {
                        Log.d(TAG, "cache key: " + key + " value: " + responseHeaders.get(key));
                    }
                    */
                    Log.d(TAG, "response code: " + response.statusCode);
                    mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };

            if (mId != null) {
                jsonObjectRequest.setTag(mId);
            }

/*
        Cache cache = VolleyHandler.getInstance(mContext).getRequestQueue().getCache();
        if (cache instanceof DiskBasedCache) {
            DiskBasedCache diskBasedCache = (DiskBasedCache) cache;
            Cache.Entry entry = diskBasedCache.get(jsonObjectRequest.getCacheKey());
            if (entry != null) {
                Log.d(TAG, "cache hint: " + url);
                Log.d(TAG, "Cache isExpired: " + entry.isExpired() + " refreshNeeded: " + entry.refreshNeeded());
                Log.d(TAG, "Cache lastModified: " + entry.lastModified + " serverDate: " + entry.serverDate);
                Log.d(TAG, "Cache etag: " + entry.etag + " ttl: " + entry.ttl + " softTtl: " + entry.softTtl);
                Log.d(TAG, "Cache data: " + new String(entry.data));
            } else {
                Log.d(TAG, "no cache: " + url);
            }
        }
*/
            // Add the request to the RequestQueue.
            if (jsonObjectRequest != null) {
                VolleyHandler.getInstance(mContext).addToRequestQueue(jsonObjectRequest, shouldCache);
            } else {
                onCommandFailed(ErrorCodeList.UNKNOWN_ERROR, mCurrentCommand.getDataModel());
            }
        }

        return null;
    }

    protected void onPostExecute(String result) {
        onCommandComplete();
    }

    /**
     * Listener to receive the JSON response
     */
    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            parseJsonObject(jsonObject);
        }
    };

    private void parseJsonObject(String jsonString) {
        try {
            parseJsonObject(new JSONObject(jsonString));
        } catch (JSONException je) {
            je.printStackTrace();
            onCommandFailed(ErrorCodeList.VOLLEY_PARSE_ERROR, mCurrentCommand.getDataModel());
        }
    }

    private void parseJsonObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            Log.d(TAG, "response: " + jsonObject);
            Object object = null;

            // Parsing json object to data object
            try {
                object = GSONUtil.fromJSON(jsonObject, mCurrentCommand.getDataModel());
            } catch (JSONException je) {
                je.printStackTrace();
                object = ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
            }

            // Set the original json object into ObtainItem
            if (object != null) {
                mObtainItem = (AMailItem) object;
                mObtainItem.setOriginalJSONObject(jsonObject);
            } else {
                mObtainItem = null;
            }
        } else {
            Log.d(TAG, "response is null");
        }

        if(mObtainItem!=null){
            Log.i(TAG, "mObtainItem!=null");
            onPostExecute("");
        }else{
            Log.i(TAG, "mObtainItem=null");
            onCommandFailed(ErrorCodeList.UNKNOWN_ERROR, mCurrentCommand.getDataModel());
        }
    }

    /**
     * Error listener, or null to ignore errors
     */
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            String errorMessage = null;
            ErrorCodeList errorCodeList = ErrorCodeList.UNKNOWN_ERROR;

            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                Log.e(TAG, "Timeout");
                errorCodeList = ErrorCodeList.VOLLEY_TIMEOUT;
            } else if (volleyError instanceof AuthFailureError) {
                Log.e(TAG, "Auth Failure Error");
                errorCodeList = ErrorCodeList.VOLLEY_AUTH_FAILURE;
            } else if (volleyError instanceof ServerError) {
                Log.e(TAG, "Server Error");
                errorCodeList = ErrorCodeList.VOLLEY_SERVER_ERROR;
            } else if (volleyError instanceof NetworkError) {
                Log.e(TAG, "Network Error");
                errorCodeList = ErrorCodeList.VOLLEY_NETWORK_ERROR;
            } else if (volleyError instanceof ParseError) {
                Log.e(TAG, "Parse Error");
                errorCodeList = ErrorCodeList.VOLLEY_PARSE_ERROR;
                ParseError parseError = (ParseError) volleyError;

                // Check if the HTTP status code is Ok
                if (mStatusCode == HttpURLConnection.HTTP_OK) {
                    mObtainItem = ErrorCodeHandler.genSuccessItem(mCurrentCommand.getDataModel());
                    onCommandComplete();
                    return;
                }
            }

            NetworkResponse response = volleyError.networkResponse;
            if(response != null && response.data != null){
                Log.d(TAG, "status code: " + response.statusCode);
                errorMessage = new String(response.data);
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
            onCommandFailed(errorCodeList, mCurrentCommand.getDataModel());
        }
    };

    private void onCommandComplete() {
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), mObtainItem);
    }

    private void onCommandFailed(ErrorCodeList errorCodeList, Class<? extends AMailItem> classOfT) {
        AMailItem retrieveItem = ErrorCodeHandler.genErrorItem(errorCodeList, classOfT);
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), retrieveItem);
    }
}

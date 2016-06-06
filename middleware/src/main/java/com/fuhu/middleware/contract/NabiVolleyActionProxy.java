package com.fuhu.middleware.contract;

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
import com.fuhu.middleware.componet.IHttpCommand;
import com.fuhu.middleware.componet.IPostOfficeProxy;
import com.fuhu.middleware.componet.ISchedulingActionProxy;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.Priority;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NabiVolleyActionProxy implements ISchedulingActionProxy, Runnable{
    private static String TAG = NabiVolleyActionProxy.class.getSimpleName();
    private static final int RETRY_MAX_COUNT = 3;

    private IHttpCommand mCurrentCommand;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private AMailItem mObtainItem = null;
    private Map<String, String> mHeaderPair = null;
    private Map<String, DataPart> mDataPartMap = null;

    private static int retry_count = 0;

    private static Executor executorGeneral = Executors.newFixedThreadPool(10);
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public NabiVolleyActionProxy(final Context mContext, IPostOfficeProxy mPostOfficeProxy, IHttpCommand command){
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mCurrentCommand = command;
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
        mHeaderPair = mCurrentCommand.getHeaders();
        mDataPartMap = mCurrentCommand.getDataPartMap();
        String id = mCurrentCommand.getID();
        String url = mCurrentCommand.getURL();
        JSONObject jsonObject = mCurrentCommand.getJSONObject();
        int method = mCurrentCommand.getMethod();
        Priority priority = mCurrentCommand.getPriority();
        boolean shouldCache = mCurrentCommand.shouldCache();

        Log.d(TAG, "url: " + url);
        Log.d(TAG, "id: " + id + " method: " + method + " priority: " + priority);

        if (jsonObject != null) {
            Log.d(TAG, "params: " + jsonObject);
        }

        // Creates a new request with JSON parameters
        if (mDataPartMap != null) {
            Log.d(TAG, "send MultiPartRequest");

            Log.d(TAG, "size: " + mDataPartMap.keySet().size());
            for (String key : mDataPartMap.keySet()) {
                DataPart dataPart = mDataPartMap.get(key);
                if (dataPart != null && dataPart.getType() == null) {
                    dataPart.setType(getContentType(dataPart));

                    // reset data part
                    mDataPartMap.remove(key);
                    mDataPartMap.put(key, dataPart);
                }
                Log.d(TAG, "add part: " + dataPart.getFileName() + " type: " + dataPart.getType());
            }

            // upload file with okhttp3
            uploadFileWithOkHttp(url, mHeaderPair, mDataPartMap);

        } else {
            if (mHeaderPair != null) {
                for (String key : mHeaderPair.keySet()) {
                    Log.d(TAG, "header: " + key + " value: " + mHeaderPair.get(key));
                }
            }

            jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return (mHeaderPair != null) ? mHeaderPair : super.getHeaders();
                }

            /*
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                for (String key : responseHeaders.keySet()) {
                    Log.d(TAG, "cache key: " + key + " value: " + responseHeaders.get(key));
                }
                return super.parseNetworkResponse(response);
            }
            */
            };


            if (id != null) {
                jsonObjectRequest.setTag(id);
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
//        Log.i(TAG, "isNeedToDoNext:" + (mCommandQueue.size() > 0));
//        synchronized (mCommandQueue) {
//            if (mCommandQueue.size() > 0) {
//                execute();
//              mPostOffice.doNextAction(mMediaItem, mPostOfficeProxy, mObtainItems);
//            } else {
                onCommandComplete();

//              mPostOffice.onCommandComplete(mPostOfficeProxy, this, mMediaItem, mObtainItems);
//            }
//        }
    }

    /**
     * Listener to receive the JSON response
     */
    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Log.d(TAG, "response: " + jsonObject);
            parseJsonObject(jsonObject);
        }
    };

    private void parseJsonObject(JSONObject jsonObject) {
        if (jsonObject != null && mCurrentCommand != null && mCurrentCommand.getDataModel() != null) {
            Object object = null;

            // Parsing json object to data object
            try {
                object = GSONUtil.fromJSON(jsonObject, mCurrentCommand.getDataModel());
            } catch (JSONException je) {
                je.printStackTrace();
                object = ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
            }

            if (object != null) {
                mObtainItem = (AMailItem) object;
            } else {
                mObtainItem = null;
            }
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
            }

            NetworkResponse response = volleyError.networkResponse;
            if(response != null && response.data != null){
                Log.d(TAG, "status code: " + response.statusCode);
                errorMessage = new String(response.data);
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
            onCommandFailed(errorCodeList, mCurrentCommand.getDataModel());
//            mPostOffice.onCommandFailed(mContext, mPostOfficeProxy, mMediaItem, ErrorCodeList);
        }
    };

    /*
     * Get the file's content URI from the incoming Intent, then
     * get the file's MIME type
     */
    public String getContentType(DataPart dataPart) {
        String mimeType = "image/jpeg";
        Log.d(TAG, "mime type: " + mimeType);
        if (mimeType == null) {
            mimeType = URLConnection.guessContentTypeFromName(dataPart.getFileName());
            Log.d(TAG, "URL content type");
        }

        return mimeType;
    }

    /**
     * Upload file with OkHttp3
     * @param url
     * @param mHeaderPair
     * @param mDataPartMap
     */
    private void uploadFileWithOkHttp(String url, Map<String, String> mHeaderPair, Map<String, DataPart> mDataPartMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (String key: mDataPartMap.keySet()) {
            DataPart dataPart = mDataPartMap.get(key);
            File file = dataPart.getFile();
            if (file != null && file.exists()) {
                builder.addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file));

            }
        }

        MultipartBody requestBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        for (String header : mHeaderPair.keySet()) {
            requestBuilder.addHeader(header, mHeaderPair.get(header));
        }

        Request request = requestBuilder.build();

        if (request != null) {
            try {
                okhttp3.Response response = okHttpClient.newCall(request).execute();
                parseJsonObject(new JSONObject(response.body().string()));
            } catch (JSONException je) {
                je.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    private void onCommandComplete() {
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), mObtainItem);
    }

    private void onCommandFailed(ErrorCodeList errorCodeList, Class<? extends AMailItem> classOfT) {
        AMailItem retrieveItem = ErrorCodeHandler.genErrorItem(errorCodeList, classOfT);
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), retrieveItem);
    }
}

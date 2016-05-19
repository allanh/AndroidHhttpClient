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
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.HttpCommand;
import com.fuhu.middleware.componet.IPostOfficeProxy;
import com.fuhu.middleware.componet.ISchedulingActionProxy;
import com.fuhu.middleware.componet.Log;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NabiVolleyActionProxy implements ISchedulingActionProxy, Runnable{
    private static String TAG = NabiVolleyActionProxy.class.getSimpleName();
    private static final int RETRY_MAX_COUNT = 3;

//    private BlockingQueue<ICommand> mCommandQueue = null;
    private HttpCommand mCurrentCommand;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private AMailItem mObtainItem = null;
    private Map<String, String> mHeaderPair = null;
    
    private static int retry_count = 0;

    private static Executor executorGeneral = Executors.newFixedThreadPool(10);

    public NabiVolleyActionProxy(final Context mContext, IPostOfficeProxy mPostOfficeProxy, HttpCommand command){
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mCurrentCommand = command;
//        mCommandQueue = new LinkedBlockingQueue<ICommand>();

        /**
         * set up Command queue
         */
//        synchronized (mCommandQueue) {
//            try {
//                for (ICommand command : commandList) {
//                    mCommandQueue.put(command);
//                }
//            } catch (InterruptedException ie) {
//                ie.printStackTrace();
//            }
//        }
    }

    public void execute() {
        executorGeneral.execute(this);
    }

    @Override
    public void run() {
        doInBackground();
    }

    protected String doInBackground(String... params) {
//        try {
//            if (mCommandQueue != null && mCommandQueue.size() > 0) {
//                ICommand command = mCommandQueue.take();

//                if (command != null && command instanceof HttpCommand) {
                    JsonObjectRequest jsonObjectRequest;
                    mHeaderPair = mCurrentCommand.getHeaders();
                    String id = mCurrentCommand.getID();
                    String url = mCurrentCommand.getURL();
                    JSONObject jsonObject = mCurrentCommand.getJSONObject();
                    int method = mCurrentCommand.getMethod();
                    HttpCommand.Priority priority = mCurrentCommand.getPriority();

                    Log.d(TAG, "url: " + url);
                    Log.d(TAG, "id: " + id + " method: " + method + " priority: " + priority);
                    if (mHeaderPair != null) {
                        for (String key : mHeaderPair.keySet()) {
                            Log.d(TAG, "header: " + key + " value: " + mHeaderPair.get(key));
                        }
                    }

                    if (jsonObject != null) {
                        Log.d(TAG, "params: " + jsonObject);
                        // Creates a new request with JSON parameters
                        jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return (mHeaderPair != null) ? mHeaderPair : super.getHeaders();
                            }
                        };
                    } else {
                        // Creates a new request
                        jsonObjectRequest = new JsonObjectRequest(method, url, listener, errorListener) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return (mHeaderPair != null) ? mHeaderPair : super.getHeaders();
                            }
                        };
                    }

                    if (id != null) {
                        jsonObjectRequest.setTag(id);
                    }

                    // Add the request to the RequestQueue.
                    VolleyHandler.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
//                }
//            }
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }
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
            if (jsonObject != null && mCurrentCommand != null && mCurrentCommand.getDataModel() != null) {
                Object object = GSONUtil.fromJSON(jsonObject, mCurrentCommand.getDataModel());

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
    };

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

    private void onCommandComplete() {
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), mObtainItem);
    }

    private void onCommandFailed(ErrorCodeList errorCodeList, Class<? extends AMailItem> classOfT) {
        AMailItem retrieveItem = ErrorCodeHandler.genErrorItem(errorCodeList, classOfT);
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), retrieveItem);
    }
}

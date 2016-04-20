package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.LruBitmapCache;
import com.fuhu.test.smarthub.middleware.componet.OkHttpStack;

public class VolleyHandler {
    private static final String TAG = VolleyHandler.class.getSimpleName();
    /*
     * Set whether or not using OkHTTP stack
     */
    private static final boolean USE_OKHTTP = false;

    /*
     * Set whether or not responses to this request should be cached
     */
    private static final boolean SHOULD_CACHE = false;

    private static VolleyHandler instance;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private long startTime;
    private static final int VOLLEY_SOCKET_TIMEOUT = 15000;

    public VolleyHandler(Context mContext) {
        this.mContext = mContext;
        this.mRequestQueue = getRequestQueue();
        this.mImageLoader = getImageLoader();
    }

    public static VolleyHandler getInstance(Context mContext) {
        if(instance==null){
            synchronized(VolleyHandler.class) {
                if(instance==null) {
                    instance = new VolleyHandler(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * @return The Volley Request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Instantiate the RequestQueue.
            if (USE_OKHTTP) {
                mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack());
            } else {
                mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
        }
        return mImageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     * @param request
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * @param request Adds a Request to the dispatch queue
     */
    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(SHOULD_CACHE);
        getRequestQueue().add(request);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Clear the volley cache
     */
    public void clearCache() {
        if (mRequestQueue != null) {
            mRequestQueue.getCache().clear();
        }
    }

    public void release() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
            mRequestQueue = null;
        }
        mContext = null;
        instance = null;
    }

    /**
     * Load image into ImageView by ImageLoader
     * @param imageView image view
     * @param url image url
     * @param width max width
     * @param height max height
     * @param scaleType scale type
     */
    public void setImageView(final ImageView imageView, String url, int width, int height, ImageView.ScaleType scaleType) {
        getImageLoader().get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    imageView.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Volley image Error");

                if (error.getMessage() != null) {
                    Log.d(TAG, "message: " + error.getMessage());
                }
            }
        }, width, height, scaleType);
    }

    /**
     * Set the background
     * @param view
     * @param url
     */
//    public void setBackground(final View view, String url) {
//        getImageLoader().get(url, new ImageLoader.ImageListener() {
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                if (response.getBitmap() != null) {
//                    view.setBackground(new BitmapDrawable(mContext.getResources(), response.getBitmap()));
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, "Volley image Error");
//
//                if (error.getMessage() != null) {
//                    Log.d(TAG, "message: " + error.getMessage());
//                }
//            }
//        });
//    }
}

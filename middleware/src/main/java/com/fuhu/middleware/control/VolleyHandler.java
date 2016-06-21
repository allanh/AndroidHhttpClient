package com.fuhu.middleware.control;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.LruBitmapCache;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class VolleyHandler {
    private static final String TAG = VolleyHandler.class.getSimpleName();
    private static VolleyHandler instance;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private long startTime;
    private static final int VOLLEY_SOCKET_TIMEOUT = 15000;

    /*
     * A private Constructor prevents any other
     * class from instantiating.
     */
    private VolleyHandler(Context mContext) {
        if (mContext != null) {
            this.mContext = mContext.getApplicationContext();
        }
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
            SSLContext sslContext = getAllTrustSSLContext();
            if (sslContext != null && sslContext.getSocketFactory() != null) {
                // Instantiate the RequestQueue with SSL factory to use for HTTPS connections.
                mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(),
                        new HurlStack(null, sslContext.getSocketFactory()));
            } else {
                // Instantiate the RequestQueue.
                mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            }

//            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpsStack());
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
        request.setShouldCache(true);
        getRequestQueue().add(request);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * @param request Adds a Request to the dispatch queue
     */
    public <T> void addToRequestQueue(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        getRequestQueue().add(request);
    }

    /**
     * Adds the specified request to the global queue and .
     * @param request Adds a Request to the dispatch queue
     * @param shouldCache
     */
    public <T> void addToRequestQueue(Request<T> request, boolean shouldCache) {
        request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(shouldCache);
        getRequestQueue().add(request);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * @param tag
     */
    public void cancelRequests(Object tag) {
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

    /**
     * Trusting all certificates over HTTPS
     * @return
     */
    private static SSLContext getAllTrustSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (NoSuchAlgorithmException nae) {
            nae.printStackTrace();
        } catch (KeyManagementException kme) {
            kme.printStackTrace();
        }

        return sslContext;
    }


//    public static SSLContext getSSLContext(Context context) {
//        try {
//            android.util.Log.d(TAG, "create client");
//
//            // file not found
//            InputStream resourceStream = context.getResources().openRawResource(R.raw.test);
//
//            // loading a keystore
//            KeyStore trustStore = KeyStore.getInstance("BKS");
//            trustStore.load(resourceStream, "testing".toCharArray());
//
//            NabiTrustManager myTrustManager = new NabiTrustManager(trustStore);
//            TrustManager[] tms = new TrustManager[] { myTrustManager };
//
//            KeyManager[] kms = null;
////            if (keyStore != null) {
////                KeyManagerFactory kmf = KeyManagerFactory
////                        .getInstance(KeyManagerFactory.getDefaultAlgorithm());
////                kmf.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
////                kms = kmf.getKeyManagers();
////            }
//
//            // Create an SSL context with our private key store
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(kms, tms, null);
//
//            return sslContext;
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}

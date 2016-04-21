package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.componet.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHTTPRequest {
    private static final String TAG = OkHTTPRequest.class.getSimpleName();
    private static final String CHARSET_NAME = "UTF-8";
    private static final long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

    public final static int HTTP_ACTION_GET 	= 0;
    public final static int HTTP_ACTION_PUT 	= 1;
    public final static int HTTP_ACTION_POST 	= 2;
    public final static int HTTP_ACTION_PATCH 	= 3;
    public final static int HTTP_ACTION_DELETE  = 4;

    private static final long TIMEOUT_CONNECTION = 20000L;
    private static final long TIMEOUT_SOCKET = 30000L;

    private static OkHttpClient runtimePoolInstance;
    private static OkHttpClient sslInstance;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private long startTime;

    /**
     * API URL
     */
    private static final String IFTTTServerSite = "https://maker.ifttt.com/trigger/%s/with/key/%s";
    private static final String IFTTTEvent = "aws_test";
    private static final String IFTTTKey = "bSFARZ-rJzNywhtItAh4IS";

    private static final String GCMServerSite = "https://gcm-http.googleapis.com/gcm/send";

    private static final String AWSServerSite  = ":4321/version-service/";

    public static String getAPI_IFTTT() {
        return String.format(IFTTTServerSite, IFTTTEvent, IFTTTKey);
    }

    public static String getAPI_GCM() {
        return GCMServerSite;
    }

    public static OkHttpClient getRuntimePoolInstance(final Context context) {
        if (runtimePoolInstance == null) {
            synchronized(OkHTTPRequest.class) {
                if (runtimePoolInstance == null) {
//                    Cache cache = new Cache(context.getCacheDir(), SIZE_OF_CACHE);
                    // Instantiate the OkHttpClient.
                    runtimePoolInstance = new OkHttpClient().newBuilder()
//                            .cache(cache)
                            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return runtimePoolInstance;
    }

    public static OkHttpClient getSSLInstance(Context context) {
        if (sslInstance == null) {
//            Cache cache = new Cache(context.getCacheDir(), SIZE_OF_CACHE);
            synchronized(OkHTTPRequest.class) {
                if (sslInstance == null) {
                    // Instantiate the OkHttpClient.
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS);

                    // Sets the socket factory used to secure HTTPS connections.
//                    SSLContext sslContext = getSSLContext(context);
                    SSLContext sslContext = getAllTrustSSLContext();
                    if (sslContext != null) {
                        builder.sslSocketFactory(sslContext.getSocketFactory());
                    }

                    sslInstance = builder.build();
                }
            }
        }
        return sslInstance;
    }

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
//            InputStream resourceStream = context.getResources().openRawResource(R.raw.account_fuhu_com);
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

    /**
     * Encodes string to UTF-8
     * @param value
     * @return
     */
    public static String encodeUTF8(String value) {
        if (value != null) {
            try {
                return URLEncoder.encode(String.valueOf(value), CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Synchronous Get
     * @param request
     * @return
     * @throws IOException
     */
    public Response execute (Request request) throws IOException {
        return runtimePoolInstance.newCall(request).execute();
    }

    /**
     * Asynchronous Get
     * @param request
     * @param responseCallback
     */
    public void enqueue (Request request, Callback responseCallback) {
        runtimePoolInstance.newCall(request).enqueue(responseCallback);
    }


    public static JSONObject Request(Context mContext, JSONObject jsonObject, String URL, Map<String, String> headerPair, int Action, OkHttpClient okhttpClient) {
        Log.i(TAG, "start OkHttp request URL:" + URL);
        String result = "";
        Response response = null;

        try {
            switch(Action){
                case HTTP_ACTION_GET:
                    response = okhttpClient.newCall(onHttpGet(URL, headerPair)).execute();
                    break;
                case HTTP_ACTION_PUT:
                    response = okhttpClient.newCall(onHttpPut(jsonObject, URL, headerPair)).execute();
                    break;
                case HTTP_ACTION_POST:
                    response = okhttpClient.newCall(onHttpPost(jsonObject, URL, headerPair)).execute();
                    break;
                case HTTP_ACTION_DELETE:
                    response = okhttpClient.newCall(onHttpDelete(URL, headerPair)).execute();
                    break;
                default:
                    response = null;
                    break;
            }

            Log.i(TAG, "StatusLine StatusCode: " + response.code() + "  -URL["+URL+"]");

            result = response.body().string();
            Log.d(TAG, "result: " + result);
//            result = UnzipUtility.getJsonStringFromGZIP(response);
        }catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            Log.i(TAG, "UnsupportedEncodingException: " );
        }catch (SocketTimeoutException e) {
            e.printStackTrace();
//	    	JSONObject jsonObj = new JSONObject();
//	    	jsonObj.put("status", "-110");
//	    	result = jsonObj.toString();
        }catch (IOException ioe){
            Log.i(TAG, "IOException: " );
            ioe.printStackTrace();
        }catch (Exception e){
            Log.i(TAG, "Exception: " );
            e.printStackTrace();
        }finally{
            if(result != null){
                JSONObject getJSONObject = null;
                try {
                    Log.e(TAG, "Request - JSONObject:  + new JSONObject");
                    getJSONObject = new JSONObject(result);
                } catch (JSONException e) {
                    try {
                        Log.e(TAG, "Request - JSONObject:  + new JSONArray");
                        getJSONObject = new JSONObject();
                        getJSONObject.put("JSONArray", new JSONArray(result));
                    } catch (JSONException e1) {
                        Log.e(TAG, "Request - JSONException JSONArray: " + e.getMessage());
                        e1.printStackTrace();
                    }
                    Log.e(TAG, "Request - JSONException JSONObject: " + e.getMessage());
                }
                return getJSONObject;
            }else{
                Log.e(TAG, "Request - JSONException result = null: ");
                return null;
            }
        }
    }

    protected static Request onHttpGet(String URL, Map<String, String> headerPair){
        Request.Builder builder = new Request.Builder().url(URL);

        // set http header
        if (headerPair != null && headerPair.keySet().size() > 0) {
            for (String key: headerPair.keySet()) {
                String value = headerPair.get(key);
                if (value != null) {
                    Log.d(TAG, "GET Header key: " + key +" value: " + value);
                    value = encodeUTF8(value);
                    builder.addHeader(key, value);
                }
            }
        }
        return builder.build();
    }

    protected static Request onHttpPost(JSONObject jsonObject, String URL, Map<String, String> headerPair) {
        Request.Builder builder = new Request.Builder().url(URL);

        // set http header
        if (headerPair != null && headerPair.keySet().size() > 0) {
            for (String key: headerPair.keySet()) {
                String value = headerPair.get(key);
                if (value != null) {
                    Log.d(TAG, "POST Header key: " + key +" value: " + value);
                    value = encodeUTF8(value);
                    builder.addHeader(key, value);
                }
            }
        }

        if(jsonObject != null){
            try {
                Log.d(TAG, "POST entity: " + jsonObject.toString());
                RequestBody body = RequestBody.create(JSON, jsonObject.toString().getBytes(CHARSET_NAME));
                builder.post(body);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return builder.build();
    }

    protected static Request onHttpPut(JSONObject jsonObject,String URL, Map<String, String> headerPair){
        Request.Builder builder = new Request.Builder().url(URL);

        // set http header
        if (headerPair != null && headerPair.keySet().size() > 0) {
            for (String key: headerPair.keySet()) {
                String value = headerPair.get(key);
                if (value != null) {
                    Log.d(TAG, "PUT Header key: " + key +" value: " + value);
                    value = encodeUTF8(value);
                    builder.addHeader(key, value);
                }
            }
        }

        if(jsonObject != null){
            Log.d(TAG, "PUT entity: " + jsonObject.toString());
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            builder.put(body);
        }

        return builder.build();
    }

    protected static Request onHttpDelete(String URL, Map<String, String> headerPair){
        Request.Builder builder = new Request.Builder()
                .url(URL)
                .delete();

        // set http header
        if (headerPair != null && headerPair.keySet().size() > 0) {
            for (String key: headerPair.keySet()) {
                String value = headerPair.get(key);
                if (value != null) {
                    Log.d(TAG, "DELETE Header key: " + key +" value: " + value);
                    value = encodeUTF8(value);
                    builder.addHeader(key, value);
                }
            }
        }
        return builder.build();
    }
}

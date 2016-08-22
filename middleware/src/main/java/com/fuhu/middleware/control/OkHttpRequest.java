package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IHttpCommand.Method;
import com.fuhu.middleware.componet.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest {
    private static final String TAG = OkHttpRequest.class.getSimpleName();
    private static final String CHARSET_NAME = "UTF-8";
    private static final long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

    private static final long TIMEOUT_CONNECTION = 20000L;
    private static final long TIMEOUT_SOCKET = 30000L;

    private static OkHttpRequest INSTANCE;
    private static OkHttpClient mOkHttpClient;
    private static Context mContext;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private long startTime;

    /*
     * A private Constructor prevents any other
     * class from instantiating.
     */
    private OkHttpRequest(Context context) {
        if (mOkHttpClient == null) {
//            Cache cache = new Cache(context.getCacheDir(), SIZE_OF_CACHE);
            // Instantiate the OkHttpClient.
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SOCKET, TimeUnit.SECONDS);

            // Sets the socket factory used to secure HTTPS connections.
//                    SSLContext sslContext = getSSLContext(context);
//            SSLContext sslContext = getAllTrustSSLContext();
//            if (sslContext != null) {
//                builder.sslSocketFactory(sslContext.getSocketFactory());
//            }

            mOkHttpClient = builder.build();
        }
    }

    public static OkHttpRequest getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();

            if (INSTANCE == null) {
                synchronized(OkHttpRequest.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new OkHttpRequest(context);
                    }
                }
            }
        }

        return INSTANCE;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * Trusting all certificates over HTTPS
     * @return
     */
    private static SSLContext getAllTrustSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            sslContext.init(null, new TrustManager[]{getTrustManager()}, new SecureRandom());
        } catch (NoSuchAlgorithmException nae) {
            nae.printStackTrace();
        } catch (KeyManagementException kme) {
            kme.printStackTrace();
        }

        return sslContext;
    }

    /**
     * Default trust manager
     * @return
     */
    private static X509TrustManager getTrustManager() {
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        return tm;
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
    
    public JSONObject Request(JSONObject jsonObject, String URL, Map<String, String> headerPair, int Action) {
        Log.i(TAG, "start OkHttp request URL:" + URL);
        String result = "";
        Response response = null;

        try {
            switch(Action){
                case Method.GET:
                    response = mOkHttpClient.newCall(onHttpGet(URL, headerPair)).execute();
                    break;
                case Method.PUT:
                    response = mOkHttpClient.newCall(onHttpPut(jsonObject, URL, headerPair)).execute();
                    break;
                case Method.POST:
                    response = mOkHttpClient.newCall(onHttpPost(jsonObject, URL, headerPair)).execute();
                    break;
                case Method.DELETE:
                    response = mOkHttpClient.newCall(onHttpDelete(URL, headerPair)).execute();
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


    /**
     * Upload file with OkHttp3
     * @param command HttpCommand
     */
    public Response sendMultiPartRequest(IHttpCommand command) {
        Map<String, DataPart> dataPartMap = command.getDataPartMap();
        Map<String, String> mHeaderPair = command.getHeaders();

        // Set the MIME type
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        // Add the form data part to the body
        for (String key: dataPartMap.keySet()) {
            DataPart dataPart = dataPartMap.get(key);
            File file = dataPart.getFile();
            if (file != null && file.exists()) {
                String mineType = (dataPart.getType() != null)? dataPart.getType() : getContentType(dataPart);
                Log.d(TAG, "add: " + key + " file: " + dataPart.getFileName() + " type: " + dataPart.getType());

                builder.addFormDataPart(key, file.getName(),
                        RequestBody.create(MediaType.parse(mineType), file));
            }
        }

        // Assemble the specified parts into a request body
        MultipartBody requestBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(command.getURL())
                .post(requestBody);

        // Adds a header with name and value
        for (String header : mHeaderPair.keySet()) {
            requestBuilder.addHeader(header, mHeaderPair.get(header));
        }

        Request request = requestBuilder.build();

        if (request != null) {
            try {
                okhttp3.Response response = mOkHttpClient.newCall(request).execute();
                return response;
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

        return null;
    }


    protected Request onHttpGet(String URL, Map<String, String> headerPair){
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

    protected Request onHttpPost(JSONObject jsonObject, String URL, Map<String, String> headerPair) {
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

    protected Request onHttpPut(JSONObject jsonObject,String URL, Map<String, String> headerPair){
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

    protected Request onHttpDelete(String URL, Map<String, String> headerPair){
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

    /*
     * Get the file's content URI from the incoming Intent, then
     * get the file's MIME type
     */
    public static String getContentType(DataPart dataPart) {
        String mimeType = "image/jpeg";
        Log.d(TAG, "mime type: " + mimeType);
        if (mimeType == null) {
            mimeType = URLConnection.guessContentTypeFromName(dataPart.getFileName());
            Log.d(TAG, "URL content type");
        }

        return mimeType;
    }

    public static void release() {
        mContext = null;
        INSTANCE = null;
    }
}

package com.fuhu.middleware.control;

import com.android.volley.toolbox.HurlStack;


/**
 * An {@link com.android.volley.toolbox.HttpStack HttpStack} implementation which
 * uses OkHttp as its transport.
 */
public class OkHttpStack extends HurlStack {
//    private final OkUrlFactory mFactory;
//
//    public OkHttpStack() {
//        this(new OkHttpClient(), getSSLSocketFactory());
//    }
//
//    public OkHttpStack(OkHttpClient client) {
//        this(client, getSSLSocketFactory());
//    }
//
//    public OkHttpStack(SSLSocketFactory sslSocketFactory) {
//        this(new OkHttpClient(), sslSocketFactory);
//    }
//
//    public OkHttpStack(OkHttpClient client, SSLSocketFactory sslSocketFactory) {
//        if (client == null) {
//            throw new NullPointerException("Client must not be null.");
//        }
//
//        if (sslSocketFactory != null) {
//            client.setSslSocketFactory(sslSocketFactory);
//        }
//
//        mFactory = new OkUrlFactory(client);
//    }
//
//    public static SSLSocketFactory getSSLSocketFactory() {
//        SSLContext sslContext;
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, null, null);
//            return sslContext.getSocketFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new AssertionError(); // The system has no TLS. Just give up.
//        }
//        return null;
//    }
//
//    @Override
//    protected HttpURLConnection createConnection(URL url) throws IOException {
//        return mFactory.open(url);
//    }
}
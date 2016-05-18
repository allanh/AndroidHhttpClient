package com.fuhu.middleware.service.download;

import android.content.Context;

public class DownloadHandler {
    private static final String TAG = DownloadHandler.class.getSimpleName();

    private static DownloadHandler instance;

    private static Context mContext;

    /** Download request queue takes care of handling the request based on priority. */
    private static DownloadRequestQueue mRequestQueue;

    /**
     * Status when the download has failed due to broken url or invalid download url
     */
    public final static int STATUS_NOT_FOUND = 1 << 6;
    /**
     * Default constructor
     */
    public DownloadHandler(Context mContext, String defaultTitle, String dataPath) {
        this.mContext = mContext;
        mRequestQueue = new DownloadRequestQueue(mContext, defaultTitle, dataPath);
    }

    /**
     *
     * @param mContext
     * @param defaultTitle
     * @param dataPath
     * @return
     */
    public static DownloadHandler getInstance(Context mContext, String defaultTitle, String dataPath) {
        if(instance==null){
            instance=new DownloadHandler(mContext, defaultTitle, dataPath);
        }
        return instance;
    }

    public static String getFileName(String url) {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }

    /**
     * Add a new download.  The download will start automatically once the download manager is
     * ready to execute it and connectivity is available.
     *
     * @param request the parameters specifying this download
     * @return an ID for the download, unique across the application.  This ID is used to make future
     * calls related to this download.
     * @throws IllegalArgumentException
     */
    public long add(DownloadRequest request) throws IllegalArgumentException {
        if(request == null) {
            throw new IllegalArgumentException("DownloadRequest cannot be null");
        }

        return mRequestQueue.add(request);
    }

    public long add(String url, DownloadStatusListener mDownloadListener) {
        // TODO set default title and data path
        return add(url, "Download", mContext.getExternalCacheDir().getPath(), mDownloadListener);
    }

    public long add(String url, String title, String dataPath, DownloadStatusListener mDownloadListener) {
        if(url == null || mDownloadListener == null) {
            throw new IllegalArgumentException("URL and DownloadStatusListener cannot be null");
        }

        DownloadRequest downloadRequest = new DownloadRequest(url, title, dataPath, mDownloadListener);
        return mRequestQueue.add(downloadRequest);
    }

    public int cancel(int downloadId) {
        return mRequestQueue.cancel(downloadId);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll();
    }

    public void release() {
        if(mRequestQueue != null) {
            mRequestQueue.release();
            mRequestQueue = null;
        }

        if (instance != null) {
            instance = null;
        }
    }
}

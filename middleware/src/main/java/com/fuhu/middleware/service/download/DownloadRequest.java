package com.fuhu.middleware.service.download;

import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.fuhu.middleware.componet.Priority;

import java.io.File;
import java.util.HashMap;

public class DownloadRequest implements Comparable<DownloadRequest> {

    /** Download Id assigned to this request */
    private int mDownloadId;

    /** The URI resource that this request is to download */
    private Uri mUri;

    /** Download URL **/
    private String mURL;

    /**
     * The destination path on the device where the downloaded files needs to be put
     * It can be either External Directory ( SDcard ) or
     * internal app cache or files directory.
     * For using external SDCard access, application should have
     * this permission android.permission.WRITE_EXTERNAL_STORAGE declared.
     */
    private Uri mDestinationURI;

    /** Destination file path **/
    private String mDestinationURL;

    private RetryPolicy mRetryPolicy;

    private DownloadRequestQueue mRequestQueue;

    private DownloadStatusListener mDownloadListener;

    private HashMap<String, String> mCustomHeader;

    private String mTitle;

    /**
     * Priority values.  Requests will be processed from higher priorities to
     * lower priorities, in FIFO order.
     */
    private Priority mPriority = Priority.NORMAL;

    public DownloadRequest(String mURL, String title, String dataPath, DownloadStatusListener mDownloadListener) {
        if ( mURL == null) {
            throw new NullPointerException();
        }

        this.mURL = mURL;
        Uri uri = Uri.parse(mURL);

        String scheme = uri.getScheme();
        if (scheme == null || (!scheme.equals("http") && !scheme.equals("https"))) {
            throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
        }
        mCustomHeader  = new HashMap<String, String>();
        mUri = uri;

        // local file
        File oldFile = new File(dataPath, getFileName());
        mDestinationURL = oldFile.getAbsolutePath();
        mDestinationURI = Uri.fromFile(oldFile);

        this.mTitle = title;
        this.mDownloadListener = mDownloadListener;
    }

    /**
     * Returns the {@link Priority} of this request; {@link Priority#NORMAL} by default.
     */
    public Priority getPriority() {
        return mPriority;
    }

    /**
     * Set the {@link Priority}  of this request;
     * @param priority
     * @return request
     */
    public DownloadRequest setPriority(Priority priority) {
        mPriority = priority;
        return this;
    }

    /**
     * Adds custom header to request
     * @param key
     * @param value
     */
    public DownloadRequest addCustomHeader(String key, String value) {
        mCustomHeader.put(key, value);
        return this;
    }

    /**
     * Associates this request with the given queue. The request queue will be notified when this
     * request has finished.
     */
    void setDownloadRequestQueue(DownloadRequestQueue downloadQueue) {
        mRequestQueue = downloadQueue;
    }

    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy == null ? new DefaultRetryPolicy() : mRetryPolicy;
    }

    public DownloadRequest setRetryPolicy(RetryPolicy mRetryPolicy) {
        this.mRetryPolicy = mRetryPolicy;
        return this;
    }

    /**
     * Sets the download Id of this request.  Used by {@link DownloadRequestQueue}.
     */
    final void setDownloadId(int downloadId) {
        mDownloadId = downloadId;
    }

    final int getDownloadId() {
        return mDownloadId;
    }

    public DownloadStatusListener getDownloadListener() {
        return mDownloadListener;
    }

    public DownloadRequest setDownloadListener(DownloadStatusListener downloadListener) {
        this.mDownloadListener = downloadListener;
        return this;
    }

    public Uri getUri() {
        return mUri;
    }

    public DownloadRequest setUri(Uri mUri) {
        this.mUri = mUri;
        return this;
    }

    public Uri getDestinationURI() {
        return mDestinationURI;
    }

    public DownloadRequest setDestinationURI(Uri destinationURI) {
        this.mDestinationURI = destinationURI;
        return this;
    }

    public void setDestinationURL(String mDestinationURL) {
        this.mDestinationURL = mDestinationURL;
    }

    public String getDestinationURL() {
        return mDestinationURL;
    }

    public String getFileName() {
        return (mURL != null) ? mURL.substring(mURL.lastIndexOf('/') + 1, mURL.length()) : null;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    //Package-private methods.
    /**
     * Returns all custom headers set by user
     * @return
     */
    public HashMap<String, String> getCustomHeaders() {
        return mCustomHeader;
    }


    public void finish() {
        mRequestQueue.finish(this);
    }

    @Override
    public int compareTo(DownloadRequest other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();

        // High-priority requests are "lesser" so they are sorted to the front.
        // Equal priorities are sorted by sequence number to provide FIFO ordering.
        return left == right ?
                this.mDownloadId - other.mDownloadId :
                right.ordinal() - left.ordinal();
    }
}
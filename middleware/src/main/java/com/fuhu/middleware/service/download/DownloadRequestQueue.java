package com.fuhu.middleware.service.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;

import com.fuhu.middleware.componet.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

public class DownloadRequestQueue {
    private static final String TAG = DownloadRequestQueue.class.getSimpleName();
    private static final int MAX_RETRY_COUNT = 100;

    /**
     * The set of all requests currently being processed by this RequestQueue.
     * A Request will be in this set if it is waiting in any queue or currently being processed.
     */
    private Set<DownloadRequest> mCurrentRequests = new HashSet<DownloadRequest>();

    /** The queue of requests that are actually going out to the network. */
//    private PriorityBlockingQueue<DownloadRequest> mDownloadQueue = new PriorityBlockingQueue<DownloadRequest>();

    private CallBackDelivery mDelivery;

    private Context mContext;

    private DownloadManager downloadManager;

    private Handler downloadHandler;

    /** Whether or not the broadcast receiver has been registered **/
    private boolean isRegistered;

    /** Whether or not the status of request is checking. */
    private boolean isChecking;

    private int retryCount;

    private String mDefaultTitle;

    private String mDataPath;

    /**
     * Delivery class to delivery the call back to call back registrar in main thread.
     */
    class CallBackDelivery {

        /** Used for posting responses, typically to the main thread. */
        private final Executor mCallBackExecutor;

        /**
         * Constructor taking a handler to main thread.
         */
        public CallBackDelivery(final Handler handler) {
            // Make an Executor that just wraps the handler.
            mCallBackExecutor = new Executor() {
                @Override
                public void execute(Runnable command) {
                    handler.post(command);
                }
            };
        }

        public void postDownloadComplete(final DownloadRequest request) {
            mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    request.getDownloadListener().onDownloadComplete(request.getDownloadId());
                }
            });
        }

        public void postDownloadFailed(final DownloadRequest request, final int errorCode, final String errorMsg) {
            mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    request.getDownloadListener().onDownloadFailed(request.getDownloadId(), errorCode, errorMsg);
                }
            });
        }

        public void postProgressUpdate(final DownloadRequest request, final long totalBytes, final long downloadedBytes, final int progress) {
            mCallBackExecutor.execute(new Runnable() {
                public void run() {
                    request.getDownloadListener().onProgress(request.getDownloadId(), totalBytes, downloadedBytes, progress);
                }
            });
        }
    }

    /**
     * Default constructor.
     * @param mContext
     * @param defaultTitle Default Download Title
     * @param dataPath Destination Path
     */
    public DownloadRequestQueue(Context mContext, String defaultTitle, String dataPath) {
        this.mContext = mContext;
        this.downloadHandler = new Handler();
        this.mDefaultTitle = defaultTitle;
        this.mDataPath = dataPath;

        isRegistered = false;
        isChecking = false;

        mDelivery = new CallBackDelivery(new Handler(Looper.getMainLooper()));
        downloadManager
                = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * Check Download status
     */
    private Runnable checkDownloadStatusThread = new Runnable() {
        @Override
        public void run() {
            if (downloadHandler == null) {
                downloadHandler = new Handler();
            }

            // Check if RequestQueue has requests
            if (mCurrentRequests != null && mCurrentRequests.size() > 0) {
                isChecking = true;
                CheckDownloadStatus();
                downloadHandler.postDelayed(this, 100);
            } else {
                isChecking = false;
                downloadHandler.removeCallbacks(this);
            }
        }
    };

    // Package-Private methods.
    /**
     * Generates a download id for the request and adds the download request to the download request queue to act on immediately.
     *
     * @param downloadRequest
     * @return downloadId
     */
    public long add(DownloadRequest downloadRequest) {
        if (mCurrentRequests.contains(downloadRequest)) {
            return 0;
        }

        DownloadManager.Request request = new DownloadManager.Request(downloadRequest.getUri());

        /*
        *  request.setAllowedNetworkTypes()-
        *  Restrict the types of networks over which this download may proceed.
        *  By default, all network types are allowed.
        *  - Request.NETWORK_WIFI
        *  - Request.NETWORK_MOBILE
        *  - Request.NETWORK_WIFI | Request.NETWORK_MOBILE
        *
        *  request.setAllowedOverRoaming(false) -
        *  Set whether this download may proceed over a roaming connection.
        *  By default, roaming is allowed.
        */
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI| DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);

        /*
        *  Set Title of Download
        */
        request.setTitle(downloadRequest.getTitle() != null ? downloadRequest.getTitle() : mDefaultTitle);

        /*
        *  (for API 11 or higher!)
        *  Display Download in Notification
        */
        //request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        Log.d(TAG, "download path: " + SmartHubApp.getDataDirPath() + " filename: " + fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                downloadRequest.getFileName());

        long downloadId = downloadManager.enqueue(request);

        downloadRequest.setDownloadId((int) downloadId);
        downloadRequest.setDownloadRequestQueue(this);

        synchronized (mCurrentRequests) {
            mCurrentRequests.add(downloadRequest);
        }

        // Process requests in the order they are added.
        startCheckingThread();

        return downloadId;
    }

    /**
     * Start checking thread
     */
    public void startCheckingThread() {
        registerReceiver();

        if (downloadHandler == null) {
            downloadHandler = new Handler();
        }

        Log.d(TAG, "isChecking: " + isChecking);
        if (!isChecking) {
            downloadHandler.postDelayed(checkDownloadStatusThread, 100);
        }
    }

    /**
     * Stop checking thread
     */
    public void stopCheckingThread() {
        if (downloadHandler != null && checkDownloadStatusThread != null) {
            isChecking = false;
            downloadHandler.removeCallbacks(checkDownloadStatusThread);
        }

        unregisterReceiver();
    }

    /**
     * Check the status of download requests
     */
    public void CheckDownloadStatus(){
        DownloadManager.Query query = new DownloadManager.Query();

        if (mCurrentRequests == null || downloadManager == null)
            return;

//        Iterator<DownloadRequest> iterator = mCurrentRequests.iterator();
//        while (iterator.hasNext()) {
//            Log.d(TAG, "Download ID: " + iterator.next().getDownloadId());
//        }

        query.setFilterByStatus(DownloadManager.STATUS_FAILED
                | DownloadManager.STATUS_PAUSED | DownloadManager.STATUS_SUCCESSFUL
                | DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_PENDING);

        Cursor cursor = downloadManager.query(query);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int reason = cursor.getInt(columnReason);

            // Get download id
            long downloadId = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
//            Log.d(TAG, "COLUMN_ID: " + downloadId);
            DownloadRequest downloadRequest = null;

            switch(status){
                case DownloadManager.STATUS_FAILED:
                    String failedReason = "";
                    switch(reason){
                        case DownloadManager.ERROR_CANNOT_RESUME:
                            failedReason = "ERROR_CANNOT_RESUME";
                            break;
                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                            failedReason = "ERROR_DEVICE_NOT_FOUND";
                            break;
                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            failedReason = "ERROR_FILE_ALREADY_EXISTS";
                            break;
                        case DownloadManager.ERROR_FILE_ERROR:
                            failedReason = "ERROR_FILE_ERROR";
                            break;
                        case DownloadManager.ERROR_HTTP_DATA_ERROR:
                            failedReason = "ERROR_HTTP_DATA_ERROR";
                            break;
                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            failedReason = "ERROR_INSUFFICIENT_SPACE";
                            break;
                        case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                            failedReason = "ERROR_TOO_MANY_REDIRECTS";
                            break;
                        case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                            failedReason = "ERROR_UNHANDLED_HTTP_CODE";
                            break;
                        case DownloadManager.ERROR_UNKNOWN:
                            failedReason = "ERROR_UNKNOWN";
                            break;
                    }

                    Log.e(TAG, "FAILED: " + failedReason);
                    downloadFailed(downloadId, reason, failedReason);

                    break;

                case DownloadManager.STATUS_PAUSED:
                    String pausedReason = "";

                    switch(reason){
                        case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                            pausedReason = "PAUSED_QUEUED_FOR_WIFI";
                            break;
                        case DownloadManager.PAUSED_UNKNOWN:
                            pausedReason = "PAUSED_UNKNOWN";
                            break;
                        case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                            pausedReason = "PAUSED_WAITING_FOR_NETWORK";
                            break;
                        case DownloadManager.PAUSED_WAITING_TO_RETRY:
                            pausedReason = "PAUSED_WAITING_TO_RETRY";
                            break;
                    }

//                    Log.d(TAG, "PAUSED: " + pausedReason);

                    // check if retryCount more than Maximum Retry Count
                    if (retryCount > MAX_RETRY_COUNT) {
                        pausedReason = "The maximum retry count has been exceeded with no response from the remote endpoint";
                        downloadFailed(downloadId, reason, pausedReason);

                    } else {
                        retryCount++;
                    }
                    break;

                case DownloadManager.STATUS_PENDING:
//                    Log.d(TAG, "PENDING");
                    break;

                case DownloadManager.STATUS_RUNNING:
//                    Log.d(TAG, "RUNNING");
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);

//                    Log.d(TAG, "progress: " + dl_progress);

                    downloadRequest = getDownloadRequest(downloadId);

                    if (downloadRequest != null) {
                        // update progress
                        mDelivery.postProgressUpdate(downloadRequest,
                                bytes_total, bytes_downloaded, dl_progress);
                    } else {
                        Log.d(TAG, "Download request not found");
                    }

                    retryCount = 0;
                    break;

                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.d(TAG, "SUCCESS");
                    downloadRequest = getDownloadRequest(downloadId);

                    if (downloadRequest != null) {
                        finish(downloadRequest);
                        mDelivery.postDownloadComplete(downloadRequest);
                    }
                    break;
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * Download Failed
     * @param downloadId Download ID
     * @param errorCode Error code
     * @param message Error message
     */
    private void downloadFailed(long downloadId, int errorCode, String message) {
//        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        DownloadRequest downloadRequest = getDownloadRequest(downloadId);
        if (downloadRequest != null) {
            mDelivery.postDownloadFailed(downloadRequest, errorCode, message);
        } else {
            Log.d(TAG, "Download request not found");
        }

        // cancel this download request
        cancel(downloadId);
    }

    /**
     * Create a download receiver
     */
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            CheckDownloadStatus();
        }
    };

    /**
     * Register status receiver
     */
    public void registerReceiver() {
        Log.d(TAG, "register receiver: " + isRegistered);
        if (!isRegistered) {
            IntentFilter intentFilter
                    = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

            mContext.registerReceiver(downloadReceiver, intentFilter);
            isRegistered = true;
        }
    }

    /**
     * Unregister status receiver
     */
    public void unregisterReceiver() {
        Log.d(TAG, "unregister receiver: " + isRegistered);
        try {
            if (mContext != null && downloadReceiver != null && isRegistered) {
                mContext.unregisterReceiver(downloadReceiver);
                isRegistered = false;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel all the requests in work
     */
    public void cancelAll() {

        synchronized (mCurrentRequests) {
            for (DownloadRequest request : mCurrentRequests) {
                mCurrentRequests.remove(request);
                downloadManager.remove(request.getDownloadId());
            }

            // Remove all the requests from the queue.
            mCurrentRequests.clear();
        }
    }

    /**
     * Cancel a particular download in progress. Returns 1 if the download Id is found else returns 0.
     *
     * @param downloadId
     * @return int
     */
    public int cancel(long downloadId) {
        synchronized (mCurrentRequests) {
            for (DownloadRequest request : mCurrentRequests) {
                if (request.getDownloadId() == downloadId) {
                    mCurrentRequests.remove(request);
                    downloadManager.remove(downloadId);
                    return 1;
                }
            }
        }

        return 0;
    }

    public void finish(DownloadRequest request) {
        if (mCurrentRequests != null) {//if finish and release are called together it throws NPE

            // Remove from the queue.
            synchronized (mCurrentRequests) {
                moveFile(request);
                mCurrentRequests.remove(request);
                downloadManager.remove(request.getDownloadId());
            }
        }
    }

    /**
     * Cancels all the pending & running requests and releases the Download queue.
     */
    public void release() {
        stopCheckingThread();

        if (mCurrentRequests != null) {
            synchronized (mCurrentRequests) {
                for (DownloadRequest request : mCurrentRequests) {
                    downloadManager.remove(request.getDownloadId());
                }

                mCurrentRequests.clear();
                mCurrentRequests = null;
            }
        }

//        if (mDownloadQueue != null) {
//            mDownloadQueue = null;
//        }
    }


    // private method

    /**
     * Returns the current download request.
     *
     * @param downloadId
     * @return
     */
    private DownloadRequest getDownloadRequest(long downloadId) {
        synchronized (mCurrentRequests) {
            for (DownloadRequest request : mCurrentRequests) {
                if (request.getDownloadId() == downloadId) {
                    return request;
                }
            }
        }
        return null;
    }

    /**
     * Move downloaded file to App's cache folder
     * @param downloadRequest
     */
    private void moveFile(DownloadRequest downloadRequest) {
        if (downloadManager == null) {
            return;
        }

        // local file
        File oldFile = new File(mDataPath, downloadRequest.getFileName());
//        Log.d(TAG, "oldFile: " + oldFile.getAbsolutePath() + " exist: " + oldFile.exists());
        if (oldFile != null && oldFile.exists()) {
            oldFile.delete();
        }

        // downloaded file
        ParcelFileDescriptor parcelFileDescriptor = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        byte data[] = new byte[1024];
        int count;

        try {
            parcelFileDescriptor = downloadManager.openDownloadedFile(downloadRequest.getDownloadId());
            inputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            outputStream = new FileOutputStream(oldFile);

            while ((count = inputStream.read(data)) != -1) {
                // writing data to file
                outputStream.write(data, 0, count);
            }

            // flushing output
            outputStream.flush();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

                if (parcelFileDescriptor != null)
                    parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
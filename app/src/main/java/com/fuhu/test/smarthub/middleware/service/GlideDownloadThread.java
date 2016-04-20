package com.fuhu.test.smarthub.middleware.service;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Download images into cache using Glide
 */
public class GlideDownloadThread extends AsyncTask<Void, Void, Void> {
    private static final String TAG = GlideDownloadThread.class.getSimpleName();
    private Context mContext;
    private List<String> mImageList;
    private boolean isDownloading;

    public GlideDownloadThread(Context context, List<String> imageList) {
        setImageList(context, imageList);
    }

    public void setImageList(Context context, List<String> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
        this.isDownloading = false;
    }

    protected Void doInBackground(Void... params) {
        isDownloading = true;

        if (mContext != null && mImageList != null) {
            for (String url : mImageList) {
                if (isDownloading && url != null) {
//                    Log.d(TAG, "pre download : " + url);
                    Glide.with(mContext)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                }
            }
        }

        isDownloading = false;
        return null;
    }

    /**
     * Cancel download
     */
    public void cancelDownload() {
        isDownloading = false;
    }
}
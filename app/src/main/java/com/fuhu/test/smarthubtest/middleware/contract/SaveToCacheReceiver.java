package com.fuhu.test.smarthubtest.middleware.contract;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fuhu.test.smarthubtest.middleware.MediaItemCollection;
import com.fuhu.test.smarthubtest.middleware.componet.Log;


public class SaveToCacheReceiver extends BroadcastReceiver {
    private static final String TAG = SaveToCacheReceiver.class.getSimpleName();
    public static final String ACTION_SAVE_TO_CACHE = "com.fuhu.test.smarthubtest.action.saveYoCache";//use current Collection to save cache
    public static final String ACTION_RESET_CACHE 	= "com.fuhu.test.smarthubtest.action.resetYoCache";//use new Collection to save cache
    public static final String ACTION_RENEW_CACHE 	= "com.fuhu.test.smarthubtest.action.renewYoCache";//use self adapt Collection to save cache

    public static final String KEY_COLLECTION = "KEY_COLLECTION";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "SaveToCacheReceiver:");

        MediaItemCollection mMediaItemCollection = null;
        if(intent.getExtras() != null && intent.getExtras().getSerializable(KEY_COLLECTION) != null){
            mMediaItemCollection = (MediaItemCollection) intent.getExtras().getSerializable(KEY_COLLECTION);
        }

        if(intent.getAction().equals(ACTION_SAVE_TO_CACHE)){
            Log.d(TAG, "CACHE ACTION: ACTION_SAVE_TO_CACHE");

            MediaItemCollection.saveToCache(context, null);
        }else if(intent.getAction().equals(ACTION_RENEW_CACHE)){
            Log.d(TAG, "CACHE ACTION: ACTION_RENEW_CACHE");
            if(mMediaItemCollection == null){
                Log.d(TAG, "CACHE ACTION: ACTION_RENEW_CACHE with new MediaItem Collection");
                mMediaItemCollection = new MediaItemCollection();
            }
            MediaItemCollection.saveToCache(context, mMediaItemCollection);
        }else if(intent.getAction().equals(ACTION_RESET_CACHE)){
            Log.d(TAG, "CACHE ACTION: ACTION_RESET_CACHE with new MediaItem Collection");
            MediaItemCollection.saveToCache(context, new MediaItemCollection());
        }
    }
}

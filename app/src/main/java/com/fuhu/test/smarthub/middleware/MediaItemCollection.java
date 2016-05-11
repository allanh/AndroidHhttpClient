package com.fuhu.test.smarthub.middleware;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.fuhu.test.smarthub.SmartHubApp;
import com.fuhu.test.smarthub.middleware.componet.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MediaItemCollection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID  = 1L;
	private static MediaItemCollection instance = null;
	private static final String TAG				= MediaItemCollection.class.getSimpleName();
	private static final String mFileName		= SmartHubApp.getApplicationName() + ".ser";
	
	private static HandlerThread mThread 	= null;
	private static Handler mThreadHandler	= null;
	private static ReentrantReadWriteLock mReentrantReadWriteLock = new ReentrantReadWriteLock();

    private int mMapVersion = -1;

	private static boolean isSaved = false;

	private static final String UTF8 = "UTF-8";
	private static final String LATIN1 = "ISO-8859-1";
	private static final String BIG5 = "BIG5";

	public static MediaItemCollection loadFromCache(Context mContext){
    	FileInputStream fis = null;
    	ObjectInputStream ois = null;
    	MediaItemCollection mMediaItemCollection=null;

    	try { 
    		Log.i(TAG,"Read from Cache.");
			fis=mContext.openFileInput(mFileName);
    		ois=new ObjectInputStream(fis);
    		mMediaItemCollection = (MediaItemCollection) ois.readObject();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "loadFromCache - FileNotFoundException : " + e.getMessage());
		} catch (ConcurrentModificationException e) {
			Log.e(TAG, "loadFromCache - ConcurrentModificationException : " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "loadFromCache - IOException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "loadFromCache - ClassNotFoundException : " + e.getMessage());
//		} catch (NameNotFoundException e) {
		}finally{
			if(ois!=null && fis!=null){
				try {
					ois.close();
					fis.close();
				} catch (IOException e) {
					Log.e(TAG, "loadFromCache - IOException : " + e.getMessage());
				}
			}

			if(mMediaItemCollection==null){
				mMediaItemCollection=new MediaItemCollection();
			} else {
				Log.d(TAG, "loadFromCache ok");
			}
		}
    	
    	instance = mMediaItemCollection;
        return mMediaItemCollection;
    }
	
	public static boolean saveToCache(final Context mContext, final MediaItemCollection mMediaItemCollection){
    	boolean isSuccess=true;
    	if(mThread==null){ 
    		 mThread = new HandlerThread(MediaItemCollection.class.getName());
     		 mThread.start();
    		 mThreadHandler=new Handler(mThread.getLooper());
    	}
    	
		mThreadHandler.post(new Runnable() {
			@Override
			public void run() {
				FileOutputStream fos = null;
				ObjectOutputStream oos = null;

				try {
					fos = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
					oos = new ObjectOutputStream(fos);
					mReentrantReadWriteLock.readLock().lock();

					if (mMediaItemCollection != null) {
						Log.i(TAG, "Save to Cache.");
						oos.writeObject(mMediaItemCollection);
					} else {
						Log.i(TAG, "Save to Cache this.");
						oos.writeObject(instance);
					}
					isSaved = true;
				} catch (FileNotFoundException e) {
					Log.e(TAG, "saveToCache - FileNotFoundException : " + e.getMessage());
				} catch (ConcurrentModificationException e) {
					Log.e(TAG, "saveToCache - ConcurrentModificationException : " + e.getMessage());
				} catch (IOException e) {
					Log.e(TAG, "saveToCache - IOException : " + e.getMessage());
//				} catch (NameNotFoundException e) {
				} finally {
					try {
						oos.close();
						fos.close();
					} catch (IOException e) {
						Log.e(TAG, "saveToCache - IOException : " + e.getMessage());
					} finally {
						mReentrantReadWriteLock.readLock().unlock();
						Log.i(TAG, "release read lock");
					}
				}
				mThreadHandler.removeCallbacks(this);
			}

		});
        return isSuccess;
    }

	public static void setSaved(boolean saved) {
		isSaved = saved;
	}

	public static boolean isSaved() {
		return isSaved;
	}

    public void setMapVersion(int mMapVersion) {
        this.mMapVersion = mMapVersion;
    }

    public int getMapVersion() {
        return mMapVersion;
    }
}
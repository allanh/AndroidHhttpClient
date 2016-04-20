package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.util.Log;

import com.fuhu.test.smarthub.middleware.PostOffice;
import com.fuhu.test.smarthub.middleware.componet.IMailItem;
import com.fuhu.test.smarthub.middleware.componet.IPostOfficeProxy;
import com.fuhu.test.smarthub.middleware.componet.ISchedulingActionProxy;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author HsiehChenWei
 */
public class SchedulingActionProxy implements ISchedulingActionProxy, Runnable {
    private static final String TAG = SchedulingActionProxy.class.getSimpleName();
    private boolean isNeedToDoNext=false;
    private PostOffice mPostOffice;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;
    
    private List<IMailItem> obtainItems=null;
    private IMailItem mMediaItem;
    private Map<String, String> headerPair = null;
    
    private int id;
    private int HTTP_ACTION;
    private String url;
    private String apiKey;
    private Object[] params;
    
    static Executor executorGeneral = Executors.newFixedThreadPool(10);  
    static Executor executorSpeed = Executors.newFixedThreadPool(2);  
    
    @Override
    public void run() {
        doInBackground();
    }
    
    private static int count=0;
    public Object execute(String name){
        executorGeneral.execute(this);
        return null;
    }
    
    public Object executeSpeed(String name){
        executorGeneral.execute(this);
        return null;
    }
     
    public SchedulingActionProxy(final Context mContext, final PostOffice mPostOffice, 
                                 IMailItem mMediaItem, final boolean isNeedToDoNext, 
                                 IPostOfficeProxy mPostOfficeProxy, final Map<String, String> headerPair, 
                                 int httpAction, Object ...obj ){
        this.mPostOffice=mPostOffice;
        this.isNeedToDoNext=isNeedToDoNext;
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mMediaItem=mMediaItem;
        this.HTTP_ACTION = httpAction;
        this.headerPair = headerPair;

        url=this.mPostOffice.getURL();
        apiKey=this.mPostOffice.get_APIKey();
        params=obj;
        id=mPostOffice.getID();
    }

    protected String doInBackground(String... params) {
    	try{
            JSONObject JSONFormat=null;

            if(this.params!=null && this.params.length>0 && this.params[0] instanceof JSONObject){
                JSONObject mJSONObject=(JSONObject) this.params[0];
                JSONFormat = OkHTTPRequest.Request(mContext, mJSONObject, url, headerPair, this.HTTP_ACTION, OkHTTPRequest.getSSLInstance(mContext));
            }else{
                JSONFormat = OkHTTPRequest.Request(mContext, null, url, headerPair, this.HTTP_ACTION, OkHTTPRequest.getSSLInstance(mContext));
            }
            
            obtainItems = mPostOffice.JSONParse(isNeedToDoNext, JSONFormat, mMediaItem, this.params);

            if(obtainItems!=null){
            	Log.i(TAG, "HobtainItems!=null" ); 
                onPostExecute("");
            }else{
            	Log.i(TAG, "obtainItems=null" );
                mPostOffice.onCommandFailed(mContext, mPostOfficeProxy, mMediaItem, ErrorCodeHandler.UNKNOWN_EXCEPTION);
            }
    	}catch(Exception e){
            e.printStackTrace();
    	}
        
        return null;
    }
    
    protected void onPostExecute(String result) {
    	Log.i(TAG, "isNeedToDoNext:" + isNeedToDoNext);
    	if(isNeedToDoNext){
	        mPostOffice.doNextAction(mMediaItem, mPostOfficeProxy, obtainItems);
    	}else{
            mPostOffice.onCommandComplete(mPostOfficeProxy, this, mMediaItem, obtainItems);
    	}
    }
}
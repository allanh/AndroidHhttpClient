package com.fuhu.test.smarthub.middleware;

import android.content.Context;

import com.android.volley.Request;
import com.fuhu.test.smarthub.middleware.componet.AMailItem;
import com.fuhu.test.smarthub.middleware.componet.GCMItem;
import com.fuhu.test.smarthub.middleware.componet.HTTPHeader;
import com.fuhu.test.smarthub.middleware.componet.ICommand;
import com.fuhu.test.smarthub.middleware.componet.IFTTTItem;
import com.fuhu.test.smarthub.middleware.componet.IPostOfficeProxy;
import com.fuhu.test.smarthub.middleware.componet.ISchedulingActionProxy;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.MailItem;
import com.fuhu.test.smarthub.middleware.componet.TrackItem;
import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;
import com.fuhu.test.smarthub.middleware.contract.GSONUtil;
import com.fuhu.test.smarthub.middleware.contract.OkHTTPRequest;
import com.fuhu.test.smarthub.middleware.contract.VolleyActionProxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum PostOffice implements ICommand {
    ReqSendToIFTTT(1,1) {
        @Override
        public List<AMailItem> JSONParse(boolean isIterative, JSONObject JSONFormat, AMailItem tmpMediaItem, Object... obj) {
            List<AMailItem> retrieveItem = new ArrayList<AMailItem>();
            String resCode= ErrorCodeHandler.Default.getCode();

            Log.d("request", "ReqSendToIFTTT JSONFormat: " + JSONFormat);
            try {
                if(JSONFormat != null && JSONFormat.has("status")){
                    resCode = JSONFormat.getString("status");
                }else{
                    Log.e(TAG, "JSON is null");
                    return reqErrorCode(retrieveItem, new IFTTTItem(), ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode());
                }

                // Convert JSONObject to Object
                IFTTTItem iftttItem = GSONUtil.fromJSON(JSONFormat, IFTTTItem.class);

                // check status
                if (ErrorCodeHandler.isSuccess(resCode)) {
                    retrieveItem.add(iftttItem);
                } else {
                    return reqErrorCode(retrieveItem, iftttItem, resCode);
                }
			} catch (JSONException e) {
				e.printStackTrace();
			}
            return retrieveItem;
        }

        @Override
        public synchronized Object doAction(Context mContext, AMailItem queryItem, IPostOfficeProxy mPostOfficeProxy, Object... obj) {
            IFTTTItem iftttItem = (IFTTTItem) queryItem;
            this.setURL(OkHTTPRequest.getAPI_IFTTT());

            return new VolleyActionProxy(mContext, this, queryItem, false, mPostOfficeProxy,
                    HTTPHeader.getDefaultHeader(),
                    Request.Method.POST,
                    genJson(iftttItem)).execute("");
        }

        @Override
        public JSONObject genJson(AMailItem queryItem) {
            if (queryItem != null && queryItem instanceof IFTTTItem) {
                IFTTTItem mailItem = (IFTTTItem) queryItem;
                return GSONUtil.toJSON(mailItem, "value1");
            }
            return null;
        }
    },

    ReqSendToGCM(3,3) {
        @Override
        public List<AMailItem> JSONParse(boolean isIterative, JSONObject JSONFormat, AMailItem tmpMediaItem, Object... obj) {
            List<AMailItem> retrieveItem = new ArrayList<AMailItem>();
            String resCode= ErrorCodeHandler.Default.getCode();

            Log.d("request", "ReqSendToGCM JSONFormat: " + JSONFormat);
            try {
                if(JSONFormat != null && JSONFormat.has("status")){
                    resCode = JSONFormat.getString("status");
                }else{
                    Log.e(TAG, "JSON is null");
                    return reqErrorCode(retrieveItem, new MailItem(), ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode());
                }

                // Convert JSONObject to Object
                GCMItem mailItem = GSONUtil.fromJSON(JSONFormat, GCMItem.class);

                // check status
                if (ErrorCodeHandler.isSuccess(resCode)) {
                    retrieveItem.add(mailItem);
                } else {
                    return reqErrorCode(retrieveItem, mailItem, resCode);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retrieveItem;
        }

        @Override
        public synchronized Object doAction(Context mContext, AMailItem queryItem, IPostOfficeProxy mPostOfficeProxy, Object... obj) {
            GCMItem gcmItem = (GCMItem) queryItem;
            this.setURL(OkHTTPRequest.getAPI_GCM());

            return new VolleyActionProxy(mContext, this, queryItem, false, mPostOfficeProxy,
                    HTTPHeader.getHeader(mContext, HTTPHeader.GCM_KEY),
                    Request.Method.POST,
                    genJson(gcmItem)).execute("");
        }

        @Override
        public JSONObject genJson(AMailItem queryItem) {
            if (queryItem != null && queryItem instanceof GCMItem) {
                GCMItem mailItem = (GCMItem) queryItem;
                return GSONUtil.toJSON(mailItem, "to", "data");
            }
            return null;
        }
    },

    ReqSendToTrack(4,4) {
        @Override
        public List<AMailItem> JSONParse(boolean isIterative, JSONObject JSONFormat, AMailItem tmpMediaItem, Object... obj) {
            List<AMailItem> retrieveItem = new ArrayList<AMailItem>();
            String resCode= ErrorCodeHandler.Default.getCode();

            Log.d("request", "ReqSendToTrack JSONFormat: " + JSONFormat);
            try {
                if(JSONFormat != null && JSONFormat.has("status")){
                    resCode = JSONFormat.getString("status");
                }else{
                    Log.e(TAG, "JSON is null");
                    return reqErrorCode(retrieveItem, new MailItem(), ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode());
                }

                // Convert JSONObject to Object
                TrackItem mailItem = GSONUtil.fromJSON(JSONFormat, TrackItem.class);

                // check status
                if (ErrorCodeHandler.isSuccess(resCode)) {
                    retrieveItem.add(mailItem);
                } else {
                    return reqErrorCode(retrieveItem, mailItem, resCode);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retrieveItem;
        }

        @Override
        public synchronized Object doAction(Context mContext, AMailItem queryItem, IPostOfficeProxy mPostOfficeProxy, Object... obj) {
            TrackItem trackItem = (TrackItem) queryItem;
            this.setURL(OkHTTPRequest.getAPI_Track());

            return new VolleyActionProxy(mContext, this, queryItem, false, mPostOfficeProxy,
                    HTTPHeader.getTrackingHeader(mContext),
                    Request.Method.POST,
                    genJson(trackItem)).execute("");
        }

        @Override
        public JSONObject genJson(AMailItem queryItem) {
            if (queryItem != null && queryItem instanceof TrackItem) {
                TrackItem mailItem = (TrackItem) queryItem;
                return GSONUtil.toJSON(mailItem, "VoiceTracking");
            }
            return null;
        }
    }

    ;

    private static HashMap<Integer, PostOffice> lookupTable = new HashMap<Integer, PostOffice>();

    static {
        for (PostOffice tmp : PostOffice.values()) {
            lookupTable.put(tmp._id, tmp);
        }
    }

    public static PostOffice lookup(final int id) {
        return lookupTable.get(id);
    }

    private int _id = -1;
    private int _Content = -1;
    private String _URL = "";
    private String _APIKey;

    public static MediaItemCollection mMediaItemCollection = null;

    private PostOffice(final int id, final int Content) {
        _id = id;
        set_Content(Content);
    }

    private static final String TAG = PostOffice.class.getSimpleName();

    public int get_Content() {
        return _Content;
    }
    public void set_Content(int _Content) {
        this._Content = _Content;
    }
    public void setURL(String url){
        this._URL=url;
    }
    public synchronized String getURL(){
        return this._URL;
    }
    public void setAPIKey(String apiKey){
        this._APIKey=apiKey;
    }
    public String get_APIKey() {
        return _APIKey;
    }
    
    public List<AMailItem> JSONParse(boolean isIterative,JSONObject JSONFormat,AMailItem mMediaItem, Object ...obj){
	return null;
    }

    @Override
    public void  onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeHandler errorCode) {
        List<AMailItem> retrieveItem = new ArrayList<AMailItem>();
        MailItem resultItem = new MailItem();
        resultItem.setStatus(errorCode.getCode());
        resultItem.setMessage(errorCode.toString());
        retrieveItem.add(resultItem);
        onCommandComplete(mPostOfficeProxy, null, queryItem, retrieveItem);
    }
    
    @Override
    public void onCommandComplete(IPostOfficeProxy mPostOfficeProxy, ISchedulingActionProxy mISchedulingActionProxy, AMailItem queryITem, List<AMailItem> result, Object... parameters) {
        Log.d(TAG, "onCommandComplete: " + SmartHubCommand.lookup(this.getID()));
        mPostOfficeProxy.onMailItemUpdate(SmartHubCommand.lookup(this.getID()), queryITem, result, parameters);
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public Object doAction(Context mContext, AMailItem queryItem, IPostOfficeProxy mPostOfficeProxy, Object... obj) {
         return null;
    }

    @Override
    public Object doNextAction(AMailItem queryItem, IPostOfficeProxy mPostOfficeProxy, Object... obj) {
        return null;
    }
    
    @Override
    public int getID() {
        return _id;
    }

    public JSONObject genJson(final AMailItem queryItem) {
        return null;
    }

    public List<AMailItem> reqErrorCode(List<AMailItem> retrieveItem, AMailItem mAMailItem, String resCode){
        if(mAMailItem instanceof MailItem){
            ((MailItem) mAMailItem).setStatus(resCode);
        }

        retrieveItem.add(mAMailItem);
        return retrieveItem;
    }

    public List<AMailItem> reqSuccessCode(List<AMailItem> retrieveItem){
        if (retrieveItem == null) {
            retrieveItem = new ArrayList<AMailItem>();
        }
        MailItem contentItem = new MailItem();
        contentItem.setStatus(ErrorCodeHandler.Success.getCode());
        retrieveItem.add(contentItem);
        return retrieveItem;
    }
}
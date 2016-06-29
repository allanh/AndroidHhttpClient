package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.IWebRtpCommand;
import com.fuhu.middleware.contract.ISchedulingActionProxy;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PnWebRtcActionProxy implements ISchedulingActionProxy, Runnable {
    private static String TAG = NabiVolleyActionProxy.class.getSimpleName();
    private static Executor executorGeneral = Executors.newFixedThreadPool(10);

    private IWebRtpCommand mCurrentCommand;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private AMailItem mObtainItem = null;
    private Map<String, DataPart> mDataPartMap = null;

    private String mId;
    private JSONObject mDataJSONObject;
    private boolean shouldCache;

    public PnWebRtcActionProxy(final Context mContext, IPostOfficeProxy mPostOfficeProxy, IWebRtpCommand command){
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mCurrentCommand = command;

        this.mId = command.getID();
        this.mDataJSONObject = command.getJSONObject();
        this.shouldCache = command.shouldCache();
        this.mDataPartMap = command.getDataPartMap();
    }

    public void execute() {
        executorGeneral.execute(this);
    }

    @Override
    public void run() {
        doInBackground();
    }

    protected String doInBackground(String... params) {
        return null;
    }
}

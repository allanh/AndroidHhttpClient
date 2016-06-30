package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.MailItem;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.ISchedulingActionProxy;
import com.fuhu.middleware.contract.IWebRtcCommand;
import com.fuhu.middleware.contract.SilkMessageType;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MockWebRtcActionProxy implements ISchedulingActionProxy, Runnable {
    private static String TAG = NabiVolleyActionProxy.class.getSimpleName();
    private static Executor executorGeneral = Executors.newFixedThreadPool(10);

    private IWebRtcCommand mCurrentCommand;
    private SilkMessageType mSilkMessageType;
    private IPostOfficeProxy mPostOfficeProxy;
    private Context mContext;

    private AMailItem mQueryItem = null;
    private AMailItem mObtainItem = null;
    private Map<String, DataPart> mDataPartMap = null;

    private String mId;
    private JSONObject mDataJSONObject;
    private boolean shouldCache;

    public MockWebRtcActionProxy(final Context mContext, IPostOfficeProxy mPostOfficeProxy, IWebRtcCommand command){
        this.mPostOfficeProxy=mPostOfficeProxy;
        this.mContext = mContext;
        this.mCurrentCommand = command;

        this.mId = command.getID();
        this.mDataJSONObject = command.getJSONObject();
        this.shouldCache = command.shouldCache();
        this.mQueryItem = command.getDataObject();
        this.mDataPartMap = command.getDataPartMap();
        this.mSilkMessageType = command.getSilkMessageType();
    }

    public void execute() {
        executorGeneral.execute(this);
    }

    @Override
    public void run() {
        doInBackground();
    }

    protected String doInBackground(String... params) {
        // Checks if SilkMessageType is exist
        if (mSilkMessageType == null) {
            onCommandFailed(ErrorCodeList.INVALID_PARAMETER, mCurrentCommand.getDataModel());
            return null;
        }

        switch (mSilkMessageType) {
            case INITIALIZED:
                mObtainItem = newInstance(mCurrentCommand.getDataModel());
                mObtainItem.setStatus(ErrorCodeList.Success.getCode());
                onCommandComplete(mObtainItem);
                break;

            default:
                onCommandFailed(ErrorCodeList.INVALID_PARAMETER, mCurrentCommand.getDataModel());
        }

        return null;
    }

    /**
     * Returns a new instance of the class represented by this {@code Class},
     * created by invoking the default (that is, zero-argument) constructor.
     */
    private AMailItem newInstance(Class<? extends AMailItem> classOfT) {
        try {
            Object object = (classOfT != null) ? classOfT.newInstance()
                    : MailItem.class.newInstance();

            return (AMailItem) object;
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return null;
    }

    private void onCommandComplete() {
        mObtainItem = ErrorCodeHandler.genErrorItem(ErrorCodeList.Success, mCurrentCommand.getDataModel());
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), mObtainItem);
    }

    private void onCommandComplete(AMailItem mailItem) {
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), mailItem);
    }

    private void onCommandFailed(ErrorCodeList errorCodeList, Class<? extends AMailItem> classOfT) {
        AMailItem retrieveItem = ErrorCodeHandler.genErrorItem(errorCodeList, classOfT);
        mPostOfficeProxy.onMailItemUpdate(mCurrentCommand, mCurrentCommand.getDataObject(), retrieveItem);
    }
}

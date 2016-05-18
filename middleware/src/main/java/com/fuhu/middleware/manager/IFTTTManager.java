package com.fuhu.middleware.manager;

import android.content.Context;

import com.fuhu.middleware.callback.IFTTTCallback;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.IFTTTItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IFTTTManager {
    private static final String TAG = IFTTTManager.class.getSimpleName();

    public static void sendToIFTTT(Context context, IFTTTCallback mIFTTTCallback, String... messages) {
        IFTTTItem iftttItem = new IFTTTItem();

        //        {"value1":"android","value2":"test","value3":"aaa"}
        Method method;
        for (int i = 0; i < messages.length && i < 3; i++) {
            if (messages[i] != null) {
                Log.d(TAG, "message[" + i + "]: " + messages[i]);

                try {
                    method = IFTTTItem.class.getDeclaredMethod("setValue" + (i + 1), String.class);
                    method.invoke(iftttItem, messages[i]);
                } catch (InvocationTargetException ite) {
                    ite.printStackTrace();
                } catch (IllegalAccessException iae) {
                    iae.printStackTrace();
                } catch (NoSuchMethodException nsme) {
                    nsme.printStackTrace();
                }
            }
        }

        Log.d(TAG, "value1: " + iftttItem.getValue1() + " value2: " + iftttItem.getValue2());
        IFTTTCallback.reqSend(context, mIFTTTCallback, iftttItem);
    }
}

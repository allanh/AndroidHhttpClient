package com.fuhu.test.smarthubtest.manager;

import android.content.Context;

import com.fuhu.test.smarthubtest.callback.IFTTTCallback;
import com.fuhu.test.smarthubtest.middleware.componet.Log;
import com.fuhu.test.smarthubtest.middleware.componet.MailItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IFTTTManager {
    private static final String TAG = IFTTTManager.class.getSimpleName();

    public static void sendToIFTTT(Context context, IFTTTCallback mIFTTTCallback, String... messages) {
        MailItem mailItem = new MailItem();

        //        {"value1":"android","value2":"test","value3":"aaa"}
        Method method;
        for (int i = 0; i < messages.length && i < 3; i++) {
            if (messages[i] != null) {
                Log.d(TAG, "message[" + i + "]: " + messages[i]);

                try {
                    method = MailItem.class.getDeclaredMethod("setValue" + (i + 1), String.class);
                    method.invoke(mailItem, messages[i]);
                } catch (InvocationTargetException ite) {
                    ite.printStackTrace();
                } catch (IllegalAccessException iae) {
                    iae.printStackTrace();
                } catch (NoSuchMethodException nsme) {
                    nsme.printStackTrace();
                }
            }
        }

        Log.d(TAG, "value1: " + mailItem.getValue1() + " value2: " + mailItem.getValue2());
        IFTTTCallback.reqSend(context, mIFTTTCallback, mailItem);
    }
}

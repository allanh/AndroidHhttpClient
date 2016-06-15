package com.fuhu.middleware.contract;

import android.content.Context;
import android.widget.Toast;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeItem;
import com.fuhu.middleware.componet.ErrorCodeList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ErrorCodeHandler {
    private static final String TAG = ErrorCodeHandler.class.getSimpleName();
    private static Map<String,ErrorCodeItem> lookupTable=new HashMap<String,ErrorCodeItem>();

    /**
     * set default error code list
     */
    static{
        for (ErrorCodeItem tmpItem : ErrorCodeList.INSTANCE.getItemList()) {
            lookupTable.put(tmpItem.getCode(), tmpItem);
        }
    }

    /**
     * Finding registered error
     * @param errorCode
     * @return
     */
    public static ErrorCodeItem lookup(final String errorCode){
        return lookupTable.get(errorCode);
    }

    public static boolean isSuccess(final String errorCode) {
        if (errorCode != null) {
            return errorCode.equals(ErrorCodeList.Success.getCode());
        } else {
            return false;
        }
    }

    /**
     * Toast error message
     * @param context
     * @param message
     */
    public static void toastMessage(Context context, String message) {
        String toastMessage = (message != null)?message:ErrorCodeList.UNKNOWN_EXCEPTION.toString();

        if (context != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Generate error item
     */
    public static AMailItem genErrorItem(ErrorCodeList errorCodeList) {
        return genErrorItem(errorCodeList, AMailItem.class);
    }

    /**
     * Generate error item with class type
     */
    public static AMailItem genErrorItem(ErrorCodeList errorCodeList, Class<? extends AMailItem> classOfT) {
        try {
            Object object = (classOfT != null) ? classOfT.newInstance()
                                            : AMailItem.class.newInstance();

            /** Invoking method with a string to set status */
            Method setStatusMethod = AMailItem.class.getDeclaredMethod("setStatus", String.class);
            setStatusMethod.setAccessible(true);
            setStatusMethod.invoke(object, errorCodeList.getCode());

            /** Invoking method with a string to set message */
            Method setMessageMethod = AMailItem.class.getDeclaredMethod("setMessage", String.class);
            setMessageMethod.setAccessible(true);
            setMessageMethod.invoke(object, errorCodeList.getContent());

            return (AMailItem) object;

        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }

        return null;
    }

//    /**
//     * To register a new error code
//     * @param errorCode the error code
//     * @param errorContent the error content
//     */
//    public static boolean registerErrorCode(String errorCode, String errorContent) {
//        if (errorCode != null && errorContent != null) {
//            if (lookup(errorCode) == null) {
//
//            } else {
//                throw new IllegalArgumentException("The error code is already used.");
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * To register the error code list
//     * @param errorCodeList the error code list
//     * @return
//     */
//    public static boolean registerErrorCodeList(IErrorCodeList errorCodeList) {
//        Map<String,ErrorCodeItem> tmpMap = new HashMap<String,ErrorCodeItem>();
//        if (errorCodeList != null) {
//            for(ErrorCodeItem tmp : errorCodeList.getItemList()){
//                if (lookup(tmp.getCode()) != null) {
//                    tmpMap.clear();
//                    throw new IllegalArgumentException("The error code is already used.");
//                } else {
//                    tmpMap.put(tmp.getCode(), new ErrorCodeItem(tmp.getCode(), tmp.getContent()));
//                }
//            }
//
//            if (tmpMap.keySet().size() > 0) {
//                lookupTable.putAll(tmpMap);
//                return true;
//            }
//        }
//
//        return false;
//    }


}

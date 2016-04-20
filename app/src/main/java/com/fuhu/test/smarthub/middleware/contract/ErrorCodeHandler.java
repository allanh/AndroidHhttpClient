package com.fuhu.test.smarthub.middleware.contract;

import android.content.Context;
import android.widget.Toast;

import com.fuhu.test.smarthub.middleware.componet.IMailItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ErrorCodeHandler{
    Default("-1","Default"),
    Success("0","success"),
    OK("1","OK"),

    UNKNOWN_EXCEPTION("-2","UNKNOWN EXCEPTION"),
    UNKNOWN_ERROR("-3","UNKNOWN ERROR"),
    NO_MAILITEM_DATA("-4","NO MAILITEM DATA"),

    // Volley
    VOLLEY_TIMEOUT("-9000", "VOLLEY TIMEOUT"),
    VOLLEY_AUTH_FAILURE("-9001", "VOLLEY AUTH FAILURE"),
    VOLLEY_SERVER_ERROR("-9002", "VOLLEY SERVER ERROR"),
    VOLLEY_NETWORK_ERROR("-9003", "VOLLEY NETWORK ERROR"),
    VOLLEY_PARSE_ERROR("-9004", "VOLLEY PARSE ERROR"),

    ;


    private String 	errorCode		 = null;
    private String 	errorContent	 = null;
    private ErrorCodeHandler(final String errorCode, final String errorContent){
        this.setErrorCode(errorCode);
        this.errorContent=errorContent;
    }
    private static Map<String,ErrorCodeHandler> lookupTable=new HashMap<String,ErrorCodeHandler>();
    static{
        for(ErrorCodeHandler tmp:ErrorCodeHandler.values()){
            lookupTable.put(tmp.getCode(), tmp);
        }
    }

    public static ErrorCodeHandler lookup(final String errorCode){
        return lookupTable.get(errorCode);
    }

    @Override
    public String toString(){
        return errorContent;
    }
    /**
     * to do get ErrorCode and use to check ErrorCode, and show dialog
     */
    public static boolean doAction(final Context mContext,final List<IMailItem> MailItemList){
        checkErrorCode(mContext, getCode(MailItemList));
        return true;
    }
    /**
     * to get ErrorCode by MailItemList
     */
    public static String getCode(List<IMailItem> MailItemList){
        //also can check all Status Code
        return getCode(MailItemList.get(0));
    }

    public static String getCode(IMailItem MailItem){
        String errorCode = ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode();
        //String errorCode = "";
        if(MailItem != null){
            if(MailItem instanceof com.fuhu.test.smarthub.middleware.componet.MailItem){
                if(((com.fuhu.test.smarthub.middleware.componet.MailItem)MailItem).getStatus() != null){
                    errorCode = ((com.fuhu.test.smarthub.middleware.componet.MailItem)MailItem).getStatus();
                }else{
//					errorCode = ErrorCodeHandler.MISSING_MEDIAITEM_ERROR_CODE.getCode();
                }
            }else{
                errorCode = ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode();
            }

        }else{
            errorCode = ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode();
        }
        return errorCode;
    }
    /**
     * to check ErrorCode and show dialog
     */
    public static boolean checkErrorCode(final Context mContext,final String errorCode){
        if(errorCode.equals(ErrorCodeHandler.Success.getCode())){
            return true;
        }else if(!errorCode.equals(ErrorCodeHandler.Success.getCode()) &&
//                !errorCode.equals(ErrorCodeHandler.MISSING_ERROR_CODE.getCode()) &&
                !errorCode.equals(ErrorCodeHandler.Default.getCode())){

//            Intent intent = new Intent();
//            intent.setClass(mContext, ErrorDialogActivity.class);
//            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//
//            Bundle mbundle = new Bundle();
//            mbundle.putString(ErrorDialogActivity.ERROR_DIALOG_TYPE, ErrorDialogActivity.ERROR_DIALOG_TYPE_ERROR_CODE);
//            mbundle.putInt(ErrorDialogActivity.BUNDLE_KEY_ERROR_CODE, Integer.valueOf(errorCode));
//            intent.putExtras(mbundle);
//            mContext.startActivity(intent);
            return false;
        }else{
            return false;
        }
    }

    public String getCode() {
        return errorCode;
    }

    private void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public static boolean isSuccess(final String errorCode) {
        if (errorCode != null) {
            return errorCode.equals(Success.errorCode);
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
        String toastMessage = (message != null)?message:ErrorCodeHandler.UNKNOWN_EXCEPTION.toString();

        if (context != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
        }
    }
}

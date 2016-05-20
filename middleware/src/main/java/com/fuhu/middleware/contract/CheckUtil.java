package com.fuhu.middleware.contract;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckUtil {
    /**
     * Check if network is available
     * @param context
     * @return
     */
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try{
            ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            if (haveConnectedWifi || haveConnectedMobile) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
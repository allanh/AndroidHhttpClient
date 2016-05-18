package com.fuhu.middleware.componet;

public interface IManager {
    public void stop();

    public void startService();

    public void stopService();

    public void registerReceiver();

    public void unregisterReceiver();
}

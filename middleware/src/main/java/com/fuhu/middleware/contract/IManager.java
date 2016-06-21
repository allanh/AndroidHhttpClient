package com.fuhu.middleware.contract;

public interface IManager {
    public void stop();

    public void startService();

    public void stopService();

    public void registerReceiver();

    public void unregisterReceiver();
}

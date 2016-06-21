package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.Priority;

import java.util.Map;

public interface IHttpCommand extends ICommand {
    public String getURL();

    public Priority getPriority();

    public int getMethod();

    public Map<String, String> getHeaders();

    /**
     * Supported request methods.
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
}
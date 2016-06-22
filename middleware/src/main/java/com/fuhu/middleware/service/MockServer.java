package com.fuhu.middleware.service;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.control.MD5Visitor;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IResponse;
import com.fuhu.middleware.contract.MD5Util;

import java.util.HashMap;
import java.util.Map;

public class MockServer {
    private static final String TAG = MockServer.class.getSimpleName();
    private static MockServer INSTANCE = new MockServer();
    private static Map<String, IResponse> lookupTable = new HashMap<String, IResponse>();
    private static IMD5Visitor md5Visitor = new MD5Visitor();

    public static MockServer getInstance() {
        return INSTANCE;
    }

    public Map<String, IResponse> getResponseMap() {
        return lookupTable;
    }

    /**
     * Removes all elements from this {@code Map}, leaving it empty.
     */
    public void clearResponseMap() {
        lookupTable.clear();
    }

    /**
     * Add the response to ResponseMap
     */
    public void addResponse(IResponse... responses) {
        if (responses != null && responses.length > 0) {
            for (IResponse response: responses) {
                // Checks if response id isn't null
                if (response != null && response.getURL() != null) {

                    String key = MD5Util.genMD5Key(response.getURL());
                    Log.d(TAG, "Url: " + response.getURL());
                    if (lookupTable.get(key) != null) {
                        lookupTable.remove(key);
                    }

                    Log.d(TAG, "add response key: " + key);
                    lookupTable.put(key, response);
                }
            }
        }
    }

    /**
     * Returns the response of the mapping with the specified url.
     * @param url the url of request
     * @return
     */
    public IResponse findResponse(String url) {
        if (url != null) {
            String key = MD5Util.genMD5Key(url);
            Log.d(TAG, "find url: " + url + " key: " + key);
            if (key != null) {
                return lookupTable.get(key);
            }
        }
        return null;
    }

    /**
     * Returns the response of the mapping with the specified command.
     * @param command the command of request
     * @return
     */
    public IResponse findResponse(ICommand command) {
        String mockKey = command.getMD5Key(md5Visitor);

        Log.d(TAG, "Mock key: " + mockKey);
        if (mockKey != null) {
            String key = MD5Util.genMD5Key(mockKey);
            Log.d(TAG, "find mock key: " + mockKey + " key: " + key);
            if (key != null) {
                return lookupTable.get(key);
            }
        }
        return null;
    }
}
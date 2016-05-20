package com.fuhu.middleware.service;

import com.fuhu.middleware.componet.IResponse;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.MD5Util;

import java.util.HashMap;
import java.util.Map;

public class MockServer {
    private static final String TAG = MockServer.class.getSimpleName();
    private static MockServer INSTANCE = new MockServer();
    private static Map<String, IResponse> lookupTable = new HashMap<String, IResponse>();

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
     * Returns the response of the mapping with the specified key.
     * @param id the key
     * @return
     */
    public IResponse findResponse(String id) {
        if (id != null) {
            return lookupTable.get(id);
        }

        return null;
    }
}
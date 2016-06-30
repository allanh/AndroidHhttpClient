package com.fuhu.middleware.service;

import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IResponse;
import com.fuhu.middleware.contract.MD5Util;
import com.fuhu.middleware.control.MD5Visitor;

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
        if (lookupTable != null) {
            lookupTable.clear();
        }
    }

    public static void release() {
        INSTANCE = null;
        if (lookupTable != null) {
            lookupTable.clear();
        }
        lookupTable = null;
        md5Visitor = null;
    }

    /**
     * Add the response to ResponseMap
     */
    public void addResponse(IResponse... responses) {
        if (responses == null || responses.length == 0) {
            return;
        }

        for (IResponse response: responses) {
            if (response != null) {
                // Generates MD5 key for this response
                String mockKey = response.genMD5Key(md5Visitor);

//                Log.d(TAG, "add response key: " + mockKey);
                if (mockKey != null) {
                    if (lookupTable.get(mockKey) != null) {
                        lookupTable.remove(mockKey);
                    }

                    lookupTable.put(mockKey, response);
                }
            }
        }
    }

    /**
     * Returns the response of the mapping with the specified key.
     * @param key the key of request
     * @return
     */
    public IResponse findResponse(String key) {
        if (key != null) {
            String md5key = MD5Util.genMD5Key(key);
//            Log.d(TAG, "find key: " + key + " MD5 key: " + md5key);
            if (md5key != null) {
                return lookupTable.get(md5key);
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

//        Log.d(TAG, "Find Mock key: " + mockKey);
        if (mockKey != null) {
//            printLookupTable();
            return lookupTable.get(mockKey);
        }
        return null;
    }
}
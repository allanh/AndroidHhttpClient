package com.fuhu.test.smarthub.middleware.contract;

import com.fuhu.test.smarthub.middleware.componet.AMailItem;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GSONUtil {
    private static final String TAG = GSONUtil.class.getSimpleName();
    private static Gson mGson = new Gson();

    /**
     * convert JSONObject to MailItem using Gson
     * @param jsonObject origin JSONObject
     * @param obj MailItem class
     * @param <T> the type of the MailItem object
     * @return
     */
    public static <T> T fromJSON(JSONObject jsonObject, Class<T> obj) {
        return mGson.fromJson(jsonObject.toString(), obj);
    }

    /**
     * convert MailItem to JSONObject using Gson
     * @param mailItem
     * @return
     */
    public static JSONObject toJSON(AMailItem mailItem) {
        if (mailItem != null) {
            try {
                return mailItem.toJSONObject(mGson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public static JSONObject toJSON(AMailItem mailItem, String... keys) {
        JsonObject newJSONObject = new JsonObject();
        if (keys == null)
            return toJSON(mailItem);

        try {
            if (mailItem != null) {
                JsonElement element = mailItem.toJsonTree(mGson);

                // check if element is JSONObject
                if (element != null && element.isJsonObject()) {
                    JsonObject jObject = element.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entries = jObject.entrySet();
                    HashMap<String, JsonElement> elementHashMap = new HashMap<String, JsonElement>();

                    // put key and value to HashMap
                    for (Map.Entry<String, JsonElement> entry : entries) {
                        Log.d(TAG, "key: " + entry.getKey() + " value: " + entry.getValue());
                        elementHashMap.put(entry.getKey(), entry.getValue());
                    }

                    // check if element map contains key and add to new JSONObject
                    for (String key : keys) {
                        if (elementHashMap.containsKey(key)) {
                            newJSONObject.add(key, elementHashMap.get(key));
                        }
                    }
                    return new JSONObject(newJSONObject.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}

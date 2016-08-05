package com.fuhu.middleware.contract;

import android.os.Bundle;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GSONUtil {
    private static final String TAG = GSONUtil.class.getSimpleName();
    private static Gson mGson;
    public enum VALUE_TYPE {JSONObject, String, Integer, Double};

    /**
     * Get a Gson object (Lazy initialization)
     * @return gson
     */
    public synchronized static Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * Convert JSONObject to MailItem using Gson
     * @param jsonObject origin JSONObject
     * @param obj MailItem class
     * @param <T> the type of the MailItem object
     * @return
     */
    public static <T> T fromJSON(JSONObject jsonObject, Class<T> obj) throws JSONException {
        return getGson().fromJson(jsonObject.toString(), obj);
    }

    /**
     * Convert JSON String to MailItem using Gson
     * @param jsonString origin JSON String
     * @param obj MailItem class
     * @param <T> the type of the MailItem object
     * @return
     */
    public static <T> T fromJSON(String jsonString, Class<T> obj) throws JSONException {
        return getGson().fromJson(jsonString, obj);
    }

    /**
     * Convert all key-value pairs of the MailItem to JSONObject
     * @param mailItem
     * @return
     */
    public static JSONObject toJSON(AMailItem mailItem) throws JSONException{
        if (mailItem != null) {
            return mailItem.toJSONObject(getGson());
        } else {
            Log.d(TAG, "mailItem is null");
        }
        return new JSONObject();
    }

    /**
     * Convert the specified key-value pairs of the MailItem to JSONObject
     * @param mailItem
     * @param keys
     * @return
     */
    public static JSONObject toJSON(AMailItem mailItem, String... keys) throws JSONException {
        JsonObject newJSONObject = new JsonObject();
        if (keys == null || keys.length == 0)
            return toJSON(mailItem);

        if (mailItem != null) {
            JsonElement element = mailItem.toJsonTree(getGson());

            /** check if element is JSONObject */
            if (element != null && element.isJsonObject()) {
                JsonObject jObject = element.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = jObject.entrySet();
                HashMap<String, JsonElement> elementHashMap = new HashMap<String, JsonElement>();

                /** put key and value to HashMap */
                for (Map.Entry<String, JsonElement> entry : entries) {
                    Log.d(TAG, "key: " + entry.getKey() + " value: " + entry.getValue());
                    elementHashMap.put(entry.getKey(), entry.getValue());
                }

                /** check if element map contains key and add to new JSONObject */
                for (String key : keys) {
                    if (elementHashMap.containsKey(key)) {
                        newJSONObject.add(key, elementHashMap.get(key));
                    }
                }
                return new JSONObject(newJSONObject.toString());
            }
        }
        return new JSONObject();
    }


    /**
     * Covert JSONObject to Bundle
     * JSONArray can be convert to ArrayList<Bundle>
     *
     * @param json JSONObject
     * @return Bundle
     */
    public static Bundle toBundle(final JSONObject json) {
        final Bundle bundle = new Bundle();
        if (json != null) {
            final Iterator<String> iterator = json.keys();

            if (iterator != null) {
                while (iterator.hasNext()) {
                    final String key = iterator.next();
                    Log.i(TAG, "key: " + key);
                    if (json.isNull(key)) {
                        bundle.putString(key, null);
                        continue;
                    }
                    final Object value = json.opt(key);
                    Log.v(TAG, "value: " + value);
                    if (value instanceof JSONObject) {
                        Log.d(TAG, "JSONObject");
                        bundle.putBundle(key, toBundle((JSONObject) value));
                    } else if (value instanceof JSONArray) {
                        Log.d(TAG, "[JSONArray] " + "key: " + key + " value: " + value);
                        bundle.putParcelableArrayList(key, toBundle((JSONArray) value));
                    } else if (value instanceof Boolean) {
                        Log.d(TAG, "Boolean");
                        bundle.putBoolean(key, (boolean) value);
                    } else if (value instanceof String) {
                        Log.d(TAG, "String");
                        bundle.putString(key, (String) value);
                    } else if (value instanceof Integer) {
                        Log.d(TAG, "Integer");
                        bundle.putInt(key, (int) value);
                    } else if (value instanceof Long) {
                        Log.d(TAG, "Long");
                        bundle.putLong(key, (long) value);
                    } else if (value instanceof Double) {
                        Log.d(TAG, "Double");
                        bundle.putDouble(key, (double) value);
                    }
                }
            }
        }
        Log.e(TAG, "convert: " + bundle.toString());
        return bundle;
    }

    /**
     * Convert JSONArray to ArrayList<Bundle>
     *
     * @param array JSONArray
     * @return ArrayList<Bundle>
     */
    public static ArrayList<Bundle> toBundle(final JSONArray array) {
        final ArrayList<Bundle> bundles = new ArrayList<>();
        try {
            if (array != null) {
                for (int i = 0, size = array.length(); i < size; i++) {
                    JSONObject item = array.optJSONObject(i);

                    Log.d(TAG, "item: " + item);
                    if (item != null) {
                        bundles.add(toBundle(item));
                    } else {
                        bundles.add(toBundle(new JSONObject(array.get(i).toString())));
//                        Object object = array.opt(i);
//                        if (object instanceof Integer) {
//                            bundles.add(array.optInt(i));
//                            Bundle bundle = new Bundle();
//                            bundle.putParcelableArrayList();
//                        }
                    }
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return bundles;
    }

    /**
     * Covert Bundle to JSONObject
     * @param bundle Bundle
     * @return JSONObject
     */
    public static JSONObject toJSON(Bundle bundle) throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        if (bundle != null || bundle.isEmpty()) {
            final Iterator<String> iterator = bundle.keySet().iterator();
            while (iterator.hasNext()) {
                final String key = iterator.next();
                jsonObject.put(key, wrap(bundle.get(key)));
            }
        } else {
            Log.d(TAG, "Bundle is null.");
        }
        return jsonObject;
    }

    /**
     * Covert Object to JSONObject or JSONArrary*
     * @param object
     * @return JSONArray
     */
    public static Object wrap(Object object) {
        if (object == null) {
            return JSONObject.NULL;
        }
        if (object instanceof JSONArray || object instanceof JSONObject) {
            return object;
        }
        if (object.equals(JSONObject.NULL)) {
            return object;
        }

        try {
            // Checks if object type is Bundle
            if (object instanceof Bundle) {
                return toJSON((Bundle) object);
            }

            // Checks if object type is Collection
            if (object instanceof Collection) {
                return new JSONArray((Collection) object);
            } else if (object.getClass().isArray()) {
                return toJSONArray(object);
            }

            // Checks if object type is Map
            if (object instanceof Map) {
                return new JSONObject((Map) object);
            }

            // Checks if object type is primitive type
            if (object instanceof Boolean ||
                    object instanceof Byte ||
                    object instanceof Character ||
                    object instanceof Double ||
                    object instanceof Float ||
                    object instanceof Integer ||
                    object instanceof Long ||
                    object instanceof Short ||
                    object instanceof String) {
                return object;
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    /**
     * Covert Object to JSONOArray
     * JSONArray can be convert to ArrayList<Bundle>
     *
     * @param array
     * @return JSONArray
     */
    public static JSONArray toJSONArray(Object array) throws JSONException {
        JSONArray result = new JSONArray();
        if (!array.getClass().isArray()) {
            throw new JSONException("Not a primitive array: " + array.getClass());
        }
        final int length = Array.getLength(array);
        for (int i = 0; i < length; ++i) {
            result.put(wrap(Array.get(array, i)));
        }
        return result;
    }

    public static void release() {
        mGson = null;
    }
}

package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChannelItem extends AMailItem {
    private static final String TAG= ChannelItem.class.getSimpleName();

    // Here now
    private int occupancy;
    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    private List<String> uuids;
    public List<String> getUuids() {
        if (uuids == null) {
            uuids = new ArrayList<String>();
        }
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    // where
    private Payload payload;
    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public static class Payload {
        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        private String score;
        private String time;
    }

    private List<RtpChannel> channels_list;
    public List<RtpChannel> getChannels() {
        if (channels_list == null) {
            channels_list = new LinkedList<RtpChannel>();
        }
        return channels_list;
    }

    public void setChannels(List<RtpChannel> channels_list) {
        this.channels_list = channels_list;
    }

/**
 {"channels":{"allan-stdby":{"uuids":[{"uuid":"allan"}],"occupancy":1},"fu-stdby":{"uuids":[{"uuid":"fu"}],"occupancy":1}},"total_channels":2,"total_occupancy":2}
 */
    public void setChannels(final String UUID, final JSONObject jsonObject) {
        if (jsonObject != null) {
            Log.d(TAG, "json object: " + jsonObject.toString());
            String stdbyChannel = UUID + Constants.STDBY_SUFFIX;
            Iterator<String> keys = jsonObject.keys();

            // set keys list
            List<String> keysList = new ArrayList<String>();
            while (keys.hasNext()) {
                keysList.add(keys.next());
            }

            // initial channels list
            channels_list = getChannels();
            channels_list.clear();

            try {
                // add channel item
                for (String key : keysList) {
                    Log.d(TAG, "channel: " + stdbyChannel + " key: " + key);

                    // ignore to parse uuids when key equals user's channel
                    if (key.equals(stdbyChannel) || !key.endsWith(Constants.STDBY_SUFFIX)) {
                        continue;
                    }

                    RtpChannel channel = new RtpChannel();
                    channel.setKey(key);

                    JSONObject keyJson = jsonObject.getJSONObject(key);
                    if (keyJson.has("occupancy")) {
                        channel.setOccupancy(keyJson.getInt("occupancy"));
                    }

                    if (keyJson.has("uuids")) {
                        List<RtpUuid> uuidList = new ArrayList<RtpUuid>();
                        JSONArray uuidsJsonArray = keyJson.getJSONArray("uuids");

                        if (uuidsJsonArray != null) {
                            for (int i = 0; i < uuidsJsonArray.length(); i++) {
                                JSONObject uuidObject = uuidsJsonArray.getJSONObject(i);

                                if (uuidObject.has("uuid")) {
                                    uuidList.add(new RtpUuid(uuidObject.getString("uuid")));
                                }
                            }
                        }
                        channel.setUuids(uuidList);
                    }
                    channels_list.add(channel);
                }

                for (RtpChannel ch : channels_list) {
                    Log.d(TAG, "ch " + ch.getKey() + " occupancy: " + ch.getOccupancy());
                    for (RtpUuid id: ch.getUuids()) {
                        Log.d(TAG, "uuid: " + id.getUuid());
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
    }

    public static class RtpChannel {
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<RtpUuid> getUuids() {
            if (uuids == null) {
                uuids = new ArrayList<RtpUuid>();
            }
            return uuids;
        }

        public String getUuidsString() {
            if (uuids != null && uuids.size() > 0) {
                return uuids.get(0).getUuid();
//                StringBuilder builder = new StringBuilder();
//                for (RtpUuid uuid: uuids) {
//                    builder.append(uuid.getUuid()).append(',');
//                }
//                builder.deleteCharAt(builder.length()-1);
//                return builder.toString();
            }

            return "";
        }

        public void setUuids(List<RtpUuid> uuids) {
            this.uuids = uuids;
        }

        public int getOccupancy() {
            return occupancy;
        }

        public void setOccupancy(int occupancy) {
            this.occupancy = occupancy;
        }

        private String key;
        private List<RtpUuid> uuids;
        private int occupancy;
    }

    private int total_channels;
    public int getTotal_channels() {
        return total_channels;
    }

    public void setTotal_channels(int total_channels) {
        this.total_channels = total_channels;
    }

    private int total_occupancy;
    public int getTotal_occupancy() {
        return total_occupancy;
    }

    public void setTotal_occupancy(int total_occupancy) {
        this.total_occupancy = total_occupancy;
    }

    public static class RtpUuid {
        public RtpUuid() {
        }

        public RtpUuid(String uuid) {
            this.uuid = uuid;
        }
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
        private String uuid;
    }
}

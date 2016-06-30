package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;

public class DeviceMessage extends AMailItem {
    private String deviceId;
    private Payload payload;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public static class Payload {
        private String score;
        private long time;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}

package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;

public class DeviceInfoItem extends AMailItem {
    private DeviceInfo device_info;

    public DeviceInfo getDeviceInfo() {
        return device_info;
    }

    public void setDeviceInfo(DeviceInfo device_info) {
        this.device_info = device_info;
    }
    
    public static class DeviceInfo {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String id;
        private String name;
    }
}

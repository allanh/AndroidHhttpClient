package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;

public class IFTTTItem extends AMailItem {
    /**
     * IFTTT
     */
    private String value1;

    private String value2;

    private String value3;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public void setValues(String [] values) {
        for (int i = 0; i < values.length; i++) {

        }
    }
}

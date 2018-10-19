package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2018/4/23.
 */
public enum  MsgType {
    BIND_MOBILE(1, "绑定手机号");
    private int key;
    private String value;

    MsgType(int key, String value) {
        this.key = key;
        this.value = value;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

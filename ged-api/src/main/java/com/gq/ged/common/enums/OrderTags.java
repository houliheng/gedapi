package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/1/19.
 */
public enum OrderTags {
    HOUSE(1,"有房"),CAR(2,"有车"),COMPANY(3,"有企业"),OTHER(4,"其他资产");
    private int key;
    private String value;

    OrderTags(int key, String value) {
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

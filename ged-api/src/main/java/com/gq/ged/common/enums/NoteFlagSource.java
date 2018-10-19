package com.gq.ged.common.enums;

/**
 * Created by wyq_tomorrow on 2017/4/7.
 */
public enum NoteFlagSource {
    RFT(1, "是"),RFF(0,"否");
    private int code;
    private String desc;

    NoteFlagSource(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

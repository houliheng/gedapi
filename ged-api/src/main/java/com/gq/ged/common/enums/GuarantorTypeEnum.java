package com.gq.ged.common.enums;

/**
 * 担保人类型枚举.
 *
 * @author SeppSong
 */
public enum  GuarantorTypeEnum {
    //自有担保人
    SELF_GUARANTOR(1, "自有担保人"),

    //自有担保公司
    SELF_GUARANTEE_COMPANY(2, "自有担保公司"),

    //合作公司
    COOPERATION_COMPANY(3, "合作公司");

    private int code;

    private String value;

    GuarantorTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.gq.ged.common.enums;

/**
 * http响应Code代码.
 *
 * @author yxh by 2018/6/5
 */
public enum ResCodeEnum {

    //0000成功
    SUCCESS(0000, "成功"),

    //0001失败
    FAIL(0001, "失败");

    private int code;

    private String value;

    public static ResCodeEnum valueOf(int code) {
        switch (code) {
            case 0000 :
                return SUCCESS;
            case 0001 :
                return FAIL;
            default :
        }
        throw new IllegalArgumentException("未知的代码 : " + code);
    }

    ResCodeEnum(int code, String value) {
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

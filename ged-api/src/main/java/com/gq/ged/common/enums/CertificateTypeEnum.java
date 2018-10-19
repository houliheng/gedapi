package com.gq.ged.common.enums;

/**
 * 证件类型枚举.
 *
 * @author SeppSong
 */
public enum CertificateTypeEnum {

    //身份证
    IDENTITY_CARD(1, "身份证");

    private int code;

    private String value;

    public static String valueOf(int code) {
        switch (code) {
            case 1 :
                return IDENTITY_CARD.value;
            default :
        }
        throw new IllegalArgumentException("未知的证件类型 : " + code);
    }

    CertificateTypeEnum(int code, String value) {
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

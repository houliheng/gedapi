package com.gq.ged.common.enums;

import java.util.Comparator;
import org.apache.ibatis.annotations.Param;

/**
 * 用户类型枚举.
 *
 * @author SeppSong
 */
public enum UserTypeEnum {

    //个人
    PERSONAL(0, "个人"),

    //企业
    COMPANY(1, "企业");

    private int code;

    private String value;

    public static UserTypeEnum valueOf(int code) {
        switch (code) {
            case 0 :
                return PERSONAL;
            case 1 :
                return COMPANY;
            default :
        }
        throw new IllegalArgumentException("未知的用户类型 : " + code);
    }

    UserTypeEnum(int code, String value) {
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

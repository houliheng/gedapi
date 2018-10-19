package com.resoft.accounting.checkAccount.entity;

/**
 * 查账内容.
 *
 * @author SeppSong
 */
public enum CheckAccountContentEnum {
    //提交
    SUBMIT("提交"),
    //入账
    ACCOUNT_ENTRY("入账"),
    //退回
    REFUSE("退回");

    private String value;

    CheckAccountContentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.resoft.accounting.checkAccount.entity;

/**
 * 查账节点.
 *
 * @author SeppSong
 */
public enum CheckAccountNodeEnum {

    //查账申请节点
    CHECK_ACCOUNT_APPLY("查账申请"),
    //待查账节点
    WAIT_FOR_CHECK_ACCOUNT("待查账");

    private String value;

    CheckAccountNodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.resoft.accounting.bankAccountStatement.entity;

/**
 * 银行流水入账状态.
 *
 * @author SeppSong
 */
public enum  StatementStatusEnum {
    //已入账
    ENTER(1),
    //未入账
    UN(0);

    private Integer value;

    StatementStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

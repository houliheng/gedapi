package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/5/25.
 */
public enum PushDataMigrationStatus {
     HKING(600, "还款中", 160), YYQ(800, "逾期还款", 170);
    private int loanSystemcode;
    private String feStatusDesc;
    private int code;

    PushDataMigrationStatus(int loanSystemcode, String feStatusDesc, int code) {
        this.loanSystemcode = loanSystemcode;
        this.feStatusDesc = feStatusDesc;
        this.code = code;
    }

    public static PushDataMigrationStatus resove(int loanSystemcode) {
        switch (loanSystemcode) {
            case 600:
                return HKING;
            case 800:
                return YYQ;
            default:
                return null;
        }
    }

    public int getLoanSystemcode() {
        return loanSystemcode;
    }

    public void setLoanSystemcode(int loanSystemcode) {
        this.loanSystemcode = loanSystemcode;
    }

    public String getFeStatusDesc() {
        return feStatusDesc;
    }

    public void setFeStatusDesc(String feStatusDesc) {
        this.feStatusDesc = feStatusDesc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

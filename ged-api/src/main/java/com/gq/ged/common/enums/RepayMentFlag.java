package com.gq.ged.common.enums;

/**
 * Created by wrh on 2018/1/19.
 */
public enum RepayMentFlag {
    ZERO(0,"未还","0200"),FIRST(1,"逾期结清已还","0100"),SECOND(2,"逾期","0300"),FOUR(1,"正常结清已还","0400"),FIVE(1,"提前还款已还","0500"),SIX(1,"已还","0600");
    private int code;
    private String value;
    private String loanStatus;

    RepayMentFlag(int code, String value,String loanStatus) {
        this.code = code;
        this.value = value;
        this.loanStatus=loanStatus;
    }

    public static RepayMentFlag resove(String loanStatus) {
        switch (loanStatus) {
            case "0200":
                return ZERO;
            case "0100":
                return FIRST;
            case "0300":
                return SECOND;
            case "0400":
                return FOUR;
            case "0500":
                return FIVE;
            case "0600":
                return SIX;
            default:
                return null;
        }
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

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}

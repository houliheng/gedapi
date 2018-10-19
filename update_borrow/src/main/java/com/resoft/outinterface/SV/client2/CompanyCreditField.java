package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyCreditField {
    //企业征信信息
    //
    @JsonProperty(value = "C_unitTelephoneNumber")
    private String cunitTelephoneNumber  ;//征信中单位电话
    @JsonProperty(value = "C_creditHistoryMonth")
    private String ccreditHistoryMonth  ;//征信信⽤用历史⽉月数
    @JsonProperty(value = "C_totalExternalGuarantee")
    private String ctotalExternalGuarantee  ; //对外担保总额
    @JsonProperty(value = "C_CreditPrintTime")
    private String cCreditPrintTime  ; //打印时间
    @JsonProperty(value = "C_totalAmountOverdueLoans")
    private String ctotalAmountOverdueLoans  ;//当⽉月到期贷款总余额
    @JsonProperty(value = "C_totalMonthlyLoanAmount")
    private String ctotalMonthlyLoanAmount  ; //当⽉月逾期贷款总⾦金金额
    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getCunitTelephoneNumber() {
        return cunitTelephoneNumber;
    }

    public void setCunitTelephoneNumber(String cunitTelephoneNumber) {
        this.cunitTelephoneNumber = cunitTelephoneNumber;
    }

    public String getCcreditHistoryMonth() {
        return ccreditHistoryMonth;
    }

    public void setCcreditHistoryMonth(String ccreditHistoryMonth) {
        this.ccreditHistoryMonth = ccreditHistoryMonth;
    }

    public String getCtotalExternalGuarantee() {
        return ctotalExternalGuarantee;
    }

    public void setCtotalExternalGuarantee(String ctotalExternalGuarantee) {
        this.ctotalExternalGuarantee = ctotalExternalGuarantee;
    }

    public String getcCreditPrintTime() {
        return cCreditPrintTime;
    }

    public void setcCreditPrintTime(String cCreditPrintTime) {
        this.cCreditPrintTime = cCreditPrintTime;
    }

    public String getCtotalAmountOverdueLoans() {
        return ctotalAmountOverdueLoans;
    }

    public void setCtotalAmountOverdueLoans(String ctotalAmountOverdueLoans) {
        this.ctotalAmountOverdueLoans = ctotalAmountOverdueLoans;
    }

    public String getCtotalMonthlyLoanAmount() {
        return ctotalMonthlyLoanAmount;
    }

    public void setCtotalMonthlyLoanAmount(String ctotalMonthlyLoanAmount) {
        this.ctotalMonthlyLoanAmount = ctotalMonthlyLoanAmount;
    }
}

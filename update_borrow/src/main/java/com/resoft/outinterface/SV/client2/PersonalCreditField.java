package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalCreditField {// 个人征信信息
    @JsonProperty(value = "p_yearInquiry")
    private String pyearInquiry;//本⼈人年年内查询次数
    @JsonProperty(value = "p_quarterInquiry")
    private String pquarterInquiry  ; //本⼈人季内查询次数
    @JsonProperty(value = "p_monthInquiry")
    private String pmonthInquiry  ; //本⼈人⽉月内查询次数
    @JsonProperty(value = "p_queryTimeReportTime")
    private String pqueryTimeReportTime  ;//查询时间与报告时间间隔
    @JsonProperty(value = "p_creditHistoryMonth")
    private String pcreditHistoryMonth  ; //征信信⽤用历史
    @JsonProperty(value = "p_mobilePhoneNumber")
    private String pmobilePhoneNumber  ;//征信中⼿手机号码
    @JsonProperty(value = "p_unitTelephoneNumber")
    private String punitTelephoneNumber  ;  //征信中单位电话
    @JsonProperty(value = "p_CreditPrintTime")
    private String pCreditPrintTime  ; //打印时间
    @JsonProperty(value = "p_totalQuantity")
    private String ptotalQuantity  ;//信⽤用卡总数量量
    @JsonProperty(value = "p_overdueCardNumber")
    private String poverdueCardNumber  ; //逾期卡数
    @JsonProperty(value = "p_noUseCardNumber")
    private String pnoUseCardNumber  ; //未使⽤用卡数量量
    @JsonProperty(value = "p_abnormalCardNumber")
    private String pabnormalCardNumber  ;//异常卡数量量
    @JsonProperty(value = "P_totalCreditAmount")
    private String ptotalCreditAmount  ;//总授信额度
    @JsonProperty(value = "P_totalUseAmount")
    private String ptotalUseAmount  ;//总使⽤用额度
    @JsonProperty(value = "P_overallBalance")
    private String poverallBalance  ; //信⽤用卡总余额
    @JsonProperty(value = "P_overdueAmount")
    private String poverdueAmount  ;//逾期总额
    @JsonProperty(value = "P_useCreditCard")
    private String puseCreditCard  ;//信⽤用卡使⽤用占⽐比
    @JsonProperty(value = "P_highestCreditAmount")
    private String phighestCreditAmount  ;//最⾼高授信额度
    @JsonProperty(value = "P_highestUseAmount")
    private String phighestUseAmount  ;//最⾼高使⽤用额度
    @JsonProperty(value = "P_continuousOverdue")
    private String pcontinuousOverdue  ;//连续逾期数
    @JsonProperty(value = "P_cumulativeOverdue")
    private String pcumulativeOverdue  ;//累计逾期数
    @JsonProperty(value = "P_creditCardStatus")
    private String pcreditCardStatus  ;//信⽤用卡状态
    @JsonProperty(value = "P_monthExpireTotalAmount")
    private String pmonthExpireTotalAmount  ;//当⽉月到期贷款总余额
    @JsonProperty(value = "P_monthOverdueTotalAmount")
    private String pmonthOverdueTotalAmount  ;//当⽉月逾期贷款总⾦金金额
    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getPyearInquiry() {
        return pyearInquiry;
    }

    public void setPyearInquiry(String pyearInquiry) {
        this.pyearInquiry = pyearInquiry;
    }

    public String getPquarterInquiry() {
        return pquarterInquiry;
    }

    public void setPquarterInquiry(String pquarterInquiry) {
        this.pquarterInquiry = pquarterInquiry;
    }

    public String getPmonthInquiry() {
        return pmonthInquiry;
    }

    public void setPmonthInquiry(String pmonthInquiry) {
        this.pmonthInquiry = pmonthInquiry;
    }

    public String getPqueryTimeReportTime() {
        return pqueryTimeReportTime;
    }

    public void setPqueryTimeReportTime(String pqueryTimeReportTime) {
        this.pqueryTimeReportTime = pqueryTimeReportTime;
    }

    public String getPcreditHistoryMonth() {
        return pcreditHistoryMonth;
    }

    public void setPcreditHistoryMonth(String pcreditHistoryMonth) {
        this.pcreditHistoryMonth = pcreditHistoryMonth;
    }

    public String getPmobilePhoneNumber() {
        return pmobilePhoneNumber;
    }

    public void setPmobilePhoneNumber(String pmobilePhoneNumber) {
        this.pmobilePhoneNumber = pmobilePhoneNumber;
    }

    public String getPunitTelephoneNumber() {
        return punitTelephoneNumber;
    }

    public void setPunitTelephoneNumber(String punitTelephoneNumber) {
        this.punitTelephoneNumber = punitTelephoneNumber;
    }

    public String getpCreditPrintTime() {
        return pCreditPrintTime;
    }

    public void setpCreditPrintTime(String pCreditPrintTime) {
        this.pCreditPrintTime = pCreditPrintTime;
    }

    public String getPtotalQuantity() {
        return ptotalQuantity;
    }

    public void setPtotalQuantity(String ptotalQuantity) {
        this.ptotalQuantity = ptotalQuantity;
    }

    public String getPoverdueCardNumber() {
        return poverdueCardNumber;
    }

    public void setPoverdueCardNumber(String poverdueCardNumber) {
        this.poverdueCardNumber = poverdueCardNumber;
    }

    public String getPnoUseCardNumber() {
        return pnoUseCardNumber;
    }

    public void setPnoUseCardNumber(String pnoUseCardNumber) {
        this.pnoUseCardNumber = pnoUseCardNumber;
    }

    public String getPabnormalCardNumber() {
        return pabnormalCardNumber;
    }

    public void setPabnormalCardNumber(String pabnormalCardNumber) {
        this.pabnormalCardNumber = pabnormalCardNumber;
    }

    public String getPtotalCreditAmount() {
        return ptotalCreditAmount;
    }

    public void setPtotalCreditAmount(String ptotalCreditAmount) {
        this.ptotalCreditAmount = ptotalCreditAmount;
    }

    public String getPtotalUseAmount() {
        return ptotalUseAmount;
    }

    public void setPtotalUseAmount(String ptotalUseAmount) {
        this.ptotalUseAmount = ptotalUseAmount;
    }

    public String getPoverallBalance() {
        return poverallBalance;
    }

    public void setPoverallBalance(String poverallBalance) {
        this.poverallBalance = poverallBalance;
    }

    public String getPoverdueAmount() {
        return poverdueAmount;
    }

    public void setPoverdueAmount(String poverdueAmount) {
        this.poverdueAmount = poverdueAmount;
    }

    public String getPuseCreditCard() {
        return puseCreditCard;
    }

    public void setPuseCreditCard(String puseCreditCard) {
        this.puseCreditCard = puseCreditCard;
    }

    public String getPhighestCreditAmount() {
        return phighestCreditAmount;
    }

    public void setPhighestCreditAmount(String phighestCreditAmount) {
        this.phighestCreditAmount = phighestCreditAmount;
    }

    public String getPhighestUseAmount() {
        return phighestUseAmount;
    }

    public void setPhighestUseAmount(String phighestUseAmount) {
        this.phighestUseAmount = phighestUseAmount;
    }

    public String getPcontinuousOverdue() {
        return pcontinuousOverdue;
    }

    public void setPcontinuousOverdue(String pcontinuousOverdue) {
        this.pcontinuousOverdue = pcontinuousOverdue;
    }

    public String getPcumulativeOverdue() {
        return pcumulativeOverdue;
    }

    public void setPcumulativeOverdue(String pcumulativeOverdue) {
        this.pcumulativeOverdue = pcumulativeOverdue;
    }

    public String getPcreditCardStatus() {
        return pcreditCardStatus;
    }

    public void setPcreditCardStatus(String pcreditCardStatus) {
        this.pcreditCardStatus = pcreditCardStatus;
    }

    public String getPmonthExpireTotalAmount() {
        return pmonthExpireTotalAmount;
    }

    public void setPmonthExpireTotalAmount(String pmonthExpireTotalAmount) {
        this.pmonthExpireTotalAmount = pmonthExpireTotalAmount;
    }

    public String getPmonthOverdueTotalAmount() {
        return pmonthOverdueTotalAmount;
    }

    public void setPmonthOverdueTotalAmount(String pmonthOverdueTotalAmount) {
        this.pmonthOverdueTotalAmount = pmonthOverdueTotalAmount;
    }
}

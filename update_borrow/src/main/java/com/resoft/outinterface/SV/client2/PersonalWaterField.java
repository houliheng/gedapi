package com.resoft.outinterface.SV.client2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalWaterField {	// 个⼈人流⽔水信息
    @JsonProperty(value = "p_Month")
    private String pMonth;//月份
    @JsonProperty(value = "p_interestSettlement")
    private String pinterestSettlement ;//结息
    @JsonProperty(value = "p_Balance")
    private String pBalance ;//余额
    @JsonProperty(value = "P_cashInflow")
    private String  pcashInflow;//现金流入量
    @JsonProperty(value = "P_cashOutflow")
    private String  pcashOutflow;//现金流出量
    @JsonProperty(value = "P_printTime")
    private String pprintTime ;//打印⽇日期
    @JsonProperty(value = "P_bankName")
    private String pbankName;///银⾏行行名称
    @JsonProperty(value = "P_waterStartDate")
    private String pwaterStartDate ;//开始时间
    @JsonProperty(value = "P_waterEndDate")
    private String pwaterEndDate;//结束时间

    @JsonProperty(value = "work_id")
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getpMonth() {
        return pMonth;
    }

    public void setpMonth(String pMonth) {
        this.pMonth = pMonth;
    }

    public String getPinterestSettlement() {
        return pinterestSettlement;
    }

    public void setPinterestSettlement(String pinterestSettlement) {
        this.pinterestSettlement = pinterestSettlement;
    }

    public String getpBalance() {
        return pBalance;
    }

    public void setpBalance(String pBalance) {
        this.pBalance = pBalance;
    }

    public String getPcashInflow() {
        return pcashInflow;
    }

    public void setPcashInflow(String pcashInflow) {
        this.pcashInflow = pcashInflow;
    }

    public String getPcashOutflow() {
        return pcashOutflow;
    }

    public void setPcashOutflow(String pcashOutflow) {
        this.pcashOutflow = pcashOutflow;
    }

    public String getPprintTime() {
        return pprintTime;
    }

    public void setPprintTime(String pprintTime) {
        this.pprintTime = pprintTime;
    }

    public String getPbankName() {
        return pbankName;
    }

    public void setPbankName(String pbankName) {
        this.pbankName = pbankName;
    }

    public String getPwaterStartDate() {
        return pwaterStartDate;
    }

    public void setPwaterStartDate(String pwaterStartDate) {
        this.pwaterStartDate = pwaterStartDate;
    }

    public String getPwaterEndDate() {
        return pwaterEndDate;
    }

    public void setPwaterEndDate(String pwaterEndDate) {
        this.pwaterEndDate = pwaterEndDate;
    }
}

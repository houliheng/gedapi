
package com.resoft.accounting.discountStream.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
* @author guoshaohua
* @version 2018年5月29日 下午5:39:35
* 
*/
public class PeriodDiscountDetail extends DataEntity<PeriodDiscountDetail> {
    private static final long serialVersionUID = 1L;
    private String discountStatus;//贴息状态
    private String periodNum;//期数
    private String deductDate;//应还日期
    private String totalMoney;//当期应还总金额
    private String capitalInte;//当期应还本息
    private String stayMoney;//客户待还金额
    private String factMoney;//客户实还金额
    private String discountFee;//应贴息金额
    private String factDiscountFee;//已贴息金额
    private Date discountDate;//贴息日期
    private String periodStatus;//期供状态
    private String contractNo;//合同号

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getDeductDate() {
        return deductDate;
    }

    public void setDeductDate(String deductDate) {
        this.deductDate = deductDate;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCapitalInte() {
        return capitalInte;
    }

    public void setCapitalInte(String capitalInte) {
        this.capitalInte = capitalInte;
    }

    public String getStayMoney() {
        return stayMoney;
    }

    public void setStayMoney(String stayMoney) {
        this.stayMoney = stayMoney;
    }

    public String getFactMoney() {
        return factMoney;
    }

    public void setFactMoney(String factMoney) {
        this.factMoney = factMoney;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getFactDiscountFee() {
        return factDiscountFee;
    }

    public void setFactDiscountFee(String factDiscountFee) {
        this.factDiscountFee = factDiscountFee;
    }

    public Date getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }

    public String getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(String periodStatus) {
        this.periodStatus = periodStatus;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wrh on 2018/1/23.
 */

@ApiModel
public class RepayPlanTotleResForm implements Serializable {
    @ApiModelProperty(value = "第几期")
   private int orderTerm;
    @ApiModelProperty(value = "还款时间")
    private String repaymentTime;
    @ApiModelProperty(value = "还款金额")
    private BigDecimal repaymentAmount;
    @ApiModelProperty(value = "总共金额")
    private BigDecimal totleAmount;
    @ApiModelProperty(value = "借钱时间")
    private String loanDate;
    @ApiModelProperty(value = "已还集合")
    private List<RepayMentPlanResForm> repaidList;
    @ApiModelProperty(value = "未还集合")
    private List<RepayMentPlanResForm> notYetList;
    @ApiModelProperty(value = "共几期")
    private  int  count;
    @ApiModelProperty(value = "逾期天数")
    private int repayDay;
    @ApiModelProperty(value = "借钱")
    private BigDecimal loanAmount;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "借款期限")
    private int loanTerm;
    @ApiModelProperty(value = "首页总金额")
    private String portalAmount;
    @ApiModelProperty(value = "开户状态")
    private  int accStatus;
    @ApiModelProperty(value = "放款成功账户余额")
    private BigDecimal accountBalance;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "逾期罚息")
    private BigDecimal overDueDefaultInterest;
    @ApiModelProperty(value = "是否展示本期还款金额标识")
    private Integer currentFlag;
    @ApiModelProperty(value = "还款标识")
    private Integer repaymentFlag;
    @ApiModelProperty(value = "判断提现按钮是否置灰")
    private Integer withDrawFlag;
    @ApiModelProperty(value = "已还总共期限")
    private int readyCountTerm;
    @ApiModelProperty(value = "已还总共金额")
    private BigDecimal  readyCountAmount;
    @ApiModelProperty(value = "未还总共期限")
    private int notCountTerm;
    @ApiModelProperty(value = "未还总共金额")
    private BigDecimal notCountAmount;
    @ApiModelProperty(value = "是否逾期的标识")
    private boolean  overdueFlag;
    @ApiModelProperty(value = "电子合同标识")
    private Integer signContractFlag;

    public int getOrderTerm() {
        return orderTerm;
    }

    public void setOrderTerm(int orderTerm) {
        this.orderTerm = orderTerm;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public BigDecimal getTotleAmount() {
        return totleAmount;
    }

    public void setTotleAmount(BigDecimal totleAmount) {
        this.totleAmount = totleAmount;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public List<RepayMentPlanResForm> getRepaidList() {
        return repaidList;
    }

    public void setRepaidList(List<RepayMentPlanResForm> repaidList) {
        this.repaidList = repaidList;
    }

    public List<RepayMentPlanResForm> getNotYetList() {
        return notYetList;
    }

    public void setNotYetList(List<RepayMentPlanResForm> notYetList) {
        this.notYetList = notYetList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(int repayDay) {
        this.repayDay = repayDay;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getPortalAmount() {
        return portalAmount;
    }

    public void setPortalAmount(String portalAmount) {
        this.portalAmount = portalAmount;
    }

    public int getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(int accStatus) {
        this.accStatus = accStatus;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getOverDueDefaultInterest() {
        return overDueDefaultInterest;
    }

    public void setOverDueDefaultInterest(BigDecimal overDueDefaultInterest) {
        this.overDueDefaultInterest = overDueDefaultInterest;
    }

    public Integer getCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(Integer currentFlag) {
        this.currentFlag = currentFlag;
    }

    public Integer getRepaymentFlag() {
        return repaymentFlag;
    }

    public void setRepaymentFlag(Integer repaymentFlag) {
        this.repaymentFlag = repaymentFlag;
    }

    public Integer getWithDrawFlag() {
        return withDrawFlag;
    }

    public void setWithDrawFlag(Integer withDrawFlag) {
        this.withDrawFlag = withDrawFlag;
    }

    public int getReadyCountTerm() {
        return readyCountTerm;
    }

    public void setReadyCountTerm(int readyCountTerm) {
        this.readyCountTerm = readyCountTerm;
    }

    public BigDecimal getReadyCountAmount() {
        return readyCountAmount;
    }

    public void setReadyCountAmount(BigDecimal readyCountAmount) {
        this.readyCountAmount = readyCountAmount;
    }

    public int getNotCountTerm() {
        return notCountTerm;
    }

    public void setNotCountTerm(int notCountTerm) {
        this.notCountTerm = notCountTerm;
    }

    public BigDecimal getNotCountAmount() {
        return notCountAmount;
    }

    public void setNotCountAmount(BigDecimal notCountAmount) {
        this.notCountAmount = notCountAmount;
    }

    public boolean isOverdueFlag() {
        return overdueFlag;
    }

    public void setOverdueFlag(boolean overdueFlag) {
        this.overdueFlag = overdueFlag;
    }

    public Integer getSignContractFlag() {
        return signContractFlag;
    }

    public void setSignContractFlag(Integer signContractFlag) {
        this.signContractFlag = signContractFlag;
    }
}

package com.resoft.accounting.bankAccountStatement.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class CheckAccountForm    extends DataEntity<CheckAccountForm> {
    private static final long serialVersionUID = -9181496264160210542L;
    private String accountName;//收款户名
    private String accountNumber;//收款银行帐号
    private String receiveBankName;//收款银行名称


    //银行流水
    private Date createTradeStartTime;//交易日期开始时间
    private Date createTradeEndTime;//交易日期结束时间
    private Date tradeDate ; //'交易时间',
    private String   userName ; //'对方户名',
    private String   branchBankName ;// '分支银行名称',
    private  String  receiveAccountName ;// '收款方账号名称',
    private  String receiveAccountNumber ;// ; '收款方账号',
    private  Integer  status ;// '状态',


    public Date getCreateTradeStartTime() {
        return createTradeStartTime;
    }

    public void setCreateTradeStartTime(Date createTradeStartTime) {
        this.createTradeStartTime = createTradeStartTime;
    }

    public Date getCreateTradeEndTime() {
        return createTradeEndTime;
    }

    public void setCreateTradeEndTime(Date createTradeEndTime) {
        this.createTradeEndTime = createTradeEndTime;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public void setReceiveAccountName(String receiveAccountName) {
        this.receiveAccountName = receiveAccountName;
    }

    public String getReceiveAccountNumber() {
        return receiveAccountNumber;
    }

    public void setReceiveAccountNumber(String receiveAccountNumber) {
        this.receiveAccountNumber = receiveAccountNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReceiveBankName() {
        return receiveBankName;
    }

    public void setReceiveBankName(String receiveBankName) {
        this.receiveBankName = receiveBankName;
    }
}

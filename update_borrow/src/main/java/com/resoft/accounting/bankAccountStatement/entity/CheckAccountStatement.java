package com.resoft.accounting.bankAccountStatement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yxh
 * @Description 查账银行流水表
 * @Date 2018-7-12
 */
public class CheckAccountStatement  implements Serializable {
    private static final long serialVersionUID = -8402193380529836694L;
    private Long  id ;// '主键',
    private Date tradeDate ; //'交易时间',
    private BigDecimal tradeAmount; //'交易金额',
    private BigDecimal  enterAccountAmount ; //'入账金额',
    private BigDecimal  unAccountAmount ; //'未入账金额',
    private String   userName ; //'对方户名',
    private  String  accountNumber ; //'银行帐号',
    private String   bankCode ;// '银行',
    private String   branchBankName ;// '分支银行名称',
    private  String  receiveAccountName ;// '收款方账号名称',
    private  String receiveAccountNumber ;// ; '收款方账号',
    private String  remark ;// '备注',
    private  Integer  status ;// '状态',
    private  Date  createTime ;//'创建时间',
    private  Integer createId ;// '创建人id',

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getEnterAccountAmount() {
        return enterAccountAmount;
    }

    public void setEnterAccountAmount(BigDecimal enterAccountAmount) {
        this.enterAccountAmount = enterAccountAmount;
    }

    public BigDecimal getUnAccountAmount() {
        return unAccountAmount;
    }

    public void setUnAccountAmount(BigDecimal unAccountAmount) {
        this.unAccountAmount = unAccountAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
}

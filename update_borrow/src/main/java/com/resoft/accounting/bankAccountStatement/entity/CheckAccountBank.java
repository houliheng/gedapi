package com.resoft.accounting.bankAccountStatement.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * @Author yxh
 * @Description 查账银行表
 * @Date 2018-7-12
 */
public class CheckAccountBank implements Serializable {
    private static final long serialVersionUID = 4940969133683861263L;
    private Long id;//主键
    private String accountName;//收款户名
    private String accountNumber;//收款银行帐号
    private String receiveBankCode;//收款银行code
    private String receiveBankName;//收款银行名称
    private String bankDepositBranchName;//开户行分支名称
    private String remark;//备注
    private Integer status;//0禁用1启用
    private Date createTime;

    @Override
    public String toString() {
        return "CheckAccountBank{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", receiveBankCode='" + receiveBankCode + '\'' +
                ", receiveBankName='" + receiveBankName + '\'' +
                ", bankDepositBranchName='" + bankDepositBranchName + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createId=" + createId +
                '}';
    }

    //创建时间
    private Long createId;//创建人id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReceiveBankCode() {
        return receiveBankCode;
    }

    public void setReceiveBankCode(String receiveBankCode) {
        this.receiveBankCode = receiveBankCode;
    }

    public String getReceiveBankName() {
        return receiveBankName;
    }

    public void setReceiveBankName(String receiveBankName) {
        this.receiveBankName = receiveBankName;
    }

    public String getBankDepositBranchName() {
        return bankDepositBranchName;
    }

    public void setBankDepositBranchName(String bankDepositBranchName) {
        this.bankDepositBranchName = bankDepositBranchName;
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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }
}

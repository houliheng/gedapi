package com.resoft.accounting.checkAccount.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * 查账申请实体.
 *
 * @author SeppSong
 */
public class CheckAccountApply implements Serializable {

    private static final long serialVersionUID = 8143208658065362171L;

    private Long id;
    /**
     * 流程实例Id
     */
    private String proInsId;

    private String taskId;
    /**
     * 合同号
     */
    private String contractNumber;
    /**
     * 借款人名称
     */
    private String loanName;
    /**
     * 交易时间
     */
    private Date tradeDate;
    /**
     * 查账金额
     */
    private BigDecimal checkAmount;
    /**
     * 出账户名
     */
    private String outAccountName;
    /**
     * 出账银行编码
     */
    private String outAccountBankCode;
    /**
     * 出账银行名称
     */
    private String outAccountBankName;
    /**
     * 出账银行卡号
     */
    private String outAccountNumber;
    /**
     * 申请状态
     */
    private Integer status;

    private User createBy;

    private Date createDate;

    public CheckAccountApply() {
    }

    public CheckAccountApply(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public CheckAccountApply(String contractNumber, Integer status) {
        this.contractNumber = contractNumber;
        this.status = status;
    }

    public CheckAccountApply(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProInsId() {
        return proInsId;
    }

    public void setProInsId(String proInsId) {
        this.proInsId = proInsId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    public String getOutAccountName() {
        return outAccountName;
    }

    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName;
    }

    public String getOutAccountBankCode() {
        return outAccountBankCode;
    }

    public void setOutAccountBankCode(String outAccountBankCode) {
        this.outAccountBankCode = outAccountBankCode;
    }

    public String getOutAccountBankName() {
        return outAccountBankName;
    }

    public void setOutAccountBankName(String outAccountBankName) {
        this.outAccountBankName = outAccountBankName;
    }

    public String getOutAccountNumber() {
        return outAccountNumber;
    }

    public void setOutAccountNumber(String outAccountNumber) {
        this.outAccountNumber = outAccountNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void preInsert() {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())) {
            this.createBy = user;
        }
        this.createDate = new Date();
    }
}

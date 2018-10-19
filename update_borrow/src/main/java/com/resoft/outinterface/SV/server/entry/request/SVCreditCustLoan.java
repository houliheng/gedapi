package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * SV接口征信个人贷款明细Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditCustLoan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private String creditCustId;// 对应的个人征信编号
	private String remarks; // 备注
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private SVCreditCust svCreditCust; // 个人征信ID
	// 生成xsd字段
	@XmlElement(required = true, name = "ID_TYPE")
	private String idType; // 证件类型
	@XmlElement(required = true, name = "ID_NUM")
	private String idNum; // 证件号
	@XmlElement(required = true, name = "BANK_NAME")
	private String bankName; // 发贷行
	@XmlElement(required = true, name = "LOAN_KIND")
	private String loanKind; // 贷款类型
	@XmlElement(required = true, name = "LOAN_STATUS")
	private String loanStatus; // 贷款状态
	@XmlElement(required = true, name = "LOAN_DATE")
	private String loanDate; // 放款日期
	@XmlElement(required = true, name = "EXPIRE_DATE")
	private String expireDate; // 到期日期
	@XmlElement(required = true, name = "GUARANTEE_TYPE")
	private String guaranteeType; // 担保方式
	@XmlElement(required = true, name = "LOAN_AMOUNT")
	private String loanAmount; // 放款金额
	@XmlElement(required = true, name = "BALANCE_AMOUNT")
	private String balanceAmount; // 余额
	@XmlElement(required = true, name = "OVERDUE_AMOUNT")
	private String overdueAmount; // 逾期金额
	@XmlElement(required = true, name = "MONTH_EXPIRE_AMOUNT")
	private String monthExpireAmount; // 月内贷款到期

	public SVCreditCustLoan(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, SVCreditCust svCreditCust, String idType, String idNum, String bankName, String loanKind, String loanStatus, String loanDate, String expireDate, String guaranteeType, String loanAmount, String balanceAmount, String overdueAmount, String monthExpireAmount) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.remarks = remarks;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.svCreditCust = svCreditCust;
		this.idType = idType;
		this.idNum = idNum;
		this.bankName = bankName;
		this.loanKind = loanKind;
		this.loanStatus = loanStatus;
		this.loanDate = loanDate;
		this.expireDate = expireDate;
		this.guaranteeType = guaranteeType;
		this.loanAmount = loanAmount;
		this.balanceAmount = balanceAmount;
		this.overdueAmount = overdueAmount;
		this.monthExpireAmount = monthExpireAmount;
	}

	public SVCreditCustLoan() {
		super();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getMonthExpireAmount() {
		return monthExpireAmount;
	}

	public void setMonthExpireAmount(String monthExpireAmount) {
		this.monthExpireAmount = monthExpireAmount;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public SVCreditCust getSvCreditCust() {
		return svCreditCust;
	}

	public void setSvCreditCust(SVCreditCust svCreditCust) {
		this.svCreditCust = svCreditCust;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public String getCreditCustId() {
		return creditCustId;
	}

	public void setCreditCustId(String creditCustId) {
		this.creditCustId = creditCustId;
	}

}
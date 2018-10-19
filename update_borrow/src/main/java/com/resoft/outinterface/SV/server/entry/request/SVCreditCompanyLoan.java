package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV接口征信企业贷款明细Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditCompanyLoan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private SVCreditCompany svCreditCompany; // 企业征信ID
	// 生成xsd字段
	@XmlElement(required = true, name = "BUSI_REG_NAME")
	private String busiRegName; // 工商登记名称
	@XmlElement(required = true, name = "UN_SOC_CREDIT_NO")
	private String unSocCreditNo; // 统一社会信用代码
	@XmlElement(required = true, name = "ORGANIZATION_NO")
	private String organizationNo; // 组织机构代码
	@XmlElement(required = true, name = "REGISTER_CODE")
	private String registerCode; // 登记注册代码
	@XmlElement(required = true, name = "ID_NUM")
	private String idNum; // 法人证件号
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
	@XmlElement(required = true, name = "CURR_CD")
	private String currCd; // 币种
	@XmlElement(required = true, name = "LOAN_AMOUNT")
	private String loanAmount; // 放款金额
	@XmlElement(required = true, name = "BALANCE_AMOUNT")
	private String balanceAmount; // 余额
	@XmlElement(required = true, name = "OVERDUE_AMOUNT")
	private String overdueAmount; // 逾期金额
	@XmlElement(required = true, name = "GUARANTEE_OUT_STAT")
	private String guaranteeOutStat; // 对外担保状态
	@XmlElement(required = true, name = "MONTH_EXPIRE_AMOUNT")
	private String monthExpireAmount; // 月内贷款到期

	public SVCreditCompany getSvCreditCompany() {
		return svCreditCompany;
	}

	public void setSvCreditCompany(SVCreditCompany svCreditCompany) {
		this.svCreditCompany = svCreditCompany;
	}

	public String getBusiRegName() {
		return busiRegName;
	}

	public void setBusiRegName(String busiRegName) {
		this.busiRegName = busiRegName;
	}

	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
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

	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
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

	public String getGuaranteeOutStat() {
		return guaranteeOutStat;
	}

	public void setGuaranteeOutStat(String guaranteeOutStat) {
		this.guaranteeOutStat = guaranteeOutStat;
	}

	public String getMonthExpireAmount() {
		return monthExpireAmount;
	}

	public void setMonthExpireAmount(String monthExpireAmount) {
		this.monthExpireAmount = monthExpireAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}
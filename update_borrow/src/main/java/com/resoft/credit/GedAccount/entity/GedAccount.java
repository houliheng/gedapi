package com.resoft.credit.GedAccount.entity;


import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E贷账户信息Entity
 * @author gsh
 * @version 2018-01-24
 */
public class GedAccount extends DataEntity<GedAccount> {
	
	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户Id
	private String companyBankOfDeposit;		// 企业开户行
	private String companyAccount;		// 企业对公账户
	private String companyBankBranchName;		// 开户行支行名称
	private String provinceName;		// 省
	private String cityName;		// 市
	private Integer status;		// 0未开户1开户中2已开户
	private Long createId;		// 创建者id
	private Date createTime;		// 创建时间
	private Long modifyId;		// 修改者id
	private Date modifyTime;		// 修改时间
	private String comIdNum; //统一社会信用代码
	private String legalPerNum;//法人证件号码
	private String legalPerName;//法人姓名
	private String companyName;//企业名称
	private String legalPerPhone;//法人号码
	private String custId;
	private String address;
	
	private String CompanyId;
	public GedAccount() {
		super();
	}

	public GedAccount(String id){
		super(id);
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
		
	public String getCompanyBankOfDeposit() {
		return companyBankOfDeposit;
	}

	public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
		this.companyBankOfDeposit = companyBankOfDeposit;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getCompanyBankBranchName() {
		return companyBankBranchName;
	}

	public void setCompanyBankBranchName(String companyBankBranchName) {
		this.companyBankBranchName = companyBankBranchName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	public String getComIdNum() {
		return comIdNum;
	}

	public void setComIdNum(String comIdNum) {
		this.comIdNum = comIdNum;
	}
	
	
	public String getLegalPerNum() {
		return legalPerNum;
	}

	public void setLegalPerNum(String legalPerNum) {
		this.legalPerNum = legalPerNum;
	}

	public String getLegalPerName() {
		return legalPerName;
	}

	public void setLegalPerName(String legalPerName) {
		this.legalPerName = legalPerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLegalPerPhone() {
		return legalPerPhone;
	}

	public void setLegalPerPhone(String legalPerPhone) {
		this.legalPerPhone = legalPerPhone;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "GedAccount [userId=" + userId + ", companyBankOfDeposit=" + companyBankOfDeposit + ", companyAccount="
				+ companyAccount + ", companyBankBranchName=" + companyBankBranchName + ", provinceName=" + provinceName
				+ ", cityName=" + cityName + ", status=" + status + ", createId=" + createId + ", createTime="
				+ createTime + ", modifyId=" + modifyId + ", modifyTime=" + modifyTime + ", comIdNum=" + comIdNum
				+ ", legalPerNum=" + legalPerNum + ", legalPerName=" + legalPerName + ", companyName=" + companyName
				+ ", legalPerPhone=" + legalPerPhone + "]";
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}

	

	
	
}
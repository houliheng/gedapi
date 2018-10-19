package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author guoshaohua
* @version 2018年1月31日 上午10:15:23
* 
*/
public class AccessGedAccount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户Id
	private String companyBankOfDeposit;		// 企业开户行
	private String companyAccount;		// 企业对公账户
	private String provinceName;		// 省
	private String cityName;		// 市
	private Integer status;		// 0未开户1开户中2已开
	private String comIdNum; //统一社会信用代码
	private String legalPerNum;//法人证件号码
	private String legalPerName;//法人姓名
	private String companyName;//企业名称
	private String legalPerPhone;//法人号码
	private String companyBankBranchName;
	private String custId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getCompanyBankBranchName() {
		return companyBankBranchName;
	}
	public void setCompanyBankBranchName(String companyBankBranchName) {
		this.companyBankBranchName = companyBankBranchName;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	
}

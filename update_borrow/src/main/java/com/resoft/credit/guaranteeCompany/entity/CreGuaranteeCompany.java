package com.resoft.credit.guaranteeCompany.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 担保公司Entity
 * @author gsh
 * @version 2018-04-16
 */
public class CreGuaranteeCompany extends DataEntity<CreGuaranteeCompany> {
	
	private static final long serialVersionUID = 1L;
	private String guaranteeCompanyName;		// 担保公司名称
	private String companyType;		// 公司性质
	private String unSocCreditNo;		// 统一社会信用代码
	private String registerCapital;		// 注册资本
	private String regProvince;		// 省
	private String regCity;		// 市
	private String regDistinct;		// 区
	private String operateAddress;		// 公司营业地址
	private String corporationRepresent;		// 公司法人代表
	private String linkman;		// 联系人
	private String linkMobile;		// 联系方式
	private String guaranteeLimit;		// 担保额度
	private String costName;		// 费用名称
	private String costRate;		// 费率
	private String guaranteeState;		// 担保状态
	private String guranteeDetails;//详细地址
	private BigDecimal guaranty;//可担保额度
	private BigDecimal guaranteeAmount;//已担保额度
	private String guranteeCount;//担保数量
	private String isGEDNum;//是否注册冠E贷账号
	private String mobileGed;//注册冠E贷的手机号码
	private String gedNumFlag;//创建冠E贷标识
	public CreGuaranteeCompany() {
		super();
	}

	public CreGuaranteeCompany(String id){
		super(id);
	}

	
	public String getGuaranteeCompanyName() {
		return guaranteeCompanyName;
	}

	public void setGuaranteeCompanyName(String guaranteeCompanyName) {
		this.guaranteeCompanyName = guaranteeCompanyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}
	
	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	
	public String getRegProvince() {
		return regProvince;
	}

	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}
	
	
	public String getRegCity() {
		return regCity;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}
	
	
	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}
	
	public String getOperateAddress() {
		return operateAddress;
	}

	public void setOperateAddress(String operateAddress) {
		this.operateAddress = operateAddress;
	}
	

	public String getCorporationRepresent() {
		return corporationRepresent;
	}

	public void setCorporationRepresent(String corporationRepresent) {
		this.corporationRepresent = corporationRepresent;
	}
	
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	
	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	
	public String getGuaranteeLimit() {
		return guaranteeLimit;
	}

	public void setGuaranteeLimit(String guaranteeLimit) {
		this.guaranteeLimit = guaranteeLimit;
	}
	
	
	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}
	
	
	public String getCostRate() {
		return costRate;
	}

	public void setCostRate(String costRate) {
		this.costRate = costRate;
	}
	
	
	public String getGuaranteeState() {
		return guaranteeState;
	}

	public void setGuaranteeState(String guaranteeState) {
		this.guaranteeState = guaranteeState;
	}

	public String getGuranteeDetails() {
		return guranteeDetails;
	}

	public void setGuranteeDetails(String guranteeDetails) {
		this.guranteeDetails = guranteeDetails;
	}

	public BigDecimal getGuaranty() {
		return guaranty;
	}

	public void setGuaranty(BigDecimal guaranty) {
		this.guaranty = guaranty;
	}

	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public String getGuranteeCount() {
		return guranteeCount;
	}

	public void setGuranteeCount(String guranteeCount) {
		this.guranteeCount = guranteeCount;
	}

	public String getIsGEDNum() {
		return isGEDNum;
	}

	public void setIsGEDNum(String isGEDNum) {
		this.isGEDNum = isGEDNum;
	}

	public String getMobileGed() {
		return mobileGed;
	}

	public void setMobileGed(String mobileGed) {
		this.mobileGed = mobileGed;
	}

	public String getGedNumFlag() {
		return gedNumFlag;
	}

	public void setGedNumFlag(String gedNumFlag) {
		this.gedNumFlag = gedNumFlag;
	}
	
	
}
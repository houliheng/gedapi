package com.resoft.accounting.discountList.entity;


import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
* @author guoshaohua
* @version 2018年5月17日 下午2:22:25
* 
*/
public class Discount extends DataEntity<Discount>{
	private static final long serialVersionUID = 1L;
	private Office orgLevel2;// 大区
	private Office orgLevel3;// 区域
	private Office orgLevel4;// 登记门店
	private String contractNo;// 合同编号
	private String applyNo;// 申请编号
	private String custId;// 客户号
	private String custName;// 客户名称
	private String approProductId;// 批借产品ID
	private String approProductName;// 批借产品名称
	private BigDecimal contractAmount;// 合同金额
	private BigDecimal discountFee;//应贴息总金额
	private BigDecimal factDiscountFee;//已贴息总金额
	private String approPeriodValue;// 批借期限值
	private BigDecimal noDiscountFee;//未贴息金额
	private String discountSave;//区分新老数据标识
	private Office company;
	private String contractStatus;//合同状态
	private String underFlag;//线下录入标识
	private String importStatus;//是否导入状态
	public Office getOrgLevel2() {
		return orgLevel2;
	}
	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	public Office getOrgLevel3() {
		return orgLevel3;
	}
	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	public Office getOrgLevel4() {
		return orgLevel4;
	}
	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getApproProductId() {
		return approProductId;
	}
	public void setApproProductId(String approProductId) {
		this.approProductId = approProductId;
	}
	public String getApproProductName() {
		return approProductName;
	}
	public void setApproProductName(String approProductName) {
		this.approProductName = approProductName;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}
	public BigDecimal getFactDiscountFee() {
		return factDiscountFee;
	}
	public void setFactDiscountFee(BigDecimal factDiscountFee) {
		this.factDiscountFee = factDiscountFee;
	}
	public String getApproPeriodValue() {
		return approPeriodValue;
	}
	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}
	public BigDecimal getNoDiscountFee() {
		return noDiscountFee;
	}
	public void setNoDiscountFee(BigDecimal noDiscountFee) {
		this.noDiscountFee = noDiscountFee;
	}
	public Office getCompany() {
		return company;
	}
	public void setCompany(Office company) {
		this.company = company;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getDiscountSave() {
		return discountSave;
	}

	public void setDiscountSave(String discountSave) {
		this.discountSave = discountSave;
	}
	public String getUnderFlag() {
		return underFlag;
	}
	public void setUnderFlag(String underFlag) {
		this.underFlag = underFlag;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	
	
	
}

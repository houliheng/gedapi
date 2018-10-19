package com.resoft.accounting.discount.entity;


import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
* @author guoshaohua
* @version 2018年5月17日 下午2:22:25
* 
*/
public class DiscountVo extends DataEntity<DiscountVo>{
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
	private BigDecimal discountFee;//应贴息金额
	private BigDecimal factDiscountFee;//已贴息金额
	private String periodNum;// 期数
	private BigDecimal noDiscountFee;//未贴息金额
	private String deductDate;//应还日期
	private BigDecimal repayAmount;//应还本息
	private BigDecimal stayAmount;//待还金额
	private BigDecimal factAmount;//实还金额
	private BigDecimal dicount;//应贴息金额
	private String periodStatus;//期状态
	
	private Office company;
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
	
	public Office getCompany() {
		return company;
	}
	public void setCompany(Office company) {
		this.company = company;
	}
	
	public String getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
	public BigDecimal getStayAmount() {
		return stayAmount;
	}
	public void setStayAmount(BigDecimal stayAmount) {
		this.stayAmount = stayAmount;
	}
	public BigDecimal getFactAmount() {
		return factAmount;
	}
	public void setFactAmount(BigDecimal factAmount) {
		this.factAmount = factAmount;
	}
	public BigDecimal getDicount() {
		return dicount;
	}
	public void setDicount(BigDecimal dicount) {
		this.dicount = dicount;
	}
	public String getPeriodStatus() {
		return periodStatus;
	}
	public void setPeriodStatus(String periodStatus) {
		this.periodStatus = periodStatus;
	}
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public BigDecimal getNoDiscountFee() {
		return noDiscountFee;
	}
	public void setNoDiscountFee(BigDecimal noDiscountFee) {
		this.noDiscountFee = noDiscountFee;
	}
	
	
}

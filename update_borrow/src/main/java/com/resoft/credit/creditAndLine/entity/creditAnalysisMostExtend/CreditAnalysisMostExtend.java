package com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class CreditAnalysisMostExtend extends DataEntity<CreditAnalysisMostExtend> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyNo;
	
	private String applicationDetails;//用途明细
	private String verifyMethod;//核实用途采用的方式方法
	private String verifyBasis;//核实用途采用的依据
	
	private String repaymentSourceBasis;//经营性还款来源分析及依据
	private String secondRepaymentSource;//第二还款来源分析及依据
	private String otherRepaymentSource;//其他还款来源
	
	
	private String guaranteeDetail;//担保人情况简述
	private String guaranteeCorporation;//担保企业情况简述
	
	public CreditAnalysisMostExtend() {
		super();
	}
	public CreditAnalysisMostExtend(String id) {
		super(id);
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getApplicationDetails() {
		return applicationDetails;
	}
	public void setApplicationDetails(String applicationDetails) {
		this.applicationDetails = applicationDetails;
	}
	public String getVerifyMethod() {
		return verifyMethod;
	}
	public void setVerifyMethod(String verifyMethod) {
		this.verifyMethod = verifyMethod;
	}
	public String getVerifyBasis() {
		return verifyBasis;
	}
	public void setVerifyBasis(String verifyBasis) {
		this.verifyBasis = verifyBasis;
	}
	public String getRepaymentSourceBasis() {
		return repaymentSourceBasis;
	}
	public void setRepaymentSourceBasis(String repaymentSourceBasis) {
		this.repaymentSourceBasis = repaymentSourceBasis;
	}
	public String getSecondRepaymentSource() {
		return secondRepaymentSource;
	}
	public void setSecondRepaymentSource(String secondRepaymentSource) {
		this.secondRepaymentSource = secondRepaymentSource;
	}
	public String getOtherRepaymentSource() {
		return otherRepaymentSource;
	}
	public void setOtherRepaymentSource(String otherRepaymentSource) {
		this.otherRepaymentSource = otherRepaymentSource;
	}

	public String getGuaranteeDetail() {
		return guaranteeDetail;
	}
	public void setGuaranteeDetail(String guaranteeDetail) {
		this.guaranteeDetail = guaranteeDetail;
	}
	public String getGuaranteeCorporation() {
		return guaranteeCorporation;
	}
	public void setGuaranteeCorporation(String guaranteeCorporation) {
		this.guaranteeCorporation = guaranteeCorporation;
	}
	

}

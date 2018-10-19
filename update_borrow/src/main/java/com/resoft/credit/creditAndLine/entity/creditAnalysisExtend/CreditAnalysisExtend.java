package com.resoft.credit.creditAndLine.entity.creditAnalysisExtend;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class CreditAnalysisExtend extends DataEntity<CreditAnalysisExtend> {

	private static final long serialVersionUID = 1L;
	private String applyNo;
	
	private String turnoverProfit;//近三年营业额极利润
	private String tfBasis;//近三年营业额极利润核实方法及依据
	private String staffNumberDesc;//员工数量及高管情况
	private String sndBasis;//员工数量及高管情况核实方法及依据
	private String saleProfitDesc;//销售收入构成及稳定性
	private String spdBasis;//销售收入构成及稳定性核实方法及依据
	private String companyDebt;//企业负债情况及分析
	private String cdBasis;//企业负债情况及分析核实方法及依据
	
	/*private String coreValue;//企业核心价值分析
	private String coreAdvantage;//企业核心优势
	private String coreGuarantee;//核心担保人介绍
	private String coreMeasures;//核心担保措施
*/	
	/*private String applicationDetails;//用途明细
	private String verifyMethod;//核实用途采用的方式方法
	private String verifyBasis;//核实用途采用的依据
	
	private String repaymentSourceBasis;//经营性还款来源分析及依据
	private String secondRepaymentSource;//第二还款来源分析及依据
	private String otherRepaymentSource;//其他还款来源
	
	
	private String policyRiskAnalysis;//政策性风险分析
	private String operateRiskAnalysis;//经营性风险分析
	private String creditRiskAnalysis;//信用风险分析
	private String unexpectedRiskAnalysis;//突发事件风险分析
	
	private String guaranteeDetail;//担保人情况简述
	private String guaranteeCorporation;//担保企业情况简述
	*/
	public CreditAnalysisExtend() {
		super();
	}
	public CreditAnalysisExtend(String id) {
		super(id);
	}
	
	
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getTurnoverProfit() {
		return turnoverProfit;
	}
	public void setTurnoverProfit(String turnoverProfit) {
		this.turnoverProfit = turnoverProfit;
	}
	public String getTfBasis() {
		return tfBasis;
	}
	public void setTfBasis(String tfBasis) {
		this.tfBasis = tfBasis;
	}
	public String getStaffNumberDesc() {
		return staffNumberDesc;
	}
	public void setStaffNumberDesc(String staffNumberDesc) {
		this.staffNumberDesc = staffNumberDesc;
	}
	public String getSndBasis() {
		return sndBasis;
	}
	public void setSndBasis(String sndBasis) {
		this.sndBasis = sndBasis;
	}
	public String getSaleProfitDesc() {
		return saleProfitDesc;
	}
	public void setSaleProfitDesc(String saleProfitDesc) {
		this.saleProfitDesc = saleProfitDesc;
	}
	public String getSpdBasis() {
		return spdBasis;
	}
	public void setSpdBasis(String spdBasis) {
		this.spdBasis = spdBasis;
	}
	public String getCompanyDebt() {
		return companyDebt;
	}
	public void setCompanyDebt(String companyDebt) {
		this.companyDebt = companyDebt;
	}
	public String getCdBasis() {
		return cdBasis;
	}
	public void setCdBasis(String cdBasis) {
		this.cdBasis = cdBasis;
	}
	

	
}

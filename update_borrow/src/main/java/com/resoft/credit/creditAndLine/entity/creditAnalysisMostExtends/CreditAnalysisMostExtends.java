package com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends;

import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.thinkgem.jeesite.common.persistence.DataEntity;

public class CreditAnalysisMostExtends extends DataEntity<CreditAnalysisMostExtends> {

	private static final long serialVersionUID = 1L;
	private String applyNo;
	
	private String coreValue;//企业核心价值分析
	private String coreAdvantage;//企业核心优势
	private String coreGuarantee;//核心担保人介绍
	private String coreMeasures;//核心担保措施
	
	private String policyRiskAnalysis;//政策性风险分析
	private String operateRiskAnalysis;//经营性风险分析
	private String creditRiskAnalysis;//信用风险分析
	private String unexpectedRiskAnalysis;//突发事件风险分析
	
	public CreditAnalysisMostExtends() {
		super();
	}
	public CreditAnalysisMostExtends(String id) {
		super(id);
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getCoreValue() {
		return coreValue;
	}
	public void setCoreValue(String coreValue) {
		this.coreValue = coreValue;
	}
	public String getCoreAdvantage() {
		return coreAdvantage;
	}
	public void setCoreAdvantage(String coreAdvantage) {
		this.coreAdvantage = coreAdvantage;
	}
	public String getCoreGuarantee() {
		return coreGuarantee;
	}
	public void setCoreGuarantee(String coreGuarantee) {
		this.coreGuarantee = coreGuarantee;
	}
	public String getCoreMeasures() {
		return coreMeasures;
	}
	public void setCoreMeasures(String coreMeasures) {
		this.coreMeasures = coreMeasures;
	}
	public String getPolicyRiskAnalysis() {
		return policyRiskAnalysis;
	}
	public void setPolicyRiskAnalysis(String policyRiskAnalysis) {
		this.policyRiskAnalysis = policyRiskAnalysis;
	}
	public String getOperateRiskAnalysis() {
		return operateRiskAnalysis;
	}
	public void setOperateRiskAnalysis(String operateRiskAnalysis) {
		this.operateRiskAnalysis = operateRiskAnalysis;
	}
	public String getCreditRiskAnalysis() {
		return creditRiskAnalysis;
	}
	public void setCreditRiskAnalysis(String creditRiskAnalysis) {
		this.creditRiskAnalysis = creditRiskAnalysis;
	}
	public String getUnexpectedRiskAnalysis() {
		return unexpectedRiskAnalysis;
	}
	public void setUnexpectedRiskAnalysis(String unexpectedRiskAnalysis) {
		this.unexpectedRiskAnalysis = unexpectedRiskAnalysis;
	}
	
	
}

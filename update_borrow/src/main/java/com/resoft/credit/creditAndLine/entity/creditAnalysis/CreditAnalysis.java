package com.resoft.credit.creditAndLine.entity.creditAnalysis;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 征信分析信息详细Entity
 * @author wuxi01
 * @version 2016-03-19
 */
public class CreditAnalysis extends DataEntity<CreditAnalysis> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String loanPurposeDesc;		// 借款用途说明
	private String businessDesc;		// 经营情况分析
	private String categoryDesc;		// 行业状况分析
	private String companyDesc;		// 借款企业分析
	private String loanRepayDesc;		// 还款来源分析
	private String guaranteeDesc;		// 担保情况分析
	private String riskPoint;		// 风险点
	private String edgeComment;		// 优势
	private String lineDesc;		// 流水分析
	private String creditDesc;		// 征信分析
	
	private String creditCompany;		//企业法人征信分析
	
	public CreditAnalysis() {
		super();
	}

	public CreditAnalysis(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1000, message="借款用途说明长度必须介于 0 和 1000 之间")
	public String getLoanPurposeDesc() {
		return loanPurposeDesc;
	}

	public void setLoanPurposeDesc(String loanPurposeDesc) {
		this.loanPurposeDesc = loanPurposeDesc;
	}
	
	@Length(min=0, max=1000, message="经营情况分析长度必须介于 0 和 1000 之间")
	public String getBusinessDesc() {
		return businessDesc;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}
	
	@Length(min=0, max=1000, message="行业状况分析长度必须介于 0 和 1000 之间")
	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	@Length(min=0, max=1000, message="借款企业分析长度必须介于 0 和 5000 之间")
	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	
	@Length(min=0, max=1000, message="还款来源分析长度必须介于 0 和 1000 之间")
	public String getLoanRepayDesc() {
		return loanRepayDesc;
	}

	public void setLoanRepayDesc(String loanRepayDesc) {
		this.loanRepayDesc = loanRepayDesc;
	}
	
	@Length(min=0, max=1000, message="担保情况分析长度必须介于 0 和 6000 之间")
	public String getGuaranteeDesc() {
		return guaranteeDesc;
	}

	public void setGuaranteeDesc(String guaranteeDesc) {
		this.guaranteeDesc = guaranteeDesc;
	}
	
	@Length(min=0, max=1000, message="风险点长度必须介于 0 和 1000 之间")
	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}
	
	@Length(min=0, max=1000, message="优势长度必须介于 0 和 1000 之间")
	public String getEdgeComment() {
		return edgeComment;
	}

	public void setEdgeComment(String edgeComment) {
		this.edgeComment = edgeComment;
	}
	
	@Length(min=0, max=1000, message="流水分析长度必须介于 0 和 1000 之间")
	public String getLineDesc() {
		return lineDesc;
	}

	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}
	
	@Length(min=0, max=1000, message="征信分析长度必须介于 0 和 1000 之间")
	public String getCreditDesc() {
		return creditDesc;
	}

	public void setCreditDesc(String creditDesc) {
		this.creditDesc = creditDesc;
	}

	public String getCreditCompany() {
		return creditCompany;
	}

	public void setCreditCompany(String creditCompany) {
		this.creditCompany = creditCompany;
	}
	
	
	
}
package com.resoft.credit.checkFace.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 面审信息Entity
 * 
 * @author yanwanmei
 * @version 2016-02-25
 */
public class CheckFace extends DataEntity<CheckFace> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String checkUserId1; // 外访人1
	private String checkUserId2; // 外方人2
	private String checkUserId3; // 检查人ID3
	private String perLoanDate; // 预借款时间
	private BigDecimal fundingGap; // 资金缺口
	private String companyDesc; // 企业情况
	private String loanPurposeDesc; // 借款用途
	private String loanRepayDesc; // 还款来源
	private String fundingDesc; // 资产状况
	private String familyDesc; // 家庭情况
	private String guaranteeMeasureDesc; // 担保措施
	private String guaranteePersonDesc; // 担保人情况
	private String description; // 电核网查外访，注意事项
	
	//----非表字段
	private String participant;

	public CheckFace() {
		super();
	}

	public CheckFace(String id) {
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 32, message = "外访人1长度必须介于 0 和 32 之间")
	public String getCheckUserId1() {
		return checkUserId1;
	}

	public void setCheckUserId1(String checkUserId1) {
		this.checkUserId1 = checkUserId1;
	}

	@Length(min = 0, max = 32, message = "外方人2长度必须介于 0 和 32 之间")
	public String getCheckUserId2() {
		return checkUserId2;
	}

	public void setCheckUserId2(String checkUserId2) {
		this.checkUserId2 = checkUserId2;
	}

	@Length(min = 0, max = 32, message = "检查人ID3长度必须介于 0 和 32 之间")
	public String getCheckUserId3() {
		return checkUserId3;
	}

	public void setCheckUserId3(String checkUserId3) {
		this.checkUserId3 = checkUserId3;
	}

	public String getPerLoanDate() {
		return perLoanDate;
	}

	public void setPerLoanDate(String perLoanDate) {
		this.perLoanDate = perLoanDate;
	}

	public BigDecimal getFundingGap() {
		return fundingGap;
	}

	public void setFundingGap(BigDecimal fundingGap) {
		this.fundingGap = fundingGap;
	}

	@Length(min = 0, max = 1000, message = "企业情况长度必须介于 0 和 1000 之间")
	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}

	@Length(min = 0, max = 1000, message = "借款用途长度必须介于 0 和 1000 之间")
	public String getLoanPurposeDesc() {
		return loanPurposeDesc;
	}

	public void setLoanPurposeDesc(String loanPurposeDesc) {
		this.loanPurposeDesc = loanPurposeDesc;
	}

	@Length(min = 0, max = 1000, message = "还款来源长度必须介于 0 和 1000 之间")
	public String getLoanRepayDesc() {
		return loanRepayDesc;
	}

	public void setLoanRepayDesc(String loanRepayDesc) {
		this.loanRepayDesc = loanRepayDesc;
	}

	@Length(min = 0, max = 1000, message = "资产状况长度必须介于 0 和 1000 之间")
	public String getFundingDesc() {
		return fundingDesc;
	}

	public void setFundingDesc(String fundingDesc) {
		this.fundingDesc = fundingDesc;
	}

	@Length(min = 0, max = 1000, message = "家庭情况长度必须介于 0 和 1000 之间")
	public String getFamilyDesc() {
		return familyDesc;
	}

	public void setFamilyDesc(String familyDesc) {
		this.familyDesc = familyDesc;
	}

	@Length(min = 0, max = 1000, message = "担保措施长度必须介于 0 和 1000 之间")
	public String getGuaranteeMeasureDesc() {
		return guaranteeMeasureDesc;
	}

	public void setGuaranteeMeasureDesc(String guaranteeMeasureDesc) {
		this.guaranteeMeasureDesc = guaranteeMeasureDesc;
	}

	@Length(min = 0, max = 1000, message = "担保人情况长度必须介于 0 和 1000 之间")
	public String getGuaranteePersonDesc() {
		return guaranteePersonDesc;
	}

	public void setGuaranteePersonDesc(String guaranteePersonDesc) {
		this.guaranteePersonDesc = guaranteePersonDesc;
	}

	@Length(min = 0, max = 1000, message = "电核网查外访，注意事项长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

}
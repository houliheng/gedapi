package com.resoft.postloan.approveExtend.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同展期审批Entity
 * @author wangguodong
 * @version 2016-05-23
 */
public class ApproveExtend extends DataEntity<ApproveExtend> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String extendAmount;		// 申请展期金额
	private String approveResult;		// 审批结果（1通过2打回）
	private String companyManager;		// 分公司经理
	private String companyDeputyManager;		// 分公司副经理
	private String regionPlManager;		// 大区借后管理经理
	private String regionRiskManager;		// 大区风控经理
	private String approvePerson;		// 审批人
	private Date approveDate;		// 批复时间
	private String suggestion; //意见
	
	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public ApproveExtend() {
		super();
	}

	public ApproveExtend(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(String extendAmount) {
		this.extendAmount = extendAmount;
	}
	
	@Length(min=0, max=4, message="审批结果（1通过2打回）长度必须介于 0 和 4 之间")
	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	
	@Length(min=0, max=30, message="分公司经理长度必须介于 0 和 30 之间")
	public String getCompanyManager() {
		return companyManager;
	}

	public void setCompanyManager(String companyManager) {
		this.companyManager = companyManager;
	}
	
	@Length(min=0, max=30, message="分公司副经理长度必须介于 0 和 30 之间")
	public String getCompanyDeputyManager() {
		return companyDeputyManager;
	}

	public void setCompanyDeputyManager(String companyDeputyManager) {
		this.companyDeputyManager = companyDeputyManager;
	}
	
	@Length(min=0, max=30, message="大区借后管理经理长度必须介于 0 和 30 之间")
	public String getRegionPlManager() {
		return regionPlManager;
	}

	public void setRegionPlManager(String regionPlManager) {
		this.regionPlManager = regionPlManager;
	}
	
	@Length(min=0, max=30, message="大区风控经理长度必须介于 0 和 30 之间")
	public String getRegionRiskManager() {
		return regionRiskManager;
	}

	public void setRegionRiskManager(String regionRiskManager) {
		this.regionRiskManager = regionRiskManager;
	}
	
	@Length(min=0, max=30, message="审批人长度必须介于 0 和 30 之间")
	public String getApprovePerson() {
		return approvePerson;
	}

	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
}
package com.resoft.postloan.accountCleanApprove.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 账务清收审批Entity
 * @author yanwanmei
 * @version 2016-06-21
 */
public class AccountCleanApprove extends DataEntity<AccountCleanApprove> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String applyById;		// 申请人员ID
	private String applyBy;		// 申请人员姓名
	private String checkedById;		// 大区审批人员ID
	private String checkedBy;		// 大区审批人员姓名
	private String applyAdvice;		// 申请原因
	private String checkResult;		// 大区审批状态（1待审批2已审批3打回）
	private String checkResultVO;		// 大区审批状态，非本表数据
	private String checkAdvice;		// 大区审批意见
	private Date applyDate;		// 申请时间
	private Date checkDate;		// 大区审批时间
	private String hqCheckedById;		// 总部审批人员ID
	private String hqCheckedBy;		// 总部审批人员姓名
	private String hqCheckResult;		// 总部总部审批状态（1待审批2已审批3打回）
	private String hqCheckAdvice;		// 总部审批意见
	private Date hqCheckDate;		// 总部审批时间
	private String hqCheckResultVO;		// 大区审批状态，非本表数据

	
	public AccountCleanApprove() {
		super();
	}

	public AccountCleanApprove(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=32, message="申请人员ID长度必须介于 0 和 32 之间")
	public String getApplyById() {
		return applyById;
	}

	public void setApplyById(String applyById) {
		this.applyById = applyById;
	}
	
	@Length(min=0, max=30, message="申请人员姓名长度必须介于 0 和 30 之间")
	public String getApplyBy() {
		return applyBy;
	}

	public void setApplyBy(String applyBy) {
		this.applyBy = applyBy;
	}
	
	@Length(min=0, max=32, message="审批人员ID长度必须介于 0 和 32 之间")
	public String getCheckedById() {
		return checkedById;
	}

	public void setCheckedById(String checkedById) {
		this.checkedById = checkedById;
	}
	
	@Length(min=0, max=30, message="审批人员姓名长度必须介于 0 和 30 之间")
	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	
	@Length(min=0, max=1000, message="申请原因长度必须介于 0 和 1000 之间")
	public String getApplyAdvice() {
		return applyAdvice;
	}

	public void setApplyAdvice(String applyAdvice) {
		this.applyAdvice = applyAdvice;
	}
	
	@Length(min=0, max=4, message="审批状态（1待审批2已审批3打回）长度必须介于 0 和 4 之间")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
	@Length(min=0, max=1000, message="审批意见长度必须介于 0 和 1000 之间")
	public String getCheckAdvice() {
		return checkAdvice;
	}

	public void setCheckAdvice(String checkAdvice) {
		this.checkAdvice = checkAdvice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckResultVO() {
		return checkResultVO;
	}

	public void setCheckResultVO(String checkResultVO) {
		this.checkResultVO = checkResultVO;
	}

	public String getHqCheckedById() {
		return hqCheckedById;
	}

	public void setHqCheckedById(String hqCheckedById) {
		this.hqCheckedById = hqCheckedById;
	}

	public String getHqCheckedBy() {
		return hqCheckedBy;
	}

	public void setHqCheckedBy(String hqCheckedBy) {
		this.hqCheckedBy = hqCheckedBy;
	}

	public String getHqCheckResult() {
		return hqCheckResult;
	}

	public void setHqCheckResult(String hqCheckResult) {
		this.hqCheckResult = hqCheckResult;
	}

	public String getHqCheckAdvice() {
		return hqCheckAdvice;
	}

	public void setHqCheckAdvice(String hqCheckAdvice) {
		this.hqCheckAdvice = hqCheckAdvice;
	}

	public Date getHqCheckDate() {
		return hqCheckDate;
	}

	public void setHqCheckDate(Date hqCheckDate) {
		this.hqCheckDate = hqCheckDate;
	}

	public String getHqCheckResultVO() {
		return hqCheckResultVO;
	}

	public void setHqCheckResultVO(String hqCheckResultVO) {
		this.hqCheckResultVO = hqCheckResultVO;
	}
	
	
	
}
package com.resoft.postloan.borrowNew.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借新还旧信息Entity
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
public class BorrowNew extends DataEntity<BorrowNew> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String applyById; // 申请人员ID
	private String applyBy; // 申请人员姓名
	private String DQcheckedById; // 大区审批人员ID
	private String DQcheckedBy; // 大区审批人员姓名
	private String ZBcheckedById; // 总部审批人员ID
	private String ZBcheckedBy; // 总部审批人员姓名
	private String applyAdvice; // 申请原因
	private String checkResult; // 大区审批状态（1待审批2已审批3打回）
	private String DQcheckAdvice; // 大区审批意见
	// private String ZBcheckResult; // 总部审批状态（1待审批2已审批3打回）
	private String ZBcheckAdvice; // 总部审批意见
	private Date applyDate; // 申请时间
	private Date DQcheckDate; // 大区审批时间
	private Date ZBcheckDate; // 总部审批时间

	private String ZBOrDQ; // 区分大区还是总部
	private String operateOrgId;// 非表字段——对应的合同的经办机构

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getZBOrDQ() {
		return ZBOrDQ;
	}

	public void setZBOrDQ(String zBOrDQ) {
		ZBOrDQ = zBOrDQ;
	}

	public BorrowNew() {
		super();
	}

	public BorrowNew(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min = 0, max = 32, message = "申请人员ID长度必须介于 0 和 32 之间")
	public String getApplyById() {
		return applyById;
	}

	public void setApplyById(String applyById) {
		this.applyById = applyById;
	}

	@Length(min = 0, max = 30, message = "申请人员姓名长度必须介于 0 和 30 之间")
	public String getApplyBy() {
		return applyBy;
	}

	public void setApplyBy(String applyBy) {
		this.applyBy = applyBy;
	}

	@Length(min = 0, max = 1000, message = "申请原因长度必须介于 0 和 1000 之间")
	public String getApplyAdvice() {
		return applyAdvice;
	}

	public void setApplyAdvice(String applyAdvice) {
		this.applyAdvice = applyAdvice;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getDQcheckedById() {
		return DQcheckedById;
	}

	public void setDQcheckedById(String dQcheckedById) {
		DQcheckedById = dQcheckedById;
	}

	public String getDQcheckedBy() {
		return DQcheckedBy;
	}

	public void setDQcheckedBy(String dQcheckedBy) {
		DQcheckedBy = dQcheckedBy;
	}

	public String getZBcheckedById() {
		return ZBcheckedById;
	}

	public void setZBcheckedById(String zBcheckedById) {
		ZBcheckedById = zBcheckedById;
	}

	public String getZBcheckedBy() {
		return ZBcheckedBy;
	}

	public void setZBcheckedBy(String zBcheckedBy) {
		ZBcheckedBy = zBcheckedBy;
	}


	public String getDQcheckAdvice() {
		return DQcheckAdvice;
	}

	public void setDQcheckAdvice(String dQcheckAdvice) {
		DQcheckAdvice = dQcheckAdvice;
	}


	public String getZBcheckAdvice() {
		return ZBcheckAdvice;
	}

	public void setZBcheckAdvice(String zBcheckAdvice) {
		ZBcheckAdvice = zBcheckAdvice;
	}

	public Date getDQcheckDate() {
		return DQcheckDate;
	}

	public void setDQcheckDate(Date dQcheckDate) {
		DQcheckDate = dQcheckDate;
	}

	public Date getZBcheckDate() {
		return ZBcheckDate;
	}

	public void setZBcheckDate(Date zBcheckDate) {
		ZBcheckDate = zBcheckDate;
	}

}
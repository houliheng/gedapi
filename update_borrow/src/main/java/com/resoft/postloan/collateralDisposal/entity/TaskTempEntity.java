package com.resoft.postloan.collateralDisposal.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 借后抵押物处置Entity
 * 
 * @author wangguodong
 * @version 2017-01-04
 */
public class TaskTempEntity extends DataEntity<TaskTempEntity> {

	private static final long serialVersionUID = 1L;
	private String custName;// 客户名称
	private String applyNo;// 申请编号
	private String contractNo; // 合同编号
	private String contractAmount;// 合同金额
	private String periodNum; // 期数
	private String overdueAmount;// 逾期金额
	private String overdueDays;// 逾期天数
	private Office orgLevel2;// 大区
	private Office orgLevel3;// 区域
	private Office orgLevel4;// 分公司
	private String mobileNum;// 手机号
	private String registerName;// 登记人
	private String oprateId;// 操作人
	private String contProvince;
	private String contCity;
	private String contDistinct;
	private String contDetails;
	
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}

	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}

	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}

	public String getOprateId() {
		return oprateId;
	}

	public void setOprateId(String oprateId) {
		this.oprateId = oprateId;
	}
	
	public TaskTempEntity() {
		super();
	}

	public TaskTempEntity(String id) {
		super(id);
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}

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

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

}
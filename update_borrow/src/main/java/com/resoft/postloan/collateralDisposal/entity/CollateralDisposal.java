package com.resoft.postloan.collateralDisposal.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借后抵押物处置Entity
 * 
 * @author wangguodong
 * @version 2017-01-04
 */
public class CollateralDisposal extends DataEntity<CollateralDisposal> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String custName;// 客户姓名
	private String periodNum; // 期数
	private String disposalStatus; // 抵押物处置状态（字典类型：DISPOSAL_STATUS）
	private String procInsId; // 流程实例ID
	private String companyId;//机构id
	private String createById;
	
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public CollateralDisposal() {
		super();
	}

	public CollateralDisposal(String id) {
		super(id);
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 1, max = 32, message = "合同编号长度必须介于 1 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min = 0, max = 10, message = "期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	@Length(min = 0, max = 4, message = "抵押物处置状态（字典类型：DISPOSAL_STATUS）长度必须介于 0 和 4 之间")
	public String getDisposalStatus() {
		return disposalStatus;
	}

	public void setDisposalStatus(String disposalStatus) {
		this.disposalStatus = disposalStatus;
	}

	@Length(min = 0, max = 32, message = "流程实例ID长度必须介于 0 和 32 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

}
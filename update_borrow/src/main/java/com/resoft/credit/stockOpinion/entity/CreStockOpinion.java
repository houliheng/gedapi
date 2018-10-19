package com.resoft.credit.stockOpinion.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司尽调审批意见Entity
 * @author jml
 * @version 2017-12-04
 */
public class CreStockOpinion extends DataEntity<CreStockOpinion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String officeOpinion;		// 团队尽调意见
	private String employeeName;		// 员工姓名
	private String employeeNo;		// 工号
	private String officeFlag;		// 0,1,2,3(代表是分公司、区域、大区、总部意见)
	private String submitStatus;
	
	public CreStockOpinion() {
		super();
	}

	public CreStockOpinion(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getOfficeOpinion() {
		return officeOpinion;
	}

	public void setOfficeOpinion(String officeOpinion) {
		this.officeOpinion = officeOpinion;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
	public String getOfficeFlag() {
		return officeFlag;
	}

	public void setOfficeFlag(String officeFlag) {
		this.officeFlag = officeFlag;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	
}
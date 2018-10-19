package com.resoft.credit.custWorkInfo.entity;

import java.math.BigDecimal;

import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户工作信息表Entity
 * 
 * @author gaofeng
 * @version 2016-02-25
 */
public class CustWorkInfo extends DataEntity<CustWorkInfo> {

	private static final long serialVersionUID = 1L;
	private CustInfo custInfo; // 客户
	private String custId;// 客户ID
	private String companyName; // 单位名称
	private String comNature; // 单位性质
	private String postType; // 职位类别（字典类型：POST_TYPE）
	private String postLevel; // 职位级别（字典类型：POST_LEVEL）
	private String postName; // 职位名称
	private String department; // 部门名称
	private String comPhoneAr; // 单位电话（区号）
	private String comPhoneSb; // 单位电话总机
	private String comPhoneEx; // 单位电话分机
	private String workInDate; // 本单位入职日期
	private String salaryMode; // 工资发放方式（字典类型：SALARY_MODE）
	private String monthIncome; // 每月基本薪金
	private BigDecimal otherMonthIncome; // 其他月收入
	private String salartDay; // 工资发放日期 （几号）
	private String companyProvince; // 单位地址省
	private String companyCity; // 单位地址市
	private String companyDistinct; // 单位地址区
	private String companyDetails; // 单位地址详细
	// 非表字段----用于存储当前流程中的申请编号
	private String currApplyNo;

	public CustWorkInfo() {
		super();
	}

	public CustWorkInfo(String id) {
		super(id);
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComNature() {
		return comNature;
	}

	public void setComNature(String comNature) {
		this.comNature = comNature;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getComPhoneAr() {
		return comPhoneAr;
	}

	public void setComPhoneAr(String comPhoneAr) {
		this.comPhoneAr = comPhoneAr;
	}

	public String getComPhoneSb() {
		return comPhoneSb;
	}

	public void setComPhoneSb(String comPhoneSb) {
		this.comPhoneSb = comPhoneSb;
	}

	public String getComPhoneEx() {
		return comPhoneEx;
	}

	public void setComPhoneEx(String comPhoneEx) {
		this.comPhoneEx = comPhoneEx;
	}

	public String getSalaryMode() {
		return salaryMode;
	}

	public void setSalaryMode(String salaryMode) {
		this.salaryMode = salaryMode;
	}

	public String getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(String monthIncome) {
		this.monthIncome = monthIncome;
	}

	public BigDecimal getOtherMonthIncome() {
		return otherMonthIncome;
	}

	public void setOtherMonthIncome(BigDecimal otherMonthIncome) {
		this.otherMonthIncome = otherMonthIncome;
	}

	public String getSalartDay() {
		return salartDay;
	}

	public void setSalartDay(String salartDay) {
		this.salartDay = salartDay;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyDistinct() {
		return companyDistinct;
	}

	public void setCompanyDistinct(String companyDistinct) {
		this.companyDistinct = companyDistinct;
	}

	public String getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(String companyDetails) {
		this.companyDetails = companyDetails;
	}

	public String getWorkInDate() {
		return workInDate;
	}

	public void setWorkInDate(String workInDate) {
		this.workInDate = workInDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCurrApplyNo() {
		return currApplyNo;
	}

	public void setCurrApplyNo(String currApplyNo) {
		this.currApplyNo = currApplyNo;
	}

}
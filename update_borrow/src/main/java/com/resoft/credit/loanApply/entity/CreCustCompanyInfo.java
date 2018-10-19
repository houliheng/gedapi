package com.resoft.credit.loanApply.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1510080102
 * @date-designer:2015年10月23日-songmin
 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
 * 		CRE_CUST_COMPANY_INFO  客户单位信息表  实体类
 */
public class CreCustCompanyInfo extends DataEntity<CreCustCompanyInfo>{
	
	private static final long serialVersionUID = 5641701764195134109L;
	
	private String id;// varchar(32) default null comment 'id', 
	private String applyId;// varchar(32) default null comment '申请id', 
	private String companyName;// varchar(100) default null comment '单位名称', 
	private String comNature;//varchar(10) default null comment '单位性质', 
	private String comIndustry;// varchar(10) default null comment '所属行业（字典类型：com_industry）', 
	private String postType;// varchar(10) default null comment '职位类别（字典类型：post_type）', 
	private String postLevel;// varchar(10) default null comment '职位级别（字典类型：post_level）', 
	private String postName;// varchar(100) default null comment '职位名称', 
	private String department;// varchar(100) default null comment '部门名称', 
	private String comPhoneAr;// varchar(10) default null comment '单位电话（区号）', 
	private String comPhoneSb;// varchar(20) default null comment '单位电话总机', 
	private String comPhoneEx;// varchar(20) default null comment '单位电话分机', 
	private Date workInDate;// timestamp null default null comment '本单位入职日期', 
	private String salaryMode;// varchar(10) default null comment '工资发放方式（字典类型：salary_mode）', 
	private BigDecimal monthIncome;// decimal(18,2) default null comment '每月基本薪金', 
	private BigDecimal otherMonthIncome;// decimal(18,2) default null comment '其他月收入', 
	private Integer salartDay;// int(10) default null comment '工资发放日期 （几号）', 
	private String comProvince;// varchar(32) default null comment '单位地址省', 
	private String comCity;// varchar(32) default null comment '地址市', 
	private String comDistinct;// varchar(32) default null comment '单位地址区', 
	private String comDetails;// varchar(200) default null comment '地址-详细' 
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the applyId
	 */
	public String getApplyNo() {
		return applyId;
	}
	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyNo(String applyId) {
		this.applyId = applyId;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the comNature
	 */
	public String getComNature() {
		return comNature;
	}
	/**
	 * @param comNature the comNature to set
	 */
	public void setComNature(String comNature) {
		this.comNature = comNature;
	}
	/**
	 * @return the comIndustry
	 */
	public String getComIndustry() {
		return comIndustry;
	}
	/**
	 * @param comIndustry the comIndustry to set
	 */
	public void setComIndustry(String comIndustry) {
		this.comIndustry = comIndustry;
	}
	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}
	/**
	 * @param postType the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}
	/**
	 * @return the postLevel
	 */
	public String getPostLevel() {
		return postLevel;
	}
	/**
	 * @param postLevel the postLevel to set
	 */
	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}
	/**
	 * @return the postName
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * @param postName the postName to set
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the comPhoneAr
	 */
	public String getComPhoneAr() {
		return comPhoneAr;
	}
	/**
	 * @param comPhoneAr the comPhoneAr to set
	 */
	public void setComPhoneAr(String comPhoneAr) {
		this.comPhoneAr = comPhoneAr;
	}
	/**
	 * @return the comPhoneSb
	 */
	public String getComPhoneSb() {
		return comPhoneSb;
	}
	/**
	 * @param comPhoneSb the comPhoneSb to set
	 */
	public void setComPhoneSb(String comPhoneSb) {
		this.comPhoneSb = comPhoneSb;
	}
	/**
	 * @return the comPhoneEx
	 */
	public String getComPhoneEx() {
		return comPhoneEx;
	}
	/**
	 * @param comPhoneEx the comPhoneEx to set
	 */
	public void setComPhoneEx(String comPhoneEx) {
		this.comPhoneEx = comPhoneEx;
	}
	/**
	 * @return the workInDate
	 */
	public Date getWorkInDate() {
		return workInDate;
	}
	/**
	 * @param workInDate the workInDate to set
	 */
	public void setWorkInDate(Date workInDate) {
		this.workInDate = workInDate;
	}
	/**
	 * @return the salaryMode
	 */
	public String getSalaryMode() {
		return salaryMode;
	}
	/**
	 * @param salaryMode the salaryMode to set
	 */
	public void setSalaryMode(String salaryMode) {
		this.salaryMode = salaryMode;
	}
	/**
	 * @return the monthIncome
	 */
	public BigDecimal getMonthIncome() {
		return monthIncome;
	}
	/**
	 * @param monthIncome the monthIncome to set
	 */
	public void setMonthIncome(BigDecimal monthIncome) {
		this.monthIncome = monthIncome;
	}
	/**
	 * @return the otherMonthIncome
	 */
	public BigDecimal getOtherMonthIncome() {
		return otherMonthIncome;
	}
	/**
	 * @param otherMonthIncome the otherMonthIncome to set
	 */
	public void setOtherMonthIncome(BigDecimal otherMonthIncome) {
		this.otherMonthIncome = otherMonthIncome;
	}
	/**
	 * @return the salartDay
	 */
	public Integer getSalartDay() {
		return salartDay;
	}
	/**
	 * @param salartDay the salartDay to set
	 */
	public void setSalartDay(Integer salartDay) {
		this.salartDay = salartDay;
	}
	/**
	 * @return the comProvince
	 */
	public String getComProvince() {
		return comProvince;
	}
	/**
	 * @param comProvince the comProvince to set
	 */
	public void setComProvince(String comProvince) {
		this.comProvince = comProvince;
	}
	/**
	 * @return the comCity
	 */
	public String getComCity() {
		return comCity;
	}
	/**
	 * @param comCity the comCity to set
	 */
	public void setComCity(String comCity) {
		this.comCity = comCity;
	}
	/**
	 * @return the comDistinct
	 */
	public String getComDistinct() {
		return comDistinct;
	}
	/**
	 * @param comDistinct the comDistinct to set
	 */
	public void setComDistinct(String comDistinct) {
		this.comDistinct = comDistinct;
	}
	/**
	 * @return the comDetails
	 */
	public String getComDetails() {
		return comDetails;
	}
	/**
	 * @param comDetails the comDetails to set
	 */
	public void setComDetails(String comDetails) {
		this.comDetails = comDetails;
	}
}

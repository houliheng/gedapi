package com.resoft.outinterface.SV.server.entry.request;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV回盘客户工作信息Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCustWorkInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	@XmlElement(required = true, name = "ID_TYPE")
	private String idType; // 证件类型
	@XmlElement(required = true, name = "ID_NUM")
	private String idNum; // 证件号
	@XmlElement(required = true, name = "COMPANY_NAME")
	private String companyName; // 单位名称
	@XmlElement(required = true, name = "COM_NATURE")
	private String comNature; // 单位性质
	@XmlElement(required = true, name = "POST_TYPE")
	private String postType; // 职位类别（字典类型：POST_TYPE）
	@XmlElement(required = true, name = "POST_LEVEL")
	private String postLevel; // 职位级别（字典类型：POST_LEVEL）
	@XmlElement(required = true, name = "POST_NAME")
	private String postName; // 职位名称
	@XmlElement(required = true, name = "DEPARTMENT")
	private String department; // 部门名称
	@XmlElement(required = true, name = "COM_PHONE_AR")
	private String comPhoneAr; // 单位电话（区号）
	@XmlElement(required = true, name = "COM_PHONE_SB")
	private String comPhoneSb; // 单位电话总机
	@XmlElement(required = true, name = "COM_PHONE_EX")
	private String comPhoneEx; // 单位电话分机
	@XmlElement(required = true, name = "WORK_IN_DATE")
	private Date workInDate; // 本单位入职日期
	@XmlElement(required = true, name = "SALARY_MODE")
	private String salaryMode; // 工资发放方式（字典类型：SALARY_MODE）
	@XmlElement(required = true, name = "MONTH_INCOME")
	private String monthIncome; // 每月基本薪金
	@XmlElement(required = true, name = "OTHER_MONTH_INCOME")
	private String otherMonthIncome; // 其他月收入
	@XmlElement(required = true, name = "SALART_DAY")
	private String salartDay; // 工资发放日期 （几号）
	@XmlElement(required = true, name = "COMPANY_PROVINCE")
	private String companyProvince; // 单位地址省
	@XmlElement(required = true, name = "COMPANY_CITY")
	private String companyCity; // 单位地址市
	@XmlElement(required = true, name = "COMPANY_DISTINCT")
	private String companyDistinct; // 单位地址区
	@XmlElement(required = true, name = "COMPANY_DETAILS")
	private String companyDetails; // 单位地址详细

	public SVCustWorkInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min = 0, max = 300, message = "单位名称长度必须介于 0 和 300 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Length(min = 0, max = 4, message = "单位性质长度必须介于 0 和 4 之间")
	public String getComNature() {
		return comNature;
	}

	public void setComNature(String comNature) {
		this.comNature = comNature;
	}

	@Length(min = 0, max = 10, message = "职位类别（字典类型：POST_TYPE）长度必须介于 0 和 10 之间")
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	@Length(min = 0, max = 10, message = "职位级别（字典类型：POST_LEVEL）长度必须介于 0 和 10 之间")
	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}

	@Length(min = 0, max = 30, message = "职位名称长度必须介于 0 和 30 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Length(min = 0, max = 30, message = "部门名称长度必须介于 0 和 30 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Length(min = 0, max = 10, message = "单位电话（区号）长度必须介于 0 和 10 之间")
	public String getComPhoneAr() {
		return comPhoneAr;
	}

	public void setComPhoneAr(String comPhoneAr) {
		this.comPhoneAr = comPhoneAr;
	}

	@Length(min = 0, max = 10, message = "单位电话总机长度必须介于 0 和 10 之间")
	public String getComPhoneSb() {
		return comPhoneSb;
	}

	public void setComPhoneSb(String comPhoneSb) {
		this.comPhoneSb = comPhoneSb;
	}

	@Length(min = 0, max = 10, message = "单位电话分机长度必须介于 0 和 10 之间")
	public String getComPhoneEx() {
		return comPhoneEx;
	}

	public void setComPhoneEx(String comPhoneEx) {
		this.comPhoneEx = comPhoneEx;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWorkInDate() {
		return workInDate;
	}

	public void setWorkInDate(Date workInDate) {
		this.workInDate = workInDate;
	}

	@Length(min = 0, max = 10, message = "工资发放方式（字典类型：SALARY_MODE）长度必须介于 0 和 10 之间")
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

	public String getOtherMonthIncome() {
		return otherMonthIncome;
	}

	public void setOtherMonthIncome(String otherMonthIncome) {
		this.otherMonthIncome = otherMonthIncome;
	}

	@Length(min = 0, max = 2, message = "工资发放日期 （几号）长度必须介于 0 和 2 之间")
	public String getSalartDay() {
		return salartDay;
	}

	public void setSalartDay(String salartDay) {
		this.salartDay = salartDay;
	}

	@Length(min = 0, max = 10, message = "单位地址省长度必须介于 0 和 10 之间")
	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	@Length(min = 0, max = 10, message = "单位地址市长度必须介于 0 和 10 之间")
	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	@Length(min = 0, max = 10, message = "单位地址区长度必须介于 0 和 10 之间")
	public String getCompanyDistinct() {
		return companyDistinct;
	}

	public void setCompanyDistinct(String companyDistinct) {
		this.companyDistinct = companyDistinct;
	}

	@Length(min = 0, max = 300, message = "单位地址详细长度必须介于 0 和 300 之间")
	public String getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(String companyDetails) {
		this.companyDetails = companyDetails;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}
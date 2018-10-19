package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 工作信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkInformation {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "P_unitEntryDate")
	private String P_unitEntryDate;// 5、本单位入职日期
	@XmlElement(name = "P_unitName")
	private String P_unitName;// 12、单位名称
	@XmlElement(name = "P_unitType")
	private String P_unitType;// 11、单位性质
	@XmlElement(name = "P_jobCategory")
	private String P_jobCategory;// 1、职位类别
	@XmlElement(name = "P_jobLevel")
	private String P_jobLevel;// 2、职位级别
	@XmlElement(name = "P_jobName")
	private String P_jobName;// 3、职位名称
	@XmlElement(name = "P_departmentName")
	private String P_departmentName;// 4、部门名称
	@XmlElement(name = "P_wagePaymentMethod")
	private String P_wagePaymentMethod; // 6、工资发放方式
	@XmlElement(name = "P_basicMonthlySalary")
	private String P_basicMonthlySalary;// 7、每月基本薪金
	@XmlElement(name = "P_otherMonthlyIncome")
	private String P_otherMonthlyIncome;// 8、其他月收入
	@XmlElement(name = "P_unitAddress")
	private String P_unitAddress; // 单位地址区
	@XmlElement(name = "P_unitDetailAddress")
	private String P_unitDetailAddress;// 10、单位详细地址

	public String getWork_id() {
		return work_id;
	}

	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}

	public String getP_unitEntryDate() {
		return P_unitEntryDate;
	}

	public void setP_unitEntryDate(String p_unitEntryDate) {
		P_unitEntryDate = p_unitEntryDate;
	}

	public String getP_unitName() {
		return P_unitName;
	}

	public void setP_unitName(String p_unitName) {
		P_unitName = p_unitName;
	}

	public String getP_unitType() {
		return P_unitType;
	}

	public void setP_unitType(String p_unitType) {
		P_unitType = p_unitType;
	}

	public String getP_jobCategory() {
		return P_jobCategory;
	}

	public void setP_jobCategory(String p_jobCategory) {
		P_jobCategory = p_jobCategory;
	}

	public String getP_jobLevel() {
		return P_jobLevel;
	}

	public void setP_jobLevel(String p_jobLevel) {
		P_jobLevel = p_jobLevel;
	}

	public String getP_jobName() {
		return P_jobName;
	}

	public void setP_jobName(String p_jobName) {
		P_jobName = p_jobName;
	}

	public String getP_departmentName() {
		return P_departmentName;
	}

	public void setP_departmentName(String p_departmentName) {
		P_departmentName = p_departmentName;
	}

	public String getP_wagePaymentMethod() {
		return P_wagePaymentMethod;
	}

	public void setP_wagePaymentMethod(String p_wagePaymentMethod) {
		P_wagePaymentMethod = p_wagePaymentMethod;
	}

	public String getP_basicMonthlySalary() {
		return P_basicMonthlySalary;
	}

	public void setP_basicMonthlySalary(String p_basicMonthlySalary) {
		P_basicMonthlySalary = p_basicMonthlySalary;
	}

	public String getP_otherMonthlyIncome() {
		return P_otherMonthlyIncome;
	}

	public void setP_otherMonthlyIncome(String p_otherMonthlyIncome) {
		P_otherMonthlyIncome = p_otherMonthlyIncome;
	}

	public String getP_unitAddress() {
		return P_unitAddress;
	}

	public void setP_unitAddress(String p_unitAddress) {
		P_unitAddress = p_unitAddress;
	}

	public String getP_unitDetailAddress() {
		return P_unitDetailAddress;
	}

	public void setP_unitDetailAddress(String p_unitDetailAddress) {
		P_unitDetailAddress = p_unitDetailAddress;
	}

}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.multds.credit.plCustinfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1601220021
 * @date-designer:2016年1月28日-gaofeng
 * @date-author:2016年1月28日-gaofeng:客户基本信息Entity
 */
public class PLCustInfo extends DataEntity<PLCustInfo> {

	private static final long serialVersionUID = 1L;
	private String custName; // 名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String mobileNum; // 手机号
	private String nationNo; // 民族
	private String birthDay; // 出生日期
	private String ageNo; // 年龄
	private String sexNo; // 性别（字典类型：sex）
	private String wedStatus; // 婚姻状况（字典类型：WED_STATUS）
	private String childrenSon; // 子女：男（数目）
	private String childrenGirl; // 子女：男（数目）
	private String providesNum; // 供养人数
	private String householdSpendingMonth; // 每月家庭支出
	private String topEducationNo; // 最高学历(教育程度)（字典类型：EDUCATION）
	private String homePhoneNo; // 家庭电话
	private String hosePhoneNo; // 住宅电话
	private String regInDate; // 现住宅地居住日期
	private String cityInDate; // 来本市日期
	private String regProvince; // 户籍地址省
	private String regCity; // 户籍地址市
	private String regDistinct; // 户籍地址区
	private String regDetails; // 户籍地址详细
	private String isLocal; // 是否本地户籍
	private String contProvince; // 现居地址省
	private String contCity; // 现居地址市
	private String contDistinct; // 现居地址区
	private String contDetails; // 现居地址详细
	private String addrResi; // 现居地与户籍地址是否一致
	private String isFixedHouse;// 名下是否有房产
	private String livingStatus; // 居住状况
	private String livingStatusDesc;// 居住状况说明
	private String perAnnualIncome; // 个人年收入
	private String sourceOfIncome; // 收入来源
	private String emailNo; // 电子邮箱
	private String microNo; // 微博号码
	private String wechatNo; // 微信号码
	private String qqNo; // QQ号码
	private String otherContactType; // 其他联系方式
	private String categoryMain; // 所属行业-门类(行业表-levle1)
	private String categoryLarge; // 所属行业-大类(行业表-levle2)
	private String categoryMedium; // 所属行业-中类(行业表-levle3)
	private String categorySmall; // 所属行业-小类(行业表-levle4)
	private String personIdStartDate;// 身份证起始日期
	private String personIdEndDate;// 身份证终止日期
	private String energentName;// 紧急联系人
	private String energentMobileNum;// 紧急联系电话
	private String isHaveCompany;// 是否有企业

	// 非表字段---(cre_apply_info字段)
	private String isHaveComLoan;// 是否有共借人
	private String isHaveAssure;// 是否有担保人

	// 非表字段---存储当前流程中申请编号(关系表字段)
	private String currApplyNo;

	// 非表字段---与主借人关系(关系表字段)
	private String relationForApply;

	// 非表字段---创建用户的名字
	private String loginName;

	public PLCustInfo() {
		super();
	}

	public PLCustInfo(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "名称长度必须介于 0 和 50 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 20, message = "证件类型长度必须介于 0 和 20 之间")
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

	@Length(min = 0, max = 15, message = "手机号长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Length(min = 0, max = 3, message = "民族长度必须介于 0 和 3 之间")
	public String getNationNo() {
		return nationNo;
	}

	public void setNationNo(String nationNo) {
		this.nationNo = nationNo;
	}

	@Length(min = 0, max = 3, message = "年龄长度必须介于 0 和 3 之间")
	public String getAgeNo() {
		return ageNo;
	}

	public void setAgeNo(String ageNo) {
		this.ageNo = ageNo;
	}

	@Length(min = 0, max = 1, message = "性别（字典类型：sex）长度必须介于 0 和 1 之间")
	public String getSexNo() {
		return sexNo;
	}

	public void setSexNo(String sexNo) {
		this.sexNo = sexNo;
	}

	@Length(min = 0, max = 1, message = "婚姻状况（字典类型：WED_STATUS）长度必须介于 0 和 1 之间")
	public String getWedStatus() {
		return wedStatus;
	}

	public void setWedStatus(String wedStatus) {
		this.wedStatus = wedStatus;
	}

	@Length(min = 0, max = 2, message = "子女：男（数目）长度必须介于 0 和 2 之间")
	public String getChildrenSon() {
		return childrenSon;
	}

	public void setChildrenSon(String childrenSon) {
		this.childrenSon = childrenSon;
	}

	@Length(min = 0, max = 2, message = "子女：男（数目）长度必须介于 0 和 2 之间")
	public String getChildrenGirl() {
		return childrenGirl;
	}

	public void setChildrenGirl(String childrenGirl) {
		this.childrenGirl = childrenGirl;
	}

	@Length(min = 0, max = 3, message = "供养人数长度必须介于 0 和 3 之间")
	public String getProvidesNum() {
		return providesNum;
	}

	public void setProvidesNum(String providesNum) {
		this.providesNum = providesNum;
	}

	public String getHouseholdSpendingMonth() {
		return householdSpendingMonth;
	}

	public void setHouseholdSpendingMonth(String householdSpendingMonth) {
		this.householdSpendingMonth = householdSpendingMonth;
	}

	@Length(min = 0, max = 4, message = "最高学历(教育程度)（字典类型：EDUCATION）长度必须介于 0 和 4 之间")
	public String getTopEducationNo() {
		return topEducationNo;
	}

	public void setTopEducationNo(String topEducationNo) {
		this.topEducationNo = topEducationNo;
	}

	@Length(min = 0, max = 15, message = "家庭电话长度必须介于 0 和 15 之间")
	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	@Length(min = 0, max = 15, message = "住宅电话长度必须介于 0 和 15 之间")
	public String getHosePhoneNo() {
		return hosePhoneNo;
	}

	public void setHosePhoneNo(String hosePhoneNo) {
		this.hosePhoneNo = hosePhoneNo;
	}

	@Length(min = 0, max = 10, message = "户籍地址省长度必须介于 0 和 10 之间")
	public String getRegProvince() {
		return regProvince;
	}

	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}

	@Length(min = 0, max = 10, message = "户籍地址市长度必须介于 0 和 10 之间")
	public String getRegCity() {
		return regCity;
	}

	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	@Length(min = 0, max = 10, message = "户籍地址区长度必须介于 0 和 10 之间")
	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}

	@Length(min = 0, max = 200, message = "户籍地址详细长度必须介于 0 和 200 之间")
	public String getRegDetails() {
		return regDetails;
	}

	public void setRegDetails(String regDetails) {
		this.regDetails = regDetails;
	}

	@Length(min = 0, max = 1, message = "是否本地户籍长度必须介于 0 和 1 之间")
	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	@Length(min = 0, max = 10, message = "现居地址省长度必须介于 0 和 10 之间")
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}

	@Length(min = 0, max = 10, message = "现居地址市长度必须介于 0 和 10 之间")
	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	@Length(min = 0, max = 10, message = "现居地址区长度必须介于 0 和 10 之间")
	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}

	@Length(min = 0, max = 200, message = "现居地址详细长度必须介于 0 和 200 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}

	@Length(min = 0, max = 1, message = "现居地与户籍地址是否一致长度必须介于 0 和 1 之间")
	public String getAddrResi() {
		return addrResi;
	}

	public void setAddrResi(String addrResi) {
		this.addrResi = addrResi;
	}

	public String getIsFixedHouse() {
		return isFixedHouse;
	}

	public void setIsFixedHouse(String isFixedHouse) {
		this.isFixedHouse = isFixedHouse;
	}

	@Length(min = 0, max = 2, message = "居住状况长度必须介于 0 和 2 之间")
	public String getLivingStatus() {
		return livingStatus;
	}

	public void setLivingStatus(String livingStatus) {
		this.livingStatus = livingStatus;
	}

	@Length(min = 0, max = 2, message = "居住状况说明长度必须介于 0 和 150 之间")
	public String getLivingStatusDesc() {
		return livingStatusDesc;
	}

	public void setLivingStatusDesc(String livingStatusDesc) {
		this.livingStatusDesc = livingStatusDesc;
	}

	public String getPerAnnualIncome() {
		return perAnnualIncome;
	}

	public void setPerAnnualIncome(String perAnnualIncome) {
		this.perAnnualIncome = perAnnualIncome;
	}

	@Length(min = 0, max = 100, message = "收入来源长度必须介于 0 和 100 之间")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	@Length(min = 0, max = 50, message = "电子邮箱长度必须介于 0 和 50 之间")
	public String getEmailNo() {
		return emailNo;
	}

	public void setEmailNo(String emailNo) {
		this.emailNo = emailNo;
	}

	@Length(min = 0, max = 50, message = "微博号码长度必须介于 0 和 50 之间")
	public String getMicroNo() {
		return microNo;
	}

	public void setMicroNo(String microNo) {
		this.microNo = microNo;
	}

	@Length(min = 0, max = 50, message = "微信号码长度必须介于 0 和 50 之间")
	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	@Length(min = 0, max = 20, message = "QQ号码长度必须介于 0 和 20 之间")
	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	@Length(min = 0, max = 100, message = "其他联系方式长度必须介于 0 和 100 之间")
	public String getOtherContactType() {
		return otherContactType;
	}

	public void setOtherContactType(String otherContactType) {
		this.otherContactType = otherContactType;
	}

	@Length(min = 0, max = 10, message = "所属行业-门类(行业表-levle1)长度必须介于 0 和 10 之间")
	public String getCategoryMain() {
		return categoryMain;
	}

	public void setCategoryMain(String categoryMain) {
		this.categoryMain = categoryMain;
	}

	@Length(min = 0, max = 10, message = "所属行业-大类(行业表-levle2)长度必须介于 0 和 10 之间")
	public String getCategoryLarge() {
		return categoryLarge;
	}

	public void setCategoryLarge(String categoryLarge) {
		this.categoryLarge = categoryLarge;
	}

	@Length(min = 0, max = 10, message = "所属行业-中类(行业表-levle3)长度必须介于 0 和 10 之间")
	public String getCategoryMedium() {
		return categoryMedium;
	}

	public void setCategoryMedium(String categoryMedium) {
		this.categoryMedium = categoryMedium;
	}

	@Length(min = 0, max = 10, message = "所属行业-小类(行业表-levle4)长度必须介于 0 和 10 之间")
	public String getCategorySmall() {
		return categorySmall;
	}

	public void setCategorySmall(String categorySmall) {
		this.categorySmall = categorySmall;
	}

	public String getIsHaveComLoan() {
		return isHaveComLoan;
	}

	public void setIsHaveComLoan(String isHaveComLoan) {
		this.isHaveComLoan = isHaveComLoan;
	}

	public String getIsHaveAssure() {
		return isHaveAssure;
	}

	public void setIsHaveAssure(String isHaveAssure) {
		this.isHaveAssure = isHaveAssure;
	}

	public String getIsHaveCompany() {
		return isHaveCompany;
	}

	public void setIsHaveCompany(String isHaveCompany) {
		this.isHaveCompany = isHaveCompany;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getRegInDate() {
		return regInDate;
	}

	public void setRegInDate(String regInDate) {
		this.regInDate = regInDate;
	}

	public String getCityInDate() {
		return cityInDate;
	}

	public void setCityInDate(String cityInDate) {
		this.cityInDate = cityInDate;
	}

	public String getCurrApplyNo() {
		return currApplyNo;
	}

	public void setCurrApplyNo(String currApplyNo) {
		this.currApplyNo = currApplyNo;
	}

	public String getRelationForApply() {
		return relationForApply;
	}

	public void setRelationForApply(String relationForApply) {
		this.relationForApply = relationForApply;
	}

	public String getPersonIdStartDate() {
		return personIdStartDate;
	}

	public void setPersonIdStartDate(String personIdStartDate) {
		this.personIdStartDate = personIdStartDate;
	}

	public String getPersonIdEndDate() {
		return personIdEndDate;
	}

	public void setPersonIdEndDate(String personIdEndDate) {
		this.personIdEndDate = personIdEndDate;
	}

	public String getEnergentName() {
		return energentName;
	}

	public void setEnergentName(String energentName) {
		this.energentName = energentName;
	}

	public String getEnergentMobileNum() {
		return energentMobileNum;
	}

	public void setEnergentMobileNum(String energentMobileNum) {
		this.energentMobileNum = energentMobileNum;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
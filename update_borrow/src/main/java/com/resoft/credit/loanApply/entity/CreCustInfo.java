package com.resoft.credit.loanApply.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1509230043
 * @date-designer:2015年10月14日-songmin
 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_产品类型选择_下一步（保存）、页面跳转
 * 	需求变更调整：
 *	点击"下一步"按钮时，前台对每个字段进行非空校验，然后保存数据库“贷款申请信息表”，
 *	同时在CRE_CUST_INFO表新增对应记录，把客户名称、证件类型、证件号从CRE_APPLY_REGISTER表中拷贝过来；
 */
/**
 * @reqno:H1510080096
 * @date-designer:2015年10月20日-songmin
 * @date-author:2015年10月20日-songmin:添加继承DataEntity类
 */
public class CreCustInfo extends DataEntity<CreCustInfo>{
	private String id;		// varchar(32) default null comment 'id',
	private String applyId;// varchar(32) default null comment '申请id',
	private String custName;// varchar(200) default null comment '客户名称',
	private String idType;	// varchar(10) default null comment '证件类型（字典类型：customer_p_id_type）',
	private String idNum;	// varchar(100) default null comment '证件号',
	private Date birthday;	// timestamp null default null comment '出生日期',
	private String sex;		// varchar(10) default null comment '性别（字典类型：sex）',
	private String mobile;	// varchar(15) default null comment '移动电话',
	private String webStatus;// varchar(10) default null comment '婚姻状况（字典类型：web_status）',
	private Integer childrens;	// int(10) default null comment '子女数量',
	private Integer provides;	// int(10) default null comment '供养人数',
	private BigDecimal payment;// decimal(18,2) default null comment '每月家庭支出',
	private String education;// varchar(10) default null comment '最高学历(教育程度)（字典类型：education）',
	private String areaCode;// varchar(10) default null comment '住宅电话的区号',
	private String phone;	// varchar(20) default null comment '住宅电话',
	private Date restime;	// timestamp null default null comment '现住宅地居住时间',
	private Date cityTime;	// timestamp null default null comment '来本市时间',
	private String email;	// varchar(100) default null comment '电子邮箱',
	private String wechatNumber;// varchar(50) default null comment '微信号码',
	private String qq;		// varchar(50) default null comment 'qq号码',
	private String microNumber;// varchar(100) default null comment '微博号码',
	private String regProvince;// varchar(32) default null comment '户籍地址省',
	private String regCity;// varchar(32) default null comment '户籍地址市',
	private String regDistinct;// varchar(32) default null comment '户籍地址区',
	private String regDetails;// varchar(200) default null comment '户籍地址详细',
	private String isLocal;// varchar(10) default null comment '是否本地户籍（字典类型：yes_no）',
	private String addrResi;// varchar(10) default null comment '现居地与户籍地址是否一致（字典类型：yes_no）',
	private String contProvince;// varchar(32) default null comment '通讯地址省(通讯地址即现住宅地址)',
	private String contCity;// varchar(32) default null comment '通讯地址市',
	private String contDistinct;// varchar(32) default null comment '通讯地址区',
	private String contDetails;// varchar(200) default null comment '通讯地址详细'
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
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}
	/**
	 * @return the idNum
	 */
	public String getIdNum() {
		return idNum;
	}
	/**
	 * @param idNum the idNum to set
	 */
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the webStatus
	 */
	public String getWebStatus() {
		return webStatus;
	}
	/**
	 * @param webStatus the webStatus to set
	 */
	public void setWebStatus(String webStatus) {
		this.webStatus = webStatus;
	}
	/**
	 * @return the childrens
	 */
	public Integer getChildrens() {
		return childrens;
	}
	/**
	 * @param childrens the childrens to set
	 */
	public void setChildrens(Integer childrens) {
		this.childrens = childrens;
	}
	/**
	 * @return the provides
	 */
	public Integer getProvides() {
		return provides;
	}
	/**
	 * @param provides the provides to set
	 */
	public void setProvides(Integer provides) {
		this.provides = provides;
	}
	/**
	 * @return the payment
	 */
	public BigDecimal getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the restime
	 */
	public Date getRestime() {
		return restime;
	}
	/**
	 * @param restime the restime to set
	 */
	public void setRestime(Date restime) {
		this.restime = restime;
	}
	/**
	 * @return the cityTime
	 */
	public Date getCityTime() {
		return cityTime;
	}
	/**
	 * @param cityTime the cityTime to set
	 */
	public void setCityTime(Date cityTime) {
		this.cityTime = cityTime;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the wechatNumber
	 */
	public String getWechatNumber() {
		return wechatNumber;
	}
	/**
	 * @param wechatNumber the wechatNumber to set
	 */
	public void setWechatNumber(String wechatNumber) {
		this.wechatNumber = wechatNumber;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the microNumber
	 */
	public String getMicroNumber() {
		return microNumber;
	}
	/**
	 * @param microNumber the microNumber to set
	 */
	public void setMicroNumber(String microNumber) {
		this.microNumber = microNumber;
	}
	/**
	 * @return the regProvince
	 */
	public String getRegProvince() {
		return regProvince;
	}
	/**
	 * @param regProvince the regProvince to set
	 */
	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}
	/**
	 * @return the regCity
	 */
	public String getRegCity() {
		return regCity;
	}
	/**
	 * @param regCity the regCity to set
	 */
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}
	/**
	 * @return the regDistinct
	 */
	public String getRegDistinct() {
		return regDistinct;
	}
	/**
	 * @param regDistinct the regDistinct to set
	 */
	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}
	/**
	 * @return the regDetails
	 */
	public String getRegDetails() {
		return regDetails;
	}
	/**
	 * @param regDetails the regDetails to set
	 */
	public void setRegDetails(String regDetails) {
		this.regDetails = regDetails;
	}
	/**
	 * @return the isLocal
	 */
	public String getIsLocal() {
		return isLocal;
	}
	/**
	 * @param isLocal the isLocal to set
	 */
	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}
	/**
	 * @return the addrResi
	 */
	public String getAddrResi() {
		return addrResi;
	}
	/**
	 * @param addrResi the addrResi to set
	 */
	public void setAddrResi(String addrResi) {
		this.addrResi = addrResi;
	}
	/**
	 * @return the contProvince
	 */
	public String getContProvince() {
		return contProvince;
	}
	/**
	 * @param contProvince the contProvince to set
	 */
	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}
	/**
	 * @return the contCity
	 */
	public String getContCity() {
		return contCity;
	}
	/**
	 * @param contCity the contCity to set
	 */
	public void setContCity(String contCity) {
		this.contCity = contCity;
	}
	/**
	 * @return the contDistinct
	 */
	public String getContDistinct() {
		return contDistinct;
	}
	/**
	 * @param contDistinct the contDistinct to set
	 */
	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}
	/**
	 * @return the contDetails
	 */
	public String getContDetails() {
		return contDetails;
	}
	/**
	 * @param contDetails the contDetails to set
	 */
	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
}

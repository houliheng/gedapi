package com.resoft.credit.mortgagedperson.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * @reqno:H1601220056
 * @date-designer:2016年1月25日-songmin
 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理
 * 	抵押权人Entity
 */
/**
 * @reqno:H1601220071
 * @date-designer:2016年1月28日-songmin
 * @date-author:2016年1月28日-songmin:需求变更，数据库变更，所以这里的实体类也相应修改
 */
/**
 * @reqno:H1601250005
 * @date-designer:2016年1月28日-songmin
 * @date-author:2016年1月28日-songmin:需求变更，数据库变更，所以这里的实体类也相应修改
 */
public class MortgagedPerson extends DataEntity<MortgagedPerson> {
	
	private static final long serialVersionUID = 1L;
	private String applyId;		// 申请ID
	private String mortgageeName;		// 抵押权限人名称
	private String idNum;		// 证件号
	private String idType = "1";		// 证件类型这里默认为1（身份证），需求中描述为身份证号，所以这里使用身份证作为默认证件类型
	private String sexNo;		// 性别（字典类型：sex）
	private String mobileNum;		// 手机号
	private Date birthDay;		// 出生日期
	private String bankcardNo;		// 银行卡号
	private String bankNo;		// 开户行代码
	private String bankDetailName;		// 开户行级别
	private String contProvince;		// 开户行地址省
	private String contCity;		// 开户行地址市
	private String contDistinct;		// 开户行地址区
	private String contProvinceCode;		// 开户行地址省
	private String contCityCode;		// 开户行地址市
	private String contDistinctCode;		// 开户行地址区
	private String capitalTerraceNo;		// 资金平台账号
	private String openAccountStatus;		// 开户状态
	private String contDetails;		// 开户行地址详细
	private String createId;		// 创建人
	private String updateId;		// 更新人
	
	/**
	 * @return the bankDetailName
	 */
	@Length(min=1, max=300, message="开户行名称长度必须介于 1 和 300 之间")
	public String getBankDetailName() {
		return bankDetailName;
	}

	/**
	 * @param bankDetailName the bankDetailName to set
	 */
	public void setBankDetailName(String bankDetailName) {
		this.bankDetailName = bankDetailName;
	}

	private String sexNoLabel;//性别-字典Label
	
	/**
	 * @return the bankNo
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * @param bankNo the bankNo to set
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * @return the sexNoLabel
	 */
	public String getSexNoLabel() {
		return sexNoLabel;
	}

	/**
	 * @param sexNoLabel the sexNoLabel to set
	 */
	public void setSexNoLabel(String sexNoLabel) {
		this.sexNoLabel = sexNoLabel;
	}

	public MortgagedPerson() {
		super();
	}

	public MortgagedPerson(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请ID长度必须介于 1 和 32 之间")
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	@Length(min=0, max=50, message="抵押权限人名称长度必须介于 0 和 50 之间")
	public String getMortgageeName() {
		return mortgageeName;
	}

	public void setMortgageeName(String mortgageeName) {
		this.mortgageeName = mortgageeName;
	}
	
	@Length(min=0, max=18, message="证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min=0, max=20, message="证件类型长度必须介于 0 和 20 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=1, message="性别（字典类型：sex）长度必须介于 0 和 1 之间")
	public String getSexNo() {
		return sexNo;
	}

	public void setSexNo(String sexNo) {
		this.sexNo = sexNo;
	}
	
	@Length(min=0, max=15, message="手机号长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	@Length(min=0, max=50, message="银行卡号长度必须介于 0 和 50 之间")
	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=10, message="开户行地址省长度必须介于 0 和 10 之间")
	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}
	
	@Length(min=0, max=10, message="开户行地址市长度必须介于 0 和 10 之间")
	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}
	
	@Length(min=0, max=10, message="开户行地址区长度必须介于 0 和 10 之间")
	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}
	
	@Length(min=0, max=32, message="资金平台账号长度必须介于 0 和 32 之间")
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}
	
	@Length(min=0, max=1, message="开户状态长度必须介于 0 和 1 之间")
	public String getOpenAccountStatus() {
		return openAccountStatus;
	}

	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}
	
	@Length(min=0, max=200, message="开户行地址详细长度必须介于 0 和 200 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
	
	@Length(min=0, max=50, message="创建人长度必须介于 0 和 50 之间")
	public String getCreateId() {
		if(StringUtils.isEmpty(createId) && null!=createBy){
			return createBy.getId();
		}
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	@Length(min=0, max=50, message="更新人长度必须介于 0 和 50 之间")
	public String getUpdateId() {
		if(StringUtils.isEmpty(updateId) && null!=createBy ){
			return createBy.getId();
		}
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getContProvinceCode() {
		return contProvinceCode;
	}

	public void setContProvinceCode(String contProvinceCode) {
		this.contProvinceCode = contProvinceCode;
	}

	public String getContCityCode() {
		return contCityCode;
	}

	public void setContCityCode(String contCityCode) {
		this.contCityCode = contCityCode;
	}

	public String getContDistinctCode() {
		return contDistinctCode;
	}

	public void setContDistinctCode(String contDistinctCode) {
		this.contDistinctCode = contDistinctCode;
	}
	
	
}
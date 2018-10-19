package com.resoft.credit.underCustInfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 线下借款-借款人基本信息Entity
 * @author jml
 * @version 2018-06-26
 */
public class UnderCustInfo extends DataEntity<UnderCustInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// apply_no
	private String custName;		// 名称
	private String idNum;		// 证件号
	private String mobileNum;		// 手机号
	private Date birthDay;		// 出生日期
	private String sexNo;		// 性别（字典类型：sex）
	private String wedStatus;		// 婚姻状况（字典类型：WED_STATUS）
	private String topEducationNo;		// 最高学历(教育程度)（字典类型：EDUCATION）
	private String platOverdueCount;		// 本平台逾期次数
	private String platOverdueMoney;		// 本平台逾期总金额(元)
	private String accountProvince;		// 开户地址：省
	private String accountCity;		// 开户地址：市
	private String accountDistinct;		// 开户地址：区
	private String accountDetails;		// 开户地址详细
	private String otherWebloanInfo;		// 其他网贷平台借款情况
	private String executivePenaltyInfo;		// 行政处罚等重大情况
	
	public UnderCustInfo() {
		super();
	}

	public UnderCustInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="apply_no长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=20, message="名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=18, message="证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
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
	
	@Length(min=0, max=1, message="性别（字典类型：sex）长度必须介于 0 和 1 之间")
	public String getSexNo() {
		return sexNo;
	}

	public void setSexNo(String sexNo) {
		this.sexNo = sexNo;
	}
	
	@Length(min=0, max=1, message="婚姻状况（字典类型：WED_STATUS）长度必须介于 0 和 1 之间")
	public String getWedStatus() {
		return wedStatus;
	}

	public void setWedStatus(String wedStatus) {
		this.wedStatus = wedStatus;
	}
	
	@Length(min=0, max=10, message="最高学历(教育程度)（字典类型：EDUCATION）长度必须介于 0 和 10 之间")
	public String getTopEducationNo() {
		return topEducationNo;
	}

	public void setTopEducationNo(String topEducationNo) {
		this.topEducationNo = topEducationNo;
	}
	
	@Length(min=0, max=10, message="本平台逾期次数长度必须介于 0 和 10 之间")
	public String getPlatOverdueCount() {
		return platOverdueCount;
	}

	public void setPlatOverdueCount(String platOverdueCount) {
		this.platOverdueCount = platOverdueCount;
	}
	
	public String getPlatOverdueMoney() {
		return platOverdueMoney;
	}

	public void setPlatOverdueMoney(String platOverdueMoney) {
		this.platOverdueMoney = platOverdueMoney;
	}
	
	@Length(min=0, max=10, message="开户地址：省长度必须介于 0 和 10 之间")
	public String getAccountProvince() {
		return accountProvince;
	}

	public void setAccountProvince(String accountProvince) {
		this.accountProvince = accountProvince;
	}
	
	@Length(min=0, max=10, message="开户地址：市长度必须介于 0 和 10 之间")
	public String getAccountCity() {
		return accountCity;
	}

	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}
	
	@Length(min=0, max=10, message="开户地址：区长度必须介于 0 和 10 之间")
	public String getAccountDistinct() {
		return accountDistinct;
	}

	public void setAccountDistinct(String accountDistinct) {
		this.accountDistinct = accountDistinct;
	}
	
	@Length(min=0, max=300, message="开户地址详细长度必须介于 0 和 300 之间")
	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}
	
	@Length(min=0, max=1000, message="其他网贷平台借款情况长度必须介于 0 和 1000 之间")
	public String getOtherWebloanInfo() {
		return otherWebloanInfo;
	}

	public void setOtherWebloanInfo(String otherWebloanInfo) {
		this.otherWebloanInfo = otherWebloanInfo;
	}
	
	@Length(min=0, max=1000, message="行政处罚等重大情况长度必须介于 0 和 1000 之间")
	public String getExecutivePenaltyInfo() {
		return executivePenaltyInfo;
	}

	public void setExecutivePenaltyInfo(String executivePenaltyInfo) {
		this.executivePenaltyInfo = executivePenaltyInfo;
	}
	
}
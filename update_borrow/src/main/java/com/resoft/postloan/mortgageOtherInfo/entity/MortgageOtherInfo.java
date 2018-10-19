package com.resoft.postloan.mortgageOtherInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 其他抵质押物信息Entity
 * @author yanwanmei
 * @version 2016-02-24
 */
public class MortgageOtherInfo extends DataEntity<MortgageOtherInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String propertyRight;		// 产权属性（个人，企业）
	private String assetsName;		// 资产名称
	private String countNum;		// 数量
	private String valueNum;		// 价值
	private String contDetails;		// 所在地址
	private String description;		// 备注
	private String delFlag;		// 备注
	private String isPushData;		// 备注
	
	private String dealAmount;//处置金额
	private String dealStatus;//处置状态
	
	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	
	public MortgageOtherInfo() {
		super();
	}

	public MortgageOtherInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1, message="产权属性（个人，企业）长度必须介于 0 和 1 之间")
	public String getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.propertyRight = propertyRight;
	}
	
	@Length(min=0, max=50, message="资产名称长度必须介于 0 和 50 之间")
	public String getAssetsName() {
		return assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}
	
	@Length(min=0, max=10, message="数量长度必须介于 0 和 10 之间")
	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	
	public String getValueNum() {
		return valueNum;
	}

	public void setValueNum(String valueNum) {
		this.valueNum = valueNum;
	}
	
	@Length(min=0, max=300, message="所在地址长度必须介于 0 和 300 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getIsPushData() {
		return isPushData;
	}

	public void setIsPushData(String isPushData) {
		this.isPushData = isPushData;
	}
	
	
}
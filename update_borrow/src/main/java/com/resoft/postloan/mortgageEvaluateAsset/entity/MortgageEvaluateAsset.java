package com.resoft.postloan.mortgageEvaluateAsset.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资产评估Entity
 * @author zhaohuakui
 * @version 2016-05-25
 */
public class MortgageEvaluateAsset extends DataEntity<MortgageEvaluateAsset> {
	
	private static final long serialVersionUID = 1L;
	
	private String applyNo;		// 申请编号
	private String contractNo;		// 申请编号
	private String assetName;		// 资产名称
	private BigDecimal bookValue;		// 账面价值
	private String roleType;		// 角色类型(主借人，共借人，担保人，配偶)
	private String custId;		// 客户ID
	private String custName;		// 客户名称
	private BigDecimal marketValue;		// 市值
	private String isMortgage;		// 是否已抵押
	private String isCheck;		// 是否外访
	private String detailComment;		// 详细情况
	private BigDecimal evaluatePrice;		// 评估价格
	private BigDecimal moreOrLessValue;		// 增减值
	private BigDecimal moreOrLessRate;		// 增值率
	private Date mortgageDate;		// 评估基准日期
	private String mortgageUserId;		// 评估人员
	private String remarks;		// 备注
	private String isPushData;		// 是否追加数据
	
	
	public MortgageEvaluateAsset() {
		super();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MortgageEvaluateAsset(String id){
		super(id);
	}
	
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min=0, max=30, message="资产名称长度必须介于 0 和 30 之间")
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public BigDecimal getBookValue() {
		return bookValue;
	}

	public void setBookValue(BigDecimal bookValue) {
		this.bookValue = bookValue;
	}

	public BigDecimal getEvaluatePrice() {
		return evaluatePrice;
	}

	public void setEvaluatePrice(BigDecimal evaluatePrice) {
		this.evaluatePrice = evaluatePrice;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	@Length(min=0, max=4, message="角色类型(主借人，共借人，担保人，配偶)长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=1, max=32, message="客户ID长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	

	@Length(min=0, max=1, message="是否已抵押长度必须介于 0 和 1 之间")
	public String getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}
	
	@Length(min=0, max=1, message="是否外访长度必须介于 0 和 1 之间")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@Length(min=0, max=1000, message="详细情况长度必须介于 0 和 1000 之间")
	public String getDetailComment() {
		return detailComment;
	}

	public void setDetailComment(String detailComment) {
		this.detailComment = detailComment;
	}
	
	
	public BigDecimal getMoreOrLessRate() {
		return moreOrLessRate;
	}

	public void setMoreOrLessRate(BigDecimal moreOrLessRate) {
		this.moreOrLessRate = moreOrLessRate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMortgageDate() {
		return mortgageDate;
	}

	public void setMortgageDate(Date mortgageDate) {
		this.mortgageDate = mortgageDate;
	}
	
	@Length(min=0, max=32, message="评估人员长度必须介于 0 和 32 之间")
	public String getMortgageUserId() {
		return mortgageUserId;
	}

	public void setMortgageUserId(String mortgageUserId) {
		this.mortgageUserId = mortgageUserId;
	}

	public BigDecimal getMoreOrLessValue() {
		return moreOrLessValue;
	}

	public void setMoreOrLessValue(BigDecimal moreOrLessValue) {
		this.moreOrLessValue = moreOrLessValue;
	}

	public String getIsPushData() {
		return isPushData;
	}

	public void setIsPushData(String isPushData) {
		this.isPushData = isPushData;
	}
	
	
}
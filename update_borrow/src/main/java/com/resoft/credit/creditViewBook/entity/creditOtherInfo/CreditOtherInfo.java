package com.resoft.credit.creditViewBook.entity.creditOtherInfo;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 征信意见书其他信息Entity
 * @author wuxi01
 * @version 2016-02-29
 */
public class CreditOtherInfo extends DataEntity<CreditOtherInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String coupleAssetsOfLoan;		// 借款人夫妻资产占借款金额比例
	private String guaranteeAssetsOfLoan;		// 担保人资产占借款金额比例
	private String lastYearStockChange;		// 最近一年股权变更
	private String payTaxStatus;		// 缴税情况
	private String managementFeeStatus;		// 物业费缴纳情况
	private String powerFeeStatus;		// 电费缴纳情况
	private BigDecimal mainManOfStock;		// 主借企业中主借人占股
	private String capitalContributionPeriod;//申请人在用款企业的出资年限
	private Date landEndDate; // 借款到期日期
	
	
	public Date getLandEndDate() {
		return landEndDate;
	}

	public void setLandEndDate(Date landEndDate) {
		this.landEndDate = landEndDate;
	}

	public CreditOtherInfo() {
		super();
	}

	public CreditOtherInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=10, message="借款人夫妻资产占借款金额比例长度必须介于 0 和 10 之间")
	public String getCoupleAssetsOfLoan() {
		return coupleAssetsOfLoan;
	}

	public void setCoupleAssetsOfLoan(String coupleAssetsOfLoan) {
		this.coupleAssetsOfLoan = coupleAssetsOfLoan;
	}
	
	@Length(min=0, max=10, message="担保人资产占借款金额比例长度必须介于 0 和 10 之间")
	public String getGuaranteeAssetsOfLoan() {
		return guaranteeAssetsOfLoan;
	}

	public void setGuaranteeAssetsOfLoan(String guaranteeAssetsOfLoan) {
		this.guaranteeAssetsOfLoan = guaranteeAssetsOfLoan;
	}
	
	@Length(min=0, max=10, message="最近一年股权变更长度必须介于 0 和 10 之间")
	public String getLastYearStockChange() {
		return lastYearStockChange;
	}

	public void setLastYearStockChange(String lastYearStockChange) {
		this.lastYearStockChange = lastYearStockChange;
	}
	
	@Length(min=0, max=10, message="缴税情况长度必须介于 0 和 10 之间")
	public String getPayTaxStatus() {
		return payTaxStatus;
	}

	public void setPayTaxStatus(String payTaxStatus) {
		this.payTaxStatus = payTaxStatus;
	}
	
	@Length(min=0, max=10, message="物业费缴纳情况长度必须介于 0 和 10 之间")
	public String getManagementFeeStatus() {
		return managementFeeStatus;
	}

	public void setManagementFeeStatus(String managementFeeStatus) {
		this.managementFeeStatus = managementFeeStatus;
	}
	
	@Length(min=0, max=10, message="电费缴纳情况长度必须介于 0 和 10 之间")
	public String getPowerFeeStatus() {
		return powerFeeStatus;
	}

	public void setPowerFeeStatus(String powerFeeStatus) {
		this.powerFeeStatus = powerFeeStatus;
	}

	public BigDecimal getMainManOfStock() {
		return mainManOfStock;
	}

	public void setMainManOfStock(BigDecimal mainManOfStock) {
		this.mainManOfStock = mainManOfStock;
	}

	public String getCapitalContributionPeriod() {
		return capitalContributionPeriod;
	}

	public void setCapitalContributionPeriod(String capitalContributionPeriod) {
		this.capitalContributionPeriod = capitalContributionPeriod;
	}
	
}
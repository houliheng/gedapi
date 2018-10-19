package com.resoft.credit.crePurchaseInfo.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 采购商品信息Entity
 * @author jml
 * @version 2017-11-30
 */
public class PurchaseInfo extends DataEntity<PurchaseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String commodityName;		// 采购商品名称
	private String commodityFormat;		// 商品规格
	private Long commodityNum;		// 商品数量
	private String commodityCompany;		// 采购商品单位
	private BigDecimal commodityPrePrice;		// 采购商品单位原价格(元）
	private BigDecimal commodityDiscountPrice;		// 采购商品优惠后价格（元）
	private BigDecimal commodityMargin;		// 货物利差
	
	public PurchaseInfo() {
		super();
	}

	public PurchaseInfo(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	
	public String getCommodityFormat() {
		return commodityFormat;
	}

	public void setCommodityFormat(String commodityFormat) {
		this.commodityFormat = commodityFormat;
	}
	
	public Long getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(Long commodityNum) {
		this.commodityNum = commodityNum;
	}
	
	public String getCommodityCompany() {
		return commodityCompany;
	}

	public void setCommodityCompany(String commodityCompany) {
		this.commodityCompany = commodityCompany;
	}

	public BigDecimal getCommodityPrePrice() {
		return commodityPrePrice;
	}

	public void setCommodityPrePrice(BigDecimal commodityPrePrice) {
		this.commodityPrePrice = commodityPrePrice;
	}

	public BigDecimal getCommodityDiscountPrice() {
		return commodityDiscountPrice;
	}

	public void setCommodityDiscountPrice(BigDecimal commodityDiscountPrice) {
		this.commodityDiscountPrice = commodityDiscountPrice;
	}

	public BigDecimal getCommodityMargin() {
		return commodityMargin;
	}

	public void setCommodityMargin(BigDecimal commodityMargin) {
		this.commodityMargin = commodityMargin;
	}
	
	
	
}
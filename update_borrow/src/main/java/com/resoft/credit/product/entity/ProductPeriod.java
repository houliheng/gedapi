package com.resoft.credit.product.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
 */
/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:产品期限表实体类 ACC_PRODUCT_PERIOD
 */
public class ProductPeriod extends DataEntity<ProductPeriod> {
	private static final long serialVersionUID = 2435900271774250938L;
	private String id;// VARCHAR2(32),
	private String productId;// VARCHAR2(32),
	private Integer periodValue;// NUMBER(10),
	private String periodType;// VARCHAR2(10),
	private Double yearRate;// NUMBER(18,6),
	private Double integratedRate;// NUMBER(18,6),
	private String delFlag;// VARCHAR2(10),
	private String remarks;// VARCHAR2(255)
	
	/**
	 * @reqno:H1509130076
	 * @date-designer:2015年10月12日-songmin
	 * @date-author:2015年10月12日-songmin:扩展字段   产品期限名称
	 */
	private String periodTypeName;// VARCHAR2(10),
	
	public String getPeriodTypeName() {
		return periodTypeName;
	}
	
	public void setPeriodTypeName(String periodTypeName) {
		this.periodTypeName = periodTypeName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Integer getPeriodValue() {
		return periodValue;
	}
	
	public void setPeriodValue(Integer periodValue) {
		this.periodValue = periodValue;
	}
	
	public String getPeriodType() {
		return periodType;
	}
	
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	public Double getYearRate() {
		return yearRate;
	}
	
	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}
	
	public Double getIntegratedRate() {
		return integratedRate;
	}
	
	public void setIntegratedRate(Double integratedRate) {
		this.integratedRate = integratedRate;
	}
	
	public String getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}

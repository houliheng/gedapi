package com.resoft.credit.product.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
 */
/**
 * @reqno:H1509130046
 * @date-designer:2015年9月22日-songmin
 * @date-author:2015年9月22日-songmin:产品费用列表实体类 Table：ACC_PRODUCT_FEE
 * 		费率类型feeRate  BigDecimal  
 * 		Double类型在15位以后将缺失精度，且页面展示时需要进行相应的格式化处理，所以不再推荐通过Double类型来接收关于金额类型的值
 * @db-z:ACC_PRODUCT_FEE:ID、PRODUCT_ID、FEE_NAME、FEE_RATE、FEE_TAG、DEL_FLAG
 */
public class ProductFee extends DataEntity<ProductFee> {
	private static final long serialVersionUID = -8606199180321311443L;
	private String id;// VARCHAR2(32),
	private String productId;// VARCHAR2(32),//产品ID
	private String feeName;// VARCHAR2(255 CHAR),//费用名称
	private BigDecimal feeRate;// NUMBER(18,2),//费用（率）
	private String feeTag;// VARCHAR2(10),//费用（率）类型-Value  参见字典表：FEE_TAG-value
	private String delFlag;// VARCHAR2(10)//是否启用  参见字典表：yes_no
	
	private String feeRateLabel;//费用（率）类型-Label  参见字典表：FEE_TAG-label
	public String getFeeRateLabel() {
		return feeRateLabel;
	}
	public void setFeeRateLabel(String feeRateLabel) {
		this.feeRateLabel = feeRateLabel;
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
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	public String getFeeTag() {
		return feeTag;
	}
	public void setFeeTag(String feeTag) {
		this.feeTag = feeTag;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}

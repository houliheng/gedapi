package com.resoft.credit.product.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 产品信息Entity
 * @author chenshaojia
 * @version 2016-03-04
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String productCode;		// 产品代码
	private String productCode1;		// 产品代码
	private String productName;		// 产品名称
	private String productTypeCode;		// 产品类型编码
	private String procDefKey;		// 流程标识
	private String procName;		// 流程名称
	private String deductDateType;		// 扣款日类型（字典类型：DEDUCT_DATE_TYPE）
	private String deductDateValue;		// 扣款日
	private String description;		// 产品描述
	private String cityId;		// 城市ID（保留字段）
	private String cityName;		// 城市名称（保留字段）
	private Office company;		// 机构ID
	private String orgName;		// 机构名称（保留字段）
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@Length(min=0, max=10, message="产品代码长度必须介于 0 和 10 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getProductCode1() {
		return productCode1;
	}

	public void setProductCode1(String productCode1) {
		this.productCode1 = productCode1;
	}
	
	@Length(min=0, max=30, message="产品名称长度必须介于 0 和 30 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=10, message="产品类型编码长度必须介于 0 和 10 之间")
	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
	public String getProductTypeDesc(){
		return DictUtils.getDictLabel(productTypeCode, Constants.PRODUCT_TYPE, null);
	}
	
	@Length(min=0, max=255, message="流程标识长度必须介于 0 和 255 之间")
	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
	@Length(min=0, max=30, message="流程名称长度必须介于 0 和 30 之间")
	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}
	
	@Length(min=0, max=10, message="扣款日类型（字典类型：DEDUCT_DATE_TYPE）长度必须介于 0 和 10 之间")
	public String getDeductDateType() {
		return deductDateType;
	}

	public void setDeductDateType(String deductDateType) {
		this.deductDateType = deductDateType;
	}
	
	@Length(min=0, max=100, message="扣款日长度必须介于 0 和 100 之间")
	public String getDeductDateValue() {
		return deductDateValue;
	}

	public void setDeductDateValue(String deductDateValue) {
		this.deductDateValue = deductDateValue;
	}
	
	@Length(min=0, max=1000, message="产品描述长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=32, message="城市ID（保留字段）长度必须介于 0 和 32 之间")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@Length(min=0, max=10, message="城市名称（保留字段）长度必须介于 0 和 10 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@Length(min=0, max=200, message="机构名称（保留字段）长度必须介于 0 和 200 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
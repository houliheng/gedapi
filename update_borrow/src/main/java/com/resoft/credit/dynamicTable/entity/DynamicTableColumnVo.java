package com.resoft.credit.dynamicTable.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class DynamicTableColumnVo extends DataEntity<DynamicTableColumnVo>{

	/**
	 * @reqno:H1510170009
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_查询。由于一个xml中只能存在一个fingPage和count方法，如果进行两次查询两次生成表单
	 * 									        原CreProFromColumn POJO只能处理一个list表单生成，这里使用一个伪PO类进行数据封装，所有以value结尾的都是中文字段
	 */
	private static final long serialVersionUID = -5066181684813298479L;
	private String id;
	private String productType;
	private String productTypeValue;
	private String dataGroup;
	private String dataGroupValue;
	private String showPosition;
	private String showPositionValue;
	private String columnCount;
	
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询。由于一个xml中只能存在一个fingPage和count方法，如果进行两次查询两次生成表单
	 * 									        原CreProFromColumn POJO只能处理一个list表单生成，这里使用一个伪PO类进行数据封装，封装creDataGroupTableId，本字段
	 * 									       使在CreProFromColumn唯一关联一条CreDataGroup表中的一条记录，便于后期唯一性查询
	 */
	private String creDataGroupTableId;
	private String companyId;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductTypeCode(String productType) {
		this.productType = productType;
	}
	public String getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeCodeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}
	public String getDataGroup() {
		return dataGroup;
	}
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}
	public String getDataGroupValue() {
		return dataGroupValue;
	}
	public void setDataGroupValue(String dataGroupValue) {
		this.dataGroupValue = dataGroupValue;
	}
	public String getShowPosition() {
		return showPosition;
	}
	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}
	public String getShowPositionValue() {
		return showPositionValue;
	}
	public void setShowPositionValue(String showPositionValue) {
		this.showPositionValue = showPositionValue;
	}
	public String getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}
	public String getCreDataGroupTableId() {
		return creDataGroupTableId;
	}
	public void setCreDataGroupTableId(String creDataGroupTableId) {
		this.creDataGroupTableId = creDataGroupTableId;
	}
	
}

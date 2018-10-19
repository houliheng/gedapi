package com.resoft.postloan.plProFromColumn.entity;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:
 * 		产品动态表单数据项配置表  Table  cre_pro_from_column  
 */
/**
 * @reqno:H1510080093
 * @date-designer:2015年10月17日-songmin
 * @date-author:2015年10月17日-songmin:系统修改了表结构，加入了多法人的支持
 * 		表CRE_PRO_FROM_COLUMN和CRE_DATA_GROUP_TABLE通过DATA_GROUP_ID进行关联
 */
/**
 * @reqno:H1510080094
 * @date-designer:2015年10月19日-songmin
 * @date-author:2015年10月19日-songmin:新加一个companyId用于关联表单动态配置表查询
 */

public class PLProFromColumn extends DataEntity<PLProFromColumn>{
	
	private static final long serialVersionUID = -8398899518557211824L;
	private String	id;// varchar(32) default null comment 'id', 
	private String	productType;// varchar(10) default null comment '产品类型', 
	
	private String	columnCode;// varchar(200) default null comment '数据项字段', 
	private String	columnName;// varchar(200) default null comment '数据项字段显示名称', 
	private String	isRequired;// varchar(10) default null comment '是否必填项（字典类型：yes_no）',
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151021-huangxuecheng
	 * @date-author:20151021-huangxuecheng:进行表单数据项，数据项查询时，因为是int型默认值又出现了0的情况，改正成integer，一般的pojo都应该用Integer包装类
	 */
	private Integer columnLength;// bigint(10) default null comment '字段长度', 
	private Integer	sort;// bigint(10) default null comment '排序' 
	
	//系统加入多法人的支持--动态表单配置项表和产品动态信息表通过该值进行关联
	private String	dataGroupId;// varchar(10) default null comment '表单数据项分类（字典类型：data_group）',
	//扩展字段
	private String showPosition;//'显示位置（1：贷款申请信息；2：客户信息）', 
	private String companyId;//varchar(32)  公司ID（用户所在机构的根机构，用于做多法人支持）
	/**
	 * @reqno:H1510170009
	 * @date-designer:20151021-huangxuecheng
	 * @date-author:20151021-huangxuecheng:新增字段，用于在系统管理_表单数据项配置_查询页面进行中文字段接收保存，可用于页面抓取，或者数据回显
	 * productTypeValue--产品类型name，dataGroupValue--数据栏目，showPositionValue--数据类型,columnCount--数据条数回显
	 */
	//拓展字段
	private String productTypeValue;
	private String dataGroupValue;
	private String showPositionValue;
	private Integer columnCount;
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151021-huangxuecheng
	 * @date-author:20151021-huangxuecheng:新增字段，CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询进行中文字段接收保存，可用于页面抓取，或者数据回显
	 * isRequiredValue--是否必填，dataGroup--原先dnp(弃用)字段，现在补充但是不是用于实现原先功能，dataTableValue数据表中文，showPositionValue--数据类型
	 */
	private String isRequiredValue;
	private String dataGroup;
	private String dataTableValue;
	private String creDataGroupTableId;
	private String dataTableName;
	
	private List<Map<String,String>> columnList;
	private String tableComment;
	private String tableName;

	private List<String> columnNameList;
	private List<String> columnCommentList;
	
	public List<String> getColumnNameList() {
		return columnNameList;
	}
	public void setColumnNameList(List<String> columnNameList) {
		this.columnNameList = columnNameList;
	}
	public List<String> getColumnCommentList() {
		return columnCommentList;
	}
	public void setColumnCommentList(List<String> columnCommentList) {
		this.columnCommentList = columnCommentList;
	}
	public String getDataTableName() {
		return dataTableName;
	}
	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}
	
	
	public List<Map<String, String>> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<Map<String, String>> columnList) {
		this.columnList = columnList;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCreDataGroupTableId() {
		return creDataGroupTableId;
	}
	public void setCreDataGroupTableId(String creDataGroupTableId) {
		this.creDataGroupTableId = creDataGroupTableId;
	}
	public Integer getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}
	public String getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeCodeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}
	public String getDataGroupValue() {
		return dataGroupValue;
	}
	public void setDataGroupValue(String dataGroupValue) {
		this.dataGroupValue = dataGroupValue;
	}
	public String getShowPositionValue() {
		return showPositionValue;
	}
	public void setShowPositionValue(String showPositionValue) {
		this.showPositionValue = showPositionValue;
	}
	public String getIsRequiredValue() {
		return isRequiredValue;
	}
	public void setIsRequiredValue(String isRequiredValue) {
		this.isRequiredValue = isRequiredValue;
	}
	public String getDataTableValue() {
		return dataTableValue;
	}
	public void setDataTableValue(String dataTableValue) {
		this.dataTableValue = dataTableValue;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the dataGroupId
	 */
	public String getDataGroupId() {
		return dataGroupId;
	}
	/**
	 * @param dataGroupId the dataGroupId to set
	 */
	public void setDataGroupId(String dataGroupId) {
		this.dataGroupId = dataGroupId;
	}
	/**
	 * @return the showPosition
	 */
	public String getShowPosition() {
		return showPosition;
	}
	/**
	 * @param showPosition the showPosition to set
	 */
	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductTypeCode(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the dataGroup
	 */
	public String getDataGroup() {
		return dataGroup;
	}
	/**
	 * @param dataGroup the dataGroup to set
	 */
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}
	/**
	 * @return the columnCode
	 */
	public String getColumnCode() {
		return columnCode;
	}
	/**
	 * @param columnCode the columnCode to set
	 */
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the isRequired
	 */
	public String getIsRequired() {
		return isRequired;
	}
	/**
	 * @param isRequired the isRequired to set
	 */
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public Integer getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}

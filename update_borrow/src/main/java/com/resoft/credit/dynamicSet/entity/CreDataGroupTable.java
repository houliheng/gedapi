package com.resoft.credit.dynamicSet.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1510080092
 * @date-designer:2015年10月15日-songmin
 * @date-author:2015年10月15日-songmin:表单数据项分类配置表 实体类   Table：cre_data_group_table
 */
/**
 * @reqno:H1510080093
 * @date-designer:2015年10月17日-songmin
 * @date-author:2015年10月17日-songmin:系统修改了表结构，加入了多法人的支持
 * 		新增ID和COMPANY_ID2个字段 ，表CRE_DATA_GROUP_TABLE和CRE_PRO_FROM_COLUMN通过ID进行关联；
 *  	多法人支持通过COMPANY_ID完成
 */
public class CreDataGroupTable extends DataEntity<CreDataGroupTable>{
	
	private static final long serialVersionUID = -3564832570490376361L;
	private String	id;
	private String 	dataGroup;// varchar(10) default null comment '表单数据项分类', 
	private String	dataTableName;// varchar(200) default null comment '物理表名称', 
	private String	showPosition;// varchar(10) default null comment '显示位置（1：贷款申请信息；2：客户信息）', 
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151017-huangxuecheng:查询数据项栏目时，int型页面出现默认0值，这个不应该出现，pojo的类型一般都是Integer型
	 */
	private Integer	sort;// bigint(10) default null comment '排序' 
	private String companyId;//varchar(32)  公司ID（用户所在机构的根机构，用于做多法人支持）
	
	private List<CreProFromColumn> proFromColumnList = new ArrayList<CreProFromColumn>();//产品动态表单数据项配置表实体类，对应关系为1：N
	private Map resultMap;//动态表单结果数据
	
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:增加字段tableList数据结构，在数据库中取出所有的tableName以及tableComment,然后使用map封装，list返回
	 */
	private List<Map<String,String>> tableList;
	private String tableComment;
	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:增加字段用户接收字典表查询data_group字段Label以及Value,show_position的value值,用于接收字典表中文信息
	 */
	private String dataGroupLabel;
	private String dataGroupValue;
	private String showPositionValue;
	
	public String getShowPositionValue() {
		return showPositionValue;
	}
	public void setShowPositionValue(String showPositionValue) {
		this.showPositionValue = showPositionValue;
	}
	public String getDataGroupLabel() {
		return dataGroupLabel;
	}
	public void setDataGroupLabel(String dataGroupLabel) {
		this.dataGroupLabel = dataGroupLabel;
	}
	public String getDataGroupValue() {
		return dataGroupValue;
	}
	public void setDataGroupValue(String dataGroupValue) {
		this.dataGroupValue = dataGroupValue;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public List<Map<String, String>> getTableList() {
		return tableList;
	}
	public void setTableList(List<Map<String, String>> tableList) {
		this.tableList = tableList;
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
	 * @return the resultMap
	 */
	public Map getResultMap() {
		return resultMap;
	}
	/**
	 * @param resultMap the resultMap to set
	 */
	public void setResultMap(Map resultMap) {
		this.resultMap = resultMap;
	}

	/**
	 * @return the proFromColumnList
	 */
	public List<CreProFromColumn> getProFromColumnList() {
		return proFromColumnList;
	}
	/**
	 * @param proFromColumnList the proFromColumnList to set
	 */
	public void setProFromColumnList(List<CreProFromColumn> proFromColumnList) {
		this.proFromColumnList = proFromColumnList;
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
	 * @return the dataTableName
	 */
	public String getDataTableName() {
		return dataTableName;
	}
	/**
	 * @param dataTableName the dataTableName to set
	 */
	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}

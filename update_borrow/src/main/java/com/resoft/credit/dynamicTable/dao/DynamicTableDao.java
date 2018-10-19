package com.resoft.credit.dynamicTable.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface DynamicTableDao extends CrudDao<CreDataGroupTable>{
	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:批量删除方法，待用
	 */
	public void deleteMulti(List<String> idsList);
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:取出所有的table名和描述，提供修改页面使用
	 */
	/**
	 * @reqno:H1511300032
	 * @date-designer:20151202-huangxuecheng
	 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
	 */	
	public List<Map<String,String>> selectAllTableName(String schemaName);
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:根据id查询数据栏目，提供修改校验使用
	 */
	public String selectIdByDataGroupAndCompanyid(CreDataGroupTable creDataGroupTable);
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:根据用户id查到跟机构id修改保存使用
	 */
	public String selectParentIdsByCompanyid(String id);	
}

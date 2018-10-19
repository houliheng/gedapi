package com.resoft.credit.dynamicTable.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface DynamicTableColumnDeployDao extends CrudDao<CreProFromColumn>{
	/**
	 * @reqno:H1510170011
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:新增时，查出所有table名，以便筛选
	 */
	public void deleteMulti(List<String> idsList);
	/**
	 * @reqno:H1511300032
	 * @date-designer:20151202-huangxuecheng
	 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
	 */	
	public List<Map<String,String>> selectAllTableName(String schemaName);
	/**
	 * @reqno:H1510170009
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:查询列表时，必须先查询当前用户的顶级机构，用于支持多法人报送
	 */
	public String selectParentIdsByCompanyid(String companyId);
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:查询数据项配置列表时，首先查出tableName,以便进行后续回显传值操作
	 */		
	public String selectTableNameById(String id);
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:获取最新的数据项配置POJO方便于后续的回显
	 */	
	public CreProFromColumn selectInforByIdAndProductType(Map<String, String> map);
	/**
	 * @reqno:H1511300032
	 * @date-designer:20151202-huangxuecheng
	 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
	 */	
	public List<Map<String,String>> selectColumnAndCommentByTableName(Map<String, String> map);
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:修改前或者新增前，先将所有的columnName和columnValue查出进行封装后返回
	 */
	/**
	 * @reqno:H1511300032
	 * @date-designer:20151202-huangxuecheng
	 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
	 */
	public List<String> selectColumnNameOrColumnCommentByTableName(Map<String, String> map);
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:修改前判断column唯一性方法
	 */
	public long selectColumnCount(String columnCode);
	
	public long selectColumnCountByColumnCodeAndProductType(Map<String, String> map);
	/**
	 * @reqno:H1511050008
	 * @date-designer:20151109-huangxuecheng
	 * @date-author:20151109-huangxuecheng:查询出当前列的所有信息的方法，可以查询出当前column的类型
	 */
	public List<Map<String, String>> selectColumnInfor(Map<String, String> map);
}

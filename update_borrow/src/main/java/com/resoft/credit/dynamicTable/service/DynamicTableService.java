package com.resoft.credit.dynamicTable.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicTable.dao.DynamicTableDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
/**
 * @author huangxuecheng 数据栏目service
 *
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class DynamicTableService extends CrudService<DynamicTableDao, CreDataGroupTable>{
	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:注入dynamicTableDao
	 */
	@Autowired
	private DynamicTableDao dynamicTableDao;
	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:批量删除方法，待用
	 */
	@Transactional(value="CRE",readOnly = false)
	public void deleteMutil(List<String> idsList){
		dynamicTableDao.deleteMulti(idsList);
	}
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
	public List<Map<String,String>> selectAllTableName(String schemaName){
		return dynamicTableDao.selectAllTableName(schemaName);
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:根据id查询数据栏目，提供修改校验使用
	 */
	public String searchIdByDataGroup(CreDataGroupTable creDataGroupTable){
		return dynamicTableDao.selectIdByDataGroupAndCompanyid(creDataGroupTable);
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:根据用户id查到跟机构id修改保存使用
	 */
	public String searchParentIdsByCompanyId(String companyId){
		return dynamicTableDao.selectParentIdsByCompanyid(companyId);
	}
}

package com.resoft.credit.dynamicTable.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.dynamicTable.dao.DynamicTableColumnDao;
import com.resoft.credit.dynamicTable.entity.DynamicTableColumnVo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class DynamicTableColumnService extends CrudService<DynamicTableColumnDao, DynamicTableColumnVo>{
	
	@Autowired
	private DynamicTableColumnDao dynamicTableColumnDao;
	
	@Transactional(value="CRE",readOnly = false)
	public void deleteMutil(List<String> idsList){
		dynamicTableColumnDao.deleteMulti(idsList);
	}
	/**
	 * @reqno:H1510170011
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:新增时，查出所有table名，以便筛选
	 */
	/**
	 * @reqno:H1511300032
	 * @date-designer:20151202-huangxuecheng
	 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
	 */	
	public List<Map<String,String>> selectAllTableName(String schemaName){
		return dynamicTableColumnDao.selectAllTableName(schemaName);
	}
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询，点击配置执行的方法。查询时查出顶级机构id
	 */
	public String searchParentIdsByCompanyId(String companyId){
		return dynamicTableColumnDao.selectParentIdsByCompanyid(companyId);
	}
}

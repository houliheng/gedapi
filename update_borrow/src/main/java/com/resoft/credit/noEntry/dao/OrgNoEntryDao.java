package com.resoft.credit.noEntry.dao;

import java.util.List;

import com.resoft.credit.noEntry.entity.OrgNoEntry;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 机构禁件表DAO接口
 * 
 * @author lirongchao
 * @version 2016-01-08
 */
@MyBatisDao
public interface OrgNoEntryDao extends CrudDao<OrgNoEntry> {
	/**
	 * @reqno: H1512260022
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z :cre_company_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:批量删除机构禁件列表信息
	 */
	public void batchDelete(List<String> idList);

	/**
	 * 根据机构ID查询上级机构禁件列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrgNoEntry> findParentOrgNoEntryList(String orgId);

	/**
	 * 根据机构ID查询禁件列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrgNoEntry> findListByOrgId(String orgId);
}
package com.resoft.credit.noEntry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.noEntry.dao.OrgNoEntryDao;
import com.resoft.credit.noEntry.entity.OrgNoEntry;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 机构禁件表Service
 * @author lirongchao
 * @version 2016-01-08
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class OrgNoEntryService extends CrudService<OrgNoEntryDao, OrgNoEntry> {

	@Autowired
	private OrgNoEntryDao companyNoEntryDao;
	public OrgNoEntry get(String id) {
		return super.get(id);
	}
	
	public List<OrgNoEntry> findList(OrgNoEntry companyNoEntry) {
		return super.findList(companyNoEntry);
	}
	/**
	 * @reqno: H1512260021
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z :cre_company_no_entry : org_id
	 * @db-j :sys_office : code,name
	 * @date-author:2016年01月13日-lirongchao:根据查询条件查询机构禁件列表
	 * 										查询条件-机构编码【模糊查询】、机构名称【模糊查询】
	 */	
	public Page<OrgNoEntry> findPage(Page<OrgNoEntry> page, OrgNoEntry companyNoEntry) {
		return super.findPage(page, companyNoEntry);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(OrgNoEntry companyNoEntry,String idsArr) {
		String ids[] = idsArr.split(",");
		List<OrgNoEntry> userNoEntryList = companyNoEntryDao.findList(companyNoEntry);
		List<String> userIdList = new ArrayList<String>();
		if(ids.length > 0){//遍历出禁件人员列表是否有需要删除的人员
			for(OrgNoEntry cne: userNoEntryList){
				boolean flag = true;
				for(String officeId : ids){
					if(officeId.equals(cne.getOrgId())){
						flag = false;
						break;
					}
				}
				if(flag){
					userIdList.add(cne.getId());
				}
			}
			if(userIdList.size()>0){
				//删除已添加的禁件人员
				companyNoEntryDao.batchDelete(userIdList);
			}
			for(String officeId : ids){//遍历出需要新增的禁件人员
				boolean flag = true;
				for(OrgNoEntry cne: userNoEntryList){
					if(officeId.equals(cne.getOrgId())){
						flag = false;
						break;
					}
				}
				if(flag && !StringUtils.isEmpty(officeId)){
					OrgNoEntry insertcompanyNoEntry = new OrgNoEntry();
					insertcompanyNoEntry.setOrgId(officeId);
					super.save(insertcompanyNoEntry);
				}
			}			
		}		
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(OrgNoEntry companyNoEntry) {
		super.delete(companyNoEntry);
	}
	/**
	 * @reqno: H1512260022
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z :cre_company_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:批量删除机构禁件列表信息
	 */		
	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(String ids){
		List<String> idList = new ArrayList<String>();
		if(!StringUtils.isEmpty(ids)){
			for(String id: ids.split(",")){
				idList.add(id);
			}
			super.dao.batchDelete(idList);
		}
	}	
	
	/**
	 * 根据机构ID查询上级机构禁件列表
	 * @param orgId
	 * @return
	 */
	public List<OrgNoEntry> findParentOrgNoEntryList(String orgId){
		return super.dao.findParentOrgNoEntryList(orgId);
	};
	
	
	/**
	 * 根据机构ID查询禁件列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrgNoEntry> findListByOrgId(String orgId){
		return super.dao.findListByOrgId(orgId);
	};
}
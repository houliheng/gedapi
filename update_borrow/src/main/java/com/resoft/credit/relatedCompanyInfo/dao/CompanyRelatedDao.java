package com.resoft.credit.relatedCompanyInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.relatedCompanyInfo.entity.CompanyRelated;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 企业关联企业信息DAO接口
 * @author caoyinglong
 * @version 2016-02-29
 */
@MyBatisDao
public interface CompanyRelatedDao extends CrudDao<CompanyRelated> {
	public void batchDelete(@Param("idList")List<String> idList);
	
	public List<CompanyRelated> findListByParams(Map<String, Object> params);
	
	public List<CompanyRelated> findRelatedList(Map<String, Object> params);
}
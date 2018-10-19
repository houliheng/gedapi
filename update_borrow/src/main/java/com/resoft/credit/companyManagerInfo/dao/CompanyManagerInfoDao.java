package com.resoft.credit.companyManagerInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.companyManagerInfo.entity.CompanyManagerInfo;

/**
 * 企业高管信息DAO接口
 * @author caoyinglong
 * @version 2016-02-27
 */
@MyBatisDao
public interface CompanyManagerInfoDao extends CrudDao<CompanyManagerInfo> {
	public void batchDelete(@Param("idList")List<String> idList);
	
	public List<CompanyManagerInfo> findListByParams(Map<String, Object> params);
	
	public List<CompanyManagerInfo> findManagerList(Map<String, Object> params);
}
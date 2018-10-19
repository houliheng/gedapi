package com.resoft.credit.gqgetGuarantorCompany.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.gqgetGuarantorCompany.entity.GqgetGuarantorCompany;

/**
 * 冠e通担保企业DAO接口
 * @author wangguodong
 * @version 2016-09-29
 */
@MyBatisDao
public interface GqgetGuarantorCompanyDao extends CrudDao<GqgetGuarantorCompany> {
	/**
	 * 根据applyNo查询冠e通担保企业
	 */
	public List<GqgetGuarantorCompany> findGqgetGuarantorCompanyDataByApplyNo(String applyNo);
}
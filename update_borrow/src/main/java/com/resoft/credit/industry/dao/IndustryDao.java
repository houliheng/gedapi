package com.resoft.credit.industry.dao;

import java.util.List;

import com.resoft.credit.industry.entity.Industry;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 行业分类信息表DAO接口
 * @author songmin
 * @version 2016-01-06
 */
/**
 * @reqno:H1512210027
 * @date-designer:2016年1月6日-songmin
 * @date-author:2016年1月6日-songmin:行业分类DAO
 */
@MyBatisDao
public interface IndustryDao extends CrudDao<Industry> {
	
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 根据上级行业编码获取下级行业编码信息
	 */
	public List<Industry> findByParentCode(String parentInduCode);
}
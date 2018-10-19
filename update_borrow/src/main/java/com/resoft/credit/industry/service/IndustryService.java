package com.resoft.credit.industry.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.industry.dao.IndustryDao;
import com.resoft.credit.industry.entity.Industry;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 行业分类信息表Service
 * @author songmin
 * @version 2016-01-06
 */
/**
 * @reqno:H1512210027
 * @date-designer:2016年1月6日-songmin
 * @date-author:2016年1月6日-songmin:行业分类信息表Service
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class IndustryService extends CrudService<IndustryDao, Industry> {

	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 根据上级行业编码获取下级行业编码信息
	 */
	public List<Industry> findByParentCode(String parentInduCode){
		if(StringUtils.isEmpty(parentInduCode)){
			parentInduCode  = "root";
		}
		return super.dao.findByParentCode(parentInduCode);
	}
}
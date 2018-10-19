package com.resoft.credit.loanApply.dao;

import com.resoft.credit.loanApply.entity.CreCustCompanyInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1510080102
 * @date-designer:2015年10月23日-songmin
 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
 * 		客户单位信息表 DAO 
 */
/**
 * @reqno:H1510080102
 * @date-designer:2015年10月23日-songmin
 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
 * 		根据applyId获取客户所在公司信息  get（） 
 */
@MyBatisDao("CreCustCompanyInfoDao")
public interface CreCustCompanyInfoDao extends CrudDao<CreCustCompanyInfo>{
	
}

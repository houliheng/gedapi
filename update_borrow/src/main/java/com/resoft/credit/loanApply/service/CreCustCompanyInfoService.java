package com.resoft.credit.loanApply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.loanApply.dao.CreCustCompanyInfoDao;
import com.resoft.credit.loanApply.entity.CreCustCompanyInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1510080102
 * @date-designer:2015年10月23日-songmin
 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
 * 		客户单位信息表  Service
 */
/**
 * @reqno:H1510080102
 * @date-designer:2015年10月23日-songmin
 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
 * 		根据applyId获取客户所在公司信息  get（） 
 */
@Service("CreCustCompanyInfoService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreCustCompanyInfoService extends CrudService<CreCustCompanyInfoDao, CreCustCompanyInfo>{

}

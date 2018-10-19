package com.resoft.credit.loanApply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.loanApply.dao.CreCustHousingInfoDao;
import com.resoft.credit.loanApply.entity.CreCustHousingInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1510080098
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
 * 客户信息表Service  get（）
 */
@Service("CreCustHousingInfoService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreCustHousingInfoService extends CrudService<CreCustHousingInfoDao, CreCustHousingInfo>{
	
}

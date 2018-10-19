package com.resoft.credit.loanApply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.loanApply.dao.CreCustCarInfoDao;
import com.resoft.credit.loanApply.entity.CreCustCarInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1510080100
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_车辆信息【默认数据项】展现
 * 		客户车辆信息表   CRE_CUST_CAR_INFO Service   默认调用过的方法：get（applyId）
 */
@Service("CreCustCarInfoService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class CreCustCarInfoService extends CrudService<CreCustCarInfoDao, CreCustCarInfo>{
	
}

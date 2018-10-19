package com.resoft.credit.loanApply.dao;

import com.resoft.credit.loanApply.entity.CreCustCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1510080100
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_车辆信息【默认数据项】展现
 * 		CRE_CUST_CAR_INFO   车辆信息 DAO  默认调用过的方法：get（applyId）
 */
@MyBatisDao("CreCustCarInfoDao")
public interface CreCustCarInfoDao extends CrudDao<CreCustCarInfo>{
	
}

package com.resoft.credit.loanApply.dao;

import com.resoft.credit.loanApply.entity.CreCustHousingInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1510080098
 * @date-designer:2015年10月21日-songmin
 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
 * 		客户信息表Dao   get（）
 */
@MyBatisDao("CreCustHousingInfoDao")
public interface CreCustHousingInfoDao extends CrudDao<CreCustHousingInfo> {

}

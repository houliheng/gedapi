package com.resoft.postloan.mortgageEvaluateInfo.dao;

import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 资产管理DAO接口
 * @author zhaohuakui
 * @version 2016-05-25
 */
@MyBatisDao("plMortgageEvaluateInfoDao")
public interface MortgageEvaluateInfoDao extends CrudDao<MortgageEvaluateInfo> {

	MortgageEvaluateInfo findMortgageByInfoId(String infoId);

}

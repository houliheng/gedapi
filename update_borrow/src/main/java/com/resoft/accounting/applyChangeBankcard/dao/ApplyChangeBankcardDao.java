package com.resoft.accounting.applyChangeBankcard.dao;

import java.util.List;

import com.resoft.accounting.applyChangeBankcard.entity.ApplyChangeBankcard;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 银行卡变更申请DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@MyBatisDao
public interface ApplyChangeBankcardDao extends CrudDao<ApplyChangeBankcard> {

	/**
	 * 通过合约号查询银行卡变更信息(审核过的信息)
	 */
	ApplyChangeBankcard getApplyChangeBankcardByContractNo(ApplyChangeBankcard applyChangeBankcard);

	/**
	 * 通过合约号查询银行卡变更信息(审核中的信息)
	 */
	List<ApplyChangeBankcard> getApplyChangeBankcardByContractNoFail(String contractNo);
}
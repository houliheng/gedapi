package com.resoft.postloan.accountCleanApprove.dao;

import java.util.List;
import java.util.Map;

import com.resoft.postloan.accountCleanApprove.entity.AccountCleanApprove;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 账务清收审批DAO接口
 * @author yanwanmei
 * @version 2016-06-21
 */
@MyBatisDao
public interface AccountCleanApproveDao extends CrudDao<AccountCleanApprove> {
	public List<MyMap> getAccountCleanApprovePLContract(MyMap paramMap);
	public void updateHQCheckResultByContractNo(Map<String,String> param);
	public List<MyMap> getAccountCleanApproveList(MyMap paramMap);
}
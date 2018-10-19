package com.resoft.postloan.accountCleanAllocate.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.accountCleanAllocate.entity.AccountCleanAllocate;

/**
 * 借后清收任务分配表DAO接口
 * @author yanwanmei
 * @version 2016-06-02
 */
@MyBatisDao
public interface AccountCleanAllocateDao extends CrudDao<AccountCleanAllocate> {
	public List<String> getContractNosByAllocateType(Map<String,String> paramMap);
	public void updateAllocateTypeByContractNo(Map<String,String> paraMap);
	public AccountCleanAllocate getAccountCleanAllocateByContractNo(String contractNo);
	public List<MyMap> getAccountCleanPLContract(MyMap paramMap);

}
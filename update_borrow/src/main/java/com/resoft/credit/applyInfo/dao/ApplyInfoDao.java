package com.resoft.credit.applyInfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.applyInfo.entity.ApplyInfo;

/**
 * 申请借款信息表DAO接口
 * 
 * @author chenshaojia
 * @version 2016-03-01
 */
@MyBatisDao
public interface ApplyInfoDao extends CrudDao<ApplyInfo> {
	public ApplyInfo findApplyInfoByApplyNo(String applyNo);

	/**
	 * 批复信息保存后，更新借款申请信息中的合同金额
	 * 
	 * @param applyInfo
	 */
	public void updateContractAmount(ApplyInfo applyInfo);

	public List<ApplyInfo> checkDoubleInfo(ApplyInfo applyInfo);

	public Map<String, String> findGEDNeedCom(Map<String, String> param);

	public Map<String, String> findGEDNeedInfo(Map<String, String> param);
}
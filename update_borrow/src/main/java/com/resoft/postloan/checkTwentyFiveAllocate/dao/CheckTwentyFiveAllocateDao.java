package com.resoft.postloan.checkTwentyFiveAllocate.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.postloan.checkTwentyFiveAllocate.entity.CheckTwentyFiveAllocate;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 借后25日复核任务分配表DAO接口
 * 
 * @author yanwanmei
 * @version 2016-06-01
 */
@MyBatisDao
public interface CheckTwentyFiveAllocateDao extends CrudDao<CheckTwentyFiveAllocate> {
	public void saveList(@Param("checkTwentyFiveAllocates") List<CheckTwentyFiveAllocate> checkTwentyFiveAllocates);

	// 查询借后经理已下发任务
	public List<String> getContractNos(Map<String, Object> paraMap);

	public void updateCheckedTypeByContractNo(Map<String, String> param);

	public List<MyMap> getToAllocatedPLContract(MyMap paramMap);

	public List<MyMap> getHasAllocatedPLContract(MyMap paramMap);

	public List<MyMap> getAllHasAllocatedPLContract(MyMap paramMap);

	/**
	 * 查询符合日常检查条件的合同编号
	 * 
	 * @param params
	 * @return
	 */
	public List<String> findContractNosForDaily(Map<String, Object> params);

	/**
	 * 重新复核
	 */
	public void checkedAgain(String contractNo);

	/**
	 * 根据合同号查询复核信息
	 */
	public CheckTwentyFiveAllocate getTwentyFiveByContractNo(Map<String, Object> params);

	public List<MyMap> getSevenPLContract(MyMap paramMap);
}
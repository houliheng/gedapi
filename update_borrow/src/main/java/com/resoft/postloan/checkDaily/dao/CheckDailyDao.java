package com.resoft.postloan.checkDaily.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;

/**
 * 日常检查DAO接口
 * 
 * @author wuxi01
 * @version 2016-05-23
 */
@MyBatisDao
public interface CheckDailyDao extends CrudDao<CheckDaily> {
	/**
	 * 查询任务下发人员列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MyMap> findCheckDailyOperatorList(MyMap paramMap);

	/**
	 * 查询日常检查已分配、待检查、已检查列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MyMap> findCheckDailyList(MyMap paramMap);

	/**
	 * 查询日常检查待分配列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MyMap> findToAllocateList(MyMap paramMap);
	
	/**
	 * 查询日常检查待分配列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MyMap> findCheckDailyContractList(MyMap paramMap);

	/**
	 * 日常检查任务下发
	 * 
	 * @param params
	 */
	public void assign(CheckDailyAllocate checkDailyAllocate);

	/**
	 * 根据检查人ID查询合同编号List
	 * 
	 * @param checkedById
	 * @return
	 */
	public List<String> findContractNoList(Map<String, Object> params);

	/**
	 * 查询流程ID及合同编号
	 * 
	 * @param params
	 * @return
	 */
	public List<MyMap> findContractNoAndTaskId(Map<String, Object> params);

	/**
	 * 根据ID查询List
	 * 
	 * @param params
	 * @return
	 */
	public List<CheckDailyAllocate> findListByParams(Map<String, Object> params);

	/**
	 * 更新下一流程
	 * 
	 * @param checkDailyAllocate
	 */
	public void updateCheckDailyProc(CheckDaily checkDaily);

	/**
	 * 获取合同编号List
	 * 
	 * @param params
	 * @return
	 */
	public List<String> getContractNoList(Map<String, Object> params);

	/**
	 * 查询日常检查次数、上次检查时间、上次检查结果
	 * 
	 * @param contractNo
	 * @return
	 */
	public MyMap findCheckDailyCountNum(String contractNo);
}
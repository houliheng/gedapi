package com.resoft.postloan.debtColletion.dao;

import java.util.List;

import com.resoft.postloan.debtColletion.entity.TurnTask;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同转办统计DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface TurnTaskDao extends CrudDao<TurnTask> {
	/**
	 * 修改记录状态
	 * 
	 * @param turnTask
	 */
	public void updateTurnTask(TurnTask turnTask);
	
	/**
	 * 查询转办明细
	 */
	public List<TurnTask> getTurnTaskDetailList(TurnTask turnTasks);

}
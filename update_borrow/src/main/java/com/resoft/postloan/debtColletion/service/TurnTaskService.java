package com.resoft.postloan.debtColletion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.debtColletion.dao.TurnTaskDao;
import com.resoft.postloan.debtColletion.entity.TurnTask;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同转办统计Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class TurnTaskService extends CrudService<TurnTaskDao, TurnTask> {

	public TurnTask get(String id) {
		return super.get(id);
	}

	public List<TurnTask> findList(TurnTask turnTask) {
		return super.findList(turnTask);
	}

	public Page<TurnTask> findPage(Page<TurnTask> page, TurnTask turnTask) {
		return super.findPage(page, turnTask);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(TurnTask turnTask) {
		super.save(turnTask);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(TurnTask turnTask) {
		super.delete(turnTask);
	}

	/**
	 * 修改记录状态
	 * 
	 * @param turnTask
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateTurnTask(TurnTask turnTask) {
		this.dao.updateTurnTask(turnTask);
	}

	/**
	 * 查询转办明细
	 */
	@Transactional(value = "PL", readOnly = false)
	public List<TurnTask> getTurnTaskDetailList(TurnTask turnTasks) {
		return this.dao.getTurnTaskDetailList(turnTasks);
	}

	@Transactional(value = "PL", readOnly = false)
	public Page<TurnTask> getUserNames(Page<TurnTask> page) {
		List<TurnTask> turnTasks = page.getList();
		for (TurnTask turnTask : turnTasks) {
			User user = UserUtils.get(turnTask.getTurnPerson());
			if (user != null && user.getName() != null) {
				turnTask.setTurnPerson(user.getName());
			}
		}
		page.setList(turnTasks);
		return page;

	}
}
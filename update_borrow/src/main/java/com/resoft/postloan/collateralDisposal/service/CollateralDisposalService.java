package com.resoft.postloan.collateralDisposal.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.resoft.postloan.collateralDisposal.entity.CollateralDisposal;
import com.resoft.postloan.collateralDisposal.entity.TaskTempEntity;
import com.resoft.postloan.collateralDisposal.dao.CollateralDisposalDao;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;

/**
 * 借后抵押物处置Service
 * 
 * @author wangguodong
 * @version 2017-01-04
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class CollateralDisposalService extends CrudService<CollateralDisposalDao, CollateralDisposal> {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private TaskService taskService;

	public CollateralDisposal get(String id) {
		return super.get(id);
	}

	public List<CollateralDisposal> findList(CollateralDisposal collateralDisposal) {
		return super.findList(collateralDisposal);
	}

	public List<TaskTempEntity> findContractNoAndOverdueDataByProcInsId(String procInsId) {
		return this.dao.findContractNoAndOverdueDataByProcInsId(procInsId);
	}

	public Page<CollateralDisposal> findPage(Page<CollateralDisposal> page, CollateralDisposal collateralDisposal) {
		return super.findPage(page, collateralDisposal);
	}

	public Page<CollateralDisposal> findtoDoneList(Page<CollateralDisposal> page, CollateralDisposal collateralDisposal) {
		collateralDisposal.setPage(page);
		page.setList(this.dao.findToDoneList(collateralDisposal));
		return page;
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(CollateralDisposal collateralDisposal) {
		super.save(collateralDisposal);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(CollateralDisposal collateralDisposal) {
		super.delete(collateralDisposal);
	}

	@Transactional(value = "PL", readOnly = false)
	public void taskDown(ActTaskParam actTaskParam, CollateralDisposal collateralDisposal, String loginName) throws Exception {
		if (StringUtils.isNull(actTaskParam.getTaskDefKey())) {
			collateralDisposal.setDisposalStatus("0");
			save(collateralDisposal);
			String procInstId = actTaskService.startProcess(Constants.WORKFLOW_COLLATERAL_DISPOSAL, "pl_collateral_disposal", collateralDisposal.getId());
			collateralDisposal.setProcInsId(procInstId);
			save(collateralDisposal);
			Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
			actTaskService.doAssign(task.getId(), loginName);
		} else {
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】", "提交", null);
			if ("utask_dqrwxf".equals(actTaskParam.getTaskDefKey())) {
				Task task = taskService.createTaskQuery().processInstanceId(actTaskParam.getProcInstId()).singleResult();
				actTaskService.doAssign(task.getId(), loginName);
			}
		}
	}

	/**
	 * 查询指定群组和制定机构下的人
	 */
	public List<Map<String, Object>> findOrgUserList(Map<String, Object> params) {
		return this.dao.findOrgUserList(params);
	}

	/**
	 * 查询逾期未处理的任务
	 */
	public List<Map<String, Object>> findOverDueList(Map<String, Object> params){
		return this.dao.findOverDueList(params);
	}
	
}
package com.resoft.postloan.uploadFile.util;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.postloan.collateralDisposal.entity.CollateralDisposal;
import com.resoft.postloan.collateralDisposal.service.CollateralDisposalService;
import com.resoft.postloan.collateralDisposalConclusion.entity.CollateralDisposalConclusion;
import com.resoft.postloan.collateralDisposalConclusion.service.CollateralDisposalConclusionService;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.overdueHandle.dao.OverdueHandleConclusionDao;
import com.resoft.postloan.overdueHandle.dao.OverdueHandleDao;
import com.resoft.postloan.overdueHandle.entity.OverdueHandle;
import com.resoft.postloan.overdueHandle.entity.OverdueHandleConclusion;
import com.resoft.postloan.overdueHandle.service.OverdueHandleService;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

@Service("overdueHandle")
@DbType("pl.dbType")
@Transactional
public class TimerUtils {

	@Autowired
	private OverdueHandleService overdueHandleService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OverdueHandleDao overdueHandleDao;
	@Autowired
	private OverdueHandleConclusionDao overdueHandleConclusionDao;
	@Autowired
	private CollateralDisposalService collateralDisposalService;
	@Autowired
	private CollateralDisposalConclusionService collateralDisposalConclusionService;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 调用跑批存储
	 */
	public void callOverdueHandle(){
		//逾期50天以上90天以下区域未处理自动提交到大区处理 ，逾期90天自动提交到总部处理，需要判断是否已启动流程，到哪个环节再提交到大区处理
		MyMap params = new MyMap();
		params.put("lowerLimitDays", "50");
		List<MyMap> overdueListUnstarted = overdueHandleService.findListUnstarted(params);//未启动流程list
		if(overdueListUnstarted != null && overdueListUnstarted.size()>0){
			for(int i=0;i<overdueListUnstarted.size();i++){
				OverdueHandle overdueHandle = new OverdueHandle();
				overdueHandle.setContractNo(overdueListUnstarted.get(i).get("contractNo").toString());
				overdueHandle.setPeriodNum(overdueListUnstarted.get(i).get("periodNum").toString());
				overdueHandle.setHandleStatus("1");
				overdueHandle.preInsert();
				overdueHandleDao.insert(overdueHandle);
				try {
					//启动流程
					String procInstId = actTaskService.startProcess("gqcc_overdue_handle", "PL_OVERDUE_HANDLE", overdueHandle.getId());
					overdueHandle.setProcInsId(procInstId);
					overdueHandle.preUpdate();
					overdueHandleDao.update(overdueHandle);
					//大区下发提交
					List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
					String taskId = hisTasks.get(0).getId();
					taskService.claim(taskId, "admin");//管理员签收
					actTaskService.complete(taskId, procInstId, "【提交】" + "自动中止", "提交", null);
					//区域处理提交
					hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
					taskId = hisTasks.get(0).getId();
					taskService.claim(taskId, "admin");//管理员签收
					actTaskService.complete(taskId, procInstId, "【提交】" + "自动中止", "提交", null);
					OverdueHandleConclusion overdueHandleConclusion = new OverdueHandleConclusion();
					overdueHandleConclusion.setTaskDefKey("utask_qycl");
					overdueHandleConclusion.setContractNo(overdueHandle.getContractNo());
					overdueHandleConclusion.setPeriodNum(overdueHandle.getPeriodNum());
					overdueHandleConclusion.setConclusionDesc("自动中止");
					overdueHandleConclusion.preInsert();
					overdueHandleConclusionDao.insert(overdueHandleConclusion);
				} catch (Exception e) {
					logger.error("合同号"+overdueHandle.getContractNo()+"期数"+overdueHandle.getPeriodNum()+"自动提交逾期处理流程失败");
				}
			}
		}
		
		MyMap paramsDQXF = new MyMap();
		paramsDQXF.put("lowerLimitDays", "50");
		paramsDQXF.put("taskDefKey", "utask_dqxf");
		List<MyMap> overdueListDQXF = overdueHandleService.findListByOverdueDays(paramsDQXF);//处在大区下发环节的list
		if(overdueListDQXF != null && overdueListDQXF.size()>0){
			for(int i=0;i<overdueListDQXF.size();i++){
				try {
					//大区下发提交
					actTaskService.complete(overdueListDQXF.get(i).get("ID").toString(), overdueListDQXF.get(i).get("PROCESSINSTANCEID").toString(), "【提交】" + "自动中止", "提交", null);
					//区域处理提交
					List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(overdueListDQXF.get(i).get("PROCESSINSTANCEID").toString()).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
					String taskId = hisTasks.get(0).getId();
					taskService.claim(taskId, "admin");//管理员签收
					actTaskService.complete(taskId, overdueListDQXF.get(i).get("PROCESSINSTANCEID").toString(), "【提交】" + "自动中止", "提交", null);
					OverdueHandleConclusion overdueHandleConclusion = new OverdueHandleConclusion();
					overdueHandleConclusion.setTaskDefKey("utask_qycl");
					overdueHandleConclusion.setContractNo(overdueListDQXF.get(i).get("contractNo").toString());
					overdueHandleConclusion.setPeriodNum(overdueListDQXF.get(i).get("periodNum").toString());
					overdueHandleConclusion.setConclusionDesc("自动中止");
					overdueHandleConclusion.preInsert();
					overdueHandleConclusionDao.insert(overdueHandleConclusion);
				} catch (Exception e) {
					logger.error("合同号"+overdueListDQXF.get(i).get("contractNo").toString()+"期数"+overdueListDQXF.get(i).get("periodNum").toString()+"自动提交逾期处理流程失败");
				}
				
			}
		}
		
		MyMap paramsQYCL = new MyMap();
		paramsQYCL.put("lowerLimitDays", "50");
		paramsQYCL.put("taskDefKey", "utask_qycl");
		List<MyMap> overdueListQYCL = overdueHandleService.findListByOverdueDays(paramsQYCL);//处在区域处理环节的list
		if(overdueListQYCL != null && overdueListQYCL.size()>0){
			for(int i=0;i<overdueListQYCL.size();i++){
				try {
					//区域处理提交
					actTaskService.complete(overdueListQYCL.get(i).get("ID").toString(), overdueListQYCL.get(i).get("PROCESSINSTANCEID").toString(), "【提交】" + "自动中止", "提交", null);
					OverdueHandleConclusion overdueHandleConclusion = new OverdueHandleConclusion();
					overdueHandleConclusion.setTaskDefKey("utask_qycl");
					overdueHandleConclusion.setContractNo(overdueListQYCL.get(i).get("contractNo").toString());
					overdueHandleConclusion.setPeriodNum(overdueListQYCL.get(i).get("periodNum").toString());
					overdueHandleConclusion.setConclusionDesc("自动中止");
					overdueHandleConclusion.preInsert();
					overdueHandleConclusionDao.insert(overdueHandleConclusion);
				} catch (Exception e) {
					logger.error("合同号"+overdueListQYCL.get(i).get("contractNo").toString()+"期数"+overdueListQYCL.get(i).get("periodNum").toString()+"自动提交逾期处理流程失败");
				}
			}
		}
		
		MyMap paramsDQCL = new MyMap();
		paramsDQCL.put("lowerLimitDays", "90");
		/*paramsDQCL.put("upperLimitDays", "90");*/
		paramsDQCL.put("taskDefKey", "utask_dqcl");
		List<MyMap> overdueListDQCL = overdueHandleService.findListByOverdueDays(paramsDQCL);//处在大区处理环节的list
		if(overdueListDQCL != null && overdueListDQCL.size()>0){
			for(int i=0;i<overdueListDQCL.size();i++){
				try {
					//大区处理提交
					Task task = taskService.createTaskQuery().taskId(overdueListDQCL.get(i).get("ID").toString()).singleResult();
					if (task.getAssignee() == null) {//如果任务尚未签收
						actTaskService.claim(task.getId(), "admin");
					} 
					actTaskService.complete(overdueListDQCL.get(i).get("ID").toString(), overdueListDQCL.get(i).get("PROCESSINSTANCEID").toString(), "【提交】" + "自动中止", "提交", null);
					OverdueHandleConclusion overdueHandleConclusion = new OverdueHandleConclusion();
					overdueHandleConclusion.setTaskDefKey("utask_dqcl");
					overdueHandleConclusion.setContractNo(overdueListDQCL.get(i).get("contractNo").toString());
					overdueHandleConclusion.setPeriodNum(overdueListDQCL.get(i).get("periodNum").toString());
					overdueHandleConclusion.setConclusionDesc("自动中止");
					overdueHandleConclusion.preInsert();
					overdueHandleConclusionDao.insert(overdueHandleConclusion);
				} catch (Exception e) {
					logger.error("合同号"+overdueListDQCL.get(i).get("contractNo").toString()+"期数"+overdueListDQCL.get(i).get("periodNum").toString()+"自动提交逾期处理流程失败");
				}
			}
		}
	}
	

	/**
	 * 自动跑批（抵质押物处置） 逾期50天：未开始的任务，还停留在区域处理之前的任务 逾期90天：未开始的任务，停留在总部处理之前的正在进行的任务
	 */
	public void callCollateralDisposal() {
		// 查询超时未办的任务
		// 逾期50天
		Map<String, Object> params = Maps.newHashMap();
		params.put("overdueDays", "50");
		// 任务没有下发过
		list1(params);
		// 任务在大区任务下发
		list2(params);
		// 任务在区域处置
		list3(params);
		// 逾期90天
		Map<String, Object> param = Maps.newHashMap();
		param.put("overdueDays", "90");
		//任务在大区处置
		list4(param);
	}

	public void list1(Map<String, Object> params) {
		params.put("flag", "none");
		List<Map<String, Object>> list1 = collateralDisposalService.findOverDueList(params);
		if (list1 != null && list1.size() != 0) {
			for (Map<String, Object> pmap : list1) {
				CollateralDisposal collateralDisposal = new CollateralDisposal();
				collateralDisposal.setContractNo(pmap.get("contractNo").toString());
				collateralDisposal.setPeriodNum(pmap.get("periodNum").toString());
				collateralDisposal.setDisposalStatus("0");
				collateralDisposalService.save(collateralDisposal);
				String procInstId = actTaskService.startProcess(Constants.WORKFLOW_COLLATERAL_DISPOSAL, "pl_collateral_disposal", collateralDisposal.getId());
				collateralDisposal.setProcInsId(procInstId);
				collateralDisposal.preUpdate();
				collateralDisposalService.save(collateralDisposal);
				Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
				actTaskService.doAssign(task.getId(), "admin");
			}
		}
	}

	public void list2(Map<String, Object> params) {
		params.put("flag", "dqrwxfToDo");
		List<Map<String, Object>> list2 = collateralDisposalService.findOverDueList(params);
		if (list2 != null && list2.size() != 0) {
			for (Map<String, Object> pmap : list2) {
				Task task = taskService.createTaskQuery().taskId(pmap.get("taskId").toString()).singleResult();
				if (task.getAssignee() == null) {// 如果任务尚未签收
					actTaskService.claim(task.getId(), "admin");
				}
				actTaskService.complete(pmap.get("taskId").toString(), pmap.get("procInsId").toString(), "【提交】超时自动提交", "提交", null);
			}
		}
	}

	public void list3(Map<String, Object> params) {
		params.put("flag", "qyrwczToDo");
		List<Map<String, Object>> list3 = collateralDisposalService.findOverDueList(params);
		if (list3 != null && list3.size() != 0) {
			for (Map<String, Object> pmap : list3) {
				Task task = taskService.createTaskQuery().taskId(pmap.get("taskId").toString()).singleResult();
				if (task.getAssignee() == null) {// 如果任务尚未签收
					actTaskService.claim(task.getId(), "admin");
				}
				CollateralDisposalConclusion collateralDisposalConclusion = new CollateralDisposalConclusion();
				collateralDisposalConclusion.setContractNo(pmap.get("contractNo").toString());
				collateralDisposalConclusion.setPeriodNum(pmap.get("periodNum").toString());
				collateralDisposalConclusion.setTaskDefKey("utask_qyrwcz");
				collateralDisposalConclusion.setConclusionDesc("超时自动提交");
				collateralDisposalConclusion.setDealAllAmount("0");
				collateralDisposalConclusionService.save(collateralDisposalConclusion);
				actTaskService.complete(pmap.get("taskId").toString(), pmap.get("procInsId").toString(), "【提交】超时自动提交", "提交", null);
			}
		}
	}

	public void list4(Map<String, Object> params) {
		params.put("flag", "dqrwczToDo");
		List<Map<String, Object>> list4 = collateralDisposalService.findOverDueList(params);
		if (list4 != null && list4.size() != 0) {
			for (Map<String, Object> pmap : list4) {
				Task task = taskService.createTaskQuery().taskId(pmap.get("taskId").toString()).singleResult();
				if (task.getAssignee() == null) {// 如果任务尚未签收
					actTaskService.claim(task.getId(), "admin");
				}
				CollateralDisposalConclusion collateralDisposalConclusion = new CollateralDisposalConclusion();
				collateralDisposalConclusion.setContractNo(pmap.get("contractNo").toString());
				collateralDisposalConclusion.setPeriodNum(pmap.get("periodNum").toString());
				collateralDisposalConclusion.setTaskDefKey("utask_dqrwcz");
				collateralDisposalConclusion.setConclusionDesc("超时自动提交");
				collateralDisposalConclusion.setDealAllAmount("0");
				collateralDisposalConclusionService.save(collateralDisposalConclusion);
				actTaskService.complete(pmap.get("taskId").toString(), pmap.get("procInsId").toString(), "【提交】超时自动提交", "提交", null);
			}
		}
	}
	
	
}

package com.resoft.postloan.checkDaily.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.checkDaily.dao.CheckDailyAllocateDao;
import com.resoft.postloan.checkDaily.dao.CheckDailyDao;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.common.utils.DateUtils;
import com.resoft.postloan.debtColletion.dao.DebtCollectionDao;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 日常检查Service
 * 
 * @author wuxi01
 * @version 2016-05-23
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class CheckDailyService extends CrudService<CheckDailyDao, CheckDaily> {

	@Autowired
	private CheckDailyAllocateDao checkDailyAllocateDao;

	@Autowired
	private DebtCollectionDao debtCollectionDao;

	@Autowired
	private PLContractService plContractService;

	public CheckDaily get(String id) {
		return super.get(id);
	}

	public List<CheckDaily> findList(CheckDaily checkDaily) {
		return super.findList(checkDaily);
	}

	public Page<CheckDaily> findPage(Page<CheckDaily> page, CheckDaily checkDaily) {
		return super.findPage(page, checkDaily);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(CheckDailyAllocate checkDailyAllocate, String hiddenResult) {
		CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
		checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_BC);
		checkDaily.setCheckDailyResult(hiddenResult);
		super.dao.update(checkDaily);
		// 更新流程状态
		checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
		checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
		checkDailyAllocateDao.updateAllocateType(checkDailyAllocate);
	}

	@Transactional(value = "PL", readOnly = false)
	public void signContract(CheckDailyAllocate checkDailyAllocate, String hiddenResult) {
		CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
		checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_QSBZHT);
		checkDaily.setCheckDailyResult(hiddenResult);
		super.dao.update(checkDaily);
		// 更新流程状态
		checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
		checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
		checkDailyAllocateDao.updateAllocateType(checkDailyAllocate);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(CheckDaily checkDaily) {
		super.delete(checkDaily);
	}

	/**
	 * 日常检查提交流程
	 * 
	 * @param params
	 */
	@Transactional(value = "PL", readOnly = false)
	public void assign(CheckDailyAllocate checkDailyAllocate) throws Exception {
		CheckDaily checkDaily = new CheckDaily();
		checkDaily.setId(IdGen.uuid());
		checkDaily.setContractNo(checkDailyAllocate.getContractNo());
		checkDaily.setCheckedBy(checkDailyAllocate.getCheckedBy());
		super.dao.insert(checkDaily);
		checkDailyAllocate.setCheckDaily(checkDaily);
		super.dao.assign(checkDailyAllocate);

	}

	/**
	 * 查询日常检查已分配列表
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public Page<MyMap> findCheckDailyList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(super.dao.findCheckDailyList(paramMap));
		return page;
	}

	/**
	 * 查询日常检查待分配列表
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public Page<MyMap> findToAllocateList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(super.dao.findToAllocateList(paramMap));
		return page;
	}

	/**
	 * 查询日常检查待分配列表
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public Page<MyMap> findCheckDailyContractList(Page<MyMap> page, MyMap paramMap, List<String> contractNoCheckList) {
		paramMap.setPage(page);
		List<MyMap> resultList = new ArrayList<MyMap>();
		for (int i = 0; i < contractNoCheckList.size(); i++) {
			paramMap.put("correctContractNo", contractNoCheckList.get(i));
			List<MyMap> myList = super.dao.findCheckDailyContractList(paramMap);
			if (myList != null && myList.size() == 1) {
				MyMap myMap = myList.get(0);
				String contractNo = contractNoCheckList.get(i);
				MyMap resultMap = super.dao.findCheckDailyCountNum(contractNo);
				myMap.putAll(resultMap);
				resultList.add(myMap);
			}
		}
		page.setList(resultList);
		return page;
	}

	/**
	 * 查询任务下发人员列表
	 * 
	 * @param page
	 * @param paramMap
	 * @return
	 */
	public Page<MyMap> findCheckDailyOperatorList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(super.dao.findCheckDailyOperatorList(paramMap));
		return page;
	}

	/**
	 * 根据检查人ID查询合同编号List
	 * 
	 * @param checkedById
	 * @return
	 */
	public List<String> findContractNoList(Map<String, Object> params) {
		return super.dao.findContractNoList(params);
	}

	/**
	 * 查询流程ID及合同编号
	 * 
	 * @param params
	 * @return
	 */
	public List<MyMap> findContractNoAndTaskId(Map<String, Object> params) {
		return super.dao.findContractNoAndTaskId(params);
	}

	/**
	 * 根据ID查询List
	 * 
	 * @param params
	 * @return
	 */
	public List<CheckDailyAllocate> findListByParams(Map<String, Object> params) {
		return super.dao.findListByParams(params);
	}

	/**
	 * 更新检查状态（已检查/待检查）
	 * 
	 * @param checkDailyAllocate
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateAllocateType(CheckDailyAllocate checkDailyAllocate) {
		checkDailyAllocateDao.updateAllocateType(checkDailyAllocate);
	}

	/**
	 * 更新下一流程
	 * 
	 * @param checkDailyAllocate
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateCheckDailyProc(CheckDaily checkDaily) {
		super.dao.updateCheckDailyProc(checkDaily);
	}

	/**
	 * 特殊情况处理
	 * 
	 * @param taskId
	 */
	@Transactional(value = "PL", readOnly = false)
	public String specialCaseSave(CheckDailyAllocate checkDailyAllocate) throws Exception {
		String taskId = checkDailyAllocate.getId();
		String checkDailyResult = checkDailyAllocate.getCheckDaily().getCheckDailyResult();
		String checkDailyAdvice = checkDailyAllocate.getCheckDaily().getCheckDailyAdvice();
		Map<String, Object> params = Maps.newHashMap();
		params.put("taskId", taskId);
		List<CheckDailyAllocate> checkDailyAllocateList = this.findListByParams(params);
		if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
			checkDailyAllocate = checkDailyAllocateList.get(0);
			CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
			checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
			checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
			checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_TSQKCL);
			checkDaily.setCheckDailyResult(checkDailyResult);
			checkDaily.setCheckDailyAdvice(checkDailyAdvice);
			this.updateAllocateType(checkDailyAllocate);
			this.updateCheckDailyProc(checkDaily);
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 催收、法务
	 * 
	 * @param debtCollection
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = "PL", readOnly = false)
	public String collection(DebtCollection debtCollection, CheckDailyAllocate checkDailyAllocate, String hiddenResult) throws Exception {
		String taskId = checkDailyAllocate.getId();
		String checkDailyResult = hiddenResult;
		String checkDailyAdvice = checkDailyAllocate.getCheckDaily().getCheckDailyAdvice();
		String contractNo = debtCollection.getContractNo();
		DebtCollection hasDebtCollection = debtCollectionDao.get(debtCollection);
		if (hasDebtCollection != null) {
			return "该合同已进入催收环节，不需再次催收！";
		} else {
			List<PLContract> contractList = plContractService.findListByContractNo(contractNo);
			if (contractList != null && contractList.size() == 1) {
				PLContract contract = contractList.get(0);
				debtCollection.setContractAmount(contract.getContractAmount().toString());
				debtCollection.setCreateBy(UserUtils.getUser());
				debtCollection.setCustName(contract.getCustName());
				Map<String, Object> accStaContractStatusMap = plContractService.getAccStaContractStatusByContractNo(debtCollection.getContractNo());
				if (accStaContractStatusMap != null) {
					debtCollection.setOperateOrgId(accStaContractStatusMap.get("operateOrgId").toString());
					debtCollection.setTaOverdueTimes(accStaContractStatusMap.get("taTimes") + "");
					debtCollection.setCurrOverdueAmount(accStaContractStatusMap.get("currOverdueAmount").toString());
				}
				debtCollectionDao.insert(debtCollection);
			} else {
				return "fail";
			}

		}
		Map<String, Object> params = Maps.newHashMap();
		params.put("taskId", taskId);
		List<CheckDailyAllocate> checkDailyAllocateList = this.findListByParams(params);
		if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
			checkDailyAllocate = checkDailyAllocateList.get(0);
			CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
			checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
			checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
			checkDaily.setCheckDailyResult(checkDailyResult);
			checkDaily.setCheckDailyAdvice(checkDailyAdvice);
			if (Constants.DEBT_COLLECTION_PHONE.equals(debtCollection.getCurrCollectionType())) {// 催收
				checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_CSZ);
			} else if (Constants.DEBT_COLLECTION_LEGAL.equals(debtCollection.getCurrCollectionType())) {// 法务
				checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_FW);
			}
			this.updateAllocateType(checkDailyAllocate);
			this.updateCheckDailyProc(checkDaily);
			return "success";
		} else {
			return "fail";
		}
	}

	public List<String> getContractNoList(Map<String, Object> params) {
		return super.dao.getContractNoList(params);
	}

	public MyMap findCheckDailyCountNum(String contractNo) {
		return super.dao.findCheckDailyCountNum(contractNo);
	}
}
package com.resoft.postloan.debtColletion.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.checkDaily.service.CheckDailyService;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfoVO;
import com.resoft.postloan.checkTwentyFiveInfo.service.CheckTwentyFiveInfoService;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtCollectionFace.dao.DebtCollectionFaceDao;
import com.resoft.postloan.debtCollectionLegal.dao.DebtCollectionLegalDao;
import com.resoft.postloan.debtCollectionOut.dao.DebtCollectionOutDao;
import com.resoft.postloan.debtCollectionPhone.dao.DebtCollectionPhoneDao;
import com.resoft.postloan.debtColletion.dao.DebtCollectionDao;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.entity.TurnTask;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同带催收统计Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class DebtCollectionService extends CrudService<DebtCollectionDao, DebtCollection> {
	private static final Logger logger = LoggerFactory.getLogger(DebtCollectionService.class);
	@Autowired
	private TurnTaskService turnTaskService;
	@Autowired
	private CheckDailyService checkDailyService;
	@Autowired
	private DebtCollectionPhoneDao debtCollectionPhoneDao;
	@Autowired
	private DebtCollectionOutDao debtCollectionOutDao;
	@Autowired
	private DebtCollectionLegalDao debtCollectionLegalDao;
	@Autowired
	private DebtCollectionFaceDao debtCollectionFaceDao;
	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;
	@Autowired
	private CheckTwentyFiveInfoService checkTwentyFiveInfoService;

	public DebtCollection get(String id) {
		return super.get(id);
	}

	public List<DebtCollection> findList(DebtCollection debtCollection) {
		return super.findList(debtCollection);
	}

	public Page<DebtCollection> findPage(Page<DebtCollection> page, DebtCollection debtCollection) {
		return super.findPage(page, debtCollection);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(DebtCollection debtCollection) {
		super.save(debtCollection);
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(DebtCollection debtCollection) {
		super.delete(debtCollection);
	}

	@Transactional(value = "PL", readOnly = false)
	public String insert(DebtCollection debtCollection, CheckTwentyFiveInfoVO checkTwentyFiveInfoVO,String allocateId) {
		try {
			checkTwentyFiveInfoService.saveCheckTwentyFiveInfo(checkTwentyFiveInfoVO,allocateId,  debtCollection.getContractNo(), null);
			debtCollection.preInsert();
			super.dao.insert(debtCollection);
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("checkedType", Constants.DEBT_COLLECTION_LEGAL);
			paraMap.put("contractNo", debtCollection.getContractNo());
			checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(paraMap);
			return "true";
		} catch (Exception e) {
			logger.error("保存法务催收失败", e);
			return "false";
		}
	}

	@Transactional(value = "PL", readOnly = false)
	public void update(DebtCollection debtCollection) {
		super.dao.update(debtCollection);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("checkedType", Constants.DEBT_COLLECTION_LEGAL);
		paraMap.put("contractNo", debtCollection.getContractNo());
		checkTwentyFiveAllocateService.updateCheckedTypeByContractNo(paraMap);
	}

	@Transactional(value = "PL", readOnly = false)
	public void updateCollectionStatusAndType(Map<String, Object> params) {
		this.dao.updateCollectionStatusAndType(params);
	}

	@Transactional(value = "PL", readOnly = false)
	public void approveTask(DebtCollection debtCollection, String flag) {
		TurnTask turnTask = new TurnTask();
		turnTask = turnTaskService.get(debtCollection.getContractNo());
		if (Constants.DEBT_COLLECTION_TURN_SUCCESS.equals(flag)) {
			debtCollection.setCurrCollector(null);
			debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_DFP);
			this.dao.updateToDoDebtCollection(debtCollection);
			turnTask.setTurnStatus(Constants.DEBT_COLLECTION_TURN_SUCCESS);
			turnTaskService.updateTurnTask(turnTask);
		} else {
			// 打回
			turnTask.setTurnStatus(Constants.DEBT_COLLECTION_TURN_FAIL);
			turnTaskService.updateTurnTask(turnTask);
			debtCollection.setNewCollectionType(turnTask.getTurnBefore());
			debtCollection.setCurrCollectionFrom(turnTask.getCurrCollectionFrom());
			debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_YFPDCS);
			this.dao.updateToDoDebtCollection(debtCollection);
		}
	}

	/**
	 * 风险级别设置和转办的申请
	 * 
	 * @param toDoDebtCollection
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateToDoDebtCollection(DebtCollection debtCollection, String flag) {
		if (Constants.DEBT_COLLECTION__ZB.equals(flag)) {
			TurnTask turnTask = new TurnTask();
			turnTask.setDebtId(debtCollection.getId());
			turnTask.setContractNo(debtCollection.getContractNo());
			turnTask.setTurnDate(DateUtils.getDate());
			turnTask.setTurnPerson(debtCollection.getCurrCollector());
			turnTask.setCurrCollectionFrom(debtCollection.getCurrCollectionFrom());
			turnTask.setTurnBefore(debtCollection.getCurrCollectionType());
			turnTask.setTurnAfter(debtCollection.getNewCollectionType());
			turnTaskService.save(turnTask);
			if (Constants.DEBT_COLLECTION_PHONE.equals(debtCollection.getCurrCollectionType()) || Constants.DEBT_COLLECTION_NONE.equals(debtCollection.getCurrCollectionType())) {
				debtCollection.setCurrCollectionFrom(Constants.DEBT_COLLECTION__DHZB);
			} else if (Constants.DEBT_COLLECTION_FACE.equals(debtCollection.getCurrCollectionType())) {
				debtCollection.setCurrCollectionFrom(Constants.DEBT_COLLECTION__SMZB);
			} else if (Constants.DEBT_COLLECTION_OUT.equals(debtCollection.getCurrCollectionType())) {
				debtCollection.setCurrCollectionFrom(Constants.DEBT_COLLECTION__WBZB);
			}
		}
		this.dao.updateToDoDebtCollection(debtCollection);
	}

	/**
	 * 结束催收 改变状态
	 * 
	 * @param param
	 */
	@Transactional(value = "PL", readOnly = false)
	public void updateCurrCollectionStatus(Map<String, Object> param) {
		// 更新日常检查状态
		Map<String, Object> params = Maps.newHashMap();
		params.put("contractNo", param.get("contractNo"));
		params.put("checkDailyProc", Constants.CHECK_DAILY_PROC_CSZ);
		List<CheckDailyAllocate> checkDailyAllocateList = checkDailyService.findListByParams(params);
		if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
			CheckDailyAllocate checkDailyAllocate = checkDailyAllocateList.get(0);
			if (checkDailyAllocate != null) {
				CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
				if (checkDaily != null) {
					checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_CSJS);
					checkDailyService.updateCheckDailyProc(checkDaily);
				}
			}
		}
		int phoneCount = debtCollectionPhoneDao.getDataCountByContractNo(param.get("debtId").toString());
		int outCount = debtCollectionOutDao.getDataCountByContractNo(param.get("debtId").toString());
		int legalCount = debtCollectionLegalDao.getDataCountByContractNo(param.get("debtId").toString());
		int faceCount = debtCollectionFaceDao.getDataCountByContractNo(param.get("debtId").toString());
		int sum = phoneCount + outCount + legalCount + faceCount;
		param.put("debtId", param.get("debtId").toString());
		param.put("collectionTimes", sum);
		this.dao.updateCurrCollectionStatus(param);
	}

	@Transactional(value = "PL", readOnly = false)
	public Page<DebtCollection> getUserNames(Page<DebtCollection> page) {
		List<DebtCollection> debtCollections = page.getList();
		for (DebtCollection debtCollection : debtCollections) {
			User user = UserUtils.get(debtCollection.getCurrCollector());
			if (user != null && user.getName() != null) {
				debtCollection.setCurrCollector(user.getName());
			}
		}
		page.setList(debtCollections);
		return page;

	}
	
	public DebtCollection getDebtCollectionByLegalToClean(Map<String, Object> param){
		return this.dao.getDebtCollectionByLegalToClean(param);
	}
	
}
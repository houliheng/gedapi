package com.resoft.postloan.borrowNew.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.plApplyRegister.entity.PLApplyRegister;
import com.resoft.multds.credit.plApplyRegister.service.PLApplyRegisterService;
import com.resoft.multds.credit.plCustRemoveBind.service.PLCustRemoveBindService;
import com.resoft.postloan.borrowNew.entity.BorrowNew;
import com.resoft.postloan.borrowNew.dao.BorrowNewDao;
import com.resoft.postloan.checkDaily.dao.CheckDailyAllocateDao;
import com.resoft.postloan.checkDaily.dao.CheckDailyDao;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.common.utils.DateUtils;

/**
 * 借新还旧信息Service
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
@Service
@Transactional(value = "PL", readOnly = true)
public class BorrowNewService extends CrudService<BorrowNewDao, BorrowNew> {

	@Autowired
	private PLApplyRegisterService plApplyRegisterService;

	@Autowired
	private PLCustRemoveBindService plCustRemoveBindService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CheckDailyDao checkDailyDao;

	@Autowired
	private CheckDailyAllocateDao checkDailyAllocateDao;

	public BorrowNew get(String id) {
		return super.get(id);
	}

	public List<BorrowNew> findList(BorrowNew borrowNew) {
		return super.findList(borrowNew);
	}

	public Page<BorrowNew> findPage(Page<BorrowNew> page, BorrowNew borrowNew) {
		return super.findPage(page, borrowNew);
	}

	@Transactional(value = "PL", readOnly = false)
	public String saveBorrowNew(BorrowNew borrowNew, CheckDailyAllocate checkDailyAllocate, String applyFlag) throws Exception {
		// 保存借新还旧申请信息
		if ("apply".equals(applyFlag)) {// 申请
			borrowNew.setId(IdGen.uuid());
			super.dao.insert(borrowNew);
		} else if ("DQ".equals(applyFlag)) {
			borrowNew.setDQcheckDate(DateUtils.getCurrDateTime());
			super.dao.update(borrowNew);
		} else if ("ZB".equals(applyFlag)) {
			if(Constants.BORROW_NEW_STATUS_DQSHTG.equals(borrowNew.getCheckResult())){
				borrowNew.setCheckResult(Constants.BORROW_NEW_STATUS_ZBSHTG);
			}else{
				borrowNew.setCheckResult(Constants.BORROW_NEW_STATUS_ZBSHDH);
			}
			borrowNew.setZBcheckDate(DateUtils.getCurrDateTime());
			super.dao.update(borrowNew);
		}

		// 更新日常检查任务
		String taskId = checkDailyAllocate.getId();
		String contractNo = borrowNew.getContractNo();
		String checkDailyResult = checkDailyAllocate.getCheckDaily().getCheckDailyResult();
		String checkDailyAdvice = checkDailyAllocate.getCheckDaily().getCheckDailyAdvice();
		Map<String, Object> params = Maps.newHashMap();
		if ("apply".equals(applyFlag)) {// 申请
			params.put("taskId", taskId);
		} else if ("DQ".equals(applyFlag) || "ZB".equals(applyFlag)) {// 审核
			params.put("contractNo", contractNo);
			params.put("checkDailyProc", Constants.CHECK_DAILY_PROC_JXHJSHZ);
		}
		List<CheckDailyAllocate> checkDailyAllocateList = checkDailyDao.findListByParams(params);
		if (checkDailyAllocateList != null && checkDailyAllocateList.size() == 1) {
			checkDailyAllocate = checkDailyAllocateList.get(0);
			CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
			if (Constants.BORROW_NEW_STATUS_ZBSHTG.equals(borrowNew.getCheckResult())) {// 总部审核通过
				checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_JXHJTG);
				checkDailyDao.updateCheckDailyProc(checkDaily);
			} else if (Constants.BORROW_NEW_STATUS_ZBSHDH.equals(borrowNew.getCheckResult()) || Constants.BORROW_NEW_STATUS_DQSHDH.equals(borrowNew.getCheckResult())) {// 总部审核打回
				checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_JXHJJJ);
				checkDailyDao.updateCheckDailyProc(checkDaily);
			} else if (Constants.BORROW_NEW_STATUS_TOCHECK.equals(borrowNew.getCheckResult())) {// 申请借新还旧
				checkDaily.setCheckDailyProc(Constants.CHECK_DAILY_PROC_JXHJSHZ);
				checkDaily.setCheckDailyResult(checkDailyResult);
				checkDaily.setCheckDailyAdvice(checkDailyAdvice);
				checkDailyAllocate.setCheckedDate(DateUtils.getCurrDateTime());
				checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_DONE);
				checkDailyDao.updateCheckDailyProc(checkDaily);
				checkDailyAllocateDao.updateAllocateType(checkDailyAllocate);
			}
		} else {
			return "日常检查在办任务查询失败，可能是数据错误，请联系相关人员解决后再进行借新还旧操作！";
		}
		// 在CRE中新增进件
		if (Constants.BORROW_NEW_STATUS_ZBSHTG.equals(borrowNew.getCheckResult())) {
			List<PLApplyRegister> plApplyRegisterList = plApplyRegisterService.findApplyRegisterByContractNo(borrowNew.getContractNo());
			PLApplyRegister plApplyRegister = plApplyRegisterList.get(0);
			plApplyRegister.setApplyStatus(Constants.APPLY_STATUS_JXHJDJBC);
			plApplyRegister.setPlBorrowNewFlag(Constants.PL_BORROW_NEW_FLAG_Y);
			List<String> idNums = plContractService.findIdNumByContractNo(borrowNew.getContractNo());
			if (idNums != null && idNums.size() == 1) {
				Map<String, Object> flagMap = plCustRemoveBindService.isAlreadyBind(idNums.get(0));
				if (flagMap != null && "yes".equals(flagMap.get("flag"))) {
					User bindUser = (User) flagMap.get("bindUser");
					plApplyRegister.setCreateBy(bindUser);
					plApplyRegister.setRegisterName(bindUser.getLoginName());
					plApplyRegisterService.insert(plApplyRegister);
					return "success";
				} else {
					return "请先在信审系统中对证件号为【" + idNums.get(0) + "】的客户进行客户绑定后，再提交借新还旧！";
				}
			} else {
				return "合同信息中证件号有误，不能进行借新还旧，请核查！";
			}
		}
		return "success";
	}

	@Transactional(value = "PL", readOnly = false)
	public void delete(BorrowNew borrowNew) {
		super.delete(borrowNew);
	}

}
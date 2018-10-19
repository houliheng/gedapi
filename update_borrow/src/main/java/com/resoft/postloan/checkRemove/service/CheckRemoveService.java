package com.resoft.postloan.checkRemove.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.checkRemove.dao.CheckRemoveDao;
import com.resoft.postloan.checkRemove.entity.CheckRemove;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 核销计划Service
 * 
 * @author zhaohuakui
 * @version 2016-05-23
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class CheckRemoveService extends CrudService<CheckRemoveDao, CheckRemove> {

	@Autowired
	private PLContractService plContractService;

	public List<CheckRemove> findList(CheckRemove checkRemove) {
		return super.findList(checkRemove);
	}

	public Page<CheckRemove> findPage(Page<CheckRemove> page, CheckRemove checkRemove) {
		return super.findPage(page, checkRemove);
	}

	public CheckRemove findCheckRemoveByContractNo(CheckRemove checkRemove) {
		return this.dao.findCheckRemoveByContractNo(checkRemove);
	}

	@Transactional(value = "PL", readOnly = false)
	public void save(CheckRemove checkRemove) {
		super.save(checkRemove);
	}

	@Transactional(value = "PL", readOnly = false)
	public void approveCheckRemove(CheckRemove checkRemove, String flag) {
		CheckRemove remove = null;
		Map<String, Object> param = Maps.newHashMap();
		if (Constants.CHECK_REMOVE_ZBSP.equals(checkRemove.getZBorDQ())) {
			checkRemove.setApproverZBSH(UserUtils.getUser().getId());
			checkRemove.setApproveSuggestionZB(checkRemove.getApproveSuggestion());
			if (Constants.CHECK_REMOVE_RESULT_SHTG.equals(flag)) {
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_ZBSHTG);
				param.put("repayContractStatus", Constants.CONTRACT_STATE_YCZZ_HX);
				param.put("contractNo", checkRemove.getContractNo());
				plContractService.updateContractStatusByContractNo(param);
			} else if (Constants.CHECK_REMOVE_RESULT_SHDH.equals(flag)) {
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_ZBSHDH);
				remove = new CheckRemove();
				remove.setContractNo(checkRemove.getContractNo());
				remove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_WSQ);
				save(remove);
			} else {
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_SQWSH);
			}
		} else {
			checkRemove.setApproverDQSH(UserUtils.getUser().getId());
			checkRemove.setApproveSuggestionDQ(checkRemove.getApproveSuggestion());
			if (Constants.CHECK_REMOVE_RESULT_SHTG.equals(flag)) {
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_DQSHTG);
			} else if (Constants.CHECK_REMOVE_RESULT_SHDH.equals(flag)) {
				remove = new CheckRemove();
				remove.setContractNo(checkRemove.getContractNo());
				remove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_WSQ);
				save(remove);
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_DQSHDH);
			} else {
				checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_SQWSH);
			}
		}
		save(checkRemove);
	}
}
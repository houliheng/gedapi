package com.resoft.postloan.debtCollectionLegal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtCollectionLegal.dao.DebtCollectionLegalDao;
import com.resoft.postloan.debtCollectionLegal.entity.DebtCollectionLegal;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 法务催收Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class DebtCollectionLegalService extends CrudService<DebtCollectionLegalDao, DebtCollectionLegal> {

	@Autowired
	private DebtCollectionService debtCollectionService;

	@Autowired
	private PLContractService plContractService;

	public DebtCollectionLegal get(String id) {
		return super.get(id);
	}

	public List<DebtCollectionLegal> findList(DebtCollectionLegal debtCollectionLegal) {
		return super.findList(debtCollectionLegal);
	}

	public Page<DebtCollectionLegal> findPage(Page<DebtCollectionLegal> page, DebtCollectionLegal debtCollectionLegal) {
		return super.findPage(page, debtCollectionLegal);
	}

	@Transactional(readOnly = false)
	public void save(DebtCollectionLegal debtCollectionLegal) {
		super.save(debtCollectionLegal);
	}

	@Transactional(readOnly = false)
	public void delete(DebtCollectionLegal debtCollectionLegal) {
		super.delete(debtCollectionLegal);
	}

	@Transactional(value = "PL", readOnly = false)
	public void saveDebtCollectionLegal(DebtCollectionLegal debtCollectionLegal) {
		save(debtCollectionLegal);
		if (Constants.DEBT_COLLECTION_LEGAL_RESULT_HX.equals(debtCollectionLegal.getLegalResult())) {
			Map<String, Object> param = Maps.newHashMap();
			param.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_CSJS);
			param.put("contractNo", debtCollectionLegal.getContractNo());
			param.put("debtId", debtCollectionLegal.getDebtId());
			debtCollectionService.updateCurrCollectionStatus(param);
		}
	}

	@Transactional(value = "PL", readOnly = false)
	public void updateDebtCollectionLegal(DebtCollectionLegal debtCollectionLegal) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_CSJS);
		param.put("contractNo", debtCollectionLegal.getContractNo());
		param.put("debtId", debtCollectionLegal.getDebtId());
		debtCollectionService.updateCurrCollectionStatus(param);
		param.put("repayContractStatus", Constants.CONTRACT_STATE_YCZZ_FW);
		plContractService.updateContractStatusByContractNo(param);
	}
}
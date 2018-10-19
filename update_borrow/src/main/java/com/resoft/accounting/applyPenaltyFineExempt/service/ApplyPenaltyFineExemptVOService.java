package com.resoft.accounting.applyPenaltyFineExempt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.applyPenaltyFineExempt.dao.ApplyPenaltyFineExemptVODao;
import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExemptVO;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 违约金罚息减免VOService
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class ApplyPenaltyFineExemptVOService extends CrudService<ApplyPenaltyFineExemptVODao, ApplyPenaltyFineExemptVO> {

	public ApplyPenaltyFineExemptVO get(String id) {
		return super.get(id);
	}

	public List<ApplyPenaltyFineExemptVO> findList(ApplyPenaltyFineExemptVO applyPenaltyFineExemptVO) {
		return super.findList(applyPenaltyFineExemptVO);
	}

	public Page<ApplyPenaltyFineExemptVO> findPage(Page<ApplyPenaltyFineExemptVO> page, ApplyPenaltyFineExemptVO applyPenaltyFineExemptVO) {
		return super.findPage(page, applyPenaltyFineExemptVO);
	}

}
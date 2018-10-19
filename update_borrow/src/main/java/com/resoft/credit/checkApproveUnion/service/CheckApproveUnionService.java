package com.resoft.credit.checkApproveUnion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;

/**
 * 批复信息授信Service
 * @author wangguodong
 * @version 2016-12-14
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CheckApproveUnionService extends CrudService<CheckApproveUnionDao, CheckApproveUnion> {

	public CheckApproveUnion get(String id) {
		return super.get(id);
	}
	
	public List<CheckApproveUnion> findList(CheckApproveUnion checkApproveUnion) {
		return super.findList(checkApproveUnion);
	}
	
	public Page<CheckApproveUnion> findPage(Page<CheckApproveUnion> page, CheckApproveUnion checkApproveUnion) {
		return super.findPage(page, checkApproveUnion);
	}
	
	@Transactional(value = "CRE",readOnly = false)
	public void save(CheckApproveUnion checkApproveUnion) {
		super.save(checkApproveUnion);
	}
	
	@Transactional(value = "CRE",readOnly = false)
	public void delete(CheckApproveUnion checkApproveUnion) {
		super.delete(checkApproveUnion);
	}
	
	@Transactional(value = "CRE",readOnly = false)
	public List<CheckApproveUnion> getFreeStaffsByApplyNoOnCustInfo(String applyNo) {
		return this.dao.getFreeStaffsByApplyNoOnCustInfo(applyNo);
	}
	
	
	@Transactional(value = "CRE",readOnly = false)
	public List<CheckApproveUnion> getFreeStaffsByApplyNoOnCompany(String applyNo) {
		return this.dao.getFreeStaffsByApplyNoOnCompany(applyNo);
	}
	
	@Transactional(value = "CRE",readOnly = false)
	public List<CheckApproveUnion> getCheckApproveUnionByApplyNo(String applyNo) {
		return this.dao.getCheckApproveUnionByApplyNo(applyNo);
	}
	
	public List<CheckApproveUnion> findApproveListByApplyNo(String applyNo) {
		return super.dao.findApproveListByApplyNo(applyNo);
	}
	
	public List<CheckApproveUnion> findCustIdByApplyNo(String applyNo){
		return this.dao.findCustIdByApplyNo(applyNo);
	}

	

	public List<String> getCustIdByApplyNo(String applyNo) {
		return dao.getCustIdByApplyNo(applyNo);
	}

	public CheckApproveUnion getByApplyNoAndContract(String contractNo,String applyNo) {
		return dao.getByApplyNoAndContract(contractNo,applyNo);
	}

	public CheckApproveUnion getByApplyNoAndCustId(String applyNo, String custId) {
		return dao.getByApplyNoAndCustId(applyNo,custId);
	}
}
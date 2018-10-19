package com.resoft.credit.checkApproveUnion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.checkApproveUnion.dao.CompanyCustInfoRelatedDao;
import com.resoft.credit.checkApproveUnion.entity.CompanyCustInfoRelated;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 批复信息授信Service
 * 
 * @author wangguodong
 * @version 2016-12-14
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CompanyCustInfoRelatedService extends CrudService<CompanyCustInfoRelatedDao, CompanyCustInfoRelated> {

	public CompanyCustInfoRelated get(String id) {
		return super.get(id);
	}

	public List<CompanyCustInfoRelated> findList(CompanyCustInfoRelated companyCustInfoRelated) {
		return super.findList(companyCustInfoRelated);
	}

	public Page<CompanyCustInfoRelated> findPage(Page<CompanyCustInfoRelated> page, CompanyCustInfoRelated companyCustInfoRelated) {
		return super.findPage(page, companyCustInfoRelated);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(CompanyCustInfoRelated companyCustInfoRelated) {
		super.save(companyCustInfoRelated);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(CompanyCustInfoRelated companyCustInfoRelated) {
		super.delete(companyCustInfoRelated);
	}

	/**
	 * 根据批复Id获取关系数据
	 */
	public CompanyCustInfoRelated getCompanyCustInfoRelatedByApproId(String approId){
		return this.dao.getCompanyCustInfoRelatedByApproId(approId);
	}
	/**
	 * 根据applyNo删除数据
	 */
	void deleteCompanyCustInfoRelatedByApplyNo(String applyNo){
		this.dao.deleteCompanyCustInfoRelatedByApplyNo(applyNo);
	}
	
}
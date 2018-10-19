package com.resoft.accounting.cleanContract.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.cleanContract.dao.CleanContractDao;
import com.resoft.accounting.cleanContract.entity.CleanContract;
import com.resoft.accounting.common.utils.DateUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 数据处理Service
 * @author wangguodong
 * @version 2017-09-19
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class CleanContractService extends CrudService<CleanContractDao, CleanContract> {

	public CleanContract get(String id) {
		return super.get(id);
	}
	
	public List<CleanContract> findList(CleanContract cleanContract) {
		return super.findList(cleanContract);
	}
	
	public Page<CleanContract> findPage(Page<CleanContract> page, CleanContract cleanContract) {
		return super.findPage(page, cleanContract);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void save(CleanContract cleanContract) {
		String periodNum = cleanContract.getPeriodNum();
		if(periodNum == null){
			periodNum = "0";
		}
		CleanContract contract = new CleanContract();
		contract.setDataDt(DateUtils.dateToTimeString(new Date()));
		contract.setContractNo(cleanContract.getContractNo());
		contract.setPeriodNum(String.valueOf(Integer.parseInt(periodNum)+1));
		contract.setOrgLevel2(cleanContract.getOrgLevel2());
		contract.setOrgLevel3(cleanContract.getOrgLevel3());
		contract.setOrgLevel4(cleanContract.getOrgLevel4());
		super.save(contract);
	}
	

	@Transactional(value = "ACC", readOnly = false)
	public void updateCleanContract(CleanContract cleanContract) {
		super.dao.update(cleanContract);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void delete(CleanContract cleanContract) {
		super.delete(cleanContract);
	}
	
	public CleanContract findCleanContractByContractNoAndPeriodNum(String contractNo,String periodNum){
		return super.dao.findCleanContractByContractNoAndPeriodNum(contractNo,periodNum);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void clean(CleanContract cleanContract) {
		super.dao.cleanAccStaPenaltyFineExempt( cleanContract);
		super.dao.cleanAccApplyPenaltyFineExempt( cleanContract);
		super.dao.cleanAccStaRepayPlanStatus( cleanContract);
		super.dao.cleanAccDeductApply( cleanContract);
		super.dao.cleanAccDeductResult( cleanContract);
		super.dao.cleanAccRepayDetail( cleanContract);
		super.dao.cleanAccOverdueDetail( cleanContract);
		super.dao.cleanAccStaOverdueStatus( cleanContract);
		super.dao.cleanAccStaOverdueStatusTmp( cleanContract);
		super.dao.cleanAccApplyChangeRepay( cleanContract);
		super.dao.cleanAccReturnDeductResult( cleanContract);
	}
	
}
package com.resoft.credit.compensatory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.compensatory.dao.CompensatoryAccountDao;

/**
 * 代偿资金账户Service
 * @author jml
 * @version 2018-03-19
 */
@Service
@Transactional(readOnly = true)
public class CompensatoryAccountService extends CrudService<CompensatoryAccountDao, CompensatoryAccount> {
	
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CompensatoryAccountDao compensatoryAccountDao;
	@Autowired
	private ApplyRelationService applyRelationService;

	public CompensatoryAccount get(String id) {
		return super.get(id);
	}
	
	public List<CompensatoryAccount> findList(CompensatoryAccount compensatoryAccount) {
		return super.findList(compensatoryAccount);
	}
	
	public Page<CompensatoryAccount> findPage(Page<CompensatoryAccount> page, CompensatoryAccount compensatoryAccount) {
		return super.findPage(page, compensatoryAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(CompensatoryAccount compensatoryAccount) {
		super.save(compensatoryAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompensatoryAccount compensatoryAccount) {
		super.delete(compensatoryAccount);
	}

	public List<CompensatoryAccount> getMostPriopity() {
		return dao.getMostPriopity();
	}

	public List<CompensatoryAccount> getAllAccount() {
		return dao.getAllAccount();
	}

	public List<CompensatoryAccount> checkDouble(CompensatoryAccount compensatoryAccount) {
		return dao.checkDouble(compensatoryAccount);
	}

	public List<CompensatoryAccount> getOurselvesAccount(Contract contract) {
		List<CompensatoryAccount> compensatoryAccountList = new ArrayList<>();
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(contract.getApplyNo());
		if("gq_loan_under".equals(applyRegister.getProcDefKey())||Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())) {
			//非联合    
				//担保企业账户和     企业户没有custId暂时不进行代偿    查询的是企业个人户
			compensatoryAccountList =  compensatoryAccountDao.getGRComAccount(applyRegister.getApplyNo(),"6");
				//担保人账户
			List<CompensatoryAccount> compensatoryAccountList1 =  compensatoryAccountDao.getGRCustAccount(applyRegister.getApplyNo(),"3");
			compensatoryAccountList.addAll(compensatoryAccountList1);
		}else {
			//联合
			//批量借款企业的担保个人户    担保企业的个人户
			String companyId = contract.getCustId();
			//该合同类型
			ApplyRelation applyRelation = applyRelationService.getBatchRelationByCustId(applyRegister.getApplyNo(), companyId);
			if(applyRelation ==null) {//该合同绑定的是主借企业
				compensatoryAccountList =  compensatoryAccountDao.getGRComAccount(applyRegister.getApplyNo(),"6");
				//担保人账户
				List<CompensatoryAccount> compensatoryAccountList1 =  compensatoryAccountDao.getGRCustAccount(applyRegister.getApplyNo(),"3");
				compensatoryAccountList.addAll(compensatoryAccountList1);
			}else {
				//该合同是批量借款企业
				compensatoryAccountList = compensatoryAccountDao.getLHComAccount(applyRegister.getApplyNo(),companyId);
				List<CompensatoryAccount> compensatoryAccountList1 =  compensatoryAccountDao.getLHCustAccount(applyRegister.getApplyNo(),companyId);
				compensatoryAccountList.addAll(compensatoryAccountList1);
			}
		}
		return compensatoryAccountList;
	}

	public List<CompensatoryAccount> getDanBaoCompanyAccount(Contract contract) {
		String applyNo = contract.getApplyNo();
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(contract.getApplyNo());
		List<CompensatoryAccount> compensatoryAccountList = new ArrayList<>();
		String companyId = contract.getCustId();
		if("gq_loan_under".equals(applyRegister.getProcDefKey())||Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())) {
			//非联合
			compensatoryAccountList = compensatoryAccountDao.getGRDBComAccount(applyNo);
		}else{
			ApplyRelation applyRelation = applyRelationService.getBatchRelationByCustId(applyRegister.getApplyNo(), companyId);
			if(applyRelation ==null) {//该合同绑定的是主借企业
				compensatoryAccountList = compensatoryAccountDao.getGRDBComAccount(applyNo);
			}else {
				compensatoryAccountList = compensatoryAccountDao.getGRPJDBComAccount(applyNo,companyId);
			}
		}
		return compensatoryAccountList;
	}
	
}
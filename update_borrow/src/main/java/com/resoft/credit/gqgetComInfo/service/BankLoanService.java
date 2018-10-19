package com.resoft.credit.gqgetComInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import com.resoft.credit.gqgetComInfo.dao.BankLoanDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;


/**
 * 冠E通Service
 * @author yanwanmei
 * @version 2016-08-08
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class BankLoanService extends CrudService<BankLoanDao, BankLoan> {

	public BankLoan get(String id) {
		return super.get(id);
	}
	
	public List<BankLoan> findList(BankLoan bankLoan) {
		return super.findList(bankLoan);
	}
	
	public Page<BankLoan> findPage(Page<BankLoan> page, BankLoan bankLoan) {
		return super.findPage(page, bankLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(BankLoan bankLoan) {
		super.save(bankLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void updateByApplyNo(BankLoan bankLoan) {
		super.dao.updateByApplyNo(bankLoan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(BankLoan bankLoan) {
		super.delete(bankLoan);
	}
	
	public BankLoan getBankLoanByApplyNo(String applyNo){
		return super.dao.getBankLoanByApplyNo(applyNo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void insert(BankLoan bankLoan) {
		super.dao.insert(bankLoan);
	}
}
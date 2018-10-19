package com.resoft.credit.GedCompanyAccount.service;


import com.resoft.credit.GedCompanyAccount.dao.CreGedAccountCompanyDao;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 冠E贷企业账户信息Service
 * @author gsh
 * @version 2018-03-31
 */
@Service
@Transactional(readOnly = true)
public class CreGedAccountCompanyService extends CrudService<CreGedAccountCompanyDao, CreGedAccountCompany> {

	@Autowired
	CreGedAccountCompanyDao  creGedAccountCompanyDao;

	public CreGedAccountCompany get(String id) {
		return super.get(id);
	}
	
	public List<CreGedAccountCompany> findList(CreGedAccountCompany creGedAccountCompany) {
		return super.findList(creGedAccountCompany);
	}
	
	public Page<CreGedAccountCompany> findPage(Page<CreGedAccountCompany> page, CreGedAccountCompany creGedAccountCompany) {
		return super.findPage(page, creGedAccountCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(CreGedAccountCompany creGedAccountCompany) {
		dao.deleteByCustId(creGedAccountCompany.getCustId());
		creGedAccountCompany.preInsert();
		dao.insert(creGedAccountCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreGedAccountCompany creGedAccountCompany) {
		super.delete(creGedAccountCompany);
	}

	@Transactional(readOnly = false)
	public void updateSaveAccount(String status,String id,String reason) {
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss");
		dao.updateSaveAccount(status, id,sdf.format(new Date()),reason);
	}
	/**
	 * 查询企业账户信息
	 * @param unSocCreditNo
	 * @return
	 */
	public List<CreGedAccountCompany> findCompanyAccountBySocialCreditNo(String unSocCreditNo) {
		return this.dao.findCompanyAccountBySocialCreditNo(unSocCreditNo);
	}

	public List<String> checkCompanyAccount(String custId, String applyNo) {
		return dao.checkCompanyAccount(custId,  applyNo);
	}

	public List<String> checkBatchIsOpenAccount(String applyNo) {
		return dao.checkBatchIsOpenAccount( applyNo);
	}

	public List<String> checkDanBaoIsOpenAccount(String applyNo) {
		return dao. checkDanBaoIsOpenAccount( applyNo);
	}

	public CreGedAccountCompany getCompanyAccount(String approId) {
		return dao.getCompanyAccount(approId);
	}

	/**
	 * 查询开户审核列表
	 * */
	public Page<MyMap> findAccountCompanyList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(creGedAccountCompanyDao.findAccountCompanyList(paramMap));
		return page;
	}

	/**
	 * 查询开户审核列表
	 * */
	public Page<MyMap> findAccountCompanyDoneList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(creGedAccountCompanyDao.findAccountCompanyDoneList(paramMap));
		return page;
	}



	public CreGedAccountCompany findCompanyAccountByApproId(String approId) {

		return dao.findCompanyAccountByApproId(approId);
	}

	public CreGedAccountCompany selectCompanyAccountById(String id) {

		return dao.selectCompanyAccountById(id);
	}

	public String getCustByCompany(String contractNo){
		return dao.getCustByCompany(contractNo);
	}

	public CreGedAccountCompany getCompanyAccountByCustId(String custId) {

		return dao.getCompanyAccountByCustId(custId);
	}



	public CreGedAccountCompany getByApplyNo(String applyNo) {
		return dao.getByApplyNo(applyNo);
	}
}
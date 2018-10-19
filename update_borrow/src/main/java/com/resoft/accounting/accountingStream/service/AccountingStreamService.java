package com.resoft.accounting.accountingStream.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.accounting.accountingStream.entity.AccountingStream;
import com.resoft.accounting.accountingStream.dao.AccountingStreamDao;

/**
 * 账务流水Service
 * @author wangguodong
 * @version 2016-08-12
 */
@Service
@Transactional(readOnly = true)
public class AccountingStreamService extends CrudService<AccountingStreamDao, AccountingStream> {

	public AccountingStream get(String id) {
		return super.get(id);
	}
	
	public List<AccountingStream> findList(AccountingStream accountingStream) {
		return super.findList(accountingStream);
	}
	
	public Page<AccountingStream> findPage(Page<AccountingStream> page, AccountingStream accountingStream) {
		return super.findPage(page, accountingStream);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountingStream accountingStream) {
		super.save(accountingStream);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountingStream accountingStream) {
		super.delete(accountingStream);
	}
	
}
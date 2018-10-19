package com.resoft.credit.borrowInfoDisclose.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.borrowInfoDisclose.entity.CreBorrowInfoDisclose;
import com.resoft.credit.borrowInfoDisclose.dao.CreBorrowInfoDiscloseDao;

/**
 * 借后信息披露Service
 * @author zcl
 * @version 2018-03-09
 */
@Service
@Transactional(readOnly = true)
public class CreBorrowInfoDiscloseService extends CrudService<CreBorrowInfoDiscloseDao, CreBorrowInfoDisclose> {

	public CreBorrowInfoDisclose get(String id) {
		return super.get(id);
	}
	
	public List<CreBorrowInfoDisclose> findList(CreBorrowInfoDisclose creBorrowInfoDisclose) {
		return super.findList(creBorrowInfoDisclose);
	}
	
	public Page<CreBorrowInfoDisclose> findPage(Page<CreBorrowInfoDisclose> page, CreBorrowInfoDisclose creBorrowInfoDisclose) {
		return super.findPage(page, creBorrowInfoDisclose);
	}
	
	@Transactional(readOnly = false)
	public void save(CreBorrowInfoDisclose creBorrowInfoDisclose) {
		super.save(creBorrowInfoDisclose);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreBorrowInfoDisclose creBorrowInfoDisclose) {
		super.delete(creBorrowInfoDisclose);
	}
	
}
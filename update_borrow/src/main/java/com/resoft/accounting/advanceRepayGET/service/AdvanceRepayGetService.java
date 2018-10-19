package com.resoft.accounting.advanceRepayGET.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.advanceRepayGET.dao.AdvanceRepayGetDao;
import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 提前还款记录Service
 * @author jiangmenglun
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class AdvanceRepayGetService extends CrudService<AdvanceRepayGetDao, AdvanceRepayGet> {
	@Autowired
	private AdvanceRepayGetDao advanceRepayGetDao;
	
	public AdvanceRepayGet get(String id) {
		return super.get(id);
	}
	
	public List<AdvanceRepayGet> findList(AdvanceRepayGet advanceRepayGet) {
		return super.findList(advanceRepayGet);
	}
	
	public Page<AdvanceRepayGet> findPage(Page<AdvanceRepayGet> page, AdvanceRepayGet advanceRepayGet) {
		return super.findPage(page, advanceRepayGet);
	}
	
	@Transactional(readOnly = false)
	public void save(AdvanceRepayGet advanceRepayGet) {
		super.save(advanceRepayGet);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdvanceRepayGet advanceRepayGet) {
		super.delete(advanceRepayGet);
	}

	public String remainRepayPlan(AdvanceRepayGet advanceRepayGet) {
		return this.dao.remainRepayPlan(advanceRepayGet);
	}
	@Transactional(value = "ACC", readOnly = false)
	public String saveAdvanceRepay(AdvanceRepayGet advanceRepayGet) {
		String resq_code = Facade.facade.repayAdvanceGet(advanceRepayGet);
		if("0000".equals(resq_code)){//表示访问成功
			advanceRepayGet.setAdvanceStatus(resq_code);
			advanceRepayGet.preInsert();
			advanceRepayGetDao.insert(advanceRepayGet);
		}
		return resq_code;
	}

	public List<AdvanceRepayGet> getByContractNo(AdvanceRepayGet advanceRepayGet) {
		return advanceRepayGetDao.getByContractNo(advanceRepayGet);
	}
	
}
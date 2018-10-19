package com.resoft.credit.companyHistory.service;

import com.resoft.credit.companyHistory.dao.AccCompanyHistoryDao;
import com.resoft.credit.companyHistory.entity.AccCompanyHistory;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 企业开户历史表Service
 * @author weiruihong
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = true)
public class AccCompanyHistoryService extends CrudService<AccCompanyHistoryDao, AccCompanyHistory> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AccCompanyHistoryDao accCompanyHistoryDao;

	public AccCompanyHistory get(String id) {
		return super.get(id);
	}
	
	public List<AccCompanyHistory> findList(AccCompanyHistory accCompanyHistory) {
		return super.findList(accCompanyHistory);
	}
	
	public Page<AccCompanyHistory> findPage(Page<AccCompanyHistory> page, AccCompanyHistory accCompanyHistory) {
		return super.findPage(page, accCompanyHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(AccCompanyHistory accCompanyHistory) {
		super.save(accCompanyHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccCompanyHistory accCompanyHistory) {
		super.delete(accCompanyHistory);
	}

	public List<AccCompanyHistory> selectAccByaccountCompanyId(String companyId){
		return accCompanyHistoryDao.selectAccByaccountCompanyId(companyId);
	}

	@Transactional(readOnly = false)
	public void save(String id,int status,String operate,String reason){
		AccCompanyHistory accHistory=new AccCompanyHistory();
		accHistory.setAccountcompanyId(id);
		accHistory.setCreateTime(new Date());
		accHistory.setComment(reason);
		accHistory.setUserName(operateUserName(status));
		accHistory.setOperate(operate);
		accHistory.setStatus(String.valueOf(status));
		super.save(accHistory);
	}

	public String operateUserName(int status){
		String operUsername=UserUtils.getUser().getName();
		switch (status){
			case 6:
				operUsername=operUsername;
				break;
			case 7:
				operUsername="存管银行";
				break;
			case 8:
				operUsername="存管银行";
				break;
			case 9:
				operUsername="存管银行";
				break;
			case 10:
				operUsername=operUsername;
				break;
			default:
				operUsername="";
		}
		return operUsername;
	}

}
package com.resoft.Accoutinterface.gqget.server.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.Accoutinterface.gqget.server.dao.AccGqgetDao;
import com.resoft.Accoutinterface.gqget.server.entity.BankInfo;
import com.resoft.Accoutinterface.gqget.server.entity.GqgetCustInfo;
import com.thinkgem.jeesite.common.utils.IdGen;

@Service
@Transactional(value="ACC",readOnly = false)
public class AccGqgetService {

	@Autowired
	private AccGqgetDao accGqgetDao;

	public void saveGqgetInfo(GqgetCustInfo custInfo){
		custInfo.setId(IdGen.uuid());
		custInfo.setCreateDate(new Date());
		accGqgetDao.deletGqgetInfo(custInfo.getGqgetCustId());
		accGqgetDao.deletGqgetBankInfo(custInfo.getGqgetCustId());
		accGqgetDao.insertGqgetInfo(custInfo);
	}

	public void saveBankcardInfo(BankInfo bankInfo){
		bankInfo.setCreateDate(new Date());
		accGqgetDao.insertGqgetBankInfo(bankInfo);
	}
}

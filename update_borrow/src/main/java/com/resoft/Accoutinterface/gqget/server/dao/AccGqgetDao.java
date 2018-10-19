package com.resoft.Accoutinterface.gqget.server.dao;


import com.resoft.Accoutinterface.gqget.server.entity.BankInfo;
import com.resoft.Accoutinterface.gqget.server.entity.GqgetCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface AccGqgetDao{

	//客户信息同步接口
	public void insertGqgetInfo(GqgetCustInfo custInfo);
	public void insertGqgetBankInfo(BankInfo bankInfo);
	public void deletGqgetInfo(String gqgetCustId);
	public void deletGqgetBankInfo(String gqgetCustId);
}

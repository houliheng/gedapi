package com.resoft.outinterface.rest.ged.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.rest.ged.dao.GedClientDao;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetCarInfo;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetHouseInfo;
import com.resoft.outinterface.rest.ged.entity.GuanETInfo;
import com.resoft.outinterface.rest.ged.entity.LoanBankInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
* @author guoshaohua
* @version 2018年4月26日 下午2:09:38
* 
*/
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = false)
public class GedClientService {
	
	@Autowired
	private GedClientDao gedClientDao;
	
	public GuanETInfo queryGuanTInfoByApplyNo(String applyNo){
		return gedClientDao.queryGuanTInfoByApplyNo(applyNo);
	};
	
	public List<GqgetAssetCarInfo> queryGetCarInfos(String applyNo){
		return gedClientDao.queryGetCarInfos(applyNo);
	}
	
	public List<GqgetAssetHouseInfo> queryGqHouseInfo(String applyNo){
		return gedClientDao.queryGqHouseInfo(applyNo);
	}
	
	public LoanBankInfo queryLoanBank(String applyNo){
		return gedClientDao.queryLoanBank(applyNo);
	}
	
	public GuanETInfo queryGuanTInfoByApplyNoAndApproveId(String applyNo,String approveId){
		return gedClientDao.queryGuanTInfoByApplyNoAndApproveId(applyNo,approveId);
	}
	
	public List<GqgetAssetCarInfo> queryGetCarInfosUnion(String applyNo,String approveId){
		return gedClientDao.queryGetCarInfosUnion(applyNo,approveId);
	}
	
	public List<GqgetAssetHouseInfo> queryGqHouseInfoUnion(String applyNo,String approveId){
		return gedClientDao.queryGqHouseInfoUnion(applyNo,approveId);
	}
}

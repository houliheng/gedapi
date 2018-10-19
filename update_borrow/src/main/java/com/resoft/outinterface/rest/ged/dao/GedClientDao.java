package com.resoft.outinterface.rest.ged.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.outinterface.rest.ged.entity.GqgetAssetCarInfo;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetHouseInfo;
import com.resoft.outinterface.rest.ged.entity.GuanETInfo;
import com.resoft.outinterface.rest.ged.entity.LoanBankInfo;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
* @author guoshaohua
* @version 2018年4月26日 下午2:10:16
* 
*/
@MyBatisDao
public interface GedClientDao {
	public GuanETInfo queryGuanTInfoByApplyNo(@Param("applyNo") String applyNo);
	public List<GqgetAssetCarInfo> queryGetCarInfos(@Param("applyNo") String applyNo);
	public List<GqgetAssetHouseInfo> queryGqHouseInfo(@Param("applyNo") String applyNo);
	public LoanBankInfo queryLoanBank(String applyNo);
	public GuanETInfo queryGuanTInfoByApplyNoAndApproveId(@Param("applyNo") String applyNo,@Param("approveId") String approveId);
	public List<GqgetAssetCarInfo> queryGetCarInfosUnion(@Param("applyNo") String applyNo,@Param("approveId") String approveId);
	public List<GqgetAssetHouseInfo> queryGqHouseInfoUnion(@Param("applyNo") String applyNo,@Param("approveId") String approveId);
}

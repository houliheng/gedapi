package com.resoft.multds.postloan.pushdatatopl.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.mortgageCarEvaluateInfo.dao.MortgageCarEvaluateInfoDao;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.multds.postloan.pushdatatopl.dao.PlCreditAssetDao;
import com.resoft.multds.postloan.pushdatatopl.dao.PlMortgageCarEvaluateInfoDao;
import com.resoft.multds.postloan.pushdatatopl.dao.PlMortgageCarInfoDao;
import com.resoft.multds.postloan.pushdatatopl.dao.PlMortgageHouseInfoDao;
import com.resoft.multds.postloan.pushdatatopl.dao.PlMortgageOtherInfoDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
@Service @DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class PushDataToPlService  {
	@Autowired
	private   PlMortgageOtherInfoDao plMortgageOtherInfoDao;
	@Autowired
	private   PlMortgageHouseInfoDao plMortgageHouseInfoDao;
	@Autowired
	private   PlMortgageCarInfoDao plMortgageCarInfoDao;
	@Autowired
	private   PlMortgageCarEvaluateInfoDao plMortgageCarEvaluateInfoDao;
	@Autowired
	private   PlCreditAssetDao plCreditAssetDao;
	@Autowired
	private   MortgageCarEvaluateInfoDao mortgageCarEvaluateInfoDao;
	@Transactional(value="PL",readOnly = false)
	public  void pushDataToPl(List<MortgageOtherInfo> mortgageOtherInfoList,List<MortgageHouseInfo> mortgageHouseInfoList,List<MortgageCarInfo> mortgageCarInfoList,List<CreditAssets> creditAssetsList) {
		
		if (mortgageOtherInfoList.size()>0){
			//批量插入其他抵质押物信息
			plMortgageOtherInfoDao.insertOtherDataToPl(mortgageOtherInfoList);
		}
		if (mortgageHouseInfoList.size()>0){
			//批量插入房屋抵质押物信息到借后
			plMortgageHouseInfoDao.insertHouseDataToPl(mortgageHouseInfoList);
		}
		if (mortgageCarInfoList.size()>0){
			//批量插入车辆抵质押物信息到借后
			plMortgageCarInfoDao.insertCarDataToPl(mortgageCarInfoList);

			List idList =new ArrayList();
			for (int i=0;i<mortgageCarInfoList.size();i++){
				idList.add(mortgageCarInfoList.get(i).getId());
			}
			List<MortgageCarEvaluateInfo> mortgageCarEvaluaInfolist = mortgageCarEvaluateInfoDao.findListByCarIdList(idList);
			// 批量插入车辆评估信息抵质押物信息到借后
			if (mortgageCarEvaluaInfolist.size()>0){
				plMortgageCarEvaluateInfoDao.insertCarEvaluateDataToPl(mortgageCarEvaluaInfolist);
			}
		}
		if (creditAssetsList.size()>0){
			//批量插入资产信息到借后
			plCreditAssetDao.insertCreditAssetToPl(creditAssetsList);
		}
	}
}

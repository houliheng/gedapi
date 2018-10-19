package com.resoft.outinterface.SV.client3.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.outinterface.SV.client2.AffiliatedEnterpriseData;
import com.resoft.outinterface.SV.client2.CompanyBasicInformation;
import com.resoft.outinterface.SV.client2.PersonalBasicData;
import com.resoft.outinterface.SV.client2.PersonalFamilyData;
import com.resoft.outinterface.SV.client2.PledgeHouseData;
import com.resoft.outinterface.SV.client2.PledgeVehicleInformation;
import com.resoft.outinterface.SV.client2.ResidenceInformation;
import com.resoft.outinterface.SV.client2.WorkInformation;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface InformationMatchClientDao {
	/**
	 * 查询企业基础信息
	 */
	List<CompanyBasicInformation> findCompanyBasicInformationList(Map<String, Object> params);

	/**
	 * 查询关联企业列表
	 */
	List<AffiliatedEnterpriseData> findAffiliatedEnterpriseDataList(Map<String, Object> params);

	/**
	 * 查询个人基础信息
	 */
	List<PersonalBasicData> findPersonalBasicDataList(Map<String, Object> params);

	/**
	 * 查询家庭信息
	 */
	List<PersonalFamilyData> findPersonalFamilyDataList(Map<String, Object> params);

	/**
	 * 查询居住地信息
	 */
	List<ResidenceInformation> findResidenceInformationList(Map<String, Object> params);

	/**
	 * 查询工作信息
	 */
	List<WorkInformation> findWorkInformationList(Map<String, Object> params);

	/**
	 * 查询质押车辆信息
	 */
	List<PledgeVehicleInformation> findPledgeVehicleInformationList(Map<String, Object> params);

	/**
	 * 查询抵押房产信息
	 */
	List<PledgeHouseData> findPledgeHouseDataList(Map<String, Object> params);

//	
//	MortgageCarEvaluateInfo findMortgageCarEvaluateInfoByCarId(String carId);
//
//	MortgageHouseInfo getMortgageHouseInfoById(String id);
//	
//	void saveMortgageCarEvaluateInfo(MortgageCarEvaluateInfo mortgageCarEvaluateInfo);
//
//	void updateMortgageCarEvaluateInfo(MortgageCarEvaluateInfo mortgageCarEvaluateInfo);
//	
//	void updateMortgageHouseInfo(MortgageHouseInfo mortgageHouseInfo);
	
}

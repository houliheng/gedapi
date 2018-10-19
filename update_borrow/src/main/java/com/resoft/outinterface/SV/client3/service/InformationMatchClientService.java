package com.resoft.outinterface.SV.client3.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.outinterface.SV.client2.AffiliatedEnterpriseData;
import com.resoft.outinterface.SV.client2.CompanyBasicInformation;
import com.resoft.outinterface.SV.client2.PersonalBasicData;
import com.resoft.outinterface.SV.client2.PersonalFamilyData;
import com.resoft.outinterface.SV.client2.PledgeHouseData;
import com.resoft.outinterface.SV.client2.PledgeVehicleInformation;
import com.resoft.outinterface.SV.client2.ResidenceInformation;
import com.resoft.outinterface.SV.client2.WorkInformation;
import com.resoft.outinterface.SV.client2.WorkOrderTransferVO;
import com.resoft.outinterface.SV.client3.dao.InformationMatchClientDao;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

@Service(value = "informationMatchClientService")
@DbType("cre.dbType")
@Transactional(value = "CRE")
public class InformationMatchClientService {

	@Autowired
	private InformationMatchClientDao informationMatchClientDao;

	public WorkOrderTransferVO getDataToSVForInformationMatch(String applyNo) throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		// 查询企业基础信息
		List<CompanyBasicInformation> companyBasicInformations = informationMatchClientDao.findCompanyBasicInformationList(params);
		// 查询关联企业列表
		List<AffiliatedEnterpriseData> affiliatedEnterpriseDatas = informationMatchClientDao.findAffiliatedEnterpriseDataList(params);
		// 查询个人基础信息
		List<PersonalBasicData> personalBasicDatas = informationMatchClientDao.findPersonalBasicDataList(params);
		// 查询家庭信息
		List<PersonalFamilyData> personalFamilyDatas = informationMatchClientDao.findPersonalFamilyDataList(params);
		// 查询居住地信息
		List<ResidenceInformation> residenceInformations = informationMatchClientDao.findResidenceInformationList(params);
		// 查查询工作信息
		// List<WorkInformation> workInformations =
		// informationMatchClientDao.findWorkInformationList(params);
		// 查询质押车辆信息
		List<PledgeVehicleInformation> pledgeVehicleInformations = informationMatchClientDao.findPledgeVehicleInformationList(params);
		// 查询抵押房产信息
		List<PledgeHouseData> pledgeHouseDatas = informationMatchClientDao.findPledgeHouseDataList(params);

		WorkOrderTransferVO client = new WorkOrderTransferVO();

		client.setApplyNo(applyNo);
		client.setAffiliatedEnterpriseData(affiliatedEnterpriseDatas);
		client.setCompanyBasicInformation(companyBasicInformations);
		client.setPersonalBasicData(personalBasicDatas);
		client.setPersonalFamilyData(personalFamilyDatas);
		client.setPledgeHouseData(pledgeHouseDatas);
		client.setPledgeVehicleInformation(pledgeVehicleInformations);
		client.setResidenceInformation(residenceInformations);
		// client.setWorkInformations(workInformations);

		return client;
	}

}

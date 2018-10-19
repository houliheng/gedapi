package com.resoft.outinterface.SV.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import resoft.crms.util.StringUtil;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.outinterface.SV.client.entry.request.SurveyInfoDto;
import com.resoft.outinterface.SV.client.entry.request.WorkOrderDto;
import com.resoft.outinterface.SV.dao.WorkOrderDao;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service(value = "workOrderService")
@DbType("cre.dbType")
@Transactional(value = "CRE")
public class WorkOrderService {

	@Autowired
	private WorkOrderDao workOrderDao;

	/**
	 * 查找SV工单对象
	 *
	 * @param applyNo
	 * @return workOrderDto
	 */
	public WorkOrderDto getWorkOrderDto(String applyNo) {
		// 工单基本信息
		WorkOrderDto workOrderDto = workOrderDao.getWorkOrderDto(applyNo);
		workOrderDto.setAppNo(applyNo);
		workOrderDto.setClosingDate(Constants.CLOSING_DATE);
		Map<String, String> org =workOrderDao.findOrg(applyNo);
		workOrderDto.setOrgNo(org.get("orgNo"));
		workOrderDto.setUserCode(org.get("orgId"));
		workOrderDto.setProductNo("1");

		// 查询进调对象
		List<SurveyInfoDto> surveyInfoDtoList = new ArrayList<SurveyInfoDto>();
		// 条件
		Map<String, String> params = Maps.newConcurrentMap();
		// 主借人
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJR);
		params.put("process", Constants.PROCESS_ZJR);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_ZJR_ZHIHUANDAI);
		}
		params.put("isMaster", Constants.YES_NO_Y);
		List<SurveyInfoDto> zjrList = workOrderDao.findCust(params);
		surveyInfoDtoList.addAll(zjrList);

		Map<String,String> mortage = workOrderDao.getMortage(applyNo);

		// 主借企业
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJQY);
		params.put("process", Constants.PROCESS_ZJQY_NO);
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> zjqyList = workOrderDao.findCompany(params);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			for (SurveyInfoDto sid : zjqyList) {
				sid.setProcess(Constants.PROCESS_ZJQY_ZHIHUANDAI);
			}
		}else{
			for (SurveyInfoDto sid : zjqyList) {
				if ((Constants.EARNINGS_YES).equals(sid.getRemark())) {
					sid.setProcess(Constants.PROCESS_ZJQY_YES);
				}
			}
		}
		surveyInfoDtoList.addAll(zjqyList);

		// 共借人
//		params.clear();
//		params.put("applyNo", applyNo);
//		params.put("roleType", Constants.ROLE_TYPE_GJR);
//		params.put("process", Constants.PROCESS_GJR);
//		params.put("isMaster", Constants.YES_NO_N);
//		List<SurveyInfoDto> gjrList = workOrderDao.findCust(params);
//		surveyInfoDtoList.addAll(gjrList);

		// 配偶
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_MATE);
		params.put("process", Constants.PROCESS_DBR);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_DBR_ZHIHUANDAI);
		}
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> mateList = workOrderDao.findCust(params);
		//surveyInfoDtoList.addAll(mateList);

		// 担保人
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBR);
		params.put("isMaster", Constants.YES_NO_N);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_DBR_ZHIHUANDAI);
			List<SurveyInfoDto> dbrList = workOrderDao.findCust(params);
			for (SurveyInfoDto sfd : dbrList) {
				if ((Constants.CONTACT_RELATIONS_CAIWU).equals(sfd.getMasterRelation())) {
					sfd.setProcess(Constants.PROCESS_DBR_ZHIHUANDAI_CAIWU);
				}
				if(mateList.size()>0&&sfd.getWorkId().equals(mateList.get(0).getWorkId())){
					sfd.setProcess(Constants.PROCESS_MATE);
				}
			}
			surveyInfoDtoList.addAll(dbrList);
		}else{
			params.put("process", Constants.PROCESS_DBR);
			List<SurveyInfoDto> dbrList = workOrderDao.findCust(params);
			for (SurveyInfoDto sfd : dbrList) {
				if ((Constants.CONTACT_RELATIONS_CAIWU).equals(sfd.getMasterRelation())) {
					sfd.setProcess(Constants.PROCESS_DBR_CAIWU);
				}
				if(mateList.size()>0&&sfd.getWorkId().equals(mateList.get(0).getWorkId())){
					sfd.setProcess(Constants.PROCESS_MATE);
				}
			}
			surveyInfoDtoList.addAll(dbrList);
		}

		// 担保企业
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		params.put("process", Constants.PROCESS_DBQY);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_DBQY_ZHIHUANDAI);
		}
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> dbqyList = workOrderDao.findCompany(params);
		surveyInfoDtoList.addAll(dbqyList);

		// 房产
		params.clear();
		params.put("applyNo", applyNo);
		params.put("process", Constants.PROCESS_FC);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_FC_ZHIHUANDAI);
		}
		params.put("isMaster", Constants.YES_NO_N);
		if(!StringUtils.isEmpty(mortage.get("phoneNo"))){
			params.put("phoneNo", mortage.get("phoneNo"));
			List<SurveyInfoDto> fcList = workOrderDao.findHouse(params);
			surveyInfoDtoList.addAll(fcList);
		}

		// 车辆
		params.clear();
		params.put("applyNo", applyNo);
		params.put("process", Constants.PROCESS_CL);
		if ((Constants.CHANNEL_SOURCE_ZHIHUANDAI).equals(workOrderDto.getRemarks())) {
			params.put("process", Constants.PROCESS_CL_ZHIHUANDAI);
		}
		params.put("isMaster", Constants.YES_NO_N);
		if(!StringUtils.isEmpty(mortage.get("phoneNo"))){
			params.put("phoneNo", mortage.get("phoneNo"));
			List<SurveyInfoDto> clList = workOrderDao.findCar(params);

			for (SurveyInfoDto sf : clList) {
				sf.setAddress(mortage.get("address"));
				sf.setCity(mortage.get("city"));
				sf.setDistrict(mortage.get("district"));
				sf.setProvinces(mortage.get("provinces"));
			}
			surveyInfoDtoList.addAll(clList);
		}

		workOrderDto.setSurveyInfoDto(surveyInfoDtoList);
		String lable =DictUtils.getDictLabel(workOrderDto.getCustomerNo(), "SV_PLATFORM", "冠群驰骋投资管理(北京)有限公司");
		workOrderDto.setCustomerNo(lable);
		return workOrderDto;
	}

	public void getWorkOrderUpdate(String applyNo) {

		// 条件
		Map<String, String> params = Maps.newConcurrentMap();
		// 主借人
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJR);
		params.put("process", Constants.PROCESS_ZJR);
		params.put("isMaster", Constants.YES_NO_Y);
		List<SurveyInfoDto> zjrList = workOrderDao.findCust(params);
		if(zjrList.size()>0)
		workOrderDao.updateRelation(zjrList, applyNo);
		// 主借企业
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJQY);
		params.put("process", Constants.PROCESS_ZJQY_NO);
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> zjqyList = workOrderDao.findCompany(params);
		if(zjqyList.size()>0)
		workOrderDao.updateRelation(zjqyList, applyNo);
		// 担保人
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBR);
		params.put("process", Constants.PROCESS_DBR);
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> dbrList = workOrderDao.findCust(params);
		if(dbrList.size()>0)
		workOrderDao.updateRelation(dbrList, applyNo);
		// 担保企业
		params.clear();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		params.put("process", Constants.PROCESS_DBQY);
		params.put("isMaster", Constants.YES_NO_N);
		List<SurveyInfoDto> dbqyList = workOrderDao.findCompany(params);
		if(dbqyList.size()>0)
		workOrderDao.updateRelation(dbqyList, applyNo);
		// 房产
		params.clear();
		params.put("applyNo", applyNo);
		params.put("process", Constants.PROCESS_FC);
		params.put("isMaster", Constants.YES_NO_N);
		params.put("phoneNo", "110");
		List<SurveyInfoDto> fcList = workOrderDao.findHouse(params);
		if(fcList.size()>0)
		workOrderDao.updateHouse(fcList);
		// 车辆
		params.clear();
		params.put("applyNo", applyNo);
		params.put("process", Constants.PROCESS_CL);
		params.put("isMaster", Constants.YES_NO_N);
		params.put("phoneNo", "110");
		List<SurveyInfoDto> clList = workOrderDao.findCar(params);
		if(clList.size()>0)
		workOrderDao.updateCar(clList);
	}
}

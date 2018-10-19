package com.resoft.outinterface.SV.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.outinterface.SV.client.entry.request.SurveyInfoDto;
import com.resoft.outinterface.SV.client.entry.request.WorkOrderDto;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WorkOrderDao {

	/**
	 * 查询工单基本信息
	 * @param applyNo
	 * @return
	 */
	public WorkOrderDto getWorkOrderDto(String applyNo);

	/**
	 * 查询相关人
	 * @param params
	 * @return
	 */
	public List<SurveyInfoDto> findCust(Map<String, String> params);

	public void updateRelation(@Param("relationInfoList") List<SurveyInfoDto> relation, @Param("applyNo")String applyNo);

	/**
	 * 查询相关企业
	 * @param params
	 * @return
	 */
	public List<SurveyInfoDto> findCompany(Map<String, String> params);

	/**
	 * 查询房产信息
	 * @param params
	 * @return
	 */
	public List<SurveyInfoDto> findHouse(Map<String, String> params);

	public void updateHouse(@Param("houseInfoList") List<SurveyInfoDto> houseInfo);

	/**
	 * 查询车辆信息
	 * @param params
	 * @return
	 */
	public List<SurveyInfoDto> findCar(Map<String, String> params);

	public void updateCar(@Param("carInfoList") List<SurveyInfoDto> CarInfo);

	public Map<String, String> findOrg(String applyNo);

	public Map<String,String> getMortage(String applyNo);

}

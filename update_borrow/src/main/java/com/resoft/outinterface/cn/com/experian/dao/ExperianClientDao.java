package com.resoft.outinterface.cn.com.experian.dao;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;

import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.AppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.CoAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.FirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.GuAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.HouseInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.LoanInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCCoAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCFirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCGuAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.VehicleInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.VehiclesInfo;
import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponseHeader;
import com.resoft.outinterface.cn.com.experian.entry.response.Resinfo;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ExperianClientDao{
	public  ApplyInfo getApplyInfo(String applyNo);
	//贷款信息
	public LoanInfo getLoanInfoByApplyNo(String applyNo);
	//贷款信息-抵押率
	public String getPledgeByApplyNo(String applyNo);
	//贷款信息-月还款额
	public LoanInfo getLoanInfoRefundMonthByApplyNo(String applyNo);
	//共借人信息
	public List<CoAppInfo> getCoAppInfoByApplyNo(String applyNo);
	//借款人信息
	public AppInfo getAppInfoByApplyNo(String applyNo);
	//借款人信息-配偶年龄
	public String getCoupleAgeByApplyNoAndRoles(Map<String,String> params);
	//担保人信息
	public List<GuAppInfo> getGuAppInfoByApplyNo(String applyNo);
	//征信共借人信息
	public List<PBOCCoAppInfo> getPBOCCoAppByApplyNo(String applyNo);
	//贷款账户数,是否有贷款信息
	public Map<String,Object> getPbocLoanNumAndpbocIsHavePLByApplyNoAndRoleType(Map<String,String> params);
	//企业信息-114查证
	public String getQueryResult114ByApplyNoAndRoles(Map<String,String> params);
	//企业信息-百度网查结果
	public String getPhoneResultBDByApplyNoAndRoles(Map<String,String> params);
	//征信借款人信息
	public PBOCAppInfo getPBOCAppInfoByApplyNo(String applyNo);
	//征信中企业信息
	public List<PBOCFirmInfo> getPBOCFirmInfoByApplyNo(String applyNo);
	//征信中企业信息，贷款账户数,是否有贷款信息
	public PBOCFirmInfo getPBOCFirmNumInfoByApplyNo(String applyNo);
	//征信中担保人信息
	public List<PBOCGuAppInfo> getPBOCGuAppInfoByApplyNo(String applyNo);
	//企业信息
	public List<FirmInfo> getFirmInfoByApplyNo(String applyNo);
	//抵押房产信息
	public List<HouseInfo> getHouseInfoByApplyNo(String applyNo);
	//抵押车辆信息
	public List<VehicleInfo> getVehiclesInfoByApplyNo(String applyNo);
	//借款人信息-申请人在企业的持股比例，出资年限
	public Map<String,Object> getCreditOtherInfoByApplyNo(String applyNo);
	//征信借款人配偶信息
	public Map<String,Object> getCoupleInfoByApplyNo(String applyNo);
	//企业信息-获取赛弥斯返回值
	public String getThemisReturnValueByApplyNo(String applyNo);
	//获取随机数
	public String getSOSRandomNumberByApplyNo(String applyNo);
	//保存报文头
	public void insertExperianResponseHead(ExperianResponseHeader header);
	//保存body
	public void insertExperianResponseBody(Resinfo resinfo);
	//计算信用卡总数量
	public String findCreditBankNumByApplyNo(String applyNo);
	//获取报文头信息
	public ExperianResponseHeader getExperianResponseHeaderByApplyNo(String applyNo);
	//获取报文信息
	public Resinfo getResinfoById(String id);
	
}
package com.resoft.outinterface.cn.com.experian.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.outinterface.cn.com.experian.dao.ExperianClientDao;
import com.resoft.outinterface.cn.com.experian.entry.request.body.AppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.CoAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.FirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.GuAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.HouseInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.LoanInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCFirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.VehicleInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.head.ExperianRequestHeader;
import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponse;
import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponseHeader;
import com.resoft.outinterface.cn.com.experian.entry.response.Resinfo;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service(value = "ExperianClientService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ExperianClientService {
	@Autowired
	private ExperianClientDao experianClientDao;

	public ApplyInfo getApplyInfoByApplyNo(String applyNo) {
		ApplyInfo applyInfo = experianClientDao.getApplyInfo(applyNo);
		return applyInfo;
	}

	// 贷款信息
	public LoanInfo getLoanInfoByApplyNo(String applyNo) {
		LoanInfo loanInfo = experianClientDao.getLoanInfoByApplyNo(applyNo);

		// 月还款额,留白
		/*
		 * // 查询月还款额 LoanInfo refundMonth =
		 * experianClientDao.getLoanInfoRefundMonthByApplyNo(applyNo); if
		 * (refundMonth != null) {
		 * loanInfo.setRefundMonth(refundMonth.getRefundMonth()); } else {
		 * loanInfo.setRefundMonth(null); }
		 */
		return loanInfo;
	}

	// 抵押房产信息
	public List<HouseInfo> getHouseInfoByApplyNo(String applyNo) {
		List<HouseInfo> houseInfoList = experianClientDao.getHouseInfoByApplyNo(applyNo);
		return houseInfoList;
	}

	// 抵押车辆信息
	public List<VehicleInfo> getVehiclesInfoByApplyNo(String applyNo) {
		List<VehicleInfo> vehiclesInfoList = experianClientDao.getVehiclesInfoByApplyNo(applyNo);
		return vehiclesInfoList;
	}

	// 共借人信息
	public List<CoAppInfo> getCoAppInfoByApplyNo(String applyNo) {
		List<CoAppInfo> loanInfoList = experianClientDao.getCoAppInfoByApplyNo(applyNo);
		return loanInfoList;
	}

	// 借款人信息
	public AppInfo getAppInfoByApplyNo(String applyNo) {
		AppInfo appInfo = experianClientDao.getAppInfoByApplyNo(applyNo);
		if(appInfo==null){
			return null;
		}
		// 查询配偶年龄
		Map<String, String> params = new HashMap<String,String>();
		params.put("roleType", "4");
		params.put("applyNo", applyNo);
		String coupleAge = experianClientDao.getCoupleAgeByApplyNoAndRoles(params);
		if (coupleAge != null && !("").equals(coupleAge)) {
			appInfo.setAppSpouseAge(new BigDecimal(coupleAge));
		} else {
			appInfo.setAppSpouseAge(null);
		}

		// 借款人.申请人在企业的持股比例
		// appSharePropertion
		/*Map<String, Object> creditOtherInfoMap = experianClientDao.getCreditOtherInfoByApplyNo(applyNo);
		String appSharePropertion = null;
		String appContributYear = null;
		if (creditOtherInfoMap != null) {
			if (creditOtherInfoMap.get("appSharePropertion") != null && creditOtherInfoMap.get("appSharePropertion") != "") {
				appSharePropertion = creditOtherInfoMap.get("appSharePropertion").toString();
				appInfo.setAppSharePropertion(new BigDecimal(appSharePropertion));
			} else {
				appInfo.setAppSharePropertion(null);
			}
			// 借款人.申请人在用款企业的出资年限
			// appContributYear
			if (creditOtherInfoMap.get("appContributYear") != null && creditOtherInfoMap.get("appContributYear") != "") {
				appContributYear = creditOtherInfoMap.get("appContributYear").toString();
				appInfo.setAppContributYear(new BigDecimal(appContributYear));
			} else {
				appInfo.setAppContributYear(null);
			}
		}*/
		return appInfo;
	}

	/* 担保人信息 */
	public List<GuAppInfo> getGuAppInfoByApplyNo(String applyNo) {
		List<GuAppInfo> guAppList = experianClientDao.getGuAppInfoByApplyNo(applyNo);
		return guAppList;
	}

	// 征信共借人信息
	/*public List<PBOCCoAppInfo> getPBOCCoAppByApplyNo(String applyNo) {
		List<PBOCCoAppInfo> pbocCoAppList = experianClientDao.getPBOCCoAppByApplyNo(applyNo);
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("roleType", "2");
		params.put("applyNo", applyNo);
		return pbocCoAppList;
	}*/

	// 征信借款人信息
	public PBOCAppInfo getPBOCAppInfoByApplyNo(String applyNo) {
		PBOCAppInfo pbocAppInfo = experianClientDao.getPBOCAppInfoByApplyNo(applyNo);
		if(pbocAppInfo == null ){
			pbocAppInfo = new PBOCAppInfo();
		}
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("roleType", "1");
		params.put("applyNo", applyNo);

		Map<String, Object> coupleInfoMap = experianClientDao.getCoupleInfoByApplyNo(applyNo);
		String loanStatus = null;
		BigDecimal allUsedAmount = null;
		BigDecimal usedCreditCardRate = null;
		String maxLoanStatus = null;
		String overrideAmount = null;
		if (coupleInfoMap != null) {
			// 配偶（信用卡的使用金额）
			if (coupleInfoMap.get("loanStatus") != null && (!("").equals(coupleInfoMap.get("loanStatus")))) {
				// 配偶.贷款五级分类
				loanStatus = coupleInfoMap.get("loanStatus").toString();
				pbocAppInfo.setPbocAppSLoanCLStatus(new BigDecimal(loanStatus));
			} else {
				pbocAppInfo.setPbocAppSLoanCLStatus(null);
			}

			//UAT测试时，不需要传这个字段
			/*if (coupleInfoMap.get("allUsedAmount") != null && (!("").equals(coupleInfoMap.get("allUsedAmount"))) && coupleInfoMap.get("usedCreditCardRate") != null && (!("").equals(coupleInfoMap.get("usedCreditCardRate")))) {
				// 总使用额度
				allUsedAmount = (BigDecimal) coupleInfoMap.get("allUsedAmount");
				// 信用卡使用占比
				usedCreditCardRate = new BigDecimal(coupleInfoMap.get("usedCreditCardRate") + "");
				pbocAppInfo.setPbocAppSDCCLStatus(allUsedAmount.multiply(usedCreditCardRate));
			} else {
				pbocAppInfo.setPbocAppSDCCLStatus(null);
			}*/

			//配偶的信用卡当前逾期金额
			if (coupleInfoMap.get("overrideAmount") != null && (!("").equals(coupleInfoMap.get("overrideAmount")))) {
				overrideAmount = coupleInfoMap.get("overrideAmount").toString();
				pbocAppInfo.setPbocAppSDCOverrideAmount(new BigDecimal(overrideAmount));
			} else {
				pbocAppInfo.setPbocAppSDCOverrideAmount(null);
			}		
		}
		
		//添加信用卡总数量
		String creditCardNum = experianClientDao.findCreditBankNumByApplyNo(applyNo);
		if(creditCardNum != null){
			pbocAppInfo.setPbocAppDCNum(new BigDecimal(creditCardNum));
		}else{
			pbocAppInfo.setPbocAppDCNum(null);
		}
		
		/*if (coupleInfoMap.get("maxLoanStatus") != null && coupleInfoMap.get("maxLoanStatus") != "") {
			// 当前最差五级分类
			maxLoanStatus = coupleInfoMap.get("maxLoanStatus").toString();
			pbocAppInfo.setPbocAppSWorstFCOL(new BigDecimal(maxLoanStatus));
		} else {
			pbocAppInfo.setPbocAppSWorstFCOL(null);
		}*/

		return pbocAppInfo;
	}

	// 征信中企业信息
	public List<PBOCFirmInfo> getPBOCFirmInfoByApplyNo(String applyNo) {
		List<PBOCFirmInfo> pbocFirmList = experianClientDao.getPBOCFirmInfoByApplyNo(applyNo);
		return pbocFirmList;
	}

	// 征信中担保人信息
	/*public List<PBOCGuAppInfo> getPBOCGuAppInfoByApplyNo(String applyNo) {
		List<PBOCGuAppInfo> pbocGuAppInfoList = experianClientDao.getPBOCGuAppInfoByApplyNo(applyNo);
		return pbocGuAppInfoList;
	}
*/
	// 企业信息
	public List<FirmInfo> getFirmInfoByApplyNo(String applyNo) {
		List<FirmInfo> firmList = experianClientDao.getFirmInfoByApplyNo(applyNo);

		String queryResult114 = null;
		String phoneResultBD = null;
		String custId = null;
		String firmArtemisRank = null;
		for (int i = 0; i < firmList.size(); i++) {
			custId = firmList.get(i).getCustId();
			firmList.get(i).setIsinIBL(null);
			Map<String, String> params = Maps.newConcurrentMap();
			params.put("applyNo", applyNo);
			params.put("custId", custId);

			// 114查证结果
//			queryResult114 = experianClientDao.getQueryResult114ByApplyNoAndRoles(params);
//			if (queryResult114 != null && queryResult114 != "") {
//				firmList.get(i).setQueryResult114(new BigDecimal(queryResult114));
//			} else {
//				firmList.get(i).setQueryResult114(null);
//			}

			// 百度网查结果
			phoneResultBD = experianClientDao.getPhoneResultBDByApplyNoAndRoles(params);
			if (phoneResultBD != null && (!("").equals(phoneResultBD))) {
				firmList.get(i).setPhoneResultBD(new BigDecimal(phoneResultBD));
			} else {
				firmList.get(i).setPhoneResultBD(null);
			}

			// 获取赛弥斯返回值
			firmArtemisRank = experianClientDao.getThemisReturnValueByApplyNo(applyNo);
			if (firmArtemisRank != null && (!("").equals(firmArtemisRank))) {
				if("AAA".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "1";
				}else if("AA".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "2";
				}else if("A".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "3";
				}else if("BBB".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "4";
				}else if("BB".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "5";
				}else if("B".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "6";
				}else if("CCC".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "7";
				}else if("CC".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "8";
				}else if("C".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "9";
				}else if("DDD".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "10";
				}else if("DD".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "11";
				}else if("D".equalsIgnoreCase(firmArtemisRank)){
					firmArtemisRank = "12";
				}
				
				firmList.get(i).setFirmArtemisRank(new BigDecimal(firmArtemisRank));
			} else {
				firmList.get(i).setFirmArtemisRank(null);
			}

		}

		return firmList;
	}

	public ExperianRequestHeader getExperianRequestHeader(String applyNo, AppInfo appInfo) {
		ExperianRequestHeader erh = new ExperianRequestHeader();
		// 版本号目前是固定值
		erh.setSOSWebserviceVersion("1");
		// 申请编号
		erh.setLoanId(applyNo);
		// 请求编号
		erh.setRequestNumber(OutInterfaceUtils.makeSeqNo());
		// 主借人姓名
		erh.setBorrowerName(appInfo.getCustName());
		// 发送日期
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		erh.setSendDate(sdfDate.format(date));
		// 发送时间
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss SSS");
		erh.setSendTime(sdfTime.format(date));
		// 发起调用的登录用户
		User logger = UserUtils.getUser();
		erh.setLoginUserName(logger.getLoginName());
		//erh.setLoginUserName("admin");
		// 随机数
		String sosRandomNumber = experianClientDao.getSOSRandomNumberByApplyNo(applyNo);
		if (sosRandomNumber != null && (!("").equals(sosRandomNumber))) {
			erh.setSOSRandomNumber(sosRandomNumber);
		} else {
			erh.setSOSRandomNumber("-1");
		}
		// 结果代码
		erh.setResultCode(null);
		// 结果信息描述
		erh.setResultInfo(null);

		return erh;

	}
	
	@Transactional(value="CRE",readOnly = false)
	public void insertExperianResponse(ExperianResponse experianResponse){
		
		//保存报文头
		Map<String,String> headMap = new HashMap<String,String>();
		ExperianResponseHeader header = new ExperianResponseHeader();
		header = experianResponse.getHead();
		//处理sendTime,只保留时分秒
		String sendTime = header.getSendTime();
		int end = sendTime.indexOf(" ");
		sendTime = sendTime.substring(0, end);
		header.setSendTime(sendTime);
		
		header.setId(IdGen.uuid());
		experianClientDao.insertExperianResponseHead(header);
		
		//保存body
		Map<String,Object> bodyMap = new HashMap<String,Object>();
		Resinfo resinfo = new Resinfo();
		resinfo = experianResponse.getBody();
		resinfo.setHeadId(header.getId());
		experianClientDao.insertExperianResponseBody(resinfo);
	}
	
	//获取报文头信息
	public ExperianResponseHeader getExperianResponseHeaderByApplyNo(String applyNo){
		 return experianClientDao.getExperianResponseHeaderByApplyNo(applyNo);
	}
	
	//获取报文信息
	public Resinfo getResinfoById(String applyNo){
		 return experianClientDao.getResinfoById(applyNo);
	}
	
	//获取星级数以及对应的分数
	public Map<String, Integer> scoringIndex(String scoringScore){
		List<Dict> list = DictUtils.getDictList(Constants.CREDIT_RATING);
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = Integer.parseInt(list.get(i).getValue());
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(scoringScore != null && !scoringScore.isEmpty()){
			int score = Integer.parseInt(scoringScore);
			int rating = 1;
			for (int i = 0; i < array.length-1; i++) {
				if(score > array[i]){
					rating = i + 2;
					if (score <= array[i+1]) {
						break;
					}
				}
			}
			if (score >= array[array.length-1]) {
				rating = array.length;
			}
			
			map.put("scoringIndex", rating);
		}else{
			map.put("scoringIndex", 0);
		}
		map.put("size", list.size());
		return map;
	}
}
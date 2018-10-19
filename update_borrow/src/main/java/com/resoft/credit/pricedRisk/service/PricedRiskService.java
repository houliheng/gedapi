package com.resoft.credit.pricedRisk.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.credit.mortgageCarEvaluateInfo.dao.MortgageCarEvaluateInfoDao;
import com.resoft.credit.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.credit.mortgageHouseInfo.dao.MortgageHouseInfoDao;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageOtherInfo.dao.MortgageOtherInfoDao;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.pricedRisk.dao.PricedRiskDao;
import com.resoft.outinterface.rest.financialPlatform.FinancialPlatformClient;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 风险定价Service
 * 
 * @author wangguodong
 * @version 2016-11-04
 */

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class PricedRiskService {

	@Autowired
	private MortgageCarInfoDao mortgageCarInfoDao;

	@Autowired
	private PricedRiskDao pricedRiskDao;

	@Autowired
	private MortgageCarEvaluateInfoDao mortgageCarEvaluateInfoDao;

	@Autowired
	private MortgageHouseInfoDao mortgageHouseInfoDao;

	@Autowired
	private MortgageOtherInfoDao mortgageOtherInfoDao;
	private static final Logger logger = LoggerFactory.getLogger(FinancialPlatformClient.class);
	// 计算风险定价建议费率
	public BigDecimal countPricedRisk(String applyNo) {
		Double pricedRisk = 0d;
		BigDecimal bigDecimal1 = new BigDecimal(0.0);
		Double overdueRatio = getOverdueRatio(applyNo);
		pricedRisk = (0.008 * (1 - overdueRatio) / 0.4056 + 0.012) * 100;
		BigDecimal bigDecimal = new BigDecimal(pricedRisk).setScale(1, BigDecimal.ROUND_HALF_UP);
		if (bigDecimal.compareTo(new BigDecimal(3.0)) == 1) {
			bigDecimal1 = new BigDecimal(3.0).setScale(1, BigDecimal.ROUND_HALF_UP);
		} else if (bigDecimal.compareTo(new BigDecimal(1.5)) == -1) {
			bigDecimal1 = new BigDecimal(1.5).setScale(1, BigDecimal.ROUND_HALF_UP);
		} else {
			bigDecimal1 = bigDecimal;
		}
		return bigDecimal1;
	}

	// 逾期比例的计算
	private Double getOverdueRatio(String applyNo) {
		BigDecimal overdueRatioParam = new BigDecimal(0);
		Double overdueRatio = 0d;
		Integer v1 = getCompanyCreatePeriod(applyNo);
		BigDecimal v2 = getCreditCardUsageAccounting(applyNo);
		BigDecimal v3 = getMonthlyMeanEffectiveFlow(applyNo);
		Integer v4 = getOwnQueryTimesInOneYear(applyNo);
		BigDecimal v5 = getGuaranteedAssetCoverage(applyNo);
		BigDecimal v6 = getCollateralCoverage(applyNo);
		double f2 =v2.setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
		double f3 =v3.setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
		double f5 =v5.setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
		double f6 =v6.setScale(12, BigDecimal.ROUND_HALF_UP).doubleValue();
		logger.info("风险定价费率计算"+applyNo+"--v1="+v1+"--v2="+f2+"--v3="+f3+"--v4="+v4+"--v5="+f5+"--v6="+f6);
		BigDecimal w1 = new BigDecimal(DictUtils.getDictValue("w1", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal w2 = new BigDecimal(DictUtils.getDictValue("w2", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal w3 = new BigDecimal(DictUtils.getDictValue("w3", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal w4 = new BigDecimal(DictUtils.getDictValue("w4", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal w5 = new BigDecimal(DictUtils.getDictValue("w5", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal w6 = new BigDecimal(DictUtils.getDictValue("w6", "OVERDUE_RATIO_COEFFICIENT", ""));
		BigDecimal c = new BigDecimal(DictUtils.getDictValue("c", "OVERDUE_RATIO_COEFFICIENT", ""));
		overdueRatioParam = w1.multiply(new BigDecimal(v1)).add(w2.multiply(v2).add(w3.multiply(v3).add(w4.multiply(new BigDecimal(v4)).add(w5.multiply(v5).add(w6.multiply(v6).add(c))))));
		overdueRatio = 1 / (1 + Math.exp(-(overdueRatioParam.doubleValue())));
		return overdueRatio;
	}

	// 获取用款企业成立年限
	private Integer getCompanyCreatePeriod(String applyNo) {
		Map<String, Object> map = Maps.newHashMap();
		Integer num = 0;
		Double numTemp = 0d;
		map = pricedRiskDao.getCompanyCreatePeriod(applyNo);
		if (map != null && map.get("registerDate") != null && map.get("foundDate") != null) {
			Date registerDate = (Date) map.get("registerDate");
			Date foundDate = (Date) map.get("foundDate");
			if (registerDate.after(foundDate)) {
				numTemp = ((registerDate.getTime() - foundDate.getTime()) / (24 * 3600 * 1000 * 365.25));
			}
		}
		num = new Long(Math.round(numTemp)).intValue();
		return num;
	}

	// 获取信用卡额度使用占比
	private BigDecimal getCreditCardUsageAccounting(String applyNo) {
		Map<String, Object> map = Maps.newHashMap();
		map = pricedRiskDao.getCreditCardUsageAccounting(applyNo);
		BigDecimal ratio = new BigDecimal(0);
		if(map != null && map.get("ratio") != null){
			ratio = (BigDecimal) map.get("ratio");
		}
		return ratio;
	}

	// 获取月均有效流水占比
	private BigDecimal getMonthlyMeanEffectiveFlow(String applyNo) {
		Map<String, Object> map1 = Maps.newHashMap();
		Map<String, Object> map2 = Maps.newHashMap();
		BigDecimal ratio = new BigDecimal(0);
		// 获取月均有效流水总额
		map1 = pricedRiskDao.getEffectiveFlowSum(applyNo);
		// 获取批复合同金额
		map2 = pricedRiskDao.getContractAccountInCheckApprove(applyNo);
		if (map1 != null && map1.get("effectiveFlowSum") != null && map2 != null && map2.get("contractAccount") != null) {
			BigDecimal decimal1 = (BigDecimal) map1.get("effectiveFlowSum");
			BigDecimal decimal2 = (BigDecimal) map2.get("contractAccount");
			ratio = decimal1.divide(decimal2,15,BigDecimal.ROUND_HALF_UP);
		}
		return ratio;
	}

	// 获取一年内本人查询次数
	private Integer getOwnQueryTimesInOneYear(String applyNo) {
		Map<String, Object> map = Maps.newHashMap();
		map = pricedRiskDao.getOwnQueryTimesInOneYear(applyNo);
		Integer num = 0;
		if (map != null && map.get("ownQueryTimesInOneYear") != null) {
			num = ((BigDecimal) map.get("ownQueryTimesInOneYear")).intValue();
		}
		return num;
	}

	// 获取担保人资产覆盖率
	private BigDecimal getGuaranteedAssetCoverage(String applyNo) {
		
		Map<String, Object> map = Maps.newHashMap();
		map = pricedRiskDao.getGuaranteedAssetCoverage(applyNo);
		BigDecimal ratio = new BigDecimal(0);
		if (map != null && map.get("guaranteedAssetCoverage") != null) {
			int value = Integer.parseInt((String) map.get("guaranteedAssetCoverage"));
			if(value==1){
				ratio = new BigDecimal(0);
			}
			else if(value==2) {
				ratio = new BigDecimal(0.5);
			} else if(value==3) {
				ratio = new BigDecimal(1);
			} else if(value==4) {
				ratio = new BigDecimal(1.5);
			}else if(value==5) {
				ratio = new BigDecimal(2);
			}else if(value==6) {
				ratio = new BigDecimal(2.5);
			}else{
				ratio = new BigDecimal(3);
			}
		}
		return ratio;
	}

	// 获取抵押物覆盖率
	private BigDecimal getCollateralCoverage(String applyNo) {
		Double houseSumTemp = 0.0;
		Double otherSumTemp = 0.0;
		BigDecimal carSum = new BigDecimal(0.0);
		BigDecimal houseSum = new BigDecimal(0.0);
		BigDecimal otherSum = new BigDecimal(0.0);
		BigDecimal ratio = new BigDecimal(0.0);
		// 计算抵押物估值总和
		List<String> ids = mortgageCarInfoDao.getIdsByApplyNo(applyNo);
		if (ids != null && ids.size() > 0) {
			carSum = mortgageCarEvaluateInfoDao.getSumCarEvaluatePriceByCarIdList(ids);
		}
		List<MortgageHouseInfo> mortgageHouseInfos = mortgageHouseInfoDao.getPageByApplyNo(applyNo);
		if (mortgageHouseInfos != null && mortgageHouseInfos.size() > 0) {
			for (MortgageHouseInfo info : mortgageHouseInfos) {
				if (info.getEvaluatePrice() != null) {
					houseSumTemp += Double.parseDouble(info.getEvaluatePrice());
				}
			}
			houseSum = new BigDecimal(houseSumTemp);
		}
		List<MortgageOtherInfo> mortgageOtherInfos = mortgageOtherInfoDao.getPageByApplyNo(applyNo);
		if (mortgageOtherInfos != null && mortgageOtherInfos.size() > 0) {
			for (MortgageOtherInfo info : mortgageOtherInfos) {
				if (info.getValueNum() != null) {
					otherSumTemp += Double.parseDouble(info.getValueNum());
				}
			}
			otherSum = new BigDecimal(otherSumTemp);
		}
		// 获取批复合同金额
		Map<String, Object> map = new HashMap<String, Object>();
		map = pricedRiskDao.getContractAccountInCheckApprove(applyNo);
		if (map != null && map.get("contractAccount") != null) {
			BigDecimal decimal = (BigDecimal) map.get("contractAccount");
			carSum = carSum.add(otherSum.add(houseSum));
			ratio = carSum.divide(decimal,15,BigDecimal.ROUND_HALF_UP);
		}
		return ratio;
	}
}

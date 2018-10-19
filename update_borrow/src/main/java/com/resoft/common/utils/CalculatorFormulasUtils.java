package com.resoft.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * @author gaozhi
 *
 */
public class CalculatorFormulasUtils {
	
	private static ContractDao contractDao = SpringContextHolder.getBean(ContractDao.class);
	
	// 预计还款本金
	private static BigDecimal monthBaseAmount;
	// 预计还款利息
	private static BigDecimal monthIrrAmount;
	// 预计本息
	private static BigDecimal monthSumAmount;

	// 预计差额本金
	private static BigDecimal ceMonthBaseAmount;
	// 预计差额利息
	private static BigDecimal ceMonthIrrAmount;
	// 预计差额本息
	private static BigDecimal ceMonthSumAmount;
	
	// 预计合同还款本金
	private static BigDecimal htMonthBaseAmount;
	// 预计合同还款利息
	private static BigDecimal htMonthIrrAmount;
	// 预计合同还款本息
	private static BigDecimal htMonthSumAmount;

	// 月还本金合计计算用
	private static BigDecimal sumMonthBaseAmount;
	// 差额月还本金合计计算用
	private static BigDecimal cesumMonthBaseAmount;
	//剩余还款本金
	private static BigDecimal surplusBaseAmount;
	
	private static void init() {
		
		 monthBaseAmount = BigDecimal.ZERO;
		 monthIrrAmount = BigDecimal.ZERO;
		 monthSumAmount = BigDecimal.ZERO;

		 ceMonthBaseAmount = BigDecimal.ZERO;
		 ceMonthIrrAmount = BigDecimal.ZERO;
		 ceMonthSumAmount = BigDecimal.ZERO;
		
		 htMonthBaseAmount = BigDecimal.ZERO;
		 htMonthIrrAmount = BigDecimal.ZERO;
		 htMonthSumAmount = BigDecimal.ZERO;

		 sumMonthBaseAmount = BigDecimal.ZERO;
		 cesumMonthBaseAmount = BigDecimal.ZERO;
		 surplusBaseAmount = BigDecimal.ZERO;
		
	}
	
	/**
	 * 不能实例化
	 */
	private CalculatorFormulasUtils() {
			
		}
	
	
	
	/**
	 * 等额本息计算公式  特点每月还款金额相等  本金+利息
	 * ROUND_HALF_UP: 遇到.5的情况时往上近似,例: >= 0.5  1.5 -> 2  (四舍五入)
	 * ROUND_HALF_DOWN : 遇到.5的情况时往下近似,> 0.5 例: 1.5 ->1 (全舍)
	 * ROUND_UP ：  舍弃小数(即截断)之前增加数字,例: 1.2 ->2 (全入)
	 * @param amount			金额
	 * @param monthIrr			月利率
	 * @param period			期数
	 * @param contractRate		合同月利率
	 * @return
	 */
	public static List<Map<String, BigDecimal>> getACPIMonthPaymentAmount(BigDecimal amount,BigDecimal monthIrr, Integer period, BigDecimal contractRate) {
		/*if (amount == null || monthIrr == null || period == null || contractRate == null) {
			return null;
		}*/
		// 初始化
		init();
		// 输出还款计划（冠e通的9项款项）
		List<Map<String, BigDecimal>> exportRepayPlanList = new ArrayList<Map<String,BigDecimal>>();
		// 库表中存的月利率 需要处理
		monthIrr = monthIrr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);
		
		// 预计合同还款本金 本金/期数 保留两位 四舍五入（除了最后一期）
		htMonthBaseAmount = amount.divide(new BigDecimal(period), 2, BigDecimal.ROUND_HALF_UP);
		// 预计合同还款利息 本金*1% 保留两位 四舍五入
		htMonthIrrAmount = amount.multiply(contractRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 预计合同还款本息 预计合同还款本息=预计合同还款本金+预计合同还款利息（除了最后一期）
		htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);
		// 月还款额 =  [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1] 保留两位 向上进位
		monthSumAmount = (amount.multiply(monthIrr).multiply(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), period.doubleValue()))))
				.divide(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), period.doubleValue())-1), 2, BigDecimal.ROUND_UP);
		
		// 需要判断是否为最后一期 最后一期需要特殊处理
		for (int i = 0; i < period.intValue(); i++) {
			// 最后一期处理
			if (i == (period.intValue() - 1)) {
				// 月还款额 = [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1]
				// **//
				// 预计还款本金 = 本金 - 往期预计还款本金和
				monthBaseAmount = amount.subtract(sumMonthBaseAmount);
				// 预计合同还款本金 = 本金 - 预计合同还款本金*（期数-1）
				htMonthBaseAmount = amount.subtract(htMonthBaseAmount.multiply(new BigDecimal(period - 1)));
				// 预计还款利息 = 月还款额 - 预计还款本金
				monthIrrAmount = monthSumAmount.subtract(monthBaseAmount);
				// 预计合同还款本息 = 预计合同还款本金 + 预计合同还款利息
				htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);
				// 预计差额本金 = 预计合同还款本金 - 预计还款本金
				ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
				// 预计差额利息 = 预计合同还款利息 - 预计还款利息
				ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
				// 预计差额本息 = 预计差额本金 + 预计差额利息
				ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);
				// 输出9项款项
				Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,monthSumAmount, ceMonthBaseAmount,
						 ceMonthIrrAmount, ceMonthSumAmount,htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
				exportRepayPlan.put("period", new BigDecimal(i+1));
				exportRepayPlanList.add(exportRepayPlan);
				
				break;

			}else{
				// 月还款额 = [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1]
				// **//
				// 月还本金 = [借款金额 * 月利率 * (1+月利率)^(i)] /[(1+月利率)^总期数 -1]
				monthBaseAmount = (amount.multiply(monthIrr).multiply(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), i))))
						.divide(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), period.doubleValue()) - 1), 2, BigDecimal.ROUND_UP);
				// 预计差额本金 = 预计合同还款本金 - 预计还款本金
				ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
				// 月还本金合计计算用 往期的和
				sumMonthBaseAmount = sumMonthBaseAmount.add(monthBaseAmount);
				
				// 差额月还本金合计计算用 往期的和
				cesumMonthBaseAmount = cesumMonthBaseAmount.add(ceMonthBaseAmount);	
				//}
				// 月还利息 = 月还款额 – 月还本金
				monthIrrAmount = monthSumAmount.subtract(monthBaseAmount);
				// 预计差额利息 = 预计合同还款利息 - 预计还款利息
				ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
				// 预计差额本息 = 预计差额本金 + 预计差额利息
				ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);

				if (monthIrrAmount.compareTo(BigDecimal.ZERO) < 0) {
					monthIrrAmount = BigDecimal.ZERO;
				   }
			
				// 输出9项款项
				Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,monthSumAmount, ceMonthBaseAmount,
						ceMonthIrrAmount, ceMonthSumAmount,htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
				exportRepayPlan.put("period", new BigDecimal(i+1));
				exportRepayPlanList.add(exportRepayPlan);
		   }
		}
		return exportRepayPlanList;
	}
	
	/**
	 * 阶梯还款计算公式  每期还款按定义好的比例计算 每月还款本金=本金*阶梯比例 利息=剩余本金*月利率
	 * ROUND_HALF_UP: 遇到.5的情况时往上近似,例: >= 0.5  1.5 -> 2  (四舍五入)
	 * ROUND_HALF_DOWN : 遇到.5的情况时往下近似,> 0.5 例: 1.5 ->1 (全舍)
	 * ROUND_UP ：  舍弃小数(即截断)之前增加数字,例: 1.2 ->2 (全入)
	 * 
	 * @param amount 				金额
	 * @param monthIrr				月利率
	 * @param period				总期数
	 * @param contractRate			合同月利率
	 * @param rateCapitalList		本金阶梯比例
	 * @return
	 */																					//合同 				数据库利率表利率			总期数			 页面批复月利率					对应的阶梯和利率
	public static List<Map<String, BigDecimal>>getLadderMonthPaymentAmount(BigDecimal amount,BigDecimal monthIrr, Integer period, BigDecimal contractRate, List<RateCapital> rateCapitalList){
	
		// 初始化
		init();
		// 输出还款计划（冠e通的9项款项）
		List<Map<String, BigDecimal>> exportRepayPlanList = new ArrayList<Map<String,BigDecimal>>();
		// 库表中存的月利率 需要处理
		monthIrr = monthIrr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);
		
		// 预计合同还款利息
		htMonthIrrAmount = amount.multiply(contractRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		surplusBaseAmount = amount;
		
		for (int i=0; i <period.intValue(); i++ ) {
			
			// 预计还款本金
			monthBaseAmount = getLadderPrincipal(amount, rateCapitalList, i);
			// 参数表中当期剩余应还本金比例 * 参数表中利息月利率
			monthIrrAmount = getLadderInterest(surplusBaseAmount, monthIrr);
			// 预计本息
			monthSumAmount = monthBaseAmount.add(monthIrrAmount);
				
			// 剩余还款本金
			surplusBaseAmount = surplusBaseAmount.subtract(monthBaseAmount);
				
			// 预计合同还款本金
			htMonthBaseAmount = getLadderPrincipal(amount, rateCapitalList, i);
			// 预计合同还款本息
			htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);
				
			// 预计差额本金 = 预计合同还款本金 - 预计还款本金
			ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
			// 预计差额利息 = 预计合同还款利息 - 预计还款利息
			ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
			// 预计差额本息 = 预计差额本金 + 预计差额利息
			ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);
			
			Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,
					monthSumAmount, ceMonthBaseAmount,ceMonthIrrAmount, ceMonthSumAmount,
					htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
			exportRepayPlan.put("period", new BigDecimal(i+1));
			exportRepayPlanList.add(exportRepayPlan);	
			}		
		return exportRepayPlanList ;
	}
	
	
	/**
	 * 
	 * 
	 * @param amount                借款金额
	 * @param monthIrr              月利率（如果为年利率需要转化 方式：BigDecimal monthIrr = yearIrr.divide(new BigDecimal(12), 32, BigDecimal.ROUND_HALF_UP);）
	 * @param period                期数
	 * @param rateCapitalList       本金还款规则（本期应还本金比例）
	 * @param type					类型：1、等额本息 2、非等额本息   (loanRepayType)
	 * @param contractrate          批复页面输入合同利率
	 * @return repayPlanList
	 * @throws Exception
	 */
	public static List<Map<String, BigDecimal>> calculateRepayPlan(BigDecimal amount, String productTypeCode, Integer type, Integer period, BigDecimal contractRate) throws Exception{
	
		if (amount == null || period == null || StringUtils.isBlank(productTypeCode) || contractRate == null || type==null) {
			return null;
		}
		
		//List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		
		List<Map<String, BigDecimal>> exportRepayPlanList = new ArrayList<Map<String,BigDecimal>>();
		
		// 预计本息
		BigDecimal monthSumAmount = BigDecimal.ZERO;
		// 预计还款本金
		BigDecimal monthBaseAmount = BigDecimal.ZERO;
		// 预计还款利息
		BigDecimal monthIrrAmount = BigDecimal.ZERO;

		// 月还本金合计计算用
		BigDecimal sumMonthBaseAmount = BigDecimal.ZERO;
		// 差额月还本金合计计算用
		BigDecimal cesumMonthBaseAmount = BigDecimal.ZERO;
		//剩余还款本金
		BigDecimal surplusBaseAmount = BigDecimal.ZERO;

		// 预计合同还款本息
		BigDecimal htMonthSumAmount = BigDecimal.ZERO;
		// 预计合同还款本金
		BigDecimal htMonthBaseAmount = BigDecimal.ZERO;
		// 预计合同还款利息
		BigDecimal htMonthIrrAmount = BigDecimal.ZERO;

		// 预计差额本息
		BigDecimal ceMonthSumAmount = BigDecimal.ZERO;
		// 预计差额本金
		BigDecimal ceMonthBaseAmount = BigDecimal.ZERO;
		// 预计差额利息
		BigDecimal ceMonthIrrAmount = BigDecimal.ZERO;
		
		surplusBaseAmount = amount;
		
		
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("loanRepayType", type+"");
		params.put("periodValue", period.toString());
		params.put("approProductTypeCode", productTypeCode);

		List<RateCapital> rateCapitalList = contractDao.getRateCapitalCurr(params);
		
		BigDecimal monthIrr = new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest());
		// 库表中存的月利率 需要处理
		monthIrr = monthIrr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);
		
		//RepayPlan repayPlan = new RepayPlan();
		
		
		
		if(type==1){
		// 预计合同还款本金 本金/期数 保留两位 四舍五入（除了最后一期）
		htMonthBaseAmount = amount.divide(new BigDecimal(period), 2, BigDecimal.ROUND_HALF_UP);
		// 预计合同还款利息 本金*1% 保留两位 四舍五入
		htMonthIrrAmount = amount.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		// 预计合同还款本息 预计合同还款本息=预计合同还款本金+预计合同还款利息（除了最后一期）
		htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);

		// 月还款额 = [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1] 保留两位 向上进位
		// (1.2->2)
		// 借款
		monthSumAmount = (amount.multiply(monthIrr).multiply(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(),
								period.doubleValue())))).divide(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(),
								period.doubleValue()) - 1), 2, BigDecimal.ROUND_UP);

		// 需要判断是否为最后一期 最后一期需要特殊处理
		for (int i = 0; i < period.intValue(); i++) {
		// 最后一期处理
			if (i == (period.intValue() - 1)) {
			// 月还款额 = [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1]
			
			// 预计还款本金 = 本金 - 往期预计还款本金和
			monthBaseAmount = amount.subtract(sumMonthBaseAmount);
			// 预计合同还款本金 = 本金 - 预计合同还款本金*（期数-1）
			htMonthBaseAmount = amount.subtract(htMonthBaseAmount.multiply(new BigDecimal(period - 1)));
			
			// 预计还款利息 = 月还款额 - 预计还款本金
			monthIrrAmount = monthSumAmount.subtract(monthBaseAmount);
			// 预计合同还款本息 = 预计合同还款本金 + 预计合同还款利息
			htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);
			
			// 预计差额本金 = 预计合同还款本金 - 预计还款本金
			ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
			// 预计差额利息 = 预计合同还款利息 - 预计还款利息
			ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
			// 预计差额本息 = 预计差额本金 + 预计差额利息
			ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);
			
			Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,monthSumAmount, ceMonthBaseAmount,
					 ceMonthIrrAmount, ceMonthSumAmount,htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
			exportRepayPlan.put("period", new BigDecimal(i+1));
			/*// 计算当期本金
			repayPlan.setCapitalAmount(monthBaseAmount);
			// 计算当期利息
			repayPlan.setInterestAmount(monthIrrAmount);
			// 计算账户管理费
			repayPlan.setManagementFee(ceMonthSumAmount);
			
			
			repayPlanList.add(i,repayPlan);*/
			exportRepayPlanList.add(exportRepayPlan);			
			break;

		 } else {
			// 月还款额 = [借款金额 * 月利率 * (1+月利率)^总期数] /[(1+月利率)^总期数 -1]
			
			// 月还本金 = [借款金额 * 月利率 * (1+月利率)^(i)] /[(1+月利率)^总期数 -1]
			monthBaseAmount = (amount.multiply(monthIrr).multiply(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), i)))).divide(new BigDecimal(Math.pow((BigDecimal.ONE.add(monthIrr)).doubleValue(), period.doubleValue()) - 1), 2,
										BigDecimal.ROUND_UP);
			// 预计差额本金 = 预计合同还款本金 - 预计还款本金
			ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
			// 月还本金合计计算用 往期的和
			sumMonthBaseAmount = sumMonthBaseAmount.add(monthBaseAmount);
			// 差额月还本金合计计算用 往期的和
			cesumMonthBaseAmount = cesumMonthBaseAmount.add(ceMonthBaseAmount);
		}

		    // 月还利息 = 月还款额 – 月还本金
			monthIrrAmount = monthSumAmount.subtract(monthBaseAmount);
			// 预计差额利息 = 预计合同还款利息 - 预计还款利息
			ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
			// 预计差额本息 = 预计差额本金 + 预计差额利息
			ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);

			if (monthIrrAmount.compareTo(BigDecimal.ZERO) < 0) {
					monthIrrAmount = BigDecimal.ZERO;
				}
			
			Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,monthSumAmount, ceMonthBaseAmount,
					 ceMonthIrrAmount, ceMonthSumAmount,htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
			exportRepayPlan.put("period", new BigDecimal(i+1));
			/*// 计算当期本金
			repayPlan.setCapitalAmount(monthBaseAmount);
			// 计算当期利息
			repayPlan.setInterestAmount(monthIrrAmount);
			// 计算账户管理费
			repayPlan.setManagementFee(ceMonthSumAmount);
						
			repayPlanList.add(i,repayPlan);*/
			exportRepayPlanList.add(exportRepayPlan);	
			}
		}else if(type==2){
			
		for (int i=0; i <period.intValue(); i++ ) {
			
			// 预计还款本金
			monthBaseAmount = getLadderPrincipal(amount, rateCapitalList, i);
			// 参数表中当期剩余应还本金比例 * 参数表中利息月利率
			monthIrrAmount = getLadderInterest(surplusBaseAmount, monthIrr);
			// 预计本息
			monthSumAmount = monthBaseAmount.add(monthIrrAmount);
				
			// 剩余还款本金
			surplusBaseAmount = surplusBaseAmount.subtract(monthBaseAmount);
				
			
			// 预计合同还款本金
			htMonthBaseAmount = getLadderPrincipal(amount, rateCapitalList, i);
			// 预计合同还款利息
			htMonthIrrAmount = getLadderInterest(surplusBaseAmount, contractRate);
			// 预计合同还款本息
			htMonthSumAmount = htMonthBaseAmount.add(htMonthIrrAmount);
				
			// 预计差额本金 = 预计合同还款本金 - 预计还款本金
			ceMonthBaseAmount = htMonthBaseAmount.subtract(monthBaseAmount);
			// 预计差额利息 = 预计合同还款利息 - 预计还款利息
			ceMonthIrrAmount = htMonthIrrAmount.subtract(monthIrrAmount);
			// 预计差额本息 = 预计差额本金 + 预计差额利息
			ceMonthSumAmount = ceMonthBaseAmount.add(ceMonthIrrAmount);
			
			Map<String,BigDecimal> exportRepayPlan = exportRepayPlan(monthBaseAmount, monthIrrAmount,monthSumAmount, ceMonthBaseAmount,
					 ceMonthIrrAmount, ceMonthSumAmount,htMonthBaseAmount, htMonthIrrAmount,htMonthSumAmount);
		
			exportRepayPlan.put("period", new BigDecimal(i+1));
				
			/*// 计算当期本金
			repayPlan.setCapitalAmount(monthBaseAmount);
			// 计算当期利息
			repayPlan.setInterestAmount(monthIrrAmount);
			// 计算账户管理费
			repayPlan.setManagementFee(ceMonthSumAmount);
					
			repayPlanList.add(i,repayPlan);*/
			exportRepayPlanList.add(exportRepayPlan);	
			}	
		}
		return exportRepayPlanList;
	}
	 /**
	    * 阶梯还款借款人每月还款本金
	    * 
	    * @param amount         本金
	    * @param repaymentRule  还款规则
	    * @param period         当前期数
	    * @return
	    */
	public static BigDecimal getLadderPrincipal(BigDecimal amount, List<RateCapital> rateCapitalList, Integer period) {
	    	
	    return amount.multiply(new BigDecimal(rateCapitalList.get(period).getRateCapitalCurr())).setScale(2, BigDecimal.ROUND_HALF_UP); 
	    	
	  }

	 /**
	    * 阶梯还款借款人每月还款利息
	    * 
	    * @param principal    本金
	    * @param monthIrr     月利率
	    * @return
	    */
	  public static BigDecimal getLadderInterest(BigDecimal principal, BigDecimal monthIrr) {
	     return principal.multiply(monthIrr).setScale(2, BigDecimal.ROUND_HALF_UP);
	   }
	  
	public static Map<String, BigDecimal> exportRepayPlan(
			BigDecimal monthBaseAmount, BigDecimal monthIrrAmount,
			BigDecimal monthSumAmount, BigDecimal ceMonthBaseAmount,
			BigDecimal ceMonthIrrAmount, BigDecimal ceMonthSumAmount,
			BigDecimal htMonthBaseAmount, BigDecimal htMonthIrrAmount,
			BigDecimal htMonthSumAmount) {
			
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		
		map.put("monthBaseAmount", monthBaseAmount);
		map.put("monthIrrAmount", monthIrrAmount);
		map.put("monthSumAmount", monthSumAmount);	
		
		map.put("ceMonthBaseAmount", ceMonthBaseAmount);			
		map.put("ceMonthIrrAmount", ceMonthIrrAmount);			
		map.put("ceMonthSumAmount", ceMonthSumAmount);
		
		map.put("htMonthBaseAmount", htMonthBaseAmount);			
		map.put("htMonthIrrAmount", htMonthIrrAmount);			
		map.put("htMonthSumAmount", htMonthSumAmount);	
		
		return map;
	} 
			  
}

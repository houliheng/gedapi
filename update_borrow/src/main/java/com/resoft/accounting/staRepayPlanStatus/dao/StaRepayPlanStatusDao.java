package com.resoft.accounting.staRepayPlanStatus.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.credit.guranteeProjectList.entity.RepayPlanDetail;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 账务调整DAO接口
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface StaRepayPlanStatusDao extends CrudDao<StaRepayPlanStatus> {
	/**
	 * 查询指定合同号下查询所有实还记录
	 * @param contractNo 合同号
	 * @return
	 */
	List<StaRepayPlanStatus> findStaRepayPlanStatus(String contractNo);
	/**
	 * 查询指定合同号下查询所有实还记录
	 * @param contractNo 合同号
	 * @return
	 */
	List<Map<String, String>> findStaRepayPlanStatusData(String contractNo);
	
	/**
	 * 根据合同号和期数查询期状态
	 * @param deductApply
	 * @return
	 */
	String getPreviousStageStatusByByContractNoAndPeriodNum(DeductApply deductApply);
	
	/**
	 * 根据合同号查询本息结清日期
	 * @param deductApply
	 * @return
	 */
	List<String> getPreviousStageStatusByContractNo(DeductApply deductApply);
	
	/**
	 * 查询指定合同号下所有期数状态
	 * @param contractNo 合同号
	 * @return
	 */
	List<String> getAllStatusesByContractNo(String contractNo);
	/**
	 * 账务调整，插入数据
	 */
	void saveAccApplyChangeRepay(StaRepayPlanStatus staRepayPlanStatus);
	
	/**
	 * 查询入账后流水的变化
	 * @param param
	 * @return
	 */
	public List<StaRepayPlanStatus> findUpdateRepayPeroidStatus(Map<String,String> param);
	
	public List<StaRepayPlanStatus> getPeriod(Map<String,String> param);
	
	public StaRepayPlanStatus findStatusByContractNo(String contractNo);
	
	public BigDecimal queryContractStayMoney(Map<String,String> param);
	//查询划扣计划详情
	public List<StaRepayPlanStatus> findRepayPlanDetailByContract(Map<String,String> param);
	
	public List<RepayPlanDetail> findContractRepayDetail(String contractNo);
	public StaRepayPlanStatus findMaxDateByContract(String contractNo);
	
	//更新实还计划提前结清
	public void updateAdvancePeriodNum(Map<String,String> param);
	
	public List<StaRepayPlanStatus>  queryAccountInfo(GedRepayment gedRepayment);
	
	public StaRepayPlanStatus getStaByContractNoAndPeriodNum(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
	public void add(StaRepayPlanStatus staRepayPlanStatus);
	
	public void deleteStaRepay(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);

	List<StaRepayPlanStatus> listWithContractNoList(@Param("contractNoList") List<String> contractNoList);
	public BigDecimal getFactMoneyByContractAndPeriodNum(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
}
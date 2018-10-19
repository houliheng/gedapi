package com.resoft.accounting.repayPlan.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.entity.RepayPlanImport;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 应还款查询DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03	
 */
@MyBatisDao
public interface AccRepayPlanDao extends CrudDao<AccRepayPlan> {
	List<AccRepayPlan> findRepayPlanByContractNo(String contractNo);

	List<AccRepayPlan> getRepayPlanByContractNo(@Param("contractNo") String contractNo);

	/**
	 * 应还款查询
	 * 
	 * @param params
	 * @return
	 */
	List<AccRepayPlan> getDataInQueryRepayPlan(Map<String, String> params);
	/**
	 * 获取指定合同号期数，还款日期，罚息
	 */
	List<Map<String,String>> findRepayPlanToStaRepayPlanStatusByContractNo(String contractNo);
	
	/**
	 * 还款计划导入
	 */
	public void importRepayPlan(RepayPlanImport repayPlanImport);
	/**
	 * 还款计划更新
	 */
	public void updateimportedRepayPlan(RepayPlanImport repayPlanImport);
	
	/**
	 * 查询还款计划对应期数信息
	 * @param param
	 * @return
	 */
	public List<AccRepayPlan> getDataInQueryRepayPlanByContractAndPeriod(Map<String,String> param);
	
	
	public AccRepayPlan findAccRepayByConAndNumber(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);

	/**
	 * 查询本息总额 还有 管理费总额
	 */
     public AccRepayPlan selectAccRepayPlanByXinxi(AccRepayPlan repayPlan);

    AccRepayPlan getCurrentRepayPlan(@Param("deductDdate")Date deductDdate,@Param("contractNo")String contractNo);

	/**
	 * 根据合同编号查询客户实还管理费
	 */
	public BigDecimal queryFactServiceFee(String ContractNo);

    public List<AccRepayPlan> getAdvanceRepayPlan(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum,@Param("date") String date);
    public AdvanceRepayGet queryStayMoney(@Param("contractNo") String contractNo,@Param("date") String date);
    public List<AccRepayPlan> queryAccRepayPlan(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
}
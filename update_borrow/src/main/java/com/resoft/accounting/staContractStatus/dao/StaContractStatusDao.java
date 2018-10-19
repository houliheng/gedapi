package com.resoft.accounting.staContractStatus.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.entity.StaContractStatusImport;
import com.resoft.outinterface.rest.ged.entity.GedAdvanceRepayDetail;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 合同还款明细查询DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface StaContractStatusDao extends CrudDao<StaContractStatus> {

	List<DeductResultTemp> queryDeductResult(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
	List<DeductResultTemp> queryDeductResultByParams(Map<String,Object> params);

	/**
	 * 根据id查询机构层级
	 */
	public String getOfficeLevel(String id);

	/**
	 * 还款计划导入
	 */
	public void importStaContractStatus(StaContractStatusImport staContractStatusImport);

	/**
	 * 还款计划更新
	 */
	public void updateimportedStaContractStatus(StaContractStatusImport staContractStatusImport);

	/**
	 * 根据合同号查询统计数据
	 */
	public StaContractStatus getStaContractStatusByContractNo(String contractNo);

	List<GedAdvanceRepayDetail> getAdvanceDetailGED(@Param("contractNo")String contractNo);
	
	public void updateContractStatus(@Param("contractNo")String contractNo);
	public StaContractStatus getContractByContractNo(@Param("contractNo")String contractNo);
}
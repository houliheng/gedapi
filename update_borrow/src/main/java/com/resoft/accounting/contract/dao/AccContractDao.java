package com.resoft.accounting.contract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.resoft.accounting.accContract.AccContract;
import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractImport;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/** 
 * 合同信息DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@MyBatisDao
public interface AccContractDao extends CrudDao<Contract> {
	
	public Contract findContractByContractNo(String contractNo);
	public Contract findContractInfoByContractNo(String contractNo);
	
	public int importContract(ContractImport contractTmp);
	public void updateImportedContract(ContractImport contractTmp);
	public List<Contract> findContractByApplyNo(String applyNo);
	public String getHasAccContract(@Param("contractNo")String contractNo);
	public Contract queryContractMoney(@Param("contractNo")String contractNo);
	public Contract findContractSituation(@Param("contractNo")String contractNo);
	public void confirmGuranteeRelation(@Param("contractNo")String contractNo);
	public Contract queryIsConfirm(@Param("applySubNo")String applySubNo,@Param("applyNo") String applyNo);
	public List<Contract> findContractBySonApplyNo(@Param("contractNo")String contractNo);
	public void updateFactGuarantee(@Param("contractNo")String contractNo, @Param("factGuaranteeFee")BigDecimal factGuaranteeFee, @Param("factGuaranteeGold")BigDecimal factGuaranteeGold,@Param("factServiceFee") BigDecimal factServiceFee);

	public Contract queryContractByContractNo(String contractNo);

	/***
	 *  根据合同编号查询大区 分公司
	 */
	public Contract queryOrglevel(String contractNo);
	/**
	 * 根据合同号增加申请编号
	 */
	public void updateContactByContractNo(Map<String,Object> paramMap);





}
package com.resoft.credit.contract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.act.entity.MyMap;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.rateCapital.entity.RateCapital;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同信息DAO接口
 * @author yanwanmei
 * @version 2016-03-02
 */
@MyBatisDao("creContractDao")
public interface ContractDao extends CrudDao<Contract> {
	public Contract getContractByApplyNo(@Param("applyNo")String applyNo);
	
	public Contract getContractByContractNo(@Param("contractNo")String contractNo);
	public void updateByApplyNo(Contract contract);
	
	public Map<String,Object> findLevelNumByOrgId(@Param("orgId")String orgId);
	
	public String findParentIdById(@Param("id")String id);
	
	public List<RateCapital> getRateCapitalCurr(Map<String, String> params);
	
	public String getInterest(Map<String, String> params);
	
	public AccContract getCreContractByContractNo(@Param("contractNo")String contractNo);
	
	public Contract getContractByApplyNoCustId(Map<String, String> params);
	
	public String getCurrContractNo(Map<String, String> params);
	
	public List<Contract> findListByApplyNo(Map<String,String> params);
	/**
	 * 根据applyNo 删除合同数据
	 */
	public void deleteContractDataByApplyNo(Map<String, String> params);
	
	/**
	 * 根据applyNo查询合同信息
	 */
	public List<Contract> getContractDataByApplyNo(String applyNo);
	
	/**
	 * 根据id修改合同信息
	 */
	public void updateContractNoById(Contract contract);
	/**
	 * 根据联合授信批复ID查询合同信息
	 */
	public Contract getContractByApproveId(Map<String, String> params);
	/**
	 * 根据联合授信批复ID修改合同信息
	 */
	public void updateContractDataByApproveId(Map<String, String> params);
	/**
	 * 根据code查询机构Id
	 */
	public String getOfficeIdByCode(String code);
	
	
	public Map<String,Object> findContractAndApplyLoanStatus(@Param("applyNo")String applyNo,@Param("contractNo")String contractNo);

	public void updateFactGuarantee(@Param("contractNo")String contractNo, @Param("factGuaranteeFee")BigDecimal factGuaranteeFee, @Param("factGuaranteeGold")BigDecimal factGuaranteeGold,@Param("factServiceFee") BigDecimal factServiceFee);
	public void confirmGuranteeRelation(@Param("contractNo")  String contractNo);
	
	public Contract queryIsConfirm(@Param("contractNo")String contractNo);
	
	public void updateGuranteeRelation(@Param("applyNo") String applyNo,@Param("custId") String custId);


	//根据合同编号来修改该合同推送冠易贷的状态
	public int updateContractGedApiSave(String contractNo);

    List<MyMap> showAllContract(Map<String, Object> paramMap);
    
    public String queryContractAccountManagerFeeByCon(String contractNo);

	/**
	 * 更新合同签署信息
	 * @param contractNo 合同编号
	 * @return 更新数目
	 */
	int updateContractSignFlag(@Param("contractNo") String contractNo);

	public List<Contract> getContractListByApplyNo(@Param("applyNo") String applyNo);

    public int updateDiscountSaveByContract(Contract Contract);




}
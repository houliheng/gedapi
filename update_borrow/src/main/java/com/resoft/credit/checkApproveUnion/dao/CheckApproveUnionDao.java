package com.resoft.credit.checkApproveUnion.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 批复信息授信DAO接口
 * 
 * @author wangguodong
 * @version 2016-12-14
 */
@MyBatisDao
public interface CheckApproveUnionDao extends CrudDao<CheckApproveUnion> {

	public void saveCheckApproveUnionList(@Param("checkApproveUnions") List<CheckApproveUnion> checkApproveUnions);
	
	/**
	 * 根据申请编号查询没有绑定的担保人
	 */
	public List<CheckApproveUnion> getFreeStaffsByApplyNoOnCustInfo(String applyNo);
	/**
	 * 根据申请编号查询没有绑定的企业
	 */
	public List<CheckApproveUnion> getFreeStaffsByApplyNoOnCompany(String applyNo);
	/**
	 * 根据applyNo查询批复信息
	 */
	public List<CheckApproveUnion> getCheckApproveUnionByApplyNo(@Param("applyNo") String applyNo);

		public List<CheckApproveUnion> findApproveListByApplyNo(String applyNo);
		
	/**
	 * 批量删除批复信息
	 */
	public void deleteCheckApproveUnion(Map<String, String> params);
	
	/**
	 * 分公司授信检验是否可以提交
	 */
	public List<CheckApproveUnion> findCustIdByApplyNo(String applyNo);
	
	public List<AddOrderRequest> findOrderApproveListByApplyNo(@Param("applyNo") String applyNo);

	public List<String> getCustIdByApplyNo(@Param("applyNo") String applyNo);

	public CheckApproveUnion getByApplyNoAndContract(@Param("contractNo")String contractNo,@Param("applyNo")String applyNo);

	public CheckApproveUnion getByApplyNoAndCustId(@Param("applyNo")String applyNo, @Param("custId")String custId);

    /**
     * 根据合同号查询联合授信批复信息
     * @param contractNo
     * @return
     */
	public CheckApproveUnion queryUnionByContract(String contractNo);



}
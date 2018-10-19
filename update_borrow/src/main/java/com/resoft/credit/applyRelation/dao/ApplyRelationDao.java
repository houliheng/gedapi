package com.resoft.credit.applyRelation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.outinterface.rest.newged.entity.GedPushGuaranteeRequest;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 申请客户关联信息表DAO接口
 * @author caoyinglong
 * @version 2016-02-29
 */
@MyBatisDao
public interface ApplyRelationDao extends CrudDao<ApplyRelation> {
	
	/**
	 * 个人客户登记提交时，同时更新关系表内容
	 * @param map
	 */
	public void updateByRegisterAndCust(ApplyRelation applyRelation);
	
	/**
	 * 根据客户ID查询关系表信息
	 * @return
	 */
	public List<ApplyRelation> getRelationByParams(Map<String, String> map);
	
	/**
	 * 根据申请编号查询关系表信息
	 * @param Map<String, String> map
	 * @return ApplyRelation
	 */
	public ApplyRelation findApplyRelationByApplyNo(Map<String, String> map);
	
	/**
	 * 根据角色类型在当前流程中查询客户名称
	 * @param Map<String, String>
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> findCustNameByRoleType(Map<String, String> params);
	
	/**
	 * 根据角色类型在当前流程中查询客户名称(在企业征信中使用)
	 * @param Map<String, String>
	 * @return List<CreditCompany>
	 */
	public List<CreditCompany> findCustNameByRoleTypeOnCreditCompany(Map<String, String> params);
	/**
	 * 根据角色类型在当前流程中查询客户名称(在资产清单详情中使用)
	 * @param Map<String, String>
	 * @return List<CreditAssets>
	 */
	public List<CreditAssets> findCustNameByRoleTypeOnCreditAssets(Map<String, String> params);
	
    public List<ApplyRelation> getCheckCoupleDoubtfulByApplyNo(String applyNo);
	
	public List<ApplyRelation> getCheckWebByApplyNo(String applyNo);
	
	
	public long getRelationCount(Map<String, Object> params);
	
	public long getCompanyRelationCount(Map<String, Object> params);
	
	public List<ApplyRelation> getRelationMatchByApplyNo(@Param("applyNo")String applyNo);
	
	/**
	 * 根据申请编号和角色类型List查询关系表
	 * @param params
	 * @return
	 */
	public List<ApplyRelation> findByApplyNoAndRoleTypeList(Map<String, Object> params);
	
	/**
	 * 根据申请编号和角色类型List查询关系表(无论是否删除)
	 * @param params
	 * @return
	 */
	public List<ApplyRelation> findByApplyNoAndRoleTypeListAll(Map<String, Object> params);
	
	/**
	 * 根据客户id 和申请编号 调整关系人员(主要用于解除关系)
	 */
	public void updateApplyRelationByApplyNoAndCustId(Map<String, String> params);
	
	public void updateIsPush(String applyNo);
	
	public List<ApplyRelation> findUnionList(Map<String, String> params); 
	
	/**
	 * 根据applyNo查询关系列表
	 */
	public List<ApplyRelation> getCompanyListByApplyNo(String applyNo);
	
	/**
	 * 通过申请单号及客户ID查询当前关联记录信息
	 * @param applyNo
	 * @param custId
	 * @return
	 */
	public ApplyRelation getApplyRelation(Map<String, String> params);

	public String getRoleType(@Param("applyNo")String applyNo, @Param("unSocCreditNo")String unSocCreditNo);
	public String getCompanyRoleType(@Param("applyNo")String applyNo, @Param("unSocCreditNo")String unSocCreditNo);

	public List<ApplyRelation> findThisCompanyGuarantor(ApplyRelation applyRelation);

	public List<String> getDanBaoCompanyRoleType(@Param("applyNo")String applyNo, @Param("unSocCreditNo")String unSocCreditNo);

	public String getRoleByPhone(@Param("registerPhone")String registerPhone, @Param("applyNo")String applyNo, @Param("roleType")String roleType);

	public ApplyRelation getBatchRelationByCustId(@Param("applyNo")String applyNo, @Param("companyId")String companyId);

	public ApplyRelation getByApplyNoAndRoleType(@Param("applyNo")String applyNo,  @Param("roleType")String roleType);

	public List<GedPushGuaranteeRequest> getGEDguarantorInfo(@Param("applyNo")String applyNo);

	public List<GedPushGuaranteeRequest> getGEDguarantorCom(@Param("applyNo")String applyNo);

	public List<GedPushGuaranteeRequest> getGEDguarantorGS(@Param("applyNo")String applyNo);

	public List<ApplyRelation> getMainAndGuarantor(@Param("applyNo")String applyNo, @Param("roleType1")String roleType1, @Param("roleType2")String roleType2);

	public ApplyRelation getBatchRelationByCustIdAndRoleType(@Param("applyNo")String applyNo, @Param("custId")String custId, @Param("roleType")String roleType);

	public String guaranteeOpen(@Param("applyNo")String applyNo);

	public List<String> companyOpens(@Param("applyNo")String applyNo,@Param("roleType")String roleType);

	public List<String> peopleOpen(@Param("applyNo")String applyNo,@Param("roleType")String roleType);
	public ApplyRelation queryMainApplyrelation(@Param("applyNo")String applyNo, @Param("custId")String custId,@Param("roleType") String roleType);

	public List<GuaranteeRelation> getMainAll(@Param("applyNo")String applyNo);
	public List<ApplyRelation> findAllGuranteeRelation(@Param("custId") String custId);
	public ApplyRelation findMianApplyRelation(@Param("applyNo")String applyNo);
	public void updateApplyConfirm(@Param("applyNo")String applyNo);

    List<ApplyRelation> getListByApplyNo(@Param("applyNo")String applyNo,@Param("roleType")String roleType);
}
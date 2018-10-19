package com.resoft.credit.guaranteeCompany.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 担保公司DAO接口
 * @author gsh
 * @version 2018-04-16
 */
@MyBatisDao
public interface CreGuaranteeCompanyDao extends CrudDao<CreGuaranteeCompany> {
	public void batchDelete(@Param("idList")List<String> idList);
	public CreGuaranteeCompany queryCreGuranteeCompanyByUnSocreNo(@Param("unSocCreditNo") String unSocCreditNo);
	public List<CreGuaranteeCompany> findListCregurantee();
	public void updateGuranteeInfo(CreGuaranteeCompany creGuaranteeCompany);
	public void confirmGuranteeRelation(@Param("contractNo")String contractNo);
	public List<CreGuaranteeCompany> queryCompanyList(CreGuaranteeCompany creGuaranteeCompany);
	public CreGuaranteeCompany getByApplyNoAndRoleType(@Param("applyNo")String applyNo, @Param("roleType")String roleType);
	public void updateGuantRelationApply(String applyNo);
	public void updateGuantRelationGurantee(String applyNo);
	public void updateGuantRelationGuranCompany(String applyNo);
	public CheckApproveUnion getByApplyNoAndCustId(@Param("applyNo") String applyNo,@Param("custId") String custId);
	public CreGuaranteeCompany getBycheckAndRelation(@Param("id")String id);
	public CreGuaranteeCompany getMainBycheckAndRelation(@Param("applyIdChild")String applyIdChild, @Param("applyNo")String applyNo);
	public void updateGuranteeGedAccount(CreGuaranteeCompany company);
	public CreGuaranteeCompany getGuaranByCompanyIdAndType(@Param("applyNo")String applyNo,@Param("id")String id, @Param("roleType")String roleType);
}
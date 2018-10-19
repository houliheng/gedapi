package com.resoft.credit.guaranteeRelation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedPushGuaranteeRequest;

/**
 * 担保信息关联表DAO接口
 * @author jml
 * @version 2018-04-17
 */
@MyBatisDao
public interface GuaranteeRelationDao extends CrudDao<GuaranteeRelation> {
	
	List<GuaranteeRelation> findGedGuarantee(Map<String, Object> params);
	List<GuaranteeRelation> findGedCompanyGuarantee(Map<String, Object> params);

	void deleteAllRelation(String applyNo, String companyId);
	void batchInsert(@Param("guaranteeRelationList")List<GuaranteeRelation> guaranteeRelationList);
	void deleteAllCompanyRelation(String applyNo, String companyId);
	List<FindIsRegisterRequest> getCompanySearchMessage(@Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getSearchMessageInfo(@Param("applyNo")String applyNo);
	List<GuaranteeRelation> findAllGuarantorByApplyNo(@Param("applyNo")String applyNo);
	List<GuaranteeRelation> findAllGuarantCompanyByApplyNo(@Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getSearchBatchCompany(@Param("applyNo")String applyNo);
	List<GedPushGuaranteeRequest> findGuarantCompany(@Param("applyNo")String applyNo);
	List<String> getIdByApplyNoAndCompanyId(@Param("companyId")String companyId, @Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getMainCompanySearchMessage(@Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getMainSearchMessageInfo(@Param("applyNo")String applyNo);
	GedPushGuaranteeRequest findGuarantMainCompany(@Param("custId")String custId);
	List<GuaranteeRelation> getRelationByApplyNoAndCompanyId(@Param("companyId")String companyId, @Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getGRMainCompanySearchMessage(@Param("applyNo")String applyNo);
	List<FindIsRegisterRequest> getGRMainSearchMessageInfo(@Param("applyNo")String applyNo);
	void deleteByApplyNo(@Param("applyNo")String applyNo);
	List<GuaranteeRelation> listConfirmStatus(@Param("applyNo")String applyNo, @Param("applyRoleType") String applyRoleType,
		@Param("guaranteeRoleType") String guaranteeRoleType, @Param("companyId") String companyId);
}
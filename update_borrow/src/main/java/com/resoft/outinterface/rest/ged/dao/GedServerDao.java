package com.resoft.outinterface.rest.ged.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.outinterface.rest.ged.entity.GedCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface GedServerDao {
	public void insert(GedCustInfo custInfo);
	public void updateConfirmGuranteRelationByApply(Map<String,String> param);
	public void updateConfirmGuranteRelationByGurantee(Map<String,String> param);
	public void updateConfirmRelation(Map<String,String> param);
	public List<ApplyRelation> findApplyRelationByApplyNo(String applyNo);
	public void updateAppltReltation(Map<String,String> param);
	public List<GuaranteeRelation> getIdByApplyNoAndCompanyId(Map<String,String> param);
	public CreApplyCompanyRelation getGuranteeCompanyId(Map<String,String> param);
	public List<CreApplyCompanyRelation> findApplyCompanyRelationByApplyNo(String applyNo);
	public void updateContractNoByApplyNo(@Param("applyNo") String applyN0,@Param("custId") String custId);
}

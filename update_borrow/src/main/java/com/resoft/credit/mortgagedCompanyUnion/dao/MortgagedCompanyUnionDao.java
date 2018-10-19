package com.resoft.credit.mortgagedCompanyUnion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetGuarantorCompany.entity.GqgetGuarantorCompany;
import com.resoft.credit.mortgagedCompanyUnion.entity.MortgagedCompanyUnion;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 联合授信冠e通担保企业DAO接口
 * 
 * @author wangguodong
 * @version 2016-12-26
 */
@MyBatisDao
public interface MortgagedCompanyUnionDao extends CrudDao<MortgagedCompanyUnion> {
	/**
	 * 批量保存联合授信冠e通担保企业
	 */
	void insertMortgagedCompanyUnionData(@Param("gqgetGuarantorCompanies") List<GqgetGuarantorCompany> gqgetGuarantorCompanies, @Param("checkApproveId") String checkApproveId);
	/**
	 * 根据applyno删除数据
	 */
	void deleteMortgagedCompanyUnionData(String applyNo);
}
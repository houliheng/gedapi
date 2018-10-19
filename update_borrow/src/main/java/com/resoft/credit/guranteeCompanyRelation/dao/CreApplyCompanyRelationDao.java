package com.resoft.credit.guranteeCompanyRelation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 *
 * 批量借款企业关系表DAO接口
 * @author lb
 * @version 2018-04-25
 */
@MyBatisDao
public interface CreApplyCompanyRelationDao extends CrudDao<CreApplyCompanyRelation> {

    /**
     * 根据条件查询关联关系表
     */
     public  CreApplyCompanyRelation selectCreapplyCompanyRelation(Map<String,Object> paramMap);

    /**
     * 根据条件查询关联关系表
     */
    public  List<CreApplyCompanyRelation> selectCreapplyCompanyRelationList(Map<String,Object> paramMap);


    /**
     * 根据条件批量删除关联关系表
     */

    public  int  beachDelete(@Param("applyNo")String applyNo, @Param("companyId")String companyId, @Param("ids")List<String> ids);



	public List<CreApplyCompanyRelation> findHasRelation(@Param("applyNo")String applyNo, @Param("guaranteeCompanyId")String guaranteeCompanyId, @Param("custId")String custId);

	public List<CreApplyCompanyRelation> getByApplyNoRoleType(@Param("applyNo")String applyNo, @Param("roleType")String roleType);
	public CreApplyCompanyRelation findApplyRelationByCustAndRole(@Param("applyNo")String applyNo, @Param("custId")String custId);

	public List<CreApplyCompanyRelation> findApplyCompanyRelationByXinxi(@Param("applyNo")String applyNo,@Param("piliId")String piliId,@Param("roleType")String roleType,@Param("ids")List<String> ids);

	public List<GuaranteeRelation> getBatchAll(@Param("applyNo")String applyNo);
	public List<CreApplyCompanyRelation> findApplyGuranteeRelations(@Param("custId")String custId);
	public void updateApplyGurantee(@Param("applyNo")String applyNo,@Param("custId")String custId,@Param("companyId")String companyId);

}
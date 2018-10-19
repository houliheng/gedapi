package com.resoft.credit.guranteeCompanyRelation.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guranteeCompanyRelation.dao.CreApplyCompanyRelationDao;

/**
 * 批量借款企业关系表Service
 * @author lb
 * @version 2018-04-25
 */
@Service
@Transactional(readOnly = true)
public class CreApplyCompanyRelationService extends CrudService<CreApplyCompanyRelationDao, CreApplyCompanyRelation> {

	public CreApplyCompanyRelation get(String id) {
		return super.get(id);
	}
	
	public List<CreApplyCompanyRelation> findList(CreApplyCompanyRelation creApplyCompanyRelation) {
		return super.findList(creApplyCompanyRelation);
	}
	
	public Page<CreApplyCompanyRelation> findPage(Page<CreApplyCompanyRelation> page, CreApplyCompanyRelation creApplyCompanyRelation) {
		return super.findPage(page, creApplyCompanyRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(CreApplyCompanyRelation creApplyCompanyRelation) {
		super.save(creApplyCompanyRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreApplyCompanyRelation creApplyCompanyRelation) {
		super.delete(creApplyCompanyRelation);
	}

	/**
	 * 根据条件查询关联关系表
	 * @param
	 * @return
	 */
    public  CreApplyCompanyRelation  queryCompanyRelationList(Map<String,Object> paramMap){

		return super.dao.selectCreapplyCompanyRelation(paramMap);
	}

	/**
	 * 根据条件查询关联关系表
	 * @param
	 * @return
	 */
	public  List<CreApplyCompanyRelation>  queryCompanyRElaList(Map<String,Object> paramMap){

		return super.dao.selectCreapplyCompanyRelationList(paramMap);
	}

	/**
	 * 根据条件批量删除关联关系表
	 */
	@Transactional(value="CRE",readOnly = false)
    public int beachDelete(String applyNo,String companyId,List<String> ids) {
         return super.dao.beachDelete(applyNo,companyId,ids);
	}

	public List<CreApplyCompanyRelation> getByApplyNoRoleType(String applyNo, String roleType) {
		return dao.getByApplyNoRoleType(applyNo,roleType);
	}
	
	
	public CreApplyCompanyRelation findApplyRelationByCustAndRole(String applyNo,String custId){
		return dao.findApplyRelationByCustAndRole(applyNo,custId);
	}

	/**
	 * 查询批量借款企业下的担保公司 并且解除之间的关联关系
	 */
	@Transactional(value="CRE",readOnly = false)
	public void deleteCompanyByApplyCompanyRelation(Map<String,Object> paramMap){

		CreApplyCompanyRelation relation = super.dao.selectCreapplyCompanyRelation(paramMap);
        // 借款企业有没有关联担保公司
		if(null != relation){
			super.delete(relation);
		}
	}

	/**
	 * 避免重复关联借款企业的担保人信息 或者 担保企业信息
	 */
     public List<CreApplyCompanyRelation> findApplyCompanyRelationByXinxi(String applyNo,String piliId,String roleType,List<String> ids){

     	return super.dao.findApplyCompanyRelationByXinxi(applyNo,piliId,roleType,ids);
	 }

	public List<GuaranteeRelation> getBatchAll(String applyNo) {
		return dao.getBatchAll(applyNo);
	}
	
	public List<CreApplyCompanyRelation> findApplyGuranteeRelations(String custId){
		return dao.findApplyGuranteeRelations(custId);
	}

}
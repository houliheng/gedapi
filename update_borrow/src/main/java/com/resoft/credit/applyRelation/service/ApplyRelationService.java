package com.resoft.credit.applyRelation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.applyRegister.dao.ApplyRegisterDao;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import org.fusesource.mqtt.codec.PUBACK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.outinterface.rest.newged.entity.GedPushGuaranteeRequest;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 申请客户关联信息表Service
 * @author caoyinglong
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class ApplyRelationService extends CrudService<ApplyRelationDao, ApplyRelation> {

	@Autowired
	private ApplyRegisterDao applyRegisterService;


	public ApplyRelation get(String id) {
		return super.get(id);
	}
	
	public List<ApplyRelation> findList(ApplyRelation applyRelation) {
		return super.findList(applyRelation);
	}
	
	public Page<ApplyRelation> findPage(Page<ApplyRelation> page, ApplyRelation applyRelation) {
		return super.findPage(page, applyRelation);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(ApplyRelation applyRelation) {
		super.save(applyRelation);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(ApplyRelation applyRelation) {
		super.delete(applyRelation);
	}
	
	/**
	 * 根据申请编号查询关系表
	 * @param Map<String, String> map
	 * @return ApplyRelation
	 */
	public ApplyRelation findApplyRelationByApplyNo(Map<String, String> map){
		return super.dao.findApplyRelationByApplyNo(map);
	}
	
	/**
	 * 根据角色类型在当前流程中查询客户名称
	 * @param Map<String, String>
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> findCustNameByRoleType(Map<String, String> params) {
		return super.dao.findCustNameByRoleType(params);
	}
	
	public List<CreditCompany> findCustNameByRoleTypeOnCreditCompany(Map<String, String> params) {
		return this.dao.findCustNameByRoleTypeOnCreditCompany(params);
	}
	
	public List<CreditAssets> findCustNameByRoleTypeOnCreditAssets(Map<String, String> params) {
		return this.dao.findCustNameByRoleTypeOnCreditAssets(params);
	}
	
	public List<ApplyRelation> getRelationMatchByApplyNo(String applyNo) {
		return super.dao.getRelationMatchByApplyNo(applyNo);
	}
	
	public List<ApplyRelation> findByApplyNoAndRoleTypeList(Map<String, Object> params){
		return super.dao.findByApplyNoAndRoleTypeList(params);
	}

	public List<ApplyRelation> getCheckCoupleDoubtfulByApplyNo(String applyNo) {
		return super.dao.getCheckCoupleDoubtfulByApplyNo(applyNo);
	}
	public List<ApplyRelation> getCheckWebByApplyNo(String applyNo) {
		return super.dao.getCheckWebByApplyNo(applyNo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void updateApplyRelationByApplyNoAndCustId(Map<String, String> params) {
		this.dao.updateApplyRelationByApplyNoAndCustId(params);
	}
	
	public void updateIsPush(String applyNo){
		this.dao.updateIsPush(applyNo);
	}

	public List<ApplyRelation> findUnionList(Map<String, String> params) {
		return super.dao.findUnionList(params);
	}
	/**
	 * 通过申请单号及客户ID查询当前关联记录信息
	 * @param applyNo
	 * @param custId
	 * @return ApplyRelation
	 */
	public ApplyRelation getApplyRelation(String applyNo , String custId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("applyNo", applyNo);
		params.put("custId", custId);
		return this.dao.getApplyRelation(params);
		
	}

	public String getRoleType(String applyNo, String unSocCreditNo) {
		return dao.getRoleType(applyNo,unSocCreditNo);
	}

	public String getCompanyRoleType(String applyNo, String unSocCreditNo) {
		return dao.getCompanyRoleType(applyNo,unSocCreditNo);
	}

	public List<ApplyRelation> findThisCompanyGuarantor(ApplyRelation coCustInfoRelation) {
		return dao.findThisCompanyGuarantor(coCustInfoRelation);
	}

	public List<String> getDanBaoCompanyRoleType(String applyNo, String unSocCreditNo) {
		return dao.getDanBaoCompanyRoleType(applyNo,  unSocCreditNo);
	}

	public String getRoleByPhone(String registerPhone, String applyNo, String roleType) {
		return dao.getRoleByPhone(registerPhone,applyNo,roleType);
	}

	public ApplyRelation getBatchRelationByCustId(String applyNo, String companyId) {
		return dao.getBatchRelationByCustId(applyNo,companyId);
	}

	/*改方法适用于查住接人 ，主借企业，担保公司
	* */
	public ApplyRelation getByApplyNoAndRoleType(String applyNo, String string) {
		return dao.getByApplyNoAndRoleType(applyNo,string);
	}

	public List<GedPushGuaranteeRequest> getGEDguarantorInfo(String applyNo) {
		return dao.getGEDguarantorInfo(applyNo);
	}

	public List<GedPushGuaranteeRequest> getGEDguarantorCom(String applyNo) {
		return dao.getGEDguarantorCom(applyNo);
	}

	public List<GedPushGuaranteeRequest> getGEDguarantorGS(String applyNo) {
		return dao.getGEDguarantorGS(applyNo);
	}

	public List<ApplyRelation> getMainAndGuarantor(String applyNo, String roleType1, String roleType2) {
		return dao.getMainAndGuarantor(applyNo,roleType1,roleType2);
	}

	public ApplyRelation getBatchRelationByCustIdAndRoleType(String applyNo, String custId, String roleType) {
		return dao.getBatchRelationByCustIdAndRoleType(applyNo,custId,roleType);
	}

	public String isOpenAccount(String applyNo) {
		//
		ApplyRegister applyRegister = new ApplyRegister();
		applyRegister.setApplyNo(applyNo);
		List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
		if (!registerList.isEmpty()) {
			applyRegister = registerList.get(0);
		}
		//担保公司开户
		//String guaranteeOpen = dao.guaranteeOpen(applyNo);
		List<String> mainPeople;
		if("2".equals(applyRegister.getCustType())){
			//主借企业
			mainPeople = dao.companyOpens(applyNo,"5");
		}else{
			//主借人
			mainPeople = dao.peopleOpen(applyNo,"1");
		}
		//mainPeople.add(guaranteeOpen);
		for (String string : mainPeople) {
			if(StringUtils.isEmpty(string)) {
				return "借款人未开户，请尽快通知借款人开通资金存管账户";
			}
		}
		//担保人
		List<String> guarantInfoOpen = dao.peopleOpen(applyNo,"3");
		//担保企业  
		List<String> guarantCompanyOpen = dao.companyOpens(applyNo,"6");
		String flag = "0";
		guarantCompanyOpen.addAll(guarantInfoOpen);
		for (String string : guarantCompanyOpen) {
			if(StringUtils.isNotEmpty(string)) {
				flag="1";
				break;
			}
		}
		if("0".equals(flag)) {
			return "担保人未开户，请保证每个借款企业至少有一个担保自然人已开户";
		}
		return null;
	}
	
	public ApplyRelation queryMainApplyrelation(String applyNo,String custId,String roleType){
		return dao.queryMainApplyrelation(applyNo,custId,roleType);
	}

	/**
	 * 查询主借人关联的担保公司 有的话解除关联
	 */
	@Transactional(value="CRE",readOnly = false)
	public void deleteApplyRelation(Map<String,String> applyRelationMap){

		ApplyRelation applyRelation = super.dao.findApplyRelationByApplyNo(applyRelationMap);

		//判断是否关联 : 解除关联
		if(null != applyRelation){
             super.delete(applyRelation);
		}

	}

	public List<GuaranteeRelation> getMainAll(String applyNo) {
		return dao.getMainAll(applyNo);
	}
	
	public List<ApplyRelation> findAllGuranteeRelation(String custId){
		return dao.findAllGuranteeRelation(custId);
	}
	public ApplyRelation findMianApplyRelation(String applyNo){
		return dao.findMianApplyRelation(applyNo);
	}

	public List<ApplyRelation> getListByApplyNo(String applyNo, String roleType) {
		return dao.getListByApplyNo(applyNo,roleType);
	}
}
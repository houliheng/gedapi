package com.resoft.credit.guaranteeCompany.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.contract.entity.Contract;
import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.guaranteeCompany.dao.CreGuaranteeCompanyDao;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guranteeCompanyRelation.dao.CreApplyCompanyRelationDao;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.outinterface.rest.ged.dao.GedServerDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 担保公司Service
 * @author gsh
 * @version 2018-04-16
 */
@Service
@Transactional(readOnly = true)
public class CreGuaranteeCompanyService extends CrudService<CreGuaranteeCompanyDao, CreGuaranteeCompany> {

    @Autowired
    private CreApplyCompanyRelationDao creApplyCompanyRelationDao;

    @Autowired
	private ApplyRelationDao applyRelationDao;
    @Autowired
    private GedServerDao gedServerDao;
    
	public CreGuaranteeCompany get(String id) {
		return super.get(id);
	}
	
	public List<CreGuaranteeCompany> findList(CreGuaranteeCompany creGuaranteeCompany) {
		return super.findList(creGuaranteeCompany);
	}
	
	public Page<CreGuaranteeCompany> findPage(Page<CreGuaranteeCompany> page, CreGuaranteeCompany creGuaranteeCompany) {
		return super.findPage(page, creGuaranteeCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(CreGuaranteeCompany creGuaranteeCompany) {
		super.save(creGuaranteeCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreGuaranteeCompany creGuaranteeCompany) {
		super.delete(creGuaranteeCompany);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(List<String> idList) {
		super.dao.batchDelete(idList);
	}

	/**
	 * 根据申请编号查询担保公司信息
	 * @param
	 * @return
	 */
	public CreGuaranteeCompany queryApplyCompany(Map<String,String> applyRelationMap){

		CreGuaranteeCompany company  = null;
		//根据申请编号查询担保公司编号
		ApplyRelation applyRelation = applyRelationDao.findApplyRelationByApplyNo(applyRelationMap);


		if(null == applyRelation){

			return company;
		} else {

			String CompanyId = applyRelation.getCustId();
			/* String CompanyId = creApplyCompanyRelationDao.selectCompanyIdByApplyId(ApplyNo);*/

			//根据担保公司编号查询担保公司
			company = super.get(CompanyId);

			return company;
		}


	}

	public ApplyRelation queryApplyRelation(Map<String,String> applyRelationMap){

		ApplyRelation applyRelation = applyRelationDao.findApplyRelationByApplyNo(applyRelationMap);

		return applyRelation;
	}

	/**
	 * 批量借款企业下   排除已经关联的担保公司信息
	 */

	public List<CreGuaranteeCompany> findGuaranteeCompanyByCompanyInfo(String [] ids) {



		return null;
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void confirmGuranteeRelation(String contractNo){
		this.dao.confirmGuranteeRelation(contractNo);
	}
	@Transactional(value="CRE",readOnly = false)
	public void updateGuranteeInfo(CheckApprove  checkApprove,String socialTotalNum){
		CreGuaranteeCompany creGuaranteeCompany = this.dao.queryCreGuranteeCompanyByUnSocreNo(socialTotalNum);
		if (creGuaranteeCompany != null) {
			creGuaranteeCompany.setGuaranty(creGuaranteeCompany.getGuaranty().subtract(checkApprove.getContractAmount()));
			creGuaranteeCompany.setGuaranteeAmount(creGuaranteeCompany.getGuaranteeAmount().add(checkApprove.getContractAmount()));
			Integer count = Integer.valueOf(creGuaranteeCompany.getGuranteeCount())+1;
			creGuaranteeCompany.setGuranteeCount(String.valueOf(count));
			this.dao.updateGuranteeInfo(creGuaranteeCompany);
		}
	}

	@Transactional(value="CRE",readOnly = false)
	public void updateGuranteeInfoUnion(CheckApproveUnion  checkApprove,String socialTotalNum){
		CreGuaranteeCompany creGuaranteeCompany = this.dao.queryCreGuranteeCompanyByUnSocreNo(socialTotalNum);
		if (creGuaranteeCompany != null) {
			creGuaranteeCompany.setGuaranty(creGuaranteeCompany.getGuaranty().subtract(checkApprove.getContractAmount()));
			creGuaranteeCompany.setGuaranteeAmount(creGuaranteeCompany.getGuaranteeAmount().add(checkApprove.getContractAmount()));
			Integer count = Integer.valueOf(creGuaranteeCompany.getGuranteeCount())+1;
			creGuaranteeCompany.setGuranteeCount(String.valueOf(count));
			this.dao.updateGuranteeInfo(creGuaranteeCompany);
		}
	}


	public List<CreGuaranteeCompany> findAllList(){
		return this.dao.findListCregurantee();
	}

	 public List<CreGuaranteeCompany> queryCreGuaranteeCompanyList(CreGuaranteeCompany creGuaranteeCompany){
		return super.dao.queryCompanyList( creGuaranteeCompany);
	 }
	 @Transactional(value="CRE",readOnly = false)
	 public void updateGuranteFeeByApply(CheckApprove checkApprove,String applyNo){
			List<ApplyRelation> applyRelations = gedServerDao.findApplyRelationByApplyNo(applyNo);
			if (applyRelations.size() >0) {
				String gurantee= "";
				String other = "";
				String custId = "";
				for(ApplyRelation applyRelation:applyRelations){
					if (applyRelation.getRoleType() != null && applyRelation.getIsConfirm() != null && "8".equals(applyRelation.getRoleType()) && "1".equals(applyRelation.getIsConfirm())) {
						gurantee = "1";
						custId = applyRelation.getCustId();
						break;
					}
				}
				if ("1".equals(gurantee) && checkApprove != null) {
					updateGuranteInfo(checkApprove,custId);
					this.dao.updateGuantRelationApply(applyNo);
					this.dao.updateGuantRelationGurantee(applyNo);
					this.dao.updateGuantRelationGuranCompany(applyNo);
				}
			}
		}
		
		
		private void updateGuranteInfo(CheckApprove checkApprove,String custId){
			CreGuaranteeCompany company = get(custId);
			if (company != null) {
				company.setGuaranty(company.getGuaranty().add(checkApprove.getContractAmount()));
				company.setGuaranteeAmount(company.getGuaranteeAmount().subtract(checkApprove.getContractAmount()));
				Integer count = Integer.valueOf(company.getGuranteeCount())-1;
				company.setGuranteeCount(String.valueOf(count));
				this.dao.updateGuranteeInfo(company);
			}
		}
		
		private void updateGuranteInfoUnion(CheckApproveUnion checkApproveUnion,CreGuaranteeCompany company){
			if (company != null) {
				company.setGuaranty(company.getGuaranty().add(checkApproveUnion.getContractAmount()));
				if (company.getGuaranteeAmount().compareTo(checkApproveUnion.getContractAmount()) >= 0) {
					company.setGuaranteeAmount(company.getGuaranteeAmount().subtract(checkApproveUnion.getContractAmount()));
				}
				if (Integer.valueOf(company.getGuranteeCount()) >= 1) {
					Integer count = Integer.valueOf(company.getGuranteeCount())-1;
					company.setGuranteeCount(String.valueOf(count));
				}
				this.dao.updateGuranteeInfo(company);
			}
		}
		
		@Transactional(value="CRE",readOnly = false)
		public void updateGuranteFeeByApplyNoUnion(String applyNo){
			Map<String,String> param = new HashMap<>();
			param.put("applyNo",applyNo);
			List<CreApplyCompanyRelation> applyRelations = gedServerDao.findApplyCompanyRelationByApplyNo(applyNo);
			ApplyRelation applyRelation = applyRelationDao.getByApplyNoAndRoleType(applyNo,"8");
			ApplyRelation applyRelationMain = applyRelationDao.getByApplyNoAndRoleType(applyNo,"5");
			if (applyRelation != null && StringUtils.isNotBlank(applyRelation.getRoleType()) && "8".equals(applyRelation.getRoleType()) && applyRelation.getIsConfirm() !=  null &&  "1".equals(applyRelation.getIsConfirm())) {
				String companyId = applyRelation.getCustId();
				CreGuaranteeCompany company = this.dao.get(companyId);
				if (company != null && applyRelationMain != null) {
					CheckApproveUnion checkApproveUnion = this.dao.getByApplyNoAndCustId(applyNo,applyRelationMain.getCustId());
					if (checkApproveUnion != null && company != null && StringUtils.isNotBlank(company.getUnSocCreditNo())) {
						updateGuranteInfoUnion(checkApproveUnion,company);
						this.dao.updateGuantRelationApply(applyNo);
					}
				}
			}
			if (applyRelations.size() >0) {
				for(CreApplyCompanyRelation applyCompanyRelation:applyRelations){
					String companyId = applyCompanyRelation.getCustId();
					CreGuaranteeCompany company = this.dao.get(companyId);
					if (company != null) {
						CheckApproveUnion checkApproveUnion = this.dao.getByApplyNoAndCustId(applyNo,applyCompanyRelation.getCompanyId());
						if (checkApproveUnion != null && company != null && StringUtils.isNotBlank(company.getUnSocCreditNo())) {
							updateGuranteInfoUnion(checkApproveUnion, company);
							this.dao.updateGuantRelationApply(applyNo);
							this.dao.updateGuantRelationGurantee(applyNo);
							this.dao.updateGuantRelationGuranCompany(applyNo);
						}
					}
					}
				}
			
			
			
			}
		
		@Transactional(value="CRE",readOnly = false)
		public void updateGuranteeInfoByContractNo(com.resoft.credit.contract.entity.Contract contract){
			Map<String,String> param = new HashMap<>();
			param.put("custId",contract.getCustId());
			ApplyRelation applyRelation = applyRelationDao.getByApplyNoAndRoleType(contract.getApplyNo(),"8");
			ApplyRelation applyRelationMain = applyRelationDao.queryMainApplyrelation(contract.getApplyNo(), contract.getCustId(),"5");
			if (applyRelationMain != null && applyRelation != null) {
				applyRelationDao.updateApplyConfirm(contract.getApplyNo());
			}
			CreApplyCompanyRelation creApplyCompanyRelation = creApplyCompanyRelationDao.findApplyRelationByCustAndRole(contract.getApplyNo(),contract.getCustId());
			if (creApplyCompanyRelation != null) {
				creApplyCompanyRelationDao.updateApplyGurantee(contract.getApplyNo(),creApplyCompanyRelation.getCompanyId(),contract.getCustId());
			}
		}
		
		public CreGuaranteeCompany getByApplyNoAndRoleType(String applyNo, String roleType) {
			return dao.getByApplyNoAndRoleType(applyNo,roleType);
		}

		public CreGuaranteeCompany getBycheckAndRelation(String applyIdChild) {
			return dao.getBycheckAndRelation(applyIdChild);
		}
		
		@Transactional(value="CRE",readOnly = false)
		public void updateGuranteeGedAccount(CreGuaranteeCompany company){
			dao.updateGuranteeGedAccount(company);
		}

		public CreGuaranteeCompany getGuaranByCompanyIdAndType(String applyNo,String id, String roleType) {
			return dao.getGuaranByCompanyIdAndType(applyNo,id,roleType);
		}
}
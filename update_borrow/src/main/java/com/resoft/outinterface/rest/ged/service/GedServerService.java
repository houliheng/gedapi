package com.resoft.outinterface.rest.ged.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.outinterface.rest.ged.dao.GedServerDao;
import com.resoft.outinterface.rest.ged.entity.request.ConfirmGuranteeRelationRequest;
import com.resoft.outinterface.rest.ged.entity.GedCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = false)
public class GedServerService {

	@Autowired
	GedServerDao dao;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private CreGuaranteeCompanyService companyService;
	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CompanyInfoService companyInfoService;
	public void insert(GedCustInfo custInfo){
		dao.insert(custInfo);
	}
	
	
	@Transactional(value = "CRE", readOnly = false)
	public void confirmGuranteeRelation(ConfirmGuranteeRelationRequest confirmGuranteeRelationRequest){
		Map<String,String> param = new HashMap<>();
		param.put("flag",confirmGuranteeRelationRequest.getFlag());
		param.put("applyNo",confirmGuranteeRelationRequest.getApplyNo());
		param.put("custId",confirmGuranteeRelationRequest.getCustId());
		param.put("roleType",confirmGuranteeRelationRequest.getUserRole());
		if (StringUtils.isNotBlank(confirmGuranteeRelationRequest.getApplyNo()) && StringUtils.isNotBlank(confirmGuranteeRelationRequest.getApplySubNo())) {
			CheckApproveUnion checkApproveUnion = checkApproveUnionService.get(confirmGuranteeRelationRequest.getApplySubNo());
			ApplyRelation applyRelation = applyRelationService.queryMainApplyrelation(confirmGuranteeRelationRequest.getApplyNo(), checkApproveUnion.getCustId(),"5");
			if (applyRelation != null) {
				dao.updateConfirmGuranteRelationByApply(param);//更新主借人担保公司
			}
			if (applyRelation == null) {
				param.put("companyId", checkApproveUnion.getCustId());
				dao.updateConfirmGuranteRelationByGurantee(param);//更新批量借款企业的担保人
			}
			if ("1".equals(confirmGuranteeRelationRequest.getUserRole()) || "2".equals(confirmGuranteeRelationRequest.getUserRole())) {
				param.put("companyId", checkApproveUnion.getCustId());
				dao.updateConfirmRelation(param);//更新主借人、担保人、披露借款企业的担保关系
			}	
		}
		if (StringUtils.isEmpty(confirmGuranteeRelationRequest.getApplySubNo())&& StringUtils.isNotBlank(confirmGuranteeRelationRequest.getApplyNo())) {
			dao.updateConfirmGuranteRelationByApply(param);//更新主借人担保公司
			if ("1".equals(confirmGuranteeRelationRequest.getUserRole()) || "2".equals(confirmGuranteeRelationRequest.getUserRole())) {
				dao.updateConfirmRelation(param);//更新主借人、担保人、披露借款企业的担保关系
			}
			List<ApplyRelation> checkApplyRelations = dao.findApplyRelationByApplyNo(confirmGuranteeRelationRequest.getApplyNo());
			String guanrateeFlag = "";
			String otherFlag = "";
			if (checkApplyRelations.size() >0) {
				for(ApplyRelation applyRelation : checkApplyRelations){
					if (StringUtils.isNotBlank(applyRelation.getIsConfirm()) && "8".equals(applyRelation.getRoleType()) && "1".equals(applyRelation.getIsConfirm())) {
						guanrateeFlag = "1";
					}
					if (StringUtils.isNotBlank(applyRelation.getIsConfirm()) && "1".equals(applyRelation.getIsConfirm()) && !"8".equals(applyRelation.getRoleType())) {
						otherFlag = "1";
					}
				}
			}
			/*if ("1".equals(guanrateeFlag)) {
				CheckApprove checkApprove = checkApproveService.getCheckApproveBackUp(confirmGuranteeRelationRequest.getApplyNo());
				if (checkApprove != null) {
				//	companyService.updateGuranteeInfo(checkApprove,confirmGuranteeRelationRequest.getSocaliTotalNum());
					
				}
			}*/
			if ("1".equals(guanrateeFlag) && "1".equals(otherFlag)) {
				dao.updateContractNoByApplyNo(confirmGuranteeRelationRequest.getApplyNo(),"");
			}
		}
		
		if (StringUtils.isNotBlank(confirmGuranteeRelationRequest.getApplyNo()) && StringUtils.isNotBlank(confirmGuranteeRelationRequest.getApplySubNo())) {
			Map<String,String> guranTeeParam = new HashMap<>();
			guranTeeParam.put("applyNo",confirmGuranteeRelationRequest.getApplyNo());
			CheckApproveUnion checkApproveUnion = checkApproveUnionService.get(confirmGuranteeRelationRequest.getApplySubNo());
			if (checkApproveUnion != null && StringUtils.isNotBlank(checkApproveUnion.getCustId())) {
				guranTeeParam.put("companyId",checkApproveUnion.getCustId());
				List<GuaranteeRelation> guaranteeRelations = dao.getIdByApplyNoAndCompanyId(guranTeeParam);
				guranTeeParam.put("custId",confirmGuranteeRelationRequest.getCustId());
				CreApplyCompanyRelation companyRelation = dao.getGuranteeCompanyId(guranTeeParam);
				Boolean flag = false;
				if (guaranteeRelations.size() >0) {
					for(GuaranteeRelation guaranteeRelation :guaranteeRelations){
						if (StringUtils.isNotBlank(guaranteeRelation.getIsConfirm()) && "1".equals(guaranteeRelation.getIsConfirm())) {
							flag = true;
							break;
						}
					}
				}
				/*if (companyRelation != null && "1".equals(companyRelation.getIsConfirm()) && "3".equals(companyRelation.getRoleType()) && confirmGuranteeRelationRequest.getCustId().equals(companyRelation.getCustId())) {
				//	companyService.updateGuranteeInfoUnion(checkApproveUnion,confirmGuranteeRelationRequest.getSocaliTotalNum());
					
				}*/
			//	ApplyRelation applyRelationMain = applyRelationService.queryMainApplyrelation(confirmGuranteeRelationRequest.getApplyNo(), checkApproveUnion.getCustId(),"5");
				ApplyRelation applyRelation = applyRelationService.getByApplyNoAndRoleType(confirmGuranteeRelationRequest.getApplyNo(),"8");
					/*if (applyRelationMain != null && applyRelation != null && confirmGuranteeRelationRequest.getCustId().equals(applyRelation.getCustId()) && applyRelation.getIsConfirm() != null && "1".equals(applyRelation.getIsConfirm()) && applyRelation.getCustId().equals(confirmGuranteeRelationRequest.getCustId())) {
						companyService.updateGuranteeInfoUnion(checkApproveUnion,confirmGuranteeRelationRequest.getSocaliTotalNum());
					}*/
				if (flag && companyRelation != null && "1".equals(companyRelation.getIsConfirm()) && "3".equals(companyRelation.getRoleType())) {
					dao.updateContractNoByApplyNo(confirmGuranteeRelationRequest.getApplyNo(),confirmGuranteeRelationRequest.getApplySubNo());
				}
				if (flag && applyRelation != null && "1".equals(applyRelation.getIsConfirm()) && "8".equals(applyRelation.getRoleType())) {
					dao.updateContractNoByApplyNo(confirmGuranteeRelationRequest.getApplyNo(),confirmGuranteeRelationRequest.getApplySubNo());
				}
				
			}
			
		}
		
	}
}

package com.resoft.credit.guaranteeRelation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.guaranteeRelation.dao.GuaranteeRelationDao;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.rest.newged.entity.GedPushGuaranteeRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 担保信息关联表Service
 * @author jml
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class GuaranteeRelationService extends CrudService<GuaranteeRelationDao, GuaranteeRelation> {
	
	@Autowired
	private GuaranteeRelationDao guaranteeRelationDao;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	
	public GuaranteeRelation get(String id) {
		return super.get(id);
	}
	
	public List<GuaranteeRelation> findList(GuaranteeRelation guaranteeRelation) {
		return super.findList(guaranteeRelation);
	}
	
	public Page<GuaranteeRelation> findPage(Page<GuaranteeRelation> page, GuaranteeRelation guaranteeRelation) {
		return super.findPage(page, guaranteeRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(GuaranteeRelation guaranteeRelation) {
		super.save(guaranteeRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuaranteeRelation guaranteeRelation) {
		super.delete(guaranteeRelation);
	}

	public List<GuaranteeRelation> findGedGuarantee(Map<String, Object> params) {
		return dao.findGedGuarantee(params);
	}
	public List<GuaranteeRelation> findGedCompanyGuarantee(Map<String, Object> params) {
		return dao.findGedCompanyGuarantee(params);
	}
	@Transactional(readOnly = false)
	public void saveRelation(List<String> idList, String applyNo, String companyId) {
		dao.deleteAllRelation(applyNo,companyId);
		List<GuaranteeRelation>  guaranteeRelationList=new ArrayList<GuaranteeRelation>();
		for (String custId : idList) {
			GuaranteeRelation guaranteeRelation = new GuaranteeRelation();
			//CustInfo custInfo = custInfoService.get(custId);
			guaranteeRelation.setApplyNo(applyNo);
			guaranteeRelation.setRoleType("1");//1表示人
			guaranteeRelation.setCustId(custId);
			guaranteeRelation.setCompanyId(companyId);
			guaranteeRelation.preInsert();
			guaranteeRelationList.add(guaranteeRelation);
		}
		dao.batchInsert(guaranteeRelationList);
	}
	@Transactional(readOnly = false)
	public void saveCompanyRelation(List<String> idList, String applyNo, String companyId) {
		dao.deleteAllCompanyRelation(applyNo,companyId);
		List<GuaranteeRelation>  guaranteeRelationList=new ArrayList<GuaranteeRelation>();
		for (String custId : idList) {
			GuaranteeRelation guaranteeRelation = new GuaranteeRelation();
			//CustInfo custInfo = custInfoService.get(custId);
			guaranteeRelation.setApplyNo(applyNo);
			guaranteeRelation.setRoleType("2");//2表示企业
			guaranteeRelation.setCustId(custId);
			guaranteeRelation.setCompanyId(companyId);
			guaranteeRelation.preInsert();
			guaranteeRelationList.add(guaranteeRelation);
		}
		dao.batchInsert(guaranteeRelationList);
	}
	
	@Transactional(readOnly = false)
	public String checkHasRegister(String applyNo) {
		//授信的  批量借款企业下de担保企业
		List<FindIsRegisterRequest> listCompany1 = guaranteeRelationDao.getCompanySearchMessage(applyNo);
		//授信的  批量借款企业下的担保人
		List<FindIsRegisterRequest> listInfo1 = guaranteeRelationDao.getSearchMessageInfo(applyNo);
		//主借企业的担保企业,主借企业
		List<FindIsRegisterRequest> listCompany = guaranteeRelationDao.getMainCompanySearchMessage(applyNo);
		//主借企业的担保人,主借人
		List<FindIsRegisterRequest> listInfo = guaranteeRelationDao.getMainSearchMessageInfo(applyNo);
		//批量借款企业和主借企业
		List<FindIsRegisterRequest> batchList =  guaranteeRelationDao.getSearchBatchCompany(applyNo);
		String credit=null;
		//0借款人(需要社会统一代码)1自有担保人2自有担保企业3合作企业     
		for (FindIsRegisterRequest findIsRegisterRequest : listCompany) {
			String userRole = findIsRegisterRequest.getUserRole();
			if("5".equals(userRole)) {
				credit=findIsRegisterRequest.getCode();
			}
			findIsRegisterRequest.setUserRole("2");
		}
		for (FindIsRegisterRequest findIsRegisterRequest : listInfo) {
			String userRole = findIsRegisterRequest.getUserRole();
			if("3".equals(userRole)||"4".equals(userRole)||"7".equals(userRole)) {
				findIsRegisterRequest.setUserRole("1");
			}else if("6".equals(userRole)) {
				findIsRegisterRequest.setUserRole("2");
			}else if("1".equals(userRole)) {//借款人
				findIsRegisterRequest.setUserRole("0");
				findIsRegisterRequest.setType("1");
				findIsRegisterRequest.setCode(credit);
			}else {
				findIsRegisterRequest.setUserRole("3");
			}
		}
		listCompany.addAll(listInfo);
		listCompany.addAll(batchList);
		listCompany.addAll(listCompany1);
		listCompany.addAll(listInfo1);
		for (FindIsRegisterRequest findIsRegisterRequest : listCompany) {
			FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegisterRequest, applyNo);
			if(findIsRegisterResponse!=null){
				String code = findIsRegisterResponse.getCode();
				if("0".equals(code)){//冠易贷返回异常信息，没有注册
					return findIsRegisterRequest.getMobile()+"没有注册,";
				}
			}else{//查询异常
				return "查询注册失败，请刷新页面！";
			}
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public GedRegisterResponse pushGEDGuarantee(String applyNo) {
		ArrayList<GedPushGuaranteeRequest> gedPushGuaranteeList = new ArrayList<>();
		//担保人
		List<GuaranteeRelation> guaranteeRelationCustInfoList  = dao.findAllGuarantorByApplyNo(applyNo);
		for (GuaranteeRelation guaranteeRelation : guaranteeRelationCustInfoList) {
			GedPushGuaranteeRequest gedPushGuaranteeRequest = new GedPushGuaranteeRequest();
			gedPushGuaranteeRequest.setOrderCode(guaranteeRelation.getOrderCode());
			gedPushGuaranteeRequest.setMasterOrderCode(applyNo);
			gedPushGuaranteeRequest.setGuarantorType(guaranteeRelation.getRoleType());
			gedPushGuaranteeRequest.setGuarantMobile(guaranteeRelation.getMobileNum());
			gedPushGuaranteeRequest.setType("");
			gedPushGuaranteeRequest.setCode("");
			gedPushGuaranteeRequest.setBorrowGuarantorId(guaranteeRelation.getCustId());
			gedPushGuaranteeList.add(gedPushGuaranteeRequest);
		}
		//担保企业
		List<GuaranteeRelation> guaranteeRelationCompanyList  = dao.findAllGuarantCompanyByApplyNo(applyNo);
		for (GuaranteeRelation guaranteeRelation : guaranteeRelationCompanyList) {
			GedPushGuaranteeRequest gedPushGuaranteeRequest = new GedPushGuaranteeRequest();
			gedPushGuaranteeRequest.setOrderCode(guaranteeRelation.getOrderCode());
			gedPushGuaranteeRequest.setMasterOrderCode(applyNo);
			gedPushGuaranteeRequest.setGuarantorType(guaranteeRelation.getRoleType());
			gedPushGuaranteeRequest.setGuarantMobile(guaranteeRelation.getMobileNum());
			gedPushGuaranteeRequest.setType("1");
			gedPushGuaranteeRequest.setCode(guaranteeRelation.getIdNum());
			gedPushGuaranteeRequest.setBorrowGuarantorId(guaranteeRelation.getCustId());
			gedPushGuaranteeList.add(gedPushGuaranteeRequest);
		}
		//担保公司
		List<GedPushGuaranteeRequest> guaranteeCompanyList = dao.findGuarantCompany(applyNo);
		ApplyRelation applyRelation=applyRelationService.getByApplyNoAndRoleType(applyNo,"8");
		ApplyRelation mainApplyRelation=applyRelationService.getByApplyNoAndRoleType(applyNo,"5");
		GedPushGuaranteeRequest gedPushGuaranteeRequest = dao.findGuarantMainCompany(applyRelation.getCustId());
		CheckApproveUnion checkApproveUnion = checkApproveUnionService.getByApplyNoAndCustId(applyNo,mainApplyRelation.getCustId());
		gedPushGuaranteeRequest.setOrderCode(checkApproveUnion.getId());
		gedPushGuaranteeRequest.setMasterOrderCode(applyNo);
		guaranteeCompanyList.add(gedPushGuaranteeRequest);
		//GedPushGuaranteeRequest guaranteeCompanyList1 = dao.findGuarantMainCompany(applyRelation.getCustId());
		gedPushGuaranteeList.addAll(guaranteeCompanyList);
		GedRegisterResponse gedPushGuaranteeInterface = Facade.facade.gedPushGuaranteeInterface(gedPushGuaranteeList , applyNo);
		return gedPushGuaranteeInterface;
	}

	public List<String> getIdByApplyNoAndCompanyId(String companyId, String applyNo) {
		return dao.getIdByApplyNoAndCompanyId(companyId,applyNo);
	}

	public List<GuaranteeRelation> getRelationByApplyNoAndCompanyId(String companyId, String applyNo) {
		return dao.getRelationByApplyNoAndCompanyId(companyId,applyNo);
	}
	
	@Transactional(readOnly = false)
	public GedRegisterResponse pushGRGEDGuarantee(String applyNo) {
		//ApplyRelation mainCompany = applyRelationService.getByApplyNoAndRoleType(applyNo, "1");
		List<GedPushGuaranteeRequest> guarantorInfo = applyRelationService.getGEDguarantorInfo(applyNo);
		for (GedPushGuaranteeRequest gedPushGuaranteeRequest1 : guarantorInfo) {
			gedPushGuaranteeRequest1.setMasterOrderCode(applyNo);
		}
		List<GedPushGuaranteeRequest> guarantorCom = applyRelationService.getGEDguarantorCom(applyNo);
		for (GedPushGuaranteeRequest gedPushGuaranteeRequest2 : guarantorCom) {
			gedPushGuaranteeRequest2.setMasterOrderCode(applyNo);
		}
		List<GedPushGuaranteeRequest> guarantorGS = applyRelationService.getGEDguarantorGS(applyNo);
		for (GedPushGuaranteeRequest gedPushGuaranteeRequest3 : guarantorGS) {
			gedPushGuaranteeRequest3.setMasterOrderCode(applyNo);
		}
		guarantorInfo.addAll(guarantorCom);
		guarantorInfo.addAll(guarantorGS);
		GedRegisterResponse gedPushGuaranteeInterface = Facade.facade.gedPushGuaranteeInterface(guarantorInfo , applyNo);
		return gedPushGuaranteeInterface;
	}

	@Transactional(readOnly = false)
	public String checkGRHasRegister(String applyNo) {
		//主借企业的担保企业
		List<FindIsRegisterRequest> listCompany = guaranteeRelationDao.getGRMainCompanySearchMessage(applyNo);
		//主借企业的担保人
		List<FindIsRegisterRequest> listInfo = guaranteeRelationDao.getGRMainSearchMessageInfo(applyNo);
		String credit = null;
		//0借款人(需要社会统一代码)1自有担保人2自有担保企业3合作企业     
		listCompany.addAll(listInfo);
		int flag= 0;
		for (FindIsRegisterRequest findIsRegisterRequest : listCompany) {
			FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegisterRequest, applyNo);
			if(findIsRegisterResponse!=null){
				String code = findIsRegisterResponse.getCode();   //110 com  116 info
				if("110".equals(code)||"116".equals(code)){//冠易贷返回异常信息，没有注册
					flag = 1;
					break;
				}
			}else{//查询异常
				return "查询注册失败，请刷新页面！";
			}
		}
		if(flag==0) {
			return "至少有一个担保企业或者担保人账号需要注册易贷账号！";
		}else {
			return null;
		}
	}

	public void batchInsert(List<GuaranteeRelation> guaranteeRelationList) {
		dao.batchInsert(guaranteeRelationList);
	}

	public void deleteByApplyNo(String applyNo) {
		dao.deleteByApplyNo(applyNo);
	}

	/**
	 * 担保状态
	 * @param applyNo 申请编号
	 * @param applyRoleType applyRelation表中的roleType
	 * @param guaranteeRoleType guaranteeRelation表中的roleType
	 * @return 关系
	 */
	public List<GuaranteeRelation> listMainConfirmStatus(String applyNo, String applyRoleType, String guaranteeRoleType) {
		return guaranteeRelationDao.listConfirmStatus(applyNo, applyRoleType, guaranteeRoleType, null);
	}

	public List<GuaranteeRelation> listConfirmStatus(String applyNo, String applyRoleType, String guranteeRoleType, String companyId) {
		return guaranteeRelationDao.listConfirmStatus(applyNo, applyRoleType, guranteeRoleType, companyId);
	}
}
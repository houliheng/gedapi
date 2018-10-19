package com.resoft.outinterface.SV.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustLoanService;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.outinterface.SV.dao.SVRequestDao;
import com.resoft.outinterface.SV.server.entry.request.SVApplyContact;
import com.resoft.outinterface.SV.server.entry.request.SVCompany;
import com.resoft.outinterface.SV.server.entry.request.SVCompanyInfo;
import com.resoft.outinterface.SV.server.entry.request.SVCompanyRelated;
import com.resoft.outinterface.SV.server.entry.request.SVCreditAssets;
import com.resoft.outinterface.SV.server.entry.request.SVCreditCompany;
import com.resoft.outinterface.SV.server.entry.request.SVCreditCust;
import com.resoft.outinterface.SV.server.entry.request.SVCreditLineBankDetail;
import com.resoft.outinterface.SV.server.entry.request.SVCustInfo;
import com.resoft.outinterface.SV.server.entry.request.SVCustWorkInfo;
import com.resoft.outinterface.SV.server.entry.request.SVLineBank;
import com.resoft.outinterface.SV.server.entry.request.SVMortgageCar;
import com.resoft.outinterface.SV.server.entry.request.SVMortgageHouse;
import com.resoft.outinterface.SV.server.entry.request.SVPerson;
import com.resoft.outinterface.SV.server.entry.request.SVRequest;
import com.resoft.outinterface.SV.server.entry.request.SVRequestBody;
import com.resoft.outinterface.SV.server.entry.request.SVRequestBodyInfo;
import com.resoft.outinterface.SV.server.entry.request.SVRequestHead;
import com.resoft.outinterface.SV.server.entry.request.url.SVAfterBorrowerUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVAuditInfoUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVCoBorrowerUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVContractSignUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVGuBorrowerUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVGuCompanyUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVMainBorrowerUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVMainCompanyUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVMortApplicationInfoUrl;
import com.resoft.outinterface.SV.server.entry.request.url.SVRequestBaseData;
import com.resoft.outinterface.SV.server.entry.request.url.SVRequestBodyUrl;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.DateUtils;

@Service(value = "svRequestService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVRequestService {

	private static final Logger logger = LoggerFactory.getLogger(SVRequestService.class);

	@Autowired
	private SVRequestDao svRequestDao;

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private SVMortgageCarService svMortgageCarService;

	@Autowired
	private SVMortgageHouseService svMortgageHouseService;

	@Autowired
	private SVApplyContactService svApplyContactService;

	@Autowired
	private SVCompanyInfoService svCompanyInfoService;

	@Autowired
	private SVCustInfoService svCustInfoService;

	@Autowired
	private SVCustWorkInfoService svCustWorkInfoService;

	@Autowired
	private SVCompanyRelatedService svCompanyRelatedService;

	@Autowired
	private CreditCustLoanService creditCustLoanService;

	@Autowired
	private CreditCustService creditCustService;

	@Autowired
	private CreditLineBankService creditLineBankService;

	/**
	 * sv请求体信息
	 * 
	 * @param applyNo
	 * @return
	 */
	private SVRequestBodyUrl getSVRequestBodyUrlByApplyNo(String applyNo) {
		SVRequestBodyUrl bodyUrl = new SVRequestBodyUrl();

		return bodyUrl;
	};

	/**
	 * 查询征信银行卡流水信息
	 * 
	 * @param custId
	 * @param roleType
	 * @param applyNo
	 * @return lineBank
	 */
	private List<SVLineBank> findSVLineBankList(String custId, String roleType, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("custId", custId);
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		return svRequestDao.findSVLineBankList(params);
	}

	/**
	 * 查询企业征信信息
	 * 
	 * @param companyId
	 * @param role
	 * @param applyNo
	 * @return lineBank
	 */
	private SVCreditCompany getCompanyCredit(String companyId, String roleType, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("companyId", companyId);
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		return svRequestDao.getCompanyCredit(params);
	}

	/**
	 * 查询个人征信信息
	 * 
	 * @param custId
	 * @param roleType
	 * @param applyNo
	 * @return
	 */
	private SVCreditCust getPersonCredit(String custId, String roleType, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("custId", custId);
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		return svRequestDao.getPersonCredit(params);
	}

	/**
	 * 查询资产列表
	 * 
	 * @param assetsOwnerId
	 * @param role
	 * @param applyNo
	 * @return List<SVCreditAssets>
	 */
	private List<SVCreditAssets> findAssetsList(String assetsOwnerId, String roleType, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("assetsOwnerId", assetsOwnerId);
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		return svRequestDao.findAssetsList(params);
	}

	/**
	 * 查询SVCompany列表
	 * 
	 * @param roleType
	 * @param role
	 * @param applyNo
	 * @return List<SVCompany>
	 */
	private List<SVCompany> findSVCompanyList(String roleType, String role, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		List<Map<String, String>> svCompanyMapList = svRequestDao.findSVCompanyList(params);
		List<SVCompany> svCompanyList = new ArrayList<SVCompany>();
		for (int i = 0; i < svCompanyMapList.size(); i++) {
			Map<String, String> map = svCompanyMapList.get(i);
			SVCompany svCompany = new SVCompany();
			// svCompany.setName(map.get("name"));
			svCompany.setRole(role);
			String companyId = map.get("companyId");
			List<SVLineBank> lineBank = this.findSVLineBankList(companyId, roleType, applyNo);
			SVCreditCompany companyCredit = this.getCompanyCredit(companyId, roleType, applyNo);
			List<SVCreditAssets> assets = this.findAssetsList(companyId, roleType, applyNo);
			svCompany.setCompanyCredit(companyCredit);
			svCompany.setAssets(assets);
			svCompany.setLineBank(lineBank);
			svCompanyList.add(svCompany);

		}
		return svCompanyList;
	};

	/**
	 * 查询SVPerson列表
	 * 
	 * @param roleType
	 * @param role
	 * @param applyNo
	 * @return List<SVCompany>
	 */
	private List<SVPerson> findSVPersonList(String roleType, String role, String applyNo) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("roleType", roleType);
		params.put("applyNo", applyNo);
		List<Map<String, String>> svCompanyMapList = svRequestDao.findSVPersonList(params);
		List<SVPerson> svPersonList = new ArrayList<SVPerson>();
		for (int i = 0; i < svCompanyMapList.size(); i++) {
			Map<String, String> map = svCompanyMapList.get(i);
			SVPerson svPerson = new SVPerson();
			svPerson.setRole(role);
			String custId = map.get("custId");
			List<SVLineBank> lineBank = this.findSVLineBankList(custId, roleType, applyNo);
			SVCreditCust svCreditCust = this.getPersonCredit(custId, roleType, applyNo);
			List<SVCreditAssets> assets = this.findAssetsList(custId, roleType, applyNo);
			svPerson.setPersonCredit(svCreditCust);
			svPerson.setAssets(assets);
			svPerson.setLineBank(lineBank);
			svPersonList.add(svPerson);

		}
		return svPersonList;
	};

	/**
	 * sv请求体信息
	 * 
	 * @param applyNo
	 * @return bodyInfo
	 */
	private SVRequestBodyInfo getSVRequestBodyInfoByApplyNo(String applyNo) {
		SVRequestBodyInfo bodyInfo = new SVRequestBodyInfo();
		// ------ company
		List<SVCompany> company = new ArrayList<SVCompany>();
		// 1.主借企业
		List<SVCompany> svMainCompanyList = findSVCompanyList(Constants.ROLE_TYPE_ZJQY, Constants.SV_ROLE_C_MAIN, applyNo);
		company.add(svMainCompanyList.get(0));
		// 2.担保企业
		List<SVCompany> svGuaranCompanyInfoList = findSVCompanyList(Constants.ROLE_TYPE_DBQY, Constants.SV_ROLE_C_GUARAN, applyNo);
		if (svGuaranCompanyInfoList != null && svGuaranCompanyInfoList.size() > 0) {
			company.addAll(svGuaranCompanyInfoList);
		}
		if (company != null && company.size() > 0) {
			bodyInfo.setCompany(company);
		}
		// ------ person
		List<SVPerson> person = new ArrayList<SVPerson>();
		// 1.主借人
		List<SVPerson> svMainCust = this.findSVPersonList(Constants.ROLE_TYPE_ZJR, Constants.SV_ROLE_P_MAIN, applyNo);
		person.add(svMainCust.get(0));
		// 2.共借人
		List<SVPerson> svCoCust = this.findSVPersonList(Constants.ROLE_TYPE_GJR, Constants.SV_ROLE_P_CO, applyNo);
		if (svCoCust != null && svCoCust.size() > 0) {
			person.addAll(svCoCust);
		}
		// 3.担保人
		List<SVPerson> svGuaranCust = this.findSVPersonList(Constants.ROLE_TYPE_DBR, Constants.SV_ROLE_P_GUARAN, applyNo);
		if (svGuaranCust != null && svGuaranCust.size() > 0) {
			person.addAll(svGuaranCust);
		}
		bodyInfo.setPerson(person);
		return bodyInfo;
	};

	/**
	 * sv请求体
	 * 
	 * @param applyNo
	 * @return
	 */
	private SVRequestBody getSVRequestBodyByApplyNo(String applyNo) {
		SVRequestBody body = new SVRequestBody();
		// SVRequestBodyInfo
		SVRequestBodyInfo bodyInfo = this.getSVRequestBodyInfoByApplyNo(applyNo);
		// SVRequestBodyUrl
		SVRequestBodyUrl bodyUrl = this.getSVRequestBodyUrlByApplyNo(applyNo);
//		body.setBodyInfo(bodyInfo);
		body.setBodyUrl(bodyUrl);
		return body;
	};

	/**
	 * sv请求头
	 * 
	 * @param applyNo
	 * @return
	 */
	private SVRequestHead getSVRequestHeadByApplyNo(String applyNo) {
		SVRequestHead header = new SVRequestHead();
		header.setApplyNo(applyNo);
//		header.setContractNo("");
		header.setSeqNo("");
		return header;
	};

	/**
	 * sv请求对象
	 * 
	 * @param applyNo
	 * @return
	 */
	public SVRequest getSVrequestByApplyNo(String applyNo) {
		SVRequest svRequest = new SVRequest();
		SVRequestHead header = this.getSVRequestHeadByApplyNo(applyNo);
		SVRequestBody body = this.getSVRequestBodyByApplyNo(applyNo);
		svRequest.setHeader(header);
		svRequest.setBody(body);
		return svRequest;
	}

	/**
	 * 保存SV请求数据
	 * 
	 * @param svRequest
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveSVRequest(SVRequest svRequest) {
		SVRequestHead header = svRequest.getHeader();
		String applyNo = header.getApplyNo();
		String seqNo = header.getSeqNo();
//		String contractNo = header.getContractNo();
		Map<String, Object> baseInfo = Maps.newHashMap();
		baseInfo.put("applyNo", applyNo);
		baseInfo.put("seqNo", seqNo);
//		baseInfo.put("contractNo", contractNo);
		baseInfo.put("createDate", DateUtils.getDate());
		baseInfo.put("delFlag", Constants.DEL_FLAG_DEFAULT);
		SVRequestBody body = svRequest.getBody();
//		SVRequestBodyInfo bodyInfo = body.getBodyInfo();
//		List<SVCompany> company = bodyInfo.getCompany();
//		List<SVPerson> person = bodyInfo.getPerson();
//		List<SVMortgageCar> car = bodyInfo.getMortgageCar();
//		List<SVMortgageHouse> house = bodyInfo.getMortgageHouse();
//		if (company != null && company.size() > 0) {
//			this.saveCompany(baseInfo, company);
//		}
//		if (person != null && person.size() > 0) {
//			this.savePerson(baseInfo, person);
//		}
//		try {
//			if (car != null && car.size() > 0) {
//				this.saveCar(baseInfo, car);
//			}
//		} catch (Exception e) {
//			logger.info("车辆，非必填信息插入ift表失败" + e.toString());
//		}
//		try {
//			if (house != null && house.size() > 0) {
//				this.saveHouse(baseInfo, house);
//			}
//		} catch (Exception e) {
//			logger.info("车辆，非必填信息插入ift表失败" + e.toString());
//		}
		SVRequestBodyUrl bodyUrl = body.getBodyUrl();
		if (bodyUrl != null) {
			this.saveBodyUrl(baseInfo, bodyUrl);
		}

	}

	/**
	 * 保存抵质押物车辆信息
	 * 
	 * @param baseInfo
	 * @param car
	 */
	private void saveCar(Map<String, Object> baseInfo, List<SVMortgageCar> car) {
		svMortgageCarService.save(baseInfo, car);
	}

	/**
	 * 保存抵质押物房产信息
	 * 
	 * @param baseInfo
	 * @param house
	 */
	private void saveHouse(Map<String, Object> baseInfo, List<SVMortgageHouse> house) {
		svMortgageHouseService.save(baseInfo, house);
	}

	/**
	 * 保存个人征信信息
	 * 
	 * @param baseInfo
	 * @param creditCustList
	 */
	private void saveCreditCust(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVCreditCust> creditCustList = new ArrayList<SVCreditCust>();
		for (int i = 0; i < person.size(); i++) {
			SVPerson svPerson = person.get(i);
			if(svPerson == null){
				continue;
			}
			// 根据idNum查询客户信息
			Map<String, String> params = Maps.newConcurrentMap();
			params.put("idNum", svPerson.getIdNum());
			CustInfo cust = custInfoService.findCustInfoByIdCard(params);
			SVCreditCust creditCust = svPerson.getPersonCredit();
			creditCust.setCustInfo(cust);
			creditCust.preInsert();
			creditCustList.add(creditCust);
		}
		if (creditCustList != null && creditCustList.size() > 0) {
			baseInfo.put("list", creditCustList);
			svRequestDao.saveCreditCust(baseInfo);
		}
	}

	/**
	 * 保存企业征信信息
	 * 
	 * @param baseInfo
	 * @param creditCompanyList
	 */
	private void saveCreditCompany(Map<String, Object> baseInfo, List<SVCompany> company) {
		List<SVCreditCompany> creditCompanyList = new ArrayList<SVCreditCompany>();
		for (int i = 0; i < company.size(); i++) {
			SVCompany svCompany = company.get(i);
			if(svCompany == null){
				continue;
			}
			// 根据regNo查询公司信息
			Map<String, Object> params = Maps.newConcurrentMap();
			if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getUnSocCreditNo() != null) {
				params.put("unSocCreditNo", svCompany.getSvCompanyInfo().getUnSocCreditNo());
			} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getOrganizationNo() != null) {
				params.put("organizationNo", svCompany.getSvCompanyInfo().getOrganizationNo());
			} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getBusiLicRegNo() != null) {
				params.put("busiLicRegNo", svCompany.getSvCompanyInfo().getBusiLicRegNo());
			} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getRegisterCode() != null) {
				params.put("registerCode", svCompany.getSvCompanyInfo().getRegisterCode());
			} else {
				continue;
			}
			List<CompanyInfo> comp = companyInfoService.findListByParams(params);
			SVCreditCompany creditCompany = svCompany.getCompanyCredit();
			creditCompany.setCompanyInfo(comp.get(0));
			creditCompanyList.add(creditCompany);
		}
		if (creditCompanyList != null && creditCompanyList.size() > 0) {
			baseInfo.put("list", creditCompanyList);
			svRequestDao.saveCreditCompany(baseInfo);
		}

	}

	/**
	 * 保存银行卡信息
	 * 
	 * @param baseInfo
	 * @param lineBankList
	 */
	private void savePersonLineBank(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVLineBank> svLineBankList = new ArrayList<SVLineBank>();
		List<SVCreditLineBankDetail> svCreditLineBankDetailList = new ArrayList<SVCreditLineBankDetail>();
		for (int i = 0; i < person.size(); i++) {
			SVPerson svPerson = person.get(i);
			List<SVLineBank> svLineBanks = svPerson.getLineBank();
			for (int j = 0; j < svLineBanks.size(); j++) {
				SVLineBank svLineBank = svLineBanks.get(j);
				if(svLineBank == null){
					continue;
				}
				// 根据idNum查询客户信息
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("idNum", svPerson.getIdNum());
				CustInfo cust = custInfoService.findCustInfoByIdCard(params);
				svLineBank.setCustInfo(cust);
				if (cust != null) {
					baseInfo.put("custId", cust.getId());
					baseInfo.put("custName", cust.getCustName());
					baseInfo.put("idNum", cust.getIdNum());
					baseInfo.put("idType", cust.getIdType());
				}
				svLineBankList.add(svLineBank);
			}
		}

		if (svLineBankList != null && svLineBankList.size() > 0) {
			baseInfo.put("list", svLineBankList);
			svRequestDao.saveLineBank(baseInfo);
		}

		// 每张银行卡明细
		for (int i = 0; i < person.size(); i++) {
			SVPerson svPerson = person.get(i);
			List<SVLineBank> svLineBanks = svPerson.getLineBank();
			for (int j = 0; j < svLineBanks.size(); j++) {
				SVLineBank svLineBank = svLineBanks.get(j);
				if(svLineBank == null){
					continue;
				}
				CreditLineBank creditLineBank = new CreditLineBank();
				// 根据applyNo、idNum、bankcardNo查询银行卡
				creditLineBank.setApplyNo(baseInfo.get("applyNo").toString());
				creditLineBank.setIdNum(svLineBank.getIdNum());
				creditLineBank.setBankcardNo(svLineBank.getBankcardNo());
				List<CreditLineBank> creditLineBankList = creditLineBankService.findList(creditLineBank);
				if (creditLineBankList != null && creditLineBankList.size() > 0) {
					// 入库前处理明细信息
					List<SVCreditLineBankDetail> tempDetailList = svLineBank.getCreditLineBankDetail();
					for (int k = 0; k < tempDetailList.size(); k++) {
						SVCreditLineBankDetail tempDetail = tempDetailList.get(k);
						tempDetail.setCreditLineBank(creditLineBankList.get(0));
						svCreditLineBankDetailList.add(tempDetail);
					}
				}
			}
		}

		if (svCreditLineBankDetailList != null && svCreditLineBankDetailList.size() > 0) {
			baseInfo.put("list", svCreditLineBankDetailList);
			svRequestDao.saveLineBankDetail(baseInfo);
		}

	}

	/**
	 * 保存银行卡信息
	 * 
	 * @param baseInfo
	 * @param lineBankList
	 */
	private void saveCompanyLineBank(Map<String, Object> baseInfo, List<SVCompany> company) {
		List<SVLineBank> svLineBankList = new ArrayList<SVLineBank>();
		List<SVCreditLineBankDetail> svCreditLineBankDetailList = new ArrayList<SVCreditLineBankDetail>();
		for (int i = 0; i < company.size(); i++) {
			SVCompany svCompany = company.get(i);
			if (svCompany != null) {
				List<SVLineBank> svLineBanks = svCompany.getLineBank();
				if (svLineBanks != null) {
					for (int j = 0; j < svLineBanks.size(); j++) {
						SVLineBank svLineBank = svLineBanks.get(j);
						if(svLineBank == null){
							continue;
						}
						// 根据regNo查询公司信息
						Map<String, Object> params = Maps.newConcurrentMap();
						if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getUnSocCreditNo() != null) {
							params.put("unSocCreditNo", svCompany.getSvCompanyInfo().getUnSocCreditNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getOrganizationNo() != null) {
							params.put("organizationNo", svCompany.getSvCompanyInfo().getOrganizationNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getBusiLicRegNo() != null) {
							params.put("busiLicRegNo", svCompany.getSvCompanyInfo().getBusiLicRegNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getRegisterCode() != null) {
							params.put("registerCode", svCompany.getSvCompanyInfo().getRegisterCode());
						} else {
							continue;
						}
						List<CompanyInfo> comp = companyInfoService.findListByParams(params);
						if (comp != null && comp.size() > 0) {
							svLineBank.setCompanyInfo(comp.get(i));
							baseInfo.put("custId", comp.get(i).getId());
							baseInfo.put("custName", comp.get(i).getBusiRegName());
							if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getUnSocCreditNo() != null) {
								baseInfo.put("idNum", comp.get(i).getUnSocCreditNo());
							} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getOrganizationNo() != null) {
								baseInfo.put("idNum", comp.get(i).getOrganizationNo());
							} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getBusiLicRegNo() != null) {
								baseInfo.put("idNum", comp.get(i).getBusiLicRegNo());
							} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getRegisterCode() != null) {
								baseInfo.put("idNum", comp.get(i).getRegisterCode());
							}
						}
						svLineBankList.add(svLineBank);
					}
				}
			}
		}
		if (svLineBankList != null && svLineBankList.size() > 0) {
			baseInfo.put("list", svLineBankList);
			svRequestDao.saveLineBank(baseInfo);
		}

		// 每张银行卡明细
		for (int i = 0; i < company.size(); i++) {
			SVCompany svCompany = company.get(i);
			if (svCompany != null) {
				List<SVLineBank> svLineBanks = svCompany.getLineBank();
				if (svLineBanks != null) {
					for (int j = 0; j < svLineBanks.size(); j++) {
						SVLineBank svLineBank = svLineBanks.get(j);
						if(svLineBank == null){
							continue;
						}
						CreditLineBank creditLineBank = new CreditLineBank();
						// 根据applyNo、idNum、bankcardNo查询银行卡
						creditLineBank.setApplyNo(baseInfo.get("applyNo").toString());
						creditLineBank.setIdNum(svLineBank.getIdNum());
						creditLineBank.setBankcardNo(svLineBank.getBankcardNo());
						List<CreditLineBank> creditLineBankList = creditLineBankService.findList(creditLineBank);
						if (creditLineBankList != null && creditLineBankList.size() > 0) {
							// 入库前处理明细信息
							List<SVCreditLineBankDetail> tempDetailList = svLineBank.getCreditLineBankDetail();
							for (int k = 0; k < tempDetailList.size(); k++) {
								SVCreditLineBankDetail tempDetail = tempDetailList.get(k);
								tempDetail.setCreditLineBank(creditLineBankList.get(0));
								svCreditLineBankDetailList.add(tempDetail);
							}
						}
					}
				}
			}

		}

		if (svCreditLineBankDetailList != null && svCreditLineBankDetailList.size() > 0) {
			baseInfo.put("list", svCreditLineBankDetailList);
			svRequestDao.saveLineBankDetail(baseInfo);
		}
	}

	/**
	 * 保存资产信息
	 * 
	 * @param baseInfo
	 * @param lineBankList
	 */
	private void savePersonAssets(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVCreditAssets> creditAssetsList = new ArrayList<SVCreditAssets>();
		for (int i = 0; i < person.size(); i++) {
			SVPerson svPerson = person.get(i);
			if (svPerson != null) {
				List<SVCreditAssets> assets = svPerson.getAssets();
				if (assets != null) {
					for (int j = 0; j < assets.size(); j++) {
						SVCreditAssets asset = assets.get(j);
						if (asset == null) {
							continue;
						}
						// 根据idNum查询客户信息
						Map<String, String> params = Maps.newConcurrentMap();
						params.put("idNum", svPerson.getIdNum());
						CustInfo cust = custInfoService.findCustInfoByIdCard(params);
						asset.setCustInfo(cust);
						creditAssetsList.add(asset);
					}
				}
			}
		}
		if (creditAssetsList != null && creditAssetsList.size() > 0) {
			baseInfo.put("list", creditAssetsList);
			svRequestDao.saveAssets(baseInfo);
		}
	}

	/**
	 * 保存资产信息
	 * 
	 * @param baseInfo
	 * @param lineBankList
	 */
	private void saveCompanyAssets(Map<String, Object> baseInfo, List<SVCompany> company) {
		List<SVCreditAssets> creditAssetsList = new ArrayList<SVCreditAssets>();
		for (int i = 0; i < company.size(); i++) {
			SVCompany svCompany = company.get(i);
			if (svCompany != null) {
				List<SVCreditAssets> assets = svCompany.getAssets();
				if (assets != null && assets.size() > 0) {
					for (int j = 0; j < assets.size(); j++) {
						SVCreditAssets asset = assets.get(j);
						if (asset == null) {
							continue;
						}
						// 根据regNo查询公司信息
						Map<String, Object> params = Maps.newConcurrentMap();
						if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getUnSocCreditNo() != null) {
							params.put("unSocCreditNo", svCompany.getSvCompanyInfo().getUnSocCreditNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getOrganizationNo() != null) {
							params.put("organizationNo", svCompany.getSvCompanyInfo().getOrganizationNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getBusiLicRegNo() != null) {
							params.put("busiLicRegNo", svCompany.getSvCompanyInfo().getBusiLicRegNo());
						} else if (svCompany != null && svCompany.getSvCompanyInfo() != null && svCompany.getSvCompanyInfo().getRegisterCode() != null) {
							params.put("registerCode", svCompany.getSvCompanyInfo().getRegisterCode());
						} else {
							continue;
						}
						List<CompanyInfo> comp = companyInfoService.findListByParams(params);
						asset.setCompanyInfo(comp.get(0));
						creditAssetsList.add(asset);
					}
				}
			}
		}
		if (creditAssetsList != null && creditAssetsList.size() > 0) {
			baseInfo.put("list", creditAssetsList);
			svRequestDao.saveAssets(baseInfo);
		}
	}

	/**
	 * 保存企业征信及企业银行卡流水信息
	 * 
	 * @param company
	 */
	private void saveCompany(Map<String, Object> baseInfo, List<SVCompany> company) {
		try {
			List<SVCompanyRelated> allCompanyRelated = this.allCompanyRelated(company);
			if (allCompanyRelated != null && allCompanyRelated.size() > 0) {
				this.saveCompanyRelated(baseInfo, allCompanyRelated);
			}
			List<SVCompanyInfo> allCompanyInfo = this.allCompanyInfo(company);
			if (allCompanyInfo != null && allCompanyInfo.size() > 0) {
				this.saveCompanyInfo(baseInfo, allCompanyInfo);
			}
		} catch (Exception e) {
			logger.info("企业信息，非必填信息插入ift表失败" + e.toString(), e);
		}
		if (company != null && company.size() > 0) {
			this.saveCreditCompany(baseInfo, company);
			this.saveCompanyLineBank(baseInfo, company);
			this.saveCompanyAssets(baseInfo, company);
		}
	}

	/**
	 * 所有待入库企业基本信息
	 * 
	 * @param company
	 * @return
	 */
	private List<SVCompanyInfo> allCompanyInfo(List<SVCompany> company) {
		List<SVCompanyInfo> allCompanyInfo = new ArrayList<SVCompanyInfo>();
		for (int i = 0; i < company.size(); i++) {
			if (company.get(i) != null) {
				SVCompanyInfo temp = company.get(i).getSvCompanyInfo();
				allCompanyInfo.add(temp);
			}
		}
		return allCompanyInfo;
	}

	/**
	 * 保存企业基本信息
	 * 
	 * @param baseInfo
	 * @param allCompanyInfo
	 */
	private void saveCompanyInfo(Map<String, Object> baseInfo, List<SVCompanyInfo> allCompanyInfo) {
		svCompanyInfoService.save(baseInfo, allCompanyInfo);
	}

	/**
	 * 保存关联企业信息
	 * 
	 * @param baseInfo
	 * @param allCompanyRelated
	 */
	private void saveCompanyRelated(Map<String, Object> baseInfo, List<SVCompanyRelated> allCompanyRelated) {
		svCompanyRelatedService.save(baseInfo, allCompanyRelated);
	}

	/**
	 * 所有待入库关联企业信息
	 * 
	 * @param company
	 * @return
	 */
	private List<SVCompanyRelated> allCompanyRelated(List<SVCompany> company) {
		List<SVCompanyRelated> allCompanyRelated = new ArrayList<SVCompanyRelated>();
		for (int i = 0; i < company.size(); i++) {
			if (company.get(i) != null && company.get(i).getCompanyRelated() != null && company.get(i).getCompanyRelated().size() > 0) {
				allCompanyRelated.addAll(company.get(i).getCompanyRelated());
			}
		}
		return allCompanyRelated;
	}

	/**
	 * 保存个人征信及个人银行卡流水信息
	 * 
	 * @param person
	 */
	private void savePerson(Map<String, Object> baseInfo, List<SVPerson> person) {
		try {
			List<SVApplyContact> allContact = this.allContact(baseInfo, person);
			if (allContact != null && allContact.size() > 0) {
				this.saveApplyContact(baseInfo, allContact);
			}
			List<SVCustInfo> allCust = this.allCust(baseInfo, person);
			if (allCust != null && allCust.size() > 0) {
				this.saveCustInfo(baseInfo, allCust);
			}
			List<SVCustWorkInfo> allCustWork = this.allCustWork(baseInfo, person);
			if (allCustWork != null && allCustWork.size() > 0) {
				this.saveCustWorkInfo(baseInfo, allCustWork);
			}
		} catch (Exception e) {
			logger.info("个人信息，非必填信息插入ift表失败" + e.toString());
		}
		if (person != null && person.size() > 0) {
			this.saveCreditCust(baseInfo, person);
			this.savePersonLineBank(baseInfo, person);
			this.savePersonAssets(baseInfo, person);
		}

	}

	/**
	 * 所有待入库工作信息
	 * 
	 * @param baseInfo
	 * @param person
	 * @return
	 */
	private List<SVCustWorkInfo> allCustWork(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVCustWorkInfo> allContact = new ArrayList<SVCustWorkInfo>();
		for (int i = 0; i < person.size(); i++) {
			if (person.get(i) != null) {
				allContact.add(person.get(i).getCustWorkInfo());
			}
		}
		return allContact;
	}

	/**
	 * 所有待入库联系人
	 * 
	 * @param baseInfo
	 * @param person
	 * @return
	 */
	private List<SVApplyContact> allContact(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVApplyContact> allContact = new ArrayList<SVApplyContact>();
		for (int i = 0; i < person.size(); i++) {
			if (person.get(i) != null && person.get(i).getApplyContact() != null) {
				allContact.addAll(person.get(i).getApplyContact());
			}
		}
		return allContact;
	}

	/**
	 * 所有待入库客户基本信息
	 * 
	 * @param baseInfo
	 * @param person
	 * @return
	 */
	private List<SVCustInfo> allCust(Map<String, Object> baseInfo, List<SVPerson> person) {
		List<SVCustInfo> allContact = new ArrayList<SVCustInfo>();
		for (int i = 0; i < person.size(); i++) {
			if (person.get(i) != null) {
				allContact.add(person.get(i).getCustInfo());
			}
		}
		return allContact;
	}

	/**
	 * 保存联系人信息
	 * 
	 * @param baseInfo
	 * @param contactList
	 */
	private void saveApplyContact(Map<String, Object> baseInfo, List<SVApplyContact> contactList) {
		svApplyContactService.save(baseInfo, contactList);
	}

	/**
	 * 保存客户基本信息
	 * 
	 * @param baseInfo
	 * @param custInfo
	 */
	private void saveCustInfo(Map<String, Object> baseInfo, List<SVCustInfo> custInfo) {
		svCustInfoService.save(baseInfo, custInfo);
	}

	/**
	 * 保存工作信息
	 * 
	 * @param baseInfo
	 * @param custWorkInfo
	 */
	private void saveCustWorkInfo(Map<String, Object> baseInfo, List<SVCustWorkInfo> custWorkInfo) {
		svCustWorkInfoService.save(baseInfo, custWorkInfo);
	}

	/**
	 * 保存影像所有Url
	 * 
	 * @param bodyUrl
	 */
	private void saveBodyUrl(Map<String, Object> baseInfo, SVRequestBodyUrl bodyUrl) {
		try {
 			List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();

			List<SVMainBorrowerUrl> mainBorrowerPic = bodyUrl.getMainBorrowerPic();
			List<SVRequestBaseData> mainBorrowerPicList = null;
			if(mainBorrowerPic != null && mainBorrowerPic.size() > 0){
				mainBorrowerPicList = transSVMainBorrowerUrl(mainBorrowerPic);
			}
			if (mainBorrowerPicList != null && mainBorrowerPicList.size() > 0) {
				allSVRequestBaseData.addAll(mainBorrowerPicList);
			}

			List<SVCoBorrowerUrl> coBorrowerPic = bodyUrl.getCoBorrowerPic();
			List<SVRequestBaseData> coBorrowerPicList = null;
            if(coBorrowerPic != null && coBorrowerPic.size() > 0){
            	coBorrowerPicList = transCoBorrowerPic(coBorrowerPic);
            }
			if (coBorrowerPicList != null && coBorrowerPicList.size() > 0) {
				allSVRequestBaseData.addAll(coBorrowerPicList);
			}

			List<SVGuBorrowerUrl> guBorrowerPic = bodyUrl.getGuBorrowerPic();
			List<SVRequestBaseData> guBorrowerPicList = null;
            if(guBorrowerPic != null && guBorrowerPic.size() > 0){
            	guBorrowerPicList = transGuBorrowerUrl(guBorrowerPic);
            }
			if (guBorrowerPicList != null && guBorrowerPicList.size() > 0) {
				allSVRequestBaseData.addAll(guBorrowerPicList);
			}

			List<SVGuCompanyUrl> guCompanyPic = bodyUrl.getGuCompanyPic();
			List<SVRequestBaseData> guCompanyPicList = null;
			if(guCompanyPic != null && guCompanyPic.size() > 0){
				guCompanyPicList = transGuCompanyPic(guCompanyPic);
			}
			if (guCompanyPicList != null && guCompanyPicList.size() > 0) {
				allSVRequestBaseData.addAll(guCompanyPicList);
			}

			List<SVMainCompanyUrl> mainCompanyPic = bodyUrl.getMainCompanyPic();
			List<SVRequestBaseData> mainCompanyPicList = null;
			if(mainCompanyPic != null && mainCompanyPic.size() > 0){
				mainCompanyPicList = transMainCompanyPic(mainCompanyPic);
			}
			if (mainCompanyPicList != null && mainCompanyPicList.size() > 0) {
				allSVRequestBaseData.addAll(mainCompanyPicList);
			}

			List<SVMortApplicationInfoUrl> mortApplicationPic = bodyUrl.getMortApplicationPic();
			List<SVRequestBaseData> mortApplicationPicList = null;
			if(mortApplicationPic != null && mortApplicationPic.size() > 0){
				mortApplicationPicList = transMortApplicationPic(mortApplicationPic);
			}
			if (mortApplicationPicList != null && mortApplicationPicList.size() > 0) {
				allSVRequestBaseData.addAll(mortApplicationPicList);
			}

			List<SVAuditInfoUrl> auditInfoPic = bodyUrl.getAuditInfoPic();
			List<SVRequestBaseData> auditInfoPicList = null;
			if(auditInfoPic != null && auditInfoPic.size() > 0){
				auditInfoPicList = transAuditInfoPic(auditInfoPic);
			}
			if (auditInfoPicList != null && auditInfoPicList.size() > 0) {
				allSVRequestBaseData.addAll(auditInfoPicList);
			}

			List<SVRequestBaseData> gqGet = bodyUrl.getGQGet();
			if (gqGet != null && gqGet.size() > 0) {
				allSVRequestBaseData.addAll(gqGet);
			}

			List<SVContractSignUrl> contractSignPic = bodyUrl.getContractSignPic();
			List<SVRequestBaseData> contractSignPicList = null;
			if(contractSignPic != null && contractSignPic.size() > 0){
				contractSignPicList = transContractSignPic(contractSignPic);
			}
			if (contractSignPicList != null && contractSignPicList.size() > 0) {
				allSVRequestBaseData.addAll(contractSignPicList);
			}

			List<SVAfterBorrowerUrl> afterBorrowerPic = bodyUrl.getAfterBorrowerPic();
			List<SVRequestBaseData> afterBorrowerPicList = null;
			if(afterBorrowerPic != null && afterBorrowerPic.size() > 0){
				afterBorrowerPicList = transAfterBorrowerPic(afterBorrowerPic);
			}
			if (afterBorrowerPicList != null && afterBorrowerPicList.size() > 0) {
				allSVRequestBaseData.addAll(afterBorrowerPicList);
			}

			List<SVRequestBaseData> pdfPic = bodyUrl.getPdfPic();
			if (pdfPic != null && pdfPic.size() > 0) {
				allSVRequestBaseData.addAll(pdfPic);
			}

			List<SVRequestBaseData> financialStateExcel = bodyUrl.getFinancialStateExcel();
			if (financialStateExcel != null && financialStateExcel.size() > 0) {
				allSVRequestBaseData.addAll(financialStateExcel);
			}

			baseInfo.put("allSVRequestBaseData", allSVRequestBaseData);

			if (allSVRequestBaseData != null && allSVRequestBaseData.size() > 0) {
				svRequestDao.saveSVRequestBaseData(baseInfo);
			}

		} catch (Exception e) {
			logger.error("sv影像数据入库错误！", e);
		}
	}

	/**
	 * 将主借人影像信息翻译为SVRequestBaseData
	 * 
	 * @param svRequestBaseDataList
	 */
	public List<SVRequestBaseData> transSVMainBorrowerUrl(List<SVMainBorrowerUrl> svMainBorrowerUrlList) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < svMainBorrowerUrlList.size(); i++) {
			SVMainBorrowerUrl svMainBorrowerUrl = svMainBorrowerUrlList.get(i);

			List<SVRequestBaseData> picBefore = svMainBorrowerUrl.getPicBefore();
			if (picBefore != null && picBefore.size() > 0) {
				allSVRequestBaseData.addAll(picBefore);
			}

			List<SVRequestBaseData> identify = svMainBorrowerUrl.getIdentify();
			if (identify != null && identify.size() > 0) {
				allSVRequestBaseData.addAll(identify);
			}

			List<SVRequestBaseData> creditLine = svMainBorrowerUrl.getCreditLine();
			if (creditLine != null && creditLine.size() > 0) {
				allSVRequestBaseData.addAll(creditLine);
			}

			List<SVRequestBaseData> houseInfo = svMainBorrowerUrl.getHouseInfo();
			if (houseInfo != null && houseInfo.size() > 0) {
				allSVRequestBaseData.addAll(houseInfo);
			}

			List<SVRequestBaseData> carInfo = svMainBorrowerUrl.getCarInfo();
			if (carInfo != null && carInfo.size() > 0) {
				allSVRequestBaseData.addAll(carInfo);
			}

			List<SVRequestBaseData> otherAssets = svMainBorrowerUrl.getOtherAssets();
			if (otherAssets != null && otherAssets.size() > 0) {
				allSVRequestBaseData.addAll(otherAssets);
			}

			List<SVRequestBaseData> livingHouseInfo = svMainBorrowerUrl.getLivingHouseInfo();
			if (livingHouseInfo != null && livingHouseInfo.size() > 0) {
				allSVRequestBaseData.addAll(livingHouseInfo);
			}

			List<SVRequestBaseData> netCheck = svMainBorrowerUrl.getNetCheck();
			if (netCheck != null && netCheck.size() > 0) {
				allSVRequestBaseData.addAll(netCheck);
			}

			List<SVRequestBaseData> workInfo = svMainBorrowerUrl.getWorkInfo();
			if (workInfo != null && workInfo.size() > 0) {
				allSVRequestBaseData.addAll(workInfo);
			}

			List<SVRequestBaseData> homeInterview = svMainBorrowerUrl.getHomeInterview();
			if (homeInterview != null && homeInterview.size() > 0) {
				allSVRequestBaseData.addAll(homeInterview);
			}

			List<SVRequestBaseData> otherInfo = svMainBorrowerUrl.getOtherInfo();
			if (otherInfo != null && otherInfo.size() > 0) {
				allSVRequestBaseData.addAll(otherInfo);
			}

		}
		return allSVRequestBaseData;
	}

	/**
	 * 将CoBorrowerPic转化为List<SVRequestBaseData>
	 * 
	 * @param coBorrowerPic
	 * @return
	 */
	public List<SVRequestBaseData> transCoBorrowerPic(List<SVCoBorrowerUrl> coBorrowerPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < coBorrowerPic.size(); i++) {
			SVCoBorrowerUrl svCoBorrowerUrl = coBorrowerPic.get(i);

			List<SVRequestBaseData> identify = svCoBorrowerUrl.getIdentify();
			if(identify != null && identify.size() > 0){
				allSVRequestBaseData.addAll(identify);
			}

			List<SVRequestBaseData> creditLine = svCoBorrowerUrl.getCreditLine();
			if(creditLine != null && creditLine.size() > 0){
				allSVRequestBaseData.addAll(creditLine);
			}

			List<SVRequestBaseData> houseInfo = svCoBorrowerUrl.getHouseInfo();
			if(houseInfo != null && houseInfo.size() > 0){
				allSVRequestBaseData.addAll(houseInfo);
			}

			List<SVRequestBaseData> carInfo = svCoBorrowerUrl.getCarInfo();
			if(carInfo != null && carInfo.size() > 0){
				allSVRequestBaseData.addAll(carInfo);
			}

			List<SVRequestBaseData> otherAssets = svCoBorrowerUrl.getOtherAssets();
			if(otherAssets != null && otherAssets.size() > 0){
				allSVRequestBaseData.addAll(otherAssets);
			}

			List<SVRequestBaseData> livingHouseInfo = svCoBorrowerUrl.getLivingHouseInfo();
			if(livingHouseInfo != null && livingHouseInfo.size() > 0){
				allSVRequestBaseData.addAll(livingHouseInfo);
			}

			List<SVRequestBaseData> netCheck = svCoBorrowerUrl.getNetCheck();
			if(netCheck != null && netCheck.size() >0){
				allSVRequestBaseData.addAll(netCheck);
			}

			List<SVRequestBaseData> workInfo = svCoBorrowerUrl.getWorkInfo();
			if(workInfo != null && workInfo.size() > 0){
				allSVRequestBaseData.addAll(workInfo);
			}

			List<SVRequestBaseData> homeInterview = svCoBorrowerUrl.getHomeInterview();
			if(homeInterview != null && homeInterview.size() >0){
				allSVRequestBaseData.addAll(homeInterview);
			}

			List<SVRequestBaseData> otherInfo = svCoBorrowerUrl.getOtherInfo();
			if(otherInfo != null && otherInfo.size() > 0){
				allSVRequestBaseData.addAll(otherInfo);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transGuBorrowerUrl(List<SVGuBorrowerUrl> guBorrowerPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < guBorrowerPic.size(); i++) {
			SVGuBorrowerUrl svGuBorrowerUrl = guBorrowerPic.get(i);

			List<SVRequestBaseData> identify = svGuBorrowerUrl.getIdentify();
			if(identify != null && identify.size() > 0){
				allSVRequestBaseData.addAll(identify);
			}

			List<SVRequestBaseData> creditLine = svGuBorrowerUrl.getCreditLine();
			if(creditLine != null && creditLine.size() > 0){
				allSVRequestBaseData.addAll(creditLine);
			}

			List<SVRequestBaseData> houseInfo = svGuBorrowerUrl.getHouseInfo();
			if(houseInfo != null && houseInfo.size() >0){
				allSVRequestBaseData.addAll(houseInfo);
			}

			List<SVRequestBaseData> carInfo = svGuBorrowerUrl.getCarInfo();
			if(carInfo != null && carInfo.size() > 0){
				allSVRequestBaseData.addAll(carInfo);
			}

			List<SVRequestBaseData> otherAssets = svGuBorrowerUrl.getOtherAssets();
			if(otherAssets != null && otherAssets.size() > 0){
				allSVRequestBaseData.addAll(otherAssets);
			}

			List<SVRequestBaseData> livingHouseInfo = svGuBorrowerUrl.getLivingHouseInfo();
			if(livingHouseInfo != null && livingHouseInfo.size() > 0){
				allSVRequestBaseData.addAll(livingHouseInfo);
			}

			List<SVRequestBaseData> netCheck = svGuBorrowerUrl.getNetCheck();
			if(netCheck != null && netCheck.size() >0){
				allSVRequestBaseData.addAll(netCheck);
			}

			List<SVRequestBaseData> workInfo = svGuBorrowerUrl.getWorkInfo();
			if(workInfo != null && workInfo.size() > 0){
				allSVRequestBaseData.addAll(workInfo);
			}

			List<SVRequestBaseData> homeInterview = svGuBorrowerUrl.getHomeInterview();
			if(homeInterview != null && homeInterview.size() > 0){
				allSVRequestBaseData.addAll(homeInterview);
			}

			List<SVRequestBaseData> otherInfo = svGuBorrowerUrl.getOtherInfo();
			if(otherInfo != null && otherInfo.size() > 0){
				allSVRequestBaseData.addAll(otherInfo);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transGuCompanyPic(List<SVGuCompanyUrl> guCompanyPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < guCompanyPic.size(); i++) {
			SVGuCompanyUrl svGuCompanyUrl = guCompanyPic.get(i);

			List<SVRequestBaseData> busConInfo = svGuCompanyUrl.getBusConInfo();
			if(busConInfo != null && busConInfo.size() > 0){
				allSVRequestBaseData.addAll(busConInfo);
			}

			List<SVRequestBaseData> coCompanyInfo = svGuCompanyUrl.getCoCompanyInfo();
			if(coCompanyInfo != null && coCompanyInfo.size() > 0){
				allSVRequestBaseData.addAll(coCompanyInfo);
			}

			List<SVRequestBaseData> companyAssets = svGuCompanyUrl.getCompanyAssets();
			if(companyAssets != null && companyAssets.size() >0){
				allSVRequestBaseData.addAll(companyAssets);
			}

			List<SVRequestBaseData> companyReport = svGuCompanyUrl.getCompanyReport();
			if(companyReport != null && companyReport.size() > 0){
				allSVRequestBaseData.addAll(companyReport);
			}

			List<SVRequestBaseData> compayBaseInfo = svGuCompanyUrl.getCompayBaseInfo();
			if(compayBaseInfo != null && compayBaseInfo.size() > 0){
				allSVRequestBaseData.addAll(compayBaseInfo);
			}

			List<SVRequestBaseData> feeList = svGuCompanyUrl.getFeeList();
			if(feeList != null && feeList.size() > 0){
				allSVRequestBaseData.addAll(feeList);
			}

			List<SVRequestBaseData> hisInfoStockStruc = svGuCompanyUrl.getHisInfoStockStruc();
			if(hisInfoStockStruc != null && hisInfoStockStruc.size() > 0){
				allSVRequestBaseData.addAll(hisInfoStockStruc);
			}

			List<SVRequestBaseData> interviewStatus = svGuCompanyUrl.getInterviewStatus();
			if(interviewStatus != null && interviewStatus.size() > 0){
				allSVRequestBaseData.addAll(interviewStatus);
			}

			List<SVRequestBaseData> creditLine = svGuCompanyUrl.getCreditLine();
			if(creditLine != null && creditLine.size() > 0){
				allSVRequestBaseData.addAll(creditLine);
			}

			List<SVRequestBaseData> netCheck = svGuCompanyUrl.getNetCheck();
			if(netCheck != null && netCheck.size() > 0){
				allSVRequestBaseData.addAll(netCheck);
			}

			List<SVRequestBaseData> otherInfo = svGuCompanyUrl.getOtherInfo();
			if(otherInfo != null && otherInfo.size() > 0){
				allSVRequestBaseData.addAll(otherInfo);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transMainCompanyPic(List<SVMainCompanyUrl> mainCompanyPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < mainCompanyPic.size(); i++) {
			SVMainCompanyUrl svMainCompanyUrl = mainCompanyPic.get(i);

			List<SVRequestBaseData> busConInfo = svMainCompanyUrl.getBusConInfo();
			if(busConInfo != null && busConInfo.size() > 0){
				allSVRequestBaseData.addAll(busConInfo);
			}

			List<SVRequestBaseData> coCompanyInfo = svMainCompanyUrl.getCoCompanyInfo();
			if(coCompanyInfo != null && coCompanyInfo.size() > 0){
				allSVRequestBaseData.addAll(coCompanyInfo);
			}

			List<SVRequestBaseData> companyAssets = svMainCompanyUrl.getCompanyAssets();
			if(companyAssets != null && companyAssets.size() > 0){
				allSVRequestBaseData.addAll(companyAssets);
			}

			List<SVRequestBaseData> companyReport = svMainCompanyUrl.getCompanyReport();
			if(companyReport != null && companyReport.size() > 0){
				allSVRequestBaseData.addAll(companyReport);
			}

			List<SVRequestBaseData> compayBaseInfo = svMainCompanyUrl.getCompayBaseInfo();
			if(compayBaseInfo != null && compayBaseInfo.size() > 0){
				allSVRequestBaseData.addAll(compayBaseInfo);
			}

			List<SVRequestBaseData> feeList = svMainCompanyUrl.getFeeList();
			if(feeList != null && feeList.size() > 0){
				allSVRequestBaseData.addAll(feeList);
			}

			List<SVRequestBaseData> hisInfoStockStruc = svMainCompanyUrl.getHisInfoStockStruc();
			if(hisInfoStockStruc != null && hisInfoStockStruc.size() > 0){
				allSVRequestBaseData.addAll(hisInfoStockStruc);
			}

			List<SVRequestBaseData> interviewStatus = svMainCompanyUrl.getInterviewStatus();
			if(interviewStatus != null && interviewStatus.size() > 0){
				allSVRequestBaseData.addAll(interviewStatus);
			}

			List<SVRequestBaseData> creditLine = svMainCompanyUrl.getCreditLine();
			if(creditLine != null && creditLine.size() > 0){
				allSVRequestBaseData.addAll(creditLine);
			}

			List<SVRequestBaseData> netCheck = svMainCompanyUrl.getNetCheck();
			if(netCheck != null && netCheck.size() > 0){
				allSVRequestBaseData.addAll(netCheck);
			}

			List<SVRequestBaseData> otherInfo = svMainCompanyUrl.getOtherInfo();
			if(otherInfo != null && otherInfo.size() >0){
				allSVRequestBaseData.addAll(otherInfo);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transMortApplicationPic(List<SVMortApplicationInfoUrl> mortApplicationPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < mortApplicationPic.size(); i++) {
			SVMortApplicationInfoUrl svMortApplicationInfoUrl = mortApplicationPic.get(i);

			List<SVRequestBaseData> carProperty = svMortApplicationInfoUrl.getCarProperty();
			if(carProperty != null && carProperty.size() > 0){
				allSVRequestBaseData.addAll(carProperty);
			}

			List<SVRequestBaseData> houseProperty = svMortApplicationInfoUrl.getHouseProperty();
			if(houseProperty != null && houseProperty.size() > 0){
				allSVRequestBaseData.addAll(houseProperty);
			}

			List<SVRequestBaseData> mortBelonging = svMortApplicationInfoUrl.getMortBelonging();
			if(mortBelonging != null && mortBelonging.size() > 0){
				allSVRequestBaseData.addAll(mortBelonging);
			}

			List<SVRequestBaseData> mortInterview = svMortApplicationInfoUrl.getMortInterview();
			if(mortInterview != null && mortInterview.size() > 0){
				allSVRequestBaseData.addAll(mortInterview);
			}

			List<SVRequestBaseData> mortOther = svMortApplicationInfoUrl.getMortOther();
			if(mortOther != null && mortOther.size() > 0){
				allSVRequestBaseData.addAll(mortOther);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transAuditInfoPic(List<SVAuditInfoUrl> auditInfoPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < auditInfoPic.size(); i++) {
			SVAuditInfoUrl svAuditInfoUrl = auditInfoPic.get(i);

			List<SVRequestBaseData> custNotify = svAuditInfoUrl.getCustNotify();
			if(custNotify != null && custNotify.size() > 0){
				allSVRequestBaseData.addAll(custNotify);
			}

			List<SVRequestBaseData> otherForm = svAuditInfoUrl.getOtherForm();
			if(otherForm != null && otherForm.size() > 0){
				allSVRequestBaseData.addAll(otherForm);
			}

			List<SVRequestBaseData> signedAudit = svAuditInfoUrl.getSignedAudit();
			if(signedAudit != null && signedAudit.size() > 0){
				allSVRequestBaseData.addAll(signedAudit);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transContractSignPic(List<SVContractSignUrl> contractSignPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < contractSignPic.size(); i++) {
			SVContractSignUrl svContractSignPic = contractSignPic.get(i);

			List<SVRequestBaseData> borContract = svContractSignPic.getBorContract();
			if(borContract != null && borContract.size() > 0){
				allSVRequestBaseData.addAll(borContract);
			}

			List<SVRequestBaseData> collectionConfirm = svContractSignPic.getCollectionConfirm();
			if(collectionConfirm != null && collectionConfirm.size() > 0){
				allSVRequestBaseData.addAll(collectionConfirm);
			}

			List<SVRequestBaseData> driveBook = svContractSignPic.getDriveBook();
			if(driveBook != null && driveBook.size() > 0){
				allSVRequestBaseData.addAll(driveBook);
			}

			List<SVRequestBaseData> facRentCon = svContractSignPic.getFacRentCon();
			if(facRentCon != null && facRentCon.size() > 0){
				allSVRequestBaseData.addAll(facRentCon);
			}

			List<SVRequestBaseData> goodsPurSaleCon = svContractSignPic.getGoodsPurSaleCon();
			if(goodsPurSaleCon != null && goodsPurSaleCon.size() > 0){
				allSVRequestBaseData.addAll(goodsPurSaleCon);
			}

			List<SVRequestBaseData> houseConcord = svContractSignPic.getHouseConcord();
			if(houseConcord != null && houseConcord.size() > 0){
				allSVRequestBaseData.addAll(houseConcord);
			}

			List<SVRequestBaseData> houseHe = svContractSignPic.getHouseHe();
			if(houseHe != null && houseHe.size() > 0){
				allSVRequestBaseData.addAll(houseHe);
			}

			List<SVRequestBaseData> houseInvestReport = svContractSignPic.getHouseInvestReport();
			if(houseInvestReport != null && houseInvestReport.size() > 0){
				allSVRequestBaseData.addAll(houseInvestReport);
			}

			List<SVRequestBaseData> houseRentCon = svContractSignPic.getHouseRentCon();
			if(houseRentCon != null && houseRentCon.size() > 0){
				allSVRequestBaseData.addAll(houseRentCon);
			}

			List<SVRequestBaseData> inAssignNotice = svContractSignPic.getInAssignNotice();
			if(inAssignNotice != null && inAssignNotice.size() > 0){
				allSVRequestBaseData.addAll(inAssignNotice);
			}

			List<SVRequestBaseData> insureContract = svContractSignPic.getInsureContract();
			if(insureContract != null && insureContract.size() > 0){
				allSVRequestBaseData.addAll(insureContract);
			}

			List<SVRequestBaseData> landRentCon = svContractSignPic.getLandRentCon();
			if(landRentCon != null && landRentCon.size() > 0){
				allSVRequestBaseData.addAll(landRentCon);
			}

			List<SVRequestBaseData> landUseAssContract = svContractSignPic.getLandUseAssContract();
			if(landUseAssContract != null && landUseAssContract.size() > 0){
				allSVRequestBaseData.addAll(landUseAssContract);
			}

			List<SVRequestBaseData> mangeTransfer = svContractSignPic.getMangeTransfer();
			if(mangeTransfer != null && mangeTransfer.size() > 0){
				allSVRequestBaseData.addAll(mangeTransfer);
			}

			List<SVRequestBaseData> mechBus = svContractSignPic.getMechBus();
			if(mechBus != null && mechBus.size() > 0){
				allSVRequestBaseData.addAll(mechBus);
			}

			List<SVRequestBaseData> mort3AppDeal = svContractSignPic.getMort3AppDeal();
			if(mort3AppDeal != null && mort3AppDeal.size() > 0){
				allSVRequestBaseData.addAll(mort3AppDeal);
			}

			List<SVRequestBaseData> mortAppDeal = svContractSignPic.getMortAppDeal();
			if(mortAppDeal != null && mortAppDeal.size() > 0){
				allSVRequestBaseData.addAll(mortAppDeal);
			}

			List<SVRequestBaseData> mortContract = svContractSignPic.getMortContract();
			if(mortContract != null && mortContract.size() > 0){
				allSVRequestBaseData.addAll(mortContract);
			}

			List<SVRequestBaseData> oldCarContract = svContractSignPic.getOldCarContract();
			if(oldCarContract != null && oldCarContract.size() > 0){
				allSVRequestBaseData.addAll(oldCarContract);
			}

			List<SVRequestBaseData> pledgeContract = svContractSignPic.getPledgeContract();
			if(pledgeContract != null && pledgeContract.size() > 0){
				allSVRequestBaseData.addAll(pledgeContract);
			}

			List<SVRequestBaseData> purSaleContract = svContractSignPic.getPurSaleContract();
			if(purSaleContract != null && purSaleContract.size() > 0){
				allSVRequestBaseData.addAll(purSaleContract);
			}

			List<SVRequestBaseData> refundInsureCon = svContractSignPic.getRefundInsureCon();
			if(refundInsureCon != null && refundInsureCon.size() > 0){
				allSVRequestBaseData.addAll(refundInsureCon);
			}

			List<SVRequestBaseData> rentInAssign = svContractSignPic.getRentInAssign();
			if(rentInAssign != null && rentInAssign.size() > 0){
				allSVRequestBaseData.addAll(rentInAssign);
			}

			List<SVRequestBaseData> serviceContract = svContractSignPic.getServiceContract();
			if(serviceContract != null && serviceContract.size() > 0){
				allSVRequestBaseData.addAll(serviceContract);
			}

			List<SVRequestBaseData> shareholderResln = svContractSignPic.getShareholderResln();
			if(shareholderResln != null && shareholderResln.size() > 0){
				allSVRequestBaseData.addAll(shareholderResln);
			}

			List<SVRequestBaseData> stockAssign = svContractSignPic.getStockAssign();
			if(stockAssign != null && stockAssign.size() > 0){
				allSVRequestBaseData.addAll(stockAssign);
			}

			List<SVRequestBaseData> stockMort = svContractSignPic.getStockMort();
			if(stockMort != null && stockMort.size() > 0){
				allSVRequestBaseData.addAll(stockMort);
			}

			List<SVRequestBaseData> veAdminMortRegist = svContractSignPic.getVeAdminMortRegist();
			if(veAdminMortRegist != null && veAdminMortRegist.size() > 0){
				allSVRequestBaseData.addAll(veAdminMortRegist);
			}

			List<SVRequestBaseData> vehicleQualified = svContractSignPic.getVehicleQualified();
			if(vehicleQualified != null && vehicleQualified.size() > 0){
				allSVRequestBaseData.addAll(vehicleQualified);
			}

			List<SVRequestBaseData> vehicleRegist = svContractSignPic.getVehicleRegist();
			if(vehicleRegist != null && vehicleRegist.size() > 0){
				allSVRequestBaseData.addAll(vehicleRegist);
			}

			List<SVRequestBaseData> warehouseRent = svContractSignPic.getWarehouseRent();
			if(warehouseRent != null && warehouseRent.size() > 0){
				allSVRequestBaseData.addAll(warehouseRent);
			}

			List<SVRequestBaseData> warehouseSupvi = svContractSignPic.getWarehouseSupvi();
			if(warehouseSupvi != null && warehouseSupvi.size() > 0){
				allSVRequestBaseData.addAll(warehouseSupvi);
			}
		}
		return allSVRequestBaseData;
	}

	public List<SVRequestBaseData> transAfterBorrowerPic(List<SVAfterBorrowerUrl> afterBorrowerPic) {
		List<SVRequestBaseData> allSVRequestBaseData = new ArrayList<SVRequestBaseData>();
		for (int i = 0; i < afterBorrowerPic.size(); i++) {
			SVAfterBorrowerUrl svAfterBorrowerUrl = afterBorrowerPic.get(i);

			List<SVRequestBaseData> dailyCheck = svAfterBorrowerUrl.getDailyCheck();
			if(dailyCheck != null && dailyCheck.size() > 0){
				allSVRequestBaseData.addAll(dailyCheck);
			}

			List<SVRequestBaseData> overdue = svAfterBorrowerUrl.getOverdue();
			if(overdue != null && overdue.size() > 0){
				allSVRequestBaseData.addAll(overdue);
			}

			List<SVRequestBaseData> reaudit = svAfterBorrowerUrl.getReaudit();
			if(reaudit != null && reaudit.size() > 0){
				allSVRequestBaseData.addAll(reaudit);
			}

			List<SVRequestBaseData> remind = svAfterBorrowerUrl.getRemind();
			if(remind != null && remind.size() > 0){
				allSVRequestBaseData.addAll(remind);
			}
		}
		return allSVRequestBaseData;
	}

}

package com.resoft.credit.conclusion.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.applyInfo.dao.ApplyInfoDao;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contactInfo.dao.ContactInfoDao;
import com.resoft.credit.custinfo.dao.CustInfoDao;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.credit.mortgageHouseInfo.dao.MortgageHouseInfoDao;
import com.resoft.credit.mortgageOtherInfo.dao.MortgageOtherInfoDao;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.resoft.credit.stockWebCheck.entity.StockWebCheck;
import com.resoft.credit.stockWebCheck.service.StockWebCheckService;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ConlusionService {

	private static final Logger logger = LoggerFactory.getLogger("ConlusionService");
	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private VideoUploadDao videoUploadDao;
	@Autowired
	private TaskCenterService taskCenterService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ApplyRelationDao applyRelationDao;
	@Autowired
	private ApplyInfoDao applyInfoDao;
	@Autowired
	private ContactInfoDao contactInfoDao;
	@Autowired
	private MortgageCarInfoDao mortgageCarInfoDao;
	@Autowired
	private MortgageHouseInfoDao mortgageHouseInfoDao;
	@Autowired
	private MortgageOtherInfoDao mortgageOtherInfoDao;
	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private StockWebCheckService stockWebCheckService;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;// 企业信息service
	@Autowired
	private CustInfoService custInfoService;// 客户基本信息service
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private CheckApproveService checkApproveService;
	public long getRelationCount(String applyNo, String roleType) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", roleType);
		return applyRelationDao.getRelationCount(params);
	}

	public long getCompanyRelationCount(String applyNo, String roleType) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", roleType);
		return applyRelationDao.getCompanyRelationCount(params);
	}

	/**
	 * 存在主借人
	 * */
	public boolean isValidMain(String applyNo) {
		return getRelationCount(applyNo, Constants.ROLE_TYPE_ZJR) > 0;
	}

	/**
	 * 存在配偶
	 * */
	public boolean isValidMate(String applyNo) {
		return getRelationCount(applyNo, Constants.ROLE_TYPE_MATE) > 0;
	}

	/**
	 * 存在共借人
	 * */
	public boolean isValidGjr(String applyNo) {
		return getRelationCount(applyNo, Constants.ROLE_TYPE_GJR) > 0;
	}

	/**
	 * 存在担保人
	 * */
	public boolean isValidDbr(String applyNo) {
		return getRelationCount(applyNo, Constants.ROLE_TYPE_DBR) > 0;
	}

	/**
	 * 存在担保企业
	 * */
	public boolean isValidDbqy(String applyNo) {
		return getCompanyRelationCount(applyNo, Constants.ROLE_TYPE_DBQY) > 0;
	}

	/**
	 * 信用 混合  联系人需要大于4条
	 * */
	public boolean isValidContact(String applyNo) {
		String contactCount = DictUtils.getDictValue(Constants.CONTACT_COUNT_DESC_DY, Constants.CONTACT_COUNT, "0");
		return contactInfoDao.getContactCount(applyNo) >= new Long(contactCount);
	}
	
	/**
	 * 抵押 联系人需要大于4条
	 * */
	public boolean isValidContactDY(String applyNo) {
		String contactCount = DictUtils.getDictValue(Constants.CONTACT_COUNT_DESC_DY, Constants.CONTACT_COUNT, "0");
		return contactInfoDao.getContactCount(applyNo) >= new Long(contactCount);
	}

	/**
	 * 存在抵质押物--车辆
	 * */
	public boolean isValidMortCar(String applyNo) {
		return mortgageCarInfoDao.getCarCount(applyNo) > 0;
	}

	/**
	 * 存在抵质押物--房产
	 * */
	public boolean isValidMortHouse(String applyNo) {
		return mortgageHouseInfoDao.getHoseCount(applyNo) > 0;
	}

	/**
	 * 存在抵质押物--其他
	 * */
	public boolean isValidMortOther(String applyNo) {
		return mortgageOtherInfoDao.getOtherCount(applyNo) > 0;
	}

	/**
	 * 存在主借人
	 * */
	public String validateMain(String applyNo) {
		String message = "【主借人信息不完整】";
		if (isValidMain(applyNo)) {
			message = "";
		}
		return message;
	}

	/**
	 * 存在配偶或共借人
	 * */
	public String validateMateOrGjr(String applyNo) {
		String message = "【配偶或共借人信息不完整】";
		if (isValidMate(applyNo) || isValidGjr(applyNo)) {
			message = "";
		}
		return message;
	}

	/**
	 * 存在担保人或担保企业
	 * */
	public String validateDbrOrDbqy(String applyNo) {
		String message = "【担保人或担保企业信息不完整】";
		if (isValidDbr(applyNo) || isValidDbqy(applyNo)) {
			message = "";
		}
		return message;
	}

	/**
	 * 是否存在借款信息
	 * */
	public boolean validateApplyInfo(String applyNo) {
		boolean flag = true;
		ApplyInfo applyInfo = applyInfoDao.findApplyInfoByApplyNo(applyNo);
		if (StringUtils.isNull(applyInfo)) {
			flag = false;
		}
		return flag;

	}

	/**
	 * 联系人需要大于6条
	 * 
	 * */
	public String validateContact(String applyNo) {
		String message = "【联系人信息须填写4条以上】";
		if (isValidContact(applyNo)) {
			message = "";
		}
		return message;
	}
	
	/**
	 * 联系人需要大于4条
	 * 
	 * */
	public String validateContactDY(String applyNo) {
		String message = "【联系人信息须填写4条以上】";
		if (isValidContactDY(applyNo)) {
			message = "";
		}
		return message;
	}

	/**
	 * 存在抵质押物
	 * */
	public String validateMortgage(String applyNo) {
		String message = "【抵质押物信息不完整】";
		if (isValidMortCar(applyNo) || isValidMortHouse(applyNo) || isValidMortOther(applyNo)) {
			message = "";
		}
		return message;
	}

	public String validateBeforeSubmit(String productTypeCode, String applyNo) {
		boolean flag = validateApplyInfo(applyNo);
		  //非联合授信没有注册账号需要卡流程节点
		ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		/*if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegisterByApplyNo.getProcDefKey())){
		   Map<String, String> param = Maps.newConcurrentMap();
		   param.put("applyNo",applyNo);
		   Map<String, String> resultCom = applyInfoService.findGEDNeedCom(param);
		   if(resultCom==null){
			   return "请填写主借人和主借企业！";
		   }else{
			    String unSocCreditNo = resultCom.get("unSocCreditNo");
			    FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",unSocCreditNo);
			    FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
			    if(findIsRegisterResponse==null){
			    	return "同冠易贷连接异常";
			    }
			    int code = findIsRegisterResponse.getCode();
			    if(code==0){//冠易贷返回异常信息，没有注册
			    	return "请先注册冠易贷账号！！";
			    }
		   }
		}*/
		String message = null;
		if (!flag) {
			message = "【借款申请信息不完整】";
			return message;
		}
		message = validateMain(applyNo);
		if (StringUtils.isNotEmpty(message)) {
			return message;
		}
		// 主借人已婚时，校验配偶信息
		try {
			List<CustInfo> mainCustList = custInfoDao.findMainBorrowerByApplyNo(applyNo);
			String isHasMate = mainCustList.get(0).getWedStatus();
			if (isHasMate.equals(Constants.WED_STATUS_YH)) {
				boolean mateFlag = isValidMate(applyNo);
				if (!mateFlag) {
					return "【配偶信息不完整】";
				}
			}
		} catch (Exception e) {
			logger.error("查询主借人信息出错！", e);
			return "【主借人信息不完整】";
		}
		// 查询是否有担保人
		ApplyInfo applyInfo = applyInfoDao.findApplyInfoByApplyNo(applyNo);
		String isHaveGurrantor = applyInfo.getIsHaveAssure();
		if (Constants.PRODUCT_TYPE_XY.equals(productTypeCode) || Constants.PRODUCT_TYPE_CG.equals(productTypeCode)) {
			if (Constants.YES_NO_Y.equals(isHaveGurrantor)) {
				message += validateDbrOrDbqy(applyNo);
			}
			message += validateContact(applyNo);
		} else if (Constants.PRODUCT_TYPE_DY.equals(productTypeCode)) {
			message = validateMortgage(applyNo) + validateContactDY(applyNo);
		} else if (Constants.PRODUCT_TYPE_HH.equals(productTypeCode)) {
			if (Constants.YES_NO_Y.equals(isHaveGurrantor)) {
				message += validateDbrOrDbqy(applyNo);
			}
			message += validateContact(applyNo) + validateMortgage(applyNo);
		}else if(Constants.PRODUCT_TYPE_ZGJH.equals(productTypeCode)){
			if (Constants.YES_NO_Y.equals(isHaveGurrantor)) {
				message += validateDbrOrDbqy(applyNo);
			}
			message += validateContact(applyNo);
			message += validateStockWebCheck(applyNo);
		}
		return message;
	}
	
	private String validateStockWebCheck(String applyNo) {
		ApplyRegister  applyRegister = new ApplyRegister();
		applyRegister.setApplyNo(applyNo);
		List<ApplyRegister> list = applyRegisterService.findList(applyRegister);
		if (!list.isEmpty()) {
			applyRegister=list.get(0);//判断是否债股结合产品，3在字典表是债股结合类型
			if(Constants.PRODUCT_TYPE_ZGJH.equals(applyRegister.getApplyProductTypeCode())){
				StockWebCheck stockWebCheck = stockWebCheckService.getByApplyNo(applyNo);
				if(stockWebCheck==null)
					return "【一次网查信息不完整】";
			}
		}
		return "";
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public AjaxView save(ActTaskParam actTaskParam, String passFlag, String suggestion,String checkApproveProductType){
		AjaxView ajaxView = new AjaxView();
		if (Constants.PRODUCT_TYPE_ZGJH.equals(checkApproveProductType)  && "no".equalsIgnoreCase(passFlag) && Constants.UTASK_SQLR.equalsIgnoreCase(actTaskParam.getTaskDefKey()) ) {
			Map<String,String> param = Maps.newConcurrentMap();
			param.put("status","1");
			param.put("applyNo", actTaskParam.getApplyNo());
			param.put("taskDefKey",actTaskParam.getTaskDefKey());
			param.put("passFlag",passFlag);
			stockTaskReceiveService.updateStockTaskReciveState(param);
		}
		try {
			ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
			processSuggestionInfo.setPassFlag(passFlag);
			processSuggestionInfo.setSuggestionDesc(suggestion);
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			if ("yes".equalsIgnoreCase(passFlag)) {
				ApplyRegister applyRegister = new ApplyRegister();
				applyRegister.setApplyNo(actTaskParam.getApplyNo());
				List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
				if (!registerList.isEmpty()) {
					applyRegister = registerList.get(0);
				}
				String message = validateBeforeSubmit(applyRegister.getApplyProductTypeCode(), actTaskParam.getApplyNo());
				if (StringUtils.isBlank(message)) {
					//推送信息到冠易贷
					/*if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){//非联合授信
						AddOrderResponse addOrderResponse = sendOrderToGED(applyRegister);
						if(addOrderResponse==null){
							return ajaxView.setFailed().setMessage("推送冠易贷工单失败！");
						}else {
							 applyRegister.setSendGED("1");
						     applyRegisterService.saveSendGEDStatus(applyRegister);
						}
					}*/
					//------------------------
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + suggestion, "提交", null);
					actTaskService.appointAssigne(UserUtils.getUser().getLoginName(), actTaskParam.getTaskId());
					Map<String, Object> params = Maps.newHashMap();
					params.put("applyNo", actTaskParam.getApplyNo());
					params.put("taskDefKey", actTaskParam.getTaskDefKey());
					params.put("lockFlag", "1");
					videoUploadDao.lockVideoMessageByApplyNoAndTaskDefKey(params);
					processSuggestionInfoService.save(processSuggestionInfo);
					processSuggestionInfoService.insertFlag(processSuggestionInfo,actTaskParam.getTaskDefKey());
					ajaxView.setSuccess().setMessage("提交成功！");
					//creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_shz,0,null);
				} else {
					ajaxView.setFailed().setMessage(message);
				}
			} else {
				// 结束流程
				processSuggestionInfo.setPassFlag("no");
				processSuggestionInfoService.save(processSuggestionInfo);
				processSuggestionInfoService.insertFlag(processSuggestionInfo,actTaskParam.getTaskDefKey());
				taskCenterService.finishProcessInstanceByRefuseStatus(actTaskParam, suggestion);
				processSuggestionInfoService.backMessageToGED(actTaskParam.getApplyNo(),null, Constants.GED_APPLY_STATUS_SQJJ,null);
				ajaxView.setSuccess();
				ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
				if(!(ar.getProcDefKey()!=null && ar.getProcDefKey().contains("union"))){
					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),null, GedClient.ged_fqsq,0,null);
				}else {
					List<CheckApproveUnion> checkApproveUnionByApplyNo = checkApproveUnionService.getCheckApproveUnionByApplyNo(actTaskParam.getApplyNo());
					for (CheckApproveUnion checkApproveUnion2 : checkApproveUnionByApplyNo) {
						creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),checkApproveUnion2.getId(), GedClient.ged_fqsq,1,null);
					}
				}
				
				
				ApplyRegister applyRegister = new ApplyRegister();
				applyRegister.setApplyNo(actTaskParam.getApplyNo());
				List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
				if (!registerList.isEmpty()) {
					applyRegister = registerList.get(0);
				}
				if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
					Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
					paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
					List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
					if (checkApproveList.size() >0) {
						CheckApprove checkApprove = checkApproveList.get(0);
						creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
					}
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"0");//0非联合授信
				}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
					creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"1");//1联合授信
				}
			}
		} catch (Exception e) {
			logger.error("录入结论保存失败！", e);
			ajaxView.setFailed().setMessage("录入结论保存失败！");
		}
		return ajaxView;
		
	}

	private AddOrderResponse sendOrderToGED(ApplyRegister applyRegister) {
		String applyNo = applyRegister.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(applyNo);
		CompanyInfo companyInfo = new CompanyInfo();
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJQY);
		List<CompanyInfo> comapnyList = companyInfoService.findListByParams(params);
		if (comapnyList.size()>0) {
			companyInfo = comapnyList.get(0);
		}
		List<CustInfo> custInfoList = custInfoService.findMainBorrowerByApplyNo(applyNo);
		CustInfo custInfo = new CustInfo();
		if(custInfoList.size()>0){
			custInfo = custInfoList.get(0);
		}
		AddOrderRequest addOrderRequest=new AddOrderRequest();
		//证件号码
		addOrderRequest.setIdNum(applyRegister.getIdNum());
		System.out.println(applyRegister.getRegisterDate());
		//申请日期
		Date registerDate = applyRegister.getRegisterDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String applyData = sdf.format(registerDate);
		addOrderRequest.setApplyDate(applyData);
		//申请金额
		addOrderRequest.setApplyAmount(applyInfo.getApplyAmount());
		//申请期限
		addOrderRequest.setApplyPeriod(applyInfo.getApplyPeriodValue());
		//企业证件号码
		addOrderRequest.setCompanyCardNum(companyInfo.getUnSocCreditNo());
		//企业证件类型
		addOrderRequest.setCompanyCardType("1");
		//企业名称
		addOrderRequest.setCompanyName(companyInfo.getBusiRegName());
		//客户名称
		addOrderRequest.setCustName(applyRegister.getCustName());
		//客户来源
		addOrderRequest.setCustSource(Integer.valueOf(applyRegister.getChannelSourceType()));
		//客户类型
		addOrderRequest.setCustType(Integer.valueOf(applyInfo.getApplyCustType()));
		//移动电话   custInfo.mobileNum
		addOrderRequest.setPhoneNo(custInfo.getMobileNum());
		//产品名称
		addOrderRequest.setProductName(applyRegister.getApplyProductName());
		//产品类型
		addOrderRequest.setProductType(Integer.valueOf(applyRegister.getApplyProductTypeCode()));
		////省
		addOrderRequest.setProvince(companyInfo.getRegProvince());
		///市
		addOrderRequest.setCity(companyInfo.getRegCity());
		///县区
		addOrderRequest.setDistrict(companyInfo.getRegDistinct());
		//申请id
		addOrderRequest.setApplyId(applyRegister.getApplyNo());
		//借款用途
		addOrderRequest.setLoanPurpost(applyInfo.getLoanPurpost());
		//月利率
		addOrderRequest.setApproveMonthRate(applyInfo.getApplyYearRate());
		AddOrderResponse addOrderResponse=null;  //= Facade.facade.addOrderToGEDInterface(addOrderRequest,applyRegister.getApplyNo());
		return addOrderResponse;
	}
}
package com.resoft.credit.underConclusion.service;

import com.google.common.collect.Maps;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.discount.service.AccDiscountService;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.CreateNumberService;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyLoanStatus.dao.ApplyLoanStatusDao;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssertHouse.service.GqgetAssetHouseService;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCar.service.GqgetAssetCarService;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.service.GqgetComInfoService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.mortgageCarInfo.entity.gqGetMortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.service.MortgageCarInfoService;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageHouseInfo.service.MortgageHouseInfoService;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.mortgageOtherInfo.service.MortgageOtherInfoService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.credit.repayPlan.utils.RepayPlanUtils;
import com.resoft.credit.repayPlanUnion.service.RepayPlanUnionService;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.resoft.credit.underCompanyInfo.dao.UnderCompanyInfoDao;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import com.resoft.credit.underCustInfo.service.UnderCustInfoService;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.newged.entity.*;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class UnderConclusionService{

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private ProcessSuggestionInfoService processSuggestionInfoService;
    @Autowired
    private TaskCenterService taskCenterService;
    @Autowired
    private ApplyRegisterService applyRegisterService;

    @Autowired
    private CreateNumberService createNumberService;
    @Autowired
    private UnderCompanyInfoService underCompanyInfoService;
    @Autowired
    private UnderCompanyInfoDao underCompanyInfoDao ;
    @Autowired
    private ApplyLoanStatusDao applyLoanStatusDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private UnderCustInfoService underCustInfoService;
    @Autowired
    private CreGedAccountCompanyService gedAccountCompanyService;
    @Autowired
    private CheckApproveService checkApproveService;
    @Autowired
    private ApplyRelationService applyRelationService;
    @Autowired
    private MortgageCarInfoService mortgageCarInfoService;
    @Autowired
    private MortgageHouseInfoService mortgageHouseInfoService;
    @Autowired
    private MortgageOtherInfoService mortgageOtherInfoService;
    @Autowired
    private GqgetComInfoService gqgetComInfoService;
    @Autowired
    private RepayPlanService repayPlanService;
    @Autowired
    private GqgetAssetCarService gqgetAssetCarService;
    @Autowired
    private GqgetAssetHouseService gqgetAssetHouseService;
    @Autowired
    private AccDiscountService  discountService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private GedAccountService gedAccountService;

    @Transactional(value = "CRE", readOnly = false)
    public  String startNewProcess() {
        String procInstId = null;
        try {
            List<Integer> flagList = new ArrayList<Integer>();
            flagList.add(0, 0);
            flagList.add(1, 0);
            flagList.add(2, 0);
            String logUserId = UserUtils.getUser().getCompany().getId();
            String dateStr = DateUtils.getDate("yyMMdd");
            List<String> receiveList = createNumberService.createContractNo("", logUserId, flagList,dateStr);
            // 更新当前时间
            dateStr = (String) receiveList.get(2);
            UnderCompanyInfo underCompanyInfo=new UnderCompanyInfo();
            underCompanyInfo.setApplyNo((String) receiveList.get(0));
            underCompanyInfo.setLoanStatus("1");//1大区岗录入
            underCompanyInfo.preInsert();
             procInstId = actTaskService.startProcess("gq_loan_under", "UNDER_COMPANY_INFO", underCompanyInfo.getId());
            underCompanyInfo.setProcInstId(procInstId);
            underCompanyInfoDao.insert(underCompanyInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procInstId;

    }

    @Transactional(value = "CRE", readOnly = false)
    public AjaxView save(ActTaskParam actTaskParam, String passFlag, String suggestion) {
        AjaxView ajaxView= new AjaxView();

        Map<String, String> params = Maps.newConcurrentMap();
        params.put("applyNo", actTaskParam.getApplyNo());
        params.put("taskDefKeyV", actTaskParam.getTaskDefKey());
        params.put("taskDefKey", actTaskParam.getTaskDefKey());
        ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
        if(processSuggestionInfo==null){
            processSuggestionInfo=new ProcessSuggestionInfo();
        }
        processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
        processSuggestionInfo.setPassFlag(passFlag);
        processSuggestionInfo.setSuggestionDesc(suggestion);
        processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
        if ("yes".equalsIgnoreCase(passFlag)) {
            if("under_dqglr".equals(actTaskParam.getTaskDefKey())){
                //saveApplyLoanStatus(actTaskParam.getApplyNo());
                //推单，担保关系
                String pushOrderResult = pushOrderToGed(actTaskParam.getApplyNo());
                if(StringUtils.isNotEmpty(pushOrderResult)){
                    return ajaxView.setFailed().setMessage(pushOrderResult);
                }
                String pushGuResult = pushGuaranteeToGed(actTaskParam.getApplyNo());
                if(StringUtils.isNotEmpty(pushGuResult)){
                    return ajaxView.setFailed().setMessage(pushGuResult);
                }
                String applyContractNo = pushApplyContractNoRelation(actTaskParam.getApplyNo());
                if(StringUtils.isNotEmpty(applyContractNo)){
                    return ajaxView.setFailed().setMessage(applyContractNo);
                }
                //插入合同
                saveContract(actTaskParam.getApplyNo(),"under_htsh");
               
                
            }
            if("under_htsh".equals(actTaskParam.getTaskDefKey())){
                underCompanyInfoService.updateStatus("2",actTaskParam.getApplyNo());
                //贴息表插入数据
                CheckApprove checkApprove = checkApproveService.getByApplyNo(actTaskParam.getApplyNo(),"");
                int flag = checkApprove.getReturnServiceFeeFlag();
                if(1==flag){
                    discountService.saveUnderInterest(checkApprove);
                }
                Contract contract = contractService.getContractByApplyNo(actTaskParam.getApplyNo());
                AccContract contract2 = contractService.getCreContractByContractNo(contract.getContractNo());
                Facade.facade.gedOrderStateFeedBack(actTaskParam.getApplyNo(),null,GedClient.ged_shtg,contract2);
            }
            if("under_tb".equals(actTaskParam.getTaskDefKey())){
                UnderCompanyInfo companyInfo = underCompanyInfoService.getByApplyNo(actTaskParam.getApplyNo());
                if(!(("5".equals(companyInfo.getLoanStatus()))||("22".equals(companyInfo.getLoanStatus())))){
                    return ajaxView.setFailed().setMessage("暂时不能结束流程！");
                }
                //插入register用于dt
                ApplyRegister applyregister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
                if (applyregister != null) {
                	applyregister.setApplyNo(actTaskParam.getApplyNo());
                    applyregister.setApplyStatus("4");
                    applyregister.setSendGED("2");
                    applyregister.setProcDefKey("gq_loan_under");
                    applyRegisterService.updateUnderByApplyNo(applyregister);
				}else if (applyregister == null) {
					ApplyRegister applyregister1 = new ApplyRegister();
					applyregister1.setApplyNo(actTaskParam.getApplyNo());
					applyregister1.setApplyStatus("4");
					applyregister1.setSendGED("2");
					applyregister1.setProcDefKey("gq_loan_under");
                    applyRegisterService.save(applyregister1);
				}
                
                
            }
            processSuggestionInfoService.save(processSuggestionInfo);
            ajaxView.setSuccess().setMessage("提交成功！");
            actTaskService.appointAssigne(UserUtils.getUser().getLoginName(), actTaskParam.getTaskId());
            actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + suggestion, "提交", null);
        }else if("no".equalsIgnoreCase(passFlag)){
            if("under_tb".equals(actTaskParam.getTaskDefKey())){
                UnderCompanyInfo companyInfo = underCompanyInfoService.getByApplyNo(actTaskParam.getApplyNo());
                if(!("5".equals(companyInfo.getLoanStatus()))||!("22".equals(companyInfo.getLoanStatus()))){
                    return ajaxView.setFailed().setMessage("暂时不能结束流程！");
                }
            }
            underCompanyInfoService.updateStatus("0",actTaskParam.getApplyNo());
            processSuggestionInfo.setPassFlag("no");
            processSuggestionInfoService.save(processSuggestionInfo);
            //processSuggestionInfoService.insertFlag(processSuggestionInfo,actTaskParam.getTaskDefKey());
            actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【放弃】" + suggestion);
            ajaxView.setSuccess().setMessage("提交成功！");
        }else if("back".equalsIgnoreCase(passFlag)){
            actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
                    "【打回】" + suggestion);
            ajaxView.setSuccess().setMessage("提交成功！");
        }
        return ajaxView;
    }

    private String pushApplyContractNoRelation(String applyNo) {
        //UnderCompanyInfo companyInfo = underCompanyInfoService.getByApplyNo(applyNo);
        CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo, "");
        OrderContractReqFormLists orderContractReqFormLists =new OrderContractReqFormLists();
        List<OrderContractReqForm> orderContractReqFormList = new ArrayList<OrderContractReqForm>();
        OrderContractReqForm orderContractReqForm = repayPlanService.getUnderRepay(applyNo,"under_dqglr");
        orderContractReqForm.setServiceFee(checkApprove.getServiceFee()+"");
        orderContractReqForm.setOrderNo(applyNo);
        orderContractReqForm.setContractNo(checkApprove.getUnderContractNo());
        /*orderContractReqForm.setContractProvince();
        orderContractReqForm.setContractCity();
        orderContractReqForm.setContractDistinct();*/
        orderContractReqFormList.add(orderContractReqForm);
        orderContractReqFormLists.setList(orderContractReqFormList);
        AddOrderResponse addOrderResponse = Facade.facade.sendConytractRelation(orderContractReqFormLists);
        if(addOrderResponse==null || !("0".equals(addOrderResponse.getCode()))){
            return "推送合同号失败！";
        }
        return  null;
    }

    private String pushGuaranteeToGed(String applyNo) {
        //担保人
        List<GedPushGuaranteeRequest> guarantorInfo = applyRelationService.getGEDguarantorInfo(applyNo);
        for (GedPushGuaranteeRequest gedPushGuaranteeRequest1 : guarantorInfo) {
            gedPushGuaranteeRequest1.setMasterOrderCode(applyNo);
        }
        List<GedPushGuaranteeRequest> guarantorGS = applyRelationService.getGEDguarantorGS(applyNo);
        for (GedPushGuaranteeRequest gedPushGuaranteeRequest3 : guarantorGS) {
            gedPushGuaranteeRequest3.setMasterOrderCode(applyNo);
        }
        GedRegisterResponse pushGEDGuarantee = Facade.facade.gedPushGuaranteeInterface(guarantorInfo , applyNo);
        if(!("0".equals(pushGEDGuarantee.getCode()))) {//失败
            return "推送担保信息失败！";
        }
        return null;
    }

    private String pushOrderToGed(String applyNo) {
        CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo,null);
        AddOrderRequest addOrderRequert = new AddOrderRequest();
        addOrderRequert = underCompanyInfoService.pushOrder(applyNo);
        addOrderRequert.setReceivableCashDeposit(checkApprove.getMarginAmount().toString());
        addOrderRequert.setReceivableGuaranteeFee("1000");
        AddOrderRequestList list =new AddOrderRequestList();
        List<AddOrderRequest> list1 = new ArrayList<>();
        list1.add(addOrderRequert);
        list.setList(list1);
        AddOrderResponse addOrderResponse = Facade.facade.addOrderToGEDInterface(list,applyNo);
        if(addOrderResponse==null){
            return "推送冠易贷工单失败！";
        }/*else {
            applyRegister.setSendGED("1");
            applyRegisterService.saveSendGEDStatus(applyRegister);
            creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),null, GedClient.ged_shz,0,null);
        }*/
        return null;
    }

    //保存合同
    private void saveContract(String applyNo,String taskDefKey) {
        Contract contract = new Contract();
        UnderCompanyInfo underCompanyInfo = underCompanyInfoService.getByApplyNo(applyNo);
        UnderCustInfo underCustInfo = underCustInfoService.getByApplyNo(applyNo);
        CreGedAccountCompany gedAccountCompany = gedAccountCompanyService.getByApplyNo(applyNo);
        CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo,null);
        contract.setApproId("");
        contract.setMortgageeId("");
        contract.setApplyNo(applyNo);
        contract.setCustName(underCompanyInfo.getBusiRegName());
        contract.setCustId(underCompanyInfo.getId());
        contract.setIdNum(underCompanyInfo.getCorporationCardIdNo());

        contract.setApplyProductTypeCode(checkApprove.getApproProductTypeCode());
        contract.setApplyProductTypeName(checkApprove.getApproProductTypeName());
        contract.setApplyProductName(checkApprove.getApproProductName());
        contract.setApplyProductId(checkApprove.getApproProductId());
        contract.setApplyPeriodValue(checkApprove.getApproPeriodValue());
        contract.setApplyYearRate(checkApprove.getApproYearRate());
        contract.setApplyServiceFeeRate(checkApprove.getServiceFeeRate());
        contract.setApplyAmount(checkApprove.getApproAmount());
        contract.setApplyLoanRepayType(checkApprove.getApproLoanRepayType());

        contract.setApproProductTypeCode(checkApprove.getApproProductTypeCode());
        contract.setApproProductTypeName(checkApprove.getApproProductTypeName());
        contract.setApproProductName(checkApprove.getApproProductName());
        contract.setApproProductId(checkApprove.getApproProductId());
        contract.setApproPeriodValue(checkApprove.getApproPeriodValue());
        contract.setApproYearRate(checkApprove.getApproYearRate());
        contract.setApproAmount(checkApprove.getApproAmount());
        contract.setApproLoanRepayType(checkApprove.getApproLoanRepayType());
        contract.setContractAmount(checkApprove.getContractAmount());

        contract.setLoanAmount(checkApprove.getLoanAmount());
        contract.setServiceFeeRate(checkApprove.getServiceFeeRate());
        contract.setSpecialServiceFeeRate(checkApprove.getSpecialServiceFeeRate());
        contract.setServiceFeeType(checkApprove.getServiceFeeType());
        contract.setServiceFee(checkApprove.getServiceFee());
        contract.setSpecialServiceFee(checkApprove.getSpecialServiceFee());
        contract.setAllServiceFee(checkApprove.getAllServiceFee());
        contract.setMarginRate(checkApprove.getMarginRate());
        contract.setMarginAmount(checkApprove.getMarginAmount());
        contract.setCheckFee(checkApprove.getCheckFee());
        contract.setLoanModel(checkApprove.getLoanModel());
        contract.setFactLoanAmount(checkApprove.getLoanAmount());

        contract.setConStartDate(com.resoft.common.utils.DateUtils.getCurrDateTime());
        contract.setConEndDate(null);
        contract.setContractNo(checkApprove.getUnderContractNo());
        BigDecimal receiveAmount = null;
        if("1".equals(checkApprove.getServiceFeeType())){
            if(checkApprove.getQualityServiceMarginAmount()==null){
                receiveAmount = checkApprove.getMarginAmount();
            }else{
                receiveAmount = checkApprove.getMarginAmount().add(checkApprove.getQualityServiceMarginAmount());
            }
        }
        contract.setReceiveAmount(receiveAmount);

        contract.setRecBankcardNo(gedAccountCompany.getCompanyAccount());
        contract.setRecBankcardName(gedAccountCompany.getCompanyName());
        contract.setRecBank(gedAccountCompany.getCompanyBankOfDeposit());
        contract.setRecBankName(gedAccountCompany.getCompanyBankOfDepositValue());
        contract.setRecBankMobile(gedAccountCompany.getLegalMobile());
        contract.setRecBankIdNum(gedAccountCompany.getLegalCardNumber());
        contract.setRecBankProvince(gedAccountCompany.getProvinceCode());
        contract.setRecBankCity(gedAccountCompany.getCityCode());

        contract.setRepBankcardNo(gedAccountCompany.getCompanyAccount());
        contract.setRepBankcardName(gedAccountCompany.getCompanyName());
        contract.setRepBank(gedAccountCompany.getCompanyBankOfDeposit());
        contract.setRepBankName(gedAccountCompany.getCompanyBankOfDepositValue());
        contract.setRepBankMobile(gedAccountCompany.getLegalMobile());
        contract.setRepBankIdNum(gedAccountCompany.getLegalCardNumber());
        contract.setRepBankProvince(gedAccountCompany.getProvinceCode());
        contract.setRepBankCity(gedAccountCompany.getCityCode());
        contract.setOperateOrgId(UserUtils.getUser().getOffice().getId());
        contract.setApproId("");
        ApplyRelation byApplyNoAndRoleType2 = applyRelationService.getByApplyNoAndRoleType(contract.getApplyNo(), "8");
        contract.setGuaranteeCompanyName(byApplyNoAndRoleType2.getCustName());
        contract.setGuaranteeCompanyId(byApplyNoAndRoleType2.getCustId());
        contract.setLoanCompanyName(underCompanyInfo.getBusiRegName());
        contract.setLoanCompanyID(underCompanyInfo.getId());
        contract.setConStartDate(gedAccountCompany.getCreateTime());
        Contract contractByApplyNo = contractDao.getContractByApplyNo(applyNo);
        if(contractByApplyNo==null){
            contract.preInsert();
            contractDao.insert(contract);
        }else{
            contract.preUpdate();
            contractDao.update(contract);
        }

    }

    private void saveApplyLoanStatus(String applyNo) {
        List<ApplyLoanStatus> applyLoanStatuses = applyLoanStatusDao.getApplyLoanStatusByApplyNo(applyNo);
        if (applyLoanStatuses.size() != 0) {
            applyLoanStatusDao.deleteLoanStatusByApplyNo(applyNo);
        }
        ApplyLoanStatus applyLoanStatus = new ApplyLoanStatus();
        UnderCompanyInfo  underCompanyInfo = underCompanyInfoService.getByApplyNo(applyNo);
        applyLoanStatus.setApplyNo(applyNo);
        applyLoanStatus.setCustName(underCompanyInfo.getBusiRegName());
        applyLoanStatus.setContractNo(applyNo);
        applyLoanStatus.setContractAmount("");
        applyLoanStatus.setFactLoanAmount("");
        applyLoanStatus.setLoanModel("");
        applyLoanStatus.setLoanStatus(Constants.LOAN_STATUS_WTS);
        applyLoanStatus.setOrderLoanDate(new Date());
        applyLoanStatus.setContractState(Constants.CONTRACT_STATE_QYWC);
        applyLoanStatus.preInsert();
        applyLoanStatusDao.saveApplyLoanStatus(applyLoanStatus);
    }

    @Transactional(value = "CRE", readOnly = false)
    public String pushTarget(String applyNo) {
        Map<String, Object> resultMap = Facade.facade.underIssuingTenderData(applyNo);
        if("0000".equals(resultMap.get("flag"))){
            underCompanyInfoService.updateStatus("3",applyNo);
        }
        return (String)resultMap.get("msg");
    }
    @Transactional(value = "CRE", readOnly = false)
    public String valiProcessDate(String applyNo,String taskDefKey) {
        if("under_dqglr".equalsIgnoreCase(taskDefKey)){
            UnderCustInfo underCustInfo = underCustInfoService.getByApplyNo(applyNo);
            if(underCustInfo==null){
                return "请完善借款人基本信息！";
            }
            UnderCompanyInfo companyInfo = underCompanyInfoService.getByApplyNo(applyNo);
            if(StringUtils.isEmpty(companyInfo.getBusiRegName())||StringUtils.isEmpty(companyInfo.getGqCompanyName())){
                return "请完善企业信息或者机构信息！";
            }
            CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo, "");
            if(checkApprove==null){
                return "请完善借款信息！";
            }
            //cre_gqget_com_info   cre_gqget_asset_house
            //其他
            GqgetComInfo gqgetComInfoByApplyNo1 = gqgetComInfoService.getGqgetComInfoByApplyNo(applyNo);
            //车辆
            List<GqgetAssetCar> gqgetAssetCarList = gqgetAssetCarService.getByApplyNo(applyNo);
            List<GqgetAssetHouse> gqgetAssetHouseList = gqgetAssetHouseService.getByApplyNo(applyNo);
            //
            if(!(gqgetComInfoByApplyNo1!=null||gqgetAssetCarList.size()>0||gqgetAssetHouseList.size()>0)){
                return "请完善资产信息！";
            }
            ApplyRelation applyRelationCompany = applyRelationService.getByApplyNoAndRoleType(applyNo, "8");
            List<ApplyRelation> applyRelationList = applyRelationService.getListByApplyNo(applyNo, "3");
            if(applyRelationCompany==null||applyRelationList.size()==0){
                return "请完善担保信息信息！";
            }
            GqgetComInfo gqgetComInfoByApplyNo = gqgetComInfoService.getGqgetComInfoByApplyNo(applyNo);
            if(gqgetComInfoByApplyNo==null){
                return "请完善还款来源信息！";
            }
            FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",companyInfo.getUnSocCreditNo());
            findIsRegister.setMobile(underCustInfo.getMobileNum());
            findIsRegister.setUserRole("0");//借款人
            FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister,applyNo);
            if (findIsRegisterResponse == null||findIsRegisterResponse.getData().getMobile()==null) {
                return "请注册冠易贷账号！";
            }
            CreGedAccountCompany gedAccountCompany = gedAccountCompanyService.getByApplyNo(applyNo);
            if(gedAccountCompany==null){
                return "请开企业户后再提交！";
            }
            GedAccount gedAccount = gedAccountService.getByApplyNo(applyNo);
            if(gedAccount==null){
                return "请开个人户后再提交！";
            }
        }else if("under_htsh".equalsIgnoreCase(taskDefKey)){
            Contract contract = contractDao.getContractByApplyNo(applyNo);
            if(contract==null|| (StringUtils.isBlank(contract.getSignFlag())) || (StringUtils.isNotBlank(contract.getSignFlag()) && !("1".equals(contract.getSignFlag())))){
            	return "请将该合同签约！";
            }
        }
        return null;
    }
    
    
    private String creareApplyRepayPlan(String applyNo,String taskDefKey){
    	try {
			CheckApprove underCheckApprove = checkApproveService.getByApplyNo(applyNo, taskDefKey);
			if (underCheckApprove != null) {
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("applyNo", applyNo);
				params.put("taskDefKey", taskDefKey);
				repayPlanService.deleteRepayPlanByApplyNo(params);
				//生成还款计划表
				RepayPlanData repayPlanData = RepayPlanUtils.packRepayPlan(underCheckApprove);
				repayPlanData.setDeductDate(underCheckApprove.getSignDate());
				List<RepayPlan> repayPlanListTmp = contractService.calculateRepayPlan(repayPlanData);
				// 判断 如果是一次性付清本息 则进行数据处理
				List<RepayPlan> repayPlanList = new ArrayList<>();
				if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(underCheckApprove.getApproLoanRepayType())) {
					repayPlanList = contractService.recountData(repayPlanListTmp, underCheckApprove.getApproPeriodValue());
				} else {
					repayPlanList = repayPlanListTmp;
				}
				repayPlanService.insertRepayPlanList(repayPlanList);
				return "生成还款计划成功!";
			
			} else {
				return "生成还款计划失败!找不到借款信息";
			}
		} catch (Exception e) {
			return "生成还款计划失败!";
		}
    }
}
package com.resoft.credit.contract.web;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.CreateNumberService;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.mappingInfo.dao.MappingInfoDao;
import com.resoft.credit.mappingInfo.entity.MappingInfo;
import com.resoft.credit.mappingInfo.service.MappingInfoService;
import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;
import com.resoft.credit.mortgagedperson.service.MortgagedPersonService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequestList;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同信息Controller
 *
 * @author yanwanmei
 * @version 2016-03-02
 */
@Controller("creContractController")
@RequestMapping(value = "${adminPath}/credit/contract")
public class ContractController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
    private static String dateStr = DateUtils.getDate("yyMMdd");

    @Autowired
    private ContractService contractService;

    @Autowired
    private AreaService areaService;// 区域地质service

    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private CheckApproveService checkApproveService;

    @Autowired
    private ProcessSuggestionInfoService processSuggestionInfoService;

    @Autowired
    private MortgagedPersonService mortgagedPersonService;

    @Autowired
    private CreateNumberService createNumberService;

    @Autowired
    private ActTaskService actTaskService;

    @Autowired
    private CustInfoService custInfoService;
    @Autowired
    private ApplyRelationService applyRelationService;
    @Autowired
    private RateInterestService rateInterestService;
    @Autowired
    private MappingInfoService mappingInfoService;
    @Autowired
    private MappingInfoDao mappingInfoDao;
    @Autowired
    private GedAccountService gedAccountService;
    @Autowired
    private ApplyRegisterService applyRegisterService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private CreGedAccountCompanyService creGedAccountService;
    @Autowired
    private CreGuaranteeCompanyService creGuaranteeCompanyService;
    @ModelAttribute
    public Contract get(@RequestParam(required = false) String id) {
        Contract entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = contractService.get(id);
        }
        if (entity == null) {
            entity = new Contract();
        }
        return entity;
    }

    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = { "list", "" })
    public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
        model.addAttribute("page", page);
        return "app/credit/contract/contractList";
    }

    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = "form")
    public String form(Contract contract, Model model) {
        model.addAttribute("contract", contract);
        return "app/credit/contract/contractForm";
    }

    @RequiresPermissions("credit:contract:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxView save(ActTaskParam actTaskParam, String readOnly, Contract contract, Model model,
                         RedirectAttributes redirectAttributes, ProcessSuggestionInfo processSuggestionInfo,
                         HttpServletRequest request) {
		/*
		 * if (!beanValidator(model, contract)) { return form(contract, model);
		 * }
		 */
        AjaxView ajaxView = new AjaxView();
        try {
            String bankNo = DictUtils.getDictValue(contract.getBankNo(), "BANKS", "");
            contract.setBankNo(bankNo);
            String midBankNo = DictUtils.getDictValue(contract.getMiddlemanBankNo(), "BANKS", "");
            contract.setMiddlemanBankNo(midBankNo);
            if (Constants.YES_NO_Y.equals(contract.getIsAccord())) {
                contract.setRepBankProvince(contract.getRecBankProvince());
                contract.setRepBankCity(contract.getRecBankCity());
                contract.setRepBankDistinct(contract.getRecBankDistinct());
                contract.setRepBank(contract.getRecBank());
            }

            Map<String, String> orgLevelsMap = contractService.findOrgLevelByOrgId(request.getParameter("orgId"));
            if (orgLevelsMap.get("orgLevel2") != null && !"".equals(orgLevelsMap.get("orgLevel2"))) {
                contract.setOrgLevel2(orgLevelsMap.get("orgLevel2"));
            }
            if (orgLevelsMap.get("orgLevel3") != null && !"".equals(orgLevelsMap.get("orgLevel3"))) {
                contract.setOrgLevel3(orgLevelsMap.get("orgLevel3"));
            }
            if (orgLevelsMap.get("orgLevel4") != null && !"".equals(orgLevelsMap.get("orgLevel4"))) {
                contract.setOrgLevel4(orgLevelsMap.get("orgLevel4"));
            }

            String message = null;

            synchronized (dateStr) {
                // 合同编号不存在时，设置合同编号
                if (contract.getContractNo() == null || StringUtils.isBlank(contract.getContractNo())) {
                    MappingInfo mappingInfo = mappingInfoService.get(contract.getOperateOrgId());
                    // 如果映射表中没有数据，则插入数据
                    if (mappingInfo == null) {
                        mappingInfo = new MappingInfo();
                        mappingInfo.preInsert();
                        mappingInfo.setId(contract.getOperateOrgId());
                        mappingInfo.setContractNoFirst("0");
                        mappingInfo.setContractNoSecond("0");
                        mappingInfoDao.insert(mappingInfo);
                    } else {
                        if (Integer.parseInt(mappingInfo.getContractNoFirst()) > 25) {
                            ajaxView.setFailed().setMessage("生成合同号失败，请联系管理员");
                            return ajaxView;
                        }
                    }
                    List<Integer> flagList = new ArrayList<Integer>();
                    flagList.add(0, Integer.parseInt(mappingInfo.getContractNoFirst()));
                    flagList.add(1, Integer.parseInt(mappingInfo.getContractNoSecond()));
                    List<String> receiveList = createNumberService.createContractNo(contract.getApproProductTypeCode(),
                            contract.getOperateOrgId(), flagList, dateStr);

                    //receiveList.set(0, "HG-" + receiveList.get(0));
                    // 更新当前时间
                    dateStr = (String) receiveList.get(2);
                    String contractNoTemp = receiveList.get(0);
                    while (contractService.getContractByContractNo(contractNoTemp) != null) {
                        flagList.clear();
                        flagList.add(0, Integer.parseInt(receiveList.get(3)));
                        flagList.add(1, Integer.parseInt(receiveList.get(4)));
                        receiveList = createNumberService.createContractNo(contract.getLoanModel(),
                                contract.getOperateOrgId(), flagList, dateStr);
                        //receiveList.set(0, "HG-" + receiveList.get(0));
                        dateStr = (String) receiveList.get(2);
                        contractNoTemp = receiveList.get(0);
                    }
                    contract.setContractNo(receiveList.get(0));
                    logger.info("生成的合同编号为：" + contract.getContractNo());
                }
            }

            // 合同起始日期设置为当前时间
            contract.setConStartDate(DateUtils.getCurrDateTime());

            contract.setRecBankcardName(contract.getRecBankcardId());
            message = contractService.insertContractAndRepayPlan(contract);
            if ("success".equalsIgnoreCase(message)) {
                contractService.updateGuranteeRelation(actTaskParam.getApplyNo(), contract.getCustId(), contract.getContractNo());
            }
            if ("success".equalsIgnoreCase(message)) {
                ajaxView.setSuccess().setMessage("保存合同信息，生成还款计划成功");
                ajaxView.put("contractNo", contract.getContractNo());
                ajaxView.put("conStartDate", new SimpleDateFormat("yyyy-MM-dd").format(contract.getConStartDate()));
                ajaxView.put("conEndDate", new SimpleDateFormat("yyyy-MM-dd").format(contract.getConEndDate()));
            } else {
                ajaxView.setFailed().setMessage("保存合同信息或生成还款计划失败");
            }
        } catch (Exception e) {
            logger.error("保存合同面签出现异常！", e);
            ajaxView.setFailed().setMessage("保存合同信息或生成还款计划失败");
        }
        ajaxView.put("applyNo", actTaskParam.getApplyNo());
        ajaxView.put("id", contract.getId());
        return ajaxView;
    }

    @RequiresPermissions("credit:contract:edit")
    @RequestMapping(value = "delete")
    public String delete(Contract contract, RedirectAttributes redirectAttributes) {
        contractService.delete(contract);
        addMessage(redirectAttributes, "删除合同信息成功");
        return "redirect:" + Global.getAdminPath() + "/contract/contract/?repage";
    }

    /**
     * 面签信息模块
     *
     * @param contract
     * @param model
     * @return
     */
    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = "contractSignInfo")
    public String contractSignInfo(ActTaskParam actTaskParam, String readOnly,
                                   ProcessSuggestionInfo processSuggestionInfo, Contract contract, Model model, String checkFlag){
        String applyNo = actTaskParam.getApplyNo();
        model.addAttribute("actTaskParam", actTaskParam);
        // 判断是否是被打回过的任务
        try {
            boolean returnFlag = actTaskService.isReturned(actTaskParam.getTaskId());
            model.addAttribute("returnFlag", returnFlag);
        } catch (Exception e1) {
            //logger.error("判断是否被打回出现异常！", e1);
        }

        model.addAttribute("readOnly", readOnly);
        model.addAttribute("checkFlag", checkFlag);
        contract = contractService.getContractByApplyNo(actTaskParam.getApplyNo());

        if (contract == null) {
            contract = new Contract();
        }
        // 开户状态
        String openAccountStatusLabel = DictUtils.getDictLabel(contract.getOpenAccountStatus(),
                Constants.OPEN_ACCOUNT_STATUS, "");
        if (openAccountStatusLabel == null || StringUtils.isBlank(openAccountStatusLabel)) {
            openAccountStatusLabel = DictUtils.getDictLabel(Constants.OPEN_ACCOUNT_STATUS_WKH,
                    Constants.OPEN_ACCOUNT_STATUS, "");
        }
        model.addAttribute("openAccountStatusLabel", openAccountStatusLabel);

        if (contract.getMianContract() == null || StringUtils.isBlank(contract.getMianContract())) {
            contract.setMianContract(Constants.MIAN_CONTRACT_JKZHT);
        }
        // 查询主借人信息
        List<CustInfo> mainCustList = custInfoService.findMainBorrowerByApplyNo(applyNo);
        CustInfo mainCust = new CustInfo();
        if (mainCustList != null && mainCustList.size() == 1) {
            mainCust = mainCustList.get(0);
        } else {
            model.addAttribute("message", "主借人信息出现异常，请联系管理员！");
        }
        // 收/还款银行账户名称
        if (contract.getRecBankcardName() == null || StringUtils.isBlank(contract.getRecBankcardName())) {
            //contract.setRecBankcardName(mainCust.getId());
            contract.setRecBankcardId(mainCust.getId());
        }else{
            contract.setRecBankcardId(contract.getRecBankcardName());
        }
        // if (contract.getRepBankcardName() == null ||
        // StringUtils.isBlank(contract.getRepBankcardName())) {
        // contract.setRepBankcardName(mainCust.getCustName());
        // }
        // // 收/还款人电话
        // if (contract.getRecBankMobile() == null ||
        // StringUtils.isBlank(contract.getRecBankMobile())) {
        // contract.setRecBankMobile(mainCust.getMobileNum());
        // }
        // if (contract.getRepBankMobile() == null ||
        // StringUtils.isBlank(contract.getRepBankMobile())) {
        // contract.setRepBankMobile(mainCust.getMobileNum());
        // }
        // // 收/还款人身份证号
        // if (contract.getRecBankIdNum() == null ||
        // StringUtils.isBlank(contract.getRecBankIdNum())) {
        // contract.setRecBankIdNum(mainCust.getIdNum());
        // }
        // if (contract.getRepBankIdNum() == null ||
        // StringUtils.isBlank(contract.getRepBankIdNum())) {
        // contract.setRepBankIdNum(mainCust.getIdNum());
        // }
        // 合同预约时间
        Map<String, String> param = Maps.newConcurrentMap();
        param.put("applyNo", actTaskParam.getApplyNo());
        param.put("taskDefKey", Constants.UTASK_HTYY);
        ProcessSuggestionInfo appointInfo = processSuggestionInfoService.findByApplyNo(param);
        if (appointInfo != null) {
            String reserveTime = appointInfo.getReserveTime();
            model.addAttribute("reserveTime", reserveTime);
        }

        String bankNo = DictUtils.getDictLabel(contract.getBankNo(), "BANKS", null);
        contract.setBankNo(bankNo);
        // 获取省级下拉
        LinkedHashMap<String, String> contractProvinceMap = loadAreaData("1");// 这里的1表示全国
        // 获取市级下拉
        LinkedHashMap<String, String> contractCityMap = loadAreaData(contract.getContProvince());
        // 获取区级下拉
        LinkedHashMap<String, String> contractDistinctMap = loadAreaData(contract.getContCity());

        // 查询总公司批复意见
        Map<String, String> topComSuggParams = Maps.newConcurrentMap();
        topComSuggParams.put("applyNo", applyNo);
        List<String> topComApproveSuggList = processSuggestionInfoService.findTopComApproveSugg(topComSuggParams);
        try {
            String topComApproveSugg = topComApproveSuggList.get(0);
            model.addAttribute("topComApproveSugg", topComApproveSugg);
        } catch (Exception e) {
            logger.warn("无总公司批复意见！");
        }

        String contProvice = contract.getContProvince();
        contract.setContProvince(contractProvinceMap.get(contProvice));

        String contCity = contract.getContCity();
        contract.setContCity(contractCityMap.get(contCity));

        String contDistinct = contract.getContDistinct();
        contract.setContDistinct(contractDistinctMap.get(contDistinct));

        // 查询申请信息
        ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
        if (applyInfo == null) {
            applyInfo = new ApplyInfo();
        }
        model.addAttribute("applyInfo", applyInfo);
        String productCategoryKey=DictUtils.getDictLabel(applyInfo.getApplyRegister().getProductCategory() , "product_category", null);
        model.addAttribute("productCategoryKey",productCategoryKey);
        //
        ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
        String TypeCode = applyRegister.getApplyProductTypeCode();
        if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
            model.addAttribute("showCgFlag", "1");
        }
        // 根据申请编号查询批复信息,查询最后环节的批复信息
        Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
        paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
        List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
        CheckApprove checkApprove = new CheckApprove();
        if (checkApproveList.size() > 0) {
            checkApprove = checkApproveList.get(0);
        }

        String approveProductCategoryKey=DictUtils.getDictLabel(checkApprove.getProductCategory() , "product_category", null);
        model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
        model.addAttribute("checkApprove", checkApprove);
        CheckApprove approveOld = checkApproveService.getCheckApproveBackUp(applyNo);
        if(approveOld != null){
            if (!(checkApprove.getRemark().equalsIgnoreCase(approveOld.getRemark()))) {
                model.addAttribute("oldTextFlag", "1");
            }
            model.addAttribute("oldText", approveOld.getRemark());

            Map<String, String> param11 = Maps.newHashMap();
            param11.put("applyNo", applyNo);
            List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param11);
            CheckApprove approveNew = approves.get(0);
            List<String> messages = checkApproveService.contrastObj(approveOld,approveNew);
            if(messages.size() != 0){
                model.addAttribute("oldMessage", "1");
                model.addAttribute("messages", messages);
            }
        }

        // 查找总公司批复意见
        @SuppressWarnings("unused")
        ProcessSuggestionInfo hasProcessSuggestionInfo = new ProcessSuggestionInfo();
        Map<String, String> params = Maps.newHashMap();
        params.put("applyNo", actTaskParam.getApplyNo());
        params.put("taskDefKey", Constants.UTASK_ZGSFKSH);
        processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
        if (processSuggestionInfo == null) {
            processSuggestionInfo = new ProcessSuggestionInfo();
        }
        model.addAttribute("processSuggestionInfo", processSuggestionInfo);

        // 查询面签意见
        ProcessSuggestionInfo contractSignSuggestion = new ProcessSuggestionInfo();
        params.put("applyNo", actTaskParam.getApplyNo());
        params.put("taskDefKey", Constants.UTASK_HTMQ);
        contractSignSuggestion = processSuggestionInfoService.findByApplyNo(params);
        if (contractSignSuggestion == null) {
            contractSignSuggestion = new ProcessSuggestionInfo();
        }
        model.addAttribute("contractSignSuggestion", contractSignSuggestion);

        // 查询法务审核意见
        ProcessSuggestionInfo legalCheckSuggestion = new ProcessSuggestionInfo();
        params.put("applyNo", actTaskParam.getApplyNo());
        params.put("taskDefKey", Constants.UTASK_FWSH);
        legalCheckSuggestion = processSuggestionInfoService.findByApplyNo(params);
        if (legalCheckSuggestion == null) {
            legalCheckSuggestion = new ProcessSuggestionInfo();
        }
        model.addAttribute("legalCheckSuggestion", legalCheckSuggestion);

        // 查询合同审核意见
        ProcessSuggestionInfo contractCheckSuggestion = new ProcessSuggestionInfo();
        params.put("applyNo", actTaskParam.getApplyNo());
        params.put("taskDefKey", Constants.UTASK_HTSH);
        contractCheckSuggestion = processSuggestionInfoService.findByApplyNo(params);
        if (contractCheckSuggestion == null) {
            contractCheckSuggestion = new ProcessSuggestionInfo();
        }
        model.addAttribute("contractCheckSuggestion", contractCheckSuggestion);

        // 向前台传递任务编号，以此来处理合同面前，法务审核和合同审核
        model.addAttribute("taskDefKeyFlag", actTaskParam.getTaskDefKey());

        if (StringUtils.isNull(contract.getIsOrNoMIddleMan())) {
            contract.setIsOrNoMIddleMan("0");
        }


        // 查询抵押权人信息
        List<MortgagedPerson> mortgagedPersonList = mortgagedPersonService.getMortgagedPerson();
        model.addAttribute("mortgagedPersonList", mortgagedPersonList);

        // 查询中间人信息
        List<MortgagedPerson> middlePersonList = mortgagedPersonService.getMiddlePerson();
        model.addAttribute("middlePersonList", middlePersonList);

        // 查询收款信息列表
        List<ApplyRelation> applyRelations = applyRelationService.getCheckCoupleDoubtfulByApplyNo(applyNo);
        model.addAttribute("applyRelations", applyRelations);

        // 根据申请的产品类型查询还款方式
        Map<String, String> para = new HashMap<String, String>();
        para.put("productType", checkApprove.getApproProductTypeCode());
        para.put("loanRepayType", checkApprove.getApproLoanRepayType());
        List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
        if (loanRepayTypeList.size() > 0) {
            String approLoanRepayTypeName = loanRepayTypeList.get(0).getLoanRepayDesc();
            model.addAttribute("approLoanRepayTypeName", approLoanRepayTypeName);
        }
        model.addAttribute("loanRepayTypeList", loanRepayTypeList);


        //查询主借企业信息
        CompanyInfo companyInfo = null;
        System.out.println(companyInfo == null);
        Map<String, Object> queryComParam = Maps.newHashMap();
        String message = null;
        queryComParam.put("applyNo", actTaskParam.getApplyNo());
        queryComParam.put("roleType", Constants.ROLE_TYPE_ZJQY);
        List<CompanyInfo> comapnyList = companyInfoService.findListByParams(queryComParam);
        if (!comapnyList.isEmpty()) {
            companyInfo = comapnyList.get(0);
        }
        //查询冠E贷账户信息
        GedAccount gedAccount = null;
        gedAccount =gedAccountService.getByPhone(mainCust.getMobileNum());

        if(gedAccount == null){
            gedAccount = new GedAccount();
        }
        if(companyInfo == null){
            companyInfo = new CompanyInfo();
        }
        List<CreGedAccountCompany> companyAccountList = new ArrayList<>();
        //查询企业账户信息
        if(companyInfo != null){
            companyAccountList =creGedAccountService.findCompanyAccountBySocialCreditNo(companyInfo.getUnSocCreditNo());
        }
        CreGedAccountCompany companyAccount = null;
        if(companyAccountList.size() >0){
            companyAccount = companyAccountList.get(0);
            String cityCode = companyAccount.getCityCode();
            String areaCode = companyAccount.getAreaCode();
            String proviceCode = companyAccount.getProvinceCode();
            LinkedHashMap<String, String> gedCompanyAccountCityMap = loadAreaData(proviceCode);
            // 获取区级下拉
            LinkedHashMap<String, String> gedCompanyAccountDistinctMap = loadAreaData(cityCode);
            companyAccount.setProvinceCode(contractProvinceMap.get(proviceCode));
            companyAccount.setCityCode(gedCompanyAccountCityMap.get(cityCode));
            companyAccount.setAreaCode(gedCompanyAccountDistinctMap.get(areaCode));
        }else{
            companyAccount	= new CreGedAccountCompany();
        }
        //-------------------------------
        if("1".equals(applyRegister.getCustType())) {
            if(gedAccount!=null&&gedAccount.getId()!=null) {
                contract.setRecBankcardNo(gedAccount.getCompanyAccount());//收款银行卡号：
                contract.setRecBank(gedAccount.getCompanyBankOfDeposit());//收款银行：
                contract.setRecBankName(gedAccount.getCompanyBankBranchName());//收款开户行名称：
                if(gedAccount.getProvinceName()==null&&gedAccount.getCityName()==null){
                }else if(gedAccount.getProvinceName()==null){
                    contract.setRecBankDetail(gedAccount.getCityName());
                }else if(gedAccount.getCityName()==null){
                    contract.setRecBankDetail(gedAccount.getProvinceName());
                }else {
                    contract.setRecBankDetail(gedAccount.getProvinceName() + gedAccount.getCityName());
                }
                if (contract.getRecBankDetail() == null) {
                	contract.setRecBankDetail("");
				}
                contract.setRecBankcardName(gedAccount.getLegalPerName());
                contract.setRecBankMobile(gedAccount.getLegalPerPhone());
                contract.setRecBankIdNum(gedAccount.getLegalPerNum());
                contract.setRepBankcardName(gedAccount.getLegalPerName());
                contract.setRepBankMobile(gedAccount.getLegalPerPhone());
                contract.setRepBankIdNum(gedAccount.getLegalPerNum());
            }
        }else{
            if(companyAccount!=null&&companyAccount.getId()!=null) {
                contract.setRecBankcardNo(companyAccount.getCompanyAccount());//收款银行卡号：
                contract.setRecBank(companyAccount.getCompanyBankOfDeposit());//收款银行：
                contract.setRecBankName(companyAccount.getCompanyBankBranchName());//收款开户行名称：
                if(companyAccount.getProvinceCode()==null&&companyAccount.getCityCode()==null){
                }else if(companyAccount.getProvinceCode()==null){
                    contract.setRecBankDetail(companyAccount.getCityCode());
                }else if(companyAccount.getCityCode()==null){
                    contract.setRecBankDetail(companyAccount.getProvinceCode());
                }else {
                    contract.setRecBankDetail(companyAccount.getProvinceCode() + companyAccount.getCityCode());
                }
                if (contract.getRecBankDetail() == null) {
                	contract.setRecBankDetail("");
				}
                contract.setRecBankcardName(companyAccount.getCompanyName());
                contract.setRecBankMobile(companyAccount.getContactMobile());
                contract.setRecBankIdNum(companyAccount.getLegalCardNumber());
                contract.setRepBankcardName(companyAccount.getCompanyName());
                contract.setRepBankMobile(companyAccount.getContactMobile());
                contract.setRepBankIdNum(companyAccount.getLegalCardNumber());
            }
        }
        model.addAttribute("contract", contract);
        model.addAttribute("companyAccount",companyAccount);
        model.addAttribute("gedAccount",gedAccount);
        if(Constants.UTASK_HTMQ.equals(actTaskParam.getTaskDefKey()) && StringUtils.isEmpty(checkFlag)){
            model.addAttribute("gedMessage",message);
        }
        model.addAttribute("companyInfo",companyInfo);

        // 保证金月息差及利息月息差判断标识
        String flowCode = CheckApproveUtils.validateFlowCode(checkApprove.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
        model.addAttribute("flowCode", flowCode);
        return "app/credit/contract/contractSignInfoForm";
    }

    // 获取客户信息
    @ResponseBody
    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = "getCustData")
    public AjaxView getCustData(String custId) {
        AjaxView ajaxView = new AjaxView();
        try {
            CustInfo custInfo = custInfoService.get(custId);
            ajaxView.setSuccess();
            ajaxView.put("idNum", custInfo.getIdNum());
            ajaxView.put("phone", custInfo.getMobileNum());
        } catch (Exception e) {
            logger.error("查询收款账户信息失败", e);
            ajaxView.setFailed().setMessage("查询收款账户信息失败");
        }
        return ajaxView;
    }

    // 省市级联数据加载
    private LinkedHashMap<String, String> loadAreaData(String areaCode) {
        Map<String, String> param = Maps.newConcurrentMap();
        LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
        if (StringUtils.isNotEmpty(areaCode)) {
            param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
            List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
            if (null != regDistinctList && regDistinctList.size() > 0) {
                for (Map<String, String> mp : regDistinctList) {
                    areaMap.put(mp.get("id"), mp.get("name"));
                }
            }
        }
        return areaMap;
    }

    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = "saveSuggestion")
    // 当点“提交”时，将外访意见保存到cre_process_suggestion_info表中
    public String saveSuggestion(ActTaskParam actTaskParam, String readOnly, Contract contract,
                                 ProcessSuggestionInfo processSuggestionInfo, String passFlag, Model model, HttpServletRequest request,
                                 HttpServletResponse response) {
        Contract contractA = contractService.getContractByContractNo(contract.getContractNo());
        String checkFlag = null;
        checkFlag = contractService.saveSuggestion(actTaskParam, passFlag, contractA, processSuggestionInfo);
        if ("error".equals(checkFlag)) {
            return super.renderString(response, "500");
        }else{
            processSuggestionInfo.setUpdateBy(UserUtils.getUser().getUpdateBy());
            processSuggestionInfo.setUpdateDate(UserUtils.getUser().getUpdateDate());
            processSuggestionInfoService.insertFlag(processSuggestionInfo, actTaskParam.getTaskDefKey());
            return contractSignInfo(actTaskParam, readOnly, processSuggestionInfo, contract, model, checkFlag);
        }
    }

    @RequestMapping(value = "contractInfo")
    public String contractInfo(HttpServletRequest request, HttpServletResponse reponse) {
        String contractNo = request.getParameter("contractNo");
        String url = Global.getConfig("ACC_VISIT_URL");
        return "redirect:" + url + "/f/accounting/Acccontract/contractInfo?contractNo=" + contractNo;
    }

    @RequestMapping(value = "udpateContractNoForm")
    public String udpateContractNoForm(Contract contract,Model model) {
        model.addAttribute("contract", contract);
        return "app/credit/contract/contratNoUpdatetForm";
    }

    @ResponseBody
    @RequestMapping(value = "updateContractNo")
    public AjaxView updateContractNo(Contract contract) {
        AjaxView ajaxView = new AjaxView();
        try {
            //要修改的合同编号去查是否已经存在
            Contract contractByContractNo = contractService.getContractByContractNo(contract.getNewContractNo());
            if(contractByContractNo!=null){
                ajaxView.setFailed().setMessage("合同号已经存在,更新合同号失败");
            }else{
                Map<String,Object> map = contractService.findContractAndApplyLoanStatus(contract.getApplyNo(),contract.getContractNo());
                String applyLoanStatusId = (String)map.get("applyLoanStatusId");
                ApplyLoanStatus applyLoanStatus=new ApplyLoanStatus();
                applyLoanStatus.setId(applyLoanStatusId);
                applyLoanStatus.setContractNo(contract.getNewContractNo());
                contractService.updateContractNoById(contract,applyLoanStatus);
                ajaxView.setSuccess().setMessage("更新成功");
            }
        } catch (Exception e) {
            logger.error("更新合同号失败", e);
            ajaxView.setFailed().setMessage("更新合同号失败");
        }
        return ajaxView;
    }

    @RequestMapping(value = "queryContractNoList")
    public String queryContractNo(Contract contract, Model model) {
        String applyNo = contract.getApplyNo();
        List<Contract> contracts = Lists.newArrayList();
        if(!(StringUtils.isNull(applyNo))){
            contracts = contractService.getContractDataByApplyNo(applyNo);
        }
        model.addAttribute("contractList", contracts);
        return "app/credit/contract/contratNoUpdatetList";
    }

    //比较冠E账户信息和企业信息
    private String compareGedAndComMessage(GedAccount gedAccount,CompanyInfo companyInfo){
        StringBuilder stringBuilder = new StringBuilder();
        String message = null;
        String gedLegalName = gedAccount.getLegalPerName();
        String gedLegalPhone = gedAccount.getLegalPerPhone();
        String gedLegalNum = gedAccount.getLegalPerNum();
        String gedComNum = gedAccount.getComIdNum();
        String gedComName = gedAccount.getCompanyName();

        String legName = companyInfo.getCorporationName();
        String legPhone = companyInfo.getCorporationMobile();
        String legNum = companyInfo.getCorporationCardIdNo();
        String comNum = companyInfo.getUnSocCreditNo();
        String comName = companyInfo.getBusiRegName();
        if(!StringUtils.isBlank(gedLegalName) && !StringUtils.isBlank(legName) && !gedLegalName.equals(legName)){
            stringBuilder.append("法人姓名、");
        }
        if(!StringUtils.isBlank(gedLegalNum) && !StringUtils.isBlank(legNum) && !gedLegalNum.equals(legNum)){
            stringBuilder.append("法人证件号码、");
        }
        if(!StringUtils.isBlank(gedLegalPhone) && !StringUtils.isBlank(legPhone) && !gedLegalPhone.equals(legPhone)){
            stringBuilder.append("法人联系方式、");
        }
        if(!StringUtils.isBlank(gedComName) && !StringUtils.isBlank(comName) && !gedComName.equals(comName)){
            stringBuilder.append("企业名称、");
        }
        if(stringBuilder.length() > 0){
            message = stringBuilder.substring(0,stringBuilder.length()-1).toString();
        }

        return message;
    }

    @ResponseBody
    @RequestMapping(value="validateGedAccount")
    public AjaxView validateGedAccount(String idNum,String legalNum){
        AjaxView ajaxView = new AjaxView();
        try {
            GedAccount gedAccount = gedAccountService.findGedAccountByIdNum(idNum);
            if(gedAccount == null){
                gedAccount = gedAccountService.findGedAccountByIdNum(legalNum);
            }
            if(gedAccount != null && (StringUtils.isEmpty(gedAccount.getCompanyAccount()) || StringUtils.isEmpty(gedAccount.getCityName()) || StringUtils.isEmpty(gedAccount.getProvinceName()) || StringUtils.isEmpty(gedAccount.getCompanyBankOfDeposit()))){
                ajaxView.setStatus("4");
                ajaxView.setMessage("资金账户信息不完整");
            }else if(gedAccount == null){
                ajaxView.setStatus("3");
                ajaxView.setMessage("请注意！该用户没有开通资金账户！");
            }else{
                ajaxView.setSuccess();
            }
        } catch (Exception e) {
            logger.error("查询资金账户失败！", e);
            ajaxView.setFailed();
            ajaxView.setMessage("查询资金账户失败，请联系管理员");
        }
        return ajaxView;
    }

    @RequestMapping(value = "checkIsConfirm")
    @ResponseBody
    public AjaxView checkIsConfirm(String applyNo, Model model, RedirectAttributes redirectAttributes) {
        AjaxView rtn = new AjaxView();
        /*String checkSignContract = contractService.checkIsSignContract(applyNo);
        if(StringUtils.isNotEmpty(checkSignContract)) {
            return rtn.setFailed().setMessage(checkSignContract);
        }*/
        /*ApplyRelation applyRelation=applyRelationService.getByApplyNoAndRoleType(applyNo,"8");
        if(StringUtils.isEmpty(applyRelation.getIsConfirm())||"0".equals(applyRelation.getIsConfirm())) {
            return rtn.setFailed().setMessage("主借企业的担保公司未确认担保关系或者已经拒绝担保关系！");
        }*/
        /*List<ApplyRelation> mainApplyRelation=applyRelationService.getMainAndGuarantor(applyNo,"3","6");
        String flag = "0";
        for (ApplyRelation applyRelation2 : mainApplyRelation) {
            if("1".equals(applyRelation2.getIsConfirm())) {//至少有一个
                flag="1";
                break;
            }
        }
        if("0".equals(flag)) {
            return rtn.setFailed().setMessage("借款企业未确认担保关系或者已经拒绝担保关系！");
        }*/
        //确认开户
        /*String isOpen = applyRelationService.isOpenAccount(applyNo);
        if(StringUtils.isNotEmpty(isOpen)) {
            return rtn.setFailed().setMessage(isOpen);
        }*/
        return rtn.setSuccess();
    }

    @RequestMapping(value = "checkIsSign")
    @ResponseBody
    public AjaxView checkIsSign(String applyNo, Model model, RedirectAttributes redirectAttributes) {
        AjaxView rtn = new AjaxView();
        String checkSignContract = contractService.checkIsSignContract(applyNo);
        if(StringUtils.isNotEmpty(checkSignContract)) {
            return rtn.setFailed().setMessage(checkSignContract);
        }
        return rtn.setSuccess();
    }
    
}
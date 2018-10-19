package com.resoft.credit.mortgagedperson.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.ContractProjectList;
import com.resoft.credit.contract.service.ContractProjectListService;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.gedApiUser.entity.CreGedapiUser;
import com.resoft.credit.gedApiUser.service.CreGedapiUserService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.loanApply.entity.CreCustInfo;
import com.resoft.credit.loanApply.service.CreCustInfoService;
import com.resoft.outinterface.rest.newged.entity.GedApiReturn;
import com.resoft.outinterface.rest.newged.entity.GedRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bouncycastle.math.raw.Mod;
import org.junit.Test;
import org.restlet.engine.adapter.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;
import com.resoft.credit.mortgagedperson.service.MortgagedPersonService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * @reqno:H1601220056
 * @date-designer:2016年1月25日-songmin
 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理 抵押权人Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgagedperson/")
public class MortgagedPersonController extends BaseController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private MortgagedPersonService mortgagedPersonService;
    @Autowired
    private ApplyInfoService applyInfoService;
    @Autowired
    private ContractProjectListService contractProjectListService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private CreGedapiUserService creGedapiUserService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ApplyRegisterService applyRegisterService;
    @Autowired
    private CheckApproveService checkApproveService;
    @Autowired
    private CheckApproveUnionService checkApproveUnionService;
    @Autowired
    private CreGuaranteeCompanyService guaranteeCompanyService;
    @Autowired
    private AccContractDao accContractDao;
    @Autowired
    private CreCustInfoService creCustInfoService;
    @Autowired
    private CheckApproveUnionDao checkApproveUnionDao;



    @ModelAttribute
    public MortgagedPerson get(@RequestParam(required = false) String id) {
        MortgagedPerson entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mortgagedPersonService.get(id);
        }
        if (entity == null) {
            entity = new MortgagedPerson();
        }
        return entity;
    }

    /**
     * @reqno:H1601220056
     * @date-designer:2016年1月25日-songmin
     * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理 分页查询抵押权人信息
     */
    @RequiresPermissions("credit:mortgageInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(MortgagedPerson mortgagedPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MortgagedPerson> page = mortgagedPersonService.findPage(new Page<MortgagedPerson>(request, response), mortgagedPerson);
        model.addAttribute("page", page);
        return "/app/credit/customer/mortgagedPersonList";
    }

    /**
     * @reqno:H1601220071
     * @date-designer:2016年1月27日-songmin
     * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理_新增 跳转抵押权人信息界面
     */
    @RequiresPermissions("credit:mortgageInfo:view")
    @RequestMapping(value = "form")
    public String form(MortgagedPerson mortgagedPerson, Model model) {
        model.addAttribute("MortgagedPerson", mortgagedPerson);
        return "/app/credit/customer/mortgagedPersonForm";
    }

    private LinkedHashMap<String, String> loadAreaData(String areaCode) {
        Map param = Maps.newConcurrentMap();
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

    /**
     * @reqno:H1601220071
     * @date-designer:2016年1月27日-songmin
     * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理_新增 保存抵押权人信息
     */
    @RequiresPermissions("credit:mortgageInfo:view")
    @RequestMapping(value = "save")
    public String save(MortgagedPerson mortgagedPerson, Model model) {
        if (!beanValidator(model, mortgagedPerson)) {
            return form(mortgagedPerson, model);
        }
        try {
            mortgagedPersonService.save(mortgagedPerson);
            addMessage(model, "保存抵押权人成功！");
            model.addAttribute("closeWindow", true);
        } catch (Exception e) {
            logger.error("保存抵押权人失败！", e);
            addMessage(model, "保存抵押权人失败！");
        }

        return form(mortgagedPerson, model);
    }

    /**
     * @reqno:H1601250005
     * @date-designer:2016年1月27日-songmin
     * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理7788_修改、删除
     * @db-z:TABLE表名:字段或条件的相关说明
     * @db-j:TABLE表名:字段或条件的相关说明
     * @e-in-text:ID编号-控件名称:此控件的描述信息
     * @e-in-text:ID编号-控件名称:此控件的描述信息
     * @e-in-list:ID编号-控件名称:此控件的描述信息
     * @e-in-other:ID编号-控件名称:此控件的描述信息
     * @e-tree:ID编号-控件名称:此控件的描述信息
     * @e-out-table:ID编号-控件名称:此控件的描述信息
     * @e-ctrl:ID编号-控件名称:此控件的描述信息
     */
    @RequiresPermissions("credit:mortgageInfo:view")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String ids, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            mortgagedPersonService.deleteList(idList);
        }
        return "success";
    }

    @RequiresPermissions("credit:mortgageInfo:view")
    @RequestMapping("/mortgagedPersonList")
    @ResponseBody
    public List<MortgagedPerson> loadMortgagedPersonList() {
        List<MortgagedPerson> mortgagedPerson = mortgagedPersonService.getMortgagedPerson();
        return mortgagedPerson;
    }

    @RequiresPermissions("credit:contract:view")
    @RequestMapping("/mortgagedPersonSelected")
    @ResponseBody
    public MortgagedPerson mortgagedPersonSelected(String id) {
        MortgagedPerson mortgagedPerson = mortgagedPersonService.getMortgagedPersonById(id);
        String bankNo = DictUtils.getDictLabel(mortgagedPerson.getBankNo(), "BANKS", null);
        mortgagedPerson.setBankNo(bankNo);
        // 查询抵押权人开户行地址
        // 获取省级下拉
        LinkedHashMap<String, String> contBankProvinceMap = loadAreaData("1");// 这里的1表示全国
        // 获取市级下拉
        LinkedHashMap<String, String> contBankCityMap = loadAreaData(mortgagedPerson.getContProvince());
        // 获取区级下拉
        LinkedHashMap<String, String> contBankDistinctMap = loadAreaData(mortgagedPerson.getContCity());

        String contProvice = mortgagedPerson.getContProvince();
        mortgagedPerson.setContProvinceCode(contProvice);
        mortgagedPerson.setContProvince(contBankProvinceMap.get(contProvice));

        String contCity = mortgagedPerson.getContCity();
        mortgagedPerson.setContCityCode(contCity);
        mortgagedPerson.setContCity(contBankCityMap.get(contCity));

        String contDistinct = mortgagedPerson.getContDistinct();
        mortgagedPerson.setContDistinctCode(contDistinct);
        mortgagedPerson.setContDistinct(contBankDistinctMap.get(contDistinct));
        return mortgagedPerson;
    }


    @RequiresPermissions("credit:contract:view")
    @RequestMapping("/middlePersonSelected")
    @ResponseBody
    public MortgagedPerson middlePersonSelected(String id) {
        MortgagedPerson mortgagedPerson = mortgagedPersonService.getMiddlePersonById(id);
        if (mortgagedPerson == null) {
            mortgagedPerson = new MortgagedPerson();
        }
        String bankNo = DictUtils.getDictLabel(mortgagedPerson.getBankNo(), "BANKS", null);
        mortgagedPerson.setBankNo(bankNo);
        // 查询中间人开户行地址
        String contDistinct = mortgagedPerson.getContDistinct();
        mortgagedPerson.setContDistinctCode(contDistinct);
        Area area = this.areaService.get(contDistinct);
        String areaName = "";
        if (area != null) {
            areaName = area.getName();
        }
        mortgagedPerson.setContDistinct(areaName);
        return mortgagedPerson;
    }


    /**
     * 客户信息管理下创建冠易贷账号
     *
     * @return
     */
    @RequiresPermissions("credit:contract:view")
    @RequestMapping(value = "queryContract")
    public String queryContracte(ContractProjectList contractProjectList, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 查询合同下的信息
        Page<ContractProjectList> page = contractProjectListService.findPage(new Page<ContractProjectList>(request, response), contractProjectList);
        List<ContractProjectList> projectlist = page.getList();
        for (ContractProjectList project : projectlist) {
            CreGedapiUser user = creGedapiUserService.queryGedUSerByCustName(project.getCustId()); //根据个人或企业主键 查询冠易贷账号
            if (user != null) {
                project.setGuanyiFlag(user.getGedNumber());
            }
        }

        model.addAttribute("page", page);
        return "/app/credit/customer/creContractXinxi";

    }

    /**
     * 合同数据保存到冠易贷
     */
    @RequestMapping(value = "saveGedApi")
    @ResponseBody
    public AjaxView saveGedApi(HttpServletRequest request)  {

        String contractNo = request.getParameter("contractNo");
        String applyNo = request.getParameter("applyNo");
        String repayContractStatus = request.getParameter("repayContractStatus");
        String isFlag = request.getParameter("isFlag");
        String guanyiFlag = request.getParameter("guanyiFlag"); //冠易贷账号
        String custName = request.getParameter("custName"); //主借人名称
        String custId = request.getParameter("custId");//企业主键
        AjaxView ajaxView = new AjaxView();
        //String repayContractStatus 合同状态

        String json = null;
        GedApiReturn gedApiReturn = null;

        Map<String, Object> paramMap = new ConcurrentHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //根据合同编号查询合同信息

        paramMap.put("contractCode", contractNo);//合同编号
        paramMap.put("applyId", applyNo);//申请编号
        paramMap.put("userRole", isFlag); //1个人 2 企业 区分标识
        paramMap.put("custName",custName); //客户名称
        if (repayContractStatus != "" && repayContractStatus != null) {
            paramMap.put("status", Integer.parseInt(repayContractStatus));//借款状态
        }
        Contract contract = contractService.getContractByContractNo(contractNo);
        if (null != contract) {
            paramMap.put("idNum", contract.getIdNum());//证件号码
            paramMap.put("productType", contract.getApplyProductTypeCode());//产品类型(借款类型)
            paramMap.put("applyPeriod", contract.getApproPeriodValue());//申请期限
            CheckApproveUnion union = checkApproveUnionService.get(contract.getApproId());//联合授信下的子订单编号
            paramMap.put("rateDay", contract.getApproYearRate());//月利率

            if (null != union) {
                paramMap.put("applyIdChild", contract.getApproId()); //子订单编号
            }
        }
        //查询acc合同表
        com.resoft.accounting.contract.entity.Contract accContract = accContractDao.queryContractByContractNo(contractNo);
        if (null != accContract) {
            if (null != (accContract.getFactGuaranteeGold())) {
                paramMap.put("cashDeposit", accContract.getFactGuaranteeGold());//实收保证金
            } else {
                paramMap.put("cashDeposit", "0");//实收保证金
            }

            if (null != accContract.getFactGuaranteeFee()) {
                paramMap.put("guaranteeFee", accContract.getFactGuaranteeFee());//实收担保费
            } else {
                paramMap.put("guaranteeFee", "0");//实收担保费
            }
            if(null !=  accContract.getServiceFee()){
                paramMap.put("serviceFee", accContract.getServiceFee());//服务费 SERVICE_FEE
            }else{
                paramMap.put("serviceFee", "0");//服务费 SERVICE_FEE
            }
            paramMap.put("creditAmount",accContract.getContractAmount());//放款额度  (金额)
            paramMap.put("replyTerm", accContract.getApproPeriodValue());////批复期限 (值)

            String loanPlatForm = accContract.getLoanPlatform();
            if(loanPlatForm.equals("北京")){
                loanPlatForm = "10040001";
                paramMap.put("serviceProvinceId",  loanPlatForm);//服务方所在地
            }
            if(loanPlatForm.equals("上海")){
                loanPlatForm = "10040002";
                paramMap.put("serviceProvinceId",  loanPlatForm);//服务方所在地
            }
            if(loanPlatForm.equals("天津")){
                loanPlatForm = "10040003";
                paramMap.put("serviceProvinceId",  loanPlatForm);//服务方所在地
            }

            //loanDate;//放款日期
            paramMap.put("loanDate", sdf.format(accContract.getLoanDate())); //放款日期


        }
        ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNoAndGed(applyNo);
        if (null != applyRegister) {
            paramMap.put("applyDate", sdf.format(applyRegister.getCreateDate()));//申请日期
            paramMap.put("contactPhone", applyRegister.getMobileNum());//移动电话
        }
        if("1".equals(isFlag)){
            paramMap.put("contactPhone", guanyiFlag);//移动电话
        }
        CompanyInfo companyInfo = null;
        companyInfo =  companyInfoService.get(custId);
        if (null != companyInfo) {
            paramMap.put("companyName", companyInfo.getBusiRegName());//企业名称
            paramMap.put("companyCardType", "1");//企业证件类型
            paramMap.put("companyCardNum", companyInfo.getUnSocCreditNo());//企业证件号码
            paramMap.put("province", companyInfo.getRegProvince());//省
            paramMap.put("city", companyInfo.getRegCity());//市
            paramMap.put("contCity", companyInfo.getRegCity());//市
            paramMap.put("district", companyInfo.getRegDistinct());//县
            paramMap.put("legalName",companyInfo.getCorporationName()); //法人名称
            paramMap.put("legalMobile", companyInfo.getCorporationMobile()); //法人手机号
            paramMap.put("legalCardNumber",companyInfo.getCorporationCardIdNo()); //法人证件号码
        }

        ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(applyNo);
        if (applyInfo != null) {
            paramMap.put("loanPurpose", applyInfo.getLoanPurpost());//借款用途
        }

        Map<String, String> paramCheckApprove = new ConcurrentHashMap<>();
        paramCheckApprove.put("applyNO", applyNo);
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(applyNo);
        CreGuaranteeCompany guarantorCompany = guaranteeCompanyService.getByApplyNoAndRoleType(applyNo, "8");
        if ("gqcc_loan_union".equals(procDefKey)) {
            //这是联合授信
            CheckApproveUnion checkApproveUnion = checkApproveUnionDao.queryUnionByContract(contractNo);
            if(null != checkApproveUnion){
                paramMap.put("repaymentStyle", DictUtils.getDictLabel(checkApproveUnion.getApproLoanRepayType(), "LOAN_REPAY_TYPE", null)); //还款方式
                paramMap.put("receivableCashDeposit", checkApproveUnion.getMarginAmount().toString()); //应收保证金
            }
            if (null != guarantorCompany) {
                BigDecimal costRate = new BigDecimal(guarantorCompany.getCostRate());
                BigDecimal contractAmount = checkApproveUnion.getContractAmount();
                BigDecimal receivableGuaranteeFee = contractAmount.multiply(costRate).multiply(new BigDecimal("0.01"));
                paramMap.put("receivableGuaranteeFee", receivableGuaranteeFee.toString()); //应收担保费
            }
            paramMap.put("receivableGuaranteeFee", "0"); //应收担保费
            //  paramMap.put("creditAmount", checkApproveUnion.getLoanAmount());  //放款额度  (金额)

            paramMap.put("applyAmount", checkApproveUnion.getContractAmount());//申请金额

        } else {
            // 这是非联合授信
            Map<String, String> map = new ConcurrentHashMap<>();
            map.put("applyNo", applyNo);
            List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(map);
            CheckApprove checkApprove = null;
            if (checkApproveList.size() > 0) {
                checkApprove = checkApproveList.get(0);
                if (null != checkApprove) {
                    paramMap.put("repaymentStyle", DictUtils.getDictLabel(checkApprove.getApproLoanRepayType(), "LOAN_REPAY_TYPE", null)); //还款方式
                    paramMap.put("receivableCashDeposit", checkApprove.getMarginAmount().toString()); //应收保证金
                    //  paramMap.put("creditAmount", checkApprove.getLoanAmount());  //放款额度  (金额)
                    paramMap.put("applyAmount", checkApprove.getContractAmount());//申请金额
                    if (null != guarantorCompany) {
                        BigDecimal costRate = new BigDecimal(guarantorCompany.getCostRate());
                        BigDecimal contractAmount = checkApprove.getContractAmount();
                        BigDecimal receivableGuaranteeFee = contractAmount.multiply(costRate).multiply(new BigDecimal("0.01"));
                        paramMap.put("receivableGuaranteeFee", receivableGuaranteeFee.toString()); //应收担保费
                    }
                    paramMap.put("receivableGuaranteeFee", "0"); //应收担保费
                }
            }
        }
        try {
            json = JsonUtil.getJSONString(paramMap);
            System.out.println(json);

            gedApiReturn = Facade.facade.PushGedApiXinxi(json,contractNo);

            if (null != gedApiReturn) {
                // gedApiReturn中的Code码 : 0 成功 602  申请单已存在    601  个人账户不存在
                if ("0".equals(gedApiReturn.getCode())) {
                    ////往合同表中修改保存冠易贷状态 ;
                    int flag = contractService.updateContractGedApiSave(contractNo);
                    ApplyRegister register = new ApplyRegister();
                    register.setApplyNo(applyNo);
                    register.setSendGED("2");
                    //老数据推送冠易贷的时候 修改sengGed 为 2
                    applyRegisterService.updateRegisterByApplyNo(register);
                    if (flag == 1) {
                        ajaxView.setSuccess();
                        ajaxView.setMessage("推送订单成功,正在刷新!");
                    }
                } else if ("602".equals(gedApiReturn.getCode())) {
                    //申请单已存在
                    int flag = contractService.updateContractGedApiSave(contractNo);
                    ApplyRegister register = new ApplyRegister();
                    register.setApplyNo(applyNo);
                    register.setSendGED("2");
                    //老数据推送冠易贷的时候 修改sengGed 为 2
                    applyRegisterService.updateRegisterByApplyNo(register);
                    if (flag == 1) {
                        ajaxView.setSuccess();
                        ajaxView.setMessage("推送订单成功,正在刷新!");
                    } else {
                        ajaxView.setFailed().setMessage("访问冠易贷异常！");
                    }
                } else if ("601".equals(gedApiReturn.getCode())) {
                    //冠易贷系统中账号不存在
                    // guanyiFlag 根据冠易贷账号删除
                    creGedapiUserService.deleteGedapiUser(guanyiFlag);
                    ajaxView.setFailed().setMessage("推送失败,请使用社会统一信用代码创建账号");
                }

            } else {
                String exception = gedApiReturn.getException();
                logger.info(exception);
                ajaxView.setFailed().setMessage("访问冠易贷异常! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxView.setFailed().setMessage("访问冠易贷异常！");
        }
        return ajaxView;
    }

    @RequestMapping(value = "saveGedApiXinxi")
    public String showCreateAccount(String borrowName,String borrowType, String unSocCreditNo, String custName, String mobileNum, String corporaTionMobile, String isFlag, String applyNo, String custId,String recBankcareNo, Model model) {

        model.addAttribute("applyNo", applyNo);
        model.addAttribute("custId", custId);
        model.addAttribute("custName", custName);

        if ("1".equals(isFlag)) {
            //这是个人
            model.addAttribute("borrowName", borrowName);
            model.addAttribute("mobileNum", mobileNum);
            model.addAttribute("recBankcareNo",recBankcareNo);
            return "app/credit/guaranteeCompany/GedApiCustInfo";
        } else {
            //这是企业
            CompanyInfo companyInfo = companyInfoService.get(custId);
            if(StringUtils.isBlank(companyInfo.getUnSocCreditNo())){
                //企业没有社会统一信用代码
                String companyinfoName = companyInfo.getBusiRegName();
                model.addAttribute("companyinfoName",companyinfoName);

                return "app/credit/guaranteeCompany/updateCompanyinfo";
            }


            model.addAttribute("borrowName", borrowName);
            model.addAttribute("unSocCreditNo", unSocCreditNo);
            model.addAttribute("corporaTionMobile", corporaTionMobile);
            model.addAttribute("mobileNum", mobileNum);
            model.addAttribute("borrowType", borrowType);
            model.addAttribute("recBankcareNo",recBankcareNo);
            return "app/credit/guaranteeCompany/GedApiCompany";
        }
    }
    @RequestMapping(value = "updateCompanyCreditNo")
    @ResponseBody
    public AjaxView updateCompanyCreditNo(CompanyInfo companyInfo){

        AjaxView ajaxView = new AjaxView();

        try {
            companyInfoService.InsertunSocCreditNo(companyInfo);
            ajaxView.setSuccess().setMessage("添加成功,正在刷新");

        }catch (Exception e){
            e.printStackTrace();
            ajaxView.setFailed().setMessage("添加失败");
        }
        return  ajaxView;
    }




    @RequestMapping(value = "saveCompanyGedapi")
    @ResponseBody
    public AjaxView saveCompanyGedapi(String borrowName,String unSocCreditNo, String corporaTionMobile, String mobileNum, String borrowType, String accountType,String custId,String applyNo,String recBankcareNo) {
        AjaxView ajaxView = new AjaxView();

        GedRegisterRequest gedRegisterRequest = null;
        //0借款人1自有担保人2自有担保企业3合作企业
        String account = "";

        if ("1".equals(accountType)) {
            gedRegisterRequest = new GedRegisterRequest("1", unSocCreditNo, mobileNum, "0");
            gedRegisterRequest.setRegisterType("0");
            account = mobileNum;
        }
        if ("2".equals(accountType)) {
            gedRegisterRequest = new GedRegisterRequest("1", unSocCreditNo, corporaTionMobile, "0");
            gedRegisterRequest.setRegisterType("1");
            account = corporaTionMobile;
        }
        if ("3".equals(accountType)) {
            gedRegisterRequest = new GedRegisterRequest("1", unSocCreditNo, null, "0");
            gedRegisterRequest.setRegisterType("2");
            account = unSocCreditNo;
        }

        gedRegisterRequest.setUserType(1);
        gedRegisterRequest.setBankCode(recBankcareNo);
        gedRegisterRequest.setStatus(1);//老系统用户注册
        gedRegisterRequest.setCode(unSocCreditNo);//社会统一新员工代码
        try {
            GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
            System.out.println(gedRegisterResponse);
            if (gedRegisterResponse != null) {
                if ("0".equals(gedRegisterResponse.getCode())){

                    //插入记录
                    if (custId != null) {
                        creGedapiUserService.saveRegisterInfo(custId, "2", account);
                        ajaxView.setSuccess();//注册成功
                        ajaxView.setMessage("注册成功,正在刷新!");
                    }
                } else if("110".equals(gedRegisterResponse.getCode())){
                    //用户已经注册
                    ajaxView.setFailed();//注册失败
                        ajaxView.setMessage("手机号已经注册,请使用社会统一信用代码注册");
                    return ajaxView;
                }else {
                    String exception = gedRegisterResponse.getException();

                    ajaxView.setFailed().setMessage(exception);
                }
            } else {
                ajaxView.setFailed().setMessage("访问冠易贷异常！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ajaxView;
    }

    /**
     * 给个人注册冠易贷账号
     */
    @RequestMapping(value = "saveCustGedApi")
    @ResponseBody
    public AjaxView saveCustGedApi(String applyNo,  String custId, String mobileNum,String recBankcareNo,String recBankcareNo111) {

        AjaxView ajaxView = new AjaxView();

        GedRegisterRequest gedRegisterRequest = new GedRegisterRequest();
        gedRegisterRequest.setMobile(mobileNum);
        gedRegisterRequest.setUserType(0); //个人
        gedRegisterRequest.setUserRole("0"); //借款人
        gedRegisterRequest.setRegisterType("0"); //主借人
        gedRegisterRequest.setBankCode(recBankcareNo); //银行开户行
        gedRegisterRequest.setStatus(1);//老系统用户注册
        String account = mobileNum;

        GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
        if (gedRegisterResponse != null) {
            if ("0".equals(gedRegisterResponse.getCode())) {
                //插入记录
                if (custId != null) {
                    creGedapiUserService.saveRegisterInfo(custId, "1", account);
                    ajaxView.setSuccess();//注册成功
                    ajaxView.setMessage("注册成功,正在刷新!");
                }else{
                    ajaxView.setFailed().setMessage("创建失败！");
                }
            }else if ("110".equals(gedRegisterResponse.getCode())) {
                creGedapiUserService.saveRegisterInfo(custId, "1", account);
                ajaxView.setSuccess();//注册成功
                ajaxView.setMessage("注册成功,正在刷新!");
            }
            else {
                String exception = gedRegisterResponse.getException();
                ajaxView.setFailed().setMessage(exception);
            }
        } else {
            ajaxView.setFailed().setMessage("访问冠易贷异常！");
        }

        return ajaxView;
    }
}

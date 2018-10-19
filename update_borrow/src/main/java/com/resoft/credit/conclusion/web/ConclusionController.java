package com.resoft.credit.conclusion.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.gedApiUser.service.CreGedapiUserService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.conclusion.service.ConlusionService;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.rest.newged.entity.GedRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/credit/conclusion")
public class ConclusionController extends BaseController {

	@Autowired
	private ConlusionService conlusionService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private CreGedapiUserService creGedapiUserService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private CreApplyCompanyRelationService creApplyCompanyRelationService;

    @Autowired
    private CheckApproveService checkApproveService;
	
	@RequiresPermissions("credit:conclusion:view")
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		/*if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
			model.addAttribute("isNoUnion", "1");
		}*/
		return "app/credit/conclusion/conclusionIndex";
	}

	@RequiresPermissions("credit:conclusion:edit")
	 @ResponseBody
	 @RequestMapping(value = "save")
	 public AjaxView save(ActTaskParam actTaskParam, Model model, String passFlag, String suggestion, HttpServletRequest request) {
	  AjaxView rtn = new AjaxView();
	  ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
	  String applyNo = actTaskParam.getApplyNo();
	  ApplyRelation coCustInfoRelation = new ApplyRelation();
	  if (applyInfo == null) {
	   rtn.setFailed().setMessage("请先填写借款申请信息！！");
	   return rtn;
	  }
	  // 监看有没有担保公司
	  Map<String, String> applyRelationMap = new HashMap<>();
	  applyRelationMap.put("applyNo", actTaskParam.getApplyNo());
	  applyRelationMap.put("roleType", "8"); // 8是担保公司类型
	  //根据申请编号 查询担保公司信息
	  CreGuaranteeCompany company = creGuaranteeCompanyService.queryApplyCompany(applyRelationMap);
	  if (null == company) {
	   rtn.setFailed().setMessage("请先填写担保信息下的第三方担保公司！！");
	   return rtn;
	  }
	  coCustInfoRelation.setApplyNo(applyNo);
	  coCustInfoRelation.setRoleType(Constants.ROLE_TYPE_DBR);
	  List<ApplyRelation> coCustRelations = applyRelationService.findList(coCustInfoRelation);
	  if (coCustRelations == null || coCustRelations.size() <= 0) {
	   rtn.setFailed().setMessage("请先填写担保信息下的担保人！！");
	   return rtn;
	  }
	  //查询批量借款企业下的担保人 第三方担保公司
	  Map<String, Object> params = Maps.newConcurrentMap();
	  params.put("applyNo", applyNo);
	  params.put("roleType", Constants.CONTACT_RELATIONS_COMPANY); // 9 标示批量借款企业之间的关联
	  List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);
	  if (companyInfoList != null && companyInfoList.size() > 0) {
	   //遍历批量借款企业下
	   if (companyInfoList != null && companyInfoList.size() > 0) {
	    for (int i = 0; i < companyInfoList.size(); i++) {
	     CompanyInfo companyInfo = new CompanyInfo();
	     companyInfo = companyInfoList.get(i);
	     //获得批量借款企业的主键
	     Map<String, Object> paramMap = new ConcurrentHashMap<>();
	     //查询
	     paramMap.put("applyNo", applyNo);
	     paramMap.put("custId", companyInfo.getId());
	     paramMap.put("RoleType", "3");
	     paramMap.put("DelFlag", "0");
	     CreApplyCompanyRelation companyRelation =
	       creApplyCompanyRelationService.queryCompanyRelationList(paramMap);
	     if (companyRelation == null) {
	      rtn.setFailed().setMessage("请先建立  " + companyInfo.getBusiRegName() + "  下的第三方担保公司");
	      return rtn;
	     }


	     //查询批量借款企业下的担保人
	     Map<String, Object> paramMap1 = new HashMap<>();
	     paramMap1.put("applyNo", applyNo);
	     paramMap1.put("custId", companyInfo.getId());
	     paramMap1.put("RoleType", "1");
	     paramMap1.put("DelFlag", "0");
	     List<CreApplyCompanyRelation> relationList = creApplyCompanyRelationService.queryCompanyRElaList(paramMap1);
	     if (relationList == null || relationList.size() <= 0) {
	      rtn.setFailed().setMessage("请先建立  " + companyInfo.getBusiRegName() + "  下的担保人");
	      return rtn;
	     }

	    }

	   }

	  }
	  if ("no".equals(passFlag)) {
			ApplyRegister applyRegisterA = new ApplyRegister();
			applyRegisterA.setApplyNo(actTaskParam.getApplyNo());
			List<ApplyRegister> registerListA = applyRegisterService.findList(applyRegisterA);
			if (!registerListA.isEmpty()) {
				applyRegisterA = registerListA.get(0);
			}
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegisterA.getProcDefKey())){
				Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
				paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
				List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
				if (checkApproveList.size() >0) {
					CheckApprove checkApprove = checkApproveList.get(0);
					creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
				}
				Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"0");//0是非联合授信
			}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegisterA.getProcDefKey())) {
				creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
				Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"1");//1联合授信
			}
		}
	   String checkApproveProductType = applyInfo.getApplyRegister().getApplyProductTypeCode();
	   rtn = conlusionService.save(actTaskParam, passFlag, suggestion, checkApproveProductType);

	  return rtn;
	 }
	
	@RequestMapping(value = {"isRegisterGED", ""})
	public String isRegisterGED(String applyNo,HttpServletRequest request, HttpServletResponse response, Model model) {
		//访问接口，查询冠e贷
				//只对非联合授信
		//ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo",applyNo);
		Map<String, String> resultCom = applyInfoService.findGEDNeedCom(param);//comId
		Map<String, String> resultInfo = applyInfoService.findGEDNeedInfo(param);//custId
		model.addAttribute("comId", resultCom.get("comId"));
		model.addAttribute("custId", resultInfo.get("custId"));
		//获取主借人企业统一社会信用代码,企业名称,法人手机号,手机号
		String unSocCreditNo=null;
		if(resultCom!=null){
			String corporationMobile = resultCom.get("corporationMobile");
			//String busiRegName = resultCom.get("busiRegName");//企业名字
			unSocCreditNo = resultCom.get("unSocCreditNo");
			model.addAttribute("corporationMobile", corporationMobile);
			model.addAttribute("unSocCreditNo", unSocCreditNo);
			String corporationName=resultCom.get("corporationName");
			model.addAttribute("corporationName", corporationName);
		}
		if(resultInfo!=null){
			String mobileNum=resultInfo.get("mobileNum");
			model.addAttribute("mobileNum", mobileNum);
			String custName=resultInfo.get("custName");
			model.addAttribute("custName", custName);
		}
		if(resultCom==null||resultInfo==null){//主借人企业没填不访问查询接口
			model.addAttribute("hasNoCompany", "1");
		}else{
			//访问接口
			FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",unSocCreditNo);
			findIsRegister.setMobile(resultInfo.get("mobileNum"));
			findIsRegister.setUserRole("0");//借款人
			FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
//			if(findIsRegisterResponse!=null){
//				if(findIsRegisterResponse.getCode()!=110){//冠易贷返回异常信息，没有注册
//					findIsRegister=new FindIsRegisterRequest("1",unSocCreditNo);
//					findIsRegister.setMobile(resultCom.get("unSocCreditNo"));
//					findIsRegister.setUserRole("2");//借款人
//					 findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, applyNo);
//				}
//			}
            if (findIsRegisterResponse != null) {
                //String data =findIsRegisterResponse.getData().get//电话号码
                String data = null;
                if (findIsRegisterResponse.getData() != null) {
                    data = findIsRegisterResponse.getData().getMobile();
                }
                String code = findIsRegisterResponse.getCode();
                if ("0".equals(code)) {//冠易贷返回异常信息，没有注册
                    model.addAttribute("findException", findIsRegisterResponse.getException());
                } else {//不为0表示已经注册
                    model.addAttribute("findGedNum", data);
                    model.addAttribute("isHasGEDAccount", "1");//冠e贷已经有账号
                }
            } else {//查询异常
                model.addAttribute("findException", "查询注册失败，请刷新页面！");
            }
        }
        model.addAttribute("applyNo", applyNo);
        return "app/credit/conclusion/regsiterGED";
    }

    @ResponseBody
    @RequestMapping(value = "registerGEDAccount")
    public AjaxView registerGEDAccount(String comId, String custId, String mobileNum, String corporationMobile, String applyNo, String registerPhone, String unSocCreditNo, Model model, HttpServletRequest request) {
        AjaxView rtn = new AjaxView();
        //0借款人1自有担保人2自有担保企业3合作企业
        GedRegisterRequest gedRegisterRequest = null;
        ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
        
        String account = "";
        if ("1".equals(registerPhone)) {
            gedRegisterRequest = new GedRegisterRequest("1", unSocCreditNo, mobileNum, "0");
            gedRegisterRequest.setRegisterType("0");
            account = mobileNum;
        } else {
            gedRegisterRequest = new GedRegisterRequest("1", unSocCreditNo, corporationMobile, "0");
            gedRegisterRequest.setRegisterType("1");
            account = corporationMobile;
        }
        gedRegisterRequest.setUserType(Integer.parseInt(applyRegisterByApplyNo.getCustType())-1);
        GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
        if (gedRegisterResponse != null) {
            if ("0".equals(gedRegisterResponse.getCode())){
                rtn.setSuccess();//注册成功
                rtn.setMessage(registerPhone);
                //插入cust
                if ("1".equals(registerPhone)) {
                    //custInfoService.saveRegisterType(custId, account);
                    creGedapiUserService.saveRegisterInfo(custId, "1", account);
                    rtn.setMessage(mobileNum);
                } else {
                    //companyInfoService.saveRegisterType(account, comId);
                    creGedapiUserService.saveRegisterInfo(comId, "2", account);
                    rtn.setMessage(corporationMobile);
                }
            } else {
                String exception = gedRegisterResponse.getException();
                rtn.setFailed().setMessage(exception);
            }
        } else {
            rtn.setFailed().setMessage("访问冠易贷异常！");
        }
        return rtn;
    }

}
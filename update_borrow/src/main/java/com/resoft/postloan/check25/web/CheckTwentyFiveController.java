package com.resoft.postloan.check25.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.GuarantyContract.entity.GuarantyContract;
import com.resoft.multds.credit.GuarantyContract.service.GuarantyContractService;
import com.resoft.postloan.accountCleanAllocate.service.AccountCleanAllocateService;
import com.resoft.postloan.accountCleanApprove.service.AccountCleanApproveService;
import com.resoft.postloan.check25.entity.CheckTwentyFive;
import com.resoft.postloan.check25.service.CheckTwentyFiveService;
import com.resoft.postloan.checkDaily.service.CheckDailyService;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfo;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfoVO;
import com.resoft.postloan.checkTwentyFiveInfo.entity.Compliance;
import com.resoft.postloan.checkTwentyFiveInfo.entity.Guarantor;
import com.resoft.postloan.checkTwentyFiveInfo.entity.GuarantorCompany;
import com.resoft.postloan.checkTwentyFiveInfo.entity.MainBorrower;
import com.resoft.postloan.checkTwentyFiveInfo.entity.MainBorrowerCompany;
import com.resoft.postloan.checkTwentyFiveInfo.service.CheckTwentyFiveInfoService;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/*
 * 25日复核Controller
 * @author yanwanmei
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkTwentyFive")
public class CheckTwentyFiveController extends BaseController {

	@Autowired
	private CheckTwentyFiveService checkTwentyFiveService;
	@Autowired
	private CheckTwentyFiveInfoService checkTwentyFiveInfoService;
	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private GuarantyContractService guarantyContractService;
	
	@Autowired
	private AccountCleanAllocateService accountCleanAllocateService;
	@Autowired
	private AccountCleanApproveService accountCleanApproveService;
	@Autowired
	private CheckDailyService checkDailyService;

	@ModelAttribute
	public CheckTwentyFive get(@RequestParam(required = false) String id) {
		CheckTwentyFive entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkTwentyFiveService.get(id);
		}
		if (entity == null) {
			entity = new CheckTwentyFive();
		}
		return entity;
	}

	@RequiresPermissions("postloan:checkTwentyFive:view")
	@RequestMapping(value = { "list", "" })
	public String list(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model, CheckTwentyFiveInfoVO checkTwentyFiveInfoVO, CheckTwentyFive checkTwentyFive, MainBorrower mainBorrower, Guarantor guarantor, MainBorrowerCompany mainBorrowerCompany, GuarantorCompany guarantorCompany, Compliance compliance, String flag,String dateFlag) {
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("contractNo", plContract.getContractNo());
			paramMap.put("custName", plContract.getCustName());
			paramMap.put("approProductTypeId", plContract.getApproProductTypeId());
			paramMap.put("companyId", UserUtils.getUser().getCompany().getId());
			paramMap.put("companyParentIds", UserUtils.getUser().getCompany().getParentIds());
            
			Page page = null;
			List<String> checkedTypeList = new ArrayList<String>();
			List<String> allocateTypeList = new ArrayList<String>();
			
			//待分配列表
			if (flag.equalsIgnoreCase(Constants.CHECK_TWENTY_FIVE_TOALLOCATE)) {
				//查询合同状态为放款成功，有逾期还款中，无逾期还款中的数据
				List<String> contractStatusList = new ArrayList<String>();
				contractStatusList.add(Constants.CONTRACT_STATE_FKCG);
				contractStatusList.add(Constants.CONTRACT_STATE_WYQ_HKZ);
				contractStatusList.add(Constants.CONTRACT_STATE_YYQ_HKZ);
				paramMap.put("contractStatusList", contractStatusList);
				paramMap.put("dateFlag", dateFlag);
				page = checkTwentyFiveAllocateService.getToAllocatedPLContract(new Page<MyMap>(request, response), paramMap);
			}
			//已分配列表(没有加时间限制，查出此借后经理所有已办任务)
			if (flag.equalsIgnoreCase(Constants.CHECK_TWENTY_FIVE_HASALLOCATE)) {
				paramMap.put("allocateById", UserUtils.getUser().getId());
				page = checkTwentyFiveAllocateService.getHasAllocatedPLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			//待复核
			if (flag.equalsIgnoreCase(Constants.CHECK_TWENTY_FIVE_TOCHECK)) {
				paramMap.put("checkedById", UserUtils.getUser().getId());
				//待复核的包括未复核和清收打回的
				checkedTypeList.add(Constants.TO_BE_ALLOCATED);
				checkedTypeList.add(Constants.ACCOUNT_CLEAN_BEAT_BACK);
				paramMap.put("checkedTypeList", checkedTypeList);
				page = checkTwentyFiveAllocateService.getHasAllocatedPLContract(new Page<MyMap>(request, response), paramMap);
			}
			//已复核（包括25日复核，签署保证合同，清收，法务）
			if (flag.equalsIgnoreCase(Constants.CHECK_TWENTY_FIVE_HASCHECKED)) {
				paramMap.put("checkedById", UserUtils.getUser().getId());
				checkedTypeList.add(Constants.HAS_BEEN_ALLOCATED);
				checkedTypeList.add(Constants.SIGN_GUARANTY_CONTRACT);
				checkedTypeList.add(Constants.ACCOUNT_CLEAN_APPROVE);
				checkedTypeList.add(Constants.DEBT_COLLECTION_LEGAL);
				paramMap.put("checkedTypeList", checkedTypeList);
				page = checkTwentyFiveAllocateService.getHasAllocatedPLContract(new Page<MyMap>(request, response), paramMap);
			}
			//已复核(查询当前机构及以下机构)
			if (flag.equalsIgnoreCase(Constants.ALL_CHECK_TWENTY_FIVE_DATA)) {
				paramMap.put("companyId", UserUtils.getUser().getCompany().getId());
				checkedTypeList.add(Constants.HAS_BEEN_ALLOCATED);
				checkedTypeList.add(Constants.SIGN_GUARANTY_CONTRACT);
				checkedTypeList.add(Constants.ACCOUNT_CLEAN_APPROVE);
				checkedTypeList.add(Constants.DEBT_COLLECTION_LEGAL);
				paramMap.put("checkedTypeList", checkedTypeList);
				page = checkTwentyFiveAllocateService.getAllHasAllocatedPLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			// 已复核,签署保证合同
			if (flag.equalsIgnoreCase(Constants.HAS_SIGN_GUARANTY_CONTRACT)) {
				List<String> contractNoList = new ArrayList<String>();
				List<String> dailyContractNoList = new ArrayList<String>();
				//从pl_check_25_allocate和pl_check_daily中查询需要签署保证合同的合同
				paramMap.put("checkedById", UserUtils.getUser().getId());
				checkedTypeList.add(Constants.SIGN_GUARANTY_CONTRACT);
				paramMap.put("checkedTypeList", checkedTypeList);
				
				contractNoList = checkTwentyFiveAllocateService.getContractNos(paramMap);
				
				paramMap.put("checkedBy", UserUtils.getUser().getName());
				paramMap.put("checkDailyProc", Constants.CHECK_DAILY_PROC_QSBZHT);
				dailyContractNoList = checkDailyService.getContractNoList(paramMap);
				paramMap.put("currUserCompanyId", UserUtils.getUser().getCompany().getId());
				paramMap.put("currUserCompanyParentsIds", UserUtils.getUser().getCompany().getParentIds());
				contractNoList.addAll(dailyContractNoList);
				paramMap.put("contractNoList", contractNoList);
				page = plContractService.getToCheck25PLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			// 待清收(从pl_account_clean_allocate表中查询清收数据)
			if (flag.equalsIgnoreCase(Constants.TO_ACCOUNT_CLEAN)) {
				paramMap.put("checkedById", UserUtils.getUser().getId());
				allocateTypeList.add(Constants.TO_BE_ALLOCATED);
				paramMap.put("allocateTypeList", allocateTypeList);
				page = accountCleanAllocateService.getAccountCleanPLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			// 已清收
			if (flag.equalsIgnoreCase(Constants.HAS_ACCOUNT_CLEAN)) {
				paramMap.put("checkedById", UserUtils.getUser().getId());
				allocateTypeList.add(Constants.HAS_BEEN_ALLOCATED);
				allocateTypeList.add(Constants.ACCOUNT_CLEAN_OVER);
				paramMap.put("allocateTypeList", allocateTypeList);
				page = accountCleanAllocateService.getAccountCleanPLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			//清收待分配
			if (flag.equalsIgnoreCase(Constants.TO_ACCOUNT_CLEAN_ALLOCATE)) {
				paramMap.put("hqCheckResult", Constants.PASS_THROUGH);
				page = accountCleanApproveService.getAccountCleanApprovePLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			//清收已分配(查出待清收和已清收)
			if (flag.equalsIgnoreCase(Constants.HAS_ACCOUNT_CLEAN_ALLOCATE)) {
				paramMap.put("allocateById", UserUtils.getUser().getId());
				page = accountCleanAllocateService.getAccountCleanPLContract(new Page<MyMap>(request, response), paramMap);
			}
			
			model.addAttribute("page", page);
			model.addAttribute("plContract", plContract);
			model.addAttribute("flag", flag);
			model.addAttribute("dateFlag", dateFlag);
		} catch (Exception e) {
			logger.error("查询25日复核数据失败", e);
		}
		return "app/postloan/check25/check25List";
	}

	@RequiresPermissions("postloan:checkTwentyFive:view")
	@RequestMapping(value = "form")
	public String form(CheckTwentyFive checkTwentyFive, Model model) {
		model.addAttribute("checkTwentyFive", checkTwentyFive);
		return "app/postloan/check25/checkTwentyFiveForm";
	}

	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "save")
	public String save(CheckTwentyFive checkTwentyFive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkTwentyFive)) {
			return form(checkTwentyFive, model);
		}
		checkTwentyFiveService.save(checkTwentyFive);
		addMessage(redirectAttributes, "保存25日复核成功");
		return "redirect:" + Global.getAdminPath() + "/check25/checkTwentyFive/?repage";
	}

	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckTwentyFive checkTwentyFive, RedirectAttributes redirectAttributes) {
		checkTwentyFiveService.delete(checkTwentyFive);
		addMessage(redirectAttributes, "删除25日复核成功");
		return "redirect:" + Global.getAdminPath() + "/check25/checkTwentyFive/?repage";
	}

	@RequiresPermissions("postloan:checkTwentyFive:view")
	@RequestMapping(value = "check")
	public String check(String contractNo,String allocateId, Model model, CheckTwentyFiveInfo checkTwentyFiveInfo, CheckTwentyFiveInfoVO checkTwentyFiveInfoVO, CheckTwentyFive checkTwentyFive, MainBorrower mainBorrower, Guarantor guarantor, MainBorrowerCompany mainBorrowerCompany, GuarantorCompany guarantorCompany, Compliance compliance, HttpServletRequest request,String checkedType,String applyNo) {
		try {
			String readOnly = request.getParameter("readOnly");
			checkTwentyFive = checkTwentyFiveService.getTwentyFiveByAllocateId(allocateId);
			List<CheckTwentyFiveInfo> checkTwentyFiveInfoList = checkTwentyFiveInfoService.getCheckTwentyFiveInfoByContractNo(allocateId);
			checkTwentyFiveInfoVO.setCheckTwentyFive(checkTwentyFive);
			if (checkTwentyFiveInfoList != null && checkTwentyFiveInfoList.size() > 0) {
				for (int i = 0; i < checkTwentyFiveInfoList.size(); i++) {
					if (checkTwentyFiveInfoList.get(i).getRoleType().equalsIgnoreCase(Constants.ROLE_TYPE_ZJR)) {
						mainBorrower.setCheckTwentyFiveInfo(checkTwentyFiveInfoList.get(i));
						checkTwentyFiveInfoVO.setMainBorrower(mainBorrower);
					}
					if (checkTwentyFiveInfoList.get(i).getRoleType().equalsIgnoreCase(Constants.ROLE_TYPE_DBR)) {
						guarantor.setCheckTwentyFiveInfo(checkTwentyFiveInfoList.get(i));
						checkTwentyFiveInfoVO.setGuarantor(guarantor);
					}
					if (checkTwentyFiveInfoList.get(i).getRoleType().equalsIgnoreCase(Constants.ROLE_TYPE_ZJQY)) {
						mainBorrowerCompany.setCheckTwentyFiveInfo(checkTwentyFiveInfoList.get(i));
						checkTwentyFiveInfoVO.setMainBorrowerCompany(mainBorrowerCompany);
					}
					if (checkTwentyFiveInfoList.get(i).getRoleType().equalsIgnoreCase(Constants.ROLE_TYPE_DBQY)) {
						guarantorCompany.setCheckTwentyFiveInfo(checkTwentyFiveInfoList.get(i));
						checkTwentyFiveInfoVO.setGuarantorCompany(guarantorCompany);
					}
					if (checkTwentyFiveInfoList.get(i).getRoleType().equalsIgnoreCase(Constants.ROLE_TYPE_HGX)) {
						compliance.setCheckTwentyFiveInfo(checkTwentyFiveInfoList.get(i));
						checkTwentyFiveInfoVO.setCompliance(compliance);
					}
				}
			}
//			model.addAttribute("readOnly", readOnly);
			model.addAttribute("flag", request.getParameter("flag"));
			model.addAttribute("contractAmount", request.getParameter("contractAmount"));
			model.addAttribute("currCollectionType", Constants.DEBT_COLLECTION_LEGAL);
			model.addAttribute("currCollectionStatus", Constants.TO_BE_ALLOCATED);
			model.addAttribute("currCollectionFrom", Constants.DEBT_COLLECTION__25FH);
			model.addAttribute("contractNo", contractNo);
			model.addAttribute("allocateId", allocateId);
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("custName", URLDecoder.decode(request.getParameter("custName"), "UTF-8"));
			model.addAttribute("checkTwentyFiveInfoVO", checkTwentyFiveInfoVO);
			model.addAttribute("checkedType", checkedType);
		} catch (Exception e) {
			logger.error("查询25日核查信息失败", e);
		}
		return "app/postloan/check25/checkTwentyFiveForm";
	}

	// 详情页面
	@RequestMapping(value = "toDetailsPage")
	public String toDetailsPage(String applyNo) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/parentCompanyAudit?applyNo=" + applyNo;
	}
    
	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "signGuarContract")
	public String signGuarContract(GuarantyContract guarantyContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			guarantyContract.setTemplateType(Constants.CONTRACT_TEMPLATE_TYPE);
			Page<GuarantyContract> page = guarantyContractService.findPage(new Page<GuarantyContract>(request, response), guarantyContract);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("签署保证合同查找合同列表失败", e);
		}
		return "app/postloan/check25/guarantyContractList";
	}

	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "printGuarantyContract")
	public String printGuarantyContract(String id, Model model, HttpServletResponse response, HttpServletRequest request) {
		try {
			String path = Global.getUserfilesBaseDir() + "upload/contract/" + id;
			GuarantyContract guarantyContract = guarantyContractService.get(id);
			String fileName = guarantyContract.getTemplateType() + guarantyContract.getProductType() + ".docx";

			response.setContentType("application/msword");
			String realName = fileName.substring(fileName.indexOf("_") + 1);
			// 设置响应头,控制浏览下载该文件
			try {
				response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				logger.error("下载文件名编码失败", e1);
			}
			InputStream ips = null;
			try {
				File file = new File(path + File.separator + fileName);
				ips = new FileInputStream(file);
				OutputStream ops = response.getOutputStream();
				int data = -1;
				while ((data = ips.read()) != -1) {
					ops.write(data);
				}
				ops.flush();
			} catch (IOException e) {
				logger.error("下载失败", e);
				model.addAttribute("downloadMassage", "下载失败请重试!");
				return "app/postloan/check25/guarantyContractList";
			} finally {
				try {
					ips.close();
				} catch (IOException e) {
					logger.error("关闭资源错误", e);
				}
			}
		} catch (Exception e) {
			logger.error("打印合同模板出错", e);
		}
		
		return null;
	}


	@RequestMapping(value = { "list731", "" })
	public String list731(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model,String approPeriodId, String flag) {
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("contractNo", plContract.getContractNo());
			paramMap.put("custName", plContract.getCustName());
			paramMap.put("mobileNum", approPeriodId);
			paramMap.put("approProductTypeId", plContract.getApproProductTypeId());
			paramMap.put("companyId", UserUtils.getUser().getCompany().getId());
			Page page = null;
			//731复核
				//查询合同状态为放款成功，有逾期还款中，无逾期还款中的数据
				List<String> contractStatusList = new ArrayList<String>();
				contractStatusList.add(Constants.CONTRACT_STATE_FKCG);
				contractStatusList.add(Constants.CONTRACT_STATE_WYQ_HKZ);
				contractStatusList.add(Constants.CONTRACT_STATE_YYQ_HKZ);
				paramMap.put("contractStatusList", contractStatusList);
				page = checkTwentyFiveAllocateService.getSevenPLContract(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("plContract", plContract);
			model.addAttribute("flag", flag);
		} catch (Exception e) {
			logger.error("查询731复核数据失败", e);
		}
		return "app/postloan/check25/sevenList";
	}
	
}
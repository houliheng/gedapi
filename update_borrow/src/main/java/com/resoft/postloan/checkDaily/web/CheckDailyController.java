package com.resoft.postloan.checkDaily.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.GuarantyContract.entity.GuarantyContract;
import com.resoft.multds.credit.GuarantyContract.service.GuarantyContractService;
import com.resoft.postloan.check25.entity.CheckTwentyFive;
import com.resoft.postloan.check25.service.CheckTwentyFiveService;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.checkDaily.service.CheckDailyService;
import com.resoft.postloan.checkTwentyFiveAllocate.dao.CheckTwentyFiveAllocateDao;
import com.resoft.postloan.checkTwentyFiveAllocate.entity.CheckTwentyFiveAllocate;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfo;
import com.resoft.postloan.checkTwentyFiveInfo.service.CheckTwentyFiveInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.common.utils.DateUtils;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 日常检查Controller
 * 
 * @author wuxi01
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkDaily")
public class CheckDailyController extends BaseController {

	@Autowired
	private CheckDailyService checkDailyService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CheckTwentyFiveInfoService checkTwentyFiveInfoService;

	@Autowired
	private CheckTwentyFiveService checkTwentyFiveService;

	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;

	@Autowired
	private CheckTwentyFiveAllocateDao checkTwentyFiveAllocateDao;

	@Autowired
	private GuarantyContractService guarantyContractService;

	/**
	 * 经理日常检查待分配列表
	 * 
	 * @param plContract
	 * @param request
	 * @param response
	 * @param model
	 * @return checkDailyContractList.jsp
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = { "contractList", "" })
	public String contractList(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			MyMap paramMap = setParamMap(plContract);
			// 查询25日复核中通过、合同状态处于还款中的合同编号
			Map<String, Object> paraMap = Maps.newHashMap();
			MyMap paramss = new MyMap();
			List<String> checkedTypeList = new ArrayList<String>();
			checkedTypeList.add(Constants.HAS_BEEN_ALLOCATED);
			checkedTypeList.add(Constants.SIGN_GUARANTY_CONTRACT);
			paraMap.put("checkTypeList", checkedTypeList);
			List<String> contractStatusList = new ArrayList<String>();
			contractStatusList.add(Constants.CONTRACT_STATE_WYQ_HKZ);
			contractStatusList.add(Constants.CONTRACT_STATE_YYQ_HKZ);
			paraMap.put("contractStatusList", contractStatusList);
			List<String> contractNoCheckList = checkTwentyFiveAllocateService.findContractNosForDaily(paraMap);
			// 查询25日复核中，达到还款日的合同
			paramss.put("companyId", UserUtils.getUser().getCompany().getId());
			paramss.put("companyParentIds", UserUtils.getUser().getCompany().getParentIds());
			List<String> contractStatusList1 = new ArrayList<String>();
			contractStatusList1.add(Constants.CONTRACT_STATE_FKCG);
			contractStatusList1.add(Constants.CONTRACT_STATE_WYQ_HKZ);
			contractStatusList1.add(Constants.CONTRACT_STATE_YYQ_HKZ);
			paramss.put("contractStatusList", contractStatusList1);
			paramss.put("dateFlag", "2");
			List<MyMap> maps = checkTwentyFiveAllocateDao.getToAllocatedPLContract(paramss);
			for (MyMap map : maps) {
				int j = 0;
				for (int i = 0; i < contractNoCheckList.size() - 1; i++) {
					if (contractNoCheckList.get(i).equals(map.get("contractNo").toString())) {
						j = 1;
					}
				}
				if (j == 0) {
					contractNoCheckList.add((String) map.get("contractNo"));
				}
			}
			if (contractNoCheckList != null && contractNoCheckList.size() > 0) {
				// 查询所有处于待核查阶段的合同编号
				Map<String, Object> params = Maps.newHashMap();
				params.put("allocateType", Constants.ALLOCATE_TYPE_TODO);
				// 所有日常检查后处于催收、法务、展期申请中、展期通过、借新还旧申请中的合同编号
				List<String> checkDailyProcList = new ArrayList<String>();
				checkDailyProcList.add(Constants.CHECK_DAILY_PROC_CSZ);
				checkDailyProcList.add(Constants.CHECK_DAILY_PROC_FW);
				checkDailyProcList.add(Constants.CHECK_DAILY_PROC_ZQSQZ);
				checkDailyProcList.add(Constants.CHECK_DAILY_PROC_ZQTG);
				checkDailyProcList.add(Constants.CHECK_DAILY_PROC_JXHJSHZ);
				params.put("checkDailyProcList", checkDailyProcList);
				List<String> contractNoList = checkDailyService.findContractNoList(params);
				if (contractNoList != null && contractNoList.size() > 0) {
					contractNoCheckList.removeAll(contractNoList);
				}
				Page<MyMap> page = checkDailyService.findCheckDailyContractList(new Page<MyMap>(request, response), paramMap, contractNoCheckList);
				model.addAttribute("page", page);

			} else {
				model.addAttribute("page", new Page<>());
			}
			// 分开查询隐患：使用IN函数，长度可能太长，提供以下跨库连表查询方法，SQL有待完善
			// 分开查询隐患：循环查询，提供以下跨库连表查询方法，SQL有待完善
			// 日常检查不合规结果
			/*
			 * List<String> checkDailyProcList = new ArrayList<String>();
			 * checkDailyProcList.add(Constants.CHECK_DAILY_PROC_CS);
			 * checkDailyProcList.add(Constants.CHECK_DAILY_PROC_FW);
			 * checkDailyProcList.add(Constants.CHECK_DAILY_PROC_ZQSQZ);
			 * checkDailyProcList.add(Constants.CHECK_DAILY_PROC_ZQTG);
			 * checkDailyProcList.add(Constants.CHECK_DAILY_PROC_JXHJSHZ);
			 * paramMap.put("checkDailyProcList", checkDailyProcList);
			 * //合同状态为还款中 List<String> repayContractStatusList = new
			 * ArrayList<String>();
			 * repayContractStatusList.add(Constants.CONTRACT_STATE_WYQ_HKZ);
			 * repayContractStatusList.add(Constants.CONTRACT_STATE_YYQ_HKZ);
			 * paramMap.put("repayContractStatusList", repayContractStatusList);
			 * //25日复核结果为保存或签署保证合同 List<String> checkedTypeList = new
			 * ArrayList<String>();
			 * checkedTypeList.add(Constants.HAS_BEEN_ALLOCATED);
			 * checkedTypeList.add(Constants.SIGN_GUARANTY_CONTRACT);
			 * paramMap.put("checkedTypeList", checkedTypeList); //不存在在办的日常检查任务
			 * List<String> allocateTypeList = new ArrayList<String>();
			 * allocateTypeList.add(Constants.ALLOCATE_TYPE_TODO);
			 * paramMap.put("allocateTypeList", allocateTypeList); Page<MyMap>
			 * page = checkDailyService.findToAllocateList(new
			 * Page<MyMap>(request, response), paramMap);
			 * model.addAttribute("page", page);
			 */
			model.addAttribute("toAllocate", true);
			model.addAttribute("myPath", "contractList");
		} catch (Exception e) {
			logger.error("查询日常检查待分配列表时发生异常！", e);
			model.addAttribute("myPath", "contractList");
			model.addAttribute("message", "查询日常检查待分配列表时发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyContractList";
	}

	/**
	 * 经理日常检查已分配列表
	 * 
	 * @param plContract
	 * @param request
	 * @param response
	 * @param model
	 * @return checkDailyContractList.jsp
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = "contractDoneList")
	public String contractDoneList(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			// 1.查询已办contratNoList
			MyMap paramMap = setParamMap(plContract);
			paramMap.put("orderByElement", " A.ALLOCATE_DATE DESC ");// 分配时间倒序
			paramMap.put("allocateById", UserUtils.getUser().getId());// 分配人
			Page<MyMap> page = checkDailyService.findCheckDailyList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("toAllocate", false);
			model.addAttribute("myPath", "contractDoneList");
		} catch (Exception e) {
			logger.error("查询日常检查已分配列表时发生异常！", e);
			model.addAttribute("myPath", "contractDoneList");
			model.addAttribute("message", "查询日常检查已分配列表时发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyList";
	}

	/**
	 * 专员日常检查待检查列表
	 * 
	 * @param plContract
	 * @param request
	 * @param response
	 * @param model
	 * @return checkDailyContractList.jsp
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = "toCheckList")
	public String toCheckList(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			MyMap paramMap = setParamMap(plContract);
			String checkedById = UserUtils.getUser().getId();
			paramMap.put("checkedById", checkedById);
			paramMap.put("allocateType", Constants.ALLOCATE_TYPE_TODO);
			paramMap.put("orderByElement", " A.ALLOCATE_DATE DESC ");// 分配时间倒序
			Page<MyMap> page = checkDailyService.findCheckDailyList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("toCheck", true);
			model.addAttribute("myPath", "toCheckList");
		} catch (Exception e) {
			logger.error("查询日常检查待检查列表时发生异常！", e);
			model.addAttribute("myPath", "toCheckList");
			model.addAttribute("message", "查询日常检查待检查列表时发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyList";
	}

	/**
	 * 专员日常检查已检查列表
	 * 
	 * @param plContract
	 * @param request
	 * @param response
	 * @param model
	 * @return checkDailyContractList.jsp
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = "doneCheckList")
	public String doneCheckList(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			MyMap paramMap = setParamMap(plContract);
			String checkedById = UserUtils.getUser().getId();
			paramMap.put("checkedById", checkedById);
			paramMap.put("allocateType", Constants.ALLOCATE_TYPE_DONE);
			paramMap.put("orderByElement", " A.CHECKED_DATE DESC ");// 检查时间倒序
			Page<MyMap> page = checkDailyService.findCheckDailyList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("toCheck", false);
			model.addAttribute("myPath", "doneCheckList");
		} catch (Exception e) {
			logger.error("查询日常检查已检查列表时发生异常！", e);
			model.addAttribute("myPath", "doneCheckList");
			model.addAttribute("message", "查询日常检查已检查列表时发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyList";
	}

	/**
	 * 任务下发List
	 * 
	 * @param plContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = "toIssue")
	public String toIssue(String contractNo, String borrowNewRepayOldFlag, String userName, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("orgId", UserUtils.getUser().getOffice().getId());
			if (contractNo == null || StringUtils.isBlank(contractNo)) {
				logger.error("任务下发时，合同编号(contractNo)为空！");
				model.addAttribute("message", "任务下发时，参数传输错误！");
				return "app/postloan/checkDaily/checkDailyOperatorList";
			}
			if (userName != null && StringUtils.isNotBlank(userName)) {
				paramMap.put("userName", userName);
			}
			Page<MyMap> page = checkDailyService.findCheckDailyOperatorList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("contractNo", contractNo);
			// 借新还旧
			if ("true".equals(borrowNewRepayOldFlag)) {
				model.addAttribute("borrowNewRepayOldFlag", borrowNewRepayOldFlag);
			}
		} catch (Exception e) {
			logger.error("查询日常检查任务下发人员列表发生异常！", e);
			model.addAttribute("message", "查询日常检查任务下发人员列表发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyOperatorList";
	}

	/**
	 * 任务下发
	 * 
	 * @param contractNo
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "issue")
	public Map<String, Object> issue(CheckDailyAllocate checkDailyAllocate, Model model) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			checkDailyAllocate.setId(IdGen.uuid());
			checkDailyAllocate.setAllocateType(Constants.ALLOCATE_TYPE_TODO);
			User loginUser = UserUtils.getUser();
			checkDailyAllocate.setAllocateBy(loginUser.getLoginName());
			checkDailyAllocate.setAllocateById(loginUser.getId());
			checkDailyAllocate.setAllocateDate(DateUtils.getCurrDateTime());
			checkDailyService.assign(checkDailyAllocate);
			resultMap.put("status", "1");// 成功
			resultMap.put("message", "任务下发成功！");
		} catch (Exception e) {
			logger.error("任务下发失败！", e);
			resultMap.put("status", "0");// 失败
			resultMap.put("message", "任务下发失败！");
		}
		return resultMap;
	}

	/**
	 * 日常检查详情/检查
	 * 
	 * @param contractNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("postloan:checkDaily:view")
	@RequestMapping(value = "form")
	public String form(String taskId, String contractNo, String status, String checkFlag, String myPath, Model model) {
		try {
			// 详情或检查标识
			model.addAttribute("checkFlag", checkFlag);
			model.addAttribute("myPath", myPath);
			model.addAttribute("status", status);
			model.addAttribute("taskId", taskId);
			// 25日复核信息详情（合同基本信息）——ACC数据源
			List<MyMap> contractBaseInfo = plContractService.findCheckDailyContractBaseInfo(contractNo);
			if (contractBaseInfo != null && contractBaseInfo.size() == 1) {
				model.addAttribute("contractBaseInfo", contractBaseInfo.get(0));
			} else {
				model.addAttribute("message", "查询合同信息发生异常！");
				return "app/postloan/checkDaily/checkDailyInfoIndex";
			}
			// 25日复核检查信息——PL数据源
			CheckTwentyFiveInfo checkTwentyFiveInfo = new CheckTwentyFiveInfo();
			checkTwentyFiveInfo.setContractNo(contractNo);
			// 项目审查、25日复核借后管理意见
			Map<String, Object> param = Maps.newHashMap();
			param.put("contractNo", contractNo);
			CheckTwentyFiveAllocate checkTwentyFiveAllocate = checkTwentyFiveAllocateService.getTwentyFiveByContractNo(param);
			if (checkTwentyFiveAllocate != null) {
				CheckTwentyFive checkTwentyFive = checkTwentyFiveService.getTwentyFiveByAllocateId(checkTwentyFiveAllocate.getId());
				if (checkTwentyFive != null) {
					model.addAttribute("checkTwentyFive", checkTwentyFive);
				} else {
					model.addAttribute("checkTwentyFive", new CheckTwentyFive());
				}
			} else {
				model.addAttribute("checkTwentyFive", new CheckTwentyFive());
			}
			// 主借人25日复核信息
			checkTwentyFiveInfo.setRoleType(Constants.ROLE_TYPE_ZJR);
			List<CheckTwentyFiveInfo> mainCustCheckList = checkTwentyFiveInfoService.findList(checkTwentyFiveInfo);
			if (mainCustCheckList != null && mainCustCheckList.size() == 1) {
				model.addAttribute("mainCustCheckTwentyFive", mainCustCheckList.get(0));
			} else {
				model.addAttribute("mainCustCheckTwentyFive", new CheckTwentyFiveInfo());
				model.addAttribute("message", "主借人25日复核信息不存在！");
			}
			// 担保人25日复核信息
			checkTwentyFiveInfo.setRoleType(Constants.ROLE_TYPE_DBR);
			List<CheckTwentyFiveInfo> gurrCustCheckList = checkTwentyFiveInfoService.findList(checkTwentyFiveInfo);
			if (gurrCustCheckList != null && gurrCustCheckList.size() == 1) {
				model.addAttribute("gurrCustCheckTwentyFive", gurrCustCheckList.get(0));
			} else {
				model.addAttribute("noGurrCust", true);
				model.addAttribute("gurrCustCheckTwentyFive", new CheckTwentyFiveInfo());
			}
			// 主借企业25日复核信息
			checkTwentyFiveInfo.setRoleType(Constants.ROLE_TYPE_ZJQY);
			List<CheckTwentyFiveInfo> mainComCheckList = checkTwentyFiveInfoService.findList(checkTwentyFiveInfo);
			if (mainComCheckList != null && mainComCheckList.size() == 1) {
				model.addAttribute("mainComCheckTwentyFive", mainComCheckList.get(0));
			} else {
				model.addAttribute("noMainCom", true);
				model.addAttribute("mainComCheckTwentyFive", new CheckTwentyFiveInfo());
			}
			// 担保企业25日复核信息
			checkTwentyFiveInfo.setRoleType(Constants.ROLE_TYPE_DBQY);
			List<CheckTwentyFiveInfo> gurrComCheckList = checkTwentyFiveInfoService.findList(checkTwentyFiveInfo);
			if (gurrComCheckList != null && gurrComCheckList.size() == 1) {
				model.addAttribute("gurrComCheckTwentyFive", gurrComCheckList.get(0));
			} else {
				model.addAttribute("noGurrCom", true);
				model.addAttribute("gurrComCheckTwentyFive", new CheckTwentyFiveInfo());
			}
			// 合规性
			checkTwentyFiveInfo.setRoleType(Constants.ROLE_TYPE_HGX);
			List<CheckTwentyFiveInfo> complianceList = checkTwentyFiveInfoService.findList(checkTwentyFiveInfo);
			if (complianceList != null && complianceList.size() == 1) {
				model.addAttribute("compliance", complianceList.get(0));
			} else {
				model.addAttribute("compliance", new CheckTwentyFiveInfo());
				model.addAttribute("message", "主借人25日复核合规性信息不存在！");
			}
			// 日常检查借后管理意见
			if (taskId != null && StringUtils.isNotBlank(taskId)) {
				Map<String, Object> params = Maps.newConcurrentMap();
				params.put("taskId", taskId);
				if ("doneCheckList".equals(myPath)) {
					params.put("allocateType", Constants.ALLOCATE_TYPE_DONE);
				} else if ("contractDoneList".equals(myPath)) {
					if (status == null && "".equals(status)) {
						params.put("allocateType", Constants.ALLOCATE_TYPE_TODO);
					} else {
						params.put("allocateType", Constants.ALLOCATE_TYPE_DONE);
					}
				} else {
					params.put("allocateType", Constants.ALLOCATE_TYPE_TODO);
				}
				List<CheckDailyAllocate> checkDailyList = checkDailyService.findListByParams(params);
				if (checkDailyList != null && checkDailyList.size() == 1) {
					model.addAttribute("checkDailyAllocate", checkDailyList.get(0));
					model.addAttribute("currCollectionType", Constants.DEBT_COLLECTION_PHONE);
					model.addAttribute("currCollectionTypeLegal", Constants.DEBT_COLLECTION_LEGAL);
					model.addAttribute("currCollectionFrom", Constants.DEBT_COLLECTION__RCJC);
					model.addAttribute("currCollectionStatus", Constants.TO_BE_ALLOCATED);
				} else {
					model.addAttribute("checkDailyAllocate", new CheckDailyAllocate());
				}
			} else {
				model.addAttribute("checkDailyAllocate", new CheckDailyAllocate());
			}
		} catch (Exception e) {
			logger.error("查看日常检查详情,或进行日常检查时发生异常！", e);
			model.addAttribute("message", "查看日常检查详情,或进行日常检查时发生异常！");
		}
		return "app/postloan/checkDaily/checkDailyInfoIndex";
	}

	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "specialCaseForm")
	public String specialCaseForm(CheckDailyAllocate checkDailyAllocate, String hiddenResult, Model model) {
		try {
			CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
			checkDaily.setCheckDailyResult(hiddenResult);
			checkDailyAllocate.setCheckDaily(checkDaily);
			model.addAttribute("checkDailyAllocate", checkDailyAllocate);
		} catch (Exception e) {
			model.addAttribute("message", "特殊情况处理发生异常！");
		}
		return "app/postloan/checkDaily/specialCaseForm";
	}

	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "specialCaseList")
	public String specialCaseList(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			MyMap paramMap = setParamMap(plContract);
			paramMap.put("checkDailyProc", Constants.CHECK_DAILY_PROC_TSQKCL);
			Page<MyMap> page = checkDailyService.findCheckDailyList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("toAllocate", true);
			model.addAttribute("myPath", "contractList");
		} catch (Exception e) {
			logger.error("查询特殊情况处理列表时发生异常！", e);
			model.addAttribute("myPath", "contractList");
			model.addAttribute("message", "查询特殊情况处理列表时发生异常！");
		}
		return "app/postloan/checkDaily/specialCaseList";
	}

	/**
	 * 特殊情况处理
	 * 
	 * @param contractNo
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "specialCaseSave")
	public Map<String, Object> specialCaseSave(CheckDailyAllocate checkDailyAllocate, Model model) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			checkDailyService.specialCaseSave(checkDailyAllocate);
			resultMap.put("status", "1");// 成功
			resultMap.put("message", "特殊情况处理提交成功！");
		} catch (Exception e) {
			logger.error("特殊情况处理提交失败！", e);
			resultMap.put("status", "0");// 失败
			resultMap.put("message", "特殊情况处理提交失败！");
		}
		return resultMap;
	}

	/**
	 * 日常检查保存
	 * 
	 * @param checkDaily
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "save")
	public Map<String, Object> save(CheckDailyAllocate checkDailyAllocate, String hiddenResult, Model model) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			checkDailyService.save(checkDailyAllocate, hiddenResult);
			resultMap.put("status", "1");// 成功
			resultMap.put("message", "日常检查操作成功！");
		} catch (Exception e) {
			logger.error("保存日常检查信息失败！", e);
			resultMap.put("status", "0");// 失败
			resultMap.put("message", "日常检查操作失败！");
		}
		return resultMap;
	}

	/**
	 * 组织查询参数MAP
	 * 
	 * @param plContract
	 * @return
	 */
	private MyMap setParamMap(PLContract plContract) {
		MyMap paramMap = new MyMap();
		if (plContract != null) {
			if (plContract.getContractNo() != null && StringUtils.isNotBlank(plContract.getContractNo())) {
				paramMap.put("contractNo", plContract.getContractNo());
			}
			if (plContract.getCustName() != null && StringUtils.isNotBlank(plContract.getCustName())) {
				paramMap.put("custName", plContract.getCustName());
			}
			if (plContract.getApproProductTypeId() != null && StringUtils.isNotBlank(plContract.getApproProductTypeId())) {
				paramMap.put("approProductTypeId", plContract.getApproProductTypeId());
			}
		}
		paramMap.put("operateOrgId", UserUtils.getUser().getCompany().getId());
		paramMap.put("operateOrgParentIds", UserUtils.getUser().getCompany().getParentIds());
		return paramMap;
	}

	/**
	 * 催收、法务
	 * 
	 * @param debtCollection
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "collection")
	public AjaxView collection(DebtCollection debtCollection, CheckDailyAllocate checkDailyAllocate, String hiddenResult, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			String flag = checkDailyService.collection(debtCollection, checkDailyAllocate, hiddenResult);
			if ("success".equals(flag)) {
				if (Constants.DEBT_COLLECTION_PHONE.equals(debtCollection.getCurrCollectionType())) {
					ajaxView.setSuccess().setMessage("流转到催收成功!");
				} else if (Constants.CHECK_DAILY_PROC_FW.equals(debtCollection.getCurrCollectionType())) {
					ajaxView.setSuccess().setMessage("流转到法务催收成功!");
				} else {
					ajaxView.setSuccess().setMessage("操作成功!");
				}
			} else if ("flag".equals(flag)) {
				if (Constants.DEBT_COLLECTION_PHONE.equals(debtCollection.getCurrCollectionType())) {
					ajaxView.setFailed().setMessage("流转到催收失败!");
				} else if (Constants.CHECK_DAILY_PROC_FW.equals(debtCollection.getCurrCollectionType())) {
					ajaxView.setFailed().setMessage("流转到法务催收失败!");
				} else {
					ajaxView.setFailed().setMessage("操作失败!");
				}
			} else {
				ajaxView.setFailed().setMessage(flag);
			}
		} catch (Exception e) {
			logger.error("流转到催收/法务失败！", e);
			if (Constants.DEBT_COLLECTION_PHONE.equals(debtCollection.getCurrCollectionType())) {
				ajaxView.setFailed().setMessage("流转到催收失败!");
			} else if (Constants.CHECK_DAILY_PROC_FW.equals(debtCollection.getCurrCollectionType())) {
				ajaxView.setFailed().setMessage("流转到法务催收失败!");
			} else {
				ajaxView.setFailed().setMessage("操作失败!");
			}
		}
		return ajaxView;
	}

	/**
	 * 签署保证合同
	 * 
	 * @param checkDailyAllocate
	 * @param hiddenResult
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("postloan:checkDaily:edit")
	@RequestMapping(value = "signContract")
	public AjaxView signContract(CheckDailyAllocate checkDailyAllocate, String hiddenResult, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			checkDailyService.signContract(checkDailyAllocate, hiddenResult);
			ajaxView.setSuccess();
		} catch (Exception e) {
			logger.error("保存日常检查信息失败！", e);
			ajaxView.setFailed().setMessage("签署保证合同失败！");
		}
		return ajaxView;
	}

	/**
	 * 签署保证合同
	 * 
	 * @param guarantyContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "signGuarContract")
	public String signGuarContract(GuarantyContract guarantyContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			guarantyContract.setTemplateType(Constants.CONTRACT_TEMPLATE_TYPE);
			Page<GuarantyContract> page = guarantyContractService.findPage(new Page<GuarantyContract>(request, response), guarantyContract);
			model.addAttribute("page", page);
			model.addAttribute("myPath", "checkDaily");
		} catch (Exception e) {
			logger.error("签署保证合同查找合同列表失败", e);
		}
		return "app/postloan/check25/guarantyContractList";
	}

}
package com.resoft.credit.applyRegister.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.CreateNumberService;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRegister.service.CreVieoPathService;
import com.resoft.credit.applyRegister.service.VideoParamService;
import com.resoft.credit.blacklist.service.BlacklistService;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.fdfs.Manager;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 个人客户登记Controller
 * 
 * @author wuxi01
 * @version 2016-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/applyRegister")
public class ApplyRegisterController extends BaseController {

	private static int flagFirst = 0;
	private static int flagMiddle = 0;
	private static int flagLast = 0;
	private static String dateStr = DateUtils.getDate("yyMMdd");

	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@Autowired
	private CreVieoPathService creVieoPathService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BlacklistService blacklistService;

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private ApplyLoanStatusService applyLoanStatusService;

	@Autowired
	private CreateNumberService createNumberService;

	@Autowired
	private VideoParamService videoParamService;

	@ModelAttribute
	public ApplyRegister get(@RequestParam(required = false) String id) {
		ApplyRegister entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyRegisterService.get(id);
		}
		if (entity == null) {
			entity = new ApplyRegister();
		}
		return entity;
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = { "list", "" })
	public String list(ApplyRegister applyRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Office company = UserUtils.getUser().getCompany();
		User createBy = UserUtils.getUser();
		applyRegister.setCompany(company);
		applyRegister.setCreateBy(createBy);
		Page<ApplyRegister> page = applyRegisterService.findPage(new Page<ApplyRegister>(request, response), applyRegister);
		model.addAttribute("page", page);
		return "app/credit/applyRegister/applyRegisterList";
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = "form")
	public String form(ApplyRegister applyRegister, Model model) {
		// 查询人员禁件和机构禁件
		String entryFlag = applyRegisterService.isAllowRegiste();
		if (!"true".equals(entryFlag)) {
			model.addAttribute("readOnly", true);
		}
		// 设置登记时间为当前时间
		if (applyRegister == null || StringUtils.isEmpty(applyRegister.getId())) {
			applyRegister.setRegisterDate(DateUtils.getCurrDateTime());
		}
		// 根据产品类型加载产品列表
		// 产品类型
		String productType = applyRegister.getApplyProductTypeCode();
		// 当前操作员所在机构
		String orgId = null;
		try {
			// 修改
			if (applyRegister != null && StringUtils.isNotBlank(applyRegister.getId())) {
				orgId = applyRegister.getCompany().getId();
			} else {// 新增
				orgId = UserUtils.getUser().getCompany().getId();
			}
			// 产品查询条件
			Product product = new Product();
			product.setCompany(new Office(orgId));
			product.setProductTypeCode(productType);
			// 产品列表
			List<Product> productList = productService.findCoProductByTypeToApply(product);
			// 传入对象
			model.addAttribute("orgId", orgId);
			model.addAttribute("productList", productList);
			model.addAttribute("applyRegister", applyRegister);
			return "app/credit/applyRegister/applyRegisterForm";
		} catch (Exception e) {
			logger.error("机构ID获取错误", e);
			model.addAttribute("message", "查询产品信息错误，请联系管理员！");
			model.addAttribute("applyRegister", applyRegister);
			return "app/credit/applyRegister/applyRegisterForm";
		}
	}

	/**
	 * 新增个人客户登记信息
	 * 
	 * @param applyRegister
	 * @return AjaxView
	 */
	@RequiresPermissions("credit:applyRegister:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(ApplyRegister applyRegister) {
		AjaxView ajaxView = new AjaxView();
		// 1.保存之前数据处理
		// 查询最大序列号，设置序列号------------------生成规则未确定----后期再写
		// 2.保存
		try {
			synchronized (dateStr) {
				int applyNoFlag = 0;
				List<Integer> flagList = new ArrayList<Integer>();
				flagList.add(0, flagFirst);
				flagList.add(1, flagMiddle);
				flagList.add(2, flagLast);
				// 获取当前登录用户的机构号
				User logUser = UserUtils.getUser();
				String logUserId = logUser.getCompany().getId();
				List<String> receiveList = createNumberService.createContractNo(applyRegister.getApplyProductTypeCode(), logUserId, flagList, dateStr);
				// 更新当前时间
				dateStr = (String) receiveList.get(2);
				if (applyRegister.getApplyNo() == null || applyRegister.getApplyNo().isEmpty()) {
					applyRegister.setApplyNo((String) receiveList.get(0));
					applyNoFlag = 1;
				}
				String saveFlag = applyRegisterService.saveRegisterCustAndRelation(applyRegister);
				if ("success".equals(saveFlag)) {
					if (Constants.APPLY_STATUS_SUBMIT.equals(applyRegister.getApplyStatus())) {
						processSuggestionInfoService.backMessageToGED(applyRegister.getApplyNo(), null, Constants.GED_APPLY_STATUS_SHZ, null);
						ajaxView.setSuccess().setMessage("提交个人客户登记信息成功！");
					} else {
						ajaxView.setSuccess().setMessage("保存个人客户登记信息成功！");
					}
					// 判断标志位是否重新计算了
					// 如果重新计算了
					if ("1".equals(receiveList.get(1))) {
						flagFirst = 0;
						flagMiddle = 0;
						flagLast = 0;
					} else {
						// 英文字母为26个，当flagLast大于25时，flagFirst进位
						if (applyNoFlag == 1) {
							flagLast++;
							if (flagLast > 25) {
								flagMiddle++;
								if (flagMiddle > 25) {
									flagFirst++;
									flagMiddle = 0;
								}
								flagLast = 0;
							}
						}
					}
					return ajaxView;
				} else {
					ajaxView.setFailed().setMessage(saveFlag);
					return ajaxView;
				}
			}
		} catch (Exception e) {
			logger.error("保存个人客户登记信息失败！", e);
			ajaxView.setFailed().setMessage("保存个人客户登记信息失败！");
			return ajaxView;
		}
	}

	@RequiresPermissions("credit:applyRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyRegister applyRegister, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
		String idsStr = request.getParameter("ids");
		if (idsStr != null && !"".equals(idsStr)) {// 批量删除
			List<String> idList = Arrays.asList(idsStr.split(","));
			for (int i = 0; i < idList.size(); i++) {
				if (StringUtils.isNotEmpty(idList.get(i))) {
					String id = idList.get(i);
					ApplyRegister applyRegisterInfo = applyRegisterService.get(id);
					String applyStatus = applyRegisterInfo.getApplyStatus();
					if (!Constants.APPLY_STATUS_SAVE.equals(applyStatus)) {
						addMessage(redirectAttributes, "个人客户登记信息已提交，不可删除！");
						return "redirect:" + adminPath + "/credit/applyRegister/list";
					} else {
						processSuggestionInfoService.backMessageToGED(applyRegisterInfo.getApplyNo(), applyRegisterInfo.getId(), Constants.GED_APPLY_STATUS_SQJJ, null);
						continue;
					}
				}
			}
			try {
				applyRegisterService.banchDelete(idList);
				addMessage(redirectAttributes, "删除个人客户登记信息成功！");
			} catch (Exception e) {
				logger.error("删除个人客户登记信息失败！", e);
				addMessage(redirectAttributes, "删除个人客户登记信息失败！");
			}
		} else {// 单个删除
			try {
				String applyStatus = applyRegister.getApplyStatus();
				if (Constants.APPLY_STATUS_SUBMIT.equals(applyStatus)) {
					addMessage(redirectAttributes, "个人客户登记信息已提交，不可删除！");
					return "redirect:" + adminPath + "/credit/applyRegister/list";
				} else {
					processSuggestionInfoService.backMessageToGED(applyRegister.getApplyNo(), applyRegister.getId(), Constants.GED_APPLY_STATUS_SQJJ, null);
					applyRegisterService.delete(applyRegister);
					addMessage(redirectAttributes, "删除个人客户登记信息成功！");

				}
			} catch (Exception e) {
				logger.error("删除个人客户登记信息失败！", e);
				addMessage(redirectAttributes, "删除个人客户登记信息失败！");
			}
		}
		return "redirect:" + adminPath + "/credit/applyRegister/list";
	}

	/**
	 * 对个人客户登记信息进行唯一性校验
	 * 
	 * @param applyRegister
	 * @return
	 */
	@RequiresPermissions("credit:applyRegister:edit")
	@ResponseBody
	@RequestMapping(value = "validate")
	public AjaxView validate(ApplyRegister applyRegister) {
		AjaxView ajaxView = new AjaxView();
		// 验证条件
		String idNum = applyRegister.getIdNum();
		String mobileNum = applyRegister.getMobileNum();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idNum", idNum);
		map.put("mobileNum", mobileNum);
		// 1.判断客户是否在黑名单中
		boolean isBlackName = blacklistService.isBlackName(map);
		if (isBlackName) {// 黑名单客户
			ajaxView.setFailed();
			ajaxView.setMessage("此客户为黑名单客户，无法提交！");
			return ajaxView;
		}

		// 1.1如果客户类型为企业的话,判断企业是否存在于正在审核的进件中
		if (Constants.CUST_TYPE_QY.equals(applyRegister.getCustType())) {
			Map<String, Object> params = Maps.newHashMap();
			params.put(applyRegister.getComIdType(), applyRegister.getComIdNum());
			List<String> flag = applyRegisterService.existCompany(params);
			if (flag != null && flag.size() != 0) {
				for (String f : flag) {
					if (Constants.APPLY_STATUS_SUBMIT.equals(f)) {
						ajaxView.setFailed();
						ajaxView.setMessage("该企业正在审批中，无法提交！");
						return ajaxView;
					}
				}
			}
		}

		// 2.判断客户申请状态，是否有处于审批中的、是否有正在还款中的、是否有被拒过但未在黑名单中的
		String statusFlag = applyRegisterService.isCheckingOrRejected(map);
		if (statusFlag.equals("checking")) {
			ajaxView.setFailed();
			ajaxView.setMessage("此客户正在审批中，无法提交！");
			return ajaxView;
		} else if (statusFlag.equals("rejected")) {
			ajaxView.setStatus("confirmRejected");
			ajaxView.setMessage("此客户曾经被拒过，是否确认提交？");
			return ajaxView;
		}

		// 3.判断是否正在还款中
		List<String> contractStatusList = applyLoanStatusService.findContractStatusByIdCardOrMobile(map);
		if (contractStatusList != null && contractStatusList.size() > 0) {
			if (contractStatusList.contains(Constants.CONTRACT_STATE_HKZ_YQ) || contractStatusList.contains(Constants.CONTRACT_STATE_HKZ_WYQ)) {
				ajaxView.setStatus("confirmRepayment");
				ajaxView.setMessage("此客户正在还款中，请确认是否提交？");
				return ajaxView;
			}
		}

		// 4.判断新增客户是否在担保人信息表中
		map.put("roleType", Constants.ROLE_TYPE_DBR);// 担保人
		boolean isGuarantor = custInfoService.isTheRoleType(map);
		if (isGuarantor) {
			ajaxView.setStatus("confirmGuarantor");
			ajaxView.setMessage("此客户为担保人，是否确认提交？");
			return ajaxView;
		}
		ajaxView.setSuccess();
		return ajaxView;
	}

	@RequestMapping("toUploadPage")
	public String toUploadPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = request.getParameter("applyNo");
		String taskDefKey = request.getParameter("taskDefKey");
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/applyRegister/uploadFile";
	}

	static {
		Manager.startThreadPool();
	}

	@RequestMapping("upload")
	public void upload(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String applyNo = request.getParameter("applyNo");
		String taskDefKey = request.getParameter("taskDefKey");
		MultipartFile f = request.getFile("uploadify");
		String registerDate = applyRegisterService.getRegisterDateByApplyNo(applyNo);
		taskDefKey = videoParamService.getAuthorityLevel(taskDefKey);
		File tmp = File.createTempFile("1222", "2222");
		OutputStream out = new FileOutputStream(tmp);
		out.write(f.getBytes());
		out.flush();
		out.close();
		Manager.setDbSource(creVieoPathService);
		Manager.uploadZip(tmp, applyNo, taskDefKey, UserUtils.getUser().getName(), true, registerDate);
	}

	@ResponseBody
	@RequestMapping(value = "isSubmitting")
	public String isSubmitting(String applyNo) {
		try {
			String applyStatus = applyRegisterService.getApplyRegisterByApplyNo(applyNo).getApplyStatus();
			if (applyStatus.equals(Constants.APPLY_STATUS_SUBMIT)) {
				return "true";
			} else {
				return DictUtils.getDictLabel(applyStatus, Constants.APPLY_STATUS, "");
			}
		} catch (Exception e) {
			logger.error("查询进件申请状态失败，可能是参数错误或数据库数据错误！", e);
			return "false";
		}
	}

	@ResponseBody
	@RequestMapping(value = "isAllowRegiste")
	public String isAllowRegiste(String applyNo) {
		try {
			// 查询人员禁件和机构禁件
			String entryFlag = applyRegisterService.isAllowRegiste();
			return entryFlag;
		} catch (Exception e) {
			logger.error("查询进件申请状态失败，可能是参数错误或数据库数据错误！", e);
			return "false";
		}
	}

	@RequiresPermissions("credit:applyRegister:edit")
	@ResponseBody
	@RequestMapping(value = "validateRepaymentAndGuarantor")
	public AjaxView validateRepaymentAndGuarantor(ApplyRegister applyRegister) {
		AjaxView ajaxView = new AjaxView();
		// 验证条件
		String idNum = applyRegister.getIdNum();
		String mobileNum = applyRegister.getMobileNum();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idNum", idNum);
		map.put("mobileNum", mobileNum);

		// 3.判断是否正在还款中
		List<String> contractStatusList = applyLoanStatusService.findContractStatusByIdCardOrMobile(map);
		if (contractStatusList != null && contractStatusList.size() > 0) {
			if (contractStatusList.contains(Constants.CONTRACT_STATE_HKZ_YQ) || contractStatusList.contains(Constants.CONTRACT_STATE_HKZ_WYQ)) {
				ajaxView.setStatus("confirmRepayment");
				ajaxView.setMessage("此客户正在还款中，请确认是否提交？");
				return ajaxView;
			}
		}

		// 4.判断新增客户是否在担保人信息表中
		map.put("roleType", Constants.ROLE_TYPE_DBR);// 担保人
		boolean isGuarantor = custInfoService.isTheRoleType(map);
		if (isGuarantor) {
			ajaxView.setStatus("confirmGuarantor");
			ajaxView.setMessage("此客户为担保人，是否确认提交？");
			return ajaxView;
		}
		ajaxView.setSuccess();
		return ajaxView;
	}

	@RequiresPermissions("credit:applyRegister:edit")
	@ResponseBody
	@RequestMapping(value = "validateGuarantor")
	public AjaxView validateGuarantor(ApplyRegister applyRegister) {
		AjaxView ajaxView = new AjaxView();
		// 验证条件
		String idNum = applyRegister.getIdNum();
		String mobileNum = applyRegister.getMobileNum();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idNum", idNum);
		map.put("mobileNum", mobileNum);

		// 4.判断新增客户是否在担保人信息表中
		map.put("roleType", Constants.ROLE_TYPE_DBR);// 担保人
		boolean isGuarantor = custInfoService.isTheRoleType(map);
		if (isGuarantor) {
			ajaxView.setStatus("confirmGuarantor");
			ajaxView.setMessage("此客户为担保人，是否确认提交？");
			return ajaxView;
		}
		ajaxView.setSuccess();
		return ajaxView;
	}
}
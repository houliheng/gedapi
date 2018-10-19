package com.resoft.postloan.plCheckPhone.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.plCustinfo.entity.PLCustInfo;
import com.resoft.multds.credit.plCustinfo.service.PLCustInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.plCheckPhone.entity.PLApplyRelation;
import com.resoft.postloan.plCheckPhone.entity.PLCheckPhone;
import com.resoft.postloan.plCheckPhone.entity.PLContactInfo;
import com.resoft.postloan.plCheckPhone.service.PLCheckPhoneService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借后电话外访Controller
 * 
 * @author wangguodong
 * @version 2016-09-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/plCheckPhone")
public class PLCheckPhoneController extends BaseController {

	@Autowired
	private PLCheckPhoneService checkPhoneService;

	@Autowired
	private PLCustInfoService plCustInfoService;

	@Autowired
	private PLContractService plContractService;

	@ModelAttribute
	public PLCheckPhone get(@RequestParam(required = false) String id) {
		PLCheckPhone entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkPhoneService.get(id);
		}
		if (entity == null) {
			entity = new PLCheckPhone();
		}
		return entity;
	}

	@RequiresPermissions("postloan:plCheckPhone:view")
	@RequestMapping(value = "index")
	public String index(PLCheckPhone plCheckPhone, Model model, String readOnlyFlag) {
		model.addAttribute("contractNo", plCheckPhone.getContractNo());
		model.addAttribute("allocateId", plCheckPhone.getAllocateId());
		model.addAttribute("readOnlyFlag", readOnlyFlag);
		return "app/postloan/plCheckPhone/plCheckPhoneIndex";
	}

	@RequiresPermissions("postloan:plCheckPhone:view")
	@RequestMapping(value = { "list", "" })
	public String list(PLCheckPhone plCheckPhone,String readOnlyFlag, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<PLCheckPhone> checkPhoneList = checkPhoneService.getCheckPhoneByAllocateId(plCheckPhone.getAllocateId());
		// 电核信息列表中的数据
		model.addAttribute("checkPhoneList", checkPhoneList);
		// 根据合同编号获取申请编号
		String applyNo = null;
		List<PLContract> plContracts = plContractService.findListByContractNo(plCheckPhone.getContractNo());
		if (plContracts.size() == 1) {
			applyNo = plContracts.get(0).getApplyNo();
		}
		if (checkPhoneList.size() > 0) {
			for (PLCheckPhone checkPhone : checkPhoneList) {
				checkPhone.setApplyNo(applyNo);
			}
		}
		// 从数据库中查出一个进件中的主借人、配偶、担保人
		Map<String, Object> params = Maps.newConcurrentMap();
		try {
			params.put("applyNo", applyNo);
			List<String> roleTypeList = new ArrayList<String>();
			roleTypeList.add(Constants.ROLE_TYPE_ZJR);
			roleTypeList.add(Constants.ROLE_TYPE_DBR);
			roleTypeList.add(Constants.ROLE_TYPE_MATE);
			params.put("roleTypeList", roleTypeList);
		} catch (Exception e) {
			logger.error("查询待核查列表参数错误", e);
			model.addAttribute("message", "查询待核查列表参数错误，请联系管理员！");
		}
		List<PLApplyRelation> plApplyRelationList = checkPhoneService.findByApplyNoAndRoleTypeList(params);
		// 查询一个进件中，同一个对象的外访次数
		// 同时确定是否可提交
		String visitCountFlag = "true";
		for (int i = 0; i < plApplyRelationList.size(); i++) {
			PLApplyRelation tempApplyRelation = plApplyRelationList.get(i);
			String id = tempApplyRelation.getCustId();
			PLCustInfo custInfo = plCustInfoService.get(id);
			tempApplyRelation.setCustInfo(custInfo);
			String count = checkPhoneService.getCheckPhoneCount(plCheckPhone.getAllocateId(), tempApplyRelation.getCustId(), tempApplyRelation.getRoleType());
			if (count.equals("0")) {
				visitCountFlag = "false";
			}
			tempApplyRelation.setVisitCount(count);
			plApplyRelationList.set(i, tempApplyRelation);
		}
		model.addAttribute("applyRelationList", plApplyRelationList);
		// 从数据库中查出一个进件中的联系人
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			map.put("applyNo", applyNo);
			map.put("roleType", Constants.ROLE_TYPE_ZJR);
		} catch (Exception e) {
			logger.error("查询待核查列表参数错误", e);
			model.addAttribute("message", "查询待核查列表参数错误，请联系管理员！");
		}
		if ("2".equals(readOnlyFlag) ||"11".equals(readOnlyFlag) ) {
			model.addAttribute("readOnly", true);
		}
		List<PLContactInfo> contactInfoList = checkPhoneService.findContactInfoByApplyNo(map);
		PLContactInfo contractInfo = new PLContactInfo();
		for (int j = 0; j < contactInfoList.size(); j++) {
			contractInfo = contactInfoList.get(j);
			String contractCount = checkPhoneService.getCheckPhoneCount(plCheckPhone.getAllocateId(), contractInfo.getId(), Constants.ROLE_TYPE_CONTACT);
			if (contractCount.equals("0")) {
				visitCountFlag = "false";
			}
			contractInfo.setVisitCount(contractCount);
			contactInfoList.set(j, contractInfo);
		}
		model.addAttribute("contactInfoList", contactInfoList);
		model.addAttribute("visitCountFlag", visitCountFlag);
		model.addAttribute("plCheckPhone", plCheckPhone);
		return "app/postloan/plCheckPhone/plCheckPhoneList";
	}

	@RequiresPermissions("postloan:plCheckPhone:view")
	@RequestMapping(value = "form")
	public String form(PLCheckPhone plCheckPhone, HttpServletRequest request, Model model) {
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		String notice = null;
		try {
			String applyno = plCheckPhone.getApplyNo();
			notice = checkPhoneService.getCheckFaceByApplyNo(applyno);

			String custName = URLDecoder.decode(plCheckPhone.getCustName(), "UTF-8");
			plCheckPhone.setCustName(custName);
		} catch (UnsupportedEncodingException e) {
			logger.error("中文解码失败", e);
		}
		model.addAttribute("notice", notice);
		model.addAttribute("plCheckPhone", plCheckPhone);
		return "app/postloan/plCheckPhone/plCheckPhoneForm";
	}

	/**
	 * 新增联系人电话核查
	 * 
	 * @param checkPhone
	 * @param model
	 * @param request
	 * @return
	 */
	@RequiresPermissions("credit:checkPhone:view")
	@RequestMapping(value = "formContact")
	public String formContact(PLCheckPhone plCheckPhone, Model model, HttpServletRequest request) {
		plCheckPhone.setRoleType(Constants.ROLE_TYPE_CONTACT);
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		String notice = null;
		try {
			String applyno = plCheckPhone.getApplyNo();
			notice = checkPhoneService.getCheckFaceByApplyNo(applyno);
			String custName = URLDecoder.decode(plCheckPhone.getCustName(), "UTF-8");
			plCheckPhone.setCustName(custName);
		} catch (UnsupportedEncodingException e) {
			logger.error("中文解码错误！");
			model.addAttribute("message", "参数解码错误，请联系管理员！");
		}
		model.addAttribute("checkPhone", plCheckPhone);
		model.addAttribute("notice", notice);
		return "app/postloan/plCheckPhone/plCheckPhoneForm";
	}

	@RequiresPermissions("postloan:plCheckPhone:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(PLCheckPhone plCheckPhone, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			User loginUser = UserUtils.getUser();
			plCheckPhone.setCheckUserId(loginUser.getId());
			plCheckPhone.setCheckUserName(loginUser.getLoginName());
			checkPhoneService.save(plCheckPhone);
			ajaxView.setSuccess().setMessage("保存信息成功");
			ajaxView.put("contractNo", plCheckPhone.getContractNo());
			ajaxView.put("allocateId", plCheckPhone.getAllocateId());
		} catch (Exception e) {
			logger.error("保存信息失败。", e);
			ajaxView.setFailed().setMessage("保存信息失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:plCheckPhone:edit")
	@RequestMapping(value = "delete")
	public String delete(PLCheckPhone plCheckPhone, RedirectAttributes redirectAttributes) {
		checkPhoneService.delete(plCheckPhone);
		addMessage(redirectAttributes, "删除电话外访成功");
		return "redirect:" + Global.getAdminPath() + "/plCheckPhone/plCheckPhone/?repage";
	}

}
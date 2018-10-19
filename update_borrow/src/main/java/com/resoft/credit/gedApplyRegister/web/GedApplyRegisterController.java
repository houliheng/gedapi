package com.resoft.credit.gedApplyRegister.web;

import java.io.IOException;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.gedApplyRegister.entity.GedApplyRegister;
import com.resoft.credit.gedApplyRegister.service.GedApplyRegisterService;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 冠e贷申请客户登记信息表Controller
 * 
 * @author wangguodong
 * @version 2017-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gedApplyRegister")
public class GedApplyRegisterController extends BaseController {

	@Autowired
	private GedApplyRegisterService gedApplyRegisterService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@ModelAttribute
	public GedApplyRegister get(@RequestParam(required = false) String id) {
		GedApplyRegister entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gedApplyRegisterService.get(id);
		}
		if (entity == null) {
			entity = new GedApplyRegister();
		}
		return entity;
	}

	@RequiresPermissions("credit:gedApplyRegister:view")
	@RequestMapping(value = { "list", "" })
	public String list(GedApplyRegister gedApplyRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GedApplyRegister> page = gedApplyRegisterService.findPage(new Page<GedApplyRegister>(request, response), gedApplyRegister);
		model.addAttribute("page", page);
		return "app/credit/gedApplyRegister/gedApplyRegisterList";
	}

	@RequiresPermissions("credit:gedApplyRegister:view")
	@RequestMapping(value = "form")
	public String form(GedApplyRegister gedApplyRegister, Model model) {
		model.addAttribute("gedApplyRegister", gedApplyRegister);
		return "app/credit/gedApplyRegister/gedApplyRegisterForm";
	}

	@RequiresPermissions("credit:gedApplyRegister:edit")
	@RequestMapping(value = "save")
	public String save(GedApplyRegister gedApplyRegister, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gedApplyRegister)) {
			return form(gedApplyRegister, model);
		}
		gedApplyRegisterService.save(gedApplyRegister);
		addMessage(redirectAttributes, "保存冠e贷申请客户登记信息表成功");
		return "redirect:" + Global.getAdminPath() + "/gedApplyRegister/gedApplyRegister/?repage";
	}

	@RequiresPermissions("credit:gedApplyRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(GedApplyRegister gedApplyRegister, RedirectAttributes redirectAttributes) {
		gedApplyRegisterService.delete(gedApplyRegister);
		addMessage(redirectAttributes, "删除冠e贷申请客户登记信息表成功");
		return "redirect:" + Global.getAdminPath() + "/gedApplyRegister/gedApplyRegister/?repage";
	}

	@RequiresPermissions("credit:gedApplyRegister:view")
	@RequestMapping(value = "toAllot")
	public String toAllot(GedApplyRegister gedApplyRegister, User user, Model model) {
		String roleId = DictUtils.getDictValue("分公司经理角色", "ALLOT_ROLE", null);
		Map<String, Object> params = Maps.newHashMap();
		params.put("roleId", roleId);
		params.put("userName", user.getName());
		List<Map<String, Object>> list = gedApplyRegisterService.getUserToAllot(params);
		model.addAttribute("list", list);
		model.addAttribute("gedApplyRegister", gedApplyRegister);
		model.addAttribute("user", user);
		return "app/credit/gedApplyRegister/orgUserToAllot";
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = "allot")
	@ResponseBody
	public AjaxView allot(GedApplyRegister gedApplyRegister, String loginName, String userId, String companyId) {
		AjaxView ajaxView = new AjaxView();
		try {
			// GedApplyRegister gedApplyRegister =
			// gedApplyRegisterService.get(registerId);
			gedApplyRegisterService.allotTask(gedApplyRegister, loginName, userId, companyId);
			ajaxView.setSuccess().setMessage("分配成功");
		} catch (Exception e) {
			logger.error("分配任务失败", e);
			ajaxView.setFailed().setMessage("分配失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = "refuseTask")
	@ResponseBody
	public AjaxView refuseTask(GedApplyRegister gedApplyRegister) {
		AjaxView ajaxView = new AjaxView();
		try {
			processSuggestionInfoService.backMessageToGED(null, null, Constants.GED_APPLY_STATUS_SQJJ, gedApplyRegister.getApplyId());
			ajaxView.setSuccess().setMessage("拒绝成功");
		} catch (Exception e) {
			logger.error("拒绝失败", e);
			ajaxView.setFailed().setMessage("拒绝失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = "toProcessTrack")
	@ResponseBody
	public AjaxView toProcessTrack(GedApplyRegister gedApplyRegister, String loginName, String userId, String companyId) {
		AjaxView ajaxView = new AjaxView();
		try {
			ApplyRegister applyRegister = gedApplyRegisterService.getApplyRegisterByIFTApplyId(gedApplyRegister.getApplyId());
			if (applyRegister != null && applyRegister.getProcInsId() != null) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("暂无流程信息！");
			}
		} catch (Exception e) {
			logger.error("查询失败", e);
			ajaxView.setFailed().setMessage("查询失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:applyRegister:view")
	@RequestMapping(value = "excelExport")
	public String excelExport(GedApplyRegister gedApplyRegister, Model model, HttpServletRequest request, HttpServletResponse response, String exportFlag) throws IOException {
		String fileName = "申请数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		ExportExcel excel = new ExportExcel("申请数据", GedApplyRegister.class, 1);
		List<Object[]> annotationList = Lists.newArrayList();
		for (Object[] obj : excel.annotationList) {
			ExcelField field = (ExcelField) obj[0];
			String title = field.title();
			if (!"ID".equals(title)) {
				annotationList.add(obj);
			}
		}
		excel.annotationList = annotationList;
		excel.initializeTitle();
		if ("onepage".equals(exportFlag)) {
			// 分页查询
			Page<GedApplyRegister> page = gedApplyRegisterService.findPage(new Page<GedApplyRegister>(request, response), gedApplyRegister);
			excel.setDataList(page.getList()).write(response, fileName).dispose();
		} else if ("allpage".equals(exportFlag)) {
			// 全部查询
			List<GedApplyRegister> page = gedApplyRegisterService.findgedGedApplyRegisters(gedApplyRegister);
			excel.setDataList(page).write(response, fileName).dispose();
		}
		return null;
	}

}
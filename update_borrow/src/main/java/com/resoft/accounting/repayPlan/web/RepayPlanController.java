package com.resoft.accounting.repayPlan.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 应还款查询Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/repayPlan")
public class RepayPlanController extends BaseController {

	@Autowired
	private AccRepayPlanService repayPlanService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@ModelAttribute
	public AccRepayPlan get(@RequestParam(required = false) String id) {
		AccRepayPlan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = repayPlanService.get(id);
		}
		if (entity == null) {
			entity = new AccRepayPlan();
		}
		return entity;
	}

	@RequiresPermissions("accounting:repayPlan:view")
	@RequestMapping(value = { "list", "" })
	public String list(AccRepayPlan repayPlan, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
			if (repayPlan != null && repayPlan.getCompany() != null && repayPlan.getCompany().getId() != null) {
			Office office = new Office();
			office.setId(repayPlan.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(repayPlan.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				repayPlan.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				repayPlan.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				repayPlan.setOrgLevel4(office);
			}
		}

		Page<AccRepayPlan> page =null;
		if("button".equals(queryFlag)){

			AccRepayPlan accRepayPlan = repayPlanService.selectAccRepayPlanByXinxi(repayPlan); //查询应还本息之后 还有账户管理费之和
			model.addAttribute("accRepayPlan",accRepayPlan);

			page = repayPlanService.findPage(new Page<AccRepayPlan>(request, response), repayPlan);
			model.addAttribute("page", page);
		}
		model.addAttribute("repayPlan", repayPlan);
		return "app/accounting/repayPlan/repayPlanList";
	}

	@RequiresPermissions("accounting:repayPlan:view")
	@RequestMapping(value = "form")
	public String form(AccRepayPlan repayPlan, Model model) {
		model.addAttribute("repayPlan", repayPlan);
		return "app/accounting/repayPlan/repayPlanForm";
	}

	@RequiresPermissions("accounting:repayPlan:edit")
	@RequestMapping(value = "save")
	public String save(AccRepayPlan repayPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, repayPlan)) {
			return form(repayPlan, model);
		}
		repayPlanService.save(repayPlan);
		addMessage(redirectAttributes, "保存应还款查询成功");
		return "redirect:" + Global.getAdminPath() + "/repayPlan/repayPlan/?repage";
	}

	@RequiresPermissions("accounting:repayPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(AccRepayPlan repayPlan, RedirectAttributes redirectAttributes) {
		repayPlanService.delete(repayPlan);
		addMessage(redirectAttributes, "删除应还款查询成功");
		return "redirect:" + Global.getAdminPath() + "/repayPlan/repayPlan/?repage";
	}


	@RequestMapping(value = "upload")
	public String upload(Model model, HttpServletRequest request, HttpServletResponse reponse) throws IOException {
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
		MultipartFile file = mhsr.getFile("uploadRepay");
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (fileName == null || "".equals(fileName)) {
				model.addAttribute("errorMessageRepay", "请传入文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else if (fileName.indexOf(".xls") == -1 && fileName.indexOf(".xlsx") == -1) {
				model.addAttribute("errorMessageRepay", "请选择正确的Excel文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else {
				String message = repayPlanService.importData(file);
				if (StringUtils.isNull(message)) {
					model.addAttribute("errorMessageRepay", "导入成功");
				} else {
					model.addAttribute("errorMessageRepay", message);
				}
			}
		} else {
			model.addAttribute("errorMessageRepay", "请重新打开小窗口上传!");
		}
		return "app/accounting/contract/contratImportForm";
	}

	
}
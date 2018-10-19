package com.resoft.postloan.mortgageEvaluateInfo.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateItems;
import com.resoft.postloan.mortgageEvaluateInfo.service.MortgageEvaluateInfoService;
import com.resoft.postloan.mortgageEvaluateInfo.service.MortgageEvaluateItemsService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 抵质押物管理Controller
 * 
 * @author zhaohuakui
 * @version 2016-05-25
 * @param <MortgageEvaluateItems>
 */
@Controller("plMortgageEvaluateInfoController")
@RequestMapping(value = "${adminPath}/postloan/mortgageEvaluateInfo")
public class MortgageEvaluateInfoController extends BaseController {

	@Autowired
	private MortgageEvaluateInfoService mortgageEvaluateInfoService;
	@Autowired
	private MortgageEvaluateItemsService mortgageEvaluateItemsService;
	/* 抵(质)押物管理 */
	@Autowired
	private PLContractService plContractService;

	private MyMap setParamMap(MortgageEvaluateInfo mortgageEvaluateInfo) {
		MyMap paramMap = new MyMap();
		paramMap.put("id", mortgageEvaluateInfo.getId());
		return paramMap;
	}

	private MyMap setParamTractMap(PLContract plContract) {
		MyMap ParamTractMap = new MyMap();
		if (plContract != null) {
			ParamTractMap.put("contractNo", plContract.getContractNo());
			ParamTractMap.put("custName", plContract.getCustName());
			ParamTractMap.put("approProductTypeId", plContract.getApproProductTypeId());
		}
		return ParamTractMap;
	}

	@RequiresPermissions("postloan:mortgageEvaluateInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("plContract", plContract);
			MyMap paramMap = setParamTractMap(plContract);
			Page<MyMap> page = plContractService.findAssetContractList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
			model.addAttribute("toAllocate", true);
			model.addAttribute("myPath", "contractList");
		} catch (Exception e) {
			logger.error("查询抵(质)押物管理时发生异常！", e);
			model.addAttribute("myPath", "contractList");
			model.addAttribute("message", "查询抵(质)押物管理时发生异常！");
		}
		return "app/postloan/mortgageEvaluateInfo/mortgageEvaluateInfoList";
	}

	@RequiresPermissions("postloan:mortgageEvaluateInfo:view")
	@RequestMapping(value = "editInfo")
	public String editInfo(MortgageEvaluateInfo mortgageEvaluateInfo, String applyNo, String infoId, Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			/* 查询抵质押物 检查项目 信息 */
			List<MortgageEvaluateItems> mortgageItemsList = mortgageEvaluateItemsService.findItemsList(infoId);
			if (mortgageItemsList != null && mortgageItemsList.size() > 0) {
				model.addAttribute("mortgageItemsList", mortgageItemsList);
			} else {
				/* 如果表中并没有检查项目类别 则新增字典表里的检查项目 */
				List<String> checkItemsLst = mortgageEvaluateItemsService.getcheckItems();
				for (int i = 0; i < checkItemsLst.size(); i++) {
					String checkItems = checkItemsLst.get(i);
					String idd = IdGen.uuid();
					MyMap paramCheckMap = new MyMap();
					paramCheckMap.put("id", idd);
					paramCheckMap.put("infoId", infoId);
					paramCheckMap.put("checkItems", checkItems);
					paramCheckMap.put("applyNo", applyNo);
					mortgageEvaluateItemsService.insertCheck(paramCheckMap);
					List<MortgageEvaluateItems> mortgageItemsListAfter = mortgageEvaluateItemsService.findItemsList(infoId);
					model.addAttribute("mortgageItemsList", mortgageItemsListAfter);
				}
			}
		} catch (Exception e) {
			logger.error("新增检查项目失败", e);
			model.addAttribute("message", "新增检查项目失败,请查看后台信息");
		}
		return "app/postloan/mortgageEvaluateInfo/mortgageEvaluateItemsList";
	}

	@ResponseBody
	@RequiresPermissions("postloan:mortgageEvaluateInfo:save")
	@RequestMapping(value = "save")
	public AjaxView save(MortgageEvaluateInfo mortgageEvaluateInfo, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortgageEvaluateInfoService.save(mortgageEvaluateInfo);
			rtn.setSuccess().setMessage("保存基本信息成功！");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("保存基本信息失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}

	@RequiresPermissions("postloan:mortgageEvaluateInfo:delete")
	@RequestMapping(value = "delete")
	public String delete(MortgageEvaluateInfo mortgageEvaluateInfo, RedirectAttributes redirectAttributes) {
		mortgageEvaluateInfoService.delete(mortgageEvaluateInfo);
		addMessage(redirectAttributes, "删除资产管理成功");
		return "redirect:" + Global.getAdminPath() + "/mortgageEvaluateInfo/mortgageEvaluateInfo/?repage";
	}

	// 检查项目 详情
	@RequestMapping(value = "showEdit")
	public String showEdit(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MortgageEvaluateItems mortgageItems = mortgageEvaluateItemsService.showEdit(id);
			model.addAttribute("mortgageItems", mortgageItems);
		} catch (Exception e) {
			logger.error("检查项目 编辑详情失败", e);
			model.addAttribute("message", "检查项目 编辑详情失败,请查看后台信息");
		}
		return "app/postloan/mortgageEvaluateInfo/mortgageItemsForm";
	}

	// 保存编辑详情
	@ResponseBody
	@RequiresPermissions("postloan:mortgageEvaluateInfo:edit")
	@RequestMapping(value = "saveEdit")
	public AjaxView saveEdit(MortgageEvaluateItems mortgageEvaluateItems, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortgageEvaluateItemsService.saveEdit(mortgageEvaluateItems);
			rtn.setSuccess().setMessage("保存基本信息成功！");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("保存基本信息失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}

	// 详情页面
	@RequestMapping(value = { "toDetailsPage" })
	public String toDetailsPage(HttpServletRequest request, HttpServletResponse response, Model model, String applyNo) {
		model.addAttribute("applyNo", applyNo);
		return "app/postloan/mortgageEvaluateInfo/mortgageEvaluateAssetIndex";
	}

}
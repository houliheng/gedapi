package com.resoft.postloan.collateralDisposalConclusion.web;

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

import com.resoft.postloan.collateralDisposalConclusion.entity.CollateralDisposalConclusion;
import com.resoft.postloan.collateralDisposalConclusion.service.CollateralDisposalConclusionService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 抵押物处置流程意见Controller
 * @author wangguodong
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/collateralDisposalConclusion")
public class CollateralDisposalConclusionController extends BaseController {

	@Autowired
	private CollateralDisposalConclusionService collateralDisposalConclusionService;
	
	@ModelAttribute
	public CollateralDisposalConclusion get(@RequestParam(required=false) String id) {
		CollateralDisposalConclusion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = collateralDisposalConclusionService.get(id);
		}
		if (entity == null){
			entity = new CollateralDisposalConclusion();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:collateralDisposalConclusion:view")
	@RequestMapping(value = {"list", ""})
	public String list(CollateralDisposalConclusion collateralDisposalConclusion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CollateralDisposalConclusion> page = collateralDisposalConclusionService.findCustomPage(new Page<CollateralDisposalConclusion>(request, response), collateralDisposalConclusion); 
		model.addAttribute("page", page);
		return "app/postloan/collateralDisposalConclusion/collateralDisposalConclusionList";
	}

	@ResponseBody
	@RequiresPermissions("postloan:collateralDisposalConclusion:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ActTaskParam actTaskParam,CollateralDisposalConclusion collateralDisposalConclusion,String passFlag) {
		AjaxView ajaxView = new AjaxView();
		try {
			collateralDisposalConclusionService.saveSuggestion(actTaskParam,collateralDisposalConclusion,passFlag);
			ajaxView.setSuccess().setMessage("保存意见成功");
		} catch (Exception e) {
			logger.error("保存意见失败",e);
			ajaxView.setFailed().setMessage("保存意见失败");
		}
		return ajaxView;
	}

}
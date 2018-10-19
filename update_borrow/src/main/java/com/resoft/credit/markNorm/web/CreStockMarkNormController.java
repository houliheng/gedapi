package com.resoft.credit.markNorm.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.markNorm.entity.CreStockMarkApplyRelation;
import com.resoft.credit.markNorm.entity.CreStockMarkNorm;
import com.resoft.credit.markNorm.service.CreStockMarkApplyRelationService;
import com.resoft.credit.markNorm.service.CreStockMarkNormService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 股权加减分项Controller
 * @author zcl
 * @version 2017-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creStockMarkNorm")
public class CreStockMarkNormController extends BaseController {

	@Autowired
	private CreStockMarkNormService creStockMarkNormService;
	@Autowired
	private CreStockMarkApplyRelationService creStockMarkApplyRelationService;
	
	@ModelAttribute
	public CreStockMarkNorm get(@RequestParam(required=false) String id) {
		CreStockMarkNorm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creStockMarkNormService.get(id);
		}
		if (entity == null){
			entity = new CreStockMarkNorm();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creStockMarkNorm:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreStockMarkNorm CreStockMarkNorm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreStockMarkNorm> page = creStockMarkNormService.findCustomPage(new Page<CreStockMarkNorm>(request, response), CreStockMarkNorm); 
		model.addAttribute("page", page);
		return "app/credit/markNorm/CreStockMarkNormList";
	}

	@RequiresPermissions("credit:creStockMarkNorm:view")
	@RequestMapping(value = "form")
	public String form(CreStockMarkNorm creStockMarkNorm, Model model, ActTaskParam actTaskParam, String readOnly) {
		model.addAttribute("CreStockMarkNorm", creStockMarkNorm);
		//查询加分项
		List<CreStockMarkNorm> addList = creStockMarkNormService.findCheckedByApplyNoList(actTaskParam.getApplyNo(),Constants.stock_mark_type_add);
		model.addAttribute("addList",addList);
		//查询减分项
		List<CreStockMarkNorm> minusList = creStockMarkNormService.findCheckedByApplyNoList(actTaskParam.getApplyNo(),Constants.stock_mark_type_minus);
		model.addAttribute("minusList", minusList);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/markNorm/creStockMarkNormForm";
	}

	@RequiresPermissions("credit:creStockMarkNorm:edit")
	@RequestMapping(value = "save")
	public String save(CreStockMarkNorm CreStockMarkNorm, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, CreStockMarkNorm)){
//			return form(CreStockMarkNorm, model);
//		}
//		CreStockMarkNormService.save(CreStockMarkNorm);
		addMessage(redirectAttributes, "保存股权加减分项成功");
		return "redirect:"+Global.getAdminPath()+"/markNorm/CreStockMarkNorm/?repage";
	}
	
	@RequiresPermissions("credit:creStockMarkNorm:edit")
	@RequestMapping(value = "delete")
	public String delete(CreStockMarkNorm CreStockMarkNorm, RedirectAttributes redirectAttributes) {
		creStockMarkNormService.delete(CreStockMarkNorm);
		addMessage(redirectAttributes, "删除股权加减分项成功");
		return "redirect:"+Global.getAdminPath()+"/markNorm/CreStockMarkNorm/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creStockMarkNorm:view")
	@RequestMapping(value = "saveMarkApplyRelationAjax")
	public AjaxView saveMarkApplyRelationAjax(HttpServletRequest request,@Param(value = "applyNo") String applyNo, 
			@Param(value = "normIds") String normIds, @Param(value = "markType") String markType) {
		AjaxView av = new AjaxView();
		av.setSuccess();
		try {
			List<CreStockMarkApplyRelation> list = new ArrayList<CreStockMarkApplyRelation>();
			if(StringUtils.isNotEmpty(normIds)){
				String[] ids = normIds.split(",");
				Date date = new Date();
				User user = UserUtils.getUser();
				for(int i=0;i<ids.length;i++){
					CreStockMarkApplyRelation relateion = new CreStockMarkApplyRelation();
					relateion.setMarkNormId(ids[i]);
					relateion.setApplyNo(applyNo);
					relateion.setCreateBy(user);
					relateion.setCreateDate(date);
					relateion.setUpdateBy(user);
					relateion.setUpdateDate(date);
					list.add(relateion);
				}
			}
			creStockMarkApplyRelationService.updateNewRelation(applyNo,markType,list);
		} catch (Exception e) {
			av.setFailed();
			av.setMessage("保存数据失败" + e);
			e.printStackTrace();
		}
		return av;
	}
}
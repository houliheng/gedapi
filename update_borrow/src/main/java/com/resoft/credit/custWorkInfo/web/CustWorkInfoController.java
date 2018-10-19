package com.resoft.credit.custWorkInfo.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 客户工作信息表Controller
 * @author gaofeng
 * @version 2016-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/custWorkInfo")
public class CustWorkInfoController extends BaseController {


	@Autowired
	private AreaService areaService;//区域地质service
	
	@Autowired
	private CustWorkInfoService custWorkInfoService;
	
	@ModelAttribute
	public CustWorkInfo get(@RequestParam(required=false) String id) {
		CustWorkInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = custWorkInfoService.get(id);
		}
		if (entity == null){
			entity = new CustWorkInfo();
		}
		return entity;
	}
	
//	@RequiresPermissions("credit:custInfo:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(CustWorkInfo custWorkInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CustWorkInfo> page = custWorkInfoService.findPage(new Page<CustWorkInfo>(request, response), custWorkInfo); 
//		model.addAttribute("page", page);
//		return "app/credit/custWorkInfo/custWorkInfoList";
//	}

	@RequiresPermissions("credit:custInfo:view")
	@RequestMapping(value = "form")
	public String form(CustWorkInfo custWorkInfo, Model model) {
		//传入对象
		model.addAttribute("custWorkInfo", custWorkInfo);
		return "app/credit/custWorkInfo/custWorkInfoForm";
	}
	//级联获取方法。
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LinkedHashMap<String, String> loadAreaData(String areaCode){
		Map param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if(StringUtils.isNotEmpty(areaCode)){
			param.put("parentId", areaCode);//根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}
	
	
	@RequiresPermissions("credit:custInfo:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(CustWorkInfo custWorkInfo) {
		AjaxView ajaxView = new AjaxView();
		try {
			String custId = custWorkInfo.getCustInfo().getId();
			custWorkInfo.setCustId(custId);
			custWorkInfoService.saveCustWorkInfo(custWorkInfo);
			ajaxView.setSuccess().setMessage("保存主借人工作信息成功！");
			ajaxView.put("id", custWorkInfo.getId());
		} catch (Exception e) {
			logger.error("保存主借人工作信息失败！",e);
			ajaxView.setFailed().setMessage("保存主借人工作信息失败！");
		}
		return ajaxView;
	}
//	
//	@RequiresPermissions("credit:custInfo:edit")
//	@RequestMapping(value = "delete")
//	public String delete(CustWorkInfo custWorkInfo, RedirectAttributes redirectAttributes) {
//		custWorkInfoService.delete(custWorkInfo);
//		addMessage(redirectAttributes, "删除客户工作信息成功");
//		return "redirect:"+Global.getAdminPath()+"/custWorkInfo/custWorkInfo/?repage";
//	}

}
package com.resoft.credit.stockTaskDistribute.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.stockTaskDistribute.service.StockTaskDistributeService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 股权估值服务列表Controller
 * @author jml
 * @version 2017-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockTaskDistribute")
public class StockTaskDistributeController extends BaseController {
	@Autowired
	private StockTaskDistributeService stockTaskDistributeService;
	
	@RequestMapping("/loadOrgUser")
	public String loadOrgUser(ActTaskParam actTaskParam, Model model, HttpServletRequest request,String stockType,String stockTaskReceiveId) {
		User user = UserUtils.getUser();
		HashMap<String, Object> parmas = Maps.newHashMap();
//		parmas.put("companyId", user.getCompany().getId());
		parmas.put("id", user.getId());
		parmas.put("companyCode", request.getParameter("companyCode"));
		parmas.put("companyName", request.getParameter("companyName"));
		parmas.put("loginName", request.getParameter("loginName"));
		parmas.put("userName", request.getParameter("userName"));
		List<User> list = stockTaskDistributeService.findUserAndOrleList(parmas);
		model.addAttribute("list", list);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("queryParams", parmas);
		model.addAttribute("stockType", stockType);
		model.addAttribute("stockTaskReceiveId", stockTaskReceiveId);
		return "/app/credit/stockTaskDistribute/selectStockUser";
	}
	
	@RequestMapping("/gotoLoadOrgUser")
	public String gotoLoadOrgUser(ActTaskParam actTaskParam, Model model,String stockType,String stockTaskReceiveId) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("stockType", stockType);
		model.addAttribute("stockTaskReceiveId", stockTaskReceiveId);
		return "/app/credit/stockTaskDistribute/selectStockUser";
	}
	
	@ResponseBody
	@RequestMapping("/changeup")
	public AjaxView changeup(ActTaskParam actTaskParam, Model model, HttpServletRequest request,String stockType,String stockTaskReceiveId) {
		AjaxView ajaxView = new AjaxView();
		String sysuserid = request.getParameter("sysuserid");
		StockTaskReceive st = new StockTaskReceive();
		User user = null;
		if(sysuserid != null){
			user = UserUtils.get(sysuserid);
			st.setIsStockPost(1);
		}else{
			user = UserUtils.getUser();
			st.setIsStockPost(0);
		}
		
		try {
			if (!(StringUtils.isNull(user))) {
				st.setApplyNo(actTaskParam.getApplyNo());
				st.setCode(user.getCompany().getCode());
				st.setReceiver(user.getId());
				stockTaskDistributeService.saveStockReceive(st,actTaskParam.getTaskDefKey(),stockType,stockTaskReceiveId);
			} else {
				ajaxView.setFailed().setMessage("用户查询出错，请检查");
				return ajaxView;
			}
			ajaxView.setSuccess().setMessage("人员选择成功!!");
		} catch (Exception e) {
			logger.error("人员选择失败", e);
			ajaxView.setFailed().setMessage("人员选择失败" + e);
		}
		return ajaxView;
	}
	
//	@ModelAttribute
//	public StockTaskDistribute get(@RequestParamData(required=false) String id) {
//		StockTaskDistribute entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = stockTaskDistributeService.get(id);
//		}
//		if (entity == null){
//			entity = new StockTaskDistribute();
//		}
//		return entity;
//	}
//	
//	@RequiresPermissions("credit:stockTaskDistribute:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(StockTaskDistribute stockTaskDistribute, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<StockTaskDistribute> page = stockTaskDistributeService.findCustomPage(new Page<StockTaskDistribute>(request, response), stockTaskDistribute); 
//		model.addAttribute("page", page);
//		return "app/credit/stockTaskDistribute/stockTaskDistributeList";
//	}
//
//	@RequestMapping("/loadOrgUser")
//	public String loadCurrOrgUser(String flag,ActTaskParam actTaskParam,String distributeId , String processName,String applyNo, Model model, User user,
//			HttpServletRequest request, HttpServletResponse response,Ally ally) {
//		List<AllyUser> userList = allyService.findUser(ally.getId());
//		model.addAttribute("ally", ally);
//		model.addAttribute("applyNo", applyNo);
//		model.addAttribute("distributeId", distributeId);
//		model.addAttribute("userList", userList);
//		model.addAttribute("selectIds", Collections3.extractToString(userList, "userid", ","));
//		model.addAttribute("officeList", officeService.findAll());
//		model.addAttribute("flag", flag);
//		model.addAttribute("actTaskParam", actTaskParam);
//		return "app/credit/stockTaskDistribute/distributeUser";
//	}
	
}
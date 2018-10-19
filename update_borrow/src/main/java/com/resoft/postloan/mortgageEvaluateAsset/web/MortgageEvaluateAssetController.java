package com.resoft.postloan.mortgageEvaluateAsset.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.evaluateAsset.service.MortgageFindCustNameService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.mortgageEvaluateAsset.entity.MortgageEvaluateAsset;
import com.resoft.postloan.mortgageEvaluateAsset.service.MortgageEvaluateAssetService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 资产评估Controller
 * @author zhaohuakui
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/mortgageEvaluateAsset")
public class MortgageEvaluateAssetController extends BaseController {
	@Autowired
	private MortgageEvaluateAssetService mortgageEvaluateAssetService;
	@Autowired
	private MortgageFindCustNameService mortgageFindCustNameService;
	@Autowired
	private PLContractService plContractService;
	private MyMap setParamMap(MortgageEvaluateAsset mortgageEvaluateAsset){
		MyMap paramMap = new MyMap();
		paramMap.put("applyNo", mortgageEvaluateAsset.getApplyNo());
		paramMap.put("assetName", mortgageEvaluateAsset.getAssetName());
		paramMap.put("id", mortgageEvaluateAsset.getId());
		return paramMap; 
	}
	private MyMap setParamTractMap(PLContract plContract) {
		MyMap ParamTractMap = new MyMap();
		if (plContract != null) {
				ParamTractMap.put("approProductTypeId",plContract.getApproProductTypeId());
				ParamTractMap.put("contractNo", plContract.getContractNo());
				ParamTractMap.put("custName", plContract.getCustName());
		}
		return ParamTractMap;
	}
	@RequiresPermissions("postloan:mortgageEvaluateAsset:view")
	@RequestMapping(value = {"list", ""})
	public String list(PLContract plContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("mortgageEvaluateAsset", plContract);
			MyMap paramMap = setParamTractMap(plContract);
			Page<MyMap> page = plContractService.findAssetContractList(
					new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询资产管理清单时发生异常！", e);
			model.addAttribute("message", "查询资产管理清单时发生异常！");
		}
		return "app/postloan/mortgageEvaluateAsset/mortgageEvaluateAssetList";
	}
	/*编辑基本信息*/
	@RequestMapping(value = "edit")
	public String form(MortgageEvaluateAsset mortgageEvaluateAsset,  HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MortgageEvaluateAsset mortgage =mortgageEvaluateAssetService.get(mortgageEvaluateAsset.getId());
			if(mortgage!=null){
			model.addAttribute("mortgageEvaluateAsset", mortgage);
			if ("1".equals(mortgage.getIsPushData())){
				model.addAttribute("readOnlyAsset", false);
			}else{
				model.addAttribute("readOnlyAsset", true);
			}
			}
		} catch (Exception e) {
			logger.error("编辑基本信息",e);
			model.addAttribute("message", "编辑基本信息失败,请查看后台信息");
		}
		return "app/postloan/mortgageEvaluateAsset/mortgageAddAssetForm";
	}
	/*编辑基本信息保存*/
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(MortgageEvaluateAsset mortgageEvaluateAsset, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortgageEvaluateAssetService.save(mortgageEvaluateAsset);
			rtn.setSuccess().setMessage("保存基本信息成功！");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("保存基本信息失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}
	//查看新增资产 选择	 
	@RequestMapping(value = {"addAsset"})
	public String addAsset(MortgageEvaluateAsset mortgageEvaluateAsset, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MyMap paramMap = setParamMap(mortgageEvaluateAsset);
			Page<MyMap> page = mortgageEvaluateAssetService.findpage(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查看新增资产失败",e);
			model.addAttribute("message", "查看新增资产失败,请查看后台信息");
		}
		return "app/postloan/mortgageEvaluateAsset/mortgageaddAssetList";
	}
	//新增资产 
	@RequestMapping(value = {"addSave"})
	public String addSave(MortgageEvaluateAsset mortgageEvaluateAsset, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("mortgageEvaluateAsset", mortgageEvaluateAsset);
		model.addAttribute("readOnlyAsset", false);
		return "app/postloan/mortgageEvaluateAsset/mortgageAddAssetForm";
	}	
	//修改备注 
	@RequestMapping(value = {"reviceAsset"})
	public String reviceAsset(MortgageEvaluateAsset mortgageEvaluateAsset,HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MyMap paramMap = setParamMap(mortgageEvaluateAsset);
			Page<MyMap> page = mortgageEvaluateAssetService.findpage(new Page<MyMap>(request, response), paramMap);
			MyMap mortgage = page.getList().get(0);
			model.addAttribute("mortgage", mortgage);
		} catch (Exception e) {
			logger.error("修改备注失败",e);
			model.addAttribute("message", "修改备注失败,请查看后台信息");
		}
		return "app/postloan/mortgageEvaluateAsset/mortgageReviceAssetForm";
	}
	//备注修改保存
	@ResponseBody
	@RequestMapping(value = "saveRemark")
	public AjaxView saveRemark(MortgageEvaluateAsset mortgageEvaluateAsset, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rms = new AjaxView();
		try {
			mortgageEvaluateAssetService.saveRemark(mortgageEvaluateAsset);
			rms.setSuccess().setMessage("备注保存成功！");
		} catch (Exception e) {
			rms.setSuccess().setMessage("保存失败！");
			logger.error("备注保存失败！", e);
		}
		return rms;
	}
	//资产新增保存
	@ResponseBody
	@RequestMapping(value = "saveAsset")
	public AjaxView saveAsset(MortgageEvaluateAsset mortgageEvaluateAsset, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			if (mortgageEvaluateAsset.getId().length()<=0||mortgageEvaluateAsset.getId()==null){
				mortgageEvaluateAsset.setId(IdGen.uuid());
				mortgageEvaluateAssetService.saveAsset(mortgageEvaluateAsset);
				rtn.setSuccess().setMessage("保存成功！");
			}else{
				mortgageEvaluateAssetService.updateAsset(mortgageEvaluateAsset);
				rtn.setSuccess().setMessage("修改成功！");
			}
			
		} catch (Exception e) {
			rtn.setSuccess().setMessage("保存失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}
		/**
		 * 在当前流程中，根据角色类型查询客户名称Ajax
		 * @param creditLineBank
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "findCustNameByRoleType")
		public List<Map<String, String>> findCustNameByRoleType(String roleType, String contractNo) {
			List<Map<String, String>> custNamesAndId = new ArrayList<Map<String, String>>();
			Map<String, String> params = Maps.newConcurrentMap();
			params.put("contractNo", contractNo);
			//人员类型为除了联系人其他的类型
			if(!Constants.ROLE_TYPE_CONTACT.equals(roleType)){
				params.put("roleType", roleType);
				custNamesAndId =  mortgageFindCustNameService.findCustNameByRoleType(params);
			}else if(Constants.ROLE_TYPE_CONTACT.equals(roleType)){
				//主借人类型
				params.put("roleType", Constants.ROLE_TYPE_ZJR);
				custNamesAndId =  mortgageFindCustNameService.getContactInfoByApplyNo(params);
			}
			return custNamesAndId;
		}
			
}
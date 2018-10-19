/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.contractarchive.web;


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

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import resoft.crms.util.StringUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.DataScopeFitter;
import com.resoft.credit.contractarchive.entity.ContractArchive;
import com.resoft.credit.contractarchive.service.ContractArchiveService;

/**
 * 合同归档信息表Controller
 * @author lirongchao
 * @version 2016-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/contractArchive/")
public class ContractArchiveController extends BaseController {

	@Autowired
	private ContractArchiveService contractArchiveService;

	@ModelAttribute
	public ContractArchive get(@RequestParam(required=false) String id) {
		ContractArchive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractArchiveService.get(id);
		}
		if (entity == null){
			entity = new ContractArchive();
		}
		return entity;
	}
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : contract_no,cust_name
	 * @db-j : sys_office : label
	 * @db-j : sys_dict : label
	 * @date-author:2016年01月25日-lirongchao:查询合同归档信息管理表，并将结果返回到合同归档信息管理页面
	 * 										查询条件-【客户名称（模糊查询）[custName]、合同编号（模糊查询）[contractNo]】
													【是否发送(下拉框)[isSender]、是否签收(下拉框)[isRecipitent]】
													【归属机构（参照用户管理查询条件的样式）[orgNum]】
													【发出日期（开始[startSenderTime]、结束时间[endSenderTime]）、签收日期（开始[startRecipientTime]、结束时间[endRecipientTime]）】
	 */
	@RequiresPermissions("contractarchive:contractArchive:view")
	@RequestMapping(value = {"list", ""})
	public String list(String returnVal,ContractArchive contractArchive, HttpServletRequest request, HttpServletResponse response, Model model) {
		DataScopeFitter.companyDataScopeFilterToContractArchive(contractArchive);
		/*Page<ContractArchive> page = new Page<ContractArchive>(request, response);
		page.setOrderBy("is_recipitent,recipient_time desc");
		page = contractArchiveService.findPage(page, contractArchive);
		model.addAttribute("page", page);
		if("1".equals(returnVal)){
			addMessage(model, "保存成功!");
		}*/
		model.addAttribute("contractArchive", contractArchive);
		return "app/credit/contractarchive/contractArchiveList";
	}

	// 表格数据
	@ResponseBody
	@RequestMapping(value = "/getData")
	public Map<String, Object> getData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = Maps.newHashMap();
		Map<String, Object> parMap = Maps.newHashMap();
		List<Map<String, Object>> plans = Lists.newArrayList();
		Long totalRecountLong = 0L;

		// 当前页
		String page = request.getParameter("page");
		// 每页显示的行数
		String rows = request.getParameter("rows");
		String custName = request.getParameter("custName");
		String contractNo = request.getParameter("contractNo");
		String senderName = request.getParameter("senderName");
		parMap.put("custName", custName);
		parMap.put("contractNo", contractNo);
		parMap.put("senderName", senderName);

		totalRecountLong = contractArchiveService.getExpressNoCount(parMap);
		int totalRecount = totalRecountLong.intValue();
		int totalPage = totalRecount % Integer.parseInt(rows) == 0 ? totalRecount / Integer.parseInt(rows) : totalRecount / Integer.parseInt(rows) + 1;
		parMap.put("pageStart", Integer.parseInt(rows) * (Integer.parseInt(page) - 1));
		parMap.put("pageEnd", Integer.parseInt(rows));

		plans = contractArchiveService.getDataList(parMap);

		params.put("totalPages", totalPage);
		params.put("totalRecount", totalRecount);
		params.put("dataList", plans);
		return params;
	}

	// 子表格数据
	@ResponseBody
	@RequestMapping(value = "/getSubData")
	public Map<String, Object> getSubData(ContractArchive contractArchive, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = Maps.newHashMap();
		Map<String, Object> parMap = Maps.newHashMap();
		List<Map<String, Object>> plans = Lists.newArrayList();
		if (!StringUtil.IsEmptyStr(contractArchive.getId())) {
			parMap.put("expressNo", contractArchive.getId());
			plans = contractArchiveService.getSubDataList(parMap);
			for (Map<String, Object> map : plans) {
				int isRecipitent = 1;
				StringBuilder sb = new StringBuilder();
				if (map.get("isRecipitent").equals("否")) {
					isRecipitent = 0;
					sb.append("&nbsp;&nbsp;<a style=\"color:blue;\" href=\"#\" onclick=\"recipitent('"+map.get("id")+"')\" >签收</a>");
				}
				sb.append("&nbsp;&nbsp;<a style=\"color:blue;\" href=\"#\" onclick=\"archive('"+map.get("id")+"','"+isRecipitent+"')\" >归档</a>");
				map.put("operation", sb.toString());
			}
			params.put("subDataList", plans);
		}
		return params;
	}


	/**
	 * @reqno: H1601150171
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : contract_no,cust_name
	 * @db-j : sys_office : label
	 * @db-j : sys_dict : label
	 * @date-author:2016年01月25日-lirongchao:查询合同归档信息管理表，并将结果返回到合同归档信息管理页面
	 * 										查询条件-【客户名称（模糊查询）[custName]、合同编号（模糊查询）[contractNo]】
													【是否发送(下拉框)[isSender]、是否签收(下拉框)[isRecipitent]】
													【发出日期（开始[startSenderTime]、结束时间[endSenderTime]）、签收日期（开始[startRecipientTime]、结束时间[endRecipientTime]）】
	 */
	@RequiresPermissions("contractarchive:contractArchive:view")
	@RequestMapping(value = "sendList")
	public String sendList(String returnVal,ContractArchive contractArchive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractArchive> page = new Page<ContractArchive>(request, response);
		page.setOrderBy("is_sender,sender_time desc");
		if(contractArchive.getIsSender() == null){
			contractArchive.setIsSender("0");
		}
		Office comPany = UserUtils.getUser().getCompany();
		contractArchive.setOrgNum(comPany);
		page = contractArchiveService.findPage(page, contractArchive);
		model.addAttribute("page", page);
		if("1".equals(returnVal)){
			addMessage(model, "保存成功!");
		}
		return "app/credit/contractarchive/contractSendList";
	}
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_recipitent
	 * @date-author:2016年01月25日-lirongchao:将多条信息进行签收，并提示签收成功
	 * 										条件-将多条信息的id以一定格式传给后台，后台再进行批量更新
	 */
	@RequiresPermissions("contractarchive:contractArchive:recipitent")
	@ResponseBody
	@RequestMapping(value = "recipitent")
	public String recipitent(ContractArchive contractArchive, String ids) {
		try {
			contractArchiveService.recipitent(ids);
			return "success";
		} catch (Exception e) {
			logger.error("签收失败！",e);
			return "fali";
		}

	}
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : id
	 * @date-author:2016年01月25日-lirongchao:跳转到归档页面，是否别借出默认为否。根据id查询出这条数据的信息
	 */
	@RequiresPermissions("contractarchive:contractArchive:archive")
	@RequestMapping(value = "archive")
	public String archive(ContractArchive contractArchive,Model model) {
		contractArchive = contractArchiveService.findByContractNo(contractArchive.getId());
		if(StringUtils.isEmpty(contractArchive.getIsBorrowing())){
			contractArchive.setIsBorrowing("0");
		}
		model.addAttribute("contractArchive", contractArchive);
		return "app/credit/contractarchive/contractArchive";
	}
	/**
	 * @reqno: H1601150180
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_recipitent
	 * @date-author:2016年01月25日-lirongchao:保存合同归档信息
	 */
	@RequiresPermissions("contractarchive:contractArchive:archiveSave")
	@ResponseBody
	@RequestMapping(value = "archiveSave")
	public String archiveSave(ContractArchive contractArchive,Model model,String borrowingTime) {
		String returnValue = contractArchiveService.archiveSave(contractArchive,borrowingTime);
		return returnValue;
	}
	/**
	 * @reqno: H1601150171
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : contract_no,cust_name
	 * @db-j : sys_office : label
	 * @db-j : sys_dict : label
	 * @date-author:2016年01月25日-lirongchao:跳转到合同归档发送页面，根据id查询数据信息。
	 */
	@RequiresPermissions("contractarchive:contractArchive:send")
	@RequestMapping(value = "send")
	public String send(ContractArchive contractArchive,Model model) {
		model.addAttribute("contractArchive", contractArchive);
		return "app/credit/contractarchive/contractSend";
	}
	/**
	 * @reqno: H1601150171
	 * @date-designer:2016年01月25日-lirongchao
	 * @db-z : cre_contract_archive : is_Sender
	 * @date-author:2016年01月25日-lirongchao:保存合同归档发送信息,并将状态改为已发送
	 */
	@RequiresPermissions("contractarchive:contractArchive:archiveSave")
	@ResponseBody
	@RequestMapping(value = "sendSave")
	public String sendSave(ContractArchive contractArchive,Model model,String senderTime) {
		String returnValue = contractArchiveService.sendSave(contractArchive,senderTime);
		return returnValue;
	}

}
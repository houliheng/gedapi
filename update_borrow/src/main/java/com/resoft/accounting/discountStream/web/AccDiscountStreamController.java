package com.resoft.accounting.discountStream.web;



import java.math.BigDecimal;
import java.util.List;

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

import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.discountStream.entity.AccDiscountStream;
import com.resoft.accounting.discountStream.entity.ContractDetailVo;
import com.resoft.accounting.discountStream.entity.PeriodDiscountDetail;
import com.resoft.accounting.discountStream.service.AccDiscountStreamService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 贴息流水Controller
 * @author gsh
 * @version 2018-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/accDiscountStream")
public class AccDiscountStreamController extends BaseController {

	@Autowired
	private AccDiscountStreamService accDiscountStreamService;
	@Autowired
	private StaContractStatusService staContractStatusService;
	@Autowired
	private ContractLockDao contractLockDao;
	@ModelAttribute
	public AccDiscountStream get(@RequestParam(required=false) String id) {
		AccDiscountStream entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accDiscountStreamService.get(id);
		}
		if (entity == null){
			entity = new AccDiscountStream();
		}
		return entity;
	}
	
	@RequiresPermissions("accounting:accDiscountStream:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccDiscountStream accDiscountStream, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
		if (accDiscountStream != null && accDiscountStream.getCompany() != null && !StringUtils.isEmpty(accDiscountStream.getCompany().getId())) {
			Office office = new Office();
			office.setId(accDiscountStream.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(accDiscountStream.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				accDiscountStream.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				accDiscountStream.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				accDiscountStream.setOrgLevel4(office);
			}
		}
		Page<AccDiscountStream>  page = null;
		if ("button".equals(queryFlag)) {
			page = accDiscountStreamService.findCustomPage(new Page<AccDiscountStream>(request, response), accDiscountStream);
		} 
		model.addAttribute("page", page);
		model.addAttribute("discountDate",request.getParameter("discountDate"));
		return "app/accounting/discountStream/accDiscountStreamList";
	}

	@RequiresPermissions("accounting:accDiscountStream:view")
	@RequestMapping(value = "contractDiscountDertail")
	public String form(ContractDetailVo contractDetailVo, Model model,HttpServletRequest request) {
		try {
			String contractNo = request.getParameter("contractNo");
			contractDetailVo = accDiscountStreamService.findContractDiscountDetail(contractNo);
			if (contractDetailVo.getStayMoney().contains("-") || "1800".equals(contractDetailVo.getBorrowLoanStatus())) {
				contractDetailVo.setStayMoney("0.00");
			}
			List<PeriodDiscountDetail> periodDiscountDetails = accDiscountStreamService.queryDiscountDetails(contractNo);
			for (PeriodDiscountDetail periodDiscountDetail : periodDiscountDetails) {
				String stayMoney = periodDiscountDetail.getStayMoney();
				if ("0100".equals(periodDiscountDetail.getPeriodStatus()) || "0400".equals(periodDiscountDetail.getPeriodStatus()) || stayMoney.contains("-") || "0500".equals(periodDiscountDetail.getPeriodStatus())) {
					periodDiscountDetail.setStayMoney("0.00");
				}
			}
			for(PeriodDiscountDetail periodDiscountDetail:periodDiscountDetails){
				if (periodDiscountDetail.getPeriodStatus() == null || "0200".equals(periodDiscountDetail.getPeriodStatus()) || "0300".equals(periodDiscountDetail.getPeriodStatus())) {
					contractDetailVo.setRepayMoney(periodDiscountDetail.getCapitalInte());
					break;
				}
			}
			model.addAttribute("contractDetailVo", contractDetailVo);
			model.addAttribute("periodDiscountDetails", periodDiscountDetails);
		} catch (Exception e) {
			logger.error("查询详情出错",e);
			model.addAttribute("queryError","查询合同贴息详情出错");
		}
		return "app/accounting/discountStream/contractDetail";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(AccDiscountStream accDiscountStream, Model model, RedirectAttributes redirectAttributes) {
		accDiscountStream.setCustName(UserUtils.getUser().getName());
		AjaxView ajaxView = new AjaxView();
		ajaxView = accDiscountStreamService.pushGeTAndZjSave(accDiscountStream,ajaxView);
		return ajaxView ;
	}
	
	@RequiresPermissions("accounting:accDiscountStream:edit")
	@RequestMapping(value = "delete")
	public String delete(AccDiscountStream accDiscountStream, RedirectAttributes redirectAttributes) {
		accDiscountStreamService.delete(accDiscountStream);
		addMessage(redirectAttributes, "删除gsh成功");
		return "redirect:"+Global.getAdminPath()+"/discountStream/accDiscountStream/?repage";
	}
	
	
	@RequestMapping(value = "validate")
	@ResponseBody
	public AjaxView validate(AccDiscountStream accDiscountStream, Model model, RedirectAttributes redirectAttributes) {
		accDiscountStream.setCustName(UserUtils.getUser().getLoginName());
		AjaxView ajaxView = new AjaxView();
		try {
			 ContractLock contractLock = new ContractLock();
			   contractLock.setContractNo(accDiscountStream.getContractNo());
			   contractLock = contractLockDao.validateIsLock(contractLock);
			   if (contractLock != null) {
			    return ajaxView.setFailed().setMessage("此合同号存在正在申请中的操作");
			   }
			   if ("1".equals(accDiscountStream.getPeriodNum())) {
				
			}else{
				String periodNum = String.valueOf((Integer.valueOf(accDiscountStream.getPeriodNum())-1));
				accDiscountStream = accDiscountStreamService.queryDisStrBycontractNoAndPer(accDiscountStream.getContractNo(),periodNum);
				if (accDiscountStream == null) {
					return ajaxView.setFailed().setMessage("尚有未贴息的期数请贴完之后再贴!");
				}
			}
			ajaxView.setSuccess();   
		} catch (Exception e) {
			logger.equals(e);
			return ajaxView.setFailed().setMessage("查询出错");
		}
		return ajaxView ;
	}
	
	
}
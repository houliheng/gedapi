package com.resoft.credit.compenSatoryDetail.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.common.utils.JsonTransferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.activemq.service.ProducerService;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;
import com.resoft.credit.compensatory.service.CompensatoryAccountService;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 代偿详情表Controller
 * @author jml
 * @version 2018-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/compensatoryDetail")
public class CompensatoryDetailController extends BaseController {

	@Autowired
	private CompensatoryDetailService compensatoryDetailService;
	@Autowired
	private CompensatoryAccountService compensatoryAccountService;
	@Autowired
	private ContractLockDao contractLockDao;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;
	
	
	@ModelAttribute
	public CompensatoryDetail get(@RequestParam(required=false) String id) {
		CompensatoryDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = compensatoryDetailService.get(id);
		}
		if (entity == null){
			entity = new CompensatoryDetail();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CompensatoryDetail compensatoryDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompensatoryDetail> page = compensatoryDetailService.findCustomPage(new Page<CompensatoryDetail>(request, response), compensatoryDetail); 
		model.addAttribute("page", page);
		return "app/credit/compenSatoryDetail/compensatoryDetailList";
	}

	@RequestMapping(value = "form")
	public String form(CompensatoryDetail compensatoryDetail, Model model) {
		model.addAttribute("compensatoryDetail", compensatoryDetail);
		return "app/credit/compenSatoryDetail/compensatoryDetailForm";
	}

	
	@RequestMapping(value = "delete")
	public String delete(CompensatoryDetail compensatoryDetail, RedirectAttributes redirectAttributes) {
		compensatoryDetailService.delete(compensatoryDetail);
		addMessage(redirectAttributes, "删除代偿详情表成功");
		return "redirect:"+Global.getAdminPath()+"/compenSatoryDetail/compensatoryDetail/?repage";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(String id,CompensatoryDetail compensatoryDetail, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			//验证合同是否被锁定
		   ContractLock contractLock = new ContractLock();
		   contractLock.setContractNo(compensatoryDetail.getContractNo());
		   contractLock = contractLockDao.validateIsLock(contractLock);
		   if (contractLock != null) {
		    return rtn.setFailed().setMessage("此合同号存在正在申请中的操作");
		   }
		   //-----
			CompensatoryAccount compensatoryAccount = compensatoryAccountService.get(id);
			compensatoryDetail.setCustId(compensatoryAccount.getCustId());
			compensatoryDetail.setCompensatoryAccount(compensatoryAccount.getCompensatoryAccount());
			String serialNum = OutInterfaceUtils.makeSeqNo();
			compensatoryDetail.setSerialNum(serialNum);
			//获得该期实还金额
			BigDecimal staRepayPlanStatus = staRepayPlanStatusService.getFactMoneyByContractAndPeriodNum(compensatoryDetail.getContractNo(),compensatoryDetail.getPeriodNum());
			AccAccountDCResponse accAccountDCResponse = compensatoryDetailService.repaymentDC(compensatoryDetail,"",staRepayPlanStatus);
			//String flag = AccFacade.facade.repaymentDC(compensatoryDetail);
			if (staRepayPlanStatus.compareTo(BigDecimal.ZERO) == 1) {
				if(accAccountDCResponse!=null&&"0000".equals(accAccountDCResponse.getResp_code())) {
					rtn.setSuccess().setMessage("代偿处理中");
				}else {
					String msg="";
					if(accAccountDCResponse!=null&&accAccountDCResponse.getResp_msg()!=null) {
						msg=accAccountDCResponse.getResp_msg();
					}
					rtn.setFailed().setMessage("代偿失败"+msg);
				}
			}else{
				SendGETResponse sendGETResponse=compensatoryDetailService.sendGET(compensatoryDetail);
				if(sendGETResponse!=null&&sendGETResponse.getResult().contains("success")) {
					rtn.setSuccess().setMessage("代偿处理中,成功通知冠E通!");
				}else {
					String msgGet="";
					if(sendGETResponse!=null&&sendGETResponse.getMsg()!=null) {
						msgGet=sendGETResponse.getMsg();
					}
					rtn.setFailed().setMessage("代偿成功,通知冠E通失败"+msgGet+"!");
				}
			}
			
		} catch (Exception e) {
			logger.error("代偿失败！", e);
			rtn.setFailed().setMessage("代偿失败!");
		}
		return rtn;
	}
	
	@RequestMapping(value = "saveDetail")
	@ResponseBody
	public AjaxView saveDetail(CompensatoryDetail compensatoryDetail, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		
		try {
			//验证合同是否被锁定
		   ContractLock contractLock = new ContractLock();
		   contractLock.setContractNo(compensatoryDetail.getContractNo());
		   contractLock = contractLockDao.validateIsLock(contractLock);
		   String compensatoryType = compensatoryDetail.getCompensatoryType();
		   String serialNum = OutInterfaceUtils.makeSeqNo();

		   if (contractLock != null) {
		    return rtn.setFailed().setMessage("此合同号存在正在申请中的操作");
		   }
		   //-----
			compensatoryDetail.setSerialNum(OutInterfaceUtils.makeSeqNo());
			BigDecimal staRepayPlanStatus = staRepayPlanStatusService.getFactMoneyByContractAndPeriodNum(compensatoryDetail.getContractNo(),compensatoryDetail.getPeriodNum());
			BigDecimal aBigDecimal = new BigDecimal("8.00");

			//String flag = AccFacade.facade.repaymentDC(compensatoryDetail);
			AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
			int i = aBigDecimal.compareTo(BigDecimal.ZERO);
			if (staRepayPlanStatus.compareTo(BigDecimal.ZERO) == 1) {
				accAccountDCResponse = compensatoryDetailService.repaymentDC(compensatoryDetail,compensatoryType,staRepayPlanStatus);
				if(accAccountDCResponse!=null&&"0000".equals(accAccountDCResponse.getResp_code())) {
					rtn.setSuccess().setMessage("代偿处理中");
				}else {
					String msg="";
					if(accAccountDCResponse!=null&&accAccountDCResponse.getResp_msg()!=null) {
						msg=accAccountDCResponse.getResp_msg();
					}
					rtn.setFailed().setMessage("代偿失败"+msg);
				}
			}else{
				Map<String,String> param = new HashMap<>();
				param.put("contractNo",compensatoryDetail.getContractNo());
				param.put("seqNo",serialNum);
				param.put("custId", accAccountDCResponse.getCust_Id());
				String repayMent = JsonTransferUtils.bean2Json(param);
				SendGETResponse sendGETResponse=compensatoryDetailService.sendGET(compensatoryDetail);
				if(sendGETResponse!=null&&sendGETResponse.getResult().contains("success")) {
					if("1".equals(compensatoryType)) {//担保人代偿  入账
						transPushMoney(compensatoryDetail,serialNum);
						producerService.pushMoney(repayMent);
						rtn.setSuccess().setMessage("代偿成功,成功通知冠E通,请稍后查看入账结果!");
					}else {
						// 担保公司 和 平台资金
						rtn.setSuccess().setMessage("代偿成功,成功通知冠E通!");
					}
				}else {
					String msgGet="";
					if(sendGETResponse!=null&&sendGETResponse.getMsg()!=null) {
						msgGet=sendGETResponse.getMsg();
					}
					if("1".equals(compensatoryType)) {//担保人代偿  入账
						transPushMoney(compensatoryDetail,serialNum);
						producerService.pushMoney(repayMent);
						rtn.setFailed().setMessage("代偿成功,担保人代偿正在入账,通知冠E通失败"+msgGet+"!");
					}else {
						rtn.setFailed().setMessage("代偿成功,通知冠E通失败"+msgGet+"!");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("代偿失败！", e);
			rtn.setFailed().setMessage("代偿失败!");
		}
		return rtn;
	}

	private void transPushMoney(CompensatoryDetail compensatoryDetail,String serialNum) {
		compensatoryDetailService.insertDeductTable(compensatoryDetail,serialNum);
	}

}
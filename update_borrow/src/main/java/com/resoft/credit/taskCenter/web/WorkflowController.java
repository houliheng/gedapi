package com.resoft.credit.taskCenter.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.resoft.credit.underConclusion.service.UnderConclusionService;
import com.thinkgem.jeesite.common.web.BaseController;
//

@Controller
@RequestMapping(value = "${adminPath}/credit/workflow")
public class WorkflowController extends BaseController {
	
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private UnderConclusionService underConclusionService;
	@Autowired
	private TaskCenterService taskCenterService;



	@RequestMapping("taskTodo")
	public String todoTest(ActTaskParam actTaskParam, Model model) {
		return "app/credit/workflow/actTaskTodoList";
	}
	
	// 股权信息进调页
	@RequestMapping("stockRevice")
	public String stockRevice(ActTaskParam actTaskParam, Model model,
			HttpServletRequest request,String stockTaskReceiveId) {
		String stockInfoId = request.getParameter("stockInfoId");
		String isDone = request.getParameter("reviceStatus");
		if(!"1".equals(isDone)){//不是完成
			isDone = "0";
		}
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("isDone", isDone);
		model.addAttribute("stockInfoId",stockInfoId);
		model.addAttribute("stockTaskReceiveId",stockTaskReceiveId);
		return "app/credit/workflow/stockReviceIndex";
	}	

	// 申请录入
	@RequestMapping("loanApply")
	public String loanApply(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
        	model.addAttribute("flag","100"); // 这是联合授信产品标记位
		} else {
			model.addAttribute("flag","50"); // 这是非授信产品标记位
		}		return "app/credit/workflow/loanApplyIndex";
	}
	///credit/workflow/checkContract
	//线下借款合同审核
	@RequestMapping("checkContract")
	public String checkContract(ActTaskParam actTaskParam, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/credit/workflow/underRegisterIndex";
	}

	///credit/workflow/pushContract
	//线下借款推送标的
	@RequestMapping("pushContract")
	public String pushContract(ActTaskParam actTaskParam, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/credit/workflow/underRegisterIndex";
	}


	//线下借款录入
	@RequestMapping("areaRegister")
	public String areaRegister(ActTaskParam actTaskParam, Model model) {
		if(actTaskParam==null||actTaskParam.getApplyNo()==null){
			String procInstId = underConclusionService.startNewProcess();
			HashMap<String, String> taskMap = taskCenterService.findUnderLoanFirstDate(procInstId);
			actTaskParam.setApplyNo(taskMap.get("applyNo"));
			actTaskParam.setProcInstId(procInstId);
			actTaskParam.setProcDefId(taskMap.get("PROCESSDEFINITIONID"));
			actTaskParam.setTaskDefKey(taskMap.get("TASKDEFINITIONKEY"));
			actTaskParam.setExecId(taskMap.get("EXECUTIONID"));
			actTaskParam.setTitle(taskMap.get("NAME"));
			actTaskParam.setTaskId(taskMap.get("ID"));
			actTaskParam.setHeadUrl("/credit/taskCenter/underLoanList/" + taskMap.get("TASKDEFINITIONKEY"));
			actTaskParam.setStatus("0");//未办理0已办理1
			
			ApplyRegister applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(taskMap.get("applyNo"));
			applyRegister.setNewOld("0");
			applyRegisterService.save(applyRegister);
		}
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/credit/workflow/underRegisterIndex";
	}

	// 借前外访
	@RequestMapping("beforeBorrowingVisit")
	public String beforeBorrowingVisit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
		String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
		if("gqcc_loan_union".equals(procDefKey)){
			model.addAttribute("flag","100"); // 这是联合授信产品标记位
		} else {
			model.addAttribute("flag","50"); // 这是非授信产品标记位
		}

		return "app/credit/workflow/beforeBorrowingVisitIndex";
	}

	// 面审
	@RequestMapping("checkFace")
	public String checkFace(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}

        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/checkFaceIndex";
	}

	// 两人外访
	@RequestMapping("checkCoupleDoubt")
	public String checkCoupleDoubt(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/checkCoupleDoubtIndex";
	}

	// 电话核查
	@RequestMapping("checkPhone")
	public String checkPhone(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/checkPhoneIndex";
	}

	// 网查
	@RequestMapping("checkWeb")
	public String checkWeb(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/checkWebIndex";
	}

	// 外访费登记
	@RequestMapping("checkFee")
	public String checkFee(ActTaskParam actTaskParam, Model model) {
		return "app/credit/workflow/checkFeeIndex";
	}

	// 分公司经理审核
	@RequestMapping("branchCompanyAudit")
	public String branchCompanyAudit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }

		return "app/credit/workflow/branchCompanyAuditIndex";
	}

	// 分公司风控审核
	@RequestMapping("branchCompanyRiskAudit")
	public String branchCompanyRiskAudit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/branchCompanyRiskAuditIndex";
	}

	// 分公司复议
	@RequestMapping("branchReview")
	public String branchReview(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/branchReviewIndex";
	}

	// 区域审核
	@RequestMapping("regionAudit")
	public String regionAudit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/regionAuditIndex";
	}

	// 大区审核
	@RequestMapping("largeRegionAudit")
	public String largeRegionAudit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
		String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
        	model.addAttribute("flag","100"); // 这是联合授信产品标记位
		} else {
			model.addAttribute("flag","50"); // 这是非授信产品标记位
		}
		return "app/credit/workflow/regionAuditIndex";
	}

	// 总公司风控审核
	@RequestMapping("parentCompanyAudit")
	public String parentCompanyAudit(ActTaskParam actTaskParam, Model model) {
		if(applyRegisterService.checkIsStockWebCheck(actTaskParam.getApplyNo())){
			model.addAttribute("showStatus", "1");
		}
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/regionAuditIndex";
	}

	// 合同预约
	@RequestMapping("contractAppoint")
	public String contractAppoint(ActTaskParam actTaskParam, Model model) {
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }

		return "app/credit/workflow/contractAppointIndex";
	}

	// 取消审批
	@RequestMapping("cancelAppoint")
	public String cancelAppoint(ActTaskParam actTaskParam, Model model) {
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/cancelAppointIndex";
	}

	// 合同面签/法务审核/合同审核
	@RequestMapping("contractFaceSign")
	public String contractFaceSign(ActTaskParam actTaskParam, Model model) {
		return "app/credit/workflow/contractFaceSignIndex";
	}

	// 财务放款
	@RequestMapping("financeLoan")
	public String financeLoan(ActTaskParam actTaskParam, Model model) {
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/financeLoanIndex";
	}

	// 外访费返还
	@RequestMapping("checkFeeReturnForm")
	public String checkFeeReturnForm(String checkFeeId, String readOnly, Model model, String applyNo) {
		model.addAttribute("checkFeeId", checkFeeId);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/workflow/checkFeeReturnIndex";
	}
	

	// 分公司授信
	@RequestMapping("unionBranchCompanyCreditIndex")
	public String unionBranchCompanyCreditIndex(ActTaskParam actTaskParam, Model model) {
		return "app/credit/workflow/unionBranchCompanyCreditIndex";
	}
	
	// 总公司授信
	@RequestMapping("unionRegionAuditIndex")
	public String unionRegionAuditIndex(ActTaskParam actTaskParam, Model model) {
        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }

		return "app/credit/workflow/unionRegionAuditIndex";
	}
	
	// 授信详情
	@RequestMapping("unionDetailIndex")
	public String unionDetailIndex(ActTaskParam actTaskParam, Model model,String approId) {

        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		model.addAttribute("approId", approId);
		return "app/credit/workflow/unionDetailIndex";
	}
	// 财务放款详情
	@RequestMapping("applyLoanDetailIndex")
	public String applyLoanDetailIndex(ActTaskParam actTaskParam, Model model,String approId) {

        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		model.addAttribute("approId", approId);
		return "app/credit/workflow/unionDetailIndex";
	}
	// 合同预约
	@RequestMapping("unionContractAppoint")
	public String unionContractAppoint(ActTaskParam actTaskParam, Model model) {

        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }

		return "app/credit/workflow/unionContractAppointIndex";
	}
	
	// 合同面签/合同审核 授信
	@RequestMapping("unionContractFaceSign")
	public String unionContractFaceSign(ActTaskParam actTaskParam, Model model) {

        String procDefKey = applyRegisterService.queryRegisterByApplyNo(actTaskParam.getApplyNo());
        if("gqcc_loan_union".equals(procDefKey)){
            model.addAttribute("flag","100"); // 这是联合授信产品标记位
        } else {
            model.addAttribute("flag","50"); // 这是非授信产品标记位
        }
		return "app/credit/workflow/unionContractFaceSignIndex";
	}

	// 财务放款 授信
	@RequestMapping("unionFinanceLoan") 
	public String unionFinanceLoan(ActTaskParam actTaskParam, Model model) {
		if (actTaskParam.getTaskDefKey().equals(Constants.UTASK_CWFK)){
		System.out.println(actTaskParam.getTaskDefKey());
		return "app/credit/workflow/financeLoanIndexNew";
		}else{
		System.out.println(actTaskParam.getTaskDefKey());
		return "app/credit/workflow/unionFinanceLoanIndex";
		}
	}
	// 财务放款详情
		@RequestMapping("contractAuditForm")
		public String contractAuditForm(ActTaskParam actTaskParam, Model model,String approId,String custId) {
			model.addAttribute("approId", approId);
			model.addAttribute("custId", custId);
			return "app/credit/workflow/applyLoanDetailIndex";
		}
}

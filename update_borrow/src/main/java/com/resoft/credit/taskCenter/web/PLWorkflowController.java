package com.resoft.credit.taskCenter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegisterVO;
import com.resoft.credit.videoDir.entity.VideoDir;
import com.resoft.credit.videoDir.service.VideoUploadService;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "/f/credit/PLworkflow")
public class PLWorkflowController extends BaseController {
	@Autowired
	private VideoUploadService videoUploadService;
	
	// 25日复核详情页面
	@RequestMapping("parentCompanyAudit")
	public String parentCompanyAudit(ActTaskParam actTaskParam, Model model) {
		actTaskParam.setStatus("1");
		model.addAttribute("PLFlag", "disappear");
		return "app/credit/workflow/regionAuditIndex";
	}

	// cre借款信息页面
	@RequestMapping("toCreLoanApplyPage")
	public String toCreLoanApplyPage(ActTaskParam actTaskParam, Model model, boolean readOnly) {
		actTaskParam.setStatus("1");
		return "redirect:" + adminPath + "/credit/applyInfo/loanApplyInfoForm?readOnly=" + readOnly + "&applyNo=" + actTaskParam.getApplyNo() + "&status=" + actTaskParam.getStatus();
	}

	// cre客户信息页面
	@RequestMapping("toCreCustInfoPage")
	public String toCreCustInfoPage(ActTaskParam actTaskParam, Model model, boolean readOnly) {
		actTaskParam.setStatus("1");
		return "redirect:" + adminPath + "/custinfo/custInfoIndex/index?readOnly=" + readOnly + "&applyNo=" + actTaskParam.getApplyNo() + "&status=" + actTaskParam.getStatus();
	}

	// cre担保信息页面
	@RequestMapping("toCreGuarantorInfoPage")
	public String toCreGuarantorInfoPage(ActTaskParam actTaskParam, Model model, boolean readOnly) {
		actTaskParam.setStatus("1");
		return "redirect:" + adminPath + "/credit/guarantorInfoIndex/index?readOnly=" + readOnly + "&applyNo=" + actTaskParam.getApplyNo() + "&status=" + actTaskParam.getStatus();
	}

	// cre抵质押物信息页面
	@RequestMapping("toCreMortgageCarInfoPage")
	public String toCreMortgageCarInfoPage(ActTaskParam actTaskParam, Model model, boolean readOnly) {
		actTaskParam.setStatus("1");
		return "redirect:" + adminPath + "/credit/mortgageCarInfo/mortgage?readOnly=" + readOnly + "&applyNo=" + actTaskParam.getApplyNo() + "&status=" + actTaskParam.getStatus();
	}
	
	//影像上传
	@RequestMapping(value = "videoUpload")
	public String videoUpload(ActTaskParam actTaskParam, VideoDir videoDir, Model model, HttpServletRequest request){
		videoDir.setType("1");
		model.addAttribute("applyNo", actTaskParam.getApplyNo());
		model.addAttribute("taskDefKey", actTaskParam.getTaskDefKey());
		model.addAttribute("list", videoUploadService.findList(videoDir));
		model.addAttribute("videoDir", videoDir);
		model.addAttribute("id", "11");
		return "app/credit/videoDir/videoUploadIndex";
	}
	
	//影像查阅
	@RequestMapping(value = "videoView")
	public String videoView(ActTaskParam actTaskParam, ApplyRegisterVO ApplyRegisterVO, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("applyRegisterVO", ApplyRegisterVO);
		model.addAttribute("applyNo", actTaskParam.getApplyNo());
		model.addAttribute("taskDefKey",actTaskParam.getTaskDefKey());
		model.addAttribute("status",actTaskParam.getStatus());
		return "app/credit/comprehensiveQuery/treeJsp";
	}
}

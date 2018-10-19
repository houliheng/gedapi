package com.resoft.credit.loanApply.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.blacklist.service.BlacklistService;
import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.loanApply.entity.CreCustContact;
import com.resoft.credit.loanApply.service.CreApplyInfoService;
import com.resoft.credit.loanApply.service.CreApplyRegisterService;
import com.resoft.credit.loanApply.service.CreCustContactService;
import com.resoft.credit.taskCenter.entity.ActHiComment;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActProcessService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * @reqno:H1510130147
 * @date-designer:2015年10月27日-songmin
 * @date-author:2015年10月27日-songmin:CRE_信贷审批_申请录入_录入结论
 * 		信贷审批_申请录入_结论信息保存Action
 */
@Controller
@RequestMapping("${adminPath}/credit/loanApply/result")
public class LoanApplyResultController extends BaseController{
	@Autowired
	private CreApplyInfoService  creApplyInfoService;//贷款申请信息表service
	@Autowired
	private CreCustContactService creCustContactService;//联系人列表service
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private CreApplyRegisterService creApplyRegisterService;
	@Autowired
	private BlacklistService blacklistService;
	/**
	 * @reqno:H1510130147
	 * @date-designer:2015年10月27日-songmin
	 * @date-author:2015年10月27日-songmin:CRE_信贷审批_申请录入_录入结论
	 * 		跳转结论信息录入界面
	 */
	@RequiresPermissions("credit:loanApply:edit")
	@RequestMapping("/forward")
	public String forward(ActTaskParam actTaskParam,Model model){
		model.addAttribute("actTaskParam", actTaskParam); 
		return "/app/credit/loanApply/result_input";
	}
	
	@RequiresPermissions("credit:loanApply:edit")
	@RequestMapping("/save")
	public String save(ActTaskParam actTaskParam,String passFlag,String sugession,
			String blackNameDesc,Model model,HttpServletRequest request,HttpServletResponse response){
		if("yes".equals(passFlag)){//提交
			//校验贷款申请信息字段
			CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
			if(null==creApplyInfo){
				creApplyInfo = new CreApplyInfo();//spring的标签必须要对象绑定，这里需要确保对象不能为null
			}
			if(StringUtils.isEmpty(creApplyInfo.getApplyProductName()) ||
					(null!=creApplyInfo.getApplyYearRate() && StringUtils.isEmpty(creApplyInfo.getApplyYearRate().toString()))||
					StringUtils.isEmpty(creApplyInfo.getApplyPeriodValue())||
					(null!=creApplyInfo.getApplyAmount() && StringUtils.isEmpty(creApplyInfo.getApplyAmount().toString()))||
					(null!=creApplyInfo.getAcceptHighestAmount() && StringUtils.isEmpty(creApplyInfo.getAcceptHighestAmount().toString()))||
					StringUtils.isEmpty(creApplyInfo.getLoanRepayType())||
					StringUtils.isEmpty(creApplyInfo.getLoanPurpose())){
				return super.renderString(response, "401");
			}
			//个人客户进行联系人的校验
			CreApplyRegister applyRegister = creApplyRegisterService.findApplyRegisterInfoById(actTaskParam.getApplyNo());
			if(StringUtils.isNotEmpty(applyRegister.getCustType()) && "1".equals(applyRegister.getCustType())){
				CreCustContact ccc=new CreCustContact();
				ccc.setApplyNo(actTaskParam.getApplyNo());
				List<CreCustContact> linkerList=new ArrayList<CreCustContact>();
				linkerList=creCustContactService.findList(ccc);
				if(null==linkerList || linkerList.size()<1){
					return super.renderString(response, "402");
				}
			}
			
			Map<String, Object> vars = Maps.newHashMap();
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),  "【提交】"+sugession,"提交", vars);
		}else{//废弃+废弃并加入黑名单
			/**
			 * @reqno:H1511100047
			 * @date-designer:20151117-huangxuecheng
			 * @date-author:20151117-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.开发说明：废弃时插入comment					        
			 */
			ActHiComment actHiComment = this.paramBinderActHiComemnt(actTaskParam ,sugession);
			creApplyRegisterService.addDepositComment(actHiComment);
			actProcessService.deleteProcIns(actTaskParam.getProcInstId(), "completed");
			/**
			 * @reqno:H1511090123
			 * @date-designer:20151114-chenshaojia
			 * @date-author:20151114-chenshaojia:CRE_信贷审批_申请录入_录入结论功能优化，当录入结论选择废弃，提交成功后，后台更新需更新【客户申请信息表】中的【客户申请状态】字段为拒绝（字典表值：3），效果是废弃后，在个人客户登记页面的列表中，状态变为：拒绝
			 * @db-z : CRE_APPLY_REGISTER: status 客户申请状态
			 */
			CreApplyRegister creApplyRegister = new CreApplyRegister();
			creApplyRegister.setId(actTaskParam.getApplyNo());
			creApplyRegister.setStatus("3");//拒绝
			creApplyRegisterService.updateStatus(creApplyRegister);
			/**
			 * @reqno:H1512210026
			 * @date-designer:2016年1月4日-songmin
			 * @date-author:2016年1月4日-songmin:CRE_信贷审批_个人_申请录入_录入结论_录入结论添加“废弃并加入黑名单”功能
			 * 当操作人员选择废弃并加入黑名单时，追加加入黑名单那操作
			 */
			if("radio_no_black".equals(passFlag)){
				/**
				 * @reqno: H1512210033
				 * @date-designer:2015年12月24日-lirongchao
				 * @date-author:2015年01月07日-lirongchao:修改设置说明的字段名称
				 */						
				blacklistService.joinBalckAndDetails(actTaskParam.getApplyNo(),blackNameDesc);
			}
		}
		return super.renderString(response, "200");
	}
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.开发说明：绑定comment参数				        
	 */
	public ActHiComment paramBinderActHiComemnt(ActTaskParam actTaskParam,String sugession) {
		String name = UserUtils.getUser().getLoginName();
		ActHiComment actHiComment = new ActHiComment();
		actHiComment.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		actHiComment.setType("comment");
		actHiComment.setTime(new Date());
		actHiComment.setUserId(name);
		actHiComment.setTaskId(actTaskParam.getTaskId());
		actHiComment.setProcInstId(actTaskParam.getProcInstId());
		actHiComment.setAction("AddComment");
		sugession = "【废弃】" + sugession;
		actHiComment.setMessage(sugession);
		actHiComment.setFullMsg(sugession);
		return actHiComment;
	}
}

package com.resoft.credit.fhRiskControl.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.fdfs.Manager;
import com.resoft.credit.fdfs.util.FileUtils;
import com.resoft.credit.fhRiskControl.entity.FhRiskControl;
import com.resoft.credit.fhRiskControl.service.FhRiskControlService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 法海风控信息Controller
 * 
 * @author wangguodong
 * @version 2017-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/fhRiskControl")
public class FhRiskControlController extends BaseController {

	@Autowired
	private FhRiskControlService fhRiskControlService;

	@ModelAttribute
	public FhRiskControl get(@RequestParam(required = false) String id) {
		FhRiskControl entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = fhRiskControlService.get(id);
		}
		if (entity == null) {
			entity = new FhRiskControl();
		}
		return entity;
	}

	@RequiresPermissions("credit:fhRiskControl:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, FhRiskControl fhRiskControl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FhRiskControl> page = fhRiskControlService.findPage(new Page<FhRiskControl>(request, response), fhRiskControl);
		model.addAttribute("page", page);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("yxUrl", Global.getConfig("YX_ADDRESS"));
		return "app/credit/fhRiskControl/fhRiskControlList";
	}

	@RequiresPermissions("credit:fhRiskControl:view")
	@RequestMapping(value = "form")
	public String form(FhRiskControl fhRiskControl, HttpServletResponse response, Model model) {
		OutputStream out = null;
		String filePath = "group1/M00/00/00/wKgI7FitMd6AQbajAAB-qM66Ovk442.pdf";
		try {
			byte[] b = Manager.download(filePath);
			out = response.getOutputStream();
			out.write(b);
		} catch (Exception e) {
			logger.error("显示图片Div失败", e);
		} finally {
			try {
				FileUtils.closeIOSource(out);
			} catch (IOException e) {
				logger.error("关闭IO资源失败", e);
			}
		}
		model.addAttribute("fhRiskControl", fhRiskControl);
		return "app/credit/fhRiskControl/fhRiskControlForm";
	}

	// 验证法海相关人预查次数
	@RequestMapping(value = "verifyFhCheckNumAjax")
	@ResponseBody
	public AjaxView verifyFhCheckNumAjax(String applyNo, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
		ajaxView.setSuccess();
		if(StringUtils.isNotEmpty(applyNo)){
			List<FhRiskControl> list = fhRiskControlService.findRiskControlYcByApplyNo(applyNo);
			for(FhRiskControl fh : list){
				/*if(fh==null || fh.getCheckNum()==null || Integer.parseInt(fh.getCheckNum()) == 0){
					ajaxView.setFailed();
					ajaxView.setMessage("相关人员必须要至少预查一次风控信息！");
					break;
				}*/
			}
		}else{
			ajaxView.setFailed();
			ajaxView.setMessage("申请编号不能为空！");
		}
		
		return ajaxView;
	}
	
	// 预查
	@RequiresPermissions("credit:fhRiskControl:edit")
	@RequestMapping(value = "queryData")
	@ResponseBody
	public AjaxView queryData(FhRiskControl fhRiskControl, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> msg = Maps.newHashMap();
		// 调用接口
		String custName = null;
		try {
			custName = URLDecoder.decode(fhRiskControl.getCustName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		msg = Facade.facade.fhRiskControl(DictUtils.getDictValue("风控授权码", "FH_LICENSE_KEY", null), fhRiskControl.getApplyNo(), fhRiskControl.getCustId(), custName, fhRiskControl.getIdNum());
		if ("s".equals(msg.get("code"))) {
			List<FhRiskControl> fhRiskControls = fhRiskControlService.findFhRiskControlReportByApplyNoAndCustId(fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
			if (StringUtils.isNull(fhRiskControls) || fhRiskControls.size() == 0) {
				fhRiskControl.setReportStatus(Constants.FH_REPORT_STATUS_WSC);
				fhRiskControl.preInsert();
				fhRiskControlService.savefhRiskControlReport(fhRiskControl);
			}
			ajaxView.setSuccess().setMessage("预查成功");
		} else if ("e".equals(msg.get("code"))) {
			ajaxView.setSuccess().setMessage("预查失败，失败原因：" + msg.get("msg"));
		} else {
			ajaxView.setFailed().setMessage(msg.get("msg").toString());
		}
		return ajaxView;
	}

	// 生成报告
	@RequiresPermissions("credit:fhRiskControl:edit")
	@RequestMapping(value = "generateReport")
	@ResponseBody
	public AjaxView generateReport(FhRiskControl fhRiskControl) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> msg = Maps.newHashMap();
		// 调用接口
		String custName = null;
		try {
			custName = URLDecoder.decode(fhRiskControl.getCustName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		fhRiskControlService.updateReportStatusAndFilePathById(null, null, null, Constants.FH_REPORT_STATUS_SCZ, fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
		msg = Facade.facade.fhRiskControlReport(DictUtils.getDictValue("风控授权码", "FH_LICENSE_KEY", null), fhRiskControl.getApplyNo(), fhRiskControl.getCustId(), custName, fhRiskControl.getIdNum());
		if ("s".equals(msg.get("code"))) {
			ajaxView.setSuccess().setMessage("请求成功");
		} else if ("e".equals(msg.get("code"))) {
			ajaxView.setSuccess().setMessage("请求失败，失败原因：" + msg.get("msg"));
			List<FhRiskControl> fhs = fhRiskControlService.findFhRiskControlReportByApplyNoAndCustId(fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
			if(fhs.size()>0){
				if(Constants.FH_REPORT_STATUS_SCZ.equals(fhs.get(0).getReportStatus())){
					fhRiskControlService.updateReportStatusAndFilePathById(null, null, null, Constants.FH_REPORT_STATUS_WSC, fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
				}
			}
		} else {
			ajaxView.setFailed().setMessage(msg.get("msg").toString());
			List<FhRiskControl> fhs = fhRiskControlService.findFhRiskControlReportByApplyNoAndCustId(fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
			if(fhs.size()>0){
				if(Constants.FH_REPORT_STATUS_SCZ.equals(fhs.get(0).getReportStatus())){
					fhRiskControlService.updateReportStatusAndFilePathById(null, null, null, Constants.FH_REPORT_STATUS_WSC, fhRiskControl.getApplyNo(), fhRiskControl.getCustId());
				}
			}
		}
		return ajaxView;
	}
	
	public static void main(String[] args) {
		System.out.println("----");
		System.out.println(Integer.parseInt("2"));
	}
}
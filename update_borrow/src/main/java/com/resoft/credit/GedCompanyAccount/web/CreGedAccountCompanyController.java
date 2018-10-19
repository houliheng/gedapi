package com.resoft.credit.GedCompanyAccount.web;

import com.alibaba.fastjson.JSONObject;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.SendEmailUtil;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.companyHistory.service.AccCompanyHistoryService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 冠E贷企业账户信息Controller
 * @author gsh
 * @version 2018-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creGedAccountCompany")
public class CreGedAccountCompanyController extends BaseController {

	@Autowired
	private CreGedAccountCompanyService creGedAccountCompanyService;
	@Autowired
	private AccCompanyHistoryService accCompanyHistoryService;

	private static String fdsUrl;
	
	@ModelAttribute
	public CreGedAccountCompany get(@RequestParam(required=false) String id) {
		CreGedAccountCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creGedAccountCompanyService.get(id);
		}
		if (entity == null){
			entity = new CreGedAccountCompany();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreGedAccountCompany creGedAccountCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreGedAccountCompany> page = creGedAccountCompanyService.findCustomPage(new Page<CreGedAccountCompany>(request, response), creGedAccountCompany); 
		model.addAttribute("page", page);
		return "app/credit/GedCompanyAccount/creGedAccountCompanyList";
	}

	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = "form")
	public String form(CreGedAccountCompany creGedAccountCompany, Model model) {
		model.addAttribute("creGedAccountCompany", creGedAccountCompany);
		return "app/credit/GedCompanyAccount/creGedAccountCompanyForm";
	}

	@RequiresPermissions("credit:creGedAccountCompany:edit")
	@RequestMapping(value = "save")
	public String save(CreGedAccountCompany creGedAccountCompany, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creGedAccountCompany)){
			return form(creGedAccountCompany, model);
		}
		creGedAccountCompanyService.save(creGedAccountCompany);
		addMessage(redirectAttributes, "保存冠E贷企业账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/GedCompanyAccount/creGedAccountCompany/?repage";
	}
	
	@RequiresPermissions("credit:creGedAccountCompany:edit")
	@RequestMapping(value = "delete")
	public String delete(CreGedAccountCompany creGedAccountCompany, RedirectAttributes redirectAttributes) {
		creGedAccountCompanyService.delete(creGedAccountCompany);
		addMessage(redirectAttributes, "删除冠E贷企业账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/GedCompanyAccount/creGedAccountCompany/?repage";
	}


	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = "enterPriseAccount")
	public String enterPriseAccount(CreGedAccountCompany creGedAccountCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("========="+creGedAccountCompany.getCompanyName()+"----------------");
		Page<MyMap> page = creGedAccountCompanyService.findAccountCompanyList(new Page<MyMap>(request, response), setParamMap(creGedAccountCompany));
		model.addAttribute("page", page);
		return "app/credit/creGedAccountCompany/enterpriseAccountList";
	}

	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = "accountDoneBusiness")
	public String accountDoneBusiness(CreGedAccountCompany creGedAccountCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MyMap> page= creGedAccountCompanyService.findAccountCompanyDoneList(new Page<MyMap>(request, response), setParamMap(creGedAccountCompany));
		model.addAttribute("page", page);
		return "app/credit/creGedAccountCompany/businessAccountDoneList";
	}

	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = "retreat")
	public String retreat(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		model.addAttribute("id", id);
		model.addAttribute("value",date);
		return "app/credit/creGedAccountCompany/retreat";
	}

	@ResponseBody
	@RequestMapping(value = "updateSaveAccount")
	public AjaxView updateSaveAccount(String id,String applyDate,String repeatReason,String status) {
		AjaxView ajaxView = new AjaxView();
		CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.selectCompanyAccountById(id);
		logger.info(id+"==========="+status);
		try {
			creGedAccountCompanyService.updateSaveAccount(status,id,repeatReason);
			//同时插入历史记录表
            accCompanyHistoryService.save(id,10,"退回",repeatReason);
            //同时回调冠E贷状态
            Facade.facade.repeatAccount(creGedAccountCompany.getSocialCreditCode(),repeatReason,"10",String.valueOf(creGedAccountCompany.getUserId()));
			ajaxView.setSuccess();
			ajaxView.setSuccess().setMessage("成功");
		} catch (Exception e) {
			logger.error("退回失败。", e);
			ajaxView.setFailed().setMessage("退回失败。");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequestMapping(value = "sendEmai")
	public AjaxView sendEmai(String id,HttpServletRequest request) {
		ClassLoader loader=CreGedAccountCompanyController.class.getClassLoader();
		logger.info("loader==="+loader);
        URL resource = CreGedAccountCompanyController.class.getClassLoader().getResource("");
		String contextPath=request.getContextPath();
		logger.info("contextPath="+contextPath+"=======");
       // String url=CreGedAccountCompanyController.class.getClassLoader().getResource("").getPath()+"static/common/filelook/images/guane.jpg";
		String url=contextPath+"/static/common/filelook/images/guane.jpg";
		logger.info("url===="+url);
		AjaxView ajaxView = new AjaxView();
        Properties prop = new Properties();
        String to="";
		Properties emailProperti = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("fdfsPDF_client.conf");
			emailProperti = PropertiesLoaderUtils.loadAllProperties("email.properties");
            fdsUrl=(String)prop.get("fdfs_server_port");
            to=(String)emailProperti.get("email.to");
        } catch (IOException e) {
            System.out.println("加载属性文件失败");
        }
        CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.selectCompanyAccountById(id);
		try {
			//to  收件人   subject 主题   content  内容  	fileStr 附件路径
			String subject=" 企业开户影像资料--冠群驰骋投资管理（北京）有限公司";
			String fileStr5="";
			String fileStr4="";
			String fileStr3="";
			String fileStr2="";
			String fileStr="";
			String content= createEmail(creGedAccountCompany).toString();
			if(StringUtils.isNotBlank(creGedAccountCompany.getIdCardFaceUrl())){
				 fileStr=fdsUrl+"/"+creGedAccountCompany.getIdCardFaceUrl();
			}
			if(StringUtils.isNotBlank(creGedAccountCompany.getIdCardBackUrl())){
				 fileStr2=fdsUrl+"/"+creGedAccountCompany.getIdCardBackUrl();
			}
			if(StringUtils.isNotBlank(creGedAccountCompany.getIdCardHoldUrl())){
				 fileStr3=fdsUrl+"/"+creGedAccountCompany.getIdCardHoldUrl();
			}
			if(StringUtils.isNotBlank(creGedAccountCompany.getBusinessLicenceUrl())){
				 fileStr4=fdsUrl+"/"+creGedAccountCompany.getBusinessLicenceUrl();
			}
			if(StringUtils.isNotBlank(creGedAccountCompany.getAccountsPermitsUrl())){
				 fileStr5=fdsUrl+"/"+creGedAccountCompany.getAccountsPermitsUrl();//开户许可证
			}
			SendEmailUtil sendEmail = new SendEmailUtil(to, subject, content, fileStr,fileStr2,fileStr3,fileStr4,fileStr5);
			if(sendEmail.send(url)) {
                //同时插入历史记录表
				logger.info(id+"=========");
                accCompanyHistoryService.save(id,6,"发送","");
				ajaxView.setSuccess();
				ajaxView.setSuccess().setMessage("邮件发送成功");
			}else
				ajaxView.setFailed().setMessage("邮件发送失败。");
		} catch (Exception e) {
			logger.error("邮件发送失败。", e);
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:creGedAccountCompany:view")
	@RequestMapping(value = "detailLook")
	public String detailLook(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
         logger.info("id========"+id);
		Properties prop = new Properties();
		try {
			prop = PropertiesLoaderUtils.loadAllProperties("fdfsPDF_client.conf");
			fdsUrl=(String)prop.get("fdfs_server_port");
		} catch (IOException e) {
			System.out.println("加载属性文件失败");
		}
		logger.info("----------------"+fdsUrl);
		CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.selectCompanyAccountById(id);
		if(creGedAccountCompany!=null){
			model.addAttribute("idCardFaceUrl", fdsUrl+"/"+creGedAccountCompany.getIdCardFaceUrl());
			model.addAttribute("idCardBackUrl", fdsUrl+"/"+creGedAccountCompany.getIdCardBackUrl());
			model.addAttribute("idCardHoldUrl", fdsUrl+"/"+creGedAccountCompany.getIdCardHoldUrl());
			model.addAttribute("businessLicenceUrl", fdsUrl+"/"+creGedAccountCompany.getBusinessLicenceUrl());
			model.addAttribute("accountsPermitsUrl", fdsUrl+"/"+creGedAccountCompany.getAccountsPermitsUrl());
		}
		return "app/credit/creGedAccountCompany/detailLook";
	}


	private MyMap setParamMap(CreGedAccountCompany creGedAccountCompany) {
		MyMap paramMap = new MyMap();
		paramMap.put("companyName",creGedAccountCompany.getCompanyName());
		paramMap.put("status",creGedAccountCompany.getStatus());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(creGedAccountCompany.getCreateTime()!=null)
		paramMap.put("createTime",sdf.format(creGedAccountCompany.getCreateTime()));
		logger.info("参数为="+JSONObject.toJSONString(paramMap));
		return paramMap;
	}

	private static StringBuilder  createEmail(CreGedAccountCompany creGedAccountCompany){
        Properties emailProperti = new Properties();
        String emailMobile="";
        String emailem="";
        try {
            emailProperti = PropertiesLoaderUtils.loadAllProperties("email.properties");
            emailMobile=(String)emailProperti.get("email.mobile");
            emailem=(String)emailProperti.get("email.em");
        } catch (IOException e) {
            System.out.println("加载属性文件失败");
        }
		StringBuilder emailContent = new StringBuilder("<!DOCTYPE html><html><div class=\"FoxDiv20180626182800851334\">\n" +
				"<div><span></span>&nbsp; &nbsp; &nbsp; &nbsp; <span style=\"font-size: 16px;\">尊敬的恒丰银行审核中心，您好：</span></div><div style=\"font-size: 16px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;附件为企业客户开户所需的资料，企业客户的基本信息如下：</div><div style=\"font-size: 16px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<font color=\"#ff0000\">"+"企业名称："+creGedAccountCompany.getCompanyName()+"</font></div><div style=\"font-size: 16px;\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;统一社会信用代码："+creGedAccountCompany.getSocialCreditCode()+"</font></div><div style=\"font-size: 16px;\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"line-height: 1.5; background-color: window;\">企业对公账号："+creGedAccountCompany.getCompanyAccount()+"</span></font></div><div style=\"font-size: 16px;\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;法人姓名：</font><span style=\"color: rgb(255, 0, 0); background-color: window;\">"+creGedAccountCompany.getLegalName()+"</span></div><div style=\"font-size: 16px;\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;法人身份证号码：</font><span style=\"color: rgb(255, 0, 0); background-color: window;\">"+creGedAccountCompany.getLegalCardNumber()+"</span></div><div style=\"font-size: 16px;\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 企业联系人电话：</font><span style=\"color: rgb(255, 0, 0); background-color: window;\">"+creGedAccountCompany.getContactMobile()+"</span></div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</div><div><span style=\"font-size: 16px; line-height: 1.5; background-color: window;\">&nbsp; &nbsp; &nbsp;<span style=\"font-size: 24px;\">如有任何疑问可随时致电我公司相关工作人员，联系电话："+emailMobile+"!"+"</span></span></div>\n" +
				"<div><br></div>&nbsp; &nbsp; <hr style=\"WIDTH: 210px; HEIGHT: 1px\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n" +
				"<div><span><div style=\"margin: 10px; font-family: verdana;\"><div style=\"font-size: 10pt;\"><br></div><div style=\"font-size: 10pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <img src=\"cid:guane.jpg\" border=\"0\" style=\"font-size: 10pt; line-height: 1.5; background-color: window;\"></div><div style=\"font-size: 10pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style=\"font-size: 14px; line-height: 36px; background-color: window;\">冠群驰骋旗下专注企业借款的平台</span></div><div><span style=\"background-color: window; font-size: 19px; line-height: 28px;\">&nbsp; &nbsp; &nbsp;</span><span style=\"font-size: 13px; line-height: 1.5; background-color: window;\">联系邮箱：</span><a href=\"mailto:"+emailem+" style=\"font-size: 13px; line-height: 19px; background-color: window;\" target=\"_blank\">"+emailem+"</a><span style=\"font-size: 13px; line-height: 19px; background-color: window;\">&nbsp;</span></div><div style=\"font-size: 13px;\"><span style=\"background-color: window;\">&nbsp; &nbsp; &nbsp; &nbsp;联系电话："+emailMobile+"</span></div><div style=\"font-size: 13px;\"><span style=\"background-color: window;\">&nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"line-height: 1.5; background-color: window;\">公司名称：</span><span style=\"line-height: 1.5; background-color: window;\">冠群驰骋投资管理（北京）有限公司</span></div><div style=\"font-size: 13px;\">&nbsp; &nbsp; &nbsp; &nbsp;公司地址：<span style=\"color: rgb(51, 51, 51); font-family: 'Lucida Grande', 'Lucida Sans Unicode', sans-serif; line-height: 1.5; background-color: window;\">北京市东城区东直门南大街3号国华投资大厦5层</span></div><div style=\"font-size: 15px;\">&nbsp; &nbsp; </div><span style=\"font-size: 19px;\"></span></div></span></div></body></html>");
		return emailContent;
	}


}
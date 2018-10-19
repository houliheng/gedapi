package com.resoft.credit.custinfo.web;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyInfo.dao.ApplyInfoDao;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 担保人信息索引Controller
 * 
 * @author wuxi01
 * @date 2016-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/guarantorInfoIndex")
public class GuarantorInfoIndexController extends BaseController {
	
	@Autowired
	private ApplyInfoDao applyInfoDao;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private CustWorkInfoService custWorkInfoService;

	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, String readOnly, Model model) {
		// 流程信息
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		// 前台index页
		return "app/credit/guarantorInfo/guarantorInfoIndex";
	}




	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "validateGuarantor")
	public AjaxView validateGuarantor(String applyNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			ApplyInfo applyInfo = applyInfoDao.findApplyInfoByApplyNo(applyNo);
			if(StringUtils.isNull(applyInfo)){
				ajaxView.setFailed().setMessage("请先保存借款申请信息。");
			}else if("0".equals(applyInfo.getIsHaveAssure())){
				ajaxView.setFailed().setMessage("此次申请没有担保人和企业");
			}else{
				ajaxView.setSuccess();
			}
		} catch (Exception e) {
			logger.error("数据出现问题，请联系管理员",e);
		}
		return ajaxView;
	}

	@RequestMapping(value = "underGuarantorInfo")
	public String underGuarantorInfo(ActTaskParam actTaskParam, String readOnly, Model model) {
		// 流程信息
		model.addAttribute("actTaskParam", actTaskParam);
		if (!Constants.TRUE.equals(readOnly) && !Constants.UNDER_DQGLR.equals(actTaskParam.getTaskDefKey())) {
			readOnly = Constants.TRUE;
		}
		model.addAttribute("readOnly", readOnly);
		return "app/credit/guarantorInfo/underGuarantorInfoIndex";
	}
}

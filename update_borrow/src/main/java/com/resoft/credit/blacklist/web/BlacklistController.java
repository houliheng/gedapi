
package com.resoft.credit.blacklist.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.blacklist.entity.Blacklist;
import com.resoft.credit.blacklist.entity.BlacklistDetail;
import com.resoft.credit.blacklist.service.BlacklistService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 黑名单表Controller
 * @author lirongchao
 * @version 2015-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/blacklist/")
public class BlacklistController extends BaseController {

	@Autowired
	private BlacklistService blacklistService;
	
	@ModelAttribute
	public Blacklist get(@RequestParam(required=false) String id) {
		Blacklist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = blacklistService.get(id);
		}
		if (entity == null){
			entity = new Blacklist();
		}
		return entity;
	}
	/**
	 * @reqno: H1512210030
	 * @date-designer:2015年12月24日-lirongchao
	 * @db-z : cre_blacklist : cust_name,id_num,create_date
	 * @db-j : SYS_USER : id
	 * @date-author:2015年12月24日-lirongchao:1.查询条件-【客户名称（模糊查询）、状态】、【证件类型、证件号码（模糊查询）】、【移动电话（模糊查询）、创建日期（开始结束时间）】；
											查询表单按钮-查询、重置；
											2.列表数据项-单选框按钮、客户名称、状态（黑名单、白名单）、证件类型、证件号、移动电话、创建人、创建日期、操作（详情）
											3.列表排序-创建日期降序
											4.表头按钮-加黑、刷白
	 */		
	@RequiresPermissions("credit:blacklist:view")
	@RequestMapping(value = {"list", ""})
	public String list(Blacklist blacklist, HttpServletRequest request, HttpServletResponse response, Model model,String returnVal, RedirectAttributes redirectAttributes) {
		Page<Blacklist> page = blacklistService.findPage(new Page<Blacklist>(request, response), blacklist); 
		model.addAttribute("page", page);
		if("1".equals(returnVal)){
			addMessage(model, "保存成功!");
		}
		return "app/credit/blacklist/blacklistList";
	}
	/**
	 * @reqno: H1512210032
	 * @date-designer:2015年12月24日-lirongchao
	 * @db-z : CRE_BLACKLIST_DETAIL : BLACKLIST_ID
	 * @db-j : SYS_USER : id
	 * @date-author:2015年12月24日-lirongchao:1.点击“个人黑名单管理”页面列表中的“详情”链接，弹出窗体，窗体名称“加黑详情”；
											2.“加黑详情”页面布局：上下布局，依次为：工具栏、加黑详情列表；
											3. 工具栏:关闭按钮，点击按钮，关闭当前窗体；
											4.加黑详情列表数据项：操作人、操作时间、操作类型（加黑、刷白）、设置说明；
											   列表排序：操作时间升序；
											   表格分页显示；
											   鼠标放到【操作说明】列时，以tip显示具体内容，以避免内容过多无法查看；
											   当前环节-获取查询信息并跳转到详情页面
	 */		
	@RequiresPermissions("credit:blacklist:view")
	@RequestMapping(value = "details")
	public String details(BlacklistDetail blacklistDetail, HttpServletRequest request, HttpServletResponse response, Model model,String returnVal, RedirectAttributes redirectAttributes) {
		Page<BlacklistDetail> page = blacklistService.findDetailsPage(new Page<BlacklistDetail>(request, response), blacklistDetail); 
		model.addAttribute("page", page);
		return "app/credit/blacklist/blacklistDetails";
	}
	/**
	 * @reqno: H1512210033
	 * @date-designer:2015年12月24日-lirongchao
	 * @db-z : cre_blacklist : cust_name,id_num,create_date
	 * @db-j : SYS_USER : id
	 * @date-author:2015年12月24日-lirongchao:1.查询条件-【客户名称（模糊查询）、状态】、【证件类型、证件号码（模糊查询）】、【创建日期（开始结束时间）】；
											查询表单按钮-查询、重置；
											2.列表数据项-单选框按钮、客户名称、状态（黑名单、白名单）、证件类型、证件号、创建人、创建日期、操作（详情）
											3.列表排序-创建日期降序
											4.表头按钮-加黑、刷白
	 */	
	@RequiresPermissions("credit:blacklist:view")
	@RequestMapping(value = "business")
	public String business(Blacklist blacklist, HttpServletRequest request, HttpServletResponse response, Model model,String returnVal, RedirectAttributes redirectAttributes) {
		Page<Blacklist> page = blacklistService.findBusinessPage(new Page<Blacklist>(request, response), blacklist); 
		model.addAttribute("page", page);
		if("1".equals(returnVal)){
			addMessage(model, "保存成功!");
		}
		return "app/credit/blacklist/blacklistList";
	}
	/**
	 * @reqno: H1512210031
	 * @date-designer:2015年12月24日-lirongchao
	 * @db-z : cre_blacklist : cust_name,id_num,create_date
	 * @db-j : SYS_USER : id
	 * @date-author:2015年12月24日-lirongchao:   在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
												  当前环节-跳转到黑名单管理加黑(刷白)页面
	 */
	@RequiresPermissions("credit:blacklist:view")
	@RequestMapping(value = "edit")
	public String edit(Blacklist blacklist, Model model) {
		Blacklist  BlacklistInfo =  blacklistService.get(blacklist);
		BlacklistInfo.setListStatus(blacklist.getListStatus());
		model.addAttribute("blacklist", BlacklistInfo);
		return "app/credit/blacklist/blacklistEdit";
	}
	@RequiresPermissions("credit:blacklist:view")
	@RequestMapping(value = "form")
	public String form(Blacklist blacklist, Model model) {
		model.addAttribute("blacklist", blacklist);
		return "app/credit/blacklist/blacklistForm";
	}
	/**
	 * @reqno: H1512210034
	 * @date-designer:2015年12月24日-lirongchao
	 * @db-z : cre_blacklist : cust_name,id_num,create_date
	 * @db-z : CRE_BLACKLIST_DETAIL : BLACKLIST_ID
	 * @date-author:2015年12月24日-lirongchao:   在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
												 当前环节-保存黑名单管理信息,同时在加黑详情表添加一条记录明细
	 */
	@RequiresPermissions("credit:blacklist:edit")
	@ResponseBody
	@RequestMapping(value = "editSave")
	public AjaxView editSave(@RequestBody BlacklistDetail blacklistDetail) {
		AjaxView ajaxView = new AjaxView();
		try {
			blacklistService.revampSave(blacklistDetail);
			ajaxView.setSuccess();
			ajaxView.setMessage("保存成功！");
		} catch (Exception e) {
			logger.error("保存黑名单失败！", e);
			ajaxView.setFailed();
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:blacklist:edit")
	@RequestMapping(value = "delete")
	public String delete(Blacklist blacklist, RedirectAttributes redirectAttributes) {
		blacklistService.delete(blacklist);
		addMessage(redirectAttributes, "删除黑名单表成功");
		return "redirect:"+Global.getAdminPath()+"/credit/blacklist/?repage";
	}

}
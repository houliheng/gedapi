package com.resoft.credit.noEntry.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.noEntry.entity.OrgNoEntry;
import com.resoft.credit.noEntry.service.OrgNoEntryService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

/**
 * 机构禁件表Controller
 * @author lirongchao
 * @version 2016-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/companyNoEntry/")
public class OrgNoEntryController extends BaseController {

	@Autowired
	private OrgNoEntryService companyNoEntryService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public OrgNoEntry get(@RequestParam(required=false) String id) {
		OrgNoEntry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyNoEntryService.get(id);
		}
		if (entity == null){
			entity = new OrgNoEntry();
		}
		return entity;
	}
	/**
	 * @reqno: H1512260021
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z :cre_company_no_entry : org_id
	 * @db-j :sys_office : code,name
	 * @date-author:2016年01月13日-lirongchao:根据查询条件查询机构禁件列表
	 * 										查询条件-机构编码【模糊查询】、机构名称【模糊查询】
	 */
	@RequiresPermissions("companynoentry:companyNoEntry:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrgNoEntry companyNoEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrgNoEntry> page = companyNoEntryService.findPage(new Page<OrgNoEntry>(request, response), companyNoEntry); 
		model.addAttribute("page", page);
		model.addAttribute("companyNoEntry", companyNoEntry);
		return "app/credit/noEntry/companyNoEntryList";
	}

	@RequiresPermissions("companynoentry:companyNoEntry:edit")
	@RequestMapping(value = "save")
	public String save(OrgNoEntry companyNoEntry,String idsArr, Model model, RedirectAttributes redirectAttributes) {
		companyNoEntryService.save(companyNoEntry,idsArr);
		addMessage(redirectAttributes, "保存机构禁件成功");
		return "redirect:"+Global.getAdminPath()+"/credit/companyNoEntry/?repage";
	}
	/**
	 * @reqno: H1512260022
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z :cre_company_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:批量删除机构禁件列表信息
	 */	
	@RequiresPermissions("companynoentry:companyNoEntry:edit")
	@RequestMapping(value = "delete")
	public String delete(OrgNoEntry companyNoEntry, RedirectAttributes redirectAttributes) {
		companyNoEntryService.batchDelete(companyNoEntry.getId());
		addMessage(redirectAttributes, "删除成功!");
		return "redirect:"+Global.getAdminPath()+"/credit/companyNoEntry/?repage";
	}
	/**
	 * @reqno: H1512260022
	 * @date-designer:2016年01月13日-lirongchao
	 * @date-author:2016年01月13日-lirongchao:跳转到机构禁件设置页面
	 */		
	@RequiresPermissions("companynoentry:companyNoEntry:edit")
	@RequestMapping(value = "installCompanyNoEntry")
	public String installCompanyNoEntry(OrgNoEntry companyNoEntry,Model model) {
		List<Office> officeList = officeService.findAll();
		for(int i = 0;i < officeList.size();){
			//（1：机构；2：部门；3：小组；4：其他）
			if(!"1".equals(officeList.get(i).getType())){
				officeList.remove(i);
			}
			else {
				i++;
			}
		}
		model.addAttribute("officeList", officeList);
		model.addAttribute("companyNoEntryList", companyNoEntryService.findList(companyNoEntry));
		
		return "app/credit/noEntry/installCompanyNoEntry";
	}

}
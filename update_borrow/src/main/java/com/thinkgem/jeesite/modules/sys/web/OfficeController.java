/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}
	

	
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model, boolean addFlag) {
        model.addAttribute("list", officeService.findList(office));
        /**
         * @reqno:H1508270061
         * @date-designer:20150827-jiangbing
         * @date-author:20150827-jiangbing:添加判断是否添加机构的变量，true:刷新父页面结构树用 默认为false
         */
        model.addAttribute("addFlag", addFlag);
		return "modules/sys/officeList";
	}
	/**
	 * @reqno: H1507080024
	 * @date-designer:20150713-zhunan
	 * @db-j : sys_office : id,parent_id,name,sort,code,type
	 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
	 * @date-author:20150713-zhunan:根据传入的参数中包含的parentId，获取父节点是此的区域ID
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"listNode"})
	@ResponseBody
	public List<Office> listNode(Office office, Model model) {
		return officeService.findList(office);
	}
	
	/**
	 * @reqno: H1508280023
	 * @date-designer:20150831-jiangbing
	 * @db-j : sys_office : id,parent_id,name,sort,code,type
	 * @e-out-other : ztree - 树表 : 检索节点
	 * @date-author:20150831-jiangbing:根据传入的参数中包含的parentId，检验该节点有无子节点
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"checkNode"})
	@ResponseBody
	public List<Office> checkChildNode(Office office, Model model) {
		List<Office> list = officeService.findListByCompanyId(office.getParentId());
		return list;
	}

	/**
	 * 
	 * @reqno: 	H1506290069
	 * @date-designer:20150630-zhangxingyu
	 * @e-out-other : ID编号 - model.addAttribute（） : 将参数isCheck传到跳转的jsp页面
	 * 
	 * @date-author:20150630-zhangxingyu:为方法添加一个参数来接收请求参数，如果没有这个请求参数时  默认参数值为"0"
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model, @RequestParam(value = "isCheck",required = true,defaultValue="0") String isCheck) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		
		/**
		* @reqno        :H1509070148
		* @date-designer:20150910-jiangbing
		* @date-author  :20150910-jiangbing:查看的情况下，获取选择框的内容
		**/
		// isCheck==1 查看页面
		if ("1".equals(isCheck) && StringUtils.isNotBlank(office.getId())) {
			// 根据键值获取机构类型的标签
			office.setTypeLabel(DictUtils.getDictLabel(office.getType(), "sys_office_type", ""));
			// 根据键值获取机构级别的标签
			office.setGradeLabel(DictUtils.getDictLabel(office.getGrade(), "sys_office_grade", ""));
			// 根据键值获取是否可用的标签
			office.setUseableLabel(DictUtils.getDictLabel(office.getUseable(), "yes_no", ""));
		}
		/**
		 * 
		 * @reqno: 	H1506160031
		 * @date-designer:20150617-zhangxingyu
		 * 
		 * 
		 * @date-author:20150617-zhangxingyu:取掉原代码中   office对象默认添加的区域
		 */
  
		if (office.getArea()==null){
		}
		// 自动获取排序号
		/**
		 * @reqno: H1508310080
		 * @date-designer:20150906-jiangbing
		 * @date-author:20150906-jiangbing:机构编码存在的情况下不重置机构编码
		 */
		if (StringUtils.isBlank(office.getId()) && office.getParent()!=null
				&& (office.getCode() == null || office.getCode().trim().isEmpty())){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			
			/**
			* @reqno        :H1509070141
			* @date-designer:20150911-jiangbing
			* @date-author  :20150911-jiangbing:上级节点为NULL的时候设置为""避免添加机构时显示错误
			**/
			// 机构编码为必须输入 一定有值  如果删除自动生成机构编码功能请将此段代码删除
			if (office.getParent().getCode() == null) {
				office.getParent().setCode("");
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		model.addAttribute("isCheck", isCheck);
		return "modules/sys/officeForm";
	}
	
	/**
	 * @reqno: H1508310080
	 * @date-designer:20150906-jiangbing
	 * @db-j : sys_office : 机构表
	 * @date-author:20150906-jiangbing:判断机构编码是否已经存在 false:存在
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = {"checkCode"})
	@ResponseBody
	public boolean checkCode(String oldCode, String code) {
		// 未修改code的情况
		if (code !=null && code.equals(oldCode)) {
			return true;
		}
		Office office = new Office();
		office.setCode(code);
		// code被修改或是初次填入code的情况
		if (code !=null && officeService.getCountByCode(office) == 0) {
			return true;
		}
		return false;
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model,"0");
		}
		
		/**
		 * @reqno: H1508310080
		 * @date-designer:20150906-jiangbing
		 * @db-j : sys_office : 机构表
		 * @date-author:20150906-jiangbing:判断机构编码是否已经存在 如果存在返回原画面
		 */
		if (!checkCode(office.getOldCode(), office.getCode())) {
			addMessage(model, "保存机构'" + office.getName() + "'失败，机构编码已存在");
			return form(office, model,"0");
		}
		/**
		 * @reqno:H1603100040 
		 * @date-designer:20160310-liuxiaodong01
		 * @db-j : sys_office:levelnum levelnum展示时的级别(0为最顶级)
		 * @date-author:20160310-liuxiaodong01:需求的类型：系统新功能
		 *	需求的类别：功能
		 *	需求要实现的具体功能：
		 *	(1)点击系统管理--机构管理--机构添加
		 *	(2)界面无需改动，在点击保存按钮后后台获取上级机构的层级值+1 设置为当前层级
		 *	(3)数据库机构表增加一个字段levelnum number类型
		 *	(4)修改新增sql 增加此字段
		 *	(5)修改查询sql和分页查询需求 增加dlevel字段的查询
		 */
		office.setParent(officeService.get(office.getParent().getId()));
		if ("".equals(office.getParent().getId())){
			office.setlevelnum(0);
		}else{
			office.setlevelnum(office.getParent().getlevelnum()+1);
		}
		officeService.save(office);
		System.out.println("code值"+office.getCode()+"机构等级"+office.getGrade());
		
		StringBuilder childMsg = new StringBuilder();
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			int size = 0;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);

				/**
				* @reqno        :H1509070141
				* @date-designer:20150911-jiangbing
				* @date-author  :20150911-jiangbing:上级节点为NULL的时候设置为""避免添加机构时显示错误
				**/
				// 机构编码为必须输入 一定有值  如果删除自动生成机构编码功能请将此段代码删除
				if (office.getCode() == null) {
					office.setCode("");
				}
				childOffice.setCode(office.getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
				/**
				* @reqno        :H1509070141
				* @date-designer:20150911-jiangbing
				* @date-author  :20150911-jiangbing:检验下级机构机构编码是否已存在，设置显示信息
				**/
				if (!checkCode(childOffice.getOldCode(), childOffice.getCode())) {
					// 失败的情况设置失败信息
					childMsg.append("<br/>保存下级机构'" + childOffice.getName() + "'失败，机构编码已存在");
					// 自动生成下一个机构编码
					size++;
					continue;
				}
				officeService.save(childOffice);
				// 设置保存成功的信息
				childMsg.append("<br/>保存下级机构'" + childOffice.getName() + "'成功");
				size++;
			}
		}
		/**
		* @reqno        :H1509070141
		* @date-designer:20150911-jiangbing
		* @date-author  :20150911-jiangbing:设置下级机构保存结果信息
		**/
		// 设置添加机构保存成功后的信息 如果快速添加下级机构设置下级机构保存情况的信息
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功" + childMsg);
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		/**
         * @reqno:H1508270061
         * @date-designer:20150827-jiangbing
         * @date-author:20150827-jiangbing:添加判断是否添加机构的变量，刷新父页面结构树用
         */
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds()+"&addFlag="+true;
	}
	
	/**
	 * 
	 * @reqno:H1505280033
	 * @date-designer:20150604-zhangxingyu
	 * @e-in-other : ID编号 - if: 加一判断条件 如果该机构没有上级机构，即parentId为0，就让他重定向到查询所有机构页面
	 * 
	 * @date-author:20150604-zhangxingyu:当机构没有上级机构时，在删除时，不应该报错，应该允许删除，并且返回到所有机构数据的列表
	 */
  
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
//		}
		if("0".equals(office.getParentId())){
			/**
			 * @reqno        :H1508280023
			 * @date-designer:20150831-jiangbing
			 * @date-author  :20150831-jiangbing 增加判断是否是删除机构的FLAG true:删除机构或是添加机构
			 **/
			return "redirect:" + adminPath + "/sys/office/list?id=&parentIds="+"&addFlag="+true;
		}
		/**
		 * 
		 * @reqno: H1507070035
		 * @date-designer:20150715-zhangxingyu
		 * @date-author:20150715-zhangxingyu:给跳转链接多加一参数parent.id=office.getParentId()
		 * 							
		 */
		/**
		 * @reqno        :H1508280023
		 * @date-designer:20150831-jiangbing
		 * @date-author  :20150831-jiangbing 增加判断是否是删除机构的FLAG true:删除机构或是添加机构
		 **/
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds()+"&parent.id="+office.getParentId()+"&addFlag="+true;
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：机构；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	/**
	 * 
	 * @reqno: H1507070030
	 * @date-designer:20150710-zhangxingyu
	 * @e-in-text : companyId - input type="hidden": 隐藏域
	 * @date-author:20150710-zhangxingyu:给方法多添加个参数@RequestParam(required=false) String companyId(机构id)
	 * 									
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, @RequestParam(required=false) String companyId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		/**
		 * 
		 * @reqno: H1507070030
		 * @date-designer:20150710-zhangxingyu
		 * @db-z : sys_office : 查询所有的下级机构
		 * @date-author:20150710-zhangxingyu:当type="2" 切companyId不为空时  list为id是companyId机构下的所有机构
		 * 									
		 */
		if("2".equals(type)&&companyId!=null){
			list = officeService.findListByCompanyId(companyId);//查id为companyId机构下的所有机构
		}
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				map.put("code",e.getCode());
				map.put("grade", e.getGrade());
				/**
				 * @reqno: H1507230050
				 * @date-designer:20150728-chenshaojia
				 * @date-author:20150728-chenshaojia:为机构树结构的节点对象增加type属性
				 */
				map.put("type", e.getType());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}

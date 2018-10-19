package com.resoft.credit.dynamicTable.web;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicTable.service.DynamicTableService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * @author huangxuecheng 数据栏目总调度类,基本逐方法注释
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dynamicTable")
public class DynamicTableController extends BaseController{
	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:注入dynamicTableService
	 */
	@Autowired
	private DynamicTableService dynamicTableService;

	/**
	 * @reqno:H1510170007
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:查询出CRE_信贷审批_系统管理_表单栏目配置_查询表单页面list方法，sys_menu入口方法
	 */
	@RequestMapping(value = {"list", ""})
	public String list(CreDataGroupTable creDataGroupTable,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes,String returnVal) {
		
		//creDataGroupTable.setCompanyId(dynamicTableService.searchParentIdsByCompanyId(UserUtils.getUser().getCompany().getId()).split(",")[0]);
		/**
		 * @reqno:H1510080094
		 * @date-designer:2015年10月19日-songmin
		 * @date-author:2015年10月19日-songmin:需求中加入动态表单栏配置支持多法人
		 * 		多法人的支持：即在获取动态表单栏配置的时候，在查询时要根据用户所在的 根公司 ID来进行区分
		 * 		根公司在获取直接通过parentIDs来获得，根据系统设定，parentsIds[1]为根公司ID，这里考虑到
		 * 		根公司下的用户，当parentsIds[1]不存在时，直接获取器所在公司ID，一般状况下该情形不该出现
		 */
		User user = UserUtils.getUser();
		Office company = user.getCompany();
		String rootCompanyId = null;
		String parentIds = company.getParentIds();
		if(StringUtils.isNotEmpty(parentIds)){
			String[] parentIdsArr = parentIds.split(",");
			if(parentIdsArr.length>1){
				rootCompanyId = parentIdsArr[1];
			}else{
				rootCompanyId = company.getId();
			}
		}
		creDataGroupTable.setCompanyId(rootCompanyId);
		
		Page<CreDataGroupTable> page = dynamicTableService.findPage(new Page<CreDataGroupTable>(request, response), creDataGroupTable); 
		model.addAttribute("page", page);
		if(returnVal!=null&&!returnVal.equals("")&&!returnVal.equals("undefined")){
			if(returnVal.equals("1")){
			addMessage(model, "保存数据栏目成功");
			}
		}
		return "app/credit/dynamicTable/dynamicTableList";
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:原先预计会出现修改的方法，后来仅仅需要查询即可，此方法页面提供js调用接口即可，保留
	 */
	@RequestMapping(value = "/delete")
	public String delete(CreDataGroupTable creDataGroupTable, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,@RequestParam String ids) {
		String[] str = null;
		List<String> idsList = new ArrayList<String>();
		if(ids.contains(",")){
			str = ids.trim().split(",");
			for(String id:str){
				idsList.add(id);
			}
		}else{
			idsList.add(ids);
		}
		dynamicTableService.deleteMutil(idsList);
		addMessage(redirectAttributes, "删除成功");
		return   "redirect:" + adminPath + "/sys/dynamicTable/";
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改。逻辑：新增修改公用方法(此处无新增)，有id取pojo修改，无id便是新增并且设置tableName为null
	 *										使用model封装数据，InternalResourceViewResolver返回
	 */			
	//点击修改
	@RequestMapping(value="/editDynamicTable")
	public String editDynamicTable(CreDataGroupTable creDataGroupTable, HttpServletRequest request, HttpServletResponse response, Model model,String id) throws UnsupportedEncodingException{
		if(creDataGroupTable.getId()!=null&&!creDataGroupTable.getId().equals("")){
			creDataGroupTable = dynamicTableService.get(creDataGroupTable.getId());
		}else{
			creDataGroupTable.setDataTableName(null);//如果为""会引发修改页面显示问题
		}
		if(creDataGroupTable.getDataGroupLabel()!=null&&!creDataGroupTable.getDataGroupLabel().equals("")){
			creDataGroupTable.setDataGroupLabel(URLDecoder.decode(creDataGroupTable.getDataGroupLabel(), "UTF-8"));
		}	
		//global.java
		/**
		 * @reqno:H1511300032
		 * @date-designer:20151202-huangxuecheng
		 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
		 */	
		String schemaName = Global.getConfig("jdbc.schema");
		List<Map<String,String>> tableList = dynamicTableService.selectAllTableName(schemaName);
		tableList = this.sortTableList(tableList);
		creDataGroupTable.setTableList(tableList);
		model.addAttribute("creDataGroupTable", creDataGroupTable);
		return "app/credit/dynamicTable/dynamicTableListEdit";
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改。逻辑：取出所有table的数据调用方法，此处使用List封装map返回Mybatis数据
	 *										目的是最终转换成tableName为key，tableComment为value的map,存放入list返回
	 */
	//遍历list
	public List<Map<String,String>> sortTableList(List<Map<String,String>> tableList){
		Map<String, String> tempMap ;
		List<Map<String,String>> newList = new ArrayList<Map<String,String>>();
		for(Map<String, String> map : tableList){
			tempMap = new HashMap<String,String>();;
			tempMap = this.sortTableMap(map);
			newList.add(tempMap);
		}
		return newList;
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改。逻辑：取出所有table的数据调用方法，此处使用map返回Mybatis数据
	 *										目的是最终转换成tableName为key，tableComment为value的map
	 */
	//遍历map
	public Map<String, String> sortTableMap(Map<String,String> map){
		Map<String, String> tempMap = new HashMap<String,String>();
		String key = "";
		String value = "";
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().trim().equalsIgnoreCase("TABLE_COMMENT")){
				key = entry.getValue();
			}else if(entry.getKey().trim().equalsIgnoreCase("TABLE_NAME")){
				value = entry.getValue();
			}
		}
		tempMap.put(key, value);
		return tempMap;
	}
	/**
	 * @reqno:H1510170008
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改保存。逻辑：使用saveDynamicTable进行数据保存工作，如果原先的column在数据库中存在，不能修改									
	 */
	//点击保存
	@RequestMapping(value="/saveDynamicTable")
	public @ResponseBody Map<String, String> saveDynamicTable(CreDataGroupTable creDataGroupTable, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		Map<String, String> map = new HashMap<String, String>();
		String idFromDb = dynamicTableService.searchIdByDataGroup(creDataGroupTable);
		if(idFromDb!=null){
			map.put("result", "未能保存,数据库中已存在同栏目信息");
		}
		if(creDataGroupTable.getDataGroupLabel()!=null&&!creDataGroupTable.getDataGroupLabel().equals("")){
			creDataGroupTable.setDataGroupLabel(URLDecoder.decode(creDataGroupTable.getDataGroupLabel(), "UTF-8"));
		}
		if(UserUtils.getUser().getCompany().getId()!=null&&!UserUtils.getUser().getCompany().getId().equals("")){
			//creDataGroupTable.setCompanyId(dynamicTableService.searchParentIdsByCompanyId(UserUtils.getUser().getCompany().getId()).split(",")[0]);
			/**
			 * @reqno:H1510080094
			 * @date-designer:2015年10月19日-songmin
			 * @date-author:2015年10月19日-songmin:需求中加入动态表单栏配置支持多法人
			 * 		多法人的支持：即在获取动态表单栏配置的时候，在查询时要根据用户所在的 根公司 ID来进行区分
			 * 		根公司在获取直接通过parentIDs来获得，根据系统设定，parentsIds[1]为根公司ID，这里考虑到
			 * 		根公司下的用户，当parentsIds[1]不存在时，直接获取器所在公司ID，一般状况下该情形不该出现
			 */
			User user = UserUtils.getUser();
			Office company = user.getCompany();
			String rootCompanyId = null;
			String parentIds = company.getParentIds();
			if(StringUtils.isNotEmpty(parentIds)){
				String[] parentIdsArr = parentIds.split(",");
				if(parentIdsArr.length>1){
					rootCompanyId = parentIdsArr[1];
				}else{
					rootCompanyId = company.getId();
				}
			}
			creDataGroupTable.setCompanyId(rootCompanyId);
		}
		dynamicTableService.save(creDataGroupTable);
		map.put("result","success");
		return map;
	}
}

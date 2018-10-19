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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.resoft.credit.dynamicTable.entity.DynamicTableColumnVo;
import com.resoft.credit.dynamicTable.service.DynamicTableColumnDeployService;
import com.resoft.credit.dynamicTable.service.DynamicTableColumnService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * @author huangxuecheng 表单数据项配置总调度
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dynamicTableColumn")
public class DynamicTableColumnController extends BaseController{
	/**
	 * @reqno:H1510170009
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:注入列表数据项字段显示service
	 */
	@Autowired
	public DynamicTableColumnService dynamicTableColumnService;
	/**
	 * @reqno:H1510170009
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:注入列表数据项字段配置service
	 */
	@Autowired
	public DynamicTableColumnDeployService dynamicTableColumnDeployService;
	/**
	 * @reqno:H1510170009
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_查询。逻辑：使用现有的baseController中的方法，使用model封装数据，InternalResourceViewResolver返回，用page封装数据和条数返回页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(DynamicTableColumnVo dynamicTableColumnVo,HttpServletRequest request, HttpServletResponse response, Model model,String returnVal) {
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
		//dynamicTableColumnVo.setCompanyId(dynamicTableColumnService.searchParentIdsByCompanyId(UserUtils.getUser().getCompany().getId()).split(",")[0]);
		dynamicTableColumnVo.setCompanyId(rootCompanyId);
		Page<DynamicTableColumnVo> page = dynamicTableColumnService.findPage(new Page<DynamicTableColumnVo>(request, response), dynamicTableColumnVo); 
		model.addAttribute("page", page);
		if(returnVal!=null&&!returnVal.equals("")&&!returnVal.equals("undefined")){
			if(returnVal.equals("1")){
			addMessage(model, "新增数据项成功");
			}
		}
		return "app/credit/dynamicTable/dynamicTableColumnList";
	}
	/**
	 * @reqno:H1510170010
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询，点击配置执行的方法。逻辑：在model中保存产品类别名称、数据类型、数据栏目、顶级机构id，如果在request域中同时满足productType、creDataGroupTableId并存，可以确定一个唯一的实体类，查出，
	 * 									         以便于后续CRUD，使用returnVal做页面标记位进行数据提示操作
	 */
	//点击配置
	@RequestMapping(value="/dynamicTableColumnDeploy")
	public String editDynamicTableColumn(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String returnVal) throws UnsupportedEncodingException{
		//可以唯一获取虚表中的唯一记录的两个关键条件
		if(creProFromColumn.getProductType()!=null&&!creProFromColumn.getProductType().equals("")){
			String productType = creProFromColumn.getProductType();
			if(productType.contains(",")){
				productType = productType.split(",")[0];
				creProFromColumn.setProductTypeCode(productType);
				model.addAttribute("productType", productType);
			}else{
				model.addAttribute("productType", creProFromColumn.getProductType());
			}
		}
		if(creProFromColumn.getCreDataGroupTableId()!=null&&!creProFromColumn.getCreDataGroupTableId().equals("")){
			String dataGroupTableId = creProFromColumn.getCreDataGroupTableId();
			if(dataGroupTableId.contains(",")){
				dataGroupTableId = dataGroupTableId.split(",")[0];
				creProFromColumn.setCreDataGroupTableId(dataGroupTableId);
				model.addAttribute("creDataGroupTableId", dataGroupTableId);
			}else{				
				model.addAttribute("creDataGroupTableId", creProFromColumn.getCreDataGroupTableId());
			}
			model.addAttribute("dataTableName",dynamicTableColumnDeployService.searchTableNameById(dataGroupTableId));
		}
		//获取产品类别名称
		if(creProFromColumn.getProductTypeValue()!=null&&!creProFromColumn.getProductTypeValue().equals("")){
			model.addAttribute("productTypeValuePage", URLDecoder.decode(creProFromColumn.getProductTypeValue(), "UTF-8"));
		}
		//获取数据类型
		if(creProFromColumn.getShowPositionValue()!=null&&!creProFromColumn.getShowPositionValue().equals("")){
			model.addAttribute("showPositionValuePage", URLDecoder.decode(creProFromColumn.getShowPositionValue(), "UTF-8"));
		}
		//获取数据栏目
		if(creProFromColumn.getDataGroupValue()!=null&&!creProFromColumn.getDataGroupValue().equals("")){
			model.addAttribute("dataGroupValuePage", URLDecoder.decode(creProFromColumn.getDataGroupValue(), "UTF-8"));
		}
		//获取顶级机构id
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
		String companyId = rootCompanyId;
		creProFromColumn.setCompanyId(companyId);
		//如果双向满足，直接获取新的CreProFromColumn，以便获取全条件进行查询
		if((creProFromColumn.getProductType()!=null&&!creProFromColumn.getProductType().equals(""))&(creProFromColumn.getCreDataGroupTableId()!=null&&!creProFromColumn.getCreDataGroupTableId().equals(""))){
			Map<String, String> map = new HashMap<String, String>();
			map.put("companyId", companyId);
			map.put("creDataGroupTableId", creProFromColumn.getCreDataGroupTableId());
			map.put("productType", creProFromColumn.getProductType());
			creProFromColumn = dynamicTableColumnDeployService.searchNewCreProFromColumn(map);
		}
		model.addAttribute("addOne",creProFromColumn);
		Page<CreProFromColumn> page = dynamicTableColumnDeployService.findPage(new Page<CreProFromColumn>(request, response), creProFromColumn); 
		model.addAttribute("page", page);
		if(returnVal!=null&&!returnVal.equals("")&&!returnVal.equals("undefined")){
			if(returnVal.equals("1")){
				addMessage(model, "新增数据项成功");
			}else if(returnVal.equals("2")){
				addMessage(model, "删除数据项成功");
			}else if(returnVal.equals("3")){
				addMessage(model, "批量删除数据项成功");
			}else if(returnVal.equals("4")){
				addMessage(model, "修改数据项成功");
			}
		}
		return "app/credit/dynamicTable/dynamicTableColumnDeployList";
	}
	/**
	 * @reqno:H1510170011
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询_新增。点击新增触发本方法，如果是新增id为空，不经过数据库直接返回即可
	 */
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询_修改、删除。点击修改触发本方法，如果id不为空，查出，回显
	 */
	//点击新增或者修改
	@RequestMapping(value="/addTableColumn")
	public String addTableColumn(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String returnVal) throws UnsupportedEncodingException{
		String tableName = "";
		if(creProFromColumn.getCreDataGroupTableId()!=null&&!creProFromColumn.getCreDataGroupTableId().equals("")){
		tableName = dynamicTableColumnDeployService.searchTableNameById(creProFromColumn.getCreDataGroupTableId());
		creProFromColumn.setDataGroupId((creProFromColumn.getCreDataGroupTableId()));
		}
		if(creProFromColumn.getId()!=null&&!creProFromColumn.getId().equals("")){
			creProFromColumn = dynamicTableColumnDeployService.get(creProFromColumn.getId());
		}
		/**
		 * @reqno:H1511300032
		 * @date-designer:20151202-huangxuecheng
		 * @date-author:20151202-huangxuecheng:开发原因：CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来。处理方式：传入对应的schema名称
		 */	
		String schemaName = Global.getConfig("jdbc.schema");
		Map<String, String> map = Maps.newHashMap();
		map.put("tableName", tableName);
		map.put("schemaName", schemaName);
		List<Map<String,String>> columnList = dynamicTableColumnDeployService.searchTableColumnAndCommentByTableName(map);
		List<List<String>> list = this.sortTableList(columnList);
		List<String> columnNameList = list.get(0);
		List<String> columnCommentList = list.get(1);
		creProFromColumn.setColumnNameList(columnNameList);
		creProFromColumn.setColumnCommentList(columnCommentList);
		model.addAttribute("creProFromColumn", creProFromColumn);
		return "app/credit/dynamicTable/dynamicTableColumnAdd";
	}
	/**
	 * @reqno:H1510170011
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改。逻辑：取出所有table的数据调用方法，此处使用List封装map返回Mybatis数据
	 *										目的是最终转换成columnName为key，columnComment为value的map,存放入list返回
	 */
	//遍历list
	public List<List<String>> sortTableList(List<Map<String,String>> columnList){
		List<String> columnNameList = new ArrayList<String>();
		List<String> columnCommentList = new ArrayList<String>();
		List<List<String>> sumList = new ArrayList<List<String>>();
		Map<String, String> tempMap ;
		for(Map<String, String> map : columnList){
			tempMap = this.sortTableMap(map);
			columnNameList.add(tempMap.entrySet().iterator().next().getKey());
			columnCommentList.add(tempMap.entrySet().iterator().next().getValue());
		}
		sumList.add(columnNameList);
		sumList.add(columnCommentList);
		return sumList;
	}
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改。逻辑：取出所有table的数据调用方法，此处使用List封装map返回Mybatis数据
	 *										目的是最终转换成columnName为key，columnComment为value的map,存放入list返回
	 */
	//遍历map
	public Map<String, String> sortTableMap(Map<String,String> map){
		Map<String, String> tempMap = new HashMap<String,String>();
		String key = "";
		String value = "";
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().trim().equalsIgnoreCase("COLUMN_NAME")){
				key = entry.getValue();
			}else if(entry.getKey().trim().equalsIgnoreCase("COLUMN_COMMENT")){
				value = entry.getValue();
			}
		}
			tempMap.put(key, value);
			return tempMap;
		}
	/**
	 * @reqno:H1510170011
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改或者新增，保存。逻辑，如果是新增，查datagroup唯一性，唯一，可以新增，不唯一，新增失败；
	 * 										修改，使用hidden域进行新老datagroup比较，如果相同，不做校验，如果不同，做唯一性校验
	 */
	/**
	 * @reqno:H1511050008
	 * @date-designer:20151109-huangxuecheng
	 * @date-author:20151109-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_修改或者新增，保存。描述：在保存的时候，如果数据列在数据库表中的长度大于输入的长度，弹窗提示，并且保存失败
	 */
	//新增保存
	@RequestMapping(value="/saveTableColumn")
	public @ResponseBody Map<String, String> saveTableColumn(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String returnVal,String columnCodeOld){
		Map<String, String> map = new HashMap<String, String>();
		if(creProFromColumn.getId()!=null&&!creProFromColumn.getId().equals("")){
			map.put("reval","4");
			if(creProFromColumn.getColumnCode()!=null&&!creProFromColumn.getColumnCode().equals("")){
				if(!columnCodeOld.equalsIgnoreCase(creProFromColumn.getColumnCode())){
					map.put("columnCode", creProFromColumn.getColumnCode());
					map.put("productType", creProFromColumn.getProductType());
					map.put("dataGroupId", creProFromColumn.getDataGroupId());
					long result = dynamicTableColumnDeployService.searchColumnCount(map);
					if(result>0){
						map.put("result","保存失败,数据表中已经存在同样的数据项列名");
						return map;
					}
				}
			}
		}else{
			map.put("reval","1");
			if((creProFromColumn.getColumnCode()!=null&&!creProFromColumn.getColumnCode().equals(""))&(creProFromColumn.getProductType()!=null&&!creProFromColumn.getProductType().equals(""))&(creProFromColumn.getDataGroupId()!=null&&!creProFromColumn.getDataGroupId().equals(""))){
				map.put("columnCode", creProFromColumn.getColumnCode());
				map.put("productType", creProFromColumn.getProductType());
				map.put("dataGroupId", creProFromColumn.getDataGroupId());
				long result = dynamicTableColumnDeployService.searchColumnCount(map);
				if(result>0){
					map.put("result","新增失败,数据表中已经存在同样的数据项列名");
					return map;
				}
			}
		}
		String tableName = dynamicTableColumnDeployService.searchTableNameById(creProFromColumn.getDataGroupId());
		String columnName = creProFromColumn.getColumnCode();
		map.put("tableName", tableName);
		map.put("columnName", columnName);
		List<Map<String, String>> columnInforList = dynamicTableColumnDeployService.searchColumnInfor(map);
		Integer targetNumber = this.getColumnLength(columnInforList);
		Integer sortInput = creProFromColumn.getColumnLength();
		if(sortInput > targetNumber){
			map.put("result", columnName + "列根据数据库设计，最大输入长度需小于或等于" + targetNumber + "，请修改！");
			return map;
		}
		dynamicTableColumnDeployService.save(creProFromColumn);
		map.put("result","success");
		return map;
	}
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_删除：可以实现单删除和批量删除
	 */
	//删除和批量删除
	@RequestMapping(value="/deleteTableColumn")
	public @ResponseBody Map<String, String> deleteTableColumn(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String ids){
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
		dynamicTableColumnDeployService.deleteMutil(idsList);
		Map<String,String> map = new HashMap<String,String>();
		map.put("meg","删除数据项方案成功");
		return map;
	}
	//查询栏目的数据库建表时长度
	public Integer getColumnLength(List<Map<String, String>> list){
		String type = list.get(0).get("Type");
		int start = type.indexOf("(");
		int middle = type.indexOf(",");
		int end = type.indexOf(")");
		//timestamp型，不做控制；如果是decimal型，控制小数点前的数字；如果是varchar或者bigint型，直接取数即可
		if(start == -1){
			return Integer.MAX_VALUE;
		}else if(middle != -1){
			return Integer.parseInt(type.substring(start+1, middle));
		}else{
			return Integer.parseInt(type.substring(start+1, end));
		}
	}
}

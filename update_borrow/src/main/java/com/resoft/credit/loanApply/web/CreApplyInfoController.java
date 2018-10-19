package com.resoft.credit.loanApply.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.resoft.common.utils.HttpConnector;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.resoft.credit.dynamicSet.service.CreDataGroupTableService;
import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.loanApply.service.CreApplyInfoService;
import com.resoft.credit.loanApply.service.CreApplyRegisterService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.entity.ProductPeriod;
import com.resoft.credit.product.service.ProductPeriodService;
import com.resoft.credit.product.service.ProductService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * CRE_信贷审批_申请录入  贷款申请Action
 */
@Controller
@RequestMapping(value="${adminPath}/credit/loanApply")
public class CreApplyInfoController extends BaseController{
	
	@Autowired
	private CreApplyInfoService  creApplyInfoService;//贷款申请信息表service
	@Autowired
	private CreApplyRegisterService creApplyRegisterService;//客户申请信息表service
	@Autowired
	private ProductService productService;//产品sercice
	@Autowired
	private ProductPeriodService productPeriodService;//产品期限service
	@Autowired
	private CreDataGroupTableService creDataGroupTableService;
	@Autowired
	private ActTaskService actTaskService;
	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_申请录入_产品类型选择_查询
	 * 	判断是否登记过申请贷款的产品类型，在填写贷款申请前必须先填写产品类型
	 * 
	 * 	在待办理任务列表，用户选择一条任务名称为“申请录入”的待办任务，点击“办理”按钮，调用该方法，
	 *  	当本任务第一次办理，没有选择过产品类型（也即在“贷款申请信息表”中没有本申请对应的记录）时，
	 *  	跳到申请录入->产品类型选择页面；
	 *  @param actTaskParam  代办任务的默认参数封装类
	 */
	/**
	 * @reqno:H1512210025
	 * @date-designer:2015年12月30日-songmin
	 * @date-author:2015年12月30日-songmin:CRE_信贷审批_个人_申请录入_页面重构
	 * 	2015年12月30日：需求变更，产品类别登记移动到客户登记时就录入了，这里不再需要判断产品是否登记过，直接跳转申请录入界面
	 */
	@RequestMapping("/checkExists")
	public String checkProductType(ActTaskParam actTaskParam,Model model){
		/**
		 * @reqno:H1510130147
		 * @date-designer:2015年10月28日-songmin
		 * @date-author:2015年10月28日-songmin:CRE_信贷审批_申请录入_录入结论
		 * 这里Spring自动绑定actTaskParam到Model中，为了保证统一，还是手动设置将actTaskParam设置到Model中
		 */
		model.addAttribute("actTaskParam", actTaskParam);
		/**
		 * @reqno:H1510210082
		 * @date-designer:2015年11月3日-songmin
		 * @date-author:2015年11月3日-songmin:CRE_信贷审批_总监审批_集成展示：贷款申请信息、客户信息、内匹配信息、征信信息
		 * 		整合影像上传功能，该功能参考lirongchao的影像上传-查看
		 */
		String productType = "";	//产品类型
		CreApplyInfo creApplyInfo = new CreApplyInfo();
		creApplyInfo.setApplyNo(actTaskParam.getApplyNo());
		List<CreApplyInfo> onlyPructTypeApplyInfo = creApplyInfoService.findList(creApplyInfo);
		if(!onlyPructTypeApplyInfo.isEmpty()){
			productType=onlyPructTypeApplyInfo.get(0).getApplyProductTypeCode();
		}
		model.addAttribute("productType", productType);
		return "/app/credit/loanApply/loanApply_index";
	}
	
	@RequestMapping("/checkExistsradoy")
	public String checkExistsradoy(ActTaskParam actTaskParam,Model model){
		/**
		 * @reqno: H1511100159
		 * @date-designer:20151113-lirongchao
		 * @db-z : cre_apply_info :APPLY_PRODUCT_TYPE_CODE
		 * @date-author:20151113-lirongchao:  1.CRE_信贷审批_初审_内匹配信息列表中，在“状态”列后面，添加操作列，内容为“客户详情”链接，点击时，弹出窗口，查看客户贷款申请信息、客户信息；
2.点击列表操作中的“客户详情”，弹出窗口,窗口名称“客户详情”；
3.页面布局：以tab页签形式显示，包括：贷款申请信息、客户信息；默认只加载“贷款申请信息”页签，其它页签只有在点击时才做加载；
两页面显示的元素与信贷审批系统中申请录入保持不变；页面所有的表单均为只读，保存、新增、删除、修改按钮隐藏不显示；
	 当前环节-加载客户详情页面，获取产品类型
		 */
		model.addAttribute("actTaskParam", actTaskParam);
			String productType="";	//产品类型
			CreApplyInfo creApplyInfo =new CreApplyInfo();
			creApplyInfo.setApplyNo(actTaskParam.getApplyNo());
			List<CreApplyInfo> onlyPructTypeApplyInfo=creApplyInfoService.findList(creApplyInfo);
			if(onlyPructTypeApplyInfo!=null&&onlyPructTypeApplyInfo.size()>0){
				productType=onlyPructTypeApplyInfo.get(0).getApplyProductTypeCode();
			}
			model.addAttribute("productType", productType);
			actTaskParam.setTitle("客户详情");
			return "/app/credit/loanApply/loanApply_indexradoy";
		
	}
	/**
	 * @reqno:H1509230043
	 * @date-designer:2015年10月14日-songmin
	 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_产品类型选择_下一步（保存）、页面跳转
	 * 		保存客户贷款申请的产品类型信息登记信息
	 */
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月24日-songmin
	 * @date-author:2015年12月24日-songmin:业务变更代码不再使用
	 */
	
	/**
	 * @reqno: H1510280019
	 * @date-designer:20151105-lirongchao
	 * @db-z :sys_user :login_name,name,id
	 * @db-j :sys_office :name
	 * @date-author:20151105-lirongchao: 1.申请录入页面：
 页面布局：上下布局，上面为工具栏（包括：影像上传、影像查阅、关闭）；下面为tab页签（包括：贷款申请信息、客户信息、录入结论）；
2.本需求是在“影像查阅”后面添加：“转办” 按钮；
3.点击“转办”按钮，弹出窗口，窗口名称“转办人员选择”；
4.窗口页面内容：上下布局，分别为：
  表头按钮：转办、关闭；
  人员列表，数据项：单选框按钮、序号、登陆名、姓名、归属部门、归属机构；
5.人员列表，加载与当前用户同一机构下的用户，供选择；列表要做成分布显示
6.选择一个用户，点击“转办”按钮，进行转办，前台提示转办成功，关闭窗口，同时刷新待办任务列表；
7.点击“关闭”按钮，关闭窗口；
	 当前环节-获取相同机构下的人员，不包括自己,跳转到转办页面	
	*/
	@RequestMapping("/change")
	public String change(ActTaskParam actTaskParam,Model model){
		String companyid=UserUtils.getUser().getCompany().getId();
		String id=UserUtils.getUser().getId();
		HashMap<String, Object> parmas = Maps.newHashMap();
		parmas.put("companyId", companyid);
		parmas.put("id", id);
		List<User> list=creApplyInfoService.finduserList(parmas);
		model.addAttribute("list", list);
		model.addAttribute("actTaskParam", actTaskParam);
		return "/app/credit/loanApply/loanApply_change";
	}	
	
	/**
	 * @reqno:H1601150121
	 * @date-designer:2016年1月16日-songmin
	 * @date-author:2016年1月16日-songmin:跳转同机构下人员选择页面
	 * 	该功能和上面的change方法功能类型，但是上面的方法在使用中已经限定了只用用于转办功能，这里，将上面的功能抽取出来，做到在以后别的地方需要选择本机构下人员时能共用页面
	 * 	该方法，目前有一个缺陷在于，提示信息有点固化，以后如果在使用中有其他的功能需要时，需要将提示信息动态化上做一定处理
	 */
	@RequestMapping("/loadOrgUser")
	public String loadCurrOrgUser(ActTaskParam actTaskParam,Model model){
		User user = UserUtils.getUser();
		String companyid=user.getCompany().getId();
		String id=user.getId();
		HashMap<String, Object> parmas = Maps.newHashMap();
		parmas.put("companyId", companyid);
		parmas.put("id", id);
		List<User> list=creApplyInfoService.finduserList(parmas);
		model.addAttribute("list", list);
		model.addAttribute("actTaskParam", actTaskParam);
		return "/app/credit/loanApply/orgUser";
	}
	
	/**
	 * @reqno: H1510280019
	 * @date-designer:20151105-lirongchao
	 * @db-z :sys_user :login_name,name,id
	 * @db-j :sys_office :name
	 * @date-author:20151105-lirongchao: 1.申请录入页面：
 页面布局：上下布局，上面为工具栏（包括：影像上传、影像查阅、关闭）；下面为tab页签（包括：贷款申请信息、客户信息、录入结论）；
2.本需求是在“影像查阅”后面添加：“转办” 按钮；
3.点击“转办”按钮，弹出窗口，窗口名称“转办人员选择”；
4.窗口页面内容：上下布局，分别为：
  表头按钮：转办、关闭；
  人员列表，数据项：单选框按钮、序号、登陆名、姓名、归属部门、归属机构；
5.人员列表，加载与当前用户同一机构下的用户，供选择；列表要做成分布显示
6.选择一个用户，点击“转办”按钮，进行转办，前台提示转办成功，关闭窗口，同时刷新待办任务列表；
7.点击“关闭”按钮，关闭窗口；
	 当前环节-进入转办环节，成功之后关闭前台页面	
	*/
	@ResponseBody
	@RequestMapping("/changeup")
	public String changeup(ActTaskParam actTaskParam,Model model,HttpServletRequest request){
		String sysuserid=request.getParameter("sysuserid");
		actTaskService.turnToDo(actTaskParam.getTaskId(), sysuserid, actTaskParam.getProcInstId());
		return "";
	}	
	/**
	 * @reqno:H1510080090
	 * @date-designer:2015年10月14日-songmin
	 * @date-author:2015年10月14日-songmin:CRE_信贷审批_申请录入_贷款申请信息_客户登记信息
	 * 		跳转贷款申请页面：
	 * 		1、预查询客户贷款登记信息
	 */
	/**
	 * @reqno:H1510080091
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息默认数据项展现
	 * 		2、预查询该贷款申请中产品类型对应的所有可用产品列表
	 * 		3、如果存在产品ID，预查询该贷款申请中产品所对应的所有可用期限类型
	 */
	/**
	 * @reqno:H1510080092
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息_动态配置数据项展现
	 * 		4、预查询动态表单配置信息
	 */
	/**
	* @reqno:H1510080111
	* @date-designer:2015年10月27日-songmin
	* @date-author:2015年10月27日-songmin:CRE_信贷审批_贷款初审_贷款申请信息_查看
	*	在“初审”环节，当点击“贷款申请信息”页签时，显示贷款申请信息，全部为只读，具体内容与申请录入保持一致
	*	实现逻辑：初审环节调用该方法是传入参数readOnly，用于标示页面元素只读，页面在检测到该值存在时将所有可填写元素设置只读属性
	 */
	@RequestMapping("/loanApplyInfoForm")
	public String loanApplyInfoForm(ActTaskParam actTaskParam,Model model,String readOnly){
		//查询客户贷款登记信息
		CreApplyRegister  creApplyRegister  = 
				creApplyRegisterService.findApplyRegisterInfoById(actTaskParam.getApplyNo());
		model.addAttribute("creApplyRegister", creApplyRegister);
		//查询贷款申请信息
		CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
		if(null==creApplyInfo){
			creApplyInfo = new CreApplyInfo();//spring的标签必须要对象绑定，这里需要确保对象不能为null
		}
		//补充信息说明-字符转译
		if(null!=creApplyInfo&&StringUtils.isNotEmpty(creApplyInfo.getDescription())){
			String descEs = StringEscapeUtils.escapeHtml4(creApplyInfo.getDescription());
			creApplyInfo.setDescription(descEs);
		}
		model.addAttribute("creApplyInfo", creApplyInfo);
		//根据申请的产品类型，获取该类型下的所有产品信息
		if(null!=creApplyInfo){
			/**
			 * @reqno:H1511100081
			 * @date-designer:2015年11月13日-songmin
			 * @date-author:2015年11月13日-songmin:产品列表做数据权限控制，改为只加载显示当前用户所在机构下的产品
			 * @param productType 产品类型
			 * @param orgId	机构ID
			 */
			/**
			 * @reqno:H1511190029
			 * @date-designer:2015年11月13日-songmin
			 * @date-author:2015年11月13日-songmin:审核人员由于不一定是当前机构人员，造成前台页面无法获取到产品名称的bug修复
			 */
			//查询时不限制产品机构权限（原因：查看人员的机构有可能为上级机构，根据当前代码的实现逻辑所以不做产品机构限制）
			if("true".equals(readOnly)){
				List<Product>  productList = productService.findProductByProductType(creApplyInfo.getApplyProductTypeCode());
				model.addAttribute("productList", productList);
			}else{//录入产品信息时需要限定产品机构权限
				String orgId = UserUtils.getUser().getCompany().getId();
			 	Product product = new Product();
			 	product.setCompany(new Office(orgId));
			 	product.setProductTypeCode(creApplyInfo.getApplyProductTypeCode());
			 	List<Product> productList = productService.findCoProductByType(product);
				
				model.addAttribute("productList", productList);
			}
		}
		//如果申请贷款信息填写过，根据申请的产品ID，获取该产品的所有期限配置信息
		if(StringUtils.isNotEmpty(creApplyInfo.getApplyProductId())){
			List<ProductPeriod>  productPeriodList = productPeriodService.findUseablePeriodByProductId(creApplyInfo.getApplyProductId());
			model.addAttribute("productPeriodList", productPeriodList);
		}
		//查询动态表单配置信息--这里的1表示查询的显示位置：贷款申请信息
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("1", creApplyInfo.getApplyProductTypeCode());
		model.addAttribute("creDataGroupTableList", creDataGroupTableList);
		if(null!=creDataGroupTableList){
			creDataGroupTableService.dynaResultQurey(actTaskParam.getApplyNo(), creDataGroupTableList);
		}
		
		model.addAttribute("actTaskParam", actTaskParam);
		
		if("true".equals(readOnly)){
			model.addAttribute("readOnly", true);
		}
		return "/app/credit/loanApply/loanApply_form";
	}
	
	/**
	 * @reqno:H1510080094
	 * @date-designer:2015年10月19日-songmin
	 * @date-author:2015年10月19日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_动态表单栏数据
	 * 		保存贷款申请信息的动态表单记录
	 * 		根据申请的产品类型（内含2个隐含条件：所在根公司、显示位置：贷款申请信息）获取所有动态表字段信息，
	 * 		然后根据字段信息从request中取值，最后根据动态表单配置表和产品动态配置表的相关配置（表名，字段对应关系）将操作人员填写的记录存储到数据库中
	 */
	/**
	 * @reqno:H1510080095
	 * @date-designer:2015年10月20日-songmin
	 * @date-author:2015年10月20日-songmin:CRE_信贷审批_申请录入_贷款申请信息_更新、保存_贷款申请信息数据
	 * 		保存贷款申请信息
	 */
	@RequestMapping("/saveForm")
	public String saveLoanApplyInfo(CreApplyInfo creApplyInfo,ActTaskParam actTaskParam,
			HttpServletRequest request,Model model){
		//贷款申请信息固定属性保存
		//补充信息说明-字符反转译
		if(null!=creApplyInfo&&StringUtils.isNotEmpty(creApplyInfo.getDescription())){
			String descUnes = StringEscapeUtils.unescapeHtml4(creApplyInfo.getDescription());
			creApplyInfo.setDescription(descUnes);
		}
		if(creApplyInfo.getMonthRepayAmount()==null||"".equals(creApplyInfo.getMonthRepayAmount())){
			creApplyInfo.setMonthRepayAmount(null);
		}
		creApplyInfoService.loanApplyRecord(creApplyInfo);
		//动态表单保存
		//查询动态表单配置信息--这里的1表示查询的显示位置：贷款申请信息
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("1", creApplyInfo.getApplyProductTypeCode());
		if(null!=creDataGroupTableList){
			for (CreDataGroupTable creDataGroupTable : creDataGroupTableList) {
				//用来存储产品动态表单配置的属性和值信息
				Map entryMap = new HashMap();
				
				List<CreProFromColumn> creProFromColumnList = creDataGroupTable.getProFromColumnList();
				if(null!=creProFromColumnList && creProFromColumnList.size()>0){
					boolean insertFlag = false;//默认是修改操作
					//系统默认值保存  APPLY_ID、ID
					String applyid = request.getParameter("APPLY_ID_"+creDataGroupTable.getId());
					if(StringUtils.isEmpty(applyid)){
						applyid = actTaskParam.getApplyNo();
					}
					
					String id = request.getParameter("ID_"+creDataGroupTable.getId());
					if(StringUtils.isEmpty(id)){
						/**
						 * @reqno:H1510210079
						 * @date-designer:2015年11月3日-songmin
						 * @date-author:2015年11月3日-songmin:CRE_信贷审批_总监审批_集成展示：贷款申请信息、客户信息、内匹配信息、征信信息
						 * 		系统存在多个栏目指向同一张表的情况，所以这里还需要判断同一批次是否存在保存到同一张表的情况出现
						 */
						Map dyMapData = creDataGroupTableService.dynaFormQuery(creDataGroupTable.getDataTableName(), applyid);
						if(null==dyMapData || dyMapData.size()==0){
							id = IdGen.uuid();
							insertFlag = true;
							//只在新增时添加下面2个参数
							entryMap.put("APPLY_ID", applyid);
							entryMap.put("ID", id);
						}else{
							id  = dyMapData.get("ID").toString();
						}
					}
					
					for (CreProFromColumn creProFromColumn : creProFromColumnList) {
						//前后台约定表单的key为columnCode_dataGroupId格式
						String reqKey = creProFromColumn.getColumnCode()+"_"+creProFromColumn.getDataGroupId();
						String reqVal = request.getParameter(reqKey);
						
						entryMap.put(creProFromColumn.getColumnCode(), reqVal);
					}
					
					if(insertFlag){
						creDataGroupTableService.dynaResultInsert(creDataGroupTable.getDataTableName(), entryMap);
					}else{
						creDataGroupTableService.dynaResultUpdate(creDataGroupTable.getDataTableName(), entryMap, id);
					}
				}
			}
		}
		addMessage(model,"保存成功！");
		return loanApplyInfoForm(actTaskParam,model,"false");
	}
	/**
	 * @reqno: H1511120073
	 * @date-designer:20151116-lirongchao
	 * @db-z : ACC_PRODUCT_PERIOD :PERIOD_VALUE
	 * @db-j : SYS_DICT :label
	 * @date-author:20151116-lirongchao: 1.申请录入、录入复核_贷款申请信息中，当贷款申请信息栏目中的产品期限、还批贷额度、
	 									款方式变动时，页面自动调用后台，计算出最新的月还款额度，反填到批复信息栏目中的“月还款”数据项中；
	 									同时在提交时，把月还款保存到数据库中；
										2.备注：计算方法在账务系统中实现，跨系统调用，接口参数：额度、产品期限、产品年利率、还款方式；
										当前环节-跳转后台计算月还款金额
	 */
	@ResponseBody
	@RequestMapping("/monthLimit")
	public String monthLimit(CreApplyInfo creApplyInfo,HttpServletRequest request){
		String appIeriod = request.getParameter("appIeriod");
		ProductPeriod productPeriod = productPeriodService.get(appIeriod);//根据产品期限id查询产品期限信息
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("applyAmount", creApplyInfo.getApplyAmount());//申请额度
		map.put("applyPeriodValue", productPeriod.getPeriodValue());//产品期限值
		map.put("applyYearRate", productPeriod.getYearRate());//年利率
		map.put("loanRepayType", creApplyInfo.getLoanRepayType());//还款方式
		HttpConnector connector = new HttpConnector();
		String rsContentObj="";
		try {
		String rs = connector.request(map, "monthLimit", "ACC_VISIT_URL");
		ObjectMapper obmap=new ObjectMapper();
			Map remap=obmap.readValue(rs, Map.class);
			rsContentObj = remap.get("rsContent").toString();
		} catch (Exception e) {
			rsContentObj="eror";
			org.apache.log4j.Logger.getLogger(this.getClass()).error("", e);
		} 
		return rsContentObj;
	}
}

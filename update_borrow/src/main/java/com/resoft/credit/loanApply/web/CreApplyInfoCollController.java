package com.resoft.credit.loanApply.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicSet.service.CreDataGroupTableService;
import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.loanApply.entity.CreCustCarInfo;
import com.resoft.credit.loanApply.entity.CreCustCompanyInfo;
import com.resoft.credit.loanApply.entity.CreCustContact;
import com.resoft.credit.loanApply.entity.CreCustHousingInfo;
import com.resoft.credit.loanApply.entity.CreCustInfo;
import com.resoft.credit.loanApply.service.CreApplyInfoService;
import com.resoft.credit.loanApply.service.CreApplyRegisterService;
import com.resoft.credit.loanApply.service.CreCustCarInfoService;
import com.resoft.credit.loanApply.service.CreCustCompanyInfoService;
import com.resoft.credit.loanApply.service.CreCustContactService;
import com.resoft.credit.loanApply.service.CreCustHousingInfoService;
import com.resoft.credit.loanApply.service.CreCustInfoService;
import com.resoft.credit.product.entity.ProductPeriod;
import com.resoft.credit.product.service.ProductPeriodService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * CRE_信贷审批_申请录入  贷款申请Action
 */
@Controller
@RequestMapping(value="/f/credit/loanApply")
public class CreApplyInfoCollController extends BaseController{
	
	@Autowired
	private CreApplyInfoService  creApplyInfoService;//贷款申请信息表service
	@Autowired
	private CreCustInfoService creCustInfoService;
	@Autowired
	private CreCustCarInfoService creCustCarInfoService;
	@Autowired
	private CreCustHousingInfoService creCustHousingInfoService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CreCustContactService creCustContactService;//联系人列表service
	@Autowired
	private CreCustCompanyInfoService creCustCompanyInfoService;
	@Autowired
	private CreApplyRegisterService creApplyRegisterService;//客户申请信息表service
	@Autowired
	private ProductPeriodService productPeriodService;//产品期限service
	@Autowired
	private CreDataGroupTableService creDataGroupTableService;
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
	@RequestMapping("/checkExists")
	public String checkProductType(ActTaskParam actTaskParam,Model model){
		
		//大于0表示登记过产品类型，反之表示没有登记过
		long result = creApplyInfoService.checkExists(actTaskParam.getApplyNo());

		model.addAttribute("actTaskParam", actTaskParam);
		if(result>0){

			String productType="";	//产品类型
			CreApplyInfo creApplyInfo =new CreApplyInfo();
			creApplyInfo.setApplyNo(actTaskParam.getApplyNo());
			List<CreApplyInfo> onlyPructTypeApplyInfo=creApplyInfoService.findList(creApplyInfo);
			if(onlyPructTypeApplyInfo!=null&&onlyPructTypeApplyInfo.size()>0){
				productType=onlyPructTypeApplyInfo.get(0).getApplyProductTypeCode();
			}
			model.addAttribute("productType", productType);
			return "/app/credit/loanApply/loanApply_index";
		}else{
			CreApplyRegister  creApplyRegister  = creApplyRegisterService.findApplyRegisterInfoById(actTaskParam.getApplyNo());
			model.addAttribute("creApplyRegister", creApplyRegister);
			model.addAttribute("productType", "");//Spring的form：select标签需要一个属性字段，这里默认赋值空串
			return "/app/credit/loanApply/product_asign";
		}
	}
	@RequestMapping("/checkExistsradoy")
	public String checkExistsradoy(ActTaskParam actTaskParam,Model model,HttpServletRequest request){

		model.addAttribute("actTaskParam", actTaskParam);
			String productType="";	//产品类型
			CreApplyInfo creApplyInfo =new CreApplyInfo();
			creApplyInfo.setApplyNo(actTaskParam.getApplyNo());
			List<CreApplyInfo> onlyPructTypeApplyInfo=creApplyInfoService.findList(creApplyInfo);
			if(onlyPructTypeApplyInfo!=null&&onlyPructTypeApplyInfo.size()>0){
				productType=onlyPructTypeApplyInfo.get(0).getApplyProductTypeCode();
			}
			String orgid=request.getParameter("orgid");
			String rootCompanyId=request.getParameter("rootCompanyId");
			model.addAttribute("orgid", orgid);
			model.addAttribute("rootCompanyId", rootCompanyId);
			model.addAttribute("productType", productType);
			actTaskParam.setTitle("客户详情");
			return "/app/credit/loanApply/loanApply_indexradoycoll";
		
	}
	@RequestMapping(value = "/luru")
	public String luru(HttpServletRequest request, String applyId,
			String system, String optype, String productTypeId, ModelMap model) throws Exception {
		model.put("applyId", applyId);
		model.put("system", system);
		model.put("optype", optype);
		
		model.put("productTypeId", productTypeId);
		String url = Global.getConfig("ICMP_VISIT_URL");
		return "redirect:"+url+"/f/applyimage/icmpApplyImage/luru";
	}

	/**
	 * @reqno: H1511100160
	 * @date-designer:20151118-lirongchao
	 * @db-z :CRE_APPLY_INFO : id
	 * @db-z :CRE_PRODUCT : id
	 * @db-z :CRE_PRODUCT_PERIOD : id
	 * @db-j :CRE_APPLY_REGISTER : id
	 * @db-j :SYS_DICT : type
	 * @db-j :CRE_PRODUCT_PERIOD : id
	 * @date-author:20151118-lirongchao: 1.点击列表操作中的“客户详情”，弹出窗口，跨系统访问信贷审批系统，查看客户贷款申请信息、客户信息；
											2.点击列表操作中的“客户详情”，弹出窗口,窗口名称“客户详情”；
											3.页面布局：以tab页签形式显示，包括：贷款申请信息、客户信息；默认只加载“贷款申请信息”页签，其它页签只有在点击时才做加载；
											两页面显示的元素与信贷审批系统中申请录入保持不变；页面所有的表单均为只读，保存、新增、删除、修改按钮隐藏不显示；
											当前环节-加载申请贷款信息

	 */
	@RequestMapping("/loanApplyInfoForm")
	public String loanApplyInfoForm(ActTaskParam actTaskParam,Model model,String readOnly,HttpServletRequest request){
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

		//如果申请贷款信息填写过，根据申请的产品ID，获取该产品的所有期限配置信息
		if(StringUtils.isNotEmpty(creApplyInfo.getApplyProductId())){
			List<ProductPeriod>  productPeriodList = productPeriodService.findUseablePeriodByProductId(creApplyInfo.getApplyProductId());
			model.addAttribute("productPeriodList", productPeriodList);
		}
		String	rootCompanyId=request.getParameter("rootCompanyId");
		//查询动态表单配置信息--这里的1表示查询的显示位置：贷款申请信息
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("1", creApplyInfo.getApplyProductTypeCode(),rootCompanyId);
		model.addAttribute("creDataGroupTableList", creDataGroupTableList);
		if(null!=creDataGroupTableList){
			creDataGroupTableService.dynaResultQurey(actTaskParam.getApplyNo(), creDataGroupTableList);
		}
		
		model.addAttribute("actTaskParam", actTaskParam);
		
		if("true".equals(readOnly)){
			model.addAttribute("readOnly", true);
		}
		return "/app/credit/loanApply/loanApply_formcoll";
	}
	@RequestMapping("/info")
	public String forward(ActTaskParam actTaskParam,Model model,String readOnly){
		/**
		 * @reqno: H1512300002
		 * @date-designer:20151230-lirongchao
		 * @date-author:20151230-lirongchao: BUG-点击详情跳转到了登陆页面
		 * 									修改-点击详情时能正常访问详情页面
		 * 									当前环节-跨系统访问增加判断字段strideSystem
		 */		
		model.addAttribute("strideSystem", "1");
		//客户信息初始化
		CreCustInfo creCustInfo = new CreCustInfo();
		creCustInfo.setApplyNo(actTaskParam.getApplyNo());
		CreCustInfo creCustInfo_db = creCustInfoService.get(creCustInfo);
		model.addAttribute("creCustInfo", creCustInfo_db);
		//省市级联初始化数据
		//cityInit(creCustInfo_db, model);//2015年10月21日：追加现居地址的省市级联数据
		//初始化房产信息
		CreCustHousingInfo creCustHousingInfo = new CreCustHousingInfo();
		creCustHousingInfo.setApplyNo(actTaskParam.getApplyNo());
		creCustHousingInfo = creCustHousingInfoService.get(creCustHousingInfo);
		if(null==creCustHousingInfo){
			creCustHousingInfo = new CreCustHousingInfo();
		}
		model.addAttribute("creCustHousingInfo", creCustHousingInfo);
		//省市级联初始化数据
		//cityInit(creCustInfo_db, model ,creCustHousingInfo);//2015年10月21日：追加房屋信息中的省市级联初始化数据查询
		//初始化车辆信息
		CreCustCarInfo creCustCarInfo = new CreCustCarInfo();
		creCustCarInfo.setApplyNo(actTaskParam.getApplyNo());
		creCustCarInfo = creCustCarInfoService.get(creCustCarInfo);
		if(null==creCustCarInfo){
			creCustCarInfo = new CreCustCarInfo();
		}
		model.addAttribute("creCustCarInfo", creCustCarInfo);
		/**
		 * @reqno: H1510080107
		 * @date-designer:20151021-lirongchao
		 * @date-author:20151021-lirongchao: 需求-联系人列表数据项：复选择框、姓名、人员类型、移动电话、是否知晓本次贷款、住宅电话、单位名称
												列表排序：创建时间降序
												表头按钮：新增、修改、删除、详情
											当前环节-根据申请id查询联系人列表，将结果传给前台
		 */
		CreCustContact ccc=new CreCustContact();
		ccc.setApplyNo(actTaskParam.getApplyNo());
		List<CreCustContact> listccc=new ArrayList<CreCustContact>();
		listccc=creCustContactService.findList(ccc);
		model.addAttribute("listccc", listccc);
		
		//初始化工作信息（公司信息）
		CreCustCompanyInfo creCustCompanyInfo = new CreCustCompanyInfo();
		creCustCompanyInfo.setApplyNo(actTaskParam.getApplyNo());
		creCustCompanyInfo = creCustCompanyInfoService.get(creCustCompanyInfo);
		if(null==creCustCompanyInfo){
			creCustCompanyInfo = new CreCustCompanyInfo();
		}
		model.addAttribute("creCustCompanyInfo", creCustCompanyInfo);
		//省市级联初始化数据
		cityInit(creCustInfo_db, model ,creCustHousingInfo,creCustCompanyInfo);//2015年10月23日：追加公司信息中的省市级联初始化数据查询
		//查询动态表单配置信息--这里的2表示查询的显示位置：客户信息
//		CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
//		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("2", creApplyInfo.getApplyProductTypeCode());
//		model.addAttribute("creDataGroupTableList", creDataGroupTableList);
//		if(null!=creDataGroupTableList){
//			creDataGroupTableService.dynaResultQurey(actTaskParam.getApplyNo(), creDataGroupTableList);
//		}
		
		if("true".equals(readOnly)){
			model.addAttribute("readOnly", true);
		}
		return "/app/credit/loanApply/custInfo";
	}	

	private void cityInit(CreCustInfo creCustInfo,Model model,CreCustHousingInfo creCustHousingInfo,
			CreCustCompanyInfo  creCustCompanyInfo){
		//省份下拉列表数据
		Map param = Maps.newConcurrentMap();
		param.put("parentId", "1");//1表示中国区域
		List<Map<String, String>> regProvinceList = this.areaService.getTreeNode(param);
		LinkedHashMap<String, String> regProvinceMap = new LinkedHashMap<String, String>();
		if (null != regProvinceList && regProvinceList.size() > 0) {
			for (Map<String, String> mp : regProvinceList) {
				regProvinceMap.put(mp.get("id"), mp.get("name"));
			}
		}
		model.addAttribute("regProvinceMap", regProvinceMap);
		model.addAttribute("contProvinceMap", regProvinceMap);//省份数据必然是一致的
		model.addAttribute("hsProvinceMap", regProvinceMap);//省份数据必然是一致的
		model.addAttribute("comProvinceMap", regProvinceMap);//省份数据必然是一致的
		
		//市级下拉列表数据
		LinkedHashMap<String, String> regCityMap = new LinkedHashMap<String, String>();
		if(StringUtils.isNotEmpty(creCustInfo.getRegProvince())){
			param.clear();
			param.put("parentId", creCustInfo.getRegProvince());//根据省份ID获取市级数据信息
			List<Map<String, String>> regCityList = this.areaService.getTreeNode(param);
			if (null != regCityList && regCityList.size() > 0) {
				for (Map<String, String> mp : regCityList) {
					regCityMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		model.addAttribute("regCityMap", regCityMap);
		relateCityInit(creCustInfo.getRegCity(), creCustInfo.getContCity(), regCityMap,
				"contCityMap", creCustInfo.getContProvince(), model);//现居地市级联查询
		relateCityInit(creCustInfo.getRegCity(), creCustHousingInfo.getHsCity(), regCityMap,
				"hsCityMap", creCustHousingInfo.getHsProvince(), model);//房产信息市级联查询
		relateCityInit(creCustInfo.getRegCity(), creCustCompanyInfo.getComCity(), regCityMap,
				"comCityMap", creCustCompanyInfo.getComProvince(), model);//公司信息市级联查询
		
		//区、县级下拉列表数据
		LinkedHashMap<String, String> regDistinctMap = new LinkedHashMap<String, String>();
		if(StringUtils.isNotEmpty(creCustInfo.getRegCity())){
			param.clear();
			param.put("parentId", creCustInfo.getRegCity());//根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					regDistinctMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		model.addAttribute("regDistinctMap", regDistinctMap);
		relateCityInit(creCustInfo.getRegDistinct(), creCustInfo.getContDistinct(), regDistinctMap,
				"contDistinctMap", creCustInfo.getContCity(), model);//现居地区级联查询
		relateCityInit(creCustInfo.getRegDistinct(), creCustHousingInfo.getHsDistinct(), regDistinctMap,
				"hsDistinctMap", creCustHousingInfo.getHsCity(), model);//房产信息区级联查询
		relateCityInit(creCustInfo.getRegDistinct(), creCustCompanyInfo.getComDistinct(), regDistinctMap,
				"comDistinctMap", creCustCompanyInfo.getComCity(), model);//公司信息区级联查询
	}
	private void relateCityInit(String cityFlagVal,String currCityVal,LinkedHashMap<String, String> cityFlagMap,
			String currkey,String parentID,Model model){
		if(null!=cityFlagVal && cityFlagVal.equals(currCityVal)){//户籍地城市代码等于现居地城市代码
			model.addAttribute(currkey, cityFlagMap);
		}else{
			LinkedHashMap<String, String> queryMap = new LinkedHashMap<String, String>();
			if(StringUtils.isNotEmpty(parentID)){
				Map param = Maps.newConcurrentMap();
				param.put("parentId", parentID);
				List<Map<String, String>> contCityList = this.areaService.getTreeNode(param);
				if (null != contCityList && contCityList.size() > 0) {
					for (Map<String, String> mp : contCityList) {
						queryMap.put(mp.get("id"), mp.get("name"));
					}
				}
			}
			model.addAttribute(currkey, queryMap);
		}
	}
	/**
	 * @reqno: H1512300002
	 * @date-designer:20151230-lirongchao
	 * @date-author:20151230-lirongchao: BUG-点击详情跳转到了登陆页面
	 * 									修改-点击详情时能正常访问详情页面
	 * 									当前环节-点击详情时不进行登陆拦截
	 */		
	@RequestMapping("/viewContactInfo")	
	public String viewContactInfo(CreCustContact creCustContact,Model model,HttpServletRequest request){
		List<CreCustContact> listcus=creCustContactService.findList(creCustContact);
		model.addAttribute("creCustContact", listcus.get(0));
		return"/app/credit/loanApply/creCustContactFormeqd";
	}	
}

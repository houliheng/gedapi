package com.resoft.credit.loanApply.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.dynamicSet.entity.CreDataGroupTable;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.resoft.credit.dynamicSet.service.CreDataGroupTableService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * @reqno:H1510080096
 * @date-designer:2015年10月20日-songmin
 * @date-author:2015年10月20日-songmin:客户信息Action类
 */
@Controller("CreCustInfoController")
@RequestMapping("${adminPath}/credit/custInfo")
public class CreCustInfoController extends BaseController{
	@Autowired
	private AreaService areaService;
	@Autowired
	private CreCustInfoService creCustInfoService;
	@Autowired
	private CreCustHousingInfoService creCustHousingInfoService;
	@Autowired
	private CreCustContactService creCustContactService;//联系人列表service
	@Autowired
	private CreCustCarInfoService creCustCarInfoService;
	@Autowired
	private CreCustCompanyInfoService creCustCompanyInfoService;
	@Autowired
	private CreDataGroupTableService creDataGroupTableService;
	@Autowired
	private CreApplyInfoService  creApplyInfoService;//贷款申请信息表service
	@Autowired
	private CreApplyRegisterService creApplyRegisterService;
	@Autowired
	private CompanyInfoService companyInfoService;//企业客户信息表service
	@Autowired
	private IndustryService industryService;//行业分类service
	/**
	 * @reqno:H1510080096
	 * @date-designer:2015年10月20日-songmin
	 * @date-author:2015年10月20日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
	 * 		跳转客户基本信息页面
	 */
	/**
	 * @reqno:H1510080098
	 * @date-designer:2015年10月21日-songmin
	 * @date-author:2015年10月21日-songmin:房产信息初始化
	 */
	/**
	 * @reqno:H1510080100
	 * @date-designer:2015年10月21日-songmin
	 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_车辆信息【默认数据项】展现
	 * 	查询客户登记的车辆信息
	 * 	查询条件：applyId  
	 */
	/**
	 * @reqno:H1510080102
	 * @date-designer:2015年10月23日-songmin
	 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_工作信息【默认数据项】展现
	 * 		查询客户工作信息
	 * 		查询条件：applyId 
	 */
	/**
	 * @reqno:H1510080097
	 * @date-designer:2015年10月27日-songmin
	 * @date-author:2015年10月27日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息、房产信息、车辆信息、工作信息【动态配置数据项】
	 * 		添加动态表单数据初始化-
	 * 			通过直接调用creDataGroupTableService.findDataSets方法初始化动态栏目信息、表单配置信息
	 * 			通过creDataGroupTableService.dynaResultQurey初始化动态表单数据信息
	 */
	/**
	 * @reqno:H1510080112
	 * @date-designer:2015年10月27日-songmin
	 * @date-author:2015年10月27日-songmin:CRE_信贷审批_贷款初审_客户信息_查看
	 * 		在“初审”环节，当点击“客户信息”页签时，显示客户信息，全部为只读，具体内容与申请录入保持一致
	 * 		初审环节调用该方法是传入参数readOnly，用于标示页面元素只读，页面在检测到该值存在时将所有可填写元素设置只读属性
	 */
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 	跳转客户信息页面
	 * 	目前，由于企业客户和个人客户共用流程，所以需要根据参数（applyId）来进行个人和企业客户的判断，然后再根据客户类型跳转相应的页面，
	 *  这里将原本的forward方法独立处理出来只做客户类型（企业或个人）的判断，然后根据客户类型调用不同的方法进行处理
	 * 	如果客户类型获取失败时，不返回任何内容
	 */
	@RequestMapping("/info")
	public String checkCustType(ActTaskParam actTaskParam,Model model,String readOnly,String oper){
		//从客户登记表中获取客户类型
		CreApplyRegister applyRegister = creApplyRegisterService.findApplyRegisterInfoById(actTaskParam.getApplyNo());
		if(null != applyRegister){
			if("1".equals(applyRegister.getCustType())){//个人客户
				return custInfo(actTaskParam, model, readOnly, oper);
			}else if("2".equals(applyRegister.getCustType())){//企业客户
				return companyInfo(actTaskParam, model, readOnly, oper);
			}
		}
		logger.error("获取客户信息时无法判定客户类型："+actTaskParam.getApplyNo());
		return null;
	}
	
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 	获取企业基本信息
	 */
	/**
	 * @reqno:H1512210029
	 * @date-designer:2016年1月8日-songmin
	 * @date-author:2016年1月8日-songmin:动态表单初始化
	 */
	public String companyInfo(ActTaskParam actTaskParam,Model model,String readOnly,String oper){
		//获取企业基本信息
		CompanyInfo companyInfo = companyInfoService.getByApplyNo(actTaskParam.getApplyNo());
		model.addAttribute("companyInfo", companyInfo);
		
		//基本信息省市级联处理
		//省份下拉列表数据-注册地址+经营地址
		LinkedHashMap<String, String> provinceMap = loadAreaData("1");//这里的1表示全国
		model.addAttribute("regProvinceMap", provinceMap);
		model.addAttribute("operateProvinceMap", provinceMap);
		//市级下拉列表数据-注册地址
		LinkedHashMap<String, String> regCityMap = loadAreaData(companyInfo.getRegProvince());
		model.addAttribute("regCityMap", regCityMap);
		//经营地址（市）级联获取
		relateCityInit(companyInfo.getRegCity(), companyInfo.getOperateCity(), regCityMap, "operateCityMap", companyInfo.getOperateProvince(), model);
		//区县下拉列表数据-注册地址
		LinkedHashMap<String, String> regDistinctMap = loadAreaData(companyInfo.getRegCity());
		model.addAttribute("regDistinctMap", regDistinctMap);
		//经营地址（区县）级联获取
		relateCityInit(companyInfo.getRegDistinct(), companyInfo.getOperateDistinct(), regDistinctMap, "operateDistinctMap", companyInfo.getOperateCity(), model);
		
		//初始化行业门类
		List<Industry> mainCategoryList =  industryService.findByParentCode("root");
		model.addAttribute("mainCategoryList", mainCategoryList);
		//初始化行业大类
		if(StringUtils.isNotEmpty(companyInfo.getCategoryLarge())){
			List<Industry> largeCategoryList =  industryService.findByParentCode(companyInfo.getCategoryMain());
			model.addAttribute("largeCategoryList", largeCategoryList);
		}
		//初始化行业中类
		if(StringUtils.isNotEmpty(companyInfo.getCategoryMedium())){
			List<Industry> mediumCategoryList =  industryService.findByParentCode(companyInfo.getCategoryLarge());
			model.addAttribute("mediumCategoryList", mediumCategoryList);
		}
		//初始化行业小类
		if(StringUtils.isNotEmpty(companyInfo.getCategorySmall())){
			List<Industry> smallCategoryList =  industryService.findByParentCode(companyInfo.getCategoryMedium());
			model.addAttribute("smallCategoryList", smallCategoryList);
		}
		
		//初始化动态表单
		CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("2", creApplyInfo.getApplyProductTypeCode());
		model.addAttribute("creDataGroupTableList", creDataGroupTableList);
		if(null!=creDataGroupTableList){
			creDataGroupTableService.dynaResultQurey(actTaskParam.getApplyNo(), creDataGroupTableList);
		}
		
		if("true".equals(readOnly)){
			model.addAttribute("readOnly", true);
		}
		return "/app/credit/loanApply/companyInfo";
	}
	
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 	省市级联数据（下级数据）加载
	 */
	private LinkedHashMap<String, String> loadAreaData(String areaCode){
		Map param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if(StringUtils.isNotEmpty(areaCode)){
			param.put("parentId", areaCode);//根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}
	
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 	这里将原来的跳转个人客户信息的方法进行了改名，在个人、企业客户共用跳转以后，方法本身只处理逻辑，不再作为url对外提供功能
	 * 	@RequestMapping("/info")
	 * 	public String forward(ActTaskParam actTaskParam,Model model,String readOnly,String oper){
	 */
	public String custInfo(ActTaskParam actTaskParam,Model model,String readOnly,String oper){
		String orenApplyId = actTaskParam.getApplyNo();
		boolean operFlag = false;
		/**
		 * @reqno:H1511100082
		 * @date-designer:2015年11月13日-songmin
		 * @date-author:2015年11月13日-songmin:CRE_信贷审批_申请录入_客户信息
		 * 	流程在走到申请录入环节，根据证件类型、证件号，判断之前是否申请过，如申请过，
		 * 	则把最近一次申请过的基本信息、房产信息、车辆信息、工作信息、联系人信息，初始化到当前申请中来，
		 *	用户只需在此基础上更改客户信息即可，方便、提高客户信息录入操作；
		 *
		 *	这里查询客户基本信息在很多地方都有用到，所以在申请录入界面传入一个oper=apply的参数，用于标识是申请录入操作，申请录入时查询客户等级表，
		 *	根据证件类型和证件号码取得用户上次的申请ID，以此申请ID来获取用户上次填写的用户信息
		 */
		//客户信息初始化
		CreCustInfo creCustInfo = new CreCustInfo();
		creCustInfo.setApplyNo(actTaskParam.getApplyNo());
		CreCustInfo creCustInfo_db = creCustInfoService.get(creCustInfo);
		//上面获取到本次用户信息，判断其中几个必填字段是否存在，如果存在表示录入过客户信息，如果不存在，表示尚未录入过客户信息则加载同证件上次的客户信息
		//这里以出生日期和性别作为是否填写过客户信息的判断标准
		if(null==creCustInfo_db.getBirthday() || StringUtils.isEmpty(creCustInfo_db.getSex())){
			//没有填写过--并且是申请录入页面跳转过来的
			if(StringUtils.isNotEmpty(oper) && "apply".equals(oper)){//通过申请录入过来
				//暂存本次的基本数据
				String custName = creCustInfo_db.getCustName();
				String idType= creCustInfo_db.getIdType();
				String idNum = creCustInfo_db.getIdNum();
				String id = creCustInfo_db.getId();
				//根据申请ID，获取上次的申请信息
				CreApplyRegister creApplyRegister =  creApplyRegisterService.findSimpleApplyRegisterInfoById(actTaskParam.getApplyNo());
				if(null!=creApplyRegister && StringUtils.isNotEmpty(creApplyRegister.getIdType())
						&& StringUtils.isNotEmpty(creApplyRegister.getIdNum())){
					List<CreApplyRegister> creApplyRegisterList = creApplyRegisterService.findApplyRegisterByIdCard(creApplyRegister);
					if(null!=creApplyRegister && creApplyRegisterList.size()==2){//找到同证件的用户则吧当前的申请ID替换为上次的申请ID，在最后再把申请ID替换回来
						creApplyRegister = creApplyRegisterList.get(1);//因为查询时没有排除本身数据，所以这里需要取第二条数据
						actTaskParam.setApplyNo(creApplyRegister.getId());
						operFlag = true;
						//查询上次同证件的客户信息数据
						creCustInfo.setApplyNo(actTaskParam.getApplyNo());
						creCustInfo_db = creCustInfoService.get(creCustInfo);
						//将暂存数据放到上次的数据中
						creCustInfo_db.setCustName(custName);
						creCustInfo_db.setIdType(idType);
						creCustInfo_db.setIdNum(idNum);
						creCustInfo_db.setId(id);
					}
				}
			}
		}
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
		//先查询本次流程是存在联系人信息
		//流程在走到申请录入环节，根据证件类型、证件号，判断之前是否申请过，如申请过，则把最近一次申请过的基本信息、房产信息、车辆信息、工作信息、联系人信息，初始化到当前申请中来，用户只需在此基础上更改客户信息即可，方便、提高客户信息录入操作；
		//该需求中由于联系人信息是单独保存的，所以这里需要先进行后台进行保存
		CreCustContact ccc=new CreCustContact();
		ccc.setApplyNo(orenApplyId);
		List<CreCustContact> listccc = creCustContactService.findList(ccc);
		if((null==listccc || listccc.size()<1) && operFlag ){//如果本次流程不存在联系人记录，且客户信息取的为上次登记的客户信息，则查询上次联系人记录
			ccc=new CreCustContact();
			ccc.setApplyNo(actTaskParam.getApplyNo());
			listccc = creCustContactService.findList(ccc);
			//联系人信息如果存在上次申请保存的联系人信息，先把上次的联系人数据复制到当前申请中（applyId更新，其他字段原样保存）
			if(null!=listccc && listccc.size()>0){
				for (CreCustContact newCreCustContact : listccc) {
					newCreCustContact.setId(null);
					newCreCustContact.setApplyNo(orenApplyId);
					newCreCustContact.setCreateTime(Calendar.getInstance().getTime());
					creCustContactService.save(newCreCustContact);
				}
			}
		}
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
		
		/**
		 * @reqno:H1512260013
		 * @date-designer:2015年12月29日-songmin
		 * @date-author:2015年12月29日-songmin:内匹配信息存在的客户在录入复核环节中客户信息没有加载过来
		 * @db-z:cre_cust_car_info:ID、APPLY_ID
		 * @db-z:cre_cust_company_info:ID、APPLY_ID
		 * @db-z:cre_cust_contact:ID、APPLY_ID
		 * @db-z:cre_cust_housing_info:ID、APPLY_ID
		 * @db-z:cre_cust_info:ID、APPLY_ID
		 * @db-j:sys_dict:LABEL、VALUE、TYPE
		 * 2015年12月28日：以前未在后台数据库中同步数据，需要页面点击保存按钮才进行后台数据的同步，现在更改为内匹配成功则直接在后台进行数据同步
		 * 客户信息、房产信息、车辆信息、公司信息同步直接调用creCustInfoService.updateCustInfo（...）方法完成，调用之前清空房产信息、车辆信息、公司信息ID，客户信息不需要清空（因为在客户登记时，该记录便已经存在）
		 * 动态表单同步调用creDataGroupTableService.dynaResultInsert(...)完成同步，调用前判断本次和内匹配的贷款申请的产品类型是否一致，只有一致的情况下才进行动态表单同步
		 * 数据同步前都需要将内匹配的ID和APPLY_ID进行重置处理
		 */
		//查询动态表单配置信息--这里的2表示查询的显示位置：客户信息
		//2015年11月23日：初始化上次的客户信息由于可能存在2次选择的产品类型不同的情况，这里暂时不初始化动态表单
		boolean dyFlag = true;//动态表单数据同步标记位
		CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
		if(operFlag){//如果贷款申请信息是获取的内匹配信息
			CreApplyInfo creApplyInfoF =creApplyInfoService.findLoanInfoByApplyId(orenApplyId);//当前的贷款申请信息
			if(null == creApplyInfo || !(creApplyInfoF.getApplyProductTypeCode().equals(creApplyInfo.getApplyProductTypeCode()))){
				creApplyInfo = creApplyInfoF;//2次的贷款申请信息不一致，只获取本次的贷款申请信息
				dyFlag = false;//2次的贷款申请信息不一致，不进行动态表单的数据同步
			}
		}
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("2", creApplyInfo.getApplyProductTypeCode());
		model.addAttribute("creDataGroupTableList", creDataGroupTableList);
		if(null!=creDataGroupTableList){
			//2015年11月23日：初始化上次的客户信息由于可能存在2次选择的产品类型不同的情况，这里暂时不初始化动态表单
			creDataGroupTableService.dynaResultQurey(creApplyInfo.getApplyNo(), creDataGroupTableList);
		}
		
		if(operFlag && dyFlag){//如果动态表单数据是从上次获取到的数据，即actTaskParam.getApplyNo()是使用的上次同证件的申请ID-那么将动态表单数据进行同步
			if(null != creDataGroupTableList && creDataGroupTableList.size() > 0){
				for (CreDataGroupTable dtTable : creDataGroupTableList) {
					if(null != dtTable && null != dtTable.getResultMap()){//存在配置表单项
							Map rp = dtTable.getResultMap();
							rp.remove("APPLY_ID");//移除上次的数据中的ID信息
							rp.remove("ID");
							rp.put("ID", IdGen.uuid());//重置为本次的ID和Apply_ID
							rp.put("APPLY_ID", orenApplyId);
							creDataGroupTableService.dynaResultInsert(dtTable.getDataTableName(), rp);
					}
				}
			}
		}
		
//		//2015年11月23日：不再初始化上次的客户信息，所以不需要进行申请ID和ID的处理了
//		//如果本次的动态表单数据是从上次贷款申请中获取的，这里就需要将id和applyId重置为null
		//已办任务或其他只读节点标记，前台通过该标记设置表单只读
		if("true".equals(readOnly)){
			model.addAttribute("readOnly", true);
		}
		
		if(operFlag){//operFlag=true表明上面取的所有内容都是从上次登记的客户信息中获取得到的
			actTaskParam.setApplyNo(orenApplyId);//替换回当前的applyID，页面上所有关于applyI的地方都需要从actTaskParam中获取
			
			//creCustInfo.setId(null);基本信息不能重置，因为客户信息是在产品类型选择时就初始化了得，所以客户信息本身是必然存在的
			creCustHousingInfo.setId(null);
			creCustCompanyInfo.setId(null);
			creCustCarInfo.setId(null);
			//如果是从上次登记的信息中获取的数据，则将上次的数据直接作为本次客户信息的基础数据录入
			creCustInfo.setApplyNo(orenApplyId);
			creCustHousingInfo.setApplyNo(orenApplyId);
			creCustCompanyInfo.setApplyNo(orenApplyId);
			creCustCarInfo.setApplyNo(orenApplyId);
			creCustInfoService.updateCustInfo(creCustInfo_db, creCustHousingInfo,creCustCompanyInfo, creCustCarInfo);
		}
		return "/app/credit/loanApply/custInfo";
	}
	/**
	 * @reqno: H1510080108
	 * @date-designer:20151021-lirongchao
	 * @date-author:20151021-lirongchao: 需求-1.新增：
												   弹出新增窗体，窗体名称：新增联系人信息；新增数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
												   操作按钮：保存、关闭；
												   保存时前台会对每个字段进行非空、长度校验。
												   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
											2.备注：新增保存后，提示保存成功，关闭保存窗体，同时页面刷新联系人列表
										当前环节-跳转到新增页面,并获取到申请编号
	 */	
	@RequestMapping("/addcust")	
	public String addcust(CreCustContact creCustContact,Model model,HttpServletRequest request){
		
		/**
		 * @reqno: H1510080109
		 * @date-designer:20151023-lirongchao
		 * @date-author:20151023-lirongchao: 需求-1.修改：
	   弹出修改窗体，窗体名称：修改联系人信息；修改数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
	   操作按钮：保存、关闭；
	   保存时前台会对每个字段进行非空、长度校验。
	   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
	2. 详情：
	    弹出详情窗体，窗体名称：查看联系人信息；详情数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
	   操作按钮：关闭；
	3.删除：
	  在查询页面表格，选择1个及1个以上记录，点击删除，进行删除操作；
	4. 备注：修改、删除后，页面刷新联系人列表
											当前环节-点击修改时将联系人信息查询出来，传给前台。
											点击详情时同理，切将保存按钮值为不可见
		 */			
		String id=creCustContact.getId();	
		String stats=request.getParameter("stats");
		model.addAttribute("stats", stats);
		
		if("1".equals(stats)){
			List<CreCustContact> listcus=creCustContactService.findList(creCustContact);
			model.addAttribute("creCustContact", listcus.get(0));
			return"/app/credit/loanApply/creCustContactFormeqd";
		}
		else {
			
			if(id!=null&&!"".equals(id)){
				creCustContact=creCustContactService.get(id);
			}		
			model.addAttribute("creCustContact", creCustContact);
			
			return"/app/credit/loanApply/creCustContactForm";
		}
	}
	/**
	 * @reqno: H1510080108
	 * @date-designer:20151021-lirongchao
	 * @date-author:20151021-lirongchao: 需求-1.新增：
												   弹出新增窗体，窗体名称：新增联系人信息；新增数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
												   操作按钮：保存、关闭；
												   保存时前台会对每个字段进行非空、长度校验。
												   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
											2.备注：新增保存后，提示保存成功，关闭保存窗体，同时页面刷新联系人列表
										当前环节-保存联系人信息
	 */		
	@ResponseBody
	@RequestMapping("/savecust")	
	public Map<String, String> savecust(CreCustContact creCustContact,Model model, RedirectAttributes redirectAttributes){
		Map<String, String> map=new HashMap<String, String>();
		try {
			
			creCustContactService.save(creCustContact);
			map.put("jg", "BCCG");
		} catch (Exception e) {
			
			logger.error(e.toString());
			
			map.put("jg", "BCCW");
		}
		
		return map;
	}
	/**
	 * @reqno: H1510080109
	 * @date-designer:20151023-lirongchao
	 * @date-author:20151023-lirongchao: 需求-1.修改：
   弹出修改窗体，窗体名称：修改联系人信息；修改数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：保存、关闭；
   保存时前台会对每个字段进行非空、长度校验。
   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
2. 详情：
    弹出详情窗体，窗体名称：查看联系人信息；详情数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：关闭；
3.删除：
  在查询页面表格，选择1个及1个以上记录，点击删除，进行删除操作；
4. 备注：修改、删除后，页面刷新联系人列表
										当前环节-删除操作
	 */	
	@ResponseBody
	@RequestMapping("/deletecust")	
	public Map<String, String> deletecust(CreCustContact creCustContact,Model model, RedirectAttributes redirectAttributes){
		
		Map<String, String> map=new HashMap<String, String>();
		try {
			String[] str=creCustContact.getId().split(",");
			
			for(String id:str){
				CreCustContact coc=new CreCustContact();
				coc.setId(id);
				creCustContactService.delete(coc);
			}
			map.put("stats", "CG");
			
		} catch (Exception e) {
			logger.error(e.toString());
			map.put("stats", "CW");
		}
		
		return map;
	}
	/**
	 * @reqno: H1510080109
	 * @date-designer:20151023-lirongchao
	 * @date-author:20151023-lirongchao: 需求-1.修改：
   弹出修改窗体，窗体名称：修改联系人信息；修改数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：保存、关闭；
   保存时前台会对每个字段进行非空、长度校验。
   姓名、人员类型、移动电话、是否知晓本次贷款为必填项
2. 详情：
    弹出详情窗体，窗体名称：查看联系人信息；详情数据项包括：【姓名、人员类型、移动电话】、【是否知晓本次贷款、住宅电话、住宅地址】、【单位名称、单位地址、部门名称】、【职位名称】；
   操作按钮：关闭；
3.删除：
  在查询页面表格，选择1个及1个以上记录，点击删除，进行删除操作；
4. 备注：修改、删除后，页面刷新联系人列表
										当前环节-操作之后刷新联系人列表
	 */		
	@ResponseBody
	@RequestMapping("/reloadDataIdred")	
	public Map<String, String> reloadDataIdred(CreCustContact creCustContact,Model model, RedirectAttributes redirectAttributes){
		
		Map<String, String> map=new HashMap<String, String>();
		List<CreCustContact> listccc=new ArrayList<CreCustContact>();
		listccc=creCustContactService.findList(creCustContact);
		//拼接前台页面
		StringBuffer str=new StringBuffer();
		str.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">");
		/**
		 * @reqno:H1511230004
		 * @date-designer:2015年11月30日-songmin
		 * @date-author:2015年11月30日-songmin:低分辨率下页面样式更改
		 */
		str.append("	<tr>");
		str.append("		<th width=\"20px\"><input type=\"checkbox\" onclick=\"selectAll()\" name=\"all\" id=\"all\" /></th>");
		str.append("		<th width=\"13%\">姓名</th>");
		str.append("		<th width=\"13%\">人员类型</th>");
		str.append("		<th width=\"13%\">移动电话</th>");
		str.append("		<th width=\"13%\">是否知晓本次贷款</th>");
		str.append("		<th width=\"13%\">住宅电话</th>");
		str.append("		<th width=\"13%\">单位名称</th>");
		str.append("		<th>操作</th>");
		str.append("	</tr>");
		
		for(int i=0; i<listccc.size();i++){
			if(i%2==0){	
				str.append("<tr class='doubleRow'>");
			}
			else if(i%2==1) {
				str.append("<tr>");
				
			}
			str.append("<td width='20px;' id='id'><input type='checkbox' value='"+listccc.get(i).getId()+"'  name='id' "+"</td>");
			str.append("<td width='100px;' id='contactName'   class='title' title='"+listccc.get(i).getContactName()+"' >"+listccc.get(i).getContactName()+"</td>");
			str.append("<td width='100px;' id='contactRelations' class='title' title='"+listccc.get(i).getContactRelationsLabel()+"' >"+listccc.get(i).getContactRelationsLabel()+"</td>");
			str.append("<td width='150px;' id='conMobil' class='title' title='"+listccc.get(i).getConMobil()+"' >"+listccc.get(i).getConMobil()+"</td>");
			str.append("<td width='100px;' id='isKnow' class='title' title='"+listccc.get(i).getIsKnowLabel()+"' >"+listccc.get(i).getIsKnowLabel()+"</td>");
			str.append("<td width='150px;' id='phone' class='title' title='"+listccc.get(i).getPhone()+"' >"+listccc.get(i).getPhone()+"</td>");
			str.append("<td width='300px;' id='deptName' class='title' title='"+listccc.get(i).getDeptName()+"' >"+listccc.get(i).getDeptName()+"</td>");
			
			str.append("<td>"+
							"<a href='#' onclick=\"editcust('"+listccc.get(i).getId()+"')\">修改</a> &nbsp;"+
							"<a href='#' onclick=\"deletecust('"+listccc.get(i).getId()+"')\">删除</a> &nbsp;"+
							"<a href='#' onclick=\"editxqcust('"+listccc.get(i).getId()+"')\">详情</a> &nbsp;"
					
					+"</td>");
		
			
			str.append("</tr>");
		}
		
		
		
		str.append("</table>");
		map.put("tableDataId", str.toString());
		return map;
	}
	/**
	 * @reqno:H1510080096
	 * @date-designer:2015年10月21日-songmin
	 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
	 * 		初始化省市级联数据
	 * 			如果现居地与户籍地址是否一致等于是，则直接将户籍地的省市级联数据存入现居地的省市级联数据
	 * 			如果现居地与户籍地址是否一致等于否，则再次通过数据库查询现居地的省市级联数据
	 * 		后经修改为：直接判断户籍地址的市是否和现居地的市是否一致。如果一致直接从户籍地的市Map中获取数据，不一致才从数据库中获取
	 * 				户籍地的县是否和现居地的县是否一致，一致则直接从户籍地的县Map中获取数据，不一致则从数据库中获取数据
	 */
	/**
	 * @reqno:H1510080098
	 * @date-designer:2015年10月21日-songmin
	 * @date-author:2015年10月21日-songmin:通过封装解决代码冗余
	 */
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
	
	
	/**
	 * @reqno:H1510080098
	 * @date-designer:2015年10月21日-songmin
	 * @date-author:2015年10月21日-songmin:CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
	 * 		关联省市级联查询，目前在该页面存在4次的省市级联数据查询，每次都单独的查询，会造成代码务必冗余，所以这里对查询做了简单封装，这样将代码最大程度的缩减
	 * 		这里的基本思路就是：将第一次查询出的省市级联数据作为标记，后面的省市级联数据都根该标记做对比，如果一致则直接使用标记中查询出的省市级联数据，如果不一致才进行数据库的查询
	 * 			所以，在实际使用中，应该把最可能作为参照的省市级联数据作为单独查询的标记，在这里的查询中是把户籍地址的省市级联数据作为标记查询
	 * 
	 * @param cityFlagVal  用来参照的省市级联值-该值是同一个方法内首次查询的省市级联数据
	 * @param currCityVal  当前将要用于查询的省市级联数据的值   如果该值和上面的一样，就能说明将要查询的数据和用于参照的查询数据为同样的省市级联数据
	 * @param cityFlagMap  首次查询出的省市级联Map数据
	 * @param currkey	将要查询出的省市级联Map数据存储于Model中对应的Key
	 * @param parentID	用于查询省市级联数据的上级Key，用来查询该层级下的数据信息
	 * @param model
	 */
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
	 * @reqno:H1510080106
	 * @date-designer:2015年10月23日-songmin
	 * @date-author:2015年10月23日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_基本信息、房产信息、车辆信息、工作信息数据
	 * 		保存、修改   客户信息（基本信息、房产信息、车辆信息、工作信息数据）
	 * 		客户基本信息只做修改操作，其他信息（房产信息、车辆信息、工作信息）在首次保存时做新增操作，其他时候做修改操作
	 * @param creCustInfo  客户基本信息  
	 * @param creCustHousingInfo  房产信息
	 * @param creCustCompanyInfo  工作信息
	 * @param creCustCarInfo  车辆信息
	 */
	@RequestMapping("/save")
	public String saveCustInfo(CreCustInfo creCustInfo,CreCustHousingInfo creCustHousingInfo,
			CreCustCompanyInfo creCustCompanyInfo,CreCustCarInfo creCustCarInfo,
			HttpServletRequest request,
			ActTaskParam actTaskParam,Model model){
		//客户基本信息只会有修改操作
		creCustInfo.setId(request.getParameter("cust_id"));
		creCustHousingInfo.setId(request.getParameter("housing_id"));
		creCustCompanyInfo.setId(request.getParameter("company_id"));
		creCustCarInfo.setId(request.getParameter("car_id"));
		//固定表单修改
		creCustInfoService.updateCustInfo(creCustInfo, creCustHousingInfo, 
				creCustCompanyInfo, creCustCarInfo);
		
		/**
		 * @reqno:H1510080105
		 * @date-designer:2015年10月27日-songmin
		 * @date-author:2015年10月27日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_动态表单栏数据
		 * 		动态表单保存，该保存和贷款申请信息中的动态表单保存方式是一致的
		 * 		保存逻辑，通过查询数据库中关于客户信息的动态表单配置信息，然后从request中取相应的参数（页面上参数名的格式为参数_datagroupid的命名方式），
		 * 		最后根据insert、update封装成新增或修改语句，进行动态数据的保存
		 */
		/**
		 * @reqno:H1510210081
		 * @date-designer:2015年11月06日-songmin
		 * @date-author:2015年11月06日-songmin:增量代码1106
		 */
		saveDynamicTable(request, actTaskParam);
		
		addMessage(model, "保存成功！");
		return custInfo(actTaskParam, model ,"false", null);
	}
	
	/**
	 * @reqno:H1512210029
	 * @date-designer:2016年1月8日-songmin
	 * @date-author:2016年1月8日-songmin:CRE_信贷审批_企业_申请录入_客户信息_更新、保存_企业基本信息数据、动态表单栏数据
	 *  获取并保存动态表单
	 *  该方法可以为个人、企业客户动态表单保存共用
	 */
	public void saveDynamicTable(HttpServletRequest request,ActTaskParam actTaskParam){
		//查询贷款申请信息
		CreApplyInfo creApplyInfo =creApplyInfoService.findLoanInfoByApplyId(actTaskParam.getApplyNo());
		//根据申请的贷款类型获取默认的动态表单配置项
		List<CreDataGroupTable> creDataGroupTableList = creDataGroupTableService.findDataSets("2", creApplyInfo.getApplyProductTypeCode());
		if(null!=creDataGroupTableList){
			for (CreDataGroupTable creDataGroupTable : creDataGroupTableList) {
				//用来存储产品动态表单配置的属性和值信息
				Map entryMap = new HashMap();
				/**
				 * @reqno:H1510210083
				 * @date-designer:2015年11月7日-songmin
				 * @date-author:2015年11月7日-songmin:修复动态表单未配置表单项时的bug
				 */
				List<CreProFromColumn> creProFromColumnList = creDataGroupTable.getProFromColumnList();
				if(null!=creProFromColumnList  && creProFromColumnList.size()>0){
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
	}
	
	/**
	 * @return 
	 * @reqno:H1512210029
	 * @date-designer:2016年1月8日-songmin
	 * @date-author:2016年1月8日-songmin:CRE_信贷审批_企业_申请录入_客户信息_更新、保存_企业基本信息数据、动态表单栏数据
	 * 	保存企业客户基本信息及相关动态表单数据信息
	 *  保存成功后跳转企业客户基本信息页面
	 */
	@RequestMapping("/saveCompany")
	public String saveCompanyInfo(CompanyInfo companyInfo,
			HttpServletRequest request,RedirectAttributes redirectAttributes,
			ActTaskParam actTaskParam,Model model){
		//基本信息保存
		companyInfoService.update(companyInfo);
		//动态表单保存
		saveDynamicTable(request, actTaskParam);
		
		addMessage(model, "保存成功！");
		return companyInfo(actTaskParam, model, "false", null);
	}
}

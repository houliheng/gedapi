package com.resoft.credit.applyRegister.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.Customer;
import com.resoft.credit.applyRegister.service.CusListService;
import com.resoft.credit.blacklist.service.BlacklistService;
import com.resoft.credit.dynamicTable.service.DynamicTableService;
import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/credit/customer")
public class CusListController extends BaseController {
	private static final Logger logger = Logger.getLogger(CusListController.class);
	/**
	 * @reqno:H1509260004
	 * @date-designer:20151010-huangxuecheng
	 * @date-author:20151010-huangxuecheng:开发内容：
     * 1. 新增提交成功后，后台启动信贷审批流程；前台效果：提交成功后，相应办理流程用户，在待办任务列表，能够看到本条申请的待办任务，任务名称“申请录入”；
     * 2. 新增提交成功后，在【客户信息表】新增一条记录，把客户名称、证件类型、证件号从登记信息中同步过来; 前台效果：提交成功后，当用户在办理本申请，在申请录入环节的客户信息页签的基本信息中，能够看到客户名称、证件类型、证件号数据与登记时一致，且是只读状态；
	 * 这里：将ActTaskService进行注入，使用这个service接口方法实现业务需求
	 */
	@Autowired
	private CusListService cusListService;
	@Autowired
	private DynamicTableService dynamicTableService;
	@Autowired
	private ProductService productService;//产品sercice
	/**
	 * @reqno: H1509110039
	 * @date-designer:20150918-gengchang
	 * @date-author:20150918-gengchang:CRE_信贷审批_进件管理_客户登记列表_查询
	 * 						list方法方法findPage()方法查询分页数据，调用的方法有：findPage()查询分页数据方法，
	 * 								获得分页总数--getCount(),查询所有数据列表--findList()
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
//		return list_c(customer, model, request, response);//企业客户登记测试代码
		customer.setCreateBy(UserUtils.getUser());;
		Page<Customer> page = cusListService.findPage(new Page<Customer>(request, response), customer); 
        model.addAttribute("page", page);
		return "app/credit/applyRegister/cusList";
	}
	
	/**
	 * @reqno: H1509110041
	 * @date-designer:20150918-gengchang
	 * @date-author:20150918-gengchang:CRE_信贷审批_进件管理_客户登记列表_新增
	 * 						add()方法首先判断获取到的id是否为空，为空表示是新增页面，传值到前台进行新增操作。
	 * 								
	 */
	/**
	 * @reqno:H1509260004
	 * @date-designer:20151010-huangxuecheng
	 * @date-author:20151010-huangxuecheng:开发内容：
     * 1. 新增提交成功后，后台启动信贷审批流程；前台效果：提交成功后，相应办理流程用户，在待办任务列表，能够看到本条申请的待办任务，任务名称“申请录入”；
     * 2. 新增提交成功后，在【客户信息表】新增一条记录，把客户名称、证件类型、证件号从登记信息中同步过来; 前台效果：提交成功后，当用户在办理本申请，在申请录入环节的客户信息页签的基本信息中，能够看到客户名称、证件类型、证件号数据与登记时一致，且是只读状态；
	 * 做法：执行actTaskService的startProcess接口方法实现本功能
	 */
	@RequestMapping(value="add")
	public String add(Customer customer,Model model) {
//		当取得的id为空时，表示是客户信息添加；不为空则进入修改页面
		if(StringUtils.isNotBlank(customer.getId())){
			customer = cusListService.get(customer.getId());
			/**
			 * @reqno:H1512210023
			 * @date-designer:2015年12月30日-songmin
			 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
			 * 初始化产品名称下拉选项
			 */
			//这里是用于产品select标签的初始值创建
			CreApplyInfo applyInfo = customer.getApplyInfo();
			String orgId = UserUtils.getUser().getCompany().getId();
		 	Product product = new Product();
		 	product.setCompany(new Office(orgId));
		 	product.setProductTypeCode(applyInfo.getApplyProductTypeCode());
		 	List<Product> productList = productService.findCoProductByType(product);
			model.addAttribute("productList", productList);
		}else{
			customer.setRegisterDate(new Date());		//格式化转换可以直接用new Date()
		}
		model.addAttribute("customer", customer);
		return "app/credit/applyRegister/cusForm";
	}
	
	
	/**
	 * @reqno: H1510210067
	 * @date-designer:20151028-gengchang
	 * @date-author:20151028-gengchang: CRE_信贷审批_进件管理_客户登记列表_新增、修改_提交_做客户唯一性校验
	 *				根据证件类型、证件号，在客户申请信息表中判断本公司是否有状态为提交审批中，如果有，
	 *			提示“本客户已在系统中登记申请，处于审3批中，不能重复申请!”
  */
	@Autowired
	private BlacklistService blacklistService;
	@ResponseBody
	@RequestMapping(value="validate")
	public Map<String, String> validate(Customer customer,Model model, HttpServletRequest request, HttpServletResponse response){
		String idType = request.getParameter("idType");
		String idNum = request.getParameter("idNum");
		String mobile = request.getParameter("mobile");
		HashMap<String, String> maptype=new HashMap<String, String>();
		
		/**
		 * @reqno:H1512160007
		 * @date-designer:2015年12月24日-songmin
		 * @date-author:2015年12月24日-songmin:只在提交时（post=2）校验是否有处于提交待审核状态的登记信息，因为和下面的需求冲突，所以加了post参数的限定条件
		 */
		/**
		 * @reqno:H1512210023
		 * @date-designer:2015年12月30日-songmin
		 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
		 * 		点击“保存”或“提交”时，后台根据证件类型、证件号，在客户申请信息表中判断本是否有状态为提交审批中，如果有，提示“本客户已在系统中登记申请，处于审批中，不能重复申请!”；
		 */
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idType", idType);
		map.put("idNum", idNum);
		List<HashMap<String, String>> listmap=new ArrayList<HashMap<String,String>>();
		listmap=cusListService.statusResult(map);
		//判断客户申请状态，是否有处于审批中的
		for( int i=0;i<listmap.size();i++){
			maptype=listmap.get(i); //获取申请状态
			String applyStatus=maptype.get("STATUS");
			if("2".equals(applyStatus)){
				maptype.put("st", "status");
				return maptype;
			}
		}
		/**
		 * @reqno:H1512160007
		 * @date-designer:2015年12月24日-songmin
		 * @date-author:2015年12月24日-songmin:企业客户登记，加入黑名单校验功能
		 * 		点击“保存”或“提交”时，a.后台根据证件类型、证件号，到企业黑名单表中，判断是否当前被列入黑名单？如果有，提示“此证件号已被列入黑名单，无法登记！”；
		 */
		/**
		 * @reqno:H1512210023
		 * @date-designer:2015年12月30日-songmin
		 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
		 * 		点击“保存”或“提交”时，a.后台根据证件类型、证件号，到个人黑名单表中，判断是否当前被列入黑名单？如果有，提示“此证件号已被列入黑名单，无法登记！”；
		 */
		boolean isBlackName = blacklistService.isBlackName(map);
		if(isBlackName){//黑名单客户
			maptype.put("st", "blackName");
			return maptype;
		}
		/**
		 * @reqno:H1512210023
		 * @date-designer:2015年12月30日-songmin
		 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
		 * 		b.后台根据移动电话，到个人黑名单表中，判断此移动电话是否当前被列入黑名单？如果有，提示“此移动电话已被列入黑名单，无法登记！”；
		 */
		if("1".equals(customer.getCustType())){
			map.clear();
			map.put("mobile", mobile);
			isBlackName = blacklistService.isBlackName(map);
			if(isBlackName){//黑名单客户
				maptype.put("st", "blackName_mobile");
				return maptype;
			}
		}
		return maptype;
	}
	
		/**
		 * @reqno: H1509110042
		 * @date-designer:20150918-gengchang
		 * @date-author:20150918-gengchang: CRE_信贷审批_进件管理_客户登记列表_修改、删除
		 *			申请编号随机生成（已经更改为按规则生成）
	  */
	@RequestMapping(value="save")
	public String save(Customer customer, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		/**
		 * @reqno:H1511100082
		 * @date-designer:2015年11月23日-songmin
		 * @date-author:2015年11月23日-songmin:这里需要精确到秒，不然一天内多条业务时会出现排序bug
		 * 		所以，这里重新获取了时间，而不是从页面获取精确到天的日期
		 */
		customer.setRegisterDate(Calendar.getInstance().getTime());
		//@reqno:AH1509140009  新增时保存登记机构ID
		customer.setRegisterOrgId(UserUtils.getUser().getCompany().getId());
		//获取当前用户和id
		customer.setCreateBy(UserUtils.getUser());
		customer.getApplyStatus();
		/**
		 * @reqno: H1510210068
		 * @date-designer:20151023-lirongchao
		 * @db-j : cre_apply_register :SERIAL_NUMBER,COMPANY_ID
		 * @db-j : sys_office : parent_ids
		 * @date-author:20151023-lirongchao: 需求-1.在客户登记列表的新增或修改页面，
		 * 										当用户点击“提交”按钮，后台在提交前按规则生成申请编号、并保存；
												2.生成申请编号的规则：YYYYMMDD+00001+4位随机数；其中“00001”为序号，
												每个公司每天从00001开始递增
											当前环节-创建申请编号生成规则
		 */
		/**
		 * @reqno:H1601280119
		 * @date-designer:2016年1月28日-lirongchao
		 * @db-z:cre_apply_register:id,apply_id,ID_TYPE,ID_NUM,LIST_STATUS,cust_type,mobileNum
		 * @date-author:2016年1月28日-lirongchao:CRE_信贷审批_进件管理_客户登记列表_个人客户登记_改造
		 										因新表和旧表差异，所以需要修改表结构,相对应的也需要修改实体类
		 */		
		Customer cum=new Customer();
		cum.setRegisterDate(customer.getRegisterDate()); //登记时间
		
		Office company=UserUtils.getUser().getCompany();//公司id
		String companyid = company.getId();
		companyid = dynamicTableService.searchParentIdsByCompanyId(companyid);
		companyid = companyid.split(",")[0];
		User user = new User();
		Office office = new Office();
		office.setId(companyid);
		user.setCompany(office);
		cum.setCreateBy(user);
		List<Customer> listcon=cusListService.findserial(cum);
		Integer  serialNumber = new Integer(0);					//初始序号为00000
		for(Customer con:listcon){						//获取最大的序号
			Integer ing=new Integer(con.getSerialNumber());
			if(ing != null && ing >= serialNumber){
				
				serialNumber = ing;
			}
		}
		serialNumber+=1;									//最大序号加1
		String strser=serialNumber.toString();
		int strnum=strser.length();
		for(int j=5; j>strnum;j--){								//序号长度保持为5位
			strser="0"+strser;
		}
		
		String date = "";
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
		date=sim.format(customer.getRegisterDate());			
		
		String aera="";
		double dou=Math.random();
		aera=(dou+"").substring(2, 6);//4位随即数
		
		String applyNo=date+strser+aera;
		customer.setApplyNo(applyNo);
		customer.setSerialNumber(strser);
		/**
		 * @reqno:H1512160007
		 * @date-designer:2015年12月23日-songmin
		 * @date-author:2015年12月23日-songmin:CRE_信贷审批_进件管理_企业客户登记_新增、修改_提交操作：启动审批流程、同步业务数据
		 * 		这里注释掉了工作流流程启动相关操作，将工作流启动相关操作放到了service层，加入事物控制，放置登记信息提交后，造成客户信息变成提交审核状态而无法被删除的情况发生
		 * 		保存、提交结果提示信息由IF、ELSE修改为三目运算格式
		 * 		关联客户登记信息和贷款申请信息数据
		 */
		CreApplyInfo applyInfo = customer.getApplyInfo();
		applyInfo.setId(request.getParameter("applyInfo_id"));
		//这里有一个很奇怪的问题，applyProductTypeName和applyProductName通过spring封装没有成功，applyProductTypeName和applyProductName通过spring注入成功后，又不能通过request取到值
		applyInfo.setApplyProductTypeName(request.getParameter("applyProductTypeName"));
		applyInfo.setApplyProductName(request.getParameter("applyProductName"));
		boolean saveResult = true;
		try {
			cusListService.save(customer);
		} catch (Exception e) {
			saveResult = false;
			logger.error("登记客户信息（" + ("1".equals(customer.getCustType())?"个人":"企业")+"|" + customer.getApplyInfo().getApplyProductName() + customer.getApplyInfo().getApplyProductId()+"）异常：" + e.getMessage(), e);
		}
		/**
		 * @reqno:H1512160004
		 * @date-designer:2015年12月22日-songmin
		 * @date-author:2015年12月22日-songmin:CRE_信贷审批_系统管理_产品管理_新增、修改、查询_添加【关联流程】数据项
		 * 		需求变更：产品关联了流程定义，所以不同的产品需要走不同的审批流程
		 * 			根据产品ID，查询产品关联的流程定义KEY，根据不同的KEY启动不动的审批流程
		 * 			当流程挂起时，前台会提示出错
		 */
		String post = request.getParameter("post");
		/**
		 * @reqno:H1512160006
		 * @date-designer:2015年12月16日-songmin
		 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_新增、修改
		 * 		个人、企业 客户登记信息的保存公用一个方法完成，所以提示信息根据需要区分企业、个人客户  CUST_TYPE=1个人  CUST_TYPE=2 企业
		 */
		addMessage(redirectAttributes, ("2".equals(post) ? "提交" : "保存") + ("1".equals(customer.getCustType()) ? "个人" : "企业") + "客户信息【" + customer.getCusName() + (saveResult ? "】成功" : "】失败"));
		if("1".equals(customer.getCustType())){//跳转个人客户信息列表页面
			return   "redirect:" + adminPath + "/credit/customer";
		}else{//跳转企业客户信息列表页面
			return   "redirect:" + adminPath + "/credit/customer/list_c";
		}
	}
	
	/**
	 * @reqno: H1509110042
	 * @date-designer:20150918-gengchang
	 * @date-author:20150918-gengchang: CRE_信贷审批_进件管理_客户登记列表_修改、删除
	 *			删除一条或多条数据，复选框可以传值id，对未提交的状态值为'1'的客户信息可以删除，而已提交的则不能删除。
     */
	@RequestMapping(value = "delete")
	public String delete(Customer customer, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String idStr = request.getParameter("id");
		String str[] = idStr.split(",");
		for(int i=0;i<str.length;i++){
			if(!("").equals(str[i])){
				String id = str[i];
				customer.setId(id);
				Customer cus11 =  cusListService.get(customer.getId());
				String cusStatus = cus11.getApplyStatus();
				if("2".equals(cusStatus)){
					addMessage(redirectAttributes, "客户信息已提交，不可删除");
					return   "redirect:" + adminPath + "/credit/customer/";
				}else{
					continue;
				}
			}
		}
		for(int i=0;i<str.length;i++){
			String id = str[i];
			customer.setId(id);
			cusListService.delete(customer);
			addMessage(redirectAttributes, "删除客户信息成功");
		}

			return   "redirect:" + adminPath + "/credit/customer/";
	}
	
	/**
	 * @reqno:AH1509140009
	 * @date-designer:2015年9月28日-songmin
	 * @date-author:2015年9月28日-songmin:面初审环节，点击“内匹配信息”Tab，进入内匹配信息页面，
	 * 用户的内匹配信息通过用户登记信息时的证件类型和证件号码作为判断标准，证件类型和证件号码一致则视用户为内匹配成功用户<br/>
	 * 必填参数：actTaskParam-applyId  
	 */
	@RequestMapping(value="apply_his")
	public String applyHis(ActTaskParam actTaskParam,Model model){
		List<Customer> customerlist =  cusListService.findApplyHis(actTaskParam.getApplyNo());
		/** 
		* @reqno:H1511190029
		* @date-designer:2015年11月06日-songmin
		* @date-author:2015年11月06日-songmin:内匹配时过滤掉当前的申请历史记录
	   */
		//排除当前申请记录
		int index = 0;
		if(null!=customerlist && customerlist.size()>0){
			for (Customer customer : customerlist) {
				if(null!=customer && null!=customer.getId() 
						&& actTaskParam.getApplyNo().equals(customer.getId())){
					break;
				}
				index++;
			}
			customerlist.remove(index);
		}
		model.addAttribute("customerlist", customerlist);
		return "app/credit/customer/customer_apply_his";
	}
	
	/**
	 * @reqno:H1512160005
	 * @date-designer:2015年12月15日-songmin
	 * @date-author:2015年12月15日-songmin:CRE_信贷审批_进件管理_企业客户登记_查询
	 * 		分页查询企业客户登记信息
	 *  	查询条件：Customer：cusName 客户名称、status 状态、idType 证件类型、idNum 证件号码
	 */
	@RequestMapping(value="list_c")
	public String list_c(Customer customer,Model model,
			HttpServletRequest request,HttpServletResponse response){
		
		customer.setCreateBy(UserUtils.getUser());
		
		Page<Customer> page = cusListService.findEnListPage(new Page<Customer>(request, response), customer); 
        model.addAttribute("page", page);
		
        return "app/credit/customer/cusList_c";
	}
	
	/**
	 * @reqno:H1512160006
	 * @date-designer:2015年12月16日-songmin
	 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_新增、修改
	 * 		进入企业客户登记信息新增、修改页面
	 * 		新增、修改判定条件：customer-id是否有值（存在=修改；不存在=新增）
	 */
	@RequestMapping(value="edit_c")
	public String edit_c(Customer customer,Model model) {
		if(StringUtils.isEmpty(customer.getId())){//新增
			customer.setRegisterDate(new Date());	
		}else{//修改
			customer = cusListService.get(customer.getId());
			model.addAttribute("customer", customer);
			/**
			 * @reqno:H1512160007
			 * @date-designer:2015年12月17日-songmin
			 * @date-author:2015年12月17日-songmin:CRE_信贷审批_进件管理_企业客户登记_同步业务数据
			 * 		从贷款申请表中获取登记时同步的产品相关信息（产品类型编号、产品ID）
			 * @see com.resoft.credit.service.CusListService#save(customer)
			 */
			//如果存在贷款申请信息同步数据，则查询对应产品类型下的本机构的产品信息（当然这里是必然存在的，如果不存在只能说明程序出现了异常）
			//这里是用于产品select标签的初始值创建
			CreApplyInfo applyInfo = customer.getApplyInfo();
			String orgId = UserUtils.getUser().getCompany().getId();
		 	Product product = new Product();
		 	product.setCompany(new Office(orgId));
		 	product.setProductTypeCode(applyInfo.getApplyProductTypeCode());
		 	List<Product> productList = productService.findCoProductByType(product);
			model.addAttribute("productList", productList);
		}
		return "app/credit/customer/cusForm_c";
	}
	
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月26日-songmin
	 * @date-author:2015年12月26日-songmin:删除未提交的企业登记信息
	 */
	@RequestMapping(value = "delete_c")
	public String delete_c(Customer customer, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String idStr = request.getParameter("id");
		String str[] = idStr.split(",");
		for(int i=0;i<str.length;i++){
			if(!("").equals(str[i])){
				String id = str[i];
				customer.setId(id);
				Customer cus11 =  cusListService.get(customer.getId());
				String cusStatus = cus11.getApplyStatus();
				if("2".equals(cusStatus)){
					addMessage(redirectAttributes, "客户信息已提交，不可删除");
					return   "redirect:" + adminPath + "/credit/customer/list_c";
				}else{
					continue;
				}
			}
		}
		for(int i=0;i<str.length;i++){
			String id = str[i];
			customer.setId(id);
			cusListService.delete(customer);
			addMessage(redirectAttributes, "删除客户信息成功");
		}
		return   "redirect:" + adminPath + "/credit/customer/list_c";
	}
}
package com.resoft.credit.applyRegister.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.dao.CusDao;
import com.resoft.credit.applyRegister.entity.Customer;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.loanApply.entity.CreApplyInfo;
import com.resoft.credit.loanApply.service.CreApplyInfoService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CusListService extends CrudService<CusDao, Customer> {
	@Autowired
	private CreApplyInfoService applyInfoService;
	@Autowired
	private ProductService productService;//产品sercice
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private ApplyRelationService applyRelationService;
	
	public Page<Customer> findPage(Page<Customer> page, Customer cus) {
		/**
		 * @reqno: H1509110039
		 * @date-designer:20150918-gengchang
		 * @date-author:20150918-gengchang:CRE_信贷审批_进件管理_客户登记列表_查询
		 * 						findPage()查询分页数据方法，用到的方法有：获得分页总数--getCount(),查询所有数据列表--findList()
		 */
			return super.findPage(page, cus);
			
		}
	
	/**
	 * @reqno:AH1509140009
	 * @date-designer:2015年9月28日-songmin
	 * @date-author:2015年9月28日-songmin:CRE_信贷审批_贷款初审_内匹配信息<br/>
	 * 	根据证件号码、证件类型查询指定客户的历史贷款申请记录（完全匹配）<br/>
	 * 	查询参数：id-申请ID
	 */
	public List<Customer> findApplyHis(String id){
		List<Customer> customerlist = new ArrayList<Customer>();
		Customer customer = get(id);
		//无效的ID
		if(null==customer){
			return customerlist; 
		}
		//根据当前指定用户的证件号码、证件类型来获取用户内匹配记录
		return super.dao.findApplyHis(customer);
	}
	/**
	 * @reqno: H1510210068
	 * @date-designer:20151023-lirongchao
	 * @date-author:20151023-lirongchao: 需求-1.在客户登记列表的新增或修改页面，
	 * 										当用户点击“提交”按钮，后台在提交前按规则生成申请编号、并保存；
											2.生成申请编号的规则：YYYYMMDD+00001+4位随机数；其中“00001”为序号，
											每个公司每天从00001开始递增
										当前环节-查询日期为当天的，当前公司登记的用户.
	 */
	public List<Customer> findserial(Customer customer){
		
		return super.dao.findserial(customer);
	}
	
	/**
	 * @reqno: H1510210067
	 * @date-designer:20151028-gengchang
	 * @date-author:20151028-gengchang: CRE_信贷审批_进件管理_客户登记列表_新增、修改_提交_做客户唯一性校验
	 *				statusResult():查询数据库中证件类型和证件号，在客户申请信息表中查询出status
  */
	public List<HashMap<String, String>> statusResult(HashMap<String, String> map){
		return super.dao.statusResult(map);
	}
	/**
	 * @reqno:H1512160005
	 * @date-designer:2015年12月16日-songmin
	 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_查询
	 * 		根据客户名称（模糊）、证件类型、证件号、状态分页查询企业客户数
	 */
	public Page<Customer> findEnListPage(Page<Customer> page, Customer customer) {
		page.setCount(this.dao.findEnListCount(customer));
		page.setDefaultCount(false);
		customer.setPage(page);
		page.setList(this.dao.findEnList(customer));
		return page;
	}
	
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月17日-songmin
	 * @date-author:2015年12月17日-songmin:CRE_信贷审批_进件管理_企业客户登记_同步业务数据
	 * 		保存客户（个人、企业）登记信息
	 * 		原始的保存方法是直接调用CrudService的save方法进行保存操作，目前需要在保存企业客户登记信息后追加同步数据到企业客户信息表和贷款申请信息表的数据
	 * 		备注：企业基本信息表数据只在提交时进行同步，未提交前不会使用到企业信息数据
	 */
	/**
	 * @reqno:H1512210023
	 * @date-designer:2015年12月30日-songmin
	 * @date-author:2015年12月30日-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
	 * 		1、同步贷款申请信息不再区分个人、企业用户
	 * 		2、提交时，企业客户登记同步企业信息表，个人客户同步个人客户信息表
	 */
	@Transactional(value="CRE",readOnly=false)
	public void save(Customer customer){
		//保存客户登记信息（个人、企业）
		super.save(customer);
		/**
		 * @reqno:H1601280119
		 * @date-designer:2016年1月28日-lirongchao
		 * @db-z:cre_apply_register:id,apply_id,ID_TYPE,ID_NUM,LIST_STATUS,cust_type,mobileNum
		 * @date-author:2016年1月28日-lirongchao:CRE_信贷审批_进件管理_客户登记列表_个人客户登记_改造
		 										因新表和旧表差异，所以需要修改表结构,相对应的也需要修改实体类
		 */		
		//同步数据，CustType  2：企业客户   1：个人客户  Status 2：提交
		if(null != customer){
			if("2".equals(customer.getApplyStatus())){//只在提交时同步企业基本信息-提交后不再允许修改，所以这里实际值调用了insert
				if("2".equals(customer.getCustType())){
					//同步企业客户信息表数据-开始
//					CompanyInfo companyInfo = new CompanyInfo();
//					companyInfo.setApplyNo(customer.getId());
//					companyInfo.setCustName(customer.getCusName());
//					companyInfo.setIdType(customer.getIdType());
//					companyInfo.setIdNum(customer.getIdNum());
//					companyInfo.setLinkmanName(customer.getContactsName());
//					companyInfo.setLinkmanIdNo(customer.getContactsCardIdNo());
//					companyInfo.setLinkmanPhone(customer.getContactsMobile());
//					companyInfoService.save(companyInfo);
					//同步企业客户信息表数据-结束
				}
			}
			if("1".equals(customer.getCustType())){
				//同步个人客户信息表数据-开始
				CustInfo custInfo = custInfoService.getIdNum(customer.getIdNum()); 
				if(custInfo == null){
					custInfo = new CustInfo();
				}
				if(custInfo.getId() != null ){
					ApplyRelation applyRelation = new ApplyRelation();
					applyRelation.setApplyNo(customer.getId());
					applyRelation.setRoleType(Constants.MAIN_LOANER);
					applyRelation.setCustId(customer.getId());
					List<ApplyRelation> applyRelationList = applyRelationService.findList(applyRelation);
					if(applyRelationList.size() == 0 ||applyRelationList == null){
						applyRelationService.save(applyRelation);
					}
				}
				custInfo.setCustName(customer.getCusName());
				custInfo.setIdType(customer.getIdType());
				custInfo.setIdNum(customer.getIdNum());
				custInfo.setMobileNum(customer.getMobileNum());
				if("".equals(custInfo.getPersonIdEndDate())) {
					custInfo.setPersonIdEndDate(null);
				}
				custInfoService.save(custInfo);
				//同步个人客户信息表数据-结束
			}			
			//保存、提交时  同步贷款信息表数据-开始  
			CreApplyInfo creApplyInfo =customer.getApplyInfo();
			creApplyInfo.setApplyNo(customer.getId());//客户登记表外键
			applyInfoService.save(creApplyInfo);
			//同步贷款信息表数据-结束
		}
		//status为已提交[status==2]
		if("2".equals(customer.getApplyStatus())){
			startProcess(customer);//提交后启动工作流
		}
	}
	
	/**
	 * @reqno:H1512160007
	 * @date-designer:2015年12月23日-songmin
	 * @date-author:2015年12月23日-songmin:启动工作流
	 * 		这里个人和企业都会根据产品关联的流程标识启动工作流，后面有需求关于个人客户登记也需要根据产品名称启动工作流，所以这里一块儿完成该工作，后面的需求则不再做此工作的修改
	 */
	private void startProcess(Customer customer){
		Product product = productService.get(customer.getApplyInfo().getApplyProductId());
		if (null != product && StringUtils.isNotEmpty(product.getProcDefKey())) {
			actTaskService.startProcess(product.getProcDefKey(), "CRE_APPLY_REGISTER", customer.getId());
		}else{
			//没有关联流程时抛出异常
			throw new RuntimeException("产品没有关联流程或流程已挂起！");
		}
	}
}

package com.resoft.credit.applyRegister.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.dao.ApplyRegisterDao;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRelation.dao.ApplyRelationDao;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.custRemoveBind.dao.CustRemoveBindDao;
import com.resoft.credit.custRemoveBind.entity.CustRemoveBind;
import com.resoft.credit.custRemoveBind.service.CustRemoveBindService;
import com.resoft.credit.custinfo.dao.CustInfoDao;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.noEntry.entity.OrgNoEntry;
import com.resoft.credit.noEntry.entity.UserNoEntry;
import com.resoft.credit.noEntry.service.OrgNoEntryService;
import com.resoft.credit.noEntry.service.UserNoEntryService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequestList;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.utils.Facade;
import com.resoft.postloan.checkTwentyFiveInfo.entity.GuarantorCompany;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 个人客户登记Service
 * 
 * @author wuxi01
 * @version 2016-03-05
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ApplyRegisterService extends CrudService<ApplyRegisterDao, ApplyRegister> {

	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private CustRemoveBindDao custRemoveBindDao;
	@Autowired
	private CustRemoveBindService custRemoveBindService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OrgNoEntryService orgNoEntryService;
	@Autowired
	private UserNoEntryService userNoEntryService;
	@Autowired
	private CheckApproveDao  checkApproveDao;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private ApplyRelationDao applyRelationDao;
	@Autowired
	private CreGuaranteeCompanyService guaranteeCompanyService;

	public ApplyRegister get(String id) {
		return super.get(id);
	}
	
	public String  getRegisterDateByApplyNo(String applyNo) {
		return super.dao.getRegisterDateByApplyNo(applyNo);
	}

	public List<ApplyRegister> findList(ApplyRegister applyRegister) {
		return super.findList(applyRegister);
	}

	public Page<ApplyRegister> findPage(Page<ApplyRegister> page, ApplyRegister applyRegister) {
		return super.findPage(page, applyRegister);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ApplyRegister applyRegister) {
		super.save(applyRegister);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void saveApplyRegisterFromGedApplyRegister(ApplyRegister applyRegister) {
		this.dao.saveApplyRegisterFromGedApplyRegister(applyRegister);
	}

	/**
	 * 批量删除方法
	 * 
	 * @param idList
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void banchDelete(List<String> idList) {
		if (null != idList && idList.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.banchDelete(param);
		}
	}

	/**
	 * 根据申请编号查询产品类型 : 联合授信产品 还是非联合授信产品
	 */
	 public String queryRegisterByApplyNo(String applyNo){

	 	return super.dao.selectRegisterByApplyNo(applyNo);
	 }


	/**
	 * 根据证件类型、证件号和手机号查询信息登记状态
	 * 
	 * @param map
	 * @return
	 */
	public List<String> statusResult(Map<String, String> map) {
		return super.dao.statusResult(map);
	}

	/**
	 * 将ApplyRegister对象相关数据set进CustInfo对象
	 * 
	 * @param applyRegister
	 * @return CustInfo
	 */
	public CustInfo transApplyRegisterToCustInfo(ApplyRegister applyRegister) {
		CustInfo custInfo = new CustInfo();
		custInfo.setCustName(applyRegister.getCustName());
		custInfo.setMobileNum(applyRegister.getMobileNum());
		custInfo.setIdType(applyRegister.getIdType());
		custInfo.setIdNum(applyRegister.getIdNum());
		return custInfo;
	}

	/**
	 * 将ApplyRegister和CustInfo对象相关数据set进ApplyRelation对象，同时将角色类型设置为主借人
	 * 
	 * @param applyRegister
	 * @return ApplyRelation
	 */
	public ApplyRelation transApplyRegisterToApplyRelation(ApplyRegister applyRegister, CustInfo custInfo) {
		ApplyRelation applyRelation = new ApplyRelation();
		applyRelation.setApplyNo(applyRegister.getApplyNo());
		applyRelation.setRoleType(Constants.ROLE_TYPE_ZJR);// 主借人
		applyRelation.setCustId(custInfo.getId());
		applyRelation.setCustName(custInfo.getCustName());
		return applyRelation;

	}

	/**
	 * 新增个人客户登记信息时，同时对客户基本信息、申请客户关联表、客户解绑定表进行操作
	 * 
	 * @return String
	 * @param ApplyRegister
	 */
	@Transactional(value = "CRE", readOnly = false)
	public String saveRegisterCustAndRelation(ApplyRegister applyRegister) throws Exception {
		// 2016-03-25 雨杰 注释掉，待婉梅回来后，再详细修改。
		// applyRegister.setApplyNo(DateUtils.formatDate(new Date(),
		// "yyyyMMddHHmmss"));
		// 1.数据准备
		User loginUser = UserUtils.getUser();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idNum", applyRegister.getIdNum());
		map.put("mobileNum", applyRegister.getMobileNum());
		// 2.根据证件号查询客户信息表，若客户信息不存在，在客户信息表中插入数据
		CustInfo custInfoToUpdate = custInfoDao.findCustInfoByIdCard(map);
		// 类型转换
		CustInfo custInfo = transApplyRegisterToCustInfo(applyRegister);
		// 2.1.客户信息表中存在，更新客户信息表
		if (custInfoToUpdate != null && StringUtils.isNotBlank(custInfoToUpdate.getId())) {// 客户信息表中存在，更新客户信息表
			// 2.1.1.该客户是否存在处于提交审批环节的进件
			// 如果在提交审批阶段：不更新cust_info
			// 否则：更新cust_info
			String checkFlag = isCheckingOrRejected(map);
			// 如果不存在处于提交审批阶段的进件：更新cust_info
			if (!"checking".equals(checkFlag)) {
				// 2.1.1.1.1. 更新客户信息表
				custInfo.preUpdate();
				custInfo.setId(custInfoToUpdate.getId());
				custInfoDao.updateByApplyRegister(custInfo);
			}
			// 2.1.2.客户绑定信息
			String idNum = applyRegister.getIdNum();
			CustRemoveBind custRemoveBind = new CustRemoveBind();
			custRemoveBind.setIdNum(idNum);
			custRemoveBind.setMobileNum(applyRegister.getMobileNum());
			Map<String, Object> isAlreadyBindMap = custRemoveBindService.isAlreadyBind(custRemoveBind);
			// 查询客户绑定信息结果处理
			String flag = isAlreadyBindMap.get("flag").toString();
			if ("bindAndUpdate".equals(flag)) {// 修改绑定后再保存、提交
				// 绑定操作
				CustRemoveBind custRemoveBindExisted = (CustRemoveBind) isAlreadyBindMap.get("custRemoveBindExisted");
				custRemoveBindExisted.preUpdate();
				custRemoveBindExisted.setUser(loginUser);
				custRemoveBindExisted.setIsBind(Constants.BIND_STATUS_YBD);
				custRemoveBindDao.update(custRemoveBindExisted);
			} else if ("forbidden".equals(flag)) {// 禁止保存、提交
				User bindUser = UserUtils.get(isAlreadyBindMap.get("bindUser").toString());
				return "当前客户已经绑定" + bindUser.getLoginName() + "客户经理！";
			}
			// 2.2. 客户信息表中不存在，客户信息表、relation表、客户绑定表插入相关数据
		} else {
			// 2.2.1.新增客户基本信息信息
			custInfo.preInsert();
			custInfoDao.insert(custInfo);
			// 2.2.3.新增绑定信息
			CustRemoveBind newCustRemoveBind = new CustRemoveBind();
			newCustRemoveBind.preInsert();
			newCustRemoveBind.setUser(loginUser);
			newCustRemoveBind.setIsBind(Constants.BIND_STATUS_YBD);
			newCustRemoveBind.setCompany(loginUser.getCompany());
			newCustRemoveBind.setCustId(custInfo.getId());
			newCustRemoveBind.setCustName(custInfo.getCustName());
			newCustRemoveBind.setIdType(applyRegister.getIdType());
			newCustRemoveBind.setIdNum(applyRegister.getIdNum());
			newCustRemoveBind.setMobileNum(applyRegister.getMobileNum());
			custRemoveBindDao.insert(newCustRemoveBind);
		}

		// 3.对客户登记信息做入库前数据处理
		if (applyRegister != null && StringUtils.isNotBlank(applyRegister.getId())) {// 修改客户登记信息
			applyRegister.preUpdate();
			super.dao.update(applyRegister);
		} else {// 新增客户登记信息
			// 设置登记人为当前登录人 
			applyRegister.setRegisterName(loginUser.getLoginName());
			// 设置登机门店为当前登录人机构
			applyRegister.setCompany(UserUtils.getUser().getCompany());
			applyRegister.preInsert();
			ApplyRegister applyRegister2 = checkDouble(applyRegister);
			if(applyRegister2==null){
				super.dao.insert(applyRegister);
			}
		}

		if (Constants.APPLY_STATUS_SUBMIT.equals(applyRegister.getApplyStatus())) {
			startProcess(applyRegister, loginUser.getLoginName());// 提交后启动工作流
		}
		return "success";
	}


	private ApplyRegister checkDouble(ApplyRegister applyRegister) {
		return dao.checkDouble(applyRegister);
	}

	/**
	 * 启动产品对应的流程,并将下个环节的任务指定为当前用户
	 * */
	private void startProcess(ApplyRegister applyRegister, String loginName) {
		Product product = productService.get(applyRegister.getApplyProductId());
		if (null != product && StringUtils.isNotEmpty(product.getProcDefKey())) {
			String procInstId = actTaskService.startProcess(product.getProcDefKey(), "CRE_APPLY_REGISTER", applyRegister.getId());
			applyRegister.setProcInsId(procInstId);
			applyRegister.setProcDefKey(product.getProcDefKey());
			super.dao.updateProcInsIdById(applyRegister);
			List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
			String taskId = hisTasks.get(0).getId();
			taskService.claim(taskId, loginName);
		} else {
			// 没有关联流程时抛出异常
			throw new RuntimeException("产品没有关联流程或流程已挂起！");
		}
	}

	/**
	 * 根据证件类型、证件号、手机号查询该客户是否存在正在审批中、或者曾经被拒的进件
	 * 
	 * @param map
	 * @return String checking：存在正在审批中的进件 rejected：存在被拒的进件 true：不存在正在审批中或者被拒的进件
	 */
	public String isCheckingOrRejected(Map<String, String> map) {
		List<String> statusList = new ArrayList<String>();
		statusList = super.dao.statusResult(map);
		if (statusList.contains(Constants.APPLY_STATUS_SUBMIT)) {
			return "checking";
		} else if (statusList.contains(Constants.APPLY_STATUS_REFUSED)) {
			return "rejected";
		} else {
			return "true";
		}
	}

	/**
	 * 验证当前产品是否是债股结合
	 * @param applyNo
	 * @return
	 */
	public boolean checkIsStockWebCheck(String applyNo){
		ApplyRegister  applyRegister = new ApplyRegister();
		applyRegister.setApplyNo(applyNo);
		List<ApplyRegister> list = findList(applyRegister);
		if (!list.isEmpty()) {
			applyRegister=list.get(0);//判断是否债股结合产品，3在字典表是债股结合类型
		}
		if(Constants.PRODUCT_TYPE_ZGJH.equals(applyRegister.getApplyProductTypeCode())){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据applyNo更新客户登记状态
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateApplyStatus(String applyNo, String applyStatus) {
		super.dao.updateApplyStatus(applyNo, applyStatus);
	}

	public ApplyRegister getApplyRegisterByApplyNo(String applyNo) {
		return super.dao.getApplyRegisterByApplyNo(applyNo);
	}

	public String isAllowRegiste() {
		// 机构是否被禁件
		User loginUser = UserUtils.getUser();
		String userId = loginUser.getId();
		String orgId = loginUser.getCompany().getId();
		OrgNoEntry companyNoEntry = new OrgNoEntry();
		companyNoEntry.setOrgId(orgId);
		List<OrgNoEntry> orgNoEntryList = orgNoEntryService.findListByOrgId(orgId);
		if (orgNoEntryList != null && orgNoEntryList.size() > 0) {
			return loginUser.getLoginName() + "您好，您所在机构已被禁件，不可进行操作！";
		}
		List<OrgNoEntry> parentOrgNoEntryList = orgNoEntryService.findParentOrgNoEntryList(orgId);
		if (parentOrgNoEntryList != null && parentOrgNoEntryList.size() > 0) {
			return loginUser.getLoginName() + "您好，您所在机构已被禁件，不可进行操作！";
		}
		// 人员是否被禁件
		List<UserNoEntry> userNoEntryList = userNoEntryService.findListByUserId(userId);
		if (userNoEntryList != null && userNoEntryList.size() > 0) {
			return loginUser.getLoginName() + "您好，您已被禁件，不可进行操作！";
		}

		return "true";
	}

	/**
	 * 根据企业标志（4选1）判断该企业是否存在
	 */
	public List<String> existCompany(Map<String, Object> params){
		return this.dao.existCompany(params);
	}
	
	/**
	 * 查询进件信息
	 * @param applyNo
	 * @return
	 */
	public ApplyRegister getApplyRegisterByApplyNoAndGed(String applyNo) {
		return super.dao.getApplyRegisterByApplyNoAndGed(applyNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void saveSendGEDStatus(ApplyRegister applyRegister) {
		dao.updateSendGEDStatus(applyRegister);
		
	}

	/**
	 * 判断是否存在对应身份证号码
	 * @param idNum
	 * @return
	 */
	public List<ApplyRegister> findApplyListsByIdNum(String idNum){
		return super.dao.findApplyListsByIdNum(idNum);
	};
	
	public boolean findGEDByContractNo(String ContractNo) {
		String sendGED = dao.findGEDByContractNo(ContractNo);
		if("1".equals(sendGED)) {
			return true;
		}else {
			return false;
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public AddOrderResponse sendOrderToGED(String applyNo) {
		AddOrderRequest addOrderRequert = new AddOrderRequest();
		Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
		paramCheckApprove.put("applyNo", applyNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CheckApprove> checkApproveList = checkApproveDao.getCheckApproveByApplyNo(paramCheckApprove);
		CheckApprove checkApprove = new CheckApprove();
		if (checkApproveList.size() > 0) {
			checkApprove = checkApproveList.get(0);
			addOrderRequert.setApplyAmount(checkApprove.getContractAmount());
			addOrderRequert.setApplyPeriod(checkApprove.getApproPeriodValue());
			addOrderRequert.setApplyId(checkApprove.getApplyNo());
			addOrderRequert.setProductName(checkApprove.getApproProductName());
			addOrderRequert.setProductType(Integer.parseInt(checkApprove.getApproProductTypeCode()));
			addOrderRequert.setApproveMonthRate(checkApprove.getApproYearRate());
			addOrderRequert.setRepaymentStyle(DictUtils.getDictLabel(checkApprove.getApproLoanRepayType(), "LOAN_REPAY_TYPE", null));
		}
		ApplyRegister applyRegister = getApplyRegisterByApplyNoAndGed(applyNo);
		if(applyRegister != null){
			addOrderRequert.setCustSource(Integer.parseInt(applyRegister.getChannelSourceType()));
			addOrderRequert.setCustName(applyRegister.getCustName());
			addOrderRequert.setIdNum(applyRegister.getIdNum());
			addOrderRequert.setApplyDate(sdf.format(applyRegister.getCreateDate()));
			addOrderRequert.setPhoneNo(applyRegister.getMobileNum());
		}	
		CompanyInfo companyInfo = null;
		Map<String, Object> queryComParam = Maps.newHashMap();
		queryComParam.put("applyNo", applyNo);
		queryComParam.put("roleType", Constants.ROLE_TYPE_ZJQY);
		List<CompanyInfo> comapnyList = companyInfoService.findListByParams(queryComParam);
		if (!comapnyList.isEmpty()) {
			companyInfo = comapnyList.get(0);
		}
		if(companyInfo != null){
			addOrderRequert.setLegalMobile(companyInfo.getCorporationMobile());
			addOrderRequert.setLegalName(companyInfo.getCorporationName());
			addOrderRequert.setLegalCardNumber(companyInfo.getCorporationCardIdNo());
			addOrderRequert.setContCity(companyInfo.getOperateCity());
			//--
			addOrderRequert.setCompanyName(companyInfo.getBusiRegName());
			addOrderRequert.setCompanyCardNum(companyInfo.getUnSocCreditNo());
			addOrderRequert.setCompanyCardType("1");
			addOrderRequert.setProvince(companyInfo.getOperateProvince());
			addOrderRequert.setCity(companyInfo.getOperateCity());
			addOrderRequert.setDistrict(companyInfo.getOperateDistinct());
		}
		//查询贷款申请信息
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(applyNo);
		if(applyInfo != null){
			addOrderRequert.setCustType(Integer.parseInt(applyInfo.getApplyCustType()));
			addOrderRequert.setLoanPurpost(applyInfo.getLoanPurpost());
		}
		
		addOrderRequert.setReceivableCashDeposit(checkApprove.getMarginAmount().toString());
		CreGuaranteeCompany guarantorCompany= guaranteeCompanyService.getByApplyNoAndRoleType(applyNo,"8");
		//BigDecimal costRate = new BigDecimal(guarantorCompany.getCostRate());
				//BigDecimal contractAmount = checkApprove.getContractAmount();
				//BigDecimal receivableGuaranteeFee = contractAmount.multiply(costRate).multiply(new BigDecimal("0.01"));
				addOrderRequert.setReceivableGuaranteeFee("1000");
		AddOrderRequestList list =new AddOrderRequestList();
		List<AddOrderRequest> list1 = new ArrayList<>();
		list1.add(addOrderRequert);
		list.setList(list1);
		AddOrderResponse addOrderToGEDInterface = Facade.facade.addOrderToGEDInterface(list,applyNo);
		return addOrderToGEDInterface;
	}

	public ApplyRegister getApplyRegisterByContractNo(String contractNo) {
		return dao.getApplyRegisterByContractNo(contractNo);
	}

	// 老数据推送冠易贷的时候 修改sengGed 为 2
	@Transactional(value = "CRE", readOnly = false)
	public int updateRegisterByApplyNo(ApplyRegister applyRegister){
		return dao.updateRegisterByApplyNo(applyRegister);
	}
	@Transactional(value = "CRE", readOnly = false)
    public void test() {
		String procInstId = actTaskService.startProcess("gq_loan_under", "CRE_APPLY_REGISTER", "1233211233321");
		System.out.println(procInstId);
    }
	
	@Transactional(value = "CRE", readOnly = false)
	public void updateUnderByApplyNo(ApplyRegister applyRegister){
		dao.updateUnderByApplyNo(applyRegister);
	}
	
	public ApplyRegister getApplyRegisterByAccontractNo(String contractNo){
		return dao.getApplyRegisterByAccontractNo(contractNo);
	}
}

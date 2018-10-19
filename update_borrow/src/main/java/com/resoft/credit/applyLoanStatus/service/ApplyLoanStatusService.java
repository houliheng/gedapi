package com.resoft.credit.applyLoanStatus.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Maps;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.applyLoanStatus.dao.ApplyLoanStatusDao;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.contractarchive.entity.ContractArchive;
import com.resoft.credit.contractarchive.service.ContractArchiveService;
import com.resoft.credit.creditViewBook.dao.creditAssets.CreditAssetsDao;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.mortgageCarInfo.dao.MortgageCarInfoDao;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageHouseInfo.dao.MortgageHouseInfoDao;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageOtherInfo.dao.MortgageOtherInfoDao;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.resoft.multds.postloan.pushdatatopl.service.PushDataToPlService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 财务放款Service
 *
 * @author wuxi01
 * @version 2016-03-03
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ApplyLoanStatusService extends CrudService<ApplyLoanStatusDao, ApplyLoanStatus> {

	private static final Logger logger = LoggerFactory.getLogger(ApplyLoanStatusService.class);
	@Autowired
	private VideoUploadDao videoUploadDao;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private ContractArchiveService contractArchiveService;
	@Autowired
	private MortgageOtherInfoDao mortgageOtherInfoDao;
	@Autowired
	private MortgageHouseInfoDao mortgageHouseInfoDao;
	@Autowired
	private MortgageCarInfoDao mortgageCarInfoDao;
	@Autowired
	private CreditAssetsDao creditAssetsDao;
	@Autowired
	private PushDataToPlService pushDataToPlServer;
	@Autowired
	private Acc4FinancialPlatformServer acc4FinancialPlatformServer;

	public ApplyLoanStatus get(String id) {
		return super.get(id);
	}

	public List<ApplyLoanStatus> findList(ApplyLoanStatus applyLoanStatus) {
		return super.findList(applyLoanStatus);
	}

	public Page<ApplyLoanStatus> findPage(Page<ApplyLoanStatus> page, ApplyLoanStatus applyLoanStatus) {
		return super.findPage(page, applyLoanStatus);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ApplyLoanStatus applyLoanStatus) {
		super.save(applyLoanStatus);
	}

	/**
	 * 保存财务放款-审批意见
	 *
	 * @param actTaskParam
	 * @param processMap
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveLoanSuggestion(ActTaskParam actTaskParam, Map<String, String> processMap) throws Exception {
		processSuggestionInfoService.saveProcessSuggestionInfo(actTaskParam, processMap);
		if ("success".equals(processMap.get("passFlag"))) {
			// 更新APPLY_STATUS
			applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(), Constants.APPLY_STATUS_FKCG);
			// 向合同归档表插入数据
			// 判断是否是联合受信产品
			List<CheckApproveUnion> applyLoanStatusList =getLoanStatusNew(actTaskParam.getApplyNo());
			if (applyLoanStatusList.size()>0){//是联合受信产品
			//查询需要归档的合同信息
			List<ContractArchive> contractArchivelst = contractArchiveService.getArchiveLstByApplyNo(actTaskParam.getApplyNo());
			if (contractArchivelst.size() ==0) {
				logger.error("合同信息不存在，建立归档失败，请联系管理员！");
			}else {
				Iterator<ContractArchive> it=contractArchivelst.iterator();
				while(it.hasNext()){
					ContractArchive contractArchive = new ContractArchive();
					contractArchive = it.next();
					contractArchive.preInsert();
					Office orgNum = new Office();
					orgNum.setId(contractArchive.getBorrowingReason());
					contractArchive.setOrgNum(orgNum);
				}
				contractArchiveService.insertContractArchiveLst(contractArchivelst);
			}
			}
			else { //非联合受信产品
			Contract contract = contractService.getContractByApplyNo(actTaskParam.getApplyNo());
			if (contract == null) {
				logger.error("合同信息不存在，建立归档失败，请联系管理员！");
			}
			ContractArchive contractArchive = new ContractArchive();
			contractArchive.setContractNo(contract.getContractNo());
			Map<String, Object> params = Maps.newConcurrentMap();
			params.put("contractNo", contract.getContractNo());
			contractArchive.setCustName(contract.getCustName());
			Office orgNum = new Office();
			orgNum.setId(contract.getOperateOrgId());
			contractArchive.setOrgNum(orgNum);
			contractArchive.setIsSender(Constants.YES_NO_N);
			contractArchive.setIsRecipitent(Constants.YES_NO_N);
			contractArchive.setIsBorrowing(Constants.YES_NO_N);
			List<ContractArchive> contractArchiveList = contractArchiveService.findListByContractNo(params);
			if (contractArchiveList != null && contractArchiveList.size() > 0) {
				logger.warn("该合同数据已存在于归档表中，可能有数据异常！");
				contractArchive.setIsNewRecord(false);
				contractArchiveService.save(contractArchive);
			} else {
				contractArchive.setIsNewRecord(true);
				contractArchive.setId(IdGen.uuid());
				contractArchiveService.save(contractArchive);
			}
			}
		} else if ("flow".equals(processMap.get("passFlag"))) {
			// 更新APPLY_STATUS
			applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(), Constants.APPLY_STATUS_LB);
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(ApplyLoanStatus applyLoanStatus) {
		super.delete(applyLoanStatus);
	}

	/**
	 * 根据证件类型+证件号或手机号查询合同状态
	 *
	 * @return
	 */
	public List<String> findContractStatusByIdCardOrMobile(Map<String, String> map) {
		return super.dao.findContractStatusByIdCardOrMobile(map);
	};

	/**
	 * 确定 放款 提现 流标 三按钮
	 */
	public void confirmStatus(String flag, String status, Model model) {
		if (Constants.PRODUCT_TYPE_XY.equals(status)) {// 信用
			// 放款按钮状态
			if (Constants.LOAN_STATUS_YMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_DFKSQ.equals(flag)) {
				model.addAttribute("btnSubmitt", "false");
			} else {
				model.addAttribute("btnSubmitt", "true");
			}
			// 流标按钮状态
			if (Constants.LOAN_STATUS_YMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				model.addAttribute("btnCancell", "false");
			} else {
				model.addAttribute("btnCancell", "true");
			}
			// 提现按钮状态
			if (Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				model.addAttribute("btnCancel", "false");
			} else {
				model.addAttribute("btnCancel", "true");
			}
		} else if (Constants.PRODUCT_TYPE_DY.equals(status) || Constants.PRODUCT_TYPE_HH.equals(status)) { // 抵押
			// 放款按钮状态 抵押/混合
			if (Constants.LOAN_STATUS_DFKSQ.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag)) {
				model.addAttribute("btnSubmitt", "false");
			} else {
				model.addAttribute("btnSubmitt", "true");
			}
			// 流标按钮状态
			if (Constants.LOAN_STATUS_FB.equals(flag) || Constants.LOAN_STATUS_DFKSQ.equals(flag) || Constants.LOAN_STATUS_DMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_DTXGDYR.equals(flag) || Constants.LOAN_STATUS_HSDYQGCZ.equals(flag) || Constants.LOAN_STATUS_HSDYQCG.equals(flag) || Constants.LOAN_STATUS_HSDYQSB.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				model.addAttribute("btnCancell", "false");
			} else {
				model.addAttribute("btnCancell", "true");
			}
			// 提现按钮状态
			if (Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_HSDYQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				model.addAttribute("btnCancel", "false");
			} else {
				model.addAttribute("btnCancel", "true");
			}
		} else {
			model.addAttribute("btnCancell", "true");
		}
	}

	/**
	 * 刷新用 确定 放款 提现 流标 三按钮
	 */
	public AjaxView refreshConfirmStatus(String flag, String status) {
		AjaxView ajaxView = new AjaxView();
		if (Constants.PRODUCT_TYPE_XY.equals(status)) {// 信用
			// 放款按钮状态
			if (Constants.LOAN_STATUS_YMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_DFKSQ.equals(flag)) {
				ajaxView.put("btnSubmitt", "false");
			} else {
				ajaxView.put("btnSubmitt", "true");
			}
			// 流标按钮状态
			if (Constants.LOAN_STATUS_YMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				ajaxView.put("btnCancell", "false");
			} else {
				ajaxView.put("btnCancell", "true");
			}
			// 提现按钮状态
			if (Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				ajaxView.put("btnCancel", "false");
			} else {
				ajaxView.put("btnCancel", "true");
			}
		} else if (Constants.PRODUCT_TYPE_DY.equals(status) || Constants.PRODUCT_TYPE_HH.equals(status)) { // 抵押
			// 放款按钮状态 抵押/混合
			if (Constants.LOAN_STATUS_DFKSQ.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag)) {
				ajaxView.put("btnSubmitt", "false");
			} else {
				ajaxView.put("btnSubmitt", "true");
			}
			// 流标按钮状态
			if (Constants.LOAN_STATUS_FB.equals(flag) || Constants.LOAN_STATUS_DFKSQ.equals(flag) || Constants.LOAN_STATUS_DMB.equals(flag) || Constants.LOAN_STATUS_FKSQSB.equals(flag) || Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_DTXGDYR.equals(flag) || Constants.LOAN_STATUS_HSDYQGCZ.equals(flag) || Constants.LOAN_STATUS_HSDYQCG.equals(flag) || Constants.LOAN_STATUS_HSDYQSB.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				ajaxView.put("btnCancell", "false");
			} else {
				ajaxView.put("btnCancell", "true");
			}
			// 提现按钮状态
			if (Constants.LOAN_STATUS_FKSQCG.equals(flag) || Constants.LOAN_STATUS_HSDYQCG.equals(flag) || Constants.LOAN_STATUS_TXSQSB.equals(flag)) {
				ajaxView.put("btnCancel", "false");
			} else {
				ajaxView.put("btnCancel", "true");
			}
		} else {
			ajaxView.put("btnCancell", "true");
		}
		return ajaxView;
	}

	/**
	 * 根据合同号查询记录
	 */
	public List<ApplyLoanStatus> getApplyLoanStatusByApplyNo(String applyNo) {
		return this.dao.getApplyLoanStatusByApplyNo(applyNo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateContractState(String contractNo) {
		this.dao.updateContractState(contractNo);
	}

	@Transactional(readOnly = false)
	public AjaxView toPrepare(String contractNo, String time) {
		AjaxView ajaxView = new AjaxView();
		try {
			AccContract contract = contractService.getCreContractByContractNo(contractNo);
			if(contract == null){
				ajaxView.setFailed().setMessage("此合同号数据不存在，请确认合同号是否正确");
				return ajaxView;
			}
			// 转换日期格式
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdfs.parse(time);
			} catch (ParseException e) {
				logger.error("日期转换错误", e);
				ajaxView.setFailed().setMessage("日期转换错误");
				return ajaxView;
			}
			contract.setLoanDate(date);
			// 生成还款计划表
			RepayPlanData repayPlanData = new RepayPlanData();
			repayPlanData.setApplyNo(contract.getApplyNo());
			repayPlanData.setContractNo(contract.getContractNo());
			repayPlanData.setApproLoanRepayType(contract.getApproLoanRepayType());
			repayPlanData.setApproPeriodValue(contract.getApproPeriodValue());
			repayPlanData.setContractAmount(contract.getContractAmount());
			repayPlanData.setServiceFeeType(contract.getServiceFeeType());
			repayPlanData.setServiceFeeRate(new BigDecimal(contract.getServiceFeeRate()));

			String deductDateStr = sdfs.format(DateUtils.getNextMonth(date));
			repayPlanData.setApproProductTypeCode(contract.getApproProductTypeId());
			repayPlanData.setDeductDate(DateUtils.getNextMonth(date));
			repayPlanData.setDeductDateStr(deductDateStr);
			repayPlanData.setCustName(contract.getCustName());
			repayPlanData.setCapitalTerraceNo(contract.getCapitalTerraceNo());
			repayPlanData.setOrgLevel2(contract.getOrgLevel2());
			repayPlanData.setOrgLevel3(contract.getOrgLevel3());
			repayPlanData.setOrgLevel4(contract.getOrgLevel4());
			repayPlanData.setIsAcc("true");
			// 查询利率
			BigDecimal interest = contract.getApproYearRate();
			if (interest != null) {
				interest = interest.multiply(new BigDecimal("0.01"));
			}
			repayPlanData.setInterest(interest);
			List<RepayPlan> repayPlanList = contractService.calculateRepayPlan(repayPlanData);
			acc4FinancialPlatformServer.insertAccRepayPlanAndAccContract(repayPlanList, contract);
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("生成还款计划失败");
			logger.error("向acc_repay_plan中插入数据时出错", e);
			return ajaxView;
		}
		try {
			acc4FinancialPlatformServer.insertAccStaContractStatus(contractNo);
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("生成合同数据失败");
			logger.error("插入acc_sta_contract_status出错", e);
			return ajaxView;
		}
		ajaxView.setSuccess().setMessage("重新建账成功");
		return ajaxView;
	}

	@Transactional(value = "CRE", readOnly = false)
	public AjaxView saveLoanSuggestion(ActTaskParam actTaskParam,String suggestionDesc,String passFlag,ProcessSuggestionInfo processSuggestionInfo){
		AjaxView ajaxView = new AjaxView();
		try {
			if ("success".equals(passFlag)) {
				Map<String, Object> params = Maps.newHashMap();
				params.put("applyNo", actTaskParam.getApplyNo());
				params.put("taskDefKey", actTaskParam.getTaskDefKey());
				params.put("lockFlag", "1");
				videoUploadDao.lockVideoMessageByApplyNoAndTaskDefKey(params);
				pushDataToPl(processSuggestionInfo.getApplyNo());
			}
			Map<String, String> processMap = Maps.newHashMap();
			processMap.put("passFlag", passFlag);
			processMap.put("suggestionDesc", processSuggestionInfo.getSuggestionDesc());
			saveLoanSuggestion(actTaskParam, processMap);
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + suggestionDesc, "提交", null);
			ajaxView.setSuccess().put("suggestionMessage", "提交成功！");
		} catch (Exception e) {
			logger.error("保存财务放款审批信息失败！", e);
			ajaxView.setFailed().put("suggestionMessage", "提交失败！");
		}
		return ajaxView;
	}

	/**
	 * 推送cre数据到借后
	 */
	public void pushDataToPl(String applyNo) {
		// 查询其他抵质押物信息
		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoDao.getOtherData(applyNo);
		// 查询房屋抵质押物信息
		List<MortgageHouseInfo> mortgageHouseInfoList = mortgageHouseInfoDao.getHouseData(applyNo);
		// 查询车辆抵质押物信息和车辆评估表的信息
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoDao.getCarData(applyNo);
		// 查询征信个人资产清单信息 用的以前的方法
		List<CreditAssets> creditAssetsList = creditAssetsDao.getCreditAssetsByApplyNo(applyNo);
		// 推送数据到pl
		pushDataToPlServer.pushDataToPl(mortgageOtherInfoList, mortgageHouseInfoList, mortgageCarInfoList, creditAssetsList);
	}
	// 查询联合授信信息
	public List<CheckApproveUnion> findUnionCust(Map<String, String> unionMap) {
		return	this.dao.findUnionCust(unionMap);
	}
	// 更新状态为已推送待提现
	@Transactional(value = "CRE", readOnly = false)
	public Map<String, Object> updateLoanStatus(ApplyLoanStatus applyLoanStatus, String custId, String checkId) {
		Map<String, Object> msg = Maps.newHashMap();
		msg.put("flag", "!");
		this.dao.updateLoanStatus(applyLoanStatus);
		//调用  冠E通发标接口访问
	    msg = Facade.facade.issuingTenderData(applyLoanStatus.getApplyNo(),checkId);
	    
		return msg;
	}
	//查询是否全部 已提现
	public List<CheckApproveUnion> getLoanStatusNew(String applyNo) {
		return	this.dao.getLoanStatusNew(applyNo);
	}
	/**
	 * 根据申请编号取得 放款状态
	 */
	public String getLoanStatusByApplyNo(String applyNo) {
		return	this.dao.getLoanStatusByApplyNo(applyNo);
	}
	/**
	 * 冠易贷订单状态反馈(一次提现、二次提现)状态更新
	 * @param applyNo
	 * @param loanStatus
	 */
	@Transactional(readOnly = false)
	public boolean updateDrawStatus(String applyNo , String loanStatus){
		return this.dao.updateDrawStatus(applyNo,loanStatus);
	}
	
	/**
	 * 服务收取状态更新
	 * @param applyNo
	 */
	@Transactional(readOnly = false)
	public void updateServiceFeeByApplyNo(Map<String,String> param){
		this.dao.updateServiceFeeByApplyNo(param);
	}
	
	/**
	 * 查询是否存在对应放款信息
	 * @param applyNo
	 * @return
	 */
	public List<ApplyLoanStatus> finApplyLoanStatus(Map<String,String> param){
		return this.dao.finApplyLoanStatus(param);
	}
	
	/**
	 * 首次提现更新状态
	 * @param param
	 */
	@Transactional(readOnly = false)
	public void updateLoanStatusByApplyNoAndContractNo(Map<String,String> param){
		this.dao.updateLoanStatusByApplyNoAndContractNo(param);
	}
	
	/**
	 * 更新已推送冠E贷解冻状态
	 * @param param
	 */
	@Transactional(readOnly = false)
	public void updateSendGEDJd(Map<String,String> param){
		this.dao.updateSendGEDJd(param);
	}
	
	
	public ApplyLoanStatus queryContractLoanStatus(String contractNo){
		return this.dao.queryContractLoanStatus(contractNo);
	}
}
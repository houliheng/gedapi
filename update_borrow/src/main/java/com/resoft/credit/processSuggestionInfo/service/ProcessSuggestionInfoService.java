package com.resoft.credit.processSuggestionInfo.service;

import java.util.*;

import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.resoft.credit.creditAndLine.service.creditCompany.CreditCompanyService;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankService;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.financialStateImport.dao.ThemisReportHeadDao;
import com.resoft.outinterface.SV.client2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.JointCredit;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyLoanStatus.dao.ApplyLoanStatusDao;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.blacklist.service.BlacklistService;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.checkApproveUnion.dao.CompanyCustInfoRelatedDao;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.gedApplyRegister.entity.GedApplyRegister;
import com.resoft.credit.gedApplyRegister.service.GedApplyRegisterService;
import com.resoft.credit.generalAudit.service.GeneralManagerAuditService;
import com.resoft.credit.gqgetAssetCarUnion.dao.GqgetAssetCarUnionDao;
import com.resoft.credit.gqgetAssetHouseUnion.dao.GqgetAssetHouseUnionDao;
import com.resoft.credit.gqgetComInfoUnion.dao.GqgetComInfoUnionDao;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import com.resoft.credit.mortageEquipmentUnion.dao.MortageEquipmentUnionDao;
import com.resoft.credit.mortgagedCompanyUnion.dao.MortgagedCompanyUnionDao;
import com.resoft.credit.processSuggestionInfo.dao.ProcessSuggestionInfoDao;
import com.resoft.credit.processSuggestionInfo.entity.CreStaTaskResult;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.repayPlanUnion.dao.RepayPlanUnionDao;
import com.resoft.credit.stockOpinion.dao.CreStockOpinionDao;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.resoft.outinterface.rest.ged.entity.GedApproveState;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 流程意见Service
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ProcessSuggestionInfoService extends CrudService<ProcessSuggestionInfoDao, ProcessSuggestionInfo> {

	private static final Logger logger = LoggerFactory.getLogger(ProcessSuggestionInfoService.class);

	@Autowired
	private CreditCompanyService creditCompanyService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private JointCredit jointCredit;
	@Autowired
	private RepayPlanUnionDao repayPlanUnionDao;
	@Autowired
	private CheckApproveUnionDao checkApproveUnionDao;
	@Autowired
	private VideoUploadDao videoUploadDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApplyLoanStatusDao applyLoanStatusDao;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private GeneralManagerAuditService generalManagerAuditService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TaskCenterService taskCenterService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private GedApplyRegisterService gedApplyRegisterService;
	@Autowired
	private GqgetAssetCarUnionDao gqgetAssetCarUnionDao;
	@Autowired
	private GqgetAssetHouseUnionDao gqgetAssetHouseUnionDao;
	@Autowired
	private GqgetComInfoUnionDao gqgetComInfoUnionDao;
	@Autowired
	private MortageEquipmentUnionDao mortageEquipmentUnionDao;
	@Autowired
	private MortgagedCompanyUnionDao mortgagedCompanyUnionDao;
	@Autowired
	private CompanyCustInfoRelatedDao companyCustInfoRelatedDao;
	@Autowired
	private CheckApproveDao checkApproveDao;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	@Autowired
	private CreStockOpinionDao creStockOpinionDao;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CreApplyCompanyRelationService applyCompanyRelationService;
	@Autowired
	private CreditLineBankService creditLineBankService;
	@Autowired
	private CreditCustService creditCustService;
	@Autowired
	private ThemisReportHeadDao themisReportHeadDao;
	
	public ProcessSuggestionInfo get(String id) {
		return super.get(id);
	}

	public List<ProcessSuggestionInfo> findList(ProcessSuggestionInfo processSuggestionInfo) {
		return super.findList(processSuggestionInfo);
	}

	public Page<ProcessSuggestionInfo> findPage(Page<ProcessSuggestionInfo> page,
			ProcessSuggestionInfo processSuggestionInfo) {
		return super.findPage(page, processSuggestionInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ProcessSuggestionInfo processSuggestionInfo) {
		super.save(processSuggestionInfo);
	}

	// 记录流程节点信息
	@Transactional(value = "CRE", readOnly = false)
	public void insertFlag(ProcessSuggestionInfo processSuggestionInfo, String uTask) {
		HashMap<String, Object> myMapTime = new HashMap<String, Object>();
		processSuggestionInfo.setTaskDefKey(uTask);
		myMapTime = super.dao.getStartTime(processSuggestionInfo);
		HashMap<Object, Object> myMapReg = new HashMap<Object, Object>();
		myMapReg = super.dao.getOrg(processSuggestionInfo);
		CreStaTaskResult creStaTaskResult = new CreStaTaskResult();
		creStaTaskResult.setId(IdGen.uuid());
		creStaTaskResult.setApplyNo(processSuggestionInfo.getApplyNo());
		creStaTaskResult.setTaskDefKey(processSuggestionInfo.getTaskDefKey());
		creStaTaskResult.setPassFlag(processSuggestionInfo.getPassFlag());
		String officeId = UserUtils.getUser().getOffice().getId();
		creStaTaskResult.setOfficeId(officeId);
		if (myMapReg == null || myMapReg.size() == 0) {
		} else {
			creStaTaskResult.setItemCreateDate((Date) myMapReg.get("itemCreateDate"));
			creStaTaskResult.setOrgId((String) myMapReg.get("orgId"));
		}
		Map<String, Object> myTimeCliam = Maps.newHashMap();
		myTimeCliam = super.dao.getMyTimeCliam(processSuggestionInfo);
		if ("reDo".equals(processSuggestionInfo.getPassFlag())) {
			// 撤回的开始时间是上个节点的结束时间
			HashMap<String, Object> myMapTimeToo = new HashMap<String, Object>();
			myMapTimeToo = super.dao.getStartTimeToo(processSuggestionInfo);
			creStaTaskResult.setStartTime((Date) myMapTimeToo.get("reDoEndTime"));
		} else {
			if (!(StringUtils.isNull(myTimeCliam))) {
				// 如果没有签收的情况下分配
				creStaTaskResult.setStartTime((Date) myTimeCliam.get("startTime"));
			} else {
				if (myMapTime == null || myMapTime.size() == 0) {
					// 取得ru_task里的代办时间
					HashMap<String, Object> myMapTimeToo = new HashMap<String, Object>();
					myMapTimeToo = super.dao.getStartTimeToo(processSuggestionInfo);
					creStaTaskResult.setStartTime((Date) myMapTimeToo.get("startTime"));
				} else {
					// 如果没有签收，则取得act_hi_taskinst的开始时间
					creStaTaskResult.setStartTime((Date) myMapTime.get("startTime"));
				}
			}
		}
		// 当前结束时时间
		Date mowDate = new Date();
		creStaTaskResult.setEndTime(mowDate);
		creStaTaskResult.setUpdateBy(UserUtils.getUser());
		super.dao.insertDate(creStaTaskResult);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(ProcessSuggestionInfo processSuggestionInfo) {
		super.delete(processSuggestionInfo);
	}

	// 根据applyNo,taskDefKey查找车辆抵质押物的数据
	public ProcessSuggestionInfo findByApplyNo(Map<String, String> params) {
		return super.dao.findByApplyNo(params);
	}

	// 更新抵质押物评估表数据
	@Transactional(value = "CRE", readOnly = false)
	public void updateByApplyNo(ProcessSuggestionInfo processSuggestionInfo) {
		super.dao.updateByApplyNo(processSuggestionInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	// 保存借前外访中的外访意见
	public void saveVisit(ProcessSuggestionInfo processSuggestionInfo) {
		// 判断数据库中是否有此applyNo的数据
		String applyNo = processSuggestionInfo.getApplyNo();
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("taskDefKey", processSuggestionInfo.getTaskDefKey());
		ProcessSuggestionInfo hasProcessSuggestionInfo = findByApplyNo(params);
		if (hasProcessSuggestionInfo != null) {
			try {
				processSuggestionInfo.preUpdate();
				updateByApplyNo(processSuggestionInfo);
			} catch (Exception e) {
				logger.error("更新数据错误" + e.getMessage(), e);
			}
		} else {
			try {
				save(processSuggestionInfo);
			} catch (Exception e) {
				logger.error("保存数据错误" + e.getMessage(), e);
			}
		}
		if (Constants.UTASK_HTSH.equals(processSuggestionInfo.getTaskDefKey())) {
			saveApplyLoanStatus(applyNo);
		}
	}

	/**
	 * 审核通过 添加记录到放款提现申请表中
	 */
	public void saveApplyLoanStatus(String applyNo) {
		List<ApplyLoanStatus> applyLoanStatuses = applyLoanStatusDao.getApplyLoanStatusByApplyNo(applyNo);
		if (applyLoanStatuses.size() != 0) {
			applyLoanStatusDao.deleteLoanStatusByApplyNo(applyNo);
		}
		ApplyLoanStatus applyLoanStatus = new ApplyLoanStatus();
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo", applyNo);
		List<Contract> contractList = contractDao.findListByApplyNo(param);
		if (contractList != null) {
			for (int i = 0; i < contractList.size(); i++) {
				Contract contract = contractList.get(i);
				applyLoanStatus.setOrgLevel2(contract.getOrgLevel2());
				applyLoanStatus.setOrgLevel3(contract.getOrgLevel3());
				applyLoanStatus.setOrgLevel4(contract.getOrgLevel4());
				applyLoanStatus.setApplyNo(applyNo);
				applyLoanStatus.setCustName(contract.getCustName());
				applyLoanStatus.setContractNo(contract.getContractNo());
				applyLoanStatus.setContractAmount(contract.getContractAmount().toString());
				applyLoanStatus.setFactLoanAmount(contract.getFactLoanAmount().toString());
				applyLoanStatus.setLoanModel(contract.getLoanModel());
				applyLoanStatus.setLoanStatus(Constants.LOAN_STATUS_WTS);
				applyLoanStatus.setOrderLoanDate(new Date());
				applyLoanStatus.setContractState(Constants.CONTRACT_STATE_QYWC);
				applyLoanStatus.preInsert();
				applyLoanStatusDao.saveApplyLoanStatus(applyLoanStatus);
			}
		}
	}

	/**
	 * 保存分公司复议-复议结论
	 * 
	 * @param actTaskParam
	 * @param processMap
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveReviewSuggestion(ActTaskParam actTaskParam, Map<String, String> processMap) throws Exception {
		this.saveProcessSuggestionInfo(actTaskParam, processMap);
		if ("black".equals(processMap.get("passFlag"))) {
			blacklistService.joinBalckAndDetails(actTaskParam.getApplyNo(), processMap.get("blacklistRemarks"));
		}

	}

	/**
	 * 保存批复意见-批复结论
	 * 
	 * @param actTaskParam
	 * @param processMap
	 * @throws Exception
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void saveApproveSuggestion(ActTaskParam actTaskParam, Map<String, String> processMap, Model model)
			throws Exception {
		this.saveProcessSuggestionInfo(actTaskParam, processMap);
		String passFlag = processMap.get("passFlag");// 通过还是打回等
		String deleteDataFlag = processMap.get("deleteDataFlag");// 是否要进行拆分
		String suggestionDesc = processMap.get("suggestionDesc");
		String unionFlag = processMap.get("unionFlag");
		String flag = processMap.get("flag");// 提交还是保存
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		String checkApproveProduct = applyInfo.getApplyRegister().getApplyProductTypeCode();
		if(StringUtils.isEmpty(processMap.get("checkApproveProductType"))){
			processMap.put("checkApproveProductType", checkApproveProduct);
		}
		if (Constants.PRODUCT_TYPE_ZGJH.equals(processMap.get("checkApproveProductType"))  && "0".equals(flag) && ("yes".equals(passFlag) || "no".equalsIgnoreCase(passFlag) || "black".equalsIgnoreCase(passFlag) || "finish".equalsIgnoreCase(passFlag)) && (Constants.UTASK_FGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_QYFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_DQFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_SQLR.equalsIgnoreCase(actTaskParam.getTaskDefKey()) || "utask_ms".equalsIgnoreCase(actTaskParam.getTaskDefKey()))) {
			Map<String,String> param = Maps.newConcurrentMap();
			if("yes".equals(passFlag) || "no".equals(passFlag) || "black".equals(passFlag) || "finish".equalsIgnoreCase(passFlag)){
				param.put("status","1");
			}
			param.put("applyNo", actTaskParam.getApplyNo());
			param.put("taskDefKey",actTaskParam.getTaskDefKey());
			param.put("passFlag",passFlag);
			if(!("yes".equals(passFlag) && Constants.UTASK_MS.equals(actTaskParam.getTaskDefKey()))){
				stockTaskReceiveService.updateStockTaskReciveState(param);
			}
			
			String grade = "";
			if("utask_fgsfksh".equalsIgnoreCase(actTaskParam.getTaskDefKey())){
				grade = "4";
			}
			if("utask_qyfksh".equalsIgnoreCase(actTaskParam.getTaskDefKey())){
				grade = "3";
			}
			if("utask_dqfkzysh".equalsIgnoreCase(actTaskParam.getTaskDefKey())){
				grade = "2";
			}
			if("utask_zgsfksh".equalsIgnoreCase(actTaskParam.getTaskDefKey())){
				grade = "1";
			}
			if(!StringUtils.isEmpty(grade) && "yes".equals(passFlag)){
				creStockOpinionDao.updateCreStockOpionState(actTaskParam.getApplyNo(),grade);
			}
		}
		if (Constants.PRODUCT_TYPE_ZGJH.equals(processMap.get("checkApproveProductType"))  && "0".equals(flag) && ("back".equals(passFlag) || "backToSQLR".equals(passFlag)) && (Constants.UTASK_QYJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_FGSJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_DQFKJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_FGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_QYFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_DQFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_FGSSX.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSZJLSH.equals(actTaskParam.getTaskDefKey()))) {
			Map<String,String> param = Maps.newConcurrentMap();
			if("back".equals(passFlag) || "backToSQLR".equals(passFlag)){
				param.put("status","0");
			}
			param.put("applyNo", actTaskParam.getApplyNo());
			param.put("taskDefKey", actTaskParam.getTaskDefKey());
			param.put("passFlag", passFlag);
			stockTaskReceiveService.updateStockTaskReciveState(param);
		}
		Map<String, Object> processParams = Maps.newHashMap();
		if ("0".equals(flag)) {
			if ("yes".equals(passFlag)) {
				Map<String, Object> params = Maps.newHashMap();
				params.put("applyNo", actTaskParam.getApplyNo());
				params.put("taskDefKey", actTaskParam.getTaskDefKey());
				params.put("lockFlag", "1");
				videoUploadDao.lockVideoMessageByApplyNoAndTaskDefKey(params);
			}
			if (Constants.UTASK_DQFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
				if ("yes".equals(passFlag)) {
					processParams.put("is_utask_dqfkzysh_refuse", "1");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【提交】" + suggestionDesc, "提交", processParams);
				} else if ("no".equals(passFlag)) {
					processParams.put("is_utask_dqfkzysh_refuse", "0");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【拒绝】" + suggestionDesc, "拒绝", processParams);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				} else if ("backToSQLR".equals(passFlag)) {
					actTaskService.backOtherNode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【打回到申请录入】" + suggestionDesc, Constants.UTASK_SQLR);
				}
			} else if (Constants.UTASK_DQFKJLSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
				if ("yes".equals(passFlag)) {
					processParams.put("is_utask_dqfkjlsh_refuse", "1");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【提交】" + suggestionDesc, "提交", processParams);
				} else if ("no".equals(passFlag)) {
					processParams.put("is_utask_dqfkjlsh_refuse", "0");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【拒绝】" + suggestionDesc, "拒绝", processParams);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				} else if ("backToSQLR".equals(passFlag)) {
					actTaskService.backOtherNode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【打回到申请录入】" + suggestionDesc, Constants.UTASK_SQLR);
				}
			} else if (Constants.UTASK_ZGSJLSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
				if ("yes".equals(passFlag)) {
					String contractAmount = processMap.get("contractAmount");
					String checkApproveProductType = processMap.get("checkApproveProductType");
					boolean isNeedGeneralAudit = generalManagerAuditService.isGeneralManagerAudit(contractAmount,
							checkApproveProductType, actTaskParam.getApplyNo());
					if (isNeedGeneralAudit) {
						processParams.put("is_utask_zgszjlsh_need", "0");
						actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
								"【提交】" + suggestionDesc, "提交", processParams);
					} else {
						try {
							if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(unionFlag)) {
								if("no".equals(deleteDataFlag)){
									deletePartData(actTaskParam.getApplyNo());
								}else{
									deleteData(actTaskParam.getApplyNo());
								}
								logger.info("开始进行拆分---" + actTaskParam.getApplyNo());
								jointCredit.resoluteContract(actTaskParam,deleteDataFlag);
								logger.info("拆分结束---" + actTaskParam.getApplyNo());
							}else{
								if("yes".equals(deleteDataFlag)){
									Map<String, String> params = Maps.newHashMap();
									params.put("applyNo", actTaskParam.getApplyNo());
									repayPlanUnionDao.deleteRepayPlanUnion(params);
									contractDao.deleteContractDataByApplyNo(params);
								}
							}
						} catch (Exception e) {
							logger.error("分公司授信失败", e);
						}
						model.addAttribute("checkFlag", actTaskParam.getTaskDefKey());
						processParams.put("is_utask_zgszjlsh_need", "1");
						actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
								"【提交】" + suggestionDesc, "提交", processParams);
					}
				} else if ("no".equals(passFlag)) {
					taskCenterService.finishProcessInstanceByRefuseStatus(actTaskParam, suggestionDesc);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				} else if ("backToSQLR".equals(passFlag)) {
					actTaskService.backOtherNode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【打回到申请录入】" + suggestionDesc, Constants.UTASK_SQLR);
				} else if ("finish".equals(passFlag)) {
					actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【同意并结束流程】" + suggestionDesc);
					applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(),
							Constants.APPLY_STATUS_AGREEANDFINISHI);
				}

			} else if (Constants.UTASK_HTYY.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
				if ("yes".equals(passFlag)) {
					processParams.put("is_utask_cancel", "1");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【提交】" + suggestionDesc, "提交", processParams);
				} else if ("no".equals(passFlag)) {
					processParams.put("is_utask_cancel", "0");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【取消预约】" + suggestionDesc, "取消预约", processParams);
					applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(), Constants.APPLY_STATUS_REFUSED);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				} else if ("backToSQLR".equals(passFlag)) {
					actTaskService.backOtherNode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【打回到申请录入】" + suggestionDesc, Constants.UTASK_SQLR);
				}
			} else if (Constants.UTASK_FGSFY.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
				if ("yes".equals(passFlag)) {
					model.addAttribute("checkFlag", actTaskParam.getTaskDefKey());
					processParams.put("is_utask_fgsfy_cancel", "1");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【提交】" + suggestionDesc, "提交", processParams);
				} else if ("no".equals(passFlag)) {
					processParams.put("is_utask_fgsfy_cancel", "0");
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【拒绝】" + suggestionDesc, "拒绝", processParams);
					applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(), Constants.APPLY_STATUS_REFUSED);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				}
			} else {
				if ("yes".equals(passFlag)) {
					if (Constants.UTASK_FGSFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())){
						String pushflag = pushNewAddFieldToSV(actTaskParam.getApplyNo());
						if(!("1".equalsIgnoreCase(pushflag))){
							throw new Exception();
						}
					}
					if (Constants.UTASK_ZGSSXQR.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
						guaranteeRelationService.deleteByApplyNo(actTaskParam.getApplyNo());
						//主借企业
						ApplyRelation byApplyNoAndRoleType = applyRelationService.getByApplyNoAndRoleType(actTaskParam.getApplyNo(), "5");
						//主借企业，担保企业  担保人
						List<GuaranteeRelation> guaranteeRelationList = applyRelationService.getMainAll(actTaskParam.getApplyNo());

						for (GuaranteeRelation guaranteeRelation : guaranteeRelationList) {
							guaranteeRelation.setCompanyId(byApplyNoAndRoleType.getCustId());
							if("3".equals(guaranteeRelation.getRoleType())) {
								guaranteeRelation.setRoleType("1");
							}
							if("6".equals(guaranteeRelation.getRoleType())) {
								guaranteeRelation.setRoleType("2");
							}
							guaranteeRelation.preInsert();
						}
						//批量借款企业的担保人，担保企业
						List<GuaranteeRelation> guaranteeBatchRelationList = applyCompanyRelationService.getBatchAll(actTaskParam.getApplyNo());
						for (GuaranteeRelation guaranteeRelation : guaranteeBatchRelationList) {
							guaranteeRelation.preInsert();
						}
						guaranteeRelationList.addAll(guaranteeBatchRelationList);
						guaranteeRelationService.batchInsert(guaranteeRelationList);
					}	
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【提交】" + suggestionDesc, "提交", null);
					if (Constants.UTASK_ZGSFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
						User currentUser = UserUtils.getUser();
						if (currentUser.getOffice() != null) {
							taskCenterService.insertOfficeApplyRelation(actTaskParam.getApplyNo(),
									currentUser.getOffice().getId());
						} else {
							logger.warn("用户【" + currentUser.getLoginName() + "的部门为空！");
						}
					}
					if (Constants.UTASK_ZGSZJLSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
						try {
							if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(unionFlag)) {
								if("no".equals(deleteDataFlag)){
									deletePartData(actTaskParam.getApplyNo());
								}else{
									deleteData(actTaskParam.getApplyNo());
								}
								logger.info("开始进行拆分---" + actTaskParam.getApplyNo());
								jointCredit.resoluteContract(actTaskParam,deleteDataFlag);
								logger.info("拆分结束---" + actTaskParam.getApplyNo());
							}else{
								if("yes".equals(deleteDataFlag)){
									Map<String, String> params = Maps.newHashMap();
									params.put("applyNo", actTaskParam.getApplyNo());
									repayPlanUnionDao.deleteRepayPlanUnion(params);
									contractDao.deleteContractDataByApplyNo(params);
								}
							}
						} catch (Exception e) {
							logger.error("分公司授信失败", e);
						}
					}
				} else if ("no".equals(passFlag)) {
					taskCenterService.finishProcessInstanceByRefuseStatus(actTaskParam, suggestionDesc);
					backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
				} else if ("backToSQLR".equals(passFlag)) {
					actTaskService.backOtherNode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
							"【打回到申请录入】" + suggestionDesc, Constants.UTASK_SQLR);
				}
			}
			if ("back".equals(passFlag)) {
				if(Constants.UTASK_HTYY.equalsIgnoreCase(actTaskParam.getTaskDefKey()) || Constants.UTASK_FGSSX.equalsIgnoreCase(actTaskParam.getTaskDefKey())){
					checkApproveDao.deleteChekApproveBackUp(actTaskParam.getApplyNo());
					checkApproveDao.insertCheckApproveBackUp(actTaskParam.getApplyNo());
				}
				actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(),
						"【打回】" + suggestionDesc);
			} else if ("black".equals(passFlag)) {
				blacklistService.joinBalckAndDetails(actTaskParam.getApplyNo(), processMap.get("blacklistRemarks"));
				taskCenterService.finishProcessInstanceByRefuseStatus(actTaskParam, processMap.get("suggestionDesc"));
				backMessageToGED(actTaskParam.getApplyNo(), null, Constants.GED_APPLY_STATUS_SQJJ, null);
			}
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public String pushNewAddFieldToSV(String applyNo) {
		WorkOrderNewField workOrderNewField = new WorkOrderNewField();
		// 个⼈人流⽔水信息    主借人     配偶   担保人
		List<PersonalWaterField> personalWaterInformation = creditLineBankService.getLineBankAndDetail(applyNo);
		// 个⼈人征信信息
		List<PersonalCreditField> personalCreditInformation = creditCustService.getCreditByApply(applyNo);

		List<WorkField> workInformation = custInfoService.getMainWork(applyNo);//工作信息
		//List<WorkField> workInformation2 = custInfoService.getBatchWork(applyNo);//工作信息
		//workInformation.addAll(workInformation2);

		List<ResidenceField> residenceInformation = custInfoService.getMainRealAddress(applyNo);	// 居住地信息
		//List<ResidenceField> residenceInformation1 = custInfoService.getBatchRealAddress(applyNo);	// 居住地信息

		List<OtherField>	otherInformation = new ArrayList();		// 其他信息

		List<OpinionFeedbackField> opinionFeedback = new ArrayList();//意见反馈信息

		List<CompanyCreditField> companyCreditInformation = creditCompanyService.getByapplyNo(applyNo)   ;// 企业征信信息

		List<EnterpriseFinancialField> enterpriseFinancialInformation = themisReportHeadDao.getFinancialHeadByApplyNo(applyNo) ;// /*企业财务信息*/
		workOrderNewField.setApp_no(applyNo);
		workOrderNewField.setEnterpriseFinancialInformation(enterpriseFinancialInformation);
		workOrderNewField.setCompanyCreditInformation(companyCreditInformation);
		workOrderNewField.setResidenceInformation(residenceInformation);
		workOrderNewField.setWorkInformation(workInformation);
		workOrderNewField.setPersonalCreditInformation(personalCreditInformation);
		workOrderNewField.setPersonalWaterInformation(personalWaterInformation);
		workOrderNewField.setOtherInformation(otherInformation);
		workOrderNewField.setOpinionFeedback(opinionFeedback);
		return Facade.facade.SVAddField(workOrderNewField);
	}

	@Transactional(value = "CRE", readOnly = false)
	public ProcessSuggestionInfo saveProcessSuggestionInfo(ActTaskParam actTaskParam, Map<String, String> processMap) {
		ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
		processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
		processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		processSuggestionInfo.setSuggestionDesc(processMap.get("suggestionDesc"));
		processSuggestionInfo.setIsAbnormal(processMap.get("isAbnormal"));
		processSuggestionInfo.setIsAbnormal2(processMap.get("isAbnormal2"));
		processSuggestionInfo.setReserveTime(processMap.get("reserveTime"));
		processSuggestionInfo.setPassFlag(processMap.get("passFlag"));
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo info = findByApplyNo(params);
		if (info != null) {
			processSuggestionInfo.setId(info.getId());
		}
		this.save(processSuggestionInfo);
		// 提交保存 节点信息
		if ("0".equals(processMap.get("flag"))) {
			this.insertFlag(processSuggestionInfo, actTaskParam.getTaskDefKey());
		}
		return processSuggestionInfo;
	}

	/**
	 * 总公司批复意见
	 * 
	 * @param params
	 * @return
	 */
	public List<String> findTopComApproveSugg(Map<String, String> params) {
		return super.dao.findTopComApproveSugg(params);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void deleteData(String applyNo) {
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		checkApproveUnionDao.deleteCheckApproveUnion(params);
		repayPlanUnionDao.deleteRepayPlanUnion(params);
		contractDao.deleteContractDataByApplyNo(params);
		gqgetAssetCarUnionDao.deleteGqgetAssetCarUnionData(applyNo);
		gqgetAssetHouseUnionDao.deleteGqgetAssetHouseUnionData(applyNo);
		gqgetComInfoUnionDao.deleteGqgetComInfoUnionData(applyNo);
		mortageEquipmentUnionDao.deleteMortageEquipmentUnionData(applyNo);
		mortgagedCompanyUnionDao.deleteMortgagedCompanyUnionData(applyNo);
		companyCustInfoRelatedDao.deleteCompanyCustInfoRelatedByApplyNo(applyNo);
	}


	@Transactional(value = "CRE", readOnly = false)
	public void deletePartData(String applyNo) {
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		gqgetAssetCarUnionDao.deleteGqgetAssetCarUnionData(applyNo);
		gqgetAssetHouseUnionDao.deleteGqgetAssetHouseUnionData(applyNo);
		gqgetComInfoUnionDao.deleteGqgetComInfoUnionData(applyNo);
		mortageEquipmentUnionDao.deleteMortageEquipmentUnionData(applyNo);
		mortgagedCompanyUnionDao.deleteMortgagedCompanyUnionData(applyNo);
	}


	@Transactional(value = "CRE", readOnly = false)
	public void backMessageToGED(String applyNo, String applyRegisterId, String approveState, String gedId) {
		String applyId = null;
		String custName = null;
		String changeDate = DateUtils.getDateTime();
		try {
			if (StringUtils.isNull(gedId)) {
				ApplyRegister applyRegister = new ApplyRegister();
				if (StringUtils.isNull(applyNo)) {
					if (!(StringUtils.isNull(applyRegisterId))) {
						applyRegister = applyRegisterService.get(applyRegisterId);
					}
				} else {
					applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
				}
				applyId = applyRegister.getIftApplyId();
			} else {
				applyId = gedId;
			}
			if (StringUtils.isNotEmpty(applyId)) {
				GedApplyRegister register = gedApplyRegisterService.getGedApplyRegisterByApplyId(applyId);
				register.setGedApplyStatus(approveState);
				gedApplyRegisterService.save(register);
				custName = register.getCustName();
				GedApproveState gedApproveState = new GedApproveState(custName, applyId, approveState, changeDate);
				Facade.facade.approveStates(gedApproveState);
			}
		} catch (Exception e) {
			logger.error("向冠易贷返回信息失败", e);
		}
	}

}
package com.resoft.outinterface.rest.financialPlatform;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.TransferWithHoldEntity;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.ZjResoonseInfo;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.checkAccount.utils.CheckAccountUtils;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.activemq.service.ProducerService;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.AccountEnterResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDespositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.FeeList;
import com.resoft.outinterface.rest.financialPlatform.entry.FinancialPlatformResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingList;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingResponse;
import com.resoft.outinterface.rest.financialPlatform.service.FinancialPlatformService;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.utils.Facade;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.resoft.outinterface.utils.RSAUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/f/rest/funds/service")
public class FinanPlatfromRestService {
	private static final Logger logger = LoggerFactory.getLogger(FinancialPlatformClient.class);
	@Autowired
	private FinancialPlatformService financialPlatformService;
	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private Acc4FinancialPlatformServer acc4FinancialPlatformServer;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ApplyLoanStatusService applyLoanStatusService;
	@Autowired
	private StaContractStatusService staContractStatusService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CreGuaranteeCompanyService creGuranteeCompanyService;
	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private CompensatoryDetailService compensatoryDetialService;
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	

	// 借款人放款
	@RequestMapping(method = RequestMethod.POST, value = "borrowerLoan")
	public String form(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("借款人放款回盘接收：" + json);
		BorrowerLoanResponse obj = null;
		String str = "";
		try {
			obj = (BorrowerLoanResponse) JsonTransferUtils.json2Bean(json, BorrowerLoanResponse.class);
			if ("0000".equals(obj.getResp_code())) {
				financialPlatformService.updateBorrowerLoanSuc(obj);
			} else {
				financialPlatformService.updateBorrowerLoanFai(obj.getContract_no());
			}
		} catch (Exception e) {
			logger.error("接口传入参数格式不正确", e);
			str = "接口传入参数格式不正确";
		}
		rp.setSeq_no(obj.getSeq_no());
		rp.setSignature(obj.getSignature());
		rp.setMchn(obj.getMchn());
		rp.setTrade_type(obj.getTrade_type());
		if (str != "") {
			rp.setResp_code("1111");
			rp.setResp_msg(str);
		} else {
			rp.setResp_code("0000");
			rp.setResp_msg("成功");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "借款人放款回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！");
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("借款人放款回盘接口返回值转化JSON失败", e);
		}
		logger.debug("借款人放款回盘发送：" + str);
		return str;
	}

	// 借款人提现 
	@RequestMapping(method = RequestMethod.POST, value = "borrowerDeposit")
	public String form2(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("借款人提现回盘接收：" + json);
		BorrowerDespositResponse obj = null;
		String str = "";
		try {
			obj = (BorrowerDespositResponse) JsonTransferUtils.json2Bean(json, BorrowerDespositResponse.class);
			if ("0000".equals(obj.getResp_code()) || "00000000".equals(obj.getResp_code())) {
				financialPlatformService.updateBorrowerDepositSuc(obj);
				try {
					AccContract contract = contractService.getCreContractByContractNo(obj.getContract_no());
					// 转换日期格式
					SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdfs.parse(obj.getWithDraw_date());
					} catch (ParseException e) {
						str = "日期转换错误";
						logger.error("日期转换错误", e);
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
					str = acc4FinancialPlatformServer.insertAccRepayPlanAndAccContract(repayPlanList, contract);
				} catch (Exception e) {
					str = "向acc_contract或acc_repay_plan中插入数据时出错";
					logger.error("向acc_repay_plan中插入数据时出错", e);
				}

				try {
					acc4FinancialPlatformServer.insertAccStaContractStatus(obj.getContract_no());
				} catch (Exception e) {
					str = "插入acc_sta_contract_status出错";
					logger.error("插入acc_sta_contract_status出错", e);
				}

				try {
					applyLoanStatusService.updateContractState(obj.getContract_no());
				} catch (Exception e) {
					str = "更新cre_apply_loan_status出错";
					logger.error("更新cre_apply_loan_status出错", e);
				}
			} else {
				financialPlatformService.updateBorrowerDepositFai(obj.getContract_no());
			}
		} catch (Exception e) {
			str = "接口传入参数格式不正确";
			logger.error("接口传入参数格式不正确", e);
		}
		rp.setSeq_no(obj.getSeq_no());
		rp.setSignature(obj.getSignature());
		rp.setMchn(obj.getMchn());
		rp.setTrade_type(obj.getTrade_type());
		if (str != "") {
			rp.setResp_code("1111");
			rp.setResp_msg(str);
			try {
				str = JsonTransferUtils.bean2Json(rp);
			} catch (IOException e) {
				logger.error("主借人提现接口返回值转化JSON失败", e);
			}
			logger.debug(str);
			return str;
		} else {
			rp.setResp_code("0000");
			rp.setResp_msg("成功");
		}

		String contractType = financialPlatformService.getProductTypeByContractNo(obj.getContract_no());
		String applyNo = financialPlatformService.getApplyNoByContractNo(obj.getContract_no());
		if (Constants.PRODUCT_TYPE_DY.equals(contractType) || Constants.PRODUCT_TYPE_HH.equals(contractType)) {
			// 抵押标发标
			// Facade.facade.issuingTenderData(applyNo);
		} else {
			// 冠E通满标提现
			Facade.facade.withdrawSuccessBidData(applyNo);
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "借款人提现回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("主借人提现回盘接口返回值转化JSON失败", e);
		}
		logger.debug("借款人提现回盘发送：" + str);
		return str;
	}

	// 抵押权人提现
	@RequestMapping(method = RequestMethod.POST, value = "mortgageeDeposit")
	public String form3(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("抵押权人提现回盘接收：" + json);
		MortgageeDepositResponse obj = null;
		String str = "";
		try {
			obj = (MortgageeDepositResponse) JsonTransferUtils.json2Bean(json, MortgageeDepositResponse.class);
			if ("0000".equals(obj.getResp_code())) {
				financialPlatformService.mortgageeDepositInterfaceIn(obj);
			}
		} catch (Exception e) {
			logger.error("接口传入参数格式不正确", e);
			str = "接口传入参数格式不正确";
		}
		rp.setSeq_no(obj.getSeq_no());
		rp.setSignature(obj.getSignature());
		rp.setMchn(obj.getMchn());
		rp.setTrade_type(obj.getTrade_type());
		if (str != "") {
			rp.setResp_code("1111");
			rp.setResp_msg(str);
		} else {
			rp.setResp_code("0000");
			rp.setResp_msg("成功");
		}

		String applyNo = financialPlatformService.getApplyNoByContractNo(obj.getContract_no());
		try {
			Facade.facade.withdrawSuccessBidData(applyNo);
		} catch (Exception e) {
			logger.error("冠E通满标提现接口访问失败!", e);
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "抵押权人提现回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("抵押权人提现回盘接口返回值转化JSON失败", e);
		}
		logger.debug("抵押权人提现回盘发送：" + str);
		return str;
	}

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");

	// 还款代扣
	@RequestMapping(method = RequestMethod.POST, value = "repaymentWithholding")
	public String form4(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("还款划扣回盘接收" + json);
		RepaymentWithholdingResponse obj = null;
		String str = "";
		try {
			obj = (RepaymentWithholdingResponse) JsonTransferUtils.json2Bean(json, RepaymentWithholdingResponse.class);
			acc4FinancialPlatformServer.repaymentWithhodingResultIn(obj);
			List<DeductResult> deductResult = acc4FinancialPlatformServer.findDeductResultBySySNo(obj.getSeq_no());
			if(deductResult.size() == 0){
				acc4FinancialPlatformServer.repaymentResultInDeduct(obj.getSeq_no());
			}
		} catch (IOException e) {
			logger.error("接口传入参数格式不正确", e);
			str = "接口传入参数格式不正确";
		} catch (Exception e) {
			logger.error("还款划扣回盘数据入库失败", e);
			str = "还款划扣回盘数据入库失败";
		}
		rp.setMchn(obj.getMchn());
		rp.setSeq_no(obj.getSeq_no());
		rp.setTrade_type(obj.getTrade_type());
		if (str != "") {
			rp.setResp_code("1111");
			rp.setResp_msg(str);
		} else {
			rp.setResp_code("0000");
			rp.setResp_msg("成功");
			// 调用存储
			Connection connection = null;
			try {
				connection = dataSource.getConnection();
				//手工划扣
				CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT('" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "','" + obj.getSeq_no() + "')}");
				callableStatement.execute();
//				pushUpdateRepaymentPlan(obj.getRepay_list());
//				Facade.facade.repaymentSynState(obj.getRepay_list());
			} catch (SQLException e) {
				logger.error("存储调用失败！", e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("连接关闭失败！", e);
				}
			}
		}

		try {
			List<RepaymentWithholdingList> lrwl = obj.getRepay_list();
			Iterator<RepaymentWithholdingList> it = lrwl.iterator();
			while (it.hasNext()) {
				RepaymentWithholdingList rwl = it.next();
				// 判断当前合同是否提前还款表中，
				String breakRepayStatus = acc4FinancialPlatformServer.getBreakRepayStatusByContractNo(rwl.getContract_no());
				if (breakRepayStatus != null && breakRepayStatus != "") {
					String applyNo = financialPlatformService.getApplyNoByContractNo(rwl.getContract_no());
					Facade.facade.repaymentBidData(applyNo);
				}
				String contractStatus = acc4FinancialPlatformServer.findContractStatusByContractNo(rwl.getContract_no());
				Contract contract = contractService.getContractByContractNo(rwl.getContract_no());
				if ("0700".equals(contractStatus) || "0900".equals(contractStatus) && contract != null && "0".equals(contract.getGuaranteeRelation())) {
					creGuranteeCompanyService.updateGuranteeInfoByContractNo(contract);
				}
			}
		} catch (Exception e) {
			logger.error("冠E通提前还款接口访问失败!", e);
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "还款划扣回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("还款划扣回盘接口返回值转化JSON失败", e);
		}
		logger.debug("还款划扣回盘发送" + str);
		return str;
	}

	// 入账接口暂不做处理，直接返回成功。
	@RequestMapping(method = RequestMethod.POST, value = "accountEnter")
	public String form5(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("入账回盘接收" + json);
		AccountEnterResponse obj = null;
		try {
			obj = (AccountEnterResponse) JsonTransferUtils.json2Bean(json, AccountEnterResponse.class);
		} catch (Exception e) {
			logger.error(e.toString() + "接口传入参数格式不正确");
			return null;
		}
		rp.setMchn(obj.getMchn());
		rp.setSeq_no(obj.getSeq_no());
		rp.setTrade_type(obj.getTrade_type());
		rp.setResp_code("0000");
		rp.setResp_msg("成功");
		String str = "";
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("接口传入参数格式不正确");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "入账回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.debug("入账回盘发送" + json);
		return str;
	}

	// 银行卡变更
	@RequestMapping(method = RequestMethod.POST, value = "bankCardChange")
	public String form6(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("银行卡变更回盘接收" + json);
		BankCardChangeResponse obj = null;
		BankCardChangeResponse obj2 = new BankCardChangeResponse();
		try {
			System.out.println(JsonTransferUtils.bean2Json(obj2));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String str = "";
		try {
			obj = (BankCardChangeResponse) JsonTransferUtils.json2Bean(json, BankCardChangeResponse.class);
			if ("0000".equals(obj.getResp_code())) {
				acc4FinancialPlatformServer.bankCardUpdateSuc(obj);
			} else {
				acc4FinancialPlatformServer.bankCardUpdateFai(obj);
			}
		} catch (Exception e) {
			logger.error(e.toString() + "接口传入参数格式不正确");
			return null;
		}
		rp.setSeq_no(obj.getSeq_no());
		rp.setSignature(obj.getSignature());
		rp.setMchn(obj.getMchn());
		rp.setTrade_type(obj.getTrade_type());
		rp.setResp_code("0000");
		rp.setResp_msg("成功");
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "银行卡变更回盘" , rp.getResp_msg(), sendDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("银行卡变更接口数据转换错误", e);
		}
		logger.debug("银行卡变更回盘发送" + str);
		return str;
	}

	// 流标
	@RequestMapping(method = RequestMethod.POST, value = "abortiveTender")
	public String form7(@RequestBody String json) {
		Date sendDate = new Date();
		FinancialPlatformResponse rp = new FinancialPlatformResponse();
		logger.debug("流标回盘接收" + json);
		AbortiveTenderResponse obj = null;
		AbortiveTenderResponse obj2 = new AbortiveTenderResponse();
		List<FeeList> adsa = new ArrayList<FeeList>();
		FeeList asdq = new FeeList();
		adsa.add(asdq);
		obj2.setFee_list(adsa);
		try {
			System.out.println(JsonTransferUtils.bean2Json(obj2));
		} catch (IOException e2) {
			logger.error("流标接口数据转换错误", e2);
		}
		String str = "";
		try {
			obj = (AbortiveTenderResponse) JsonTransferUtils.json2Bean(json, AbortiveTenderResponse.class);
			if ("0000".equals(obj.getResp_code())) {
				financialPlatformService.abortiveTenderUpApply(obj.getContract_no());
				financialPlatformService.abortiveTenderInsert(obj);
			}
		} catch (Exception e) {
			logger.error(e.toString() + "接口传入参数格式不正确");
			return null;
		}
		rp.setSeq_no(obj.getSeq_no());
		rp.setSignature(obj.getSignature());
		rp.setMchn(obj.getMchn());
		rp.setTrade_type(obj.getTrade_type());
		rp.setResp_code("0000");
		rp.setResp_msg("成功");
		try {
			interfaceInfoService.save(new InterfaceInfo(obj.getSeq_no(), "流标回盘" , rp.getResp_msg(), sendDate, json));
			
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		try {
			String applyNo = financialPlatformService.getApplyNoByContractNo(obj.getContract_no());
			Facade.facade.GetAbortiveTender(applyNo);
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
			Integer type;
			if(Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
				type=1;
				List<CheckApproveUnion> checkApproveUnionByApplyNo = checkApproveUnionService.getCheckApproveUnionByApplyNo(applyNo);
				for (CheckApproveUnion checkApproveUnion : checkApproveUnionByApplyNo) {
					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(applyNo,checkApproveUnion.getId(), GedClient.ged_lb,type,null);
				}
			}else {
				type=0;
				creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(applyNo,null, GedClient.ged_lb,type,null);
			}
		} catch (Exception e) {
			logger.error("冠E通流标接口访问失败!", e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("冠E通流标接口数据转换错误!", e);
		}
		logger.debug("流标回盘发送" + json);
		return str;
	}
	
	/**
	 * 
	 * @param repayments 
	 */
	private void pushUpdateRepaymentPlan(List<RepaymentWithholdingList> repayments){
		if(repayments.size() > 0){
			String contractNo = repayments.get(0).getContract_no();
			Contract contract = contractService.getContractByContractNo(contractNo);
			ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(contract.getApplyNo());
			//判断是否是联合授信
			if(!(ar.getProcDefKey()!=null && ar.getProcDefKey().contains("union"))){
				List<Contract> conList = contractService.getContractDataByApplyNo(contract.getApplyNo());
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				for(Contract con : conList){
					List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(con.getContractNo(),null);
					for(DeductResultTemp temp : deductResultTemps){
						Map<String,Object> planMap = new HashMap<String,Object>();
						planMap.put("contractNo", con.getContractNo());	//合同编号
						planMap.put("periodNum", temp.getPeriodNum()); 		//期数
						planMap.put("periodState", temp.getRepayPeriodStatus());//0200	待还款, 字典表PERIOD_STATE
						planMap.put("deductDate",temp.getDeductDate());		//还款日期
						planMap.put("repayAmount", temp.getRepayAmount());	//应还金额(元)
						planMap.put("factServiceFee",temp.getServiceFee());	//服务费
						planMap.put("managementFee", temp.getManagementFee());	//账户管理费
						planMap.put("capitalAmount",temp.getCapitalAmount());	//本金
						planMap.put("interestAmount",temp.getInterestAmount());	//利息
						planMap.put("penaltyAmount", temp.getPenaltyAmount());	//违约金
						planMap.put("ineAmount", temp.getFineAmount());			//罚息
						planMap.put("overdueDays", temp.getOverdueDays());		//逾期天数
						list.add(planMap);
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderCode", contract.getApplyNo());
				map.put("contractForms", list);	//
				map.put("creditType", "2");		//授信类型(1联合授信2非联合授信)
				map.put("operatorFlag", "2");	//1插入2更新
				Facade.facade.gedPushUpdateRepaymentPlan(map,  contract.getApplyNo());
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "hbczCallBack")
	public AccAccountDCResponse hbczCallBack(AccAccountDCResponse result) {
		try {
			if (result != null && "0000".equals(result.getResp_code())) {
				String initStr = result.getMchn() + "|" + result.getSeq_no() + "|" + result.getTrade_type() + "|" + result.getData() + "|" + result.getResp_code() + "|" + result.getResp_msg();
				if (RSAUtils.verify(initStr.getBytes(StandardCharsets.UTF_8), Global.getConfig("syspbkey"), result.getSignature())) {
					TransferWithHoldEntity data = JsonTransferUtils.json2Bean(result.getData(), TransferWithHoldEntity.class);
					GedRepayment gedRepayment = new GedRepayment();
					Map<String, String> params = new HashMap<>();
					params.put("contractNo", data.getContractNo());
					List<StaRepayPlanStatus> statusList = staRepayPlanStatusService.findUpdateRepayPeroidStatus(params);
					CheckAccountUtils.initGedRepayment(gedRepayment, statusList, data, result);
					acc4FinancialPlatformServer.hbczCallBack(gedRepayment);
					String repayment = JsonTransferUtils.bean2Json(gedRepayment);
					producerService.sendMessageRepayment(repayment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResp_code("90080000");
			result.setResp_msg("更新划拨充值失败");
			logger.error("划拨充值 回调接口异常 :" + result, e);
		}
		try {
			TransferWithHoldEntity data = JsonTransferUtils.json2Bean(result.getData(), TransferWithHoldEntity.class);
			interfaceInfoService.save(
				new InterfaceInfo(data.getContractNo(), "划拨充值回调", result.getResp_msg(), new Date(),
					JsonTransferUtils.bean2Json(result)));
		} catch (Exception e) {
			logger.error("接口信息记录失败！", e);
		}
		return result;
	}
	
	/**
	 * 转账后资金回调结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "capitalTransferBack")
	public AccAccountDCResponse compensatoryDetailBack(AccAccountDCResponse accAccountDCResponse){
		try {
			if (accAccountDCResponse != null && "0000".equals(accAccountDCResponse.getResp_code())) {
			compensatoryDetialService.updateCompensatoryStatusBySeqNum(accAccountDCResponse.getSeq_no());
			CompensatoryDetail compensatoryDetail = compensatoryDetialService.queryCompenMoneyBySeqNo(accAccountDCResponse.getSeq_no(),"0");
			String serialNum = OutInterfaceUtils.makeSeqNo();
			String compensatoryType = compensatoryDetail.getCompensatoryType();
			if (compensatoryDetail != null) {
						Map<String,String> param = new HashMap<>();
						param.put("contractNo",compensatoryDetail.getContractNo());
						param.put("seqNo",serialNum);
						param.put("custId", accAccountDCResponse.getCust_Id());
						String repayMent = JsonTransferUtils.bean2Json(param);		
						SendGETResponse sendGetResponse =compensatoryDetialService.sendGET(compensatoryDetail);
						if(sendGetResponse!=null&&sendGetResponse.getResult().contains("success")) {
							
							if("1".equals(compensatoryType)) {//担保人代偿  入账
								transPushMoney(compensatoryDetail,serialNum);
								producerService.pushMoney(repayMent);
								logger.info("代偿成功,成功通知冠E通,请稍后查看入账结果!");
							}else {
								// 担保公司 和 平台资金
								logger.info("代偿成功,成功通知冠E通!");
							}
						}else {
								String msgGet="";
								if(sendGetResponse!=null&&sendGetResponse.getMsg()!=null) {
									msgGet=sendGetResponse.getMsg();
								}
								if("1".equals(compensatoryType)) {//担保人代偿  入账
									transPushMoney(compensatoryDetail,serialNum);
									producerService.pushMoney(repayMent);
									logger.info("代偿成功,成功通知冠E通,请稍后查看入账结果!");
								}
								logger.info("代偿成功,通知冠E通失败"+msgGet+"!");
							}
					}
			}
			
		} catch (Exception e) {
			logger.error("更新代偿状态失败!", e);
			accAccountDCResponse.setResp_code("90080000");
			accAccountDCResponse.setResp_msg("更新代偿或通知冠E通失败");
		}
		
		try {
			ZjResoonseInfo zjResoonseInfo = JsonTransferUtils.json2Bean(accAccountDCResponse.getData(), ZjResoonseInfo.class);
			interfaceInfoService.save(new InterfaceInfo(zjResoonseInfo.getContractNo(), "代偿资金回调" , accAccountDCResponse.getResp_msg(), new Date(),JsonTransferUtils.bean2Json(accAccountDCResponse)));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		return accAccountDCResponse;
	}
	
	
	/**
	 * 还款资金回调
	 */
	@RequestMapping(method = RequestMethod.POST, value = "repaymentCapoitalBack")
	public AccAccountDCResponse repaymentCapoitalBack(AccAccountDCResponse accAccountDCResponse){
		try {
			if (accAccountDCResponse != null && "0000".equals(accAccountDCResponse.getResp_code())) {
				CompensatoryDetail compensatoryDetail = compensatoryDetialService.queryCompenMoneyBySeqNo(accAccountDCResponse.getSeq_no(),"1");
				if (compensatoryDetail != null) {
					Map<String,String> param = Maps.newHashMap();
					param.put("contractNo",compensatoryDetail.getContractNo());
					param.put("periodNum",compensatoryDetail.getPeriodNum());
					BigDecimal surplusAmount = compensatoryDetail.getSurplusAmount().add(compensatoryDetail.getCompensatoryAmount());
					BigDecimal compensatoryAmount = compensatoryDetail.getCompensatoryAmount();
					compensatoryDetialService.updateSendZjStatus(param,surplusAmount,compensatoryAmount);
				}else{
					logger.info("查询不到对应流水信息----------------------------------------------");
				}
			}else {
				logger.info("资金转账失败----------------------------------------------");
			}	
		} catch (Exception e) {
			logger.error("代偿还款处理失败!", e);
			accAccountDCResponse.setResp_code("90080000");
			accAccountDCResponse.setResp_msg("代偿还款处理失败");
		}
		
		try {
			ZjResoonseInfo zjResoonseInfo = JsonTransferUtils.json2Bean(accAccountDCResponse.getData(), ZjResoonseInfo.class);
			interfaceInfoService.save(new InterfaceInfo(zjResoonseInfo.getContractNo(), "代偿还款资金回调" , accAccountDCResponse.getResp_msg(), new Date(),JsonTransferUtils.bean2Json(accAccountDCResponse)));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		return accAccountDCResponse;
	}
	
	private void transPushMoney(CompensatoryDetail compensatoryDetail,String serialNum) {
		compensatoryDetialService.insertDeductTable(compensatoryDetail,serialNum);
	}
}

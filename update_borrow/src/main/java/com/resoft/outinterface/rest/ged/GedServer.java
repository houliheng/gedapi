package com.resoft.outinterface.rest.ged;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import com.resoft.outinterface.rest.ged.entity.*;

import com.resoft.outinterface.rest.ged.entity.AdvanceFullRepayment;
import com.resoft.outinterface.rest.ged.entity.request.ConfirmGuranteeRelationRequest;
import com.resoft.outinterface.rest.ged.entity.request.ContractNoListRequest;
import com.resoft.outinterface.rest.ged.entity.request.ContractSignFlagRequest;
import com.resoft.outinterface.rest.ged.entity.request.CreditInfoToRequest;
import com.resoft.outinterface.rest.ged.entity.response.BorrowLoanResponse;
import com.resoft.outinterface.rest.ged.entity.response.ContractSignFlagResponse;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanListResponse;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanResponse;
import com.resoft.outinterface.rest.ged.entity.response.RepayPlanStayDetailResponse;
import com.resoft.outinterface.rest.ged.utils.RepaymentUtils;
import com.thinkgem.jeesite.common.utils.BusinessException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.WithdrawStream.service.CreWithdrawStreamService;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.activemq.service.ProducerService;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.companyHistory.service.AccCompanyHistoryService;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.credit.repayPlanUnion.service.RepayPlanUnionService;
import com.resoft.outinterface.rest.ged.entity.*;
import com.resoft.outinterface.rest.ged.service.GedClientService;
import com.resoft.outinterface.rest.ged.service.GedServerService;
import com.resoft.outinterface.rest.sendGET.entry.SendGETRequest;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(value = "/f/rest/api/")
public class GedServer {

	private static final Logger logger = LoggerFactory.getLogger(GedServer.class);

	private CreGedBorrowStatusService creGedBorrowStatusService = SpringContextHolder.getBean("creGedBorrowStatusService");
	private GedServerService gedService = SpringContextHolder.getBean("gedServerService");
	private ApplyLoanStatusService applyLoanStatusService = SpringContextHolder.getBean("applyLoanStatusService");
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	private GedAccountService gedAccountService = SpringContextHolder.getBean("gedAccountService");
	private AccContractDao accContractDao = SpringContextHolder.getBean("accContractDao");
	private StaContractStatusService staContractStatusService = SpringContextHolder.getBean("staContractStatusService");
	private Acc4FinancialPlatformServer acc4FinancialPlatformServer = SpringContextHolder.getBean("acc4FinancialPlatformServer");
	private CompensatoryDetailService compensatoryDetailService = SpringContextHolder.getBean("compensatoryDetailService");
	private CreGedAccountCompanyService creGedAccountCompanyService = SpringContextHolder.getBean("creGedAccountCompanyService");
	private CreWithdrawStreamService creWithdrawStreamService = SpringContextHolder.getBean("creWithdrawStreamService");
	private StaRepayPlanStatusService staRepayPlanStatusService = SpringContextHolder.getBean("staRepayPlanStatusService");
	private GedClientService gedClientService = SpringContextHolder.getBean("gedClientService");
	private AccCompanyHistoryService accCompanyHistoryService = SpringContextHolder.getBean("accCompanyHistoryService");
	@Autowired
	private ProducerService producerService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private RepayPlanService repayPlanService;
	@Autowired
	private com.resoft.credit.contract.service.ContractService creContractService;
	@Autowired
	private RepayPlanUnionService repayPlanUnionService;
	@Autowired
	private AccRepayPlanService accRepayPlanService;
	@Autowired
	private UnderCompanyInfoService underCompanyInfoService;


	/**
	 * 查询订单编号
	 * @param json
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gedFindContractByContractNo")
	public String gedFindContractByContractNo(@RequestBody String json){
		logger.info("查询订单编号--参数:"+json);
		String response = null;
		GedResponse rs = new GedResponse();
		try {
			Map<String,String> params = JsonTransferUtils.json2Bean(json,Map.class);
			String contractNo = params.get("contractNo");
			if(StringUtils.isNotEmpty(contractNo)){
				Contract contract = accContractDao.findContractByContractNo(contractNo);
				String msg = JsonTransferUtils.bean2Json(contract);
				rs.setMsg(msg);
				rs.setCode("0000");
			}else{
				logger.error("信息错误：合同号不能为空");
				rs.setCode("9999");
				rs.setMsg("信息错误：合同号不能为空");
			}
			response = JsonTransferUtils.bean2Json(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;
	}
	/*提前结清详情
	* */
	@RequestMapping(method = RequestMethod.POST, value = "advanceRepayDetail")
	public String advanceRepayDetail(@RequestBody String json) throws IOException{
		logger.info("冠易贷提前还款查询--参数:"+json);
		String response = "";
		HashMap<String, Object> receiveMap;
		String contractNo = "";
		HashMap<String ,Object> map=new HashMap();
		HashMap<String,String> data = new HashMap();
		AccRepayPlan accRepayPlan = null;
		try {
			receiveMap = JsonTransferUtils.json2Bean(json, HashMap.class);
			contractNo = (String)receiveMap.get("contractNo");
			//获取当前期数
			accRepayPlan =  accRepayPlanService.getCurrentRepayPlan(new Date(),contractNo);
			if(accRepayPlan==null){
				map.put("code","1111");
				map.put("msg","该合同不可提前还款");
				data.put("contractNo",contractNo);//合同号
				data.put("stayMoney",null);//待还总金额
				data.put("advanceOverduePenalty",null);//提前结清违约金
				data.put("periodValue",null);//待还总期数
				data.put("periodValueAll",null);//合同总期数
				data.put("contractAmount",null);//合同金额
				data.put("conEndDate",null);//借款到期日
				data.put("fineAmountAll",null);////逾期总罚息
				data.put("overduePenaltyAll",null);////逾期总违约金
				data.put("loanDate",null);//放款日期前一天
				map.put("data",data);
				response = JsonTransferUtils.bean2Json(map);
				interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷提前还款查询接口" ,response , new Date(), json));
				return response;
			}
		}catch (Exception e1) {
			e1.printStackTrace();
			map.put("code","1111");
			map.put("msg","系统异常");
			data.put("contractNo",contractNo);//合同号
			data.put("stayMoney",null);//待还总金额
			data.put("advanceOverduePenalty",null);//提前结清违约金
			data.put("periodValue",null);//待还总期数
			data.put("periodValueAll",null);//合同总期数
			data.put("contractAmount",null);//合同金额
			data.put("conEndDate",null);//借款到期日
			data.put("fineAmountAll",null);////逾期总罚息
			data.put("overduePenaltyAll",null);////逾期总违约金
			data.put("loanDate",null);//放款日期前一天
			map.put("data",data);
			response = JsonTransferUtils.bean2Json(map);
			interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷提前还款查询接口" ,response , new Date(), json));
			return response;
		}
		try {
			String currentPeriodNum = accRepayPlan.getPeriodNum();
			List<GedAdvanceRepayDetail> gedAdvanceRepayDetail = staContractStatusService.getAdvanceDetailGED(contractNo);
			BigDecimal  stayMoney= new BigDecimal("0.00");//总待还
			BigDecimal  fineAmountAll= new BigDecimal("0.00");//逾期总罚息
			BigDecimal  overduePenaltyAll= new BigDecimal("0.00");//逾期总违约金
			for (int i=0;i<gedAdvanceRepayDetail.size();i++){
				GedAdvanceRepayDetail gedAdvanceRepayDetail1 = gedAdvanceRepayDetail.get(i);
				if("0300".equals(gedAdvanceRepayDetail1.getRepayPeriodStatus())){
					//逾期
					BigDecimal allFactMoney = gedAdvanceRepayDetail1.getAllFactMoney();//实
					BigDecimal allRepayMoney = gedAdvanceRepayDetail1.getAllRepayMoney();//应
					BigDecimal discountFee = gedAdvanceRepayDetail1.getDiscountFee();//贴息
					BigDecimal stayMoneyTemp = allRepayMoney.subtract(allFactMoney).subtract(discountFee);
					if(!(stayMoneyTemp.compareTo(BigDecimal.ZERO)==-1)){
						stayMoney = stayMoney.add(stayMoneyTemp);
					}else{
						stayMoney = stayMoney.add(allRepayMoney.subtract(allFactMoney));
					}
					fineAmountAll = fineAmountAll.add(gedAdvanceRepayDetail1.getFineAmount());
					overduePenaltyAll = overduePenaltyAll.add(gedAdvanceRepayDetail1.getOverduePenalty());
				}else if("0200".equals(gedAdvanceRepayDetail1.getRepayPeriodStatus())){
					//待还款    不包含违约金
					BigDecimal allFactMoney = gedAdvanceRepayDetail1.getAllFactMoney();//所有实+所有减免
					BigDecimal allRepayMoney = gedAdvanceRepayDetail1.getAllRepayMoney().subtract(gedAdvanceRepayDetail1.getOverduePenalty());// 应还 本期没有违约金，所有6项应还要减去违约金
					BigDecimal discountFee = gedAdvanceRepayDetail1.getDiscountFee();//贴息
					BigDecimal stayMoneyTemp = allRepayMoney.subtract(allFactMoney).subtract(discountFee);
					if(!(stayMoneyTemp.compareTo(BigDecimal.ZERO)==-1)){
						stayMoney = stayMoney.add(stayMoneyTemp);
					}else{
						stayMoney = stayMoney.add(allRepayMoney.subtract(allFactMoney));
					}
				}else{//currentPeriodNum   为空
					String periodNum = gedAdvanceRepayDetail1.getPeriodNum();
					if(Integer.parseInt(periodNum)>Integer.parseInt(currentPeriodNum)){//这一期大于当前的期数-提前还款
						stayMoney = stayMoney.add(new BigDecimal(gedAdvanceRepayDetail1.getCapitalAmount()));

					}else if(Integer.parseInt(periodNum)==Integer.parseInt(currentPeriodNum)){//本期   没有违约金
						BigDecimal allFactMoney = gedAdvanceRepayDetail1.getAllFactMoney();//所有实+所有减免
						BigDecimal allRepayMoney = gedAdvanceRepayDetail1.getAllRepayMoney().subtract(gedAdvanceRepayDetail1.getOverduePenalty());// 应还 本期没有违约金，所有6项应还要减去违约金
						BigDecimal discountFee = gedAdvanceRepayDetail1.getDiscountFee();//贴息
						BigDecimal stayMoneyTemp = allRepayMoney.subtract(allFactMoney).subtract(discountFee);
						if(!(stayMoneyTemp.compareTo(BigDecimal.ZERO)==-1)){
							stayMoney = stayMoney.add(stayMoneyTemp);
						}else{
							stayMoney = stayMoney.add(allRepayMoney.subtract(allFactMoney));
						}
					}else{//逾期
						BigDecimal allFactMoney = gedAdvanceRepayDetail1.getAllFactMoney();//实
						BigDecimal allRepayMoney = gedAdvanceRepayDetail1.getAllRepayMoney();//应
						BigDecimal discountFee = gedAdvanceRepayDetail1.getDiscountFee();//贴息
						BigDecimal stayMoneyTemp = allRepayMoney.subtract(allFactMoney).subtract(discountFee);
						if(!(stayMoneyTemp.compareTo(BigDecimal.ZERO)==-1)){
							stayMoney = stayMoney.add(stayMoneyTemp);
						}else{
							stayMoney = stayMoney.add(allRepayMoney.subtract(allFactMoney));
						}
						fineAmountAll = fineAmountAll.add(gedAdvanceRepayDetail1.getFineAmount());
						overduePenaltyAll = overduePenaltyAll.add(gedAdvanceRepayDetail1.getOverduePenalty());
					}
				}
			}
			double advanceOverduePenalty = gedAdvanceRepayDetail.get(0).getContractAmount().multiply(new BigDecimal("0.03")).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
			stayMoney = stayMoney.add(BigDecimal.valueOf(advanceOverduePenalty));
			map.put("code","0000");
			map.put("msg","查询成功");
			data.put("contractNo",contractNo);//合同号
			data.put("stayMoney",stayMoney.toString());//待还总金额
			data.put("advanceOverduePenalty",advanceOverduePenalty+"");//提前结清违约金
			data.put("periodValue",gedAdvanceRepayDetail.size()+"");//待还总期数
			data.put("periodValueAll",gedAdvanceRepayDetail.get(0).getPeriodValue());//合同总期数
			data.put("contractAmount",gedAdvanceRepayDetail.get(0).getContractAmount().toString());//合同金额
			data.put("conEndDate",gedAdvanceRepayDetail.get(0).getConEndDate());//借款到期日
			data.put("fineAmountAll",fineAmountAll.toString());////逾期总罚息
			data.put("overduePenaltyAll",overduePenaltyAll.toString());////逾期总违约金
			String loanDate = contractService.subDay(gedAdvanceRepayDetail.get(0).getLoanDate());//放款日期前一天
			data.put("loanDate",loanDate);//放款日期前一天
			map.put("data",data);
			response = JsonTransferUtils.bean2Json(map);
			interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷提前还款查询接口" ,response , new Date(), json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷提前还款查询接口" ,e.getMessage()+response , new Date(), json));
		}
		return response;
	}
	/**
	 * 冠易贷申请进件
	 * @param json
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gedRepaymentPlan")
	public String gedRepaymentPlan(@RequestBody String json){
		logger.info("接收接收冠易贷查询 还款计划--参数:"+json);
		String response = null;
		GedResponse rs = new GedResponse();
		try {
			Map<String,String> params = JsonTransferUtils.json2Bean(json,Map.class);
			if(params.get("orderNo")!=null ){
				//非联合授信
				if("0".equals(params.get("jointCredit").toString())){
					List<Contract> contracts = null;
					contracts = accContractDao.findContractByApplyNo(params.get("orderNo").toString());
					if(contracts==null||contracts.size()==0) {//子订单号查询
						String approveId = params.get("orderNo").toString();
						Map<String,String> map = new HashMap<>();
						map.put("approveId", approveId);
						com.resoft.credit.contract.entity.Contract contractByApproveId = contractDao.getContractByApproveId(map);
						String contractNo = contractByApproveId.getContractNo();
							contracts = accContractDao.findContractBySonApplyNo(contractNo);
					}
					if(contracts.size()>0){
						Date startDate = null;
						Date endDate = null;
						String dateFlag = params.get("dateFlag");
						if(StringUtils.isNotEmpty(dateFlag)){
							if("1".equals(dateFlag)){
								endDate = new Date();
							}else{
								startDate = new Date();
							}
						}
						List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResultByParams(contracts.get(0).getContractNo(), startDate, endDate,null);
						List<DeductResultTemp> resultList = new ArrayList<DeductResultTemp>();
						String repayPeriodStatus = params.get("repayPeriodStatus");
						for(DeductResultTemp temp : deductResultTemps){
							BigDecimal repayAmount = null;			//应还金额
							BigDecimal penaltyAmount = null;		//违约金
							BigDecimal fineAmount = null;			//罚息
							BigDecimal penaltyExemptAmount = null;	// 违约金减免
							BigDecimal fineExemptAmount = null;		// 罚息减免
							Integer overdueDays = null;
							BigDecimal stayMoney = null;//待还金额
							//factServiceFee   factManagementFee  factOverdueCapialAmount  
							//factInterestAmount  factFineAmount  factPenaltyAmount
							BigDecimal factRepayAmount= null;//实还金额
							factRepayAmount=new BigDecimal(temp.getFactServiceFee())
									.add(new BigDecimal(temp.getFactManagementFee()))
									.add(new BigDecimal(temp.getFactOverdueCapialAmount()))
									.add(new BigDecimal(temp.getFactInterestAmount()))
									.add(new BigDecimal(temp.getFactFineAmount()))
									.add(new BigDecimal(temp.getFactPenaltyAmount()));
							if(StringUtils.isNotEmpty(temp.getOverdueDays())){
								overdueDays = Integer.parseInt(temp.getOverdueDays());
							}else{
								overdueDays = 0;
							}
							if(overdueDays > 0){
								if("0200".equals(temp.getRepayPeriodStatus())){
									temp.setRepayPeriodStatus("0300");
								}
								if(StringUtils.isNotEmpty(temp.getPenaltyAmount())){
									penaltyAmount = new BigDecimal(temp.getPenaltyAmount());
								}else{
									penaltyAmount = new BigDecimal(0);
								}
								if(StringUtils.isNotEmpty(temp.getPenaltyExemptAmount())){
									penaltyExemptAmount = new BigDecimal(temp.getPenaltyExemptAmount());
								}else{
									penaltyExemptAmount = new BigDecimal(0);
								}
							}else{
								penaltyAmount = new BigDecimal(0);
								penaltyExemptAmount = new BigDecimal(0);
							}
							if(StringUtils.isNotEmpty(temp.getRepayAmount())){
								repayAmount = new BigDecimal(temp.getRepayAmount());
							}else{
								repayAmount = new BigDecimal(0);
							}
							 if(StringUtils.isNotEmpty(temp.getStayMoney())){
								stayMoney = new BigDecimal(temp.getStayMoney());
							}else{
								stayMoney = new BigDecimal(0);
							}
							
							if(StringUtils.isNotEmpty(temp.getFineAmount())){
								fineAmount = new BigDecimal(temp.getFineAmount());
							}else{
								fineAmount = new BigDecimal(0);
							}
							
							if(StringUtils.isNotEmpty(temp.getFineExemptAmount())){
								fineExemptAmount =  new BigDecimal(temp.getFineExemptAmount());
							}else{
								fineExemptAmount =  new BigDecimal(0);
							}
							BigDecimal accStayMoney = stayMoney;
							repayAmount = repayAmount.add(penaltyAmount).add(fineAmount).subtract(penaltyExemptAmount).subtract(fineExemptAmount);
							stayMoney =repayAmount.subtract(stayMoney);
							stayMoney =stayMoney.subtract(factRepayAmount);//--
							if (stayMoney.compareTo(new BigDecimal("0.00")) <= 0) {
								stayMoney = new BigDecimal("0.00");
								if ("0200".equals(temp.getRepayPeriodStatus())) {
									temp.setRepayPeriodStatus("0400");
								}
								if ("0300".equals(temp.getRepayPeriodStatus())) {
									temp.setRepayPeriodStatus("0100");
								}
							}
							if("0500".equals(temp.getRepayPeriodStatus())){
								stayMoney = new BigDecimal("0.00");
							}
							temp.setRepayAmount(repayAmount.toString());
							temp.setStayMoney(stayMoney.toString()); 
							if(StringUtils.isNoneEmpty(repayPeriodStatus)){
								if(repayPeriodStatus.contains(temp.getRepayPeriodStatus())){
									resultList.add(temp);
								}
							}else{
								resultList.add(temp);
							}
						}
						
						String msg = JsonTransferUtils.bean2Json(resultList);
						String loanRepayTypeName = DictUtils.getDictLabel(contracts.get(0).getApproLoanRepayType(), "LOAN_REPAY_TYPE", "");
						rs.getMapValue().put("loanRepayTypeName", loanRepayTypeName);//还款方式
						rs.setMsg(msg);
						rs.setCode("0000");
					}else{
						rs.setMsg("订单号不存在");
						rs.setCode("0000");
					}
				}
			}else{
				logger.error("信息错误：订单号不能为空");
				rs.setCode("9999");
				rs.setMsg("信息错误：订单号不能为空");
			}
			response = JsonTransferUtils.bean2Json(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;
	}
	/*合同面签审批流的还款计划
	 * */
	@RequestMapping(method = RequestMethod.POST, value = "gedActivitiRepayPlan")
	public String gedActivitiRepayPlan(@RequestBody String json) throws IOException{
		logger.info("冠易贷查询审批流还款计划开始--参数:"+json);
		String response = "";
		HashMap<String, Object> receiveMap;
		String contractNo = "";
		GedRepayPlanListResponse gedRepayPlanListResponse = new GedRepayPlanListResponse();
		try {
			receiveMap = JsonTransferUtils.json2Bean(json, HashMap.class);
			contractNo = (String)receiveMap.get("contractNo");
		}catch (Exception e1) {
			e1.printStackTrace();
			if(StringUtils.isEmpty(contractNo)) {
				gedRepayPlanListResponse.setCode("0001");
				gedRepayPlanListResponse.setMsg("参数错误！");
				response = JsonTransferUtils.bean2Json(gedRepayPlanListResponse);
				interfaceInfoService.save(new InterfaceInfo("error", "冠易贷查询审批流还款计划接口" ,response , new Date(), json));
				return response;
			}
		}
		try {
			
			com.resoft.credit.contract.entity.Contract contractByContractNo = creContractService.getContractByContractNo(contractNo);
			List<GedRepayPlanResponse>  repayPlanList=repayPlanService.getByContractNo(contractByContractNo.getApplyNo(),"utask_htmq");
			if (repayPlanList == null || repayPlanList.size() == 0) {
				repayPlanList=repayPlanService.getByContractNo(contractByContractNo.getApplyNo(),"under_dqglr");
			}
			if(repayPlanList==null||repayPlanList.size()==0) {
				Map<String, String> param = Maps.newConcurrentMap();
				param.put("applyNo", contractByContractNo.getApplyNo());
				param.put("taskDefKey","utask_htmq");
				param.put("approId", contractByContractNo.getApproId());
				repayPlanList=repayPlanUnionService.getByApplyTaskAppro(param);
			}
			if(repayPlanList==null||repayPlanList.size()==0) {
				Map<String, String> param = Maps.newConcurrentMap();
				param.put("applyNo", contractByContractNo.getApplyNo());
				param.put("taskDefKey","under_dqglr");
				param.put("approId", contractByContractNo.getApproId());
				repayPlanList=repayPlanUnionService.getByApplyTaskAppro(param);
			}
			if(repayPlanList==null||repayPlanList.size()==0) {
				gedRepayPlanListResponse.setList(repayPlanList);
				gedRepayPlanListResponse.setCode("0002");
				gedRepayPlanListResponse.setMsg("未找到合同号！");
				response = JsonTransferUtils.bean2Json(gedRepayPlanListResponse);
				interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷查询审批流还款计划接口" ,response , new Date(), json));
			}else {
				gedRepayPlanListResponse.setList(repayPlanList);
				gedRepayPlanListResponse.setCode("0000");
				gedRepayPlanListResponse.setMsg("查询成功");
				response = JsonTransferUtils.bean2Json(gedRepayPlanListResponse);
				interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷查询审批流还款计划接口" ,response , new Date(), json));
			}
		} catch (Exception e) {
			e.printStackTrace();
			gedRepayPlanListResponse.setCode("2222");
			gedRepayPlanListResponse.setMsg("系统未知错误！");
			response = JsonTransferUtils.bean2Json(gedRepayPlanListResponse);
			interfaceInfoService.save(new InterfaceInfo(contractNo, "冠易贷查询审批流还款计划接口" ,response , new Date(), json));
		}
		return response;
	}
	
	
	
	
	/**
	 * 冠易贷申请进件
	 * @param json
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gedApplyRegister")
	public String gedApplyRegister(@RequestBody String json){
		logger.info("接收冠易贷推送客户信息开始--参数:"+json);
		GedResponse rs = new GedResponse();
		GedCustInfo custInfo = null;
		String response = null;
		try {
			custInfo = (GedCustInfo)JsonTransferUtils.json2Bean(json, GedCustInfo.class);
			try {
				gedService.insert(custInfo);
				rs.setCode("0000");
				rs.setMsg("成功");
				rs.setApplyId(custInfo.getApplyId());
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				rs.setCode("9999");
				rs.setMsg("信息存储失败:"+e.toString());
				rs.setApplyId(custInfo.getApplyId());
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			rs.setCode("9999");
			rs.setMsg("传输数据格式错误:"+e.toString());
		}
		try {
			response = JsonTransferUtils.bean2Json(rs);
		} catch (IOException e) {
			logger.error("接收冠易贷推送客户信息返回值转化JSON失败", e);
		}
		logger.info("冠易贷返回信息:"+response+"接收冠易贷推送客户信息结束");
		return response;
	}
	
	/**
	 * 冠易贷状态反馈接口(一次提现、二次提现使用)
	 * @param json
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gedUpdateOrderStatus")
	public String gedUpdateOrderStatus(@RequestBody String json){
		logger.info("冠易贷订单状态反馈接口开始--参数:"+json);
		GedResponse rs = new GedResponse();
		GedDrawInfo gedDrawInfo = null;
		String response = null;
		try {
			gedDrawInfo = (GedDrawInfo)JsonTransferUtils.json2Bean(json, GedDrawInfo.class);
			try {
				if(gedDrawInfo!=null&&!"".equals(gedDrawInfo.getOrderNo().trim())&&!"".equals(gedDrawInfo.getStatus().trim())){
					Boolean b = creGedBorrowStatusService.updateDrawStatus(gedDrawInfo.getOrderNo(),gedDrawInfo.getStatus());
					if(b){
						rs.setCode("0000");
						rs.setMsg("成功");
						interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","接收成功",new Date(),json));
					}else{
						rs.setCode("9999");
						rs.setMsg("订单号不存在!");
						interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","不存在该订单号!",new Date(),json));
					}
				}else{
					rs.setCode("9999");
					rs.setMsg("数据不能为空!");
					interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","参数为空",new Date(),json));
				}
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				rs.setCode("9999");
				rs.setMsg("信息存储失败:"+e.toString());
				interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","存储异常",new Date(),json));
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			rs.setCode("9999");
			rs.setMsg("传输数据格式错误:"+e.toString());
			interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","数据格式错误",new Date(),json));
		}
		try {
			response = JsonTransferUtils.bean2Json(rs);
		} catch (IOException e) {
			logger.error("冠易贷订单状态反馈接口返回值转化JSON失败", e);
		}
		logger.info("冠易贷返回信息:"+response+"冠易贷服务结束---------end");
		return response;
	}
	
//	/**
//	 * 冠易贷状态反馈接口(一次提现、二次提现使用)
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.POST, value = "gedOrderStatus")
//	public String gedOrderStatus(@RequestBody String json){
//		logger.info("冠易贷订单状态反馈接口开始--参数:"+json);
//		GedResponse rs = new GedResponse();
//		GedDrawInfo gedDrawInfo = null;
//		String response = null;
//		try {
//			gedDrawInfo = (GedDrawInfo)JsonTransferUtils.json2Bean(json, GedDrawInfo.class);
//			try {
//				if(gedDrawInfo!=null&&!"".equals(gedDrawInfo.getOrderNo().trim())&&!"".equals(gedDrawInfo.getStatus().trim())){
//					Boolean b = applyLoanStatusService.updateDrawStatus(gedDrawInfo.getOrderNo(),gedDrawInfo.getStatus());
//					if(b){
//						rs.setCode("0000");
//						rs.setMsg("成功");
//						interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","接收成功",new Date(),json));
//					}else{
//						rs.setCode("9999");
//						rs.setMsg("订单号不存在!");
//						interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","不存在该订单号!",new Date(),json));
//					}
//				}else{
//					rs.setCode("9999");
//					rs.setMsg("数据不能为空!");
//					interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","参数为空",new Date(),json));
//				}
//			} catch (Exception e) {
//				logger.error("信息存储失败："+e.toString());
//				rs.setCode("9999");
//				rs.setMsg("信息存储失败:"+e.toString());
//				interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","存储异常",new Date(),json));
//			}
//		} catch (Exception e) {
//			logger.error("传输数据格式错误:"+e.toString());
//			rs.setCode("9999");
//			rs.setMsg("传输数据格式错误:"+e.toString());
//			interfaceInfoService.save(new InterfaceInfo(gedDrawInfo.getOrderNo(),"冠易贷状态反馈接口","数据格式错误",new Date(),json));
//		}
//		try {
//			response = JsonTransferUtils.bean2Json(rs);
//		} catch (IOException e) {
//			logger.error("冠易贷订单状态反馈接口返回值转化JSON失败", e);
//		}
//		logger.info("冠易贷返回信息:"+response+"冠易贷服务结束---------end");
//		return response;
//	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "gedAccount")
	public String gedAccountMessage(@RequestBody String json){
		logger.info("冠易贷个人账户信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		AccessGedAccount rs = new AccessGedAccount();
		GedAccount gedAccount =new GedAccount();
		Date acceptDate = new Date();
		Map<String,Object> response = new HashMap<String,Object>();
		String	responses = "";
		String idNum = "";
		try {
			rs = (AccessGedAccount)JsonTransferUtils.json2Bean(json, AccessGedAccount.class);
			//List<ApplyRegister> applyRegisters = applyRegisterService.findApplyListsByIdNum(rs.getLegalPerNum());
			idNum = rs.getLegalPerNum();
			try {
				//if(applyRegisters.size() >0){
					Boolean boolean1 = GedAccountService.isAllFieldNull(rs);
					if(boolean1){
						gedAccount = (GedAccount)JsonTransferUtils.json2Bean(json,GedAccount.class);
						gedAccount.setCreateTime(acceptDate);
						String companyBankOfDeposit = gedAccount.getCompanyBankOfDeposit();
						String substring = companyBankOfDeposit.substring(1);
						gedAccount.setCompanyBankOfDeposit(substring);
						GedAccount account = gedAccountService.queryByBankNo(gedAccount.getCompanyAccount());
						if (account == null) {

						}else if (account != null) {
							gedAccountService.delete(account);
						}
						gedAccountService.save(gedAccount);
					}else {
						response.put("code",4);
						response.put("exception","参数值不能为空");
					}
				/*}else{
					response.put("code",1);
					response.put("exception","对应进件信息不存在");
				}*/
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				response.put("code",1);
				response.put("exception","信息存储失败");
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			response.put("code",2);
			response.put("exception","失败,传输数据格式错误!");
		}
		if(response.isEmpty()){
			response.put("code",0);
			response.put("data","成功");
		}
		try {
		 responses = JsonTransferUtils.bean2Json(response);
		} catch (IOException e) {
			logger.error("冠易贷账户信息接口返回值转化JSON失败", e);
			response.put("code",3);
			response.put("exception","接口返回转化失败");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(idNum, "冠E贷个人推送账户信息接口" ,responses , acceptDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷个人账户信息返回信息:"+responses);
		logger.info("冠易贷个人账户信息结束-----------------------end");
		return responses;
	}
	
	
	
	

	@RequestMapping(method = RequestMethod.POST, value = "serviceFee")
	public String gedServiceFee(@RequestBody String json){
		logger.info("冠易贷推送服务费已收信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		GedServiceFee gedServiceFee = null;
		Map<String,Object> response = new HashMap<String,Object>();
		String responses = null;
		try {
			Map<String,String> param = Maps.newHashMap();
			
			gedServiceFee = (GedServiceFee)JsonTransferUtils.json2Bean(json, GedServiceFee.class);
			try {
					Map<String,String> gedParam = Maps.newHashMap();
					gedParam.put("flag",gedServiceFee.getFlag());
					gedParam.put("contractNo", gedServiceFee.getContractNo());
					applyLoanStatusService.updateServiceFeeByApplyNo(gedParam);			
				
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				response.put("code",1);
				response.put("exception","信息存储失败");
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			response.put("code",1);
			response.put("exception","失败,传输数据格式错误!");
		}
		if(response.isEmpty()){
			response.put("code",0);
			response.put("data","成功");
		}
		try {
		 responses = JsonTransferUtils.bean2Json(response);
		} catch (IOException e) {
			logger.error("冠易贷账户信息接口返回值转化JSON失败", e);
			response.put("code",1);
			response.put("exception","接口返回转化失败");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(gedServiceFee.getApplyNo(), "冠E贷推送服务收取信息接口" ,responses , acceptDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷推送服务费信息返回信息:"+responses);
		logger.info("冠易贷推送服务费信息结束-----------------------end");
		return responses;
	}
	
	/**
	 * 冠E贷一次提现成功
	 * @param json
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="firstWithDeposit")
	public String FirstWithdrawDeposit(@RequestBody String json){
		logger.info("冠易贷一次提现成功接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		GedServiceFee gedServiceFee = null;
		Map<String,Object> response = new HashMap<String,Object>();
		String responses = null;
		try {
			Map<String,String> param = Maps.newHashMap();
			gedServiceFee = (GedServiceFee)JsonTransferUtils.json2Bean(json, GedServiceFee.class);
			try {
				param.put("applyNo", gedServiceFee.getApplyNo());
				param.put("contractNo",gedServiceFee.getContractNo());
				//线下录入改变状态 ，首次提现
				underCompanyInfoService.updateStatus(gedServiceFee.getLoanStatus(),gedServiceFee.getApplyNo());
				List<ApplyLoanStatus> applyLoanStatusList = applyLoanStatusService.finApplyLoanStatus(param);
				if(applyLoanStatusList.size() >0){
					param.put("loanStatus", gedServiceFee.getLoanStatus());
					applyLoanStatusService.updateLoanStatusByApplyNoAndContractNo(param);
					creWithdrawStreamService.saveStream(gedServiceFee);
				}else{
					response.put("code",1);
					response.put("data","对应申请编号信息不存在");
				}
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				response.put("code",1);
				response.put("exception","信息存储失败");
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			response.put("code",1);
			response.put("exception","失败,传输数据格式错误!");
		}
		if(response.isEmpty()){
			response.put("code",0);
			response.put("data","成功");
		}
		try {
		 responses = JsonTransferUtils.bean2Json(response);
		} catch (IOException e) {
			logger.error("冠易贷账户信息接口返回值转化JSON失败", e);
			response.put("code",1);
			response.put("exception","接口返回转化失败");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(gedServiceFee.getApplyNo(), "冠E贷推送首次提现接口" ,responses , acceptDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷推送服务费信息返回信息:"+responses);
		logger.info("冠易贷推送服务费信息结束-----------------------end");
		return responses;
	}
	
	
	/**
	 * 冠E贷推送流水入账
	 * @param json
	 * @return
	 */
	@RequestMapping(value="accAccount",method=RequestMethod.POST)
	public String gedAccAccount(@RequestBody String json){
		logger.info("冠易贷推送入账流水开始--参数:"+json); 
		logger.info("开始处理时间："+System.currentTimeMillis());
		GedRepayment gedRepayment = null;
		String str = "";
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String deductTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		Map<String,String> conparam = Maps.newHashMap();
		String gedJson = "";
		Map<String,String> responseGED = Maps.newHashMap();
		try {	
			gedRepayment = (GedRepayment)JsonTransferUtils.json2Bean(json,GedRepayment.class);
			conparam.put("contractNo", gedRepayment.getContractNo());
			List<StaRepayPlanStatus>  staRepayPlanStatusList = staRepayPlanStatusService.findUpdateRepayPeroidStatus(conparam);
			RepaymentUtils.updatePeriodNum(gedRepayment, staRepayPlanStatusList);
			gedRepayment.setSeqNo(AccFinancialPlatformUtils.makeSeqNo());
			gedRepayment.setDataDt(date);
			gedRepayment.setDeductTime(deductTime);
			String repayMent = JsonTransferUtils.bean2Json(gedRepayment);
			acc4FinancialPlatformServer.insertGedAccAccount(gedRepayment);
			producerService.sendMessageRepayment(repayMent);
		} catch (IOException e) {
			logger.error("接口传入参数格式不正确", e);
			str = "接口传入参数格式不正确";
		} catch (Exception e) {
			logger.error("冠E贷推送充值流水数据入库失败", e);
			str = "冠E贷推送充值流水数据入库失败";
		}
		try {
			responseGED.put("code","0000");
			gedJson = JsonTransferUtils.bean2Json(responseGED);
		} catch (Exception e) {
			logger.error("冠E贷返回转换异常",e);
		}
		logger.info("冠E贷充值入账:"+str+"冠易贷服务结束---------end");
  		interfaceInfoService.save(new InterfaceInfo(gedRepayment.getContractNo(), "冠E贷充值还款信息接口" ,str,new Date(),json));
		return gedJson;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "companyAccount")
	public String gedCompanyAccountMessage(@RequestBody String json){
		logger.info("冠易贷企业账户信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		CreGedAccountCompany comAccount = new CreGedAccountCompany();
		Date acceptDate = new Date();
		Map<String,Object> response = new HashMap<String,Object>();
		String	responses = "";
		String socialCreditCode = "";
		try {
			comAccount = (CreGedAccountCompany)JsonTransferUtils.jsontoBean(json, CreGedAccountCompany.class);
			CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.selectCompanyAccountById(comAccount.getId());
			int returnTime=0;
			if(creGedAccountCompany!=null){
				returnTime=creGedAccountCompany.getReturnTime();
			}
			if(comAccount.getStatus()==8){
				returnTime=returnTime+1;
			}
			try {
				comAccount.setReturnTime(returnTime);
                String companyBankOfDeposit = comAccount.getCompanyBankOfDeposit();
                String substring = companyBankOfDeposit.substring(1);
                comAccount.setCompanyBankOfDeposit(substring);
				comAccount.setCreateTime(acceptDate);
				comAccount.setIsNewRecord(true);
				creGedAccountCompanyService.save(comAccount);
                 logger.info("主键Id===="+comAccount.getId());
				socialCreditCode = comAccount.getSocialCreditCode();
				//同时新增历史记录表
				accCompanyHistoryService.save(comAccount.getId(),comAccount.getStatus(),TagValue(String.valueOf(comAccount.getStatus())),comAccount.getAccountVerifyInfo());
			} catch (Exception e) {
				logger.error("信息存储失败："+e.toString());
				response.put("code",1);
				response.put("exception","信息存储失败");
			}
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			response.put("code",2);
			response.put("exception","失败,传输数据格式错误!");
		}
		if(response.isEmpty()){
			response.put("code",0);
			response.put("data","成功");
		}
		try {
		 responses = JsonTransferUtils.bean2Json(response);
		} catch (IOException e) {
			logger.error("冠易贷企业账户信息接口返回值转化JSON失败", e);
			response.put("code",3);
			response.put("exception","接口返回转化失败");
		}
		try {
			interfaceInfoService.save(new InterfaceInfo(socialCreditCode, "冠E贷推送企业账户信息接口" ,responses , acceptDate, json));
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷企业账户信息返回信息:"+responses);
		logger.info("冠易贷企业账户信息结束-----------------------end");
		return responses;
	}

	public String TagValue(String key) {
		if("6".equals(key)) {
			return "待审核";
		}else if("7".equals(key)){
			return "审核通过";
		}else if("8".equals(key)){
			return "审核退回";
		}else if("9".equals(key)){
			return "审核拒绝";
		}else  if("10".equals(key)){
			return "人工退回";
		}
		return "";
	}

	/*
	 * 代偿结果
	 * */
	
	@RequestMapping(method = RequestMethod.POST, value = "compensatoryResult")
	public String compensatoryResult(@RequestBody String json){
		logger.info("冠易贷查询代偿发送信息："+json);
		String amount=null;
		SendGETRequest comAccount = null;
		try {
			comAccount = (SendGETRequest)JsonTransferUtils.json2Bean(json, SendGETRequest.class);
			amount = compensatoryDetailService.getAmount(comAccount);
			interfaceInfoService.save(new InterfaceInfo(comAccount.getContractNo() , "冠E贷查询代偿金额接口" ,amount , new Date(), json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(comAccount.getContractNo() , "冠E贷查询代偿金额接口" ,amount , new Date(), json));
		}
		return "{amount:"+amount+"}";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "borrowConditon")
	public String contractNoDetail(@RequestBody String json){
		logger.info("冠易贷查询还款信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		List<String> contractNos = new ArrayList<>();
		ContractNoListRequest contractNoListRequest = new ContractNoListRequest();
		BorrowLoanResponse borrowLoanResponse = new BorrowLoanResponse();
		RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
		String str = "";
		String response= "";
		try {
			contractNoListRequest = (ContractNoListRequest)JsonTransferUtils.json2Bean(json,ContractNoListRequest.class);
			contractNos = contractNoListRequest.getContractNo();
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			str = "传输格式错误";
		}
		if(StringUtils.isEmpty(str)){
			try {
				BigDecimal contractsMoney = contractService.queryContractMoney(contractNos);
				borrowLoanResponse.setLoanTotalMoney(contractsMoney.toString());
				repayPlanInfo = contractService.findContractSituation(contractNos);
				borrowLoanResponse.setContractStayMoney(repayPlanInfo.getStayTotalMoney());
				borrowLoanResponse.setCount(String.valueOf(contractNos.size()));
				borrowLoanResponse.setCode("0000");
				borrowLoanResponse.setMessage("成功");
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
				borrowLoanResponse.setCode("1111");
				borrowLoanResponse.setMessage("获取合同情况异常");
			}
			
		}else {
			borrowLoanResponse.setCode("1111");
			borrowLoanResponse.setMessage(str);
		}
		try {
			response = JsonTransferUtils.bean2Json(borrowLoanResponse);
			} catch (IOException e) {
				logger.error("冠易贷查询还款信息接口返回值转化JSON失败", e);
			}
		try {
			if (contractNos.size()>0) {
				for(String contractNo:contractNos){
					interfaceInfoService.save(new InterfaceInfo(contractNo, "冠E贷推送企业账户信息接口" ,response , acceptDate, json));
				}
			}	
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷查询还款信息返回信息:"+response);
		logger.info("冠易贷查询还款信息结束-----------------------end");
		return response;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "planDetail")
	public String planDetail(@RequestBody String json){
		logger.info("冠易贷查询还款详细信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		List<String> contractNos = new ArrayList<>();
		ContractNoListRequest request = new ContractNoListRequest();
		RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
		String str = "";
		String response= "";
		RepayPlanStayDetailResponse repayPlanStayDetailResponse = new RepayPlanStayDetailResponse();
		try {
			request = (ContractNoListRequest)JsonTransferUtils.json2Bean(json,ContractNoListRequest.class);
			contractNos = request.getContractNo();
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			str = "传输格式错误";
		}
		if(StringUtils.isEmpty(str)){
			try {
				repayPlanInfo = contractService.findContractSituation(contractNos);
				repayPlanStayDetailResponse.setRepayPlanInfo(repayPlanInfo);
				repayPlanStayDetailResponse.setCode("0000");
				repayPlanStayDetailResponse.setMessage("成功");
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
				repayPlanStayDetailResponse.setCode("1111");
				repayPlanStayDetailResponse.setMessage("获取合同情况异常");
			}
			
		}else {
			repayPlanStayDetailResponse.setCode("1111");
			repayPlanStayDetailResponse.setMessage(str);
		}
		try {
			response = JsonTransferUtils.bean2Json(repayPlanStayDetailResponse);
			} catch (IOException e) {
				logger.error("冠易贷查询还款详细信息接口返回值转化JSON失败", e);
			}
		try {
			if (contractNos.size()>0) {
				for(String contractNo:contractNos){
					interfaceInfoService.save(new InterfaceInfo(contractNo, "冠E贷推送还款合同号信息接口" ,response , acceptDate, json));
				}
			}	
		} catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷查询还款详细信息返回信息:"+response);
		logger.info("冠易贷查询还款详细信息结束-----------------------end");
		return response;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "confirmRelation")
	public String updateContractStatue(@RequestBody String json){
		logger.info("冠易贷确认担保关系信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		List<String> contractNos = new ArrayList<>();
		ConfirmGuranteeRelationRequest confirmRequestRelation = new ConfirmGuranteeRelationRequest();
		String str = "";
		String response= "";
		Map<String,String> responseParam = new HashMap<>();
		RepayPlanStayDetailResponse repayPlanStayDetailResponse = new RepayPlanStayDetailResponse();
		try {
			confirmRequestRelation = (ConfirmGuranteeRelationRequest)JsonTransferUtils.json2Bean(json,ConfirmGuranteeRelationRequest.class);
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			str = "传输格式错误";
		}
		if(StringUtils.isEmpty(str)){
			gedService.confirmGuranteeRelation(confirmRequestRelation);
			responseParam.put("code","0000");
		}else {
			responseParam.put("code","1111");
		}
		try {
			response = JsonTransferUtils.bean2Json(responseParam);
			} catch (IOException e) {
				logger.error("冠易贷确认担保关系信息接口返回值转化JSON失败", e);
			}
		try {
			interfaceInfoService.save(new InterfaceInfo(confirmRequestRelation.getSocaliTotalNum(), "冠E贷确认担保信息接口" ,response , acceptDate, json));
			}	
		catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷确认担保关系信息返回信息:"+response);
		logger.info("冠易贷查询还款详细信息结束-----------------------end");
		return response;
	} 
	
	
	@RequestMapping(method = RequestMethod.POST, value = "gedCreditInfo")
	public String gedCreditInfo(@RequestBody String json){
		logger.info("冠易查询信审信息接口开始-----------------------start");
		logger.info("冠易贷发送信息："+json);
		Date acceptDate = new Date();
		CreditInfoToRequest creditInfoToRequest = new CreditInfoToRequest();
		ConfirmGuranteeRelationRequest confirmRequestRelation = new ConfirmGuranteeRelationRequest();
		String str = "";
		String response= "";
		Map<String,String> responseParam = new HashMap<>();
		try {
			confirmRequestRelation = (ConfirmGuranteeRelationRequest)JsonTransferUtils.json2Bean(json,ConfirmGuranteeRelationRequest.class);
		} catch (Exception e) {
			logger.error("传输数据格式错误:"+e.toString());
			str = "传输格式错误";
		}
		if(StringUtils.isEmpty(str)){
			try {
				if (StringUtils.isNotBlank(confirmRequestRelation.getApplyNo()) && StringUtils.isNotBlank(confirmRequestRelation.getApplySubNo())) {
					creditInfoToRequest = creditInfoUnion(confirmRequestRelation.getApplyNo(),confirmRequestRelation.getApplySubNo());
				}else if (StringUtils.isNotBlank(confirmRequestRelation.getApplyNo()) && StringUtils.isEmpty(confirmRequestRelation.getApplySubNo())) {
					creditInfoToRequest = creditInfo(confirmRequestRelation.getApplyNo());
				}
				creditInfoToRequest.setCode("0000");
			} catch (Exception e) {
				logger.error("查询信审信息出错:"+e.toString());
				creditInfoToRequest.setCode("1111");
			}
		}else {
			creditInfoToRequest.setCode("1111");
		}
		try {
			response = JsonTransferUtils.bean2Json(creditInfoToRequest);
			} catch (IOException e) {
				logger.error("冠易贷信审信息接口返回值转化JSON失败", e);
			}
		try {
			interfaceInfoService.save(new InterfaceInfo(confirmRequestRelation.getSocaliTotalNum(), "冠E贷查询信审信息接口" ,response , acceptDate, json));
			}	
		catch (Exception e) {
			logger.error("接口信息记录失败！",e);
		}
		logger.info("冠易贷信审信息返回信息:"+response);
		logger.info("冠易贷查询信审信息结束-----------------------end");
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateContractSignFlag")
	public String updateContractSignFlag(@RequestBody String json) {
		logger.info("冠易贷更新合同签署信息接口开始------------------------start");
		logger.info("冠易贷发送信息 : " + json);
		ContractSignFlagRequest request = new ContractSignFlagRequest();
		boolean flag = true;
		ContractSignFlagResponse response = new ContractSignFlagResponse();
		try {
			request = JsonTransferUtils.json2Bean(json, ContractSignFlagRequest.class);
		} catch (Exception e) {
			logger.error("传输数据格式错误 " + e.toString());
			flag = false;
		}
		if (flag) {
			try {
				contractDao.updateContractSignFlag(request.getContractNo());
			} catch (Exception e) {
				logger.error("更新合同签署信息失败 " + e.toString());
				flag = false;
			}
		}
		if (flag) {
			response.setCode("0000");
		} else {
			response.setCode("1111");
		}
		String result = "";
		try {
			result = JsonTransferUtils.bean2Json(response);
		} catch (Exception e) {
			logger.error("冠易贷更新合同签署信息接口转换JSON失败", e);
		}
		logger.info("冠易贷更新合同签署信息接口响应信息 : " + result);
		logger.info("冠易贷更新合同亲属信息接口结束----------------------end");
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "advanceFullRepayment")
	public String advanceFullRepayment(String json) {
		logger.info("冠易贷提前结清--参数" + json);
		AdvanceFullRepayment fullRepayment = null;
		Map<String, String> gedResponse = new HashMap<>();
		String str = "";
		String gedJson = "";
		try {
			//入账
			fullRepayment = JsonTransferUtils.json2Bean(json, AdvanceFullRepayment.class);
			Map<String, String> param = new HashMap<>();
			param.put("contractNo", fullRepayment.getContractNo());
			List<StaRepayPlanStatus> staRepayPlanStatusList = staRepayPlanStatusService.findUpdateRepayPeroidStatus(param);
			RepaymentUtils.updatePeriodNum(fullRepayment, staRepayPlanStatusList);
			List<RepayPlan> repayList = repayPlanService.findList(
				RepaymentUtils.listRepayPlanByContractNo(fullRepayment.getContractNo()));
			Contract contract = contractService.findContractByContractNo(fullRepayment.getContractNo());
			BigDecimal repaymentMoney = RepaymentUtils.calcRepaymentMoney(contract, repayList, fullRepayment);
			if (repaymentMoney.compareTo(fullRepayment.getDeductAmount()) != 0) {
				throw new BusinessException("冠易贷 提前结清 金额异常 传入金额 ：" + fullRepayment + " 计算金额 ：" + repaymentMoney);
			}
			RepaymentUtils.packGedRepayment(fullRepayment);
			acc4FinancialPlatformServer.insertGedAccAccount(fullRepayment);
			String repayment = JsonTransferUtils.bean2Json(fullRepayment);
			producerService.advancePullRepayment(repayment);
		} catch (IOException e) {
			logger.error("接口传入参数格式不正确", e);
			str = "接口传入参数格式不正确";
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			str = e.getMessage();
		} catch (Exception e) {
			logger.error("冠E贷推送充值流水数据入库失败", e);
			str = "冠E贷推送充值流水数据入库失败";
		}
		try {
			gedResponse.put("code","0000");
			gedJson = JsonTransferUtils.bean2Json(gedResponse);
		} catch (Exception e) {
			logger.error("冠E贷返回转换异常",e);
		}
		logger.info("冠易贷 提前结清 ：" + str + "冠易贷服务结束 ---------- end");
		interfaceInfoService.save(new InterfaceInfo(fullRepayment.getContractNo(), "冠E贷充值还款信息接口" ,str,new Date(),json));
		return gedJson;
	}

	private CreditInfoToRequest creditInfoUnion(String applyNo,String approveId){
		CreditInfoToRequest creditInfoToRquest = new CreditInfoToRequest();
		GuanETInfo guanEDInfo = gedClientService.queryGuanTInfoByApplyNoAndApproveId(applyNo,approveId);
		List<GqgetAssetCarInfo> gqgetAssetCarInfos = gedClientService.queryGetCarInfosUnion(applyNo,approveId);
		List<GqgetAssetHouseInfo> gqgetAssetHouseInfos = gedClientService.queryGqHouseInfoUnion(applyNo,approveId);
		if (guanEDInfo != null && guanEDInfo.getOrderNo() != null && StringUtils.isNotBlank(guanEDInfo.getOrderNo())) {
			creditInfoToRquest.setOrderNo(guanEDInfo.getOrderNo());
		}
		if (guanEDInfo != null && guanEDInfo.getOrderSubNo() != null && StringUtils.isNotBlank(guanEDInfo.getOrderSubNo())) {
			creditInfoToRquest.setOrderSubNo(guanEDInfo.getOrderSubNo());
		}
		creditInfoToRquest.setGetAssetCardList(gqgetAssetCarInfos);
		creditInfoToRquest.setGqgetHouseList(gqgetAssetHouseInfos);
		creditInfoToRquest.setGuanETInfo(guanEDInfo);
		return creditInfoToRquest;
	}
	
	private CreditInfoToRequest creditInfo(String applyNo){
		CreditInfoToRequest creditInfoToRquest = new CreditInfoToRequest();
		GuanETInfo guanEDInfo = gedClientService.queryGuanTInfoByApplyNo(applyNo);
		List<GqgetAssetCarInfo> gqgetAssetCarInfos = gedClientService.queryGetCarInfos(applyNo);
		List<GqgetAssetHouseInfo> gqgetAssetHouseInfos = gedClientService.queryGqHouseInfo(applyNo);
		if (guanEDInfo != null && guanEDInfo.getOrderNo() != null && StringUtils.isNotEmpty(guanEDInfo.getOrderNo())) {
			creditInfoToRquest.setOrderNo(guanEDInfo.getOrderNo());
		}
		creditInfoToRquest.setGetAssetCardList(gqgetAssetCarInfos);
		creditInfoToRquest.setGqgetHouseList(gqgetAssetHouseInfos);
		creditInfoToRquest.setGuanETInfo(guanEDInfo);
		return creditInfoToRquest;
	}
}
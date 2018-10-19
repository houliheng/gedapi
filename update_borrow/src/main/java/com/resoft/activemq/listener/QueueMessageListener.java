package com.resoft.activemq.listener;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.ZjResoonseInfo;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.advanceGed.entity.AccAdvanceGed;
import com.resoft.accounting.advanceGed.service.AccAdvanceGedService;
import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.discount.entity.AccDiscount;
import com.resoft.accounting.discount.service.AccDiscountService;
import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.ged.entity.AdvanceFullRepayment;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.rest.sendGET.entry.SendGETRequest;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.utils.Facade;
import com.resoft.outinterface.utils.RSAUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * Created by Levi on 2017/8/9.
 */
public class QueueMessageListener implements MessageListener {

  static Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);


  @Value("${activityMQ.queue.repayment.procedure}")
  private String repaymentProcedure;
  @Autowired
  private AccContractDao accContractDao;
  @Autowired
  private Acc4FinancialPlatformServer acc4FinancialPlatformServer;
  @Autowired
  private AccRepayPlanService accRepayPlanService;
  @Autowired
  private CompensatoryDetailService compensatoryDetailService;
  @Autowired
  private StaRepayPlanStatusService staRepayPlanStatusService;
  @Autowired
  private InterfaceInfoService interfaceInfoService;
  @Autowired
  private ContractLockDao contractLockDao;
  @Autowired
  private CreGuaranteeCompanyService creGuranteeCompanyService;
  @Autowired
  private ContractService contractService;
  @Autowired
  private ApplyRegisterService applyRegisterService;
  private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");
  @Autowired
  private StaContractStatusService staContractService;
  @Autowired
  private AccAdvanceGedService advanceGedService;
  @Autowired
  private AccDiscountService accDiscountService;
  // 当收到消息后，自动调用该方法
  @Override
  public void onMessage(Message message) {

    try {
      ActiveMQDestination queues = (ActiveMQDestination) message.getJMSDestination();
      // 上报地理位置
      if (queues.getPhysicalName().equals(repaymentProcedure)) {
        if (message instanceof TextMessage) {
          TextMessage tm = (TextMessage) message;
			System.err.println(tm);
          GedRepayment  gedRepayment = (GedRepayment)JsonTransferUtils.json2Bean(tm.getText(),GedRepayment.class);
          String str = "";
          String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			BigDecimal oldFactServiceFee = accRepayPlanService.queryFactServiceFee(gedRepayment.getContractNo());//之前服务费
			logger.info("存储开始处理时间："+System.currentTimeMillis());
			//提前还款的各项待还费用

  			// 调用存储
  			Connection connection = null;
  			try {
				connection = dataSource.getConnection();
				CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT('" + date + "','" + gedRepayment.getSeqNo() + "')}");
				callableStatement.execute();

  			} catch (SQLException e) {
  				logger.error("存储调用失败！", e);
  				str = "入账失败";
  			} finally {
  				try {
  					connection.close();
  					logger.info("存储结束处理时间："+System.currentTimeMillis());
  				} catch (SQLException e) {
  					logger.error("连接关闭失败！", e);
  				}
  			}
  		ContractLock contractLock = new ContractLock();
  		try {
			Map<String,Object> paramMap = new ConcurrentHashMap<>();
			paramMap.put("contractNo",gedRepayment.getContractNo());
			paramMap.put("oldfactServiceFee",oldFactServiceFee);
			paramMap.put("cust_id",gedRepayment.getCustId());
			accRepayPlanService.compensation(paramMap);//充值服务费
  			if(StringUtils.isEmpty(str)){
  				contractLock.setContractNo(gedRepayment.getContractNo());
  				contractLock.setLockFlag("1");
  				contractLockDao.saveLockInfo(contractLock);
  				if (!"1".equals(gedRepayment.getAdvanceFlag())) {
					
				}
  				if ("1".equals(gedRepayment.getAdvanceFlag())) {
  					StaContractStatus staContractStatus = new StaContractStatus();
					staContractStatus.setContractNo(gedRepayment.getContractNo());
					StaContractStatus staContractStatusQuery = staContractService.get(staContractStatus);
					BigDecimal advance = new BigDecimal(staContractStatusQuery.getContractAmount()).multiply(new BigDecimal("0.03"));
					advance = advance.setScale(2,BigDecimal.ROUND_HALF_UP);
  					gedRepayment.setAdvanceRepayMoney(advance.toString());
  					//pushPeriodServiceFee(gedRepayment.getContractNo(),gedRepayment.getCustId(),Global.getConfig("advanceSettlePenaltyAccountCusId"),gedRepayment.getAdvanceRepayMoney());
  					String periodNum = "";
  					DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
  					AccRepayPlan accRepayPlanPeriod = accRepayPlanService.getCurrentRepayPlan(format2.parse(date), gedRepayment.getContractNo());
  					String currentPeriodNum = "";
  					if (accRepayPlanPeriod != null) {
  						currentPeriodNum = accRepayPlanPeriod.getPeriodNum();
  						updateAdvanceFee(gedRepayment,date,currentPeriodNum);
					}
					staContractService.updateContractStatus(gedRepayment.getContractNo());
					AccAdvanceGed accAdvanceGed = advanceGedService.queryAdvance(gedRepayment.getContractNo(),gedRepayment.getPeriodNum(),"");
					List<AccDiscount> accDiscounts = accDiscountService.findAccDiscountsByContractNo(gedRepayment.getContractNo());
					if (accDiscounts.size() == 0) {
						advancePushGet(gedRepayment,accAdvanceGed);
						/*if ((applyRegister.getNewOld() == null || ("0".equals(applyRegister.getNewOld()) && !Constants.PRODUCT_TYPE_ZGJH.equals(contract.getApproProductTypeId()) && !Constants.PRODUCT_TYPE_CG.equals(contract.getApproProductTypeId()))) && "1".equals(accAdvanceGed.getAdvanceFlag())) {
							advancePushGet(gedRepayment,accAdvanceGed);
						}else if ("0".equals(applyRegister.getNewOld()) && !Constants.PRODUCT_TYPE_ZGJH.equals(contract.getApproProductTypeId()) && !Constants.PRODUCT_TYPE_CG.equals(contract.getApproProductTypeId())) {
							advancePushGet(gedRepayment,accAdvanceGed);
						}*/
					}
  				}
  				// 对实还表做处理后查询实还服务费
				accRepayPlanService.compensation(paramMap);//充值服务费

  				SendGETRequest sendGETRequest = new SendGETRequest();
  				SendGETResponse sendGetResponse = null;
  				//还款判断期数是否逾期==1表示改期逾期不等于则逾期 
  				BigDecimal deductMoney = gedRepayment.getDeductAmount();
  				StaRepayPlanStatus staRepayPlanStatus = staRepayPlanStatusService.findStatusByContractNo(gedRepayment.getContractNo());
  				Map<String,Object> gedParam = Maps.newHashMap();
  				if (staRepayPlanStatus != null) {
  					com.resoft.credit.contract.entity.Contract creContract = contractService.getContractByContractNo(gedRepayment.getContractNo());
  					ApplyRegister applyRegister = new ApplyRegister();
  					applyRegister.setApplyNo(creContract.getApplyNo());
  					List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
  					if (!registerList.isEmpty()) {
  						applyRegister = registerList.get(0);
  					}
  					if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
  						gedParam.put("orderNo",creContract.getApplyNo());
  						gedParam.put("applyIdChild","");
  					}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
  						gedParam.put("orderNo",creContract.getApplyNo());
  						gedParam.put("applyIdChild",creContract.getApproId());
  					}
  					if(StringUtils.isNotBlank(staRepayPlanStatus.getRepayPeriodStatus()) && "0400".equals(staRepayPlanStatus.getRepayPeriodStatus()) || "0500".equals(staRepayPlanStatus.getRepayPeriodStatus())){
  						gedParam.put("status", 180);
  					}else if(StringUtils.isNotBlank(staRepayPlanStatus.getRepayPeriodStatus()) && "0100".equals(staRepayPlanStatus.getRepayPeriodStatus())){
  						gedParam.put("status", 190);
  					}
  					if (gedParam.size() == 3) {
  						AddOrderResponse addOrderResponseGed =	Facade.facade.sendGuanEDaiPeriodStatus(gedParam);
  						logger.info("推送冠E贷状态信息",addOrderResponseGed.getCode(),addOrderResponseGed.getReason());
  					}
  				}
  				Map<String,String> paramGedT = Maps.newHashMap();
  				paramGedT.put("contractNo",gedRepayment.getContractNo());
  				paramGedT.put("periodNum", gedRepayment.getPeriodNum());
  				List<StaRepayPlanStatus> staRepayPlanStatusList = staRepayPlanStatusService.getPeriod(paramGedT);
  				for(int i=0;i<staRepayPlanStatusList.size();i++){
  					StaRepayPlanStatus staRepayPlanStatusPeriod = staRepayPlanStatusList.get(i);
  					if(StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getRepayPeriodStatus()) && StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getPeriodNum())){
  						Integer hasIs = acc4FinancialPlatformServer.findOverdueContractPeriod(staRepayPlanStatusPeriod.getContractNo(),staRepayPlanStatusPeriod.getPeriodNum());
  						List<CompensatoryDetail> compensatoryDetails = compensatoryDetailService.findCompenSatorByConAndPer(staRepayPlanStatusPeriod.getContractNo(),staRepayPlanStatusPeriod.getPeriodNum());
  						if ("0".equals(String.valueOf(hasIs)) && "0400".equals(staRepayPlanStatusPeriod.getRepayPeriodStatus()) && !"1".equals(gedRepayment.getAdvanceFlag())) {
  							noamlRepayMent(staRepayPlanStatusPeriod,sendGETRequest,sendGetResponse);
						}
  						if ("1".equals(String.valueOf(hasIs)) && compensatoryDetails.size()>0 && deductMoney.compareTo(new BigDecimal("0.00")) > 0 && ("0100".equals(staRepayPlanStatusPeriod.getRepayPeriodStatus()) || "0300".equals(staRepayPlanStatusPeriod.getRepayPeriodStatus()))) {
  							deductMoney = overdueCompent(gedRepayment,compensatoryDetails,deductMoney);
						}
  					}
  				}
  				
  				
  			}
  			String contractStatus = acc4FinancialPlatformServer.findContractStatusByContractNo(gedRepayment.getContractNo());
  			com.resoft.credit.contract.entity.Contract creContract = contractService.getContractByContractNo(gedRepayment.getContractNo());
			if (("0700".equals(contractStatus) || "0900".equals(contractStatus) || "1800".equals(contractStatus)) && creContract != null && "0".equals(creContract.getGuaranteeRelation())) {
				creGuranteeCompanyService.updateGuranteeInfoByContractNo(creContract);
			}
  		} catch (Exception e) {
  			logger.error("推送冠E通或者资金平台失败：",e);
  		}finally{
  			contractLockDao.deleteLock(contractLock);
  		}
  		if(StringUtils.isEmpty(str)){
  		//根据合同号查询申请编号
  			Contract contract =  accContractDao.findContractByContractNo(gedRepayment.getContractNo());
  			ApplyRegister applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(contract.getApplyNo());
			List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
			if (!registerList.isEmpty()) {
				applyRegister = registerList.get(0);
			}
			String applyNo = "";
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey()) || "gq_loan_under".equals(applyRegister.getProcDefKey())){
				applyNo = contract.getApplyNo();
			}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
				com.resoft.credit.contract.entity.Contract creContract = contractService.getContractByContractNo(gedRepayment.getContractNo());
			if (creContract != null && StringUtils.isNotBlank(creContract.getApproId())) {
				applyNo = creContract.getApproId();
				}
			}

  		AddOrderResponse addOrderResponse = Facade.facade.sendOrderNoGET(applyNo);
  		logger.info(String.valueOf(addOrderResponse.getCode()),addOrderResponse.getReason());
  		}
  		logger.info("冠E贷充值入账:"+str+"冠易贷服务结束---------end");
  		interfaceInfoService.save(new InterfaceInfo(gedRepayment.getContractNo(), "冠E贷充值还款信息接口" ,str,new Date(),tm.getText()));
  		logger.info("结束处理时间："+System.currentTimeMillis());
        }
      }
      if("pushMoney".equalsIgnoreCase(queues.getPhysicalName())) {
    	  if (message instanceof TextMessage) {
             TextMessage tm = (TextMessage) message;
             String json = tm.getText();
			  System.err.println(json);
             Connection connection = null;
			  GedRepayment  gedRepayment = (GedRepayment)JsonTransferUtils.json2Bean(tm.getText(),GedRepayment.class);
			  String sqlNo = gedRepayment.getSeqNo();
			  String str = "";
 			  try {
   				logger.info("存储开始处理时间："+System.currentTimeMillis());
   				logger.info("流水："+json);
				connection = dataSource.getConnection();
 				CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT('" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "','" + sqlNo + "')}");
 				callableStatement.execute();

   			} catch (SQLException e) {
   				logger.error("存储调用失败！", e);
   				str = "入账失败";
   			} finally {
   				try {
   					connection.close();
   					logger.info("存储结束处理时间："+System.currentTimeMillis());
   				} catch (SQLException e) {
   					logger.error("连接关闭失败！", e);
   				}
   			}
    	  }
      }
      //贴息入账
      if("discountEnterAccount".equalsIgnoreCase(queues.getPhysicalName())) {
    	  if (message instanceof TextMessage) {
             TextMessage dis = (TextMessage) message;
             String json = dis.getText();
             GedRepayment  gedRepayment = (GedRepayment)JsonTransferUtils.json2Bean(dis.getText(),GedRepayment.class);
             Connection connection = null;
             String str = "";
 			  try {
   				logger.info("存储开始处理时间："+System.currentTimeMillis());
   				logger.info("流水："+json);
				connection = dataSource.getConnection();
 				CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT('" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "','" + gedRepayment.getSeqNo() + "')}");
 				callableStatement.execute();

   			} catch (SQLException e) {
   				logger.error("存储调用失败！", e);
   				str = "入账失败";
   			} finally {
   				try {
   					connection.close();
   					logger.info("存储结束处理时间："+System.currentTimeMillis());
   				} catch (SQLException e) {
   					logger.error("连接关闭失败！", e);
   				}
   			}

   			StaRepayPlanStatus staRepayPlanStatus = staRepayPlanStatusService.getStaByContractNoAndPeriodNum(gedRepayment.getContractNo(),gedRepayment.getPeriodNum());
   			AccAdvanceGed accAdvanceGed = advanceGedService.queryMaxPeriodNumAdvance(gedRepayment.getContractNo());
   			AccAdvanceGed accAdvance = advanceGedService.queryAdvance(gedRepayment.getContractNo(), gedRepayment.getPeriodNum(),null);
   			if (accAdvance != null) {
   				staContractService.updateContractStatus(gedRepayment.getContractNo());
			}
   			if (staRepayPlanStatus != null && StringUtils.isNotBlank(staRepayPlanStatus.getRepayPeriodStatus()) && ("0100".equals(staRepayPlanStatus.getRepayPeriodStatus()) || "0400".equals(staRepayPlanStatus.getRepayPeriodStatus())) && accAdvanceGed != null && StringUtils.isNotBlank(accAdvanceGed.getPeriodNum()) && gedRepayment.getPeriodNum().equals(accAdvanceGed.getPeriodNum())) {
   				StaContractStatus staContractStatus = new StaContractStatus();
				staContractStatus.setContractNo(gedRepayment.getContractNo());
				StaContractStatus staContractStatusQuery = staContractService.get(staContractStatus);
				BigDecimal advance = new BigDecimal(staContractStatusQuery.getContractAmount()).multiply(new BigDecimal("0.03"));
				advance = advance.setScale(2,BigDecimal.ROUND_HALF_UP);
					gedRepayment.setAdvanceRepayMoney(advance.toString());
   				advancePushGet(gedRepayment,accAdvanceGed);
			}else if (accAdvanceGed == null){
				pushGetFinishRepay(gedRepayment);//贴息完正常还款通知冠E通
			}
   			SendGETRequest sendGETRequest = new SendGETRequest();
			SendGETResponse sendGetResponse = null;
   			Map<String,String> paramGedT = Maps.newHashMap();
				paramGedT.put("contractNo",gedRepayment.getContractNo());
				paramGedT.put("periodNum", gedRepayment.getPeriodNum());
				List<StaRepayPlanStatus> staRepayPlanStatusList = staRepayPlanStatusService.getPeriod(paramGedT);
				for(int i=0;i<staRepayPlanStatusList.size();i++){
					StaRepayPlanStatus staRepayPlanStatusPeriod = staRepayPlanStatusList.get(i);
					if(StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getRepayPeriodStatus()) && StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getPeriodNum())){
						Integer hasIs = acc4FinancialPlatformServer.findOverdueContractPeriod(staRepayPlanStatusPeriod.getContractNo(),staRepayPlanStatusPeriod.getPeriodNum());
						if ("0".equals(String.valueOf(hasIs)) && "0400".equals(staRepayPlanStatusPeriod.getRepayPeriodStatus())) {
							noamlRepayMent(staRepayPlanStatusPeriod,sendGETRequest,sendGetResponse);
					}
				}
			}
    	  }
      	}
      if ("advanceFullRepayment".equalsIgnoreCase(queues.getPhysicalName())) {
		  if (message instanceof TextMessage) {
			  TextMessage textMessage = (TextMessage)message;
			  AdvanceFullRepayment fullRepayment = JsonTransferUtils
				  .json2Bean(textMessage.getText(), AdvanceFullRepayment.class);
			  String str = "";
			  String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			  try (Connection connection = dataSource.getConnection()) {
				  CallableStatement callableStatement = connection
					  .prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT('" + date + "','" + fullRepayment.getSeqNo() + "')}");
				  callableStatement.execute();
			  } catch (SQLException e) {
				  logger.error("存储调用失败！", e);
				  str = "入账失败";
			  }
		  }
		  //todo
	  }
    } catch (JMSException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  

  
  
  private void noamlRepayMent(StaRepayPlanStatus staRepayPlanStatusP,SendGETRequest sendGETRequest,SendGETResponse sendGetResponse){
			if (StringUtils.isNotBlank(staRepayPlanStatusP.getContractNo()) && StringUtils.isNotBlank(staRepayPlanStatusP.getPeriodNum())) {
				sendGETRequest.setContractNo(staRepayPlanStatusP.getContractNo());
				sendGETRequest.setPeriod(staRepayPlanStatusP.getPeriodNum());
				sendGetResponse = Facade.facade.SendGET(sendGETRequest,"1");
				logger.info(sendGetResponse.getResult(),sendGetResponse.getMsg());
			}
				
  } 
  private BigDecimal overdueCompent(GedRepayment gedRepayment,List<CompensatoryDetail> compensatoryDetails,BigDecimal deductMoney) throws Exception{
	  List<CompensatoryDetail> realCompensatoryDetails = new ArrayList<>();
	  for(int i= 0;i<compensatoryDetails.size();i++){
			Boolean flag = false;
			if(deductMoney.compareTo(compensatoryDetails.get(i).getCompensatoryAmount()) == 1 && !flag){
				realCompensatoryDetails.add(i, compensatoryDetails.get(i));
				deductMoney = deductMoney.subtract(compensatoryDetails.get(i).getCompensatoryAmount());
				flag =true;
			}
			if(deductMoney.compareTo(compensatoryDetails.get(i).getCompensatoryAmount()) == -1 && (deductMoney.compareTo(new BigDecimal("0.00")) == 1) && !flag){
				compensatoryDetails.get(i).setCompensatoryAmount(deductMoney);
				deductMoney = deductMoney.subtract(compensatoryDetails.get(i).getCompensatoryAmount());
				realCompensatoryDetails.add(i, compensatoryDetails.get(i));
				flag =true;
			}
			if(deductMoney.compareTo(compensatoryDetails.get(i).getCompensatoryAmount()) == 0 && !flag){
				compensatoryDetails.get(i).setCompensatoryAmount(deductMoney);
				realCompensatoryDetails.add(i, compensatoryDetails.get(i));
				deductMoney = deductMoney.subtract(compensatoryDetails.get(i).getCompensatoryAmount());
				flag =true;
			}
			if(deductMoney.compareTo(new BigDecimal("0.00")) == 0 || deductMoney.compareTo(new BigDecimal("0.00")) == -1){
				break;
			}		
		}
		String flag="";
		//推送资金平台
		for(CompensatoryDetail compensatoryDetail:realCompensatoryDetails){
			String seqNo = AccFinancialPlatformUtils.makeSeqNo();
			AccAccountDCRequest accAccountDCRequest = new AccAccountDCRequest();
			Map<String,Object> paramAccount = new HashMap<>();
			paramAccount.put("contractNo", compensatoryDetail.getContractNo());
			paramAccount.put("from_cust_id", gedRepayment.getCustId());
			paramAccount.put("to_cust_id", compensatoryDetail.getCustId());
			paramAccount.put("amount", compensatoryDetail.getCompensatoryAmount());
			AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
			//accAccountDCRequest.setFrom_cust_no(gedRepayment.getCustId());
			//accAccountDCRequest.setTo_cust_no(compensatoryDetail.getCustId());
			//accAccountDCRequest.setAmt(compensatoryDetail.getCompensatoryAmount().toString());
			accAccountDCRequest.setMchn(Global.getConfig("FPMchn"));
			//accAccountDCRequest.setSeq_no(compensatoryDetail.getSerialNum());
			accAccountDCRequest.setSeq_no(seqNo);
			if (Integer.valueOf(gedRepayment.getCustId())>100) {
				paramAccount.put("from_cust_type", "1");
				//accAccountDCRequest.setFrom_cust_type("1");
			}else {
				paramAccount.put("from_cust_type", "0");
				//accAccountDCRequest.setFrom_cust_type("0");
			}
			//accAccountDCRequest.setTo_cust_no(compensatoryDetail.getCustId());

			if (Integer.valueOf(compensatoryDetail.getCustId())>100) {
				paramAccount.put("to_cust_type", "1");
				//accAccountDCRequest.setTo_cust_type("1");//对公账户的话就填 0  借款人账户就填  1
			}else {
				paramAccount.put("to_cust_type", "0");
				//accAccountDCRequest.setTo_cust_type("0");//对公账户的话就填 0  借款人账户就填  1
			}
			accAccountDCRequest.setTrade_type("11080006");
			accAccountDCRequest.setData(JsonTransferUtils.bean2Json(paramAccount));
			accAccountDCRequest.setBack_url(Global.getConfig("Domain")+"/f/rest/funds/service/repaymentCapoitalBack");
			accAccountDCRequest.setPage_url("");
			String sign = Global.getConfig("FPMchn")+"|"+accAccountDCRequest.getSeq_no()+"|"+accAccountDCRequest.getTrade_type()+"|"+accAccountDCRequest.getData()+"|"+accAccountDCRequest.getPage_url()+"|"+accAccountDCRequest.getBack_url();
			accAccountDCRequest.setSignature(RSAUtils.signCallBack(sign.getBytes(),Global.getConfig("sysprkey")));
			accAccountDCResponse.setData(JsonTransferUtils.bean2Json(new ZjResoonseInfo()));
			accAccountDCResponse = AccFacade.facade.repaymentDC(accAccountDCRequest,gedRepayment.getContractNo(),"1");
			if("0000".equals(accAccountDCResponse.getResp_code())) {
				compensatoryDetailService.updateCompensatoryInfoBySeqNum(compensatoryDetail.getSerialNum(),seqNo);
			}
			logger.info(accAccountDCResponse.getResp_code(),accAccountDCResponse.getResp_msg());
			}
	  return deductMoney;
	  
  }
  
  /*private void pushPeriodServiceFee(String contractNo,String fromCustId,String toCustId,String money) throws Exception{
	  AccAccountDCRequest accAcrcountDCRequest = new AccAccountDCRequest();

		accAcrcountDCRequest.setMchn(Global.getConfig("FPMchn"));
		AccAccountRequest accAccountRequest = new AccAccountRequest();
		accAccountRequest.setContractNo(contractNo);
		accAccountRequest.setCustId(fromCustId);
		accAccountRequest.setCustType("1");
		accAccountRequest.setAmount(new BigDecimal(money));

		
  }*/
  

  private void pushGetFinishRepay(GedRepayment gedRepayment){
	  SendGETRequest sendGETRequest = new SendGETRequest();
		SendGETResponse sendGetResponse = null;
			Map<String,String> paramGedT = Maps.newHashMap();
			paramGedT.put("contractNo",gedRepayment.getContractNo());
			paramGedT.put("periodNum", gedRepayment.getPeriodNum());
			List<StaRepayPlanStatus> staRepayPlanStatusList = staRepayPlanStatusService.getPeriod(paramGedT);
			for(int i=0;i<staRepayPlanStatusList.size();i++){
				StaRepayPlanStatus staRepayPlanStatusPeriod = staRepayPlanStatusList.get(i);
				if(StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getRepayPeriodStatus()) && StringUtils.isNotEmpty(staRepayPlanStatusPeriod.getPeriodNum())){
					Integer hasIs = acc4FinancialPlatformServer.findOverdueContractPeriod(staRepayPlanStatusPeriod.getContractNo(),staRepayPlanStatusPeriod.getPeriodNum());
					if ("0".equals(String.valueOf(hasIs)) && "0400".equals(staRepayPlanStatusPeriod.getRepayPeriodStatus())) {
						noamlRepayMent(staRepayPlanStatusPeriod,sendGETRequest,sendGetResponse);
				}
			}
		}
  }
  
  
  //提前还款通知冠E通
  private void advancePushGet(GedRepayment gedRepayment,AccAdvanceGed accAdvanceGed)throws Exception{
	  StaContractStatus staContractStatus = new StaContractStatus();
		staContractStatus.setContractNo(gedRepayment.getContractNo());
		StaContractStatus staContractStatu = staContractService.get(staContractStatus);
		if ("1800".equals(staContractStatu.getRepayContractStatus())) {
			String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			AdvanceRepayGet advanceRepayGet =accRepayPlanService.queryStayMoney(gedRepayment.getContractNo(),date);
			AccAdvanceGed advance = advanceGedService.queryAdvance(gedRepayment.getContractNo(), "","0");
			if (advance == null) {
				AccRepayPlan accRepayPlan = accRepayPlanService.getCurrentRepayPlan(format1.parse(date), gedRepayment.getContractNo());
				if (accRepayPlan != null) {
					advanceRepayGet.setRemainAllInterest(new BigDecimal(accRepayPlan.getMangementFee()).add(new BigDecimal(accRepayPlan.getInterestAmount())));
				}
			}
			if (advanceRepayGet != null) {
				advanceRepayGet.setRepayDate(accAdvanceGed.getDataDate());
				advanceRepayGet.setRemainServiceFee(new BigDecimal("0.00"));
				advanceRepayGet.setContractNo(gedRepayment.getContractNo());
				advanceRepayGet.setAdvanceServiceFee(gedRepayment.getAdvanceRepayMoney().toString());
				String code = Facade.facade.repayAdvanceGet(advanceRepayGet);
				logger.info("提前还款通知冠E通",code);
			}
		}
  	}
  
  
//提前还款更新实还表
  private  void  updateAdvanceFee(GedRepayment gedRepayment,String date,String periodNum) throws Exception{
	  List<AccRepayPlan> accRepayPlans = accRepayPlanService.queryAccRepayPlan(gedRepayment.getContractNo(),gedRepayment.getPeriodNum());
	  List<StaRepayPlanStatus> staRepayPlanStatus = new ArrayList<>();
	  if (accRepayPlans.size() >0) {
		  Boolean flag = false;
		for(AccRepayPlan accRepayPlan : accRepayPlans){
			StaRepayPlanStatus staRepayPlanStatus2 = staRepayPlanStatusService.getStaByContractNoAndPeriodNum(accRepayPlan.getContractNo(), accRepayPlan.getPeriodNum());
			StaRepayPlanStatus staRepayPlan = new StaRepayPlanStatus();
			String state = comparePeriod(accRepayPlan.getPeriodNum(),periodNum);
			staRepayPlan.setFactAddAmount("0.00");
			staRepayPlan.setFactCapitalAmount("0.00");
			staRepayPlan.setFactFineAmount("0.00");
			staRepayPlan.setFactInterestAmount("0.00");
			staRepayPlan.setFactMangementFee("0.00");
			staRepayPlan.setFactPenaltyAmount("0.00");
			staRepayPlan.setFactServiceFee("0.00");
			staRepayPlan.setPenaltyExemptAmount("0.00");
			staRepayPlan.setFineEepmtAmount("0.00");
			if ("0".equals(state)) {
				staRepayPlan.setRepayPeriodStatus("0400");
			}
			if ("1".equals(state)) {
				staRepayPlan.setRepayPeriodStatus("0500");
			}
			if ("2".equals(state)) {
				staRepayPlan.setRepayPeriodStatus("0100");
			}
			if (!flag) {
				staRepayPlan.setFactBreakAmount(gedRepayment.getAdvanceRepayMoney());
				flag = true;
			}
			BigDecimal stayMoney = new BigDecimal(accRepayPlan.getRepayAmount());
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getInterestAmount())) >= 0 && !"1".equals(state)) {
				staRepayPlan.setFactInterestAmount(accRepayPlan.getInterestAmount());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getInterestAmount()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getInterestAmount())) < 0 && !"1".equals(state) && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && new BigDecimal(staRepayPlan.getFactInterestAmount()).compareTo(new BigDecimal("0.00")) == 0) {
				staRepayPlan.setFactInterestAmount(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getMangementFee())) >= 0 && !"1".equals(state)) {
				staRepayPlan.setFactMangementFee(accRepayPlan.getMangementFee());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getMangementFee()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getMangementFee())) < 0 && !"1".equals(state) && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && new BigDecimal(staRepayPlan.getFactMangementFee()).compareTo(new BigDecimal("0.00")) == 0) {
				staRepayPlan.setFactMangementFee(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getServiceFee())) >= 0 && !"1".equals(state)) {
				staRepayPlan.setFactServiceFee(accRepayPlan.getServiceFee());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getServiceFee()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getServiceFee())) < 0 && !"1".equals(state) && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && new BigDecimal(staRepayPlan.getFactServiceFee()).compareTo(new BigDecimal("0.00")) == 0) {
				staRepayPlan.setFactServiceFee(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getCapitalAmount())) >= 0) {
				staRepayPlan.setFactCapitalAmount(accRepayPlan.getCapitalAmount());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getCapitalAmount()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getCapitalAmount())) < 0 && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && (new BigDecimal(staRepayPlan.getFactCapitalAmount()).compareTo(new BigDecimal("0.00")) == 0 || staRepayPlan.getFactCapitalAmount() == null)) {
				staRepayPlan.setFactCapitalAmount(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getPenaltyAmount())) >= 0 && "2".equals(state)) {
				staRepayPlan.setFactPenaltyAmount(accRepayPlan.getPenaltyAmount());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getPenaltyAmount()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getPenaltyAmount())) < 0 && "2".equals(state) && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && new BigDecimal(staRepayPlan.getFactPenaltyAmount()).compareTo(new BigDecimal("0.00")) == 0) {
				staRepayPlan.setFactPenaltyAmount(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getFineAmount())) >= 0 && "2".equals(state)) {
				staRepayPlan.setFactFineAmount(accRepayPlan.getFineAmount());
				stayMoney = stayMoney.subtract(new BigDecimal(accRepayPlan.getFineAmount()));
			}
			if (stayMoney.compareTo(new BigDecimal(accRepayPlan.getFineAmount())) < 0  && stayMoney.compareTo(new BigDecimal("0.00")) > 0 && new BigDecimal(staRepayPlan.getFactFineAmount()).compareTo(new BigDecimal("0.00")) == 0 && "2".equals(state)) {
				staRepayPlan.setFactFineAmount(stayMoney.toString());
				stayMoney = stayMoney.subtract(stayMoney);
			}
			staRepayPlan.setCapitalInterestRepayDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
			staRepayPlan.setAllRepayDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
			staRepayPlan.setPeriodNum(accRepayPlan.getPeriodNum());
			staRepayPlan.setContractNo(accRepayPlan.getContractNo());
			staRepayPlan.setFactRepayAmount(new BigDecimal(staRepayPlan.getFactMangementFee()).add(new BigDecimal(staRepayPlan.getFactCapitalAmount()).add(new BigDecimal(staRepayPlan.getFactServiceFee()).add(new BigDecimal(staRepayPlan.getFactInterestAmount())))).toString());
			if (staRepayPlanStatus2 != null) {
				staRepayPlan.setDataDt(staRepayPlanStatus2.getDataDt());
				staRepayPlan.setOrgLevel2(staRepayPlanStatus2.getOrgLevel2());
				staRepayPlan.setOrgLevel3(staRepayPlanStatus2.getOrgLevel3());
				staRepayPlan.setOrgLevel4(staRepayPlanStatus2.getOrgLevel4());
				staRepayPlan.setPenaltyExemptAmount(staRepayPlanStatus2.getPenaltyExemptAmount());
				staRepayPlan.setBackAmount(staRepayPlanStatus2.getBackAmount());
				staRepayPlan.setFactAddAmount(staRepayPlanStatus2.getFactAddAmount());
				staRepayPlan.setFineEepmtAmount(staRepayPlanStatus2.getFineEepmtAmount());
			}
			
			staRepayPlanStatus.add(staRepayPlan);
			if (("0.00".equals(stayMoney.toString()) && !"1".equals(state)) || ("1".equals(state) && stayMoney.compareTo(new BigDecimal("0.00") ) >= 0)) {
				continue;
			}
			
		}
	}
	  
	  if (staRepayPlanStatus.size() > 0) {
			staRepayPlanStatusService.saveUpdate(staRepayPlanStatus,gedRepayment.getContractNo());;
	}
  }
  
  
//两个日期的比较
  private String comparePeriod(String accPeriodNum,String currentPeriodNum) throws Exception{  
      if (Integer.valueOf(accPeriodNum) == Integer.valueOf(currentPeriodNum)) {  
    	  return "0";  //正常
      		}else if (Integer.valueOf(accPeriodNum) > Integer.valueOf(currentPeriodNum)) {
    	  return "1"; //提前
      	}else if (Integer.valueOf(accPeriodNum) < Integer.valueOf(currentPeriodNum)) {
      		return "2";//逾期
		}
      return "";
  }
}

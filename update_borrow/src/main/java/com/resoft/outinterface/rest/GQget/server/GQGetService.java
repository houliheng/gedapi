package com.resoft.outinterface.rest.GQget.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.GQget.client.service.GQGetClientService;
import com.resoft.outinterface.rest.GQget.server.entry.request.bidWithdraw.BidWithdrawRequest;
import com.resoft.outinterface.rest.GQget.server.entry.response.GetServiceBidDataResponse;
import com.resoft.outinterface.rest.GQget.server.entry.response.GetServiceResponse;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@RestController
@RequestMapping(value="/f/rest/Get/service")
public class GQGetService {
	//满标接口
	@Autowired
	private GQGetClientService clientService;
	@Autowired
	private Acc4FinancialPlatformServer acc4FinancialPlatformServer;
	@Autowired
	private ContractService contractService;
	@Autowired
	private UnderCompanyInfoService underCompanyInfoService;
	@Autowired
	private CheckApproveService checkApproveService;

	private static final Logger logger=LoggerFactory.getLogger(GQGetService.class);

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	@RequestMapping(method=RequestMethod.POST,value="bidWithdraw")
	public String form2(@RequestBody String json){
		Date sendDate = new Date();
		logger.info("提现回盘接口接收："+json);
		String str="";
		GetServiceResponse rp = new GetServiceResponse();
		GetServiceBidDataResponse bdr = new GetServiceBidDataResponse();
		BidWithdrawRequest obj=null;
		try {
			obj = (BidWithdrawRequest)JsonTransferUtils.json2Bean(json, BidWithdrawRequest.class);
			String contract_no = obj.getBiddata().getContract_no();

			String deposit_status = obj.getBiddata().getDeposit_status();
			CheckApprove checkApprove = checkApproveService.getByUnderContract(contract_no);
			if(checkApprove!=null){
				if("0".equals(deposit_status)){
					//满标放款
					underCompanyInfoService.updateStatus("4", checkApprove.getApplyNo());
				}else if("2".equals(deposit_status)){
					underCompanyInfoService.updateStatus("5", checkApprove.getApplyNo());
				}
			}
			try {
				AccContract contract = contractService.getCreContractByContractNo(obj.getBiddata().getContract_no());
				clientService.updateApplyLoanStatusWithdraw(obj.getBiddata(),contract);
				// 转换日期格式
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdfs.parse(obj.getBiddata().getDeposit_time());
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

				//String deductDateStr = sdfs.format(DateUtils.getNextMonth(date));
				String deductDateStr = sdfs.format(date);//直接传提现回盘日期，否则如果getNextMonth得到2月可能导致还款日不准确
				repayPlanData.setApproProductTypeCode(contract.getApproProductTypeId());
				//repayPlanData.setDeductDate(DateUtils.getNextMonth(date));
				repayPlanData.setDeductDate(date);//直接传提现回盘日期，否则如果getNextMonth得到2月可能导致还款日不准确
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
				acc4FinancialPlatformServer.insertAccStaContractStatus(obj.getBiddata().getContract_no());
			} catch (Exception e) {
				str = "插入acc_sta_contract_status出错";
				logger.error("插入acc_sta_contract_status出错", e);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			bdr.setIs_success(false);
			bdr.setResq_code("1111");
			bdr.setResq_msg("失败");
			rp.setBiddata(bdr);
			try {
				str = JsonTransferUtils.bean2Json(rp);
			} catch (IOException e1) {
				logger.error("提现回盘接口", e1);
			}
			logger.info("提现回盘发送："+str);
			return str;
		}
		bdr.setIs_success(true);
		bdr.setResq_code("0000");
		bdr.setResq_msg("成功");
		rp.setBiddata(bdr);
		try{
			interfaceInfoService.save(new InterfaceInfo(obj.getBiddata().getContract_no(), "提现回盘接口" , rp.getBiddata().getResq_msg(), sendDate,json));
		}catch(Exception e){
			logger.error("接口信息记录失败", e);
		}
		try {
			str = JsonTransferUtils.bean2Json(rp);
		} catch (IOException e) {
			logger.error("满标接口数据转换错误", e);
		}
		logger.info("满标回盘接口发送："+str);
		return str;
	}

}

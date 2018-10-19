package com.resoft.Accoutinterface.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resoft.Accoutinterface.rest.financialPlatform.AccFinancialPlatformClient;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountSearchResponse;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;

public enum  AccFacade {
	
	facade;  
	private AccXmlUtilsInterface lmd= new AccXMlUtilsImpl();
	private AccFinancialPlatformClient fpc = new AccFinancialPlatformClient();
	private static final Logger logger=LoggerFactory.getLogger(AccFinancialPlatformClient.class);
	public String formatXml(String str){
		try {
			return lmd.formatXml(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public <T>T XMLStringToBean(String Xml,Class<T> objClass) throws Exception{
		return lmd.XMLStringToBean(Xml, objClass);
	}
	public <T>String BeanToXmlString(Object obj,Class<T> objClass)throws Exception{
		return lmd.BeanToXmlString(obj, objClass);
	}
	 /**
		 * @author caoyinglong
		 *流标
		 */
	 /**
		 * @author caoyinglong
		 *银行卡变更
		 */
	 public void bankCardChange(String contractNo){
		 logger.info("资金平台银行卡变更接口访问 -----------start");
		 fpc.bankCardChange(contractNo);
		 logger.info("资金平台银行卡变更接口访问 -----------end");
	 }
	
	
	 /**
		 * @author caoyinglong
		 *保证金退还
		 */
	 public void refundDeposit(String contractNo){
		 logger.info("资金平台保证金退还接口访问 -----------start");
		 fpc.refundDeposit(contractNo);
		 logger.info("资金平台保证金退还接口访问 -----------end");
	 }
	 /**
		 * @author caoyinglong
		 *还款划扣
		 */
	 public void repaymentWithholding(String contractNo){
		 logger.info("资金平台还款划扣接口访问 -----------start");
		 try {
			fpc.repaymentWithholding(contractNo);
		} catch (Exception e) {
			logger.error("资金平台还款划扣接口访问失败",e);
		}
		 logger.info("资金平台还款划扣接口访问 -----------end");
	 }
	 
	public List<CompensatoryAccount> repaymentFindAccount(List<CompensatoryAccount> oldCompensatoryAccountList) {
		List<CompensatoryAccount> newCompensatoryAccountList=null;
		 logger.info("对公账户余额查询接口访问 -----------start");
		 try {
			 newCompensatoryAccountList = fpc.repaymentFindAccount(oldCompensatoryAccountList);
		} catch (Exception e) {
			logger.error("对公账户余额查询接口访访问失败",e);
		}
		 logger.info("对公账户余额查询接口访问 -----------end");
		return newCompensatoryAccountList;
	}
	
	public AccAccountDCResponse repaymentDC(AccAccountDCRequest accAccountDCRequest,String contractNo,String flag) {
		logger.info("资金平台DC接口访问 -----------start");
		AccAccountDCResponse accAccountDCResponse=null; 
		 try {
			 accAccountDCResponse =  fpc.repaymentDC(accAccountDCRequest,contractNo,flag);
		} catch (Exception e) {
			logger.error("资金平台DC接口访问失败",e);
		}
		 logger.info("资金平台DC接口访问 -----------end");
		return accAccountDCResponse;
	}
	
	/**
	 * 冠E贷查询账户余额
	 * @param oldCompensatoryAccountList
	 * @return
	 */
	public AccAccountSearchResponse  repaymentFindAccountGed(String contractNo, String custId) {
		 logger.info("冠E通发送信息对公账户余额查询接口访问 -----------start");
		 AccAccountSearchResponse accAccountSearchResponse = new AccAccountSearchResponse();
		 try {
			 accAccountSearchResponse = fpc.repaymentFindAccountGed(contractNo,custId);
		} catch (Exception e) {
			logger.error("冠E通发送信息对公账户余额查询接口访访问失败",e);
		}
		 logger.info("冠E通发送信息对公账户余额查询接口访问 -----------end");
		return accAccountSearchResponse;
	}



	//资金代偿、费用收取、公共接口
	public AccAccountDCResponse  repaymentCompensation(String json,String contractNo){

		logger.info("资金代偿费用收取--------------start");
		AccAccountDCResponse accAccountDCResponse =  null;
		try{
			accAccountDCResponse = fpc.Compensation(json,contractNo);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("资金代偿费用收取访问失败");
		}
		logger.info("资金代偿费用收取------------end");
		return accAccountDCResponse;
	}

	/**
	 * 划拨充值接口
	 * @param request 接口请求信息
	 * @param contractNo 合同编号
	 * @return
	 */
	public AccAccountDCResponse transferWithHold(AccAccountDCRequest request, String contractNo) {
		logger.info("资金 划拨充值 接口访问 ------------------ start");
		AccAccountDCResponse response = null;
		try {
			response = fpc.transferWithHold(request, contractNo);
		} catch (Exception e) {
			logger.error("资金 划拨充值 接口访问失败", e);
		}
		logger.info("资金 划拨充值 接口访问 ------------------ end");
		return response;
	}
	



}

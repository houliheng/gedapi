package com.resoft.accounting.discountStream.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductApply.dao.DeductApplyDao;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.discountStream.dao.AccDiscountStreamDao;
import com.resoft.accounting.discountStream.entity.AccDiscountStream;
import com.resoft.accounting.discountStream.entity.ContractDetailVo;
import com.resoft.accounting.discountStream.entity.PeriodDiscountDetail;
import com.resoft.accounting.financialPlatform.dao.Acc4FinancialPlatformDao;
import com.resoft.activemq.service.ProducerService;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.outinterface.rest.ged.entity.GedAccAccountDeduct;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 贴息流水Service
 * @author gsh
 * @version 2018-05-23
 */
@Service
@Transactional(readOnly = true)
public class AccDiscountStreamService extends CrudService<AccDiscountStreamDao, AccDiscountStream> {
	private static final Logger logger = LoggerFactory.getLogger(AccDiscountStreamService.class);
	@Autowired
	private ContractService contractService;
	@Autowired
	private GedAccountService gedAccountService;
	@Autowired
	private DeductApplyDao deductApplyDao;
	@Autowired
	private ContractLockDao contractLockDao;
	@Autowired
	private  Acc4FinancialPlatformDao acc4FinancialPlatformDao;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private AccContractDao accContractDao;
	public AccDiscountStream get(String id) {
		return super.get(id);
	}
	
	public List<AccDiscountStream> findList(AccDiscountStream accDiscountStream) {
		return super.findList(accDiscountStream);
	}
	
	public Page<AccDiscountStream> findPage(Page<AccDiscountStream> page, AccDiscountStream accDiscountStream) {
		return super.findPage(page, accDiscountStream);
	}
	
	@Transactional(readOnly = false)
	public void save(AccDiscountStream accDiscountStream) {
		super.save(accDiscountStream);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccDiscountStream accDiscountStream) {
		super.delete(accDiscountStream);
	}
	
	@Transactional(readOnly = false)
	public AjaxView pushGeTAndZjSave(AccDiscountStream accDiscountStream,AjaxView ajaxView){
		try {
			AccAccountDCRequest accAccountDCRequest=new AccAccountDCRequest();
			AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
			Map<String,String> paramAccount = new HashMap<>();
			paramAccount.put("contractNo", accDiscountStream.getContractNo());
			paramAccount.put("from_cust_id",accDiscountStream.getFromCapitalNo());
			paramAccount.put("to_cust_id",accDiscountStream.getToCapitalNo());
			paramAccount.put("amount", accDiscountStream.getDiscountFee().toString());
			accAccountDCRequest.setMchn(Global.getConfig("FPMchn"));
			String seqNo = OutInterfaceUtils.makeSeqNo();
			accAccountDCRequest.setSeq_no(seqNo);
			
			accAccountDCRequest.setTrade_type("11080004");
			//accAccountDCRequest.setFrom_cust_no(accDiscountStream.getFromCapitalNo());
			//accAccountDCRequest.setFrom_cust_no("17623");
			if (Integer.valueOf(accDiscountStream.getFromCapitalNo())>100) {
				paramAccount.put("from_cust_type", "1");
			}else {
				paramAccount.put("from_cust_type", "0");
			}
			//accAccountDCRequest.setTo_cust_no(accDiscountStream.getToCapitalNo());
			
			if (Integer.valueOf(accDiscountStream.getToCapitalNo())>100) {
				paramAccount.put("to_cust_type", "1");//对公账户的话就填 0  借款人账户就填  1
			}else {
				paramAccount.put("to_cust_type", "0");//对公账户的话就填 0  借款人账户就填  1
			}
		//	accAccountDCRequest.setAmt(accDiscountStream.getDiscountFee().toString());
		//	accAccountDCRequest.setTransfer_flag("0");
			accAccountDCRequest.setData(JsonTransferUtils.bean2Json(paramAccount));
			accAccountDCResponse = AccFacade.facade.repaymentDC(accAccountDCRequest,accDiscountStream.getContractNo(),"2");
			accDiscountStream.setSeqNo(seqNo);
			if("0000".equals(accAccountDCResponse.getResp_code())) {
				accDiscountStream.setDiscountStatus("0");
				accDiscountStream.setFactDiscountFee(accDiscountStream.getDiscountFee());
				save(accDiscountStream);
				insertDeductTable(accDiscountStream,seqNo);
				ajaxView.setSuccess().setMessage("贴息成功");
			}else {
				accDiscountStream.setDiscountStatus("1");
				ajaxView.setFailed().setMessage("贴息失败");
				save(accDiscountStream);
			}
			
		} catch (Exception e) {
			logger.error("保存失败",e);
			ajaxView.setFailed().setMessage("保存失败");
		}
		return ajaxView;
	}
	
	
	
	private void insertDeductTable(AccDiscountStream accDiscountStream,String seqNo) throws Exception{
		DeductApply deductApply = new DeductApply();
		Contract contract = contractService.getContractByContractNo(accDiscountStream.getContractNo());
		if ("0".equals(accDiscountStream.getDiscountType()) && contract != null) {
			
			deductApply.setMobileNum(contract.getMiddlemanMobileNum());
			deductApply.setBankcardNo(contract.getMiddlemanBankcardNo());//银行卡号
			String bank = DictUtils.getDictLabel(contract.getMiddlemanBankNo(), "BANKS", null);
			deductApply.setBank(bank);//银行
			deductApply.setCustName(contract.getMiddlemanName());//个人账户名称
		}
		com.resoft.accounting.contract.entity.Contract accContract = accContractDao.findContractByContractNo(accDiscountStream.getContractNo());
		if ("0".equals(accDiscountStream.getDiscountType()) && contract == null) {
			deductApply.setMobileNum(accContract.getRepBankMobile());
			deductApply.setBankcardNo(accContract.getRepBankcardNo());//银行卡号
			String bank = DictUtils.getDictLabel(accContract.getRepBank(), "BANKS", null);
			deductApply.setBank(bank);//银行
			deductApply.setCustName(accContract.getCustName());//个人账户名称
		}
		if ("1".equals(accDiscountStream.getDiscountType())) {
			GedAccount singleByCustID = gedAccountService.getSingleByCustID(accDiscountStream.getFromCapitalNo());
			deductApply.setMobileNum(singleByCustID.getLegalPerPhone());
			deductApply.setBankcardNo(singleByCustID.getCompanyAccount());//银行卡号
			String bank = DictUtils.getDictLabel(singleByCustID.getCompanyBankOfDeposit(), "BANKS", null);
			deductApply.setBank(bank);//银行
			deductApply.setCustName(singleByCustID.getLegalPerName());//个人账户名称
		}
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String deductTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		String deductApplyNo = IdGen.uuid();
		deductApply.setDataDt(date);
		deductApply.setDeductTime(deductTime);
		deductApply.setDeductCustId(Global.getConfig("deductCustId"));
		deductApply.setDeductCustName(UserUtils.getUser().getName());
		deductApply.setStreamNo(AccFinancialPlatformUtils.makeSeqNo());
		deductApply.setDeductApplyNo(deductApplyNo);
		deductApply.setContractNo(accDiscountStream.getContractNo());
		deductApply.setCapitalTerraceNo(accDiscountStream.getFromCapitalNo());//资金平台账号
		deductApply.setDeductAmount(accDiscountStream.getDiscountFee().toString());
		deductApply.setDeductType("10180001");
		deductApply.setIsLock("1");
		deductApplyDao.saveDCDeductApply(deductApply);
		
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(accDiscountStream.getContractNo());
		contractLock.setLockFlag("1");
		contractLockDao.saveLockInfo(contractLock);
		
		GedAccAccountDeduct gedAccAccountDeduct = new GedAccAccountDeduct();
		gedAccAccountDeduct.setCapitalTerraceNo(accDiscountStream.getFromCapitalNo());
		gedAccAccountDeduct.setContractNo(accDiscountStream.getContractNo());
		gedAccAccountDeduct.setSeqNo(seqNo);
		gedAccAccountDeduct.setDeductApplyNo(deductApplyNo);
		gedAccAccountDeduct.setStreamNo(OutInterfaceUtils.makeSeqNo());
		gedAccAccountDeduct.setCompeleteTime(new Date());
		gedAccAccountDeduct.setDeductAmount(accDiscountStream.getDiscountFee().toString());
		acc4FinancialPlatformDao.insertGedAccAccountDeduct(gedAccAccountDeduct);
		//Contract contrac = contractService.getContractByContractNo(accDiscountStream.getContractNo());
		Map<String,String> param = new HashMap<>();
		param.put("contractNo",accDiscountStream.getContractNo());
		param.put("periodNum",accDiscountStream.getPeriodNum());
		param.put("seqNo",seqNo);
		if (accContract != null) {
			param.put("custId", accContract.getCapitalTerraceNo());
		}
		String repayMent = JsonTransferUtils.bean2Json(param);
		producerService.discountEnterAccount(repayMent);
	}
	
	
	
	public AccDiscountStream queryDisStrBycontractNoAndPer(String contractNo,String periodNum){
		return dao.queryDisStrBycontractNoAndPer(contractNo,periodNum);
	}
	
	
	public ContractDetailVo findContractDiscountDetail(String contractNo){
		return dao.findContractDiscountDetail(contractNo);
	}
	
	public List<PeriodDiscountDetail> queryDiscountDetails(String contractNo){
		return dao.queryDiscountDetails(contractNo);
	}

	public List<AccDiscountStream> getByContract(String contractNo) {
		// TODO Auto-generated method stub
		return dao.getByContract(contractNo);
	}
}
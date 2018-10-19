package com.resoft.credit.compenSatoryDetail.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.ZjResoonseInfo;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.common.utils.JsonTransferUtils;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductApply.dao.DeductApplyDao;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.financialPlatform.dao.Acc4FinancialPlatformDao;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.compenSatoryDetail.dao.CompensatoryDetailDao;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.outinterface.rest.ged.entity.GedAccAccountDeduct;
import com.resoft.outinterface.rest.sendGET.entry.SendGETRequest;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.utils.Facade;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.resoft.outinterface.utils.RSAUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 代偿详情表Service
 * @author jml
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class CompensatoryDetailService extends CrudService<CompensatoryDetailDao, CompensatoryDetail> {

	@Autowired
	private GedAccountService gedAccountService;
	@Autowired
	private CompensatoryDetailDao compensatoryDetailDao;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private DeductApplyDao deductApplyDao;
	@Autowired
	private ContractLockDao contractLockDao;
	@Autowired
	private Acc4FinancialPlatformDao acc4FinancialPlatformDao;
	@Autowired
	private ContractService contractService;
	@Autowired
	private CreGedAccountCompanyService gedCompanyAccountService;
	public CompensatoryDetail get(String id) {
		return super.get(id);
	}

	public List<CompensatoryDetail> findList(CompensatoryDetail compensatoryDetail) {
		return super.findList(compensatoryDetail);
	}

	public Page<CompensatoryDetail> findPage(Page<CompensatoryDetail> page, CompensatoryDetail compensatoryDetail) {
		return super.findPage(page, compensatoryDetail);
	}

	@Transactional(readOnly = false)
	public void save(CompensatoryDetail compensatoryDetail) {
		compensatoryDetail.setSurplusAmount(new BigDecimal("0.00"));
		super.save(compensatoryDetail);
	}

	@Transactional(readOnly = false)
	public void delete(CompensatoryDetail compensatoryDetail) {
		super.delete(compensatoryDetail);
	}
	@Transactional(readOnly = false)
	public AccAccountDCResponse repaymentDC(CompensatoryDetail compensatoryDetail,String compensatoryType,BigDecimal staRepayPlanStatus) throws Exception {
		AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
		if (staRepayPlanStatus.compareTo(BigDecimal.ZERO) == 1) {
			accAccountDCResponse = DcTransfer(compensatoryDetail,staRepayPlanStatus,accAccountDCResponse);
		}else{
			compensatoryDetail.setCompensatoryStatus("0");
		}
		compensatoryDetail.setId(null);
		compensatoryDetail.preInsert();
		compensatoryDetailDao.insert(compensatoryDetail);
		return accAccountDCResponse;
	}
	@Transactional(readOnly = false)
	public SendGETResponse sendGET(CompensatoryDetail compensatoryDetail) {
		SendGETRequest sendGETRequest =new SendGETRequest();
		sendGETRequest.setContractNo(compensatoryDetail.getContractNo());
		sendGETRequest.setPeriod(compensatoryDetail.getPeriodNum());
		sendGETRequest.setCompensatoryCustId(compensatoryDetail.getCustId());
		SendGETResponse sendGETResponse = Facade.facade.SendGET(sendGETRequest,"0");
		return sendGETResponse;
	}



	/**
	 * 查询代扣明细
	 * @param
	 * @return
	 */
	public List<CompensatoryDetail>  findCompenSatorByConAndPer(String contractNo,String periodNum){
		return this.dao.findCompenSatorByConAndPer(contractNo,periodNum);
	}

	/**
	 * 更新是否已还代偿人账户
	 * @param param
	 */
	@Transactional(readOnly = false)
	public void updateSendZjStatus(Map<String,String> param,BigDecimal surplusAmount,BigDecimal compensatoryAmount){
		this.dao.updateSendZjStatus(param.get("contractNo"),param.get("periodNum"),surplusAmount);
	}

	public String getAmount(SendGETRequest comAccount) {
		return dao.getAmount(comAccount.getContractNo(),comAccount.getPeriod());
	}


	/**
	 * 查询代偿金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal queryCompenMoneyByContractNo(String contractNo){
		return this.dao.queryCompenMoneyByContractNo(contractNo).getCompensatoryAmount();
	}

	@Transactional(readOnly = false)
	public void insertDeductTable(CompensatoryDetail compensatoryDetail,String serialNum) {
		DeductApply deductApply = new DeductApply();
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String deductTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		String deductApplyNo = IdGen.uuid();
		deductApply.setDataDt(date);
		deductApply.setDeductTime(deductTime);
		deductApply.setDeductCustId(Global.getConfig("deductCustId"));
		deductApply.setDeductCustName(UserUtils.getUser().getName());
		deductApply.setStreamNo(AccFinancialPlatformUtils.makeSeqNo());
		deductApply.setDeductApplyNo(deductApplyNo);
		deductApply.setContractNo(compensatoryDetail.getContractNo());

		GedAccount singleByCustID = gedAccountService.getSingleByCustID(compensatoryDetail.getCustId());
		if (singleByCustID != null) {
			deductApply.setMobileNum(singleByCustID.getLegalPerPhone());
			deductApply.setBankcardNo(singleByCustID.getCompanyAccount());//银行卡号
			String bank = DictUtils.getDictLabel(singleByCustID.getCompanyBankOfDeposit(), "BANKS", null);
			deductApply.setCustName(singleByCustID.getLegalPerName());//个人账户名称
			deductApply.setBank(bank);//银行
		}
		if (singleByCustID == null) {
			CreGedAccountCompany gedCompanyAccount = gedCompanyAccountService.getCompanyAccountByCustId(compensatoryDetail.getCustId());
			if (gedCompanyAccount != null) {
				deductApply.setMobileNum(gedCompanyAccount.getLegalMobile());
				deductApply.setBankcardNo(gedCompanyAccount.getCompanyAccount());//银行卡号
				deductApply.setCustName(gedCompanyAccount.getLegalName());//个人账户名称
				deductApply.setBank(gedCompanyAccount.getCompanyBankOfDepositValue());//银行
			}
		}
		deductApply.setCapitalTerraceNo(compensatoryDetail.getCustId());//资金平台账号
		//deductApply.setCapitalTerraceNo("15157");//资金平台账号
		deductApply.setDeductAmount(compensatoryDetail.getCompensatoryAmount().toString());
		deductApply.setDeductType("10180001");

		deductApply.setIsLock("1");
		deductApply.setFlag("3");//表示代偿进行的划扣
		deductApplyDao.saveDCDeductApply(deductApply);

		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(compensatoryDetail.getContractNo());
		contractLock.setLockFlag("1");
		contractLockDao.saveLockInfo(contractLock);

		GedAccAccountDeduct gedAccAccountDeduct = new GedAccAccountDeduct();
		gedAccAccountDeduct.setCapitalTerraceNo(compensatoryDetail.getCustId());
		//gedAccAccountDeduct.setCapitalTerraceNo("15157");
		gedAccAccountDeduct.setContractNo(compensatoryDetail.getContractNo());
		gedAccAccountDeduct.setSeqNo(serialNum);
		gedAccAccountDeduct.setDeductApplyNo(deductApplyNo);
		gedAccAccountDeduct.setStreamNo(OutInterfaceUtils.makeSeqNo());
		gedAccAccountDeduct.setCompeleteTime(new Date());
		gedAccAccountDeduct.setDeductAmount(compensatoryDetail.getCompensatoryAmount().toString());
		//gedAccAccountDeduct.setDeductId(UserUtils.getUser().getLoginName());
		acc4FinancialPlatformDao.insertGedAccAccountDeduct(gedAccAccountDeduct);

	}

	public CompensatoryDetail getCompenyByContractAndPerNum(String contractNo,String period){
		return dao.getCompenyByContractAndPerNum(contractNo, period);
	}

	private AccAccountDCResponse DcTransfer(CompensatoryDetail compensatoryDetail,BigDecimal staRepayPlanStatus,AccAccountDCResponse accAccountDCResponse) throws Exception{
		AccAccountDCRequest accAccountDCRequest=new AccAccountDCRequest();
		String contractNo = compensatoryDetail.getContractNo();
		String custId = null;
		Contract contract = null;
		ApplyRegister applyRegister=applyRegisterService.getApplyRegisterByContractNo(compensatoryDetail.getContractNo());
		contract = contractService.getContractByApplyNo(applyRegister.getApplyNo());
		if (Constants.CUST_TYPE_PERSON.equals(applyRegister.getCustType())) {
			custId = gedAccountService.getByBankNo(contract.getRecBankcardNo());
		}else if (Constants.CUST_TYPE_ORG.equals(applyRegister.getCustType())) {
			if (Constants.PROC_DEF_KEY_LHSX.equals(applyRegister.getProcDefKey())) {
				custId = gedAccountService.getLHCustIdByContract(contractNo);
			}else if (Constants.PROC_DEF_KEY_GR.equals(applyRegister.getProcDefKey())) {
				custId = gedAccountService.getCustByCompany(contractNo);
			}
		}


		compensatoryDetail.setSurplusAmount(new BigDecimal("0.00"));
		compensatoryDetail.setToCompensatoryAccount(custId);
		accAccountDCRequest.setMchn(Global.getConfig("FPMchn"));
		accAccountDCRequest.setSeq_no(compensatoryDetail.getSerialNum());
		accAccountDCRequest.setTrade_type("11081003");
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("contractNo",contractNo);
		dataMap.put("from_cust_type",0);//
		dataMap.put("to_cust_type",1);//
		dataMap.put("remark","");
		dataMap.put("from_cust_id",custId);
		dataMap.put("to_cust_id",compensatoryDetail.getCustId());
		dataMap.put("amount",staRepayPlanStatus);
		String  date =null;
		date = JsonTransferUtils.bean2Json(dataMap);
		accAccountDCRequest.setData(date);
		accAccountDCRequest.setBack_url(Global.getConfig("Domain")+"/f/rest/funds/service/capitalTransferBack");
		accAccountDCRequest.setPage_url("");
		String sign = Global.getConfig("FPMchn")+"|"+accAccountDCRequest.getSeq_no()+"|"+accAccountDCRequest.getTrade_type()+"|"+date+"|"+accAccountDCRequest.getPage_url()+"|"+accAccountDCRequest.getBack_url();
		accAccountDCRequest.setSignature(RSAUtils.signCallBack(sign.getBytes(),Global.getConfig("sysprkey")));
		accAccountDCResponse.setData(JsonTransferUtils.bean2Json(new ZjResoonseInfo()));

		accAccountDCResponse = AccFacade.facade.repaymentDC(accAccountDCRequest,contractNo,"0");
		if("0000".equals(accAccountDCResponse.getResp_code())) {
			compensatoryDetail.setCompensatoryStatus("2");//2代偿处理中
		}else {
			compensatoryDetail.setCompensatoryStatus("1");
			compensatoryDetail.setFailReason(accAccountDCResponse.getResp_msg());
		}
		if (custId == null) {
			compensatoryDetail.setFailReason(accAccountDCResponse.getResp_msg()+contract.getRepBankcardName()+"没有开户");
		}

		return accAccountDCResponse;
	}

	/**
	 * 更新代偿状态
	 * @param seqNo
	 */
	@Transactional(readOnly = false)
	public void updateCompensatoryStatusBySeqNum(String seqNo){
		dao.updateCompensatoryStatusBySeqNum(seqNo);
	}

	/**
	 * 通过流水号查询代偿信息
	 * @param seqNo
	 * @return
	 */
	public CompensatoryDetail queryCompenMoneyBySeqNo(String seqNo,String flag){
		return dao.queryCompenMoneyBySeqNo(seqNo,flag);
	}

	@Transactional(readOnly = false)
	public void updateCompensatoryInfoBySeqNum(String seqNum,String seqNo){
		dao.updateCompensatoryInfoBySeqNum(seqNum,seqNo);
	}
}
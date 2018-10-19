package com.resoft.accounting.contract.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractImport;
import com.resoft.accounting.contract.web.ExcelUtil;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.outinterface.rest.ged.entity.ContractRepayPlanDetail;
import com.resoft.outinterface.rest.ged.entity.RepayPalnDetail;
import com.resoft.outinterface.rest.ged.entity.RepayPlanInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;


/**
 * 合同信息Service
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class ContractService extends CrudService<AccContractDao, Contract> {

	private static final Logger logger = Logger.getLogger(ContractService.class);
	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;
	public Contract get(String id) {
		return super.get(id);
	}

	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}

	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(Contract contract) {
		super.save(contract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public Contract findContractByContractNo(String contractNo) {
		return this.dao.findContractByContractNo(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public Contract findContractInfoByContractNo(String contractNo) {
		return this.dao.findContractInfoByContractNo(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void insert(Contract contract) {
		super.dao.insert(contract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void update(Contract contract) {
		super.dao.update(contract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public int importContract(ContractImport contractTmp) {
		return super.dao.importContract(contractTmp);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void updateImportedContract(ContractImport contractTmp) {
		super.dao.updateImportedContract(contractTmp);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String importData(MultipartFile file) {
		String errerMessage = null;
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			logger.error("获取文件输入流失败", e);
			errerMessage = e.getMessage();
			return errerMessage;
		}
		String fileName = file.getOriginalFilename();
		ExcelUtil excelUtil = new ExcelUtil();
		ContractImport contractTmp = new ContractImport();
		String operateId = UserUtils.getUser().getOffice().getId();
		List<Object> contractTmps = excelUtil.importDataFromExcel(contractTmp, is, fileName, "contractImport");
		if (contractTmps != null && contractTmps.size() != 0) {
			for (int i = 0; i < contractTmps.size(); i++) {
				ContractImport tmp = (ContractImport) contractTmps.get(i);
				tmp.setOperateOrgId(operateId);
				String idNum = tmp.getIdNum();
			 	 if (idNum != null) {
					tmp.setIdNum(idNum.replace(" ", ""));
					if (StringUtils.isNull(idNum) || idNum.length() != 18) {
						return "合同：" + tmp.getContractNo() + "身份证信息有误";
					}
				}
			}
			for (int i = 0; i < contractTmps.size(); i++) {
				ContractImport tmp = (ContractImport) contractTmps.get(i);
				try {
					boolean flag = dealContractData(tmp);
					if (flag) {
						importContract(tmp);
					} else {
						updateImportedContract(tmp);
					}
					
				} catch (Exception e) {
					logger.error("合同导入数据库失败", e);
					errerMessage = "合同号为" + tmp.getContractNo() + "的数据出现问题,已导入成功" + i + "条";
					return errerMessage;
				}
			}
		} else {
			errerMessage = "导入出现问题，请仔细核对规范，若再次出现请联系管理员";
		}
		return errerMessage;
	}

	boolean dealContractData(ContractImport tmp) {
		boolean flag = false;
		Contract contract = findContractInfoByContractNo(tmp.getContractNo());
		if (contract == null) {
			flag = true;
		}
		return flag;
	}
	
	
	public BigDecimal queryContractMoney(List<String> contracts){
		BigDecimal contractsMoney = new BigDecimal("0.00");
		for(String contract :contracts){
		Contract	contractMoney = this.dao.queryContractMoney(contract);
		if (contractMoney != null) {
			contractsMoney = contractsMoney.add(contractMoney.getContractAmount());
			}		
		}
		return contractsMoney;
	}
	

	public RepayPlanInfo findContractSituation(List<String> contractNos){
		List<ContractRepayPlanDetail> contractRepayPlanDetails = new ArrayList<>();
		RepayPlanInfo repayPlanStayResponse = new RepayPlanInfo();
		BigDecimal stayTotalMoney = new BigDecimal("0.00");
		BigDecimal stayOverdueTotalMoney = new BigDecimal("0.00");
		BigDecimal stayFineTotalAmount = new BigDecimal("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(String contract:contractNos){
			Contract contractSituation = this.dao.findContractSituation(contract);
			if (contractSituation != null) {
				ContractRepayPlanDetail contractRepayPlanDetail = new ContractRepayPlanDetail();
				contractRepayPlanDetail.setContractMoney(contractSituation.getContractAmount().toString());
				contractRepayPlanDetail.setContractNo(contractSituation.getContractNo());
				contractRepayPlanDetail.setLoanDate(subDay(contractSituation.getLoanDate()));
				contractRepayPlanDetail.setPeroidValue(contractSituation.getApproPeriodValue());
				BigDecimal contractStayMoney = new BigDecimal("0.00");
				BigDecimal contractStayOverdueMoney = new BigDecimal("0.00");
				BigDecimal contractStayFineAmount = new BigDecimal("0.00");
				BigDecimal contractService = new BigDecimal("0.00");
				BigDecimal contractManage = new BigDecimal("0.00");
				BigDecimal contractRepayAmount = new BigDecimal("0.00");
				BigDecimal contractFactRepayAmount = new BigDecimal("0.00");
				List<RepayPalnDetail>  repayPalnDetails = staRepayPlanStatusService.findRepayPlanDetailByContract(contract);
				contractRepayPlanDetail.setRepayPalnDetails(repayPalnDetails);
				for(RepayPalnDetail repayPalnDetail :repayPalnDetails){
				contractStayMoney = contractStayMoney.add(new BigDecimal(repayPalnDetail.getDiscountStayMoney()));
				contractStayOverdueMoney = contractStayOverdueMoney.add(new BigDecimal(repayPalnDetail.getOverdueMoney()));
				contractStayFineAmount = contractStayFineAmount.add(new BigDecimal(repayPalnDetail.getFineMoney()));
				contractService = contractService.add(new BigDecimal(repayPalnDetail.getServiceFee()));
				contractManage = contractService.add(new BigDecimal(repayPalnDetail.getManageFee()));
				contractRepayAmount = contractRepayAmount.add(new BigDecimal(repayPalnDetail.getRepayAmount()));
				contractFactRepayAmount = contractFactRepayAmount.add(new BigDecimal(repayPalnDetail.getFactRepayAmount()));
				}
				contractRepayPlanDetail.setContractStayMoney(contractStayMoney.toString());
				contractRepayPlanDetail.setContractStayOverdueMoney(contractStayOverdueMoney.toString());
				contractRepayPlanDetail.setContractStayFine(contractStayFineAmount.toString());
				contractRepayPlanDetail.setContractNoStayMoney(contractStayMoney.toString());
				contractRepayPlanDetail.setContractManage(contractManage.toString());
				contractRepayPlanDetail.setContractService(contractService.toString());
				contractRepayPlanDetail.setContractRepayAmount(contractRepayAmount.toString());
				contractRepayPlanDetail.setContractFactRepayAmount(contractFactRepayAmount.toString());
				contractRepayPlanDetails.add(contractRepayPlanDetail);
			}	
		}	
		if (contractRepayPlanDetails.size() >0) {
			for(ContractRepayPlanDetail contractRepayPlanDetail:contractRepayPlanDetails){
				stayTotalMoney = stayTotalMoney.add(new BigDecimal(contractRepayPlanDetail.getContractStayMoney()));
				stayOverdueTotalMoney = stayOverdueTotalMoney.add(new BigDecimal(contractRepayPlanDetail.getContractStayOverdueMoney()));
				stayFineTotalAmount = stayFineTotalAmount.add(new BigDecimal(contractRepayPlanDetail.getContractStayFine()));
			}
		}
		repayPlanStayResponse.setStayFineAmount(stayFineTotalAmount.toString());
		repayPlanStayResponse.setStayOverdueMoney(stayOverdueTotalMoney.toString());
		repayPlanStayResponse.setStayTotalMoney(stayTotalMoney.toString());
		repayPlanStayResponse.setData(contractRepayPlanDetails);
		return repayPlanStayResponse;
	}
	
	
	 public  String subDay(Date date){
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	     Calendar rightNow = Calendar.getInstance();  
	     rightNow.setTime(date);  
	     rightNow.add(Calendar.DAY_OF_MONTH, -1);  
	     Date dt1 = rightNow.getTime();  
	     String reStr = sdf.format(dt1);  
	     return reStr;  
	 }
	
}
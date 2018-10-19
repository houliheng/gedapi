package com.resoft.accounting.staRepayPlanStatus.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.advanceGed.entity.AccAdvanceGed;
import com.resoft.accounting.advanceGed.service.AccAdvanceGedService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.deductResult.service.DeductResultService;
import com.resoft.accounting.staRepayPlanStatus.dao.StaRepayPlanStatusDao;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.credit.guranteeProjectList.entity.RepayPlanDetail;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.resoft.outinterface.rest.ged.entity.RepayPalnDetail;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 账务调整Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class StaRepayPlanStatusService extends CrudService<StaRepayPlanStatusDao, StaRepayPlanStatus> {
	private static final Logger logger = LoggerFactory.getLogger(StaRepayPlanStatusService.class);

	@Autowired
	private ContractLockDao contractLockDao;

	@Autowired
	private DeductResultService deductResultService;

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");
	@Autowired
	private AccAdvanceGedService accAdvanceGedService;
	public StaRepayPlanStatus get(String id) {
		return super.get(id);
	}

	@SuppressWarnings("deprecation")
	public List<StaRepayPlanStatus> findAllList() {
		return this.dao.findAllList();
	}

	public List<StaRepayPlanStatus> findList(StaRepayPlanStatus staRepayPlanStatus) {
		return super.findList(staRepayPlanStatus);
	}

	public Page<StaRepayPlanStatus> findPage(Page<StaRepayPlanStatus> page, StaRepayPlanStatus staRepayPlanStatus) {
		return super.findPage(page, staRepayPlanStatus);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(StaRepayPlanStatus staRepayPlanStatus) {
		super.save(staRepayPlanStatus);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(StaRepayPlanStatus staRepayPlanStatus) {
		super.delete(staRepayPlanStatus);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<StaRepayPlanStatus> findStaRepayPlanStatus(String contractNo) {
		return this.dao.findStaRepayPlanStatus(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<Map<String, String>> findStaRepayPlanStatusData(String contractNo) {
		return this.dao.findStaRepayPlanStatusData(contractNo);
	}

	/**
	 * 根据合同号和期数查询期状态
	 * 
	 * @param deductApply
	 * @return
	 */
	@Transactional(value = "ACC", readOnly = false)
	public String getPreviousStageStatusByByContractNoAndPeriodNum(DeductApply deductApply) {
		return this.dao.getPreviousStageStatusByByContractNoAndPeriodNum(deductApply);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void saveAccApplyChangeRepay(StaRepayPlanStatus staRepayPlanStatus) {
		this.dao.saveAccApplyChangeRepay(staRepayPlanStatus);
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView saveaccApplyChangeRepays(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：合同号
			logger.info("开始调用存储：SP_RUN_ACC_CHANGE,参数为：合同号  " + contractNo);
			CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_CHANGE('" + contractNo + "')}");
			callableStatement.execute();
			ajaxView.setSuccess().setMessage("账务调整操作成功！");
		} catch (SQLException e) {
			ajaxView.setFailed().setMessage("账务调整入账失败！");
			logger.error("SP_RUN_ACC_CHANGE调用失败！", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("账务调整，资源释放失败", e);
			}
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView savePZ(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：合同号
			logger.info("开始调用存储：SP_RUN_ACC_CHANGE_PZ,参数为：数据日期（当前日期）合同号  " + contractNo);
			CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_CHANGE_PZ('" + DateUtils.formatDate(new Date()) + "','" + contractNo + "')}");
			callableStatement.execute();
			ajaxView.setSuccess().setMessage("账务调整操作成功！");
		} catch (SQLException e) {
			ajaxView.setFailed().setMessage("账务调整入账失败！");
			logger.error("SP_RUN_ACC_CHANGE_PZ调用失败！", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("账务调整，资源释放失败", e);
			}
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView validateIdLockToFineExempt(String contractNo, boolean pzFlag) {
		AjaxView ajaxView = new AjaxView();
		ContractLock contractLock1 = null;
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(contractNo);
		contractLock1 = contractLockDao.validateIsLock(contractLock);
		if (contractLock1 != null) {
			if (Constants.CONTRACT_LOCK_FLAG_ZWTZ.equals(contractLock1.getLockFlag())) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("正在跑批中，请稍后操作。");
			}
		} else {
			contractLock.setLockFlag(Constants.CONTRACT_LOCK_FLAG_ZWTZ);
			contractLock1 = contractLockDao.validateIsLock(contractLock);
			if (contractLock1 != null) {
				ajaxView.setSuccess();
			} else {
				contractLockDao.saveLockInfo(contractLock);
				if (pzFlag) {
					ajaxView.setSuccess();
				} else {
					Connection connection = null;
					try {
						connection = dataSource.getConnection();
						// 存储过程 传参数 ：合同号
						logger.info("调用存储：SP_RUN_ACC_CHANGE_OVERDUE,参数为：合同号 " + contractNo);
						CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_CHANGE_OVERDUE('" + contractNo + "')}");
						callableStatement.execute();
						ajaxView.setSuccess();
					} catch (SQLException e) {
						ajaxView.setFailed().setMessage("生成罚息失败。");
						logger.error("生成罚息失败。", e);
					} finally {
						try {
							connection.close();
						} catch (SQLException e) {
							logger.error("生成罚息，资源释放失败", e);
							ajaxView.setFailed().setMessage("生成罚息失败。");
						}
					}
				}
			}
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView deleteLock(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：合同号
			logger.info("调用存储：SP_ACC_CLOSE_ADJUST,参数为：合同号 " + contractNo);
			CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_CLOSE_ADJUST('" + contractNo + "')}");
			callableStatement.execute();
			ajaxView.setSuccess();
		} catch (SQLException e) {
			ajaxView.setFailed().setMessage("操作失败。");
			logger.error("操作失败。", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("终止入账，释放资源失败", e);
				ajaxView.setFailed().setMessage("终止入账操作失败。");
			}
		}
		return ajaxView;
	}

	@Transactional(value = "ACC", readOnly = false)
	public boolean prepareAmount(List<StaRepayPlanStatus> staRepayPlanStatus, String contractNo) {
		boolean flag = false;
		Double sum = 0d;
		String num = "";
		try {
			num = deductResultService.getSumDeductAmount(contractNo);
			if (StringUtils.isBlank(num)) {
				num = deductResultService.getSumDeductAmountInChangeResult(contractNo);
				if (StringUtils.isBlank(num)) {
					num = "0";
				}
			}
			for (StaRepayPlanStatus planStatus : staRepayPlanStatus) {
				if (planStatus.getFactServiceFee() != "" && planStatus.getFactCapitalAmount() != "" && planStatus.getFactFineAmount() != "" && planStatus.getFactInterestAmount() != "" && planStatus.getFactMangementFee() != "" && planStatus.getFactAddAmount() != "" && planStatus.getBackAmount() != "") {
					sum += (Double.parseDouble(planStatus.getFactServiceFee()) + Double.parseDouble(planStatus.getFactCapitalAmount()) + Double.parseDouble(planStatus.getFactFineAmount()) + Double.parseDouble(planStatus.getFactInterestAmount()) + Double.parseDouble(planStatus.getFactAddAmount()) + Double.parseDouble(planStatus.getFactMangementFee()) + Double.parseDouble(planStatus.getFactPenaltyAmount()) + Double.parseDouble(planStatus.getBackAmount()));
				} else {
					return flag;
				}
			}

			if (Double.parseDouble(num) == sum) {
				flag = true;
			}
		} catch (NumberFormatException e) {
			logger.error("数值为空。", e);
		}
		return flag;
	}

	@Transactional(value = "ACC", readOnly = false)
	public boolean getSumRepayAmount(String contractNo) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：合同号
			logger.info("调用存储：SP_ACC_CHANGE_RESULT,参数为：合同号 " + contractNo + " ,当前时间 " + DateUtils.formatDate(new Date()));
			CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_CHANGE_RESULT('" + DateUtils.formatDate(new Date()) + "','" + contractNo + "')}");
			callableStatement.execute();
			flag = true;
		} catch (SQLException e) {
			logger.error("SP_ACC_CHANGE_RESULT存储调用失败。", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("SP_ACC_CHANGE_RESULT存储关闭失败。", e);
			}
		}
		return flag;

	}
	
	/**
	 * 查询入账后期数状态的变化
	 * @param param
	 * @return
	 */
	public List<StaRepayPlanStatus> findUpdateRepayPeroidStatus(Map<String,String> param){
		return this.dao.findUpdateRepayPeroidStatus(param);
	}
	
	
	public List<StaRepayPlanStatus> getPeriod(Map<String,String> param){
		return this.dao.getPeriod(param);
	}
	
	/**
	 * 通过合同号查询还款计划
	 * @param contractNo
	 * @return
	 */
	public StaRepayPlanStatus findStatusByContractNo(String contractNo){
		return this.dao.findStatusByContractNo(contractNo);
	}
	
	
	public BigDecimal queryContractStayMoney(List<String> contractNos){
		BigDecimal contractStatMoney = new BigDecimal("0.00");
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		Map<String,String> param = new HashMap<>();
		param.put("deductDate",date);
		for(String contractNo :contractNos){
			param.put("contractNo",contractNo);
			findRepayPlanDetailByContract(contractNo);
			BigDecimal contractStayMoney = this.dao.queryContractStayMoney(param);
			contractStatMoney = contractStatMoney.add(contractStayMoney);
		}
		return contractStatMoney;
	}
	
	public List<RepayPalnDetail> findRepayPlanDetailByContract(String contractNo){
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		Map<String,String> param = new HashMap<>();
		param.put("deductDate",date);
		param.put("contractNo",contractNo);
		List<RepayPalnDetail> repayPalnDetails = new ArrayList<>();
		List<StaRepayPlanStatus> staRepayPlanStatusList = this.dao.findRepayPlanDetailByContract(param);
		StaRepayPlanStatus staRepayPlanStatusMax = this.dao.findMaxDateByContract(contractNo);
		List<StaRepayPlanStatus> filterStaRepayPlanStatusList = new ArrayList<>();
		String maxDate = "";
		if (staRepayPlanStatusMax != null && StringUtils.isNotBlank(staRepayPlanStatusMax.getDataDt())) {
			maxDate = staRepayPlanStatusMax.getDataDt();
		}
		if (staRepayPlanStatusList.size() >0) {
			for(int i=0;i<staRepayPlanStatusList.size();i++){
				if ((StringUtils.isNotEmpty(maxDate) && date.compareTo(maxDate) <= 0 && "0.00".equals(staRepayPlanStatusList.get(i).getFactFineAmount()))) {
					
				}else if(staRepayPlanStatusList.get(i).getFactCapitalAmount().contains("-")){
					
				}else{
					if ("0.00".equals(staRepayPlanStatusList.get(i).getFactFineAmount())) {
						if (staRepayPlanStatusList.get(i).getRepayPeriodStatus() == null ) {
							staRepayPlanStatusList.get(i).setRepayPeriodStatus("0200");
						}
						filterStaRepayPlanStatusList.add(staRepayPlanStatusList.get(i));
						break;
					}
					if (!"0.00".equals(staRepayPlanStatusList.get(i).getFactFineAmount())) {
						if (staRepayPlanStatusList.get(i).getRepayPeriodStatus() == null) {
							staRepayPlanStatusList.get(i).setRepayPeriodStatus("0300");
						}
						filterStaRepayPlanStatusList.add(staRepayPlanStatusList.get(i));
					}
					
					
				}
				
				
			}
			if (filterStaRepayPlanStatusList.size() > 0) {
				for(StaRepayPlanStatus staRepayPlanStatus:filterStaRepayPlanStatusList){
					RepayPalnDetail repayPalnDetail = new RepayPalnDetail();
					if (staRepayPlanStatus != null) {
						repayPalnDetail.setDeductDate(staRepayPlanStatus.getDataDt());
						repayPalnDetail.setPeriodNum(staRepayPlanStatus.getPeriodNum());
						repayPalnDetail.setRepayStatus(staRepayPlanStatus.getRepayPeriodStatus());
						repayPalnDetail.setStayMoney((new BigDecimal(staRepayPlanStatus.getFactCapitalAmount()).add(new BigDecimal(staRepayPlanStatus.getFactAddAmount()))).toString());
						repayPalnDetail.setOverdueMoney(staRepayPlanStatus.getFactPenaltyAmount());
						repayPalnDetail.setFineMoney(staRepayPlanStatus.getFactFineAmount());
						repayPalnDetail.setManageFee(staRepayPlanStatus.getFactMangementFee());
						repayPalnDetail.setServiceFee(staRepayPlanStatus.getFactServiceFee());
						repayPalnDetail.setRepayPrincipal(staRepayPlanStatus.getFactInterestAmount());
						if (staRepayPlanStatus.getFactCapitalAmount().contains("-")) {
							repayPalnDetail.setDiscountStayMoney("0.00");
						}else{
							repayPalnDetail.setDiscountStayMoney(staRepayPlanStatus.getFactCapitalAmount());
						}
						repayPalnDetail.setRepayAmount(staRepayPlanStatus.getBackAmount());
						repayPalnDetail.setFactRepayAmount(staRepayPlanStatus.getFineEepmtAmount());
						repayPalnDetails.add(repayPalnDetail);
					}
				}
			}
		}
		return repayPalnDetails;
	}
	
	
	public List<RepayPlanDetail> findContractRepayDetail(String contractNo){
		return this.dao.findContractRepayDetail(contractNo);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void updateAdvancePeriodNum(Map<String,String> param){
		this.dao.updateAdvancePeriodNum(param);
	}
	
	public List<StaRepayPlanStatus> queryAccountInfo(GedRepayment gedRepayment){
		return this.dao.queryAccountInfo(gedRepayment);
	}
	
	public StaRepayPlanStatus getStaByContractNoAndPeriodNum(String contractNo,String periodNum){
		return this.dao.getStaByContractNoAndPeriodNum(contractNo,periodNum);
	};
	
	@Transactional(value = "ACC", readOnly = false)
	public void saveUpdate(List<StaRepayPlanStatus> staRepayPlanStatus,String contractNo){
		StaRepayPlanStatus staRepayPlan = new StaRepayPlanStatus();
		staRepayPlan.setContractNo(contractNo);
		for(StaRepayPlanStatus staRepay:staRepayPlanStatus){
			this.dao.deleteStaRepay(staRepay.getContractNo(),staRepay.getPeriodNum());
			this.dao.add(staRepay);
			AccAdvanceGed accAdvanceGed = new AccAdvanceGed();
			accAdvanceGed.setContractNo(staRepay.getContractNo());
			accAdvanceGed.setPeriodNum(staRepay.getPeriodNum());
			if ("0500".equals(staRepay.getRepayPeriodStatus())) {
				accAdvanceGed.setAdvanceFlag("1");//提前还款标识
			}else if ("0400".equals(staRepay.getRepayPeriodStatus())) {
				accAdvanceGed.setAdvanceFlag("0");//正常待还
			}else if ("0100".equals(staRepay.getRepayPeriodStatus())) {
				accAdvanceGed.setAdvanceFlag("2");//逾期待还
			}
			accAdvanceGed.setDataDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			accAdvanceGedService.save(accAdvanceGed);
		}
	}

	public List<StaRepayPlanStatus> listWithContracts(List<String> contractNoList) {
		return this.dao.listWithContractNoList(contractNoList);
	}
	
	public BigDecimal getFactMoneyByContractAndPeriodNum(String contractNo,String periodNum){
		return dao.getFactMoneyByContractAndPeriodNum(contractNo,periodNum);
	}
}
package com.resoft.accounting.deductResult.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.resoft.accounting.deductApply.service.DeductApplyService;
import com.resoft.accounting.deductApply.service.DeductApplyVOService;
import com.resoft.accounting.deductResult.dao.DeductResultDao;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 补录流水Service
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class DeductResultService extends CrudService<DeductResultDao, DeductResult> {

	@Autowired
	private DeductApplyService deductApplyService;
	@Autowired
	private DeductApplyVOService deductApplyVOService;
	@Autowired
	private AccContractDao accContractDao;
	@Autowired
	private ContractLockDao contractLockDao;

	public DeductResult get(String id) {
		return super.get(id);
	}

	public List<DeductResult> findList(DeductResult deductResult) {
		return super.findList(deductResult);
	}

	public Page<DeductResult> findPage(Page<DeductResult> page, DeductResult deductResult) {
		return super.findPage(page, deductResult);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(DeductResult deductResult) {
		super.save(deductResult);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(DeductResult deductResult) {
		super.delete(deductResult);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String getSumDeductAmount(String contractNo) {
		return this.dao.getSumDeductAmount(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public String getSumDeductAmountInChangeResult(String contractNo) {
		return this.dao.getSumDeductAmountInChangeResult(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<DeductResult> validateStreamNo(DeductResult deductResult) {
		return this.dao.validateStreamNo(deductResult);

	}

	@Transactional(value = "ACC", readOnly = false)
	public void deleteData(DeductResult deductResult) {
		this.dao.deleteData(deductResult);
	}

	@Transactional(value = "ACC", readOnly = false)
	public DeductApplyVO saveDeductApplyAndDeductApplyVO(DeductResult deductResult, Contract contract) {
		DeductApply deductApply = saveDeductApply(deductResult, contract);
		DeductApplyVO deductApplyVO = saveDeductApplyVO(deductApply, deductResult, contract);
		return deductApplyVO;
	}

	@Transactional(value = "ACC", readOnly = false)
	public AjaxView supplementAccount(List<DeductResult> list,String flag,DruidDataSource dataSource,AjaxView ajaxView) throws Exception {
		
		for(int i=0;i<list.size();i++){
			Contract contract = accContractDao.findContractByContractNo(list.get(i).getContractNo());
			if (contract != null) {
				if ("1".equals(flag)) {
					ContractLock contractLock = new ContractLock();
					contractLock.setContractNo(list.get(i).getContractNo());
					contractLock = contractLockDao.validateIsLock(contractLock);
					if (contractLock == null) {
						DeductApplyVO deductApplyVO = saveDeductApplyAndDeductApplyVO(list.get(i), contract);
						// 调用存储
						Connection connection = null;
						try {
							connection = dataSource.getConnection();
							CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT_OUT(?,?,?)}");
							callableStatement.setString(1, DateUtils.dateToTimeString(list.get(i).getEntryTime()));
							callableStatement.setString(2, deductApplyVO.getSysSeqNo());
							callableStatement.registerOutParameter(3, java.sql.Types.CHAR);
							callableStatement.execute();
							String returnCount = callableStatement.getString(3);
							if(!"0".equals(returnCount)){
								ajaxView.setFailed().setMessage("补录流水失败！请检查第" + (i+1) + "行");
							}
							ajaxView.put("flag", flag);
						} catch (SQLException e) {
							ajaxView.setFailed().setMessage("存储调用失败！请检查第" + (i+1) + "行");
							throw e;
						} finally {
							connection.close();
						}
					} else {
						ajaxView.setFailed().setMessage("此合同正在进行其他操作！"+ contract.getContractNo());
						throw new Exception("此合同正在进行其他操作！");
					}
				} else {
					list.get(i).setIsLock("1");
					save(list.get(i));
				}
			} else {
				ajaxView.setFailed().setMessage("合同信息查询出错，请联系管理员！");
				throw new Exception("合同信息查询出错，请联系管理员！");
			}
		}
		return ajaxView;
	}
	
	// 保存信息到划扣申请表
	public DeductApply saveDeductApply(DeductResult deductResult, Contract contract) {
		DeductApply deductApply = new DeductApply();
		Integer a = (int) (Math.random()*10000000) ;
		Long b = System.currentTimeMillis();
		String deductApplyNo = Integer.toString(a)+b;
		deductApply.setDataDt(DateUtils.formatDate(deductResult.getEntryTime()));
		deductApply.setDeductCustId(UserUtils.getUser().getId());
		deductApply.setDeductAmount(deductResult.getDeductAmount());
		deductApply.setContractNo(deductResult.getContractNo());
		deductApply.setDeductApplyNo(deductApplyNo);
		deductApply.setCapitalTerraceNo(contract.getCapitalTerraceNo());
		deductApply.setDeductTime(DateUtils.formatDateTime(deductResult.getEntryTime()));
		deductApply.setIsLock("1");
		deductApplyService.saveDeductApplyToDeductResult(deductApply);
		return deductApply;
	}

	// 保存信息到划扣结果表
	public DeductApplyVO saveDeductApplyVO(DeductApply deductApply, DeductResult deductResult, Contract contract) {
		DeductApplyVO deductApplyVO = new DeductApplyVO();
		deductApplyVO.setDataDt(DateUtils.formatDate(deductResult.getEntryTime()));
		deductApplyVO.setCapitalTerraceNo(contract.getCapitalTerraceNo());
		deductApplyVO.setContractNo(deductResult.getContractNo());
		deductApplyVO.setSysSeqNo(IdGen.uuid());
		deductApplyVO.setDeductApplyNo(deductApply.getDeductApplyNo());
		deductApplyVO.setStreamNo(deductResult.getStreamNo());
		deductApplyVO.setStreamTime(deductResult.getEntryTime());
		deductApplyVO.setDeductAmountResult(deductResult.getDeductAmount());
		deductApplyVO.setDeductCustId(UserUtils.getUser().getId());
		deductApplyVO.setDescription(deductResult.getDescription());
		deductApplyVO.setDeductResult("1");
		deductApplyVO.setIsLock("1");
		deductApplyVOService.save(deductApplyVO);
		return deductApplyVO;
	}

	public AjaxView validateStreamNoBatch(DeductResult deductResult,AjaxView ajaxView) {
		try {
			String streamNo = URLDecoder.decode(deductResult.getStreamNo(), "UTF-8");
			deductResult.setStreamNo(streamNo);
			List<DeductResult> deductResultTmps = validateStreamNo(deductResult);
			if (deductResultTmps.size() != 0) {
				for (DeductResult deductResultTmp : deductResultTmps) {
					if (deductResultTmp != null) {
						ajaxView.setFailed().setMessage("此流水号已存在，请重新输入！");
						return ajaxView;
					} else {
						ajaxView.setSuccess();
					}
				}
			} else {
				ajaxView.setSuccess();
			}
		} catch (UnsupportedEncodingException e) {
			ajaxView.setFailed().setMessage("汉字转码失败");
		}
		return ajaxView;
	}
}
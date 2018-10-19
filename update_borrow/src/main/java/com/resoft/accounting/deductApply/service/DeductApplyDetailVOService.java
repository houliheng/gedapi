package com.resoft.accounting.deductApply.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.deductApply.dao.DeductApplyDao;
import com.resoft.accounting.deductApply.dao.DeductApplyDetailVODao;
import com.resoft.accounting.deductApply.dao.DeductApplyVODao;
import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

/**
 * 划扣入账明细查询Service
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class DeductApplyDetailVOService extends CrudService<DeductApplyDetailVODao, DeductApplyVO> {

	private static final Logger logger = LoggerFactory.getLogger(DeductApplyDetailVOService.class);
	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");
	@Autowired
	private DeductApplyVODao deductApplyVODao;

	@Autowired
	private DeductApplyDao deductApplyDao;

	public DeductApplyVO get(String id) {
		return super.get(id);
	}

	public List<DeductApplyVO> findList(DeductApplyVO deductApplyVO) {
		return super.findList(deductApplyVO);
	}

	public Page<DeductApplyVO> findPage(Page<DeductApplyVO> page, DeductApplyVO deductApplyVO) {
		return super.findPage(page, deductApplyVO);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(DeductApplyVO deductApplyVO) {
		super.save(deductApplyVO);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(DeductApplyVO deductApplyVO) {
		super.delete(deductApplyVO);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void recordAgain(DeductApplyVO deductApplyVO) {
		deductApplyDao.updateDeductApplyIsLock(deductApplyVO.getDeductApplyNo());
		deductApplyVODao.updateDeductResultIsLock(deductApplyVO.getDeductApplyNo());
	}

	/**
	 * 入账成功查询
	 */
	public Page<DeductApplyVO> getDeductApplyVOForSuccess(Page<DeductApplyVO> page, DeductApplyVO deductApplyVO) {
		deductApplyVO.setPage(page);
		page.setList(dao.getDeductApplyVOForSuccess(deductApplyVO));
		return page;
	}

	/**
	 * 入账失败查询
	 */
	public Page<DeductApplyVO> getDeductApplyVOForFail(Page<DeductApplyVO> page, DeductApplyVO deductApplyVO) {
		deductApplyVO.setPage(page);
		page.setList(dao.getDeductApplyVOForFail(deductApplyVO));
		return page;
	}

	/**
	 * 获取指定还款明细数据
	 */
	public List<DeductApplyVO> getDeductAmountList(DeductApplyVO applyVO) {
		return this.dao.getDeductAmountList(applyVO);
	}

	public List<Map<String, Object>> getRepayDetailData(Map<String, Object> params) {
		return this.dao.getRepayDetailData(params);
	}

	/**
	 * 重新匹配
	 */
	@Transactional(value = "ACC", readOnly = false)
	public String matchAgain(List<DeductApplyVO> deductApplyVOs, String amountSumStr, String contractNoClick,String streamTimeStrClick,String deductApplyNoClick) {
		String message = "";
		try {
			BigDecimal amountSumOld = new BigDecimal(amountSumStr);
			BigDecimal amountSumNow = new BigDecimal(0);
			for (DeductApplyVO deductApplyVO : deductApplyVOs) {
				deductApplyVO.preInsert();
				if (deductApplyVO.getRepayCapitalAmount() != "" && deductApplyVO.getRepayInterestAmount() != "" && deductApplyVO.getRepayManagementFee() != "" && deductApplyVO.getRepayServiceFee() != "" && deductApplyVO.getRepayFineAmount() != "" && deductApplyVO.getRepayPenaltyAmount() != "" && deductApplyVO.getRepayAddAmount() != "" && deductApplyVO.getRepayBreakAmount() != "" && deductApplyVO.getBackAmount() != "") {
					amountSumNow = amountSumNow.add(new BigDecimal(deductApplyVO.getRepayInterestAmount()).add(new BigDecimal(deductApplyVO.getRepayManagementFee()).add(new BigDecimal(deductApplyVO.getRepayServiceFee()).add(new BigDecimal(deductApplyVO.getRepayFineAmount()).add(new BigDecimal(deductApplyVO.getRepayPenaltyAmount()).add(new BigDecimal(deductApplyVO.getRepayAddAmount()).add(new BigDecimal(deductApplyVO.getRepayCapitalAmount()).add(new BigDecimal(deductApplyVO.getBackAmount())))))))));
				} else {
					message = "请确认数据的正确性";
					return message;
				}
			}
			if (amountSumOld.compareTo(amountSumNow) == 0) {
				Connection connection = null;
				try {
					connection = dataSource.getConnection();
					// 存储过程 传参数 ：数据日期,合同号,流水号
					logger.info("开始调用存储： SP_ACC_CHANGE_DETAIL_STRIKE,参数为：数据日期（当前日期）,选中的流水日期，合同号  " + contractNoClick + "划扣申请号：" + deductApplyNoClick);
					CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_CHANGE_DETAIL_STRIKE(?,?,?,?,?)}");
					callableStatement.setString(1, DateUtils.dateToTimeString(new Date()));
					callableStatement.setString(2, streamTimeStrClick);
					callableStatement.setString(3, contractNoClick);
					callableStatement.setString(4, deductApplyNoClick);
					callableStatement.registerOutParameter(5, java.sql.Types.CHAR);
					callableStatement.executeQuery();
					String a = callableStatement.getString(5);
					if ("1".equals(a)) {
						try {
							this.dao.saveDataInRepayDetail(deductApplyVOs);
						} catch (Exception e) {
							logger.error("acc_repay_detail保存数据失败！", e);
							message = "重新匹配失败";
						}
					}
				} catch (SQLException e) {
					logger.error("调用存储SP_ACC_CHANGE_DETAIL_STRIKE失败！", e);
					message = "重新匹配失败";
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error("用存储SP_ACC_CHANGE_DETAIL_STRIKE,关闭连接失败", e);
						message = "重新匹配失败";
					}
				}
				message = "true";
			} else {
				message = "金额匹配不成功";
			}
		} catch (Exception e) {
			logger.error("重新匹配失败", e);
			message = "重新匹配失败";
		}
		return message;
	}

	/**
	 * 根据deductapplyNo查询选中数据的总金额
	 */
	public List<String> getDeductAmountByDeductAppyNo(String deductApplyNo){
		return this.dao.getDeductAmountByDeductAppyNo(deductApplyNo);
	}
	
}
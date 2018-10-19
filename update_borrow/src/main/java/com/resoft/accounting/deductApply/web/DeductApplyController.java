package com.resoft.accounting.deductApply.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.contract.service.ContractLockService;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.resoft.accounting.deductApply.service.DeductApplyDetailVOService;
import com.resoft.accounting.deductApply.service.DeductApplyService;
import com.resoft.accounting.deductApply.service.DeductApplyVOService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 还款划扣Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/deductApply")
public class DeductApplyController extends BaseController {

	@Autowired
	private DeductApplyService deductApplyService;

	@Autowired
	private ContractLockService contractLockService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@Autowired
	private DeductApplyVOService deductApplyVOService;

	@Autowired
	private DeductApplyDetailVOService deductApplyDetailVOService;

	@ModelAttribute
	public DeductApply get(@RequestParam(required = false) String id) {
		DeductApply entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = deductApplyService.get(id);
		}
		if (entity == null) {
			entity = new DeductApply();
		}
		return entity;
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = { "list", "" })
	public String list(DeductApplyVO deductApplyVO, HttpServletRequest request, HttpServletResponse response, Model model, String queryFlag) {
		String level = null;
		if (deductApplyVO != null && deductApplyVO.getCompany() != null && !StringUtils.isEmpty(deductApplyVO.getCompany().getId())) {
			Office office = new Office();
			office.setId(deductApplyVO.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(deductApplyVO.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				deductApplyVO.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				deductApplyVO.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				deductApplyVO.setOrgLevel4(office);
			}
		}
		if ("button".equals(queryFlag)) {
			Page<DeductApplyVO> page = deductApplyVOService.findPage(new Page<DeductApplyVO>(request, response), deductApplyVO);
			model.addAttribute("page", page);
		} else {
			deductApplyVO.setStreamStartTime(new Date());
			deductApplyVO.setStreamEndTime(new Date());
			Page<DeductApplyVO> page = deductApplyVOService.findPage(new Page<DeductApplyVO>(request, response), deductApplyVO);
			model.addAttribute("page", page);
		}
		model.addAttribute("deductApplyVO", deductApplyVO);
		return "app/accounting/deductApply/deductApplyList";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "detailList")
	public String detailList(DeductApplyVO deductApplyVO, HttpServletRequest request, HttpServletResponse response, Model model, String queryFlag) {
		String chooseFlag = request.getParameter("chooseFlag");
		Page<DeductApplyVO> page = null;
		String level = null;
		if (deductApplyVO != null && deductApplyVO.getCompany() != null && !StringUtils.isEmpty(deductApplyVO.getCompany().getId())) {
			Office office = new Office();
			office.setId(deductApplyVO.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(deductApplyVO.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				deductApplyVO.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				deductApplyVO.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				deductApplyVO.setOrgLevel4(office);
			}
		}
		if ("success".equals(chooseFlag)) {
			page = deductApplyDetailVOService.getDeductApplyVOForSuccess(new Page<DeductApplyVO>(request, response), deductApplyVO);
		} else if ("fail".equals(chooseFlag)) {
			page = deductApplyDetailVOService.getDeductApplyVOForFail(new Page<DeductApplyVO>(request, response), deductApplyVO);
		} else {
			if ("button".equals(queryFlag)) {
				page = deductApplyDetailVOService.findPage(new Page<DeductApplyVO>(request, response), deductApplyVO);
			} else {
				deductApplyVO.setStartTime(new Date());
				deductApplyVO.setEndTime(new Date());
				page = deductApplyDetailVOService.findPage(new Page<DeductApplyVO>(request, response), deductApplyVO);
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("chooseFlag", chooseFlag);
		model.addAttribute("deductApplyVO", deductApplyVO);
		return "app/accounting/deductApply/deductApplyDetailList";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "form")
	public String form(DeductApply deductApply, Model model) {
		String contractNo;
		try {
			if(!StringUtils.isBlank(deductApply.getContractNo())){
				contractNo = URLDecoder.decode(deductApply.getContractNo(), "UTF-8");
				deductApply.setContractNo(contractNo);
				deductApply = deductApplyService.getDeductApplyByContractNoAndPeriodNum(deductApply);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		model.addAttribute("deductApply", deductApply);
		return "app/accounting/deductApply/deductApplyForm";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "queryBankData")
	public String queryBankData(DeductApply deductApply, Model model) {
		List<Map<String, Object>> maps = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put("mobileNum", deductApply.getMobileNum());
		params.put("idNum", deductApply.getIdNum());
		maps = deductApplyService.findBankDataList(params);
		model.addAttribute("list", maps);
		return "app/accounting/deductApply/queryBankDataList";
	}

	@ResponseBody
	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "queryMobileNumAndIdNum")
	public AjaxView queryMobileNumAndIdNum(DeductApply deductApply, Model model) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> map = Maps.newHashMap();
		String contractNo = null;
		try {
			contractNo = URLDecoder.decode(deductApply.getContractNo(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		map = deductApplyService.queryMobileNumAndIdNum(contractNo);
		if (map != null) {
			ajaxView.setSuccess();
			ajaxView.put("mobileNum", map.get("mobileNum"));
			ajaxView.put("idNum", map.get("idNum"));
			ajaxView.put("contractNo", map.get("contractNo"));
			ajaxView.put("mainCustId", map.get("mainCustId"));
		} else {
			ajaxView.setFailed().setMessage("合同号"+contractNo+"在ift_acc_gqget_cust_info表中查询数据为空，请核实数据");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:deductApply:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DeductApply deductApply, String custId) {
		AjaxView ajaxView = new AjaxView();
		try {
			if (!(Constants.DEDUCT_TYPE_ZJHK.equals(deductApply.getDeductType()))) {
				deductApply.setMidCapitalTerraceNo(custId);
			}
			deductApplyService.saveDeductApply(deductApply);//保存在tmp表中
			ajaxView.setSuccess().setMessage("保存还款划扣成功！");
			AccFacade.facade.repaymentWithholding(deductApply.getContractNo());
		} catch (Exception e) {
			logger.error("保存还款划扣失败！", e);
			ajaxView.setFailed().setMessage("保存还款划扣失败！");
		}
		return ajaxView;
	}

	// 划扣验证
	@ResponseBody
	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "validateIsLock")
	public AjaxView enterAccount(DeductApply deductApply) {
		AjaxView ajaxView = new AjaxView();
		try {
			String contractNo = URLDecoder.decode(deductApply.getContractNo(), "UTF-8");
			deductApply.setContractNo(contractNo);
			ajaxView = deductApplyService.enterAccountValidate(deductApply);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
			ajaxView.setFailed().setMessage("汉字解码失败");
		}
		return ajaxView;
	}

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");

	// 重新入账
	@ResponseBody
	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "recordAgain")
	public AjaxView recordAgain(DeductApplyVO deductApplyVO) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			DeductApplyVO vo = deductApplyVOService.getDeductApplyVOByDeductApplyNo(deductApplyVO.getDeductApplyNo());
			// 调用存储
			deductApplyDetailVOService.recordAgain(vo);
			connection = dataSource.getConnection();
			CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT_OUT(?,?,?)}");
			callableStatement.setString(1, DateUtils.dateToTimeString(vo.getStreamTime()));
			callableStatement.setString(2, vo.getSysSeqNo());
			callableStatement.registerOutParameter(3, java.sql.Types.CHAR);
			callableStatement.execute();
			String returnCount = callableStatement.getString(3);
			if("0".equalsIgnoreCase(returnCount)){
				ajaxView.setSuccess().setMessage("重新入账成功");
			}else{
				ajaxView.setFailed().setMessage("重新入账失败,存储调用失败！");
			}
		} catch (SQLException e) {
			logger.error("重新入账失败，存储调用失败！", e);
			ajaxView.setFailed().setMessage("重新入账失败");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("重新入账失败，连接关闭失败！", e);
				ajaxView.setFailed().setMessage("重新入账失败");
			}
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:deductApply:edit")
	@RequestMapping(value = "addBox")
	public String addBox(String deductApplyNo, Model model) {
		List<String> deductAmounts = deductApplyDetailVOService.getDeductAmountByDeductAppyNo(deductApplyNo);
		String streamNoAmount = "0";
		if (deductAmounts.size() != 0) {
			streamNoAmount = deductAmounts.get(0);
		}
		model.addAttribute("streamNoAmount", streamNoAmount);
		return "app/accounting/deductApply/deductApplyDetailFormForUpdateForm";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "detailIndex")
	public String index(DeductApplyVO deductApplyVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DeductApplyVO> deductApplyVOs = Lists.newArrayList();
		deductApplyVOs = deductApplyDetailVOService.getDeductAmountList(deductApplyVO);
		if (deductApplyVOs.size() != 0) {
			BigDecimal amountSum = new BigDecimal(0);
			for (DeductApplyVO applyVO : deductApplyVOs) {
				amountSum = amountSum.add(new BigDecimal(applyVO.getDeductAmountResult()));
			}
			model.addAttribute("amountSumStr", amountSum);
		}
		model.addAttribute("deductApplyNoClick", deductApplyVO.getDeductApplyNo());
		model.addAttribute("streamNoClick", deductApplyVO.getStreamNo());
		model.addAttribute("contractNoClick", deductApplyVO.getContractNo());
		model.addAttribute("streamTimeStrClick", deductApplyVO.getStreamTimeStr());
		return "app/accounting/deductApply/deductApplyDetailVOIndex";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "detailForm")
	public String detailForm(DeductApplyVO deductApplyVO, String pageFlag, HttpServletResponse response, Model model) {
		try {
			String contractNo = URLDecoder.decode(deductApplyVO.getContractNo(), "UTF-8");
			model.addAttribute("contractNoClick", contractNo);
			String streamNo = URLDecoder.decode(deductApplyVO.getStreamNo(), "UTF-8");
			model.addAttribute("streamNoClick", streamNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		model.addAttribute("deductApplyNoClick", deductApplyVO.getDeductApplyNo());
		model.addAttribute("streamTimeStrClick", deductApplyVO.getStreamTimeStr());
		if ("update".equals(pageFlag)) {
			return "app/accounting/deductApply/deductApplyDetailFormForUpdate";
		} else if ("readOnly".equals(pageFlag)) {
			return "app/accounting/deductApply/deductApplyDetailFormReadOnly";
		} else {
			return null;
		}
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "adjustAccount")
	public String adjustAccount(DeductApplyVO deductApplyVO, Model model) {
		DeductApplyVO deductApplyVOTmp = deductApplyVOService.get(deductApplyVO);
		deductApplyVOTmp.setDescription("");
		model.addAttribute("deductApplyVO", deductApplyVOTmp);
		return "app/accounting/deductApply/adjustAccountForm";
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "saveAdjustAccount")
	@ResponseBody
	public AjaxView saveAdjustAccount(DeductApplyVO deductApplyVO) {
		AjaxView ajaxView = new AjaxView();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			// 存储过程 传参数 ：数据日期,合同号,流水号
			logger.info("开始调用存储： SP_ACC_DELETE_DETAIL_STRIKE,参数为：数据日期（当前日期）,合同号  " + deductApplyVO.getContractNo() + "划扣申请号：" + deductApplyVO.getDeductApplyNo() + "流水时间：" + deductApplyVO.getStreamTimeStr());
			CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_DELETE_DETAIL_STRIKE(?,?,?,?,?)}");
			callableStatement.setString(1, DateUtils.dateToTimeString(new Date()));
			callableStatement.setString(2, deductApplyVO.getStreamTimeStr());
			callableStatement.setString(3, deductApplyVO.getContractNo());
			callableStatement.setString(4, deductApplyVO.getDeductApplyNo());
			callableStatement.registerOutParameter(5, java.sql.Types.CHAR);
			callableStatement.executeQuery();
			String flag = callableStatement.getString(5);
			if ("5".equals(flag)) {
				// 插入数据到result表
				String deductAmountResultOld = deductApplyVO.getDeductAmountResult();
				String deductAmountResultNow = "-" + deductAmountResultOld;
				deductApplyVO.setDeductAmountResult(deductAmountResultNow);
				deductApplyVO.setDataDt(DateUtils.dateToTimeString(new Date()));
				deductApplyVO.setStreamTime(new Date());
				deductApplyVO.setStrikeFlag("1");
				deductApplyVO.setIsLock("0");
				deductApplyVO.setDeductResult("");
				Integer a = (int) (Math.random() * 10000000);
				Long b = System.currentTimeMillis();
				String deductApplyNo = Integer.toString(a) + b;
				deductApplyVO.setDeductApplyNo(deductApplyNo);
				deductApplyVOService.save(deductApplyVO);
				DeductApply deductApply = new DeductApply();
				deductApply.setDataDt(deductApplyVO.getDataDt());
				deductApply.setDeductApplyNo(deductApplyVO.getDeductApplyNo());
				deductApply.setContractNo(deductApplyVO.getContractNo());
				deductApply.setDeductAmount(deductApplyVO.getDeductAmountResult());
				deductApply.setDeductCustId(deductApplyVO.getDeductCustId());
				deductApply.setIsLock("0");
				deductApply.setDeductTime(DateUtils.formatDate(deductApplyVO.getStreamTime()));
				deductApplyService.saveDeductApplyToDeductResult(deductApply);
			}
			ajaxView.setSuccess().setMessage("冲正成功");
		} catch (SQLException e) {
			logger.error("调用存储SP_ACC_DELETE_DETAIL_STRIKE失败！", e);
			ajaxView.setFailed().setMessage("冲正失败");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("调用存储SP_ACC_DELETE_DETAIL_STRIKE,关闭连接失败", e);
				ajaxView.setFailed().setMessage("重新匹配失败");
			}
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "getData")
	@ResponseBody
	public List<Map<String, Object>> getData(String contractNo, String streamTimeStr) {
		String contractNoTmp = null;
		try {
			contractNoTmp = URLDecoder.decode(contractNo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		List<Map<String, Object>> datas = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put("contractNo", contractNoTmp);
		params.put("streamTimeStr", streamTimeStr);
		datas = deductApplyDetailVOService.getRepayDetailData(params);
		return datas;
	}

	@RequiresPermissions("accounting:deductApply:view")
	@RequestMapping(value = "matchAgain")
	@ResponseBody
	public AjaxView matchAgain(@RequestBody List<DeductApplyVO> deductApplyVOs, String amountSumStr, String contractNoClick, String streamTimeStrClick, String deductApplyNoClick) {
		try {
			contractNoClick = URLDecoder.decode(contractNoClick, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解析失败", e1);
		}
		AjaxView ajaxView = new AjaxView();
		String message = deductApplyDetailVOService.matchAgain(deductApplyVOs, amountSumStr, contractNoClick, streamTimeStrClick, deductApplyNoClick);
		if ("true".equals(message)) {
			Connection connection = null;
			try {
				connection = dataSource.getConnection();
				// 存储过程 传参数 ：数据日期,合同号,流水号
				logger.info("开始调用存储： SP_ACC_CHANGE_DETAIL_STA,参数为：数据日期（当前日期）,合同号  " + contractNoClick + "划扣申请号：" + deductApplyNoClick);
				CallableStatement callableStatement = connection.prepareCall("{call SP_ACC_CHANGE_DETAIL_STA(?,?,?)}");
				callableStatement.setString(1, DateUtils.dateToTimeString(new Date()));
				callableStatement.setString(2, contractNoClick);
				callableStatement.setString(3, streamTimeStrClick);
				callableStatement.execute();
				ajaxView.setSuccess().setMessage("重新匹配成功");
			} catch (SQLException e) {
				logger.error("调用存储SP_ACC_CHANGE_DETAIL_STA失败！", e);
				ajaxView.setFailed().setMessage("重新匹配失败");
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("调用存储SP_ACC_CHANGE_DETAIL_STA,关闭连接失败", e);
					ajaxView.setFailed().setMessage("重新匹配失败");
				}
			}
		} else {
			ajaxView.setFailed().setMessage(message);
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:deductApply")
	@RequestMapping(value = "validateIdLock")
	@ResponseBody
	public AjaxView validateIdLock(ContractLock contractLock) {
		try {
			String contractNo = URLDecoder.decode(contractLock.getContractNo(), "UTF-8");
			contractLock.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解码失败", e1);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			ContractLock contractLockTmp = contractLockService.validateIsLock(contractLock);
			if (StringUtils.isNull(contractLockTmp)) {
				contractLock.setLockFlag(Constants.CONTRACT_LOCK_FLAG_CXPP);
				contractLockService.saveLockInfo(contractLock);
				ajaxView.setSuccess();
			} else {
				if (Constants.CONTRACT_LOCK_FLAG_CXPP.equals(contractLockTmp.getLockFlag())) {
					ajaxView.setSuccess();
				} else {
					ajaxView.setFailed().setMessage("该合同已锁定,请等待其他操作结束");
				}
			}
		} catch (Exception e) {
			logger.error("验证是否加锁失败", e);
			ajaxView.setFailed().setMessage("验证是否加锁失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:deductApply")
	@RequestMapping(value = "validateAdjustAccountLock")
	@ResponseBody
	public AjaxView validateAdjustAccountLock(ContractLock contractLock) {
		try {
			String contractNo = URLDecoder.decode(contractLock.getContractNo(), "UTF-8");
			contractLock.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解码失败", e1);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			ContractLock contractLockTmp = contractLockService.validateIsLock(contractLock);
			if (StringUtils.isNull(contractLockTmp)) {
				contractLock.setLockFlag(Constants.CONTRACT_LOCK_FLAG_CZLS);
				contractLockService.saveLockInfo(contractLock);
				ajaxView.setSuccess();
			} else {
				if (Constants.CONTRACT_LOCK_FLAG_CZLS.equals(contractLockTmp.getLockFlag())) {
					ajaxView.setSuccess();
				} else {
					ajaxView.setFailed().setMessage("该合同已锁定,请等待其他操作结束");
				}
			}
		} catch (Exception e) {
			logger.error("验证是否加锁失败", e);
			ajaxView.setFailed().setMessage("验证是否加锁失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:deductApply")
	@RequestMapping(value = "deleteLock")
	@ResponseBody
	public AjaxView deleteLock(ContractLock contractLock) {
		try {
			String contractNo = URLDecoder.decode(contractLock.getContractNo(), "UTF-8");
			contractLock.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解码失败", e1);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			contractLockService.deleteLock(contractLock);
			ajaxView.setSuccess();
		} catch (Exception e) {
			logger.error("解锁失败", e);
			ajaxView.setFailed().setMessage("解锁失败");
		}
		return ajaxView;
	}

}
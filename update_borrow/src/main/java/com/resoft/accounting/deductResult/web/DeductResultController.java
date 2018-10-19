package com.resoft.accounting.deductResult.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.accounting.deductResult.service.DeductResultService;
import com.resoft.common.utils.ExcelReader;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 补录流水Controller
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/deductResult")
public class DeductResultController extends BaseController {

	@Autowired
	private DeductResultService deductResultService;

	@Autowired
	private ContractLockDao contractLockDao;

	@Autowired
	private ContractService contractService;

	@ModelAttribute
	public DeductResult get(@RequestParam(required = false) String id) {
		DeductResult entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = deductResultService.get(id);
		}
		if (entity == null) {
			entity = new DeductResult();
		}
		return entity;
	}

	@RequiresPermissions("accounting:deductResult:view")
	@RequestMapping(value = "index")
	public String index(DeductResult deductResult, HttpServletRequest request, HttpServletResponse response, Model model) {

		if (deductResult != null) {
			String contractNo = deductResult.getContractNo();
			if (contractNo.contains("\\")) {
				contractNo = contractNo.replace("\\", "\\\\");
				deductResult.setContractNo(contractNo);
			}
		}
		model.addAttribute("contractNo", deductResult.getContractNo());
		return "app/accounting/deductResult/deductResultIndex";
	}




	@RequiresPermissions("accounting:deductResult:view")
	@RequestMapping(value = { "list", "" })
	public String list(DeductResult deductResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			String contractNo = URLDecoder.decode(deductResult.getContractNo(), "UTF-8");
			deductResult.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		Page<DeductResult> page = deductResultService.findPage(new Page<DeductResult>(request, response), deductResult);
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(deductResult.getContractNo());
		contractLock.setLockFlag(Constants.CONTRACT_LOCK_FLAG_ZWTZ);// 账务调整标示
		contractLock = contractLockDao.validateIsLock(contractLock);
		if (contractLock != null) {
			model.addAttribute("readonly", "true");
		}
		model.addAttribute("page", page);
		if (deductResult != null) {
			String contractNo = deductResult.getContractNo();
			if (contractNo.contains("\\")) {
				contractNo = contractNo.replace("\\", "\\\\");
				deductResult.setContractNo(contractNo);
			}
		}
		model.addAttribute("contractNo", deductResult.getContractNo());
		return "app/accounting/deductResult/deductResultList";
	}

	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "form")
	public String form(DeductResult deductResult, String flag, Model model) {

		String deductUserName = UserUtils.getUser().getLoginName();
		String deductUserId = UserUtils.getUser().getId();
		deductResult.setDeductUserId(deductUserId);
		deductResult.setDeductUserName(deductUserName);
      if (deductResult != null) {
            String contractNo = deductResult.getContractNo();
            if (contractNo.contains("\\")) {
                contractNo = contractNo.replace("\\", "\\\\");
                deductResult.setContractNo(contractNo);
            }
        }
		model.addAttribute("deductResult", deductResult);
		model.addAttribute("flag", flag);
		return "app/accounting/deductResult/deductResultForm";
	}

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");

	@ResponseBody
	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DeductResult deductResult, String flag) {
		AjaxView ajaxView = new AjaxView();
		String contractNo = deductResult.getContractNo();
		if(contractNo.contains("\\\\")){
			contractNo = contractNo.replace("\\\\", "\\");
			deductResult.setContractNo(contractNo);
	}
		Contract contract = contractService.findContractByContractNo(deductResult.getContractNo());
		try {
			if (contract != null) {
				if ("1".equals(flag)) {
					ContractLock contractLock = new ContractLock();
					contractLock.setContractNo(deductResult.getContractNo());
					contractLock = contractLockDao.validateIsLock(contractLock);
					if (contractLock == null) {
						DeductApplyVO deductApplyVO = deductResultService.saveDeductApplyAndDeductApplyVO(deductResult, contract);
						// 调用存储
						Connection connection = null;
						try {
							connection = dataSource.getConnection();
							CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_REPAY_ACCOUNT_OUT(?,?,?)}");
							callableStatement.setString(1, DateUtils.dateToTimeString(deductResult.getEntryTime()));
							callableStatement.setString(2, deductApplyVO.getSysSeqNo());
							callableStatement.registerOutParameter(3, java.sql.Types.CHAR);
							callableStatement.execute();
							String returnCount = callableStatement.getString(3);
							if("0".equals(returnCount)){
								ajaxView.setSuccess().setMessage("补录流水成功！");
							}else{
								ajaxView.setFailed().setMessage("补录流水失败！");
							}
							ajaxView.put("flag", flag);
						} catch (SQLException e) {
							logger.error("存储调用失败！", e);
						} finally {
							try {
								connection.close();
							} catch (SQLException e) {
								logger.error("连接关闭失败！", e);
							}
						}
					} else {
						ajaxView.setFailed().setMessage("此合同正在进行其他操作！");
					}
				} else {
					deductResult.setIsLock("1");
					deductResultService.save(deductResult);
					ajaxView.setSuccess().setMessage("补录流水成功！");
				}
			} else {
				ajaxView.setFailed().setMessage("合同信息查询出错，请联系管理员！");
			}
		} catch (Exception e) {
			logger.error("补录流水失败！", e);
			ajaxView.setFailed().setMessage("补录流水失败！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "validateStreamNo")
	public AjaxView validateStreamNo(DeductResult deductResult) {
		AjaxView ajaxView = new AjaxView();
		try {
			String streamNo = URLDecoder.decode(deductResult.getStreamNo(), "UTF-8");
			deductResult.setStreamNo(streamNo);
			List<DeductResult> deductResultTmps = deductResultService.validateStreamNo(deductResult);
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
			logger.error("汉字转码失败", e);
			ajaxView.setFailed().setMessage("汉字转码失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(DeductResult deductResult) {
		String streamNo;
		try {
			streamNo = URLDecoder.decode(deductResult.getStreamNo(), "UTF-8");
			deductResult.setStreamNo(streamNo);
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解码失败",e1);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			deductResultService.deleteData(deductResult);
			ajaxView.setSuccess().setMessage("删除成功！");
		} catch (Exception e) {
			logger.error("删除失败！", e);
			ajaxView.setFailed().setMessage("删除失败！");
		}
		return ajaxView;
	}
	
	
	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "formBatch")
	public String formBatch(DeductResult deductResult, String flag, Model model) {
		String deductUserName = UserUtils.getUser().getLoginName();
		String deductUserId = UserUtils.getUser().getId();
		deductResult.setDeductUserId(deductUserId);
		deductResult.setDeductUserName(deductUserName);
		model.addAttribute("deductResult", deductResult);
		model.addAttribute("flag", flag);
		return "app/accounting/deductResult/deductResultFormBatch";
	}
	
	@ResponseBody
	@RequiresPermissions("accounting:deductResult:edit")
	@RequestMapping(value = "upload")
	public AjaxView uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
//		String contractNo = request.getParameter("contractNo");
		String deductUserId = request.getParameter("deductUserId");
		String deductUserName = request.getParameter("deductUserName");
		// 判断文件是否为空
        String flag = request.getParameter("flag");//上传标志
        ajaxView.put("flag", flag);
        ajaxView.setSuccess().setMessage("补录流水成功！");
        if (!file.isEmpty()) {
            InputStream is;
            HSSFWorkbook wb;
			try {
				is = file.getInputStream();
				//获取输入流
                wb = new HSSFWorkbook(is);
			} catch (IOException e1) {
				e1.printStackTrace();
				ajaxView.setFailed().setMessage("获取流信息失败" + e1.toString());
				return ajaxView;
			}
            int rowNum = ExcelReader.getRowNum(0, wb);
            HSSFSheet sheet = wb.getSheetAt(0);
            List<DeductResult> list = new ArrayList<DeductResult>();
            for(int i=1;i<rowNum;i++){
            	try {
                	HSSFRow row = sheet.getRow(i);
                	DeductResult deductResult = new DeductResult();
                	deductResult.setStreamNo(ExcelReader.getStringCellValue(row.getCell(0)).replace(".00", ""));// 流水号
                	deductResult.setDeductAmount(ExcelReader.getStringCellValue(row.getCell(1)));//扣款金额
                	deductResult.setDeductUserId(deductUserId);
                	deductResult.setDeductUserName(deductUserName);
                	deductResult.setEntryTime(row.getCell(2).getDateCellValue());
                	deductResult.setDescription(ExcelReader.getStringCellValue(row.getCell(3)).replace(".00", ""));
                	deductResult.setContractNo(ExcelReader.getStringCellValue(row.getCell(4)).replace(".00", ""));
                	if(StringUtils.isEmpty(deductResult.getStreamNo())){
                		break;
                	}else{
                		deductResultService.validateStreamNoBatch(deductResult, ajaxView);
                		if("1".equals(ajaxView.get("status"))){
                			list.add(deductResult);
                		}else{
                			ajaxView.setMessage("第" + i + "行" + ajaxView.get("message"));
                			return ajaxView;
                		}
                	}
                } catch (Exception e) {
                	ajaxView.setFailed().setMessage("数据库格式错误！第" + i + "行" );
                    e.printStackTrace();
                    return ajaxView;
                }
            }
        
            try {
				ajaxView = deductResultService.supplementAccount(list, flag, dataSource, ajaxView);
			} catch (Exception e) {
				e.printStackTrace();
				ajaxView.setFailed().setMessage(ajaxView.get("message") + e.toString());
			}
            
        }
        return ajaxView;
	}
}
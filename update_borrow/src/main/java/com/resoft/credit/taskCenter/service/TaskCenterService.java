package com.resoft.credit.taskCenter.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.stockAssesseTarget.service.StockAssesseTargetService;
import com.resoft.credit.stockInfo.entity.StockInfo;
import com.resoft.credit.stockInfo.service.StockInfoService;
import com.resoft.credit.stockOperateDetail.dao.StockOperateDetailDao;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;
import com.resoft.credit.stockOpinion.service.CreStockOpinionService;
import com.resoft.credit.stockTaskReceive.dao.StockTaskReceiveDao;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.resoft.credit.taskCenter.dao.TaskCenterDao;
import com.resoft.credit.taskCenter.entity.ActHiTaskInst;
import com.resoft.credit.taskCenter.entity.ActRuTask;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service("creTaskCenterService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class TaskCenterService {
	private FileOutputStream fos;

	@Autowired
	private TaskCenterDao taskCenterDao;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	@Autowired
	private StockInfoService stockInfoService;
	@Autowired
	private CreStockOpinionService stockOpinionService;
	@Autowired
	private StockOperateDetailDao stockOperateDetailDao;
	@Autowired
	private StockAssesseTargetService stockAssesseTargetService;
	@Autowired
	private StockTaskReceiveDao stockTaskReceiveDao;

	/**
	 * @reqno:H1511100047
	 * @date-designer:20151111-huangxuecheng
	 * @date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.描述：根据传入的procInstId查询ActRuTask实体类，封装了参数execId和proDefId 
	 *                                                                                                                           提供流程图
	 *                                                                                                                           、
	 *                                                                                                                           流程轨迹使用
	 */
	public ActRuTask findSingleRunTask(String procInstId) {
		return taskCenterDao.selectSingleRunTask(procInstId);
	}

	/**
	 * @reqno:H1511100047
	 * @date-designer:20151111-huangxuecheng
	 * @date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.描述：根据传入的procInstId查询ActHiTaskInst实体类，封装了参数execId和proDefId 
	 *                                                                                                                               提供流程图
	 *                                                                                                                               、
	 *                                                                                                                               流程轨迹使用
	 */
	public List<ActHiTaskInst> findSingleHisTask(String procInstId) {
		return taskCenterDao.selectHisTask(procInstId);
	}

	/**
	 * @reqno:H1510230034
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:CRE_信贷审批_合同复核_批量提交待办任务列表_导出放款文件。使用一个service集中处理Excel的每行每列的单元格的写入
	 */
	@Transactional(value = "CRE", readOnly = false)
	public HSSFWorkbook toExcel(HttpServletRequest request, HttpServletResponse response, HSSFSheet sheet, List<Map<String, Object>> list, String[][] titleText, String sheetName, String ExcelName, String outputFile, HSSFWorkbook workbook, int num) throws Exception {
		try {
			fos = new FileOutputStream(outputFile);
			for (int i = 0; i < titleText.length; i++) {
				sheet.setColumnWidth(i, Long.valueOf(titleText[i][2]).intValue());
			}
			HSSFCellStyle style1 = workbook.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont font1 = workbook.createFont();
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setFontName("微软雅黑");
			style1.setFont(font1);
			// 设置内容样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			HSSFFont font2 = workbook.createFont();
			style2.setFont(font2);
			// 从第几行开始加载
			HSSFRow row1 = sheet.createRow(num);
			for (int i = 0; i < titleText.length; i++) {
				HSSFCell cellHandle = row1.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(titleText[i][1]);
				cellHandle.setCellValue(text);
				cellHandle.setCellStyle(style1);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new TreeMap<String, Object>();
				map = list.get(i);
				HSSFRow row = sheet.createRow(i + num + 1);
				for (int j = 0; j < titleText.length; j++) {
					HSSFCell cellText1 = row.createCell(j);
					System.out.println(titleText[j][0]);
					Object value = map.get(titleText[j][0]);
					if (value != null) {

						cellText1.setCellValue(value.toString());
					} else {
						cellText1.setCellValue("");
					}
					cellText1.setCellStyle(style2);
				}
			}

			getExcel(workbook);
			OutputStream out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename=" + ExcelName + ".xls");
			response.setContentType("application/msexcel;charset=UTF-8");
			workbook.write(out);
			out.close();
			FileUtils.deleteFile(outputFile);
		} catch (FileNotFoundException e) {

			org.apache.log4j.Logger.getLogger(this.getClass()).error("", e);
		}
		return workbook;
	}

	/**
	 * 创建Excel文件，返回Sheet
	 * 
	 * @throws FileNotFoundException
	 */
	@Transactional(value = "CRE", readOnly = false)
	public HSSFSheet createExcel(String address, String sheetName, HSSFWorkbook workbook) throws FileNotFoundException {
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, sheetName);
		FileUtils.createFile(address);
		fos = new FileOutputStream(address);
		return sheet;
	}

	/**
	 * 生成Excel文件
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void getExcel(HSSFWorkbook workbook) {
		try {
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error("", e);
		}
	}

	/**
	 * @reqno:H1511100047
	 * @date-designer:20151116-huangxuecheng
	 * @date-author:20151116-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹。对流程轨迹进行处理，以前是天数现在精确到天、时、分、秒
	 */
	/**
	 * @reqno:H1510230035
	 * @date-designer:20151116-huangxuecheng
	 * @date-author:20151116-huangxuecheng:CRE_信贷审批_合同复核_批量提交已办任务列表。对流程轨迹进行处理，以前是天数现在精确到天、时、分、秒
	 */
	public List<MyMap> sortList(List<MyMap> list) {
		for (MyMap m : list) {
			if (m.containsKey("TIMESPAN")) {
				String time = m.get("TIMESPAN").toString();
				time = DateUtils.formatDateTime(Long.parseLong(time));
				m.put("TIMESPAN", time);
			}
		}
		return list;
	}

	/**
	 * @reqno:H1510280020
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_在办流程监控.开发说明：实现在办流程监控的查询列表以及分页功能，分别查询出count和list封装在page中
	 */
	public Page<MyMap> findToDoProcessPage(Page<MyMap> page, MyMap paramMap) {
		Page<MyMap> result = page;
		if (page.getList() == null)
			page.setList(new ArrayList<MyMap>());
		else {
			page.getList().clear();
		}
		if (paramMap.size() > 0) {
			result.setCount(taskCenterDao.selectToDoProcessCount(paramMap));
			result.setDefaultCount(false);
			paramMap.setPage(page);
			result.getList().addAll(taskCenterDao.selectToDoProcess(paramMap));
		}
		return result;
	}

	/**
	 * @reqno:H1510280021
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_办结流程监控.开发说明：实现办结流程监控的查询列表以及分页功能，分别查询出count和list封装在page中
	 */
	public Page<MyMap> findDoneProcessPage(Page<MyMap> page, MyMap paramMap) {
		Page<MyMap> result = page;
		if (page.getList() == null)
			page.setList(new ArrayList<MyMap>());
		else {
			page.getList().clear();
		}
		if (paramMap.size() > 0) {
			result.setCount(taskCenterDao.selectDoneProcessCount(paramMap));
			result.setDefaultCount(false);
			paramMap.setPage(page);
			result.getList().addAll(taskCenterDao.selectDoneProcess(paramMap));
		}
		return result;
	}

	@Transactional(value = "CRE", readOnly = false)
	public void finishProcessInstanceByRefuseStatus(ActTaskParam actTaskParam, String suggestion) {
		actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【放弃】" + suggestion);
		applyRegisterService.updateApplyStatus(actTaskParam.getApplyNo(), Constants.APPLY_STATUS_REFUSED);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void insertOfficeApplyRelation(String applyNo, String officeId) {
		taskCenterDao.insertOfficeApplyRelation(applyNo, officeId);
	}

	/**
	 * 待办任务查询
	 * */
	public Page<MyMap> todoListNewByPage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findToDoListByPage(paramMap));
		return page;
	}

	/**
	 * 合同审核、财务放款待办任务查询
	 * */
	public Page<MyMap> todoListNewByPageTwo(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findToDoListByPageTwo(paramMap));
		return page;
	}
	
	/**
	 * 已办任务查询
	 * */
	public Page<MyMap> findDoneTaskList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findDoneTaskList(paramMap));
		return page;
	}
	
	/**
	 * 合同审核已办任务查询
	 * */
	public Page<MyMap> findDoneTaskListHtsh(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findDoneTaskListHtsh(paramMap));
		return page;
	}

	
	/**
	 * 已分配任务查询
	 * */
	public Page<MyMap> findDoneClaimlist(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findDoneClaimlist(paramMap));
		return page;
	}

	/**
	 * 保存已分配任务信息
	 * */
	@Transactional(value = "CRE", readOnly = false)
	public void saveTaskAllocate(Map<String, Object> paramMap) {
		taskCenterDao.saveTaskAllocate(paramMap);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void saveTaskAllocateAndDoAsign(ActTaskParam actTaskParam, String user, String processName, String userId) throws UnsupportedEncodingException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("id", IdGen.uuid());
		param.put("applyNo", actTaskParam.getApplyNo());
		param.put("processName", URLDecoder.decode(processName, "UTF-8"));
		param.put("allocator", userId);
		param.put("createId", UserUtils.getUser().getId());
		param.put("createDate", new Date());
		param.put("taskDefKey", actTaskParam.getTaskDefKey());
		param.put("execId", actTaskParam.getExecId());
		param.put("procDefId", actTaskParam.getProcDefId());
		saveTaskAllocate(param);
		actTaskService.doAssign(actTaskParam.getTaskId(), user);
	}

	/**
	 * 系统管理员待办任务查询
	 * */
	public Page<MyMap> allTodoListNewByPage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findAllToDoListByPage(paramMap));
		return page;
	}

	/**
	 * 线下待办任务查询
	 * */
	public Page<MyMap> findUnderAllToDoListByPage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findUnderAllToDoListByPage(paramMap));
		return page;
	}
	
	/**
	 * 系统管理员合同审核、财务放款待办任务查询
	 * */
	public Page<MyMap> allTodoListNewByPageTwo(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findAllToDoListByPageTwo(paramMap));
		return page;
	}
	
	/**
	 * 系统管理员已办监控查询
	 * */
	public Page<MyMap> allFindDoneOrFinishList(Page<MyMap> page, MyMap paramMap) {

		Page<MyMap> result = page;
		if (StringUtils.isBlank(page.getOrderBy())) {
			page.setOrderBy("ENDTIME DESC");
		}

		paramMap.setPage(page);
		result.setList(taskCenterDao.allFindDoneOrFinishList(paramMap));
		return result;
	}

	/**
	 * 拒签
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void refuseToSignTask(String taskId) {
		taskCenterDao.refuseToSignTask(taskId);
	}
	
	/**
	 * 已办总任务查询
	 * */
	@Transactional(value = "CRE", readOnly = false)
	public Page<MyMap> findAllDoneTaskList(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(taskCenterDao.findAllDoneTaskList(paramMap));
		return page;
	}

	/**
	 * 转办
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void turnToDoTask(ActTaskParam actTaskParam, User user) {
		String taskDefKey = actTaskParam.getTaskDefKey();
		String applyNo = actTaskParam.getApplyNo();
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		String applyProductTypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_ZGJH.equals(applyProductTypeCode)){//债股结合转办
			//是否是自己
			String grade="";
			if("utask_fgsfksh".equalsIgnoreCase(taskDefKey)){
				grade="4"; //分公司
			}
			if("utask_qyfksh".equalsIgnoreCase(taskDefKey)){
				grade="3"; 		
			}
			if("utask_dqfkzysh".equalsIgnoreCase(taskDefKey)){
				grade="2"; 
			}
			if("utask_zgsfksh".equalsIgnoreCase(taskDefKey)){
				grade="1"; 
			}	
			StockTaskReceive receive = stockTaskReceiveService.getReceiveByApplyNoAndGrade(applyNo, grade);
			if(receive!=null){
				if("0".equals(receive.getIsStockPost()+"")){//如果是否就删除股权信息
					stockInfoService.deleteStockInfoByApplyGrade(applyNo, grade);
					stockTaskReceiveService.deleteStockInfoByApplyGrade(applyNo, grade);
					stockOpinionService.deleteStockInfoByApplyGrade(applyNo, grade);
					stockAssesseTargetService.deleteStockInfoByApplyGrade(applyNo, grade);
					//重新转办
					StockInfo stockInfo=new StockInfo();
					stockInfo.setApplyNo(applyNo);
					stockInfo.setGrade(grade);
					stockInfo.preInsert();
					stockInfoService.saveStockInfoOnlyApplyNo(stockInfo);
					
					StockTaskReceive stockTaskReceive=new StockTaskReceive();
					stockTaskReceive.setStockInfoId(stockInfo.getId());
					stockTaskReceive.setApplyNo(applyNo);
					stockTaskReceive.setReceiver(user.getId());
					stockTaskReceive.setCode(user.getOffice().getId());
					stockTaskReceive.setGrade(grade);
					stockTaskReceive.setIsStockPost(0);
					stockTaskReceive.preInsert();
					stockTaskReceiveDao.insert(stockTaskReceive);
					//加入流程轨迹
					StockOperateDetail stockOperateDetail=new StockOperateDetail();
					stockOperateDetail.setGrade(grade);
					stockOperateDetail.setApplyNo(applyNo);
					stockOperateDetail.setOperate("4");//4表示债股一起转办
					stockOperateDetail.setReceiver(user.getId());
					stockOperateDetail.preInsert();
					stockOperateDetailDao.insert(stockOperateDetail);
				}
			}
		}
		if (Constants.UTASK_ZGSJLSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
			taskCenterDao.updateOfficeApplyRelationByApplyNo(actTaskParam.getApplyNo(), user.getOffice().getId());
		}
		actTaskService.turnToDo(actTaskParam.getTaskId(), user.getId(), actTaskParam.getProcInstId());
	}

	public HashMap<String,String> findUnderLoanFirstDate(String procInstId) {
		return taskCenterDao.findUnderLoanFirstDate(procInstId);
	}

    public Page<MyMap> findUnderAllDoneListByPage(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findUnderAllDoneListByPage(paramMap));
		return page;
    }

    public Page<MyMap> findUnderEndProcess(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findUnderEndProcess(paramMap));
		return page;
    }
}

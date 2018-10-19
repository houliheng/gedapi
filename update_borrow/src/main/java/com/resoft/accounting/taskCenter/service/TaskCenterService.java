package com.resoft.accounting.taskCenter.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
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

import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.taskCenter.dao.TaskCenterDao;
import com.resoft.accounting.taskCenter.entity.ActHiTaskInst;
import com.resoft.accounting.taskCenter.entity.ActRuTask;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.act.entity.MyMap;


@Service("taskCenterService")@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class TaskCenterService{
	
	private FileOutputStream fos;
	
	@Autowired
	private TaskCenterDao taskCenterDao;
	@Autowired
	private RepositoryService repositoryService;

	// 根据 节点名称 还有流程id 查询运行时任务的信息
	public Integer queryActRuTaskByParamMap(Map<String,String> paramMap){
		return taskCenterDao.selectActRuTaskByParamMap(paramMap);
	}

	/**
	 * 根据传入的procInstId查询ActRuTask实体类，封装了参数execId和proDefId 提供流程图、流程轨迹使用
	 */
	public ActRuTask findSingleRunTask(String procInstId){
		return taskCenterDao.selectSingleRunTask(procInstId);
	}
	/**
	 *根据传入的procInstId查询ActHiTaskInst实体类，封装了参数execId和proDefId  提供流程图、流程轨迹使用
	 */
	public List<ActHiTaskInst> findSingleHisTask(String procInstId){
		return taskCenterDao.selectHisTask(procInstId);
	}
	/**
	 * CRE_信贷审批_合同复核_批量提交待办任务列表_导出放款文件。使用一个service集中处理Excel的每行每列的单元格的写入
	 */
	@Transactional(value = "ACC", readOnly = false)
	public HSSFWorkbook toExcel(HttpServletRequest request,
			HttpServletResponse response, HSSFSheet sheet,
			List<Map<String, Object>> list, String[][] titleText,
			String sheetName, String ExcelName, String outputFile,
			HSSFWorkbook workbook, int num) throws Exception {
		try {
			fos = new FileOutputStream(outputFile);
			for (int i = 0; i < titleText.length; i++) {
				sheet.setColumnWidth(i, Long.valueOf(titleText[i][2])
						.intValue());
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
				HSSFRichTextString text = new HSSFRichTextString(
						titleText[i][1]);
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
			response.setHeader("Content-disposition", "attachment;filename="
					+ ExcelName + ".xls");
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
	@Transactional(value = "ACC", readOnly = false)
	public HSSFSheet createExcel(String address, String sheetName,
			HSSFWorkbook workbook) throws FileNotFoundException {
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
	@Transactional(value = "ACC", readOnly = false)
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
	 * 对流程轨迹进行处理，以前是天数现在精确到天、时、分、秒
	 */
    public List<MyMap> sortList(List<MyMap> list){
    	for(MyMap m : list){
    		if(m.containsKey("TIMESPAN")){
    		String time = m.get("TIMESPAN").toString();
    		time = DateUtils.formatDateTime(Long.parseLong(time));
    		m.put("TIMESPAN", time);
    		}
    	}
    	return list;
    }
	/**
	 *实现在办流程监控的查询列表以及分页功能，分别查询出count和list封装在page中								        
	 */
    public Page<MyMap> findToDoProcessPage(Page<MyMap> page, MyMap paramMap){
    	Page<MyMap> result = page;
    	if (page.getList() == null)
	        page.setList(new ArrayList<MyMap>());
	    else {
	        page.getList().clear();
	    }
	    if(paramMap.size()>0){
	        result.setCount(taskCenterDao.selectToDoProcessCount(paramMap));
	        result.setDefaultCount(false);
	        paramMap.setPage(page);
	        result.getList().addAll(taskCenterDao.selectToDoProcess(paramMap));
	    }
	    return result;
    }
	/**
	 * 实现办结流程监控的查询列表以及分页功能，分别查询出count和list封装在page中
	 */
    public Page<MyMap> findDoneProcessPage(Page<MyMap> page, MyMap paramMap){
    	Page<MyMap> result = page;
    	if (page.getList() == null)
	        page.setList(new ArrayList<MyMap>());
	    else {
	        page.getList().clear();
	    }
	    if(paramMap.size()>0){
	        result.setCount(taskCenterDao.selectDoneProcessCount(paramMap));
	        result.setDefaultCount(false);
	        paramMap.setPage(page);
	        result.getList().addAll(taskCenterDao.selectDoneProcess(paramMap));
	    }
	    return result;
    }
    /**
	 * 获取所有启动（激活）状态的流程实例
	 */
    public List<ProcessDefinition> loadActiveProcessList() {
		ProcessDefinitionQuery processDefinitionQuery = (ProcessDefinitionQuery) this.repositoryService
				.createProcessDefinitionQuery().active().latestVersion()
				.orderByProcessDefinitionKey().asc();
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
		return processDefinitionList;
	}
    
    /**
     * 保证金退还待办任务查询
     * */
	public Page<MyMap> findMarginRepayToDoList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findMarginRepayToDoList(paramMap));
		return page;
	}
	
	/**
	 * 保证金退还待办任务查询
	 * */
	public Page<MyMap> findMarginRepayDoneList(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(taskCenterDao.findMarginRepayDoneList(paramMap));
		return page;
	}
	
	/**
     * 违约金罚息减免待办任务查询
     * */
	public Page<MyMap> findPenaltyFineExemptToDoList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findPenaltyFineExemptToDoList(paramMap));
		return page;
	}
	
	/**
	 * 违约金罚息减免已办任务查询
	 * */
	public Page<MyMap> findPenaltyFineExemptDoneList(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(taskCenterDao.findPenaltyFineExemptDoneList(paramMap));
		return page;
	}
	/**
	 * 提前还款待办
	 */
	public Page<MyMap> findAdvanceRepayToDoList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findAdvanceRepayToDoList(paramMap));
		return page;
	}
	
	/**
	 * 提前还款已办
	 */
	public Page<MyMap> findAdvanceRepayDoneList(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(taskCenterDao.findAdvanceRepayDoneList(paramMap));
		return page;
	}
}

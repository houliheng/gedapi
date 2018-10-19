package com.resoft.accounting.contract.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.resoft.accounting.contract.entity.ContractImport;
import com.resoft.accounting.repayPlan.entity.RepayPlanImport;
import com.resoft.accounting.staContractStatus.entity.StaContractStatusImport;

public class ExcelUtil {

	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	/**
	 * @Title: createWorkbook
	 * @Description: 判断excel文件后缀名，生成不同的workbook
	 * @param @param is
	 * @param @param excelFileName
	 * @param @return
	 * @param @throws IOException
	 * @return Workbook
	 * @throws
	 */
	public Workbook createWorkbook(InputStream is, String excelFileName) throws IOException {
		if (excelFileName.endsWith(".xls")) {
			return new HSSFWorkbook(is);
		} else if (excelFileName.endsWith(".xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	/**
	 * @Title: getSheet
	 * @Description: 根据sheet索引号获取对应的sheet
	 * @param @param workbook
	 * @param @param sheetIndex
	 * @param @return
	 * @return Sheet
	 * @throws
	 */
	public Sheet getSheet(Workbook workbook, int sheetIndex) {
		return workbook.getSheetAt(0);
	}

	/**
	 * @Title: importDataFromExcel
	 * @Description: 将sheet中的数据保存到list中，
	 *               1、调用此方法时，vo的属性个数必须和excel文件每行数据的列数相同且一一对应，vo的所有属性都为String
	 *               2、在action调用此方法时，需声明 private File excelFile;上传的文件 private
	 *               String excelFileName;原始文件的文件名 3、页面的file控件name需对应File的文件名
	 * @param @param vo javaBean
	 * @param @param is 输入流
	 * @param @param excelFileName
	 * @param @return
	 * @return List<Object>
	 * @throws
	 */
	public List<Object> importDataFromExcel(Object vo, InputStream is, String excelFileName,String flag) {
		List<Object> list = new ArrayList<Object>();
		try {
			// 创建工作簿
			Workbook workbook = this.createWorkbook(is, excelFileName);
			// 创建工作表sheet
			Sheet sheet = this.getSheet(workbook, 0);
			// 获取sheet中数据的行数
			int rows = sheet.getPhysicalNumberOfRows();
			// 获取表头单元格个数
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();
			// 利用反射，给JavaBean的属性进行赋值
			Field[] fields = vo.getClass().getDeclaredFields();
			for (int i = 1; i < rows; i++) {// 第一行为标题栏，从第二行开始取数据
				Row row = sheet.getRow(i);
				int index = 0;
				if(i != 1){
					if("staContractStatusImport".equals(flag)){
						vo = new StaContractStatusImport();
					}else if("repayPlanImport".equals(flag)){
						vo = new RepayPlanImport();
					}else if("contractImport".equals(flag)){
						vo = new ContractImport();
					}
				}
				while (index < cells) {
					Cell cell = row.getCell(index);
					if (null == cell) {
						cell = row.createCell(index);
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = null == cell.getStringCellValue() ? "" : cell.getStringCellValue();

					Field field = fields[index];
					String fieldName = field.getName();
					String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method setMethod = vo.getClass().getMethod(methodName, new Class[] { String.class });
					setMethod.invoke(vo, new Object[] { value });
					index++;
				}
				list.add(vo);
			}
		} catch (Exception e) {
			logger.error("合同导入时映射失败", e);
		} finally {
			try {
				is.close();// 关闭流
			} catch (Exception e2) {
				logger.error("连接关闭失败", e2);
			}
		}
		return list;
	}

}

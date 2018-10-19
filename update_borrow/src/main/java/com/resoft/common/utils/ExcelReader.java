package com.resoft.common.utils;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	public static String getStringCellValue(HSSFCell cell){
		if(cell == null){
			return "";
		}
		String strCell="";
		switch (cell.getCellType()){
		case HSSFCell.CELL_TYPE_NUMERIC:
			DecimalFormat df = new DecimalFormat("0.00");
			strCell = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			strCell= cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell =Boolean.toString(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			strCell=cell.getStringCellValue();
			if(strCell!=null){
				strCell=strCell.replaceAll("#N/A","").trim();
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell="";
			break;
		default:
				strCell="";
				break;
		}
		if(strCell.equals("")||strCell==null){
			return "";
		}
		return strCell;
	}
	public static String getDateCellValue(HSSFCell cell){
		String result="";
		try{
			int cellType=cell.getCellType();
			if(cellType==HSSFCell.CELL_TYPE_NUMERIC){
				result = DateUtils.formatDateTime(cell.getDateCellValue());
			}
		}catch(Exception e){
			logger.error("获取单元格数据错误", e);
			return "error";
		}
		return result;
	}
	public static int getColumnNum(int sheetIndex, HSSFWorkbook wb){
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		HSSFRow row = sheet.getRow(0);
		if(row!=null&&row.getLastCellNum()>0){
			return row.getLastCellNum();
		}
		return 0;
	}
	public static int getRowNum(int sheetIndex,HSSFWorkbook wb){
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}
}

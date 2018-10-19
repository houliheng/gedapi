package com.resoft.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.StringUtils;

@SuppressWarnings("unchecked")
public class WordUtils {
	private static final Logger logger = LoggerFactory.getLogger(WordUtils.class);

	/**
	 * 
	 * 替换段落里的变量
	 * 
	 * @param replaceInPara
	 */
	public static void replaceInPara(XWPFDocument doc, Map<String, Object> paramss) {
		java.util.Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			replaceInPara(para, paramss);
		}
	}

	/**
	 * 删除标志位
	 * @param doc
	 */
	public static void removeAllForKeys(XWPFDocument doc){
		for (int i = 0; i < doc.getBodyElements().size(); i++) {
			IBodyElement element = doc.getBodyElements().get(i);
			if(element instanceof XWPFParagraph){
				XWPFParagraph para = (XWPFParagraph) element;
				if(para.getText().contains("forStart")
						||para.getText().contains("forEnd")
						||para.getText().contains("deleteRun")){
					doc.removeBodyElement(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * 替换table里的变量
	 * @param doc
	 * @param paramss
	 */
	public static void replaceInTables(XWPFDocument doc,Map<String, Object> paramss){
		Iterator<XWPFTable> it = doc.getTablesIterator();
		while (it.hasNext()) {
			XWPFTable table = it.next();
			List<XWPFTableRow> rows = table.getRows();
			for(XWPFTableRow row : rows){
				List<XWPFTableCell> cellList = row.getTableCells();
				for(XWPFTableCell cell : cellList){
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for(XWPFParagraph ph : paragraphs){
						replaceInPara(ph, paramss);
					}
				}
			}
		}
	}
	
	/**
	 * 替换段落里的变量
	 * 
	 * @param replaceInPara
	 */
	public static void replaceInParaUnion(XWPFDocument doc, Map<String, Object> paramss) {
		List<XWPFParagraph> forList = new ArrayList<XWPFParagraph>();
		// 基本内容替换
		boolean isFor = false;
		String eachKey = "";
		int forint = 0;
		for (int i = 0; i < doc.getParagraphs().size(); i++) {
			XWPFParagraph para = doc.getParagraphs().get(i);
			if (para.getText().contains("forStart")) {
				isFor = true;
				eachKey = para.getText().split(",")[0];
				forint = i;
			} else if (isFor) {
				if (para.getText().contains("forEnd")) {
					List<Map<String, Object>> forParams = (List<Map<String, Object>>) paramss.get(eachKey.trim());
					isFor = false;
					if ((forParams != null) && (forParams.size() > 0)) {
						for (int j = 1; j < forParams.size(); j++) {
							for (int u = 0; u < forList.size(); u++) {
								XmlCursor cursor = doc.getDocument().getBody().getPArray(i).newCursor();
								XWPFParagraph ar = doc.insertNewParagraph(cursor);
								ar.setIndentationFirstLine(forList.get(u).getIndentationFirstLine());
								for (int e = 0; e < forList.get(u).getRuns().size(); e++) {
									XWPFRun run = forList.get(u).getRuns().get(e);
									XWPFRun arrun = ar.createRun();
									arrun.setBold(run.isBold());
									arrun.setFontFamily(run.getFontFamily());
									arrun.setFontSize(run.getFontSize());
									arrun.setText(run.toString());
									arrun.setUnderline(run.getUnderline());
								}
		
								forParams.get(j).put("index", NumberToChn.NumberToChn(j + 1));
								forParams.get(j).put("numIndex", j + 1);
								i = createTableFun(ar,doc, forParams.get(j),i);
								i++;
							}
						}
					
						forParams.get(0).put("index", "一");
						forParams.get(0).put("numIndex", "1");
						for (int u = 0; u < forList.size(); u++) {
							int s = createTableFun(forList.get(u),doc,forParams.get(0),forint+u+1);
							i = s-forint-u-1 + i;
						}
					}else{
						for (int u = 0; u < forList.size(); u++) {
							replaceInPara(forList.get(u), paramss);
						}
					}
					forList = new ArrayList<XWPFParagraph>();
				} else {
					forList.add(doc.getParagraphs().get(i));
				}
			} else {
				i=createTableFun(para,doc,paramss,i);
			}
		}
	}

	private static void replaceInPara(XWPFParagraph para, Map<String, Object> paramss){
		if (matcher(para.getParagraphText()).find()) {
			boolean is1 = false;
			String is1Key = "";
			boolean is2 = false;
			boolean is21 = false;
			for(int i=0;i<para.getRuns().size();i++){
				if(is1){
					if(para.getRuns().get(i).toString().contains("}")){
						String s[] = para.getRuns().get(i).toString().split("}");
						if(s.length > 0){
							is1Key = is1Key + s[0];
						}
						is1 = false;
						if(s.length > 1){
							tihuanRun(para, i, s[1], para.getRuns().get(i));
						}else {
							tihuanRun(para, i, "", para.getRuns().get(i));
						}
						addRunByParagraph(para, i, paramss.get(is1Key)+"");
						is1Key = "";
						i++;
					}else{
						is1Key = is1Key + para.getRuns().get(i).toString();
						tihuanRun(para, i, "", para.getRuns().get(i));
					}
				}else if(is2){
					if("{".equals(para.getRuns().get(i).toString().charAt(0))){
						String s1 = "";
						if(para.getRuns().get(i).toString().split("{").length>1){
							s1 = para.getRuns().get(i).toString().split("{")[1];
							if(s1.contains("}")){
								String[] s1ar = s1.split("}");
								if(s1ar.length > 0){
									is1Key = s1ar[0];
								}
								
								is2 = false;
								addRunByParagraph(para, i, paramss.get(is1Key)+"");
								is1Key = "";
								i++;
							}else{
								is1Key = is1Key + s1;
								is21 = true;
							}
						}
					}if(is21){
						if(para.getRuns().get(i).toString().contains("}")){
							String[] sar = para.getRuns().get(i).toString().split("}");
							if(sar.length > 0){
								is1Key = is1Key + sar[0];
							}
							is21 = false;
							is2 = false;
							addRunByParagraph(para, i, paramss.get(is1Key)+"");
							is1Key = "";
							i++;
						}else{
							is1Key = is1Key + para.getRuns().get(i).toString();
						}
					}else{
						is2 = false;
					}
				}else if(para.getRuns().get(i).toString().contains("${")){
					String s1 = "";
					String[] sar = para.getRuns().get(i).toString().split("\\$\\{");
					if(sar.length>1){
						s1 = sar[1];
					}
					if(s1.contains("}") && s1.length()>1){
						String[]sqar = s1.split("}");
						is1Key = sqar[0];
						tihuanRun(para, i, "", para.getRuns().get(i));
						addRunByParagraph(para, i, paramss.get(is1Key)+"");
						if(sqar.length> 1){
							tihuanRun(para, i+1, sqar[1], para.getRuns().get(i));
						}
						is1Key = "";
						i++;
					}else{
						is1Key = s1;
						is1 = true;
						if(sar.length > 0){
							tihuanRun(para, i, sar[0], para.getRuns().get(i));
						}else {
							tihuanRun(para, i, "", para.getRuns().get(i));
						}
					}
					
				}else if(para.getRuns().contains("$")){
					is2 = true;
				}
			}
		}
	}
	
	/**
	 * 删除原un 替换新内容 
	 * @param para
	 * @param i
	 * @param value
	 * @param yuRun
	 */
	public static void tihuanRun(XWPFParagraph para,int i, String value,XWPFRun yuRun){
		XWPFRun run = para.insertNewRun(i);
		run.setText(value);
		run.setFontSize(yuRun.getFontSize());
		run.setFontFamily(yuRun.getFontFamily());
		run.setBold(yuRun.isBold());
		run.setUnderline(yuRun.getUnderline());
		para.removeRun(i+1);
	}
	
	/**
	 * 设备段落内容
	 * @param para
	 * @param i
	 * @param text
	 */
	private static void addRunByParagraph(XWPFParagraph para, int i,String text){
		XWPFRun run = para.insertNewRun(i);
		if(StringUtils.isEmpty(text) || "null".equals(text)){
			run.setText("");
		}else{
			run.setText(text);
		}
		if(i > 0){
			XWPFRun yuRun = para.getRuns().get(i-1);
			run.setFontSize(yuRun.getFontSize());
			run.setFontFamily(yuRun.getFontFamily());
			run.setBold(yuRun.isBold());
			run.setUnderline(yuRun.getUnderline());
		}
	}
	
	/**
	 * 替换段落里的变量
	 * 
	 * @param replaceInPara
	 */
	private static void replaceInPara1(XWPFParagraph para, Map<String, Object> paramss) {
		if (matcher(para.getParagraphText()).find()) {
			List<XWPFRun> runs;
			Matcher matcher;
			String runText = "";
			int fontSize = 10;
			String fontFamily = "";
			boolean fontBold = false;
			fontSize = para.getRuns().get(0).getFontSize();
			fontFamily = para.getRuns().get(0).getFontFamily();
			fontBold = para.getRuns().get(0).isBold();
			runs = para.getRuns();
			if (runs.size() > 0) {
				int j = runs.size();
				for (int i = 0; i < j; i++) {
					XWPFRun run = runs.get(0);
					String i1 = run.toString();
					runText += i1;
					para.removeRun(0);
				}
			}
			String[] subRunText = runText.split("}");
			int k = 0;
			for (String a : subRunText) {
				a += "}";
				matcher = matcher(a);
				if (matcher.find()) {
					while ((matcher = matcher(a)).find()) {
						a = matcher.replaceFirst(String.valueOf(paramss.get(matcher.group(1))));
					}
				}
				a = a.replace("{", "");
				a = a.replace("}", "");
				if ("null".equals(a)) {
					a = "     ";
				}
				if (a.indexOf("null") != -1) {
					a = a.replace("null", "");
				}
				para.insertNewRun(k);
				XWPFRun fr = para.insertNewRun(k);
				fr.setText(a);
				fr.setFontSize(fontSize);
				fr.setFontFamily(fontFamily);
				fr.setBold(fontBold);
				k = k + 1;
			}
		}
	}

	/**
	 * 替换表格的变量
	 * 
	 * @param replaceInTable
	 */
	public static void replaceInTable(XWPFDocument doc, Map<String, Object> paramss) {
		java.util.Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						replaceInPara1(para, paramss);
					}
				}
			}
		}
	}

	// 正则匹配字符串
	private static Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.{1,}{0,1})\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	public static boolean deleteFile(String path, String fileName) {
		File file = new File(path + "\\" + fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				// "删除文件失败!"
				return false;
			}
		} else {
			// "文件不存在!"
			return false;
		}
	}

	/**
	 * 关闭输入流
	 * 
	 * @param close
	 */
	public static void close(FileInputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("关闭输入流失败", e);
			}
		}
	}

	private static int createTableFun(XWPFParagraph para,XWPFDocument doc, Map<String, Object> forParamss,int i){
		if(para.getText().contains("createTableStart")){
			String[] str = para.getText().split("=-=");
			removeAllXWPFParagraph(para);
			String key = str[1];
			List<Map<String,Object>> vList = (List<Map<String,Object>>)forParamss.get(key);
			String[] titles = str[2].split(",");
			String[] vars = str[3].split(",");
			XmlCursor cursor = doc.getDocument().getBody().getPArray(i).newCursor();
			XWPFTable table = doc.insertNewTbl(cursor);
			XWPFTableRow taRow = table.getRow(0);
			for (int u = 0; u < titles.length; u++) {
				XWPFTableCell cell = taRow.getCell(u);
				if(cell == null){
					cell = taRow.createCell();
				}
				CTTc cttc = cell.getCTTc();  
				cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
				cell.setText(titles[u]);
			}
			
			if(vList != null){
				for(Map<String,Object> map : vList){
					XWPFTableRow row = table.createRow();
					for(int j = 0;j < vars.length; j++){
						XWPFTableCell cell = (row.getCell(j)==null)?row.createCell():row.getCell(j);
						cell.setText((map.get(vars[j])==null)?vars[j]:map.get(vars[j]).toString());
						CTTc cttc = cell.getCTTc();  
						cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
					}
				}
			}
			
			
			CTTbl ttbl = table.getCTTbl();
			CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
			CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
			tblWidth.setW(new BigInteger("10000"));
			tblWidth.setType(STTblWidth.DXA);
		}else{
			replaceInPara(para, forParamss);
		}
		return i;
	}
	
	/**
	 * 关闭输出流
	 * 
	 * @param close
	 */
	public static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("关闭输出流失败", e);
			}
		}
	}
	
	private static void removeAllXWPFParagraph(XWPFParagraph para){
		int size = para.getRuns().size();
		for(int i=0;i<size;i++){
			para.removeRun(0);
		}
		
	}
	
	
	public static void main(String[] args) {
		String str = "${2222";
		if(str.contains("${")){
			System.out.println("--");
		}
		String[] s = str.split("\\$\\{");
		System.out.println(s[0]);
		for(String a : s){
			System.out.println(a);
		}
	}
}
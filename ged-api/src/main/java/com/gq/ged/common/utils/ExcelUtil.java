package com.gq.ged.common.utils;

import com.gq.ged.order.controller.req.GedModel;
import jodd.util.BCrypt;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wrh on 2018/7/9.
 */
public class ExcelUtil {

    public  List<GedModel> createExcel(boolean isExcel2003) {
        try {
            InputStream is = new FileInputStream("e:/poiexcel/GQSC.xlsx");
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return excleIn(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public  List<GedModel> excleIn(Workbook wb) {
        List<GedModel> list = new ArrayList<GedModel>();
        GedModel gedOrder = null;
        try {
            // 获取选项卡对象 第0个选项卡 , 因为我们这里只有一个选项卡，如果你每个选项卡的内容是一样，可以通过循环取出
            Sheet hssfSheet = wb.getSheetAt(0);
         //   System.out.println(hssfSheet.getLastRowNum());
            // 循环取出每行的值
            int i=0;
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                String password="";
                Row hssfRow = hssfSheet.getRow(rowNum);
                gedOrder = new GedModel();
                //注意Poi读取的内容是有类型的，处理起来也jxl有所不同
                if(hssfRow.getCell(0)!=null)
                gedOrder.setId((int)hssfRow.getCell(0).getNumericCellValue());
                if(hssfRow.getCell(1)!=null)
                gedOrder.setUserId((int)hssfRow.getCell(1).getNumericCellValue());
                if(hssfRow.getCell(2)!=null)
                gedOrder.setContractCode(hssfRow.getCell(2).getStringCellValue());
                if(hssfRow.getCell(3)!=null)
                gedOrder.setCompanyName(hssfRow.getCell(3).getStringCellValue());
                if(hssfRow.getCell(4)!=null)
                gedOrder.setCompanyCardNum(hssfRow.getCell(4).getStringCellValue());
                if(hssfRow.getCell(5)!=null)
                gedOrder.setPersonCardNum(hssfRow.getCell(5).getStringCellValue());
                if(hssfRow.getCell(6)!=null)
                gedOrder.setLoanpose(hssfRow.getCell(6).getStringCellValue());
                if(hssfRow.getCell(7)!=null)
                gedOrder.setLoanType((int)hssfRow.getCell(7).getNumericCellValue());//借款类型
                if(hssfRow.getCell(8)!=null)
                gedOrder.setLoanAmount(new BigDecimal(hssfRow.getCell(8).getNumericCellValue()));//借款金额
                if(hssfRow.getCell(9)!=null)
                gedOrder.setReplyAmount(new BigDecimal(hssfRow.getCell(9).getNumericCellValue()));//借款金额
                if(hssfRow.getCell(10)!=null)
                gedOrder.setLoanTerm((int)hssfRow.getCell(10).getNumericCellValue());//借款期限
                if(hssfRow.getCell(11)!=null)
                gedOrder.setReplyTerm((int)hssfRow.getCell(11).getNumericCellValue());//批复期限
                if(hssfRow.getCell(12)!=null)
                gedOrder.setLoanDate(hssfRow.getCell(12).getDateCellValue());//放款日期
                if(hssfRow.getCell(13)!=null)
                gedOrder.setRateDay(new BigDecimal(hssfRow.getCell(13).getNumericCellValue()));//月利率
                if(hssfRow.getCell(14)!=null)
                gedOrder.setManagementAddr(hssfRow.getCell(14).getStringCellValue());//企业所在地
                if(hssfRow.getCell(15)!=null)
                gedOrder.setContactPhone(hssfRow.getCell(15).getStringCellValue());//联系电话
                if(hssfRow.getCell(16)!=null)
                gedOrder.setCashDeposit(new BigDecimal(hssfRow.getCell(16).getNumericCellValue()));//保证金
                if(hssfRow.getCell(17)!=null)
                gedOrder.setServiceFeeWay((int)hssfRow.getCell(17).getNumericCellValue());//服务费收取方式
                if(hssfRow.getCell(18)!=null)
                gedOrder.setServiceFee(new BigDecimal(hssfRow.getCell(18).getNumericCellValue()));//服务费
                if(hssfRow.getCell(19)!=null)
                gedOrder.setAccountManageFee(new BigDecimal(hssfRow.getCell(19).getNumericCellValue()));//账户管理费
                if(hssfRow.getCell(20)!=null)
                gedOrder.setRepaymentStyle(hssfRow.getCell(20).getStringCellValue());//还款方式
                if(hssfRow.getCell(21)!=null)
                gedOrder.setStatus((int)hssfRow.getCell(21).getNumericCellValue());//订单状态
                if(hssfRow.getCell(22)!=null)
                gedOrder.setCompanyAccount(hssfRow.getCell(22).getStringCellValue());//银行卡号
                if(hssfRow.getCell(23)!=null)
                gedOrder.setUserName(hssfRow.getCell(23).getStringCellValue());//用户姓名
                if(hssfRow.getCell(24)!=null)
                gedOrder.setUserType((int)hssfRow.getCell(24).getNumericCellValue());//用户类型\
                if(hssfRow.getCell(25)!=null)
                gedOrder.setGetCustId((int)hssfRow.getCell(25).getNumericCellValue());//客户Id
                if((int)hssfRow.getCell(24).getNumericCellValue()==0){
                    String mobile=hssfRow.getCell(15).getStringCellValue();
                    password = mobile.substring(mobile.length() - 6, mobile.length());
                    gedOrder.setPassword(BCrypt.hashpw(password, "$2a$10$LnBOX/Y75eUMFtUyN/THse"));
                }else{
                  String social=hssfRow.getCell(4).getStringCellValue();
                    password= social.substring(social.length() - 6, social.length());
                    gedOrder.setPassword(BCrypt.hashpw(password, "$2a$10$LnBOX/Y75eUMFtUyN/THse"));
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                i++;
                gedOrder.setOrderCode(sf.format(new Date())+""+i);
                list.add(gedOrder);
            }
        } catch (Exception e){

        }
             System.out.println("集合条数"+list.size());
          excleOut(list);
        return list;
    }

    /**
     * 针对Book类进行导出的操作
     *
     * @param list
     */
    public  void excleOut(List<GedModel> list) {
        // 创建Excel文档
        HSSFWorkbook hwb = new HSSFWorkbook();
        // 通过excle对象创建一个选项卡对象
        HSSFSheet sheet = hwb.createSheet("同步数据");
        System.out.println("准备导入数据!");
        GedModel gedModel = null;
        // 循环list创建行
        for (int i = 0; i < list.size(); i++) {
            // 新建一行
            HSSFRow row = sheet.createRow(i);
            gedModel = list.get(i);
            // 设置i+1行第0列的数据
            row.createCell(0).setCellValue(gedModel.getId());
            row.createCell(1).setCellValue(gedModel.getUserId());
            row.createCell(2).setCellValue(gedModel.getContractCode());
            row.createCell(3).setCellValue(gedModel.getCompanyName());
            row.createCell(4).setCellValue(gedModel.getCompanyCardNum());
            row.createCell(5).setCellValue(gedModel.getPersonCardNum());
            row.createCell(6).setCellValue(gedModel.getLoanpose());
            row.createCell(7).setCellValue(gedModel.getLoanType());
            row.createCell(8).setCellValue(gedModel.getLoanAmount().doubleValue());
            row.createCell(9).setCellValue(gedModel.getReplyAmount().doubleValue());
            row.createCell(10).setCellValue(gedModel.getLoanTerm());
            row.createCell(11).setCellValue(gedModel.getReplyTerm());
            Date theDate = gedModel.getLoanDate();
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            row.createCell(12).setCellValue(dff.format(theDate));
            row.createCell(13).setCellValue(gedModel.getRateDay().doubleValue());
            row.createCell(14).setCellValue(gedModel.getManagementAddr());
            row.createCell(15).setCellValue(gedModel.getContactPhone());
            row.createCell(16).setCellValue(gedModel.getCashDeposit().doubleValue());
            row.createCell(17).setCellValue(gedModel.getServiceFeeWay());
            row.createCell(18).setCellValue(gedModel.getServiceFee().doubleValue());
            row.createCell(19).setCellValue(gedModel.getAccountManageFee().doubleValue());
            row.createCell(20).setCellValue(gedModel.getRepaymentStyle());
            row.createCell(21).setCellValue(gedModel.getStatus());
            row.createCell(22).setCellValue(gedModel.getCompanyAccount());
            row.createCell(23).setCellValue(gedModel.getUserName());
            row.createCell(24).setCellValue(gedModel.getUserType());
            row.createCell(25).setCellValue(gedModel.getGetCustId());
            row.createCell(26).setCellValue(gedModel.getPassword());
            row.createCell(27).setCellValue(gedModel.getOrderCode());
        }
        OutputStream out = null;
        try {
            System.out.println("导入完成!");
            out = new FileOutputStream("e:/poiexcel/gedPoiOut3.xls");
            hwb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
      new ExcelUtil().createExcel(false);
    }
}

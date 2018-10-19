package com.resoft.credit.GedImportGedOrder.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.GedImportGedOrder.entity.CreGedImportGetOrder;
import com.resoft.credit.GedImportGedOrder.service.CreGedImportGetOrderService;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 冠e通线下数据导入给冠e贷Controller
 *
 * @author lb
 * @version 2018-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creGedImportGetOrder")
public class CreGedImportGetOrderController extends BaseController {

    @Autowired
    private CreGedImportGetOrderService creGedImportGetOrderService;

    @ModelAttribute
    public CreGedImportGetOrder get(@RequestParam(required = false) String id) {
        CreGedImportGetOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = creGedImportGetOrderService.get(id);
        }
        if (entity == null) {
            entity = new CreGedImportGetOrder();
        }
        return entity;
    }

    @RequiresPermissions("credit:creGedImportGetOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CreGedImportGetOrder creGedImportGetOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CreGedImportGetOrder> page = creGedImportGetOrderService.findCustomPage(new Page<CreGedImportGetOrder>(request, response), creGedImportGetOrder);
        model.addAttribute("page", page);
        return "app/credit/GedImportGedOrder/creGedImportGetOrderList";
    }

    @RequiresPermissions("credit:creGedImportGetOrder:view")
    @RequestMapping(value = "form")
    public String form(CreGedImportGetOrder creGedImportGetOrder, Model model) {
        model.addAttribute("creGedImportGetOrder", creGedImportGetOrder);
        return "app/credit/GedImportGedOrder/creGedImportGetOrderForm";
    }

    @RequiresPermissions("credit:creGedImportGetOrder:edit")
    @RequestMapping(value = "save")
    public String save(CreGedImportGetOrder creGedImportGetOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, creGedImportGetOrder)) {
            return form(creGedImportGetOrder, model);
        }
        creGedImportGetOrderService.save(creGedImportGetOrder);
        addMessage(redirectAttributes, "保存冠e通线下数据导入给冠e贷成功");
        return "redirect:" + Global.getAdminPath() + "/GedImportGedOrder/creGedImportGetOrder/?repage";
    }

    @RequiresPermissions("credit:creGedImportGetOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(CreGedImportGetOrder creGedImportGetOrder, RedirectAttributes redirectAttributes) {
        creGedImportGetOrderService.delete(creGedImportGetOrder);
        addMessage(redirectAttributes, "删除冠e通线下数据导入给冠e贷成功");
        return "redirect:" + Global.getAdminPath() + "/GedImportGedOrder/creGedImportGetOrder/?repage";
    }

    @RequestMapping(value = "upload")
    @ResponseBody
    public AjaxView upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        AjaxView ajaxView = new AjaxView();
        if (file != null) {
            String fileName = file.getOriginalFilename();
            if (fileName == null || "".equals(fileName)) {
                ajaxView.setFailed().setMessage("请传入文件(后缀名为xls或xlsx)!");
                return ajaxView;
            } else if (fileName.indexOf(".xlsx") == -1) {
                ajaxView.setFailed().setMessage("请选择正确的Excel文件(后缀名为xlsx)!");
                return ajaxView;
            } else {
                if (!file.isEmpty()) {
                    InputStream is;
                    XSSFWorkbook wb;
                    try {
                        is = file.getInputStream();
                        //获取输入流
                        wb = new XSSFWorkbook(is);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        ajaxView.setFailed().setMessage("获取流信息失败" + e1.toString());
                        return ajaxView;
                    }
                    XSSFSheet sheet = wb.getSheetAt(0);
                    int rowNum = sheet.getLastRowNum();
                    List<CreGedImportGetOrder> list = new ArrayList<>();
                    try {

                    for (int i = 1; i <=rowNum; i++) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            XSSFRow r = sheet.getRow(i);
                            CreGedImportGetOrder order = new CreGedImportGetOrder();
                            //参数校验  暂时不写
                            order.setContractNo(getValueFromCell(r.getCell(0)));
                            order.setCompanyName(getValueFromCell(r.getCell(1)));
                            order.setCompanyCardNo(getValueFromCell(r.getCell(2)));
                            order.setIdNumber(getValueFromCell(r.getCell(3)));
                            order.setLoanPurpose(getValueFromCell(r.getCell(4)));
                            order.setLoanType(getValueFromCell(r.getCell(5)));
                            order.setLoanAmount(getValueFromCell(r.getCell(6)));
                            order.setCreditAmount(getValueFromCell(r.getCell(7)));
                            order.setLoanTerm(getValueFromCell(r.getCell(8)));
                            order.setApproTerm(getValueFromCell(r.getCell(9)));
                            order.setLoanDate( sdf.parse(getValueFromCell(r.getCell(10))));
                            order.setMonthRate(getValueFromCell(r.getCell(11)));
                            if(null != r.getCell(12)){
                                order.setAddress(getValueFromCell(r.getCell(12)));
                            }
                            order.setContractPhone(getValueFromCell(r.getCell(13)));
                            order.setCashDeposit(getValueFromCell(r.getCell(14)));
                            order.setServiceFeeWay(getValueFromCell(r.getCell(15)));
                            order.setServiceFee(getValueFromCell(r.getCell(16)));
                            order.setManageFee(getValueFromCell(r.getCell(17)));
                            order.setRepaymentType(getValueFromCell(r.getCell(18)));
                            order.setStatus(getValueFromCell(r.getCell(19)));
                            order.setAccountCode(getValueFromCell(r.getCell(20)));
                            order.setUserName(getValueFromCell(r.getCell(21)));
                            order.setUserType(getValueFromCell(r.getCell(22)));
                            order.setCustId(getValueFromCell(r.getCell(23)));
                            order.setCityCode(getValueFromCell(r.getCell(24)));
                            order.setBankBranchName(getValueFromCell(r.getCell(25)));
                            order.setBankCode(getValueFromCell(r.getCell(26)));
                            //申请编号自动生成
/*							
                            order.setOrderCode(getValueFromCell(r.getCell(26)));
*/
                            //申请编号改为自动生成:
                            String orderNo1 = IdGen.uuid().substring(20);
                            String orderNo = "xianxia"+orderNo1;
                            order.setOrderCode(orderNo);
                            //导入日期
                            order.setBatchDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
                            list.add(order);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ajaxView.setFailed().setMessage("请把表格中的数据补全，再运行");
                            return ajaxView;
                        }
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try {
                            is.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (list.size() > 0 && list != null) {
                        String json = null;
                        try {
                            Map<String , Object>  data = new HashMap<>();
                             String aa = JsonTransferUtils.bean2Json(list);
                            data.put("jsonRes",aa);
                             json = JsonUtil.getJSONString(data);
                             logger.info(json);
                            //冠易贷处理线下订单任务
                            Facade.facade.savePushUnderInfo(json);
                                    //成功推送给冠e贷
                                 boolean flag = creGedImportGetOrderService.importCregedOrder(list);
                                   if(flag){
                                       ajaxView.setSuccess().setMessage("线下数据推送冠e贷成功，导入成功");
                                   }else{
                                       ajaxView.setFailed().setMessage("线下数据推送冠e贷成功，导入失败");
                                   }
                            }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return ajaxView;
    }
    public static String getValueFromCell(XSSFCell cell) {
        String ret = "";
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                ret = cell.getStringCellValue();
                System.out.println(ret);
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date d = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    ret = sdf.format(d);
                    System.out.println(ret);
                } else {
                    //防止数字变成科学数字法
                    DecimalFormat df = new DecimalFormat("0");
                    String number = df.format(cell.getNumericCellValue());
                    //截取小数点
                   // String number = String.valueOf(cell.getNumericCellValue());
                   //  ret = number.substring(0,number.indexOf("."));
                    ret =number;
                    System.out.println(ret);
                }
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                ret = cell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                ret = "";
                break;
            default:
                ret = "";
        }
        return ret;
    }
}
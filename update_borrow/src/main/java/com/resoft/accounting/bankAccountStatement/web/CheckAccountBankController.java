package com.resoft.accounting.bankAccountStatement.web;

import com.alibaba.fastjson.JSON;
import com.resoft.accounting.bankAccountStatement.entity.AccountNameForm;
import com.resoft.accounting.bankAccountStatement.util.BankStatementDateUtil;
import com.resoft.accounting.bankAccountStatement.util.ReadTextUtil;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.DataScopeFitter;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountBank;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountForm;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountStatement;
import com.resoft.accounting.bankAccountStatement.service.CheckAccountBankService;
import com.resoft.accounting.bankAccountStatement.service.CheckAccountStatementService;
import com.resoft.common.utils.ExcelReader;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 收款银行Controller
 *
 * @author yxh
 * @version 2018-7-12
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/bankAccountStatement")
public class CheckAccountBankController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CheckAccountBankService checkAccountBankService;
    @Autowired
    private CheckAccountStatementService checkAccountStatementService;

    @RequestMapping(value = "insertCheckAccountBank")
    @ResponseBody
    public AjaxView insertCheckAccountBank(CheckAccountBank checkAccountBank, Model model) {
        logger.info("保存收款银行账户。。。");
        AjaxView rtn = new AjaxView();
        try {
            checkAccountBankService.insertCheckAccountBank(checkAccountBank);
            logger.info("保存收款银行账户成功。。。");
            rtn.setSuccess().setMessage("保存收款银行账户成功!");
            rtn.put("id", checkAccountBank.getId());
            rtn.put("msg", "success");
            addMessage(model, "保存收款银行账户成功!");
        } catch (Exception e) {
            e.printStackTrace();
            rtn.setFailed().setMessage("保存收款银行账户失败!");
            rtn.put("msg", "fail");
            addMessage(model, "保存收款银行账户失败!");
            logger.error("保存收款银行账户失败!", e);
        }
        return rtn;
    }

    /**
     * 根据id查询CheckAccountBank
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "getCheckAccountById")
    @ResponseBody
    public AjaxView getCheckAccountById(Integer id, Model model) {
        AjaxView rtn = new AjaxView();
        try {
            CheckAccountBank checkAccountBank = checkAccountBankService.getCheckAccountById(id);
            rtn.setData(checkAccountBank);
            rtn.put("id", checkAccountBank.getId());
            rtn.put("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            rtn.put("msg", "fail");
        }
        return rtn;
    }

    @RequestMapping(value = "updateAccountBankByModel")
    @ResponseBody
    public AjaxView updateAccountBankByModel(CheckAccountBank checkAccountBank, Model model) {
        logger.info(checkAccountBank.toString());
        logger.info("修改收款银行账户信息。。。");
        AjaxView rtn = new AjaxView();
        try {
            checkAccountBankService.updateAccountBankByModel(checkAccountBank);
            rtn.setSuccess().setMessage("修改收款银行账户成功!");
            rtn.put("id", checkAccountBank.getId());
            rtn.put("msg", "success");
            addMessage(model, "修改收款银行账户成功!");
        } catch (Exception e) {
            e.printStackTrace();
            rtn.setFailed().setMessage("修改收款银行账户失败!");
            rtn.put("msg", "fail");
            addMessage(model, "修改收款银行账户失败!");
            logger.error("修改收款银行账户失败!", e);
        }
        return rtn;
    }


    /**
     * 修改状态
     *
     * @param checkAccountBank
     * @param model
     * @return
     */
    @RequestMapping(value = "updateCheckAccountBank")
    @ResponseBody
    public AjaxView updateCheckAccountBank(CheckAccountBank checkAccountBank, Model model) {
        logger.info("修改收款银行账户状态。。。");
        AjaxView rtn = new AjaxView();
        try {
            checkAccountBankService.updateCheckAccountBankStatus(checkAccountBank);
            rtn.setSuccess().setMessage("修改收款银行账户状态成功!");
            rtn.put("id", checkAccountBank.getId());
            rtn.put("msg", "success");
            addMessage(model, "修改收款银行账户状态成功!");
        } catch (Exception e) {
            e.printStackTrace();
            rtn.setFailed().setMessage("修改收款银行账户状态失败!");
            rtn.put("msg", "fail");
            addMessage(model, "修改收款银行账户状态失败!");
            logger.error("修改收款银行账户状态失败!", e);
        }
        return rtn;
    }

    // 收款账户列表
    @RequestMapping(value = {"checkAccountList", ""})
    public String checkAccountList(HttpServletRequest request, HttpServletResponse response, Model model, CheckAccountForm checkAccountForm) {
        MyMap paramMap = setParamMap(checkAccountForm);
        Page<MyMap> page = checkAccountBankService.checkAccountList(new Page<MyMap>(request, response), paramMap);
        model.addAttribute("page", page);
        model.addAttribute("headUrl", "/accounting/taskCenter/list");
        model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
        // 根据不同的url跳转到不同的页面
        return "app/accounting/bankAccountStatement/checkAccountBank";
    }


    private MyMap setParamMap(CheckAccountForm checkAccountForm) {
        MyMap paramMap = new MyMap();
        DataScopeFitter.companyDataScopeFilter(checkAccountForm);
        paramMap.put("sqlMap", checkAccountForm.getSqlMap());
        paramMap.put("receiveBankName", checkAccountForm.getReceiveBankName());
        paramMap.put("accountName", checkAccountForm.getAccountName());
        paramMap.put("accountNumber", checkAccountForm.getAccountNumber());


        paramMap.put("createTradeStartTime", checkAccountForm.getCreateTradeStartTime());
        paramMap.put("createTradeEndTime", checkAccountForm.getCreateTradeEndTime());
        paramMap.put("userName", checkAccountForm.getUserName());
        paramMap.put("branchBankName", checkAccountForm.getBranchBankName());
        paramMap.put("receiveAccountName", checkAccountForm.getReceiveAccountName());
        paramMap.put("receiveAccountNumber", checkAccountForm.getReceiveAccountNumber());
        paramMap.put("status", checkAccountForm.getStatus());
        return paramMap;
    }


    //=================银行流水账单======y==================================


    /**
     * 导入银行流水
     *
     * @param branchBankName
     * @param receiveAccountNumber
     * @param receiveAccountName
     * @param file
     * @return
     */
    @RequestMapping(value = "bankStatementImport")
    public String bankStatementImport(
                                        HttpServletRequest request,
                                        HttpServletResponse response,
                                        String branchBankName,
                                        String receiveAccountNumber,
                                        String receiveAccountName,
                                        MultipartFile file, Model model) {

        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
            Workbook workbook = null;
            if (!"xls".equals(suffixName) && !"xlsx".equals(suffixName)&& !"csv".equals(suffixName)) {
                throw new RuntimeException("你选择的不是Excel文件，请选择Excel格式的文件!");
            }
            String[] array = {"工行.csv",
                    "中信.xls", "农行.xls", "民生.xls", "建行.xls",
                    "中信.xlsx", "农行.xlsx", "民生.xlsx", "建行.xlsx"
            };
            boolean flag = Arrays.asList(array).contains(fileName);
            if (!flag) {
                throw new RuntimeException("你选择的模板不是指定的模板，请选择正确的模板!");
            }
            if ("工行.csv".equals(fileName)) {
                logger.info("导入工行流水。。。");
                List<String> dataList = ReadTextUtil.importCsv(inputStream);
                logger.info(dataList.toString());
                model.addAttribute("message", "工行流水导入成功！");
                loadList(request, response, model);
                return "app/accounting/bankAccountStatement/checkAccountBankStatement";
            }
            if ("xls".equals(suffixName)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if ("xlsx".equals(suffixName)) {
                workbook = new XSSFWorkbook(inputStream);
            }
            List<CheckAccountStatement> list = new ArrayList<CheckAccountStatement>();
            CheckAccountStatement statement = null;
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            if ("建行.xls".equals(fileName)||"建行.xlsx".equals(fileName)) {
                logger.info("导入建行流水。。。");
                Row row1 = sheet.getRow(1);
                Row row2 = sheet.getRow(2);
                Row row3 = sheet.getRow(3);
                String accountNumber = row1.getCell(1).getStringCellValue();//账号
                String openAccountPlace = row2.getCell(1).getStringCellValue();//开户机构
                String moneyType = row3.getCell(1).getStringCellValue();//币种
                for (int i = 6; i <= rows; i++) {
                    Row row = sheet.getRow(i);
                    String tradeCardNum = ExcelReader.getStringCellValue((HSSFCell) row.getCell(0));
                    String tradeDate = ExcelReader.getStringCellValue((HSSFCell) row.getCell(1));
                    tradeDate = BankStatementDateUtil.getTradeDate(tradeDate);
                    //交易地点
                    String tradingPlaces = ExcelReader.getStringCellValue((HSSFCell) row.getCell(3));
                    //支出金额
                    String expenseAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(4));
                    //收入金额
                    String incomeAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(5));
                    //账户余额
                    String accountBalance = ExcelReader.getStringCellValue((HSSFCell) row.getCell(6));
                    //对方账户
                    String accountNum = ExcelReader.getStringCellValue((HSSFCell) row.getCell(7));
                    //对方户名
                    String userName = ExcelReader.getStringCellValue((HSSFCell) row.getCell(8));
                    //币种
                    String bz = ExcelReader.getStringCellValue((HSSFCell) row.getCell(9));
                    //摘要
                    String zy = ExcelReader.getStringCellValue((HSSFCell) row.getCell(10));
                    //备注
                    Map<String, Object> json = new HashMap<String, Object>();
                    json.put("币种", bz);
                    json.put("摘要", zy);
                    statement = new CheckAccountStatement();
                    statement.setTradeDate(new SimpleDateFormat("yyyy-MM-dd").parse(tradeDate));
                    statement.setTradeAmount(new BigDecimal(expenseAmount));
                    statement.setEnterAccountAmount(new BigDecimal(incomeAmount));
                    statement.setUnAccountAmount(new BigDecimal(accountBalance));
                    statement.setUserName(userName);
                    statement.setBranchBankName(branchBankName);
                    statement.setRemark(JSON.toJSONString(json));
                    statement.setStatus(0);
                    statement.setReceiveAccountNumber(receiveAccountNumber);
                    statement.setReceiveAccountName(receiveAccountName);
                    //checkAccountStatementService.insertCheckAccountStatement(statement);
                    list.add(statement);
                }
                checkAccountStatementService.insertCheckAccountStatementBatch(list);
                model.addAttribute("message", "建行流水导入成功！");
            } else if ("民生.xls".equals(fileName)||"民生.xlsx".equals(fileName)) {
                logger.info("导入民生银行流水。。。");
            } else if ("农行.xls".equals(fileName)||"农行.xlsx".equals(fileName)) {
                logger.info("导入农行流水。。。");
                for (int i = 3; i <= rows; i++) {
                    Row row = sheet.getRow(i);
                    //交易日期
                    String tradeDate = ExcelReader.getStringCellValue((HSSFCell) row.getCell(0));
                    tradeDate = BankStatementDateUtil.getTradeDate(tradeDate);
                    //交易时间
                    String tradeTime = ExcelReader.getStringCellValue((HSSFCell) row.getCell(1));
                    tradeTime = BankStatementDateUtil.getTradeTime(tradeTime);
                    tradeDate = tradeDate + " " + tradeTime;
                    //收入金额
                    String incomeAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(2));
                    //支出金额
                    String expenseAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(3));
                    //本次余额
                    String accountBalance = ExcelReader.getStringCellValue((HSSFCell) row.getCell(4));
                    //对方账户
                    String accountNum = ExcelReader.getStringCellValue((HSSFCell) row.getCell(5));
                    //对方户名
                    String userName = ExcelReader.getStringCellValue((HSSFCell) row.getCell(6));
                    //交易行名称
                    String tradeBankName = ExcelReader.getStringCellValue((HSSFCell) row.getCell(7));
                    //交易方式
                    String tradeWay = ExcelReader.getStringCellValue((HSSFCell) row.getCell(8));
                    //交易渠道
                    String tradeQu = ExcelReader.getStringCellValue((HSSFCell) row.getCell(9));
                    //转账用途
                    String tradeYt = ExcelReader.getStringCellValue((HSSFCell) row.getCell(10));
                    //交易说明
                    String tradeSm = ExcelReader.getStringCellValue((HSSFCell) row.getCell(11));
                    //交易摘要
                    String tradeZy = ExcelReader.getStringCellValue((HSSFCell) row.getCell(12));
                    Map<String, Object> json = new HashMap<String, Object>();
                    json.put("交易方式", tradeWay);
                    json.put("交易渠道", tradeQu);
                    json.put("转账用途", tradeYt);
                    json.put("交易说明", tradeSm);
                    json.put("交易摘要", tradeZy);
                    statement = new CheckAccountStatement();
                    statement.setTradeDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tradeDate));
                    if (incomeAmount != null && !"".equals(incomeAmount)) {
                        statement.setTradeAmount(new BigDecimal(incomeAmount));
                    }
                    /*if (incomeAmount != null && !"".equals(incomeAmount)) {
                        statement.setEnterAccountAmount(new BigDecimal(incomeAmount));
                    }
                    if (accountBalance != null && !"".equals(accountBalance)) {
                        statement.setUnAccountAmount(new BigDecimal(accountBalance));
                    }*/
                    statement.setEnterAccountAmount(new BigDecimal(0.0));
                    if (incomeAmount != null && !"".equals(incomeAmount)) {
                        statement.setUnAccountAmount(new BigDecimal(incomeAmount));
                    }

                    statement.setUserName(userName);
                    statement.setBranchBankName(tradeBankName);
                    if (accountNum != null && !"".equals(accountNum)) {
                        statement.setAccountNumber(accountNum);
                    }
                    statement.setBankCode(branchBankName);
                    statement.setRemark(JSON.toJSONString(json));
                    statement.setStatus(0);
                    statement.setReceiveAccountNumber(receiveAccountNumber);
                    statement.setReceiveAccountName(receiveAccountName);
                    list.add(statement);
                }
               /* for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getEnterAccountAmount() != null) {
                        if (list.get(i).getEnterAccountAmount().compareTo(new BigDecimal(0.0)) == 1) {
                            throw new RuntimeException("导入的流水中存在已入账的流水，请重新选择后导入 ");
                        }
                    }
                }*/
                checkAccountStatementService.insertCheckAccountStatementBatch(list);
                model.addAttribute("message", "农行流水导入成功！");
            } else if ("中信.xls".equals(fileName)||"中信.xlsx".equals(fileName)) {
                logger.info("导入中信流水。。。");
                for (int i = 2; i <= rows; i++) {
                    Row row = sheet.getRow(i);
                    String tradeCardNum = ExcelReader.getStringCellValue((HSSFCell) row.getCell(0));
                    String tradeDate = ExcelReader.getStringCellValue((HSSFCell) row.getCell(1));
                    tradeDate = BankStatementDateUtil.getTradeDate(tradeDate);
                    String expenseAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(2));
                    String incomeAmount = ExcelReader.getStringCellValue((HSSFCell) row.getCell(3));
                    String accountBalance = ExcelReader.getStringCellValue((HSSFCell) row.getCell(4));
                    String opposite = ExcelReader.getStringCellValue((HSSFCell) row.getCell(5));
                    String acceptingInstitution = ExcelReader.getStringCellValue((HSSFCell) row.getCell(6));
                    String remark = ExcelReader.getStringCellValue((HSSFCell) row.getCell(7));
                    String status = ExcelReader.getStringCellValue((HSSFCell) row.getCell(8));
                    statement = new CheckAccountStatement();
                    statement.setTradeDate(new SimpleDateFormat("yyyy-MM-dd").parse(tradeDate));
                    statement.setTradeAmount(new BigDecimal(expenseAmount));
                    statement.setEnterAccountAmount(new BigDecimal(incomeAmount));
                    statement.setUnAccountAmount(new BigDecimal(accountBalance));
                    statement.setUserName(opposite);
                    statement.setAccountNumber(tradeCardNum);
                    statement.setBankCode(branchBankName);
                    statement.setBranchBankName(branchBankName);
                    statement.setRemark(remark);
                    statement.setStatus(0);
                    statement.setReceiveAccountNumber(receiveAccountNumber);
                    statement.setReceiveAccountName(receiveAccountName);
                    /**
                     * 对于单条重复导入：银行流水已匹配金额=0的，则允许重复导入，
                     * 新导入的流水覆盖原流水；银行流水已入账金额>0的，重复导入
                     * 后会提示“此银行流水已入账，不允许再次导入”
                     */
                    if(statement.getEnterAccountAmount().compareTo(new BigDecimal(0.0))==1){
                        throw new RuntimeException("此银行流水已入账，不允许再次导入");
                    }
                   /* if(statement.getEnterAccountAmount().compareTo(new BigDecimal(0.0))==0){
                        logger.info("银行流水已匹配金额=0，则允许重复导入，新导入的流水覆盖原流水");
                        checkAccountStatementService.deleteCheckAccountStatement(statement);
                        logger.info("原银行流水删除。。。");
                    }*/
                    //checkAccountStatementService.insertCheckAccountStatement(statement);
                    list.add(statement);
                }
                checkAccountStatementService.insertCheckAccountStatementBatch(list);

                model.addAttribute("message", "中信流水导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "导入失败，原因：" + e.getMessage());
        }
        loadList(request, response, model);
        return "app/accounting/bankAccountStatement/checkAccountBankStatement";
    }

    private void loadList(HttpServletRequest request, HttpServletResponse response, Model model) {
        MyMap paramMap = setParamMap(new CheckAccountForm());
        Page<MyMap> page = checkAccountStatementService.checkAccountStatementList(new Page<MyMap>(request, response), paramMap);
        List<MyMap> list = page.getList();
        logger.info(list.toString());

        BigDecimal sumAmount = new BigDecimal("0.0");
        for (MyMap en : list) {
            BigDecimal tradeAmount = (BigDecimal) en.get("tradeAmount");
            if (tradeAmount != null && !"".equals(tradeAmount)) {
                sumAmount = sumAmount.add(tradeAmount);
            }
        }
        model.addAttribute("sumAmount", sumAmount);
        model.addAttribute("page", page);
    }


    @RequestMapping(value = "loadBank")
    @ResponseBody
    public List<CheckAccountBank> loadBank() {
        return checkAccountBankService.loadBank();
    }

    @RequestMapping(value = "loadBankImport")
    @ResponseBody
    public List<CheckAccountBank> loadBankImport() {
        return checkAccountBankService.loadBankImport();
    }
    @RequestMapping(value = "loadReceiveBankNumber")
    @ResponseBody
    public List<CheckAccountBank> loadReceiveBankNumber(String receiveBankName) {
        return checkAccountBankService.loadReceiveBankNumber(receiveBankName);
    }

    @RequestMapping(value = "loadAccountName")
    @ResponseBody
    public AccountNameForm loadAccountName(String accountNum) {
        return checkAccountBankService.loadAccountName(accountNum);
    }


    // 银行流水列表

    /**
     * 查询
     *
     * @param request
     * @param response
     * @param model
     * @param checkAccountForm
     * @return
     */
    @RequestMapping(value = {"checkAccountStatementList", ""})
    public String checkAccountStatementList(HttpServletRequest request, HttpServletResponse response, Model model, CheckAccountForm checkAccountForm) {
        logger.info("查询流水======");
        MyMap paramMap = setParamMap(checkAccountForm);
        Page<MyMap> page = checkAccountStatementService.checkAccountStatementList(new Page<MyMap>(request, response), paramMap);
        List<MyMap> list = page.getList();
        logger.info(list.toString());

        BigDecimal sumAmount = new BigDecimal("0.0");
        for (MyMap en : list) {
            BigDecimal tradeAmount = (BigDecimal) en.get("tradeAmount");
            if (tradeAmount != null && !"".equals(tradeAmount)) {
                sumAmount = sumAmount.add(tradeAmount);
            }
        }
        model.addAttribute("sumAmount", sumAmount);
        model.addAttribute("page", page);
        return "app/accounting/bankAccountStatement/checkAccountBankStatement";
    }
}

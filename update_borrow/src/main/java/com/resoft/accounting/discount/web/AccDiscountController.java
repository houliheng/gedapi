package com.resoft.accounting.discount.web;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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

import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.discount.entity.AccDiscount;
import com.resoft.accounting.discount.entity.DiscountVo;
import com.resoft.accounting.discount.service.AccDiscountService;
import com.resoft.accounting.discount.service.DiscountVoService;
import com.resoft.accounting.discountStream.entity.AccDiscountStream;
import com.resoft.accounting.discountStream.service.AccDiscountStreamService;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.common.utils.ExcelReader;
import com.resoft.credit.GedImportGedOrder.entity.CreGedImportGetOrder;
import com.resoft.credit.GedImportGedOrder.service.CreGedImportGetOrderService;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 贴息表Controller
 * @author gsh
 * @version 2018-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/accDiscount")
public class AccDiscountController extends BaseController {

	@Autowired
	private AccDiscountService accDiscountService;
	@Autowired
	private DiscountVoService discountVoService;
	@Autowired
	private StaContractStatusService staContractStatusService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private CompensatoryDetailService compensatoryDetailService;
	@Autowired
	private AccDiscountStreamService accDiscountStreamService;
	@Autowired
	private CreGedImportGetOrderService gedimportOrderService;
	@ModelAttribute
	public AccDiscount get(@RequestParam(required=false) String id) {
		AccDiscount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accDiscountService.get(id);
		}
		if (entity == null){
			entity = new AccDiscount();
		}
		return entity;
	}
	
	@RequiresPermissions("accounting:accDiscount:view")
	@RequestMapping(value = {"list", ""})
	public String list(DiscountVo discountVo, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
		if (discountVo != null && discountVo.getCompany() != null && !StringUtils.isEmpty(discountVo.getCompany().getId())) {
			Office office = new Office();
			office.setId(discountVo.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(discountVo.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				discountVo.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				discountVo.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				discountVo.setOrgLevel4(office);
			}
		}
		Page<DiscountVo>  page = null;
		BigDecimal discountCount = new BigDecimal("0.00");
		if ("button".equals(queryFlag)) {
			page = discountVoService.findPage(new Page<DiscountVo>(request, response), discountVo);
			List<DiscountVo> discountVos = page.getList();
			List<DiscountVo> discountVoList = new ArrayList<>();
			if (discountVos.size() >0) {
				for(DiscountVo discountV:discountVos){
					if (StringUtils.isNotBlank(discountV.getStayAmount().toString()) && discountV.getStayAmount().toString().contains("-")) {
						discountV.setStayAmount(new BigDecimal("0.00"));
					}
					AccDiscountStream accDiscountStream = accDiscountStreamService.queryDisStrBycontractNoAndPer(discountV.getContractNo(),discountV.getPeriodNum());
					if (accDiscountStream == null) {
						discountCount = discountCount.add(discountV.getDiscountFee());
						discountVoList.add(discountV);
					}
				}
				page.setList(discountVoList);
			}
			
		}
		 
		model.addAttribute("page", page);
		model.addAttribute("discountCount",discountCount);
		return "app/accounting/discount/accDiscountList";
	}

	@RequiresPermissions("accounting:accDiscount:view")
	@RequestMapping(value = "form")
	public String form(AccDiscount accDiscount, Model model) {
		model.addAttribute("accDiscount", accDiscount);
		return "app/accounting/discount/accDiscountForm";
	}

	@RequiresPermissions("accounting:accDiscount:edit")
	@RequestMapping(value = "save")
	public String save(AccDiscount accDiscount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accDiscount)){
			return form(accDiscount, model);
		}
		accDiscountService.save(accDiscount);
		addMessage(redirectAttributes, "保存贴息表成功");
		return "redirect:"+Global.getAdminPath()+"/discount/accDiscount/?repage";
	}
	
	@RequiresPermissions("accounting:accDiscount:edit")
	@RequestMapping(value = "delete")
	public String delete(AccDiscount accDiscount, RedirectAttributes redirectAttributes) {
		accDiscountService.delete(accDiscount);
		addMessage(redirectAttributes, "删除贴息表成功");
		return "redirect:"+Global.getAdminPath()+"/discount/accDiscount/?repage";
	}
	
	
	@RequestMapping(value = "discountLeading")
	public String formBatch(AccDiscount accDiscount,Model model){
		model.addAttribute("accDiscount",accDiscount);
		return "app/accounting/discount/accDiscountImport";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "upload")
	public AjaxView uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String operateName = UserUtils.getUser().getName();
		AjaxView ajaxView = new AjaxView();
		String message = null;
		String contractNo = request.getParameter("contractNo");
		List<AccDiscountStream> accDiscountStream2 = accDiscountStreamService.getByContract(contractNo);
		if(accDiscountStream2.size()>0) {
			ajaxView.setFailed().setMessage("该合同已经导入！");
			return ajaxView;
		}
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (fileName == null || "".equals(fileName)) {
				ajaxView.setFailed().setMessage("请传入文件(后缀名为xls或xlsx)!");
				return ajaxView;
			} else if (fileName.indexOf(".xls") == -1) {
				ajaxView.setFailed().setMessage("请选择正确的Excel文件(后缀名为xls)!");
				return ajaxView;
			}else if (fileName.endsWith(".xlsx")) {
				ajaxView.setFailed().setMessage("请选择正确的Excel2003-2007格式的文档!");
				return ajaxView;
			} else {
				
				if (!file.isEmpty()) {
		            InputStream is;
		           HSSFWorkbook wb;
					try {
						is = file.getInputStream();

						//获取输入流
		                wb = new HSSFWorkbook(is);
		                
					} catch (Exception e1) {
						e1.printStackTrace();
						ajaxView.setFailed().setMessage("获取流信息失败" + e1.toString());
						return ajaxView;
					}
		            try {
		            	int rowNum = ExcelReader.getRowNum(0, wb);
			            HSSFSheet sheet = wb.getSheetAt(0);
			            List<AccDiscount> list = new ArrayList<AccDiscount>();
			            for(int i=1;i<=rowNum;i++){
			            	try {
			            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			                	HSSFRow row = sheet.getRow(i);
			                	StaContractStatus  contract = staContractStatusService.getContractByContractNo(ExcelReader.getStringCellValue(row.getCell(0)));
			                	if (contract == null) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行不存在“"+ExcelReader.getStringCellValue(row.getCell(0))+"”合同号");
								}else if (contract != null && ("0700".equals(contract.getRepayContractStatus()) || "0900".equals(contract.getRepayContractStatus()) || "1800".equals(contract.getRepayContractStatus()))) {
									return ajaxView.setFailed().setMessage("第" + i +"行“"+ ExcelReader.getStringCellValue(row.getCell(0))+"”合同已结清不允许导入");
								}
			                	AccDiscount discount = new AccDiscount();
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(0)))) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行缺少合同号");
								}
			                	row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			                	discount.setContractNo(row.getCell(0).getStringCellValue());
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(1)))) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行缺少期数");
								}
			                	row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			                	if (!StringUtils.isNumeric(ExcelReader.getStringCellValue(row.getCell(1)))) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行“"+ExcelReader.getStringCellValue(row.getCell(1))+"”数据格式错误");
								}
			                	discount.setPeriodNum(row.getCell(1).getStringCellValue());
			                	try {
			                		if (StringUtils.isNotEmpty(ExcelReader.getStringCellValue(row.getCell(2)))) {
			                			BigDecimal trasfer = new BigDecimal(ExcelReader.getStringCellValue(row.getCell(2)));
									}
								} catch (Exception e) {
									return ajaxView.setFailed().setMessage("第" + i +"行“"+ExcelReader.getStringCellValue(row.getCell(2)) +"”数据格式不对");
								}
			                	try {
			                		if (StringUtils.isNotEmpty(ExcelReader.getStringCellValue(row.getCell(3)))) {
			                			BigDecimal trasfer = new BigDecimal(ExcelReader.getStringCellValue(row.getCell(3)));
									}
								} catch (Exception e) {
									return ajaxView.setFailed().setMessage("第" + i +"行“"+ExcelReader.getStringCellValue(row.getCell(3))+ "”数据格式不对");
								}
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(2)))) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行缺少贴息金额");
								}
			                	discount.setDiscountFee(ExcelReader.getStringCellValue(row.getCell(2)));
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(3)))) {
									
								}else{
									discount.setFactDiscountFee(ExcelReader.getStringCellValue(row.getCell(3)));
								}
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(4)))) {
									
								}else if (StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(4)))) {
									try{  
										sdf.format(row.getCell(4).getDateCellValue()); 
										discount.setDiscountDate(sdf.parse(sdf.format(row.getCell(4).getDateCellValue())));
							        }catch(Exception e){
							        	return ajaxView.setFailed().setMessage("第" + i + "行“"+row.getCell(4).getDateCellValue()+"”日期格式错误");
							        }
								}else {
									discount.setDiscountDate(sdf.parse(sdf.format(row.getCell(4).getDateCellValue())));
								}
			                	/*if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(3))) && StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(4)))) {
			                		return ajaxView.setFailed().setMessage("第" + i + "行缺少实贴金额");
								}*/
			                	//-----------------贴息金额实贴金额不相等
			                	String result = getDeductResultTemp(ExcelReader.getStringCellValue(row.getCell(1)),ExcelReader.getStringCellValue(row.getCell(0)));
			                	if(!(ExcelReader.getStringCellValue(row.getCell(2)).equals(ExcelReader.getStringCellValue(row.getCell(3))))){
			                		
			                			if ("1".equals(result)) {
			                				return ajaxView.setFailed().setMessage("第" + i + "行应贴和实贴金额不相等");
										}
			                			
			                	}
			                	//-----------------
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(4))) && StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(3))) && "2".equals(result)) {
			                		return ajaxView.setFailed().setMessage("该期未结清，" + i + "行不允许存在实贴金额和贴息日期");
								}
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(3))) && StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(4))) && "2".equals(result)) {
			                		return ajaxView.setFailed().setMessage("该期未结清，" + i + "行不允许存在实贴金额和贴息日期");
								}
			                	if (StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(3))) && StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(4))) && "2".equals(result)) {
			                		return ajaxView.setFailed().setMessage("该期未结清，" + i + "行不允许存在实贴金额和贴息日期");
								}
			                	if (StringUtils.isBlank(ExcelReader.getStringCellValue(row.getCell(4))) && StringUtils.isNotBlank(ExcelReader.getStringCellValue(row.getCell(3))) && "1".equals(result)) {
			                		return ajaxView.setFailed().setMessage("第" + i +"行“" +ExcelReader.getStringCellValue(row.getCell(0))+"”合同已经贴息不允许导入");
								}
			                	row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			                	discount.setRemarks(row.getCell(5).getStringCellValue());
			                	discount.setOperateName(operateName);
			                	if (StringUtils.isNotEmpty(ExcelReader.getStringCellValue(row.getCell(3)))) {
			                		discount.setDiscountPerson(operateName);
								}
			                	if (StringUtils.isNotBlank(contractNo) && !contractNo.equals(row.getCell(0).getStringCellValue())) {
									return ajaxView.setFailed().setMessage("合同号与上传合同号不一致");
								}else{
									list.add(discount);
								}
			                } catch (Exception e) {
			                	ajaxView.setFailed().setMessage("数据库格式错误！第" + i + "行" );
			                    logger.error("解析失败",e);
			                    return ajaxView;
			                }
			            }
			            if (list.size() > 0) {
			            	message = accDiscountService.importDiscount(list,contractNo);
			    			if (message == null) {
			    				ajaxView.setSuccess().setMessage("导入成功");
			    			}else{
			    				ajaxView.setFailed().setMessage(message);
			    			}
						}else{
							ajaxView.setFailed().setMessage("文件内容为空");
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}finally {
						try {  
							is.close(); 
					    } catch (Exception e) {  
					    	logger.error(e.getMessage()); 
					    }
					}
		        }
			}
		} else {
			 return ajaxView.setFailed().setMessage("请重新打开小窗口上传!");
		}
		
        return ajaxView;
	}
	
	private String getDeductResultTemp(String periodNum, String contractNo) {
		List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(contractNo,periodNum);
			DeductResultTemp deductResultTemp = deductResultTemps.get(0);
			if ("0100".equals(deductResultTemp.getRepayPeriodStatus()) || "0400".equals(deductResultTemp.getRepayPeriodStatus()) || "0500".equals(deductResultTemp.getRepayPeriodStatus())) {
				return "1";
			}else if ("0200".equals(deductResultTemp.getRepayPeriodStatus()) || "0300".equals(deductResultTemp.getRepayPeriodStatus())){
				return "2";
			}else {
				return "";
			}	
	}

	@RequestMapping(value = "discountConfirm")
	public String discountConfirm(AccDiscount accDiscount, Model model,HttpServletRequest request) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		accDiscount = accDiscountService.findAccDisCountByContractNoAndPeriodNum(accDiscount);
		Contract contract = null;
		CompensatoryDetail compensatoryDetail = null;
		CreGedImportGetOrder gedImportGetOrder = gedimportOrderService.findCreGedImportByContractNo(accDiscount.getContractNo());
		if (StringUtils.isNotBlank(accDiscount.getContractNo()) && StringUtils.isNotBlank(accDiscount.getPeriodNum())) {
			compensatoryDetail = compensatoryDetailService.getCompenyByContractAndPerNum(accDiscount.getContractNo(),accDiscount.getPeriodNum());
			contract = contractService.getContractByContractNo(accDiscount.getContractNo());
			accDiscount.setDiscountAccount(Global.getConfig("discouAccount"));
			model.addAttribute("fromCapitalNo",Global.getConfig("capitalNo"));
		}
		if (compensatoryDetail != null) {
			model.addAttribute("toAccount",compensatoryDetail.getCompensatoryAccount());
			model.addAttribute("toCapitalNo",compensatoryDetail.getCustId());
			model.addAttribute("type","1");//代表代偿账户
		}else if (contract != null) {
			model.addAttribute("toAccount",contract.getRecBankcardNo());
			model.addAttribute("toCapitalNo",contract.getCapitalTerraceNo());
			model.addAttribute("type","0");//代表出借人账户
		}else if (gedImportGetOrder != null) {
			model.addAttribute("toAccount",gedImportGetOrder.getAccountCode());
			model.addAttribute("toCapitalNo",gedImportGetOrder.getCustId());
			model.addAttribute("type","0");//代表出借人账户
		}
		accDiscount.setDiscountDate(sdf.parse(sdf.format(new Date())));
		model.addAttribute("accDiscount",accDiscount);
		return "app/accounting/discount/discountConfirm";
	}
}
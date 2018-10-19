package com.resoft.accounting.discount.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.resoft.accounting.discount.dao.AccDiscountDao;
import com.resoft.accounting.discount.entity.AccDiscount;
import com.resoft.accounting.discountStream.entity.AccDiscountStream;
import com.resoft.accounting.discountStream.service.AccDiscountStreamService;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 贴息表Service
 * @author gsh
 * @version 2018-05-18
 */
@Service
@Transactional(readOnly = true)
public class AccDiscountService extends CrudService<AccDiscountDao, AccDiscount> {
	private static final Logger logger = LoggerFactory.getLogger(AccDiscountService.class);
	@Autowired
	private AccDiscountStreamService accDiscountStreamService;
	public AccDiscount get(String id) {
		return super.get(id);
	}
	
	public List<AccDiscount> findList(AccDiscount accDiscount) {
		return super.findList(accDiscount);
	}
	
	public Page<AccDiscount> findPage(Page<AccDiscount> page, AccDiscount accDiscount) {
		return super.findPage(page, accDiscount);
	}
	
	public void save(AccDiscount accDiscount) {
		super.save(accDiscount);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccDiscount accDiscount) {
		super.delete(accDiscount);
	}
	
	@Transactional(readOnly = false)
	public void deleteDiscountByContractAndPeriodNum(AccDiscount accDiscount) {
		dao.deleteDiscountByContractAndPeriodNum(accDiscount.getContractNo(),accDiscount.getPeriodNum());;
	}
	@Transactional(readOnly = false)
	public String importDiscount(List<AccDiscount> accDiscounts,String contractNo) {
		String message = null;
		try {
			if (accDiscounts.size() >0) {
				for(AccDiscount accDiscou:accDiscounts){
				//	List<AccDiscount> accDiscountList = dao.findAccDiscountsByContractNo(accDiscou.getContractNo());
					AccDiscountStream accDiscountStream = accDiscountStreamService.queryDisStrBycontractNoAndPer(accDiscou.getContractNo(),accDiscou.getPeriodNum());
					if (accDiscountStream == null) {
						deleteDiscountByContractAndPeriodNum(accDiscou);
					}
				}
			}
			for(int i=0;i<accDiscounts.size();i++){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					AccDiscountStream accDiscountStream = accDiscountStreamService.queryDisStrBycontractNoAndPer(accDiscounts.get(i).getContractNo(),accDiscounts.get(i).getPeriodNum());
					if (accDiscountStream == null) {
						if (accDiscounts.get(i).getDiscountDate() != null &&  accDiscounts.get(i).getFactDiscountFee() != null && StringUtils.isNotEmpty(accDiscounts.get(i).getFactDiscountFee()) && StringUtils.isNotBlank(formatter.format(accDiscounts.get(i).getDiscountDate())) && StringUtils.isNotBlank(accDiscounts.get(i).getFactDiscountFee()) && StringUtils.isNotBlank(accDiscounts.get(i).getDiscountFee())) {
							AccDiscountStream accDiscou = accDiscountStreamService.queryDisStrBycontractNoAndPer(accDiscounts.get(i).getContractNo(),accDiscounts.get(i).getPeriodNum());
							if (accDiscou != null) {
								accDiscountStreamService.delete(accDiscou);
							}
							AccDiscountStream  accdiscountStream = new AccDiscountStream();
							accdiscountStream.setContractNo(accDiscounts.get(i).getContractNo());
							accdiscountStream.setPeriodNum(accDiscounts.get(i).getPeriodNum());
							accdiscountStream.setDiscountFee(new BigDecimal(accDiscounts.get(i).getDiscountFee()));
							accdiscountStream.setSeqNo(OutInterfaceUtils.makeSeqNo());
							accdiscountStream.setCustName(accDiscounts.get(i).getOperateName());
							accdiscountStream.setDiscountDate(accDiscounts.get(i).getDiscountDate());
							accdiscountStream.setDiscountStatus("0");
							accdiscountStream.setFactDiscountFee(new BigDecimal(accDiscounts.get(i).getFactDiscountFee()));
							accDiscountStreamService.save(accdiscountStream);
						}
						save(accDiscounts.get(i));
					}
					
				} catch (Exception e) {
				logger.error("保存失败",e);
				return	message = "合同号"+accDiscounts.get(i).getContractNo()+"导入失败"+"已成功导入"+(i)+"条";	
				}
					
			}
		} catch (Exception e) {
			logger.error("保存失败",e);
			return message = e.getMessage();
		}
		
		
		return message;
	}
	
	
	public static  Workbook getWorkBook(MultipartFile file) throws IOException{
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        //获取excel文件的io流
        InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
        if(fileName.endsWith("xls")){
           //2003
            workbook = new HSSFWorkbook(is);
         }else if(fileName.endsWith("xlsx")){
          //2007 及2007以上
           workbook = new XSSFWorkbook(is);
            }
        return workbook;
    }
	
	
	public AccDiscount findAccDisCountByContractNoAndPeriodNum(AccDiscount accDiscount){
		return dao.findAccDisCountByContractNoAndPeriodNum(accDiscount);
	}

	@Transactional(readOnly = false)
    public void saveUnderInterest(CheckApprove checkApprove) {
		dao.deleteAccDiscount(checkApprove.getUnderContractNo());
		BigDecimal returnServiceFee = checkApprove.getReturnServiceFee();
		String approPeriodValue = checkApprove.getApproPeriodValue();
		BigDecimal returnMonthMoney = returnServiceFee.divide(new BigDecimal(approPeriodValue), 2, BigDecimal.ROUND_HALF_UP);
		int periodValue = Integer.parseInt(approPeriodValue);
		for (int i = 1; i <= periodValue; i++) {
			AccDiscount discount = new AccDiscount();
			discount.setContractNo(checkApprove.getUnderContractNo());
			discount.setPeriodNum(String.valueOf(i));
			discount.setDiscountFee(returnMonthMoney.toString());
			discount.setOperateName(UserUtils.getUser().getLoginName());
			discount.setOperateOrgName(UserUtils.getUser().getOffice().getName());
			discount.setOperateId(UserUtils.getUser().getId());
			discount.setOperateOrgId(UserUtils.getUser().getOffice().getName());
			save(discount);
		}
	}
	
	public List<AccDiscount> findAccDiscountsByContractNo(String contractNo){
		return dao.findAccDiscountsByContractNo(contractNo);
	}
	
}
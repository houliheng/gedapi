package com.resoft.credit.contract.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.dao.ContractTemplateDao;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.ContractTemplate;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;

@Service("contractTemplateService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ContractTemplateService extends CrudService<ContractTemplateDao, ContractTemplate> {

	@Autowired
	private ContractDao contractDao;
	public List<ContractTemplate> findList(ContractTemplate contractTemplate) {
		return super.findList(contractTemplate);
	}

	public Page<ContractTemplate> findPage(Page<ContractTemplate> page, ContractTemplate contractTemplate) {
		return super.findPage(page, contractTemplate);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(ContractTemplate contractTemplate) {
		super.save(contractTemplate);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void insert(ContractTemplate contractTemplate) {
		contractTemplate.setId(IdGen.uuid());
		super.dao.insert(contractTemplate);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void update(ContractTemplate contractTemplate) {
		super.dao.update(contractTemplate);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(ContractTemplate contractTemplate) {
		super.delete(contractTemplate);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateName(ContractTemplate ctl) {
		super.dao.updateName(ctl);
	}

	/**
	 * 点击打印 弹出合同打印框 选择所需打印的合同模板类型， 点击打印，打印出对应的合同模板，将数据库中的数据注入到模板中，并且打印出来
	 * 
	 * @param destFile
	 *            将要生成的目标PDF文件路径
	 */
	@Transactional(value = "CRE", readOnly = false)
	public static void getPdfFile(String templateFile, String destFileName) throws IOException, DocumentException {
		Map<String, String> params = Maps.newHashMap();
		params.put("xm", "李磊");
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			destFile.delete();
		}
		PdfReader reader = new PdfReader(templateFile);
		// 将要生成的目标PDF文件
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
		// 使用中文字体
		BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// 取出报表中的所有字段
		AcroFields fields = stamper.getAcroFields();
		fields.addSubstitutionFont(baseFont);
		for (Iterator<Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
			Entry<String, String> entry = iter.next();
			fields.setField(entry.getKey(), entry.getValue());
		}
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
	}

	@Transactional(value = "CRE", readOnly = false)
	public void generateWordFile(String templateFile, String targetFile, String contractNo) throws IOException, DocumentException {
		Map<String, Object> params = generateParams(contractNo);
	}

	public Map<String, Object> generateParams(String contractNo) {
		Map<String, Object> params = Maps.newHashMap();
		Contract contract = contractDao.getContractByContractNo(contractNo);
		params.put("xm", contract.getCustName());
		params.put("hth", contract.getContractNo());
		return params;
	}

}
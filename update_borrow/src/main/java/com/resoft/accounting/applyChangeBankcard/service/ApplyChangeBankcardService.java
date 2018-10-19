package com.resoft.accounting.applyChangeBankcard.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.accounting.applyChangeBankcard.dao.ApplyChangeBankcardDao;
import com.resoft.accounting.applyChangeBankcard.entity.ApplyChangeBankcard;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractTemp;
import com.resoft.accounting.fdfs.Manager;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 银行卡变更申请Service
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class ApplyChangeBankcardService extends CrudService<ApplyChangeBankcardDao, ApplyChangeBankcard> {
	private static final Logger logger = LoggerFactory.getLogger(ApplyChangeBankcardService.class);
	@Autowired
	private AccContractDao accContractDao;

	@Autowired
	private AreaService areaService;// 区域地质service

	@Autowired
	private AccVieoPathService accVieoPathService;

	public ApplyChangeBankcard get(String id) {
		return super.get(id);
	}

	public List<ApplyChangeBankcard> findList(ApplyChangeBankcard applyChangeBankcard) {
		return super.findList(applyChangeBankcard);
	}

	public Page<ApplyChangeBankcard> findPage(Page<ApplyChangeBankcard> page, ApplyChangeBankcard applyChangeBankcard) {
		return super.findPage(page, applyChangeBankcard);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void save(ApplyChangeBankcard applyChangeBankcard) {
		super.save(applyChangeBankcard);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void delete(ApplyChangeBankcard applyChangeBankcard) {
		super.delete(applyChangeBankcard);
	}

	@Transactional(value = "ACC", readOnly = false)
	public ApplyChangeBankcard getApplyChangeBankcardByContractNo(ApplyChangeBankcard applyChangeBankcard) {
		return this.dao.getApplyChangeBankcardByContractNo(applyChangeBankcard);
	}

	@Transactional(value = "ACC", readOnly = false)
	public List<ApplyChangeBankcard> getApplyChangeBankcardByContractNoFail(String contractNo) {
		return this.dao.getApplyChangeBankcardByContractNoFail(contractNo);
	}

	public Contract validateIsNotBlank(ApplyChangeBankcard applyChangeBankcard) {
		Contract contract = accContractDao.findContractByContractNo(applyChangeBankcard.getContractNo());
		ApplyChangeBankcard bankcard = getApplyChangeBankcardByContractNo(applyChangeBankcard);
		if (bankcard != null) {
			contract.setRepBankcardNo(bankcard.getNewRepBankcardNo()); // 银行行号
			contract.setRepBankcardName(bankcard.getNewRepBankcardName());// 账户名称
			contract.setRepBank(bankcard.getNewRepBank()); // 银行
			contract.setRepBankName(bankcard.getNewRepBankName()); // 网点
			contract.setRepBankProvince(bankcard.getNewRepBankProvince());// 省
			contract.setRepBankCity(bankcard.getNewRepBankCity());// 市
			contract.setRepBankDistinct(bankcard.getNewRepBankDistinct());// 区
			contract.setRepBankDetail(bankcard.getNewRepBankDetail());// 详细
		}
		return contract;
	}

	// 省市级联数据加载
	public LinkedHashMap<String, String> loadAreaData(String areaCode) {
		Map<String,String> param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(areaCode)) {
			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}

	// 省市级联数据加载
	public List<ContractTemp> loadAreaDataObject(String areaCode) {
		Map<String, String> param = Maps.newConcurrentMap();
		List<ContractTemp> temps = Lists.newArrayList();
		ContractTemp contractTemp = null;
		if (StringUtils.isNotEmpty(areaCode)) {
			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					contractTemp = new ContractTemp();
					contractTemp.setAddressId(mp.get("id"));
					contractTemp.setAddressValue(mp.get("name"));
					temps.add(contractTemp);
				}
			}
		}
		return temps;
	}

	@Transactional(value = "ACC", readOnly = false)
	public boolean saveApplyChangeBankcardData(MultipartHttpServletRequest request, Model model) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ApplyChangeBankcard applyChangeBankcard = new ApplyChangeBankcard();
		String contractNo = request.getParameter("contractNo");
		String newRepBankcardNo = request.getParameter("newRepBankcardNo");
		String newRepBankcardName = request.getParameter("newRepBankcardName");
		String newRepBank = request.getParameter("newRepBank");
		String newRepBankName = request.getParameter("newRepBankName");
		String newRepBankProvince = request.getParameter("newRepBankProvince");
		String newRepBankCity = request.getParameter("newRepBankCity");
		String newRepBankDistinct = request.getParameter("newRepBankDistinct");
		String newRepBankDetail = request.getParameter("newRepBankDetail");
		Contract contract = accContractDao.findContractByContractNo(contractNo);
		MultipartFile f = request.getFile("pictureAddress");
		if (f.getSize() != 0) {
			try {
				File tmp;
				tmp = File.createTempFile("1222", "2222");
				OutputStream out = new FileOutputStream(tmp);
				out.write(f.getBytes());
				out.flush();
				out.close();
				Manager.setDbSource(accVieoPathService);
				Manager.uploadZip(tmp, contract.getApplyNo(), UserUtils.getUser().getName(), true, sdf.format(contract.getOccurDate()));
			} catch (IOException e) {
				logger.error("上传文件出现问题！", e);
				return flag;
			}
			List<String> paths = accVieoPathService.findPictureAddressQueryList(contract.getApplyNo());
			if (paths.size() != 0) {
				applyChangeBankcard.setPictureAddress(StringUtils.join(paths.toArray(), "|"));
				accVieoPathService.deleteAccVideoPath(contract.getApplyNo());
			}
		}
		applyChangeBankcard.setContractNo(contractNo);
		applyChangeBankcard.setNewRepBankcardNo(newRepBankcardNo);
		applyChangeBankcard.setNewRepBankcardName(newRepBankcardName);
		applyChangeBankcard.setNewRepBank(newRepBank);
		applyChangeBankcard.setNewRepBankName(newRepBankName);
		applyChangeBankcard.setNewRepBankProvince(newRepBankProvince);
		applyChangeBankcard.setNewRepBankCity(newRepBankCity);
		applyChangeBankcard.setNewRepBankDistinct(newRepBankDistinct);
		applyChangeBankcard.setNewRepBankDetail(newRepBankDetail);
		applyChangeBankcard.setFlowState(Constants.FLOW_STATE_SQZ);
		save(applyChangeBankcard);
		flag = true;
		return flag;
	}
}

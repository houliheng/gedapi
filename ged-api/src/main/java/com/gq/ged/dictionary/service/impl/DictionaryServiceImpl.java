package com.gq.ged.dictionary.service.impl;

import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.dictionary.controller.res.BankInfo;
import com.gq.ged.dictionary.controller.res.LoanListResForm;
import com.gq.ged.dictionary.dao.mapper.SystemDictionaryItemMapper;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItemExample;
import com.gq.ged.dictionary.service.DictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/28.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

  @Resource
  SystemDictionaryItemMapper dictionaryItemMapper;

  @Override
  public String getPortalAmount() {
    List<SystemDictionaryItem> list = getItemList("PORTAL_AMOUNT");
    return list.get(0).getValue();
  }

  @Override
  public List<LoanListResForm> getLoanList() {
    List<LoanListResForm> result = new ArrayList<>();
    List<SystemDictionaryItem> list = getItemList("LOAN_TYPE_LIST");
    list.forEach(n -> {
      LoanListResForm resForm = new LoanListResForm();
      resForm.setProductName(n.getValue());
      resForm.setId(n.getCode());
      resForm.setDesc(n.getDescription());
      result.add(resForm);
    });
    return result;
  }

  private List<SystemDictionaryItem> getItemList(String item) {
    SystemDictionaryItemExample example = new SystemDictionaryItemExample();
    example.createCriteria().andDictIdEqualTo(item).andIsEnabledEqualTo(Constant.COMMONE_ONE);
    List<SystemDictionaryItem> list = dictionaryItemMapper.selectByExample(example);
    if (list.size() == 0) {
      throw new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND);
    }
    return list;
  }

  @Override
  public SystemDictionaryItem getDictionaryByParam(String dicId, String key) {
    SystemDictionaryItemExample example = new SystemDictionaryItemExample();
    example.createCriteria().andDictIdEqualTo(dicId).andCodeEqualTo(key);
    List<SystemDictionaryItem> list = dictionaryItemMapper.selectByExample(example);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public String getDictionaryValue(String dicId, Integer key) {
    SystemDictionaryItem item = getDictionaryByParam(dicId, key.toString());
    if (item == null) {
      return "";
    }
    return item.getValue();
  }

  @Override
  public List<BankInfo> getBanks(String itemCode) {
    List<SystemDictionaryItem> list = this.getItemList(itemCode);
    List<BankInfo> datas = new ArrayList<>();
    for (SystemDictionaryItem s : list) {
      BankInfo bankInfo = new BankInfo();
      if("BANK_TYPE".equals(itemCode)){
        bankInfo.setBankName(s.getValue());
        bankInfo.setCode(s.getCode());
      }else {
        bankInfo.setBankName(s.getDescription());
        bankInfo.setFuyouCode(s.getValue());
      }

      datas.add(bankInfo);
    }
    return datas;
  }

  @Override
  public SystemDictionaryItem getBankInfo(String code) {
    SystemDictionaryItemExample example = new SystemDictionaryItemExample();
    example.createCriteria().andCodeEqualTo(code);
    List<SystemDictionaryItem> list = dictionaryItemMapper.selectByExample(example);
    SystemDictionaryItem dictionaryItem = null;

    if (list != null && list.size() > 0) {
      dictionaryItem = list.get(0);
    }
    return dictionaryItem;
  }

}

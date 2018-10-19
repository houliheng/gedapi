package com.gq.ged.dictionary.service.impl;

import com.gq.ged.dictionary.dao.mapper.GedFuiouBankCodeMapper;
import com.gq.ged.dictionary.dao.model.*;
import com.gq.ged.dictionary.service.GedFuiouBankCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wrh on 2018/8/02.
 */
@Service
public class GedFuiouBankCodeServiceImpl implements GedFuiouBankCodeService {

  @Resource
  GedFuiouBankCodeMapper mapper;

  @Override
  public GedFuiouBankCode getBankInfo(String code) {
    GedFuiouBankCodeExample  example = new GedFuiouBankCodeExample();
    example.createCriteria().andBankCodeEqualTo(code);
    List<GedFuiouBankCode> list = mapper.selectByExample(example);
    if(list.size()>0){
      return list.get(0);
    }
    return null;
  }


}

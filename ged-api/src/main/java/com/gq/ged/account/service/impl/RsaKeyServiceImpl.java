package com.gq.ged.account.service.impl;

import com.gq.ged.account.dao.mapper.RsaKeyMapper;
import com.gq.ged.account.dao.model.RsaKey;
import com.gq.ged.account.dao.model.RsaKeyExample;
import com.gq.ged.account.service.RsaKeyService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@Service
public class RsaKeyServiceImpl implements RsaKeyService {

  public static String prik = "";
  public static String pubk = "";

  @Resource
  RsaKeyMapper rsaKeyMapper;

  @PostConstruct
  public void intkeys(){
    pubk = this.getPublicKey(1);
    prik = this.getPrivateKey(1);
  }


  @Override
  public String getPublicKey(Integer type) {
    RsaKeyExample example = new RsaKeyExample();
    example.createCriteria().andTypeEqualTo(type);
    List<RsaKey> list = rsaKeyMapper.selectByExample(example);
    return list.get(0).getPublicKey();
  }

  @Override
  public String getPrivateKey(Integer type) {
    RsaKeyExample example = new RsaKeyExample();
    example.createCriteria().andTypeEqualTo(type);
    List<RsaKey> list = rsaKeyMapper.selectByExample(example);
    return list.get(0).getPrivateKey();
  }

  @Override
  public RsaKey getAllKeys(Integer type) {
    RsaKeyExample example = new RsaKeyExample();
    example.createCriteria().andTypeEqualTo(type);
    List<RsaKey> list = rsaKeyMapper.selectByExample(example);
    return list.get(0);
  }
}

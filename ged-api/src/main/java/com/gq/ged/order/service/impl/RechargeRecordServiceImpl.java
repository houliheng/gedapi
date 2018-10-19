package com.gq.ged.order.service.impl;

import com.gq.ged.account.controller.req.v2.RechargeFeeForm;
import com.gq.ged.order.dao.mapper.GedRechargeRecordMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedOrderExample;
import com.gq.ged.order.dao.model.GedRechargeRecord;
import com.gq.ged.order.dao.model.GedRechargeRecordExample;
import com.gq.ged.order.service.RechargeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Levi on 2018/3/9.
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    rollbackFor = Exception.class)
public class RechargeRecordServiceImpl implements RechargeRecordService {


  @Resource
  GedRechargeRecordMapper rechargeRecordMapper;

  @Override
  public Integer insert(GedRechargeRecord rechargeRecord) {
    return rechargeRecordMapper.insert(rechargeRecord);
  }

  @Override
  public GedRechargeRecord getRechargeRecordByOrderNo(String orderNo) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andSeqNoEqualTo(orderNo);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

  @Override
  public GedRechargeRecord getRechargeRecordByStreamNo(String streamNo) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(streamNo);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

    @Override
    public GedRechargeRecord getRechargeRecordBySeqNo(String seqNo) {
        GedRechargeRecordExample example = new GedRechargeRecordExample();
        example.setOrderByClause("create_time desc");
        GedRechargeRecordExample.Criteria criteria = example.createCriteria();
        criteria.andSeqNoEqualTo(seqNo).andTypeEqualTo(1);
        List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
  public GedRechargeRecord getWithDrawRecordBySeqNo(String seqNo) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andSeqNoEqualTo(seqNo).andTypeEqualTo(3);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

  @Override
  public GedRechargeRecord getRechargeRecordByOrderNoAndAmount(RechargeFeeForm rechargeFeeForm) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    /*
     * criteria.andOrderNoEqualTo(rechargeFeeForm.getOrderNo()).
     * andRechargeAmountEqualTo(rechargeFeeForm.getAmount()).
     * andCustNoEqualTo(rechargeFeeForm.getCustId()).andTypeEqualTo(1);
     */
    criteria.andOrderNoEqualTo(rechargeFeeForm.getOrderNo()).andStatusEqualTo(1).andTypeEqualTo(1);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

  @Override
  public GedRechargeRecord getWithDrawRecordByOrderNoAndSeqNO(String orderNo, BigDecimal amount,
      String resp_code) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andOrderNoEqualTo(orderNo).andTypeEqualTo(3).andRechargeAmountEqualTo(amount);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return (list == null || list.size() == 0) ? null : list.get(0);
  }

  @Override
  public List<GedRechargeRecord> getGedRechargeRecordsByOrderNo(String orderNo) {
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    GedRechargeRecordExample.Criteria criteria = example.createCriteria();
    criteria.andSeqNoEqualTo(orderNo);
    List<GedRechargeRecord> list = rechargeRecordMapper.selectByExample(example);
    return list;
  }

  @Override
  public Integer updateById(GedRechargeRecord data) {
    return rechargeRecordMapper.updateByPrimaryKeySelective(data);
  }

  @Override
  public GedRechargeRecord getRechargeRecordByCustId(String custId) {
    /*
     * List<Integer> list = new LinkedList<Integer>(); list.add(0);//未充值 list.add(2);//充值失败
     */
    GedRechargeRecordExample example = new GedRechargeRecordExample();
    example.setOrderByClause("create_time desc");
    example.createCriteria().andCustNoEqualTo(custId);
    List<GedRechargeRecord> gedRechargeRecordList = rechargeRecordMapper.selectByExample(example);

    GedRechargeRecord gedRechargeRecord = null;
    if (!gedRechargeRecordList.isEmpty())
      gedRechargeRecord = gedRechargeRecordList.get(0);
    return gedRechargeRecord;
  }
}

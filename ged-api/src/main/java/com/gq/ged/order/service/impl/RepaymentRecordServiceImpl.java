package com.gq.ged.order.service.impl;

import com.gq.ged.order.dao.mapper.GedRepaymentRecordMapper;
import com.gq.ged.order.dao.model.GedRechargeRecordExample;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.dao.model.GedRepaymentRecordExample;
import com.gq.ged.order.service.RepaymentRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/12 19:03
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
        rollbackFor = Exception.class)
public class RepaymentRecordServiceImpl implements RepaymentRecordService{
    @Resource
    GedRepaymentRecordMapper repaymentRecordMapper;

    @Override
    public GedRepaymentRecord getRechargeRecordByOrderNo(String orderNo) {
        GedRepaymentRecordExample example = new GedRepaymentRecordExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<GedRepaymentRecord> repaymentRecordList = repaymentRecordMapper.selectByExample(example);
        GedRepaymentRecord gedRepaymentRecord = null;
        if(repaymentRecordList != null && repaymentRecordList.size() > 0)
            gedRepaymentRecord = repaymentRecordList.get(0);
        return gedRepaymentRecord;
    }

    @Override
    public GedRepaymentRecord getRapaymentRecordBySeqNo(String seqNo) {
        GedRepaymentRecordExample example = new GedRepaymentRecordExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria().andSeqNoEqualTo(seqNo);
        List<GedRepaymentRecord> repaymentRecordList = repaymentRecordMapper.selectByExample(example);
        GedRepaymentRecord gedRepaymentRecord = null;
        if(repaymentRecordList != null && repaymentRecordList.size() > 0)
            gedRepaymentRecord = repaymentRecordList.get(0);
        return gedRepaymentRecord;
    }


    @Override
    public List<GedRepaymentRecord> getRepaymentRecordsByOrderNo(String orderNo) {
        GedRepaymentRecordExample example = new GedRepaymentRecordExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<GedRepaymentRecord> repaymentRecordList = repaymentRecordMapper.selectByExample(example);
        return repaymentRecordList;
    }

    @Override
    public Integer updateRepaymentRecordById(GedRepaymentRecord data) {
        return repaymentRecordMapper.updateByPrimaryKeySelective(data);
    }

    @Override
    public Integer insertRepaymentRecordById(GedRepaymentRecord data) {
        return repaymentRecordMapper.insertSelective(data);
    }
}

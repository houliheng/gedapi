package com.gq.ged.order.service;

import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.controller.res.JoinCompanyInfo;
import com.gq.ged.order.pc.res.GeneratePayParas;
import com.gq.ged.order.pc.res.LoanRecordResForm;
import com.gq.ged.order.pc.res.RechargeReForm;

import java.util.List;

/**
 * Created by Levi on 2018/4/26.
 */
public interface CompanyService {

    /**
     * 根据
     * @param userId
     * @return
     */
    public List<JoinCompanyInfo> getJoinCompanyInfos(Long userId , String masterCode);

    /**
     * 获取联合授信订单列表信息
     * @param userId
     * @param masterCode
     * @return
     */
    public List<LoanRecordResForm> getJoinCompanyOrderInfos(Long userId , String masterCode);
}

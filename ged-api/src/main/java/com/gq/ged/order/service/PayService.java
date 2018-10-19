package com.gq.ged.order.service;

import com.gq.ged.order.controller.req.PayCallBackReqForm;
import com.gq.ged.order.pc.res.GeneratePayParas;
import com.gq.ged.order.pc.res.RechargeReForm;

/**
 * Created by Levi on 2018/4/26.
 */
public interface PayService {


    /**
     * 通用充值
     * @param userId
     * @param bankId
     * @param amt
     * @param period
     * @return
     * @throws Exception
     */
    public RechargeReForm commonUserRecharge(Long userId, String bankId, String amt,
                                             String period) throws Exception ;

    /**
     * 生成普通支付的支付参数
     * @param seqNum
     * @param loginName
     * @param orderNo
     * @param userId
     * @param orderPayType
     * @param issinscd
     * @param amt
     * @return
     */
    public GeneratePayParas commonGeneratePayParas(String seqNum, String loginName,
                                                   String orderNo, Long userId, String orderPayType, String issinscd, String amt);


    /**
     * 富有支付成功回调get入账接口
     * @param orderNo 第三方流水号
     * @param amt
     * @param payCallBackReqForm
     * @throws Exception
     */
    public void commonUserRechargeCallBack(String orderNo, String amt , PayCallBackReqForm payCallBackReqForm) throws Exception;
}

package com.gq.ged.account.controller;

import com.alibaba.fastjson.JSON;
import com.gq.ged.account.controller.req.AccountFlagResForm;
import com.gq.ged.account.controller.req.v2.*;
import com.gq.ged.account.controller.res.v2.*;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.order.controller.res.OrderDetailResForm;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaozb on 2018/3/19
 */
@Api(value = "V2.0账户信息", description = "V2.0账户信息")
@RestController
@RequestMapping(value = "/v2/api/account/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountOpenController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AccountOpenController.class);

    @Resource
    AccountOpenService accountOpenService;
    @Resource
    UserService userService;
    @Value("${gq.ged.call.v2.accont.enterprise.tradeType}")
    String enterpriseTradeType;
    @Value("${gq.ged.call.v2.verifyDeduct.tradeType}")
    String verifyDeductTradeType;

    @ApiOperation(value = "v2.0银行卡变更", notes = "v2.0银行卡变更")
    @RequestMapping(value = "/bankCardChange", method = RequestMethod.POST)
    public ResponseEntity<?> bankCardChange(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        PageResForm pageResForm = accountOpenService.bankCardChange(user);
        return ResponseEntityUtil.success(pageResForm);
    }

    @ApiOperation(value = "银行卡变更资金系统回调", notes = "银行卡变更资金系统回调")
    @RequestMapping(value = "/bankCardChangePageCallBack", method = RequestMethod.POST)
    public Map<String,Object> bankCardChangePageCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("资金系统回调  进入银行卡变更回调接口中了。。。。。");
        Map<String,Object> resMap =  accountOpenService.bankCardChangePageCallBack(reqForm);
        logger.info("资金系统回调  银行卡变更回调接口返回结果：" + JSON.toJSONString(resMap));
        return resMap;
    }

    @ApiOperation(value = "银行卡变更---PC结果页回调", notes = "银行卡变更---PC结果页回调")
    @RequestMapping(value = "/pageBankCardChangePageCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> pageBankCardChangePageCallBack(@RequestBody CapitalResForm reqForm) throws Exception {
        logger.info("前台调用 --- 银行卡变更回调接口中了。。。。。");
        Map<String,Object> resMap = accountOpenService.bankCardChangePageCallBack(reqForm);
        logger.info("前台调用 --- 银行卡变更回调接口返回结果：" + JSON.toJSONString(resMap));
        return ResponseEntityUtil.success(resMap);
    }


    @ApiOperation(value = "v2.0网页提现", notes = "v2.0网页提现")
    @RequestMapping(value = "/toWithdrawPage/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<?> toWithdrawPage(@PathVariable("orderNo") String orderNo,
                                            HttpServletRequest request) throws Exception {
        logger.info("==============进入网页提现接口中了==============");
        User user = this.getUserInfo(request);
        WithDrawPageResForm pageResForm = accountOpenService.toWithdrawPage(orderNo, user);
        return ResponseEntityUtil.success(pageResForm);
    }

   /* @ApiOperation(value = "查询账户状态", notes = "查询账户状态")
    @RequestMapping(value = "/queryAccStatus/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> queryAccStatus(@PathVariable("userId") Long userId,
                                            HttpServletRequest request) throws Exception {
        logger.info("==============查询账户状态==============");
        return ResponseEntityUtil.success(accountOpenService.queryAccStatus(userId));
    }

    @ApiOperation(value = "查询交易流水", notes = "查询交易流水")
    @RequestMapping(value = "/queryTradeFlow/{userId}/{tradeFilters}", method = RequestMethod.GET)
    public ResponseEntity<?> queryTradeFlow(@PathVariable("userId") Long userId,@PathVariable("tradeFilters")
            String tradeFilters,HttpServletRequest request) throws Exception {
        logger.info("==============查询交易流水==============");
        return ResponseEntityUtil.success(accountOpenService.queryTradeFlow(userId,tradeFilters));
    }*/

    @ApiOperation(value = "v2.0网页提现APP", notes = "v2.0网页提现APP")
    @RequestMapping(value = "/toWithdrawPageAPP/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<?> toWithdrawPageAPP(@PathVariable("orderNo") String orderNo,
                                            HttpServletRequest request) throws Exception {
        logger.info("==============进入网页提现接口中了==============");
        User user = this.getUserInfo(request);
        WithDrawPageResForm pageResForm = accountOpenService.toWithdrawPageAPP(orderNo, user);
        return ResponseEntityUtil.success(pageResForm);
    }

    @ApiOperation(value = "v2.0网页提现回调", notes = "v2.0网页提现回调")
    @RequestMapping(value = "/toWithdrawPageCallBack", method = RequestMethod.POST)
    public Map<String, Object> toWithdrawPageCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("==============进入网页提现回调接口中了==============");
        Map<String, Object> resMap = accountOpenService.withdrawPageCallBack(reqForm);
        return resMap;
    }

    @ApiOperation(value = "page网页提现回调", notes = "page网页提现回调")
    @RequestMapping(value = "/pageWithdrawPageCallBack", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> pageWithdrawPageCallBack(@RequestBody  CapitalResForm reqForm) throws Exception {
        logger.info("==============前端或app进入网页提现回调接口中了==============");
        Map<String, Object> resMap = accountOpenService.withdrawPageCallBack(reqForm);
        return ResponseEntityUtil.success(resMap);
    }

    @ApiOperation(value = "查询借款详情", notes = "查询借款详情")
    @RequestMapping(value = "/queryLoanDetailByCode/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<OrderDetailResForm> queryLoanDetailByCode(
            @PathVariable(value = "orderNo") String orderNo, HttpServletRequest request)
            throws Exception {
        User userInfo = this.getUserInfo(request);
        OrderDetailResForm resForm = accountOpenService.getOrderDetailInfo(orderNo, userInfo.getId());
        return ResponseEntityUtil.success(resForm);
    }


    @ApiOperation(value = "查询开户信息", notes = "查询开户信息")
    @RequestMapping(value = "/queryAccountList", method = RequestMethod.GET)
    public ResponseEntity<?> queryAccountList(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        Map<String, Object> map = accountOpenService.queryAccountInfo(user.getId());
        logger.info("查询开户信息：" + JSON.toJSONString(map));
        return ResponseEntityUtil.success(map.get("data"));
    }

    @ApiOperation(value = "查询用户开户标识等开户信息", notes = "查询用户开户标识等开户信息")
    @RequestMapping(value = "/queryUserAccountFlag", method = RequestMethod.GET)
    public ResponseEntity<AccountFlagNewResForm> queryUserAccountFlag(HttpServletRequest request)
            throws Exception {
        logger.info(">>>>>>>>>账户管理  开户状态信息查询>>>");
        User user = this.getUserInfo(request);
        AccountFlagNewResForm resForm = accountOpenService.queryUserAccountFlag(user);
        logger.info(">>>>>>>>>账户管理  开户状态信息查询返回信息：" + JSON.toJSONString(resForm));
        return ResponseEntityUtil.success(resForm);
    }

    @ApiOperation(value = "个人开户APP", notes = "个人开户APP")
    @RequestMapping(value = "/toPersonalPageAPP", method = RequestMethod.GET)
    public ResponseEntity<?> toPersonalPageAPP(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toPersonalPageAPP(user));
    }

    @ApiOperation(value = "个人开户PC", notes = "个人开户PC")
    @RequestMapping(value = "/toPersonalPagePC", method = RequestMethod.GET)
    public ResponseEntity<?> toPersonalPagePC(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toPersonalPagePC(user));
    }

    @ApiOperation(value = "个人开户回调", notes = "个人开户回调")
    @RequestMapping(value = "/personAccountCallBack", method = RequestMethod.POST)
    public Map<String,Object> personAccountCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("执行个人开户回调接口>>>>>>>>>");
        return accountOpenService.personAccountCallBack(reqForm);
    }

    @ApiOperation(value = "企业开户APP", notes = "企业开户APP")
    @RequestMapping(value = "/toEnterprisePageAPP", method = RequestMethod.GET)
    public ResponseEntity<?> toEnterprisePageAPP(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toEnterprisePageAPP(user));
    }

    @ApiOperation(value = "企业开户PC", notes = "企业开户PC")
    @RequestMapping(value = "/toEnterprisePagePC", method = RequestMethod.GET)
    public ResponseEntity<?> toEnterprisePagePC(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toEnterprisePagePC(user));
    }

    @ApiOperation(value = "企业开户修改上次资料PC", notes = "企业开户修改上次资料PC")
    @RequestMapping(value = "/toModifyEnterprisePC", method = RequestMethod.GET)
    public ResponseEntity<?> toModifyEnterprisePC(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toModifyEnterprisePC(user));
    }

    @ApiOperation(value = "企业开户修改上次资料PC", notes = "企业开户修改上次资料PC")
    @RequestMapping(value = "/toModifyEnterpriseAPP", method = RequestMethod.GET)
    public ResponseEntity<?> toModifyEnterpriseAPP(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toModifyEnterpriseAPP(user));
    }

    @ApiOperation(value = "PC开户结果页调用", notes = "PC开户结果页调用")
    @RequestMapping(value = "/pcAccountCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> pcAccountCallBack(@RequestBody CapitalResForm resForm,HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        Map<String,Object> resMap = new HashMap<String,Object>();
        if((user.getUserRole()==0 && user.getUserType() ==0) || user.getUserRole() == 1){
            resMap = accountOpenService.personAccountCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }else {
            resMap = accountOpenService.enterpriseAccountCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }
    }

    @ApiOperation(value = "APP开户结果页调用", notes = "APP开户结果页调用")
    @RequestMapping(value = "/appAccountCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> appAccountCallBack(@RequestBody CapitalResForm resForm) throws Exception {
        Map<String,Object> resMap = new HashMap<String,Object>();
        String tradeType = resForm.getTrade_type();
        if(tradeType.equals(enterpriseTradeType)){
            resMap = accountOpenService.enterpriseAccountCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }else {
            resMap = accountOpenService.personAccountCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }
    }

    @ApiOperation(value = "PC充值和缴费结果页调用", notes = "PC充值和缴费结果页调用")
    @RequestMapping(value = "/rechargeFeeCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> rechargeFeeCallBack(@RequestBody CapitalResForm resForm) throws Exception {
        Map<String,Object> resMap = new HashMap<String,Object>();
        String tradeType = resForm.getTrade_type();
        logger.info("PC充值和缴费结果页调用 交易类型：" + tradeType);
        if(tradeType.equals(verifyDeductTradeType)){//缴费
            resMap = accountOpenService.verifyDeductCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }else {//缴费业务的充值
            resMap = accountOpenService.paychargeCallBack(resForm);
            return  ResponseEntityUtil.success(resMap);
        }
    }


    @ApiOperation(value = "企业开户回调", notes = "企业开户回调")
    @RequestMapping(value = "/enterpriseAccountCallBack", method ={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object>  enterpriseAccountCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("执行企业开户 回调接口>>>>>>>>>");
        Map<String,Object> resMap = accountOpenService.enterpriseAccountCallBack(reqForm);
        logger.info("企业开户 回调接口 返回数据 >>>>>>>>>" + JSON.toJSONString(resMap));
        return resMap;
    }

    @ApiOperation(value = "借款系统人工退回企业开户信息回调", notes = "借款系统人工退回企业开户信息回调")
    @RequestMapping(value = "/enterpriseAccountLoanCallBack", method ={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object>  enterpriseAccountLoanCallBack(@RequestBody LoanEnterpriseReturnForm reqForm) throws Exception {
        logger.info("借款系统 退回 企业开户 回调接口>>>>>>>>>");
        Map<String,Object> resMap = accountOpenService.enterpriseAccountLoanCallBack(reqForm);
        logger.info("借款系统 退回 接口 返回数据 >>>>>>>>>" + JSON.toJSONString(resMap));
        return resMap;
    }

    @ApiOperation(value = "还款PC", notes = "还款PC")
    @RequestMapping(value = "/repaymentRecharge", method = RequestMethod.POST)
    public ResponseEntity<?> repaymentRecharge(@RequestBody RepaymentReqForm reqForm,
                                                   HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        logger.info("请求还款PC页面 >>>>>>>>> 参数：" + JSON.toJSONString(reqForm));
        return ResponseEntityUtil.success(accountOpenService.repaymentRecharge(reqForm, user));
    }

    @ApiOperation(value = "资金系统还款PC回调", notes = "资金系统还款PC回调")
    @RequestMapping(value = "/repaymentRechargeCallBack", method = RequestMethod.POST)
    public Map<String,Object> repaymentRechargeCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("执行还款PC回调接口>>>>>>>>>");
        Map<String,Object>  resMap = accountOpenService.repaymentRechargeCallBack(reqForm);
        logger.info("还款PC回调接口返回结果  >>>>>>>>>" + JSON.toJSONString(resMap));
        return resMap;
    }


    @ApiOperation(value = "PC还款结果页调用", notes = "PC还款结果页调用")
    @RequestMapping(value = "/pageRepaymentRechargeCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> pageRepaymentRechargeCallBack(@RequestBody CapitalResForm reqForm) throws Exception {
        logger.info("PC还款结果页充值回调接口>>>>>>>>>");
        return ResponseEntityUtil.success(accountOpenService.repaymentRechargeCallBack(reqForm));
    }

    @ApiOperation(value = "用户激活", notes = "用户激活")
    @RequestMapping(value = "/activateUser", method = RequestMethod.GET)
    public ResponseEntity<?> activateUser(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.activateUser(user));
    }

    @ApiOperation(value = "资金系统用户激活回调", notes = "资金系统用户激活回调")
    @RequestMapping(value = "/activateUserCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> activateUserCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("资金系统调用>>>>执行 用户激活回调接口>>>>>>>>>");
        Map<String,Object> resMap = accountOpenService.activateUserCallBack(reqForm);
        logger.info("资金系统调用>>>> 用户激活回调返回结果  >>>>>>>>>" + JSON.toJSONString(resMap));
        return ResponseEntityUtil.success(resMap);
    }

    @ApiOperation(value = "PC用户激活结果页回调", notes = "PC用户激活结果页回调")
    @RequestMapping(value = "/pageActivateUserCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> pageActivateUserCallBack(@RequestBody  CapitalResForm reqForm) throws Exception {
        logger.info("PC用户激活结果页回调--- 执行 用户激活回调接口>>>>>>>>>");
        Map<String,Object> resMap = accountOpenService.activateUserCallBack(reqForm);
        logger.info("PC用户激活结果页回调--- >>>>用户激活回调接口返回结果  >>>>>>>>>" + JSON.toJSONString(resMap));
        return ResponseEntityUtil.success(resMap);
    }

    @ApiOperation(value = "查询新老用户状态", notes = "查询新老用户状态")
    @RequestMapping(value = "/queryUserStatus", method = RequestMethod.GET)
    public ResponseEntity<?> queryUserStatus(HttpServletRequest request) throws Exception {
        logger.info(">>>>>>>>> 查询新老用户状态>>>>>>");
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.queryUserStatus(user.getId()));
    }

    @ApiOperation(value = "查询用户激活状态", notes = "查询用户激活状态")
    @RequestMapping(value = "/queryActivateUserStatus", method = RequestMethod.GET)
    public ResponseEntity<?> queryActivateUserStatus(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.queryActivateUserStatus(user.getGetCustId()));
    }


    @ApiOperation(value = "网银充值(缴费)页面", notes = "网银充值(缴费)页面")
    @RequestMapping(value = "/toChargePage", method = RequestMethod.POST)
    public ResponseEntity<?> toChargePage(@RequestBody ChargeReqForm chargeReqForm,HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toChargePage(chargeReqForm,user));
    }

    @ApiOperation(value = "网银充值(缴费)页面-担保人", notes = "网银充值(缴费)页面-担保人")
    @RequestMapping(value = "/toChargePageGuarantor", method = RequestMethod.POST)
    public ResponseEntity<?> toChargePageGuarantor(HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        return ResponseEntityUtil.success(accountOpenService.toChargePageGuarantor(user));
    }

    @ApiOperation(value = "资金系统网银充值回调", notes = "资金系统网银充值回调")
    @RequestMapping(value = "/paychargeCallBack", method = RequestMethod.POST)
    public Map<String, Object>  paychargeCallBack(CapitalResForm reqForm) throws Exception {
        Map<String, Object> resMap = accountOpenService.paychargeCallBack(reqForm);
        logger.info("网银充值回调(缴费) ---------- 返回结果：" +JSON.toJSONString(resMap));
        return resMap;
    }

    @ApiOperation(value = "资金系统充值回调(担保人)", notes = "资金系统充值回调(担保人)")
    @RequestMapping(value = "/rechargeGuarantorCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> rechargeGuarantorCallBack(CapitalResForm reqForm) throws Exception {
        accountOpenService.rechargeGuarantorCallBack(reqForm);
        return ResponseEntityUtil.success();
    }

    /*@ApiOperation(value = "缴费结果回调", notes = "缴费结果回调")
    @RequestMapping(value = "/chargesCallBack", method = RequestMethod.POST)
    public ResponseEntity<?> chargesCallBack(CapitalResForm reqForm) throws Exception {
        logger.info("执行网银充值缴费回调接口>>>>>>>>>");
        return ResponseEntityUtil.success(accountOpenService.chargesCallBack(reqForm));
    }*/

    @ApiOperation(value = "缴费接口(直连)", notes = "缴费接口(直连)")
    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public ResponseEntity<?> charge(@RequestBody RechargeReqForm rechargeReqForm, HttpServletRequest request) throws Exception {
        return ResponseEntityUtil.success(accountOpenService.charge(rechargeReqForm));
    }

    @ApiOperation(value = "缴费接口(页面)", notes = "缴费接口(页面)")
    @RequestMapping(value = "/toVerifyDeductPage", method = RequestMethod.POST)
    public ResponseEntity<?> toVerifyDeductPage(@RequestBody ChargeReqForm chargeReqForm, HttpServletRequest request) throws Exception {
        User user = this.getUserInfo(request);
        logger.info("缴费接口(页面) 请求参数:"+JSON.toJSONString(chargeReqForm));
        return ResponseEntityUtil.success(accountOpenService.toVerifyDeductPage(chargeReqForm, user));
    }


    @ApiOperation(value = "资金系统缴费回调(页面)", notes = "资金系统缴费回调(页面)")
    @RequestMapping(value = "/verifyDeductCallBack", method = RequestMethod.POST)
    public Map<String,Object> verifyDeductCallBack(CapitalResForm resForm) throws Exception {
        logger.info("执行资金系统缴费回调(页面)>>>>>>>>> resForm:" + JSON.toJSONString(resForm));
        return accountOpenService.verifyDeductCallBack(resForm);
    }


}


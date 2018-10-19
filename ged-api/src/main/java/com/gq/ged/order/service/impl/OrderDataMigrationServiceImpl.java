package com.gq.ged.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.OrderStatus;
import com.gq.ged.common.enums.PushDataMigrationStatus;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.order.controller.req.OrderDataMigrationReqForm;
import com.gq.ged.order.controller.res.ContractSignResult;
import com.gq.ged.order.dao.mapper.GedOrderMapper;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.OrderLog;
import com.gq.ged.order.service.OrderDataMigrationService;
import com.gq.ged.order.service.OrderLogService;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.mapper.UserLoginMapper;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.dao.model.UserExample;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.service.UserService;
import jodd.util.BCrypt;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wrh on 2018/5/22.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    rollbackFor = Exception.class)
public class OrderDataMigrationServiceImpl implements OrderDataMigrationService {
  Logger logger = LoggerFactory.getLogger(OrderDataMigrationServiceImpl.class);

  @Resource
  OrderLogService orderLogService;
  @Resource
  OrderService orderService;
  @Resource
  UserService userService;
  @Resource
  UserMapper userMapper;
  @Resource
  GedOrderMapper gedOrderMapper;
  @Resource
  UserLoginMapper userLoginMapper;

  @Value("${gq.ged.getcontract.url}")
  String contractSignUrl;
  @Value("${gq.ged.fastDFS.url}")
  String getFastDFSurl;
  @Value("${user.password.salt}")
  String salt;

  @Override
  public void pushDataMigration(OrderDataMigrationReqForm reqForm) throws Exception {
    logger.info("数据迁移参数=" + JSONObject.toJSONString(reqForm));
    GedOrder gedOrder = new GedOrder();
    // 新增订单表
    // +++++++++++++++++++++等待参数++++++++++++++++++++++++++++//
    String applyIdChlid = reqForm.getApplyIdChild();
    if (StringUtils.isBlank(applyIdChlid)) {// 非联合授信
      gedOrder.setOrderCode(reqForm.getApplyId());
    } else {// 联合授信
      gedOrder.setOrderCode(applyIdChlid);
      gedOrder.setMasterOrderCode(reqForm.getApplyId());
    }
    GedOrder ged = orderService.selectOrderByOrderNo(gedOrder.getOrderCode());
    if(ged!=null){
       throw  new BusinessException(Errors.ORDER_EXISTS);
    }
    gedOrder.setContractCode(reqForm.getContractCode());// 合同号
    gedOrder.setPersonCardNum(reqForm.getIdNum());// 证件号码
    gedOrder
        .setCreateTime(DateFormatUtils.parseDateStr(reqForm.getApplyDate(), "yyyy-MM-dd"));// 申请日期
    gedOrder.setLoanType(reqForm.getProductType());// 借款类型
    gedOrder.setContactPhone(reqForm.getContactPhone());// 联系电话
    gedOrder.setLoanAmount(reqForm.getApplyAmount());// 申请金额
    gedOrder.setLoanTerm(reqForm.getApplyPeriod());// 申请期限
    gedOrder.setCompanyName(reqForm.getCompanyName());// 企业名称
    gedOrder.setCompanyType(reqForm.getCompanyCardType());// 企业证件类型
    gedOrder.setCompanyCardNum(reqForm.getCompanyCardNum());// 企业证件号码
    gedOrder.setLoanPurpose(reqForm.getLoanPurpose());// 借款用途
    // 根据企业证件类型 查询用户
      User user=null;
      if(reqForm.getUserRole()==1){//个人
        user=userService.userByMobile(reqForm.getContactPhone());
      }else if(reqForm.getUserRole()==2){//企业
        if(StringUtils.isBlank(reqForm.getCompanyCardNum())){
          throw  new BusinessException(Errors.USER_SHTYDM_NOT_EXISTS_ERROR);
        }else {
          user = userService.selectUserBytype(reqForm.getCompanyCardType(), reqForm.getCompanyCardNum());
        }
      }
    if (user == null) {
      throw new BusinessException(Errors.CCOUNT_NOT_EXISTS);
    }
    Long userId = user.getId();
    logger.info("===========用户id===" + userId);
    user.setId(user.getId());
    user.setUsername(reqForm.getCustName());//客户名称
    user.setCompanyName(reqForm.getCompanyName());//企业名称
    user.setLegalName(reqForm.getLegalName());//法人名称
    user.setLegalMobile(reqForm.getLegalMobile());//法人手机号
    user.setLegalCardNumber(reqForm.getLegalCardNumber());//法人证件号码
    user.setLegalCardType(1);
    userMapper.updateByPrimaryKeySelective(user);
    gedOrder.setUserId(userId);
    gedOrder.setRateDay(reqForm.getRateDay());// 月利率
    gedOrder.setPersonCardType(1);// 默认身份证
    // 传的是码值
    gedOrder
        .setProvinceId(reqForm.getProvince() != null ? Long.valueOf(reqForm.getProvince()) : null);// 省
    gedOrder.setCityId(reqForm.getCity() != null ? Long.valueOf(reqForm.getCity()) : null);// 市
    gedOrder.setAreaId(reqForm.getDistrict() != null ? Long.valueOf(reqForm.getDistrict()) : null);// 区
    gedOrder.setRepaymentStyle(reqForm.getRepaymentStyle());// 还款方式
    gedOrder.setReceivableCashDeposit(reqForm.getReceivableCashDeposit());// 应收保证金
    gedOrder.setReceivableGuaranteeFee(reqForm.getReceivableGuaranteeFee());// 应收担保费
    gedOrder.setCashDeposit(reqForm.getCashDeposit());// 实收保证金
    gedOrder.setGuaranteeFee(reqForm.getGuaranteeFee());// 实收担保费
    gedOrder.setServiceFee(reqForm.getServiceFee());// 服务费
    gedOrder.setCreditAmount(reqForm.getCreditAmount());// 放款额度
    gedOrder.setReplyAmount(reqForm.getCreditAmount());// 批复额度
    gedOrder.setReplyTerm(reqForm.getApplyPeriod());// 批复期限
    gedOrder.setServiceProvinceId(reqForm.getServiceProvinceId());// 服务方所在地
    gedOrder.setManagementAddr(reqForm.getContCity());// 所在城市
    if(PushDataMigrationStatus.resove(reqForm.getStatus())!=null) {
      gedOrder.setStatus(PushDataMigrationStatus.resove(reqForm.getStatus()).getCode());// 借款状态
    }else {
      gedOrder.setStatus(reqForm.getStatus());// 传值错误
    }
    gedOrder.setModifyTime(new Date());
    gedOrder.setWithdrawFlag(4);
    gedOrder.setWithdrawAmount(reqForm.getCreditAmount());
    gedOrder.setServiceFeeFlag(1);
    gedOrder.setGuaranteeFeeFlag(1);
    gedOrder.setSysFlag(0);// 借款系统推过来的订单
    gedOrder.setStockFlag(1);//存量数据
    orderService.pushCreateOrder(gedOrder);

    // 新增日志表
    OrderLog log = new OrderLog();
    log.setOrderId(gedOrder.getId());
    log.setCreateTime(new Date());
    if(PushDataMigrationStatus.resove(reqForm.getStatus())!=null) {
      log.setSourceStatus(PushDataMigrationStatus.resove(reqForm.getStatus()).getCode());
      log.setTargetStatus(PushDataMigrationStatus.resove(reqForm.getStatus()).getCode());
    }else {
      log.setSourceStatus(reqForm.getStatus());
      log.setTargetStatus(reqForm.getStatus());
    }
    log.setCreateId(userId);
    orderLogService.createOrderlog(log);
    //放款成功日志
    if(StringUtils.isNotBlank(reqForm.getLoanDate())){
        OrderLog fkchLog = new OrderLog();
        fkchLog.setOrderId(gedOrder.getId());
        fkchLog.setSourceStatus(OrderStatus.FKCG.getCode());
        fkchLog.setTargetStatus(OrderStatus.FKCG.getCode());
        fkchLog.setCreateTime(DateFormatUtils.parseDateStr(reqForm.getLoanDate(),"yyyy-MM-dd"));
        fkchLog.setCreateId(userId);
        orderLogService.createOrderlog(fkchLog);
    }
  }

  @Override
  public void dataMigration() throws Exception {
    UserExample example = new UserExample();
    UserExample.Criteria criteria = example.createCriteria();
    criteria.andStatusEqualTo(2);
    List<User> userList = userMapper.selectByExample(example);
    logger.info(JSONObject.toJSONString(userList));
    String password = "";
    if(userList!=null && userList.size()!=0){
        for(User user:userList){
          UserLogin userLogin = new UserLogin();
          String socialCreditCode = user.getSocialCreditCode();
          String mobile=user.getMobile();
          if(StringUtils.isBlank(socialCreditCode)&&StringUtils.isBlank(mobile)){
                 continue;
          }
          if(user.getUserType()==0){
            password = mobile.substring(mobile.length() - 6, mobile.length());
          }else if(user.getUserType()==1){
            password =socialCreditCode.substring(socialCreditCode.length() - 6, socialCreditCode.length());
          }
          userLogin.setId(user.getId());
          userLogin.setPassword(BCrypt.hashpw(password, salt));
          userLogin.setSocialCreditCode(socialCreditCode);
          userLogin.setCreateId(-1L);
          userLoginMapper.updateByPrimaryKeySelective(userLogin);
        }
    }
  }

  @Override
  public List<ContractSignResult> contractSign(String orderNo) throws  Exception {
    List contractList=new ArrayList<>();
    if(StringUtils.isBlank(orderNo)) {
        throw  new BusinessException(Errors.SYSTEM_DATA_NOT_FOUND);
    }
    GedOrder gedOrder = orderService.selectOrderByOrderNo(orderNo);
    if(gedOrder!=null){
      String contractNo=gedOrder.getContractCode();
      //通过合同号调用资金端接口返回标题和PDF
      Map<String,String> param=new HashMap<String,String>();
       param.put("contractNo",contractNo);
     // param.put("contractNo","GJX00180521AC-B-A");
      logger.info("合同号====="+contractNo);
    //  JSONObject jsonObject = HttpUtils.doPost("http://10.100.33.98:8080"+"/api/ged/queryLoanRelatedContract", JSONObject.toJSONString(param));
      JSONObject jsonObject = HttpUtils.doPost(contractSignUrl+"/api/ged/queryLoanRelatedContract", JSONObject.toJSONString(param));
       if(jsonObject!=null)
      logger.info("合同签署返回的参数值===============" +jsonObject.get("code") + "");
      if(jsonObject!=null && Constant.RES_CODE_SUCCESS.equals(jsonObject.getString("code"))){
        JSONArray data = jsonObject.getJSONArray("data");
        String js=JSONObject.toJSONString(data);//将array数组转换成字符串
        List<ContractSignResult> SignResult = JSONObject.parseArray(js, ContractSignResult.class);
        for(ContractSignResult result:SignResult){
          ContractSignResult signResult=new ContractSignResult();
          signResult.setTitle(result.getTitle());
          signResult.setUrl(getFastDFSurl+"/"+result.getUrl());
          contractList.add(signResult);
        }
      }
      String contractUrlByContractNoUrl = orderService.getContractUrlByContractNo(contractNo);
      if(StringUtils.isNotBlank(contractUrlByContractNoUrl)){
        ContractSignResult signResult=new ContractSignResult();
        signResult.setTitle("借款合同");
        signResult.setUrl(contractUrlByContractNoUrl);
        contractList.add(signResult);
      }

    }
    return contractList;
  }

}

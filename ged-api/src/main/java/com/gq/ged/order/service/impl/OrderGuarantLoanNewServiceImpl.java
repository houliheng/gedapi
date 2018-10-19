package com.gq.ged.order.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.gq.ged.account.service.AccountOpenService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.JointCreditFlag;
import com.gq.ged.common.enums.OrderStatus;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.order.controller.req.GuarantConfirmReqForm;
import com.gq.ged.order.controller.req.GuaranteeForm;
import com.gq.ged.order.controller.req.PageReqForm;
import com.gq.ged.order.controller.req.PushGuarantRecordReqForm;
import com.gq.ged.order.controller.res.DeductResult;
import com.gq.ged.order.controller.res.GuaranteeConfirmResForm;
import com.gq.ged.order.controller.res.GuaranteeLoanResForm;
import com.gq.ged.order.controller.res.OrderVerifyResForm;
import com.gq.ged.order.dao.mapper.GedOrderGuarantorMapper;
import com.gq.ged.order.dao.model.*;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.order.service.*;
import com.gq.ged.user.dao.mapper.UserLoginMapper;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.dao.model.UserExample;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.service.UserService;

import jodd.util.BCrypt;

/**
 * Created by wrh on 2018/4/17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    rollbackFor = Exception.class)
public class OrderGuarantLoanNewServiceImpl implements OrderGuarantLoanNewService {
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @Resource
  UserService userService;
  @Resource
  OrderNewService orderService;
  @Resource
  DictionaryService dictionaryService;
  @Resource
  GedOrderGuarantorMapper gedOrderGuarantorMapper;
  @Resource
  AccountOpenService accountService;
  @Resource
  OrderBorrowerNewService orderBorrowerService;
  @Resource
  UserMapper userMapper;
  @Resource
  UserLoginMapper userLoginMapper;

  @Resource
  OrderLogService orderLogService;

  @Value("${gq.borrow.url}")
  String confirmRelationUrl;
  @Value("${user.password.salt}")
  String salt;

  @Resource
  OrderVerifyService orderVerifyService;
  // OrderVerifyMapper orderVerifyMapper;



  @Override
  public GuaranteeLoanResForm getOrderGuarantLoanInfo(Long guarantorId) {
    GuaranteeLoanResForm resultForm = new GuaranteeLoanResForm();
    User user = userService.getUserById(guarantorId);// 查询担保人的用户信息
    if (user == null) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }
    List<GedOrderGuarantor> guarantorList = selectGedOrderGurantById(guarantorId, 0);// 查询担保人未担保记录
    if (guarantorList != null && guarantorList.size() != 0) {
      GedOrderGuarantor guarantor = guarantorList.get(0);
      Long getOrderId = guarantor.getOrderId();
      GedOrder gedOrder = orderService.selectByPrimy(getOrderId);
      resultForm.setLoanAmount(gedOrder.getLoanAmount());// 借款金额
      resultForm.setLoanTerm(gedOrder.getLoanTerm() + "个月");// 借款期限
      if (gedOrder.getLoanType() != null) {
        resultForm
            .setLoanType(dictionaryService.getDictionaryValue("LOAN_TYPE", gedOrder.getLoanType()));// 借款类型
      }
      if (gedOrder.getRateDay() != null) {
        resultForm.setRateDay(gedOrder.getRateDay() + "%");// 月利率
      }
      resultForm.setLoanUser(gedOrder.getCompanyName());// 借款人
      resultForm.setOrderNo(gedOrder.getOrderCode());// 订单编号
      resultForm.setToWhethorGuarantee(1);// 有待担保记录
      // 判断担保人是否开通资金存管账户信息
      Integer guarantorType = user.getUserRole();// 担保类型
      if (guarantorType == 1) {// 担保人
        if (user.getCheckAccountFlag() == 4) {
          resultForm.setWhethorAccountFlag(1);// 状态4个人签约成功
        }
      } else if (guarantorType == 2 || guarantorType == 3) {
        if (user.getCompanyAccountFlag() == 1) {
          resultForm.setWhethorAccountFlag(1);// 企业户已开
        }
      }
    } else {
      resultForm.setToWhethorGuarantee(0);// 无待担保记录
    }
    logger.info("=============" + JSONObject.toJSONString(resultForm));
    return resultForm;
  }

  // 担保记录
  @Override
  public List<GuaranteeConfirmResForm> selectGuaranteeRecord(Long guarantorId, PageReqForm reqForm)
      throws Exception {
    List<GuaranteeConfirmResForm> resList = new ArrayList<GuaranteeConfirmResForm>();
    // 查出担保人的信息
    int pageNum = reqForm.getPageNum();
    int pageSize = reqForm.getPageSize();
    List<GedOrderGuarantor> guarantorList =
        selectGedOrderGurantById(guarantorId, 1, pageNum, pageSize);
    GuaResList(guarantorList, resList);
    logger.info("查询担保记录返回的集合" + JSONObject.toJSONString(resList));
    return resList;
  }

  @Override
  public List<GuaranteeConfirmResForm> selectGuaranteeConfirm(Long guarantorId, PageReqForm reqForm)
      throws Exception {
    List<GuaranteeConfirmResForm> resList = new ArrayList<GuaranteeConfirmResForm>();
    int pageNum = reqForm.getPageNum();
    int pageSize = reqForm.getPageSize();
    List<GedOrderGuarantor> guarantorList =
        selectGedOrderGurantById(guarantorId, 0, pageNum, pageSize);// 查询担保人未担保记录
    GuaResList(guarantorList, resList);
    logger.info("担保确认返回的list" + JSONObject.toJSONString(resList));
    return resList;
  }

  @Override
  public void ConfirmGuarantee(Long userId, GuarantConfirmReqForm reqForm) throws Exception {
    int confirmFlag = reqForm.getConfirmFlag();
    if (confirmFlag == 0) {// 拒绝
      // 通过订单编号查询订单信息
      GedOrder gedOrder = orderService.selectOrderByOrderNo(reqForm.getOrderNo());
      if (gedOrder != null) {
        // 更新担保记录表中的状态及拒绝原因
        GedOrderGuarantorExample example = new GedOrderGuarantorExample();
        GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(gedOrder.getId());
        criteria.andGuarantorIdEqualTo(userId);
        GedOrderGuarantor gedOrderGuarantor = new GedOrderGuarantor();
        gedOrderGuarantor.setStatus(2);
        gedOrderGuarantor.setRefuseReason(reqForm.getMsg());
        gedOrderGuarantorMapper.updateByExampleSelective(gedOrderGuarantor, example);
        // 查询担保确认的担保关系
        List<GedOrderGuarantor> guarantList = gedOrderGuarantorMapper.selectByExample(example);
        if (guarantList != null && guarantList.size() != 0) {
          GedOrderGuarantor guaran = guarantList.get(0);
          logger.info("查询出来的拒绝担保关系担保类型" + guaran.getGuarantorType());
          // 推送借款系统
          pushGuarantee(gedOrder, reqForm, guaran);
        }

      }

    } else if (confirmFlag == 1) {// 确认担保
      // 通过订单编号查询订单信息
      GedOrder gedOrder = orderService.selectOrderByOrderNo(reqForm.getOrderNo());
      if (gedOrder != null) {
        GedOrderGuarantorExample example = new GedOrderGuarantorExample();
        GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(gedOrder.getId());
        criteria.andGuarantorIdEqualTo(userId);
        GedOrderGuarantor gedOrderGuarantor = new GedOrderGuarantor();
        gedOrderGuarantor.setStatus(1);
        gedOrderGuarantorMapper.updateByExampleSelective(gedOrderGuarantor, example);
        List<GedOrderGuarantor> guarantList = gedOrderGuarantorMapper.selectByExample(example);
        if (guarantList != null && guarantList.size() != 0) {
          GedOrderGuarantor guaran = guarantList.get(0);
          logger.info("查询出来的确认担保关系担保类型" + guaran.getGuarantorType());
          // 推送借款系统
          pushGuarantee(gedOrder, reqForm, guaran);
        }
      }

    }
  }

  // 推送借款担保确认
  public void pushGuarantee(GedOrder gedOrder, GuarantConfirmReqForm reqForm,
      GedOrderGuarantor guaran) throws Exception {
    String companyCardCode = companyCode(gedOrder);
    logger.info("企业证件号码=" + companyCardCode);
    Map<String, String> param = new HashMap<String, String>();
    if (StringUtils.isBlank(gedOrder.getMasterOrderCode())) {// 非联合授信
      param.put("applyNo", gedOrder.getOrderCode());
      param.put("applySubNo", "");// 子订单编号
    } else {
      param.put("applyNo", gedOrder.getMasterOrderCode());
      param.put("applySubNo", gedOrder.getOrderCode());// 子订单编号
    }
    param.put("flag", String.valueOf(reqForm.getConfirmFlag()));// 确认标识
    param.put("reason", reqForm.getMsg());// 拒绝原因
    param.put("socaliTotalNum", companyCardCode);// 企业证件号码
    param.put("userRole", guaran.getGuarantorType() + "");// 担保类型
    param.put("custId", guaran.getBorrowGuarantorId());// 借款系统担保人id
    logger.info("担保确认推送的参数==" + JSONObject.toJSONString(param));
    JSONObject json = HttpUtils.doPost(confirmRelationUrl + "/api/confirmRelation",
        JSONObject.toJSONString(param));
    logger.info("推送担保确认返回的值==" + JSONObject.toJSONString(json));
    if (!"0000".equals(json.get("code"))) {
      throw new BusinessException(Errors.SYSTEM_ERROR);
    }
  }

  public String companyCode(GedOrder gedOrder) {
    String companyCode = "";
    GedOrder ged = orderService.selectOrderByOrderNo(gedOrder.getOrderCode());// 通过订单编号查询订单
    if (ged != null) {
      String masterOrderCode = ged.getMasterOrderCode();
      if (StringUtils.isBlank(masterOrderCode)) {// 非联合授信
        User user = userService.getUserById(ged.getUserId());
        if (user != null) {
          companyCode = user.getCompanyCardCode();
          return companyCode;
        }
      } else {// 联合授信
        List<GedOrder> list = orderService.selectOrderByMasterOrderNo(masterOrderCode);
        if (list != null && list.size() != 0) {
          for (GedOrder g : list) {
            GedOrderGuarantorExample example = new GedOrderGuarantorExample();
            GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
            criteria.andOrderIdEqualTo(g.getId()).andGuarantorTypeEqualTo(3);
            List<GedOrderGuarantor> l = gedOrderGuarantorMapper.selectByExample(example);
            if (l != null && l.size() != 0) {
              Long guarId = l.get(0).getGuarantorId();
              User u = userService.getUserById(guarId);
              if (u != null)
                companyCode = u.getCompanyCardCode();
              break;
            }
          }
        }
      }
    }
    return companyCode;
  }

  @Override
  public void pushGuaranteeRecord(GuaranteeForm guaranteeForm) {
    logger.info("推过来的担保信息" + JSONObject.toJSONString(guaranteeForm));
    List<PushGuarantRecordReqForm> list = guaranteeForm.getList();
    if (list != null && list.size() != 0) {
      for (PushGuarantRecordReqForm reqForm : list) {
        UserExample example = new UserExample();
        if (reqForm.getGuarantorType() == 0 || reqForm.getGuarantorType() == 2
            || reqForm.getGuarantorType() == 3) {
          example.createCriteria().andCompanyCardTypeEqualTo(reqForm.getType())
              .andCompanyCardCodeEqualTo(reqForm.getCode());
        } else if (reqForm.getGuarantorType() == 1) {
          example.createCriteria().andMobileEqualTo(reqForm.getGuarantMobile());
        }
        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() != 0) {
          User us = userList.get(0);
          long guarantorId = us.getId();
          insertGuarant(guarantorId, reqForm);
        } else {
          // 没查询出信息 创建担保对象
          Date date = new Date();
          UserLogin userLogin = new UserLogin();
          String mobile = reqForm.getGuarantMobile();
          userLogin.setMobile(mobile);
          String password = mobile.substring(mobile.length() - 6, mobile.length());
          userLogin.setPassword(BCrypt.hashpw(password, salt));
          userLogin.setIsEnabled(Constant.COMMONE_ONE);
          userLogin.setCreateTime(date);
          userLogin.setCreateId(-1l);
          userLoginMapper.insertSelective(userLogin);
          User user = new User();
          user.setId(userLogin.getId());
          user.setGuaranteeAmount(reqForm.getGuaranteeAmount());
          user.setMobile(mobile);// 担保人手机号
          user.setCompanyCardCode(reqForm.getCode());// 担保公司企业证件号码
          // 企业社会统一代码
          if (reqForm.getType() != null && reqForm.getType().equals("1")) {
            user.setSocialCreditCode(reqForm.getCode());
          }
          if (reqForm.getGuarantorType() != null && reqForm.getGuarantorType() == 1) {
            user.setUserType(0);
          } else {
            user.setUserType(1);
          }
          user.setCompanyCardType(reqForm.getType());
          user.setUserRole(reqForm.getGuarantorType());// 担保类型
          user.setIsEnabled(Constant.COMMONE_ONE);
          user.setCreateId(-1l);
          user.setCreateTime(date);
          user.setModifyTime(new Date());
          userMapper.insertSelective(user);
          long guarantorId = user.getId();
          logger.info("新增的担保人id" + guarantorId);
          insertGuarant(guarantorId, reqForm);
        }
      }
    }
  }

  // 保存担保记录信息
  public void insertGuarant(Long guarantorId, PushGuarantRecordReqForm reqForm) {
    GedOrderGuarantor guarantor = new GedOrderGuarantor();
    guarantor.setGuarantorId(guarantorId);// 担保id
    guarantor.setGuarantorType(reqForm.getGuarantorType());// 担保类型
    guarantor.setCreateTime(new Date());
    if (StringUtils.isBlank(reqForm.getOrderCode())) {// 子订单编号为空 则为非联合授信
      logger.info("非联合授信  订单编号" + reqForm.getMasterOrderCode());
      GedOrder gedOrder = orderService.selectOrderByOrderNo(reqForm.getMasterOrderCode()); // 根据订单编号查询订单主键id
      if (gedOrder != null)
        guarantor.setOrderId(gedOrder.getId());// 订单id
    } else {
      logger.info("联合授信  订单编号" + reqForm.getOrderCode());
      GedOrder gedOrder = orderService.selectOrderByOrderNo(reqForm.getOrderCode()); // 根据订单编号查询订单主键id
      if (gedOrder != null)
        guarantor.setOrderId(gedOrder.getId());// 订单id
    }
    guarantor.setStatus(0);
    guarantor.setBorrowGuarantorId(reqForm.getBorrowGuarantorId());// 借款系统担保人id
    gedOrderGuarantorMapper.insertSelective(guarantor);// 新增担保记录表
  }

  @Override
  public List<ConfirmGuaranteeListResForm> confirmGuaranteeList(Long userId) {
    List<ConfirmGuaranteeListResForm> returnDatas = new ArrayList<>();
    List<GedOrder> orders = new ArrayList<>();
    List<Map<String, Object>> maps = new ArrayList<>();
    User user = userService.getUserById(userId);
    if (user == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保列表用户信息查询失败");
    }
    List<GedOrderGuarantor> datas = selectGedOrderGurantById(userId, 0);
    if (datas == null || datas.size() == 0)
      return null;
    else {
      for (GedOrderGuarantor g : datas) {

        GedOrder order = orderService.selectByPrimy(g.getOrderId());
        if (order != null && order.getStatus() < OrderStatus.YHQ.getCode()){
          Map<String, Object> map = new HashedMap();
          map.put("order", order);
          map.put("g", g);
          maps.add(map);
        }

      }
      // 傻逼chanpinyaodenimad排序
      bubbleSort1(maps);

      for (Map<String, Object> m : maps) {
        GedOrder order = (GedOrder) m.get("order");
        GedOrderGuarantor g = (GedOrderGuarantor) m.get("g");
        ConfirmGuaranteeListResForm res = new ConfirmGuaranteeListResForm();
        orders.add(order);
        if (order == null)
          throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保列表订单信息查询失败");
        res.setLoanPurpose(order.getLoanPurpose() + "");
        if (order.getLoanPurpose() != null) {
          SystemDictionaryItem item =
              dictionaryService.getDictionaryByParam("LOAN_PURPOST", order.getLoanPurpose() + "");
          res.setLoanPurpose(item == null ? "" : item.getValue());
        }
        if (order.getLoanType() != null) {
          SystemDictionaryItem item =
              dictionaryService.getDictionaryByParam("LOAN_TYPE", order.getLoanType() + "");
          res.setLoanType(item == null ? "" : item.getValue());
        }

        copyUtil(user, res, order, g);
        returnDatas.add(res);
      }
    }
    return returnDatas;
  }

  private void copyUtil(User user, GuaranteeBaseForm res, GedOrder order, GedOrderGuarantor g) {
    // @ApiModelProperty("借款人名称")
    // @ApiModelProperty("借款金额")
    // @ApiModelProperty("借款期限")
    // @ApiModelProperty("日利率")
    // @ApiModelProperty("还款方式")
    // @ApiModelProperty("订单状态")
    // @ApiModelProperty("订单编号")
    res.setGuaranteeId(g.getId().intValue() + "");
    res.setName(order.getCompanyName());
    res.setLoanAmount(order.getLoanAmount() == null ? ""
        : order.getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setLoanTerm(order.getLoanTerm() + "");
    res.setRateDay(order.getRateDay().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setRepayWay(order.getRepaymentStyle());
    res.setStatus(OrderStatus.resove(order.getStatus()).getFeStatusDesc());
    res.setOrderNo(order.getOrderCode());
  }

  @Override
  public PageInfo<GuaranteeRecordListResForm> guaranteeRecordList(Long userId, Integer pageNum,
      Integer pageSize) {
    List<GuaranteeRecordListResForm> returnDatas = new ArrayList<>();
    User user = userService.getUserById(userId);
    if (user == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "担保列表用户信息查询失败");
    }
    PageHelper.startPage(pageNum, pageSize);
    List<GedOrderGuarantor> datas = selectGedOrderGurantById(userId, 1);
    PageInfo pageInfo = new PageInfo(datas);
    if (datas == null || datas.size() == 0)
      return null;
    else {
      for (GedOrderGuarantor g : datas) {
        GuaranteeRecordListResForm res = new GuaranteeRecordListResForm();
        GedOrder order = orderService.selectByPrimy(g.getOrderId());
        if (order == null)
          throw new BusinessException(Errors.BORROWER_EXCEPTION, "担保列表订单信息查询失败");

        copyUtil(user, res, order, g);
        res.setCashDeposit(order.getCashDeposit().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        res.setGuaranteeFee(order.getGuaranteeFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
        res.setGuaranteeStatus(g.getStatus() + "");
        returnDatas.add(res);
      }
      pageInfo.setList(returnDatas);
    }
    return pageInfo;
  }

  @Override
  public ConfirmGuaranteeDetailResForm confirmGuaranteeDetail(Long guaranteeId, Long userId)
      throws Exception {
    GedOrderGuarantor guarantor = gedOrderGuarantorMapper.selectByPrimaryKey(guaranteeId);
    if (guarantor == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情担保信息查询失败");
    }
    GedOrder order = orderService.selectByPrimy(guarantor.getOrderId());
    if (order == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情担订单息查询失败");
    }
    User user = userService.getUserById(userId);
    if (order == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情用户息查询失败");
    }
    ConfirmGuaranteeDetailResForm res = new ConfirmGuaranteeDetailResForm();
    copyUtil(user, res, order, guarantor);
    res.setCashDeposit(
        order.getReceivableCashDeposit().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setGuaranteeFee(
        order.getReceivableGuaranteeFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setGuaranteeStatus(guarantor.getStatus() + "");
    if (order.getLoanPurpose() != null) {
      SystemDictionaryItem item =
          dictionaryService.getDictionaryByParam("LOAN_PURPOST", order.getLoanPurpose() + "");
      res.setLoanPurpose(item == null ? "" : item.getValue());
    }
    if (order.getLoanType() != null) {
      SystemDictionaryItem item =
          dictionaryService.getDictionaryByParam("LOAN_TYPE", order.getLoanType() + "");
      res.setLoanType(item == null ? "" : item.getValue());
    }
    // OrderVerify condition = new OrderVerify();
    // condition.setOrderId(order.getId());
    // OrderVerify orderVerify = this.getOrderVerifyByCondition(condition);
    OrderVerifyResForm orderVerify =
        orderVerifyService.selectOrderVerify(order.getMasterOrderCode(), order.getOrderCode());
    if (orderVerify != null)
      copyOrderVerify(res, orderVerify);
    return res;
  }

  @Override
  public GuaranteeRecordDetailResForm guaranteeRecordDetail(Long guaranteeId, Long userId)
      throws Exception {
    GedOrderGuarantor guarantor = gedOrderGuarantorMapper.selectByPrimaryKey(guaranteeId);
    if (guarantor == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情担保信息查询失败");
    }
    GedOrder order = orderService.selectByPrimy(guarantor.getOrderId());
    if (order == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情担订单息查询失败");
    }
    User user = userService.getUserById(userId);
    if (order == null) {
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "确认担保详情用户息查询失败");
    }
    GuaranteeRecordDetailResForm res = new GuaranteeRecordDetailResForm();
    copyUtil(user, res, order, guarantor);
    // res.setCashDeposit(order.getCashDeposit().setScale(2,BigDecimal.ROUND_HALF_DOWN)+"");
    // res.setGuaranteeFee(order.getGuaranteeFee().setScale(2,BigDecimal.ROUND_HALF_DOWN)+"");
    res.setCashDeposit(
        order.getReceivableCashDeposit().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setGuaranteeFee(
        order.getReceivableGuaranteeFee().setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    res.setGuaranteeStatus(guarantor.getStatus() + "");
    // 查询还款日
    OrderLog orderLog =
        orderLogService.getOrderLogByOrderId(order.getId(), OrderStatus.FKCG.getCode());
    if (orderLog != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(orderLog.getCreateTime());
      // loan.setRepayDate(getRepaymentDay(calendar) + "/月");
      res.setReplayDate(orderBorrowerService.getRepaymentDay(calendar) + "");
    }
    if (order.getLoanPurpose() != null) {
      SystemDictionaryItem item =
          dictionaryService.getDictionaryByParam("LOAN_PURPOST", order.getLoanPurpose() + "");
      res.setLoanPurpose(item == null ? "" : item.getValue());
    }
    if (order.getLoanType() != null) {
      SystemDictionaryItem item =
          dictionaryService.getDictionaryByParam("LOAN_TYPE", order.getLoanType() + "");
      res.setLoanType(item == null ? "" : item.getValue());
    }
    OrderVerify condition = new OrderVerify();
    condition.setOrderId(order.getId());

    // 获取还款计划
    // 2 查询还款计划列表
    if (order.getStatus() >= OrderStatus.FKCG.getCode()
        && order.getStatus() <= OrderStatus.YQYH.getCode()) {
//      String url = orderService.getContractUrlByContractNo(order.getContractCode());
//      res.setContractUrl(url);

      // 2 查询还款计划列表
      Map<String, String> param = new HashMap<>();
      param.put("dateFlag", "");
      param.put("repayPeriodStatus", "");
      param.put("orderNo", order.getOrderCode());
      param.put("jointCredit", JointCreditFlag.NOT_JOINTCREDIT.getCode() + "");

      List<RepaymentItem> datas = new ArrayList<>();
      List<DeductResult> list = null;
      Map<String, Object> map = orderService.LoanSystem(param);
      if (map != null)
        list = (List<DeductResult>) map.get("planList");
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Integer count = 0;
      Integer overdueNum = -1; // 逾期记录位置
      Integer nomalNum = -1; // 正常还款记录位置
      if (list != null && list.size() != 0) {
        for (DeductResult deductResult : list) {
          if (deductResult.getRepayPeriodStatus().equals("0200")
                  || deductResult.getRepayPeriodStatus().equals("0300")) {
            // 查找逾期记录首条
            if (overdueNum < 0)
              if (deductResult.getRepayPeriodStatus().equals("0300"))
                overdueNum = count;
            // 查找未逾期的下一期记录
            if (nomalNum < 0)
              if (deductResult.getRepayPeriodStatus().equals("0200") && simpleDateFormat
                      .parse(deductResult.getDeductDate()).getTime() >= simpleDateFormat
                      .parse(simpleDateFormat.format(new Date(System.currentTimeMillis())))
                      .getTime())
                nomalNum = count;
          }

          RepaymentItem repaymentItem = new RepaymentItem();
          orderBorrowerService.repayCopyItem(deductResult, repaymentItem);
          if(!(deductResult !=null && deductResult.getCustId()!=null && !deductResult.getCustId().equals("") && user.getGetCustId() != null && Integer.parseInt(deductResult.getCustId()) == Integer.parseInt(user.getGetCustId()))){
            repaymentItem.setCompensatoryAmount("");
          }
          datas.add(repaymentItem);
          count++;
        }
        Integer thisPerisNum = -1;
        if (nomalNum >= 0) {
          thisPerisNum = nomalNum;
        }
        if (thisPerisNum != -1) {
          if (thisPerisNum == 0) {
            if (list.get(thisPerisNum).getRepayPeriodStatus().equals("0200")) {
              datas.get(thisPerisNum).setRepayPeriodStatus("待还款");
            }
          } else if (thisPerisNum > 0) {
            if (!list.get(thisPerisNum - 1).getRepayPeriodStatus().equals("0200")
                    && list.get(thisPerisNum).getRepayPeriodStatus().equals("0200")
                    && simpleDateFormat.parse(list.get(thisPerisNum - 1).getDeductDate())
                    .getTime() >= simpleDateFormat
                    .parse(simpleDateFormat.format(new Date(System.currentTimeMillis())))
                    .getTime()) {
            } else {
              datas.get(thisPerisNum).setRepayPeriodStatus("待还款");
            }
          }
        }
      }
      res.setRepaymentPlans(datas);
      isCanpay(list, datas);
    }
    return res;
  }

  public void isCanpay(List<DeductResult> list, List<RepaymentItem> datas) throws ParseException {
    Integer count = 0;
    Integer overdueNum = -1; // 逾期记录位置
    Integer nomalNum = -1; // 正常还款记录位置
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    if (list != null && list.size() > 0) {
      for (DeductResult deductResult : list) {
        if (deductResult.getRepayPeriodStatus().equals("0200")
            || deductResult.getRepayPeriodStatus().equals("0300")) {
          // 查找逾期记录首条
          if (overdueNum < 0)
            if (deductResult.getRepayPeriodStatus().equals("0300"))
              overdueNum = count;
          // 查找未逾期的下一期记录
          if (nomalNum < 0)
            if (deductResult.getRepayPeriodStatus().equals("0200") && simpleDateFormat
                .parse(deductResult.getDeductDate()).getTime() >= simpleDateFormat
                    .parse(simpleDateFormat.format(new Date(System.currentTimeMillis()))).getTime())
              nomalNum = count;
        }
        ++count;
      }

      if (nomalNum > -1) {
        datas.get(nomalNum).setIsCanPay("1");
      } else if (nomalNum == -1 && overdueNum > -1) {
        datas.get(datas.size() - 1).setIsCanPay("1");
      }
    }

  }

  public OrderVerify getOrderVerifyByCondition(OrderVerify orderVerify) {
    /*
     * OrderVerifyExample example = new OrderVerifyExample(); OrderVerifyExample.Criteria criteria =
     * example.createCriteria(); if (orderVerify.getOrderId() != null)
     * criteria.andOrderIdEqualTo(orderVerify.getOrderId()); List<OrderVerify> datas =
     * orderVerifyMapper.selectByExample(example); return datas.size() == 0 ? null : datas.get(0);
     */
    return null;
  }

  private void copyOrderVerify(ConfirmGuaranteeDetailResForm res, OrderVerifyResForm orderVerify) {
    res.setInfoCompanyInfo(orderVerify.getCompanyInfo());
    res.setInfoLoanPurpose(orderVerify.getLoanPurpose());
    res.setInfoCompanyProductInfo(orderVerify.getCompanyProductInfo());
    res.setInfoBorrowerLitigation(orderVerify.getBorrowerLitigation());
    res.setInfoBorrowerActInfo(orderVerify.getBorrowerActInfo());
    res.setInfoBorrowerDebtInfo(orderVerify.getBorrowerDebtInfo());
    res.setInfoBankLoanInfo(orderVerify.getBankLoanInfo());
    res.setInfoOverdueNumber(orderVerify.getOverdueNumber());
    res.setInfoAssetsInfo(orderVerify.getAssetsInfo());
    res.setInfoRepayChanel(orderVerify.getRepayChanel());
    res.setInfoBorrowerSanction(orderVerify.getBorrowerSanction());
  }

  @Override
  public void cascadingDeletion(String masterOrderNo) {
    List<GedOrder> list = orderService.selectOrderByMasterOrderNo(masterOrderNo);
    if (list != null && list.size() != 0) {
      for (GedOrder gedOrder : list) {
        Long orderId = gedOrder.getId();
        detetetionGuar(orderId);
      }
    }
  }

  @Override
  public void detetetionGuar(Long orderId) {
    GedOrderGuarantorExample example = new GedOrderGuarantorExample();
    GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
    criteria.andOrderIdEqualTo(orderId);
    gedOrderGuarantorMapper.deleteByExample(example);
  }

  @Override
  public GuarantorIndexResForm index(Long userId) throws Exception {
    User user = userService.getUserById(userId);
    if (user == null)
      throw new BusinessException(Errors.BORROWER_EXCEPTION, "借款人首页user查询失败");

    GuarantorIndexResForm res = new GuarantorIndexResForm();

    // 第一版面
    res.setUserName(user.getUsername());
    res.setMobile(nullUtil(user.getMobile()));
    res.setSocialCreditCode(nullUtil(user.getSocialCreditCode()));
    res.setIsOpenAccount(orderBorrowerService.isOpenAccount(user)+"");

    // 第二版面 获取担保信息
    List<GedOrderGuarantor> list = selectGedOrderGurantById(user.getId(), 1);

    // List<GedOrder> orders = orderService.getOrderingByCondition(condition , 1);
    List<GedOrder> orders = new ArrayList<>();
    if (list != null && list.size() > 0) {
      for (GedOrderGuarantor g : list) {
        GedOrder condition = new GedOrder();
        condition.setId(g.getOrderId());
        GedOrder o = orderService.getOneOrderingByCondition(condition, 1);
        if (o != null && o.getStatus() < OrderStatus.YHQ.getCode())
          orders.add(o);
      }
    }
    // 计算担保人总担保额度
    BigDecimal sumGuranteeAmount = new BigDecimal(0);
    if (orders != null && orders.size() > 0) {
      for (GedOrder oo : orders) {
        if (oo.getLoanAmount() != null)
          sumGuranteeAmount = sumGuranteeAmount.add(oo.getLoanAmount());
      }
    }
    if (user.getUserRole() != null && user.getUserRole() == 3) {
      // @ApiModelProperty("剩余担保额度")
      res.setRemainGuaranteeFeeAount(user.getGuaranteeAmount().subtract(sumGuranteeAmount)
          .setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");

    }
    // @ApiModelProperty("总担保额度"){}
    res.setGuaranteeFeeAount(sumGuranteeAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // @ApiModelProperty("账户担保余额")
    res.setAccountGuaranteeFeeAount(
        accountService.queryAccountBalance(userId).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "");
    // res.setAccountGuaranteeFeeAount(
    // orderService.accountBalance(null, userId).setScale(4, BigDecimal.ROUND_HALF_DOWN) + "");

    if (orders == null || orders.size() == 0)
      res.setCount("0");
    else
      res.setCount(orders.size() + "");

    return res;
  }

  private String nullUtil(String msg) {
    return msg == null ? "" : msg;
  }

  public List<GedOrderGuarantor> selectGedOrderGurantById(Long guarantorId, Integer status,
      Integer pageNum, Integer pageSize) {
    pageNum = pageNum == null ? 1 : pageNum;
    pageSize = pageSize == null ? 10 : pageSize;
    GedOrderGuarantorExample example = new GedOrderGuarantorExample();
    example.setOrderByClause("create_time desc");
    GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
    criteria.andGuarantorIdEqualTo(guarantorId);
    if (status != null)
      criteria.andStatusEqualTo(status);
    PageHelper.startPage(pageNum, pageSize);
    List<GedOrderGuarantor> Guarantorlist = gedOrderGuarantorMapper.selectByExample(example);
    PageInfo<GedOrderGuarantor> pageInfo = new PageInfo<GedOrderGuarantor>(Guarantorlist);
    return pageInfo.getList();
  }

  public List<GedOrderGuarantor> selectGedOrderGurantById(Long guarantorId, Integer status) {
    GedOrderGuarantorExample example = new GedOrderGuarantorExample();
    example.setOrderByClause("create_time desc");
    GedOrderGuarantorExample.Criteria criteria = example.createCriteria();
    criteria.andGuarantorIdEqualTo(guarantorId);
    if (status != null)
      criteria.andStatusEqualTo(status);
    List<GedOrderGuarantor> Guarantorlist = gedOrderGuarantorMapper.selectByExample(example);
    return Guarantorlist;
  }


  public List<GuaranteeConfirmResForm> GuaResList(List<GedOrderGuarantor> guarantorList,
      List<GuaranteeConfirmResForm> resList) {
    if (guarantorList != null && guarantorList.size() != 0) {
      for (GedOrderGuarantor guarantor : guarantorList) {
        Long orderId = guarantor.getOrderId();// 订单id
        GedOrder gedOrder = orderService.selectByPrimy(orderId);
        if (gedOrder != null && gedOrder.getStatus() < OrderStatus.YHQ.getCode()) {
          GuaranteeConfirmResForm resForm = new GuaranteeConfirmResForm();
          resGuarant(gedOrder, resForm);
          resList.add(resForm);
        }
      }
    }
    return resList;
  }


  public void resGuarant(GedOrder gedOrder, GuaranteeConfirmResForm resForm) {
    resForm.setLoanAmount(gedOrder.getLoanAmount());// 借款金额
    resForm.setApplyDate(DateFormatUtils.formatDate(gedOrder.getCreateTime(), "YYYY-MM-dd"));// 申请日期
    resForm.setLoanTerm(gedOrder.getLoanTerm() + "个月");// 借款期限
    if (gedOrder.getRateDay() != null) {
      resForm.setRateDay(gedOrder.getRateDay() + "%");// 月利率
    }
    resForm.setLoanUser(gedOrder.getCompanyName());// 借款人
    resForm.setStatus(OrderStatus.resove(gedOrder.getStatus()).getFeStatus());// 状态
    resForm.setOrderNo(gedOrder.getOrderCode());// 订单编号
  }

  /**
   * 冒泡排序
   * 
   */
  public static void bubbleSort1(List<Map<String, Object>> list) {
    if (list == null || list.size() == 0 || list.size() == 1)
      return;
    int i, j;
    int n = list.size();
    for (i = 0; i < list.size(); i++) {// 表示n次排序过程。

      for (j = 1; j < n - i; j++) {
        GedOrder order1 = (GedOrder) list.get(j - 1).get("order");
        GedOrder order2 = (GedOrder) list.get(j).get("order");
        if (order1.getCreateTime().getTime() < order2.getCreateTime().getTime()) {// 前面的数字大于后面的数字就交换
          // 交换a[j-1]和a[j]
          Map<String, Object> temp;
          temp = list.get(j - 1);
          list.set(j - 1, list.get(j));
          list.set(j, temp);
        }
      }
    }
  }// end
}

package com.resoft.accounting.checkAccount.dao;

import com.resoft.accounting.checkAccount.entity.CheckAccount;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import java.util.List;

/**
 * .
 *
 * @author SeppSong
 */
@MyBatisDao
public interface CheckAccountDao {

    /**
     * 查询合同信息
     * @param checkAccount 合同信息条件
     * @return 合同信息
     */
    CheckAccount get(CheckAccount checkAccount);

    /**
     * 查询合同信息列表
     * 未进行申请的还款中合同
     * @param checkAccount 合同条件
     * @return 合同列表
     */
    List<CheckAccount> list(CheckAccount checkAccount);

    /**
     * 查询合同信息列表
     * 已完成一次申请的还款中合同
     * @param checkAccount 合同条件
     * @return 合同列表
     */
    List<CheckAccount> listDone(CheckAccount checkAccount);
}

package com.resoft.accounting.checkAccount.service;

import com.resoft.accounting.checkAccount.entity.CheckAccount;
import java.util.List;

/**
 * 查账列表.
 *
 * @author SeppSong
 */
public interface CheckAccountService {

    /**
     * 查询合同信息
     * @param contractNo 合同编号
     * @return 合同信息
     */
    CheckAccount getCheckAccount(String contractNo);

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

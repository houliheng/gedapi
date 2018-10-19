package com.resoft.accounting.checkAccount.service;

import com.resoft.accounting.checkAccount.entity.ApplyStatusEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;

/**
 * 查账申请.
 *
 * @author SeppSong
 */
public interface CheckAccountApplyService {
    /**
     * 获取查账申请信息
     * @param checkAccountApply 查账申请条件
     * @return 查账申请信息
     */
    CheckAccountApply get(CheckAccountApply checkAccountApply);

    /**
     * 获取保存的查账信息
     * @param contractNo 合同编号
     * @param applyStatus 申请状态
     * @return 查账信息
     */
    CheckAccountApply get(String contractNo, ApplyStatusEnum applyStatus);

    /**
     * 保存查账申请信息
     * @param checkAccountApply 查账申请信息
     */
    void save(CheckAccountApply checkAccountApply);

    /**
     * 更新查账申请信息
     * @param checkAccountApply 查账申请信息
     */
    void update(CheckAccountApply checkAccountApply);


}

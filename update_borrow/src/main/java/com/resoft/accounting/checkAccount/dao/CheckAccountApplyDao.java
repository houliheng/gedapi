package com.resoft.accounting.checkAccount.dao;

import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 查账申请.
 *
 * @author SeppSong
 */
@MyBatisDao
public interface CheckAccountApplyDao {
    /**
     * 获取查账申请信息
     *
     * @param checkAccountApply 查账申请条件
     * @return 查账申请信息
     */
    CheckAccountApply get(CheckAccountApply checkAccountApply);

    /**
     * 保存查账申请信息
     *
     * @param checkAccountApply 查账申请信息
     */
    void insert(CheckAccountApply checkAccountApply);

    /**
     * 更新查账申请信息
     *
     * @param checkAccountApply 查账申请信息
     */
    void update(CheckAccountApply checkAccountApply);
}

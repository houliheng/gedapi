package com.resoft.accounting.checkAccount.dao;

import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import java.util.List;

/**
 * 申请查账历史.
 *
 * @author SeppSong
 */
@MyBatisDao
public interface CheckAccountHisDao {
    /**
     * 查询查账申请历史
     * @param checkAccountHistory 查账历史条件
     * @return 查账申请历史
     */
    List<CheckAccountHistory> list(CheckAccountHistory checkAccountHistory);

    /**
     * 插入查账申请记录
     * @param checkAccountHistory 查账申请
     */
    void insert(CheckAccountHistory checkAccountHistory);
}

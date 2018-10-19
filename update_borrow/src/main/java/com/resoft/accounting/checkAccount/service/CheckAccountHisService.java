package com.resoft.accounting.checkAccount.service;

import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import java.util.List;

/**
 * 申请查账历史.
 *
 * @author SeppSong
 */
public interface CheckAccountHisService {

    /**
     * 查询查账历史列表
     * @param history 历史条件
     * @return 查账历史列表
     */
    List<CheckAccountHistory> list(CheckAccountHistory history);

    /**
     * 新增查账历史
     * @param checkAccountHistory 查账历史
     */
    void insert(CheckAccountHistory checkAccountHistory);
}

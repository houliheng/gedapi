package com.resoft.accounting.checkAccount.service.impl;

import com.resoft.accounting.checkAccount.dao.CheckAccountHisDao;
import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import com.resoft.accounting.checkAccount.service.CheckAccountHisService;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 申请查账历史.
 *
 * @author SeppSong
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC",
    readOnly = true,
    rollbackFor = Exception.class)
public class CheckAccountHisServiceImpl implements CheckAccountHisService {

    private final CheckAccountHisDao checkAccountHisDao;

    @Autowired
    public CheckAccountHisServiceImpl(CheckAccountHisDao checkAccountHisDao) {
        this.checkAccountHisDao = checkAccountHisDao;
    }

    public List<CheckAccountHistory> list(CheckAccountHistory history) {
        return checkAccountHisDao.list(history);
    }

    @Override
    @Transactional(value = "ACC",
        rollbackFor = Exception.class)
    public void insert(CheckAccountHistory checkAccountHistory) {
        checkAccountHisDao.insert(checkAccountHistory);
    }
}

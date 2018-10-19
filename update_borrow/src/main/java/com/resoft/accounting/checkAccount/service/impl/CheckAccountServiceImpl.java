package com.resoft.accounting.checkAccount.service.impl;

import com.resoft.accounting.checkAccount.dao.CheckAccountDao;
import com.resoft.accounting.checkAccount.entity.CheckAccount;
import com.resoft.accounting.checkAccount.service.CheckAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查账service.
 *
 * @author SeppSong
 */
@Service
public class CheckAccountServiceImpl implements CheckAccountService {

    private final CheckAccountDao checkAccountDao;

    @Autowired
    public CheckAccountServiceImpl(CheckAccountDao checkAccountDao) {
        this.checkAccountDao = checkAccountDao;
    }

    @Override
    public CheckAccount getCheckAccount(String contractNo) {
        CheckAccount checkAccount = new CheckAccount();
        checkAccount.setContractNo(contractNo);
        return checkAccountDao.get(checkAccount);
    }

    @Override
    public List<CheckAccount> list(CheckAccount checkAccount) {
        return checkAccountDao.list(checkAccount);
    }

    @Override
    public List<CheckAccount> listDone(CheckAccount checkAccount) {
        return checkAccountDao.listDone(checkAccount);
    }
}

package com.resoft.accounting.checkAccount.service.impl;

import com.resoft.accounting.checkAccount.dao.CheckAccountVerifyDao;
import com.resoft.accounting.checkAccount.entity.CheckAccountVerify;
import com.resoft.accounting.checkAccount.service.CheckAccountVerifyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查账审核.
 *
 * @author SeppSong
 */
@Service
public class CheckAccountVerifyServiceImpl implements CheckAccountVerifyService {

    private final CheckAccountVerifyDao checkAccountVerifyDao;

    @Autowired
    public CheckAccountVerifyServiceImpl(CheckAccountVerifyDao checkAccountVerifyDao) {
        this.checkAccountVerifyDao = checkAccountVerifyDao;
    }

    @Override
    public List<CheckAccountVerify> list(CheckAccountVerify checkAccountVerify) {
        return checkAccountVerifyDao.list(checkAccountVerify);
    }

    @Override
    public List<CheckAccountVerify> listDone(CheckAccountVerify checkAccountVerify) {
        return checkAccountVerifyDao.listDone(checkAccountVerify);
    }
}

package com.resoft.accounting.checkAccount.service.impl;

import com.resoft.accounting.checkAccount.dao.CheckAccountApplyDao;
import com.resoft.accounting.checkAccount.entity.ApplyStatusEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.resoft.accounting.checkAccount.service.CheckAccountApplyService;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 查账申请.
 *
 * @author SeppSong
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC",
    readOnly = true,
    rollbackFor = Exception.class)
public class CheckAccountApplyServiceImpl implements CheckAccountApplyService {

    private final CheckAccountApplyDao checkAccountApplyDao;

    @Autowired
    public CheckAccountApplyServiceImpl(CheckAccountApplyDao checkAccountApplyDao) {
        this.checkAccountApplyDao = checkAccountApplyDao;
    }

    @Override
    public CheckAccountApply get(CheckAccountApply checkAccountApply) {
        return checkAccountApplyDao.get(checkAccountApply);
    }

    @Override
    public CheckAccountApply get(String contractNo, ApplyStatusEnum applyStatus) {
        CheckAccountApply apply = new CheckAccountApply(contractNo, applyStatus.getValue());
        return checkAccountApplyDao.get(apply);
    }

    @Override
    @Transactional(value = "ACC",
        rollbackFor = Exception.class)
    public void save(CheckAccountApply checkAccountApply) {
        if (checkAccountApply.getId() != null) {
            checkAccountApplyDao.update(checkAccountApply);
        } else {
            checkAccountApply.preInsert();
            checkAccountApply.setStatus(0);
            checkAccountApplyDao.insert(checkAccountApply);
        }
    }

    @Override
    @Transactional(value = "ACC",
        rollbackFor = Exception.class)
    public void update(CheckAccountApply checkAccountApply) {
        checkAccountApplyDao.update(checkAccountApply);
    }

}

package com.resoft.accounting.bankAccountStatement.service;

import com.resoft.accounting.bankAccountStatement.dao.CheckAccountStatementDao;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountStatement;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行流水Service
 * @Author yxh
 * @date 2018-7-12
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class CheckAccountStatementService {
    @Autowired
    private CheckAccountStatementDao checkAccountStatementDao;
    /**
     * 插入流水
     */
    @Transactional(value = "ACC", readOnly = false)
    public void insertCheckAccountStatement(CheckAccountStatement checkAccountStatement){
        checkAccountStatementDao.insertCheckAccountStatement(checkAccountStatement);
    }
    /**
     * 银行流水列表
     */
    public Page<MyMap> checkAccountStatementList(Page<MyMap> page, MyMap paramMap) {
        paramMap.setPage(page);
        page.setList(checkAccountStatementDao.findAccountStatementListByPage(paramMap));
        return page;
    }

    @Transactional(value = "ACC", readOnly = false)
    public void insertCheckAccountStatementBatch(List<CheckAccountStatement> list) {
        checkAccountStatementDao.insertCheckAccountStatementBatch(list);
    }

    @Transactional(value = "ACC", readOnly = false)
    public void deleteCheckAccountStatement(CheckAccountStatement statement) {
        checkAccountStatementDao.deleteCheckAccountStatement(statement);
    }

    public List<CheckAccountStatement> listMatchBankStatement(CheckAccountApply checkAccountApply) {
        CheckAccountStatement checkAccountStatement = new CheckAccountStatement();
        checkAccountStatement.setTradeDate(checkAccountApply.getTradeDate());
        checkAccountStatement.setUserName(checkAccountApply.getOutAccountName());
        checkAccountStatement.setBankCode(checkAccountApply.getOutAccountBankCode());
        checkAccountStatement.setAccountNumber(checkAccountApply.getOutAccountNumber());
        return checkAccountStatementDao.listMatchBankStatement(checkAccountStatement);
    }

    public List<CheckAccountStatement> list(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            throw new Exception("银行流水ID列表不能为空");
        }
        String[] idArrays = ids.split(",");
        List idList = Arrays.asList(idArrays);
        return checkAccountStatementDao.listWitchIds(idList);
    }

    @Transactional(value = "ACC", rollbackFor = Exception.class)
    public void updateList(List<CheckAccountStatement> list) {
        checkAccountStatementDao.updateList(list);
    }
}

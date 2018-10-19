package com.resoft.accounting.bankAccountStatement.service;

import com.resoft.accounting.bankAccountStatement.dao.CheckAccountBankDao;
import com.resoft.accounting.bankAccountStatement.dao.MyDictDao;
import com.resoft.accounting.bankAccountStatement.entity.AccountNameForm;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountBank;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 查账银行业务Service
 *
 * @Author yxh
 * @date 2018-7-12
 */
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class CheckAccountBankService {
    @Autowired
    private CheckAccountBankDao checkAccountBankDao;
    @Autowired
    private MyDictService myDictService;

    /**
     * 新增收款账户
     */
    @Transactional(value = "ACC", readOnly = false)
    public void insertCheckAccountBank(CheckAccountBank checkAccountBank) {
       /* List<Dict> list = myDictService.findDictListByLabel(checkAccountBank.getReceiveBankName());
        if (list== null||list.size()==0){
            Dict dict = new Dict();
            dict.setId(getUniqueCode());
            dict.setLabel(checkAccountBank.getReceiveBankName());
            dict.setType("BANKS");
            dict.setDescription("银行范围");
            dict.setParentId("0");
            dict.setCreateDate(new Date());
            dict.setUpdateDate(new Date());
            dict.setSort(new Random(5).nextInt(100));
            dict.setDelFlag("0");
            dict.setSysNo("ALL");
            dict.setValue(getUniqueCode());
            myDictService.insertDict(dict);
            checkAccountBank.setReceiveBankCode(dict.getValue());
        }else {
            checkAccountBank.setReceiveBankCode(list.get(0).getValue());
        }*/
        checkAccountBank.setReceiveBankCode(getUniqueCode());
        checkAccountBankDao.insertCheckAccountBank(checkAccountBank);
    }
    private String getUniqueCode(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }


    /**
     * 修改收款账户状态
     */
    @Transactional(value = "ACC", readOnly = false)
    public void updateCheckAccountBankStatus(CheckAccountBank checkAccountBank) {
        checkAccountBankDao.updateCheckAccountBank(checkAccountBank);
    }

    public List<CheckAccountBank> loadBank() {
        return checkAccountBankDao.loadBank();
    }

    /**
     * 收款账户列表
     */
    public Page<MyMap> checkAccountList(Page<MyMap> page, MyMap paramMap) {
        paramMap.setPage(page);
        page.setList(checkAccountBankDao.findListByPage(paramMap));
        return page;
    }


    public List<CheckAccountBank> loadReceiveBankNumber(String receiveBankName) {
        return checkAccountBankDao.loadReceiveBankNumber(receiveBankName);
    }

    public AccountNameForm loadAccountName(String accountNum) {
        return checkAccountBankDao.loadAccountName(accountNum);
    }
    @Transactional(value = "ACC", readOnly = false)
    public void updateAccountBankByModel(CheckAccountBank checkAccountBank) {
        checkAccountBankDao.updateAccountBankByModel(checkAccountBank);
    }

    public CheckAccountBank getCheckAccountById(Integer id) {
        return checkAccountBankDao.getCheckAccountById(id);
    }

    public List<CheckAccountBank> loadBankImport() {
        return checkAccountBankDao.loadBankImport();
    }
}

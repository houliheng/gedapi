package com.resoft.accounting.bankAccountStatement.dao;

import com.resoft.accounting.bankAccountStatement.entity.AccountNameForm;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountBank;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CheckAccountBankDao {
    void insertCheckAccountBank(CheckAccountBank checkAccountBank);

    void updateCheckAccountBank(CheckAccountBank checkAccountBank);

    List<MyMap> findListByPage(Map<String, Object> paramMap);

    List<CheckAccountBank> loadBank();

    List<CheckAccountBank> loadBankImport();

    List<CheckAccountBank> loadReceiveBankNumber(@Param("receiveBankName") String receiveBankName);

    AccountNameForm loadAccountName(@Param("accountNum") String accountNum);

    void updateAccountBankByModel(CheckAccountBank checkAccountBank);

    CheckAccountBank getCheckAccountById(@Param("id") Integer id);
}

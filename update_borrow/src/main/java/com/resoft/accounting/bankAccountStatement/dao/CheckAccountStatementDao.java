package com.resoft.accounting.bankAccountStatement.dao;

import com.resoft.accounting.bankAccountStatement.entity.CheckAccountStatement;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface CheckAccountStatementDao {
    void insertCheckAccountStatement(CheckAccountStatement checkAccountStatement);
    List<MyMap> findAccountStatementListByPage(Map<String, Object> paramMap);

    void insertCheckAccountStatementBatch(List<CheckAccountStatement> checkAccountStatements);

    void deleteCheckAccountStatement(CheckAccountStatement statement);

    /**
     * 根据申请信息匹配银行流水列表
     * @param checkAccountStatement 申请信息
     * @return 银行流水列表
     */
    List<CheckAccountStatement> listMatchBankStatement(CheckAccountStatement checkAccountStatement);

    /**
     * 根据id列表查询银行流水列表
     * @param ids ID列表
     * @return 银行流水列表
     */
    List<CheckAccountStatement> listWitchIds(@Param("ids") List ids);

    /**
     * 更新银行流水列表
     * @param list 待更新列表
     */
    void updateList(List<CheckAccountStatement> list);

}

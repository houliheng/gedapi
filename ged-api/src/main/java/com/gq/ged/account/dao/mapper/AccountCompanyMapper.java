package com.gq.ged.account.dao.mapper;

import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.dao.model.AccountCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountCompanyMapper {
    int countByExample(AccountCompanyExample example);

    int deleteByExample(AccountCompanyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountCompany record);

    int insertSelective(AccountCompany record);

    List<AccountCompany> selectByExample(AccountCompanyExample example);

    AccountCompany selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccountCompany record, @Param("example") AccountCompanyExample example);

    int updateByExample(@Param("record") AccountCompany record, @Param("example") AccountCompanyExample example);

    int updateByPrimaryKeySelective(AccountCompany record);

    int updateByPrimaryKey(AccountCompany record);
}
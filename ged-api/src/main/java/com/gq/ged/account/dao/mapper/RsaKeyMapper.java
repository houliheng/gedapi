package com.gq.ged.account.dao.mapper;

import com.gq.ged.account.dao.model.RsaKey;
import com.gq.ged.account.dao.model.RsaKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RsaKeyMapper {
    int countByExample(RsaKeyExample example);

    int deleteByExample(RsaKeyExample example);

    int insert(RsaKey record);

    int insertSelective(RsaKey record);

    List<RsaKey> selectByExample(RsaKeyExample example);

    int updateByExampleSelective(@Param("record") RsaKey record, @Param("example") RsaKeyExample example);

    int updateByExample(@Param("record") RsaKey record, @Param("example") RsaKeyExample example);
}
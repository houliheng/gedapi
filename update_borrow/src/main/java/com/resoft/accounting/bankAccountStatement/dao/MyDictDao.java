package com.resoft.accounting.bankAccountStatement.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface MyDictDao extends CrudDao<Dict> {
    List<Dict> findDictByLabel(@Param("label") String label);
}

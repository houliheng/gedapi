package com.resoft.credit.contract.dao;

import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.ContractProjectList;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * ClassName:ContractProjectListDao
 *
 * @Date 2018/5/22 11:37
 * @Author liangbin
 */
@MyBatisDao("contractProjectListDao")
public interface ContractProjectListDao extends CrudDao<ContractProjectList> {


}

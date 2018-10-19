package com.resoft.credit.contract.service;


import com.resoft.credit.contract.dao.ContractProjectListDao;
import com.resoft.credit.contract.entity.ContractProjectList;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:ContractProjectListService
 *
 * @Date 2018/5/22 11:47
 * @Author liangbin
 *  合同批复信息表
 */
@Service("contractProjectListService")
@DbType("cre.dbType")
@Transactional(value = "CRE",readOnly = true)
public class ContractProjectListService extends CrudService<ContractProjectListDao, ContractProjectList> {

    public Page<ContractProjectList> findPage(Page<ContractProjectList> page, ContractProjectList product) {
        return super.findPage(page, product);
    }

}

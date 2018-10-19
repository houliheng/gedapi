package com.resoft.accounting.bankAccountStatement.service;

import com.resoft.accounting.bankAccountStatement.dao.MyDictDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 *
 * @Author yxh
 * @date 2018-7-26
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class MyDictService {
    @Autowired
    private MyDictDao myDictDao;
    public List<Dict> findDictListByLabel(String label){
        return myDictDao.findDictByLabel(label);
    }
    @Transactional(value = "CRE", readOnly = false)
    public void insertDict(Dict dict){
        myDictDao.insert(dict);
    }
}

package com.resoft.credit.gedApiUser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.gedApiUser.entity.CreGedapiUser;

import java.util.List;

/**
 * 冠E贷账号DAO接口
 * @author lb
 * @version 2018-05-23
 */
@MyBatisDao
public interface CreGedapiUserDao extends CrudDao<CreGedapiUser> {

   // 根据custName 查询冠易贷证号信息
     public  CreGedapiUser  queryGedApiUserByCustName(String custName);

     public int deleteGedapiUserByGedNumber(String gedNumber);
}
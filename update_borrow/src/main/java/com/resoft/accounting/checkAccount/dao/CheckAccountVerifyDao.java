package com.resoft.accounting.checkAccount.dao;

import com.resoft.accounting.checkAccount.entity.CheckAccountVerify;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import java.util.List;

/**
 * 待查账/已查账.
 *
 * @author SeppSong
 */
@MyBatisDao
public interface CheckAccountVerifyDao {

    /**
     * 查询待查账列表
     * @param checkAccountVerify 待查账条件
     * @return 带查账列表
     */
    List<CheckAccountVerify> list(CheckAccountVerify checkAccountVerify);

    /**
     * 查询已查账列表
     * @param checkAccountVerify 查账条件
     * @return 已查账列表
     */
    List<CheckAccountVerify> listDone(CheckAccountVerify checkAccountVerify);
}

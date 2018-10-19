package com.resoft.credit.underCompanyInfo.dao;

import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 线下借款-企业信息DAO接口
 * @author jml
 * @version 2018-06-26
 */
@MyBatisDao
public interface UnderCompanyInfoDao extends CrudDao<UnderCompanyInfo> {

    void updayeStatus(@Param("status")String status, @Param("applyNo")String applyNo);

    public Integer updateByApplyNo(UnderCompanyInfo underCompanyInfos);

    public UnderCompanyInfo getByApplyNo(@Param("applyNo") String applyNo);

    /**
     * 更新机构信息
     * @param info 机构信息
     */
    void updateOrgInfoByApplyNo(UnderCompanyInfo info);

    AddOrderRequest pushOrder(@Param("applyNo")String applyNo);

    void updateGedAccount(@Param("gedNumber")String gedNumber,@Param("applyNo")String applyNo);

}
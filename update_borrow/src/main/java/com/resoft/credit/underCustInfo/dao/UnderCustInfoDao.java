package com.resoft.credit.underCustInfo.dao;

import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 线下借款-借款人基本信息DAO接口
 * @author jml
 * @version 2018-06-26
 */
@MyBatisDao
public interface UnderCustInfoDao extends CrudDao<UnderCustInfo> {

    /***
     * 根据申请编号获取客户信息
     * @param applyNo
     * @return
     */
    public UnderCustInfo getByApplyNo(@Param("applyNo") String applyNo);

    /***
     * 插入
     * @param underCustInfo
     * @return
     */
    public int insert(UnderCustInfo underCustInfo);

    /***
     * 更新
     * @param underCustInfo
     * @return
     */
    public int update(UnderCustInfo underCustInfo);

    /***
     * 根据applyno进行更新操作
     * @param underCustInfo
     * @return
     */
    public int updateByApplyNo(UnderCustInfo underCustInfo);


}
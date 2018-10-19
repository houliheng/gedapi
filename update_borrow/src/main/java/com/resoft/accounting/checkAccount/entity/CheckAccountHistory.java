package com.resoft.accounting.checkAccount.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import java.util.Date;

/**
 * 申请查账历史.
 *
 * @author SeppSong
 */
public class CheckAccountHistory{

    private static final long serialVersionUID = -173442459374569849L;

    private Long id;

    private String contractNo;

    private Long applyId;

    private String nodeName;

    private String content;

    private String remark;

    private User createBy;

    private Date createDate;

    public CheckAccountHistory() {
    }

    public CheckAccountHistory(String contractNo) {
        this.contractNo = contractNo;
    }

    public CheckAccountHistory(Long applyId) {
        this.applyId = applyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void preInsert() {
        this.createBy = UserUtils.getUser();
        this.createDate = new Date();
    }
}

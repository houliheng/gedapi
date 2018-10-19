package com.resoft.credit.noEntry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.noEntry.dao.UserNoEntryDao;
import com.resoft.credit.noEntry.entity.UserNoEntry;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 人员禁件表Service
 * @author lirongchao
 * @version 2016-01-08
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class UserNoEntryService extends CrudService<UserNoEntryDao, UserNoEntry> {
	
	@Autowired
	private UserNoEntryDao userNoEntryDao;

	public UserNoEntry get(String id) {
		return super.get(id);
	}
	
	public List<UserNoEntry> findList(UserNoEntry userNoEntry) {
		return super.findList(userNoEntry);
	}
	
	public Page<UserNoEntry> findPage(Page<UserNoEntry> page, UserNoEntry userNoEntry) {
		return super.findPage(page, userNoEntry);
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : userId
	 * @date-author:2016年01月13日-lirongchao:保存操作
	 * 										先遍历出禁件人员列表是否有需要删除的人员，如果有就进行将需要删除的人员删除
	 * 										先遍历出需要新增的禁件人员，避免数据重复
	 */		
	@Transactional(value="CRE",readOnly = false)
	public void save(UserNoEntry userNoEntry,String idsArr) {
		String ids[] = idsArr.split(",");
		List<UserNoEntry> userNoEntryList = userNoEntryDao.findList(userNoEntry);
		List<String> userIdList = new ArrayList<String>();
		if(ids.length > 0){//遍历出禁件人员列表是否有需要删除的人员
			for(UserNoEntry une: userNoEntryList){
				boolean flag = true;
				for(String userId:ids){
					if(userId.equals(une.getUserId())){
						flag = false;
						break;
					}
				}
				if(flag){
					userIdList.add(une.getId());
				}
			}
			if(userIdList.size()>0){
				//删除已添加的禁件人员
				userNoEntryDao.batchDelete(userIdList);
			}
			for(String userId:ids){//遍历出需要新增的禁件人员
				boolean flag = true;
				for(UserNoEntry une: userNoEntryList){
					if(userId.equals(une.getUserId())){
						flag = false;
						break;
					}
				}
				if(flag&&!StringUtils.isEmpty(userId)){
					UserNoEntry saveuserNoEntry = new UserNoEntry();
					saveuserNoEntry.setUserId(userId);
					super.save(saveuserNoEntry);
				}
			}			
		}		
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(UserNoEntry userNoEntry) {
		super.delete(userNoEntry);
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:批量删除人员禁件列表
	 */		
	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(List<String> idList){
		super.dao.batchDelete(idList);
	}
	
	/**
	 * 根据用户ID查询客户禁件列表
	 * @param userId
	 * @return
	 */
	public List<UserNoEntry> findListByUserId(String userId){
		return super.dao.findListByUserId(userId);
	};
}
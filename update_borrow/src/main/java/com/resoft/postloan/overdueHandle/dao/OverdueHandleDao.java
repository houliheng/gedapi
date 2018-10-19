package com.resoft.postloan.overdueHandle.dao;

import java.util.List;

import com.resoft.postloan.overdueHandle.entity.OverdueHandle;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 贷后逾期处理DAO接口
 * @author lixing
 * @version 2017-01-04
 */
@MyBatisDao
public interface OverdueHandleDao extends CrudDao<OverdueHandle> {
	public List<MyMap> findZBXFList(MyMap myMap);
	public List<MyMap> findZBXFDoneList(MyMap myMap);
	public List<MyMap> todoListAdmin(MyMap myMap);
	public List<MyMap> todoList(MyMap myMap);
	public List<MyMap> findZBXFAssignList(MyMap myMap);
	public List<MyMap> doneList(MyMap myMap);
	public List<MyMap> findListByOverdueDays(MyMap myMap);
	public List<MyMap> findListUnstarted(MyMap myMap);
}
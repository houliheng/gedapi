package com.resoft.credit.custRemoveBind.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.custRemoveBind.dao.CustRemoveBindDao;
import com.resoft.credit.custRemoveBind.entity.CustRemoveBind;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.CustomUserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CustRemoveBindService extends CrudService<CustRemoveBindDao, CustRemoveBind> {

	@Autowired
	private CustomUserDao customUserDao;
	
	public CustRemoveBind get(String id) {
		return super.get(id);
	}

	public CustRemoveBind getByIdCard(CustRemoveBind custRemoveBind) {
		return super.dao.getByIdCard(custRemoveBind);
	}

	// 页面查询
	public Page<CustRemoveBind> findPage(Page<CustRemoveBind> page, CustRemoveBind custRemoveBind) {
		return super.findPage(page, custRemoveBind);
	}

	// 绑定时查询所有用户
	public Page<User> findUserPage(Page<User> page, User user) {
		user.setPage(page);
		page.setList(customUserDao.findUserLists(user));
		return page;
	}

	// 绑定操作
	@Transactional(value = "CRE", readOnly = false)
	public void bind(CustRemoveBind custRemoveBind) {
		super.dao.bind(custRemoveBind);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(CustRemoveBind custRemoveBind) {
		super.save(custRemoveBind);
	}

	/**
	 * 根据证件类型、证件号判断当前登记客户是否已经绑定客户经理
	 * 
	 * @param custRemoveBind
	 *            - 仅有证件号条件
	 * @return Map<String, String>
	 * @return "flag" : "save" - 绑定且是当前登录人：正常保存、提交
	 * @return "flag" : "forbidden" - 绑定但不是当前登录人：不允许保存、提交，提示“当前客户已经绑定**客户经理！”
	 * @return "flag" : "bindAndSave" - 未绑定：新增绑定当前登录人后，正常保存、提交
	 * @return "flag" : "bindAndUpdate" - 未绑定：修改绑定当前登录人后，正常保存、提交
	 * @return "flag" : "false" - 其他异常情况
	 */
	public Map<String, Object> isAlreadyBind(CustRemoveBind custRemoveBind) {
		User loginUser = UserUtils.getUser();
		Map<String, Object> map = Maps.newConcurrentMap();
		CustRemoveBind custRemoveBindExisted = this.getByIdCard(custRemoveBind);
		if (custRemoveBindExisted != null && StringUtils.isNotBlank(custRemoveBindExisted.getId())) {
			if (Constants.BIND_STATUS_YBD.equals(custRemoveBindExisted.getIsBind())) {// 已绑定
				if (loginUser.getId().equals(custRemoveBindExisted.getUser().getId())) {// 已绑定且绑定客户经理为当前登录人
					map.put("flag", "save");
					return map;
				} else {// 已绑定但绑定客户经理不是当前登录人
					map.put("flag", "forbidden");
					map.put("bindUser", custRemoveBindExisted.getUser());
					return map;
				}
			} else if (Constants.BIND_STATUS_WBD.equals(custRemoveBindExisted.getIsBind())) {// 未绑定，客户绑定信息表中有数据，进行修改
				map.put("flag", "bindAndUpdate");
				map.put("custRemoveBindExisted", custRemoveBindExisted);
				return map;
			}
		} else {// 未绑定，客户绑定信息表中没有数据，进行插入
			map.put("flag", "bindAndSave");
			return map;
		}
		map.put("flag", "false");
		return map;
	}
}
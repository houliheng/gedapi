package com.resoft.multds.credit.plCustRemoveBind.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.credit.plCustRemoveBind.dao.PLCustRemoveBindDao;
import com.resoft.multds.credit.plCustRemoveBind.entity.PLCustRemoveBind;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class PLCustRemoveBindService extends CrudService<PLCustRemoveBindDao, PLCustRemoveBind> {

	public PLCustRemoveBind get(String id) {
		return super.get(id);
	}

	public PLCustRemoveBind getByIdCard(String idNum) {
		return super.dao.getByIdCard(idNum);
	}

	// 页面查询
	public Page<PLCustRemoveBind> findPage(Page<PLCustRemoveBind> page, PLCustRemoveBind PLCustRemoveBind) {
		return super.findPage(page, PLCustRemoveBind);
	}

	// 绑定操作
	@Transactional(value = "CRE", readOnly = false)
	public void bind(PLCustRemoveBind PLCustRemoveBind) {
		super.dao.bind(PLCustRemoveBind);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(PLCustRemoveBind PLCustRemoveBind) {
		super.save(PLCustRemoveBind);
	}

	/**
	 * 根据证件类型、证件号判断当前登记客户是否已经绑定客户经理
	 * 
	 * @param plCustRemoveBind
	 *            - 仅有证件号条件
	 * @return Map<String, String>
	 * @return "flag" : "save" - 绑定且是当前登录人：正常保存、提交
	 * @return "flag" : "forbidden" - 绑定但不是当前登录人：不允许保存、提交，提示“当前客户已经绑定**客户经理！”
	 * @return "flag" : "bindAndSave" - 未绑定：新增绑定当前登录人后，正常保存、提交
	 * @return "flag" : "bindAndUpdate" - 未绑定：修改绑定当前登录人后，正常保存、提交
	 * @return "flag" : "false" - 其他异常情况
	 */
	public Map<String, Object> isAlreadyBind(String idNum) {
		Map<String, Object> map = Maps.newConcurrentMap();
		PLCustRemoveBind plCustRemoveBindExisted = this.getByIdCard(idNum);
		if (plCustRemoveBindExisted != null && StringUtils.isNotBlank(plCustRemoveBindExisted.getId())) {
			if (Constants.BIND_STATUS_YBD.equals(plCustRemoveBindExisted.getIsBind())) {// 已绑定
				map.put("flag", "yes");
				map.put("bindUser", plCustRemoveBindExisted.getUser());
				return map;
			} else {// 未绑定，客户绑定信息表中有数据，进行修改
				map.put("flag", "no");
				return map;
			}
		} else {// 未绑定，客户绑定信息表中没有数据，进行插入
			map.put("flag", "no");
			return map;
		}
	}
}
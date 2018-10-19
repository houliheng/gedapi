<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>黑名单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		resetTip();
		$("#searchForm").validate({
		rules : {
		custName : {
			noLegalInput : true
		},
		idNum : {
			noLegalInput : true
		},
		mobile : {
			noLegalInput : true
		}
		},
		submitHandler : function(form) {
			if ($("#createStartTime").val() != '' & $("#createEndTime").val() != '') {
				var start = $("#createStartTime").val().split("-");
				var createStartTime = new Date(start[0], start[1], start[2]);
				var end = $("#createEndTime").val().split("-");
				var createEndTime = new Date(end[0], end[1], end[2]);
				if (createStartTime > createEndTime) {
					alertx("开始时间应小于结束时间！");
					return;
				} else {
					form.submit();
				}
			} else {
				form.submit();
			}
		}
		});
	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function fromReset() {
		$("#custName").val('');
		$("#listStatus").val('1');
		$("#idType").val('');
		$("#idNum").val('');
		$("#mobile").val('');
		$("#createStartTime").val('');
		$("#createEndTime").val('');
		$("#s2id_listStatus>.select2-choice>.select2-chosen").html('黑名单');
		$("#s2id_idType>.select2-choice>.select2-chosen").html('--全部--');
	}
	function addBlack() {
		var id = $("input[name=pcheck]:checked").val();
		var listStatus = $("input[name=pcheck]:checked").next("input[name=listStatus]:hidden").val();
		if (id == null) {
			alertx("请选择要加黑的用户！");
			return;
		}
		if ("2" == listStatus) {
			var url = "${ctx}/credit/blacklist/edit?id=" + id + "&listStatus=1";
			$.jBox.open("iframe:" + url, "加黑设置", 700, 500, {
			buttons : {
			"保存" : "ok",
			'取消' : true
			},
			submit : function(v, h, f) {
				if (v == "ok") {
					var flag = h.find("iframe")[0].contentWindow.$("#detailForm").valid();
					if (flag) {
						var formJson = h.find("iframe")[0].contentWindow.$("#detailForm").serializeJson();
						saveJson("${ctx}/credit/blacklist/editSave", JSON.stringify(removeDotProperty(formJson)), function(data) {
							if (data) {
								if (data.status == 1) {
									var searchUrl = "${ctx}/credit/blacklist/?returnVal=1";
									$("#searchForm").attr("action", searchUrl).submit();
								}
							}
						});
					} else {
						return false;
					}
				}
			},
			loaded : function(h) {
				$(".jbox-content", document).css("overflow-y", "hidden");
			},
			persistent : true
			});
		} else {
			alertx("你所选择的用户不是白名单用户，不能进行加黑操作！");
		}
	}
	function changeWhite() {
		var id = $("input[name=pcheck]:checked").val();
		var listStatus = $("input[name=pcheck]:checked").next("input[name=listStatus]:hidden").val();
		if (id == null) {
			alertx("请选择要刷白的用户！");
			return;
		}
		if ("1" == listStatus) {
			var url = "${ctx}/credit/blacklist/edit?id=" + id + "&listStatus=2";
			$.jBox.open("iframe:" + url, "刷白设置", 700, 500, {
			buttons : {
			"保存" : "ok",
			'取消' : true
			},
			submit : function(v, h, f) {
				if (v == "ok") {
					var flag = h.find("iframe")[0].contentWindow.$("#detailForm").valid();
					if (flag) {
						var formJson = h.find("iframe")[0].contentWindow.$("#detailForm").serializeJson();
						saveJson("${ctx}/credit/blacklist/editSave", JSON.stringify(removeDotProperty(formJson)), function(data) {
							if (data) {
								if (data.status == 1) {
									var searchUrl = "${ctx}/credit/blacklist/?returnVal=1";
									$("#searchForm").attr("action", searchUrl).submit();
								}
							}
						});
					} else {
						return false;
					}
				}
			},
			loaded : function(h) {
				$(".jbox-content", document).css("overflow-y", "hidden");
			},
			persistent : true
			});
		} else {
			alertx("你所选择的用户不是黑名单用户，不能进行刷白操作！");
		}
	}
	//详情
	function details(id) {
		openJBox("blacklist-details", "${ctx}/credit/blacklist/details?blacklistId=" + id, "详情", 1000, 700);
		//window.showModalDialog("${ctx}/credit/blacklist/details?blacklistId=" + id, null, "dialogWidth:1000px" + ";dialogHeight:700px" + ";status:no;help:no;resizable:yes;");
	}
</script>
</head>
<body>
	<!-- 
	 * @reqno: H1512210030
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-in-other	: custName -客户名称 : 客户名称
	 * @e-in-other	: listStatus -状态 : 状态
	 * @e-in-other	: idType -证件类型 : 证件类型
	 * @e-in-other	: idNum -证件号码: 证件号码
	 * @e-in-other	: mobile -移动电话 : 移动电话
	 * @e-in-other	: createStartTime -开始时间 : 开始时间
	 * @e-in-other	: createEndTime -结束时间 : 结束时间
	 * @e-out-other	: custName -客户名称 : 客户名称
	 * @e-out-other	: listStatusLabel -状态: 状态
	 * @e-out-other	: idTypeLabel -证件类型 : 证件类型
	 * @e-out-other	: idNum -证件号 : 证件号
	 * @e-out-other	: mobile -移动电话 : 移动电话
	 * @e-out-other	: name -创建人 : 创建人
	 * @e-out-other	: createDate -创建日期 : 创建日期
	 * @e-ctrl : details -详情 : 详情
	 * @e-ctrl : btnSubmit -查询 : 查询
	 * @e-ctrl : btnReset -重置: 重置
	 * @e-ctrl : addBlack -加黑: 加黑
	 * @e-ctrl : changeWhite -刷白 : 刷白
	 * @date-author:2015年12月24日-lirongchao:1.查询条件-【客户名称（模糊查询）、状态】、【证件类型、证件号码（模糊查询）】、【移动电话（模糊查询）、创建日期（开始结束时间）】；
											查询表单按钮-查询、重置；
											2.列表数据项-单选框按钮、客户名称、状态（黑名单、白名单）、证件类型、证件号、移动电话、创建人、创建日期、操作（详情）
											3.列表排序-创建日期降序
											4.表头按钮-加黑、刷白
	 -->
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="blacklist" action="${ctx}/credit/blacklist/" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="custType" name="custType" type="hidden" value="${blacklist.custType}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${blacklist.custName }" />
								</td>
								<td class="ft_label">状态：</td>
								<td class="ft_content">
									<form:select id="listStatus" name="listStatus" path="listStatus" class="input-medium" value="${blacklist.listStatus}">
										<form:option value="" label="  --全部--" />
										<form:options items="${fns:getDictList('BLACKLIST_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${blacklist.custType eq '1'}">
										<td class="ft_label">证件类型：</td>
										<td class="ft_content">
											<form:select id="idType" name="idType" path="idType" class="input-medium" value="${blacklist.idType}">
												<form:option value="" label="  --全部--" />
												<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
									</c:when>
									<c:otherwise>
										<td class="ft_label">证件类型：</td>
										<td class="ft_content">
											<form:select id="idType" name="idType" path="idType" class="input-medium" value="${blacklist.idType}">
												<form:option value="" label="  --全部--" />
												<form:options items="${fns:getDictList('CUSTOMER_C_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
									</c:otherwise>
								</c:choose>
								<td class="ft_label">证件号码：</td>
								<td class="ft_content">
									<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${blacklist.idNum }" />
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${blacklist.custType eq '1'}">
										<td class="ft_label">移动电话：</td>
										<td class="ft_content">
											<input id="mobile" name="mobile" type="text" maxlength="50" class="input-medium" value="${blacklist.mobile }" />
										</td>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								<td class="ft_label">创建日期：</td>
								<td class="ft_content" style="width: 240px;">
									<input id="createStartTime" name="createStartTime" type="text"  maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${blacklist.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({ onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									<label>&nbsp;--&nbsp;</label>
									<input id="createEndTime" name="createEndTime" type="text"  maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${blacklist.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({ onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<!-- 
	 * @reqno: H1512210031
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-ctrl : addBlack -加黑: 加黑
	 * @e-ctrl : changeWhite -刷白 : 刷白
	 * @date-author:2015年12月24日-lirongchao:   在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
	 -->
				<li class="mcp_info">
					<a href="#" id="addBlack" onclick="addBlack()">
						<span>
							<b></b>
							加黑
						</span>
					</a>
				</li>
				<li class="mcp_pic">
					<a href="#" id="changeWhite" onclick="changeWhite()">
						<span>
							<b></b>
							刷白
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px"></th>
						<th width="10%">客户名称</th>
						<th width="10%">状态</th>
						<th width="10%">证件类型</th>
						<th width="15%">证件号</th>
						<th width="10%">移动电话</th>
						<th width="10%">创建人</th>
						<th width="10%">创建日期</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="black" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="id">
							<input type="checkbox" value="${black.id}" name="pcheck" onclick="selectSingle('pcheck')" id="c_${index.count}">
							<input type="hidden" value="${black.listStatus}" name="listStatus">
						</td>
						<td id="custName" class="title" title="${black.custName}">${black.custName}</td>
						<td id="listStatusLabel" class="title" title="${black.listStatusLabel}">${black.listStatusLabel}</td>
						<td id="idTypeLabel" class="title" title="${black.idTypeLabel}">${black.idTypeLabel}</td>
						<td id="idNum" class="title" title="${black.idNum}">${black.idNum}</td>
						<td id="mobile" class="title" title="${black.mobile}">${black.mobile}</td>
						<td id="name" class="title" title="${black.createBy.name}">${black.createBy.name}</td>
						<td id="createDate" class="title">
							<fmt:formatDate value="${black.createDate }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<!-- 
	 * @reqno: H1512210032
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-ctrl : details -详情 : 详情
	 * @date-author:2015年12月24日-lirongchao: 	1.点击“个人黑名单管理”页面列表中的“详情”链接，弹出窗体，窗体名称“加黑详情”；
												2.“加黑详情”页面布局：上下布局，依次为：工具栏、加黑详情列表；
												3. 工具栏:关闭按钮，点击按钮，关闭当前窗体；
												4.加黑详情列表数据项：操作人、操作时间、操作类型（加黑、刷白）、设置说明；
												   列表排序：操作时间升序；
												   表格分页显示；
												   鼠标放到【操作说明】列时，以tip显示具体内容，以避免内容过多无法查看；
	 -->
						<td id="details" class="title">
							<a href="#" onclick="details('${black.id}')">详情</a>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
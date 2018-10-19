<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>两人外访管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//页面初始化后，设置“加黑说明”不可编辑。
		$("#blackTr").hide();
		$("#conclusionForm").validate({
		submitHandler : function(form) {
			//查询实时进件状态，若不是审批中状态，提示不可提交
			var flag = $("input[name='passFlag']:checked").val();
			if (flag == "black") {
				confirmx("确认加入黑名单吗？", function() {
					saveForm(form);
				});
			} else {
				saveForm(form);
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		adjustTextareaLength("suggestionDesc", "preSuggestionDesc");
		adjustTextareaLength("sugession", "preSuggestion");
	});
	function saveForm(form) {
		$.post("${ctx}/credit/applyRegister/isSubmitting", {
			applyNo : '${actTaskParam.applyNo}'
		}, function(data) {
			if (data == "true") {
				var flag = $("input[name='passFlag']:checked").val();
				if (flag == "yes") {
					if ('${visitCountFlag}' == "true") {
						loading('正在提交，请稍等...');
						form.submit();
					} else {
						alertx("两人外访未完成，不能提交！");
					}
				} else {
					loading('正在提交，请稍等...');
					form.submit();
				}
			} else if (data == "false") {
				alertx("进件参数发生错误,请联系管理员！");
			} else {
				alertx("进件正处于" + data + "状态，不可提交，请关闭窗口并刷新列表！");
			}
		});
	}
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blackTr").hide();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blackTr").show();
		}
	}

	//新增
	function add(urlSingle, title, custName, roleType, applyNo, custId) {
		custName = encodeX(custName);
		roleType = encodeX(roleType);
		var width = $(document).width() - 100;
		var height = $(document).height() - 100;
		var url = urlSingle + "&roleType=" + roleType + "&custName=" + custName + "&applyNo=" + applyNo + "&custId=" + custId;
		openJBox("", url, title, width, height);
	}
</script>
</head>
<body>
	<div class="tableList">
		<div>
			<h3 class="tableTitle">外访对象列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>外访对象</th>
							<th>姓名</th>
							<th>外访次数</th>
							<c:if test="${readOnly ne 'true' }">
								<th>操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${applyRelationList}" var="applyRelation" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="roleType" class="title" title="${applyRelation.roleType}">
									${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}
									</a>
								</td>
								<td id="custName" class="title" title="${applyRelation.custName}">${applyRelation.custName}</td>
								<td id="visitCount" class="title" title="外访次数">${applyRelation.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<td>
										<a id="add" href="javascript:void(0);" onclick="add('${ctx}/credit/checkCoupleDoubtful/form?','新增两人外访信息','${applyRelation.custName}','${applyRelation.roleType}','${applyRelation.applyNo}','${applyRelation.custId}');">外访</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">外访信息列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>外访对象</th>
							<th>姓名</th>
							<th>外访类型</th>
							<th>外访地点</th>
							<th>外访时间</th>
							<th>外访人</th>
							<th>外访意见</th>
							<shiro:hasPermission name="credit:checkCoupleDoubtful:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkCoupleDoubtfulList}" var="checkCoupleDoubtful" varStatus="i">
							<tr>
								<!-- 列表中添加序号 -->
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="roleType" class="title" title="${fns:getDictLabel(checkCoupleDoubtful.roleType, 'ROLE_TYPE', '')}">${fns:getDictLabel(checkCoupleDoubtful.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkCoupleDoubtful.custName}">${checkCoupleDoubtful.custName}</td>
								<td id="checkType" class="title" title="${checkCoupleDoubtful.checkType}">${fns:getDictLabel(checkCoupleDoubtful.checkType, 'CHECK_TYPE', '')}</td>
								<td id="checkAddress" class="title" title="${checkCoupleDoubtful.checkAddress}">${checkCoupleDoubtful.checkAddress}</td>
								<td id="checkDate" class="title" title="<fmt:formatDate value='${checkCoupleDoubtful.checkDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${checkCoupleDoubtful.checkDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkUserName" class="title" title="${checkCoupleDoubtful.checkUserName}">${checkCoupleDoubtful.checkUserName}</td>
								<td id="description" class="title" title="${checkCoupleDoubtful.description}">${checkCoupleDoubtful.description}</td>
								<shiro:hasPermission name="credit:checkCoupleDoubtful:edit">
									<td>
										<a href="javascript:void(0);" onclick="add('${ctx}/credit/checkCoupleDoubtful/form?readOnly=true&id=${checkCoupleDoubtful.id}','查看两人外访信息','${checkCoupleDoubtful.custName}','${checkCoupleDoubtful.roleType}','${checkCoupleDoubtful.applyNo}','${checkCoupleDoubtful.custId}');">详情</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	    <c:if test="${actTaskParam.taskDefKey ne 'utask_fgsfksh'}"> 
		<div id="isHideSuggestionDiv" class="infoListCon">
			<form id="conclusionForm" action="${ctx}/credit/checkCoupleDoubtful/conclusion" method="post">
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<div class="filter" id="isHideSuggestionDiv">
					<h3 class="infoListTitle">外访结论</h3>
					<table class="fromTable">
						<tr>
							<td class="ft_label">审批结论：</td>
							<td class="" colspan="3">
								<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" onclick="validate('non_display')">
								<label for="radio_yes">通过</label>
								&nbsp;&nbsp;
								<input type="radio" name="passFlag" value="no" id="radio_no" class="required" onclick="validate('non_display')">
								<label for="radio_no">拒绝</label>
								&nbsp;&nbsp;
								<input type="radio" name="passFlag" value="black" id="radio_black" class="required" onclick="validate('dispaly')">
								<label for="radio_black">拒绝并加入黑名单</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
							</td>
						</tr>
						<tr id="blackTr">
							<td class="ft_label">加黑说明：</td>
							<td class="" colspan="5">
								<pre class="input-medium  pre-style required" style="width:900px" id="preSuggestion"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="sugession" name="sugession" class="input-medium textarea-style required" style="width:900px" onkeyup="adjustTextareaLength('sugession','preSuggestion')">${sugession}</textarea>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">外访综合意见：</td>
							<td class="" colspan="5">
								<pre class="textarea-style pre-style" style="width:900px" id="preSuggestionDesc"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="textarea-style required" style="width:900px" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')">${suggestionDesc}</textarea>
								<font color="red"></font>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
						&nbsp;
						<a id="checkCoupleDoubtfulSkipId" target="_parent"></a>
					</div>
				</div>
			</form>
		</div>
		</c:if>
		 <c:if test="${actTaskParam.taskDefKey eq 'utask_fgsfksh'}"> 
		<div id="isHideSuggestionDiv" class="infoListCon">
			<form id="conclusionForm" action="${ctx}/credit/checkCoupleDoubtful/conclusion" method="post">
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<div class="filter" id="isHideSuggestionDiv">
					<h3 class="infoListTitle">外访结论</h3>
					<table class="fromTable">
						<tr>
							<td class="ft_label">外访综合意见：</td>
							<td class="" colspan="5">
								<pre class="textarea-style pre-style" style="width:900px" id="preSuggestionDesc"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="textarea-style required" style="width:900px" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')">${suggestionDesc}</textarea>
								<font color="red"></font>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="保 存" />
					</div>
				</div>
			</form>
		</div>
		</c:if>
	</div>
	<c:if test="radioSelect!=null">
		<script>
			alert(${radioSelect});
			$(document).ready(function() {
				$("#radio_no").attr("checked", "checked");
			});
		</script>
	</c:if>
	<c:if test="${not empty close }">
		<script type="text/javascript">
			alertx('${submitMessg}', function() {
				goToPage('${ctx}${actTaskParam.headUrl}', 'checkCoupleDoubtfulSkipId');
			});
		</script>
	</c:if>
	<c:if test="${not empty closeNewJob }">
		<script type="text/javascript">
			alertx('${submitMessg}');
		</script>
	</c:if>
</body>
</html>
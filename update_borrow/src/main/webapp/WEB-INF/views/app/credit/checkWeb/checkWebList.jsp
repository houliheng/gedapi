<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>网查管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//页面初始化后，设置“加黑说明”不可编辑。
		$("#blacklistDiv").hide();
		$("#resultForm").validate({
		submitHandler : function(form) {
		//查询实时进件状态，若不是审批中状态，提示不可提交
			var flag = $("input[name='passFlag']:checked").val();
		if (flag == "black") {
				confirmx("确认加入黑名单吗？", function() {
					$.post("${ctx}/credit/applyRegister/isSubmitting", {
					applyNo : "${applyNo}"
				}, function(data) {
					if (data == "true") {
						var flag = $("input[name='passFlag']:checked").val();
						if (flag == "yes") {
							if ('${visitCountFlag}' == "true") {
								loading('正在提交，请稍等...');
								form.submit();
							} else {
								alertx("网查未完成，不能提交！");
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

			});
		} else {
				$.post("${ctx}/credit/applyRegister/isSubmitting", {
					applyNo : "${applyNo}"
				}, function(data) {
					if (data == "true") {
						var flag = $("input[name='passFlag']:checked").val();
						if (flag == "yes") {
							if ('${visitCountFlag}' == "true") {
								loading('正在提交，请稍等...');
								form.submit();
							} else {
								alertx("网查未完成，不能提交！");
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

		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		if('${statusFlag}' == "1"){
			var province = document.getElementById("radio_yes");
			if(province != null){
				province.checked = "checked";
				}
		}else if('${statusFlag}' == "0"){
			var province = document.getElementById("radio_no");
			if(province != null){
				province.checked = "checked";
				}
		}
		
		adjustTextareaLength("sugession", "pre");
		adjustTextareaLength("suggestionDesc", "pre1");
	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blacklistDiv").hide();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
		}
	}

	//新增
	function add(urlSingle, title, custName, roleType, applyNo, custId) {
		var width = $(document).width() - 100;
		var height = $(document).height() - 100;
		custName = encodeX(custName);
		roleType = encodeX(roleType);
		var url = urlSingle + "&roleType=" + roleType + "&custName=" + custName + "&applyNo=" + applyNo + "&custId=" + custId;
		openJBox('', url, title, width, height);
	}

</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">待核查列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>核查次数</th>
							<c:if test="${readOnly ne 'true' }">
								<shiro:hasPermission name="credit:checkWeb:edit">
									<th>操作</th>
								</shiro:hasPermission>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${applyRelationList}" var="applyRelation" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="roleType" class="title" title="${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}">${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${applyRelation.custName}">${applyRelation.custName}</td>
								<c:if test="${applyRelation.roleType =='5' ||  applyRelation.roleType =='6'}">
									<td id="corporationMobile" class="title" title="${applyRelation.companyInfo.corporationMobile}">${applyRelation.companyInfo.corporationMobile}</td>
								</c:if>
								<c:if test="${applyRelation.roleType !='5' &&  applyRelation.roleType !='6'}">
									<td id="mobileNum" class="title" title="${applyRelation.custInfo.mobileNum}">${applyRelation.custInfo.mobileNum}</td>
								</c:if>
								<td id="visitCount" class="title" title="${applyRelation.visitCount}">${applyRelation.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<shiro:hasPermission name="credit:checkWeb:edit">
										<td>
											<a id="add" href="javascript:void(0);" onclick="add('${ctx}/credit/checkWeb/form?','新增网查信息','${applyRelation.custName}','${applyRelation.roleType}','${applyRelation.applyNo}','${applyRelation.custId}');">核查</a>
										</td>
									</shiro:hasPermission>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">网查信息列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>核查时间</th>
							<th>核查人</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<shiro:hasPermission name="credit:checkWeb:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkWebList}" var="checkWeb" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="checkDate" class="title" title="${checkWeb.checkDate}">
									<fmt:formatDate value="${checkWeb.checkDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkUserName" class="title" title="${checkWeb.checkUserName}">${checkWeb.checkUserName}</td>
								<td id="roleType" class="title" title="${checkWeb.roleType}">${fns:getDictLabel(checkWeb.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkWeb.custName}">${checkWeb.custName}</td>
								<td id="mobileNum" class="title" title="${checkWeb.mobileNum}">${checkWeb.mobileNum}</td>
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/credit/checkWeb/form?readOnly=true&id=${checkWeb.id}','查看网查信息','${checkWeb.custName}','${checkWeb.roleType}','${checkWeb.applyNo}','${checkWeb.custId}');">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<pre class="textarea-width pre-style required" id="pre"></pre>
		<pre class="textarea-width pre-style required" id="pre1"></pre>
		<c:if test="${actTaskParam.taskDefKey ne 'utask_fgsfksh'}"> 
		<div class="infoListCon">
			<form id="resultForm" action="${ctx}/credit/checkWeb/conclusion" method="post">
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<div class="filter" id="isHideSuggestionDiv">
					<h3 class="infoListTitle">审批结论</h3>
					<table class="fromTable">
						<tr>
							<td class="ft_label">审批结论：</td>
							<td class="" colspan="5">
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
						<tr id="blacklistDiv">
							<td class="ft_label">加黑说明：</td>
							<td class="" colspan="5">
								<textarea rows="5" cols="100" maxlength="1000" id="sugession" name="sugession" class="textarea-width textarea-style required " onkeyup="adjustTextareaLength('sugession','pre')"></textarea>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">是否有异常：</td>
							<td class="">
								<input type="radio" name="isAbnormal" value="1" id="radio_yes" class="required">
								<label for="radio_yes">是</label>
								&nbsp;&nbsp;
								<input type="radio" name="isAbnormal" value="0" id="radio_no" class="required">
								<label for="radio_no">否</label>
								<font color="red">*</font>
								<a id="checkWebSkipId" target="_parent"></a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">网查意见：</td>
							<td class="" colspan="5">
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" onkeyup="adjustTextareaLength('suggestionDesc','pre1')" name="suggestionDesc" class="textarea-width textarea-style required"></textarea>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
					</div>
				</div>
			</form>
		</div>
		</c:if>
		<c:if test="${actTaskParam.taskDefKey eq 'utask_fgsfksh'}"> 
		<div class="infoListCon">
			<form id="resultForm" action="${ctx}/credit/checkWeb/conclusion" method="post">
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<div class="filter" id="isHideSuggestionDiv">
					<h3 class="infoListTitle">审批结论</h3>
					<table class="fromTable">
					<tr>
							<td class="ft_label">是否有异常：</td>
							<td class="">
								<input type="radio" name="isAbnormal" value="1" id="radio_yes" class="required">
								<label for="radio_yes">是</label>
								&nbsp;&nbsp;
								<input type="radio" name="isAbnormal" value="0" id="radio_no" class="required">
								<label for="radio_no">否</label>
								<font color="red">*</font>
								<a id="checkWebSkipId" target="_parent"></a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">网查意见：</td>
							<td class="" colspan="5">
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" onkeyup="adjustTextareaLength('suggestionDesc','pre1')" name="suggestionDesc" class="textarea-width textarea-style required">${suggestionDesc}</textarea>
								<font color="red">*</font>
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
	<c:if test="${not empty close}">
		<script type="text/javascript">
			goToPage('${ctx}${actTaskParam.headUrl}', 'checkWebSkipId');
		</script>
	</c:if>
	<c:if test="${not empty closeNewJob }">
		<script type="text/javascript">
			alertx('${submitMessg}');
		</script>
	</c:if>
</body>
</html>
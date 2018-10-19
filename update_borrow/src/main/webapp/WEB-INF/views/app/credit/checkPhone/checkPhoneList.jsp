<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话核查管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//页面初始化后，设置“加黑说明”不可编辑。
		if('${readOnly}' == "true"){
			$("#resultForm").hide();
		}
		$("#blacklistDiv").hide();
		$("#resultForm").validate({
		submitHandler : function(form) {
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
		
		if('${statusFlag}' == "1"){
			var province = document.getElementById("radio_yess");
			if(province != null){
			province.checked = "checked";
			}
		}else if('${statusFlag}' == "0"){
			var province = document.getElementById("radio_noo");
			if(province != null){
				province.checked = "checked";
				}
		}
		if('${statusFlag1}' == "1"){
			var province = document.getElementById("radio_yes");
			if(province != null){
				province.checked = "checked";
				}
		}else if('${statusFlag1}' == "0"){
			var province = document.getElementById("radio_no");
			if(province != null){
				province.checked = "checked";
				}
		}
		adjustTextareaLength("sugession", "preSugession");
		adjustTextareaLength("suggestionDesc", "preSuggestionDesc");
	});
	function saveForm(form) {
		//查询实时进件状态，若不是审批中状态，提示不可提交
		$.post("${ctx}/credit/applyRegister/isSubmitting", {
			applyNo : '${applyNo}'
		}, function(data) {
			if (data == "true") {
				var flag = $("input[name='passFlag']:checked").val();
				if (flag == "yes") {
					if ('${visitCountFlag}' == "true") {
						loading('正在提交，请稍等...');
						form.submit();
					} else {
						alertx("电话核查未完成，不能提交！");
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
			$("#blacklistDiv").hide();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
		}
	}

	//查询
	function queryContact(urlSingle, title, custId, custName, roleType, applyNo, mobileNum) {
		custName = encodeX(custName);
		roleType = encodeX(roleType);
		var width = $(document).width() - 100
		var height = $(document).height() - 100;
		var url = urlSingle + "&roleType=" + roleType + "&custId=" + custId + "&custName=" + custName + "&applyNo=" + applyNo + "&mobileNum=" + mobileNum;
		openJBox('', url, title, width, height);
	}

	//新增
	function addContact(urlSingle, title, custId, custName, applyNo, mobileNum) {
		custName = encodeX(custName);
		var width = $(document).width() - 100;
		var height = $(document).height() - 100;
		var url = urlSingle + "&custId=" + custId + "&custName=" + custName + "&applyNo=" + applyNo + "&mobileNum=" + mobileNum;
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
								<th>操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${applyRelationList}" var="applyRelation" varStatus="i">
							<tr>
								<td id="num" class="title" title="${i.index+1}">${i.index+1}</td>
								<td id="roleType" class="title" title="${applyRelation.roleType}">${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}
								<td id="custName" class="title" title="${applyRelation.custName}">${applyRelation.custName}</td>
								<td id="mobileNum" class="title" title="${applyRelation.custInfo.mobileNum}">${applyRelation.custInfo.mobileNum}</td>
								<td id="visitCount" class="title" title="${applyRelation.visitCount}">${applyRelation.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<td>
										<a id="add" href="javascript:void(0);" onclick="queryContact('${ctx}/credit/checkPhone/form?','新增电话核查信息','${applyRelation.custId}','${applyRelation.custName}','${applyRelation.roleType}','${applyRelation.applyNo}','${applyRelation.custInfo.mobileNum}');">核查</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
						<c:forEach items="${contactInfoList}" var="contactInfo" varStatus="i">
							<tr>
								<td id="num" class="title" title="${(fn:length(applyRelationList))+i.index+1}">${(fn:length(applyRelationList))+i.index+1}</td>
								<td id="roleType" class="title" title="联系人">联系人</td>
								<td id="custName" class="title" title="${contactInfo.contactName}">${contactInfo.contactName}</td>
								<td id="mobileNum" class="title" title="${contactInfo.contactMobile}">${contactInfo.contactMobile}</td>
								<td id="visitCount" class="title" title="${contactInfo.visitCount}">${contactInfo.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<td>
										<a id="add" href="javascript:void(0);" onclick="addContact('${ctx}/credit/checkPhone/formContact?','新增电话核查信息','${contactInfo.id}','${contactInfo.contactName}','${applyNo}','${contactInfo.contactMobile}');">核查</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">电话核查信息列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>拨打时间</th>
							<th>拨打人</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>异常风险点</th>
							<th>电核详情</th>
							<shiro:hasPermission name="credit:checkPhone:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkPhoneList}" var="checkPhone" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="dialTime" class="title" title="${checkPhone.dialTime}">
									<fmt:formatDate value="${checkPhone.dialTime}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkUserName" class="title" title="${checkPhone.checkUserName}">${checkPhone.checkUserName}</td>
								<td id="roleType" class="title" title="${checkPhone.roleType}">${fns:getDictLabel(checkPhone.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkPhone.custName}">${checkPhone.custName}</td>
								<td id="mobileNum" class="title" title="${checkPhone.mobileNum}">${checkPhone.mobileNum}</td>
								<td id="riskPoint" class="title" title="${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}">${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}</td>
								<td id="description" class="title" title="${checkPhone.description}">${checkPhone.description}</td>
								<shiro:hasPermission name="credit:checkPhone:edit">
									<td>
										<a href="javascript:void(0);" onclick="queryContact('${ctx}/credit/checkPhone/form?readOnly=true&id=${checkPhone.id}','查看电话核查信息','${checkPhone.custId}','${checkPhone.custName}','${checkPhone.roleType}','${checkPhone.applyNo}','${checkPhone.mobileNum}');">详情</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${actTaskParam.taskDefKey ne 'utask_fgsfksh'}"> 
		<div class="infoListCon">
			<form id="resultForm" action="${ctx}/credit/checkPhone/conclusion" method="post">
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<div class="filter" id="isHideSuggestionDiv">
					<h3 class="infoListTitle">审批结论</h3>
					<table class="fromTable">
						<tr>
							<td class="ft_label">审批结论：</td>
							<td class="" colspan="1">
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
								<pre class="textareaWidth pre-style required" id="preSugession"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="sugession" name="sugession" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('sugession','preSugession')"></textarea>
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
							</td>
						</tr>
						<tr>
							<td class="ft_label">企业电话是否核查：</td>
							<td class="">
								<input type="radio" name="isAbnormal2" value="1" id="radio_yes" class="required">
								<label for="radio_yes">是</label>
								&nbsp;&nbsp;
								<input type="radio" name="isAbnormal2" value="0" id="radio_no" class="required">
								<label for="radio_no">否</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
								<a id="checkPhoneSkipId" target="_parent"></a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">电核意见：</td>
							<td class="" colspan="5">
								<pre class="textareaWidth pre-style" id="preSuggestionDesc"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')"></textarea>
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
			<form id="resultForm" action="${ctx}/credit/checkPhone/conclusion" method="post">
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
							</td>
						</tr>
						<tr>
							<td class="ft_label">企业电话是否核查：</td>
							<td class="">
								<input type="radio" name="isAbnormal2" value="1" id="radio_yess" class="required">
								<label for="radio_yes">是</label>
								&nbsp;&nbsp;
								<input type="radio" name="isAbnormal2" value="0" id="radio_noo" class="required">
								<label for="radio_no">否</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
								<a id="checkPhoneSkipId" target="_parent"></a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">电核意见：</td>
							<td class="" colspan="5">
								<pre class="textareaWidth pre-style" id="preSuggestionDesc"></pre>
								<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')">${suggestionDesc }</textarea>
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
			goToPage('${ctx}${actTaskParam.headUrl}', 'checkPhoneSkipId');
		</script>
	</c:if>
	<c:if test="${not empty closeNewJob }">
		<script type="text/javascript">
			alertx('${submitMessg}');
		</script>
	</c:if>
</body>
</html>
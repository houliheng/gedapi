<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>综合查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		resetTip();
		$("#searchForm").validate({
			submitHandler : function(form) {
				loading();
				form.submit();
			}
		});
	});

	//重置
	function resetForm() {
		$("#custName").val('');
		$("#applyStatus").select2("val", "");
		$("#idType").select2("val", "");
		$("#idNum").val('');
		$("#registerName").val('');
		//重置归属结构
		$("#companyId").val('');
		$("#companyName").val('');
		$("#applyNo").val('');
	}

	//详细
	function detail() {
		var $checkLine = $("input[name='applyRegisterVOIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条综合查询信息");
		} else {
			var url1 = "${ctx}/credit/comprehensiveQuery/form?applyRegisterVOId=" + $checkLine.val();
			$.post(url1,null,function(data1){
				if(data1){
					if(data1.flag=='success'){
						var url2 = "${ctx}/credit/comprehensiveQuery/doBusiness/" + data1.procDefId + "/" + data1.execId + "/" + data1.taskDefKey + "?taskId=" + data1.taskId;
						$.ajax({
							type:"post",
							url:url2,
							dataType:"json",
							success:function(data2){
							    if(data2.flag == "fail"){
							        alertx("您选择的数据不属于查看范围，请确认！");
							        page();//查询
							    }else{
								    //url
									var location =  data2.location;
									//flag表示未办理0已办理1
								    var flag = "1"; 
									var title = "综合查询详细信息";
									var newUrl = "${ctx}" + location + "?procDefId=" + data1.procDefId + "&taskDefKey=" + data1.taskDefKey + "&applyNo=" + data1.applyNo + "&execId=" + data1.execId + "&status=" + flag + "&title=" + title + "&taskId=" + data1.taskId+"&procInstId="+data1.procInstId+"&headUrl=/credit/comprehensiveQuery/list";
									var windowWidth = window.document.body.offsetWidth - 50;
									var windowHeight = window.document.body.offsetHeight - 50;
									goToPage(newUrl,'queryListSkipId');
							    }
							},
							error:function(msg){
								alertx("未能查看，请查看后台信息");
							}
						});
					}else if(data1.flag=='notask'){
		 		$.post("${ctx}/credit/comprehensiveQuery/showDown?applyRegisterVOId=" + $checkLine.val(), function(data) {
						if (data) {
								var procDefId = data.map.PROCESSDEFINITIONID;
								var execId = data.map.EXECUTIONID;
								var taskDefKey = data.map.TASKDEFINITIONKEY;
								var id = data.map.applyNo;
								var title = data.map.custName;
								var taskId = data.map.id;
								var procInstId = data.map.procInsId;
								showInfoDown(procDefId, execId, taskDefKey, id, title, taskId, procInstId);
						} else {
							alertx("未能查看已办流程信息，请查看后台信息");
						}
					}
				); 
					}else{
						alertx("获取流程参数失败，请联系管理员！");
					}
				}else{
					alertx("获取流程参数失败，请联系管理员！");
				}
			});
		}
	}
	function showInfoDown(procDefId, execId, taskDefKey, id, title, taskId, procInstId) {
		var url = "${ctx}/credit/taskCenter/loadTaskAddr/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		success : function(data) {
			if (data.flag == "fail") {
				alertx("任务查询失败！无法获取任务地址信息！");
			} else {
				//url
				var location = data.location;
				var canRedo = data.canRedo;
				//flag表示未办理0已办理1
				var flag = "1";
				var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId + "&procInstId=" + procInstId + "&canRedo=" + canRedo + "&headUrl=${headUrl}";
				//openJBox("box_" + execId, newUrl, title, width, height);
				var skip = document.getElementById("skipId");
				skip.href = newUrl;
				skip.click();
			}
		},
		error : function(msg) {
			alertx("任务查询失败！无法获取任务地址信息！");
		}
		});
	}

	//流程轨迹
	function processTrack() {
		var str = $("input[name='applyRegisterVOIds']:checked");
		var $len = str.length;
		if ($len != 1) {
			alertx("请选择一条数据！");
		} else {
			if (str.val() == null || "" == str.val()) {
				alertx("暂无流程信息！");
			} else {
				var url = "${ctx}/credit/comprehensiveQuery/processTrack?applyRegisterVOId=" + str.val();
				var windowWidth = window.document.body.offsetWidth - 50;
				var windowHeight = window.document.body.offsetHeight - 50;
				openJBox("processTrackBox", url, "流程轨迹", windowWidth, windowHeight);
				// window.showModalDialog(url, null, "dialogWidth:1000px;dialogHeight:600px;status:no;help:no;resizable:yes;");
			}
		}

	}

	//流程图
	function tracePhoto() {
		var str = $("input[name='procInsIds']:checked");
		var $len = str.length;
		if ($len != 1) {
			alertx("请选择一条数据！");
		} else {
			if (str.val() == null || "" == str.val()) {
				alertx("暂无流程信息！");
			} else {
				var url = "${ctx}/act/model/tracePhoto?procInsId=" + str.val();
				var windowWidth = window.document.body.offsetWidth - 50;
				var windowHeight = window.document.body.offsetHeight - 50;
				openJBox("tracePhotoBox", url, "流程图", windowWidth, windowHeight);
				//window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			}

		}

	}
	function checkMyProcInsId(id) {
		var str = $("input[name='procInsIds']:checked");
		var $len = str.length;
		if ($len != 0) {
			str.each(function() {
				$(this).attr("checked", false);
			});
		}
		$("#procInsIds" + id).attr("checked", $("#applyRegisterVOIds" + id).attr("checked"));
	}
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if($("#pageNo")[0].value.length>10){
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if($("#pageSize")[0].value.length>10){
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true;
		}
		loading();
		$("#searchForm").submit();
	    return false;
	}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon filter">
				<form:form id="searchForm" modelAttribute="applyRegisterVO" action="${ctx}/credit/comprehensiveQuery/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<a id="queryListSkipId" href="javascript:void(0)"></a>
					<div class="filter">
						<table class="searchTable" width="100%">
							<tr>
								<td align="right">
									<label>客户名称：</label>
								</td>
								<td>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td align="right">
									<label>证件类型：</label>
								</td>
								<td>
									<form:select id="idType" name="idType" path="idType" class="input-medium" value="${applyRegister.idType}" cssStyle="width:164px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td align="right">
									<label>证件号：</label>
								</td>
								<td>
									<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<label>登记人：</label>
								</td>
								<td>
									<form:input path="registerName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td align="right">
									<label>状态：</label>
								</td>
								<td>
									<form:select path="applyStatus" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td align="right">
									<label>登记门店：</label>
								</td>
								<td>
									<sys:usertreeselect id="company" name="orgId" value="${applyRegisterVO.orgId}" labelName="company.name" labelValue="${applyRegisterVO.company.name}" title="机构" url="/sys/office/treeData?type=2&companyId=${companyId}"
										cssClass="input-small" notAllowSelectParent="false" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<label>申请编号：</label>
								</td>
								<td>
									<form:input path="applyNo" htmlEscape="false" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<li class="edit">
						<a id="detail" href="javascript:void(0);" onclick="detail();">
							<span>
								<b></b>
								详情
							</span>
						</a>
						<a id="skipId" ></a>
						<a id="todoBusinessListSkipId"></a>
					</li>
					<li class="mcp_pic">
						<a id="" href="javascript:void(0);" onclick="tracePhoto();">
							<span>
								<b></b>
								流程图
							</span>
						</a>
					</li>
					<li class="mcp_change">
						<a id="" href="javascript:void(0);" onclick="processTrack();">
							<span>
								<b></b>
								流程轨迹
							</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table width="100%" id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="5%"></th>
							<th width="5%">序号</th>
							<th width="10%">客户名称</th>
							<th width="10%">申请编号</th>
							<th width="10%">证件类型</th>
							<th width="20%">证件号</th>
							<th width="10%">申请金额(元)</th>
							<th width="10%">合同金额(元)</th>
							<th width="10%">登记日期</th>
							<th width="10%">登记门店</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="applyRegisterVO" varStatus="comprehensiveQueryList">
							<tr>
								<td>
									<input type="checkbox" id="applyRegisterVOIds${applyRegisterVO.id }" name="applyRegisterVOIds" value="${applyRegisterVO.id }" onclick="selectSingle('applyRegisterVOIds');"
										onchange="checkMyProcInsId('${applyRegisterVO.id }');" />
									<input type="checkbox" id="procInsIds${applyRegisterVO.id }" hidden="true" style="display: none;" name="procInsIds" value="${applyRegisterVO.procInsId }" onchange="selectSingle('procInsIds');">
								</td>
								<td>${comprehensiveQueryList.count }</td>
								<td id="custName" class="title" title="${applyRegisterVO.custName}">${applyRegisterVO.custName}</td>
								<td id="applyNo" class="title" title="${applyRegisterVO.applyNo}">${applyRegisterVO.applyNo}</td>
								<td id="idType" class="title" title="${fns:getDictLabel(applyRegisterVO.idType, 'CUSTOMER_P_ID_TYPE', '')}">${fns:getDictLabel(applyRegisterVO.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
								<td id="idNum" class="title" title="${applyRegisterVO.idNum}">${applyRegisterVO.idNum}</td>
								<td id="applyAmount" class="title" title="${applyRegisterVO.applyAmount}">${applyRegisterVO.applyAmount}</td>
								<td id="contractAmount" class="title" title="${applyRegisterVO.applyAmount}">${applyRegisterVO.contractAmount}</td>
								<td id="registerDate" class="title" title="${applyRegisterVO.registerDate}">${applyRegisterVO.registerDate}</td>
								<td id="orgId" class="title" title="${applyRegisterVO.company.name}">${applyRegisterVO.company.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
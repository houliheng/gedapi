<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贷后逾期处理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//重置
		function resetForm() {
			$("#contractNo").val('');
			$("#custName").val('');
		}
		//单选框
		function selectSingle(){
			$("[name=pcheck]:checkbox").each(function(){
				$(this).click(function(){
					if($(this).attr("checked")){
						$("[name=pcheck]:checkbox").removeAttr("checked");
						$(this).attr("checked","checked");
					}
				});
			});
		}
		//获取复选框的val
		function getCheckBoxVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str += $(this).val() + ",";
				}
			});
			return str;
		}
		var width = $(window).width()-100;
		var height = $(window).height()-50;
		//流程图
		function tracePhoto(){
			var str = getCheckBoxVal();
			if(str!=""){	
				if(str.indexOf(",")!=str.length-1){
					alertx("请不要选择多条对应的流程任务");
				}else{
			    	str = str.substring(0,str.length-1);
			    	var procDefId = getProcHiddenVal();
			    	var execId = getExecHiddenVal();
			    	var url = "${ctx}/postloan/taskCenter/trace/photo/" + procDefId + "/" + execId;
					openJBox("box_"+execId, url, "流程图", width, height);
				}
		    }else{
				alertx("请选择对应的流程任务");
			}
		}
		//流程轨迹
		function processTrack(){
			var str = getCheckBoxVal();
			var taskDefKey = getTaskHiddenVal();
			if(str!=""){	
				if(str.indexOf(",")!=str.length-1){
					alertx("请不要选择多条对应的流程任务");
				}else{
				    str = str.substring(0,str.length-1);
				    var url = "${ctx}/postloan/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
					openJBox("box_"+taskDefKey, url, "流程轨迹", width, height);
				}
			}else{
				alertx("请选择对应的流程任务");
			}
		}
		//获取PROCDEF
		function getProcHiddenVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str = $(this).next("input[name=pHiddenPro]:hidden").val();
				}
			});
			return str;
		}
		//获取EXEC
		function getExecHiddenVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").val();
				}
			});
			return str;
		}
		//获取taskDefKey
		function getTaskHiddenVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
				}
			});
			return str;
		}
		
		function dqxf(taskId,procInstId,contractNo,periodNum){
			contractNo = encodeX(contractNo);
			$.ajax({
				type : "post",
				url : "${ctx}/postloan/overdueHandle/dqxf?contractNo=" + contractNo + "&periodNum=" + periodNum + "&taskId= " +　taskId + "&procInstId=" + procInstId,
				dataType : "json",
				success : function(data) {
					alertx(data.message);
					location.reload();
				},
				error : function(msg) {
					alertx("操作失败，请查看后台信息");
				}
			});
		}
		
		//详情
		function details(applyNo) {
			var height = $(window).height() - 100;
			var width = $(window.document).width() - 100;
			var url = "${ctx}/postloan/checkTwentyFive/toDetailsPage?" +"applyNo=" + applyNo;
			openJBox('details', url, "详情", width, height);
		}
		
		//结论
		/* function handle(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
	 		var url = "${ctx}/postloan/taskCenter/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
			$.ajax({
				type:"post",
				url:url,
				dataType:"json",
				success:function(data){
				    if(data.flag == "fail"){
				        alertx("任务下发出现问题，无法办理");
				        page();//查询
				    }else{
						var location =  data.location;
						//flag表示未办理0已办理1
					    var flag = "0"; 
						var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId+"&procInstId="+procInstId+"&headUrl=${headUrl}";
						goToPage(newUrl,'todoBusinessListSkipId');
				    }
				},
				error:function(msg){
					alertx("未能办理，请查看后台信息");
				}
			});
		} */
		//结论
		function handle(procDefId, execId, taskDefKey, id, title,taskId,procInstId,contractNo,periodNum){
			contractNo = encodeX(contractNo);
	 		var url = "${ctx}/postloan/overdueHandle/doBusiness?procDefId=" + procDefId + "&execId=" + execId + "&taskDefKey=" + taskDefKey + "&taskId=" + taskId + "&procInstId=" + procInstId + "&contractNo=" + contractNo + "&periodNum=" + periodNum;
	 		var height = $(window).height() - 100;
			var width = $(window.document).width() - 100;
			//var url = "${ctx}/postloan/checkTwentyFive/toDetailsPage?" +"applyNo=" + applyNo;
			openJBox('conclusion', url, "结论", width, height);
		}
		
		function dqxfAssignList(taskId,procInstId,contractNo,periodNum){
			contractNo = encodeX(contractNo);
			var urlsuffix = "?contractNo= " + contractNo + "&periodNum= " +　periodNum + "&taskId= " +　taskId + "&procInstId=" + procInstId;
			var url = "${ctx}/postloan/overdueHandle/zbxfAssignList" + urlsuffix;
			var width = Math.max($(document).width() - 100, 800);
			var height = Math.min($(document).height() - 100, 400);
			openJBox("assignBox", url, "任务下发", width, height);
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="overdueHandle" action="${ctx}/postloan/overdueHandle/doneList/${taskDefKey}" method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium"/>
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium"/>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_pic">
					<a href="javascript:void(0)" id="processPicture" onclick="tracePhoto()"><span>流程图</span></a>
				</li>
				<li class="mcp_change">
					<a href="javascript:void(0)" id="processTrack" onclick="processTrack()"><span>流程轨迹</span></a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>客户名称</th>
							<th>合同编号</th>
							<th>合同金额</th>
							<th>期数</th>
							<th>逾期金额</th>
							<th>逾期天数</th>
							<th>大区</th>
							<th>区域</th>
							<th>分公司</th>
							<th>登记人</th>
							<th>完成时间</th>
							<!-- <th>操作</th> -->
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
							<td width="20px">
								<input type="checkbox" value="${process.PROCESSINSTANCEID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
								<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
								<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
								<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
								<input type="hidden" value="${process.ID }" name="pHiddenid">
								<input type="hidden" value="${process.APPLY_NO }" name="pHiddenApp">
							</td>
							<td id="custName" class="title" title="${process.custName}">${process.custName}</td>
							<td id="contractNo" class="title" title="${process.contractNo}">${process.contractNo}</td>
							<td id="contractAmount" class="title" title="${process.contractAmount}">${process.contractAmount}</td>
							<td id="periodNum" class="title" title="${process.periodNum}">${process.periodNum}</td>
							<td id="overdueAmount" class="title" title="${process.overdueAmount}">${process.overdueAmount}</td>
							<td id="overdueDays" class="title" title="${process.overdueDays}">${process.overdueDays}</td>
							<td id="orgLevel2" class="title" title="${process.orgLevel2}">${process.orgLevel2}</td>
							<td id="orgLevel3" class="title" title="${process.orgLevel3}">${process.orgLevel3}</td>
							<td id="orgLevel4" class="title" title="${process.orgLevel4}">${process.orgLevel4}</td>
							<td id="registerName" class="title" title="${process.registerName}">${process.registerName}</td>
							<td id="endTime" class="title" title="<fmt:formatDate value="${process.ENDTIME}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${process.ENDTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<%-- <c:if test="${process.TASKDEFINITIONKEY eq 'utask_dqxf'}">
								<td><a href="javascript:void(0)" onclick="dqxfAssignList('${process.ID}','${process.PROCESSINSTANCEID}','${process.contractNo}','${process.periodNum}');">已下发</a></td>
							</c:if>
							<c:if test="${process.TASKDEFINITIONKEY ne 'utask_dqxf'}">
								<td>
									<a href="javascript:void(0)" onclick="details('${process.applyNo}');">详情</a>&nbsp;&nbsp;
									<a href="javascript:void(0)" onclick="handle('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.APPLY_NO }','${process.NAME}','${process.ID }','${process.PROCESSINSTANCEID}','${process.contractNo}','${process.periodNum}')">结论</a>
									<a id="todoBusinessListSkipId" href="javascript:void(0)"></a>
								</td>
							</c:if> --%>
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
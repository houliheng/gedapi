<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--
* @reqno:H1601150121
* @date-designer:2016年1月15日-songmin
* @date-author:2016年1月15日-songmin:查询、展示当前用户待分配任务列表
* 	展示当前用户待分配任务列表
* @e-in-text:cusName-客户名称:客户名称（模糊）
* @e-in-text:idNum-证件号码:证件号码（模糊）
* @e-in-text:applyNo-申请编号:申请编号（模糊）
* @e-in-text:createStartTime-创建时间（开始）:创建时间（开始）
* @e-in-text:createEndTime-创建时间（结束）:创建时间（结束）
* @e-ctrl:btnSubmit-查询:查询待分配任务
* @e-ctrl:btnReset-重置:重置查询条件
* @e-ctrl:processDo-分配:打开待分配任务人员选择页面
* @e-ctrl:processPicture-流程图:查看任务流程图
* @e-ctrl:processTrack-流程轨迹:查看任务流程轨迹
* @e-out-table:待分配任务列表-待分配任务列表:任务名称、客户名称、证件类型、证件号、申请编号、申请金额、合同金额、登记门店、创建时间
-->
<html>
<head>
<title>待分配任务列表</title>
<meta name="decorator" content="default" />
	
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if($("#pageNo")[0].value.length>10){
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if($("#pageSize")[0].value.length>10){
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true
		}
		$("#searchForm").submit();
	    return false;
	}
	
	function fromReset(){
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		$("#createStartTime").val('');
		$("#createEndTime").val('');
		page();//查询
	}
	
	function timeJudge(){
		if($("#createStartTime").val()!=''&$("#createEndTime").val()!=''){
			if($("#createStartTime").val()>$("#createEndTime").val()){
				alertx("开始时间应小于结束时间！");
				return;
			}else{
				$("#searchForm").submit();
				return;
				}
			}
		$("#searchForm").submit();
	}
	//获取复选框的val
	function getCheckBoxVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str += $(this).val() + ",";
			}
		});
		return str;
	}
	//获取PROCDEF
	function getProcHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").val();
			}
		});
		return str;
	}
	//获取EXEC
	function getExecHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").val();
			}
		});
		return str;
	}
	
	//获取taskId
	function getTaskHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
			}
		});
		return str;
	}
	//获取Id
	function getIdHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").val();
			}
		});
		return str;
	}
	
	//获取applyCode
	function getApplyCodeVal(){
	 	var str = "";
		$("input[name=pcheck]:checked").each(function(){
			if($(this).attr("checked")){
		    str = $(this).parent().next().next().next().next().next().text();
			}
		});
		return str;
	}
	//流程轨迹
	function processTrack(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程任务");
			}else{
				var taskDefKey = $("#taskDefKey").val();
			    str = str.substring(0,str.length-1);
			    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
				window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:600px;status:no;help:no;resizable:yes;");
			}
		}else{
			alertx("请选择对应的流程任务");
		}
	}
	
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
		    	//var url = "${ctx}/credit/taskCenter/tracePhoto?procInstId=" + str + "&procDefId=" + proc + "&execId=" + exec; url接受
		    	var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
		    	var windowWidth = window.parent.window.document.body.offsetWidth -50;
		    	//alert(windowWidth);
				var windowHeight = window.parent.window.document.body.offsetHeight - 50;
				//alert(windowHeight); 
				window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			   //window.open(url,"流程图查看窗口","width=1700px,height=800px,location=no,status=no");
			}
	    }else{
			alertx("请选择对应的流程任务");
		}
	}
	
	//办理按钮
	function doBusiness(){
		var str = getCheckBoxVal();
		if(str != ""){	
			if(str.indexOf(",") != str.length-1){
				alertx("请不要选择多条对应的办理任务");
			}else{
				$("input[name=pcheck]:checked").each(function(){
					if($(this).attr("checked")){
						var checkboxId = $(this).attr("id");
						$("#"+checkboxId+"_alink").click();
					}
				});
			}
	    }else{
			alertx("请选择对应的流程任务");
		}
	}
	
	function doSignBusiness(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
		title = encodeX(title);
 		var url = "${ctx}/credit/taskCenter/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			
			success:function(data){
			    if(data.flag == "fail"){
			        alertx("您选择的任务已被其他人抢办，无法再办理");
			        page();//查询
			    }else{
				    //url
					var location =  data.location;
					//flag表示未办理0已办理1
				    var flag = "0"; 
					var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyId=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId+"&procInstId="+procInstId;
					var windowWidth = window.parent.window.document.body.offsetWidth - 50;
					var windowHeight = window.parent.window.document.body.offsetHeight - 50;
					window.showModalDialog(newUrl, window, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			    }
			},
			error:function(msg){
					alertx("未能办理，请查看后台信息");
			}
		});
	}
	
	//任务分配
	function doClaim(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
		var tasklist =$("input[name='pcheck']:checked");
		if(tasklist.length<1){
			alertx("没有选择记录，请选择！");
		}else{
			var windowWidth = window.parent.window.document.body.offsetWidth - 50;
			var windowHeight = window.parent.window.document.body.offsetHeight - 50;
			window.showModalDialog("${ctx}/credit/loanApply/loadOrgUser", window, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
		}
	}
	
	//选择任务分配人员后，回调该函数
	function selectReturn(user){
		var taskid=$("input[name='pcheck']:checked").attr("taskId");//这里目前只支持选中单个任务
		$.ajax({
			url:"${ctx}/credit/taskCenter/doAsign?user="+user.loginName+"&taskId=" + taskid,
			type:"POST",		
			success:function(data){ 
				if("success"==data){
					alertx("任务分配成功！");
					$("#searchForm").submit();
				}else{
					alertx("任务分配失败！");
				}
			}
		}); 
	}
	//任务详情
	function showSignBusiness(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
		title = encodeX(title);
 		var url = "${ctx}/credit/taskCenter/loadTaskAddr/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			success:function(data){
			    if(data.flag == "fail"){
			        alertx("任务查询失败！无法获取任务地址信息！");
			    }else{
				    //url
					var location =  data.location;
					var canRedo = data.canRedo;
					//flag表示未办理0已办理1
				    var flag = "1"; 
					var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyId=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId+"&procInstId="+procInstId+"&canRedo="+canRedo;
					var windowWidth = window.parent.window.document.body.offsetWidth - 50;
					var windowHeight = window.parent.window.document.body.offsetHeight - 50;
					window.showModalDialog(newUrl, window, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			    }
			},
			error:function(msg){
					alertx("任务查询失败！无法获取任务地址信息！");
			}
		});
	}
	
	$(document).ready(function() {
		var tds = $(".title");
		$.each(tds,function(){
			$(this).attr("title",$.trim($(this).text()));
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/claimlist" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content"><input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" /></td>
								<td class="ft_label">证件号码：</td>
								<td class="ft_content"><input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum }" /></td>
							</tr>
							<tr>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content"><input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" /></td>
								<td class="ft_label">创建时间：</td>
								<td class="ft_content">
								<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
									value="<fmt:formatDate value="${customer.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									<label>&nbsp;--&nbsp;</label>
								<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
									value="<fmt:formatDate value="${customer.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />&nbsp; 
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>

		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_commit"><a href="#" id="processDo" onclick="doClaim()"><span><b></b>分配</span></a></li>
				<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="1%"></th>
						<th width="10%">任务名称</th>
						<th width="10%">客户名称</th>
						<th width="10%">证件类型</th>
						<th width="13%">证件号</th>
						<th width="10%">申请编号</th>
						<th width="10%">申请金额</th>
						<th width="10%">合同金额</th>
						<th >登记门店</th>
						<th width="10%">创建时间</th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
						<input type="radio" value="${process.PROC_INS_ID}" name="pcheck"  onclick="selectSingle()" id="c_${index.count}" taskId="${process.ID }">
						<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
						<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
						<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
						<input type="hidden" value="${process.APPLY_ID }" name="pHiddenid">
						<input type="hidden" value="${process.ID }" name="pHiddenid">
						</td>
						<td class="title" title="${process.NAME}"><a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.APPLY_ID }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}')">${process.NAME}</a></td>
						<td style="word-break:break-all;word-wrap:break-word;">${process.CUST_NAME}</td>
						<td class="title">
							<c:choose>
								<c:when test="${'1'==process.CUST_TYPE}">
									${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}
								</c:when>
								<c:when test="${'2'==process.CUST_TYPE}">
									${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_C_ID_TYPE', '')}
								</c:when>
							</c:choose>
						</td>
						<td class="title">${process.ID_NUM}</td>
						<td class="title">${process.APPLY_CODE}</td>
						<td class="title">${process.APPLY_AMOUNT}</td>
						<td class="title"T}">${process.CONTRACT_AMOUNT}</td>
						<td class="title">${process.ORG_ID}</td>
						<td class="title"><fmt:formatDate value="${process.CREATETIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
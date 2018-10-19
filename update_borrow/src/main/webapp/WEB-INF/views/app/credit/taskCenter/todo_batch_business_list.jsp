<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--  
@reqno:H1511230004
@date-designer:20151125-songmin
@date-author:20151125-songmin:低分辨率下页面样式更改
-->
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<!--  
@reqno:H1512260014
@date-designer:20151226-songmin
@date-author:20151226-songmin:金额(元)、百分比格式化处理，当金额(元)、百分比小于1时，不显示个数位bug修复
-->
<html>
<head>
<title>待办任务列表</title>
<meta name="decorator" content="default" />
	<!--  
  	@reqno:H1510230032
  	@date-designer:20151027-huangxuecheng
  	@date-author:20151027-huangxuecheng:CRE_信贷审批_任务中心_待办理任务列表_查询需求概述：查看当前用户的待办理任务列表，这个待办理任务列表是根据传入的taskDefId如果包含了utask_batch便进行渲染跳转的页面，使其功能区别于普通待办任务
	@e-in-text:cusName-客户名称:客户名称
	@e-in-text:idNum-证件类型:证件类
	@e-in-text:applyNo-申请编号
 	@e-in-text:createStartTime-完成时间
 	@e-in-text:createEndTime-完成时间
	@e-ctrl:processDo-办理:办理流程
	@e-ctrl:processPicture-流程图:打开流程图
	@e-ctrl:processTrack-流程轨迹:打开流程轨迹
	@e-ctrl:processDoBatchSubmit-批量办理:执行批量办理
	@e-ctrl:processDoExport-导出excel:点击下载excel文件
	@e-out-table:tableDataId-输出待办任务:输出待办任务table
     -->
	<!--  
  	@reqno:H1510230033
  	@date-designer:20151027-huangxuecheng
  	@date-author:20151027-huangxuecheng:CRE_信贷审批_合同复核_批量提交待办任务列表_批量提交
	@e-ctrl:processDoBatchSubmit-批量办理:执行批量办理
     -->
    <!--  
  	@reqno:H1510230034
  	@date-designer:20151027-huangxuecheng
  	@date-author:20151027-huangxuecheng:CRE_信贷审批_合同复核_批量提交待办任务列表_导出放款文件格式为excel
	@e-ctrl:processDoExport-导出excel:点击下载excel文件
     -->
    <!--
	 * @reqno:H1512160006
	 * @date-designer:2016年1月5日-songmin
	 * @date-author:2016年1月5日-songmin:证件类型根据客户类型动态获取
	-->
<style type="text/css">
	.tableList .title{
	white-space:nowrap;
	width:140px;
	overflow:hidden;
	}
</style>
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
	function selectAll() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=pcheck]:checkbox').attr("checked", true);
		} else {
			$('[name=pcheck]:checkbox').attr("checked", false);
		}
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
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str += $(this).val() + ",";
			}
		});
		return str;
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
	//获取taskId
	function getTaskHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
			}
		});
		return str;
	}
	//获取Id
	function getIdHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str += $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenApplyId]:hidden").next("input[name=pHiddenid]:hidden").val() + ",";
			}
		});
		return str;
	}
	//获取applyCode
	function getApplyCodeVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
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
			    str = str.substring(0,str.length-1);
			    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + str;
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
		    	var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
		    	var windowWidth = window.parent.window.document.body.offsetWidth -50;
				var windowHeight = window.parent.window.document.body.offsetHeight - 50;
				window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");	
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
				$("input[name=pcheck]:checkbox").each(function(){
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
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:申请录入功能js，通过已经写好的js方法传入procDefId流程定义id，execId执行id， taskDefKey任务定义id，
	 *									        并通过ajax提交，返回值进行处理跳转到处理页面
	 */
	 function showSignBusiness(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
 		title = encodeX(title);
 		var url = "${ctx}/credit/taskCenter/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			success:function(data){
			    if(data.flag == "fail"){
			        alertx("未能办理，该任务已被他人签收");
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
	//页签增加
	function addTabPage002(title, url, applyId, taskId, taskDefKey, procDefId, status){
		url = url + '?applyId=' + applyId + '&taskId=' + taskId + '&taskDefKey=' + taskDefKey + '&procDefId=' + procDefId + '&status=' + status;
		addTabPage(title, url);
	}
   /**
	 * @reqno:H1510230033
	 * @date-designer:20151026-huangxuecheng
	 * @date-author:20151026-huangxuecheng:批量办理功能js，根据业务需求传入proInsId流程定义id以及taskId任务定义id然后就能进行对应数据的批量办理工作
	 */
	//批量办理
	function doBatchSubmit(){
		var str = getCheckBoxVal();
		if(str!=""){
			str = str.substring(0,str.length-1);
			var id = getIdHiddenVal();
			id = id.substring(0,str.length);
			var url = "${ctx}/credit/taskCenter/list/toBatchSubmit?procInsIds=" + str + "&taskIds=" +id;
			var returnVal = window.showModalDialog(url,null,"dialogWidth:800px;dialogHeight:400px;status:no;help:no;resizable:yes;");
			var finalUrl = "${ctx}/credit/taskCenter/list/batch/${taskDefKey}?returnVal=" + returnVal;
			$("#searchForm").attr("action",finalUrl).submit();
		}else{
			alertx("请选择对应的数据信息名称");
		}	
	}
	/**
	 * @reqno: H1510230034
	 * @date-designer:20151026-huangxuecheng
	 * @date-author:20151026-huangxuecheng:导出的js方法，本方法传入了对应的proInsIds和taskIds和taskDefKey三个重要参数能够在后台查询对应的数据，然后后台执行导出方法
	 */
	//导出
	function doExport(){
		var str = getCheckBoxVal();
		if(str!=""){
			str = str.substring(0,str.length-1);
			var id = getIdHiddenVal();
			id = id.substring(0,str.length-1);
			var taskDefKey = $("#taskDefKey").val();
			//status0是待办1是已办
			var url = "${ctx}/credit/taskCenter/list/export/${taskDefKey}?procInsIds=" + str + "&taskIds=" +id + "&taskDefKey=" +taskDefKey + "&status=0";
			top.$.jBox.tip.mess = null;
			location.href = url;
		}else{
			alertx("请选择对应的数据信息名称");
		}	
	}
	//页面数据列表高度设置
	$(document).ready(function() {
		/* var windowHeight = window.parent.window.document.body.offsetHeight;
		var x = (windowHeight - 700) + "px";
		document.getElementById("tableDataId2").style.height = x; */
		var tds = $(".title");
		$.each(tds,function(){
			$(this).attr("title",$.trim($(this).text()));
		});
		resetTip();
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/list/batch/${taskDefKey}" method="post">
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
								<td class="ft_content" style="width: 240px;">
								<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
									value="<fmt:formatDate value="${customer.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" /><label>&nbsp;--&nbsp;&nbsp;</label>
								<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
									value="<fmt:formatDate value="${customer.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
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
				<li class="mcp_commit"><a href="#" id="processDo" onclick="doBusiness()"><span><b></b>办理</span></a></li>
				<c:if test="${hidden == '0'}">
				<li class="mcp_commit"><a href="#" id="processDoBatchSubmit" onclick="doBatchSubmit()"><span><b></b>批量提交</span></a></li>
				</c:if>
				<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
				<li class="mcp_produce"><a href="#" id="processDoExport" onclick="doExport()"><span><b></b>导出放款文件</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" style="table-layout:auto;">
					<tr>
						<th width="20px"><input type="checkbox" onclick="selectAll()" name="all" id="all" /></th>
						<th ><div class="title">任务名称</div></th>
						<th ><div class="title">客户名称</div></th>
						<th ><div class="title">证件类型</div></th>
						<th ><div class="title">证件号码</div></th>
						<th ><div class="title">申请编号</div></th>
						<th ><div class="title">放款金额(元)</div></th>
						<th ><div class="title">创建时间</div></th>
						<th ><div class="title">批贷产品</div></th>
						<th ><div class="title">批贷期限</div></th>
						<th ><div class="title">年利率</div></th>
						<th ><div class="title">放款账号</div></th>
						<th ><div class="title">放款户名</div></th>
						<th ><div class="title">放款银行</div></th>
						<th ><div class="title">合同金额(元)</div></th>
						<th ><div class="title">合同编号</div></th>
						<th ><div class="title">合同签订日期</div></th>
						<th ><div class="title">申请金额(元)</div></th>
						<th ><div class="title">登记门店</div></th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
						<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck" id="c_${index.count}">
						<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
						<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
						<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
						<input type="hidden" value="${process.APPLY_ID }" name="pHiddenApplyId">
						<input type="hidden" value="${process.ID }" name="pHiddenid">
						</td>
						<!-- procDefId, execId, taskDefKey, applyCode -->
						<td ><div class="title"><a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.APPLY_ID }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}')">${process.NAME}</a></div></td>
						<td ><div class="title">${process.CUST_NAME}</div></td>
						<td >
							<div class="title">
								<c:choose>
									<c:when test="${'1'==process.CUST_TYPE}">
										${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}
									</c:when>
									<c:when test="${'2'==process.CUST_TYPE}">
										${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_C_ID_TYPE', '')}
									</c:when>
								</c:choose>
							</div>
						</td>
						<td ><div class="title">${process.ID_NUM}</div></td>
						<td ><div class="title">${process.APPLY_CODE}</div></td>
						<td ><div class="title"><fmt:formatNumber pattern="###,##0.00" value="${process.appAmount}"></fmt:formatNumber></div></td>
						<td ><div class="title"><fmt:formatDate value="${process.CREATETIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></div></td>
						<td ><div class="title">${process.appProductName}</div></td>
						<td ><div class="title">${process.appPeriodValue}</div></td>
						<td ><div class="title">${process.appYearRate}</div></td>
						<td ><div class="title">${process.recAccount}</div></td>
						<td ><div class="title">${process.recAccountName}</div></td>
						<td ><div class="title">${process.recBankValue}</div></td>
						<td ><div class="title"><fmt:formatNumber pattern="###,##0.00" value="${process.contractAmount}"></fmt:formatNumber></div></td>
						<td ><div class="title">${process.contractNo}</div></td>
						<td ><div class="title"><fmt:formatDate value="${process.occurDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></div></td>
						<td ><div class="title"><fmt:formatNumber pattern="###,##0.00" value="${process.APPLY_AMOUNT}"></fmt:formatNumber></div></td>
						<td ><div class="title">${process.ORG_ID}</div></td>
						</tr>
					</c:forEach>
				</table>
				
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>

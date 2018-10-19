<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!-- 
/**
* @reqno: H1601050069
* @date-designer:20160115-sunna
* @date-author:20160115-sunna:角色管理-增加报表选择
*/
 -->
<html>
<head>
	<title>报表选择</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	/**
	* @reqno: H1601050069
	* @date-designer:20160117-sunna
	* @date-author:20160117-sunna:角色管理-增加报表选择。页面加载完成后设置选中状态
	*/
	$(document).ready(function(){
		changeCheck();
	});
	function changeCheck(){
		var report = $(":checkbox");
		for(var i=0;i<${reportId}.length;i++){
			for(var j=0;j<report.length;j++){
				if(${reportId}[i] == report[j].value){
					$("#"+${reportId}[i]).attr("checked","checked");
				}
			}
		}
	}

	 

</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<li class="active"><a href="${ctx}/sys/role/assign?id=${role.id}"><shiro:hasPermission name="sys:role:edit">报表选择</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">人员列表</shiro:lacksPermission></a></li>
	</ul>
	<div class="container-fluid breadcrumb">
		<div class="row-fluid span12">
			<span class="span4">角色名称: <b>${role.name}</b></span>
			<span class="span4">归属机构: ${role.office.name}</span>
			<span class="span4">英文名称: ${role.enname}</span>
		</div>
		<div class="row-fluid span8">
			<span class="span4">角色类型:
				<c:choose>
					<c:when test="${role.roleType eq 'assignment'}">任务分配</c:when>
					<c:when test="${role.roleType eq 'security-role'}">管理角色</c:when>
					<c:when test="${role.roleType eq 'user'}">普通角色</c:when>
				</c:choose> 
			</span>
			<c:set var="dictvalue" value="${role.dataScope}" scope="page" />
			<span class="span4">数据范围: ${fns:getDictLabel(dictvalue, 'sys_data_scope', '')}</span>
		</div>
	</div>
	<sys:message content="${message}"/>
	<div class="breadcrumb">
		<label class="control-label">报表选择:</label>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>报表编号</th><th>报表名称</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>		
		<c:forEach items="${reportList}" var="report">
			<tr>
				<td>${report.no}</td>
				<td>${report.name}</td>
				
				<shiro:hasPermission name="sys:role:edit">
				<td>
					<input type="checkbox" id="${report.id}" value="${report.id}" onclick="rSelect(this,'${role.id}','${report.id}')"/>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript">
	 
	function rSelect(obj,roleId,reportId) {
		//1、如果值相等标识为选中状态，点击后为不选中状态，需要删除数据库中的数据
		if(obj.checked == false){
			$.ajax({
				type: "POST",
			   	url: "${ctx}/sys/role/deleteReportAndRole",
			   	data:{"reportId":reportId,"roleId": roleId},
			   	datatype: 'json',
			   	success: function(result){
			   		if(result.isOK == true) {
			   			var href = '${ctx}/sys/role/deleteReportAndRole?reportId=' + reportId+'&roleId='+roleId;
			   			window.location.href = href; 
			   		 } else {
			   			top.$.jBox.tip('出错了！赶紧查查');
			   		}
			   	}
			}); 
		}else{
			//2、如果值相等标识为选中状态，点击后为不选中状态，需要删除数据库中的数据
			$.ajax({
				type: "POST",
			   	url: "${ctx}/sys/role/updateReportAndRole",
			   	data:{"reportId":reportId,"roleId": roleId},
			   	datatype: 'json',
			   	success: function(result){
			   		if(result.isOK == true) {
			   			var href = '${ctx}/sys/role/updateReportAndRole?reportId=' + reportId+'&roleId='+roleId;
			   			window.location.href = href; 
			   		 } else {
			   			top.$.jBox.tip('出错了！赶紧查查');
			   		}
			   	}
			});
		}
		 
	} 
		
	</script>
</body>
</html>

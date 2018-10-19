<!-- 
 * @reqno: H1512260023
 * @date-designer:2016年01月13日-lirongchao
 * @e-in-other	: userLoginName-查询条件：登录名
 * @e-in-other	: Name-查询条件:姓名
 * @e-in-other	: userName-查询条件:所属机构
 * @e-ctrl : btnSubmit-查询：查询
 * @e-ctrl : btnReset-重置：重置
 * @e-ctrl : installUserNoEntry-设置：设置
 * @e-ctrl : deleteUserNoEntry-删除：删除
 * @date-author:2016年01月13日-lirongchao:人员禁件列表页面jsp
 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员禁件表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			resetTip();
			$("#searchForm").validate({
			   rules:{
					"user.loginName":{
						noLegalInput:true
					},
					"user.name":{
						noLegalInput:true
					}
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			$("#setUserNoEntryBtn").click(function(){
				top.$.jBox.open("iframe:${ctx}/credit/userNoEntry/installUserNoEntry", "人员禁件设置",810,$(top.document).height()-240,{
					buttons:{"保存":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员进行禁件设置。",submit:function(v, h, f){
						var ids = h.find("iframe")[0].contentWindow.ids;
						if (v=="ok"){
							if(ids==null){
								ids = [''];
							}
							// 删除''的元素
							if(ids[0]==''){
								ids.shift();
							}
					    	// 执行保存
					    	loading('正在提交，请稍等...');
					    	var idsArr = "";
					    	for (var i = 0; i<ids.length; i++) {
					    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
					    	}
					    	location.href = "${ctx}/credit/userNoEntry/save?idsArr="+idsArr ;
					    	return true;
						} else if (v=="clear"){
							h.find("iframe")[0].contentWindow.clearAssign();
							return false;
		                }
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});				
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//重置
		function fromReset() {
			$("#userLoginName").val('');
			$("#Name").val('');
			$("#userId").val('');
			$("#userName").val('');
			$("#pageNo").val('');
			$("#pageSize").val('');			
		}
		function deleteUserNoEntry() {
			resetTip();
			var str = getCheckedValue();
			if(str != ""){
				var url = "${ctx}/credit/userNoEntry/delete?id="+str;
				confirmx("确认要删除所选信息吗？",url);
			}
		}
	</script>
</head>
<body>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="userNoEntry" action="${ctx}/credit/userNoEntry/" method="post"  class=" form-search ">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">登录名：</td>
								<td class="ft_content"><input id="userLoginName" name="user.loginName" type="text" maxlength="50" class="input-medium" value="${userNoEntry.user.loginName}" /></td>
								<td class="ft_label">姓名：</td>
								<td class="ft_content"><input id="Name" name="user.name" type="text" maxlength="50" class="input-medium" value="${userNoEntry.user.name}" /></td>
 							</tr>
							<tr>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
								<sys:treeselect id="user" name="user.office.id" value="${userNoEntry.user.office.id}" labelName="user.office.name" labelValue="${userNoEntry.user.office.name}"
									title="机构" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>								
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="edit"><a href="#" id="setUserNoEntryBtn"><span><b></b>设置</span></a></li>
				<li class="delete"><a href="#" id="deleteUserNoEntry" onclick="deleteUserNoEntry()"><span><b></b>删除</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px"><input type="checkbox" onclick="allCheck()" name="all" id="all" /></th>
						<th width="10%">登录名</th>
						<th width="10%">姓名</th>
						<th width="10%">所属机构</th>
						<th>设置时间</th>
					</tr>
					<c:forEach items="${page.list}" var="userNoEntryInfo" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="id"><input type="checkbox" value="${userNoEntryInfo.id}" name="type" id="c_${index.count}"></td>
						<td id="user.loginName" class="title" title="${userNoEntryInfo.user.loginName}">${userNoEntryInfo.user.loginName}</td>
						<td id="user.name" class="title" title="${userNoEntryInfo.user.name}">${userNoEntryInfo.user.name}</td>
						<td id="user.office.name" class="title" title="${userNoEntryInfo.user.office.name}">${userNoEntryInfo.user.office.name}</td>
						<td id="createDate" class="title" title="<fmt:formatDate value="${userNoEntryInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${userNoEntryInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
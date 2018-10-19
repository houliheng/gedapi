<!-- 
 * @reqno: H1512260021
 * @date-designer:2016年01月13日-lirongchao
 * @e-in-other	: officeName-查询条件：机构编码
 * @e-in-other	: Name-查询条件:机构名称
 * @e-ctrl : btnSubmit-查询：查询
 * @e-ctrl : btnReset-重置：重置
 * @e-ctrl : installCompanyNoEntry-设置：设置
 * @e-ctrl : deleteCompanyNoEntry-删除：删除
 * @date-author:2016年01月13日-lirongchao:人员禁件列表页面jsp
 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构禁件表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			resetTip();
			$("#searchForm").validate({
				rules:{
					"office.code":{
						noLegalInput:true
					},
					"office.name":{
						noLegalInput:true
					}
				},
				submitHandler: function(form){
						form.submit();
				}
			});
			$("#installCompanyNoEntry").click(function(){
				top.$.jBox.open("iframe:${ctx}/credit/companyNoEntry/installCompanyNoEntry", "机构禁件设置",300,440,{
					buttons:{"保存":"ok", "关闭":true}, bottomText:"",submit:function(v, h, f){
						var ids = h.find("iframe")[0].contentWindow.getCheckedNodes();
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
					    	location.href = "${ctx}/credit/companyNoEntry/save?idsArr="+idsArr ;
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
			$("#officeName").val('');
			$("#Name").val('');
			$("#pageNo").val('');
			$("#pageSize").val('');			
		}
		function deleteCompanyNoEntry() {
			resetTip();
			var str = getCheckedValue();
			if(str != ""){
				var url = "${ctx}/credit/companyNoEntry/delete?id="+str;
				confirmx("确认要删除所选信息吗？",url);
			}
		}
	</script>
</head>
<body>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="companyNoEntry" action="${ctx}/credit/companyNoEntry/" method="post"  class=" form-search ">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">机构编码：</td>
								<td class="ft_content"><input id="officeName" name="office.code" type="text" maxlength="50" class="input-medium" value="${companyNoEntry.office.code}" /></td>
								<td class="ft_label">机构名称：</td>
								<td class="ft_content"><input id="Name" name="office.name" type="text" maxlength="50" class="input-medium" value="${companyNoEntry.office.name}" /></td>
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
				<li class="edit"><a href="#" id="installCompanyNoEntry"><span><b></b>设置</span></a></li>
				<li class="delete"><a href="#" id="deleteCompanyNoEntry" onclick="deleteCompanyNoEntry()"><span><b></b>删除</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px"><input type="checkbox" onclick="allCheck()" name="all" id="all" /></th>
						<th width="10%">机构编码</th>
						<th width="10%">机构名称</th>
						<th>设置时间</th>
					</tr>
					<c:forEach items="${page.list}" var="companyNoEntryInfo" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id = "id"><input type="checkbox" value="${companyNoEntryInfo.id}" name="type" id="c_${index.count}"></td>
						<td id = "office.code" class="title" title="${companyNoEntryInfo.office.code}">${companyNoEntryInfo.office.code}</td>
						<td id = "office.name" class="title" title="${companyNoEntryInfo.office.name}">${companyNoEntryInfo.office.name}</td>
						<td id = "createDate" class="title" title="<fmt:formatDate value="${companyNoEntryInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${companyNoEntryInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
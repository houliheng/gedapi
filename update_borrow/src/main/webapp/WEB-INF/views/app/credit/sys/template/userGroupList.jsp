<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
 	$(function() {
 	<c:forEach items="${userGroupList}" var ="userGroup">
 		$("#"+'${userGroup.allyId}').attr("checked",true);
 	</c:forEach> 
 			$("tr:gt(0)").live("click",function(){
				if($(this).find(":checkbox").attr("checked")){
					$(this).find(":checkbox").attr("checked",false);
				}else{
					$(this).find(":checkbox").attr("checked",true);
				}
			});
	}); 
 	function saveUser(userId) {
		var checkLine = $("input[name='type']:checked");
			var allyId = "";
			checkLine.each(function(v) {
				allyId += (this.value + ",");
			});
			loading();
				$.post("${ctx}/credit/userGroup/saveUser?allyId="+allyId+"&userId="+userId, function(data) {
					if(data){
						closeTip();
						if (data.status == '1') {
							alertx(data.message, function() {
							loading();
							parent.location.reload();
							});
						}else{
							alertx(data.message);
						}
					}else{
						alertx("系统出现问题，请联系管理员");
					}
					});
	}
	function setCheckBox(contentTable){
	
		$("tr").first().nextAll().click(function(){
			$(this).children().toggleClass("yellow");
			if($(this).children().css("background-color") != $(document.body).css("background-color")){
				$(this).children().first().children().attr("checked",true);
			}
			else{
				$(this).children().first().children().attr("checked",false);
			}
		});
	} 
	</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList" >
			<div id="taskDownDiv" class="ribbon">
				<a id="taskDown" href="javascript:void(0);" onclick="saveUser('${userId}');">
					<span >
						<b style="font-size: 25px">保存</b>
					</span>
				</a>
			</div>
		<div   >
			<table id="contentTable" border="0" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="95%">群组名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userAllGroupList}" var="allUserGroup" varStatus="i">
					<tr >
						<td >
							<input id ="${allUserGroup.id}" name="type" type="checkbox" value="${allUserGroup.id}" >
						</td>
						<td id="contactMobile" class="title" title="${allUserGroup.allyName}">
							${allUserGroup.allyName}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
			</div>
		</div>
	</div>
</body>
</html>


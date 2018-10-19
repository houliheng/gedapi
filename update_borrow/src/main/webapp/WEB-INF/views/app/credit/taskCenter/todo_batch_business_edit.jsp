<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <!--  
	@reqno:H1511300032
	@date-designer:20151202-huangxuecheng
	@date-author:20151202-huangxuecheng:开发原因：批量提交传入表单信息。CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来
    -->
<title>批量提交</title>
<meta name="decorator" content="default" />
<base target="_self"/>
<script type="text/javascript">
	function fromClose(){
	 window.close();
	}
	function selectAll() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=pcheck]:checkbox').attr("checked", true);
		} else {
			$('[name=pcheck]:checkbox').attr("checked", false);
		}
	}
	//dataTableNameValidate positionValidate sortValidate
	 $(document).ready(function() {
		$("#searchForm").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					//loading("正在提交，请稍等...");
					var procInsIds = $("#procInsIds").val();
					var taskIds = $("#taskIds").val();
				    var textAreaComment = $("#textAreaComment").val();
				    /*
					var regex = /^[a-zA-Z0-9_\u4E00-\u9FA5-]+$/;
					if(!textAreaComment.match(regex)){
						$("#meg").text("全文中有无效特殊字符");
						return false;
					}else{
						$("#meg").text("");
					} */
					$.ajax({
						type:"post",
						url:"${ctx}/credit/taskCenter/list/batchSubmit?procInsIds=" + procInsIds + "&taskIds=" + taskIds,
						dataType:"json",
						data:{"textAreaComment":textAreaComment},
						success:
						function(data){
							if(data.result=="success"){
						 		window.parent.returnValue = data.reval;
						 		window.close();
						 	}else{
						 		alert(data.result);
						 		resetTip();
						 	}	 
				 	},
					error:function(msg){
						alert("未能批量办理，请查看后台信息");
						resetTip();
						}
					});
				} 
			});
		}); 
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo" style="height:300px">
			<h3 class="searchTitle">批量提交</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="#" method="post"> 
					<input  type="hidden" id="procInsIds" class="btn btn-primary" value="${procInsIds }" /> 
					<input  type="hidden" id="taskIds" class="btn btn-primary" value="${taskIds }"  /> 
					<div class="filter">
						<table  class="fromTable" width="700px">
							<tr height="10px;">
								<td class="ft_label" >结论：</td>
									<td width="500px" >
										通过
									</td>
							</tr>
							<tr height="10px;">
								<td class="ft_label">意见：</td>
									<td class="ft_content">
											<form:textarea id="textAreaComment" name="textAreaComment"  path="textAreaComment" cssClass="required " style="width:350px;height:80px" maxlength="300"/><font color="red" id="meg">*</font>
									</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp; 
							<input id="btnClose" class="btn btn-primary" type="button" value="关闭" onclick="return fromClose();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>

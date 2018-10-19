<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<html>

<head>
<title>合同模板打印</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	window.name="spare";
	$(document).ready(function() {
		$("#inputForm").validate({
			rules: {
			},
			submitHandler: function(form){
				loading('正在上传，请稍等...');
				form.submit();
				window.close();
			}
		});
	});
	function print(){
		top.$.jBox.tip.mess = null;
		if($("#id").val()==null||$("#id").val()==""){
			alert("请选择模板类型！");
			return;
		}
 		$("#importForm").submit();
		location.href="${ctx}/credit/contractTemplate/printContract?id="+$("#id").val();
	}
</script>
</head>
<body>
	<sys:message content="${message}"/>
	<div class="searchInfo">
	    <h3 class="searchTitle">合同打印</h3>
	        <div class="searchCon">
				<form id="importForm" action="${ctx}/credit/contractTemplate/printFrom" method="post">
					<div class="filter">
					
						<table class="fromTable">
							<tr>
								<td class="ft_label">模板类型：</td>
									<td class="ft_content">
										<select id="id" name="id" style="width: 150px">
											<option value="">请选择</option>
											<c:forEach items="${listctl}" var="tlp">
												<option value="${tlp.id}">${tlp.templateType}</option>
											</c:forEach>
							
										</select>
								</td>　								
							</tr>
						</table>
						<input id="productType" name="productType" value="${contractTemplate.productType}" readonly="readonly" type="hidden" />					
						<div class="searchButton">
							<input id="btnImportSubmit" class="btn btn-primary" type="button" onclick="print()" value="打 印 " />&nbsp; &nbsp; &nbsp; 
							<input id="btnImportSubmitgb" class="btn btn-primary" type="button" onclick="closeJBox()" value="关 闭 " />
						</div>
					</div>
				</form>
			</div>
		</div>
</body>
</html>
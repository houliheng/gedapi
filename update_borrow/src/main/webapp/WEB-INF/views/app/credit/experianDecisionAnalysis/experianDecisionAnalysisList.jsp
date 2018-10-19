<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>益博睿决策分析</title>
	<!-- <meta name="decorator" content="default"/> -->
	
	<script type="text/javascript">
		$(document).ready(function() {
			if('${star.scoringIndex}' > 0){
				var id = "td" + ${star.scoringIndex};
				document.getElementById(id).style.border='2px #00bfff solid'; 
			}else{
				$("#scoringIndex").val('');
			}
		}); 
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="otherForm" modelAttribute="experianResponse" action="#" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<table class="filter" style="width:100%; ">
			<h3 class="infoListTitle">益博睿决策分析</h3>
			<tr>
			<td class="ft_label" style="width: 5%;">评级参考：</td>
				<td>
					<table style="width:850px;text-align:center;margin: 5px 0 0 5px">
						<tr style="color:#FFF">
							<c:forEach  var="i" begin="1" end = "${star.size }">
								<td class="experian${i}" id = "td${i }">${i }星</td>
							</c:forEach>
						</tr>
						<tr style="height:30px">
							<c:forEach items="${fns:getDictList('credit_rating')}" var="rate">
								<td>${rate }</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			<hr class="solid-line" style="height:7px"/>
			<tr>
			<td class="ft_label" style="width: 5%;">客户名称：</td>
				<td class="ft_content">
			    <input type="text" id="custName" name="custName" class="input-medium" readonly="true" value="${experianResponse.head.borrowerName}" />
			</td>
			</tr>
			<tr>
			<td class="ft_label" style="width: 5%;">信用评分：</td>
				<td class="ft_content">
			    <input type="text" id="scoringScore" name="scoringScore" class="input-medium" readonly="true" value="${experianResponse.body.scoringScore}" />
			</td>
			</tr>
			<tr>
			<td class="ft_label" style="width: 5%;">信用评级：</td>
				<td class="ft_content">
			    <input type="text" id="scoringIndex" name="scoringIndex" class="input-medium" readonly="true" value="${star.scoringIndex}星" />
			</td>
			</tr>
			<tr>
			<td class="ft_label" style="width: 5%;">评估时间：</td>
				<td class="ft_content">
			    <input type="text" id="evaluateDate" name="evaluateDate" class="input-medium" readonly="true" value="${experianResponse.head.sendDate}" />
			</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

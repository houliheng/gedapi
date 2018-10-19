<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>征信与流水分析管理</title>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	<div class="wrapper">
		<sys:message content="${message}"/>
		<div class="tableList">
			<form action="">
				<div id="">
					<h3 class="tableTitle">分析信息</h3>
					<table id="" border="0" style="width: 100%;">
						<tr>
							<td>
								流水分析：
								<textarea rows="5" cols="3" maxlength="1000" style="width: 80%;"></textarea>
								<br />
							</td>
						</tr>
						<tr>
							<td>
								征信分析：
								<textarea rows="5" cols="3" maxlength="1000" style="width: 80%;"></textarea>
								<br />
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">
								<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
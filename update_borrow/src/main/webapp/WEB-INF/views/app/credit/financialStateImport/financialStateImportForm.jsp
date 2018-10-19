<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>财报导入</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("themis", "${ctx }/credit/financialStateImport/showExcel", null, "post");
		var SVerrorMessage='${SVerrorMessage}';
		if(SVerrorMessage!=null){
			$("#SVerrorMessage").html(SVerrorMessage);
		}
	});
	function uploaddata() {
		$("#upload").click();
	}
	function getUrl() {
		$("#custName").val($("#upload").val());
	}
	function submitExcel() {
		var excelFile = $("#upload").val();
		if (excelFile == "") {
			$("#inputStatus").css("color", "red");
			$("#inputStatus").html("请选择要上传的文件!");
			return false;
		} else if (excelFile.indexOf(".xls") == -1&&excelFile.indexOf(".xlsx")==-1) {
			$("#inputStatus").css("color", "red");
			$("#inputStatus").html("请选择正确的Excel文件(后缀名为xls或xlsx)!");
			return false;
		} else {
			return true;
		}
		//$("#searchForm").submit();
	}
</script>
</head>
<body>
<style>
.textopacity{
position: absolute;
top:0;
left: 0;
font-size: 100px;
cursor:pointer;
opacity:0;
filter:alpha(opacity=0);}
.afile {
position: relative;
}
</style>
	<div class="searchInfo">
		<div class="searchCon">
			<form:form id="searchForm" modelAttribute="financialStateImport" action="${ctx}/credit/financialStateImport/upload" enctype="multipart/form-data" method="post"  class="form-horizontal">
				<h3 class="searchTitle">Themis财务数据导入</h3>
				<form:hidden path="id" />
				<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo }" />
				<sys:message content="${message}" />
				<div class="filter">
					<table class="fromTable">
						<ul class="ul-form">
							<tr>
								<td class="ft_label" style="width:10%">
									<span>Excel模板下载：</span>
								</td>
								<td class="ft_content" style="width:25%">
									<a href="${ctx }/credit/financialStateImport/excel">Themis财务报表模板.xls</a>
								</td>
								<td>
									<span style="color: red;width: 20%">${downloadMassage} </span>
								</td>
							<td rowspan="3"  >	<span id="sugges" style="color: red;">导入财务报表注意事项：<br>1.财报导入的年份和月份不需要输入"年","月"等字样,只需输入数字即可,如'201612';报表至少需为连续三年的同一月份且报表年份和报表月份需为'文本'格式。<br>2.下载excel模板后,需用保存为2003格式。<br>3.财报中各项单元格为空,需要使用0.00填充。<br>4.'流动资产','非流动资产','流动负债','所有者权益'需要填充'-'。</span></td>
							</tr>
							<%--<tr>
							<td class="ft_label">企业财报：</td>
							<td class="ft_content">
								<input type="text" id="file" name="file" readonly="readonly" class="input-medium" style="width:90%" />
							</td>
							<td class="ft_content">
								<input type="button" class="btn" id="inputbtn" name="input" readonlu="readonly"  value="选择" >
								<input  type="file" name="upload" id="upload"class="textopacity"  onchange="document.getElementById('file').value=this.value" style="width:100%">
							</td>
							</tr>--%>
							<tr>
								<td class="ft_label">企业财报：</td>
								<td class="ft_content">
									<input  type="file" name="upload" id="upload"   style="width:100%" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
								</td>
								<td class="ft_content">

								</td>
							</tr>
							<tr>
								<td class="ft_label">导入状态：</td>
								<td class="ft_content">
									<span id="SVerrorMessage" style="color: red;"></span>
									<span id="inputStatus" style="color: red;">${errorMessage }</span>
								</td>
								<td class="ft_content">
									<input type="submit" class="btn" id="input" name="input" readonlu="readonly"  value="导入" />
								</td>
							</tr>
						</ul>
					</table>
				</div>
			</form:form>
		</div>
	</div>
	<input type="hidden" value='${ThemisReportHead }' id="ThemisReportHead">
	<input type="hidden" value='${ThemisReportInfo1 }' id="ThemisReportInfo1">
	<input type="hidden" value='${ThemisReportInfo2 }' id="ThemisReportInfo2">
	<div id="themis" class="searchInfo"></div>
</body>
</html>

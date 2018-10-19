<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>补录流水</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	//导入文件
	function importExp() {
	    var formData = new FormData();
	    var name = $("#upload").val();
	    formData.append("file",$("#upload")[0].files[0]);
	    /* formData.append("contractNo",document.getElementById("contractNo").value); */
	    formData.append("deductUserId",document.getElementById("deductUserId").value);
	    formData.append("deductUserName",document.getElementById("deductUserName").value);
	    formData.append("flag",document.getElementById("flag").value);
/* 	    formData.append("name",name); */
	    $.ajax({
	    	url : "${ctx}/accounting/deductResult/upload",
	        type : 'POST',
	        async : false,
	        data : formData,
	        // 告诉jQuery不要去处理发送的数据
	        processData : false,
	        // 告诉jQuery不要去设置Content-Type请求头
	        contentType : false,
	        beforeSend:function(){
	            console.log("正在进行，请稍候");
	        },
	        success : function(data) {
				 if (data) {
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
 							alert("导入成功"); 
 							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				} 
	        }
	    });
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
		<h3 class="searchTitle">批量补录流水</h3>
		<div class="searchCon">
			<%-- <input type="hidden" id="contractNo" name="contractNo" value="${deductResult.contractNo}" /> --%>
			<table class="fromTable filter">
			<tr>
				<td class="ft_label" style="width:30%">操作人员代码：</td>
				<td class="ft_content">
					<input type="text" id="deductUserName" value="${deductResult.deductUserName}" htmlEscape="false" readonly="true" class="input-medium" />
					<input type="hidden" id="deductUserId" value="${deductResult.deductUserId}" />
					<input type="hidden" id="flag" value="${flag}" />
				</td>
			</tr>
			<tr>
			<td class="ft_label">文件导入：</td>
			<td class="ft_content">
				<input type="text" id="upfile" name="upfile" readonly="readonly" class="input-medium" style="width:90%" />
			</td>
			<td class="ft_content" style="width:150px">
			<a href="javascript:void(0);" class="afile">
				<input type="file" name="upload" id="upload" class="textopacity"  onchange="document.getElementById('upfile').value=this.value">
				<input type="button" class="btn btn-primary" id="inputbtn" name="input" readonlu="readonly"  value="选择" >
			</a>
			</td>
			</tr>
			<tr>
				<td  colspan="3" align="center"><input type="button" class="btn btn-primary"  onclick="importExp()" readonlu="readonly"  value="导  入" /></td>
			</tr>
			</table>
		</div>
	</div>
</body>
</html>
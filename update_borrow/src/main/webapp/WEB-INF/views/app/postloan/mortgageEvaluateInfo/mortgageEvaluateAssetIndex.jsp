<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("mortgageCarEvaluate", "${ctx}/postloan/mortgageCarEvaluateInfo/toCarEvaluateIndex", {
			applyNo : '${applyNo}'
		}, "post");
		$.loadDiv("mortgageHouseEvaluate", "${ctx}/postloan/mortgageHouseInfo/toHouseEvaluateIndex", {
			applyNo : '${applyNo}'
		}, "post");
		$.loadDiv("mortgageOtherEvaluate", "${ctx}/postloan/mortgageOtherInfo/toOtherEvaluateIndex", {
			applyNo : '${applyNo}'
		}, "post");

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//查询
	function query(applyNo,id, urlSingle, title) {
		var width = 1000;
		var height = 600;
		openJBox('formBox', urlSingle, title, width, $(top.document).height() - 200,{applyNo:applyNo,id:id});
	}
	//修改
	function editEvaluate(applyNo,id, urlSingle, title) {
		var width = 1000;
		var height = 600;
		var url = urlSingle + id + "&flag=1";
		openJBox('formBox', url, title, width, $(top.document).height() - 200,{applyNo:applyNo,id:id});
	}
	//删除 
	function deleteInfo(divName,applyNo,id, urlDelete,urlFresh) {
		$.post(urlDelete,{id:id}, function(data) {
						if (data.status == '1') {
							alertx(data.message, function() {
								$.loadDiv(divName, urlFresh, {
									applyNo : applyNo
										}, "post");
								this.close();
							});
						}else{
							alertx(data.message);
						}
					});
	}
	   function addMortgage(applyNo,urlAdd,title){
        var width = $(window).width() - 100;
	   	var height = $(window).height()-100;
	   	height = Math.min(height,"400");
		openJBox("AddAssetSelect",urlAdd,title,width,height,{applyNo:applyNo});
	}
</script>
</head>
<body>

	<div id="mortgageCarEvaluate"></div>
	<div id="mortgageHouseEvaluate"></div>
	<div id="mortgageOtherEvaluate"></div>
		<div style="overflow: auto;">
			
		</div>
</body>
</html>

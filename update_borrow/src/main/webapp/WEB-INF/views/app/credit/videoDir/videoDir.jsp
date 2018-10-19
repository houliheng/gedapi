<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>影像目录管理</title>
<meta name="decorator" content="default" />
</head>
<script type="text/javaScript">
	$(function(){
		$('#mytab a').click(function(e){
			console.info("1111111");
		})
	})
</script>
<body>
	<ul class="nav nav-tabs" id="mytab">
		<li class="active"><a href="#credit" data-toggle="tab" value="1">信用</a></li>
		<li><a href="#guaranty" data-toggle="tab" value="2">抵押</a></li>
		<li><a href="#mix" data-toggle="tab" value="3">混合</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="credit" name="CREDIT">
			<%@ include file="/WEB-INF/views/app/credit/VideoDir/videoDirIndex.jsp"%>
		</div>
		<div class="tab-pane fade" id="guaranty" >
			
		</div>
		<div class="tab-pane fade" id="mix">
			
		</div>
	</div>
</body>
</html>
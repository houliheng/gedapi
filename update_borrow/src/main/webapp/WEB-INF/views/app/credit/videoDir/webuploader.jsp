<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>公共模型</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/webuploader/css/webuploader.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/webuploader/css/style.css" />
	<script type="text/javascript" src="${ctxStatic}/webuploader/js/jquery.js"></script>
	<script type="text/javascript" src="${ctxStatic}/webuploader/js/webuploader.js"></script>
	<script type="text/javascript" src="${ctxStatic}/webuploader/js/upload.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		var taskDefKey = $("#taskDefKey").val();
		if(taskDefKey == '100' || taskDefKey == "101" || taskDefKey == "102" ||taskDefKey == "103"){
			$("#btnReset").hide();
		}
		
	});
		$(function(){
			parent.$(".jbox-close").removeClass("jbox-close");
		});
		function closeWindow(){
			var treeObj = parent.parent.$.fn.zTree.getZTreeObj("ztree");
			var node  = treeObj.getNodeByParam("id",'${videoDir.id}');
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null,treeObj.setting.treeId,node);
		}
		
	</script>
</head>
<body>
		<input type="hidden" id="applyNo" name="applyNo" value="${applyNo}"/>
		<input type="hidden" id="taskDefKey" name="taskDefKey" value="${taskDefKey}" />
		<input type="hidden" id="fileDir" name="fileDir" value="${videoDir.fileDir}" />
		<input type="hidden" id="basePath" name="basePath" value="<%=basePath%>"/>
		<%-- <h1>当前目录：${videoDir.fileDir}</h1> --%>
		<div id="container" style="overflow:auto">
            <!--头部，相册选择和格式选择 -->
            <div id="uploader"  >
                <div class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                        <p>或将照片拖到这里，单次最多可选300张</p>
                    </div>
                </div>
                <div class="statusBar" style="display:none;" >
                    <div class="progress">
                        <span class="text">0%</span>
                        <span class="percentage"></span>
                    </div><div class="info"></div>
                    <div class="btns">
                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                    </div>
                </div>
            </div>
	        <div class="searchButton">
	        	<input id="btnReset" class="btn-primary btn " type="button" value="关 闭" onclick="closeWindow();" />
			</div>
      </div>
</body>
</html>
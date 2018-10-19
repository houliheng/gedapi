<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>uploadPage</title>
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<link href="${ctxStatic}/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	window.onbeforeunload = onclose;
	function onclose() {
		if (count > 0) {
			return "有尚未完成的" + count + "个上传任务，离开页面将终止上传。是否要离开页面？";
		}
	}
	var count = 0;
	$(document).ready(function() {
		$("#uploadify").uploadify({
			'swf' : '${ctxStatic}/jqueryUpload/uploadify.swf', //是组件自带的flash，用于打开选取本地文件的按钮
			'uploader' : '${ctx}/credit/applyRegister/upload;JSESSIONID=<shiro:principal property="sessionid"/>',
			'file_post_name' : 'uploadify',//和input的name属性值保持一致就好，Struts2就能处理了
			'queueID' : 'fileQueue',
			'fileSizeLimit' : '500MB', //上传文件大小限制
			'auto' : false,//是否选取文件后自动上传
			'folder' : 'uploads',//上传文件的目录
			'multi' : true,//是否支持多文件上传
			'removeTimeout' : 0,
			'simUploadLimit' : 20,//每次最大上传文件数
			'buttonText' : '添加附件',//按钮上的文字
			'progressData' : 'percentage',//有speed和percentage两种，一个显示速度，一个显示完成百分比
			'fileTypeDesc' : '支持格式:zip压缩包', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
			'fileTypeExts' : '*.zip',//允许的格式，用;分隔
			'onCancel' : function(file) { //点叉的时候,计数器-1
				count--;
			},
// 			'onSelect' : function(file) { //当选中一个文件时,计数器+1
// 				count++;
// 				$("#uploadify").uploadify('settings', 'formData', {'applyNo': '${applyNo}','taskDefKey':'${taskDefKey}'});
// 				$("#uploadify").uploadify('upload', file.id);
// 			},
			'onQueueComplete' : function(data) { //当队列所有文件处理完成时,计数器清零
				count = 0;
			}
		});
	});
	function doUpload() {
		$("#uploadify").uploadify('settings', 'formData', {'applyNo': '${applyNo}','taskDefKey':'${taskDefKey}'});
		$("#uploadify").uploadify('upload', '*');
	}
</script>
<body scroll="yes">
	<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify" multiple="true" />
	<input class="uploadify-button" style="width: 120px;height:35px " type="button"  onclick="doUpload()"  value="开始上传">
</body>
</html>

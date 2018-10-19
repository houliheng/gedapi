<%@ page contentType="text/html;charset=UTF-8"%>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://www.resoft.css.com.cn" />
<meta name="renderer" content="webkit">

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<html>
<head>
<title>影像查询</title>
<script src="${ctxStatic}/viewer/js/jquery.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/css/icons.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>

<link href="${ctxStatic}/viewer/css/bootstrap.min.css" rel="stylesheet" />
<%-- <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" /> --%>
<link href="${ctxStatic}/viewer/css/main.css" rel="stylesheet" />
<link href="${ctxStatic}/viewer/css/viewer.css" rel="stylesheet" />


<script src="${ctxStatic}/viewer/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/viewer/js/main.js" type="text/javascript"></script>
<script src="${ctxStatic}/viewer/js/viewer.js" type="text/javascript"></script>

<link href="${ctxStatic}/common/resoft/css/resoft_main.css" rel="stylesheet" />
<script src="${ctxStatic}/common/resoft/js/resoft_main.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<style type="text/css">
	input[type="checkbox"], input[type="radio"] {
	    line-height: normal;
	    margin: 4px 70px 0;
	}

	.col-md-6 {
	    width: 95%;
	}

</style>
</head>
<body>
	<input type="hidden" id="status" name="status" value="${status}"/>
	<div class="tableList" style="width:1200px;float:left">
		<div style="width:350px;height:660px;float:left;">
			<h3 class="tableTitle" style="width:350px;height:40px;">影像查阅</h3>
			<input id="applyNo1" name="applyNo2" type="hidden" value="${applyNo}" />
			<input id="filePath" name="filePath" type="hidden" value="${filePath}" />
			<input id="fileDir" name="fileDir" type="hidden" />
			<input id="pageNo" name="pageNo" type="hidden" value="${pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${pageSize}" />
			<input id="listSize" name="listSize" type="hidden" value="${listSize}" /> <input id="taskDefKey"
				name="taskDefKey" type="hidden" value="${taskDefKey}" />
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<span style="background-color: #fafafa;">
					<!-- 右键菜单div -->
					<div id="rMenu" style="position: absolute; display: none;">
						<shiro:hasPermission name="credit:downloadPic">
							<input type="button" id="m_download" value="下载图片" onclick="download();"/>
						</shiro:hasPermission>
					</div>
			</span>
		</div>
		<div class="gallery_container111" style="width:800px;height:40px;float:left;">
			<h3 class="tableTitle" style="width:800px;height:40px;float:left;">图片区</h3>
			<div>
				<div class="gallery_thumbnails" style="width:850px;height:550px;float:center;">
					<div class='col-sm-8 col-md-6'>
						<ul class='docs-pictures clearfix'>
						</ul>
					</div>
				</div>
				<div id="delbox" style="width:850px;height:50px;"></div>
				<div id="pagination" style="width:500px;height:100px;"></div>
				<div class="clear_both"></div>
			</div>
			<div class="gallery_preview"></div>
			<div class="clear_both"></div>

			<div class="clear_both"></div>
			<div class="gallery_preload_area"></div>

		</div>

	</div>
</body>
</html>
<script type="text/javascript">
	function allSelect() {
		if ($("[name=ids]:checkbox").prop("checked") == true) {
			$("[name=ids]:checkbox").prop("checked", false);
		} else {
			$("[name=ids]:checkbox").prop("checked", true);
		}
	}

	function doDel() {
		var $checkLine = $("input[name='ids']:checked");
		//var $checkLine = $("[name=ids]:checkbox").prop("checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除图片");
		} else {
			var checkedValue = getCheckedValue("ids");
			var url = "${ctx}/credit/ApplyRegisterVO/delPicture?ids="
					+ checkedValue + "&applyNo=" + applyNo + "&taskDefKey="
					+ taskDefKey+"&status="+$("#status").val();
			confirmx('确认要删除勾选的图片吗？', url);
		}
	}
	function onCheck(filePath, pageNo) {
		document.getElementById("filePath").value = filePath;
		var applyNo = document.getElementById("applyNo1").value;
		var taskDefKey = document.getElementById("taskDefKey").value;
		//var pageNo=document.getElementById("pageNo").value;
		//alert(pageNo);
		if (pageNo.length < 0) {
			pageNo = 1;
		}
		$("#showcheckbox").empty();
		$("#pagination").empty();
		$.ajax({
					type : "POST",
					url : "${ctx}/credit/ApplyRegisterVO/postShowPicture",
					async : false,
					data : {
						"ids" : applyNo,
						"taskDefKey" : taskDefKey,
						"filePath" : filePath,
						"pageNo" : pageNo
					},
					success : function(data) {
						var Str = "";
						var strPage = "";
						var input = "";
						var returnObj = eval(data);
						var length = returnObj.length - 1;
						var user = returnObj[length].currentUser;
						if (user == null) {
							user = ${"currentUser"};
						}

						for (var i = 0; i < returnObj.length - 1; i++) {
							var flag = true;
							if(((returnObj[i].thumbnailStoragePath)+'').indexOf('data:image/jpeg;base64')=='-1'){
								if(checkIsNull((returnObj[i].thumbnailStoragePath)+'')){
									if (returnObj[i].createBy1 == user) {
									Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
											+ returnObj[i].fileStoragePath
											+ "' src='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
											+ returnObj[i].fileStoragePath
											+ "' alt='"
											+ returnObj[i].fileName
											+ "'>";
											if(returnObj[i].lockFlag == '0'){
												Str += "<input id='ids' name='ids' type='checkbox' value='"+ returnObj[i].fileStoragePath+"|"+returnObj[i].thumbnailStoragePath+"'/></li>";
											}
									} else {
										Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].fileStoragePath
												+ "' src='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].fileStoragePath
												+ "' alt='"
												+ returnObj[i].fileName
												+ "'></li>";
									}
								}else{
									if (returnObj[i].createBy1 == user) {
										Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].fileStoragePath
												+ "' src='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].thumbnailStoragePath
												+ "' alt='"
												+ returnObj[i].fileName
												+ "'>";
												if(returnObj[i].lockFlag == '0'){
													Str += "<input id='ids' name='ids' type='checkbox' value='"+ returnObj[i].fileStoragePath+"|"+returnObj[i].thumbnailStoragePath+"'/></li>";
												}
									} else {
										Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].fileStoragePath
												+ "' src='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
												+ returnObj[i].thumbnailStoragePath
												+ "' alt='"
												+ returnObj[i].fileName
												+ "'></li>";
									}
								}
							}else{
								if (returnObj[i].createBy1 == user) {
									Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
											+ returnObj[i].fileStoragePath
											+ "' src='"	+ returnObj[i].thumbnailStoragePath
											+ "' alt='"
											+ returnObj[i].fileName
											+ "'>";
											if(returnObj[i].lockFlag == '0'){
												Str += "<input id='ids' name='ids' type='checkbox' value='"+ returnObj[i].fileStoragePath+"'/></li>";
											}
								} else {
									Str += "<li><img height='140' data-original='${ctx}/credit/ApplyRegisterVO/showPictureDiv?filePath="
											+ returnObj[i].fileStoragePath
											+ "' src='"	+ returnObj[i].thumbnailStoragePath
											+ "' alt='"
											+ returnObj[i].fileName
											+ "'></li>";
								}
							}
						}
						$('.docs-pictures').html(Str);
						$('.docs-pictures').viewer("destroy").viewer({
							url : 'data-original'
						});

						document.getElementById("listSize").value = returnObj[length].listSize / 15;
						if (length > -1) {
							document.getElementById("pageNo").value = returnObj[length].pageNo;
							var filePath = document.getElementById("filePath").value;
							strPage += "<div class='pagination'><ul><li class='active'><a href='javascript:onPageUp("
									+ returnObj[length].pageNo
									+ ");'>&#171; 上一页</a></li><li class='active'>"
									+ "<a href='javascript:'>"
									+ returnObj[length].pageNo
									+ "</a></li><li class='active'><a href='javascript:onPageDown("
									+ returnObj[length].pageNo
									+ ");'>下一页 &#187;</a></li><li class='disabled controls'><a href='javascript:'>当前第 <input"+
						" maxlength='4' type='text' value='"+returnObj[length].pageNo+"'/>页， 每页<input maxlength='3'"+
						"type='text' value='15' />条，共 "
									+ returnObj[length].listSize
									+ "条 </a></li></ul><div style='clear:both;'></div></div>";
						}

						$("#showcheckbox").append(Str);
						//$("#pagination").append(strPage);
						if($("#status").val()=='0'){
							$("#delbox").html("<div><input class='uploadify-button' style='width: 50%;height:40px;' type='button'  onclick='allSelect()'  value='全    选'><input class='uploadify-button' style='width: 50%;height:40px;' type='button'  onclick='doDel()'  value='删    除'></div>");
							$("#delbox").append(strPage);
						}else{
							$("#delbox").html(strPage);
						}
					}
				});

	}

	function onPageUp(pageNo) {
		var filePath = document.getElementById("filePath").value;
		if (pageNo == 1) {
			return;
		}
		onCheck(filePath, pageNo - 1);
	}
	function onPageDown(pageNo) {
		var filePath = document.getElementById("filePath").value;
		var listSize = document.getElementById("listSize").value;
		if (pageNo > listSize) {
			return;
		}
		onCheck(filePath, pageNo + 1);
	}
	var zTree;
	var setting = {
			data : {
				simpleData : {
					enable : true,// 是否是简单数据类型
					idKey : "id",
					pIdKey : "pId",
					rootPId : '0'
				}
			},
			callback : {
				onRightClick : zTreeOnRightClick
			}

	};
	var zNodes;
	var applyNo = document.getElementById("applyNo1").value;
/* 	var taskDefKey = document.getElementById("taskDefKey").value; */
	$.ajax({
		type : "post",
		url : "${ctx}/credit/ApplyRegisterVO/showPostTree?ids=" + applyNo,
		dataType : "json",
		success : function(data) {
			//请求成功后处理函数
			zNodes = data;//把后台封装好的简单Json格式赋给treeNodes
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	});

	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	var code;
	function showCode(str) {
		if (!code)
			code = $("#code");
		code.empty();
		code.append("<li>" + str + "</li>");
	}

	 //显示右键菜单
	function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_download").hide();

		}
		$("#rMenu").css({"top":y+"px", "left":x+"px", "display":"block"});
	}
	//隐藏右键菜单
	function hideRMenu() {
		$("#rMenu").hide();
	}

	//鼠标右键事件-创建右键菜单
	function zTreeOnRightClick(event, treeId, treeNode) {
		$("#fileDir").val(treeNode.id);
		if (!treeNode) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && treeNode.pId != null) { //noR属性为true表示禁止右键菜单
			if (treeNode.newrole && event.target.tagName != "a" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}
	}

	
	$(function() {
		$("body").bind(//鼠标点击事件不在节点上时隐藏右键菜单
		"mousedown", function(event) {
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
				$("#rMenu").hide();
			}
		});
	});

	function download() {
		window.location.href="${ctx}/credit/videoUpload/download?applyNo=${applyNo}&fileDir="+$("#fileDir").val();
	}

	var selectPath = function() {
		try {
			var Message = "请选择文件夹";
			var Shell = new ActiveXObject("Shell.Application");
			var Folder = Shell.BrowseForFolder(0, Message, 0x0040, 0x11);
			//var Folder=Shell.BrowseForFolder(0,Message,0);
			if (Folder != null) {
				Folder = Folder.items();
				Folder = Folder.item();
				Folder = Folder.Path;
				if (Folder.charAt(Folder.length - 1) != "\\") {
					Folder = Folder + "\\";
				}
				return Folder;
			}
			return null;
		} catch (e) {
			alert("请在IE安全设置中启用'对未标记为安全执行脚本的AcitveX控件初始化并执行'选项。");
			return null;
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});

	function getSelectedNodes() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getCheckedNodes(true), v = "";
		var ids = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}

		if (ids.length > 0)
			ids = ids.substring(0, ids.length - 1);
		alert(ids);
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		//var cityObj = $("#citySel");
		//var cityObjIds = $("#citySelIds");
		//给父窗体updateContact.jsp中所属分类赋值
		//		 window.opener.document.getElementById("cateSelIds").value=ids;
		//		 window.opener.document.getElementById("cateSelName").value=v;
	}
</script>
<script type="text/javascript">
	(function(jQuery){

		if(jQuery.browser) return;

		jQuery.browser = {};
		jQuery.browser.mozilla = false;
		jQuery.browser.webkit = false;
		jQuery.browser.opera = false;
		jQuery.browser.msie = false;

		var nAgt = navigator.userAgent;
		jQuery.browser.name = navigator.appName;
		jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);
		jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);
		var nameOffset,verOffset,ix;

		// In Opera, the true version is after "Opera" or after "Version"
		if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
		jQuery.browser.opera = true;
		jQuery.browser.name = "Opera";
		jQuery.browser.fullVersion = nAgt.substring(verOffset+6);
		if ((verOffset=nAgt.indexOf("Version"))!=-1)
		jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
		}
		// In MSIE, the true version is after "MSIE" in userAgent
		else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
		jQuery.browser.msie = true;
		jQuery.browser.name = "Microsoft Internet Explorer";
		jQuery.browser.fullVersion = nAgt.substring(verOffset+5);
		}
		// In Chrome, the true version is after "Chrome"
		else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
		jQuery.browser.webkit = true;
		jQuery.browser.name = "Chrome";
		jQuery.browser.fullVersion = nAgt.substring(verOffset+7);
		}
		// In Safari, the true version is after "Safari" or after "Version"
		else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
		jQuery.browser.webkit = true;
		jQuery.browser.name = "Safari";
		jQuery.browser.fullVersion = nAgt.substring(verOffset+7);
		if ((verOffset=nAgt.indexOf("Version"))!=-1)
		jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
		}
		// In Firefox, the true version is after "Firefox"
		else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
		jQuery.browser.mozilla = true;
		jQuery.browser.name = "Firefox";
		jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
		}
		// In most other browsers, "name/version" is at the end of userAgent
		else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) <
		(verOffset=nAgt.lastIndexOf('/')) )
		{
		jQuery.browser.name = nAgt.substring(nameOffset,verOffset);
		jQuery.browser.fullVersion = nAgt.substring(verOffset+1);
		if (jQuery.browser.name.toLowerCase()==jQuery.browser.name.toUpperCase()) {
		jQuery.browser.name = navigator.appName;
		}
		}
		// trim the fullVersion string at semicolon/space if present
		if ((ix=jQuery.browser.fullVersion.indexOf(";"))!=-1)
		jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);
		if ((ix=jQuery.browser.fullVersion.indexOf(" "))!=-1)
		jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);

		jQuery.browser.majorVersion = parseInt(''+jQuery.browser.fullVersion,10);
		if (isNaN(jQuery.browser.majorVersion)) {
		jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);
		jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);
		}
		jQuery.browser.version = jQuery.browser.majorVersion;
		})(jQuery);
</script>
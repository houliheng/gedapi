<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>影像分类列表</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style>
body {
	background-color: white;
	margin: 0;
	padding: 0;
	text-align: center;
}

div,p,table,th,td {
	list-style: none;
	margin: 0;
	padding: 0;
	color: #333;
	font-size: 12px;
	font-family: dotum, Verdana, Arial, Helvetica, AppleGothic, sans-serif;
}

#testIframe {
	margin-left: 10px;
}
</style>
<script>
	var zTree;
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		treeNodeKey : "id",
       	treeNodeParentKey : "pId",
      	showLine : true,
	    checkable : true,
        callback : {
            onRightClick : zTreeOnRightClick
        }
	};

	var zNodes = [<c:forEach items="${zNodesList}" var="tpl">
             {id:'${tpl.id}', pId:'${tpl.pId}', name:"${tpl.name}", fileDir:"${tpl.fileDir}",
           	  url:"${ctx}/video/show/${tpl.fileDir ！= null ? 'picture':'none'}?fileDir=${tpl.fileDir}&applyNo=${applyNo}&taskDefKey=${taskDefKey}",
           	  target:"cmsMainFrame"},
             </c:forEach>];

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
		//console.log(JSON.stringify(treeNode));
		$("#fileDir").val(treeNode.fileDir);
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

	function reloadTree() {
        hideRMenu();
        zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
    }

	$(function() {
        //reloadTree();
        $("body").bind(//鼠标点击事件不在节点上时隐藏右键菜单
                "mousedown",
                function(event) {
                    if (!(event.target.id == "rMenu" || $(event.target)
                            .parents("#rMenu").length > 0)) {
                        $("#rMenu").hide();
                    }
                });
    });

	function download(){
		var path = selectPath();
	 	$("#rMenu").hide();
	 	$.post("${ctx}/video/show/download",{"applyNo":'${applyNo}',"taskDefKey":'${taskDefKey}',"fileDir":$("#fileDir").val(),"path":path},function(msg){
	 		if(msg =="success")
	 		alertx("图片下载完成！");
	 	});
	}

	var selectPath = function(){
		try{
			var Message="请选择文件夹";
			var Shell=new ActiveXObject("Shell.Application");
			var Folder=Shell.BrowseForFolder(0,Message,0x0040,0x11);
			//var Folder=Shell.BrowseForFolder(0,Message,0);
			if(Folder != null){
				Folder = Folder.items();
				Folder = Folder.item();
				Folder = Folder.Path;
				if(Folder.charAt(Folder.length-1) != "\\"){
					Folder = Folder + "\\";
				}
				return Folder;
			}
			return null;
		}catch(e){
			alert("请在IE安全设置中启用AcitveX控件相关选项。");
			return null;
		}
	}

	$(document).ready(function(){
		zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
	});
	</script>
</head>
<body>
	<input id="fileDir" name="fileDir" type="hidden" />
	<div id="content" class="zTreeDemoBackground left">
		<ul id="tree" class="ztree"></ul>
	</div>
	<span style="background-color: #fafafa;">
		<!-- 右键菜单div -->
		<div id="rMenu" style="position: absolute; display: none;">
			<input type="button" id="m_download" value="下载图片" onclick="download();"/>
		</div>
	</span>
</body>
</html>
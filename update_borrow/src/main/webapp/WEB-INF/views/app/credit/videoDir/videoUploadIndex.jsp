<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>影像上传</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>

<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}
.form-search .ul-form li label {
    text-align: right;
    width: 240px;

}
</style>
<script type="text/javascript">

	function addColumn(){
		var columnName = getDictLabel(${fns:toJson(fns:getDictList('cre_video_cust'))}, $("#columnType").val());
		if(!checkIsNull(columnName)){
			if(columnName != '自定义目录'){
				confirmx("确认要增加"+columnName+"？", newColumn);
			}else{
				openJBox('addRootColumn',"${ctx}/credit/videoUpload/rootForm","新增自定义目录",700,350,{applyNo:$("#applyNo").val(),type:$("#type").val()});
			}
		}else{
			alertx("请选择要添加的目录类型！");
		}
	}

	function newColumn(){
		var columnName = getDictLabel(${fns:toJson(fns:getDictList('cre_video_cust'))}, $("#columnType").val());
		$.ajax({
			type:"POST",
			url:"${ctx}/credit/videoUpload/addColumn",
			data:{
				applyNo:$("#applyNo").val(),
				type:$("#type").val(),
				name:columnName
			},
			success:function(){
				refreshTree(false, 0);
			},
			error:function(){}
		});
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
			alert("请在IE安全设置中启用'对未标记为安全执行脚本的AcitveX控件初始化并执行'选项。");
			return null;
		}
	}

	function download(){
		var path = selectPath();
		console.log(path);
		if(path != null){
		 	$("#rMenu").hide();
		 	$.post("${ctx}/credit/videoUpload/download",{"applyNo":'${applyNo}',"taskDefKey":'${taskDefKey}',"fileDir":'${applyNo}'+'/'+$("#fileDir").val(),"path":path},function(msg){
		 		if(msg =="success")
		 		alertx("图片下载完成！");
		 	});
		}
	}

</script>
</head>
<body>
	<input type="hidden" id="applyNo" name="applyNo" value="${applyNo}" />
	<input type="hidden" id="type" name="type" value="1" />
	<input type="hidden" id="fileDir" name="fileDir"  />
	<div class="searchCon">
		<form:form id="searchForm" modelAttribute="videoDir"  class="breadcrumb form-search">
			<div class="filter">
				<ul class="ul-form">
					<li><label>新增目录类型：</label></li>
					<li>
						<form:select path="" id="columnType" class="input-medium required" cssStyle="width:177px;" >
								<form:option value="" label="请选择" />
								<form:options items="${fns:getDictList('cre_video_cust')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</li>
					<li><div style="width:100px"></div></li>
					<li>
						<input class="btn btn-primary" type="button" id="btnNew" value="新增目录" onclick='addColumn()' />
					</li>

				</ul>

			</div>
		</form:form>
	</div>

	<sys:message content="${message}" />
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle">组织影像目录<i class="icon-refresh pull-right" onclick="refreshTree(false, 0);"></i></a>
			</div>
			<div id="ztree" class="ztree" ></div>
			<span style="background-color: #fafafa;">
				<!-- 右键菜单div -->
				<div id="rMenu" style="position: absolute; display: none;">
					<shiro:hasPermission name="credit:videoDir:edit">
						<input type="button" id="m_download" value="下载图片" onclick="download();"/>
					</shiro:hasPermission>
				</div>
			</span>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent"
				src="" width="100%"
				height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var options = {
			onClick:function(event, treeId, treeNode) {//定义单机事件的方法
				console.log(JSON.stringify(treeNode));
				var id = treeNode.id == '0' ? '' : treeNode.id;
				if(treeNode.pId != '0'||treeNode.pIds == '-1')
				$('#officeContent').attr("src","${ctx}/credit/videoUpload/videoShow?id=" + id +"&type="+treeNode.type+"&applyNo=${applyNo}&taskDefKey=${taskDefKey}");
			},
			zTreeOnRightClick:function(event, treeId, treeNode) {//鼠标右键事件-创建右键菜单
				$("#fileDir").val(treeNode.fileDir);
				if (!treeNode) {
					treeObj.cancelSelectedNode();
					showRMenu("root", event.clientX, event.clientY);
				} else if (treeNode && treeNode.pId != null) { //noR属性为true表示禁止右键菜单
					if (treeNode.newrole && event.target.tagName != "a" && $(event.target).parents("a").length == 0) {
						treeObj.cancelSelectedNode();
						showRMenu("root", event.clientX, event.clientY);
					} else {
						treeObj.selectNode(treeNode);
						showRMenu("node", event.clientX, event.clientY);
					}
				}
			},
			dataUrl:"${ctx}/credit/videoUpload/treeData?type="+$("#type").val()+"&applyNo="+$("#applyNo").val()+"&taskDefKey="+'${taskDefKey}',//获取数据的url
			isOpen :false,//是否展开全部，
			/* openLevel:1,//展开一级 */
			openNumber:1,//每层展开第一个节点
			div:$("#ztree"),//生成结构树所在的div
			addflag:false,//是否添加新影像目录
			addTreeNode:0//添加新影像目录的节点
		};

		$.fn.mmInfoZtree(options);

		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0]-80);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}

		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		console.log('${id}');
		var node  = treeObj.getNodeByParam("id",'${id}');
		treeObj.selectNode(node);
		treeObj.setting.callback.onClick(null,treeObj.setting.treeId,node);

		function refreshTree(addflag, id) {
			options["addflag"] = addflag;//是否添加新影像目录
			options["addTreeNode"] = id;//添加新影像目录的节点
			$.fn.mmInfoZtree(options);
		}

		 $("body").bind(//鼠标点击事件不在节点上时隐藏右键菜单
                "mousedown",
                function(event) {
                    if (!(event.target.id == "rMenu" || $(event.target)
                            .parents("#rMenu").length > 0)) {
                        $("#rMenu").hide();
                    }
                });

	</script>
	<script src="${ctxStatic}/common/wsize.min.js?v=20161129" type="text/javascript"></script>
</body>
</html>

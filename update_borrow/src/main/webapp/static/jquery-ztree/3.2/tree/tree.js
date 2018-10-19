var currentObjId;
var currentObjName;
var currentTreeId;

/**
 * @reqno:H1507060055
 * @date-designer:2015年7月7日-suzhenyu
 * @date-author:2015年7月7日-suzhenyu:
 * 生成树：每个页面只生成一次，初次生成后，会缓存起来。
 * @param objId 返回ID值的input对象
 * @param objName 返回name值的input对象，同时也是弹出树的参考对象
 * @param datatype 树的数据类型：机构树、用户树等等
 * @param treeType 树的类型：单选树（radio）、多选树（checkbox）
 * @param rootId 树的根节点ID
 * @param showRoot 是否显示根节点：显示(true)，不显示(false)
 */
function showtree(objId, objName, dataType, treeType, rootId, showRoot) {
	// 初始化全局参数
	currentObjId = objId;
	currentObjName = objName;
	currentTreeId = objName + "TreeUl";
	// 1.从缓存中取树
	var tree = $(document.body).data(objName);
	if(typeof(tree)=="undefined"){
		// 2.从缓存中没有取到树，生成一个新树。
		// 2.1获取树的显示坐标
		var obj = $("#" + objName);
		var objOffset = obj.offset();
		tree = $("<div class='menuContent' style='position: absolute;'><ul id='" + currentTreeId + "' class='ztree' style='margin-top:0; width:250px; height: 400px;'></ul></div>");
		//tree.append("过滤：<input type='text' id='searchKey' class='empty'/>");
		tree.css({left:objOffset.left + "px", top:objOffset.top + obj.outerHeight() + "px"});
		$(document.body).prepend(tree);
		// 2.2将树放入缓存中
		$(document.body).data(objName, tree);
		// 2.3初始化树
		initTree(tree.children(), dataType, treeType, rootId, showRoot);
	}else{
		// 3.从缓存中取到树，将树显示出来。
		tree.slideDown("fast");
	}
	// 4.绑定鼠标事件
	$(document.body).bind("mousedown", onBodyDown);
}
/**
 * 鼠标事件
 * @param event
 */
function onBodyDown(event) {
	if (!($(event.target).parents(".menuContent").length>0)) {
		hideMenu();
	}
}
/**
 * 隐藏树列表
 */
function hideMenu() {
	$(".menuContent").fadeOut("fast");
	$(document.body).unbind("mousedown", onBodyDown);
}
/**
 * 初始化树
 * @param tree
 * @param dataType 
 * @param treeType
 * @param rootId 树的根节点ID
 * @param showRoot 是否显示根节点：显示(true)，不显示(false)
 */
function initTree(tree, dataType, treeType, rootId, showRoot){
	var checkedId = $("#" + currentObjId).val();
	var setting = {
			data:{
				simpleData:{
					enable: true
				}
			},
			async: {
				enable: true,
				url:"/cr/treeAction?treeType="+dataType+"&rootId="+rootId+"&showRoot="+showRoot+"&checkedNodeIds="+checkedId,
				autoParam:["id"],
				otherParam:{"checkedNodeIds":checkedId}
			},
			check: {
				enable: true,
				chkStyle: treeType,
				chkboxType: { "Y": "s", "N": "s" },
				radioType: "all"
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck
			},
			view : {
				fontCss : getFontCss
			}
		};
	if(treeType=="checkbox"){// 递归选中下级节点
		setting.check.autoCheckTrigger = true;//设置为true，当自动勾选时，会触发onCheck事件
		setting.callback.beforeCheck=beforeCheck;
		setting.callback.onAsyncSuccess=zTreeOnAsyncSuccess;
	}
	$.fn.zTree.init(tree, setting);
	//$("#searchKey").bind("propertychange", searchNode);
}
/**
 * 用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
/**
 * 用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
 * @param e  event 对象
 * @param treeId
 * @param treeNode
 */
function onCheck(e, treeId, treeNode) {
	beforeCheck(treeId, treeNode);
	var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
			.getCheckedNodes(true), v = "", vi = "";
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vi += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (vi.length > 0)
		vi = vi.substring(0, vi.length - 1);
	$("#" + currentObjName).attr("value", v);
	$("#" + currentObjId).attr("value", vi);
}

//*===========用于处理递归选中下级节点功能======================
/**
 * 用于捕获 勾选 或 取消勾选 之前的事件回调函数，并且根据返回值确定是否允许 勾选 或 取消勾选 
 * @param treeId
 * @param treeNode
 */
function beforeCheck(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if (!treeNode.zAsync) {
		// 第三个参数改为false，则会自动打开节点，改为true，则不会自动打开节点
		zTree.reAsyncChildNodes(treeNode, "refresh", false);
	}
	return true;
}
/**
 * 用于捕获异步加载正常结束的事件回调函数
 * @param e  event 对象
 * @param treeId 对应 zTree 的 treeId，便于用户操控
 * @param treeNode 进行异步加载的父节点 JSON 数据对象
 * @param msg 异步获取的节点数据字符串，主要便于用户调试使用
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (treeNode==null || treeNode.checked==null || !treeNode.checked) {// 已选中，则需要处理下级的选中
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var childrenNode = treeNode.children;
	for ( var i = 0; i < childrenNode.length; i++) {
		zTree.checkNode(childrenNode[i], treeNode.checked, true, true);
	}
}
//============用于处理查询过滤功能=======================*//
//*============用于处理查询过滤功能========================
var lastValue = "", nodeList = [];
function searchNode(e) {
	var zTree = $.fn.zTree.getZTreeObj(currentTreeId);
	var value = $("#searchKey").val();
	if(value==""){
		return;
	}
	updateNodes(false);
	nodeList = zTree.getNodesByParamFuzzy("name", value);
	updateNodes(true);
}
/**
 * 更新节点属性
 * @param highlight
 */
function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj(currentTreeId);
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}

/**
 * 设置节点样式
 * @param treeId
 * @param treeNode
 * @returns
 */
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}
//============用于处理查询过滤功能========================*//
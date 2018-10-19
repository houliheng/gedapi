<!--
@reqno        :H1508270061
@e-tree       :ztree-组织机构:画面右侧组织机构树 ztree
@db-z         :sys_office : 机构表
@date-designer:20150827-jiangbing
@date-author  :20150827-jiangbing 改修BUG 组织机构树不能刷新，添加机构后不刷新组织机构树
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}
</style>
</head>
<body>
	<sys:message content="${message}" />
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle">组织机构<i
					class="icon-refresh pull-right" onclick="refreshTree(false, 0);"></i></a>
			</div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<!--
			 * @reqno: H1507080024
			 * @date-designer:20150713-zhunan
			 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
	 		 * @db-j : sys_office : id,parent_id,name,sort,code,type
			 * @date-author:20150713-zhunan: 只显示根节点为0的数据
			-->
			<iframe id="officeContent"
				src="${ctx}/sys/office/list?id=&parent.id=0" width="100%"
				height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		/**
		 * 
		 * @reqno:H1507080030
		 * @date-designer:20150717-zhangxingyu
		 * @e-tree : ztree- div: 结构树的div
		 * @e-tree : officeContent - iframe : 引入另一页面
		 * @db-z : sys_office : 机构表
		 * @date-author:20150717-zhangxingyu:定义该页面ztree组件所需要的参数
		 */
		var options = {
			onClick:function(event, treeId, treeNode) {//定义单机事件的方法
				/**
				 * @reqno: H1507230023
				 * @date-designer:20150727-chenshaojia
				 * @db-j : sys_office : parent_ids
				 * @date-author:20150727-chenshaojia: 更改节点单击事件传入参数的值，单击节点，显示本节点以下所有的节点(不包括本节点)。
				 */
				var id = treeNode.id == '0' ? '' : treeNode.id;
				$('#officeContent').attr("src","${ctx}/sys/office/list?id=" + id + "&parentIds="+ treeNode.pIds + id);
			},
			dataUrl:"${ctx}/sys/office/treeData",//获取数据的url
			isOpen :false,//是否展开全部，
			openLevel:1,//展开一级
			openNumber:1,//每层展开第一个节点
			div:$("#ztree"),//生成结构树所在的div
			/**
             * @reqno:H1508270061
             * @date-designer:20150827-jiangbing
             * @date-author:20150827-jiangbing:添加机构用变量默认值
             */
			addflag:false,//是否添加新机构
			addTreeNode:0//添加新机构的节点
		};
		/**
		 * 
		 * @reqno:H1507080030
		 * @date-designer:20150717-zhangxingyu
		 * @date-author:20150717-zhangxingyu:调用ztree组件方法生成结构树；
		 */
		$.fn.mmInfoZtree(options);
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
		
		/**
		 * @reqno:H1508270061
		 * @date-designer:20150827-jiangbing
		 * @date-author:20150827-jiangbing:刷新结构树
		 */
		function refreshTree(addflag, id) {
			options["addflag"] = addflag;//是否添加新机构
			options["addTreeNode"] = id;//添加新机构的节点
			$.fn.mmInfoZtree(options);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
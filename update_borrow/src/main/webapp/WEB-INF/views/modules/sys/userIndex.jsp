<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	
</head>
<body>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="${ctx}/sys/user/list" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		/**
		 * @reqno        :H1511230055
		 * @date-designer:20151123-lvhaishan
		 * @e-in-text    :office:归属机构
		 * @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
		 *   20150717-zhangxingyu:定义该页面ztree组件所需要的参数
		 */
		var options = {
			onClick:function(event, treeId, treeNode){//定义单机事件的方法
				var id = treeNode.id == '0' ? '' :treeNode.id;
				/**
				 * @reqno: H1507230050
				 * @date-designer:20150728-chenshaojia
				 * @db-z : sys_office : 机构表
				 * @db-z : sys_user : 用户表
				 * @date-author:20150728-chenshaojia:增加判断逻辑，区分查询条件“归属机构”“归属部门”的查询操作
				 */
				if(treeNode.type == '1'){
					$("#officeContent").attr("src","${ctx}/sys/user/list?company.id="+id+"&company.name="+encodeX(treeNode.name));
				}
				else{
					$("#officeContent").attr("src","${ctx}/sys/user/list?office.id="+id+"&office.name="+encodeX(treeNode.name));
				}
			},
			dataUrl:"${ctx}/sys/office/treeData",//获取数据的url
			isOpen :false,//是否展开全部
			openLevel:1,//展开一级
			openNumber:1,//每层展开第一个节点
			div:$("#ztree"),//生成结构树所在的div
			/**
	         * @reqno:H1508270061
	         * @date-designer:20150827-jiangbing
	         * @date-author:20150827-jiangbing:添加机构用变量默认值， 用户刷新不需要考虑
	         */
			addflag:false,
			addTreeNode:0
		};
		$.fn.mmInfoZtree(options);//调用ztree组件方法生成结构树
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
        function refreshTree() {
            $.fn.mmInfoZtree(options);
        }
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
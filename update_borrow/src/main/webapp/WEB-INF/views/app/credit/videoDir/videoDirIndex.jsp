<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>影像目录管理</title>
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
<script type="text/javascript">
	$(function (){
		//alert('${videoDir.type}');
	})
			
	function reload(obj){
		//console.info($("#type").val());
		options["dataUrl"]="${ctx}/credit/videoDir/treeData?type="+$(obj).val();
		$.fn.mmInfoZtree(options);
		$("#officeContent").attr("src","${ctx}/credit/videoDir/list?type="+$(obj).val());
	}
</script>
</head>
<body>
	<div class="searchCon">
		<form:form id="searchForm" modelAttribute="videoDir"  method="post" class="breadcrumb form-search">
			<div class="filter">
				<ul class="ul-form">
					<li><label>产品类型：</label>
						<form:select path="type" class="input-medium required" cssStyle="width:177px;" onchange="reload(this)">
								<form:option value="" label="请选择" />
								<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</li>
				</ul>
				
			</div>
		</form:form>
	</div>
		
	<sys:message content="${message}" />
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle">组织影像目录<i
					class="icon-refresh pull-right" onclick="refreshTree(false, 0);"></i></a>
			</div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent"
				src="${ctx}/credit/videoDir/list?type=${videoDir.type}" width="100%"
				height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var options = {
			onClick:function(event, treeId, treeNode) {//定义单机事件的方法
				var id = treeNode.id == '0' ? '' : treeNode.id;
				$('#officeContent').attr("src","${ctx}/credit/videoDir/listOrg?id=" + id +"&type="+treeNode.type);
			},
			dataUrl:"${ctx}/credit/videoDir/treeData?type="+$("#type").val(),//获取数据的url
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
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
		
		function refreshTree(addflag, id) {
			options["addflag"] = addflag;//是否添加新影像目录
			options["addTreeNode"] = id;//添加新影像目录的节点
			$.fn.mmInfoZtree(options);
		}
		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
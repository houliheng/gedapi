var dataUrl = $("#show_data").val();
var options = {
	onClick : function(event, treeId, treeNode) {// 定义单机事件的方法
		var id = treeNode.id == '0' ? '' : treeNode.id;
	},
	dataUrl : dataUrl,// 获取数据的url
	isOpen : false,// 是否展开全部
	openLevel : 1,// 展开一级
	openNumber : 1,// 每层展开第一个节点
	div : $("#ztree")
// 生成结构树所在的div
};

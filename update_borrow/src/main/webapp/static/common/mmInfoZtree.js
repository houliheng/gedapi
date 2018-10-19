/**
 *
 * @reqno:H1507080031
 * @date-designer:20150717-zhangxingyu
 * @e-tree : ztree- div: 结构树
 * @date-author:20150717-zhangxingyu:ztree的公共方法的js文件
 * 						参数 onClick:  //单机事件
							dataUrl:  //获取数据的url
							isOpen ://是否展开全部
							openLevel://展开几级   注：不全部展开时有效
							openNumber://每层展开几个节点    注：参数大于该层节点数时 去该层节点数  上级节点未被展开时无效
							div://生成结构树所在的div
 */
(function($) {
	$.fn.mmInfoZtree = function(options) {
		/**
		 * ztree配置参数
		 */
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
				onClick : options.onClick,
				onRightClick : options.zTreeOnRightClick
			// 单击事件
			}
		};
		var mmInfoTree = {
			init : function(setting) {
					$.ajaxSettings.async = false;
					$.getJSON(
						options.dataUrl,
						function(data) {// 获取数据
							var tree = $.fn.zTree.init(options.div,
									setting, data);
							tree.expandAll(options.isOpen);
							if (!options.isOpen) {// 不展开全部，
								for (var l = 0; l < options.openLevel; l++) {
									var nodes = tree.getNodesByParam("level", l, null);
									var num = nodes.length;
									if(options.openNumber!= null && nodes.length>options.openNumber){//注：参数大于该层节点数时 去该层节点数
										num=options.openNumber;
									}
									for (var i = 0; i < num; i++) {
										if(l!=0 && nodes[i].getParentNode().open==false) continue;//上级节点未被展开时无效
										nodes[i].open = true;
									}
								}
							}
							tree.refresh();// 刷新结构树
						});
			}
		}
		mmInfoTree.init(setting);
	}
})(jQuery);
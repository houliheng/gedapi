<%@ page contentType="text/html;charset=UTF-8"%>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://www.resoft.css.com.cn"/>
<meta name="renderer" content="webkit">

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<html>
<head>
<title>影像查询</title>
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery.fancybox-2.1.5/fancybox/jquery.fancybox.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery.fancybox-2.1.5/fancybox/jquery.fancybox.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery.fancybox-2.1.5/fancybox/helpers/jquery.fancybox-buttons.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery.fancybox-2.1.5/fancybox/helpers/jquery.fancybox-buttons.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery.fancybox-2.1.5/jquery.mousewheel-3.0.6.pack.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/css/icons.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>

<link href="${ctxStatic}/common/resoft/css/resoft_main.css" rel="stylesheet" />
<script src="${ctxStatic}/common/resoft/js/resoft_main.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>


</head>
<body>
	<div class="tableList" style="width:1200px;float:left;">
		<div style="width:350px;height:660px;float:left;">
		<h3 class="tableTitle" style="width:350px;height:40px;">影像查阅</h3>
		<input id="applyNo1" name="applyNo2" type="hidden" value="${applyNo}" />
		<input id="filePath" name="filePath" type="hidden" value="${filePath}" />
		<input id="pageNo" name="pageNo" type="hidden" value="${pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${pageSize}"/>
		<input id="listSize" name="listSize" type="hidden" value="${listSize}"/>
		<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}"/>
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>		
		<div class="gallery_container111" style="width:800px;height:40px;float:left;">
			<h3 class="tableTitle" style="width:800px;height:40px;float:left;">图片区</h3>
<!-- 			<div class="gallery_top"></div> -->
			<div >
				<div id="showcheckbox" class="gallery_thumbnails"
					style="width:850px;height:500px;float:center;">
					</div>
				<div id="delbox"  style="width:850px;height:50px;"></div>
				<div id="pagination" style="width:500px;height:100px;">
				</div>
				<div class="clear_both"></div>
			</div>
			<div class="gallery_preview">
			</div>
			<div class="clear_both">
			</div>

			<div class="clear_both" ></div>
			<div class="gallery_preload_area" ></div>

		</div>

<!-- 		<div class="gallery_bottom"></div> -->
	</div>
</body>
</html>
<script type="text/javascript">

function allSelect(){
	if ($("[name=ids]:checkbox").attr("checked") == "checked") {
		$("[name=ids]:checkbox").attr("checked", false);
	}else{
		$("[name=ids]:checkbox").attr("checked", true);
	}
} 




function doDel(){
	var $checkLine = $("input[name='ids']:checked");
	var $len = $checkLine.length;
	if ($len < 1) {
		alertx("请选择要删除图片");
	} else {
		var checkedValue = getCheckedValue("ids");
		var url = "${ctx}/postloan/showImage/delPicture?ids=" + checkedValue+"&applyNo="+applyNo+"&taskDefKey="+taskDefKey;
		confirmx('确认要删除勾选的图片吗？', url);
	}
}
	function onCheck(filePath,pageNo) {
		document.getElementById("filePath").value=filePath;
		//var pageNo=document.getElementById("pageNo").value;
		//alert(pageNo);
		if(pageNo.length<0){
			pageNo=1;
		}
		$("#showcheckbox").empty();
		$("#pagination").empty();
		jQuery
				.ajax({
					type : "POST",
					url : "${ctx}/postloan/showImage/showPicture",
					async : false,
					data : {
						"filePath" : filePath,
						"pageNo":pageNo
					},
					success : function(data) {
						var Str = "";
						var strPage = "";
						var returnObj = eval(data);
						var length=returnObj.length-1;
						var user= returnObj[length].currentUser;
						if(user ==null){
							user=${"currentUser"};
						}
						for (var i = 0; i < returnObj.length-1; i++) {
							var flag=true;
							Str +="<div style='float: left;width:20%;padding-top: 25px;padding-bottom: 25px;text-align:right'>";
							if(returnObj[i].createBy1==user){
								Str +="<input id='ids' name='ids' type='checkbox' value='"+ returnObj[i].fileStoragePath+"|"+returnObj[i].thumbnailStoragePath+"'/>";
							}
							Str +="<a class='fancybox-buttons'  data-fancybox-group='group1' href='${ctx}/postloan/showImage/showPictureDiv?filePath="
									+ returnObj[i].fileStoragePath
									+ "'><img style='padding-left: 10px;margin-right: 10px;width:115px;height:87;' src='${ctx}/postloan/showImage/showPictureDiv?filePath="
									+ returnObj[i].thumbnailStoragePath
									+ "'/></a></div>";
						}
						document.getElementById("listSize").value=returnObj[length].listSize/15;
					if(length>-1){
						document.getElementById("pageNo").value=returnObj[length].pageNo;
						var filePath=document.getElementById("filePath").value;
						strPage+="<div class='pagination'><ul><li class='active'><a href='javascript:onPageUp("+returnObj[length].pageNo+");'>&#171; 上一页</a></li><li class='active'>"+
						"<a href='javascript:'>"+returnObj[length].pageNo+"</a></li><li class='active'><a href='javascript:onPageDown("+returnObj[length].pageNo+");'>下一页 &#187;</a></li><li class='disabled controls'><a href='javascript:'>当前第 <input"+
						" maxlength='4' type='text' value='"+returnObj[length].pageNo+"'/>页， 每页<input maxlength='3'"+
						"type='text' value='15' />条，共 "+returnObj[length].listSize+"条 </a></li></ul><div style='clear:both;'></div></div>";
					}
						
						$("#showcheckbox").append(Str);
						$("#pagination").append(strPage);
						$("#delbox").html("<div><input class='uploadify-button' style='width: 50%;height:40px;' type='button'  onclick='allSelect()'  value='全    选'><input class='uploadify-button' style='width: 50%;height:40px;' type='button'  onclick='doDel()'  value='删    除'></div>");
					}
				});
		setFancyBoxLinks();
		
	/* 	 $(document).ready(function() {        
		        $('#imageFullScreen').smartZoom({'containerClass':'zoomableContainer'});        
		        $('#topPositionMap,#leftPositionMap,#rightPositionMap,#bottomPositionMap').bind("click", moveButtonClickHandler);
		        $('#zoomInButton,#zoomOutButton').bind("click", zoomButtonClickHandler);
		        
		        function zoomButtonClickHandler(e){
		            var scaleToAdd = 0.8;
		            if(e.target.id == 'zoomOutButton')
		                scaleToAdd = -scaleToAdd;
		            $('#imageFullScreen').smartZoom('zoom', scaleToAdd);
		        }        
		        function moveButtonClickHandler(e){
		            var pixelsToMoveOnX = 0;
		            var pixelsToMoveOnY = 0;
		    
		            switch(e.target.id){
		                case "leftPositionMap":
		                    pixelsToMoveOnX = 50;	
		                break;
		                case "rightPositionMap":
		                    pixelsToMoveOnX = -50;
		                break;
		                case "topPositionMap":
		                    pixelsToMoveOnY = 50;	
		                break;
		                case "bottomPositionMap":
		                    pixelsToMoveOnY = -50;	
		                break;
		            }
		            $('#imageFullScreen').smartZoom('pan', pixelsToMoveOnX, pixelsToMoveOnY);
		        } 
		    }); */
	}

	
	function onPageUp(pageNo){
		var filePath=document.getElementById("filePath").value;
		if(pageNo==1){
			return;
		}
		onCheck(filePath,pageNo-1);	
	}
	function onPageDown(pageNo){
		var filePath=document.getElementById("filePath").value;
		var listSize=document.getElementById("listSize").value;
		if(pageNo>listSize){
			return;
		}
		onCheck(filePath,pageNo+1);	
	}
	
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		}

	};
	var zNodes;
	var applyNo = document.getElementById("applyNo1").value;
	var taskDefKey = document.getElementById("taskDefKey").value;
	$.ajax({
		type : "post",
		url : "${ctx}/postloan/showImage/showTree?ids=" + applyNo+"&taskDefKey="+taskDefKey,
		dataType : "json",
		success : function(data) {
			//请求成功后处理函数
			zNodes = data;//把后台封装好的简单Json格式赋给treeNodes 
			$(document).ready(function() {
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			});
		}
	});
	//-->
	/* 		var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType : { "Y" : "", "N" : "" }
			},
			//获取json数据
	        async : {  
	            enable : true, 
	            url : "getRuleTime2.action", // Ajax 获取数据的 URL 地址  
	            autoParam : [ "id", "name" ] //ajax提交的时候，传的是id值
	      
	        },  
	        data:{ // 必须使用data  
	            simpleData : {  
	                enable : true,  
	                idKey : "id", // id编号命名   
	                pIdKey : "pId", // 父id编号命名    
	                rootId : 0
	            }  
	  		}, 
	// 回调函数  
	        callback : {  
	            onClick : function(event, treeId, treeNode, clickFlag) {  
	                if(true) {
	                    alert(" 节点id是：" + treeNode.id + ", 节点文本是：" + treeNode.name);      
	                


	                }  
	                
	            },  
	            //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数  
	            onAsyncSuccess : function(event, treeId, treeNode, msg){  
	            	//	alert("调用成功！");
	            	//var nodes=getCheckedNodes(true));
	            	//alert(nodes);
	            },
	            beforeClick: beforeClick,
				onCheck: onCheck
	        }  
		};   */
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

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		//setCheck();

	});

	function getSelectedNodes() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getCheckedNodes(true), v = "";
		var ids = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}

		if (ids.length > 0)
			ids = ids.substring(0, ids.length - 1);
		//alert(ids);
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		//var cityObj = $("#citySel");
		//var cityObjIds = $("#citySelIds");
		//给父窗体updateContact.jsp中所属分类赋值
		//		 window.opener.document.getElementById("cateSelIds").value=ids;
		//		 window.opener.document.getElementById("cateSelName").value=v;
	}

	$(document).ready(function() {

		// Set these DIVs to show for browsers suporting JavaScipt
		$('.gallery_data').css('display', 'block');
		//	$('.gallery_thumbnails').css('width','1000px');
		setFancyBoxLinks();

		 
		// Capture the thumbnail links
		$('div.gallery_thumbnails a').click(function(e) {
			// Disables standard link behavior
			e.preventDefault();
			$('div.gallery_preload_area img').imgpreload(function() {
				setFancyBoxLinks();
				updateThumbnails();
			});

		});

	});

	function setFancyBoxLinks() {
		// Links for Facnybox
		$('.fancybox-buttons').fancybox({
			openEffect  : 'none',
			closeEffect : 'none',
			prevEffect : 'none',
			nextEffect : 'none',
			closeBtn  : false,
			helpers : {
				title : {
					type : 'inside'
				},
				buttons	: {}
			}
		});
	}

	function updateThumbnails() {
		$('.gallery_thumbnails a').each(function(index) {

			if ($('.gallery_preview a').attr('href') == $(this).attr('href')) {
				$(this).addClass('selected');
				$(this).children().fadeTo(250, .4);
			} else {
				$(this).removeClass('selected');
				$(this).children().css('opacity', '1');
			}
		});

	}
</script>
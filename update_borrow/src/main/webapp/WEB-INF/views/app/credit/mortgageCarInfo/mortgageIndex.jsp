<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车辆抵质押物信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		/* 引入车辆、房产、其他抵质押物的div */
			$.loadDiv("car","${ctx }/credit/mortgageCarInfo/list",{applyNo:'${actTaskParam.applyNo}'},"post");
			$.loadDiv("house","${ctx }/credit/mortgageHouseInfo",{applyNo:'${actTaskParam.applyNo}'},"post");
			$.loadDiv("other","${ctx }/credit/mortgageOtherInfo",{applyNo:'${actTaskParam.applyNo}'},"post");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
		function getCheckBoxVal(){
		 	var str = "";
			$("input[name=type]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str += $(this).val() + ",";
				}
			});
			return str;
		}
		
		 //新增
	    
		function add(url, title) {
	        var width = $(document).width()-100;
	    	var height = $(document).height()-50;
			openJBox('', url, title, width, height, {applyNo : '${actTaskParam.applyNo}'});
		}

		//修改
		function edit(type, urlSingle, title) {
	        var width = $(document).width()-100;
	    	var height = $(document).height()-50;
			var $checkLine = $("input[name='" + type + "']:checked");
			var $len = $checkLine.length;
			if ($len != 1) {
				alertx("请选择一条数据");
			} else {
				var url = urlSingle + "?id=" + $checkLine.val();
				openJBox('', url, title, width, height, {applyNo : '${actTaskParam.applyNo}'});
			}
		}
		//删除
		function del(type, url, divName, divUrl) {
			var $checkLine = $("input[name='" + type + "']:checked");
			if (0 == $checkLine.length) {
				alertx("请选择需要删除的数据！");
			} else {
				confirmx("是否删除?", function() {
					delOper(type, url, divName, divUrl);
				});
			}
		}

		function delOper(type, url, divName, divUrl) {
			var $checkLine = $("input[name='" + type + "']:checked");
			if (null != $checkLine && $checkLine.length > 0) {
				var ids = "";
				$checkLine.each(function(v) {
					ids += (this.value + ",");
				});
				$.post(url, {
					"ids" : ids
				}, function(data) {
					if ("success" == data) {
						alertx("删除成功！");
						$.loadDiv(divName, divUrl,{applyNo:'${actTaskParam.applyNo}'}, "post");
					}
				});
			}
		}

		//查看详情
		function details(url, message) {
			var width = $(document).width() - 100;
			var height = $(document).height() - 50;
			openJBox('', url, message, width, height);
		}
	</script>
</head>
<body>
	<div id="car"></div>
	<div id="house"></div>
	<div id="other"></div>
</body>
</html>
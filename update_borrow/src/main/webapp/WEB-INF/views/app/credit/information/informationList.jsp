<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	
	//新增
    function add(urlSingle,title,custName,roleType,applyNo){
        custName = encodeX(custName);
	    roleType = encodeX(roleType);
    	var width = $(top.document).width()-300;
    	width = Math.max(width,1000);
    	var height = $(top.document).height()-200;
    	var url = urlSingle +"&roleType=" + roleType + "&custName="+custName+"&applyNo="+applyNo ;
    	openJBox("", url, title, width, height);
    }
	</script>
	
	<style type="text/css">
		.ft_label{text-align: left;width: 90px;float: right;margin-top: 10px;}
	</style>
</head>
<body>
 	<div id="checkDoubtful">
  <%@ include file="/WEB-INF/views/app/credit/checkDoubtful/checkDoubtfulAllList.jsp"%>
  </div>
   <div id="checkCoupleDouble">
  <%@ include file="/WEB-INF/views/app/credit/checkCoupleDoubtful/checkCoupleDoubtfulAllList.jsp"%>
  </div>
  <div id="checkPhone">
   <%@ include file="/WEB-INF/views/app/credit/checkPhone/checkPhoneAllList.jsp"%>
  </div>
  <div id="checkWeb">
   <%@ include file="/WEB-INF/views/app/credit/checkWeb/checkWebAllList.jsp"%>
  </div> 
</body>
</html>

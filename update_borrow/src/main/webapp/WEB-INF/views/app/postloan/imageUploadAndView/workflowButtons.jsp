<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
//影像上传
	function uploadImage() {
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/postloan/uploadFile/toUploadPage?applyNo=${applyNo}&&contractNo=${contractNo}";
		//openJBox("uploadImageBox", url, "影像上传", windowWidth, height);
		window.open(url,'${applyNo}',"width= 500px" + ",height=250px");
	}
	//查看影像
	function viewImage() {
		var windowWidth = document.body.offsetWidth -50;
		var url ="${ctx}/postloan/showImage/form?applyNo=${applyNo}";
		//openJBox("viewImageBox", url, "查看影像", windowWidth, height);
			//window.showModalDialog(url,null,"dialogWidth:550px;dialogHeight:300px;status:no;help:no;resizable:yes;");
			window.open(url,'${applyNo}');
			//window.location.href=url;
	}

</script>
<div class="ribbon">
	<ul class="layout">
		<li class="mcp_upload"><a href="#" onclick="uploadImage();"><span><b></b>影像上传</span></a></li>
		<li class="mcp_pic"><a href="#" onclick="viewImage();"><span><b></b>影像查阅</span></a></li>
		<li class="mcp_close"><a href="#" onclick="closeJBox()"><span><b></b>关闭</span></a></li>
		<li class="mcp_back"><a href="#" onclick="history.go(-1)"><span><b></b>返回</span></a></li>
	</ul>
</div>
<div id="rs_msg"></div>
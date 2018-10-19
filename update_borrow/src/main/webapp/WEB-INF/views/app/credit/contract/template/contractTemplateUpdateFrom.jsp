<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同模板修改</title>
<meta name="decorator" content="default" />
</head>
<body>
	<form id="webform" name="webform" action="" onsubmit="return SaveDocument();">
		<table border=0 cellspacing='0' cellpadding='0' width="100%">
			<tr>
				<td>
					<font color="RED">注意：只有选择《上传服务器》后，所做的操作才有效！</font>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="上传服务器" class="input-button">
					<input type="button" value="返&nbsp;&nbsp;回" class="input-button" onclick="history.back();">
				</td>
			</tr>
		</table>
		<object id="WebOffice" name="WebOffice" width="100%" height="100%" classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499" codebase="${ctxStatic}/WebOffice/iWebOffice2009.cab#version=10,8,6,14"></object>
	</form>
	<script type="text/javascript">
	var WebOffice = document.getElementById("WebOffice");
	var webform = document.getElementById("webform");
	//作用：载入iWebOffice
	function Load(){
		try{   
		   if(WebOffice.WebReFresh());
		} catch (ex){
	   		alert("您没有安装控件请安装！");
	   		/* document.getElementById("pageDownload").style.display='';
	   		document.getElementById("ceateWebOffice").style.display='none'; */
	     }  

	  try{

	  //以下属性必须设置，实始化iWebOffice
	 <%--  webform.WebOffice.WebUrl="<%=mServerUrl%>";	//WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档，重要文件
	  webform.WebOffice.RecordID="<%=mRecordID%>";	//RecordID:本文档记录编号
	  webform.WebOffice.Template="<%=mRecordID%>";	//Template:模板编号
	  webform.WebOffice.FileName="<%=mFileName%>";	//FileName:文档名称
	  webform.WebOffice.FileType="<%=mFileType%>";	//FileType:文档类型  .doc  .xls  .wps
	  webform.WebOffice.EditType="<%=mEditType%>";	//EditType:编辑类型  方式一、方式二  <参考技术文档>
	  webform.WebOffice.UserName="<%=mUserName%>";	//UserName:操作用户名 --%>

	  WebOffice.WebUrl="${pageContext.request.contextPath}/servlet/webofficeServlet";		//处理页地址，这里用的是相对路径
	  WebOffice.RecordID="${contractTemplate.id}";			//文档编号
	  WebOffice.Template="${contractTemplate.id}";			//模板编号
	  WebOffice.FileName="${contractTemplate.templateName}";	//文件名称
	  WebOffice.FileType=".doc";				//文件类型
	  WebOffice.UserName="test";			//用户名
	  WebOffice.EditType="1,1";				//编辑类型
	  WebOffice.PenColor="#FF0000";			//默认手写笔迹颜色
	  WebOffice.PenWidth="1";					//默认手写笔迹宽度
	  WebOffice.Print="1";					//是否打印批注

	  //以下属性可以不要
	  WebOffice.ShowToolBar="0";		//ShowToolBar:是否显示工具栏:1显示,0不显示
	  WebOffice.ShowMenu="0";		//ShowMenu:1 显示菜单  0 隐藏菜单
	  /*WebOffice.AppendMenu("1","打开本地文件111(&L)");
	  WebOffice.AppendMenu("2","保存本地文件(&S)");
	  WebOffice.AppendMenu("3","-");
	  WebOffice.AppendMenu("4","保存并退出(&E)");
	  WebOffice.AppendMenu("5","-");
	  WebOffice.AppendMenu("6","打印文档(&P)");
	  WebOffice.DisableMenu("宏(&M);选项(&O)...");    //禁止菜单 */

	  WebOffice.WebOpen();			//打开该文档    交互OfficeServer的OPTION="LOADTEMPLATE"
	  WebOffice.ShowType=1;			//文档显示方式  1:表示文字批注  2:表示手写批注  0:表示文档核稿
	  StatusMsg(WebOffice.Status);
	  }catch(e){
		  console.info(e);
	  }
	}

	//作用：退出iWebOffice
	function UnLoad(){
	  try{
	  if (!webform.WebOffice.WebClose()){
	     StatusMsg(webform.WebOffice.Status);
	  }else{
	     StatusMsg("关闭文档...");
	  }
	  }catch(e){}
	}

	//作用：打开文档
	function LoadDocument(){
	  StatusMsg("正在打开文档...");
	  if (!webform.WebOffice.WebLoadTemplate()){  //交互OfficeServer的OPTION="LOADTEMPLATE"
	     StatusMsg(webform.WebOffice.Status);
	  }else{
	     StatusMsg(webform.WebOffice.Status);
	  }
	}

	//作用：刷新文档
	function WebReFresh(){
	  webform.WebOffice.WebReFresh();
	  StatusMsg("文档已刷新...");
	}

	//作用：保存文档
	function SaveDocument(){
		StatusMsg("开始上传服务器...");
	  webform.WebOffice.WebClearMessage();            //清空iWebOffice变量
		/* var fileNameTemp = document.getElementById("FileName").value;
		if(fileNameTemp==null || fileNameTemp==""){
			alert("模板名称不能为空！");
			return false;
		} */
	   /*  if (!webform.WebOffice.WebSaveBookMarks()){    //交互OfficeServer的OPTION="SAVEBOOKMARKS"
	      StatusMsg(webform.WebOffice.Status);
	      return false;
	    } */
	  //webform.WebOffice.WebSetMsgByName("MyDefine1","自定义变量值1");  //设置变量MyDefine1="自定义变量值1"，变量可以设置多个  在WebSaveTemplate()时，一起提交到OfficeServer中
	 //	alert(webform.WebOffice.WebSaveTemplate(true));
	  if (!webform.WebOffice.WebSaveTemplate(true)){    //交互OfficeServer的OPTION="SAVETEMPLATE"，参数true表示保存OFFICE文档
	     StatusMsg(webform.WebOffice.Status);
	     return false;
	  }else{
	    StatusMsg(webform.WebOffice.Status);
	     return false;
	  }
	}
	//作用：显示操作状态
	function StatusMsg(mString){
	  //alertx(mString);
	}
	function WebOpenLocal(){
		  try{
		    webform.WebOffice.WebOpenLocal();
		    StatusMsg(webform.WebOffice.Status);
		  }catch(e){}
		}
	$(document).ready(function() {
		Load();
	});
	</script>
</body>
</html>
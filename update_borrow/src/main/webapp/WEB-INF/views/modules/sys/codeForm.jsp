<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>机构码添加</title>
	<meta name="decorator" content="default"/> 
	<script type="text/javascript">
	function showCode(){
		top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData")+"&module=${module}&checked=${checked}&extId=${extId}&isAll=${isAll}", "选择机构", 300, 420, {
			ajaxData:{selectIds: $("#officeId").val()},buttons:{"确定":"ok","取消":'cancel',"关闭":true}, submit:function(v, h, f){
				if (v=="ok"){
					var tree = h.find("iframe")[0].contentWindow.tree;
					var ids = [], names = [], nodes = [],orgCodes = [],types = [],grades = [];
					if ("${checked}" == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
						if (nodes[i].isParent){
							continue; // 如果为复选框选择，则过滤掉父节点
						}//</c:if><c:if test="${notAllowSelectRoot}">
						if (nodes[i].level == 0){
							top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}//</c:if><c:if test="${notAllowSelectParent}">
						if (nodes[i].isParent){
							top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}//</c:if><c:if test="${not empty module && selectScopeModule}">
						if (nodes[i].module == ""){
							top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
							return false;
						}else if (nodes[i].module != "${module}"){
							top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
							return false;
						}//</c:if>
						ids.push(nodes[i].id);
						names.push(nodes[i].name);
						orgCodes.push(nodes[i].code);
						types.push(nodes[i].type);
						grades.push(nodes[i].grade)//<c:if test="${!checked}">
						break; // 如果为非复选框选择，则返回第一个选择  </c:if>
					}
					$("#officeId").val(ids.join(",").replace(/u_/ig,""));
					$("#officeName").val(names.join(","));
					 $("#nameValue").val(orgCodes.join(","));
					$("#nameValue").attr("readonly","readonly");
					validateCode();	 
				}
				else if (v=="cancel"){
					$("#nameValue").val("");
					$("#officeName").val("");
					$("#btnSubmit").removeAttr("disabled");;
	            }
				if(typeof $officeTreeselectCallBack == 'function'){
					$officeTreeselectCallBack(v, h, f);
				}
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
		
	}
	
	
	function validateCode(){
		var namevalue = $("#nameValue").val();
		if(namevalue != null && namevalue != ''){
		 $.post("${ctx}/sy/code/validate",{id:namevalue},function(result){
			    if(result.status == "1" || result.status == 1){
			    	alertx("该机构对应关系已存在！");
			    	$("#btnSubmit").attr("disabled", "disabled");
			    }else{
			    	$("#btnSubmit").removeAttr("disabled");
			    }
		 });}
		
	}
	function save(){
		var namevalue = $("#nameValue").val();
		var type = $("#type").val();
		var grade = $("#grade").val();
		var codeValue = $("#codeValue").val();
		if(namevalue == null || namevalue == ""){
			alertx("机构和机构码不能为空");
		}else{
			
				$.ajax({
		            type: "POST",
		            url:"${ctx}/sy/code/save",
		            data:$('#inputForm').serialize(),
		            async: false,
		            error: function(request) {
		            	alertx("后台错误，请联系管理员");
		            },
		            success: function(data) {
		                if(data.status == '1' || data.status == 1){
		                	alertx(data.message, function() {
		                		window.location.href="${ctx}/sy/code/list";
							});

		                }else{
		                	alertx(data.message);
		                }
		            }
		        });
			
			
		}
		
	}
	</script>
	<style type="text/css">
.span4Tree {
	width : 240px;
			}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sy/code/list">码值查询</a></li>
		<li class="active"><a href="${ctx}/sy/code/creForm?id=${creCode.id}">码值新增</a></li>
	</ul><br/>
	<form:form id="inputForm"  modelAttribute="code" action="#" method="post" class="form-horizontal">
		<%-- <form:hidden path="id"/> --%>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">对应机构：</label>
			
				<div class="controls">
				<input id="officeId" name="parent.id" class="span4Tree" value="" type="hidden">
				<input id="officeName" name="parent.name" readonly="readonly" value="" data-msg-required="" class="input-xlarge" style="" type="text"><a id="officeButton" onclick="showCode();" href="javascript:" class="btn  " style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>
				<font color="red">*</font> </span> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构码：</label>
			<div class="controls">
				<form:input id = "nameValue" value="${cre.id}" onchange="validateCode()" path="id" htmlEscape="false" maxlength="500" class="input-xlarge required"/> 
				<input type="hidden" id = "codeValue" name ="orgValue" value="${cre.orgValue }">
				<input type="hidden" id = "type" name ="type" >
				<input type="hidden" id="grade" name = "grade">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
		<!-- <input id="btnSubmit" class="btn btn-primary" type="button" onclick="save()" value="保 存"/> -->
			 <shiro:hasPermission name="sys:code:edit"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="save()" value="保 存"/></shiro:hasPermission> &nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>

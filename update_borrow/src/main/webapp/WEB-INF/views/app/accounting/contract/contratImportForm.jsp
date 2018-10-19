<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财报导入</title>
	<meta name="decorator" content="default" />
	<script type="text/javascript">
        $(document).ready(function() {
        });

        function importSubmit(formId){
            loading('Loading...');
            $("#"+formId).submit();
        }

        //导入文件
        function importExp111() {

            var formData = new FormData();
            var name = $("#upload").val();
            formData.append("file",$("#upload")[0].files[0]);
            $.ajax({
                url : "${ctx}/credit/creGedImportGetOrder/upload",
                type : 'POST',
                async : false,
                data : formData,
                // 告诉jQuery不要去处理发送的数据
                processData : false,
                // 告诉jQuery不要去设置Content-Type请求头
                contentType : false,
                beforeSend:function(){
                    console.log("正在进行，请稍候");
                },
                success : function(data) {
                    if (data) {
                        if (data.status == 1) {//保存成功
                            alertx(data.message);
                        } else {
                            alertx(data.message);
                        }
                    }
                },
                error:function () {
                    alertx("出现异常");
                }
            });
        }

	</script>
</head>
<body>
<style>
	.textopacity {
		position: absolute;
		top: 0;
		left: 0;
		font-size: 100px;
		cursor: pointer;
		opacity: 0;
		filter: alpha(opacity = 0);
	}

	.afile {
		position: relative;
	}
</style>
<div class="searchInfo">
	<div class="searchCon">
		<form:form  id="contractImportId" modelAttribute="contractImport" action="${ctx}/accounting/Acccontract/upload" enctype="multipart/form-data" method="post" class="form-horizontal">
			<h3 class="searchTitle">合同数据导入</h3>
			<sys:message content="${message}" />
			<div class="filter">
				<table class="table table-striped table-bordered table-condensed">
					<ul class="ul-form">
						<tr>
							<td class="ft_label" style="width:20%">合同文件：</td>
							<td class="ft_content" style="width:50%">
								<input type="text" id="fileContract" name="fileContract" readonly="readonly" class="input-medium" style="width:90%" />
							</td>
							<td class="ft_content" style="width:30%">
								<a href="javascript:void(0);" class="afile">
									<input type="button" class="btn"  name="input" readonlu="readonly" value="选择">
									<input type="file" name="uploadContract" id="uploadContract" class="textopacity" onchange="document.getElementById('fileContract').value=this.value" style="width:100%">
								</a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">导入状态：</td>
							<td class="ft_content">
								<span id="inputStatus" style="color: red;">${errorMessage}</span>
							</td>
							<td class="ft_content" style="width:50%">
								<input type="button" class="btn"  name="input" value="导入" onclick="importSubmit('contractImportId')" />
							</td>
						</tr>
					</ul>
				</table>
			</div>
		</form:form>
	</div>
</div>
<div class="searchInfo">
	<div class="searchCon">
		<form:form id="repayPlanImportId" imodelAttribute="repayPlanImport" action="${ctx}/accounting/repayPlan/upload" enctype="multipart/form-data" method="post" class="form-horizontal">
			<h3 class="searchTitle">还款计划数据导入</h3>
			<sys:message content="${message}" />
			<div class="filter">
				<table class="table table-striped table-bordered table-condensed">
					<ul class="ul-form">
						<tr>
							<td class="ft_label" style="width:20%">合同文件：</td>
							<td class="ft_content" style="width:50%">
								<input type="text" id="fileRepay" name="fileRepay" readonly="readonly" class="input-medium" style="width:90%" />
							</td>
							<td class="ft_content" style="width:30%">
								<a href="javascript:void(0);" class="afile">
									<input type="button" class="btn"  name="input" readonlu="readonly" value="选择">
									<input type="file" name="uploadRepay" id="uploadRepay" class="textopacity" onchange="document.getElementById('fileRepay').value=this.value" style="width:100%">
								</a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">导入状态：</td>
							<td class="ft_content">
								<span id="inputStatus" style="color: red;">${errorMessageRepay}</span>
							</td>
							<td class="ft_content" style="width:50%">
								<input type="button" class="btn"  name="input" value="导入" onclick="importSubmit('repayPlanImportId')" />
							</td>
						</tr>
					</ul>
				</table>
			</div>
		</form:form>
	</div>
</div>
<div class="searchInfo">
	<div class="searchCon">
		<form:form id="staContractStatusImportId" modelAttribute="staContractStatusImport" action="${ctx}/accounting/staContractStatus/upload" enctype="multipart/form-data" method="post" class="form-horizontal">
			<h3 class="searchTitle">合同执行汇总数据导入</h3>
			<sys:message content="${message}" />
			<div class="filter">
				<table class="table table-striped table-bordered table-condensed">
					<ul class="ul-form">
						<tr>
							<td class="ft_label" style="width:20%">合同文件：</td>
							<td class="ft_content" style="width:50%">
								<input type="text" id="fileSta" name="fileSta" readonly="readonly" class="input-medium" style="width:90%" />
							</td>
							<td class="ft_content" style="width:30%">
								<a href="javascript:void(0);" class="afile">
									<input type="button" class="btn"  name="input" readonlu="readonly" value="选择">
									<input type="file" name="uploadSta" id="uploadSta" class="textopacity" onchange="document.getElementById('fileSta').value=this.value" style="width:100%">
								</a>
							</td>
						</tr>
						<tr>
							<td class="ft_label">导入状态：</td>
							<td class="ft_content">
								<span style="color: red;">${errorMessageSta}</span>
							</td>
							<td class="ft_content" style="width:50%">
								<input type="button" class="btn"  name="input" value="导入" onclick="importSubmit('staContractStatusImportId')" />
							</td>
						</tr>
					</ul>
				</table>
			</div>
		</form:form>
	</div>
</div>

<!--冠e通线下数据导入-->


<div class="searchInfo">
	<div class="searchCon">
		<h3 class="searchTitle">冠e通线下数据导入</h3>
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">文件导入：</td>
				<td class="ft_content">
					<input type="text" id="upfile" name="upfile" readonly="readonly" class="input-medium" style="width:90%" />
				</td>
				<td class="ft_content" style="width:150px">
					<a href="javascript:void(0);" class="afile">
						<input type="file" name="upload" id="upload" class="textopacity"  onchange="document.getElementById('upfile').value=this.value">
						<input type="button" class="btn btn-primary" id="inputbtn" name="input" readonlu="readonly"  value="选择" >
					</a>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center"><input type="button" class="btn btn-primary"  onclick="importExp111()" readonlu="readonly"  value="导  入" /></td>
			</tr>
		</table>
	</div>
</div>





</body>
</html>
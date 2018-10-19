<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>冠e通录入接口信息管理</title>
	<meta name="decorator" content="default" />
	<script type="text/javascript">
        $(document).ready(function() {
			/* adjustTextareaLength('intrOfMainborrower', 'preIntrOfMainborrower'); */
            adjustTextareaLength('intrOfCompany', 'preIntrOfCompany');
            adjustTextareaLength('mixLoanUsage', 'preMixLoanUsage');
            adjustTextareaLength('intrOfComProduction', 'preIntrOfComProduction');
            adjustTextareaLength('operateActuality', 'preOperateActuality');
            adjustTextareaLength('other', 'preOther');
            adjustTextareaLength('sourceOfDepayment1', 'preSourceOfDepayment1');
            adjustTextareaLength('sourceOfDepayment2', 'preSourceOfDepayment2');
            adjustTextareaLength('sourceOfDepayment3', 'preSourceOfDepayment3');
            adjustTextareaLength('sourceOfDepayment4', 'preSourceOfDepayment4');
            adjustTextareaLength('auditOpintion', 'preAuditOpintion');

            adjustTextareaLength('borrowAndMatePunish', 'preBorrowAndMatePunish');
            adjustTextareaLength('borrowInvolveInfo', 'preBorrowInvolveInfo');
            adjustTextareaLength('borrowCrimaAdminPunish', 'preBorrowCrimaAdminPunish');
            adjustTextareaLength('borrowNewLoanBlance', 'preBorrowNewLoanBlance');
            if('${companyInfo.isAddrResi}' == "1"){
                var isAddrResi = document.getElementById("radio_comYes");
                isAddrResi.checked = "checked";
            }
            if('${companyInfo.isAddrResi}' == "0"){
                var isAddrResi = document.getElementById("radio_comNo");
                isAddrResi.checked = "checked";
            }
            if('${custType}' == "2"){
                $(".personCustType").hide();
            }
            $("#inputForm").validate({
                submitHandler : function(form) {
                    saveForm();
                },
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error, element);
                }
            });
            $("#warehouse").hide();
            $("#comId").hide();
            if ('${custType}' == "2") {//客户类型为企业
                $("#comId").show();
            }
            if (!checkIsNull('${productType}')) {
                if ('${productType}' == '1') {//信用
                    //	$("#intrOfComProductionId").hide();
                    $("#equip").hide();
                    $("#car").hide();
                    $("#house").hide();
                    $("#gqgetGuarantorCompanyInfo").hide();
                    $("#operateActualityId").hide();
                    $("#propertyType").show();
                } else if ('${productType}' == '2') {//抵押
                    $("#operateActualityId").hide();
                    //	$("#intrOfComProductionId").hide();
                    $("#gqgetGuarantorCompanyInfo").hide();
                    $("#otherId").hide();
                    $("#assetCar").hide();
                    $("#assetHouse").hide();
					/* $("#overallRanking").removeClass("required"); */
                    $("#isLoan").removeClass("required");
                    $("#isOverdue").removeClass("required");
                    $("#sourceOfCreRegist").removeClass("required");
                    var a = document.getElementById("zcxi");
                    $(a).html("抵押信息")
                    $("#propertyType").hide();
                } else if ('${productType}' == '4') {//混合
					/* $("#mainBorrowerId").hide(); */
                    $("#otherId").hide();
                    $("#assetCar").hide();
                    $("#assetHouse").hide();
					/* $("#overallRanking").removeClass("required"); */
                    $("#propertyType").hide();
                    var a = document.getElementById("zcxi");
                    $(a).html("抵押信息")
                } else if ('${productType}' == '7') {
                    $("#operateActualityId").hide();
                    $("#house").hide();
                    $("#car").hide();
                    $("#warehouse").show();
                    $("#comId").show();
                    $("#equip").hide();
                    $("#gqgetGuarantorCompanyInfo").hide();
                }
            }

            $("#otherId").hide();
            $("#assetCar").hide();
            $("#assetHouse").hide();
            <%--if (!checkIsNull('${productType}')) {--%>
                <%--if ('${productType}' == '1' || '${productType}' == '7' ) {--%>
                    <%--if (!checkIsNull('${gqgetComInfo.propertyHouse}') && '${gqgetComInfo.propertyHouse}' == '1') {--%>
                        <%--$("#propertyHouseRadio").val("1");--%>
                        <%--$("#assetHouse").show();--%>
                    <%--}--%>
                    <%--if (!checkIsNull('${gqgetComInfo.propertyCar}') && '${gqgetComInfo.propertyCar}' == '1') {--%>
                        <%--$("#propertyCarRadio").val("1");--%>
                        <%--$("#assetCar").show();--%>
                    <%--}--%>
                    <%--if (!checkIsNull('${gqgetComInfo.propertyOther}') && '${gqgetComInfo.propertyOther}' == '1') {--%>
                        <%--$("#propertyOtherRadio").val("1");--%>
                        <%--$("#otherId").show();--%>
                    <%--}--%>
                <%--}--%>
            <%--}--%>

            <%--$.loadDiv("gqgetGuarantorInfoList", "${ctx }/credit/guarantorInfo/list?gqgetFlag=2", {--%>
                <%--applyNo : '${actTaskParam.applyNo}'--%>
            <%--}, "post");--%>
            <%--$.loadDiv("gqgetGuarantorCompanyInfo", "${ctx }/credit/gqgetGuarantorCompany/list", {--%>
                <%--applyNo : '${actTaskParam.applyNo}'--%>
            <%--}, "post");--%>
            <%--$.loadDiv("equip", "${ctx }/credit/gqgetComInfo/mortEquip", {--%>
            <%--applyNo : '${actTaskParam.applyNo}'--%>
            <%--}, "post");--%>








            <%--$.loadDiv("house", "${ctx }/credit/mortgageHouseInfo/list?gqgetFlag=2", {--%>
                <%--applyNo : '${actTaskParam.applyNo}'--%>
            <%--}, "post");--%>
            $.loadDiv("assetHouse", "${ctx }/credit/gqgetAssetHouse/list", {
                applyNo : '${actTaskParam.applyNo}'
            }, "post");
            <%--$.loadDiv("car", "${ctx }/credit/mortgageCarInfo/list?gqgetFlag=2", {--%>
                <%--applyNo : '${actTaskParam.applyNo}'--%>
            <%--}, "post");--%>
            $.loadDiv("assetCar", "${ctx }/credit/gqgetAssetCar/list", {
                gqgetApplyNo : '${actTaskParam.applyNo}'
            }, "post");

            $.loadDiv("warehouse", "${ctx }/credit/gqgetComInfo/warehouseList", {
                applyNo : '${actTaskParam.applyNo}'
            }, "post");



			if (!checkIsNull('${gqgetComInfo.propertyHouse}') && '${gqgetComInfo.propertyHouse}' == '1') {
				$("#propertyHouseRadio").val("1");
				$("#assetHouse").show();
			}
			if (!checkIsNull('${gqgetComInfo.propertyCar}') && '${gqgetComInfo.propertyCar}' == '1') {
				$("#propertyCarRadio").val("1");
				$("#assetCar").show();
			}
			if (!checkIsNull('${gqgetComInfo.propertyOther}') && '${gqgetComInfo.propertyOther}' == '1') {
				$("#propertyOtherRadio").val("1");
				$("#otherId").show();
			}

        });

        function saveForm() {
            loading();
            top.$.jBox.tip.mess = null;
            var formJson = $("#inputForm").serializeJson();

            $.post("${ctx}/credit/gqgetComInfo/zichanSave", formJson, function(data) {
                if (data) {
                    closeTip();
                    if (data.status == 1) {//保存成功
                        alertx(data.message, function() {
//                            var id = data.bankLoanId;
//                            $("#bankLoanId").val(id);
                        });
                    } else {
                        alertx(data.message);
                    }
                }
            });
        }

        function addCom() {
            openJBox('addComBox', "${ctx}/credit/gqgetGuarantorCompany/form", "新增担保企业信息", $(window).width() - 70, 600, {
                applyNo : $("#applyNo").val()
            });

        }

        function addAssetCar() {
            openJBox('addAssetCarBox', "${ctx}/credit/gqgetAssetCar/form", "新增车辆资产信息", $(window).width() - 400, 250, {
                gqgetApplyNo : $("#applyNo").val()
            });

        }
        function editCom() {
            var id = getCheckedIds('comCheck');
            if (id.length < 1) {
                alertx("请选择一条数据");
            } else {
                openJBox('editComBox', "${ctx}/credit/gqgetGuarantorCompany/form", "编辑担保企业信息", $(window).width() - 70, 600, {
                    id : id[0]
                });
            }
        }
        function editAssetCar() {
            var id = getCheckedIds('carCheck');
            if (id.length < 1) {
                alertx("请选择一条数据");
            } else {
                openJBox('editAssetCarBox', "${ctx}/credit/gqgetAssetCar/form", "编辑车辆资产信息", $(window).width() - 400, 250, {
                    id : id[0]
                });
            }
        }
        function queryCom(id) {
            openJBox('queryComBox', "${ctx}/credit/gqgetGuarantorCompany/form?readOnly=true", "编辑担保企业信息", $(window).width() - 70, 600, {
                id : id
            });
        }
        function queryAssetCar(id) {
            openJBox('queryAssetCarBox', "${ctx}/credit/gqgetAssetCar/form?readOnly=true", "编辑车辆资产信息", $(window).width() - 400, 300, {
                id : id
            });
        }
        function deleteCom() {
            var ids = getCheckedIds('comCheck');
            if (1 > ids.length) {
                alertx("请选择需要删除的数据！");
            } else {
                confirmx("是否删除该信息?", function() {
                    $.post("${ctx}/credit/gqgetGuarantorCompany/delete", {
                        id : ids[0]
                    }, function(data) {
                        if (data.status == '1') {
                            alertx(data.message, function() {
                                $.loadDiv("gqgetGuarantorCompanyInfo", "${ctx }/credit/gqgetGuarantorCompany/list", {
                                    applyNo : $("#applyNo").val()
                                }, "post");
                            });
                        } else {
                            alertx(data.message);
                        }
                    });
                });
            }
        }
        function deleteAssetCar() {
            var ids = getCheckedIds('carCheck');
            if (1 > ids.length) {
                alertx("请选择需要删除的数据！");
            } else {
                confirmx("是否删除该资产?", function() {
                    $.post("${ctx}/credit/gqgetAssetCar/delete", {
                        id : ids[0]
                    }, function(data) {
                        if (data.status == '1') {
                            alertx(data.message, function() {
                                $.loadDiv("assetCar", "${ctx }/credit/gqgetAssetCar/list", {
                                    gqgetApplyNo : '${actTaskParam.applyNo}'
                                }, "post");
                            });
                        } else {
                            alertx(data.message);
                        }
                    });
                });
            }
        }
        function changePropertyHouse(property) {
            if ('1' == property) {//已选中，取消
                $("#propertyHouseRadio").attr("checked", false);
                $("#propertyHouseRadio").val("0");
                $("#propertyHouse").val("0");
                $("#assetHouse").hide();
            } else {
                $("#propertyHouseRadio").val("1");
                $("#assetHouse").show();
                $("#propertyHouse").val("1");
            }
        }
        function changePropertyCar(property) {
            if ("1" == property) {//已选中，取消
                $("#propertyCarRadio").attr("checked", false);
                $("#propertyCarRadio").val("0");
                $("#propertyCar").val("0");
                $("#assetCar").hide();
            } else {
                $("#propertyCarRadio").val("1");
                $("#assetCar").show();
                $("#propertyCar").val("1");
            }
        }
        function changePropertyOther(property) {
            if ("1" == property) {//已选中，取消
                $("#propertyOtherRadio").attr("checked", false);
                $("#propertyOtherRadio").val("0");
                $("#propertyOther").val("0");
                $("#otherId").hide();
            } else {
                $("#propertyOtherRadio").val("1");
                $("#otherId").show();
                $("#propertyOther").val("1");
            }
        }
        function checkProperty() {
            if (!checkIsNull('${productType}')) {
                if ('${productType}' == '1') {
                    if (!($("#propertyHouse").val() == '1' || $("#propertyCar").val() == '1' || $("#propertyOther").val() == '1')) {
                        alertx("资产信息必填");
                        return false;
                    }
                }
            }
        }
	</script>
	<c:if test="${true == readOnly}">
		<!-- 查看详细信息时生效 -->
		<script type="text/javascript">
            $(document).ready(function() {
                $("input").attr("readOnly", "readOnly");
                $("textarea").attr("readOnly", "readOnly");
                disableSelect2();
                $("div[class='searchButton']").remove();
                $("font").remove();//由于页面的特殊性，所以这里直接将所有的fongt节点删除
            });
		</script>
	</c:if>
</head>
<body>
<form:form id="inputForm" modelAttribute="gqgetComInfo" action="${ctx}/credit/gqgetComInfo/zichanSave" method="post" class="form-horizontal">
	<form:hidden path="id" />
	<form:hidden path="applyNo" value="${gqgetComInfo.applyNo}" />
	<%--<form:hidden path="productType" value="${productType}" />--%>
	<sys:message content="${message}" />
	<!-- <pre class="textareaWidth pre-style" id="preIntrOfMainborrower"></pre> -->
	<pre class="textareaWidth pre-style" id="preIntrOfCompany"></pre>
	<pre class="textareaWidth pre-style" id="preOperateActuality"></pre>
	<pre class="textareaWidth pre-style" id="preMixLoanUsage"></pre>
	<pre class="textareaWidth pre-style" id="preIntrOfComProduction"></pre>
	<pre class="textareaWidth pre-style" id="preOther"></pre>
	<pre class="textareaWidth pre-style" id="preSourceOfDepayment1"></pre>
	<pre class="textareaWidth pre-style" id="preSourceOfDepayment2"></pre>
	<pre class="textareaWidth pre-style" id="preSourceOfDepayment3"></pre>
	<pre class="textareaWidth pre-style" id="preSourceOfDepayment4"></pre>
	<pre class="textareaWidth pre-style" id="preAuditOpintion"></pre>

	<pre class="textareaWidth pre-style" id="preBorrowAndMatePunish"></pre>
	<pre class="textareaWidth pre-style" id="preBorrowInvolveInfo"></pre>
	<pre class="textareaWidth pre-style" id="preBorrowCrimaAdminPunish"></pre>
	<pre class="textareaWidth pre-style" id="preBorrowNewLoanBlance"></pre>

	<!-- 信用借款 -->
	<div class="searchInfo" id="creditDiv" style="min-height: 500px">
		<%--<h3 class="searchTitle">新增标的信审信息</h3>--%>
		<%--<div class="searchCon"></div>--%>
		<%--<div id="comId"></div>--%>
			<input type="hidden" name="applyNo" value="${actTaskParam.applyNo}" >
		<div class="searchInfo" id="creditDiv">
			<h3 id="zcxi" class="searchTitle">资产信息</h3>
			<h3 class="tableTitle" id="propertyType">
				<input type="radio" name="propertyHouseRadio" value="0" id="propertyHouseRadio" onclick="changePropertyHouse(this.value);" <c:if test="${gqgetComInfo.propertyHouse==1}">checked</c:if>>
				<label for="propertyHouseRadio">有房</label>
				<input type="hidden" id="propertyHouse" name="propertyHouse" class="input-medium" value="${gqgetComInfo.propertyHouse}" />
				<input type="radio" name="propertyCarRadio" value="0" id="propertyCarRadio" onclick="changePropertyCar(this.value);" <c:if test="${gqgetComInfo.propertyCar==1}">checked</c:if>>
				<label for="propertyCarRadio">有车</label>
				<input type="hidden" id="propertyCar" name="propertyCar" class="input-medium" value="${gqgetComInfo.propertyCar}" />
				<input type="radio" name="propertyOtherRadio" value="0" id="propertyOtherRadio" onclick="changePropertyOther(this.value);" <c:if test="${gqgetComInfo.propertyOther==1}">checked</c:if>>
				<label for="propertyOtherRadio">其他</label>
				<input type="hidden" id="propertyOther" name="propertyOther" class="input-medium" value="${gqgetComInfo.propertyOther}" />
				<font style="color: red">*</font>
			</h3>
			<div class="searchCon">
				<div id="house"></div>
				<div id="assetHouse"></div>
				<div id="car"></div>
				<div id="assetCar"></div>
				<div id="otherId">
					<h3 class="searchTitle">其他资产</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">
								<font style="color: red">*</font>
								其他：
							</td>
							<td class="ft_content">
								<form:textarea path="other"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberTwenty" onkeyup="adjustTextareaLength('other', 'preOther');" />
								<br>
								<font style="color: red">最少输入20字，最多可输入1000字</font>
							</td>
						</tr>
					</table>
				</div>
				<div id="equip"></div>
				<div id="warehouse"></div>
			</div>
		</div>
		<div id="gqgetGuarantorInfoList"></div>
		<div id="gqgetGuarantorCompanyInfo"></div>
		<%--<div class="searchInfo" id="creditDiv"></div>--%>
		<%--<div class="searchInfo" id="creditDiv"></div>--%>
	</div>
	<div class="searchButton">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return checkProperty();" />
	</div>
</form:form>
</body>
</html>
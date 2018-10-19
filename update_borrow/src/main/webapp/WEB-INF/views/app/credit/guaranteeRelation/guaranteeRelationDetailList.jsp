<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>批量借款企业 </title>
</head>
<script type="text/javascript">
    $(document).ready(function() {
        if ('${readOnly}' == '0') {
            setPageReadOnly();
        }
    });
    // -100
    var width = $(window).width() - 100;
    //新增担保企业信息  不用这个
    function addGuarantorCompany() {
        var url = "${ctx}/credit/creApplyCompanyRelation/jumpCompanyInfo";
        openJBox("companyInfo-form", url, "新增批量借款企业信息", width, 600, {
            applyNo : $("#applyNo").val()
        });
    }


    //新增批量借款企业  这是现在使用的
    function ApplyCompanyRelation(){
		var applyNo = $("#applyNo").val();
		var taskDefKey = '${taskDefKey}' ;

        var url = "${ctx}/credit/creApplyCompanyRelation/compinySave";
        openJBox("companfo-form", url, "新增批量借款企业信息", width, 600, {
            applyNo : applyNo

        });




    }

    // 批量借款企业与担保公司 担保人 等的关联
    function luruGuarantorCompany(cust_id){
        var applyNo = $("#applyNo").val();
        /*   弹窗样式显示不行
        var url = "${ctx}/credit/creApplyCompanyRelation/jump?applyNo="+applyNo+"&custId="+cust_id;
        openJBox("companyInm", url, "新增批量借款企业信息", width, 600, {
            applyNo : $("#applyNo").val()
        });*/

        window.location.href="${ctx}/credit/creApplyCompanyRelation/jump?" +
            "applyNo="+applyNo+"&custId="+cust_id+"&readOnly="+"${readOnly}" + "&taskDefKey=" + "${taskDefKey}";
    }


    //删除担保企业信息
    function deleteGuarantorCompany() {
        var $checkLine = $("input[name='guarantorCompanyIds']:checked");
        var $len = $checkLine.length;
        if ($len < 1) {
            alertx("请选择要删除的借款企业");
        } else {
            var checkedValue = getCheckedValue("guarantorCompanyIds");
            var applyNo = $("#applyNo").val();
            var url = "${ctx}/credit/creApplyCompanyRelation/deleteCompanyList?ids=" + checkedValue +"&&applyNo="+applyNo;
            confirmx('确认要删除勾选的借款企业信息吗？', function() {
                saveJson(url, null, function(data) {
                    if (data) {
                        if (data.status == 1) {

                            alertx(data.message);
                            $.loadDiv("guarantorCompanyInfo", "${ctx}/credit/creApplyCompanyRelation/queryCompanyList", {
                                applyNo : $("#applyNo").val()
                            }, "post");

                        } else {
                            alertx(data.message);
                        }

                    }
                });
            });
        }
    }

    //详情
    function detailGuarantorCompany(companyId) {
        var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId=" + companyId
            + "&&readOnly=0&&applyNo="+$("#applyNo").val();
        openJBox("companyInfo-detai", url, "借款企业信息详情", width, 600, null);
    }

    //编辑
    function editGuarantorCompany() {
        var $checkLine = $("input[name='guarantorCompanyIds']:checked");
        var $len = $checkLine.length;
        if ($len != 1) {//需要勾选一条信息进行修改
            alertx("请选择一条借款企业信息！");
        } else {
            var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId="
                + $checkLine.val() + "&&readOnly=1&&applyNo="+$("#applyNo").val();
            openJBox("companyInfo-detai", url, "编辑借款企业信息", width, 600, null);
        }

    }

    function guaranB(){
        $("#guaranBId").toggle(600);
    }
</script>
<body>
<div class="tableList">
    <sys:message content="${message}" />
    <div id="tableDataId" style="max-height: 400px; overflow: auto;">
        <div class="searchInfo">
            <h3 onclick="guaranB()" class="searchTitle">批量借款企业列表</h3>
            <div id="guaranBId" class="ribbon filter">
                <ul class="layout">
                    <c:if test="${readOnly == false}" >
                        <li class="add">
                            <a id="add" href="javascript:void(0);" onclick="ApplyCompanyRelation();">
								<span>
									<b></b>
									新增
								</span>
                            </a>
                        </li>
                        <li class="edit">
                            <a id="edit" href="javascript:void(0);" onclick="editGuarantorCompany();">
								<span>
									<b></b>
									编辑
								</span>
                            </a>
                        </li>
                        <li class="delete">
                            <a id="delete" href="javascript:void(0);" onclick="deleteGuarantorCompany();">
								<span>
									<b></b>
									删除
								</span>
                            </a>
                        </li>

                    </c:if>

                </ul>
                <table id="contentTable"
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th width="3%"><input type="checkbox" onclick="allCheck('guarantorCompanyCheckAll','guarantorCompanyIds')"
                                              name="guarantorCompanyCheckAll" id="guarantorCompanyCheckAll" /></th>
                        <th width="3%">序号</th>
                        <th width="10%">工商登记名称</th>
                        <th width="10%">组织机构代码</th>
                        <th width="10%">统一社会信用代码</th>
                        <th width="10%">经营期限</th>
                        <th width="10%">注册资金(元)</th>
                        <th width="10%">成立时间</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${companyInfoList}" var="companyInfo"
                               varStatus="companyInfoList">
                        <tr>
                            <td><input type="checkbox" id="guarantorCompanyIds"
                                       name="guarantorCompanyIds" value="${companyInfo.id }"/></td>
                            <td>${companyInfoList.index+1 }</td>
                            <td>${companyInfo.busiRegName}</td>
                            <td>${companyInfo.organizationNo}</td>
                            <td>${companyInfo.unSocCreditNo }</td>
                            <td>${companyInfo.operatePeriod }</td>
                            <td><fmt:formatNumber value="${companyInfo.registerCapital }" pattern="###,##0.00"></fmt:formatNumber></td>
                            <td><fmt:formatDate value="${companyInfo.foundDate}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <a href="javascript:void(0)" onclick="detailGuarantorCompany('${companyInfo.id}')">详情</a>

                                <c:choose>
                                    <c:when test="${readOnly == true}">
                                        <a href="javascript:void(0)" onclick="luruGuarantorCompany('${companyInfo.id}')">查看担保关系</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:void(0)" onclick="luruGuarantorCompany('${companyInfo.id}')">建立担保关系</a>
                                    </c:otherwise>
                                </c:choose>

                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>

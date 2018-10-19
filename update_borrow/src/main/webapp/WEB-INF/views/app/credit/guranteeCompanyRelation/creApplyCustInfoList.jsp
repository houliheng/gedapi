<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>担保人选择</title>
    <meta name="decorator" content="default" />
    <%@ include file="/WEB-INF/views/include/cityselectJS.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {


        });

        // 添加要跟订单关联的担保公司信息
        function  saveCompany(){

            var applyNo  = $("#applyNo").val();
            var piliId = "${piliId}";

            var $checkLine = $("input[name='guarantorIds']:checked");
            var $len = $checkLine.length;
            if ($len < 1) {
                alertx("请选择要担保的担保人信息");
                return false;
            }else {
                var checkedValue = getCheckedValue("guarantorIds");
                 $.ajax({
                    url:"${ctx}/credit/creApplyCompanyRelation/saveCustInfoList",
                     data:{
                         ids : checkedValue,
                         applyNo : applyNo,
                         piliId : piliId
                     },
                     type : "post",
                     success : function (data) {
                          if(data.status == '1'){
                              alertx(data.message);
                              parent.$.loadDiv("guarantorInfoList", "${ctx}/credit/creApplyCompanyRelation/selectCustInfo", {
                                  custId :  piliId,
                                  applyNo :  applyNo
                              }, "post");
                              closeJBox();

                           /*  $.loadDiv("guarantorInfoList", "${ctx}/credit/creApplyCompanyRelation/selectCustInfo", {
                                  custId : '${custId}',
                                  applyNo : '${applyNo}',
                                  readOnly :'${readOnly}',
                              }, "post");*/
                          } else {
                              alertx(data.message)
                          }
                     }
                 });


            }

        }

    </script>
</head>
<body>
<div class="searchInfo">
    <h3 class="searchTitle">担保人列表</h3>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>

        <tr>

            <input type="hidden" id="applyNo" value="${applyNo}"> <!--申请编号-->
            <input type="hidden" id="piliId" value="${piliId}"><!--借款企业主键-->
            <th width="3%">
                <input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
            </th>
            <th width="3%">序号</th>
            <th width="10%">姓名</th>
            <th width="10%">与借款人关系</th>
            <th width="10%">证件类型</th>
            <th width="15%">证件号</th>
            <th width="15%">手机号</th>
            <th width="10%">单位名称</th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${guarantorRelationList}" var="guarantorRelation" varStatus="guarantorRelationList">
            <tr>
                <c:if test="${guarantorRelation.relationForApply != '1' }" >
                    <td>
                        <input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
                    </td>
                </c:if>
                <c:if test="${guarantorRelation.relationForApply == '1' }">
                    <td></td>
                </c:if>
                <td>${guarantorRelationList.index+1 }</td>
                <td>${guarantorRelation.custInfo.custName}</td>
                <td>${fns:getDictLabel(guarantorRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td>
                <td>${fns:getDictLabel(guarantorRelation.custInfo.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
                <td>${guarantorRelation.custInfo.idNum}</td>
                <td>${guarantorRelation.custInfo.mobileNum}</td>
                <td>${guarantorWorkInfoList[guarantorRelationList.index].companyName }</td>

        </c:forEach>

        </tbody>
        <tr>
            <td class="searchButton" colspan="10" style="text-align: right;">

                <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveCompany();" />&nbsp;
                <input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />&nbsp;
            </td>
        </tr>
    </table>



</div>
</body>
</html>
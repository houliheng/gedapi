<%@ page contentType="text/html;charset=UTF-8"%>
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
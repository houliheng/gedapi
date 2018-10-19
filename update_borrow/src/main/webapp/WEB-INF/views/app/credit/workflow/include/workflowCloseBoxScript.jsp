<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${not empty close}">
	<script type="text/javascript">
		parent.page();
		closeJBox();
	</script>
</c:if>
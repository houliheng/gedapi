$(function() {
	var dataUrl = $("#show_data").val();
	var html = "";
	$.ajax({
		type : "POST",
		url : dataUrl,
		data : {},
		datatype : 'json',
		success : function(data) {
			jQuery.each(data.list, function(i, item) {
				html += "<tr><td>"+item.priority+"</td> <td>"+item.secretLevel+"</td> <td> " + item.title2 + "</td><td>"
						+ item.wordNo + "</td><td>" + item.processId
						+ "</td><td>" + item.drafterName + "</td><td>"+item.draftdeptName+"</td> <td>"+item.draftDate+"</td> </tr>";

			});
			$("#show_table").append(html);
			$("#pageNo").val(data.pageNo);
			$("#pageSize").val(data.pageSize);
			$(".pagination").append(data.html);
		}
	});

});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	if($("#pageNo")[0].value.length>10){
		top.$.jBox.tip('当前页码大小长度不能大于10位！');
		return true;
	}
	else if($("#pageSize")[0].value.length>10){
		top.$.jBox.tip('每页条数大小的长度不能大于10位！');
		return true
	}else{
		$("#searchForm").submit();
    	return false;
	}
}

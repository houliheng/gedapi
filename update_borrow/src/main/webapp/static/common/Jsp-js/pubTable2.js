$(function() {
	var dataUrl = $("#show_data").val();
	var html = "";
	$.ajax({
		type : "POST",
		url : dataUrl,
		data : {},
		datatype : 'json',
		success : function(data) {
			jQuery.each(data, function(i, item) {
				html += "<tr><td> <a href='"+ctx+item.categoryId+"-"+item.titleId+end_path+"' target='black'>"+item.title+" </a></td><td>" + item.hits + "</td><td>"+item.createByName+"</td> <td>"+item.updateDate+"</td></tr>";
			});
			$("#show_table").append(html);
		}
	});

});
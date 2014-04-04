CategoryAdministration = function() {
	$.ajax({
		url: "getCategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var category = [];
				category.push(response[i].id);
				category.push(response[i].description);
				
				if(response[i].active==true){
					category.push("Si");	
				}else{
					category.push("No");
				}
				category.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>" +
						"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>");
				aaData.push(category);
				
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
			window.location = "error.do";
		}
	});
	
};
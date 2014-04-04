SubcategoryAdministration = function() {
	var categoryId;
	
	$.ajax({
		url: "getCategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				for (var j = 0, l = response[j].subcategories.length; j < l; ++j) {
					var subcategory = [];
					subcategory.push(response[i].subcategories[j].id);
					subcategory.push(response[i].subcategories[j].description);
					subcategory.push(response[i].description)
					
					if(response[i].active==true){
						subcategory.push("Si");	
					}else{
						subcategory.push("No");
					}
					subcategory.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>" +
							"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>");
					aaData.push(subcategory);
				}
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
	
	$("#addSubcategory").click(function() {
		window.location="addSubcategory";
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		categoryId = parent.find("td:first-child").html();
		
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteCategory",
			type: "POST",
			data: {
				categoryId: categoryId,
			},
			async: true,
			success: function(response) {
				window.location = "deleteConfirmation";
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});
	
	$('#divTable').on("click", ".edit-row", function() {
		var parent = $(this).parent().parent();
		categoryId = parent.find("td:first-child").html();
		
		window.location = "updateCategory?id=" + categoryId;
	});
};
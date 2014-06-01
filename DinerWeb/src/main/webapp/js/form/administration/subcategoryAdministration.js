SubcategoryAdministration = function() {
	var categoryId = null;
	var url = $.url(document.location);
	var hasBeenChanged = url.param("change");
	
	if(hasBeenChanged == "true"){
		$('#successMessageDiv').show();
	}
	
	$.ajax({
		url: "getCategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var len = response[i].subcategories.length;
				for (var j = 0; j < len; ++j) {
					var subcategory = [];
					subcategory.push(response[i].subcategories[j].id);
					subcategory.push(response[i].subcategories[j].description);
					subcategory.push(response[i].description);
					
					if(response[i].subcategories[j].active==true){
						subcategory.push("Si");	
					}else{
						subcategory.push("No");
					}
					subcategory.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
							"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>" +
							"<span class='span-categoryId' style='display:none'>" + response[i].id + "</span>");
					aaData.push(subcategory);
				}
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
	$("#addSubcategory").click(function() {
		window.location="addSubcategory";
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		subcategoryId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteSubcategory",
			type: "POST",
			data: {
				categoryId: categoryId,
				subcategoryId: subcategoryId,
			},
			async: true,
			success: function(response) {
				window.location = "subcategoryAdministration?change=true";
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});
	
	$('#divTable').on("click", ".edit-row", function() {
		var parent = $(this).parent().parent();
		subcategoryId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		
		window.location = "updateSubcategory?id=" + subcategoryId +"&categoryId=" + categoryId;
	});
};
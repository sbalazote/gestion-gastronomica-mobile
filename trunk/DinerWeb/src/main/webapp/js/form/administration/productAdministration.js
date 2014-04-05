ProductAdministration = function() {
	var categoryId;
	
	$.ajax({
		url: "getSubcategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var len = response[i].products.length;
				for (var j = 0; j < len; ++j) {
					var product = [];
					product.push(response[i].products[j].id);
					product.push(response[i].products[j].description);
					product.push(response[i].description);
					product.push(response[i].products[j].price);
					
					if(response[i].products[j].active==true){
						product.push("Si");	
					}else{
						product.push("No");
					}
					product.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>" +
							"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>" +
							"<span class='span-categoryId' style='display:none'>" + response[i].id + "</span>");
					aaData.push(product);
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
		subcategoryId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		
		window.location = "updateSubcategory?id=" + subcategoryId +"&categoryId=" + categoryId;
	});
};
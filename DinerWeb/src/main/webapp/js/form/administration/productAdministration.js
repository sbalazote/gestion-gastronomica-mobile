ProductAdministration = function() {
	var categoryId = null;
	
	$.ajax({
		url: "getCategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var len = response[i].subcategories.length;
				for (var j = 0; j < len; ++j) {
					var long = response[i].subcategories[j].products.length;
					for (var k = 0; k < long; ++k) {	
						var product = [];
						product.push(response[i].subcategories[j].products[k].id);
						product.push(response[i].subcategories[j].products[k].description);
						product.push(response[i].description);
						product.push(response[i].subcategories[j].description);
						product.push(response[i].subcategories[j].products[k].price);
						
						if(response[i].subcategories[j].products[k].estimatedTime == null){
							product.push("-");	
						}else{
							product.push(response[i].subcategories[j].products[k].estimatedTime);
						}
						
						if(response[i].subcategories[j].products[k].celiacAllowed==true){
							product.push("Si");	
						}else{
							product.push("No");
						}
						
						if(response[i].subcategories[j].products[k].active==true){
							product.push("Si");	
						}else{
							product.push("No");
						}
						
						if(response[i].subcategories[j].products[k].kitchen==true){
							product.push("Si");	
						}else{
							product.push("No");
						}
						
						if(response[i].subcategories[j].products[k].stock==true){
							product.push("Si");	
						}else{
							product.push("No");
						}
						
						product.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
								"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>" +
								"<span class='span-categoryId' style='display:none'>" + response[i].id + "</span>"
								+ "<span class='span-subcategoryId' style='display:none'>" + response[i].subcategories[j].id + "</span>");
						aaData.push(product);
						}
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
	
	$("#addProduct").click(function() {
		window.location="addProduct";
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		productId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		subcategoryId = parent.find(".span-subcategoryId").html();
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteProduct",
			type: "POST",
			data: {
				categoryId: categoryId,
				subcategoryId: subcategoryId,
				productId: productId,
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
		productId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		subcategoryId = parent.find(".span-subcategoryId").html();
		
		window.location = "updateProduct?id=" + productId + "&subcategoryId=" + subcategoryId +"&categoryId=" + categoryId;
	});
};
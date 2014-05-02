KitchenProductAdministration = function() {
	var categoryId = null;
	var parent;
	var stock;
	
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
						if(response[i].subcategories[j].products[k].kitchen == true && response[i].subcategories[j].products[k].active == true){
							var product = [];
							product.push(response[i].subcategories[j].products[k].id);
							product.push(response[i].subcategories[j].products[k].description);
							product.push(response[i].description);
							product.push(response[i].subcategories[j].description);
							
							if(response[i].subcategories[j].products[k].stock==true){
								product.push("Disponible");	
							}else{
								product.push("No Disponible");
							}
							
							var string = "<a href='javascript:void(0);'";
							if(response[i].subcategories[j].products[k].stock == false){
								string += " style='display:none'";
							}
							string += "class='no-stock-row'>Cambiar Estado</span></a>";
							string += "<a href='javascript:void(0);'";
							if(response[i].subcategories[j].products[k].stock == true){
								string += " style='display:none'";
							}
							string += " class='stock-row'>Cambiar Estado</span></a>"
						    string += "<span class='span-categoryId' style='display:none'>" + response[i].id + "</span>"
							+ "<span class='span-subcategoryId' style='display:none'>" + response[i].subcategories[j].id + "</span>";
							
							product.push(string);
							aaData.push(product);
							}
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
	
	$('#divTable').on("click", ".no-stock-row", function() {
		parent = $(this).parent().parent();
		productId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		subcategoryId = parent.find(".span-subcategoryId").html();
		stock = false;
		$('#stockConfirmationModal').modal('show');
	});
	
	$('#divTable').on("click", ".stock-row", function() {
		parent = $(this).parent().parent();
		productId = parent.find("td:first-child").html();
		categoryId = parent.find(".span-categoryId").html();
		subcategoryId = parent.find(".span-subcategoryId").html();
		stock = true;
		$('#stockConfirmationModal').modal('show');
	});
	
	$("#stockStateChange").click(function() {

		$.ajax({
			url: "updateProductStock",
			type: "POST",
			data: {
				productId: productId,
				stock: stock,
			},
			async: true,
			success: function(response) {
				if(!stock){
					parent.find(".no-stock-row").hide();
					parent.find(".stock-row").show();
					parent.find("td:eq(4)").text("No Disponible");
				}else{
					parent.find(".stock-row").hide();
					parent.find(".no-stock-row").show();
					parent.find("td:eq(4)").text("Disponible");
				}
			},
			error: function(response) {
			}
		});
	});
	
};
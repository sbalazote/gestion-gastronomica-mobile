OrdersReception = function() {
	var categoryId = null;
	
	$.ajax({
		url: "getOrders",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				for (var j = 0, len = response[i].details.length; j < len; ++j) {
				var orderDetail = [];
				
				orderDetail.push(response[i].id);
				orderDetail.push(response[i].details[j].product.description);
				orderDetail.push(response[i].details[j].amount);
				orderDetail.push(response[i].details[j].comment);
				orderDetail.push(response[i].details[j].requestDate);
				orderDetail.push(response[i].details[j].state.description);
				//orderDetail.push(response[i].tables[0].waiter.surname + " " + response[i].tables[0].waiter.name );
				orderDetail.push("Rodriguez, Carlos");
				
				orderDetail.push("<button class='btn btn-success btn-block' type='button'>Cambiar Estado</button>");
				aaData.push(orderDetail);
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
	
	$("#addCategory").click(function() {
		window.location="addCategory";
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
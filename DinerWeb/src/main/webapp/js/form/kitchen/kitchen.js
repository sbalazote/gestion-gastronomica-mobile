
Kitchen = function() {
	
	$.ajax({
		url: "getKitchenOrders",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var orderDetail = [];
				
				orderDetail.push(response[i].detail.id);
				orderDetail.push(response[i].table.id);
				orderDetail.push(response[i].detail.product.description);
				orderDetail.push(response[i].detail.amount);
				orderDetail.push(response[i].detail.comment);
				//orderDetail.push(response[i].tables[0].waiter.surname + " " + response[i].tables[0].waiter.name );
				orderDetail.push("Rodriguez");
				
				if (response[i].detail.requestDate) {
					var requestDate = new Date(response[i].detail.requestDate);
					orderDetail.push(requestDate.format("dd-mm-yyyy HH:MM:ss"));
				} else {
					orderDetail.push("");
				}
				
				if (response[i].detail.state.id == 2) {
					orderDetail.push("<strong><span style='color:blue'>" + response[i].detail.state.description + "</span></strong>");
					orderDetail.push("<a href='javascript:void(0);' class='a-start'>Iniciar</a>");
				} else if (response[i].detail.state.id == 3) {
					orderDetail.push("<strong><span style='color:orange'>" + response[i].detail.state.description + "</span></strong>");
					orderDetail.push("<a href='javascript:void(0);' class='a-end'>Finalizar</a>");
				} else {
					orderDetail.push("<strong><span style='color:green'>" + response[i].detail.state.description + "</span></strong>");
					orderDetail.push("");
				}
				
				aaData.push(orderDetail);
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true,
			    "iDisplayLength": -1,
			    "aaSorting": [[ 0, "desc" ]] // Sort by first column descending
			});
		},
		error: function(response) {
		}
	});
	
	
	
};
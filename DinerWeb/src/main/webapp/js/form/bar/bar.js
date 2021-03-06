function refreshTable() {
	$.ajax({
		url: "getBarOrders",
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
				orderDetail.push(response[i].table.user.name);
				
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
				} else if (response[i].detail.state.id == 4) {
					orderDetail.push("<strong><span style='color:green'>" + response[i].detail.state.description + "</span></strong>");
					orderDetail.push("");
				} else if (response[i].detail.state.id == 9) {
					orderDetail.push("<strong><span style='color:grey'>" + response[i].detail.state.description + "</span></strong>");
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
			$('.timeago').timeago();
		},
		error: function(response) {
		}
	});
}

Bar = function() {
	var orderDetailId;
	var orderDetailStartDate;
	
	refreshTable();
	window.setInterval(refreshTable, 5000);
	
	$('#divTable').on("click", ".a-start", function() {
		parent = $(this).parent().parent();
		orderDetailId = parent.find("td:first-child").html();
		changeState();
		parent.find("td:eq(7)").html("<strong><span style='color:orange'>Iniciado</span></strong>");
		var newDate = dateFormat(orderDetailStartDate, "yyyy-mm-dd HH:MM:ss");
		parent.find("td:eq(8)").html("<a href='javascript:void(0);' class='a-end'>Finalizar</a>");
		parent.find("td:eq(9)").html("<abbr class='timeago' title='"+ newDate + "'>" + newDate + "</abbr>");
		$('.timeago').timeago();
	});
	
	$('#divTable').on("click", ".a-end", function() {
		parent = $(this).parent().parent();
		orderDetailId = parent.find("td:first-child").html();
		changeState();
		parent.find("td:eq(7)").html("<strong><span style='color:green'>Finalizado</span></strong>");
		parent.find("td:eq(8)").html("");
		parent.find("td:eq(9)").html("");
	});
	
	var changeState = function() {

		$.ajax({
			url: "changeBarOrderDetailState",
			async: false,
			crossDomain: true,
			type: "GET",
			data: {
				orderDetailId: orderDetailId,
			},
			success: function(response) {
				orderDetailStartDate = response.preparationStartDate;
			},
			error: function(response) {
			}
		});
	};
    	
};
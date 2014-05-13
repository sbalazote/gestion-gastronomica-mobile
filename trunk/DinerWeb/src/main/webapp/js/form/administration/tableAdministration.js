TableAdministration = function() {
	var tableId = null;
	var waiterName;
	
	$.ajax({
		url: "getTablesWithClosedOrder",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var table = [];
				table.push(response[i].id);
				
				table.push(response[i].waiter.surname + ", " + response[i].waiter.name);	
				table.push("<a href='javascript:void(0);' class='print-row'>Comprobante</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
					"<a href='javascript:void(0);' class='delete-row'>Medio de Pago</span></a>");	
				aaData.push(table);
				
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
	
	$('#divTable').on("click", ".print-row", function() {
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		waiterName = parent.find("td:eq(1)").html();
		printOrder();
	});
	
	var printOrder = function(){
		$('#tableModal').empty();
		$.ajax({
			url: "getOrderById",
			data: {
				id: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				
				$('#modalTitle').html("Mozo: " + waiterName + " - Mesa Nº" + tableId);
				$('#modalDate').html("Fecha:" + new Date().format("dd-mm-yyyy HH:MM:ss"));
				for (var i = 0, l = response.details.length; i < l; ++i) {
					$('#tableModal').append('<tr><td>'+ response.details[i].product.id + '</td><td>' +  response.details[i].product.description + '</td></tr>');
				}
			},
			error: function(response) {
			}
		});
		$('#printOrderModal').modal('show');
	}
	
	
	$("#printButton").onclick = function() {
	    window.print();
	}
}
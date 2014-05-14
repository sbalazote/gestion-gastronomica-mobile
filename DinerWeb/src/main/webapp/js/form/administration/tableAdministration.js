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
					"<a href='javascript:void(0);' class='payment-row'>Medio de Pago</span></a>");	
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
	
	$('#divTable').on("click", ".payment-row", function() {
		var total = 0;
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		waiterName = parent.find("td:eq(1)").html();
		
		$.ajax({
			url: "getOrderById",
			data: {
				id: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				
				for (var i = 0, l = response.details.length; i < l; ++i) {
					total += response.details[i].product.price*response.details[i].amount;
				}
			},
			error: function(response) {
			}
		});
		$('#totalInput').val(total);
		$('#modalPaymentTitle').html("Mozo: " + waiterName + " - Mesa Nº" + tableId);
		$('#paymentMediaModal').modal('show');
	});
	
	$("#paymentMediaSelect").change(function() {

		
		if($("#paymentMediaSelect").val() == 3){
			$('#cashDiv').show();
		}
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
	
	$("#printButton").click(function(){
	    window.print();
	});
	
	$("#confirmPaymentMediaButton").click(function() {
	
		$.ajax({
			url: "savePaymentMedia",
			type: "POST",
			async: false,
			data: {
				tableId: tableId,
				paymentMediaId: $("#paymentMediaSelect option:selected").val(),
				total: $("#totalInput").val(),
				change: 0,
			},
			success: function(response) {
				window.location = "entitySaved";
			}
		});
	});
}
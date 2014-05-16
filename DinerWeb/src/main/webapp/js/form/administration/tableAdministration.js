TableAdministration = function() {
	var tableId = null;
	var waiterName;
	var change = null;
	var servicePrice = 0;
	var servicePriceActive = false;
	
	$.ajax({
		url: "getParameters",
		type: "GET",
		async: false,
		success: function(response) {
			if(response.dinnerServiceActive == true){
				servicePrice = response.dinnerServicePrice;
				servicePriceActive = true;
			}
		}
	});
	
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
			$("#confirmPaymentMediaButton").prop("disabled",true);
		}else{
			change = 0;
		}
	});

	
	$("#cashInput").on("change keyup paste click", function(){
		var value = $('#cashInput').val().replace(/^\s\s*/, '').replace(/\s\s*$/, '');
	    var intRegex = /^\d+$/;
	    if(!intRegex.test(value)) {
	    	$('#changeInput').val("El campo de Efectivo debe ser numerico");
			$("#confirmPaymentMediaButton").prop("disabled",true);
	    }else{
			if (($("#totalInput").val() - $('#cashInput').val()) > 0){
				$('#changeInput').val("Debe Ingresar un monto mayor");
				$("#confirmPaymentMediaButton").prop("disabled",true);
			}else{
				$('#changeInput').val(-($("#totalInput").val() - $('#cashInput').val()));
				change = -($("#totalInput").val() - $('#cashInput').val());
				
				$("#confirmPaymentMediaButton").prop("disabled",false);
			}
	    }
	})
	
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
				var total = 0;
				$('#modalTitle').html("Mozo: " + waiterName + " - Mesa Nº" + tableId);
				$('#modalDate').html("Fecha:" + new Date().format("dd-mm-yyyy HH:MM:ss"));
				$("#tableModal").append('<tr><td><b>Id</b></td><td><b>Producto</b></td><td><b>Cantidad</b></td><td><b>Precio</b></td></tr>');
				for (var i = 0, l = response.details.length; i < l; ++i) {
					$("#tableModal").append('<tr><td>'+ response.details[i].product.id + '</td><td>' +  response.details[i].product.description + '</td><td>' +  response.details[i].amount + '</td><td>' +  response.details[i].product.price + '</td></tr>');
					total += response.details[i].amount*response.details[i].product.price;
				}
				
				if(servicePriceActive==true){
					$("#tableModal").append('<tr><td><b>Servicio de Mesa ('+  response.customerAmount +')</b></td><td></td><td><b></b></td><td><b>' +  response.customerAmount*servicePrice + '</b></td></tr>');
					total += response.customerAmount*servicePrice;
				}
				
				$("#tableModal").append('<tr><td><b>Total</b></td><td></td><td></td><td><b>' +  total + '</b></td></tr>');
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
				change: change,
			},
			success: function(response) {
				window.location = "entitySaved";
			}
		});
	});
	
}
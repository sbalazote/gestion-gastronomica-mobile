function loadTables (){
	$.ajax({
		url: "getTables",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var table = [];
				table.push(response[i].id);
				table.push(response[i].state.description);
				
				if(response[i].waiter != null){
					table.push(response[i].waiter.surname + ", " + response[i].waiter.name);
				}else{
					table.push("");
				}
				
				if(response[i].state.id == 3){
					table.push("<a href='javascript:void(0);' class='print-row'>Comprobante</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<a href='javascript:void(0);' class='payment-row'>Medio de Pago</span></a>");
				}else{
					table.push(" ");
				}
				aaData.push(table);
				
			}
			$('.tableDatatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
}


TableAdministration = function() {
	var orderId = null;
	var tableId = null;
	var waiterName;
	var waiterId;
	var change = null;
	var servicePrice = 0;
	var servicePriceActive = false;
	
	loadTables();
	window.setInterval(loadTables, 5000);
	
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
			url: "getOrderByTable",
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
		$('#totalInput').val(total.toFixed(2));
		$('#modalPaymentTitle').html("Mozo: " + waiterName + " - Mesa Nro: " + tableId);
		$('#paymentMediaModal').modal('show');
	});
	
	$("#paymentMediaSelect").change(function() {
		if($("#paymentMediaSelect").val() == 3){
			$('#cashDiv').show();
			$("#confirmPaymentMediaButton").prop("disabled",true);
		}else{
			change = 0;
			$("#confirmPaymentMediaButton").prop("disabled",false);
			$('#cashDiv').hide();
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
				$('#changeInput').val((-($("#totalInput").val() - $('#cashInput').val())).toFixed(2));
				change = -($("#totalInput").val() - $('#cashInput').val());
				change.toFixed(2);
				$("#confirmPaymentMediaButton").prop("disabled",false);
			}
	    }
	})
	
	var printOrder = function(){
		$("#tableModal tbody").empty();
		$('#modalDate').html("");
		$.ajax({
			url: "getOrderByTable",
			data: {
				id: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				var subtotal = 0;
				$('#modalTableDetail').html("Mozo: " + waiterName + " - Mesa Nro: " + tableId);
				$('#modalDate').html("Fecha:" + new Date().format("dd-mm-yyyy HH:MM:ss"));
				for (var i = 0, l = response.details.length; i < l; ++i) {
					subtotal += response.details[i].amount * response.details[i].product.price;

					$("#tableModal tbody").append("<tr>"
							+ "<td>" + response.details[i].product.id + "</td>"
							+ "<td>" + response.details[i].product.description + "</td>"
							+ "<td>" + response.details[i].amount + "</td>"
							+ "<td>" + response.details[i].product.price + "</td>"
							+ "</tr>");
				}
				subtotal.toFixed(2);
				// inserto subtotal
				$("#tableModal tbody").append("<tr>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + "SUBTOTAL" + "</td>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + subtotal + "</td>"
						+ "</tr>");

				// inserto servicio de mesa
				var totalServicePrice = 0;
				if(servicePriceActive==true) {
					totalServicePrice = servicePrice * response.customerAmount;
				}
				$("#tableModal tbody").append("<tr>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + "Servicio de Mesa" + "</td>"
						+ "<td>" + response.customerAmount + "</td>"
						+ "<td>" + totalServicePrice + "</td>"
						+ "</tr>");

				// inserto total
				var totalAmount = subtotal + totalServicePrice;
				totalAmount.toFixed(2);
				$("#tableModal tbody").append("<tr>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + "TOTAL" + "</td>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + totalAmount + "</td>"
						+ "</tr>");
			},
			error: function(response) {
			}
		});
		$('#printOrderModal').modal('show');
	}
	
	$("#printButton").click(function() {
		$.ajax({
			url: "getOrderByTable",
			data: {
				id: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				orderId = response.id;
				$.ajax({
					url: "printOrder",
					crossDomain: true,
					type: "GET",
					data: {
						orderId: orderId,
					},
					async: false,
					success: function(response) {
					},
					error: function(response) {
					}
				});
			},
			error: function(response) {
			}
		});
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
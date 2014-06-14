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
				
				var attachedTables = "";
				for (var j = 0, m = response[i].attachedTables.length; j < m; ++j) {
					attachedTables += response[i].attachedTables[j].id;
					if (j < (m-1)) {
						attachedTables += ", ";
					}
				}
				table.push(attachedTables);
				
				if (response[i].state.id == 1) {
					table.push("<strong><span style='color:green'>" + response[i].state.description + "</span></strong>");
				} else if (response[i].state.id == 2) {
					table.push("<strong><span style='color:blue'>" + response[i].state.description + "</span></strong>");
				} else {
					table.push("<strong><span style='color:orange'>" + response[i].state.description + "</span></strong>");
				}
				
				if(response[i].user != null){
					table.push(response[i].user.name);
				}else{
					table.push("");
				}
				
				if (response[i].state.id == 3) {
					table.push("<a href='javascript:void(0);' class='print-row'>Comprobante</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<a href='javascript:void(0);' class='payment-row'>Medio de Pago</span></a>");
				} else if(response[i].state.id == 1) {
					if (attachedTables) {
						table.push("<a href='javascript:void(0);' class='split-tables'>Separar</a>");
					} else {
						table.push("<a href='javascript:void(0);' class='join-tables'>Adjuntar</a>");
					}
				} else {
					table.push("<a href='javascript:void(0);' class='cancel-order-modal'>Anular pedido</a>");
				}
				aaData.push(table);
				
			}
			$('.tableDatatable').dataTable({
				"aaData": aaData,
				"iDisplayLength": 50,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
}


TableStatus = function() {
	var orderId = null;
	var tableId = null;
	var userName;
	var userId;
	var change = null;
	var servicePrice = 0;
	var servicePriceActive = false;
	
	var url = $.url(document.location);
	var hasBeenChanged = url.param("change");
	
	if(hasBeenChanged == "true"){
		$('#successMessageDiv').show();
	}
	
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
		userName = parent.find("td:eq(1)").html();
		printOrder();
	});
	
	$('#divTable').on("click", ".payment-row", function() {
		var total = 0;
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		userName = parent.find("td:eq(1)").html();
		
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
		$('#modalPaymentTitle').html("Mozo: " + userName + " - Mesa Nro: " + tableId);
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
		var value = $('#cashInput').val();
	    if(isNaN(value)) {
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
				var couponDiscount = 0;
				$('#modalTableDetail').html("Mozo: " + userName + " - Mesa Nro: " + tableId);
				$('#modalDate').html("Fecha:" + new Date().format("dd-mm-yyyy HH:MM:ss"));
				for (var i = 0, l = response.details.length; i < l; ++i) {
					subtotal += response.details[i].amount * response.details[i].product.price;

					$("#tableModal tbody").append("<tr>"
							+ "<td>" + response.details[i].product.id + "</td>"
							+ "<td>" + response.details[i].product.description + "</td>"
							+ "<td>" + response.details[i].amount + "</td>"
							+ "<td>" + response.details[i].product.price.toFixed(2) + "</td>"
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
				
				$("#tableModal tbody").append("<tr>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + "Servicio de Mesa" + "</td>"
						+ "<td>" + response.customerAmount + "</td>"
						+ "<td>" + totalServicePrice + "</td>"
						+ "</tr>");
				}
				if(response.coupon != null){
					couponDiscount += (totalServicePrice + subtotal)*(response.coupon.percentage);
					$("#tableModal tbody").append("<tr>"
							+ "<td>" + "-" + "</td>"
							+ "<td>" + response.coupon.description + "</td>"
							+ "<td>" + "-" + "</td>"
							+ "<td>" + couponDiscount + "</td>"
							+ "</tr>");
				}
				// inserto total
				var totalAmount = subtotal + totalServicePrice - couponDiscount ;
				totalAmount.toFixed(2);
				$("#tableModal tbody").append("<tr>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + "TOTAL" + "</td>"
						+ "<td>" + "-" + "</td>"
						+ "<td>" + totalAmount  + "</td>"
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
				window.location = "tableStatus?change=true";
			}
		});
	});
	
	
	// Unir/Separar Mesas
	
	$('#divTable').on("click", ".join-tables", function() {
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		
		$("#my-select").html("");
		
		$.ajax({
			url: "getAttachedTables",
			data: {
				tableId: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				for (var i = 0, l = response.length; i < l; ++i) {
					var id = response[i].id;
					var selected = response[i].selected;
					if (selected == 'true') {
						$("#my-select").append("<option value="+id+" selected>"+id+"</option>)");
					} else {
						$("#my-select").append("<option value="+id+">"+id+"</option>)");
					}
				}
				$("#my-select").multiSelect('refresh');
			},
			error: function(response) {
			}
		});
		$('#tableAttachmentModal').modal('show');
	});
	
	$("#confirmTableAttachmentButton").click(function() {
		var jsonUser = {
			"tableId": tableId,
			"attachedTables": $("#my-select").val() || new Array()
		};

		$.ajax({
			url: "saveAttachedTables",
			type: "POST",
			contentType:"application/json",
			data: JSON.stringify(jsonUser),
			async: true,
			success: function(response) {
				$('#successMessageDiv').show();
			}
		});

	});
	
	$('#divTable').on("click", ".split-tables", function() {
		var me = $(this);
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		
		$("#my-select").html("");
		
		$.ajax({
			url: "splitAttachedTables",
			data: {
				tableId: tableId,
			},
			type: "POST",
			async: false,
			success: function(response) {
				$('#successMessageDiv').show();
				me.hide();
			},
			error: function(response) {
			}
		});
		$('#successMessageDiv').show();
	});
	
	
	// Anular pedidos
	
	$('#divTable').on("click", ".cancel-order-modal", function() {
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		
		$.ajax({
			url: "getOrderByTable",
			data: {
				id: tableId,
			},
			type: "GET",
			async: false,
			success: function(response) {
				var aaData = [];
				if (response.details) {
					for (var i = 0, l = response.details.length; i < l; ++i) {
						var orderDetail = [];
						
						orderDetail.push(response.details[i].id);
						orderDetail.push(response.details[i].product.description);
						orderDetail.push(response.details[i].amount);
						orderDetail.push(response.details[i].comment);
						if (response.details[i].user) {
							orderDetail.push(response.details[i].user.name);
						} else {
							orderDetail.push("");
						}
						
						if (response.details[i].requestDate) {
							var requestDate = new Date(response.details[i].requestDate);
							orderDetail.push(requestDate.format("dd-mm-yyyy HH:MM:ss"));
						} else {
							orderDetail.push("");
						}
						
						if (response.details[i].state.id == 2) {
							orderDetail.push("<strong><span style='color:blue'>" + response.details[i].state.description + "</span></strong>");
						} else if (response.details[i].state.id == 3) {
							orderDetail.push("<strong><span style='color:orange'>" + response.details[i].state.description + "</span></strong>");
						} else if (response.details[i].state.id == 4) {
							orderDetail.push("<strong><span style='color:green'>" + response.details[i].state.description + "</span></strong>");
						} else if (response.details[i].state.id == 5) {
							orderDetail.push("<strong><span style='color:red'>" + response.details[i].state.description + "</span></strong>");
						} else if (response.details[i].state.id == 9) {
							orderDetail.push("<strong><span style='color:grey'>" + response.details[i].state.description + "</span></strong>");
						}
						
						if (response.details[i].state.id != 9) {
							orderDetail.push("<a href='javascript:void(0);' class='cancel-order'>Anular</a>");
						} else {
							orderDetail.push("");
						}
	
						aaData.push(orderDetail);
					}
				}
				$('.cancelOrderDatatable').dataTable({
					"aaData": aaData,
					"bDestroy": true,
				    "iDisplayLength": -1,
				    "aaSorting": [[ 0, "desc" ]] // Sort by first column descending
				});
			},
			error: function(response) {
			}
		});
		$('#cancelOrderModal').modal('show');
	});
	
	$('#cancelOrderTable').on("click", ".cancel-order", function() {
		var me = $(this);
		var parent = $(this).parent().parent();
		detailId = parent.find("td:first-child").html();
		
		$.ajax({
			url: "cancelOrderDetail",
			data: {
				id: detailId,
			},
			type: "POST",
			async: false,
			success: function(response) {
				parent.find("td:eq(6)").html("<strong><span style='color:grey'>Anulado</span></strong>");
				parent.find("td:eq(7)").html("");
			},
			error: function(response) {
			}
		});
	});
	
}
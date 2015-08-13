CouponAdministration = function() {
	var couponId = null;
	var url = $.url(document.location);
	var hasBeenChanged = url.param("change");
	
	if(hasBeenChanged == "true"){
		$('#successMessageDiv').show();
	}
	
	$.ajax({
		url: "getCoupons",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var coupon = [];
				coupon.push(response[i].id);
				coupon.push(response[i].description);
				coupon.push(response[i].percentage * 100);
				
				var startingDate = new Date(response[i].startingDate);
				coupon.push(startingDate.format("dd-mm-yyyy"));
				
				var expirationDate = new Date(response[i].expirationDate);
				coupon.push(expirationDate.format("dd-mm-yyyy"));

				coupon.push("<a href='javascript:void(0);' class='print-row'><span class='glyphicon glyphicon-print'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>");
				
				aaData.push(coupon);
				
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
	$("#addCoupon").click(function() {
		window.location="addCoupon";
	});
	
	$('#divTable').on("click", ".print-row", function() {
		var parent = $(this).parent().parent();
		couponId = parent.find("td:first-child").html();
		
		// Recupero la imagen QR para previsualizar
		$.ajax({
			url : "retrieveCouponImage",
			type : "POST",
			data : {
				couponId: couponId
			},
			async : true,
			success : function(response) {
				var dataURL="data:image/png;base64,"+response;
		        document.getElementById("QRCodeImage").src = dataURL;
			}
		});
		
		$("#QRCodeImage").printThis({
			pageTitle : "QR Code Image",
		});
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		couponId = parent.find("td:first-child").html();
		
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteCoupon",
			type: "POST",
			data: {
				couponId: couponId,
			},
			async: true,
			success: function(response) {
				window.location = "couponAdministration?change=true";
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});
	
};
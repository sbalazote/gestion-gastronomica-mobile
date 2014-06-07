CouponsAdministration = function() {

	$("#expirationDateButton").click(function() {
		$("#expirationDateInput").datepicker().focus();
	});
	
	var validateForm = function() {
		var form = $("#couponsAdministrationForm");
		form.validate({
			rules: {
				description: {
					required: true
				},
				percentage: {
					required: true
				},
				expirationDate: {
					required: true
				}
			},
			showErrors: myShowErrors,
			onsubmit: false
		});
		return form.valid();
	};
	
	var jsonCoupon;
	var QRgenerated = false;
	
	$("#generateQRCodeButton").click(function() {
		if (validateForm()) {
			jsonCoupon = {
				"description" : $("#descriptionInput").val(),
				"percentage" : $("#percentageSelect option:selected").text(),
				"expirationDate" : $("#expirationDateInput").val()
			};

			$.ajax({
				url : "generateCoupon",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(jsonCoupon),
				async : true,
				success : function(response) {
					QRgenerated = true;

					// Recupero la imagen QR para previsualizar
					$.ajax({
						url : "retrieveCouponImage",
						type : "POST",
						data : {
							description: $("#descriptionInput").val()
						},
						async : true,
						success : function(response) {
							var dataURL="data:image/png;base64,"+response;
					        document.getElementById("QRCodeImage").src = dataURL;
						}
					});
				}
			});
		}
	});
	
	$("#printQRCodeImageButton").click(function() {
		if (QRgenerated) {
			//	TODO printear el <img> recien generado.
		}
	});
	
	$("#confirm").click(function() {
		if (QRgenerated) {
			$.ajax({
				url: "saveCoupon",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonCoupon),
				async: true,
				success: function(response) {
					alert("Ok!");
				}
			});
		}
	});
};
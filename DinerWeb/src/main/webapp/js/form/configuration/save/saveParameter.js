SaveParameter = function() {
	
	var validateForm = function() {
		var form = $("#parameterAdministrationForm");
		form.validate({
			rules: {
				restaurantName: {
					required: true
				},
				dinnerServicePrice: {
					required: true
				},
				dinnerServiceActive: {
					required: true
				}
			},
			showErrors: myShowErrors,
			onsubmit: false
		});
		return form.valid();
	};
	
	$("#confirm").click(function() {
		if (validateForm()) {
			var jsonCategory = {
					"id": $("#idInput").val(),
					"restaurantName": $("#restaurantNameInput").val(),
					"dinnerServicePrice": $("#dinnerServicePriceInput").val(),
					"dinnerServiceActive": $("#dinnerServiceActiveSelect option:selected").val(),
			};

			$.ajax({
				url: "saveParameter.do",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonCategory),
				async: true,
				success: function(response) {
					window.location = "entitySaved.do";
				}
			});
		}
	});
	
};
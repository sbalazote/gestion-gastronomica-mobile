SaveTable = function() {
	
	var validateForm = function() {
		var form = $("#tableAdministrationForm");
		form.validate({
			rules: {
				tableNumberInput: {
					required: true,
					digits: true
				},
				active: {
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
			var jsonTable = {
					"id": $("#tableNumberInput").val(),
					"active": $("#activeSelect option:selected").val(),
			};

			$.ajax({
				url: "saveTable",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonTable),
				async: true,
				success: function(response) {
					window.location = "tableAdministration?change=true";
				}
			});
		}
	});
	
};
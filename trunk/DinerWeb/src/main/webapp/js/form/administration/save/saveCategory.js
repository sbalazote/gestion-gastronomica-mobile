SaveCategory = function() {
	
	var validateForm = function() {
		var form = $("#categoryAdministrationForm");
		form.validate({
			rules: {
				description: {
					required: true
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
			var jsonCategory = {
					"id": $("#idInput").val(),
					"description": $("#descriptionInput").val(),
					"active": $("#activeSelect option:selected").val(),
			};

			$.ajax({
				url: "saveCategory",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonCategory),
				async: true,
				success: function(response) {
					window.location = "entitySaved";
				}
			});
		}
	});
	
};
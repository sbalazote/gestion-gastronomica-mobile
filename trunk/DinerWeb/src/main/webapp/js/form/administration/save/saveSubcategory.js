SaveSubcategory = function() {
	
	var validateForm = function() {
		var form = $("#subcategoryAdministrationForm");
		form.validate({
			rules: {
				description: {
					required: true
				},
				category: {
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
			var jsonSubcategory = {
					"id": $("#idInput").val(),
					"description": $("#descriptionInput").val(),
					"categoryId": $("#categoryInput option:selected").val(),
					"active": $("#activeSelect option:selected").val(),
					"oldCategoryId": $("#idCategory").val()
			};

			$.ajax({
				url: "saveSubcategory.do",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonSubcategory),
				async: true,
				success: function(response) {
					window.location = "entitySaved.do";
				}
			});
		}
	});
	
};
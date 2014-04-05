SaveProduct = function() {
	
	 
	 $("#categorySelect").chosen().change(function() {
			$.ajax({
				url: "getSubcategoriesByCategory",
				type: "GET",
				async: false,
				data: {
					categoryId: $("#categorySelect option:selected").val(),
				},
				success: function(response) {
					for (var i = 0, l = response.length; i < l; ++i) {
						$('#subcategorySelect').append('<option value="foo">Bar</option>');
					}
					$('#subcategorySelect').trigger("chosen:updated");
				}
			});
		});
	
	
	var validateForm = function() {
		var form = $("#productAdministrationForm");
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
			var jsonProduct = {
					"id": $("#idInput").val(),
					"description": $("#descriptionInput").val(),
					"subcategoryId": $("#subcategorySelect option:selected").val(),
					"active": $("#activeSelect option:selected").val(),
					"oldSubcategoryId": $("#idCategory").val()
			};

			$.ajax({
				url: "saveProduct.do",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonProduct),
				async: true,
				success: function(response) {
					window.location = "entitySaved.do";
				}
			});
		}
	});
	
};
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
				$('#subcategorySelect').empty();
				for (var i = 0, l = response.length; i < l; ++i) {
					$('#subcategorySelect').append('<option value="'+ response[i].id + '">' + response[i].id + ' - ' + response[i].description +'</option>');
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
					"price": $("#priceInput").val(),
					"estimatedTime": $("#estimatedTimeInput").val(),
					"active": $("#activeSelect option:selected").val(),
					"kitchen": $("#kitchenSelect option:selected").val(),
					"stock": $("#stockSelect option:selected").val(),
					"celiacAllowed": $("#celiacAllowedSelect option:selected").val(),
					"oldSubcategoryId": $("#idSubcategory").val()
			};

			$.ajax({
				url: "saveProduct",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(jsonProduct),
				async: true,
				success: function(response) {
					window.location = "productAdministration?change=true";
				}
			});
		}
	});
	
};
SaveUser = function() {
	
	var originalName = $("#nameInput").val();
	
	var validateForm = function() {
		var form = $("#userAdministrationForm");
		form.validate({
			rules: {
				name: {
					required: true,
				},
				password: {
					required: true, minlength: 5
				},
				passwordCheck: { 
	                required: true, equalTo: "#passwordInput", minlength: 5
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
	
	var existsUser = function() {
		var exists = false;
		$.ajax({
			url: "existsUser.do",
			type: "GET",
			async: false,
			data: {
				name: $("#nameInput").val()
			},
			success: function(response) {
				exists = response;
			},
			error: function(response) {
				console.log(response);
			}
		});
		return exists;
	};
	
	$("#delete").click(function() {
		$('#deleteConfirmationModal').modal('show');
	});
	
	$('#my-select').multiSelect();
	
	$("#confirm").click(function() {
		if (validateForm()) {
			var jsonUser = {
					"id": $("#idInput").val(),
					"name": $("#nameInput").val(),
					"password": $("#passwordInput").val(),
					"roles": $("#my-select").val() || new Array(),
					"active": $("#activeSelect option:selected").val(),
			};

			//	si existe el codigo y ademas no se trata de una actualizacion, lanzo modal.
			if (existsUser() && ($("#nameInput").val() != originalName)) {
				$('#existingElementModal').modal('show');
			} else {
				$.ajax({
					url: "saveUser.do",
					type: "POST",
					contentType:"application/json",
					data: JSON.stringify(jsonUser),
					async: true,
					success: function(response) {
						window.location = "userAdministration?change=true";
					}
				});
			}
		}
	});
	
	$("#userAdministrationForm input, #userAdministrationForm select").keypress(function(event) {
		return event.keyCode != 13;
	});
	
	$("#changePasswordSelect").change(function(){
		var value = $(this).find("option:selected").val();
		if (value == "true") {
			$("#passwordInput").prop("disabled", false);
			$("#passwordInputCheck").prop("disabled", false);
		} else {
			$("#passwordInput").prop("disabled", true);
			$("#passwordInputCheck").prop("disabled", true);
			$("#passwordInput").val("");
			$("#passwordInputCheck").val("");
			$('#passwordInput').data("title", "").removeClass("has-error").tooltip("destroy");
			$('#passwordInputCheck').data("title", "").removeClass("has-error").tooltip("destroy");
		}
	});

};
SaveCoupon = function() {

	$("#startingDateInput").attr('readOnly', 'true');
	$("#expirationDateInput").attr('readOnly', 'true');
	
	$("#startingDateInput").datepicker({
	      onClose: function( selectedDate ) {
	        $( "#expirationDateInput" ).datepicker( "option", "minDate", selectedDate );
	      }
	});
	
	$("#expirationDateInput").datepicker({
		beforeShow: function() {
			$( "#expirationDateInput" ).datepicker( "option", "minDate", new Date() );
		},
		onClose: function( selectedDate ) {
			$( "#startingDateInput" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$("#startingDateButton").click(function() {
		$("#startingDateInput").datepicker().focus();
	});
	
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
				startingDate: {
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
	
	$("#confirm").click(function() {
		if (validateForm()) {
			jsonCoupon = {
				"description" : $("#descriptionInput").val(),
				"percentage" : $("#percentageSelect option:selected").val(),
				"startingDate" : $("#startingDateInput").val(),
				"expirationDate" : $("#expirationDateInput").val()
			};

			$.ajax({
				url : "saveCoupon",
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(jsonCoupon),
				async : true,
				success : function(response) {
					window.location = "couponAdministration?change=true";
				}
			});
		}
	});
};
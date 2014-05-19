Login = function() {
	
	var validateForm = function() {
		var form = $("#loginForm");
		form.validate({
			rules: {
				j_username: {
					usernameRequired: true
				},
				j_password: {
					passwordRequired: true
				}
			},
			showErrors: myShowErrors
		});
		return form.valid();
	};
	
	$("#loginButton").click(function() {
		if (validateForm()) {
			$("#loginButton").submit();
		}
	});
};
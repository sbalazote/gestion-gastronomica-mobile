UserAdministration = function() {
	var userId = null;
	var users = [];
	
	$.ajax({
		url: "getUsers",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var user = [];
				user.push(response[i].id);
				user.push(response[i].name);
				if(response[i].active==true){
					user.push("Si");	
				}else{
					user.push("No");
				}
			
				user.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>" +
						"<span class='span-userId' style='display:none'>" + response[i].id + "</span>");
				aaData.push(user);
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});

	$("#addUser").click(function() {
		window.location="addUser";
	});
	
	$('#userTableBody').on("click", ".edit-row", function() {
		var parent = $(this).parent().parent();
		userId = parent.find(".span-userId").html();
		
		window.location = "updateUser?id=" + userId;
	});
	
	
	$('#userTableBody').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		userId = parent.find(".span-userId").html();
		
		$.ajax({
			url: "deleteUser.do",
			type: "POST",
			data: {
				userId: userId,
			},
			async: true,
			success: function(response) {
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});

};
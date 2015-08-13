TableAdministration = function() {
	var tableId = null;
	var url = $.url(document.location);
	var hasBeenChanged = url.param("change");
	
	if(hasBeenChanged == "true"){
		$('#successMessageDiv').show();
	}
	
	$.ajax({
		url: "getAllTables",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var table = [];
				table.push(response[i].id);
				
				if(response[i].active==true){
					table.push("Si");	
				}else{
					table.push("No");
				}
				table.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>");
				aaData.push(table);
				
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
	$("#addTable").click(function() {
		window.location="addTable";
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteTable",
			type: "POST",
			data: {
				tableId: tableId,
			},
			async: true,
			success: function(response) {
				window.location = "tableAdministration?change=true";
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});
	
	$('#divTable').on("click", ".edit-row", function() {
		var parent = $(this).parent().parent();
		tableId = parent.find("td:first-child").html();
		
		window.location = "updateTable?id=" + tableId;
	});
	
};
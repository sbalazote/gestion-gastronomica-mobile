CategoryAdministration = function() {
	var categoryId = null;
	
	$.ajax({
		url: "getCategories",
		type: "GET",
		async: false,
		success: function(response) {
			var aaData = [];
			for (var i = 0, l = response.length; i < l; ++i) {
				var category = [];
				category.push(response[i].id);
				category.push(response[i].description);
				
				if(response[i].active==true){
					category.push("Si");	
				}else{
					category.push("No");
				}
				category.push("<a href='javascript:void(0);' class='edit-row'><span class='glyphicon glyphicon-pencil'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<a href='javascript:void(0);' class='delete-row'><span class='glyphicon glyphicon-remove'></span></a>");
				aaData.push(category);
				
			}
			$('.datatable').dataTable({
				"aaData": aaData,
				"bDestroy": true
			});
		},
		error: function(response) {
		}
	});
	
	$("#addCategory").click(function() {
		window.location="addCategory";
	});
	
	$('#divTable').on("click", ".delete-row", function() {
		var parent = $(this).parent().parent();
		categoryId = parent.find("td:first-child").html();
		
		$('#deleteConfirmationModal').modal('show');
	});
	
	$("#deleteEntityButton").click(function() {

		$.ajax({
			url: "deleteCategory",
			type: "POST",
			data: {
				categoryId: categoryId,
			},
			async: true,
			success: function(response) {
				window.location = "deleteConfirmation";
			},
			error: function(response) {
				console.log(response),
				$('#deleteFailModal').modal('show');
			}
		});
	});
	
	$('#divTable').on("click", ".edit-row", function() {
		var parent = $(this).parent().parent();
		categoryId = parent.find("td:first-child").html();
		
		window.location = "updateCategory?id=" + categoryId;
	});
	
	$(function () { 
	    $('#container').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: 'Fruit Consumption'
	        },
	        xAxis: {
	            categories: ['Apples', 'Bananas', 'Oranges']
	        },
	        yAxis: {
	            title: {
	                text: 'Fruit eaten'
	            }
	        },
	        series: [{
	            name: 'Jane',
	            data: [1, 0, 4]
	        }, {
	            name: 'John',
	            data: [5, 7, 3]
	        }]
	    });
	});
};
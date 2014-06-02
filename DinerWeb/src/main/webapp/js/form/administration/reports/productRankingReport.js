ProductRankingReport = function() {
	
	$('#categoriesSubcategoriesSelect').multiSelect();
	
	var daysToAdd = 365;
	
	$("#productRankingDateFromInput").attr('readOnly', 'true');
	$("#productRankingDateToInput").attr('readOnly', 'true');
	
	$("#productRankingDateFromInput").datepicker({
	      onClose: function( selectedDate ) {
	        $( "#productRankingDateToInput" ).datepicker( "option", "minDate", selectedDate );
	        var splittedDate = selectedDate.split("/");
	        var maxDate = new Date(splittedDate[2], splittedDate[1] - 1, splittedDate[0]);
	        maxDate.setDate(maxDate.getDate() + daysToAdd);
	        var dd = maxDate.getDate();
	        var mm = maxDate.getMonth() + 1;
	        var y = maxDate.getFullYear();
	        var maxDateFormatted = dd + '/'+ mm + '/'+ y;
	        $( "#productRankingDateToInput" ).datepicker( "option", "maxDate", maxDateFormatted );
	      }
	});
	
	$("#productRankingDateToInput").datepicker({
	      onClose: function( selectedDate ) {
	        $( "#productRankingDateFromInput" ).datepicker( "option", "maxDate", selectedDate );
	        var splittedDate = selectedDate.split("/");
	        var minDate = new Date(splittedDate[2], splittedDate[1] - 1, splittedDate[0]);
	        minDate.setDate(minDate.getDate() - daysToAdd);
	        var dd = minDate.getDate();
	        var mm = minDate.getMonth() + 1;
	        var y = minDate.getFullYear();
	        var minDateFormatted = dd + '/'+ mm + '/'+ y;
	        $( "#productRankingDateFromInput" ).datepicker( "option", "minDate", minDateFormatted );
	      }
	});
	
	
	$('#productRankingDateFromButton').click(function() {
		$("#productRankingDateFromInput").datepicker().focus();
	});
	
	$('#productRankingDateToButton').click(function() {
		$("#productRankingDateToInput").datepicker().focus();
	});
	
	var validateForm = function() {
		var form = $("#productRankingReportForm");
		form.validate({
			rules: {
				productRankingDateFrom: {
					required: true
				},
				productRankingDateTo: {
					required: true
				},
				categoriesSubcategories: {
					emptyCategoriesSubcategories: true,
					categoriesRepeated: true
				}
			},
			ignore: ':hidden:not("#categoriesSubcategoriesSelect")',
			showErrors: myShowErrors,
			onsubmit: false
		});
		return form.valid();
	};
	
	$("#productRankingCleanButton").click(function() {
		$.datepicker._clearDate('#productRankingDateFromInput');
		$.datepicker._clearDate('#productRankingDateToInput');
		
	});
	
	$("#productRankingSearchButton").click(function() {
		if(validateForm()){
			var jsonProductRankingReportFilters = {
					"from": $("#productRankingDateFromInput").val(),
					"to": $("#productRankingDateToInput").val(),
					"categorySubcategoryFilter": $("#categoriesSubcategoriesSelect").val()
			};
			$.ajax({
				url: "getRankedProductsBetweenDates",
				type: "POST",
				contentType: "application/json",
				async: false,
				data: JSON.stringify(jsonProductRankingReportFilters),
				success: function(response) {
					var aaData = [];
					var xAxis = [];
					var yAxis = [];
					for (var i = 0, l = response.length; i < l; ++i) {
						var productRankingResult = [];
						productRankingResult.push(response[i].category);
						productRankingResult.push(response[i].subcategory);
						productRankingResult.push(response[i].productDescription);
						xAxis.push(response[i].productDescription);
						productRankingResult.push(response[i].numberOfTimesServed);
						yAxis.push(response[i].numberOfTimesServed);
						aaData.push(productRankingResult);
					}
					var oTable = $('#productRankingTable').dataTable({
						"aaData": aaData,
						"bDestroy": true
					});
					
					var tableTools = new $.fn.dataTable.TableTools( oTable, {
						"aButtons": [{
						            	 "sExtends":     "xls",
						            	 "sButtonText": "Generar Reporte"
						}],
						"sSwfPath": "swf/copy_csv_xls.swf"
					    });
					      
					$( tableTools.fnContainer() ).insertAfter('#productRankingHighchartDiv');
					
					$('#productRankingHighchartDiv').highcharts({
			            chart: {
			                type: 'column'
			            },
			            title: {
			                text: 'Reporte de Ranking de Productos'
			            },
			            xAxis: {
			                categories: xAxis
			            },
			            yAxis: {
			                min: 0,
			                title: {
			                    text: 'Veces Servidas'
			                }
			            },
			            tooltip: {
			                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			                pointFormat: '<tr><td style="color:{series.color};padding:0">Veces Servidas: </td>' +
			                    '<td style="padding:0"><b>{point.y}</b></td></tr>',
			                footerFormat: '</table>',
			                shared: true,
			                useHTML: true
			            },
			            plotOptions: {
			                column: {
			                    pointPadding: 0.2,
			                    borderWidth: 0
			                }
			            },
			            series: [{
			            	showInLegend: false,
			                data: yAxis
			    
			            }]
			        });
				}
			});
		}
	});
	
};
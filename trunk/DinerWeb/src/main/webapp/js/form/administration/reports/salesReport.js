SalesReport = function() {
	
	var daysToAdd = 365;
	
	$("#salesDateFromInput").attr('readOnly', 'true');
	$("#salesDateToInput").attr('readOnly', 'true');
	
	$("#salesDateFromInput").datepicker({
	      onClose: function( selectedDate ) {
	        $( "#salesDateToInput" ).datepicker( "option", "minDate", selectedDate );
	        var splittedDate = selectedDate.split("/");
	        var maxDate = new Date(splittedDate[2], splittedDate[1] - 1, splittedDate[0]);
	        maxDate.setDate(maxDate.getDate() + daysToAdd);
	        var dd = maxDate.getDate();
	        var mm = maxDate.getMonth() + 1;
	        var y = maxDate.getFullYear();
	        var maxDateFormatted = dd + '/'+ mm + '/'+ y;
	        $( "#salesDateToInput" ).datepicker( "option", "maxDate", maxDateFormatted );
	      }
	});
	
	$("#salesDateToInput").datepicker({
	      onClose: function( selectedDate ) {
	        $( "#salesDateFromInput" ).datepicker( "option", "maxDate", selectedDate );
	        var splittedDate = selectedDate.split("/");
	        var minDate = new Date(splittedDate[2], splittedDate[1] - 1, splittedDate[0]);
	        minDate.setDate(minDate.getDate() - daysToAdd);
	        var dd = minDate.getDate();
	        var mm = minDate.getMonth() + 1;
	        var y = minDate.getFullYear();
	        var minDateFormatted = dd + '/'+ mm + '/'+ y;
	        $( "#salesDateFromInput" ).datepicker( "option", "minDate", minDateFormatted );
	      }
	});
	
	
	$('#salesDateFromButton').click(function() {
		$("#salesDateFromInput").datepicker().focus();
	});
	
	$('#salesDateToButton').click(function() {
		$("#salesDateToInput").datepicker().focus();
	});
	
	var validateForm = function() {
		var form = $("#salesReportForm");
		form.validate({
			rules: {
				salesDateFrom: {
					dateITA: true,
					maxDate: $("#salesDateToInput")
				},
				salesDateTo: {
					dateITA: true,
					minDate: $("#salesDateFromInput")
				}
			},
			showErrors: myShowErrors,
			onsubmit: false
		});
		return form.valid();
	};
	
	$("#salesCleanButton").click(function() {
		$.datepicker._clearDate('#salesDateFromInput');
		$.datepicker._clearDate('#salesDateToInput');
		
	});
	
	$("#salesSearchButton").click(function() {
		if(validateForm()){
			var datefrom = new Date($("#salesDateFromInput").val());
			var dto= new Date($("#salesDateToInput").val());
			$.ajax({
				url: "getBilledOrdersBetweenDates",
				type: "POST",
				async: false,
				data: {
					from: $("#salesDateFromInput").val(),
					to: $("#salesDateToInput").val()
				},
				success: function(response) {
					var aaData = [];
					var xAxis = [];
					var yAxis = [];
					for (var i = 0, l = response.length; i < l; ++i) {
						var saleResult = [];
						saleResult.push(myParseDate(response[i].billingDate));
						xAxis.push(myParseDate(response[i].billingDate));
						saleResult.push(response[i].dayTotal);
						yAxis.push(response[i].dayTotal);
						aaData.push(saleResult);
					}
					var oTable = $('#salesTable').dataTable({
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
					      
					$( tableTools.fnContainer() ).insertAfter('#salesHighchartDiv');
					
					$('#salesHighchartDiv').highcharts({
			            chart: {
			                type: 'column'
			            },
			            title: {
			                text: 'Reporte de Facturación'
			            },
			            xAxis: {
			                categories: xAxis
			            },
			            yAxis: {
			                min: 0,
			                title: {
			                    text: 'Total Facturado (en $)'
			                }
			            },
			            tooltip: {
			                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			                pointFormat: '<tr><td style="color:{series.color};padding:0">Total: </td>' +
			                    '<td style="padding:0"><b>$ {point.y:.1f}</b></td></tr>',
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
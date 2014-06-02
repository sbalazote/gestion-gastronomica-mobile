SalesReport = function() {
	
	var validateForm = function() {
		var form = $("#salesReportForm");
		form.validate({
			rules: {
				salesDateFrom: {
					required: true,
					monthYearDateOnly: true,
					maxDate: $("#salesDateToInput")
				},
				salesDateTo: {
					required: true,
					monthYearDateOnly: true,
					minDate: $("#salesDateFromInput")
				}
			},
			showErrors: myShowErrors,
			onsubmit: false
		});
		return form.valid();
	};
	
	$("#salesCleanButton").click(function() {
		$("#salesDateFromInput").val("");
		$("#salesDateToInput").val("");
	});
	
	$("#salesSearchButton").click(function() {
		if(validateForm()){
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
						saleResult.push(myParseDateMMYYYY(response[i].billingDate));
						xAxis.push(myParseDateMMYYYY(response[i].billingDate));
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
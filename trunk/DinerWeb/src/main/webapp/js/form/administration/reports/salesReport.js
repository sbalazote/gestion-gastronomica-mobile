SalesReport = function() {
	
	var daysToAdd = 365;
	$('#salesDateFromButton').click(function() {
		var maxDate = $( "#salesDateToInput" ).datepicker( "getDate" );
		
		var dtMax = new Date(maxDate);
        dtMax.setDate(dtMax.getDate() - daysToAdd); 
        var dd = dtMax.getDate();
        var mm = dtMax.getMonth() + 1;
        var y = dtMax.getFullYear();
        var dtFormatted = dd + '/'+ mm + '/'+ y;
        $("#salesDateFromInput").datepicker("option", "minDate", dtFormatted);
		
		$("#salesDateFromInput").datepicker("option", "maxDate", maxDate);
		$("#salesDateFromInput").datepicker().focus();
	});
	
	$('#salesDateToButton').click(function() {
		var minDate = $( "#salesDateFromInput" ).datepicker( "getDate" );
		$("#salesDateToInput").datepicker("option", "minDate", minDate);
		
		var dtMax = new Date(minDate);
        dtMax.setDate(dtMax.getDate() + daysToAdd); 
        var dd = dtMax.getDate();
        var mm = dtMax.getMonth() + 1;
        var y = dtMax.getFullYear();
        var dtFormatted = dd + '/'+ mm + '/'+ y;
        $("#salesDateToInput").datepicker("option", "maxDate", dtFormatted)
		$("#salesDateToInput").datepicker().focus();
	});

	$("#salesDateFromInput").datepicker();
	$("#salesDateToInput").datepicker();
	
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
		if($("#salesDateFromInput").val()!= ""){
			$.datepicker._clearDate('#salesDateFromInput');
		}
		if($("#salesDateToInput").val()!= ""){
			$.datepicker._clearDate('#salesDateToInput');
		}
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
						"bDestroy": true,
						//"dom": 'lTfrtip',
						//"dom": 'lfrtip<"#salesHighchartDiv"T>',
						//"oTableTools": {
						//	"aButtons": [ "csv" ],
						//	"sSwfPath": "swf/copy_csv_xls.swf",
						//	//"sExtends":     "csv",
		                //    "sButtonText": "Generar Reporte"
				        //},
					});
					
					 var tableTools = new $.fn.dataTable.TableTools( oTable, {
//					        "buttons": [
//					            "copy",
//					            "csv",
//					            "xls",
//					            "pdf",
//					            { "type": "print", "buttonText": "Print me!" }
//					        ],
						 "aButtons": [
						                {
						                    "sExtends":     "csv",
						                    "sButtonText": "Generar Reporte"
						                }
						            ],
					        "sSwfPath": "swf/copy_csv_xls.swf"
					    } );
					      
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
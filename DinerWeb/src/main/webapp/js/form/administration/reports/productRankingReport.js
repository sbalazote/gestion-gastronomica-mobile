ProductRankingReport = function() {
	
//	function keysbyValue(O){
//	    var A= [];
//	    for(var p in O){
//	        if(O.hasOwnProperty(p)) A.push([p, O[p]]);
//	    }
//	    A.sort(function(a, b){
//	        var a1= a[1], b1= b[1];
//	        return a1-b1;
//	    });
//	    for(var i= 0, L= A.length; i<L; i++){
//	        A[i]= A[i][0];
//	    }
//	    return A;
//	}
	
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
					categoriesRepeated: true,
					maxAmount: 10
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
					var paretoMapXAxis = [];
					var paretoMapYAxis = [];
					var paretoAcum = [];
					var acumTotal = 0;
					var total = 0;
					for (var i = 0, l = response.length; i < l; ++i) {
						var productRankingResult = [];
						productRankingResult.push(response[i].category);
						productRankingResult.push(response[i].subcategory);
						productRankingResult.push(response[i].productDescription);
						xAxis.push(response[i].productDescription);
						productRankingResult.push(response[i].numberOfTimesServed);
						yAxis.push(response[i].numberOfTimesServed);
						
						//	Ordenar en forma DESCENDENTE (los primeros 10) y utilizarlo en el grafico Pareto.
						paretoMapXAxis.push(response[i].productDescription);
						paretoMapYAxis.push(response[i].numberOfTimesServed);
						
						//var a = keysbyValue(paretoMap);
						
						aaData.push(productRankingResult);
					}
					
					for (var i = 0; i < paretoMapYAxis.length; i++) {
					    total += paretoMapYAxis[i] << 0;
					}
					for (var i = 0; i < paretoMapYAxis.length; i++) {
					    paretoAcum.push((paretoMapYAxis[i] / total * 100)+acumTotal);
					    acumTotal += (paretoMapYAxis[i] / total * 100);
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
					      
					$( tableTools.fnContainer() ).insertAfter('#chartTabs');
					
					$('#productRankingChartDiv').highcharts({
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
					//	Grafico Pareto
					var chart;
					chart = new Highcharts.Chart({
				        chart: {
				            renderTo: 'productRankingParetoChartDiv'
				        },
				        credits: {
				            enabled: false
				        },
				        legend: {
				            layout: 'horizontal',
				            verticalAlign: 'bottom'
				        },
				        title: {
				            text: ''
				        },
				        tooltip: {
				            formatter: function () {
				                if (this.series.name == 'Acumulado') {
				                    return this.y + '%';
				                }
				                return this.x + '<br/>' + '<b> ' + this.y.toString().replace('.', ',') + ' </b>';
				            }
				        },
				        xAxis: {
				        	// Aca va la descripcion de paretoMap
				            categories: paretoMapXAxis
				        },
				        yAxis: [{
				            title: {
				                text: ''
				            }
				        }, {
				            labels: {
				                formatter: function () {
				                    return this.value + '%';
				                }
				            },
				            max: 100,
				            min: 0,
				            opposite: true,
				            plotLines: [{
				                color: '#89A54E',
				                dashStyle: 'shortdash',
				                value: 80,
				                width: 3,
				                zIndex: 10
				            }],
				            title: {
				                text: ''
				            }
				        }],
				        series: [{
				        	// Aca va los valores en de paretoMap en forma descendente
				            data: paretoMapYAxis,
				            name: 'Veces Servido',
				            type: 'column'
				        }, {
				        	// Aca va el acumulado de paretoMap
				            data: paretoAcum,
				            name: 'Acumulado',
				            type: 'spline',
				            yAxis: 1,
				            id: 'acumulado'
				        }]
				    },function(chart){
				    
				        
				        var x = 0.8 * chart.plotWidth;
				        
				        chart.renderer.path([
				                 'M',
				                 x, chart.plotTop,
				                 'L',
				                 x, chart.plotTop + chart.plotHeight
				            ]).attr({
				                'stroke-width': 2,
				                stroke: 'red',
				                id: 'vert',
				               'stroke-dasharray':"5,5",
				                zIndex: 2000
				            }).add();
				        
				    });
					
					if (response.length === 0) {
						$('.DTTT_button_xls').attr('disabled', true);
					} else {
						$('.DTTT_button_xls').attr('disabled', false);
					}
				}
			});
		}
	});
	
};
$(document).ready(function() {
	
	// To highlight menu options
	
	var path = window.location.pathname.split("/");
	var href = path[path.length-1];
	$("ul.navbar-nav li a[href='"+href+"'] ").closest("li.activable").addClass("active");
	
	// My Global Functions
	
	myShowErrors = function(errorMap, errorList) {
		// Limpio los tooltips para elementos validos.
		$.each(this.validElements(), function (index, element) {
			// Limpio el titulo del tooltip. No hay elementos invalidos asociados.
			if ($(element).hasClass("chosen-select")) {
				var chosenElement = $(element).next().find("a");
				chosenElement.data("title", "").removeClass("has-error").tooltip("destroy");
			} else {
				$(element).data("title", "").removeClass("has-error").tooltip("destroy");
			}
		});
		// 	Creo nuevos tooltips para elementos invalidos.
		$.each(errorList, function (index, error) {
			// Destruyo tooltips preexistentes y cargo otros con nuevo contenido.
			if ($(error.element).hasClass("chosen-select")) {
				var chosenElement = $(error.element).next().find("a");
				chosenElement.tooltip("destroy").data("title", error.message).addClass("has-error").tooltip();
			} else {
				$(error.element).tooltip("destroy").data("title", error.message).addClass("has-error").tooltip();
			}
		});
	};
	
	myResetForm = function(form, validator) {
		$("input", form).each(function() {
			$(this).data("title", "").removeClass("has-error").tooltip("destroy");
			$(this).val("");
		});
		if (validator != null) {
			validator.resetForm();
		}
	};
	
	myParseDate = function(date) {
		var myDate = new Date(date);
		var day = ("0" + myDate.getDate()).slice(-2);
		var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
		var year = myDate.getFullYear();
		return day + "/" + month + "/" + year;
	};
	
	validateExpirationDate = function(dd, mm, aaaa) {
		var valid = false;
		var xdata = new Date(aaaa,mm-1,dd);
		if ( ( xdata.getFullYear() === aaaa ) && ( xdata.getMonth() === mm - 1 ) && ( xdata.getDate() === dd ) ){
			var today = new Date();
			if (today <= xdata) {
				valid = true;
			}
		}
		return valid;
	};
	
	// My Custom Validators
	
    jQuery.validator.addMethod("expirationDate", function(value, element) {
        var check = false;
        var re = /^\d{6}$/;
        if (re.test(value)) {
            var dd = parseInt(value.substring(0,2));
            var mm = parseInt(value.substring(2,4));
            var aaaa = parseInt("20"+value.substring(4));
            var xdata = new Date(aaaa,mm-1,dd);
            if ( ( xdata.getFullYear() === aaaa ) && ( xdata.getMonth() === mm - 1 ) && ( xdata.getDate() === dd ) ){
            	var today = new Date();
            	if (today <= xdata) {
            		check = true;
            	}
            }
        }
        return this.optional(element) || check;
    }, "Por favor, escribe una fecha mayor a la fecha del día. (Formato: ddmmaa)");
    
    jQuery.validator.addMethod("minDate", function(value, element, param) {
    	var partsTo = value.split("/");
    	var partsFrom = param.val().split("/");
    	if (partsTo == "" || partsTo == "")
    		return true;
    	var dateF = new Date(partsFrom[2], partsFrom[1] - 1, partsFrom[0]);
    	var dateT = new Date(partsTo[2], partsTo[1] - 1, partsTo[0]);
    	return (dateT >= dateF);
    }, jQuery.format("La Fecha Hasta debe ser mayor o igual a la Fecha Desde."));
    
    jQuery.validator.addMethod("maxDate", function(value, element, param) {
    	var partsFrom = value.split("/");
    	var partsTo = param.val().split("/");
    	if (partsTo == "" || partsTo == "")
    		return true;
    	var dateF = new Date(partsFrom[2], partsFrom[1] - 1, partsFrom[0]);
    	var dateT = new Date(partsTo[2], partsTo[1] - 1, partsTo[0]);
    	return (dateF <= dateT);
    }, jQuery.format("La Fecha Desde debe ser menor o igual a la Fecha Hasta."));
    
    jQuery.validator.addMethod("exactLength", function(value, element, param) {
   	 return this.optional(element) || value.length == param;
   	}, jQuery.format("La longitud debe ser exactamente {0} caracteres."));
    
    // To activate datatables
	$('.datatable').dataTable();

	// To activate chosen-select
	$(".chosen-select").chosen({allow_single_deselect: true});
	
	// To validate chosen-select
	$.validator.setDefaults({ignore: ":hidden:not(select)"});
	
	$(".chosen-select").change(function() {
		if ($(this).val()) {
			$(this).next().find("a").data("title", "").removeClass("has-error").tooltip("destroy");
		}
	});
    
	//a minimum number value on a validate form
	$.validator.addMethod('minValue', function (value, el, param) {
	    return value > param;
	},"Por favor, ingrese un número mayor a cero");
	
	jQuery(document).ready(function() {
		  jQuery("abbr.timeago").timeago();
	});
	
	
});
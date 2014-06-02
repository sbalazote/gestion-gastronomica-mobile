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
			
			if ($(element).hasClass("multipleSelector")) {
				$("div.ms-selection").data("title", "").removeClass("has-error").tooltip("destroy");
			} else {
				$("div.ms-selection").data("title", "").removeClass("has-error").tooltip("destroy");
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
			
			if ($(error.element).hasClass("multipleSelector")) {
				$("div.ms-selection").tooltip("destroy").data("title", error.message).addClass("has-error").tooltip();
			} else {
				$("div.ms-selection").tooltip("destroy").data("title", error.message).addClass("has-error").tooltip();
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
	
	jQuery.validator.addMethod("maxAmount", function(value, element, param) {
    	return (value.length <= param);
    }, jQuery.format("Se han ingresado más de diez Rubros-Subrubros."));
	
	jQuery.validator.addMethod("monthYearDateOnly", function(value, element) {
		var check = false;
		var re = /^\d{1,2}\/\d{4}$/;
		if( re.test(value)) {
			var adata = value.split('/');
			var mm = parseInt(adata[0],10);
			var aaaa = parseInt(adata[1],10);
			var xdata = new Date(aaaa,mm-1,1);
			if ( ( xdata.getFullYear() === aaaa ) && ( xdata.getMonth() === mm - 1 ) ){
				check = true;
			} else {
				check = false;
			}
		} else {
			check = false;
		}
		return this.optional(element) || check;
	}, "Por favor, escriba una fecha que solo contenga mes y año.  (Formato: mm/aaaa)");
	
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
    
    jQuery.validator.addMethod("categoriesRepeated", function(value, element) {
    	var check = true;
    	if (value != null) {
    		if (value.length > 1) {
    			var onlyCategories = [];
    			$.each(value, function (index, element) {
    				var parts = element.split("-");
    				if (parts[1] === undefined) {
    					onlyCategories.push(parts[0]);
    				}
    				else {
    					if (jQuery.inArray(parts[1], onlyCategories) != -1) {
    						check = false;
    					}
    				}
    			});
    		}
    	}
        return check;
    }, "Ha indicado un Rubro que abarca todos sus Subrubros y ademas ha indicado Rubro/Subrubro. Este ultimo es innecesario ya que esta contenido en el primero, retirelo por favor");
    
    jQuery.validator.addMethod("emptyCategoriesSubcategories", function(value, element) {
        var check = true;
        if (value == null) {
            check = false;
        }
        return check;
    }, "Por favor, seleccione al menos un criterio de busqueda con Categoria y/o subcategoria");
    
    jQuery.validator.addMethod("minDate", function(value, element, param) {
    	var partsTo = value.split("/");
    	var partsFrom = param.val().split("/");
    	if (partsTo == "" || partsTo == "")
    		return true;
    	var dateF = new Date(partsFrom[1], partsFrom[0] - 1, 1);
    	var dateT = new Date(partsTo[1], partsTo[0] - 1, 1);
    	dateT.setDate(dateT.getDate() - 365);
    	return (dateT <= dateF);
    }, jQuery.format("El rango de fechas no puede ser superior a un año."));
    
    jQuery.validator.addMethod("maxDate", function(value, element, param) {
    	var partsFrom = value.split("/");
    	var partsTo = param.val().split("/");
    	if (partsTo == "" || partsTo == "")
    		return true;
    	var dateF = new Date(partsFrom[1], partsFrom[0] - 1, 1);
    	var dateT = new Date(partsTo[1], partsTo[0] - 1, 1);
    	dateF.setDate(dateF.getDate() + 365);
    	return (dateF >= dateT);
    }, jQuery.format("El rango de fechas no puede ser superior a un año."));
    
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
	
	$.validator.addMethod('usernameRequired', function (value, el, param) {
		return $.trim(value).length > 0;
	},"El usuario es un dato obligatorio");
	
	$.validator.addMethod('passwordRequired', function (value, el, param) {
		return $.trim(value).length > 0;
	},"La contrasenia es un dato obligatorio");
	
	jQuery.timeago.settings.strings = {
			   prefixAgo: "hace",
			   prefixFromNow: "dentro de",
			   suffixAgo: "",
			   suffixFromNow: "",
			   seconds: "menos de un minuto",
			   minute: "un minuto",
			   minutes: "unos %d minutos",
			   hour: "una hora",
			   hours: "%d horas",
			   day: "un día",
			   days: "%d días",
			   month: "un mes",
			   months: "%d meses",
			   year: "un año",
			   years: "%d años"
			};
	
	jQuery(document).ready(function() {
		  jQuery("abbr.timeago").timeago();
	});
	
	jQuery.validator.addMethod("userOrPassInput", function(value, element) {
		return this.optional(element) || /^[\w\-.@]+$/i.test(value);
	}, 'Por favor ingrese únicamente letras, números, "-", "_", "." o "@"');
	
});
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/form/configuration/save/saveParameter.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveParameter();
	});
</script>

<div id="body">
<div class="content">

<form id="parameterAdministrationForm" action="" onsubmit="return false;">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="configuration.updateParameter"/></h2>
		<input type="hidden" class="form-control" id="idInput" value="${id}">
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		new SaveParameter();
	});
</script>

<div class="row">
	<div class="col-md-12 form-group">
		<label for="restaurantNameInput"><spring:message code="configuration.restaurantName"/></label> 
		<input type="text" class="form-control" id="restaurantNameInput" name="restaurantName" value="${restaurantName}">
	</div>
</div>

<div class="row">
	<div class="col-md-6 form-group">
		<label for="dinnerServicePriceInput"><spring:message code="configuration.dinnerServicePrice"/></label>
		<input type="text" class="form-control" name="dinnerServicePrice" id="dinnerServicePriceInput" value="${dinnerServicePrice}">
	</div>
</div>

<div class="row">
	<div class="col-md-6 form-group">	
		<label for="dinnerServiceActiveSelect"><spring:message code="configuration.dinnerServiceActive"/></label>
		<select class="form-control" id="dinnerServiceActiveSelect">
			<option value="true" ${dinnerServiceActive == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${dinnerServiceActive == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-2 col-md-offset-8">
		<button onclick="location.href='home'" class="btn btn-danger btn-block" id="abort"><span class="glyphicon glyphicon-remove"></span> <spring:message code="common.abort"/></button>
	</div>
	<div class="col-md-2">
		<button class="btn btn-success btn-block" id="confirm"><span class="glyphicon glyphicon-ok"></span> <spring:message code="common.confirm"/></button>
	</div>
</div>
</form>

</div>
</div>
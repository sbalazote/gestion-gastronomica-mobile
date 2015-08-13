<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/form/administration/save/saveCategory.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveCategory();
	});
</script>

<div id="body">
<div class="content">

<form id="categoryAdministrationForm" action="" onsubmit="return false;">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.addCategory"/></h2>
	</div>
</div>

<div class="row">
	<div class="col-md-9 form-group">
		<label for="descriptionInput"><spring:message code="common.description"/></label> 
		<input type="text" class="form-control" id="descriptionInput" name="description">
	</div>
	<div class="col-md-3 form-group">	
		<label for="activeSelect"><spring:message code="common.active"/></label>
		<select class="form-control" id="activeSelect" name="active">
			<option value="true">Si</option>
			<option value="false">No</option>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-2 col-md-offset-8">
		<button onclick="location.href='categoryAdministration'" class="btn btn-danger btn-block" id="abort"><span class="glyphicon glyphicon-remove"></span> <spring:message code="common.abort"/></button>
	</div>
	<div class="col-md-2">
		<button class="btn btn-success btn-block" id="confirm"><span class="glyphicon glyphicon-ok"></span> <spring:message code="common.add.entity"/></button>
	</div>
</div>
</form>

</div>
</div>
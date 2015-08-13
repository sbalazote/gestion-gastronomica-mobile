<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/form/administration/save/saveTable.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveTable();
	});
</script>

<div id="body">
<div class="content">

<form id="tableAdministrationForm" action="" onsubmit="return false;">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.updateTable"/></h2>
		<input type="hidden" class="form-control" id="idInput" value="${id}">
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		new SaveTable();
	});
</script>

<div class="row">
	<div class="col-md-9 form-group">
		<label for="tableNumberInput"><spring:message code="common.table"/></label> 
		<input type="text" class="form-control" id="tableNumberInput" name="tableNumberInput" value="${id}" disabled>
	</div>
	<div class="col-md-3 form-group">	
		<label for="activeSelect"><spring:message code="common.active"/></label>
		<select class="form-control" id="activeSelect" name="active">
			<option value="true" ${active == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${active == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
</div>


<div class="row">
	<div class="col-md-2 col-md-offset-8">
		<button onclick="location.href='tableAdministration'" class="btn btn-default btn-block" name="abort"><span class="glyphicon glyphicon-remove"></span> <spring:message code="common.abort"/></button>
	</div>
	<div class="col-md-2">
		<button class="btn btn-success btn-block" id="confirm"><span class="glyphicon glyphicon-ok"></span> <spring:message code="common.confirm"/></button>
	</div>
</div>

</form>

</div>
</div>
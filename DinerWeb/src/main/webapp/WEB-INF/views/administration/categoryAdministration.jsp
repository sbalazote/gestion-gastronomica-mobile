<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/administration/categoryAdministration.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new CategoryAdministration();
	});
</script>

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.category.menu"/></h2>
	</div>

</div>

<div class="bs-example bs-example-tabs">
	<div class="row">
		<div class="col-md-4 form-group">
			<button class="btn btn-primary btn-block" id="addCategory"><span class="glyphicon glyphicon-plus"></span> <spring:message code="administration.addCategory"/></button>
		</div>
	</div>
</div>

<div id=divTable>
	<table class="table table-striped datatable my-datatable" id="mainTable">
		<thead>
	        <tr>
	            <th><spring:message code="common.id"/></th>
	            <th><spring:message code="common.description"/></th>
	           	<th><spring:message code="common.active"/></th>
	           	<th><spring:message code="common.action"/></th>
	        </tr>
   	 	</thead>
   	 	<tbody id="categoryTableBody">
		</tbody>
	</table>
</div>
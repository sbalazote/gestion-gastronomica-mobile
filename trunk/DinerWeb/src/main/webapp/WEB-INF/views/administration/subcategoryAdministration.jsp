<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/administration/subcategoryAdministration.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new SubcategoryAdministration();
	});
</script>

<div id="body">
<div class="content">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.subcategory.menu"/></h2>
	</div>

</div>
<div id="successMessageDiv" style="display: none;" class="alert alert-success alert-dismissable">
	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong><spring:message code="administration.success.message"/></strong>
</div>

<div class="bs-example bs-example-tabs">
	<div class="row">
		<div class="col-md-4 form-group">
			<button class="btn btn-primary btn-block" id="addSubcategory"><span class="glyphicon glyphicon-plus"></span> <spring:message code="administration.addSubcategory"/></button>
		</div>
	</div>
</div>

<div id=divTable>
	<table class="table table-striped datatable my-datatable" id="mainTable">
		<thead>
	        <tr>
	            <th><spring:message code="common.id"/></th>
	            <th><spring:message code="common.description"/></th>
	            <th><spring:message code="common.category"/></th>
	           	<th><spring:message code="common.active"/></th>
	           	<th><spring:message code="common.action"/></th>
	        </tr>
   	 	</thead>
   	 	<tbody id="categoryTableBody">
		</tbody>
	</table>
</div>

<%-- Confirmación de que se borrará definitivamente --%>
<div class="modal fade" data-backdrop="static" id="deleteConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:250px">
		<div class="modal-content">
			<div class="modal-body">
				<strong><span style="color:red"><spring:message code="administration.entity.delete"/></span></strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="common.no"/></button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="deleteEntityButton"><spring:message code="common.yes"/></button>
			</div>
		</div>
	</div>
</div>


<%-- Advertencia de falla al borrar elemento --%>
<div class="modal fade" data-backdrop="static" id="deleteFailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:250px">
		<div class="modal-content">
			<div class="modal-body">
				<strong><span style="color:red"><spring:message code="administration.entity.delete.fail"/></span></strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="common.accept"/></button>
			</div>
		</div>
	</div>
</div>

</div>
</div>
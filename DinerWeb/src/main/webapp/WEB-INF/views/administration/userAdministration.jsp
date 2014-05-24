<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/administration/userAdministration.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new UserAdministration();
	});
</script>

<div id="body">
	<div class="content">
	
		<div class="row">
			<div class="col-md-9 form-group">
				<h2><spring:message code="administration.user.menu"/></h2>
			</div>
		</div>
		
		<div class="bs-example bs-example-tabs">
			<div class="row">
				<div class="col-md-4 form-group">
					<button class="btn btn-primary btn-block" id="addUser"><span class="glyphicon glyphicon-plus"></span> <spring:message code="administration.addUser"/></button>
				</div>
			</div>
		</div>
		
		<div id=divTable>
			<table class="table table-striped datatable my-datatable" id="mainTable">
				<thead>
			        <tr>
			            <th><spring:message code="common.id"/></th>
			            <th><spring:message code="common.userName"/></th>
			            <th><spring:message code="common.active"/></th>
			           	<th><spring:message code="common.action"/></th>
			        </tr>
		   	 	</thead>
		   	 	<tbody id="userTableBody">
				</tbody>
			</table>
		</div>
	</div>
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

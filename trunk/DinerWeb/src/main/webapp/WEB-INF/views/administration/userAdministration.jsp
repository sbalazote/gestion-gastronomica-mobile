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

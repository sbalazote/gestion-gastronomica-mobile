<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/kitchen/kitchenProductAdministration.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new KitchenProductAdministration();
	});
</script>

<div id="body">
<div class="content">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.product.menu"/></h2>
	</div>

</div>

<div id=divTable>
	<table class="table table-striped datatable my-datatable" id="mainTable">
		<thead>
	        <tr>
	            <th><spring:message code="common.id"/></th>
	            <th><spring:message code="common.description"/></th>
	            <th><spring:message code="common.category"/></th>
	            <th><spring:message code="common.subcategory"/></th>
	           	<th><spring:message code="common.action"/></th>
	        </tr>
   	 	</thead>
   	 	<tbody id="productTableBody">
		</tbody>
	</table>
</div>

<%-- Confirmación de que se modificará el estado del producto --%>
<div class="modal fade" data-backdrop="static" id="stockConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog"">
		<div class="modal-content">
			<div class="modal-body">
				<strong><span><spring:message code="administration.entity.changeStockState"/></span></strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="common.no"/></button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="stockStateChange"><spring:message code="common.yes"/></button>
			</div>
		</div>
	</div>
</div>


</div>
</div>
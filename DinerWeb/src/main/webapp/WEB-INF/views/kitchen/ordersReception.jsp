<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/kitchen/ordersReception.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new OrdersReception();
	});
</script>

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.kitchen.orders"/></h2>
	</div>
</div>

<div id=divTable>
	<table class="table table-striped datatable my-datatable" id="mainTable">
		<thead>
	        <tr>
	            <th><spring:message code="common.id"/></th>
	            <th><spring:message code="common.description"/></th>
	           	<th><spring:message code="common.amount"/></th>
	           	<th><spring:message code="common.comment"/></th>
	           	<th><spring:message code="common.date"/></th>
	           	<th><spring:message code="common.state"/></th>
	           	<th><spring:message code="common.waiter"/></th>
	           	<th><spring:message code="common.action"/></th>
	        </tr>
   	 	</thead>
   	 	<tbody id="categoryTableBody">
		</tbody>
	</table>
</div>
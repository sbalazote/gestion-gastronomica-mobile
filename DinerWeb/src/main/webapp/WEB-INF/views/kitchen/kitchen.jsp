<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="js/form/kitchen/kitchen.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new Kitchen();
	});
</script>

<div id="large-body">
<div class="content">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="common.orders"/></h2>
	</div>
</div>

<div id=divTable>
	<table class="table table-striped datatable my-datatable" id="mainTable">
		<thead>
	        <tr>
	            <th><spring:message code="common.id"/></th>
	            <th><spring:message code="common.table"/></th>
	            <th><spring:message code="common.description"/></th>
	           	<th><spring:message code="common.amount"/></th>
	           	<th><spring:message code="common.comment"/></th>
	           	<th><spring:message code="common.waiter"/></th>
	           	<th><spring:message code="common.requestDate"/></th>
	           	<th><spring:message code="common.state"/></th>
	           	<th><spring:message code="common.action"/></th>
	           	<th><spring:message code="common.estimatedTime"/></th>
	           	<th><spring:message code="common.time"/></th>
	        </tr>
   	 	</thead>
   	 	<tbody>
		</tbody>
	</table>
</div>

</div>
</div>
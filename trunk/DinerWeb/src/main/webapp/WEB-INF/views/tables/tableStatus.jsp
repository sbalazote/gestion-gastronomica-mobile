<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
	src="js/form/tables/tableStatus.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new TableStatus();
	});
</script>

<div id="large-body">
	<div class="content">
		<div class="row">
			<div class="col-md-9 form-group">
				<h2>
					<spring:message code="common.table.status.title" />
				</h2>
			</div>
		</div>
		<div id="successMessageDiv" style="display: none;" class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				<strong><spring:message code="administration.success.message"/></strong>
		</div>
		<div id=divTable>
			<table class="table table-striped tableDatatable my-datatable"
				id="mainTable">
				<thead>
					<tr>
						<th><spring:message code="common.table" /></th>
						<th><spring:message code="common.attached" /></th>
						<th><spring:message code="common.state" /></th>
						<th><spring:message code="common.waiter" /></th>
						<th><spring:message code="common.action" /></th>
					</tr>
				</thead>
				<tbody id="tableBody">
				</tbody>
			</table>
		</div>
	</div>
</div>

<%-- Impresión de Comprobante--%>
<div class="modal fade" data-backdrop="static" id="printOrderModal">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="modal-title"><spring:message code="administration.printOrderModal.title" /></h4>
		</div>
			<div class="modal-body">
				<div class="row">
					<div style="text-align: center;">
						<h3>${restaurantName}</h3>
					</div>
				</div>
				<div class="row">
					<div style="text-align: center;">
						<h3>${restaurantAddress}</h3>
					</div>
				</div>
				<div class="row">
					<div style="text-align: center;">
						<h5 id="modalTableDetail"></h5>
					</div>
				</div>
				<div class="row">
					<div style="text-align: center;">
						<h5 id="modalDate"></h5>
					</div>
				</div>
				<div class="row">
					<div class="col-md-9">
						<h5>Detalle del Pedido</h5>
					</div>
				</div>
				<div>
				<table class="table" id="tableModal">
					<thead>
						<tr>
							<th><spring:message code="common.id" /></th>
							<th><spring:message code="common.product" /></th>
							<th><spring:message code="common.amount" /></th>
							<th><spring:message code="common.price" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="common.abort" /></button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="printButton">
					<spring:message code="administration.printReceipt" />
				</button>
			</div>
		</div>
	</div>
</div>

<%-- Confirmación de Medio de Pago --%>
<div class="modal fade" data-backdrop="static" id="paymentMediaModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title"><spring:message code="administration.paymentMediaModal.title" /></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div style="text-align: center;">
						<h5 id="modalPaymentTitle"></h5>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 form-group">
						<label for="total"><spring:message code="common.total" /></label>
						<input type="text" class="form-control" id="totalInput"
							name="totalInput" disabled>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 form-group">
						<label for="cashInput"><spring:message
								code="common.paymentMedia" /></label> <select class="form-control"
							id="paymentMediaSelect" name="paymentMedia">
							<option value="1">Tarjeta de Debito</option>
							<option value="2">Tarjeta de Credito</option>
							<option value="3">Efectivo</option>
						</select>
					</div>
				</div>
				<div id="cashDiv" style="display: none">
					<div class="row">
						<div class="col-md-12 form-group">
							<label for="cashInput"><spring:message	code="common.cash" /></label> 
							<input type="text" class="form-control"	id="cashInput" name="cash">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group">
							<label for="changeInput"><spring:message code="common.change" /></label> 
							<input type="text" class="form-control"	id="changeInput" name="change" disabled>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="common.abort" /></button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="confirmPaymentMediaButton">
					<spring:message code="common.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>

<%-- Modal Union de Mesas --%>
<div class="modal fade" data-backdrop="static" id="tableAttachmentModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title"><spring:message code="administration.tableAttachmentModal.title" /></h4>
			</div>
			<div class="modal-body">
				<div class="col-md- form-group">	
					<div class="ms-container">
						<select multiple="multiple" id="my-select" name="my-select[]" >

						</select>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="common.abort" /></button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="confirmTableAttachmentButton">
					<spring:message code="common.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>

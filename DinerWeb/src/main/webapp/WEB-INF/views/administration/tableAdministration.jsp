<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
	src="js/form/administration/tableAdministration.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new TableAdministration();
	});
</script>

<div id="body">
	<div class="content">

		<div class="row">
			<div class="col-md-9 form-group">
				<h2>
					<spring:message code="administration.table.menu" />
				</h2>
			</div>
		</div>

		<div id=divTable>
			<table class="table table-striped datatable my-datatable"
				id="mainTable">
				<thead>
					<tr>
						<th><spring:message code="common.table" /></th>
						<th><spring:message code="common.waiter" /></th>
						<th><spring:message code="common.action" /></th>
					</tr>
				</thead>
				<tbody id="tableBody">
				</tbody>
			</table>
		</div>

		<%-- Impresión de Comprobante--%>
		<div class="modal fade" data-backdrop="static" id="printOrderModal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-9">
								<h3 id="modalTitle"></h3>
							</div>
						</div>
						<div class="row">
							<div style="text-align: center;">
								<h3>${restaurantName}</h3>
							</div>
						</div>
						<div class="row">
							<div class="col-md-9">
								<h5 id="modalDate"></h5>
							</div>
						</div>
						<div class="row">
							<div class="col-md-9">
								<h5>Detalle del Pedido</h5>
							</div>
						</div>
						<table class="table table-striped" id="tableModal">
							<thead>
								<tr>
									<th><spring:message code="common.id" /></th>
									<th><spring:message code="common.product" /></th>
								</tr>
							</thead>
							<tbody id="orderBody">
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							id="printButton">
							<spring:message code="administration.printReceipt" />
						</button>
					</div>
				</div>
			</div>
		</div>

		<%-- Confirmación de Medio de Pago --%>
		<div class="modal fade" data-backdrop="static" id="paymentMediaModal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-9">
								<h3 id="modalPaymentTitle"></h3>
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
									<label for="cashInput"><spring:message
											code="common.cash" /></label> <input type="text" class="form-control"
										id="cashInput" name="cash">
								</div>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							id="confirmPaymentMediaButton">
							<spring:message code="common.confirm" />
						</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript"
	src="js/printThis.js"></script>
<script type="text/javascript"
	src="js/form/administration/couponsAdministration.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		new CouponsAdministration();
	});
</script>

<div id="body">
	<div class="content">
		<form id="couponsAdministrationForm" action=""
			onsubmit="return false;">
			<div class="row">
				<div class="col-md-9 form-group">
					<h1>
						<spring:message code="administration.coupons.menu" />
					</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3 form-group">
					<label for="descriptionInput"><spring:message
							code="administration.coupons.description" /></label> <input type="text"
						class="form-control" id="descriptionInput" name="description">
				</div>
				<div class="col-md-3 form-group">
					<label for="percentageSelect"><spring:message
							code="administration.coupons.percentage" /></label> <select
						class="form-control" id="percentageSelect" name="percentage">
						<option value="true">0.25</option>
						<option value="false">0.50</option>
					</select>
				</div>
				<div class="col-md-3 form-group">
					<label for="expirationDateInput"><spring:message
							code="administration.coupons.expirationDate" /></label>
					<div class="input-group">
						<input type="text" class="form-control"
							name="expirationDate" id="expirationDateInput"> <span
							class="input-group-addon" id="expirationDateButton"
							style="cursor: pointer;"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
				<div class="col-md-3 form-margin">
					<button class="btn btn-info btn-block"
						id="generateQRCodeButton">
						<span class="glyphicon glyphicon-picture"></span>
						<spring:message code="administration.coupons.generateQRCode" />
					</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-2 col-md-offset-5">
					<img id="QRCodeImage" name="QRCode" src="images/no_image.png" style="width: 100%;">
				</div>
			</div>
			<div class="row">
				<div class="col-md-2 col-md-offset-5">
					<button class="btn btn-info btn-block" id="printQRCodeImageButton">
						<span class="glyphicon glyphicon-print"></span>
						<spring:message code="administration.coupons.printQRCodeImage" />
					</button>
				</div>
			</div>
			<br> <br> <br>
			<div class="row">
				<div class="col-md-2 col-md-offset-8">
					<button class="btn btn-danger btn-block" id="abort">
						<span class="glyphicon glyphicon-remove"></span>
						<spring:message code="common.abort" />
					</button>
				</div>
				<div class="col-md-2">
					<button class="btn btn-success btn-block" id="confirm">
						<span class="glyphicon glyphicon-ok"></span>
						<spring:message code="common.accept" />
					</button>
				</div>
			</div>
		</form>
	</div>
</div>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="js/form/administration/save/saveCoupon.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveCoupon();
	});
</script>

<div id="body">
	<div class="content">
		<form id="couponsAdministrationForm" action=""
			onsubmit="return false;">
			<div class="row">
				<div class="col-md-9 form-group">
					<h1>
						<spring:message code="administration.addCoupon" />
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
						<option value="0.05">5 %</option>
						<option value="0.10">10 %</option>
						<option value="0.15">15 %</option>
						<option value="0.20">20 %</option>
						<option value="0.25">25 %</option>
						<option value="0.30">30 %</option>
						<option value="0.35">35 %</option>
						<option value="0.40">40 %</option>
						<option value="0.45">45 %</option>
						<option value="0.50">50 %</option>
						<option value="0.55">55 %</option>
						<option value="0.60">60 %</option>
					</select>
				</div>
				<div class="col-md-3 form-group">
					<label for="startingDateInput"><spring:message
							code="administration.coupons.startingDate" /></label>
					<div class="input-group">
						<input type="text" class="form-control"
							name="startingDate" id="startingDateInput"> <span
							class="input-group-addon" id="startingDateButton"
							style="cursor: pointer;"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
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
			</div>
			<br>
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
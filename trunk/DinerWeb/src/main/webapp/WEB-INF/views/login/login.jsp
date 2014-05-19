<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="js/form/login/login.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new Login();
	});
</script>

<div id="body">
	<div class="content">

		<form id="loginForm" action="j_spring_security_check" method="post">
			<div class="row">
				<div class="form-group col-md-4 col-md-offset-4">
					<h2 class="form-signin-heading">
						<spring:message code="login.enter.data" />
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-4 col-md-offset-4">
					<input type="text" class="form-control" name="j_username"
						placeholder="<spring:message code="login.user.placeholder"/>"
						autofocus>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-4 col-md-offset-4">
					<input type="password" class="form-control" name="j_password"
						placeholder="<spring:message code="login.password.placeholder"/>">
				</div>
			</div>
			<%--<label class="checkbox">
			<input type="checkbox" value="recordarme"><spring:message code="login.remember.me"/>
		</label>
		 --%>
			<div class="row">
				<div class="form-group col-md-4 col-md-offset-4">
					<button class="btn btn-primary btn-block" id="loginButton">
						<spring:message code="login.login" />
					</button>
				</div>
			</div>
			
			<c:if test="${not empty error}">
				<div class="row">
					<div class="form-group col-md-12">
						<div class="error">${error}</div>
					</div>
				</div>
			</c:if>
		</form>

	</div>
</div>
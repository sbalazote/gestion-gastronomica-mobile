<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/form/administration/save/saveUser.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveUser();
	});
</script>

<form id="userAdministrationForm" action="" onsubmit="return false;">

<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="common.user"/></h2>
	</div>
</div>

<div class="row">
	<div class="col-md-6 form-group">
		<label for="nameInput"><spring:message code="common.name"/></label> 
		<input type="text" class="form-control" id="nameInput" name="name">
	</div>
	<div class="col-md-6 form-group">	
		<label for="activeSelect"><spring:message code="common.active"/></label>
		<select class="form-control" id="activeSelect" name="active">
			<option value="true">Si</option>
			<option value="false">No</option>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-6 form-group">
		<label for="passwordInput"><spring:message code="common.password"/></label> 
		<input type="password" class="form-control" id="passwordInput" name="password">
	</div>
	<div class="col-md-6 form-group">
		<label for="passwordInputCheck"><spring:message code="common.repeatPassword"/></label> 
		<input type="password" class="form-control" id="passwordInputCheck" name="passwordCheck">
	</div>
</div>


<div class="row">
	<div class="col-md-4 form-group">	
		<label for="role"><spring:message code="common.roles"/></label>
	</div>
</div>

<div>
	<div class="col-md- form-group">	
		<div class="ms-container">
			<select multiple="multiple" id="my-select" name="my-select[]">
				<c:forEach items="${roles}" var="role" varStatus="status">
					<option value="${role.id}"><c:out value="${role.id} - ${role.description}"></c:out></option>
				</c:forEach>
			</select>
		</div>
	</div>
</div>
		
<div class="row">
	<div class="col-md-2 col-md-offset-8">
		<button onclick="location.href='userAdministration.do'" class="btn btn-danger btn-block" id="abort"><span class="glyphicon glyphicon-remove"></span> <spring:message code="common.abort"/></button>
	</div>
	<div class="col-md-2">
		<button class="btn btn-success btn-block" id="confirm"><span class="glyphicon glyphicon-ok"></span> <spring:message code="common.add.entity"/></button>
	</div>
</div>

</form>

<%-- Advertencia código de elemento existente --%>
<div class="modal fade" data-backdrop="static" id="existingElementModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:250px">
		<div class="modal-content">
			<div class="modal-body">
				<strong><span style="color:red"><spring:message code="administration.entity.existingElement"/></span></strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="common.accept"/></button>
			</div>
		</div>
	</div>
</div>
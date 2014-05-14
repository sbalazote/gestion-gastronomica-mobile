<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="js/form/administration/save/saveProduct.js" /></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SaveProduct();
	});
</script>

<div id="body">
<div class="content">

<form id="productAdministrationForm" action="" onsubmit="return false;">


<div class="row">
	<div class="col-md-9 form-group">
		<h2><spring:message code="administration.updateProduct"/></h2>
		<input type="hidden" class="form-control" id="idInput" value="${id}">
		<input type="hidden" class="form-control" id="idSubcategory" value="${subcategoryId}">
	</div>
</div>

<div class="row">
	<div class="col-md-12 form-group">
		<label for="descriptionInput"><spring:message code="common.description"/></label> 
		<input type="text" class="form-control" id="descriptionInput" name="description" value="${description}">
	</div>
</div>

<div class="row">
	<div class="col-md-12 form-group">
		<label for="categorySelect"><spring:message code="common.category"/></label>
		<select id="categorySelect" name="category" class="form-control chosen-select" data-placeholder="<spring:message code='common.select.option'/>">
			<option value=""></option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.id}" ${categoryId == category.id ? 'selected' : ''}><c:out value="${category.id}"></c:out> - <c:out value="${category.description}"></c:out></option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-12 form-group">
		<label for="subcategorySelect"><spring:message code="common.subcategory"/></label>
		<select id="subcategorySelect" name="subcategory" class="form-control chosen-select" data-placeholder="<spring:message code='common.select.option'/>">
			<option value=""></option>
			<c:forEach items="${subcategories}" var="subcategory">
				<option value="${subcategory.id}" ${subcategoryId == subcategory.id ? 'selected' : ''}><c:out value="${subcategory.id}"></c:out> - <c:out value="${subcategory.description}"></c:out></option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-2 form-group">
		<label for="priceInput"><spring:message code="common.price"/></label>
		<input type="text" class="form-control" name="price" id="priceInput" value="${price}">
	</div>
	
	<div class="col-md-2 form-group">
		<label for="estimatedTimeInput"><spring:message code="common.estimatedTime"/></label>
		<input type="text" class="form-control" name="estimatedTime" id="estimatedTimeInput" value="${estimatedTime}">
	</div>

	<div class="col-md-2 form-group">
		<label for="activeSelect"><spring:message code="common.active"/></label>
		<select class="form-control" id="activeSelect">
			<option value="true" ${active == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${active == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
	
	<div class="col-md-2 form-group">
		<label for="celiacAllowedSelect"><spring:message code="common.celiacAllowed"/></label>
		<select class="form-control" id="celiacAllowedSelect">
			<option value="true" ${celiacAllowed == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${celiacAllowed == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
	
	<div class="col-md-2 form-group">
		<label for="kitchenSelect"><spring:message code="common.kitchen"/></label>
				<select class="form-control" id="kitchenSelect" name="kitchen">
			<option value="true" ${kitchen == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${kitchen == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
	
	<div class="col-md-2 form-group">
		<label for="stockSelect"><spring:message code="common.stockAvailable"/></label>
				<select class="form-control" id="stockSelect" name="stock">
			<option value="true" ${stock == 'true' ? 'selected' : ''}>Si</option>
			<option value="false" ${stock == 'false' ? 'selected' : ''}>No</option>
		</select>
	</div>
</div>

<div class="row">
	<div class="col-md-2 col-md-offset-8">
		<button onclick="location.href='productAdministration'" class="btn btn-danger btn-block" id="abort"><span class="glyphicon glyphicon-remove"></span> <spring:message code="common.abort"/></button>
	</div>
	<div class="col-md-2">
		<button class="btn btn-success btn-block" id="confirm"><span class="glyphicon glyphicon-ok"></span> <spring:message code="common.confirm"/></button>
	</div>
</div>
</form>

</div>
</div>
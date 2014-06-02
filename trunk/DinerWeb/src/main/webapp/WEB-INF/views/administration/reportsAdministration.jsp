<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="js/form/administration/reports/salesReport.js"></script>
<script type="text/javascript" src="js/form/administration/reports/productRankingReport.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		new SalesReport();
		new ProductRankingReport();
	});
</script>

<div id="body">
	<div class="content">
		<div class="row">
			<div class="col-md-9 form-group">
				<h1><spring:message code="administration.reports.title"/></h1>
			</div>
		</div>
		
		<div class="tabs">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#sales" data-toggle="tab"><spring:message code="administration.reports.sales"/></a></li>
				<li><a href="#productRanking" data-toggle="tab"><spring:message code="administration.reports.productRanking"/></a></li>
			</ul>
			<br>
		
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="sales">
					<form id="salesReportForm" action="" onsubmit="return false;">
						<div class="row">
							<div class="col-md-4 form-group">
								<label for="salesDateFromInput"><spring:message code="common.dateFromMMYYYY"/></label>
								<div class="input-group">
									<input type="text" class="form-control monthYearCalendar" name="salesDateFrom" id="salesDateFromInput">
									<span class="input-group-addon" id="salesDateFromButton" style="cursor:pointer;">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
							<div class="col-md-4 form-group">
								<label for="salesDateToInput"><spring:message code="common.dateToMMYYYY"/></label>
								<div class="input-group">
									<input type="text" class="form-control monthYearCalendar" name="salesDateTo" id="salesDateToInput">
									<span class="input-group-addon" id="salesDateToButton" style="cursor:pointer;">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
							<div class="col-md-2 form-margin">
								<button class="btn btn-info btn-block" type="submit" id="salesCleanButton">
									<span class="glyphicon glyphicon-trash"></span>
									<spring:message code="common.clean" />
								</button>
							</div>
							<div class="col-md-2 form-margin">
								<button class="btn btn-success btn-block" type="submit" id="salesSearchButton">
									<span class="glyphicon glyphicon-search"></span>
									<spring:message code="common.search" />
								</button>
							</div>
						</div>
					</form>
					<div id="salesTableDiv">
						<table class="table table-striped tableDatatable my-datatable" id="salesTable">
							<thead>
								<tr>
									<th><spring:message code="common.date" /></th>
									<th><spring:message code="administration.reports.sales.total" /></th>
								</tr>
							</thead>
							<tbody id="salesTableBody">
							</tbody>
						</table>
					</div>
					<div id="salesHighchartDiv" style="width:100%; height:400px;"></div>
				</div>
					
				<div class="tab-pane fade" id="productRanking">
						<form id="productRankingReportForm" action="" onsubmit="return false;">
							<div class="row">
								<div class="col-md-4 form-group">
									<label for="productRankingDateFromInput"><spring:message code="common.dateFrom"/></label>
									<div class="input-group">
										<input type="text" class="form-control" name="productRankingDateFrom" id="productRankingDateFromInput">
										<span class="input-group-addon" id="dateFromButton" style="cursor:pointer;">
											<span class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
								<div class="col-md-4 form-group">
									<label for="productRankingDateToInput"><spring:message code="common.dateTo"/></label>
									<div class="input-group">
										<input type="text" class="form-control" name="productRankingDateTo" id="productRankingDateToInput">
										<span class="input-group-addon" id="dateToButton" style="cursor:pointer;">
											<span class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
								<div class="col-md-2 form-margin">
									<button class="btn btn-info btn-block" type="submit" id="productRankingCleanButton">
										<span class="glyphicon glyphicon-trash"></span>
										<spring:message code="common.clean" />
									</button>
								</div>
								<div class="col-md-2 form-margin">
									<button class="btn btn-success btn-block" type="submit" id="productRankingSearchButton">
										<span class="glyphicon glyphicon-search"></span>
										<spring:message code="common.search" />
									</button>
								</div>
							</div>
						
						<div class="row">
							<div class="col-md-4 form-group">	
								<label for="role"><spring:message code="common.categorySubcategory"/></label>
							</div>
						</div>
						<div>
							<div class="col-md- form-group">	
								<div class="ms-container">
									<select class="multipleSelector" multiple="multiple" id="categoriesSubcategoriesSelect" name="categoriesSubcategories">
										<c:forEach items="${categories}" var="category" varStatus="status">
											<option value="${category.id}"><c:out value="${category.description} "></c:out></option>
										</c:forEach>
										<c:forEach items="${categoriesSubcategories}" var="categoriesSubcategories" varStatus="status">
											<option value="${categoriesSubcategories.categoryId}-${categoriesSubcategories.subcategoryId}"><c:out value="${categoriesSubcategories.categoryDescription} / ${categoriesSubcategories.subcategoryDescription}"></c:out></option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						</form>
						<div id=productRankingTableDiv>
							<table class="table table-striped reassignTablesdatatable my-datatable" id="productRankingTable">
								<thead>
									<tr>
										<th><spring:message code="common.categories" /></th>
										<th><spring:message code="common.subcategories" /></th>
										<th><spring:message code="common.products" /></th>
										<th><spring:message code="administration.reports.productRanking.total" /></th>
									</tr>
								</thead>
								<tbody id="productRankingTableBody">
								</tbody>
							</table>
						</div>
						<div id="chartTabs" class="tabs">
							<ul id="productRankingTab" class="nav nav-tabs">
								<li class="active"><a href="#productRankingChart" data-toggle="tab"><spring:message code="administration.reports.productRankingChart"/></a></li>
								<li><a href="#productRankingParetoChart" data-toggle="tab"><spring:message code="administration.reports.productRankingParetoChart"/></a></li>
							</ul>
							<br>
							<div id="myTabContent2" class="tab-content">
								<div class="tab-pane fade in active" id="productRankingChart">
									<div id="productRankingChartDiv" style="width:100%; height:400px;"></div>
								</div>
								<div class="tab-pane fade" id="productRankingParetoChart">
									<div id="productRankingParetoChartDiv" style="width:100%; height:400px;"></div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</div>
				</div>
</div>

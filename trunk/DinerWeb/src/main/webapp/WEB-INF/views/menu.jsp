<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default" role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-ex1-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="home"><spring:message
				code="common.appName" /></a>
	</div>

	<div class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav">

			<sec:authorize access="hasRole('ENTITY_ADMINISTRATION')">
				<li class="activable dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"><spring:message code="common.configuration" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<a href="updateParameter"><spring:message code="common.parameters" /></a>
							</li>
						</ul>
				</li>
			</sec:authorize>

			<sec:authorize access="hasRole('ENTITY_ADMINISTRATION')">
				<li class="activable dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"><spring:message code="common.administration" /> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<a href="categoryAdministration"><spring:message code="common.categories" /></a>
						</li>
						<li>
							<a href="subcategoryAdministration"><spring:message code="common.subcategories" /></a>
						</li>
						<li>
							<a href="productAdministration"><spring:message code="common.products" /></a>
						</li>
						<li>
							<a href="tableAdministration"><spring:message code="common.tables" /></a>
						</li>
						<li>
							<a href="userAdministration"><spring:message code="common.users" /></a>
						</li>
						<li>
							<a href="reportsAdministration"><spring:message code="common.reports" /></a>
						</li>
						<li>
							<a href="couponsAdministration"><spring:message code="common.coupons" /></a>
						</li>
					</ul>
				</li>
			</sec:authorize>

			<sec:authorize access="hasRole('KITCHEN')">
				<ul class="nav navbar-nav">
					<li class="activable dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><spring:message code="common.kitchen" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<a href="kitchen"><spring:message code="common.requests" /></a>
							</li>
							<li>
								<a href="kitchenProductAdministration"><spring:message code="common.products" /></a>
							</li>
						</ul>
					</li>
				</ul>
			</sec:authorize>

			<sec:authorize access="hasRole('BAR')">
				<ul class="nav navbar-nav">
					<li class="activable dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><spring:message code="common.bar" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<a href="bar"><spring:message code="common.requests" /></a>
							</li>
							<li>
								<a href="barProductAdministration"><spring:message code="common.products" /></a>
							</li>
						</ul>
					</li>
				</ul>
			</sec:authorize>
		
			<sec:authorize access="hasRole('ENTITY_ADMINISTRATION')">
				<li class="activable dropdown">
					<a href="tableStatus"><spring:message code="common.tables" /></a>
				</li>
			</sec:authorize>
		
		</ul>

		<strong><a class="logout-button" href="j_spring_security_logout">Salir</a></strong>

	</div>
</nav>
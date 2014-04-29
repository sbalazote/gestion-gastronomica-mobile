<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">  
<html>
	<head>
		<title><spring:message code="common.appName"/></title>
		<link rel="shortcut icon" href=images/worlwide_drugstore_logo_c.png type="image/png">
		
	    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css">
	    <link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<link rel="stylesheet" type="text/css" href="css/multi-select.css">
		<link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css">
		<link rel="stylesheet" type="text/css" href="css/chosen.css">
		<link rel="stylesheet" type="text/css" href="css/chosen-bootstrap.css">		
		
	    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
	    <script type="text/javascript" src="js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/additional-methods.min.js"></script>
	    <script type="text/javascript" src="js/localization/messages_es.js"></script>
	    <script type="text/javascript" src="js/localization/jquery.ui.datepicker-es.min.js"></script>
	    <script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/jquery.multi-select.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables-10beta.min.js"></script>
		<script type="text/javascript" src="js/datatables.esp.js"></script>
		<script type="text/javascript" src="js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
		<script type="text/javascript" src="js/dateFormatter.js"></script>
		<script type="text/javascript" src="js/jquery.timeago.js"></script>
	</head>
	<body>
		<div id="wrap">
	        <div id="menu">
	            <tiles:insertAttribute name="menu" />
	        </div>
	        <tiles:insertAttribute name="body" />
	    </div>
        <div id="footer">
            <tiles:insertAttribute name="footer" />
        </div>
	</body>
</html>
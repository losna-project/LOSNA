<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	type="text/javascript"></script>
<meta charset="ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title>Acheter des crédits</title>
</head>
<body>
<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
		<div class="container">
			<div class="container-fluid">
			
<div align="center">
	<form action="BuyCredits" method=post>
	<div>
		<label for="credits"><fmt:message key="msg_indiq_nbre_credits"></fmt:message> :</label><input type="text" id="credits" name="credits" class="form-control col-md-1" required/></div><br>
		<button type="submit" value="calculate" name="submit" class="btn btn-info"><fmt:message key="msg_enregistrer"></fmt:message></button>
	</form>
	<br>
	<form action="PaymentForm" method=get 	${cost eq 0  ? 'hidden="true"' : ''}>
	<div>
		<label for="price"><fmt:message key="msg_prix_total"></fmt:message> : ${cost} euro</label>
		<input type="hidden" value="${cost}"/>
		<input type="hidden" value="${credits}"/></div>
		<button type="submit" name="submit" class="btn btn-info"><fmt:message key="msg_paiement"></fmt:message></button>
	</form>
</div>
</fmt:bundle>
</body>
</html>
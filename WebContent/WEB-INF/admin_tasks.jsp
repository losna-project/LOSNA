<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>Gérer l'utilisateur</title>
</head>
<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
		<div class="container">
			<div class="container-fluid">

				<h1>
					<fmt:message key="msg_gerer_utilisateur"></fmt:message>
				</h1>
				<p>${ messageadmin }</p>
				<form action="AdminTasks" method="POST">
					<div>
						<input type="hidden" name="user_id" value="${user_id}" /> <label
							for="accountaction"><fmt:message
								key="msg_desact_utilisateur"></fmt:message></label>
						<button type="submit" class="btn btn-outline-info btn-block"
							value="Deactivate" name="accountaction">
							<fmt:message key="msg_desact"></fmt:message>
						</button>
					</div>
					<div>
						<input type="hidden" name="user_id" value="${user_id}" /> <label
							for="accountaction"><fmt:message
								key="msg_suppr_utilisateur"></fmt:message></label>
						<button type="submit" class="btn btn-outline-info btn-block"
							value="DeleteAccount" name="accountaction">
							<fmt:message key="msg_supprimer"></fmt:message>
						</button>
					</div>
				</form>
				<a href="AuctionList"><button class="btn btn-info"
						value="Return">
						<fmt:message key="msg_retour"></fmt:message>
					</button></a>
	</fmt:bundle>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
<title>Insert title here</title>
</head>
<body>
<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	<nav id="navbar"
		class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
		<a href="AuctionList" class="navbar-brand"><img
			src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
		<ul class="nav navbar-nav mr-auto"></ul>
		<ul class="navbar-nav flex-row">
			<li class="nav-item"><a href="BidderList" class="nav-link px-2"><fmt:message key="msg_enchere"></fmt:message></a>
			<li class="nav-item"><a href="NewAuction" class="nav-link px-2"
				${loggedInUser.status == 'ban' ? 'onclick="myFunction()"' : ''}><fmt:message key="msg_new_auction"></fmt:message></a></li>
			<li class="nav-item"><a href="ProfileDetailsUser"
				class="nav-link px-2"><fmt:message key="msg_profil"></fmt:message></a></li>
			<li class="nav-item"><a href="Disconnect" class="nav-link px-2"><fmt:message key="msg_deconnexion"></fmt:message></a></li>
		</ul>
	</nav>
	
	<h3 id="titreListe"><fmt:message key="msg_titre_liste"></fmt:message></h3>
	<div class="container">
		<div class="row justify-content-center">
			<table id="tableauEncherisseur" class="table table-striped">
				<thead>
					<tr>
						<th>Article</th>
						<th><fmt:message key="msg_montant"></fmt:message></th>
						<th><fmt:message key="msg_encherisseur"></fmt:message></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="e" items="${bidderList}">
						<tr>
							<td>${e.selectByNoProduct(e.no_article).getNom_article()}</td>
							<td>${e.montant_enchere}points</td>
							<td>${e.selectUser(e.no_utilisateur).getPseudo()}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</fmt:bundle>
</body>
</html>
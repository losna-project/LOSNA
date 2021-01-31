<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Détail vente</title>
</head>
<body>
<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	<div class="container">
		<div class="container-fluid">

			<nav id="navbar"
				class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
				<a href="AuctionList" class="navbar-brand"><img
					src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
				<ul class="nav navbar-nav mr-auto"></ul>
				<ul class="navbar-nav flex-row">
					<li class="nav-item"><a href="NewAuction"
						id="navnouvellevente" class="nav-link px-2"><fmt:message key="msg_new_auction"></fmt:message></a></li>
					<li class="nav-item"><a href="ProfileDetailsUser"
						id="nav_nav_monprofil" class="nav-link px-2"><fmt:message key="msg_profil"></fmt:message></a></li>
					<li class="nav-item"><a href="Disconnect"
						class="nav-link px-2"><fmt:message key="msg_deconnexion"></fmt:message></a></li>
				</ul>
			</nav>
			<br>
		</div>


		<div id="presentation">
			<div class="col-lg-12 text-center">
				<c:choose>
					<c:when test="${isBefore==1 && aquereur==noUser}">
						<h2><fmt:message key="msg_remporte"></fmt:message></h2>
					</c:when>
					<c:when test="${isBefore==1 && user.no_utilisateur==noUser}">
						<h2>${nomAquereure} <fmt:message key="msg_remporte2"></fmt:message></h2>
					</c:when>
					<c:when test="${isBefore==0}">
						<h1><fmt:message key="msg_titre_details"></fmt:message></h1>
					</c:when>
				</c:choose>
			</div>
		</div>
		<br>

		<div>
			<div class="row">
				<div class="col-lg-4">
					<img src="${product.images}" alt="Card image">
				</div>

				<div class="col-lg-8">

					<h3>${product.nom_article}</h3>
					<br> <label id="labelauctiondetail">Description : </label>
					<textareas> ${product.description}</textareas>
					<br>

					<c:if test="${isBefore==0}">
						<label id="labelauctiondetail"><fmt:message key="msg_categorie"></fmt:message> : </label>
						<textareas> ${category.libelle}</textareas>
						<br>
					</c:if>
					<label id="labelauctiondetail"><fmt:message key="msg_best"></fmt:message> : </label>
					<textareas> ${auction.montant_enchere}</textareas>
					<br> <label id="labelauctiondetail"><fmt:message key="msg_prix_initial"></fmt:message> : </label>
					<textareas> ${product.prix_initial}</textareas>
					<br>

					<c:if
						test="${isBefore==0 ||isBefore==1 && user.no_utilisateur==noUser}">
						<label id="labelauctiondetail"><fmt:message key="msg_fin"></fmt:message> : </label>
						<textareas> <tags:localDate date="${product.date_fin_encheres}" /></textareas>
						<br>

					</c:if>
					<label id="labelauctiondetail"><fmt:message key="msg_retrait"></fmt:message> : </label>
					<textareas> ${deliveryAddress.rue} </textareas>
					<br> <label id="labelauctiondetail"></label>
					<textareas> ${deliveryAddress.code_postal} -
					${deliveryAddress.ville} </textareas>
					<br> <label id="labelauctiondetail"><fmt:message key="msg_vendeur"></fmt:message> : </label>
					<textareas> ${user.pseudo} </textareas>
					<br>
					<c:if test="${isBefore==1 && aquereur==noUser}">
						<!-- <liTel :>${user.telephone}</li> -->
					</c:if>


					<c:if test="${isBefore==0}">
						<form method="post" action="AuctionDetails">
							<label id="labelauctiondetail"
								${status eq  'ban'  ? 'hidden="true"' : '' || noUser eq  no_vendeur  ? 'hidden="true"' : ''}><fmt:message key="msg_offre"></fmt:message> : </label> <input type="number" name="prix"
								${status eq  'ban'  ? 'hidden="true"' : '' || noUser eq  no_vendeur  ? 'hidden="true"' : ''}> <input
								type="submit" value="<fmt:message key="msg_encherir"></fmt:message>"
								${status eq  'ban'  ? 'hidden="true"' : '' ||  noUser eq  no_vendeur  ? 'hidden="true"' : ''}>
						</form>
					</c:if>


					<c:if test="${isBefore==1 && aquereur==noUser}">
						<a href="AuctionList" class="btn btn-info" role="button"><fmt:message key="msg_retour"></fmt:message></a>
					</c:if>

					<c:if test="${isBefore==1 && user.no_utilisateur==noUser}">
						<form method="post" action="AuctionDetails">
							<input type="submit" class="btn btn-info"
								value="<fmt:message key="msg_retrait_ok"></fmt:message>" name="retrait">
						</form>
					</c:if>

				</div>
				</fmt:bundle>
</body>
</html>
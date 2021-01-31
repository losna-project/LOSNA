<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<title>Page d'accueil</title>

</head>

<body>
	<c:if test="${!empty param.langue}">
	<fmt:setLocale value="${param.langue}"/>
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	<div class="container-md">
		<div class="container-fluid">

			<nav id="navbar"
				class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
				<a href="Index" class="navbar-brand"><img
					src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
				<ul class="nav navbar-nav mr-auto"></ul>
				<ul class="navbar-nav flex-row">
					<li class="nav-item"><a href="CreateProfile"
						class="nav-link px-2"><fmt:message key="msg_inscrire"></fmt:message></a></li>
					<li class="nav-item"><a href="Login" class="nav-link px-2"><fmt:message key="msg_connexion"></fmt:message></a></li>
				</ul>
			</nav>
			<br>
		</div>
		<div id="presentation">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2><fmt:message key="msg_titre"></fmt:message> </h2>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<h5><fmt:message key="msg_filtre"></fmt:message></h5>
				</div>
			</div>
		</div>

		<div id="conteneur" class="row">
			<div class="col">
				<form action="Index" method="post">
					<label for="selectbyname"></label> <input class="form-control"
						placeholder="<fmt:message key="msg_ph_recherche"></fmt:message>" class="search"
						name="selectbyname"><br>

					<div class="form-inline">
						<label for="select" class="mr-sm-2"><fmt:message key="msg_categorie"></fmt:message> : </label> <select
							id="select" name="select" class="form-control">
							<c:forEach items="${listCategory}" var="category">
								<option value="${category.libelle}">${category.libelle}</option>
							</c:forEach>
						</select>
					</div>
			</div>
			<br>

			<div class="col align-middle">
				<button name="valider" id="button" class="btn btn-info"><fmt:message key="msg_btn_recherche"></fmt:message></button>
			</div>
		</div>


		<div id="contenu" class="row">

			<c:forEach var="product" items="${selectItems}">
				<div class="col-lg-6">
					<div id="row" class="row">
						<div class="col-4">
							<img class="img-fluid" src="${product.images}" alt="Card image">

						</div>
						<div class="col-8">
							<input type="hidden" name="no_art" value="${product.no_article}">
							<p>${product.nom_article }</p>
							<c:choose>
								<c:when
									test="${product.selectBestAuction(product.no_article) != 0}"><fmt:message key="msg_prix"></fmt:message> : ${product.selectBestAuction(product.no_article)} points</c:when>
								<c:otherwise><fmt:message key="msg_prix"></fmt:message> : ${product.prix_initial} points</c:otherwise>
							</c:choose>
							<%-- 							<p>Fin de l'enchère : ${product.date_fin_encheres}</p> --%>
							<p>
								<fmt:message key="msg_fin_enchere"></fmt:message> :
								<tags:localDate date="${product.date_fin_encheres}" />
							</p>
							<p><fmt:message key="msg_vendeur"></fmt:message> :
								${product.selectUserByProduct(product.no_article)}</p>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="row justify-content-center" id="pagination">
			<c:set var="p" value="${0}" />
			<ul class="pagination">
				<c:forEach var="i" items="${nbPages}">
					<c:set var="p" value="${p+1}" />
					<li><input id="${p}" type="submit" value="${p}" name="page"
						class="btn btn-info mr-sm-2"></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	</fmt:bundle>
</body>

</html>
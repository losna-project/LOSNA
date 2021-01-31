<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" 
integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" 
crossorigin="anonymous">
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Nouvelle Vente</title>
</head>
<body>
<c:if test="${!empty param.langue}">
	<fmt:setLocale value="${param.langue}"/>
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
					<h2><fmt:message key="msg_new_auction"></fmt:message></h2>
				</div>
			</div>
			
<!--  added attribute to form, enctype="multipart/form-data -->

<div>
	<div class="row">
		<div class="col-lg-4">
				<img src="${images}" alt="Card image" id="output">
			</div>
			
		<div class="col-lg-8">	
		
		<form method="post" action="NewAuction" enctype="multipart/form-data">
		<label id="labelnewauction" for="article">Article:</label> <input type="text" id="article"
			name="article" value="${article}"><br> <br> 
			<label  id="labelnewauction" for="description">Description:</label> 
			<input id="inputnewauctiondescription" type="text" name="description" value="${description}"><br>
		<br> <div class="form-inline">
						<label for="select" class="mr-sm-2"><fmt:message key="msg_categorie"></fmt:message> : </label> <select
							id="select" name="categorie" class="form-control">
							<c:forEach items="${listCategory}" var="category">
								<option value="${category.no_categorie}">${category.libelle}</option>
							</c:forEach>
						</select>
						</div>
<!--  added image upload button -->

		<div class="form-group file-area">
			<label  id="labelnewauction" for="images"><span><fmt:message key="msg_photo"></fmt:message></span></label> <input
				type="file" name="photo" id="image" onchange="loadFile(event)" />
		</div>
<script>
  var loadFile = function(event) {
    var output = document.getElementById('output');
    output.src = URL.createObjectURL(event.target.files[0]);
    output.onload = function() {
      URL.revokeObjectURL(output.src) // free memory
    }
  };
</script>

		<br> <label id="labelnewauction" for="prix"><fmt:message key="msg_prix"></fmt:message>:</label> <input type="number"
			id="prix" name="prix" value="${prix}"><br> <br> 
			<label id="labelnewauction" for="dateDebut"><fmt:message key="msg_debut"></fmt:message>:</label> <input type="date"
			id="dateDebut" name="dateDebut" value="${debut}" min="${today}"><br> <br>
		<label  id="labelnewauction" for="dateFin"><fmt:message key="msg_fin"></fmt:message>:</label> <input
			type="date" id="dateFin" name="dateFin" value="${fin}" min="${today}"><br>

		<fieldset class="fieldset">
			<legend class="legend"><fmt:message key="msg_retrait"></fmt:message></legend>
			   	<label id="labelnewauction" for="rue"><fmt:message key="msg_rue"></fmt:message>:</label> 
			<input type="text" id="rue" name="rue"
				value="${userRue}"><br> <br> 
				<label id="labelnewauction" for="codePostal"><fmt:message key="msg_postal"></fmt:message>:</label> <input type="text"
				id="codePostal" name="codePostal" value="${userCodePostal}"><br>
			<br> <label  id="labelnewauction" for="ville"><fmt:message key="msg_ville"></fmt:message>:</label> 
			<input type="text" id="ville" name="ville" value="${userVille}"><br> <br>
		</fieldset>
		
		<button type="submit" class="btn btn-info" name="choix"
			value="Enregistrer"><fmt:message key="msg_enregistrer"></fmt:message></button> <a href="AuctionList"
			class="btn btn-info" role="button"><fmt:message key="msg_annuler"></fmt:message></a>
		<c:if test="${isBefore==1 && no_vendeur==noUser}">
			<button type="submit" class="btn btn-info" name="choix"
				value="Supprimer"><fmt:message key="msg_supprimer"></fmt:message></button>
		</c:if>
		</fmt:bundle>
</body>
</html>
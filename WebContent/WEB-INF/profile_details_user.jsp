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
<title>Mon profil</title>
</head>
<body>
		<c:if test="${!empty param.langue}">
	<fmt:setLocale value="${param.langue}"/>
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	
<div class="container">
<div class="container-fluid">
<div class="row">
			<nav id="navbar"
			class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
				<a href="AuctionList" class="navbar-brand"><img
					src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
				<ul class="nav navbar-nav mr-auto"></ul>
				<ul class="navbar-nav flex-row">
					<li class="nav-item"><a href="AuctionList"
						class="nav-link px-2"><fmt:message key="msg_retour"></fmt:message></a></li>
				</ul>
			</nav>
			<br>
</div>
	<div class="row">
<div id="presentation">
	<p>${ messageupdate }</p>
	</div>
	</div>
	
	<div class="row justify-content-center">
	<div class="col-lg-6">	
	<label id= "labelprofiledetail" ><fmt:message key="msg_ph_pseudo"></fmt:message> : </label> 
		<textareas> ${ loggedInUser.pseudo } </textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_nom"></fmt:message> : </label> 
		<textareas>${ loggedInUser.nom }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_prenom"></fmt:message> : </label> 
		<textareas>${ loggedInUser.prenom }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_email"></fmt:message> : </label> 
		<textareas>${ loggedInUser.email }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_telephone"></fmt:message> : </label> 
		<textareas> ${ loggedInUser.telephone }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_rue"></fmt:message> : </label> 
		<textareas> ${ loggedInUser.rue }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_postal"></fmt:message> : </label> 
		<textareas> ${ loggedInUser.code_postal }</textareas><br>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_ville"></fmt:message> : </label> 
		<textareas> ${ loggedInUser.ville }</textareas><br><br>

	
		<label id= "labelprofiledetail" ></label>
		<a href="ModifyProfile" type="button" role="button" class="btn btn-info"><fmt:message key="msg_modifier"></fmt:message></a>
		<a href="BuyCredits" type="button" role="button" class="btn btn-info"><fmt:message key="msg_ach_credits"></fmt:message></a>
		</div>
	</div>
	</div>
	</fmt:bundle>	
</body>
</html>
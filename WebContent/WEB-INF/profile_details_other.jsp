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
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>User Profile</title>
</head>
<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
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
	<script>
		function myFunction() {
			var myWindow = window
					.open(
							"UserBlocked",
							"_blank",
							"toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
		}
		</script>
		
		<label id= "labelprofiledetail" ><fmt:message key="msg_ph_pseudo"></fmt:message> : </label> 
			<textareas> ${ userData.pseudo } </textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_nom"></fmt:message> : </label> 
			<textareas> ${ userData.nom }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_prenom"></fmt:message> : </label> 
			<textareas>${ userData.prenom }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_email"></fmt:message> : </label> 
			<textareas>${ userData.email }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_telephone"></fmt:message> : </label> 
			<textareas> ${ userData.telephone }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_rue"></fmt:message> : </label> 
			<textareas> ${ userData.rue }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_postal"></fmt:message> : </label> 
			<textareas> ${ userData.code_postal }</textareas><br>
			
			<label id= "labelprofiledetail" ><fmt:message key="msg_ville"></fmt:message> : </label> 
			<textareas> ${ userData.ville }</textareas><br>
		
		
				<form action="AdminTasks" method=get>
				<input type="hidden" name="user_id"
					value="${userData.no_utilisateur }" />
				<button type="submit" class="btn btn-outline-info btn-block"
					${status ne 1  ? 'hidden="true"' : ''}>ManageUser</button>
			</form>
		
				
	</script>
	
		</form>
		
			</div>
		</div>
	</div>
</fmt:bundle>
</body>
</html>
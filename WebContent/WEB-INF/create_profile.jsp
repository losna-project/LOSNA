<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>Create profile</title>
</head>

<body>
	<c:if test="${!empty param.langue}">
	<fmt:setLocale value="${param.langue}"/>
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	<div class="container">
		<div class="container-fluid">

	<nav
		class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
		<a href="Index" class="navbar-brand"><img
					src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
		<ul class="nav navbar-nav mr-auto"></ul>
		<ul class="navbar-nav flex-row">
					<li class="nav-item"><a href="CreateProfile" class="nav-link px-2"><fmt:message key="msg_inscrire"></fmt:message></a></li>
					<li class="nav-item"><a href="Login" class="nav-link px-2"><fmt:message key="msg_connexion"></fmt:message></a></li>
				</ul>
	</nav>
	
		</div>
		<div id="presentation">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2><fmt:message key="msg_creer_profil"></fmt:message></h2>
				</div>
			</div>

		
<div class="container py-5">
    <div class="row">
        <div class="col-md-10 mx-auto">
        <form action="CreateProfile" method="POST">
                <div class="form-group row">
                    <div class="col-sm-6">
					<input type="text" class="form-control" placeholder="<fmt:message key="msg_ph_pseudo"></fmt:message>*"	value="" name="pseudo" required /><br> 
						</div>
						 <div class="col-sm-6">
						 <input type="text" class="form-control" placeholder="<fmt:message key="msg_rue"></fmt:message>*" value="" name="rue" required /><br> 
						</div>
 						</div>
					<div class="form-group row">
                    <div class="col-sm-6">
                    <input type="text" class="form-control" placeholder="<fmt:message key="msg_prenom"></fmt:message>*" value="" name="prenom" required /> <br> 
						</div>
						 <div class="col-sm-6">
						 <input type="text"	class="form-control" placeholder="<fmt:message key="msg_postal"></fmt:message>*" value="" name="code_postal" required /> <br> 
						</div>
 						</div>
				  <div class="form-group row">
						<div class="col-sm-6">
					<input type="text" class="form-control"
						placeholder="<fmt:message key="msg_nom"></fmt:message>*" value="" name="nom" required /> <br> 
						</div>
						 <div class="col-sm-6">
						 <input type="text" class="form-control" placeholder="<fmt:message key="msg_ville"></fmt:message>*" value="" name="ville" required /> <br> 
						</div>
 						</div> 
                <div class="form-group row">
						 <div class="col-sm-6">
					<input type="password" class="form-control" placeholder="<fmt:message key="msg_ph_mdp"></fmt:message>*" value="" name="new_mot_de_passe" required /><br> 
					</div>	
						 <div class="col-sm-6">
						 <input type="email" class="form-control" placeholder="<fmt:message key="msg_email"></fmt:message>*" value="" name="email" required />  <br> 
						</div>
 						</div>
                <div class="form-group row">
					 <div class="col-sm-6">
					<input	type="password" class="form-control" placeholder="<fmt:message key="msg_cfrm_mdp"></fmt:message>*" value="" name="mot_de_passe"required /><br> 
					</div>	
						 <div class="col-sm-6">
						 <input	type="text" minlength="10" maxlength="10" class="form-control"	placeholder="<fmt:message key="msg_telephone"></fmt:message>*" value="" name="telephone" required /> <br>   
						</div>
 						</div>
 						
 					<div class="form-group row">
					 <div class="col-sm-6">	
					<input id="btn-create-profile" type="submit" class="btn btn-info" value="<fmt:message key="msg_creer"></fmt:message>" name="creer" /><a href="Index">
					</div>	
					 <div class="col-sm-6">
					 <input id="btn-cancel-profile" type="button" class="btn btn-info" value="<fmt:message key="msg_annuler"></fmt:message>" name="annuler" />	</a>
					 
					 </div>	
					</form>
				</div>
			</div>
		</div>
	</fmt:bundle>	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Modify your profile</title>
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
		<a href="AuctionList" class="navbar-brand"><img
		src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
		<ul class="nav navbar-nav mr-auto"></ul>
		<ul class="navbar-nav flex-row">
					<li class="nav-item"><a href="AuctionList"
						class="nav-link px-2"><fmt:message key="msg_retour"></fmt:message></a></li>
		</ul>
	</nav>
	
</div>
		<div id="presentation">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2><fmt:message key="msg_modifier_mon_profil"></fmt:message></h2>
				</div>
			</div>

	<div class="row">
	<form action="ModifyProfile" method="POST">
				<div class="tab-content" id="myTabContent">
					<div class="row register-form">
						<div class="col-md-6">
							<div class="form-group row">
								<label for="pseudo" class="col-sm-4 col-form-label"><fmt:message key="msg_ph_pseudo"></fmt:message> : </label>
								<div class="col-sm-8">
									<input type="pseudo" class="form-control" id="pseudo"
										placeholder="<fmt:message key="msg_ph_pseudo"></fmt:message>*"  value="${loggedInUser.pseudo}" name="pseudo">
								</div>
								<label style="margin-top: 10px;" for="prenom" class="col-sm-4 col-form-label"><fmt:message key="msg_prenom"></fmt:message> : </label>
								<div class="col-sm-8">
									<input style="margin-top: 10px;" type="prenom" class="form-control" id="prenom"
										placeholder="<fmt:message key="msg_prenom"></fmt:message>*"  value="${ loggedInUser.prenom }" name="prenom">
								</div>
								<label style="margin-top: 10px;" for="telephone" class="col-sm-4 col-form-label"><fmt:message key="msg_telephone"></fmt:message> : </label>
								<div class="col-sm-8">
								<input style=" margin-top: 10px; float: right;" type="telephone" class="form-control" id="telephone"
										placeholder="<fmt:message key="msg_telephone"></fmt:message>*"  value="${ loggedInUser.telephone }" name="telephone">		
								</div>
								<label style="margin-top: 10px;" for="codepostal" class="col-sm-4 col-form-label"><fmt:message key="msg_postal"></fmt:message> : </label>
								<div class="col-sm-8">
								<input style=" margin-top: 10px; float: right;" class="form-control" placeholder="<fmt:message key="msg_postal"></fmt:message>*"  value="${ loggedInUser.code_postal }" name="code_postal">
								</div>	
								
								
								<label style="margin-top: 10px;" for="motdepasseactuel" class="col-sm-4 col-form-label"><fmt:message key="msg_act_mdp"></fmt:message> : </label>
								<div class="col-sm-8">
									<input style="margin-top: 10px;" type="password" class="form-control" id="motdepasse"
										placeholder="<fmt:message key="msg_act_mdp"></fmt:message>*"  name="old_mot_de_passe" required>
								</div>
								<label style="margin-top: 10px;" for="newmotdepasse" class="col-sm-4 col-form-label"><fmt:message key="msg_nouv_mdp"></fmt:message> : </label>
								<div class="col-sm-8">
									<input style="margin-top: 10px;" type="password" class="form-control" id="motdepasse"
										placeholder="<fmt:message key="msg_nouv_mdp"></fmt:message>*"  value="" name="new_mot_de_passe">
								</div>
								<br>
								<p class="col-sm-4 col-form-label" style="margin-top: 10px;"> Credit : ${loggedInUser.credit}</p>
														
								
								</div>
								<div class="form-group row">
									<div class="maxl"></div>
								</div>
							</div>
								<div class="col-md-6">
								<div class="form-group row">
								<label for="nom" class="col-sm-4 col-form-label"><fmt:message key="msg_nom"></fmt:message> : </label>
								<div class="col-sm-8">
									<input type="nom" class="form-control" id="nom"
										placeholder="Nom*"  value="${ loggedInUser.nom }" name="nom">
								</div>
								<label style="margin-top: 10px;" for="email" class="col-sm-4 col-form-label"><fmt:message key="msg_email"></fmt:message> : </label>
								<div class="col-sm-8">
									<input style="margin-top: 10px;" type="email" class="form-control" id="email"
										 placeholder="<fmt:message key="msg_email"></fmt:message>*"  value="${ loggedInUser.email }" name="email">
								</div>
								
								<label style="margin-top: 10px;" for="rue" class="col-sm-4 col-form-label"><fmt:message key="msg_rue"></fmt:message> : </label>
								<div class="col-sm-8">
								<input style="margin-top: 10px;" type="rue" class="form-control" id="rue"
										placeholder="<fmt:message key="msg_rue"></fmt:message>*"  value="${ loggedInUser.rue }" name="rue">
									</div>	
								<label style="margin-top: 10px;" for="ville" class="col-sm-4 col-form-label"><fmt:message key="msg_ville"></fmt:message> : </label>
								<div class="col-sm-8">
								<input style=" margin-top: 10px; float: right;" type="ville" class="form-control" id="ville"
										placeholder="<fmt:message key="msg_ville"></fmt:message>*"  value="${ loggedInUser.ville }" name="ville">
								</div>
								<div class="col-sm-12">
								</div>	
								<label style="margin-top: 10px;" for="confirmmotdepasse" class="col-sm-4 col-form-label"><fmt:message key="msg_cfrm_mdp"></fmt:message> : </label>
								<div class="col-sm-8">
									<input style="margin-top: 10px;" type="password" class="form-control" id="motdepasse"
										placeholder="<fmt:message key="msg_cfrm_mdp"></fmt:message>*" value="" name="mot_de_passe" required >
								</div>
										
																					
							</div>
						</div>
						<div class="col-md-6">
						<button type="submit" class="btn btn-info"  value="Modify" name="accountaction"><fmt:message key="msg_enregistrer"></fmt:message></button>
						<button type="submit" class="btn btn-info" value="DeleteAccount"
                            name="accountaction"><fmt:message key="msg_supprimer"></fmt:message></button>
   					</div>
              	</div>             
			</div>
		</form>
	</fmt:bundle>
</body>


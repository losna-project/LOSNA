<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body style="height: 1500px">
<c:if test="${!empty param.langue}">
	<fmt:setLocale value="${param.langue}"/>
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
	<div>
		<nav class="navbar fixed-top navbar-expand-md navbar-new-top">
			<a href="Index" class="navbar-brand"><img
				src="images/Logo_LOSNA.png" alt="logo" /><fmt:message key="msg_logo"></fmt:message></a>
		</nav>
		<br> <br> <br> <br> <br>
	</div>

	<div class="row">
		<div class="col-sm-2 col-md-3 col-lg-4" style=""></div>
		<div class="col-sm-8 col-md-6 col-lg-4">
			<form action="Login" method="POST">
				<div class="container login-container" align="center">

					<div class="login-form-1">
						<h3><fmt:message key="msg_connexion"></fmt:message></h3>
						<p>${messagelogin}</p>

						<div class="form-group">
							<input type="text" class="form-control" placeholder="Pseudo *"
								value="${ username }" name="pseudo" />
						</div>
						<div class="form-group">
							<input type="password" class="form-control"
								placeholder="<fmt:message key="msg_ph_mdp"></fmt:message> *" value="${ password }" name="mot_de_passe" />
						</div>
						<div class="form-group">
							<input type="submit" class="btnSubmit" value="<fmt:message key="msg_connexion"></fmt:message>" />
						</div>
						<div class="form-group">
							<label for="checkbox" class="mr-sm-2"><fmt:message key="msg_remember"></fmt:message>:</label> <input
								type="checkbox" class="checkbox" name="checkbox"
								value="ischecked" />
						</div>
						<div class="form-group">
							<a href="ForgotPassword" class="ForgetPwd"><fmt:message key="msg_forgot"></fmt:message></a>
						</div>
						<div class="form-group">
							<a href="CreateProfile" class="btnCreateProfile"><fmt:message key="msg_inscrire"></fmt:message></a>
						</div>
					</div>
				</div>
		</div>
		</form>
	</div>
	</div>
	<div class="col-sm-2 col-md-3 col-lg-4" style=""></div>
	</div>
</fmt:bundle>
</body>
</html>
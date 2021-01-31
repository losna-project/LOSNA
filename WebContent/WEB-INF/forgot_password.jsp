<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<meta charset="ISO-8859-1">
<title>Mot de passe oublié</title>
</head>
<body>
	<div align="center">
		<h2>Réinitialiser votre mot de passe</h2>
		<p>Entrez email de votre compte.</p>
		<p>Un lien pour réinitialiser votre mot de passe vous sera envoyé par courrier à cette adresse
			l'adresse.</p>
		<p>${messagelogin}</p>

		<form id="resetForm" action="ForgotPassword" method="post">
			<table>
				<tr>
					<td>Email:</td>
					<td><input type="email" name="email" id="email" size="20"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">Réinitialiser mon mot de passe</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
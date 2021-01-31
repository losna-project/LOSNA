<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Réinitialisation du mot de passe</title>
</head>
<body>
	<div align="center">
		<form action="ResetPassword" method="post">
			<label for="new_pw">Entrez un nouveau mot de passe:</label><br>
			<input type="text" id="pw" name="new_pw" value=""><br> <label
				for="pw">Confirmer le mot de passe:</label><br> <input
				type="text" id="pw" name="pw" value=""><br> <br> <input
				type="submit" value="Submit">
		</form>
	</div>
</body>
</html>
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

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	type="text/javascript"></script>
<meta charset="ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Gestionnaire de catégorie</title>
</head>
<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
		<div class="container">
			<div class="container-fluid">
				<p>${messageadmin }</p>
				<h3>Manage Categories</h3>
				<form action="CategoryManager" method=post>
					<div class="form-group">
						<label for="select" class="mr-sm-2"><fmt:message
								key="msg_select_cat_suppr"></fmt:message> :</label> <select id="select"
							name="select" class="form-control">
							<c:forEach items="${listCategory}" var="category">
								<option value="${category.no_categorie}">${category.libelle}</option>
							</c:forEach>
						</select><br>
						<button id="cat" type="submit" class="btn btn-info"
							value="DeleteCategory" name="modcat">
							<fmt:message key="msg_envoyer"></fmt:message>
						</button>
					</div>
					<div class="form-group">
						<label for="addcat"><fmt:message
								key="msg_entrer_cat_ajouter"></fmt:message> :</label> <input type="text"
							class="form-control" id="cat" name="addcat" class="col-md-6"><br>
						<button type="submit" class="btn btn-info" value="AddCategory"
							name="modcat">
							<fmt:message key="msg_envoyer"></fmt:message>
						</button>
					</div>
					<h3>Manage Users</h3>
					<div class="form-group">
						<label for="select" class="mr-sm-2"><fmt:message
								key="msg_select_cat_suppr"></fmt:message> :</label> <select id="select"
							name="user" class="form-control">
							<c:forEach items="${listUser}" var="user">
								<option value="${user.no_utilisateur}">${user.pseudo}</option>
							</c:forEach>
						</select><br>
						<button id="cat" type="submit" class="btn btn-info"
							value="BanUser" name="modcat">
							<fmt:message key="msg_desact"></fmt:message>
						</button>
						<button id="cat" type="submit" class="btn btn-info"
							value="DeleteUser" name="modcat">
							<fmt:message key="msg_supprimer"></fmt:message>
						</button>
					</div>
				</form>
				<a href="AuctionList"><button class="btn btn-info"
						value="Return">
						<fmt:message key="msg_retour"></fmt:message>
					</button></a>
	</fmt:bundle>
</body>
</html>
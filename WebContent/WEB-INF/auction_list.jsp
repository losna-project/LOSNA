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
<title>Page d'accueil</title>
</head>

<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
		<div class="container">
			<div class="container-fluid">

				<script>
					function myFunction() {
						var myWindow = window
								.open(
										"UserBlocked",
										"_blank",
										"toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
					}
				</script>

				<nav id="navbar"
					class="navbar fixed-top navbar-expand-md flex-nowrap navbar-new-top">
					<a href="AuctionList" class="navbar-brand"><img
						src="images/Logo_LOSNA.png" alt="logo" /> <fmt:message
							key="msg_logo"></fmt:message></a>
					<ul class="nav navbar-nav mr-auto"></ul>
					<ul class="navbar-nav flex-row">
						<li class="nav-item"><a href="CategoryManager"
							class="nav-link px-2"
							${loggedInUser.administrateur ne 1  ? 'hidden="true"' : ''}>Admin</a></li>
						<li class="nav-item"><a href="NewAuction"
							class="nav-link px-2"
							${loggedInUser.status == 'ban' ? 'onclick="myFunction()"' : ''}><fmt:message
									key="msg_new_auction"></fmt:message></a></li>
						<li class="nav-item"><a href="BidderList"
							class="nav-link px-2"><fmt:message key="msg_enchere"></fmt:message></a></li>
						<li class="nav-item"><a href="ProfileDetailsUser"
							class="nav-link px-2"><fmt:message key="msg_profil"></fmt:message></a></li>
						<li class="nav-item"><a href="Disconnect"
							class="nav-link px-2"><fmt:message key="msg_deconnexion"></fmt:message></a></li>
					</ul>
				</nav>
				<br>

			</div>

			<div id="presentation">
				<div class="row">
					<div class="col-md-12">
						<fmt:message key="msg_bienvenue"></fmt:message>
						${ loggedInUser.pseudo }
						<fmt:message key="msg_credits"></fmt:message>
						${credits} points
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 text-center">
						<h2>
							<fmt:message key="msg_titre"></fmt:message>
						</h2>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<h5>
							<fmt:message key="msg_filtre"></fmt:message>
							:
						</h5>
					</div>
				</div>
			</div>

			<div id="conteneur" class="row">
				<div class="col-lg-6">
					<div class="row">

						<form action="AuctionList" method="post">
							<label for="selectbyname"></label> <input class="form-control"
								placeholder="<fmt:message key="msg_ph_recherche"></fmt:message>"
								class="search " id="search" name="selectbyname"><br>

							<div class="form-inline">
								<label for="select" class="mr-sm-2"><fmt:message
										key="msg_categorie"></fmt:message> :</label> <select id="select"
									name="select" class="form-control">
									<c:forEach items="${listCategory}" var="category">
										<option value="${category.libelle}">${category.libelle}</option>
									</c:forEach>
								</select>
							</div>
							<br>
					</div>
					<script>
						$(function() {
							$("#radioOne").change(function() {
								if ($(this).is(':checked'))
									$(".checkOne").removeAttr("disabled");
								$(".checkTwo").removeAttr("checked");
								$(".checkTwo").attr("disabled", "disabled");

							});
							$("#radioTwo").change(function() {
								if ($(this).is(':checked'))
									$(".checkTwo").removeAttr("disabled");
								$(".checkOne").removeAttr("checked");
								$(".checkOne").attr("disabled", "disabled");
							});
						});
					</script>
					<div class="row">
						<div class="col-lg-6">
							<div id="achats">
								<div class="form-check-inline">
									<label class="form-check-label"> <input type="radio"
										name="radio" id="radioOne" checked="checked" /> <fmt:message
											key="msg_achats"></fmt:message>
									</label>
								</div>

								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="selectItems" class="checkOne" /> <fmt:message
											key="msg_encheres_ouvertes"></fmt:message>
									</label>
								</div>
								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="userAuctions" class="checkOne" /> <fmt:message
											key="msg_mes_encheres"></fmt:message>
									</label>
								</div>
								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="auctionWon" class="checkOne" /> <fmt:message
											key="msg_mes_encheres_remp"></fmt:message>
									</label>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div id="ventes">
								<div class="form-check-inline">
									<label class="form-check-label"> <input type="radio"
										name="radio" id="radioTwo" /> <fmt:message
											key="msg_mes_ventes"></fmt:message>
									</label>
								</div>

								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="currentAuction" class="checkTwo"
										disabled="disabled" /> <fmt:message key="msg_en_cours"></fmt:message>
									</label>
								</div>
								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="futurAuction" class="checkTwo"
										disabled="disabled" /> <fmt:message key="msg_non_debute"></fmt:message>
									</label>
								</div>

								<div class="form-check">
									<label class="form-check-label"> <input type="checkbox"
										name="selectcheck" value="finishedAuction" class="checkTwo"
										disabled="disabled" /> <fmt:message key="msg_termine"></fmt:message>
									</label>
								</div>

							</div>
						</div>
					</div>
				</div>

				<div class="col-lg-6 text-center align-middle">
					<button name="valider" id="button" class="btn btn-info">
						<fmt:message key="msg_btn_recherche"></fmt:message>
					</button>
				</div>
			</div>
			</form>

			<div id="contenu" class="row">
				<c:choose>
					<c:when test="${enchere==0}">
						<c:forEach var="product" items="${selectItems}">
							<div class="col-lg-6">
								<div id="row" class="row">
									<div class="col-md-4">
										<img class="img-fluid" src="${product.images}"
											alt="Card image">
									</div>
									<div class="col-md-8 ">
										<c:choose>
											<c:when test="${voirVente==1}">
												<form method="get" action="NewAuction">
													<input type="hidden" name="no_art"
														value="${product.no_article}"> <input
														type="submit" class="btn btn-outline-info btn-block"
														value="${product.nom_article }">
												</form>
											</c:when>
											<c:otherwise>
												<form method="get" action="AuctionDetails">
													<input type="hidden" name="no_art"
														value="${product.no_article}"> <input
														type="submit" class="btn btn-outline-info btn-block"
														value="${product.nom_article }"
														${loggedInUser.status == 'ban' ? 'onclick="myFunction()"' : ''}>
												</form>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${product.selectBestAuction(product.no_article) != 0}">
												<fmt:message key="msg_prix"></fmt:message>
												</button> : <b>${product.selectBestAuction(product.no_article)}
													points</b>
											</c:when>
											<c:otherwise>
												<fmt:message key="msg_prix"></fmt:message> : <b>${product.prix_initial}
													points</b>
											</c:otherwise>
										</c:choose>
										<br>
										<fmt:message key="msg_fin_enchere"></fmt:message>
										:
										<tags:localDate date="${product.date_fin_encheres}" />

										<form action="ProfileDetailsOther" method="post">

											<fmt:message key="msg_vendeur"></fmt:message>
											:
											<button type="submit" name="user_id"
												value="${product.no_utilisateur }" class="btn btn-link">${product.selectUserByProduct(product.no_article)}</button>

										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>

					<c:when test="${enchere==1}">
						<c:forEach var="product" items="${selectItems}">
							<div class="col-lg-6">
								<div id="row" class="row">
									<div class="col-lg-4">
										<img
											src="${product.selectByNoProduct(product.no_article).images}"
											alt="Card image">
									</div>
									<div class="col-lg-8">

										<h4>
											<form method="get" action="AuctionDetails">
												<input type="hidden" name="no_art"
													value="${product.no_article}"> <input type="submit"
													class="btn btn-outline-info btn-block"
													value="${product.selectByNoProduct(product.no_article).nom_article}"
													${loggedInUser.status == 'ban' ? 'onclick="myFunction()"' : ''}>
											</form>
										</h4>

										<fmt:message key="msg_prix"></fmt:message>
										:<b> ${product.montant_enchere} points</b><br>

										<fmt:message key="msg_fin_enchere"></fmt:message>
										: <b><tags:localDate
												date="${product.selectByNoProduct(product.no_article).date_fin_encheres}" /></b>

										<form action="ProfileDetailsOther" method="post">

											<fmt:message key="msg_vendeur"></fmt:message>
											:
											<button type="submit" name="user_id"
												value="${product.no_utilisateur }" class="btn btn-link">${product.selectUserByProduct(product.no_article)}</button>
											</p>
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="product" items="${selectItems}">
							<c:if
								test="${product.montant_enchere==product.selectBestAuction(product.no_article)}">

								<div class="col-lg-6">
									<div id="row" class="row">
										<div class="col-lg-4">
											<img
												src="${product.selectByNoProduct(product.no_article).images}"
												alt="Card image">
										</div>
										<div class="col-lg-8">

											<h4>
												<form method="get" action="AuctionDetails">
													<input type="hidden" name="no_art"
														value="${product.no_article}"> <input
														type="submit" class="btn btn-outline-info btn-block"
														value="${product.selectByNoProduct(product.no_article).nom_article}"
														${loggedInUser.status == 'ban' ? 'onclick="myFunction()"' : ''}>
												</form>
											</h4>

											<fmt:message key="msg_prix"></fmt:message>
											: <b>${product.montant_enchere} points</b><br>


											<fmt:message key="msg_fin_enchere"></fmt:message>
											: <b><tags:localDate
													date="${product.selectByNoProduct(product.no_article).date_fin_encheres}" /></b>

											<form action="ProfileDetailsOther" method="post">

												<fmt:message key="msg_vendeur"></fmt:message>
												:
												<button type="submit" name="user_id"
													value="${product.no_utilisateur }" class="btn btn-link">${product.selectUserByProduct(product.no_article)}</button>

											</form>
										</div>


									</div>
								</div>
							</c:if>
						</c:forEach>

					</c:otherwise>
				</c:choose>

			</div>
			<div id="pagination" class="row justify-content-center">

				<form method="post" action="AuctionList">
					<div id="pagination" class="container">
						<c:set var="p" value="${0}" />
						<ul class="pagination">
							<c:forEach var="i" items="${nbPages}">
								<c:set var="p" value="${p+1}" />
								<li><input id="${p}" type="submit" value="${p}" name="page"
									class="btn btn-info mr-sm-2"></li>
							</c:forEach>
						</ul>
					</div>
				</form>
			</div>
		</div>
	</fmt:bundle>
</body>


</html>
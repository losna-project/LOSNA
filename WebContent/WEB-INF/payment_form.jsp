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

<title>Payment Details</title>
</head>
<body>
	<c:if test="${!empty param.langue}">
		<fmt:setLocale value="${param.langue}" />
	</c:if>
	<fmt:bundle basename="fr.eni.losna.messages.mes_messages">
		<div class="container-fluid">
			<form action="PaymentForm" method=post>
				<div class="container">
				<div>
					<p>${ messagepayment}</p>
				</div>
					<div class="row">
						<div class="col-xs-12 col-md-4 col-md-offset-4">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<h3 class="text-center">
											<fmt:message key="msg_detail_paiement"></fmt:message>
										</h3>
										<img class="img-responsive cc-img"
											src="http://www.prepbootstrap.com/Content/images/shared/misc/creditcardicons.png">
									</div>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12">
											<div class="form-group">
												<label><fmt:message key="msg_num_carte"></fmt:message></label>
												<div class="input-group">
													<input type="text" maxlength="19"
														placeholder="Valid card number" class="form-control"
														name="card" required /> <span class="input-group-addon"><span
														class="fa fa-credit-card"></span></span>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-6 col-md-6">
											<div class="form-group">
												<label><fmt:message key="msg_exp_date"></fmt:message></label>
												<div class="col-12 pl-0">
													<select class="form-control" id="month" name="expmonth"
														required>
														<option selected>Month</option>
														<option>January</option>
														<option>February</option>
														<option>March</option>
														<option>April</option>
														<option>May</option>
														<option>June</option>
														<option>July</option>
														<option>August</option>
														<option>September</option>
														<option>October</option>
														<option>November</option>
														<option>December</option>
													</select> <select class="form-control" id="year" name="expyear"
														required>
														<option selected>Year</option>
														<option>2021</option>
														<option>2022</option>
														<option>2023</option>
														<option>2024</option>
														<option>2024</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-xs-6 col-md-6">
											<div class="form-group">
												<label> <fmt:message key="msg_code_cv"></fmt:message>
												</label> <input type="text" maxlength="3" class="form-control"
													placeholder="CVC" required />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="form-group">
												<label><fmt:message key="msg_nom_prop_carte"></fmt:message></label>
												<input type="text" class="form-control"
													placeholder="Nom Prenom" name="name" required />
											</div>
										</div>
									</div>

								</div>
								<div class="panel-footer">
									<div class="row">
										<div class="col-xs-12">
											<a href="ProfileDetailsUser" style="text-decoration: none"><button
													class="btn btn-warning btn-lg btn-block">
													<fmt:message key="msg_paiement"></fmt:message>
												</button></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</fmt:bundle>
</body>
</html>
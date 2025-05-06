<%@page import="java.util.HashMap"%>
<%@page import="com.hms.controller.UserRegistrationCtl"%>
<%@page import="com.hms.util.HTMLUtility"%>
<%@page import="com.hms.util.DataUtility"%>
<%@page import="com.hms.util.ServletUtility"%>
<%@page import="com.hms.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>User Registration</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	function phoneno() {
		$('#phone').keypress(function(e) {
			var a = [];
			var k = e.which;

			for (i = 48; i < 58; i++)
				a.push(i);

			if (!(a.indexOf(k) >= 0))
				e.preventDefault();
		});
	}
</script>

<style type="text/css">
.log1 {
	padding-top: 2%;
}

i.css {
	border: 2px solid #8080803b;
	padding: 6px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.p4 {
	/* Reference to the image location in your webapp/img folder */
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/image/UserRegistration.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat
	padding-top: 60px;
}
</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<h3 class="text-center default-text text-success pb-2">User
								Registration</h3>
							<!--Body-->
							<div>

								<jsp:useBean id="dto" class="com.hms.dto.UserDTO"
									scope="request"></jsp:useBean>
								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>
								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>

							<!-- Form Fields -->
							<!-- Name -->
							<span class="pl-sm-5"><b>Name</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="name"
										placeholder="Full Name"
										value="<%=DataUtility.getStringData(dto.getName())%>">
								</div>
							</div>

							<!-- User Name -->
							<span class="pl-sm-5"><b>User Name</b> <span
								style="color: red;">*</span></span></br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-circle grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="userName"
										placeholder="Last Name"
										value="<%=DataUtility.getStringData(dto.getUserName())%>">
								</div>
							</div>

							<!-- Password -->
							<span class="pl-sm-5"><b>Password</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-key grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="password" class="form-control" name="password"
										placeholder="password"
										value="<%=DataUtility.getStringData(dto.getPassword())%>">
								</div>
							</div>

							<!-- Confirm Password -->
							<span class="pl-sm-5"><b>Confirm Password</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-key grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="password" class="form-control"
										name="confirmPassword" placeholder="confirmPassword"
										value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
								</div>
							</div>

							<!-- Mobile No -->
							<span class="pl-sm-5"><b>Mobile No</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-phone-square grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" id="defaultForm-email"
										maxlength="10" name="mobileNo" placeholder="mobile No"
										value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
								</div>
							</div>

							<!-- Gender -->
							<span class="pl-sm-5"><b>Gender</b><span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-venus-mars grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>

									<%
										HashMap map = new HashMap();
										map.put("Male", "Male");
										map.put("Female", "Female");

										String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
									%>
									<%=htmlList%></div>

							</div>

							<!-- Date of Birth -->
							<span class="pl-sm-5"><b>DOB</b> <span style="color: red;">*</span></span></br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" id="datepicker" name="dob"
										class="form-control" placeholder="Date Of Birth"
										readonly="readonly"
										value="<%=DataUtility.getDateString(dto.getDob())%>">
								</div>
							</div>

							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=UserRegistrationCtl.OP_SIGN_UP%>"> <input
									type="submit" name="operation" class="btn btn-secondary btn-md"
									style="font-size: 17px"
									value="<%=UserRegistrationCtl.OP_RESET%>">
							</div>
						</div>

					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
		</main>

	</div>
	<div class="footer">
		<%@include file="FooterView.jsp"%>
	</div>

</body>
<br>
<br>

</html>

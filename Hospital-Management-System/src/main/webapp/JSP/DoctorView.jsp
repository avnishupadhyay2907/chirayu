<%@page import="java.util.HashMap"%>
<%@page import="com.hms.util.HTMLUtility"%>
<%@page import="com.hms.controller.DoctorCtl"%>
<%@page import="com.hms.util.ServletUtility"%>
<%@page import="com.hms.util.DataUtility"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Doctor View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/image/doctor.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed; 
	background-size: cover;
	padding-top: 75px;
	
	/* background-size: 100%; */ 
}

</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp" %>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.DOCTOR_CTL%>" method="post">

			<div class="row pt-3 pb-4">
				<!-- Grid column -->
				<jsp:useBean id="dto" class="com.hms.dto.DoctorDTO"
					scope="request"></jsp:useBean>
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null) {
							%>
							<h3 class="text-center text-primary">Update Doctor</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Doctor</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
<%
									List list = (List) request.getAttribute("specialistList");
								%>
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
							<div class="md-form">
								<span class="pl-sm-5"><b>Name</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-university grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="name" class="form-control"
											id="defaultForm-email" placeholder="Enter Name"
											value="<%=DataUtility.getStringData(dto.getName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>


<span class="pl-sm-5"><b>Specialist Department</b><span
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
											map.put("Cancer", "Cancer");
											map.put("General Physician", "General Physician");
											map.put("Cardiology", "Cardiology");
											map.put("Ear", "Ear");
											String htmlList = HTMLUtility.getList("specialistName", dto.getSpecialistName(), map);
										%>
										<%=htmlList%></div>

								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("specialistName", request)%></font></br>



	<span class="pl-sm-5"><b>Phone</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-university grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="phone" class="form-control"
											id="defaultForm-email" placeholder="Enter phone"
											value="<%=DataUtility.getStringData(dto.getPhone())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("phone", request)%></font></br>

								<span class="pl-sm-5"><b>Email</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-university grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="email" class="form-control"
											id="defaultForm-email" placeholder="Enter Email"
											value="<%=DataUtility.getStringData(dto.getEmail())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("email", request)%></font></br>
								
								
							</div>
							</br>
							<%
								if (id > 0) {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=DoctorCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=DoctorCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=DoctorCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px" value="<%=DoctorCtl.OP_RESET%>">
							</div>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
		</main>


	</div>


<%@include file="FooterView.jsp"%>

</body>
</html>
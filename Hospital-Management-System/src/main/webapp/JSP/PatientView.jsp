<%@page import="com.hms.controller.PatientCtl"%>
<%@page import="com.hms.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hms.util.ServletUtility"%>
<%@page import="com.hms.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/image/patient.jpg');
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
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
			<form action="<%=ORSView.PATIENT_CTL%>" method="post">

				<div class="row pt-3 pb-4">
					<!-- Grid column -->
					<jsp:useBean id="dto" class="com.hms.dto.PatientDTO"
						scope="request"></jsp:useBean>
					<div class="col-md-4 mb-4"></div>
					<div class="col-md-4 mb-4">
						<div class="card">
							<div class="card-body">
								<%
									long id = DataUtility.getLong(request.getParameter("id"));

									if (dto.getId() != null) {
								%>
								<h3 class="text-center text-primary">Update Patient</h3>
								<%
									} else {
								%>
								<h3 class="text-center text-primary">Add Patient</h3>
								<%
									}
								%>
								<!--Body-->
								<div>
									<%
										List list = (List) request.getAttribute("wardlist");
										List list1 = (List) request.getAttribute("doctorlist");
										List list2 = (List) request.getAttribute("diseaselist");
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

										<span class="pl-sm-5"><b>DOB</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker" name="age"
											class="form-control" placeholder="Date Of Birth"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getAge())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("age", request)%></font></br>
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
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("gender", request)%></font></br>

									<span class="pl-sm-5"><b>phone</b><span
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

									<span class="pl-sm-5"><b>Address</b><span
										style="color: red;">*</span></span> </br>
									<div class="col-sm-12">
										<div class="input-group">
											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-university grey-text"
														style="font-size: 1rem;"></i>
												</div>
											</div>
											<input type="text" name="address" class="form-control"
												id="defaultForm-email" placeholder="Enter Address"
												value="<%=DataUtility.getStringData(dto.getAddress())%>">
										</div>
									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("address", request)%></font></br>



									<span class="pl-sm-5"><b>Ward</b><span
										style="color: red;">*</span></span></br>
									<div class="col-sm-12">
										<div class="input-group">
											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>
											<%=HTMLUtility.getList("wardId", String.valueOf(dto.getWardId()),
					(List) request.getAttribute("wardlist"))%>
										</div>
									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("wardId", request)%></font></br>


									<span class="pl-sm-5"><b>Doctor</b><span
										style="color: red;">*</span></span></br>
									<div class="col-sm-12">
										<div class="input-group">
											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>
											<%=HTMLUtility.getList("doctorId", String.valueOf(dto.getDoctorId()),
					(List) request.getAttribute("doctorlist"))%>
										</div>
									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("doctorId", request)%></font></br>

									<span class="pl-sm-5"><b>Disease</b><span
										style="color: red;">*</span></span></br>
									<div class="col-sm-12">
										<div class="input-group">
											<div class="input-group-prepend">
												<div class="input-group-text">
													<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
												</div>
											</div>
											<%=HTMLUtility.getList("diseaseId", String.valueOf(dto.getDiseaseId()),
					(List) request.getAttribute("diseaselist"))%>
										</div>
									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("diseaseId", request)%></font></br>


								</div>
								</br>
								<%
									if (id > 0) {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=PatientCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=PatientCtl.OP_CANCEL%>">
								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=PatientCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=PatientCtl.OP_RESET%>">
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
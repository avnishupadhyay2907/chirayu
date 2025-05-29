<%@page import="com.hms.controller.PatientListCtl"%>
<%@page import="com.hms.util.HTMLUtility"%>
<%@page import="com.hms.dto.PatientDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.hms.model.DiseaseModelInt"%>
<%@page import="com.hms.model.DoctorModelInt"%>
<%@page import="com.hms.model.ModelFactory"%>
<%@page import="com.hms.model.WardModelInt"%>
<%@page import="com.hms.dto.DiseaseDTO"%>
<%@page import="com.hms.dto.DoctorDTO"%>
<%@page import="com.hms.dto.WardDTO"%>
<%@page import="com.hms.util.DataUtility"%>
<%@page import="com.hms.util.ServletUtility"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient List View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.p1 {
	padding: 8px;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/image/patientlist.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;

	/* background-size: 100%; */
}

.table-hover tbody tr:hover td {
	background-color: #0064ff36;
}
</style>
</head>
<body class="p4">
	<div>
		<%@include file="Header.jsp"%>
	</div>
	<div>
		<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">



			<jsp:useBean id="dto" class="com.hms.dto.PatientDTO" scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("wardlist");
				List list2 = (List) request.getAttribute("doctorlist");
				List list3 = (List) request.getAttribute("diseaselist");
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				WardDTO wbean = new WardDTO();
				DoctorDTO dbean = new DoctorDTO();
				DiseaseDTO disbean = new DiseaseDTO();

				WardModelInt wmodel = ModelFactory.getInstance().getWardModel();
				DoctorModelInt dmodel = ModelFactory.getInstance().getDoctorModel();
				DiseaseModelInt dismodel = ModelFactory.getInstance().getDiseaseModel();

				Iterator<PatientDTO> it = list.iterator();
				if (list.size() != 0) {
			%>

			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<font color="black">Patient List 
				</h1>
				</font>
			</center>

			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>

				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			<div class="row">

				<div class="col-sm-2"></div>

				<div class="col-sm-2"><%=HTMLUtility.getList("wardId", String.valueOf(dto.getWardId()),
						(List) request.getAttribute("wardlist"))%></div>
				<div class="col-sm-2"><%=HTMLUtility.getList("doctorId", String.valueOf(dto.getDoctorId()),
						(List) request.getAttribute("doctorlist"))%></div>
				<div class="col-sm-2"><%=HTMLUtility.getList("diseaseId", String.valueOf(dto.getDiseaseId()),
						(List) request.getAttribute("diseaselist"))%></div>

				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 17px" name="operation"
						value="<%=PatientListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 17px"
						name="operation" value="<%=PatientListCtl.OP_RESET%>">
				</div>
				<div class="col-sm-1"></div>
			</div>



			</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-dark table-bordered table-hover">
					<thead>
						<tr style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th class="text">S.NO</th>
							<th class="text">Name</th>
							<th class="text">Age</th>
							<th class="text">Gender</th>
							<th class="text">Contact No</th>
							<th class="text">Address</th>
							<th class="text">Ward Name</th>
							<th class="text">Doctor Name</th>
							<th class="text">Disease Name</th>
							<th class="text">Edit</th>

						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();
								wbean = wmodel.findByPK(dto.getWardId());
								dbean = dmodel.findByPK(dto.getDoctorId());
								disbean = dismodel.findByPK(dto.getDiseaseId());
					%>

					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>
							<td align="center"><%=index++%></td>
							<td align="center"><%=dto.getName()%></td>
							<td align="center"><%=DataUtility.getDateString(dto.getAge())%></td>
							<td align="center"><%=dto.getGender()%></td>
							<td align="center"><%=dto.getPhone()%></td>
							<td align="center"><%=dto.getAddress()%></td>
							<td align="center"><%=wbean.getType()%></td>
							<td align="center"><%=dbean.getName()%></td>
							<td align="center"><%=disbean.getName()%></td>

							<td align="center"><a href="PatientCtl?id=<%=dto.getId()%>">Edit</a></td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=PatientListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=PatientListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=PatientListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						style="padding: 5px;" value="<%=PatientListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr></tr>
			</table>

			</br>

			<%
				}
				if (list.size() == 0) {
					System.out.println("user list view list.size==0");
			%>
			<center>
				<h1 class="text-primary font-weight-bold pt-3">Patient List</h1>
			</center>
			</br> </br>
			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=PatientListCtl.OP_BACK%>">
			</div>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>

	</div>
	</br>
	</br>
	<%@include file="FooterView.jsp"%>
</body>


</html>
<%@page import="com.hms.controller.LoginCtl"%>
<%@page import="com.hms.dto.RoleDTO"%>
<%@page import="com.hms.dto.UserDTO"%>
<%@page import="com.hms.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Header</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
    <style>
        .navbar {
            background: linear-gradient(to bottom right, #808080, #000000); /* Gray to black gradient */
            box-shadow: 0 4px 8px rgba(0,0,0,0.3);
        }

        .navbar .nav-link,
        .navbar-brand,
        .dropdown-item {
            color: #ffffff !important;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .navbar .nav-link:hover,
        .dropdown-item:hover {
            color: #f1c40f !important;
            text-shadow: 0 0 6px rgba(255, 255, 255, 0.5);
        }

        .navbar .dropdown-menu {
            background-color: #333333;
            border: none;
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
            border-radius: 0.5rem;
        }

        .navbar .dropdown-item {
            padding: 10px 20px;
        }

        .navbar-toggler {
            border: none;
            color: white;
        }

        .navbar-toggler:focus {
            outline: none;
        }

        .dropdown-menu-right {
            right: 0;
            left: auto;
        }
    </style>
</head>
<body>
<%
    UserDTO userDto = (UserDTO) session.getAttribute("user");
    boolean userLoggedIn = userDto != null;
    String welcomeMsg = "Hi, ";
    long roleId = 0;
    if (userLoggedIn) {
        roleId = userDto.getRoleId();
        String role = (String) session.getAttribute("role");
		welcomeMsg += userDto.getName() + " (" + role + ")";

    } else {
        welcomeMsg += "Guest";
    }
%>

<nav class="navbar navbar-expand-lg fixed-top">
    <a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>">
        <img src="<%=ORSView.APP_CONTEXT%>/image/he.png" width="80px" height="45px">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
        <i class="fa fa-bars text-white" style="font-size: 24px;"></i>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">

            <% if (userLoggedIn) { %>

                <% if (roleId == RoleDTO.ADMIN) { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Patients</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.PATIENT_CTL%>">Add Patient</a>
                            <a class="dropdown-item" href="<%=ORSView.PATIENT_LIST_CTL%>">View Patients</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Doctors</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.DOCTOR_CTL%>">Add Doctor</a>
                            <a class="dropdown-item" href="<%=ORSView.DOCTOR_LIST_CTL%>">View Doctors</a>
                        </div>
                    </li>
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Users</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.USER_CTL%>">Add User</a>
                            <a class="dropdown-item" href="<%=ORSView.USER_LIST_CTL%>">User List</a>
                        </div>
                    </li>
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Disease</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.DISEASE_CTL%>">Add Disease</a>
                            <a class="dropdown-item" href="<%=ORSView.DISEASE_LIST_CTL%>">Disease List</a>
                        </div>
                    </li>
                       <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Role</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>">Add Role</a>
                            <a class="dropdown-item" href="<%=ORSView.ROLE_LIST_CTL%>">View Role</a>
                        </div>
                    </li>
                      <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Doctor Schedule</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.DOCTOR_SCHEDULE_CTL%>">Add Doctor Schedule</a>
                            <a class="dropdown-item" href="<%=ORSView.DOCTOR_SCHEDULE_LIST_CTL%>">Doctor Schedule List</a>
                        </div>
                    </li>
                    
                      <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Specialist</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.SPECIALIST_CTL%>">Add Specialist</a>
                            <a class="dropdown-item" href="<%=ORSView.SPECIALIST_LIST_CTL%>">Specialist List</a>
                        </div>
                    </li>
                   <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Ward</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.WARD_CTL%>">Add Ward</a>
                            <a class="dropdown-item" href="<%=ORSView.WARD_LIST_CTL%>">Ward List</a>
                        </div>
                    </li>
                  <%--   <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Reports</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.REPORT_CTL%>">View Reports</a>
                        </div>
                    </li> --%>
                   <%--  <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">System</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.USER_LIST_CTL%>">Manage Users</a>
                        </div>
                    </li> --%>
                <% } else if (roleId == RoleDTO.RECEPTIONIST) { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Patients</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="<%=ORSView.PATIENT_CTL%>">Add Patient</a>
                            <a class="dropdown-item" href="<%=ORSView.PATIENT_LIST_CTL%>">View Patients</a>
                        </div>
                    </li>
                <% } else if (roleId == RoleDTO.MANAGER) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=ORSView.PATIENT_LIST_CTL%>">View Patients</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=ORSView.DOCTOR_LIST_CTL%>">View Doctors</a>
                    </li>
                  <%--   <li class="nav-item">
                        <a class="nav-link" href="<%=ORSView.WARD_CTL%>">Manage Wards</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=ORSView.REPORT_CTL%>">Reports</a>
                    </li> --%>
                <% } %>
            <% } %>

            <!-- Common Section: Profile / Logout / Login -->
            <li class="nav-item dropdown ml-3">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><%= welcomeMsg %></a>
                <div class="dropdown-menu dropdown-menu-right">
                    <% if (userLoggedIn) { %>
                        <a class="dropdown-item" href="<%=ORSView.MY_PROFILE_CTL%>"><i class="fa fa-user"></i> My Profile</a>
                        <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i class="fa fa-key"></i> Change Password</a>
                        <a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><i class="fa fa-sign-out-alt"></i> Logout</a>
                    <% } else { %>
                        <a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"><i class="fa fa-sign-in-alt"></i> Login</a>
                        <a class="dropdown-item" href="<%=ORSView.USER_REGISTRATION_CTL%>"><i class="fa fa-registered"></i> Register</a>
                    <% } %>
                </div>
            </li>
        </ul>
    </div>
</nav>

</body>
</html>

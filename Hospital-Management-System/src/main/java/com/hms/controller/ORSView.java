package com.hms.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Avnish Upadhyay
 *
 */
public interface ORSView {
	public String APP_CONTEXT = "/Hospital-Management-System";

	public String PAGE_FOLDER = "/JSP";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String DISEASE_VIEW = PAGE_FOLDER + "/DiseaseView.jsp";
	public String DISEASE_LIST_VIEW = PAGE_FOLDER + "/DiseaseListView.jsp";
	public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";
	public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";
	public String WARD_VIEW = PAGE_FOLDER + "/WardView.jsp";
	public String WARD_LIST_VIEW = PAGE_FOLDER + "/WardListView.jsp";
	public String SPECIALIST_VIEW = PAGE_FOLDER + "/SpecialistView.jsp";
	public String SPECIALIST_LIST_VIEW = PAGE_FOLDER + "/SpecialistListView.jsp";
	public String DOCTOR_SCHEDULE_VIEW = PAGE_FOLDER + "/DoctorScheduleView.jsp";
	public String DOCTOR_SCHEDULE_LIST_VIEW = PAGE_FOLDER + "/DoctorScheduleListView.jsp";

	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String DISEASE_CTL = APP_CONTEXT + "/ctl/DiseaseCtl";
	public String DISEASE_LIST_CTL = APP_CONTEXT + "/ctl/DiseaseListCtl";
	public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";
	public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";
	public String DOCTOR_SCHEDULE_CTL = APP_CONTEXT + "/ctl/DoctorScheduleCtl";
	public String DOCTOR_SCHEDULE_LIST_CTL = APP_CONTEXT + "/ctl/DoctorScheduleListCtl";
	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";
	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";
	public String REPORT_CTL = APP_CONTEXT + "/ctl/ReportCtl";
	public String REPORT_LIST_CTL = APP_CONTEXT + "/ctl/ReportListCtl";
	public String SPECIALIST_CTL = APP_CONTEXT + "/ctl/SpecialistCtl";
	public String SPECIALIST_LIST_CTL = APP_CONTEXT + "/ctl/SpecialistListCtl";
	public String WARD_CTL = APP_CONTEXT + "/ctl/WardCtl";
	public String WARD_LIST_CTL = APP_CONTEXT + "/ctl/WardListCtl";
}

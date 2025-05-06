package com.hms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hms.dto.BaseDTO;
import com.hms.dto.RoleDTO;
import com.hms.dto.UserDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.ModelFactory;
import com.hms.model.UserModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * 
 * @author Avnish Upadhyay
 *
 */
@WebServlet(urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	public static final String OP_SIGN_UP = "SignUp";

	/*
	 * protected boolean validate(HttpServletRequest request) { boolean pass = true;
	 * System.out.println("validate started"); if
	 * (DataValidator.isNull(request.getParameter("name"))) {
	 * request.setAttribute("name", PropertyReader.getValue("error.require",
	 * "Name")); pass = false; } else if
	 * (!DataValidator.isName(request.getParameter("name"))) {
	 * request.setAttribute("name", "Name contain Alphabets only"); pass = false; }
	 * 
	 * if (DataValidator.isNull(request.getParameter("password"))) {
	 * request.setAttribute("password", PropertyReader.getValue("error.require",
	 * "Password")); pass = false; } else if
	 * (!DataValidator.isPasswordLength(request.getParameter("password"))) {
	 * request.setAttribute("password", "Password should be 8 to 12 characters");
	 * pass = false; } else if
	 * (!DataValidator.isPassword(request.getParameter("password"))) {
	 * request.setAttribute("password",
	 * "Password Must contain uppercase, lowercase, digit & special character");
	 * pass = false; } if
	 * (DataValidator.isNull(request.getParameter("confirmPassword"))) {
	 * request.setAttribute("confirmPassword",
	 * PropertyReader.getValue("error.require", "Confirm Password")); pass = false;
	 * } if (DataValidator.isNull(request.getParameter("gender"))) {
	 * request.setAttribute("gender", PropertyReader.getValue("error.require",
	 * "Gender")); pass = false; } if
	 * (DataValidator.isNull(request.getParameter("mobileNo"))) {
	 * request.setAttribute("mobileNo", PropertyReader.getValue("error.require",
	 * "Mobile No")); pass = false; } else if
	 * (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
	 * request.setAttribute("mobileNo", "Please Enter Valid Mobile Number"); pass =
	 * false; } if (DataValidator.isNull(request.getParameter("userName"))) {
	 * request.setAttribute("userName", PropertyReader.getValue("error.require",
	 * "User Name")); pass = false; } else if
	 * (!DataValidator.isEmail(request.getParameter("userName"))) {
	 * request.setAttribute("userName", PropertyReader.getValue("error.email",
	 * "User Name")); pass = false; } if
	 * (DataValidator.isNull(request.getParameter("dob"))) {
	 * request.setAttribute("dob", PropertyReader.getValue("error.require",
	 * "Date Of Birth")); pass = false; } if
	 * (!request.getParameter("password").equals(request.getParameter(
	 * "confirmPassword")) && !"".equals(request.getParameter("confirmPassword"))) {
	 * request.setAttribute("confirmPassword",
	 * "Confirm Password should be matched"); pass = false; }
	 * System.out.println("validate end " + pass);
	 * 
	 * return pass; }
	 */

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

//		log.debug("UserRegistrationCtl Method populatedto Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(RoleDTO.PATIENT);

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		/*
		 * log.debug("UserRegistrationCtl Method populatedto Ended");
		 */
		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				// userModel.add(dto);
				long pk = userModel.registerUser(dto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Registration successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("User Name already exists", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}

}

package com.hms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
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
 * Myprofile functionality controller.to perform update profile operation and
 * show profile
 * @author Avnish Upadhyay
 *
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";

	private static Logger log = Logger.getLogger(MyProfileCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MyProfileCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

			return pass;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			System.out.println("name" + request.getParameter("name"));
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} 

		log.debug("MyProfileCtl Method validate Ended");

		return pass;

	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("MyProfileCtl Method populatebean Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		populateBean(dto,request);
		

		return dto;
	}

	/**
	 * Display Concept for viewing profile page view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug("MyprofileCtl Method doGet Started");

		UserDTO userdto = (UserDTO) session.getAttribute("user");
		long id = userdto.getId();
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModelInt model =ModelFactory.getInstance().getUserModel() ;
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			UserDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MyProfileCtl Method doGet Ended");
	}

	/**
	 * Submit Concept
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug("MyprofileCtl Method doPost Started");

		UserDTO Userdto = (UserDTO) session.getAttribute("user");
		long id = Userdto.getId();
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				if (id > 0) {
					Userdto.setName(dto.getName());
					Userdto.setUserName(dto.getUserName());
					Userdto.setGender(dto.getGender());
					System.out.println(dto.getGender());
					Userdto.setMobileNo(dto.getMobileNo());
					Userdto.setDob(dto.getDob());
					model.update(Userdto);

				}
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("MyProfileCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}


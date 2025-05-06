package com.hms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.UserDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.RecordNotFoundException;
import com.hms.model.ModelFactory;
import com.hms.model.UserModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.ServletUtility;

import com.hms.util.PropertyReader;

/**
 * forget password ctl.To perform password send in email
 * 
 * @author Avnish Upadhyay
 *
 */
@WebServlet(name = "ForgetPasswordCtl" ,urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.email", "User Name"));
			pass = false;
		}
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		populateBean(dto, request);

		System.out.println("ForgetPasswordCtl =====>" + "in populate DTO");

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("do get method started");
		System.out.println("forget password......doget");
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("do get method started");
		String op = request.getParameter("operation");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		UserDTO dto = (UserDTO) populateDTO(request);
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				userModel.forgetPassword(dto.getUserName());
				ServletUtility.setSuccessMessage("Password has been sent to your registered username id.", request);

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.forward(getView(), request, response);

		}

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}

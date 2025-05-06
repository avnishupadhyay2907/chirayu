package com.hms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.RoleDTO;
import com.hms.dto.UserDTO;
import com.hms.exception.ApplicationException;
import com.hms.model.ModelFactory;
import com.hms.model.RoleModelInt;
import com.hms.model.UserModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

/**
 * login functionality controller. perform login operation
 * 
 * @author Avnish Upadhyay
 *
 */

@WebServlet(urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";
	private static Logger log = Logger.getLogger(LoginCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		System.out.println(request.getParameter("userName") + ".........." + request.getParameter("password"));

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.email", "User Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "password"));
			pass = false;
		}
		System.out.println(pass + "/////");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		System.out.println(request.getParameter("Login Ctl =====>" + "userName"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(request.getParameter("userName"));

		String op = request.getParameter("operation");

		UserModelInt model = ModelFactory.getInstance().getUserModel();

		HttpSession session = request.getSession(true);

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("User Logged Out Successfully!", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;
		}
		if (id > 0) {
			UserDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(";;;" + op);

		HttpSession session = request.getSession(true);

		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		RoleModelInt model1 = ModelFactory.getInstance().getRoleModel();

		// long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				dto = userModel.authenticate(dto.getUserName(), dto.getPassword());
				if (dto != null) {
					session.setAttribute("user", dto);
					long roleId = dto.getRoleId();
					RoleDTO rdto = model1.findByPK(roleId);
					if (rdto != null) {
						session.setAttribute("role", rdto.getName());
					}
					String uri = (String) request.getParameter("uri");
					if (uri == null || "null".equalsIgnoreCase(uri)) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						System.out.println();
						if (rdto.getId() == 1) {
							ServletUtility.redirect(uri, request, response);
						} else {
							ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						}

						return;
					}

				} else {
					dto = (UserDTO) populateDTO(request);
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Invalid UserName And Password!", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOGIN_VIEW;
	}

}

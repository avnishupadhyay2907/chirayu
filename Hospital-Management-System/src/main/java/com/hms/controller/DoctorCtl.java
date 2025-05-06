package com.hms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.DoctorDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.DoctorModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "DoctorCtl", urlPatterns = { "/ctl/DoctorCtl" })
public class DoctorCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorCtl.class);

	protected boolean validate(HttpServletRequest request) {

		System.out.println("Doctor ====>" + "in validate method started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Please Enter Correct Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phone"))) {
			request.setAttribute("phone", PropertyReader.getValue("error.require", "Phone"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("phone"))) {
			request.setAttribute("phone", "Please Enter Valid Phone Number");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}
		System.out.println("Doctor ====>" + "in validate method ended");
		return pass;
	}

	protected void preload(HttpServletRequest request) {
		SpecialistModelInt model = ModelFactory.getInstance().getSpecialistModel();
		try {
			List list = model.list();
			request.setAttribute("specialist", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		System.out.println("Doctor =====>" + "populate dto method started");

		DoctorDTO dto = new DoctorDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setPhone(DataUtility.getString(request.getParameter("phone")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setSpecialistName(DataUtility.getString(request.getParameter("specialistName")));
		
		System.out.println("syso===>" + request.getParameter("name"));
		System.out.println("syso===>" + request.getParameter("phone"));
		System.out.println("syso===>" + request.getParameter("email"));
		System.out.println("syso===>" + request.getParameter("specialistName"));

		System.out.println("Doctor =====>" + "populate dto method ended");
		populateBean(dto, request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Doctor =====>" + "in doget method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();
		if (id > 0 || op != null) {
			DoctorDTO dto;
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
		System.out.println("Doctor =====>" + "in doget method ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Doctor =====>" + "in do post method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DoctorDTO dto = (DoctorDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Doctor add" + dto + "id...." + id);
					// long pk
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Enail Id Already Exists", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
		System.out.println("Doctor =====>" + "in do post method ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DOCTOR_VIEW;
	}

}

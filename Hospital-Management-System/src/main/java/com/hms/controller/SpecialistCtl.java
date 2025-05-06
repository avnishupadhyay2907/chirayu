package com.hms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.SpecialistDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.DoctorModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "SpecialistCtl", urlPatterns = { "/ctl/SpecialistCtl" })
public class SpecialistCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorCtl.class);

	protected boolean validate(HttpServletRequest request) {

		System.out.println("Specialist ====>" + "in validate method started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Please Enter Correct Name");
			pass = false;
		}

		System.out.println("Specialist ====>" + "in validate method ended");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		System.out.println("Specialist =====>" + "populate dto method started");

		SpecialistDTO dto = new SpecialistDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));

		System.out.println("syso===>" + request.getParameter("name"));

		System.out.println("Specialist =====>" + "populate dto method ended");
		populateBean(dto, request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Specialist =====>" + "in doget method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		SpecialistModelInt model = ModelFactory.getInstance().getSpecialistModel();
		if (id > 0 || op != null) {
			SpecialistDTO dto;
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
		System.out.println("Specialist =====>" + "in doget method ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Specialist =====>" + "in do post method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		SpecialistModelInt model = ModelFactory.getInstance().getSpecialistModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SpecialistDTO dto = (SpecialistDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Specialist add" + dto + "id...." + id);
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
			ServletUtility.redirect(ORSView.SPECIALIST_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SPECIALIST_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
		System.out.println("Doctor =====>" + "in do post method ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SPECIALIST_VIEW;
	}

}

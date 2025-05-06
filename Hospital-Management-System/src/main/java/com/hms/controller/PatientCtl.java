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
import com.hms.dto.PatientDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.DiseaseModelInt;
import com.hms.model.DoctorModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.PatientModelInt;
import com.hms.model.SpecialistModelInt;
import com.hms.model.WardModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorCtl.class);

	protected boolean validate(HttpServletRequest request) {

		System.out.println("Patient ====>" + "in validate method started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Please Enter Correct Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("age"))) {
			request.setAttribute("age", PropertyReader.getValue("error.require", "Age"));
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
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("wardId"))) {
			request.setAttribute("wardId", PropertyReader.getValue("error.require", "Ward Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doctorId"))) {
			request.setAttribute("doctorId", PropertyReader.getValue("error.require", "Doctor Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("diseaseId"))) {
			request.setAttribute("diseaseId", PropertyReader.getValue("error.require", "Disease Id"));
			pass = false;
		}
		System.out.println("Patient ====>" + "in validate method ended");
		return pass;
	}

	protected void preload(HttpServletRequest request) {
		System.out.println("Patient =====>" + "in preload method");

		WardModelInt model = ModelFactory.getInstance().getWardModel();
		DoctorModelInt dmodel = ModelFactory.getInstance().getDoctorModel();
		DiseaseModelInt dismodel = ModelFactory.getInstance().getDiseaseModel();

		try {
			List list = model.list();
			request.setAttribute("wardlist", list);

			List dlist = dmodel.list();
			request.setAttribute("doctorlist", dlist);

			List dislist = dismodel.list();
			request.setAttribute("diseaselist", dislist);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		System.out.println("Patient =====>" + "populate dto method started");

		PatientDTO dto = new PatientDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setAge(DataUtility.getDate(request.getParameter("age")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setPhone(DataUtility.getString(request.getParameter("phone")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setWardId(DataUtility.getLong(request.getParameter("wardId")));
		dto.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
		dto.setDiseaseId(DataUtility.getLong(request.getParameter("diseaseId")));

		System.out.println("syso===>" + request.getParameter("name"));
		System.out.println("syso===>" + request.getParameter("age"));
		System.out.println("syso===>" + request.getParameter("gender"));
		System.out.println("syso===>" + request.getParameter("phone"));
		System.out.println("syso===>" + request.getParameter("address"));
		System.out.println("syso===>" + request.getParameter("wardId"));
		System.out.println("syso===>" + request.getParameter("doctorId"));
		System.out.println("syso===>" + request.getParameter("diseaseId"));

		System.out.println("Patient =====>" + "populate dto method ended");
		populateBean(dto, request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Patient =====>" + "in doget method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		PatientModelInt model = ModelFactory.getInstance().getPatientModel();
		if (id > 0 || op != null) {
			PatientDTO dto;
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
		System.out.println("Patient =====>" + "in doget method ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Patient =====>" + "in do post method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		PatientModelInt model = ModelFactory.getInstance().getPatientModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PatientDTO dto = (PatientDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Patient add" + dto + "id...." + id);
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
			ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
		System.out.println("Doctor =====>" + "in do post method ended");
	}

	@Override
	protected String getView() {
		return ORSView.PATIENT_VIEW;
	}

}

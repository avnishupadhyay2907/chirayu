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
import com.hms.dto.DoctorScheduleDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.DoctorModelInt;
import com.hms.model.DoctorScheduleModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "DoctorScheduleCtl", urlPatterns = { "/ctl/DoctorScheduleCtl" })
public class DoctorScheduleCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorScheduleCtl.class);

	protected boolean validate(HttpServletRequest request) {

		System.out.println("Doctor Schedule ====>" + "in validate method started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("day"))) {
			request.setAttribute("day", PropertyReader.getValue("error.require", "Day"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doctorId"))) {
			request.setAttribute("doctorId", PropertyReader.getValue("error.require", "Doctor Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("startTime"))) {
			request.setAttribute("startTime", PropertyReader.getValue("error.require", "Start Time"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("endTime"))) {
			request.setAttribute("endTime", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		}
		System.out.println("Doctor Schedule ====>" + "in validate method ended");
		return pass;
	}

	protected void preload(HttpServletRequest request) {
		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();
		try {
			List list = model.list();
			request.setAttribute("doctorlist", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		System.out.println("Doctor Schedule =====>" + "populate dto method started");

		DoctorScheduleDTO dto = new DoctorScheduleDTO();

		dto.setDay(DataUtility.getString(request.getParameter("day")));
		dto.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
		dto.setStartTime(DataUtility.getString(request.getParameter("startTime")));
		dto.setEndTime(DataUtility.getString(request.getParameter("endTime")));

		System.out.println("syso===>" + request.getParameter("day"));
		System.out.println("syso===>" + request.getParameter("doctorId"));
		System.out.println("syso===>" + request.getParameter("startTime"));
		System.out.println("syso===>" + request.getParameter("endTime"));

		System.out.println("Doctor Schedule =====>" + "populate dto method ended");
		populateBean(dto, request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Doctor Schedule =====>" + "in doget method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		DoctorScheduleModelInt model = ModelFactory.getInstance().getDoctorScheduleModel();
		if (id > 0 || op != null) {
			DoctorScheduleDTO dto;
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
		System.out.println("Doctor Schedule =====>" + "in doget method ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Doctor Schedule =====>" + "in do post method started");

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		DoctorScheduleModelInt model = ModelFactory.getInstance().getDoctorScheduleModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DoctorScheduleDTO dto = (DoctorScheduleDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Doctor Schedule  add" + dto + "id...." + id);
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
			ServletUtility.redirect(ORSView.DOCTOR_SCHEDULE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DOCTOR_SCHEDULE_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
		System.out.println("Doctor Schedule =====>" + "in do post method ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DOCTOR_SCHEDULE_VIEW;
	}

}

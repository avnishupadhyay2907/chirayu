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
import com.hms.model.DiseaseModelInt;
import com.hms.model.DoctorModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.PatientModelInt;
import com.hms.model.SpecialistModelInt;
import com.hms.model.WardModelInt;
import com.hms.util.DataUtility;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "PatientListCtl", urlPatterns = { "/ctl/PatientListCtl" })
public class PatientListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PatientListCtl.class);

	protected void preload(HttpServletRequest request) {
		System.out.println("Patient List=====>" + "in preload method");

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

		System.out.println("Patient List =====>" + "populate dto method started");

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

		System.out.println("Patient List =====>" + "populate dto method ended");

		return dto;
	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Patient List====>" + "do get start");

		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		System.out.println("==========" + pageSize);
		PatientDTO dto = (PatientDTO) populateDTO(request);
		// get the selected checkbox ids array for delete list
		PatientModelInt model = ModelFactory.getInstance().getPatientModel();
		try {
			System.out.println("in ctllllllllll search");
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("Disease list do get end");

	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Patient ====>" + "do post start");
		log.debug("Patient do post start");
		List list;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		PatientDTO dto = (PatientDTO) populateDTO(request);
		PatientModelInt model = ModelFactory.getInstance().getPatientModel();
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "next".equalsIgnoreCase(op) || "previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					PatientDTO deletebean = new PatientDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			List next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
		}

		log.debug("Doctor list do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PATIENT_LIST_VIEW;
	}

}

package com.hms.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.DiseaseDTO;
import com.hms.dto.RoleDTO;
import com.hms.dto.SpecialistDTO;
import com.hms.exception.ApplicationException;
import com.hms.exception.DuplicateRecordException;
import com.hms.model.DiseaseModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.RoleModelInt;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "DiseaseCtl", urlPatterns = { "/ctl/DiseaseCtl" })
public class DiseaseCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DiseaseCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Please Enter Correct Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("specialistId"))) {
			request.setAttribute("specialistId", PropertyReader.getValue("error.require", "Specialist Id"));
			pass = false;
		}

		return pass;
	}

	protected void preload(HttpServletRequest request) {
		SpecialistModelInt model = ModelFactory.getInstance().getSpecialistModel();
		try {
			List list = model.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SpecialistDTO dto = (SpecialistDTO) it.next();
				System.out.println(dto.getId());
				System.out.println(dto.getName());

			}

			request.setAttribute("specialistList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		DiseaseDTO dto = new DiseaseDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setSpecialistId(DataUtility.getLong(request.getParameter("specialistId")));

		
		System.out.println("syso===>" + request.getParameter("name"));
		System.out.println("syso===>" + request.getParameter("specialistId"));
		System.out.println("syso===>" + request.getParameter("specialistName"));
		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		DiseaseModelInt model = ModelFactory.getInstance().getDiseaseModel();
		if (id > 0 || op != null) {
			DiseaseDTO dto;
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		DiseaseModelInt model = ModelFactory.getInstance().getDiseaseModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DiseaseDTO dto = (DiseaseDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Disease add" + dto + "id...." + id);
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
				ServletUtility.setErrorMessage("User Name Already Exists", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.DISEASE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DISEASE_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DISEASE_VIEW;
	}

}

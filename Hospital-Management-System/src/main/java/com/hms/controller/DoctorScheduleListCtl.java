package com.hms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.DoctorScheduleDTO;
import com.hms.exception.ApplicationException;
import com.hms.model.DoctorModelInt;
import com.hms.model.DoctorScheduleModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "DoctorScheduleListCtl", urlPatterns = { "/ctl/DoctorScheduleListCtl" })
public class DoctorScheduleListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorListCtl.class);

	protected void preload(HttpServletRequest request) {
		DoctorScheduleModelInt model = ModelFactory.getInstance().getDoctorScheduleModel();
		try {
			List list = model.list();
			request.setAttribute("doctorlist", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		System.out.println("Doctor Schedule List =====>" + "Populate Bean started");
		log.debug("Disease list populate bean start");
		DoctorScheduleDTO dto = new DoctorScheduleDTO();
		dto.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
		dto.setDay(DataUtility.getString(request.getParameter("day")));

		dto.setStartTime(DataUtility.getString(request.getParameter("startTime")));
		dto.setEndTime(DataUtility.getString(request.getParameter("endTime")));

		System.out.println("syso===>" + request.getParameter("day"));
		System.out.println("syso===>" + request.getParameter("doctorId"));
		System.out.println("syso===>" + request.getParameter("startTime"));
		System.out.println("syso===>" + request.getParameter("endTime"));

		log.debug("Disease list populate bean end");
		System.out.println("Doctor Schedule List =====>" + "populate Bean ended" + dto);

		return dto;
	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Doctor Schedule List ====>" + "do get start");

		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		System.out.println("==========" + pageSize);
		DoctorScheduleDTO dto = (DoctorScheduleDTO) populateDTO(request);
		// get the selected checkbox ids array for delete list
		DoctorScheduleModelInt model = ModelFactory.getInstance().getDoctorScheduleModel();
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

		log.debug("Doctor Schedule list do get end");

	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Doctor Schedule List ====>" + "do post start");
		log.debug("Disease do post start");
		List list;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		DoctorScheduleModelInt model = ModelFactory.getInstance().getDoctorScheduleModel();
		DoctorScheduleDTO dto = (DoctorScheduleDTO) populateDTO(request);
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
				ServletUtility.redirect(ORSView.DOCTOR_SCHEDULE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.DOCTOR_SCHEDULE_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.DOCTOR_SCHEDULE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					DoctorScheduleDTO deletebean = new DoctorScheduleDTO();
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

		log.debug("Doctor Schedule list do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DOCTOR_SCHEDULE_LIST_VIEW;
	}

}

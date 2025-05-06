package com.hms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hms.dto.BaseDTO;
import com.hms.dto.WardDTO;
import com.hms.exception.ApplicationException;
import com.hms.model.WardModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name = "WardListCtl", urlPatterns = { "/ctl/WardListCtl" })
public class WardListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(WardListCtl.class);

	protected BaseDTO populateDTO(HttpServletRequest request) {
		System.out.println("Ward List =====>" + "Populate Bean started");
		log.debug("Ward list populate bean start");
		WardDTO dto = new WardDTO();

		dto.setType(DataUtility.getString(request.getParameter("type")));
		dto.setCapacity(DataUtility.getString(request.getParameter("capacity")));
		dto.setChargesPerDay(DataUtility.getString(request.getParameter("chargesPerDay")));

		System.out.println("syso===>" + request.getParameter("type"));
		System.out.println("syso===>" + request.getParameter("capacity"));
		System.out.println("syso===>" + request.getParameter("chargesPerDay"));
		log.debug("Ward list populate bean end");
		System.out.println("Ward List =====>" + "populate Bean ended" + dto);

		return dto;
	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("Ward List ====>" + "do get start");

		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		System.out.println("==========" + pageSize);
		WardDTO dto = (WardDTO) populateDTO(request);
		// get the selected checkbox ids array for delete list
		WardModelInt model = ModelFactory.getInstance().getWardModel();
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

		log.debug("Ward list do get end");

	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Ward ====>" + "do post start");
		log.debug("Ward do post start");
		List list;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		WardModelInt model = ModelFactory.getInstance().getWardModel();
		WardDTO dto = (WardDTO) populateDTO(request);
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
				ServletUtility.redirect(ORSView.WARD_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.WARD_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WARD_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					WardDTO deletebean = new WardDTO();
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

		log.debug("Ward list do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WARD_LIST_VIEW;
	}

}

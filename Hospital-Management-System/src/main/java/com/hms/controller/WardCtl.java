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
import com.hms.exception.DuplicateRecordException;
import com.hms.model.WardModelInt;
import com.hms.model.ModelFactory;
import com.hms.model.SpecialistModelInt;
import com.hms.util.DataUtility;
import com.hms.util.DataValidator;
import com.hms.util.PropertyReader;
import com.hms.util.ServletUtility;

@WebServlet(name ="WardCtl" ,urlPatterns = { "/ctl/WardCtl" })
public class WardCtl extends BaseCtl{

	private static Logger log = Logger.getLogger(WardCtl.class);

	protected boolean validate(HttpServletRequest request) {
	
		System.out.println("Ward ====>"+"in validate method started");
		
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "Type"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("capacity"))) {
			request.setAttribute("capacity", PropertyReader.getValue("error.require", "Capacity"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("chargesPerDay"))) {
			request.setAttribute("chargesPerDay", PropertyReader.getValue("error.require", "Charges Per Day"));
			pass = false;
		}
		System.out.println("Ward ====>"+"in validate method ended");
		return pass;
	}

	
	
	protected BaseDTO populateDTO(HttpServletRequest request) {
		
		System.out.println("Ward =====>"+"populate dto method started");
		
		WardDTO dto = new WardDTO();

		dto.setType(DataUtility.getString(request.getParameter("type")));
		dto.setCapacity(DataUtility.getString(request.getParameter("capacity")));
		dto.setChargesPerDay(DataUtility.getString(request.getParameter("chargesPerDay")));

		System.out.println("syso===>" + request.getParameter("type"));
		System.out.println("syso===>" + request.getParameter("capacity"));
		System.out.println("syso===>" + request.getParameter("chargesPerDay"));

		System.out.println("Ward =====>"+"populate dto method ended");
		populateBean(dto, request);
		return dto;
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("Ward =====>"+"in doget method started");
		
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		WardModelInt model = ModelFactory.getInstance().getWardModel();
		if (id > 0 || op != null) {
			WardDTO dto;
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
		System.out.println("Ward =====>"+"in doget method ended");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		System.out.println("Ward =====>"+"in do post method started");
		
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		WardModelInt model = ModelFactory.getInstance().getWardModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			WardDTO dto = (WardDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Ward add" + dto + "id...." + id);
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
			ServletUtility.redirect(ORSView.WARD_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WARD_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
		System.out.println("Ward =====>"+"in do post method ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.WARD_VIEW;
	}

}


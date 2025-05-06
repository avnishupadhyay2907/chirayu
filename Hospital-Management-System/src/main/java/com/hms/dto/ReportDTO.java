package com.hms.dto;

public class ReportDTO extends BaseDTO{

	private long patientId;
	private String patientName;
	private String description;
	private String reportDate;
	
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return description;
	}

}

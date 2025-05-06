package com.hms.dto;

public class DiseaseDTO extends BaseDTO{

	private String name;
	private long specialistId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSpecialistId() {
		return specialistId;
	}
	public void setSpecialistId(long specialistId) {
		this.specialistId = specialistId;
	}
	
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}

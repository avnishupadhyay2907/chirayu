package com.hms.dto;

public class WardDTO extends BaseDTO {

	private String type;
	private String capacity;
	private String chargesPerDay;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getChargesPerDay() {
		return chargesPerDay;
	}

	public void setChargesPerDay(String chargesPerDay) {
		this.chargesPerDay = chargesPerDay;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return type;
	}

}

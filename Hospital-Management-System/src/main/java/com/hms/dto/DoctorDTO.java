package com.hms.dto;

import java.io.Serializable;

public class DoctorDTO extends BaseDTO implements Serializable {

	private String name;
	private String specialistName;
	private String phone;
	private String email;

	// ✅ Add public no-arg constructor
	public DoctorDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name;
	}
}

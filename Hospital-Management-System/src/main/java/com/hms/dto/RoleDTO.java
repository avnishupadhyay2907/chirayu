package com.hms.dto;
/**
 * Role JavaDto encapsulates role attributes
 * @author Avnish Upadhyay
 *
 */

public class RoleDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ADMIN = 1;
	public static final int DOCTOR = 2;
	public static final int RECEPTIONIST = 3;
	public static final int MANAGER = 4;
	public static final int PATIENT	 = 5;
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {

		return id + "";
	}

	public String getValue() {

		return name + "";
	}
}

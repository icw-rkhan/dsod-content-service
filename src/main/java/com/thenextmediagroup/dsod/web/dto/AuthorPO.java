package com.thenextmediagroup.dsod.web.dto;

public class AuthorPO {

	private String _id;
	private int sort;
	private String firstName;
	private String lastName;
	private String authorDetails;
	private String email;
	private Integer cellPhone;
	private String role;
	private String objectId;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAuthorDetails() {
		return authorDetails;
	}
	public void setAuthorDetails(String authorDetails) {
		this.authorDetails = authorDetails;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(Integer cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
}

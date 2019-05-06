package com.thenextmediagroup.dsod.model;

public class Author {

	private String _id;
	private int sort;
	private String firstName;
	private String lastName;
	private String fullName;
	private String authorDetails;
	private String email;
	private Long cellPhone;
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
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public Long getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(Long cellPhone) {
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

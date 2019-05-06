package com.thenextmediagroup.dsod.model;

import java.util.Date;

import javax.persistence.Table;


@Table(name = "comment")
public class Comment {

	String email;
	double comment_rating;
	String comment_text;
	String content_id;
	Date create_time;
	
	private String fullName;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getComment_rating() {
		return comment_rating;
	}
	public void setComment_rating(double comment_rating) {
		this.comment_rating = comment_rating;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}

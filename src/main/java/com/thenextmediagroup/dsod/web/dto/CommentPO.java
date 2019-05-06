package com.thenextmediagroup.dsod.web.dto;

public class CommentPO {

	String email;
	String contentId;
	String commentText;
	double commentRating;
	
	private String fullName;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public double getCommentRating() {
		return commentRating;
	}
	public void setCommentRating(double commentRating) {
		this.commentRating = commentRating;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}

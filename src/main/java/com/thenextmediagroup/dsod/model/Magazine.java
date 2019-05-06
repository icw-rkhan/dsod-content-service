package com.thenextmediagroup.dsod.model;

import java.util.Date;

public class Magazine {

	private String _id;
	private String serial;
	private String vol;
	private Date publishDate;
	private String cover;
	private String [] articles;
	private Date createDate;
	private String createUser;
	private Boolean isRelease;
	private String issue;
	private int sort;
	private int status;// 1、review;2、published；3、draft；
	private String pdfId;
	private Integer version;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getVol() {
		return vol;
	}
	public void setVol(String vol) {
		this.vol = vol;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String[] getArticles() {
		return articles;
	}
	public void setArticles(String[] articles) {
		this.articles = articles;
	}
	

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Boolean getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPdfId() {
		return pdfId;
	}
	public void setPdfId(String pdfId) {
		this.pdfId = pdfId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}

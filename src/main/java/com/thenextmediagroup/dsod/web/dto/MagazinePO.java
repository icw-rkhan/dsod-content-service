package com.thenextmediagroup.dsod.web.dto;

import java.util.Date;
import java.util.List;

import com.thenextmediagroup.dsod.model.Content;

public class MagazinePO {

	private String id;
	private String serial;
	private String vol;
	private Date publishDate;
	private String cover;
	private String [] articles;
	private List<ContentPO> contents;
	private String createUser;
	private Boolean isRelease;
	private String issue;
	private int sort;
	private int status;
	private String pdfId;
	private String pdfUrl;
	private Integer version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<ContentPO> getContents() {
		return contents;
	}
	public void setContents(List<ContentPO> contents) {
		this.contents = contents;
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
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
}

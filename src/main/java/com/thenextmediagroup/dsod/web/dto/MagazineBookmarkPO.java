package com.thenextmediagroup.dsod.web.dto;

import java.util.List;

import com.thenextmediagroup.dsod.model.Content;

public class MagazineBookmarkPO {

	private String serial;

	private String vol;

	private String issue;

	private List<Content> contents;

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

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

}

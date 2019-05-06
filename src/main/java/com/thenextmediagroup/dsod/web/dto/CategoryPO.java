package com.thenextmediagroup.dsod.web.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CategoryPO {

	private String id;
	private String name;
	private int sort;
	private String sponsorId;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		if (sponsorId == null) {
			this.sponsorId = "";
		} else {
			this.sponsorId = sponsorId;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

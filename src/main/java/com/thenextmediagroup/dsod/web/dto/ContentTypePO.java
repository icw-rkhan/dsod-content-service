package com.thenextmediagroup.dsod.web.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ContentTypePO {

	private String id;
	private String name;

	/**
	 * 
	 */
	public ContentTypePO() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 */
	public ContentTypePO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

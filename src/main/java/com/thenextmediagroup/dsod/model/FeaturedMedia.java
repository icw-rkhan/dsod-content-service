package com.thenextmediagroup.dsod.model;

import java.util.HashMap;
import java.util.Map;

public class FeaturedMedia {

	private String type;
	private Map<String, String> code = new HashMap<String, String>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getCode() {
		return code;
	}

	public void setCode(Map<String, String> code) {
		this.code = code;
	}

}

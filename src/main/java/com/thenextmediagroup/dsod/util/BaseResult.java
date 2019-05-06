package com.thenextmediagroup.dsod.util;

import java.util.HashMap;
import java.util.Map;

public class BaseResult {
	// status code
	private Integer code;
	// status info
	private String msg;
	// result content
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	public Integer getCode() {
		return code;
	}

	public void setCode(StateCodeInformationUtil code) {
		this.code = code.getCode();
		this.msg = code.getMsg(code.getCode());
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}

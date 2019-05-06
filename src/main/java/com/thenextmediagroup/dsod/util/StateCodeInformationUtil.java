package com.thenextmediagroup.dsod.util;

public enum StateCodeInformationUtil {
	SUCCESS(0, "success"), 
	ERROR(-1, "error"),
	RELEASE_CONTENT_IS_NULL(2000, "release content is null"),
	EMAIL_IS_NULL(2001, "email is null"),
	TITLE_IS_NULL(2002, "title is null"),
	CONTENT_IS_NULL(2003, "content is null"),
	CONTENT_TYPE_ID_IS_NULL(2004, "content type id is null"),
	CATEGORY_ID_IS_NULL(2005,"category id is null"),
	SPONSOR_ID_IS_NULL(2006,"sponser id is null"),
	FEATURED_MEDIA_ID_IS_NULL(2007,"featured media id is null"),
	IS_PRIVATE_IS_NULL(2008,"is private is null"),
	IS_COMPLETE_IS_NULL(2009,"is complete is null"),
	IS_PUBLISH_NOW_IS_NULL(2010,"is publish now is null"),
	FILE_IS_NULL(2011, "file is null"),
	FILE_TYPE_IS_ERROR(2012, "file type is error"),
	QUERY_IS_NULL(2013,"query is null"),
	CONTENT_ID_IS_NULL(2014,"content id is null"),
	SERRCH_VALUE_IS_NULL(2015,"serrch value is null"),
	OBJECT_ID_IS_NULL(2016,"object id is null"),
	EMAIL_TOKEN_INVALID(2017,"email token invalid"),
	OVERLENGTH_TITLE(2018,"overlength title"),
	ID_IS_NULL(2019,"id is null"),
	AUTHOR_IS_NULL(2020,"author is null"),
	AUTHOR_NAME_IS_NULL(2021,"author name is null"),
	SPONSOR_IS_NULL(2022,"sponsor is null"),
	SPONSOR_NAME_IS_NULL(2023,"sponsor name is null"),
	OVERLENGTH_AUTHOR_NAME(2024,"overlength author name"),
	CATEGORY_IS_NULL(2025,"category is null"),
	CATEGORY_NAME_IS_NULL(2026,"category name is null"),
	CONTENTTYPE_IS_NULL(2027,"content type is null"),
	CONTENTTYPE_NAME_IS_NULL(2028,"content type name is null"),
	MAGAZINE_IS_NULL(2029,"magazine is null"),
	MAGAZINE_SERIAL_IS_NULL(2030,"magazine serial is null"),
	MAGAZINE_VOL_IS_NULL(2031,"magazine vol is null"),
	POST_ID_IS_NULL(2032,"post id is null"),
	THIS_POST_IS_ALREADY_BOOKMARKED(2033,"this post is already bookmarked"),
	FIRST_NAME_IS_NULL(2034,"first name is null"),
	LAST_NAME_IS_NULL(2035,"last name is null"),
	CELL_PHONE_IS_NULL(2036,"cell phone is null"),
	CELL_PHONE_IS_WRONG(2037,"cell_phone_is_wrong"),
	ROLE_IS_NULL(2038,"role is null"),
	STATUS_IS_WRONG(2039,"status is wrong"),
	FILE_TYPE_IS_WRONG(2040, "file type is wrong"),
	FILE_IDS_IS_NULL(2041, "file ids is null"),
	PARENTID_IS_NULL(2042, "parentid_is_null"),
	;
	private int code;

	private String msg;

	private StateCodeInformationUtil(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private StateCodeInformationUtil() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg(int code) {
		for (StateCodeInformationUtil values : StateCodeInformationUtil.values()) {
			if (values.getCode() == code) {
				return values.msg;
			}
		}
		return null;
	}

}

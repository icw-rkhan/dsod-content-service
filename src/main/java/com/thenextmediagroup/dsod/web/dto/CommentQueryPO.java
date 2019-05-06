package com.thenextmediagroup.dsod.web.dto;

public class CommentQueryPO {

	String contentId;
	long skip;
	int limit;
	
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public long getSkip() {
		return skip;
	}
	public void setSkip(long skip) {
		this.skip = skip;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}

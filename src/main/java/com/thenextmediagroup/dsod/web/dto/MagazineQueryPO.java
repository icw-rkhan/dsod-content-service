package com.thenextmediagroup.dsod.web.dto;

public class MagazineQueryPO {

	private int status;
	long skip;
	int limit;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}

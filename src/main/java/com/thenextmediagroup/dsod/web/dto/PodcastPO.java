package com.thenextmediagroup.dsod.web.dto;

public class PodcastPO {
	private String podcastPath;
	private String podcastName;
	private String contentType;

	public String getPodcastPath() {
		return podcastPath;
	}

	public void setPodcastPath(String podcastPath) {
		this.podcastPath = podcastPath;
	}

	public String getPodcastName() {
		return podcastName;
	}

	public void setPodcastName(String podcastName) {
		this.podcastName = podcastName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

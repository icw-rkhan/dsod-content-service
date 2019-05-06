package com.thenextmediagroup.dsod.model;

import org.springframework.data.annotation.Transient;

public class VisualEssayImage {
	private String title;
	private String alternateText;
	private String caption;
	private String thumbnailID;
	private String originalID;
	@Transient
	private String thumbnailUrl;
	@Transient
	private String originalUrl;
	
	public String getTitle() {
		return title;
	}
	public String getAlternateText() {
		return alternateText;
	}
	public String getCaption() {
		return caption;
	}
	public String getThumbnailID() {
		return thumbnailID;
	}
	public String getOriginalID() {
		return originalID;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAlternateText(String alternateText) {
		this.alternateText = alternateText;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public void setThumbnailID(String thumbnailID) {
		this.thumbnailID = thumbnailID;
	}
	public void setOriginalID(String originalID) {
		this.originalID = originalID;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
}

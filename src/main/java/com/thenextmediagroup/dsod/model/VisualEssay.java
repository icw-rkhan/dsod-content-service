package com.thenextmediagroup.dsod.model;

import java.util.Date;
import java.util.List;

public class VisualEssay {

	private String id;
	private String title;
	private String description;
	private String authorName;
	private String authorDetails;
	private Date createTime;
    private VisualEssayImage authorImage;
    private List<VisualEssayImage> visualEssayImages;
    private String parentId;
    

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getAuthorName() {
		return authorName;
	}
	public String getAuthorDetails() {
		return authorDetails;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public VisualEssayImage getAuthorImage() {
		return authorImage;
	}
	public List<VisualEssayImage> getVisualEssayImages() {
		return visualEssayImages;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public void setAuthorDetails(String authorDetails) {
		this.authorDetails = authorDetails;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setAuthorImage(VisualEssayImage authorImage) {
		this.authorImage = authorImage;
	}
	public void setVisualEssayImages(List<VisualEssayImage> visualEssayImages) {
		this.visualEssayImages = visualEssayImages;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}

package com.thenextmediagroup.dsod.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Content {
	private String id;
	private String email;
	private String authorId;
	private String title;
	private String content;
	private String contentTypeId;
	private String categoryId;
	private String sponsorId;
	private FeaturedMedia featuredMedia;
	private List<Photos> photos;
	private String[] videos;
	private String[] podcasts;
	private Boolean isPrivate;
	private Boolean isComplete;
	private Boolean isPublishNow;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishDate;

	private String countOfComment;
	private Double avgCommentRating;

	private int readNumber;

	private int sort;

	private Boolean isFeatured;

	private Date publishOn;
	private Date publishEnd;
	private Date reviewOn;
	private String subTitle;
	private int status;// 1、review;2、published；3、draft；4、rejected；
	private Boolean unite = false;
	private Boolean expedite = false;
	private String excerpt;


	private String[] relativeTopics;
	private Integer version;
	private String[] references;
	private List<VisualEssay> visualEssays;
    private String visualEssaysSearchText;
    private String[] visualEssayIds;
    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getReadNumber() {
		return readNumber;
	}

	public void setReadNumber(int readNumber) {
		this.readNumber = readNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public FeaturedMedia getFeaturedMedia() {
		return featuredMedia;
	}

	public void setFeaturedMedia(FeaturedMedia featuredMedia) {
		this.featuredMedia = featuredMedia;
	}

	public List<Photos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}

	public String[] getVideos() {
		return videos;
	}

	public void setVideos(String[] videos) {
		this.videos = videos;
	}

	public String[] getPodcasts() {
		return podcasts;
	}

	public void setPodcasts(String[] podcasts) {
		this.podcasts = podcasts;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Boolean getIsPublishNow() {
		return isPublishNow;
	}

	public void setIsPublishNow(Boolean isPublishNow) {
		this.isPublishNow = isPublishNow;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getCountOfComment() {
		return countOfComment;
	}

	public void setCountOfComment(String countOfComment) {
		this.countOfComment = countOfComment;
	}

	

	public Double getAvgCommentRating() {
		return avgCommentRating;
	}

	public void setAvgCommentRating(Double avgCommentRating) {
		this.avgCommentRating = avgCommentRating;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Date getPublishOn() {
		return publishOn;
	}

	public void setPublishOn(Date publishOn) {
		this.publishOn = publishOn;
	}

	public Date getPublishEnd() {
		return publishEnd;
	}

	public void setPublishEnd(Date publishEnd) {
		this.publishEnd = publishEnd;
	}

	public Date getReviewOn() {
		return reviewOn;
	}

	public void setReviewOn(Date reviewOn) {
		this.reviewOn = reviewOn;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getUnite() {
		return unite;
	}

	public void setUnite(Boolean unite) {
		this.unite = unite;
	}

	public Boolean getExpedite() {
		return expedite;
	}

	public void setExpedite(Boolean expedite) {
		this.expedite = expedite;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String[] getRelativeTopics() {
		return relativeTopics;
	}

	public void setRelativeTopics(String[] relativeTopics) {
		this.relativeTopics = relativeTopics;
	}

	public Integer getVersion() {
		return version == null? 0: version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}

	public List<VisualEssay> getVisualEssays() {
		return visualEssays;
	}

	public void setVisualEssays(List<VisualEssay> visualEssays) {
		this.visualEssays = visualEssays;
	}

	public String getVisualEssaysSearchText() {
		return visualEssaysSearchText;
	}

	public void setVisualEssaysSearchText(String visualEssaysSearchText) {
		this.visualEssaysSearchText = visualEssaysSearchText;
	}

	public String[] getVisualEssayIds() {
		return visualEssayIds;
	}

	public void setVisualEssayIds(String[] visualEssayIds) {
		this.visualEssayIds = visualEssayIds;
	}

}

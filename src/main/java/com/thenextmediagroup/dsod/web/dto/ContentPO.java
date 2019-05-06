package com.thenextmediagroup.dsod.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thenextmediagroup.dsod.model.Author;
import com.thenextmediagroup.dsod.model.Comment;
import com.thenextmediagroup.dsod.model.FeaturedMedia;
import com.thenextmediagroup.dsod.model.Photos;
import com.thenextmediagroup.dsod.model.VisualEssay;

public class ContentPO {
	private String id;
	private String email;
	private String authorId;
	private String title;
	private String content;
	private String contentTypeId;
	private String categoryId;
	private String sponsorId;

	private Author author;
	private String authorPhotoUrl;

	private String contentTypeName;
	private String categoryName;
	private String sponsorName;

	private FeaturedMedia featuredMedia;
	private List<PhotoUrlsPO> photoUrls;
	private List<Photos> photos;
	private String[] videos;
	private String[] videoUrls;
	private String[] podcasts;
	private String[] podcastUrls;
	private Boolean isPrivate;
	private Boolean isComplete;
	private Boolean isPublishNow;
	private Boolean isBookmark;

	private String nextContentId;
	private String previousContentId;
	private String countOfComment;
	private Double avgCommentRating;
	private List<Comment> comment;

	private Date publishDate;

	private Boolean isFeatured;

	private int readNumber;

	private Date publishOn;
	private Date publishEnd;
	private Date reviewOn;
	private String subTitle;
	private int status;// 1、review;2、published;3、draft;4、rejected
	private Boolean unite = false;
	private Boolean expedite = false;
	private String excerpt;


	private List<VisualEssay> visualEssays;

	private String[] relativeTopics;
	private List<Map<String, String>> relativeTopicList = new ArrayList<Map<String, String>>();
	private String[] references;
	
	private Integer version;
	private String[] visualEssayIds;
	
	
	public String[] getVisualEssayIds() {
		return visualEssayIds;
	}

	public void setVisualEssayIds(String[] visualEssayIds) {
		this.visualEssayIds = visualEssayIds;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

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

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Boolean getIsBookmark() {
		return isBookmark;
	}

	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

	public String getNextContentId() {
		return nextContentId;
	}

	public void setNextContentId(String nextContentId) {
		this.nextContentId = nextContentId;
	}

	public String getPreviousContentId() {
		return previousContentId;
	}

	public void setPreviousContentId(String previousContentId) {
		this.previousContentId = previousContentId;
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

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public String getContentTypeName() {
		return contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponserName) {
		this.sponsorName = sponserName;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getAuthorPhotoUrl() {
		return authorPhotoUrl;
	}

	public void setAuthorPhotoUrl(String authorPhotoUrl) {
		this.authorPhotoUrl = authorPhotoUrl;
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

	public List<PhotoUrlsPO> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<PhotoUrlsPO> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public String[] getVideoUrls() {
		return videoUrls;
	}

	public void setVideoUrls(String[] videoUrls) {
		this.videoUrls = videoUrls;
	}

	public String[] getPodcastUrls() {
		return podcastUrls;
	}

	public void setPodcastUrls(String[] podcastUrls) {
		this.podcastUrls = podcastUrls;
	}

	public List<VisualEssay> getVisualEssays() {
		return visualEssays;
	}

	public void setVisualEssays(List<VisualEssay> visualEssays) {
		this.visualEssays = visualEssays;
	}

	public FeaturedMedia getFeaturedMedia() {
		return featuredMedia;
	}

	public void setFeaturedMedia(FeaturedMedia featuredMedia) {
		this.featuredMedia = featuredMedia;
	}

	public String[] getRelativeTopics() {
		return relativeTopics;
	}

	public void setRelativeTopics(String[] relativeTopics) {
		this.relativeTopics = relativeTopics;
	}

	public List<Map<String, String>> getRelativeTopicList() {
		return relativeTopicList;
	}

	public void setRelativeTopicList(List<Map<String, String>> relativeTopicList) {
		this.relativeTopicList = relativeTopicList;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}

package com.thenextmediagroup.dsod.web.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class BookmarkPO {

	private String email;
	private String url;
	private String title;
	private String postId;
	private Date create_time;
	private String categoryId;
	private String contentTypeId;
	private String categoryName;
	private String contentTypeName;
	
	private String coverUrl;
	private String coverthumbnailUrl;
	private String excerpt;
	
	private long skip;
	private int limit;
	
	private int status;//1、Article；2、unite
	private MagazineBookmarkPO magazineBookmarkPO;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContentTypeName() {
		return contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
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

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getCoverthumbnailUrl() {
		return coverthumbnailUrl;
	}

	public void setCoverthumbnailUrl(String coverthumbnailUrl) {
		this.coverthumbnailUrl = coverthumbnailUrl;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public MagazineBookmarkPO getMagazineBookmarkPO() {
		return magazineBookmarkPO;
	}

	public void setMagazineBookmarkPO(MagazineBookmarkPO magazineBookmarkPO) {
		this.magazineBookmarkPO = magazineBookmarkPO;
	}

}

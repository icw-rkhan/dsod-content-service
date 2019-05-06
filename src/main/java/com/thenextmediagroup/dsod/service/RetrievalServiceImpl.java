package com.thenextmediagroup.dsod.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.thenextmediagroup.dsod.model.Author;
import com.thenextmediagroup.dsod.model.Bookmark;
import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.model.Content;
import com.thenextmediagroup.dsod.model.ContentType;
import com.thenextmediagroup.dsod.model.FeaturedMedia;
import com.thenextmediagroup.dsod.model.Photos;
import com.thenextmediagroup.dsod.model.Sponsor;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.PhotoUrlsPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@Service
public class RetrievalServiceImpl implements RetrievalService {

	private static Logger logger = Logger.getLogger(RetrievalServiceImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${spring.server.currentPath}")
	private String currentPath;
	@Value("${spring.server.placeholder}")
	private String placeHolder;
	
	@Override
	public BaseResult retrievalContentsByType(QueryPO queryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query queryContent = new Query();
			
			
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			List<ContentPO> contentPOs = new ArrayList<ContentPO>();
			
			Criteria criteriaContent = new Criteria();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("name").regex(queryPO.getSearchValue(),"i"));
			List<Category> categories = mongoTemplate.find(query, Category.class);
			if (null != categories && 0 < categories.size()) {

				for (int i = 0; i < categories.size(); i++) {
					criteriaList.add(Criteria.where("categoryId").is(categories.get(i).getId()));
				}
			}
			List<ContentType> contentTypes = mongoTemplate.find(query, ContentType.class);
			if (null != contentTypes && 0 < contentTypes.size()) {
				for (int i = 0; i < contentTypes.size(); i++) {
					criteriaList.add(Criteria.where("contentTypeId").is(contentTypes.get(i).getId()));
				}
			}

			Query queryAuthor = new Query();
			queryAuthor.addCriteria(Criteria.where("fullName").regex(queryPO.getSearchValue(),"i"));
			List<Author> authors = mongoTemplate.find(queryAuthor, Author.class);
			if (null != authors && 0 < authors.size()) {
				for (int i = 0; i < authors.size(); i++) {
					criteriaList.add(Criteria.where("authorId").is(authors.get(i).get_id()));
				}
			}
			List<Content> contents = new ArrayList<>();
			criteriaContent.orOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			queryContent.addCriteria(new Criteria()
									.andOperator(
											Criteria.where("publishOn").lte(new Date()),
											Criteria.where("publishEnd").gte(new Date()),
											Criteria.where("status").is(2),
											criteriaContent
											));
			long totalFound = mongoTemplate.count(queryContent, Content.class);
			queryContent.limit(queryPO.getLimit());
			queryContent.skip(queryPO.getSkip());
			
			if(criteriaList.size()>0) {
				
				contents = mongoTemplate.find(queryContent, Content.class);
				
				for (Content content : contents) {
					ContentPO contentPO = new ContentPO();
					BeanUtils.copyProperties(content,contentPO);
					if (!ValidationUtil.isBlank(content.getCategoryId())) {
						List<Category> category = mongoTemplate.find(
								new Query().addCriteria(Criteria.where("_id").is(content.getCategoryId())), Category.class);
						if (category.size() > 0) {
							contentPO.setCategoryName(category.get(0).getName());
						}
					}

					if (!ValidationUtil.isBlank(content.getContentTypeId())) {
						List<ContentType> contentType = mongoTemplate.find(
								new Query().addCriteria(Criteria.where("_id").is(content.getContentTypeId())),
								ContentType.class);
						if (contentType.size() > 0) {
							contentPO.setContentTypeName(contentType.get(0).getName());
						}
					}

					if (!ValidationUtil.isBlank(content.getSponsorId())) {
						List<Sponsor> sponsor = mongoTemplate.find(
								new Query().addCriteria(Criteria.where("_id").is(content.getSponsorId())), Sponsor.class);
						if (sponsor.size() > 0) {
							contentPO.setSponsorName(sponsor.get(0).getName());
						}
					}

					if (!ValidationUtil.isBlank(content.getAuthorId())) {
						List<Author> author = mongoTemplate.find(
								new Query().addCriteria(Criteria.where("_id").is(content.getAuthorId())), Author.class);
						if (author.size() > 0) {
							contentPO.setAuthor(author.get(0));
							if (author.get(0).getObjectId() != null && !"".equals(author.get(0).getObjectId().trim())) {
								contentPO.setAuthorPhotoUrl(currentPath + "?objectId=" + author.get(0).getObjectId());
							}
						}
					}
					List<Bookmark> bookmarks = mongoTemplate
							.find(new Query().addCriteria(Criteria.where("postId").is(content.getId()).and("email").is(queryPO.getEmail())), Bookmark.class);
					if (null != bookmarks && 0 < bookmarks.size()) {
						contentPO.setIsBookmark(true);
					} else {
						contentPO.setIsBookmark(false);
					}
					if (null != content.getFeaturedMedia()) {
						FeaturedMedia featuredMedia = content.getFeaturedMedia();
						if(featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
							featuredMedia.getCode().put("thumbnailUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("thumbnail"));
							featuredMedia.getCode().put("originalUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("original"));
						}
					}
					List<Photos> photos = content.getPhotos();
					if (null != photos && 0 < photos.size()) {
						List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
						for (int i = 0; i < photos.size(); i++) {
							PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
							photoUrlsPO.setOriginalUrl(currentPath + "?objectId=" + photos.get(i).getOriginal());
							photoUrlsPO.setThumbnailUrl(currentPath + "?objectId=" + photos.get(i).getThumbnail());
							photoUrlsPOs.add(photoUrlsPO);
						}
						contentPO.setPhotoUrls(photoUrlsPOs);
					}
					if (null != content.getVideos() && 0 < content.getVideos().length) {
						String[] videoUrls = new String[content.getVideos().length];
						for (int i = 0; i < content.getVideos().length; i++) {
								videoUrls[i] = currentPath + "?objectId=" + content.getVideos()[i];
						}
						contentPO.setVideoUrls(videoUrls);
					}
					if (null != content.getPodcasts() && 0 < content.getPodcasts().length) {
						String[] podcastUrls = new String[content.getPodcasts().length];
						for (int i = 0; i < content.getPodcasts().length; i++) {
								podcastUrls[i] = currentPath + "?objectId=" + content.getPodcasts()[i];
						}
						contentPO.setPodcastUrls(podcastUrls);
					}
					if(null != content.getAvgCommentRating()) {
						BigDecimal bg = new BigDecimal(content.getAvgCommentRating());
						double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
						contentPO.setAvgCommentRating(f1);
					}
					contentPO.setContent(content.getContent().replaceAll(placeHolder, currentPath));
					contentPOs.add(contentPO);
				}
			}
			
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", contentPOs);
			params.put("totalFound", totalFound);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}

		return baseResult;
	}

	@Override
	public BaseResult retrievalContentsByValue(QueryPO queryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query queryContent = new Query();
			
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			List<ContentPO> contentPOs = new ArrayList<ContentPO>();
			Criteria criteriaContent = new Criteria();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("name").regex(queryPO.getSearchValue().trim(),"i"));
			List<Category> categories = mongoTemplate.find(query, Category.class);
			if (null != categories && 0 < categories.size()) {

				for (int i = 0; i < categories.size(); i++) {
					criteriaList.add(Criteria.where("categoryId").is(categories.get(i).getId()));
				}
			}
			List<ContentType> contentTypes = mongoTemplate.find(query, ContentType.class);
			if (null != contentTypes && 0 < contentTypes.size()) {
				for (int i = 0; i < contentTypes.size(); i++) {
					criteriaList.add(Criteria.where("contentTypeId").is(contentTypes.get(i).getId()));
				}
			}
			
			Query queryAuthor = new Query();
			queryAuthor.addCriteria(Criteria.where("fullName").regex(queryPO.getSearchValue(),"i"));
			List<Author> authors = mongoTemplate.find(queryAuthor, Author.class);
			if (null != authors && 0 < authors.size()) {
				for (int i = 0; i < authors.size(); i++) {
					criteriaList.add(Criteria.where("authorId").is(authors.get(i).get_id()));
				}
			}
			criteriaList.add(Criteria.where("title").regex(queryPO.getSearchValue().trim(),"i"));
			criteriaList.add(Criteria.where("content").regex(queryPO.getSearchValue(),"i"));
			criteriaList.add(Criteria.where("visualEssaysSearchText").regex(queryPO.getSearchValue(),"i"));
			criteriaContent.orOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			queryContent.addCriteria(new Criteria()
									.andOperator(
											Criteria.where("publishOn").lte(new Date()),
											Criteria.where("publishEnd").gte(new Date()),
											Criteria.where("status").is(2),
											Criteria.where("unite").is(false),
											criteriaContent
											));
			long totalFound = mongoTemplate.count(queryContent, Content.class);

			queryContent.limit(queryPO.getLimit());
			queryContent.skip(queryPO.getSkip());
			List<Content> contents = new ArrayList<>();
			contents = mongoTemplate.find(queryContent, Content.class);
			
			for (Content content : contents) {
				ContentPO contentPO = new ContentPO();
				BeanUtils.copyProperties(content,contentPO);
				if (!ValidationUtil.isBlank(content.getCategoryId())) {
					List<Category> category = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getCategoryId())), Category.class);
					if (category.size() > 0) {
						contentPO.setCategoryName(category.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getContentTypeId())) {
					List<ContentType> contentType = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getContentTypeId())),
							ContentType.class);
					if (contentType.size() > 0) {
						contentPO.setContentTypeName(contentType.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getSponsorId())) {
					List<Sponsor> sponsor = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getSponsorId())), Sponsor.class);
					if (sponsor.size() > 0) {
						contentPO.setSponsorName(sponsor.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getAuthorId())) {
					List<Author> author = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getAuthorId())), Author.class);
					if (author.size() > 0) {
						contentPO.setAuthor(author.get(0));
						if (author.get(0).getObjectId() != null && !"".equals(author.get(0).getObjectId().trim())) {
							contentPO.setAuthorPhotoUrl(currentPath + "?objectId=" + author.get(0).getObjectId());
						}
					}
				}
				List<Bookmark> bookmarks = mongoTemplate
						.find(new Query().addCriteria(Criteria.where("postId").is(content.getId()).and("email").is(queryPO.getEmail())), Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}
				if (null != content.getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.getFeaturedMedia();
					if(featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
						photoUrlsPOs.add(photoUrlsPO);
					}
					contentPO.setPhotoUrls(photoUrlsPOs);
				}
				if (null != content.getVideos() && 0 < content.getVideos().length) {
					String[] videoUrls = new String[content.getVideos().length];
					for (int i = 0; i < content.getVideos().length; i++) {
							videoUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId=" + content.getVideos()[i];
					}
					contentPO.setVideoUrls(videoUrls);
				}
				if (null != content.getPodcasts() && 0 < content.getPodcasts().length) {
					String[] podcastUrls = new String[content.getPodcasts().length];
					for (int i = 0; i < content.getPodcasts().length; i++) {
							podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId=" + content.getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				if(null != content.getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				contentPO.setContent(content.getContent().replaceAll(placeHolder, currentPath));
				contentPOs.add(contentPO);
			}
			
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", contentPOs);
			params.put("totalFound", totalFound);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}

		return baseResult;
	}
	
	@Override
	public BaseResult retrievalContentsByValueAdmin(QueryPO queryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query queryContent = new Query();
			
			List<Criteria> orCriteriaList = new ArrayList<Criteria>();
			List<ContentPO> contentPOs = new ArrayList<ContentPO>();
			Criteria orCriteriaContent = new Criteria();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("name").regex(queryPO.getSearchValue(),"i"));
			List<Category> categories = mongoTemplate.find(query, Category.class);
			if (null != categories && 0 < categories.size()) {

				for (int i = 0; i < categories.size(); i++) {
					orCriteriaList.add(Criteria.where("categoryId").is(categories.get(i).getId()));
				}
			}
			List<ContentType> contentTypes = mongoTemplate.find(query, ContentType.class);
			if (null != contentTypes && 0 < contentTypes.size()) {
				for (int i = 0; i < contentTypes.size(); i++) {
					orCriteriaList.add(Criteria.where("contentTypeId").is(contentTypes.get(i).getId()));
				}
			}
			
			Query queryAuthor = new Query();
			queryAuthor.addCriteria(Criteria.where("fullName").regex(queryPO.getSearchValue(),"i"));
			List<Author> authors = mongoTemplate.find(queryAuthor, Author.class);
			if (null != authors && 0 < authors.size()) {
				for (int i = 0; i < authors.size(); i++) {
					orCriteriaList.add(Criteria.where("authorId").is(authors.get(i).get_id()));
				}
			}
			orCriteriaList.add(Criteria.where("title").regex(queryPO.getSearchValue(),"i"));
			orCriteriaList.add(Criteria.where("content").regex(queryPO.getSearchValue(),"i"));
			orCriteriaList.add(Criteria.where("visualEssaysSearchText").regex(queryPO.getSearchValue(),"i"));
			orCriteriaContent.orOperator(orCriteriaList.toArray(new Criteria[orCriteriaList.size()]));
			
			if (queryPO.getUnite() != null) {
				queryContent.addCriteria(new Criteria()
										.andOperator(
												Criteria.where("unite").is(queryPO.getUnite()),
												orCriteriaContent
												));
			} else {
				queryContent.addCriteria(orCriteriaContent);
			}
			
			long totalFound = mongoTemplate.count(queryContent, Content.class);

			queryContent.limit(queryPO.getLimit());
			queryContent.skip(queryPO.getSkip());
			List<Content> contents = new ArrayList<>();
			contents = mongoTemplate.find(queryContent, Content.class);
			
			for (Content content : contents) {
				ContentPO contentPO = new ContentPO();
				BeanUtils.copyProperties(content,contentPO);
				if (!ValidationUtil.isBlank(content.getCategoryId())) {
					List<Category> category = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getCategoryId())), Category.class);
					if (category.size() > 0) {
						contentPO.setCategoryName(category.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getContentTypeId())) {
					List<ContentType> contentType = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getContentTypeId())),
							ContentType.class);
					if (contentType.size() > 0) {
						contentPO.setContentTypeName(contentType.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getSponsorId())) {
					List<Sponsor> sponsor = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getSponsorId())), Sponsor.class);
					if (sponsor.size() > 0) {
						contentPO.setSponsorName(sponsor.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getAuthorId())) {
					List<Author> author = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getAuthorId())), Author.class);
					if (author.size() > 0) {
						contentPO.setAuthor(author.get(0));
						if (author.get(0).getObjectId() != null && !"".equals(author.get(0).getObjectId().trim())) {
							contentPO.setAuthorPhotoUrl(currentPath + "?objectId=" + author.get(0).getObjectId());
						}
					}
				}
				List<Bookmark> bookmarks = mongoTemplate
						.find(new Query().addCriteria(Criteria.where("postId").is(content.getId()).and("email").is(queryPO.getEmail())), Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}
				if (null != content.getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.getFeaturedMedia();
					if(featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
						photoUrlsPOs.add(photoUrlsPO);
					}
					contentPO.setPhotoUrls(photoUrlsPOs);
				}
				if (null != content.getVideos() && 0 < content.getVideos().length) {
					String[] videoUrls = new String[content.getVideos().length];
					for (int i = 0; i < content.getVideos().length; i++) {
							videoUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId=" + content.getVideos()[i];
					}
					contentPO.setVideoUrls(videoUrls);
				}
				if (null != content.getPodcasts() && 0 < content.getPodcasts().length) {
					String[] podcastUrls = new String[content.getPodcasts().length];
					for (int i = 0; i < content.getPodcasts().length; i++) {
							podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId=" + content.getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				if(null != content.getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				contentPO.setContent(content.getContent().replaceAll(placeHolder, currentPath));
				contentPOs.add(contentPO);
			}
			
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", contentPOs);
			params.put("totalFound", totalFound);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}

		return baseResult;
	}
}

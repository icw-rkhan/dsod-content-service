package com.thenextmediagroup.dsod.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.thenextmediagroup.dsod.dao.ContentReleaseDao;
import com.thenextmediagroup.dsod.dao.VisualEssayDao;
import com.thenextmediagroup.dsod.model.Author;
import com.thenextmediagroup.dsod.model.Bookmark;
import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.model.Comment;
import com.thenextmediagroup.dsod.model.Content;
import com.thenextmediagroup.dsod.model.ContentType;
import com.thenextmediagroup.dsod.model.FeaturedMedia;
import com.thenextmediagroup.dsod.model.FileEntity;
import com.thenextmediagroup.dsod.model.Photos;
import com.thenextmediagroup.dsod.model.Sponsor;
import com.thenextmediagroup.dsod.model.VisualEssay;
import com.thenextmediagroup.dsod.model.VisualEssayImage;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.PhotoUrlsPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@Service
public class ContentReleaseServiceImpl implements ContentReleaseService {

	private static Logger logger = Logger.getLogger(ContentReleaseServiceImpl.class);
	@Autowired
	private ContentReleaseDao contentReleaseDao;
	@Autowired
	private VisualEssayDao visualEssayDao;

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${spring.server.currentPath}")
	private String currentPath;

	@Value("${spring.server.placeholder}")
	private String placeHolder;

	/**
	 * release content
	 */
	@Override
	public BaseResult releaseContent(ContentPO contentPo) {
		// TODO Auto-generated method stub
		Content content = new Content();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(content, contentPo);
			//
			content.setReadNumber(0);
			if (content.getPublishDate() == null) {
				content.setPublishDate(new Date());
			}

			List<Photos> photos = contentPo.getPhotos();
			if (null != photos) {
				for (int i = 0; i < photos.size(); i++) {
					Query query = new Query();
					query.addCriteria(Criteria.where("_id").is(photos.get(i).getThumbnail()));
					FileEntity fileEntity = mongoTemplate.findOne(query, FileEntity.class);
					if (null != fileEntity) {
						photos.get(i).setOriginal(fileEntity.getParentId());
					}
				}
				content.setPhotos(photos);
			}

//			query = new Query();
//			query.addCriteria(Criteria.where("_id").is(visualEssay.getParentId()));
//			Content  content = mongoTemplate.findOne(query, Content.class);
//			if(null != content) {
//				content.setVisualEssayIds(visualEssays);
//				content.setVisualEssaysSearchText(visualEssaysSearchText);
//				mongoTemplate.save(content);
//			}

//			List<VisualEssay> visualEssays = contentPo.getVisualEssays();
//			if (null != visualEssays && 0 < visualEssays.size()) {
//				content.setVisualEssays(visualEssays);
//				String visualEssaysSearchText = "";
//				for (int i = 0; i < visualEssays.size(); i++) {
//					VisualEssay visualEssay = visualEssays.get(i);
//					visualEssaysSearchText += visualEssay.getAuthorDetails();
//					visualEssaysSearchText += visualEssay.getDescription();
//					visualEssaysSearchText += visualEssay.getTitle();
//
//					if (null != visualEssay.getVisualEssayImages() && 0 < visualEssay.getVisualEssayImages().size()) {
//						for (int j = 0; j < visualEssay.getVisualEssayImages().size(); j++) {
//							VisualEssayImage visualEssayImage = visualEssay.getVisualEssayImages().get(j);
//							visualEssaysSearchText += visualEssayImage.getAlternateText();
//							visualEssaysSearchText += visualEssayImage.getCaption();
//						}
//					}
//					mongoTemplate.save(visualEssay);
//				}
//				content.setVisualEssaysSearchText(visualEssaysSearchText);
//			}
			if (contentPo.getStatus() == 2) {
				content.setVersion(1);
			}
			if(null != content.getCategoryId()) {
				if (content.getCategoryId().contains(",")) {
					content.setCategoryId(content.getCategoryId().split(",")[0]);
				}
			}

			Content contentNew = contentReleaseDao.save(content);

			String[] visualEssays = contentPo.getVisualEssayIds();
			
			if (visualEssays != null && visualEssays.length > 0) {
				String visualEssaysSearchText = "";
				for (String veId : visualEssays) {
					Query queryVisualEssay = new Query();
					queryVisualEssay.addCriteria(Criteria.where("_id").is(veId));
					VisualEssay visualEssayList = mongoTemplate.findOne(queryVisualEssay, VisualEssay.class);

					if (visualEssayList != null) {
						visualEssaysSearchText += visualEssayList.getAuthorDetails();
						visualEssaysSearchText += visualEssayList.getDescription();
						visualEssaysSearchText += visualEssayList.getTitle();
						if (null != visualEssayList.getVisualEssayImages()
								&& 0 < visualEssayList.getVisualEssayImages().size()) {
							for (int j = 0; j < visualEssayList.getVisualEssayImages().size(); j++) {
								VisualEssayImage visualEssayImage = visualEssayList.getVisualEssayImages().get(j);
								visualEssaysSearchText += visualEssayImage.getAlternateText();
								visualEssaysSearchText += visualEssayImage.getCaption();
							}
						}
						visualEssayList.setParentId(contentNew.getId());
						mongoTemplate.save(visualEssayList);
					}
				}
				contentNew.setVisualEssayIds(contentPo.getVisualEssayIds());
				contentNew.setVisualEssaysSearchText(visualEssaysSearchText);
			}
			
			contentReleaseDao.save(content);
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", content.getId());
			baseResult.setResultMap(map);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}
		return baseResult;
	}

	@Override
	public BaseResult findAll(QueryPO querys) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			List<Criteria> criterias = new ArrayList<Criteria>();

			Date date = new Date();
			if (querys.getIsAdmin() != null) {
				// not admin
				if (!querys.getIsAdmin()) {

					criterias.add(Criteria.where("publishOn").lte(date));
					criterias.add(Criteria.where("publishEnd").gte(date));
					criterias.add(Criteria.where("status").is(2));
				} else {
					if (querys.getStatus() != 0) {
						criterias.add(Criteria.where("status").is(querys.getStatus()));
					}
					if (querys.getStartDate() != null) {
						criterias.add(Criteria.where("publishOn").gte(querys.getStartDate()));
					}
					if (querys.getEndDate() != null) {
						criterias.add(Criteria.where("publishOn").lte(querys.getEndDate()));
					}
				}
			}
			// not admin role
			else {
				criterias.add(Criteria.where("publishOn").lte(date));
				criterias.add(Criteria.where("publishEnd").gte(date));
				criterias.add(Criteria.where("status").is(2));
			}

			if (querys.getIsPrivate() != null) {
				if (querys.getIsPrivate()) {
					criterias.add(Criteria.where("isPrivate").is(querys.getIsPrivate()));
				} else {
					criterias.add(Criteria.where("isPrivate").is(false));
				}
			} // else {
				// criterias.add(Criteria.where("isPrivate").is(false));
				// }

			if (querys.getIsFeatured() != null) {
				// if (querys.getIsFeatured()) {
				criterias.add(Criteria.where("isFeatured").is(querys.getIsFeatured()));
				// }
			}

			if (querys.getUnite() != null) {
				if (querys.getUnite()) {
					criterias.add(Criteria.where("unite").is(querys.getUnite()));
				}
			}

			if (!ValidationUtil.isBlank(querys.getTitle())) {
				criterias.add(Criteria.where("title").regex(querys.getTitle(), "i"));
			}

//			if (!ValidationUtil.isBlank(querys.getEmail())) {
//				criterias.add(Criteria.where("email").is(querys.getEmail()));
//			}
			if (!ValidationUtil.isBlank(querys.getCategoryId())) {
				criterias.add(Criteria.where("categoryId").is(querys.getCategoryId()));
			}
			if (!ValidationUtil.isBlank(querys.getContentTypeId())) {
				criterias.add(Criteria.where("contentTypeId").is(querys.getContentTypeId()));
			}
			if (!ValidationUtil.isBlank(querys.getSponsorId())) {
				criterias.add(Criteria.where("sponsorId").is(querys.getSponsorId()));
			}
			if (!ValidationUtil.isBlank(querys.getAuthorId())) {
				criterias.add(Criteria.where("authorId").is(querys.getAuthorId()));
			}

			if (criterias.size() > 0) {
				Criteria criteriaContent = new Criteria();
				criteriaContent.andOperator(criterias.toArray(new Criteria[criterias.size()]));
				query.addCriteria(criteriaContent);
			}
			long toatlFound = mongoTemplate.count(query, Content.class);
			query.limit(querys.getLimit());
			query.skip(querys.getSkip());
			query.with(new Sort(Direction.DESC, "publishOn"));

			List<Content> l = mongoTemplate.find(query, Content.class);

			List<ContentPO> contentPOlist = new ArrayList<>();

			for (Content content : l) {
				ContentPO contentPO = new ContentPO();
				content.setVisualEssays(mongoTemplate.find(
						new Query().addCriteria(Criteria.where("parentId").is(content.getId())), VisualEssay.class));
				BeanUtils.copyProperties(contentPO, content);
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
							contentPO.setAuthorPhotoUrl(currentPath + "/file/downloadFileByObjectId?objectId="
									+ author.get(0).getObjectId());
						}
					}
				}
				List<Bookmark> bookmarks = mongoTemplate.find(
						new Query().addCriteria(
								Criteria.where("postId").is(content.getId()).and("email").is(querys.getEmail())),
						Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}
				if (null != content.getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.getFeaturedMedia();
					if (featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
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
						podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				if (null != content.getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				contentPO.setContent(content.getContent().replaceAll(placeHolder, currentPath));
				contentPOlist.add(contentPO);
			}

			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", contentPOlist);
			params.put("toatlFound", toatlFound);
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
	public BaseResult findOneById(String id, String email) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id));
			List<Content> content = mongoTemplate.find(query, Content.class);
			if (content.size() > 0) {
				ContentPO contentPO = new ContentPO();
				content.get(0)
						.setVisualEssays(mongoTemplate.find(
								new Query().addCriteria(Criteria.where("parentId").is(content.get(0).getId())),
								VisualEssay.class));
				BeanUtils.copyProperties(contentPO, content.get(0));

				if (!ValidationUtil.isBlank(content.get(0).getCategoryId())) {
					List<Category> category = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getCategoryId())),
							Category.class);
					if (category.size() > 0) {
						contentPO.setCategoryName(category.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getContentTypeId())) {
					List<ContentType> contentType = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getContentTypeId())),
							ContentType.class);
					if (contentType.size() > 0) {
						contentPO.setContentTypeName(contentType.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getSponsorId())) {
					List<Sponsor> sponsor = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getSponsorId())),
							Sponsor.class);
					if (sponsor.size() > 0) {
						contentPO.setSponsorName(sponsor.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getAuthorId())) {
					List<Author> author = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getAuthorId())),
							Author.class);
					if (author.size() > 0) {
						contentPO.setAuthor(author.get(0));
						if (author.get(0).getObjectId() != null && !"".equals(author.get(0).getObjectId().trim())) {
							contentPO.setAuthorPhotoUrl(currentPath + "/file/downloadFileByObjectId?objectId="
									+ author.get(0).getObjectId());
						}
					}
				}
				List<Bookmark> bookmarks = mongoTemplate.find(
						new Query().addCriteria(
								Criteria.where("postId").is(content.get(0).getId()).and("email").is(email)),
						Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}

				Query queryComment = new Query();
				queryComment.addCriteria(Criteria.where("content_id").is(id));
				queryComment.limit(3);
				queryComment.skip(0);
				queryComment.with(new Sort(Direction.DESC, "create_time"));
				List<Comment> comments = mongoTemplate.find(queryComment, Comment.class);
				contentPO.setComment(comments);

				if (null != content.get(0)) {
					Update updates = new Update();
					updates.set("readNumber", content.get(0).getReadNumber() + 1);
					mongoTemplate.upsert(query, updates, Content.class);
				}
				if (null != content.get(0).getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.get(0).getFeaturedMedia();
					if (featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.get(0).getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
						photoUrlsPOs.add(photoUrlsPO);
					}
					contentPO.setPhotoUrls(photoUrlsPOs);
				}
				if (null != content.get(0).getVideos() && 0 < content.get(0).getVideos().length) {
					String[] videoUrls = new String[content.get(0).getVideos().length];
					for (int i = 0; i < content.get(0).getVideos().length; i++) {
						videoUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.get(0).getVideos()[i];
					}
					contentPO.setVideoUrls(videoUrls);
				}
				if (null != content.get(0).getPodcasts() && 0 < content.get(0).getPodcasts().length) {
					String[] podcastUrls = new String[content.get(0).getPodcasts().length];
					for (int i = 0; i < content.get(0).getPodcasts().length; i++) {
						podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.get(0).getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				if (null != content.get(0).getVisualEssays() && 0 < content.get(0).getVisualEssays().size()) {
					List<VisualEssay> visualEssays = content.get(0).getVisualEssays();
					String urlPrefix = currentPath + "/file/downloadFileByObjectId?objectId=";
					for (int i = 0; i < visualEssays.size(); i++) {
						VisualEssay visualEssay = visualEssays.get(i);
						VisualEssayImage visualEssayAuthorImage = visualEssay.getAuthorImage();
						if (visualEssayAuthorImage != null) {
							visualEssayAuthorImage.setOriginalUrl(urlPrefix + visualEssayAuthorImage.getOriginalID());
							visualEssayAuthorImage.setThumbnailUrl(urlPrefix + visualEssayAuthorImage.getThumbnailID());
						}
						if (null != visualEssay.getVisualEssayImages()
								&& 0 < visualEssay.getVisualEssayImages().size()) {
							for (int j = 0; j < visualEssay.getVisualEssayImages().size(); j++) {
								VisualEssayImage visualEssayImage = visualEssay.getVisualEssayImages().get(j);
								visualEssayImage.setOriginalUrl(urlPrefix + visualEssayImage.getOriginalID());
								visualEssayImage.setThumbnailUrl(urlPrefix + visualEssayImage.getThumbnailID());

							}
						}
					}
					contentPO.setVisualEssays(visualEssays);
				}
				contentPO.setContent(content.get(0).getContent().replaceAll(placeHolder, currentPath));
				String[] relativeTopics = content.get(0).getRelativeTopics();
				if (null != relativeTopics) {
					Query relativeTopicQuery = new Query();
					relativeTopicQuery.addCriteria(Criteria.where("_id").in(relativeTopics));
					relativeTopicQuery.fields().include("_id");
					relativeTopicQuery.fields().include("title");
					List<Content> contents = mongoTemplate.find(relativeTopicQuery, Content.class);
					for (Content c : contents) {
						final Map<String, String> map = new HashMap<String, String>();
						map.put("id", c.getId());
						map.put("title", c.getTitle());
						contentPO.getRelativeTopicList().add(map);
					}
				}
				if (null != content.get(0).getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.get(0).getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("data", contentPO);
				baseResult.setResultMap(params);
			}

//			String avgComment = comments.stream().mapToDouble(Comment::getComment_rating).average().toString();
//			contentPo.setAvgCommentRating(avgComment);

			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}

		return baseResult;
	}

	@Override
	public BaseResult findOneByIdAndSearchValue(String id, String searchValue, String email) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			query.addCriteria(new Criteria().andOperator(Criteria.where("_id").is(id),
					new Criteria().orOperator(Criteria.where("title").regex(searchValue, "i"),
							Criteria.where("content").regex(searchValue, "i"))));
			List<Content> content = mongoTemplate.find(query, Content.class);
//			Criteria c1 = Criteria.where("_id").is(id);
//			Criteria c2 = Criteria.where("title").regex(searchValue, "i");
//			Criteria c3 = Criteria.where("content").regex(searchValue, "i");
//			List<Content> content = new ArrayList<>();
//			List<Content> contentByTitle = mongoTemplate.find(new Query().addCriteria(new Criteria().andOperator(c1,c2)), Content.class);
//			List<Content> contentByContent = mongoTemplate.find(new Query().addCriteria(new Criteria().andOperator(c1,c3)), Content.class);
//			content.addAll(contentByTitle);
//			content.addAll(contentByContent);
			if (content.size() > 0) {
				ContentPO contentPO = new ContentPO();
				content.get(0)
						.setVisualEssays(mongoTemplate.find(
								new Query().addCriteria(Criteria.where("parentId").is(content.get(0).getId())),
								VisualEssay.class));
				BeanUtils.copyProperties(contentPO, content.get(0));

				if (!ValidationUtil.isBlank(content.get(0).getCategoryId())) {
					List<Category> category = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getCategoryId())),
							Category.class);
					if (category.size() > 0) {
						contentPO.setCategoryName(category.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getContentTypeId())) {
					List<ContentType> contentType = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getContentTypeId())),
							ContentType.class);
					if (contentType.size() > 0) {
						contentPO.setContentTypeName(contentType.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getSponsorId())) {
					List<Sponsor> sponsor = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getSponsorId())),
							Sponsor.class);
					if (sponsor.size() > 0) {
						contentPO.setSponsorName(sponsor.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.get(0).getAuthorId())) {
					List<Author> author = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.get(0).getAuthorId())),
							Author.class);
					if (author.size() > 0) {
						contentPO.setAuthor(author.get(0));
						if (author.get(0).getObjectId() != null && !"".equals(author.get(0).getObjectId().trim())) {
							contentPO.setAuthorPhotoUrl(currentPath + "/file/downloadFileByObjectId?objectId="
									+ author.get(0).getObjectId());
						}
					}
				}
				List<Bookmark> bookmarks = mongoTemplate.find(
						new Query().addCriteria(
								Criteria.where("postId").is(content.get(0).getId()).and("email").is(email)),
						Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}

				Query queryComment = new Query();
				queryComment.addCriteria(Criteria.where("content_id").is(id));
				queryComment.limit(3);
				queryComment.skip(0);
				queryComment.with(new Sort(Direction.DESC, "create_time"));
				List<Comment> comments = mongoTemplate.find(queryComment, Comment.class);
				contentPO.setComment(comments);

				if (null != content.get(0)) {
					Update updates = new Update();
					updates.set("readNumber", content.get(0).getReadNumber() + 1);
					mongoTemplate.upsert(query, updates, Content.class);
				}
				if (null != content.get(0).getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.get(0).getFeaturedMedia();
					if (featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.get(0).getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
						photoUrlsPOs.add(photoUrlsPO);
					}
					contentPO.setPhotoUrls(photoUrlsPOs);
				}
				if (null != content.get(0).getVideos() && 0 < content.get(0).getVideos().length) {
					String[] videoUrls = new String[content.get(0).getVideos().length];
					for (int i = 0; i < content.get(0).getVideos().length; i++) {
						videoUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.get(0).getVideos()[i];
					}
					contentPO.setVideoUrls(videoUrls);
				}
				if (null != content.get(0).getPodcasts() && 0 < content.get(0).getPodcasts().length) {
					String[] podcastUrls = new String[content.get(0).getPodcasts().length];
					for (int i = 0; i < content.get(0).getPodcasts().length; i++) {
						podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.get(0).getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				if (null != content.get(0).getVisualEssays() && 0 < content.get(0).getVisualEssays().size()) {
					List<VisualEssay> visualEssays = content.get(0).getVisualEssays();
					List<VisualEssay> visualEssaysNew = new ArrayList<VisualEssay>();
					String urlPrefix = currentPath + "/file/downloadFileByObjectId?objectId=";
					for (int i = 0; i < visualEssays.size(); i++) {
						VisualEssay visualEssay = visualEssays.get(i);
						VisualEssayImage visualEssayAuthorImage = visualEssay.getAuthorImage();
						visualEssayAuthorImage.setOriginalUrl(urlPrefix + visualEssayAuthorImage.getOriginalID());
						visualEssayAuthorImage.setThumbnailUrl(urlPrefix + visualEssayAuthorImage.getThumbnailID());
						if (null != visualEssay.getVisualEssayImages()
								&& 0 < visualEssay.getVisualEssayImages().size()) {
							for (int j = 0; j < visualEssay.getVisualEssayImages().size(); j++) {
								VisualEssayImage visualEssayImage = visualEssay.getVisualEssayImages().get(j);
								visualEssayImage.setOriginalUrl(urlPrefix + visualEssayImage.getOriginalID());
								visualEssayImage.setThumbnailUrl(urlPrefix + visualEssayImage.getThumbnailID());

							}
						}
						// visualEssaysNew.add(visualEssay);
					}
					contentPO.setVisualEssays(visualEssays);
				}
				contentPO.setContent(content.get(0).getContent().replaceAll(placeHolder, currentPath));
				String[] relativeTopics = content.get(0).getRelativeTopics();
				if (null != relativeTopics) {
					Query relativeTopicQuery = new Query();
					relativeTopicQuery.addCriteria(Criteria.where("_id").in(relativeTopics));
					relativeTopicQuery.fields().include("_id");
					relativeTopicQuery.fields().include("title");
					List<Content> contents = mongoTemplate.find(relativeTopicQuery, Content.class);
					for (Content c : contents) {
						final Map<String, String> map = new HashMap<String, String>();
						map.put("id", c.getId());
						map.put("title", c.getTitle());
						contentPO.getRelativeTopicList().add(map);
					}
				}
				if (null != content.get(0).getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.get(0).getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("data", contentPO);
				baseResult.setResultMap(params);
			}

//			String avgComment = comments.stream().mapToDouble(Comment::getComment_rating).average().toString();
//			contentPo.setAvgCommentRating(avgComment);

			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}

		return baseResult;
	}

	@Override
	public BaseResult findAllSortReadNumber(QueryPO querys) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			List<Criteria> criterias = new ArrayList<Criteria>();
			Date date = new Date();
			if (querys.getIsAdmin() != null) {
				// not admin
				if (!querys.getIsAdmin()) {

					criterias.add(Criteria.where("publishOn").lte(date));
					criterias.add(Criteria.where("publishEnd").gte(date));
					criterias.add(Criteria.where("status").is(2));
				}
			}
			// not admin role
			else {
				criterias.add(Criteria.where("publishOn").lte(date));
				criterias.add(Criteria.where("publishEnd").gte(date));
				criterias.add(Criteria.where("status").is(2));
			}
			if (querys.getIsPrivate() != null) {
				if (querys.getIsPrivate()) {
					criterias.add(Criteria.where("isPrivate").is(querys.getIsPrivate()));
				} else {
					criterias.add(Criteria.where("isPrivate").is(false));
				}
			} else {
				criterias.add(Criteria.where("isPrivate").is(false));
			}

			if (querys.getIsFeatured() != null) {
				if (querys.getIsFeatured()) {
					criterias.add(Criteria.where("isFeatured").is(querys.getIsFeatured()));
				}
			}

			if (!ValidationUtil.isBlank(querys.getTitle())) {
				criterias.add(Criteria.where("title").regex(querys.getTitle(), "i"));
			}

//			if (!ValidationUtil.isBlank(querys.getEmail())) {
//				criterias.add(Criteria.where("email").is(querys.getEmail()));
//			}
			if (!ValidationUtil.isBlank(querys.getCategoryId())) {
				criterias.add(Criteria.where("categoryId").is(querys.getCategoryId()));
			}
			if (!ValidationUtil.isBlank(querys.getContentTypeId())) {
				criterias.add(Criteria.where("contentTypeId").is(querys.getContentTypeId()));
			}
			if (!ValidationUtil.isBlank(querys.getSponsorId())) {
				criterias.add(Criteria.where("sponsorId").is(querys.getSponsorId()));
			}
			if (!ValidationUtil.isBlank(querys.getAuthorId())) {
				criterias.add(Criteria.where("authorId").is(querys.getAuthorId()));
			}

			if (criterias.size() > 0) {
				Criteria criteriaContent = new Criteria();
				criteriaContent.andOperator(criterias.toArray(new Criteria[criterias.size()]));
				query.addCriteria(criteriaContent);
			}
			long totalFound = mongoTemplate.count(query, Content.class);
			query.limit(querys.getLimit());
			query.skip(querys.getSkip());
			query.with(new Sort(Direction.DESC, "readNumber"));

			List<Content> l = mongoTemplate.find(query, Content.class);

			List<ContentPO> contentPOlist = new ArrayList<>();

			for (Content content : l) {
				ContentPO contentPO = new ContentPO();
				content.setVisualEssays(mongoTemplate.find(
						new Query().addCriteria(Criteria.where("parentId").is(content.getId())), VisualEssay.class));
				BeanUtils.copyProperties(contentPO, content);
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
							contentPO.setAuthorPhotoUrl(currentPath + "/file/downloadFileByObjectId?objectId="
									+ author.get(0).getObjectId());
						}

					}
				}
				List<Bookmark> bookmarks = mongoTemplate.find(
						new Query().addCriteria(
								Criteria.where("postId").is(content.getId()).and("email").is(querys.getEmail())),
						Bookmark.class);
				if (null != bookmarks && 0 < bookmarks.size()) {
					contentPO.setIsBookmark(true);
				} else {
					contentPO.setIsBookmark(false);
				}
				if (null != content.getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.getFeaturedMedia();
					if (featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath
								+ "/file/downloadFileByObjectId?objectId=" + featuredMedia.getCode().get("original"));
					}
				}
				List<Photos> photos = content.getPhotos();
				if (null != photos && 0 < photos.size()) {
					List<PhotoUrlsPO> photoUrlsPOs = new ArrayList<PhotoUrlsPO>(photos.size());
					for (int i = 0; i < photos.size(); i++) {
						PhotoUrlsPO photoUrlsPO = new PhotoUrlsPO();
						photoUrlsPO.setOriginalUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getOriginal());
						photoUrlsPO.setThumbnailUrl(
								currentPath + "/file/downloadFileByObjectId?objectId=" + photos.get(i).getThumbnail());
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
						podcastUrls[i] = currentPath + "/file/downloadFileByObjectId?objectId="
								+ content.getPodcasts()[i];
					}
					contentPO.setPodcastUrls(podcastUrls);
				}
				contentPO.setContent(content.getContent().replaceAll(placeHolder, currentPath));
				if (null != content.getAvgCommentRating()) {
					BigDecimal bg = new BigDecimal(content.getAvgCommentRating());
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					contentPO.setAvgCommentRating(f1);
				}
				contentPOlist.add(contentPO);
			}
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", contentPOlist);
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
	public BaseResult updatePost(ContentPO contentPo) {
		// TODO Auto-generated method stub
		Content content = new Content();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(content, contentPo);

			Content contentone = mongoTemplate
					.findOne(new Query().addCriteria(Criteria.where("_id").is(contentPo.getId())), Content.class);

			if (contentone == null) {
				baseResult.setCode(StateCodeInformationUtil.CONTENT_IS_NULL);
				return baseResult;
			}
			content.setId(contentPo.getId());
			content.setVersion(contentone.getVersion() + 1);

//			List<VisualEssay> visualEssays = contentPo.getVisualEssays();
//			if (null != visualEssays && 0 < visualEssays.size()) {
//				content.setVisualEssays(visualEssays);
//				String visualEssaysSearchText = "";
//				for (int i = 0; i < visualEssays.size(); i++) {
//					VisualEssay visualEssay = visualEssays.get(i);
//					visualEssaysSearchText += visualEssay.getAuthorDetails();
//					visualEssaysSearchText += visualEssay.getDescription();
//					visualEssaysSearchText += visualEssay.getTitle();
//
//					if (null != visualEssay.getVisualEssayImages() && 0 < visualEssay.getVisualEssayImages().size()) {
//						for (int j = 0; j < visualEssay.getVisualEssayImages().size(); j++) {
//							VisualEssayImage visualEssayImage = visualEssay.getVisualEssayImages().get(j);
//							visualEssaysSearchText += visualEssayImage.getAlternateText();
//							visualEssaysSearchText += visualEssayImage.getCaption();
//						}
//					}
//				}
//				content.setVisualEssaysSearchText(visualEssaysSearchText);
//			}

			contentReleaseDao.save(content);
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("postId", content.getId());
			baseResult.setResultMap(map);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}
		return baseResult;
	}

	@Override
	public BaseResult updateContentRelativeTopicById(String id, String[] relativeTopicList) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update updates = new Update();
		updates.set("relativeTopics", relativeTopicList);
		updates.inc("version", 1);
		mongoTemplate.upsert(query, updates, Content.class);
		baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		return baseResult;
	}

	@Override
	public BaseResult deleteOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = null;
		query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult l = mongoTemplate.remove(query, Content.class);
		if (l.getDeletedCount() < 1) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			return baseResult;
		} else {
			query = new Query();
			query.addCriteria(Criteria.where("parentId").is(id));
			mongoTemplate.remove(query, VisualEssay.class);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			return baseResult;
		}
	}

	@Override
	public BaseResult saveVisualEssay(VisualEssay visualEssay) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
//		String visualEssaysSearchText = "";
//		Query query = null;
//		visualEssaysSearchText += visualEssay.getAuthorDetails();
//		visualEssaysSearchText += visualEssay.getDescription();
//		visualEssaysSearchText += visualEssay.getTitle();
//		if (null != visualEssay.getVisualEssayImages() && 0 < visualEssay.getVisualEssayImages().size()) {
//			for (int j = 0; j < visualEssay.getVisualEssayImages().size(); j++) {
//				VisualEssayImage visualEssayImage = visualEssay.getVisualEssayImages().get(j);
//				visualEssaysSearchText += visualEssayImage.getAlternateText();
//				visualEssaysSearchText += visualEssayImage.getCaption();
//			}
//		}
		mongoTemplate.save(visualEssay);
//		query = new Query();
//		query.addCriteria(Criteria.where("parentId").is(visualEssay.getParentId()));
//		List<VisualEssay> visualEssayList = mongoTemplate.find(query, VisualEssay.class);
//		String[] visualEssays = new String[visualEssayList.size()];
//		for (int i = 0; i < visualEssayList.size(); i++) {
//			visualEssays[i] = visualEssayList.get(i).getId();
//		}
//		query = new Query();
//		query.addCriteria(Criteria.where("_id").is(visualEssay.getParentId()));
//		Content content = mongoTemplate.findOne(query, Content.class);
//		if (null != content) {
//			content.setVisualEssayIds(visualEssays);
//			content.setVisualEssaysSearchText(visualEssaysSearchText);
//			mongoTemplate.save(content);
//		}
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", visualEssay.getId());
		baseResult.setResultMap(map);
		baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		return baseResult;
	}

	@Override
	public BaseResult findOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		final Map<String, Object> map = new HashMap<String, Object>();
		VisualEssay visualEssay = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id)), VisualEssay.class);
		if(null != visualEssay) {
			for (int i = 0; i < visualEssay.getVisualEssayImages().size(); i++) {
				visualEssay.getVisualEssayImages().get(i).setOriginalUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + visualEssay.getVisualEssayImages().get(i).getOriginalID());
				visualEssay.getVisualEssayImages().get(i).setThumbnailUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + visualEssay.getVisualEssayImages().get(i).getThumbnailID());;
			}
		}
		map.put("data",visualEssay);
		baseResult.setResultMap(map);
		baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		return baseResult;
	}

	@Override
	public BaseResult deleteVisualEssayById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = null;
		query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult l = mongoTemplate.remove(query, VisualEssay.class);
		if (l.getDeletedCount() < 1) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			return baseResult;
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			return baseResult;
		}
	}
}

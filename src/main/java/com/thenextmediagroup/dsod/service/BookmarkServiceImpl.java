package com.thenextmediagroup.dsod.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.thenextmediagroup.dsod.model.Bookmark;
import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.model.Content;
import com.thenextmediagroup.dsod.model.ContentType;
import com.thenextmediagroup.dsod.model.FeaturedMedia;
import com.thenextmediagroup.dsod.model.Magazine;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.BookmarkPO;
import com.thenextmediagroup.dsod.web.dto.MagazineBookmarkPO;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	private static Logger logger = Logger.getLogger(BookmarkServiceImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${spring.server.currentPath}")
	private String currentPath;

	@Override
	public BaseResult save(BookmarkPO bookmarkPO) {
		// TODO Auto-generated method stub
		Bookmark bookmark = new Bookmark();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(bookmark, bookmarkPO);
			bookmark.setCreate_time(new Date());
			Query query = new Query();
			query.addCriteria(
					Criteria.where("postId").is(bookmarkPO.getPostId()).and("email").is(bookmarkPO.getEmail()));
			List<Bookmark> bookmarkList = mongoTemplate.find(query, Bookmark.class);
			if (null != bookmarkList && 0 < bookmarkList.size()) {
				baseResult.setCode(StateCodeInformationUtil.THIS_POST_IS_ALREADY_BOOKMARKED);
				return baseResult;
			}
			mongoTemplate.save(bookmark);
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
	public BaseResult getALLByEmail(BookmarkPO bookmarkPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			List<Criteria> criterias = new ArrayList<Criteria>();
			criterias.add(Criteria.where("email").is(bookmarkPO.getEmail()));
			if (null != bookmarkPO.getCategoryId() && !"".equals(bookmarkPO.getCategoryId())) {
				criterias.add(Criteria.where("categoryId").is(bookmarkPO.getCategoryId()));
			}
			if (null != bookmarkPO.getContentTypeId() && !"".equals(bookmarkPO.getContentTypeId())) {
				criterias.add(Criteria.where("contentTypeId").is(bookmarkPO.getContentTypeId()));
			}
			criterias.add(Criteria.where("status").is(bookmarkPO.getStatus()));
			if (criterias.size() > 0) {
				Criteria criteriaContent = new Criteria();
				criteriaContent.andOperator(criterias.toArray(new Criteria[criterias.size()]));
				query.addCriteria(criteriaContent);
			}
			query.limit(bookmarkPO.getLimit());
			query.skip(bookmarkPO.getSkip());
			List<Bookmark> bookmarkList = mongoTemplate.find(query, Bookmark.class);

			List<BookmarkPO> bookpo;
			if (bookmarkPO.getStatus() == 1) {
				bookpo = getContentBookmark(bookmarkList);
			} else {
				bookpo = getUniteBookmark(bookmarkList);
			}

			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("bookmarkList", bookpo);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			baseResult.setResultMap(map);

		} catch (Exception e) {
			// TODO: handle exception
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	private List<BookmarkPO> getUniteBookmark(List<Bookmark> bookmarkList) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		List<BookmarkPO> bookpo = new ArrayList<BookmarkPO>();
		for (Bookmark bookmark : bookmarkList) {
			BookmarkPO bpo = new BookmarkPO();
			BeanUtils.copyProperties(bpo, bookmark);
			Magazine magazine = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(bookmark.getPostId())), Magazine.class);
			MagazineBookmarkPO magazineBookmarkPO = new MagazineBookmarkPO();
			magazineBookmarkPO.setIssue(magazine.getIssue());
			magazineBookmarkPO.setSerial(magazine.getSerial());
			magazineBookmarkPO.setVol(magazine.getVol());
			List<Content> contents = mongoTemplate.find(new Query().addCriteria(Criteria.where("_id").in(magazine.getArticles())), Content.class);
			for(Content content : contents) {
				if (null != content.getFeaturedMedia()) {
					FeaturedMedia featuredMedia = content.getFeaturedMedia();
					if(featuredMedia.getType().equals("1") && null != featuredMedia.getCode()) {
						featuredMedia.getCode().put("thumbnailUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("thumbnail"));
						featuredMedia.getCode().put("originalUrl", currentPath + "/file/downloadFileByObjectId?objectId=" +featuredMedia.getCode().get("original"));
					}
				}
			}
			magazineBookmarkPO.setContents(contents);
			bpo.setMagazineBookmarkPO(magazineBookmarkPO);
			bookpo.add(bpo);
		}
		return bookpo;
	}

	private List<BookmarkPO> getContentBookmark(List<Bookmark> bookmarkList)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		List<BookmarkPO> bookpo = new ArrayList<BookmarkPO>();
		for (Bookmark bookmark : bookmarkList) {
			BookmarkPO bpo = new BookmarkPO();
			Content content = mongoTemplate
					.findOne(new Query().addCriteria(Criteria.where("_id").is(bookmark.getPostId())), Content.class);
			BeanUtils.copyProperties(bpo, bookmark);
			if (content != null) {
				bpo.setExcerpt(content.getExcerpt());
				if (!ValidationUtil.isBlank(content.getCategoryId())) {
					List<Category> category = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getCategoryId())), Category.class);
					if (category.size() > 0) {
						bpo.setCategoryName(category.get(0).getName());
					}
				}

				if (!ValidationUtil.isBlank(content.getContentTypeId())) {
					List<ContentType> contentType = mongoTemplate.find(
							new Query().addCriteria(Criteria.where("_id").is(content.getContentTypeId())),
							ContentType.class);
					if (contentType.size() > 0) {
						bpo.setContentTypeName(contentType.get(0).getName());
					}
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
			}

			bookpo.add(bpo);
		}
		return bookpo;
	}

	@Override
	public BaseResult deleteOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult count = mongoTemplate.remove(query, Bookmark.class);
		if (count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}

	@Override
	public BaseResult deleteOneByEmailAndContentId(String email, String contentId) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("postId").is(contentId).and("email").is(email));
		DeleteResult count = mongoTemplate.remove(query, Bookmark.class);
		if (count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}
}

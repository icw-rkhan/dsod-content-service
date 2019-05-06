package com.thenextmediagroup.dsod.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thenextmediagroup.dsod.dao.CommentDao;
import com.thenextmediagroup.dsod.dao.ContentReleaseDao;
import com.thenextmediagroup.dsod.model.Comment;
import com.thenextmediagroup.dsod.model.Content;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.CommentPO;
import com.thenextmediagroup.dsod.web.dto.CommentQueryPO;

@Service
public class CommentServiceImpl implements CommentService {

	private static Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	@Autowired
	CommentDao commentDao;
	@Autowired
	ContentReleaseDao contentDao;
	@Autowired
	MongoTemplate mongoTemplate;

	@Transactional
	@Override
	public BaseResult saveComment(CommentPO commentPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();

		try {

			Optional<Content> content = contentDao.findById(commentPO.getContentId());
			if(!content.isPresent()) {
				baseResult.setCode(StateCodeInformationUtil.CONTENT_IS_NULL);
				return baseResult;
			}
			Comment comment = new Comment();
			comment.setEmail(commentPO.getEmail());
			comment.setContent_id(commentPO.getContentId());
			comment.setComment_text(commentPO.getCommentText());
			comment.setComment_rating(commentPO.getCommentRating());
			comment.setCreate_time(new Date());
			comment.setFullName(commentPO.getFullName());
			commentDao.save(comment);
			
			Query queryComment = new Query();
			queryComment.addCriteria(Criteria.where("content_id").is(commentPO.getContentId()));
			queryComment.with(new Sort(Direction.DESC, "create_time"));
			List<Comment> listcomment = mongoTemplate.find(queryComment, Comment.class);
			Double avgComment = listcomment.stream().mapToDouble(Comment::getComment_rating).average().getAsDouble();
			
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(commentPO.getContentId()));
			Update updates = new Update();
			updates.set("avgCommentRating", avgComment);
			updates.set("countOfComment", listcomment.size());
			
			mongoTemplate.upsert(query, updates, Content.class);
			
//			Update update = Update.update("avgCommentRating", avgComment);
//			mongoTemplate.upsert(query, updateCount, Content.class);
			
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			logger.error(e);
		}
		return baseResult;
	}

	@Override
	public BaseResult findAllCommentByContentId(CommentQueryPO commentQueryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("content_id").is(commentQueryPO.getContentId()));
			query.with(new Sort(Direction.DESC, "create_time"));
			query.limit(commentQueryPO.getLimit());
			query.skip(commentQueryPO.getSkip());
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", mongoTemplate.find(query, Comment.class));
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}
		return baseResult;
	}

}

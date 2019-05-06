package com.thenextmediagroup.dsod.service;

import java.util.List;

import com.thenextmediagroup.dsod.model.Comment;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.CommentPO;
import com.thenextmediagroup.dsod.web.dto.CommentQueryPO;

public interface CommentService {

	/**
	 * add comment
	 * @return
	 */
	BaseResult saveComment(CommentPO commentPO);
	
	/**
	 * find all comment by content id
	 * @param contentId
	 * @return
	 */
	BaseResult findAllCommentByContentId(CommentQueryPO commentQueryPO);
	
}

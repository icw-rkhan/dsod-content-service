package com.thenextmediagroup.dsod.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.CommentService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.CommentPO;
import com.thenextmediagroup.dsod.web.dto.CommentQueryPO;

@RestController
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/comment/addComment")
	@ResponseBody
	public BaseResult addComment(@RequestBody CommentPO comment) {
		BaseResult baseResult = new BaseResult();
		if(null == comment.getEmail() || "".equals(comment.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if (!ValidationUtil.isEmail(comment.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID);
			return baseResult;
		}
		if(null == comment.getContentId() || "".equals(comment.getContentId())) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_ID_IS_NULL);
			return baseResult;
		}
		baseResult=commentService.saveComment(comment);
		return baseResult;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/v1/comment/findAllByContent")
	@ResponseBody
	public BaseResult findAllByContent(@RequestBody CommentQueryPO commentQueryPO) {
		BaseResult baseResult = new BaseResult();
		if (null == commentQueryPO) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == commentQueryPO.getContentId() || "".equals(commentQueryPO.getContentId())) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_ID_IS_NULL);
			return baseResult;
		}
		baseResult=commentService.findAllCommentByContentId(commentQueryPO);
		return baseResult;
	}
	
}

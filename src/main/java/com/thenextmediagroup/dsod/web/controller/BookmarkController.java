package com.thenextmediagroup.dsod.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.BookmarkService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.BookmarkPO;

@RestController
public class BookmarkController {

	@Autowired
	private BookmarkService bookmarkService;

	private static Logger logger = Logger.getLogger(BookmarkController.class);
	
	/**
	 * save bookmark
	 * @param bookmarkPO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/bookmark/save")
	@ResponseBody
	public BaseResult save(@RequestBody BookmarkPO bookmarkPO) {
		logger.info("BookmarkController class save function:" + bookmarkPO);
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(bookmarkPO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if (!ValidationUtil.isEmail(bookmarkPO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID);
			return baseResult;
		}
		if (ValidationUtil.isBlank(bookmarkPO.getTitle())) {
			baseResult.setCode(StateCodeInformationUtil.TITLE_IS_NULL);
			return baseResult;
		}
		if (150 < bookmarkPO.getTitle().length()) {
			baseResult.setCode(StateCodeInformationUtil.OVERLENGTH_TITLE);
			return baseResult;
		}
		if (null == bookmarkPO.getPostId()) {
			baseResult.setCode(StateCodeInformationUtil.POST_ID_IS_NULL);
			return baseResult;
		}
		if(null == bookmarkPO.getCategoryId() || "".equals(bookmarkPO.getCategoryId().trim())) {
			baseResult.setCode(StateCodeInformationUtil.CATEGORY_ID_IS_NULL);
			return baseResult;
		}
		if(null == bookmarkPO.getContentTypeId() || "".equals(bookmarkPO.getContentTypeId().trim())) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_TYPE_ID_IS_NULL);
			return baseResult;
		}
		if(1 != bookmarkPO.getStatus() && 2 != bookmarkPO.getStatus()) {
			baseResult.setCode(StateCodeInformationUtil.STATUS_IS_WRONG);
			return baseResult;
		}

		return bookmarkService.save(bookmarkPO);
	}
	
	/**
	 * get all bookmark by email
	 * @param email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/bookmark/findAllByEmail")
	@ResponseBody
	public BaseResult getALLByEmail(@RequestBody BookmarkPO bookmarkPO) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(bookmarkPO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if (!ValidationUtil.isEmail(bookmarkPO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID);
			return baseResult;
		}
		if(1 != bookmarkPO.getStatus() && 2 != bookmarkPO.getStatus()) {
			baseResult.setCode(StateCodeInformationUtil.STATUS_IS_WRONG);
			return baseResult;
		}
		baseResult = bookmarkService.getALLByEmail(bookmarkPO);
		return baseResult;
	}
	
	/**
	 * delete boomark by id
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/bookmark/deleteOneById")
	@ResponseBody
	public BaseResult deleteOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = bookmarkService.deleteOneById(id);
		return baseResult;
	}
	/**
	 * delete boomark by email and contentId
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/bookmark/deleteOneByEmailAndContentId")
	@ResponseBody
	public BaseResult deleteOneByEmailAndContentId(@RequestParam String email, @RequestParam String contentId) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(email)) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if (ValidationUtil.isBlank(contentId)) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_ID_IS_NULL);
			return baseResult;
		}
		baseResult = bookmarkService.deleteOneByEmailAndContentId(email, contentId);
		return baseResult;
	}
}

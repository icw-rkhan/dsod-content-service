package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.BookmarkPO;

public interface BookmarkService {

	/**
	 * save bookmark
	 * 
	 * @param bookmarkPO
	 * @return
	 */
	BaseResult save(BookmarkPO bookmarkPO);

	/**
	 * get all bookmark by email
	 * 
	 * @param bookmarkPO
	 * @return
	 */
	BaseResult getALLByEmail(BookmarkPO bookmarkPO);
	/**
	 * delete One By Id
	 * @param id
	 * @return
	 */
	BaseResult deleteOneById(String id);
	/**
	 * delete boomark by email and contentId
	 * @param email
	 * @param contentId
	 * @return
	 */
	BaseResult deleteOneByEmailAndContentId(String email, String contentId);

}

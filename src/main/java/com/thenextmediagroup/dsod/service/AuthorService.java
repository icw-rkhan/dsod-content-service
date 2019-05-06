package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.AuthorQueryPO;

public interface AuthorService {

	/**
	 * save authorPO
	 * 
	 * @param authorPO
	 * @return
	 */
	BaseResult save(AuthorPO authorPO);

	/**
	 * get all author
	 * @param authorQueryPO 
	 * 
	 * @param email
	 * @return
	 */
	BaseResult getAllAuthor(AuthorQueryPO authorQueryPO);
	
	/**
	 * delete One By Id
	 * @param id
	 * @return
	 */
	BaseResult deleteOneById(String id);
	
	/**
	 * find One By Id
	 * @param id
	 * @return
	 */
	BaseResult findOneById(String id);

	/**
	 * edit One By Id
	 * @param authorPO
	 * @return
	 */
	BaseResult editOneById(AuthorPO authorPO);

}

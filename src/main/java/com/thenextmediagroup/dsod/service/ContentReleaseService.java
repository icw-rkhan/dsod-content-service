package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.model.VisualEssay;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

public interface ContentReleaseService {
	/**
	 * release content
	 * @param content
	 * @return
	 */
	BaseResult releaseContent(ContentPO content);
	
	/**
	 * find all content by email
	 * @param query
	 * @return
	 */
	BaseResult findAll(QueryPO query);
	
	/**
	 * find all content sort readnumber
	 * @param query
	 * @return
	 */
	BaseResult findAllSortReadNumber(QueryPO query);

	/**
	 * find one by id
	 * @param id
	 * @return
	 */
	BaseResult findOneById(String id, String email);
	
	/**
	 * update post
	 * @param content
	 * @return
	 */
	BaseResult updatePost(ContentPO content);
	
	/**
	 * update Content RelativeTopic By Id
	 * @param id
	 * @param relativeTopicList
	 * @return
	 */
	BaseResult updateContentRelativeTopicById(String id, String[] relativeTopicList);
	
	/**
	 * find One By Id And SearchValue
	 * @param id
	 * @param searchValue
	 * @param email
	 * @return
	 */
	BaseResult findOneByIdAndSearchValue(String id, String searchValue,String email);
	
	/**
	 * delete One By Id
	 * @param id
	 * @return
	 */
	BaseResult deleteOneById(String id);

	/**
	 * saveVisualEssay
	 * @param visualEssay
	 * @return
	 */
	BaseResult saveVisualEssay(VisualEssay visualEssay);

	/**
	 * findOneById（VisualEssay）
	 * @param id
	 * @return
	 */
	BaseResult findOneById(String id);

	/**
	 * deleteVisualEssayById
	 * @param id
	 * @return
	 */
	BaseResult deleteVisualEssayById(String id);

}

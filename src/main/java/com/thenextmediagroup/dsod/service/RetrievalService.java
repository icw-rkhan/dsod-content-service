package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

public interface RetrievalService {
	
	/**
	 * retrieval contents by type
	 * @param searchValue
	 * @return
	 */
	BaseResult retrievalContentsByType(QueryPO queryPO);
	
	/**
	 * retrieval contents by value
	 * @param searchValue
	 * @return
	 */
	BaseResult retrievalContentsByValue(QueryPO queryPO);
	
	BaseResult retrievalContentsByValueAdmin(QueryPO queryPO);

}

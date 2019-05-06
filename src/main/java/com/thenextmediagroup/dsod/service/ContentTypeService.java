package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.ContentTypePO;

public interface ContentTypeService {

	BaseResult findAllContentType();
	
	BaseResult save(ContentTypePO contentTypePO);
	
	BaseResult deleteOneById(String id);
}

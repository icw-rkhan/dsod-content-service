package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.CategoryPO;

public interface CategoryService {

	BaseResult findAllCategory();

	BaseResult save(CategoryPO categoryPO);

	BaseResult deleteOneById(String id);

	BaseResult findAllCatogoryBySponsor(String sponsorId);
}

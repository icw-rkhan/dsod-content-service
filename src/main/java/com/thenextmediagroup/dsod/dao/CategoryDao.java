package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.Category;

public interface CategoryDao extends MongoRepository<Category, String> {

	
	
}

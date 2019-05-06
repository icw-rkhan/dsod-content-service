package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.VisualEssay;

public interface VisualEssayDao extends MongoRepository<VisualEssay, String> {

	
}

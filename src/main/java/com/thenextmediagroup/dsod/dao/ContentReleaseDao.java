package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.Content;

public interface ContentReleaseDao extends MongoRepository<Content, String> {

	
}

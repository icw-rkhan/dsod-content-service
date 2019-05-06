package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.ContentType;

public interface ContentTypeDao extends MongoRepository<ContentType, String> {

}

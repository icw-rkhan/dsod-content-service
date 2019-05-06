package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.Magazine;

public interface MagazineDao extends MongoRepository<Magazine, String> {

}

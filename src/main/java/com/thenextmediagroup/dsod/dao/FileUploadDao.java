package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.FileEntity;

public interface FileUploadDao extends MongoRepository<FileEntity, String> {

}

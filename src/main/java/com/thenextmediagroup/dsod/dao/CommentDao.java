package com.thenextmediagroup.dsod.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thenextmediagroup.dsod.model.Comment;

public interface CommentDao extends MongoRepository<Comment, String> {

}

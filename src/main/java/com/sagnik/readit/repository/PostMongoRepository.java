package com.sagnik.readit.repository;

import com.sagnik.readit.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostMongoRepository extends MongoRepository<Post, String> {

}

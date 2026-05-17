package com.sagnik.readit.repository;

import com.sagnik.readit.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostMongoRepository extends MongoRepository<Post, String> {
    List<Post> findByUser_Id(String userId);
}

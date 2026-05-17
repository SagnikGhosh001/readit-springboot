package com.sagnik.readit.repository;

import com.sagnik.readit.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostMongoRepository extends MongoRepository<Post, String> {
    List<Post> findByUser_Id(String userId);

    @Query("""
                {
                  $or: [
                    { "user.id": ?0 },
                    { "user.id": { $in: ?1 } }
                  ]
                }
            """)
    List<Post> findFeed(String userId, List<String> subscribedUserIds);
}

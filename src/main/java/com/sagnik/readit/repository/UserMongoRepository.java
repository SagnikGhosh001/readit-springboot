package com.sagnik.readit.repository;

import com.sagnik.readit.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameStartingWithIgnoreCase(String prefix);
}

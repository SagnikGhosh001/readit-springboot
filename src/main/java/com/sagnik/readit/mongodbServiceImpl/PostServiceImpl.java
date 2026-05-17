package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.dto.PostDto;
import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.service.PostService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostMongoRepository postMongoRepository;
    private final UserMongoRepository userMongoRepository;

    public PostServiceImpl(PostMongoRepository postMongoRepository, UserMongoRepository userMongoRepository) {
        this.postMongoRepository = postMongoRepository;
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public Post createPost(PostDto postDto) {
        Optional<User> user = userMongoRepository.findById(postDto.userId());
        Post post = new Post(postDto.title(), postDto.body(), user.get());
        postMongoRepository.insert(post);
        return post;
    }
}

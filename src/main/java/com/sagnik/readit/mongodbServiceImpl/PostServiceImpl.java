package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostMongoRepository postMongoRepository;
    private final UserMongoRepository userMongoRepository;

    public PostServiceImpl(PostMongoRepository postMongoRepository, UserMongoRepository userMongoRepository) {
        this.postMongoRepository = postMongoRepository;
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        User user = userMongoRepository.findById(postRequestDto.userId())
                .orElseThrow(() -> new NotFoundException(String.format("User is not found with id %s", postRequestDto.userId())));

        Post post = new Post(postRequestDto.title(), postRequestDto.body(), user);
        postMongoRepository.insert(post);
        return post.toResponse();
    }

    @Override
    public PostResponseDto toggleLike(String postId, String userId) {
        Post post = postMongoRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Post is not found with id %s", postId)));
        User user = userMongoRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User is not found with id %s", userId)));
        Post updatedPost = post.toggleLike(user);
        Post save = postMongoRepository.save(updatedPost);
        return save.toResponse();
    }

    @Override
    public List<PostResponseDto> getUserUploadedPost(String userId) {
        userMongoRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User is not found with id %s", userId)));

        return postMongoRepository.findByUser_Id(userId)
                .stream().map(Post::toResponse).toList();
    }
}

package com.sagnik.readit.controller;

import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/")
    public ResponseEntity<PostResponseDto> post(@RequestBody PostRequestDto postRequestDto) {
        logger.info("Creating post with request {}", postRequestDto);
        PostResponseDto post = postService.createPost(postRequestDto);
        logger.info("Successfully created post with Id {}, Created By {}", post.id(), post.user().id());
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/like/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> toggleLike(@PathVariable String postId, @PathVariable String userId) {
        logger.info("Toggling like for post Id {} by user Id {}", postId, userId);
        PostResponseDto post = postService.toggleLike(postId, userId);
        logger.info("Successfully toggled like for post with Id {} by user Id {}", post.id(), post.user().id());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostResponseDto>> getUserUploadedPost(@PathVariable String userId) {
        logger.info("Fetching uploaded posts for user Id {}", userId);
        List<PostResponseDto> posts = postService.getUserUploadedPost(userId);
        logger.info("Successfully fetched {} uploaded posts for user Id {}", posts.size(), userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/user-feed/{userId}")
    public ResponseEntity<List<PostResponseDto>> getUserFeed(@PathVariable String userId) {
        logger.info("Fetching user feed for user Id {}", userId);
        List<PostResponseDto> posts = postService.getUserFeed(userId);
        logger.info("Successfully fetched {} feed posts for user Id {}", posts.size(), userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable String postId, @PathVariable String userId) {
        logger.info("Deleting post with Id {} by user Id {}", postId, userId);
        PostResponseDto post = postService.deletePost(postId, userId);
        logger.info("Successfully deleted post with Id {} by user Id {}", post.id(), userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}

package com.sagnik.readit.controller;

import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/")
    public ResponseEntity<PostResponseDto> post(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto post = postService.createPost(postRequestDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/like/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> toggleLike(@PathVariable String postId, @PathVariable String userId) {
        PostResponseDto post = postService.toggleLike(postId, userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}

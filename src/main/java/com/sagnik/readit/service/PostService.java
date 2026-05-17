package com.sagnik.readit.service;

import com.sagnik.readit.dto.PostDto;
import com.sagnik.readit.entity.Post;

public interface PostService {
    Post createPost(PostDto postDto);
}

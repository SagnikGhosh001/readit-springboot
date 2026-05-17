package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.dto.PostDto;
import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    private PostMongoRepository postMongoRepository;
    private UserMongoRepository userMongoRepository;

    @BeforeEach
    void beforeEach() {
        postMongoRepository = mock(PostMongoRepository.class);
        userMongoRepository = mock(UserMongoRepository.class);
    }

    @Test
    void shouldCreateTestWithUser() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(new User("user1")));
        postService.createPost(new PostDto("title", "body", "1"));
        verify(postMongoRepository).insert(any(Post.class));
    }

    @Test
    void shouldThrowNotFoundExceptionIfUserDoesNotExist() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        assertThrows(NotFoundException.class, () -> postService.createPost(new PostDto("title", "body", "1")));
    }
}

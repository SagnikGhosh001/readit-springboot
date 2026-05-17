package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        User user = new User("user1");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        PostResponseDto post = postService.createPost(new PostRequestDto("title", "body", "1"));

        verify(postMongoRepository).insert(any(Post.class));
        assertEquals(user.toResponse(), post.user());
        assertEquals("title", post.title());
        assertEquals("body", post.body());
        assertEquals(0, post.likedBy().size());
    }


    @Test
    void shouldThrowNotFoundExceptionIfUserDoesNotExist() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        assertThrows(NotFoundException.class, () -> postService.createPost(new PostRequestDto("title", "body", "1")));
    }

    @Test
    void shouldLikeForNewUser() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        User user = new User("user1");
        Post post = new Post("title", "body", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(postMongoRepository.save(any(Post.class))).thenReturn(post);

        PostResponseDto postResponseDto = postService.toggleLike("1", "1");
        assertEquals(1, postResponseDto.likedBy().size());
    }

//    @Test
//    void shouldLikeForNewUsers() {
//        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
//        User user = new User("user1");
//        Post post = new Post("title", "body", user);
//        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));
//        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));
//        when(postMongoRepository.save(any(Post.class))).thenReturn(post);
//
//        PostResponseDto postResponseDto1 = postService.toggleLike("1", "1");
//        assertEquals(1, postResponseDto1.likedBy().size());
//        PostResponseDto postResponseDto2 = postService.toggleLike("1", "2");
//        assertEquals(2, postResponseDto2.likedBy().size());
//    }


    @Test
    void shouldUnLikeForExistingUser() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        User user = new User("user1");
        Post post = new Post("title", "body", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(postMongoRepository.save(any(Post.class))).thenReturn(post);

        PostResponseDto postResponseDto1 = postService.toggleLike("1", "1");
        assertEquals(1, postResponseDto1.likedBy().size());
        PostResponseDto postResponseDto2 = postService.toggleLike("1", "1");
        assertEquals(0, postResponseDto2.likedBy().size());
    }

    @Test
    void shouldThrowErrorForNonExistingUser() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        User user = new User("user1");
        Post post = new Post("title", "body", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));

        assertThrows(NotFoundException.class, () -> postService.toggleLike("1", "1"));
    }

    @Test
    void shouldThrowErrorForNonExistingPost() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        User user = new User("user1");
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        assertThrows(NotFoundException.class, () -> postService.toggleLike("1", "1"));
    }

    @Test
    void shouldGiveUserUploadedPosts() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(new User("user1")));
        when(postMongoRepository.findByUser_Id(any(String.class))).thenReturn(List.of(
                new Post("title1", "body", new User("user1")),
                new Post("title1", "body", new User("user1")),
                new Post("title1", "body", new User("user1"))
        ));

        List<PostResponseDto> posts = postService.getUserUploadedPost("user1");
        assertEquals(3, posts.size());
    }

    @Test
    void shouldGiveEmptyArrayIfNoPost() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(new User("user1")));
        when(postMongoRepository.findByUser_Id(any(String.class))).thenReturn(List.of());

        List<PostResponseDto> posts = postService.getUserUploadedPost("user1");
        assertEquals(0, posts.size());
    }


    @Test
    void shouldThrowErrorIfNoUserAvailable() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(postMongoRepository.findByUser_Id(any(String.class))).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> postService.getUserUploadedPost("user1"));
    }

    @Test
    void shouldGiveUserFeed() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(new User("user1")));
        when(postMongoRepository.findFeed(any(String.class), any(List.class))).thenReturn(List.of(
                new Post("title1", "body", new User("user1")),
                new Post("title1", "body", new User("user1")),
                new Post("title1", "body", new User("user1"))
        ));

        List<PostResponseDto> posts = postService.getUserFeed("user1");
        assertEquals(3, posts.size());
    }

    @Test
    void shouldGiveEmptyArrayIfNoPostInUserFeed() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(new User("user1")));
        when(postMongoRepository.findFeed(any(String.class), any(List.class))).thenReturn(List.of());

        List<PostResponseDto> posts = postService.getUserFeed("user1");
        assertEquals(0, posts.size());
    }


    @Test
    void shouldThrowErrorIfNoUserAvailableInUserFeed() {
        PostServiceImpl postService = new PostServiceImpl(postMongoRepository, userMongoRepository);
        when(postMongoRepository.findFeed(any(String.class), any(List.class))).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> postService.getUserUploadedPost("user1"));
    }
}

// package com.tea.web.community.post.application.service;

// import com.tea.web.common.CustomException;
// import com.tea.web.common.ErrorType;
// import
// com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
// import com.tea.web.community.post.application.dto.response.PostResponseDto;
// import com.tea.web.community.category.domain.model.Category;
// import com.tea.web.community.post.domain.model.Post;
// import com.tea.web.community.post.domain.repository.PostRepository;
// import com.tea.web.users.domain.model.User;
// import com.tea.web.users.domain.repository.UserRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// class PostServiceImplTest {

// @InjectMocks
// private PostServiceImpl postService;

// @Mock
// private PostRepository postRepository;

// @Mock
// private UserRepository userRepository;

// @Mock
// private UserDetails userDetails;

// private User testUser;
// private PostCreateRequestDto createRequestDto;

// @BeforeEach
// void setUp() {
// testUser = User.builder()
// .email("test@example.com")
// .username("testUser")
// .build();

// createRequestDto = PostCreateRequestDto.builder()
// .type("BOARD")
// .title("Test Title")
// .content("Test Content")
// .category(Category.BOARD)
// .thumbnailUrl("http://example.com/thumbnail.jpg")
// .img1l("http://example.com/img1.jpg")
// .img2l("http://example.com/img2.jpg")
// .img3l("http://example.com/img3.jpg")
// .build();
// }

// @Test
// @DisplayName("게시글 생성 성공")
// void createPost_Success() {
// // given
// when(userDetails.getUsername()).thenReturn("test@example.com");
// when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
// when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
// Post post = invocation.getArgument(0);
// return post;
// });

// // when
// postService.createPost(createRequestDto, userDetails);

// // then
// verify(postRepository).save(any(Post.class));
// // assertThat(response).isNotNull();
// // assertThat(response.getTitle()).isEqualTo(createRequestDto.getTitle());
// //
// assertThat(response.getContent()).isEqualTo(createRequestDto.getContent());
// // assertThat(response.getType()).isEqualTo(createRequestDto.getType());
// //
// assertThat(response.getCategory()).isEqualTo(createRequestDto.getCategory());
// //
// assertThat(response.getThumbnailUrl()).isEqualTo(createRequestDto.getThumbnailUrl());
// }

// @Test
// @DisplayName("존재하지 않는 사용자로 게시글 생성 시도 시 실패")
// void createPost_UserNotFound_Failure() {
// // given
// when(userDetails.getUsername()).thenReturn("nonexistent@example.com");
// when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

// // when & then
// assertThatThrownBy(() -> postService.createPost(createRequestDto,
// userDetails))
// .isInstanceOf(CustomException.class)
// .satisfies(exception -> {
// CustomException customException = (CustomException) exception;
// assertThat(customException.getErrorType()).isEqualTo(ErrorType.USER_NOT_FOUND);
// });
// }
// }
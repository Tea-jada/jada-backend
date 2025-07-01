package com.tea.web.community.comments.application.service;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.community.comments.application.dto.request.CommentRequestDto;
import com.tea.web.community.comments.application.dto.response.CommentResponseDto;
import com.tea.web.community.comments.domain.model.Comment;
import com.tea.web.community.comments.domain.repository.CommentRepository;
import com.tea.web.community.post.domain.model.Post;
import com.tea.web.community.post.domain.repository.PostRepository;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(CommentRequestDto requestDto, long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorType.POST_NOT_FOUND));

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        Comment newComment = new Comment(requestDto.getComment(), post, user);
        commentRepository.save(newComment);
    }

    public Page<CommentResponseDto> getComments(Long postId, int page) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorType.POST_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, 30, Sort.by("createdAt").descending());
        Page<Comment> comments = commentRepository.findByPost(post, pageable);
        return comments.map(CommentResponseDto::new);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
            UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        validateCommentAuthor(comment, userDetails);
        comment.update(requestDto.getComment());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto deleteComment(Long commentId, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        validateCommentAuthor(comment, userDetails);
        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }

    private void validateCommentAuthor(Comment comment, UserDetails userDetails) {
        if (!comment.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new CustomException(ErrorType.NOT_COMMENT_AUTHOR);
        }
    }
}
package com.tea.web.community.comments.application.dto.response;

import java.time.LocalDateTime;

import com.tea.web.community.comments.domain.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private Long postId;
    private String username;
    private String email;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.postId = comment.getPost().getId();
        this.username = comment.getUser().getUsername();
        this.email = comment.getUser().getEmail();
        this.updatedAt = comment.getUpdatedAt();
    }
}
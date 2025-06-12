package com.tea.web.community.comments.controller;

import com.tea.web.community.comments.application.dto.request.CommentRequestDto;
import com.tea.web.community.comments.application.dto.response.CommentResponseDto;
import com.tea.web.community.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.tea.web.common.ResponseStatus;
import com.tea.web.common.ResponseDataDto;
import com.tea.web.common.ResponseMessageDto;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     *
     * @param requestDto 댓글 생성 요청 데이터
     * @return 생성된 댓글 정보
     */
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<ResponseMessageDto> createComment(@RequestBody CommentRequestDto requestDto,
            @PathVariable long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.createComment(requestDto, postId, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.COMMENT_CREATE_SUCCESS));
    }

    /**
     * 댓글 조회
     *
     * @param postId 게시글 ID
     * @param page   페이지 번호 (0부터 시작)
     * @return 댓글 목록 (페이지당 30개)
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<ResponseDataDto<Page<CommentResponseDto>>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page) {
        Page<CommentResponseDto> responseDto = commentService.getComments(postId, page);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.COMMENT_READ_SUCCESS, responseDto));
    }

    /**
     * 댓글 수정
     *
     * @param commentId  수정할 댓글 ID
     * @param requestDto 수정할 댓글 내용
     * @return 수정된 댓글 정보
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDataDto<CommentResponseDto>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.COMMENT_UPDATE_SUCCESS, responseDto));
    }

    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글 ID
     * @return 삭제된 댓글 ID
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDataDto<CommentResponseDto>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        CommentResponseDto responseDto = commentService.deleteComment(commentId, userDetails);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.COMMENT_DELETE_SUCCESS, responseDto));
    }
}
package com.tea.web.community.post.domain.repository;

import com.tea.web.community.post.domain.model.FeaturedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeaturedPostRepository extends JpaRepository<FeaturedPost, Long> {

    // 현재 등록된 대표 게시글 1건 가져오기
    Optional<FeaturedPost> findTopByIsDeletedFalseOrderByCreatedAtDesc();
}
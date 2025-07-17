package com.tea.web.community.post.domain.repository;

import com.tea.web.community.post.domain.model.Category;
import com.tea.web.community.post.domain.model.Post;
import com.tea.web.community.post.domain.model.Section;
import com.tea.web.community.post.domain.model.SubSection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.user.username LIKE %:keyword%")
    Page<Post> searchByTitleOrUsername(@Param("keyword") String keyword, Pageable pageable);

    Page<Post> findByCategoryOrderByUpdatedAtDesc(Category category, Pageable pageable);

    Page<Post> findBySectionOrderByUpdatedAtDesc(Section section, Pageable pageable);

    Page<Post> findBySectionAndSubSectionOrderByUpdatedAtDesc(Section section, SubSection subSection,
            Pageable pageable);
}

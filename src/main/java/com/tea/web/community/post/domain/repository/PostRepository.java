package com.tea.web.community.post.domain.repository;

import com.tea.web.community.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

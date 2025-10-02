package com.tea.web.community.post.domain.repository;

import com.tea.web.community.category.domain.model.Category;
import com.tea.web.community.category.domain.model.SubCategory;
import com.tea.web.community.post.domain.model.Post;
import com.tea.web.users.domain.model.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
            "WHERE (p.title LIKE %:keyword% OR p.user.username LIKE %:keyword%) " +
            "AND p.isDeleted = false")
    Page<Post> searchByTitleOrUsername(@Param("keyword") String keyword, Pageable pageable);

    Page<Post> findByCategoryOrderByUpdatedAtDesc(Category category, Pageable pageable);

    // Page<Post> findBySectionOrderByUpdatedAtDesc(Section section, Pageable
    // pageable);

    // Page<Post> findBySectionAndSubSectionOrderByUpdatedAtDesc(Section section,
    // SubSection subSection,
    // Pageable pageable);

    // Page<Post> findByCategoryAndSubCategoryOrderByUpdatedAtDesc(Category
    // category, SubCategory subCategory,
    // Pageable pageable);

    Optional<Post> findByIdAndIsDeletedFalse(Long postId);

    Page<Post> findAllByIsDeletedFalse(Pageable pageable);

    // Page<Post> searchByTitleOrUsernameAndIsDeletedFalse(String keyword, Pageable
    // pageable);

    // Page<Post> findByCategoryOrderByUpdatedAtDescAndIsDeletedFalse(Category
    // category, Pageable pageable);

    // Page<Post>
    // findByCategoryAndSubCategoryAndIsDeletedFalseOrderByUpdatedAtDesc(Category
    // category,
    // SubCategory subCategory, Pageable pageable);

    // Page<Post> findByCategoryAndIsDeletedFalseOrderByUpdatedAtDesc(Category
    // category, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.category = :category " +
            "AND p.isDeleted = false " +
            "ORDER BY p.updatedAt DESC")
    Page<Post> findActivePostsByCategory(
            @Param("category") Category category,
            Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE p.category = :category " +
            "AND p.subCategory = :subCategory " +
            "AND p.isDeleted = false " +
            "ORDER BY p.updatedAt DESC")
    Page<Post> findActivePostsByCategoryAndSubCategory(
            @Param("category") Category category,
            @Param("subCategory") SubCategory subCategory,
            Pageable pageable);
}

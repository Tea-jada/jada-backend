package com.tea.web.users.domain.repository;

import com.tea.web.users.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

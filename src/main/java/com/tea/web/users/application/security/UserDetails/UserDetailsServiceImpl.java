package com.tea.web.users.application.security.userdetails;

import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Not Found " + email));

    if (user.getIsDeleted()) {
      // 삭제된 유저는 인증 불가
      throw new UsernameNotFoundException("이미 탈퇴한 계정입니다.");
    }

    return new UserDetailsImpl(user);
  }
}
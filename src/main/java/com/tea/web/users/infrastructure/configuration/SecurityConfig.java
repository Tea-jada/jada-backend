package com.tea.web.users.infrastructure.configuration;

import com.tea.web.users.application.security.jwt.JwtAuthenticationFilter;
import com.tea.web.users.application.security.jwt.JwtAuthorizationFilter;
import com.tea.web.users.application.security.jwt.JwtUtil;
import com.tea.web.users.application.security.userdetails.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 수동Bean 등록시 필요
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final String[] permitRequests = {
            "/api/v1/users/signup/admin",
            "/api/v1/users/signup",
            "/api/v1/users/login",

            // posts
            "/api/v1/posts/section",
            "/api/v1/posts/sub-section", // 섹션, 서브섹션 별 조회
            "/api/v1/posts/search", // 검색
            "/api/v1/posts/*", // 상세 조회

            // category
            "/api/v1/posts/category/**", // 카테고리 별 조회
            "/api/v1/subcategories/**", // 서브카테고리 조회
            "/api/categories/{categoryId}/subcategories", // 카테고리별 서브카테고리 조회
            "/api/v1/categories/**", // 카테고리 조회

            // comments
            "/api/v1/posts/*/comments" // 댓글 조회
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception { // 로그인 진행 및 JWT 생성
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() { // API에 전달되는 JWT 유효성 검증 및 인가 처리
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(permitRequests).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

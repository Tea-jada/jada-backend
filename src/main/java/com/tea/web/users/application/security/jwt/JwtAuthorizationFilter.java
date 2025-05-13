package com.tea.web.users.application.security.jwt;

import com.tea.web.users.application.security.UserDetails.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessValue = jwtUtil.getJwtFromHeader(req, JwtUtil.AUTHORIZATION_HEADER);
        String refreshValue = jwtUtil.getJwtFromHeader(req, JwtUtil.REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(refreshValue)) {
            if (!jwtUtil.validateRefreshToken(refreshValue)) {
                log.error("RefreshToken Error");
                return;
            }
        }
        else if (StringUtils.hasText(accessValue)) {
            if (!jwtUtil.validateToken(accessValue)) {
                log.error("AccessToken Error");
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(accessValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setCharacterEncoding("utf-8");
                res.getWriter().write("상태 : " + res.getStatus() + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
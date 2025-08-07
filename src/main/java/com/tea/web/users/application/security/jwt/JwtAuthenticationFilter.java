package com.tea.web.users.application.security.jwt;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.users.application.dto.request.LoginRequestDto;
import com.tea.web.users.application.security.userdetails.UserDetailsImpl;
import com.tea.web.users.domain.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDto.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(), loginRequestDto.getPassword(), null));

        } catch (StreamReadException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {
        Long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail(); // 로그인 시 ID = 유저의 이메일
        String nickname = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUsername(); // 유저의 이름
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(userId, username, nickname, role); // AccessToken
        String refreshToken = jwtUtil.createRefreshToken(username); // RefreshToken

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        // 로그인 성공 메시지 JSON 바디로 응답
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseMessageDto responseMessage = new ResponseMessageDto(ResponseStatus.LOGIN_SUCCESS);
        new ObjectMapper().writeValue(response.getWriter(), responseMessage);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        log.error("로그인 실패: " + failed.getMessage());
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", "UNAUTHORIZED");
        errorResponse.put("message", "로그인에 실패했습니다.");
        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}

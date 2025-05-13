package com.tea.web.users.application.security.jwt;

import com.tea.web.users.domain.model.Role;
import com.tea.web.users.infrastructure.dao.RedisDao;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RedisDao redisDao; // RefreshToken 저장을 위해 Redis 사용
    private final String issuer; // 토큰 생성 서비스 이름
    private final SecretKey secretKey;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 24시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3; // 3일

    public JwtUtil(
            @Value("${service.jwt.secret-key}") String secretKeyValue,
            @Value("${spring.application.name}") String issuer,
            RedisDao redisDao
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyValue));
        this.issuer = issuer;
        this.redisDao = redisDao;
    }

    // 토큰 생성
    public String createToken(Long userId, String username, String nickname, Role role) {
        return BEARER_PREFIX + Jwts.builder()
                .subject(username)
                .issuer(issuer)
                .claim("userId", userId)
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // refresh token 생성
    public String createRefreshToken(String username) {
        String refreshToken = BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // Redis에 RefreshToken 넣기
        // "REFRESH_TOKEN_EXPIRE_TIME"만큼 시간이 지나면 삭제됨
        redisDao.setValues(username, refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));

        return refreshToken;
    }

    // 헤더에서 JWT 토큰 가져오기
    public String getJwtFromHeader(HttpServletRequest request, String tokenHeader) {
        String bearerToken = request.getHeader(tokenHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    // RefreshToken 검증
    public boolean validateRefreshToken(String token) {
        // 기본적인 JWT 검증
        if (!validateToken(token)) return false;

        try {
            // token에서 username 추출하기
            String username = getUserNameFromToken(token);
            // Redis에 저장된 RefreshToken과 비교하기
            String redisToken = (String) redisDao.getValues(username);
            return token.equals(redisToken);
        } catch (Exception e) {
            log.info("RefreshToken Validation Failed", e);
            return false;
        }
    }

    // 토큰에서 username 추출
    public String getUserNameFromToken(String token) {
        try {
            // 토큰 파싱해서 클레임 얻기
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 사용자 이름(subject) 반환
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되어도 클레임 내용을 가져올 수 있음
            return e.getClaims().getSubject();
        }
    }
}
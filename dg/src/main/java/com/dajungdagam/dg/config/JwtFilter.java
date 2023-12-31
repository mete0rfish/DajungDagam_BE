package com.dajungdagam.dg.config;

import com.dajungdagam.dg.jwt.jwtTokenProvider;
import com.dajungdagam.dg.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

// JWT를 매번 검사
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;


    @Value("${jwt.secret}")
    private String secretKey;

    public JwtFilter(UserService userService, String secretKey) {
        this.userService = userService;
        this.secretKey = secretKey;
    }

    // 이게 문임 . 여기서 권한부여 가능함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // 토큰 업승면 return
        if(authorization == null || !authorization.startsWith("Bearer ")){

            log.error("authorization 이 잘못 보내짐");
            filterChain.doFilter(request, response);
            return;
        }

        // Token꺼내기 (Bearer 빼기)
        String token = authorization.split(" ")[1];

        // Token 의 Expired 여부 체크
        if(jwtTokenProvider.isExpired(token, secretKey)){
            log.error("Token 이 만료됨");
            filterChain.doFilter(request, response);
            return;
        }


        // Username Token에서 꺼내기
        String kakaoName = jwtTokenProvider.getUserName(token, secretKey);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(kakaoName, null, List.of(new SimpleGrantedAuthority("USER")));
        // Detail 넣어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

package com.example.springjpa.global.auth;

import com.example.springjpa.global.jwt.TokenProvider;
import com.example.springjpa.user.domain.User;
import com.example.springjpa.user.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class AuthFilter implements Filter {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                url.startsWith("/api/user")
        ) {
            log.info("인증 처리를 하지 않는 URL : " + url);
            chain.doFilter(request, response);
        } else {
            String tokenValue = tokenProvider.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) {
                String token = tokenProvider.substringToken(tokenValue);

                if (!tokenProvider.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                Claims info = tokenProvider.getUserInfoFromToken(token);

                User user = userRepository.findById(Long.valueOf(info.getSubject())).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response);
            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }
}

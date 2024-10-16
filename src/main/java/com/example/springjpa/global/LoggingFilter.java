package com.example.springjpa.global;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "LoggingFilter")
@Component
@Order(1)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;   // casting
        String url = httpServletRequest.getRequestURI();
        log.info(url);  // warn, error 등

        chain.doFilter(request, response); // 다음 Filter 로 이동

        // 후처리
        log.info("비즈니스 로직 완료");
        // - 위에서 filter -> DispatcherServlet -> Controller에서 이것저것 수행 후 다시 DispatcherServlet이 응답을 보내면 일로 넘어옴!!
    }
}

package org.aegisai.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer { // WebFluxConfigurer

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. 모든 경로(/)에 대해
                .allowedOrigins("http://localhost:3000") // 2. React 서버 주소만 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 3. 허용할 HTTP 메서드
                .allowCredentials(true); // 4. 쿠키 등을 허용할지
    }
}


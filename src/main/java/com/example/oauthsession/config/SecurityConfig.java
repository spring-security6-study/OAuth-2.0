package com.example.oauthsession.config;

import com.example.oauthsession.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin((login) -> login.disable());
        http.httpBasic((basic) -> basic.disable());
        http.cors(cors -> {});

        http.oauth2Login((outh2) -> outh2
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)) //userDetailService를 등록해주는 EdnPoint
                        .successHandler(authenticationSuccessHandler())
                );

//        http.authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
//                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        // 로그인 성공 후 리디렉션할 URL 설정
        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler("http://localhost:3000/");
        successHandler.setAlwaysUseDefaultTargetUrl(true); // 항상 설정된 URL로 리디렉션
        return successHandler;
    }
}

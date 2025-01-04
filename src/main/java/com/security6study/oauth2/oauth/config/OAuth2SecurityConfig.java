package com.security6study.oauth2.oauth.config;

import com.security6study.oauth2.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.security6study.oauth2.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class OAuth2SecurityConfig {

	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
			 .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//				 .requestMatchers(
//					 "/oauth2/**"
//				 ).permitAll()
				 .anyRequest().authenticated()
			 )
			 .oauth2Login(oauth2 -> oauth2
				 .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
				 .successHandler(oAuth2AuthenticationSuccessHandler)
			 )
			 .logout(AbstractHttpConfigurer::disable)
			 .csrf(AbstractHttpConfigurer::disable);

        return http.build();
	}
}

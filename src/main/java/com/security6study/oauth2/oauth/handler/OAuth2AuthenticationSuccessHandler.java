package com.security6study.oauth2.oauth.handler;

import com.security6study.oauth2.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	public String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String targetUrl = getDefaultTargetUrl();

		String accessToken = jwtTokenProvider.createAccessToken(authentication);
		jwtTokenProvider.addRefreshToken(authentication, response);

		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("access-token", accessToken)
			.build().toUriString();
	}
}

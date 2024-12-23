package com.nayoung.oauth2.oauth.handler;

import com.nayoung.oauth2.jwt.token.CustomOAuth2Token;
import com.nayoung.oauth2.oauth.OAuth2Provider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private String KAKAO_CLIENT_ID;
	private String AUTH_TOKEN_URI;
	private String GRANT_TYPE;

	private final RestClient restClient;

	@Autowired
	public OAuth2AuthenticationSuccessHandler(@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
											  @Value("${spring.security.oauth2.client.provider.kakao.token-uri}") String authTokenUri,
											  @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}") String grantType) {
		this.KAKAO_CLIENT_ID = kakaoClientId;
		this.AUTH_TOKEN_URI = authTokenUri;
		this.GRANT_TYPE = grantType;

		this.restClient = RestClient.builder()
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		String id = oauthToken.getPrincipal().getAttributes().get("id").toString();
		String registrationId = oauthToken.getAuthorizedClientRegistrationId();
		String code = request.getParameter("code");

		processOAuth2Token(id, registrationId, code);
	}

	private void processOAuth2Token(String id, String registrationId, String code) {
		OAuth2Provider oAuth2Provider = OAuth2Provider.valueOf(registrationId.toUpperCase());

		CustomOAuth2Token token = null;

		if(oAuth2Provider == OAuth2Provider.GOOGLE) {

		}
		else if(oAuth2Provider == OAuth2Provider.NAVER) {

		}
		else if(oAuth2Provider == OAuth2Provider.KAKAO) {
//			Map attributes = restClient.post()
//				.uri(uriBuilder -> uriBuilder
//					.scheme("https")
//					.path(AUTH_TOKEN_URI)
//					.queryParam("grant_type", GRANT_TYPE)
//					.queryParam("client_id", KAKAO_CLIENT_ID)
//					.queryParam("code", code)
//					.build())
//				.retrieve()
//				.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
//					throw new RuntimeException("Invalid Parameter");
//				})
//				.onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
//					throw new RuntimeException("Internal Server Error");
//				})
//				.body(Map.class);
//
//			assert attributes != null;
//			token = OAuth2TokenFactory.getOAuth2Token(oAuth2Provider, id, attributes);
		}
		else throw new IllegalArgumentException();

		// TODO: TOKEN SAVE
	}

}

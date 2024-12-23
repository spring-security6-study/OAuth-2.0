package com.nayoung.oauth2.jwt.token;

import com.nayoung.oauth2.oauth.OAuth2Provider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2TokenFactory {

	public static CustomOAuth2Token getOAuth2Token(OAuth2Provider oAuth2Provider, String id, Map<String, Object> attributes) {
		if(oAuth2Provider == OAuth2Provider.GOOGLE) {
			// TODO: return new GoogleOAuth2Token(attributes);
		}
		if(oAuth2Provider == OAuth2Provider.NAVER) {
			// TODO: return new NaverOAuth2Token(attributes);
		}
		if(oAuth2Provider == OAuth2Provider.KAKAO) {
			return new KaKaoOAuth2Token(id, attributes);
		}
		throw new IllegalArgumentException("Unsupported OAuth2Provider " + oAuth2Provider);
	}
}

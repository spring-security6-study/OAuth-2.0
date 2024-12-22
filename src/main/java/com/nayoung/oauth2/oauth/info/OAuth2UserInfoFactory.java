package com.nayoung.oauth2.oauth.info;

import com.nayoung.oauth2.oauth.OAuth2Provider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(OAuth2Provider oAuth2Provider, Map<String, Object> attributes) {
		if(oAuth2Provider == OAuth2Provider.GOOGLE) {
			// TODO: return new GoogleOAuth2UserInfo(attributes);
		}
		if(oAuth2Provider == OAuth2Provider.NAVER) {
			// TODO: return new NaverOAuth2UserInfo(attributes);
		}
		if(oAuth2Provider == OAuth2Provider.KAKAO) {
			return new KakaoOAuth2UserInfo(attributes);
		}
		throw new IllegalArgumentException("Unsupported OAuth2Provider " + oAuth2Provider);
	}
}

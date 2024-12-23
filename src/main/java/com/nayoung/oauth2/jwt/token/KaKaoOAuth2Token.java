package com.nayoung.oauth2.jwt.token;

import com.nayoung.oauth2.oauth.OAuth2Provider;

import java.util.Map;

public final class KaKaoOAuth2Token extends CustomOAuth2Token {

	private final String id;

	public KaKaoOAuth2Token(String id, Map<String, Object> attributes) {
		super(attributes, OAuth2Provider.KAKAO);
		this.id = id;
	}

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

	@Override
	public String getExpiresIn() {
		return this.expiresIn;
	}

	@Override
	public String getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	public String getRefreshExpiresIn() {
		return this.refreshExpiresIn;
	}
}

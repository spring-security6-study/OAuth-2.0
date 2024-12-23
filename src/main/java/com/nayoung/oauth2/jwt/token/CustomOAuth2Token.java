package com.nayoung.oauth2.jwt.token;

import com.nayoung.oauth2.oauth.OAuth2Provider;

import java.util.Map;

public abstract sealed class CustomOAuth2Token permits KaKaoOAuth2Token {

	protected String accessToken;

	protected String expiresIn;

	protected String refreshToken;

	protected String refreshExpiresIn;

	protected OAuth2Provider oAuth2Provider;

	CustomOAuth2Token(Map<String, Object> attributes, OAuth2Provider oAuth2Provider) {
		this.accessToken = attributes.get("access_token").toString();
		this.expiresIn = attributes.get("expires_in").toString();
		this.refreshToken = attributes.get("refresh_token").toString();
		this.refreshExpiresIn = attributes.get("refresh_expires_in").toString();
		this.oAuth2Provider = oAuth2Provider;
	}

	public abstract String getAccessToken();

	public abstract String getExpiresIn();

	public abstract String getRefreshToken();

	public abstract String getRefreshExpiresIn();
}

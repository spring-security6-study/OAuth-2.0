package com.nayoung.oauth2.oauth.info;

public abstract sealed class OAuth2UserInfo permits KakaoOAuth2UserInfo {

	public abstract String getId();

	public abstract String getName();

	public abstract String getProfileImageUrl();

	public abstract String getEmail();
}

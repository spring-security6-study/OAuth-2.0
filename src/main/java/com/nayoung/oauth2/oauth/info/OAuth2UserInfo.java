package com.nayoung.oauth2.oauth.info;

import java.util.Map;

public abstract sealed class OAuth2UserInfo permits KakaoOAuth2UserInfo {

	protected final Map<String, Object> attributes;

	protected OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract String getId();

	public abstract String getName();

	public abstract String getProfileImageUrl();
}

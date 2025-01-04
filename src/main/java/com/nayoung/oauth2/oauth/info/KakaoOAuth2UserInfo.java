package com.nayoung.oauth2.oauth.info;

import java.util.Map;

public final class KakaoOAuth2UserInfo extends OAuth2UserInfo {

	private final String id;
	private final Map<String, Object> kakaoAccount;
	private final Map<String, Object> profile;

	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		this.id = attributes.get("id").toString();
		this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		this.profile = (Map<String, Object>) kakaoAccount.get("profile");
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.profile.get("nickname").toString();
	}

	@Override
	public String getProfileImageUrl() {
		return this.profile.get("profile_image_url").toString();
	}

	@Override
	public String getEmail() {
		return "";
	}
}

package com.nayoung.oauth2.oauth;

import com.nayoung.oauth2.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {

	private Member member;
	private transient Map<String, Object> attributes;

	public CustomUserDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	public Long getId() {
		return member.getId();
	}

    // OAuth2User override
	@Override
	public String getName() {
		return member.getName();
	}

	// UserDetails Override
	@Override
	public String getUsername() {
		return member.getName();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().toString()));
	}

	@Override
	public String getPassword() {
		return "";
	}
}

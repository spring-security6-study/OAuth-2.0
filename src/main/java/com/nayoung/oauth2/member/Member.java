package com.nayoung.oauth2.member;

import com.nayoung.oauth2.oauth.OAuth2Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	private String profileImage;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private OAuth2Provider provider;
}
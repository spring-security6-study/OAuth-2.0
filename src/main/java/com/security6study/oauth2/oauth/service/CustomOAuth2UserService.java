package com.security6study.oauth2.oauth.service;

import com.security6study.oauth2.member.Member;
import com.security6study.oauth2.member.Role;
import com.security6study.oauth2.member.repository.MemberRepository;
import com.security6study.oauth2.oauth.CustomUserDetails;
import com.security6study.oauth2.oauth.OAuth2Provider;
import com.security6study.oauth2.oauth.exception.OAuthProcessingException;
import com.security6study.oauth2.oauth.info.OAuth2UserInfo;
import com.security6study.oauth2.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		try {
			OAuth2User oAuth2User = super.loadUser(userRequest);
			return processOAuth2User(userRequest, oAuth2User);
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2Provider oAuth2Provider = OAuth2Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2Provider, oAuth2User.getAttributes());

		if(userInfo.getId().isEmpty()) {
			throw new OAuthProcessingException("Id not found from OAuth2 provider");
		}

		Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());

		Member member;
		if(memberOptional.isPresent()) {
			member = memberOptional.get();
			if(oAuth2Provider != member.getProvider()) {
				// TODO: 다시 로그인 시도
			}
		}
		else {
			member = createMember(userInfo, oAuth2Provider);
		}
		return new CustomUserDetails(member, oAuth2User.getAttributes());
	}

	private Member createMember(OAuth2UserInfo userInfo, OAuth2Provider oAuth2Provider) {
		return memberRepository.save(
			Member.builder()
				.name(userInfo.getName())
				.profileImage(userInfo.getProfileImageUrl())
				.email("test_email")
				.role(Role.USER)
				.provider(oAuth2Provider)
				.build()
		);
	}
}

package com.example.oauthsession.service;

import com.example.oauthsession.entity.User;
import com.example.oauthsession.repository.UserRepository;
import com.example.oauthsession.service.dto.CustomOAuth2User;
import com.example.oauthsession.service.dto.GoogleResponse;
import com.example.oauthsession.service.dto.NaverResponse;
import com.example.oauthsession.service.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAuth2UserService OAuth2UserService의 구현체

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        //db저장
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existDate = userRepository.findByUsername(username);

        String role = null;
        if(existDate == null) { //새로 생성
            User user = new User();

            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setRole("ROLE_USER");

            userRepository.save(user);

        } else { //데이터 존재 -> update
            existDate.setEmail(oAuth2Response.getEmail());

            role = existDate.getRole();
            userRepository.save(existDate);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
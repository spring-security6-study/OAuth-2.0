package com.example.oauthsession.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() { //리소스 서버로부터 넘어오는 모든 데이터
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //role값

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUserName() {
        return oAuth2Response.getProvider()+ " " + oAuth2Response.getProviderId();
    }

}

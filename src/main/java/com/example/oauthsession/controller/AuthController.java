package com.example.oauthsession.controller;

import com.example.oauthsession.service.dto.CustomOAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not logged in");
        }

        // 인증된 사용자 정보를 CustomOAuth2User로 가져오기
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();

        System.out.println("Attributes: " + user.getAttributes());
        return ResponseEntity.ok(user.getAttributes());
    }
}

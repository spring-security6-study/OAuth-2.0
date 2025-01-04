package com.security6study.oauth2.oauth.api;

import com.security6study.oauth2.oauth.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OauthController {

	@GetMapping("/")
	public void home(@AuthenticationPrincipal CustomUserDetails user) {
	}
}

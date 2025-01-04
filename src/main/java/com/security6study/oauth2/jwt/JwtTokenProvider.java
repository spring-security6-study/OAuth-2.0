package com.security6study.oauth2.jwt;

import com.security6study.oauth2.jwt.repository.RefreshTokenRepository;
import com.security6study.oauth2.jwt.token.RefreshToken;
import com.security6study.oauth2.oauth.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

	private static final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60;    // 1hour
	private static final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;  // 1week
	private final SecretKey secretKey;

	private final RefreshTokenRepository refreshTokenRepository;

	@Autowired
	public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, RefreshTokenRepository refreshTokenRepository) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String createAccessToken(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		String role = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		Date now = new Date();
		Date validityDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

		return Jwts.builder()
			.signWith(secretKey)
			.subject(String.valueOf(user.getId()))
			.claim("role", role)
			.issuer("security-study")
			.issuedAt(now)
			.expiration(validityDate)
			.compact();
	}

	public void addRefreshToken(Authentication authentication, HttpServletResponse httpServletResponse) {
		String refreshToken = createRefreshToken();
		saveRefreshToken(authentication, refreshToken);
	}

	public String createRefreshToken() {
		Date now = new Date();
		Date validityDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

		return Jwts.builder()
			.signWith(secretKey)
			.issuer("security-study")
			.issuedAt(now)
			.expiration(validityDate)
			.compact();
	}

	private void saveRefreshToken(Authentication authentication, String refreshToken) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		refreshTokenRepository.save(RefreshToken.builder()
			.id(user.getId())
			.token(refreshToken)
			.expiredTime(REFRESH_TOKEN_EXPIRE_LENGTH)
			.build());
	}
}
